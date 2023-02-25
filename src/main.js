import { createApp, defineAsyncComponent } from 'vue';
import ElementPlus from 'element-plus';
import ElementPlusLocaleZhCn from 'element-plus/dist/locale/zh-cn.min.mjs';
import { buildGetActualPathFunction, buildGetAsyncComponentFunction } from '../build-function.js';

// 获取实际路径函数
const getActualPath = buildGetActualPathFunction(import.meta.url);
// 获取动态组件函数
const getAsyncComponent = buildGetAsyncComponentFunction();

// 创建一个Vue实例
const vm = createApp(defineAsyncComponent(() => getAsyncComponent(getActualPath('@/App.vue'))));

vm.use(ElementPlus, {
    locale: ElementPlusLocaleZhCn
});

// 获取实际路径函数全局挂载
vm.config.globalProperties.$getActualPath = getActualPath;
// 获取动态组件函数全局挂载
vm.config.globalProperties.$getAsyncComponent = getAsyncComponent;

vm.mount('#app');
