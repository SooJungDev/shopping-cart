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
            <span class="subheading">색상</span>
            <v-select
              :items="seletctItems"
              label="Solo field"
              solo
            ></v-select>
            <span class="subheading">사이즈</span>
            <v-select
              :items="seletctItems"
              label="Solo field"
              solo
            ></v-select>
          </v-card-text>

          <v-card-actions>
            <v-btn
              block
              class="white--text"
              color="#00C1A3"
              @click="addCart"
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
    seletctItems: ['Foo', 'Bar', 'Fizz', 'Buzz']
  }),
  computed: {
    ...mapState({
      goodsList: state => state.goods.goodsList
    })
  },
  methods: {
    ...mapActions([
      'getGoodsList'
    ]),
    addCart () {
      console.log('addcart')
    }
  },
  destroyed () {

  }
}
</script>

<style scoped>

</style>
