import { createRouter, createWebHashHistory } from 'vue-router';
import storage from '@/utils/storage.mjs.js';

// 页面标题
const title = storage.getObject('title');

/**
 * 路由规则
 * @param path 匹配路径
 * @param name 路径名
 * @param title 页面标题名
 * @param enableBar 是否启用工具栏
 * @param component 组件
 */
const routes = [
    {
        path: '/:main(main)?',
        name: 'main',
        meta: {
            title: `首页 | ${title['home']}`,
            enableBar: true
        },
        component: () => import('@/views/home/main/MoMain.vue')
    },
    {
        path: '/archives',
        name: 'archives',
        meta: {
            title: `归档 | ${title['home']}`,
            enableBar: true
        },
        component: () => import('@/views/home/archives/MoArchives.vue')
    },
    {
        path: '/about',
        name: 'about',
        meta: {
            title: `关于 | ${title['home']}`,
            enableBar: true
        },
        component: () => import('@/views/home/about/MoAbout.vue')
    },
    {
        path: '/article/:articleId',
        name: 'article',
        meta: {
            title: `文章 | ${title['home']}`,
            enableBar: true
        },
        component: () => import('@/views/home/article/MoArticleView.vue')
    },
    {
        path: '/:other',
        name: '404',
        meta: {
            title: title['_404'],
            enableBar: false
        },
        component: () => import('@/views/common/404.vue')
    }
];

export default createRouter({ history: createWebHashHistory(), routes });
