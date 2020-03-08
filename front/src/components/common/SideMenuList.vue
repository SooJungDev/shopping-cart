<template>
  <v-navigation-drawer
    :value="sideMenuDrawer"
    clipped
    fixed
    app
  >
    <v-list dense v-for="menu in menuList" :key="menu.id">
      <v-list-tile @click="menuMove(menu.path)">
        <v-list-tile-action>
          <v-icon>{{menu.icon}}</v-icon>
        </v-list-tile-action>
        <v-list-tile-content>
          <v-list-tile-title>{{menu.name}}</v-list-tile-title>
        </v-list-tile-content>
      </v-list-tile>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
import { mapState } from 'vuex';

export default {
  name: 'SideMenuList',
  components: {
  },
  created() {

  },
  data() {
    return {
      menuList:
        [
          {
            id: 0,
            name: '상품 목록',
            icon: 'list',
            path: '/appVersion',
          },
          {
            id: 1,
            name: '장바구니',
            icon: 'cart',
            path: '/commonInit',
          }
        ],
    };
  },
  computed: {
    ...mapState({
      sideMenuDrawer: state => state.common.sideMenuDrawer,
    }),
  },
  methods: {
    menuMove(path) {
      this.$router.push(path);
    },
  },
};
</script>

<style scoped>

</style>
