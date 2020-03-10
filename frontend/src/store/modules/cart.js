// import axios from 'axios'
const cart = {
  state: {
    // Vue의 data
  },

  getters: {
    // Vue의 computed
    //    : Vue의 computed와 같이 미리 연산된 값을 접근
  },

  mutations: {
    // Vue의 method
    //    : mutations에는 동기적은 로직만 구현. (getters와 차이점은 parameter를 넘겨줄 수 있다.)
  },

  actions: {
    // Vue의 method와 같지만 actions는 async method
    //    : actions에는 비동기 로직을 구현. (동기로직이 필요할 경우 commit으로 mutations에 구현한 함수를 호출)
  }
}

export default cart
