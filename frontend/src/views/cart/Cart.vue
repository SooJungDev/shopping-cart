<template>
  <div>
    <v-container>
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
              <v-checkbox  :label='cartGoods.goods.name'></v-checkbox>
              <v-spacer></v-spacer>
              <v-icon class="mr-1">mdi-delete</v-icon>
            </v-card-title>
            <v-card-subtitle>
              {{cartGoods.goods.name}}
                색상:<span>{{cartGoods.selectOption.color}}</span>
                /사이즈:<span>{{cartGoods.selectOption.size}}</span>
              <span>수량 :   <v-btn small >+</v-btn>
              <span>{{cartGoods.buyCount}}</span>
                <v-btn small>-</v-btn><br></span>
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
              <span>총 상품수량: <br></span>
              <span>총 상품금액: {{cartInfo.totalGoodsAmount}} <br></span>
              <span>총 배송비:{{cartInfo.totalShippingAmount}} <br></span>
              <span>총 결제금액:{{cartInfo.totalPaymentAmount}} <br></span>
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
import { mapState, mapActions } from 'vuex'

export default {
  name: 'Cart',
  created () {
    this.getCartInfo(this.cartUserId)
  },
  data: () => ({
    name: '상품이름',
    checkbox: true
  }),
  computed: {
    ...mapState({
      cartUserId: state => state.cart.cartUserId,
      cartInfo: state => state.cart.cartInfo,
      cartGoodsList: state => state.cart.cartGoodsList
    })
  },
  methods: {
    ...mapActions([
      'getCartInfo'
    ])
  },
  destroyed () {

  }
}
</script>

<style scoped>

</style>
