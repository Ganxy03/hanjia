import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
import { library } from '@fortawesome/fontawesome-svg-core';
import { faMessage } from '@fortawesome/free-solid-svg-icons'
import { faMailReply } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

library.add(faMessage,faMailReply);
Vue.use(ElementUI)
Vue.config.productionTip = false
Vue.component('font-awesome-icon', FontAwesomeIcon);

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
