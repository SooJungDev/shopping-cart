import axios from 'axios'
const goods = {
  state: {
    goodsList: []
  },

  getters: {

  },

  mutations: {
    setGoodsList: (state, data) => {
      state.goodsList = data
    }
  },

  actions: {
    getGoodsList ({ commit }) {
      const url = '/goods'
      return axios.get(url, {
        params: {}
      }).then((response) => {
        let list = response.data.list
        for (let goods of list) {
          goods.buyCount = 1
        }
        commit('setGoodsList', list)
      }).catch((e) => {
        console.error(e)
      })
    }
  }
}

export default goods
