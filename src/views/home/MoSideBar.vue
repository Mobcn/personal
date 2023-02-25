<template>
    <div class="mo-side-bar">
        <span class="mo-side-bar__logo">{{ logo }}</span>
        <span>
            <el-menu class="mo-side-bar__menu" :ellipsis="false" :router="true">
                <el-menu-item v-for="item in menus" :index="item.index" @click="emits('close-side-bar')">
                    <el-icon><component :is="item.icon" /></el-icon>
                    <span>{{ item.name }}</span>
                </el-menu-item>
            </el-menu>
        </span>
    </div>
</template>

<script setup>
import { HomeFilled, Briefcase, InfoFilled } from '@element-plus/icons-vue';
import { markRaw, reactive } from 'vue';
import storage from '@/utils/storage.mjs.js';

// 回调对象
const emits = defineEmits(['close-side-bar']);

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
</script>

<style scoped>
.mo-side-bar {
    display: flex;
    align-items: center;
    flex-direction: column;
    justify-content: flex-start;
    width: 100%;
    height: 100%;
    --mo-side-bar-logo-font-family: 'HYWenHei-85W', 'Merriweather Sans', Helvetica, Tahoma, Arial, 'PingFang SC',
        'Hiragino Sans GB', 'Microsoft Yahei', 'WenQuanYi Micro Hei', 'sans-serif';
    --mo-side-bar-logo-font-size: 28px;
    --mo-side-bar-logo-font-weight: 800;
    --mo-side-bar-logo-padding: 200px 0 50px 0;
    --mo-side-bar-logo-color: #464646;
    --mo-side-bar-menu-text-color: #666;
    --mo-side-bar-menu-text-color-active: #ffd04b;
    --mo-side-bar-menu-background-color: #fff0;
}

.mo-side-bar__logo {
    font-family: var(--mo-side-bar-logo-font-family);
    font-size: var(--mo-side-bar-logo-font-size);
    font-weight: var(--mo-side-bar-logo-font-weight);
    padding: var(--mo-side-bar-logo-padding);
    color: var(--mo-side-bar-logo-color);
}

.mo-side-bar__menu.el-menu {
    user-select: none;
    border-right: none;
    --el-menu-text-color: var(--mo-side-bar-menu-text-color);
    --el-menu-active-color: var(--mo-side-bar-menu-text-color-active);
    --el-menu-bg-color: var(--mo-side-bar-menu-background-color);
}
</style>
