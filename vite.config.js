import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import path from 'path';
import { Plugin as importToCDN } from 'vite-plugin-cdn-import';

export default defineConfig({
    plugins: [
        vue(),
        importToCDN({
            modules: [
                // Vue3
                {
                    name: 'vue',
                    var: 'Vue',
                    path: 'https://cdn.staticfile.org/vue/3.2.37/vue.global.prod.min.js',
                    css: [
                        // Normalize 样式重置
                        'https://cdn.staticfile.org/normalize/8.0.1/normalize.min.css',
                        // Animate 动画库
                        'https://cdn.staticfile.org/animate.css/4.1.1/animate.min.css'
                    ]
                },

                // Vue路由
                {
                    name: 'vue-router',
                    var: 'VueRouter',
                    path: 'https://cdn.staticfile.org/vue-router/4.0.16/vue-router.global.prod.min.js'
                },

                // ElementPlus组件库
                {
                    name: 'element-plus',
                    var: 'ElementPlus',
                    path: 'https://cdn.staticfile.org/element-plus/2.2.18/index.full.min.js',
                    css: 'https://cdn.staticfile.org/element-plus/2.2.18/index.css'
                },
                {
                    name: '@element-plus/icons-vue',
                    var: 'ElementPlusIconsVue',
                    path: 'https://cdn.staticfile.org/element-plus-icons-vue/2.0.10/index.iife.min.js'
                },
                {
                    name: 'element-plus/dist/locale/zh-cn.min.mjs',
                    var: 'ElementPlusLocaleZhCn',
                    path: 'https://cdn.staticfile.org/element-plus/2.2.18/locale/zh-cn.min.js'
                },

                // Axios 网络请求库
                {
                    name: 'axios',
                    var: 'axios',
                    path: 'https://cdn.staticfile.org/axios/0.27.2/axios.min.js'
                }
            ]
        })
    ],
    resolve: {
        alias: {
            '@': path.resolve(__dirname, './src')
        }
    },
    base: './'
});
