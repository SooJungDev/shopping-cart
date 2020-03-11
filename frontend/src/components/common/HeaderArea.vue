<template>
  <v-app-bar
    app
    color="dark"
    dark
  >
    <v-app-bar-nav-icon @click.stop="toggleSideMenuDrawer" />
    <v-toolbar-title>shopping cart</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-btn icon>
      <v-badge
        :content="cartGoodsListSize"
        :value="cartGoodsListSize"
        color="#00C1A3"
        overlap
      >
        <v-icon @click="cart">mdi-cart</v-icon>
      </v-badge>
    </v-btn>
    <v-btn icon>
      <v-icon @click="logOut">mdi-logout</v-icon>
    </v-btn>
  </v-app-bar>
</template>

<script>
import {mapMutations, mapActions, mapState} from 'vuex'

export default {
  name: 'HeaderArea',
  computed: {
    ...mapState({
      cartGoodsListSize: state => state.cart.cartGoodsListSize
    })
  },
  methods: {
    ...mapActions([
      'logout'
    ]),
    ...mapMutations([
      'toggleSideMenuDrawer',
      'setShowCommonMenu'
    ]),
    logOut () {
      this.$router.push('/')
      this.logout()
    },
    cart () {
      this.$router.push('/cart')
    }
  }
}
</script>

<style scoped>

</style>
