<template>
  <v-navigation-drawer
    :value="sideMenuDrawer"
    clipped
    fixed
    app
  >
    <v-list dense v-for="menu in menuList" :key="menu.id">
      <v-list-item @click="menuMove(menu.path)">
        <v-list-item-action>
          <v-icon>{{menu.icon}}</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>{{menu.name}}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'SideMenuList',
  components: {
  },
  created () {

  },
  data () {
    return {
      menuList:
        [
          {
            id: 0,
            name: '상품 목록',
            icon: '',
            path: '/goods'
          },
          {
            id: 1,
            name: '장바구니',
            icon: '',
            path: '/cart'
          }
        ]
    }
  },
  computed: {
    ...mapState({
      sideMenuDrawer: state => state.common.sideMenuDrawer
    })
  },
  methods: {
    menuMove (path) {
      this.$router.push(path)
    }
  }
}
</script>

<style scoped>

</style>
