import Vue from 'vue'
import Vuex from 'vuex'
import common from '@/store/modules/common'
import goods from '@/store/modules/goods'
import cart from '@/store/modules/cart'
import axios from 'axios'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    common,
    goods,
    cart
  }
})

export default store

const enhanceAccessToeken = () => {
  const { accessToken } = localStorage
  if (!accessToken) return
  axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`
  store.commit('setShowCommonMenu', true)
}
enhanceAccessToeken()
