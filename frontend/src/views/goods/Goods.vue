<template>
  <div>
    <v-container fluid grid-list-xl>
      <v-layout wrap justify-space-around>
        <v-flex v-for="card in 9" :key="card.num">
          <v-card
          class="mx-auto"
          max-width="330">
          <v-card-title>
            <h2 class="display-1">상품이름</h2>
            <v-spacer></v-spacer>
            <span class="title">₩가격</span>
          </v-card-title>
            <v-card-title>
              <span class="title">공급처</span>
            </v-card-title>

          <v-card-text>
            이상품의 이름은 ㅡㅡㅡㅡ이며, 해당 상품을 판매하는 곳은 ㅡㅡㅡㅡ입니다.
            <br>배송비는 아래와 같습니다.
            ________ , ______________
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
              color="deep-purple accent-4"
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
export default {
  name: 'Goods',
  data: () => ({
    seletctItems: ['Foo', 'Bar', 'Fizz', 'Buzz']
  })
}
</script>

<style scoped>

</style>
