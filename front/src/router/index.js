import Vue from 'vue';
import Router from 'vue-router';
import Login from '@/views/Login';



Vue.use(Router);

const router = new Router({
  routes:
    [
      {
        path: '/',
        name: 'Login',
        component: Login,
      },
      {
        path: '/',
        name: 'Goods',
        component: Goods,
      },
      {
        path: '/',
        name: 'Cart',
        component: Cart,
      }
    ],
});
export default router;