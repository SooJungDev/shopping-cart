import Vue from 'vue';
import Vuex from 'vuex';
import common from '@/store/modules/common';
import cart from '@/store/modules/cart';
import goods from '@/store/modules/goods';


Vue.use(Vuex);


const store = new Vuex.Store({
  modules: {
    common,
    cart,
    goods,
  },
});

export default store;




