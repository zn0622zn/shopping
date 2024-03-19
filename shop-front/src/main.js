// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import App from './App';
import router from './router';
import ViewUI from 'view-design';

import 'view-design/dist/styles/iview.css';

Vue.use(ViewUI);


Vue.config.productionTip = false;

router.beforeEach((to, from, next) => {
  ViewUI.LoadingBar.start();
  next();
});

router.afterEach(route => {
  ViewUI.LoadingBar.finish();
});

new Vue({
    el: '#app',
    router: router,
    render: h => h(App)
});