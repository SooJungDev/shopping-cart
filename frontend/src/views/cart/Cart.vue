<template>
  <div>
    <v-container>
      <v-toolbar
        color="#00C1A3"
        dark
        cols="12"
      >
        <v-checkbox v-model="allcheck" label='전체선택' @change="changeAllcheck()"></v-checkbox>
        <v-spacer></v-spacer>

        <span> &nbsp;  </span>
        <v-btn @click="deleteSelectCart()">삭제</v-btn>

      </v-toolbar>
      <v-row align="center"
             justify="center">
        <v-col
          v-for="(cartGoods, i) in cartGoodsList"
          :key="i"
          cols="12"
        >
          <v-card
            color="#00C1A3"
            dark
          >

            <v-card-title class="headline">
              <v-checkbox :input-value="cartGoods.checked" v-model="cartGoods.checked" :label='cartGoods.goods.name'  @change="check(cartGoods)"></v-checkbox>
              <v-spacer></v-spacer>
              <v-icon class="mr-1" @click="deleteCart(cartGoods)">mdi-delete</v-icon>
            </v-card-title>
            <v-card-subtitle>
              {{cartGoods.goods.name}}
                색상:<span>{{cartGoods.selectOption.color}}</span>
                /사이즈:<span>{{cartGoods.selectOption.size}}</span>
              <span>수량 :   <v-btn small  @click="plusGoodsStock(cartGoods)">+</v-btn>
              <span>&nbsp;{{cartGoods.buyCount}}&nbsp; </span>
                <v-btn small @click="minusGoodsStock(cartGoods)">-</v-btn><br></span>
            </v-card-subtitle>

            <v-card-text>
              <span>상품금액 :{{cartGoods.goods.price}} <br></span>
              <span>주문금액 :{{cartGoods.buyCount * cartGoods.goods.price}} <br></span>
              <span>배송비 :{{cartGoods.goods.shipping.price}}<br> </span>
            </v-card-text>

          </v-card>
        </v-col>
        <v-col cols="12">
          <v-card
            color="black"
            dark
          >

            <v-card-title class="headline">
              <h1 class="display-1">총 주문정보</h1>
              <v-spacer></v-spacer>
            </v-card-title>
            <v-card-subtitle></v-card-subtitle>

            <v-card-text>
              <span>총 상품금액: {{cartPurchaseInfo.totalGoodsAmount}} <br></span>
              <span>총 배송비:{{cartPurchaseInfo.totalShippingAmount}} <br></span>
              <span>총 결제금액:{{cartPurchaseInfo.totalPaymentAmount}} <br></span>
            </v-card-text>
            <v-card-text>
              <v-btn  color="#00C1A3">결제하기</v-btn>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>
<script>
import {mapState, mapActions, mapMutations} from 'vuex'

export default {
  name: 'Cart',
  created () {
    this.getCartInfo(this.cartUserId)
    this.checkedList = this.paramCartGoodsList
  },
  data: () => ({
    allcheck: true,
    checkedList: [],
    uncheckedList: []
  }),
  computed: {
    ...mapState({
      cartUserId: state => state.cart.cartUserId,
      cartInfo: state => state.cart.cartInfo,
      cartPurchaseInfo: state => state.cart.cartPurchaseInfo,
      cartGoodsList: state => state.cart.cartGoodsList,
      paramCartGoodsList: state => state.cart.paramCartGoodsList
    })
  },
  methods: {
    ...mapMutations([
      'setCartPurchaseInfo'
    ]),
    ...mapActions([
      'getCartInfo',
      'deleteGoodsToCart',
      'updateGoodsToCart',
      'getCheckGoodsPurchaseInfo'
    ]),
    plusGoodsStock (cartGoods) {
      ++cartGoods.buyCount
      if (cartGoods.buyCount > cartGoods.selectOption.stock) {
        alert('선택한 옵션의 구매 할 수 있는 개수를 초과하였습니다.\n 현재 재고수량:' + cartGoods.selectOption.stock)
        cartGoods.buyCount = cartGoods.selectOption.stock
        return
      }
      this.updateGoodsStock(cartGoods)
    },
    minusGoodsStock (cartGoods) {
      --cartGoods.buyCount
      if (cartGoods.buyCount < 1) {
        cartGoods.buyCount = 1
        return
      }

      this.updateGoodsStock(cartGoods)
    },
    updateGoodsStock (cartGoods) {
      this.updateGoodsToCart(cartGoods)
    },
    deleteCart (cartGoods) {
      this.deleteGoodsToCart([cartGoods])
    },
    deleteSelectCart () {
      let param = this.checkedList
      if (this.checkedList.length < 1) {
        alert('삭제 할 상품을 선택해주세요.')
        return
      }

      this.deleteGoodsToCart(param)
    },
    check (cartGoods) {
      if (cartGoods.checked) {
        this.checkedList.push(cartGoods)
        this.judgeAllCheck()
        this.getCheckGoodsPurchaseInfo(this.checkedList)
        return
      }

      const idx = this.checkedList.findIndex((item) => { return item.id === cartGoods.id })
      if (idx > -1) {
        this.checkedList.splice(idx, 1)
      }

      this.judgeAllCheck()
      if (this.checkedList.length > 0) {
        this.getCheckGoodsPurchaseInfo(this.checkedList)
        return
      }
      this.setCartPurchaseInfo({totalGoodsAmount: 0, totalShippingAmount: 0, totalPaymentAmount: 0})
    },
    changeAllcheck () {
      for (let cartGoods of this.cartGoodsList) {
        cartGoods.checked = this.allcheck
      }

      if (this.allcheck) {
        this.checkedList = this.cartGoodsList
        this.getCheckGoodsPurchaseInfo(this.checkedList)
        return
      }

      this.checkedList = []
      this.setCartPurchaseInfo({totalGoodsAmount: 0, totalShippingAmount: 0, totalPaymentAmount: 0})
    },
    judgeAllCheck () {
      if (this.checkedList.length !== this.cartGoodsList.length) {
        this.allcheck = false
        return
      }
      this.allcheck = true
    }
  },
  destroyed () {

  }
}
</script>

<style scoped>

</style>
