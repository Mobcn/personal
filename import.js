/**
 * 导入配置
 */
(() => ({
    // 通过标签导入
    importToTag: {
        js: [],
        css: [
            // Normalize 样式重置
            'https://cdn.staticfile.org/normalize/8.0.1/normalize.min.css',
            // Animate 动画库
            'https://cdn.staticfile.org/animate.css/4.1.1/animate.min.css',
            // ElementPlus组件库样式
            'https://cdn.staticfile.org/element-plus/2.2.18/index.css'
        ]
    },

    // 通过ESM导入（css必须带后缀）
    importToESM: {
        // Vue3 SFC 加载器
        'vue3-sfc-loader': 'https://registry.npmmirror.com/vue3-sfc-loader/0.8.4/files/dist/vue3-sfc-loader.esm.js',

        // Vue3
        vue: 'https://cdn.staticfile.org/vue/3.2.37/vue.esm-browser.prod.min.js',

        // Vue路由
        'vue-router': 'https://cdn.staticfile.org/vue-router/4.0.16/vue-router.esm-browser.min.js',
        '@vue/devtools-api': 'https://registry.npmmirror.com/@vue/devtools-api/6.4.5/files/lib/esm/index.js',
        '@vue/': 'https://registry.npmmirror.com/@vue/devtools-api/6.4.5/files/lib/esm/',

        // ElementPlus组件库
        'element-plus': 'https://cdn.staticfile.org/element-plus/2.2.18/index.full.min.mjs',
        'element-plus/': 'https://registry.npmmirror.com/element-plus/2.2.18/files/',
        // ElementPlus图标
        '@element-plus/icons-vue': 'https://cdn.staticfile.org/element-plus-icons-vue/2.0.10/index.min.js',

        // Axios 网络请求库
        axios: 'https://cdn.staticfile.org/axios/1.1.3/esm/axios.min.js',

        // Lodash JS库
        lodash: 'https://registry.npmmirror.com/lodash-es/4.17.21/files/lodash.js',

        // MdEditor富文本插件
        'md-editor-v3': 'https://registry.npmmirror.com/md-editor-v3/2.3.0/files/lib/md-editor-v3.es.js',
        'md-editor-v3/lib/style.css': 'https://registry.npmmirror.com/md-editor-v3/2.3.0/files/lib/style.css'
    }
}))();
