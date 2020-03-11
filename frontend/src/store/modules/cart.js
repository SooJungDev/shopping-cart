import axios from 'axios'

const url = '/cart'
const cart = {
  state: {
    paramCartGoodsList: [],
    cartGoodsList: [],
    cartGoodsListSize: 0,
    cartInfo: {},
    paramCart: {}
  },

  getters: {

  },

  mutations: {
    setCartInfo: (state, data) => {
      state.cartInfo = data
    },
    setParamCartGoodsList: (state, data) => {
      state.paramCartGoodsList = data
    },
    setCartGoodsList: (state, data) => {
      state.cartGoodsList = data
    },
    setCartGoodsListSize: (state, data) => {
      state.cartGoodsListSize = data
    }
  },

  actions: {
    getCartInfo ({commit}) {
      let userId = localStorage.userId
      return axios.get(url + '/' + userId).then((response) => {
        if (response.data.status === 200) {
          commit('setParamCartGoodsList', response.data.data.goodsList)
          commit('setCartGoodsList', response.data.data.goodsList)
          commit('setCartGoodsListSize', response.data.data.goodsList.length)
          commit('setCartInfo', response.data.data)
        }
      }).catch((e) => {
        console.error(e)
      })
    },
    updateGoodsToCart ({ commit, dispatch }, paramCart) {
      return axios.post(url, paramCart)
        .then(() => {
          dispatch('getCartInfo')
        }).catch((e) => {
          console.error(e)
        })
    }
  }
}

export default cart
