import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/Login'
import Cart from '@/views/cart/Cart'
import Goods from '@/views/goods/Goods'
import store from '@/store/index'

Vue.use(Router)

// jwt 토큰 뷰 네비게이션 가드로 처리해줌
/* eslint-disable */
const requireAuth = () => (to, from, next) => {
  const { accessToken } = localStorage;

  if (accessToken === null || accessToken === undefined) {
    store.commit('setShowCommonMenu', false);
    next('/');
    alert('유효하지 않는 경로입니다. 해당페이지로 이동 할 수 없습니다. 다시 로그인해주세요.');
  } else {
    return next();
  }
};


const router = new Router({
  routes:
    [
      {
        path: '/',
        name: 'Goods',
        component: Goods,
        alias :'/goods'
      },
      {
        path: '/login',
        name: 'Login',
        component: Login,
      },
      {
        path: '/cart',
        name: 'Cart',
        component: Cart,
        //beforeEnter: requireAuth(),
      }
    ],
});
export default router;

