package com.shopping.cart.service;

import static java.util.stream.Collectors.groupingBy;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.cart.model.Cart;
import com.shopping.cart.model.CartDto;
import com.shopping.cart.model.CartGoods;
import com.shopping.cart.model.CartGoodsDto;
import com.shopping.cart.model.PurchaseInfoDto;
import com.shopping.cart.repository.CartGoodRepository;
import com.shopping.cart.repository.CartRepository;
import com.shopping.goods.model.Goods;
import com.shopping.shipping.model.Shipping;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class CartService {

    private final static String PREPAY = "PREPAY";

    private final CartRepository cartRepository;
    private final CartGoodRepository cartGoodRepository;

    @Transactional(readOnly = true)
    public Optional<Cart> getCart(Long id) {
        return cartRepository.findByUserId(id);
    }

    public CartDto getCartAmountInfo(Optional<Cart> cart) {
        Cart cartResult = cart.get();
        Set<CartGoods> goodsList = cartResult.getGoodsList();

        PurchaseInfoDto purchaseInfo = getPurchaseInfo(goodsList);

        return CartDto.builder()
                      .id(cartResult.getId())
                      .goodsList(goodsList)
                      .purchaseInfo(purchaseInfo)
                      .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public int addGoodsToCart(CartDto cartDto) {
        findCartIdAndSetting(cartDto);

        Cart cart = cartDto.toEntity();

        Set<CartGoods> goodsList = cartDto.getGoodsList();
        Cart save = cartRepository.save(cart);
        for (CartGoods cartGoods : goodsList) {
            cartGoods.setCart(save);
        }

        return cartGoodRepository.saveAll(goodsList).size();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteGoodsToCart(CartDto cartDto) {
        findCartIdAndSetting(cartDto);

        Cart cart = cartDto.toEntity();

        Set<CartGoods> goodsList = cartDto.getGoodsList();
        for (CartGoods cartGoods : goodsList) {
            cartGoods.setCart(null);
        }

        cartGoodRepository.deleteAll(goodsList);

    }

    private void findCartIdAndSetting(CartDto cartDto) {
        Optional<Cart> byUserId = cartRepository.findByUserId(cartDto.getUser().getId());

        if (byUserId.isPresent()) {
            cartDto.setId(byUserId.get().getId());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Long updateGoodsToCart(CartGoodsDto cartGoodsDto) {
        CartGoods cartGoods = cartGoodRepository.save(cartGoodsDto.toEntity());
        return cartGoods.getId();

    }

    public PurchaseInfoDto getPurchaseInfo(Set<CartGoods> goodsList) {
        int totalGoodsAmount = 0;
        int totalShippingAmount = 0;

        for (CartGoods goods : goodsList) {
            int buyCount = goods.getBuyCount();
            Goods goodsInfo = goods.getGoods();
            int goodsPrice = goodsInfo.getPrice();
            int shippingPrice = goodsInfo.getShipping().getPrice();
            totalGoodsAmount += buyCount * goodsPrice;
            totalShippingAmount += shippingPrice;

        }

        return getPurchaseInfoDto(totalGoodsAmount, totalShippingAmount);
    }

    public PurchaseInfoDto getCheckGoodsPurchaseInfo(List<CartGoodsDto> cartGoodsDtoList) {
        int totalGoodsAmount = getTotalGoodsAmount(cartGoodsDtoList);
        int totalShippingAmount = getTotalShippingAmount(cartGoodsDtoList);

        return getPurchaseInfoDto(totalGoodsAmount, totalShippingAmount);
    }

    private int getTotalShippingAmount(List<CartGoodsDto> cartGoodsDtoList) {
        int totalShippingAmount = 0;
        Map<String, List<CartGoodsDto>> cartGoodsByProvider = cartGoodsByProvider(cartGoodsDtoList);

        for (String provider : cartGoodsByProvider.keySet()) {
            List<CartGoodsDto> cartGoodsList = cartGoodsByProvider.get(provider);
            List<Shipping> prepayShippings = findPrepayShippings(cartGoodsList);
            totalShippingAmount = plusMinPriceIsBundle(totalShippingAmount, prepayShippings);
            totalShippingAmount = plusIsNotBundle(totalShippingAmount, prepayShippings);

        }

        return totalShippingAmount;
    }

    private int plusMinPriceIsBundle(int totalShippingAmount, List<Shipping> prepayShippings) {
        Shipping shipping = getMinPriceByBundle(prepayShippings);
        if (shipping != null) {
            totalShippingAmount += shipping.getPrice();
        }
        return totalShippingAmount;
    }

    private int plusIsNotBundle(int totalShippingAmount, List<Shipping> prepayShippings) {
        for (Shipping prepayShipping : prepayShippings) {
            if (!prepayShipping.isCanBundle()) {
                totalShippingAmount += prepayShipping.getPrice();
            }
        }
        return totalShippingAmount;
    }

    private Shipping getMinPriceByBundle(List<Shipping> prepayShippings) {
        return prepayShippings.stream()
                              .min(Comparator.comparing(Shipping::getPrice))
                              .filter(Shipping::isCanBundle)
                              .orElseGet(null);
    }

    private List<Shipping> findPrepayShippings(List<CartGoodsDto> cartGoodsList) {
        return cartGoodsList.stream()
                            .filter(cartGoodsDto -> PREPAY.equals(cartGoodsDto
                                                                          .getGoods()
                                                                          .getShipping()
                                                                          .getMethod()))
                            .map(CartGoodsDto::getGoods).map(goods -> goods.getShipping())
                            .collect(Collectors.toList());
    }

    private Map<String, List<CartGoodsDto>> cartGoodsByProvider(List<CartGoodsDto> cartGoodsDtoList) {
        return cartGoodsDtoList.stream()
                               .collect(groupingBy(
                                       cartGoodsDto -> cartGoodsDto
                                               .getGoods().getProvider()));
    }

    private int getTotalGoodsAmount(List<CartGoodsDto> cartGoodsDtoList) {
        int totalGoodsAmount = 0;
        for (CartGoodsDto CartGoodsDto : cartGoodsDtoList) {
            int buyCount = CartGoodsDto.getBuyCount();
            int goodsPrice = CartGoodsDto.getGoods().getPrice();
            totalGoodsAmount += buyCount * goodsPrice;
        }
        return totalGoodsAmount;
    }

    private PurchaseInfoDto getPurchaseInfoDto(int totalGoodsAmount, int totalShippingAmount) {
        return PurchaseInfoDto.builder().totalGoodsAmount(totalGoodsAmount).totalShippingAmount(
                totalShippingAmount).build();
    }

}
