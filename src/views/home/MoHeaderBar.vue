<template>
    <div class="mo-header-bar">
        <span class="mo-header-bar__logo" @click="goHome">{{ logo }}</span>
        <span>
            <el-menu
                class="mo-header-bar__menu"
                :default-active="defaultActive"
                mode="horizontal"
                :ellipsis="false"
                :router="true"
            >
                <el-menu-item class="mo-header-bar__menu-item" v-for="item in menus" :index="item.index">
                    <el-icon><component :is="item.icon" /></el-icon>
                    <span>{{ item.name }}</span>
                </el-menu-item>
            </el-menu>
        </span>
    </div>
</template>

<script setup>
import { HomeFilled, Briefcase, InfoFilled } from '@element-plus/icons-vue';
import { computed, getCurrentInstance, markRaw, reactive } from 'vue';
import { useRoute } from 'vue-router';
import storage from '@/utils/storage.mjs.js';

// 获取真实路径函数
const getActualPath = getCurrentInstance().proxy.$getActualPath;

// 跳转路由对象
const route = useRoute();

// logo
const logo = storage.getObject('title')['home'];
// 菜单项
const menus = reactive([
    {
        index: '/main',
        icon: markRaw(HomeFilled),
        name: '首页'
    },
    {
        index: '/archives',
        icon: markRaw(Briefcase),
        name: '归档'
    },
    {
        index: '/about',
        icon: markRaw(InfoFilled),
        name: '关于'
    }
]);

// 默认激活菜单项
const defaultActive = computed(() => {
    for (let menu of menus) {
        if (menu.index === route.path) {
            return route.path;
        }
    }
    return '/main';
});

/**
 * 回到主页
 */
const goHome = () => (window.location.href = getActualPath('root'));
</script>

<style scoped>
.mo-header-bar {
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 100%;
    --mo-header-bar-logo-font-family: 'HYWenHei-85W', 'Merriweather Sans', Helvetica, Tahoma, Arial, 'PingFang SC',
        'Hiragino Sans GB', 'Microsoft Yahei', 'WenQuanYi Micro Hei', 'sans-serif';
    --mo-header-bar-logo-font-size: 28px;
    --mo-header-bar-logo-font-weight: 800;
    --mo-header-bar-logo-margin: 0 0 0 10px;
    --mo-header-bar-logo-padding: 5px;
    --mo-header-bar-logo-color: #464646;
    --mo-header-bar-logo-color-hover: #ffd04b;
    --mo-header-bar-menu-margin: 0 30px 0 0;
    --mo-header-bar-menu-text-color: #666;
    --mo-header-bar-menu-text-color-hover: #409eff;
    --mo-header-bar-menu-text-color-active: #ffd04b;
    --mo-header-bar-menu-background-color: #fff0;
    --mo-header-bar-menu-background-color-hover: #ddd8;
}

.mo-header-bar__logo {
    font-family: var(--mo-header-bar-logo-font-family);
    font-size: var(--mo-header-bar-logo-font-size);
    font-weight: var(--mo-header-bar-logo-font-weight);
    margin: var(--mo-header-bar-logo-margin);
    padding: var(--mo-header-bar-logo-padding);
    color: var(--mo-header-bar-logo-color);
}

.mo-header-bar__logo:hover {
    cursor: pointer;
    user-select: none;
    color: var(--mo-header-bar-logo-color-hover);
}

.mo-header-bar__menu.el-menu {
    margin: var(--mo-header-bar-menu-margin);
    user-select: none;
    border-bottom: none;
    --el-menu-text-color: var(--mo-header-bar-menu-text-color);
    --el-menu-hover-text-color: var(--mo-header-bar-menu-text-color-hover);
    --el-menu-active-color: var(--mo-header-bar-menu-text-color-active);
    --el-menu-bg-color: var(--mo-header-bar-menu-background-color);
    --el-menu-hover-bg-color: var(--mo-header-bar-menu-background-color-hover);
}

.mo-header-bar__menu-item.el-menu-item.is-active:focus {
    background-color: var(--mo-header-bar-menu-background-color);
}

.mo-header-bar__menu-item.el-menu-item.is-active:hover {
    background-color: var(--mo-header-bar-menu-background-color-hover);
}
</style>
