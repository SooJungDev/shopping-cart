import 'vuetify/dist/vuetify.min.css';
import 'material-design-icons-iconfont/dist/material-design-icons.css';
import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-balham.css';

import { AgGridVue } from 'ag-grid-vue';

import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import Vuetify from 'vuetify';
import router from './router';
import store from './store';

Vue.use(Vuetify);
Vue.config.productionTip = false

Vue.component('ag-grid-vue', AgGridVue);

new Vue({
  render: h => h(App),
  router,
  store,
}).$mount('#app')
