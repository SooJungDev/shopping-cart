<template>
  <div>
    <v-container fluid fill-height >
      <v-layout wrap justify-space-around>
        <v-flex v-for="goods in goodsList" :key="goods.id" style="margin-bottom:15px">
          <v-card max-width="400" height="100%" >
          <v-card-title>
            <h1 class="display-1">{{goods.name}}</h1>
            <v-spacer></v-spacer>
            <span class="title">₩{{goods.price}}</span>
          </v-card-title>
            <v-card-title>
              <span class="title">{{goods.provider}}</span>
            </v-card-title>

          <v-card-text >
            이 상품의 이름은 {{goods.name}}이며, 해당 상품을 판매하는 곳은 {{goods.provider}}입니다.<br>
            <template v-if="goods.shipping.method==='FREE'">배송비 무료,</template>
            <template v-else>배송비: {{goods.shipping.price}},</template>
            <template v-if="goods.shipping.canBundle">묶음배송 가능</template>
            <template v-else>묶음배송 불가</template>
          </v-card-text>

          <v-divider class="mx-4"></v-divider>

          <v-card-text>
            <span class="subheading">옵션</span>
            <template>
            <v-select
              placeholder="옵션을 선택해 주세요"
              v-model="goods.selectOption"
              :items="goods.options"
              item-value="id"
              :item-text="item => '색상 :'+item.color +' /size :'+ item.size"
              :item-disabled="item => item.stock ===0 ? true:false"
            ></v-select>
          </template>
            <span>수량 :   <v-btn small  @click="plusGoodsStock(goods)">+</v-btn>
              <span>&nbsp;{{goods.buyCount}}&nbsp; </span>
                <v-btn small @click="minusGoodsStock(goods)">-</v-btn><br></span>
          </v-card-text>

          <v-card-actions style="position:absolute; bottom:0px;right: 0px">
            <v-btn
              block
              class="white--text"
              color="#00C1A3"
              @click="addCart(goods)"

            >
              장바구니 담기
            </v-btn>
          </v-card-actions>
        </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </div>
</template>
<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'Goods',
  components: {
  },
  created () {
    this.getGoodsList()
  },
  data: () => ({
  }),
  computed: {
    ...mapState({
      goodsList: state => state.goods.goodsList,
    })
  },
  methods: {
    ...mapActions([
      'getGoodsList',
      'addGoodsToCart'
    ]),
    plusGoodsStock (goods) {
      if (goods.selectOption === undefined) {
        alert('상품 옵션을 선택해 주세요.')
        return
      }

      let option = goods.options.filter(x => x.id === goods.selectOption)
      ++goods.buyCount
      if (goods.buyCount > option[0].stock) {
        alert('선택한 옵션의 구매 할 수 있는 개수를 초과하였습니다.\n 현재 재고수량:' + option[0].stock)
        goods.buyCount = option[0].stock
      }
    },
    minusGoodsStock (goods) {
      --goods.buyCount
      if (goods.buyCount < 1) {
        goods.buyCount = 1
      }
    },
    addCart (goods) {
      if (goods.selectOption === undefined) {
        alert('상품 옵션을 선택해 주세요.')
        return
      }
      let cartGoods = {}
      cartGoods.goods = goods
      cartGoods.buyCount = goods.buyCount
      cartGoods.selectOption = {id: goods.selectOption}
      this.addGoodsToCart([cartGoods])
    }
  },
  destroyed () {

  }
}
</script>

<style scoped>

</style>
