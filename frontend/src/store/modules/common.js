import axios from 'axios'
import router from '@/router/index'

const common = {
  state: {
    sideMenuDrawer: false,
    showCommonMenu: false,
    accessToken: null,
    userId: ''
  },
  getters: {
    accessToken: state => state.accessToken,
    userId: state => state.userId
  },
  mutations: {
    toggleSideMenuDrawer: (state) => {
      state.sideMenuDrawer = !state.sideMenuDrawer
    },
    setShowCommonMenu: (state, data) => {
      state.showCommonMenu = data
    },
    setAccessToken: (state, data) => {
      state.accessToken = data
      localStorage.accessToken = data
    },
    setUserId: (state, data) => {
      state.userId = data
      localStorage.userId = data
    },
    logOut: (state) => {
      state.accessToken = null
      delete localStorage.accessToken
      delete localStorage.userId
    }
  },
  actions: {
    login ({commit, dispatch, rootState}, userInfo) {
      const url = '/auth/login'
      return axios.post(url, userInfo)
        .then((response) => {
          axios.defaults.headers.common.Authorization = `Bearer ${response.data.access_token}`
          commit('setAccessToken', response.data.access_token)
          commit('setShowCommonMenu', true)
          commit('setUserId', response.data.user_id)
          dispatch('getCartInfo')
          router.push('/goods')
        }).catch(() => {
          alert('Login에 실패하였습니다. 다시 시도해주세요.')
        })
    },
    logout ({ commit }) {
      commit('setShowCommonMenu', false)
      axios.defaults.headers.common.Authorization = undefined
      commit('logOut')
    }
  }
}

export default common
