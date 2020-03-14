import axios from 'axios'

const url = '/cart'
const userId = localStorage.userId
const cart = {
  state: {
    paramCartGoodsList: [],
    cartGoodsList: [],
    cartGoodsListSize: 0,
    cartInfo: {},
    paramCart: {}
  },

  getters: {},

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
      return axios.get(url + '/' + userId).then((response) => {
        if (response.data.status === 200) {
          let cartInfo = response.data.data
          let goodsList = cartInfo.goodsList
          for (let goods of goodsList) {
            goods.checked = true
          }
          commit('setParamCartGoodsList', goodsList)
          commit('setCartGoodsList', goodsList)
          commit('setCartGoodsListSize', goodsList.length)
          commit('setCartInfo', cartInfo)
        }
      }).catch((e) => {
        console.error(e)
      })
    },
    addGoodsToCart ({state, commit, dispatch}, paramCartGoodsList) {
      let paramCart = {
        'id': state.cartInfo.id,
        'user': {
          'id': userId
        },
        'goodsList': paramCartGoodsList
      }
      return axios.post(url, paramCart).then(() => {
        dispatch('getCartInfo')
      }).catch((e) => {
        console.error(e)
      })
    },
    deleteGoodsToCart ({state, commit, dispatch}, paramCartGoodsList) {
      let paramCart = {
        'id': state.cartInfo.id,
        'user': {
          'id': userId
        },
        'goodsList': paramCartGoodsList
      }
      return axios.delete(url, {data: paramCart}).then(() => {
        dispatch('getCartInfo')
      }).catch((e) => {
        console.error(e)
      })
    },
    updateGoodsToCart ({state, commit, dispatch}, cartGoods) {
      let param = {
        'cart': {
          'id': state.cartInfo.id
        },
        'id': cartGoods.id,
        'goods': cartGoods.goods,
        'selectOption': cartGoods.selectOption,
        'buycount': cartGoods.buyCount
      }
      console.log(param)
      return axios.patch(url, param).then(() => {
        dispatch('getCartInfo')
      }).catch((e) => {
        console.error(e)
      })
    }
  }
}

export default cart
