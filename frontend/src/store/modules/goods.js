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
        commit('setGoodsList', response.data.list)
      }).catch((e) => {
        console.error(e)
      })
    }
  }
}

export default goods
