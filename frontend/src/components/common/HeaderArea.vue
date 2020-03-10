<template>
  <v-app-bar
    app
    color="indigo"
    dark
  >
    <v-app-bar-nav-icon @click.stop="toggleSideMenuDrawer" />
    <v-toolbar-title>shopping cart</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-btn icon>
      <v-icon @click="cart">mdi-cart</v-icon>
    </v-btn>
    <v-btn icon>
      <v-icon @click="logOut">mdi-logout</v-icon>
    </v-btn>
  </v-app-bar>
</template>

<script>
import { mapMutations, mapActions } from 'vuex'

export default {
  name: 'HeaderArea',
  methods: {
    ...mapActions([
      'logout'
    ]),
    ...mapMutations([
      'toggleSideMenuDrawer',
      'setShowCommonMenu'
    ]),
    logOut () {
      this.$router.push('/login')
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
