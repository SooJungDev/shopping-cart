<template>
  <div>
    <v-container fluid grid-list-xl>
      <v-layout wrap justify-space-around>
        <v-flex v-for="goods in goodsList" :key="goods.id">
          <v-card max-width="330">
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
            ></v-select>
          </template>
          </v-card-text>

          <v-card-actions>
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
      paramCartGoodsList: state => state.cart.paramCartGoodsList
    })
  },
  methods: {
    ...mapActions([
      'getGoodsList',
      'updateGoodsToCart'
    ]),
    addCart (goods) {
      if (goods.selectOption === undefined) {
        alert('상품 옵션을 선택해 주세요.')
        return
      }
      let cartGoods = {}
      cartGoods.goods = goods
      cartGoods.buyCount = 1
      cartGoods.selectOption = {id: goods.selectOption}
      this.paramCartGoodsList.push(cartGoods)
      this.updateGoodsToCart(this.paramCartGoodsList)
    }
  },
  destroyed () {

  }
}
</script>

<style scoped>

</style>
