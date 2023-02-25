<template>
    <div :class="`mo-home-main ${isOpenSideBar ? 'mo-home-main--behind' : ''}`">
        <span
            v-if="existSideBar"
            :class="`mo-home-main__side-bar ${isOpenSideBar ? 'mo-home-main__side-bar--open' : ''}`"
        >
            <mo-side-bar @close-side-bar="isOpenSideBar = false" />
        </span>
        <span :class="`mo-home-main__container ${isOpenSideBar ? 'mo-home-main__container--move' : ''}`">
            <div v-if="existSideBar" class="mo-home-main__side-switch" @click="isOpenSideBar = !isOpenSideBar">
                <el-icon class="mo-home-main__switch-icon">
                    <mo-icon :icon-name="isOpenSideBar ? 'x' : 'list'" />
                </el-icon>
            </div>
            <div v-if="isOpenSideBar" class="mo-home-main__mask" @click="isOpenSideBar = false" />
            <div v-if="existHeaderBar" class="mo-home-main__header-bar" :class="headerBarClass">
                <mo-header-bar />
            </div>
            <router-view />
        </span>
    </div>
</template>

<script setup>
import MoIcon from '@/components/icons/MoIcon.vue';
import MoHeaderBar from './MoHeaderBar.vue';
import MoSideBar from './MoSideBar.vue';
import { computed, getCurrentInstance, ref, watch } from 'vue';
import router from '@/router/router.mjs.js';
import store from '@/store/store.mjs.js';

// 是否启用工具栏
const enableBar = ref(true);
// 侧边栏是否打开
const isOpenSideBar = ref(false);
// 导航栏class
const headerBarClass = ref('');

// 是否存在侧边栏
const existSideBar = computed(() => enableBar.value && store.screenWidth <= 960);
// 是否存在导航栏
const existHeaderBar = computed(() => enableBar.value && store.screenWidth > 960);

/**
 * 滚动条监听事件
 *
 * @param {Event} event 事件对象
 */
const handleScroll = (event) => {
    headerBarClass.value = `mo-home-main__header-bar--bg-${
        event.target.documentElement.scrollTop > 0 ? 'show' : 'hidden'
    }`;
};

// 初始化操作
(() => {
    // 淡入效果
    document.body.style.opacity = '0';
    setTimeout(() => {
        document.body.style.transition = 'opacity 1.5s';
        document.body.style.opacity = '1';
    }, 0);

    // 设置路由
    getCurrentInstance().appContext.app.use(router);

    // 路由监听
    watch(router.currentRoute, (route) => {
        document.getElementsByTagName('title')[0].innerHTML = route.meta.title;
        enableBar.value = route.meta.enableBar;
        if (route.name === 'main') {
            headerBarClass.value = 'mo-home-main__header-bar--bg-hidden';
            window.addEventListener('scroll', handleScroll);
        } else if (route.name === '404') {
            window.removeEventListener('scroll', handleScroll);
        } else {
            headerBarClass.value = 'mo-home-main__header-bar--bg-show';
            window.removeEventListener('scroll', handleScroll);
        }
    });
})();
</script>

<style scoped>
@media screen and (max-width: 960px) {
    .mo-home-main {
        --mo-home-main-side-bar-width: 250px;
        --mo-home-main-side-bar-background-color: #fff;
        --mo-home-main-side-bar-transition: left 0.3s ease;
        --mo-home-main-side-switch-width: 50px;
        --mo-home-main-side-switch-height: 50px;
        --mo-home-main-side-switch-background-color: #cccc;
        --mo-home-main-side-switch-box-shadow: 2px 2px 10px #000;
        --mo-home-main-side-switch-icon-font-size: 40px;
        --mo-home-main-side-switch-icon-color: #fff;
        --mo-home-main-mask-background-color: #99957;
    }

    .mo-home-main--behind {
        position: fixed;
        overflow: hidden;
        width: 100%;
        height: 100vh;
    }

    .mo-home-main__side-bar {
        position: absolute;
        z-index: 999;
        left: calc(-1 * var(--mo-home-main-side-bar-width));
        display: block;
        width: var(--mo-home-main-side-bar-width);
        height: 100%;
        transition: var(--mo-home-main-side-bar-transition);
        background-color: var(--mo-home-main-side-bar-background-color);
    }

    .mo-home-main__side-bar--open {
        left: 0;
    }

    .mo-home-main__container {
        display: block;
        width: 100%;
        padding-left: 0;
        transition-duration: 0.5s;
    }

    .mo-home-main__container--move {
        padding-left: var(--mo-home-main-side-bar-width);
    }

    .mo-home-main__side-switch {
        position: fixed;
        z-index: 2;
        top: 10px;
        left: 10px;
        width: var(--mo-home-main-side-switch-width);
        height: var(--mo-home-main-side-switch-height);
        border-radius: 15%;
        background-color: var(--mo-home-main-side-switch-background-color);
        box-shadow: var(--mo-home-main-side-switch-box-shadow);
    }

    .mo-home-main__switch-icon.el-icon {
        font-size: var(--mo-home-main-side-switch-icon-font-size);
        line-height: var(--mo-home-main-side-switch-height);
        width: 100%;
        height: 100%;
        text-align: center;
        color: var(--mo-home-main-side-switch-icon-color);
    }

    .mo-home-main__mask {
        position: absolute;
        z-index: 1;
        display: block;
        width: 100%;
        height: 100vh;
        background-color: var(--mo-home-main-mask-background-color);
    }
}

@media screen and (min-width: 961px) {
    .mo-home-main {
        --mo-home-main-header-bar-height: 60px;
        --mo-home-main-header-bar-background-color: rgba(255, 255, 255, 0.95);
        --mo-home-main-header-bar-background-color-hover: rgba(255, 255, 255, 1);
        --mo-home-main-header-bar-background-color-hidden: rgba(255, 255, 255, 0);
        --mo-home-main-header-bar-box-shadow: 0 1px 40px -8px rgb(0 0 0 / 50%);
        --mo-home-main-header-bar-transition: all 0.8s;
        --mo-home-main-header-bar-transition-bg-show: all 0.5s;
    }

    .mo-home-main__header-bar {
        position: fixed;
        z-index: 998;
        width: 100%;
        height: var(--mo-home-main-header-bar-height);
        transition: var(--mo-home-main-header-bar-transition);
        background-color: var(--mo-home-main-header-bar-background-color);
    }

    .mo-home-main__header-bar:hover {
        transition: var(--mo-home-main-header-bar-transition-bg-show);
        background-color: var(--mo-home-main-header-bar-background-color-hover);
        box-shadow: var(--mo-home-main-header-bar-box-shadow);
    }

    .mo-home-main__header-bar--bg-show {
        transition: var(--mo-home-main-header-bar-transition-bg-show);
        box-shadow: var(--mo-home-main-header-bar-box-shadow);
    }

    .mo-home-main__header-bar--bg-hidden {
        background-color: var(--mo-home-main-header-bar-background-color-hidden);
    }
}
</style>
