<template>
  <div id="app">
    <v-app dark>
      <div v-if="showCommonMenu">
        <side-menu-list></side-menu-list>
        <header-area></header-area>
        <v-content>
          <v-container fluid fill-height>
            <v-layout align-space-around>
              <router-view></router-view>
            </v-layout>
          </v-container>
        </v-content>
        <footer-area></footer-area>
      </div>
      <!--login -->
      <div v-else>
        <router-view></router-view>
      </div>
    </v-app>
  </div>
</template>

<script>
import HeaderArea from '@/components/common/HeaderArea'
import FooterArea from '@/components/common/FooterArea'
import SideMenuList from '@/components/common/SideMenuList'
import { mapState } from 'vuex'

export default {
  name: 'App',
  components: {
    HeaderArea,
    SideMenuList,
    FooterArea
  },
  created () {
    // 이벤트 등록
    // 초기화 코드 실행
  },
  props: {
    source: String
  },
  data () {
    return {
    }
  },
  computed: {
    ...mapState({
      showCommonMenu: state => state.common.showCommonMenu
    })
  },
  methods: {
  },
  destroyed () {
  }
}
</script>

<style>

</style>
