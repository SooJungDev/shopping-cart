import axios from 'axios'
import router from '@/router/index'

const common = {
  state: {
    sideMenuDrawer: true,
    showCommonMenu: false,
    accessToken: null
  },
  getters: {
    getAccessToken: state => state.accessToken
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
    logOut: (state) => {
      state.accessToken = null
      delete localStorage.accessToken
    }
  },
  actions: {
    login ({ commit }, userInfo) {
      const url = '/auth/login'
      return axios.post(url, userInfo)
        .then((response) => {
          axios.defaults.headers.common.Authorization = `Bearer ${response.data.access_token}`
          commit('setAccessToken', response.data.access_token)
          commit('setShowCommonMenu', true)
          router.push('/appVersion')
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
