<template>
    <div class="mo-about">
        <div class="mo-about__head">
            <div class="mo-about__title">
                <span>关于</span>
            </div>
            <div class="mo-about__wave" />
        </div>
        <div class="mo-about__body">
            <md-editor-v3 class="mo-about__content" v-model="aboutContent" :previewOnly="true" />
        </div>
    </div>
</template>

<script setup>
import { getCurrentInstance, reactive, ref } from 'vue';
import storage from '@/utils/storage.mjs.js';
import MdEditorV3 from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';

// 获取真实路径函数
const getActualPath = getCurrentInstance().proxy.$getActualPath;

// 关于内容
const aboutContent = ref('');

// CSS变量
const cssVariable = reactive({
    // 封面路径
    coverURL: `url('${storage.get('aboutCoverImageURL') || getActualPath('static/img/about_cover.jpg')}')`
});

// 初始化操作
(() => {
    // 获取关于内容
    fetch(getActualPath('static/doc/about.md'))
        .then((res) => res.text())
        .then((text) => (aboutContent.value = text))
        .catch((error) => console.log(error));
})();
</script>

<style scoped>
.mo-about {
    display: flex;
    flex-flow: column;
    min-height: 100vh;
    --mo-about-cover-image: v-bind(cssVariable.coverURL);
    --mo-about-head-background-image: var(--mo-about-cover-image);
    --mo-about-title-font-size: 40px;
    --mo-about-title-font-weight: 500;
    --mo-about-title-color: #fff;
    --mo-about-title-text-shadow: 2px 2px 10px #000;
    --mo-about-wave-width: 150%;
    --mo-about-wave-height: 38px;
    --mo-about-wave-margin: 0 0 -18px 0;
    --mo-about-wave-border-radius: 50% 50% 0 0;
    --mo-about-wave-background: #fff;
    --mo-about-body-padding: 10px;
}

@media screen and (max-width: 960px) {
    .mo-about {
        --mo-about-head-height: 35vh;
        --mo-about-content-width: 95%;
        --mo-about-content-min-width: var(--mo-about-content-width);
    }
}

@media screen and (min-width: 961px) {
    .mo-about {
        --mo-about-head-height: 55vh;
        --mo-about-content-width: 60%;
        --mo-about-content-min-width: 800px;
    }
}

.mo-about__head {
    display: flex;
    overflow: hidden;
    align-items: center;
    flex-direction: column;
    justify-content: center;
    height: var(--mo-about-head-height);
    background-image: var(--mo-about-head-background-image);
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover;
}

.mo-about__title {
    font-size: var(--mo-about-title-font-size);
    font-weight: var(--mo-about-title-font-weight);
    display: flex;
    align-items: center;
    flex: 1;
    justify-content: center;
    color: var(--mo-about-title-color);
    text-shadow: var(--mo-about-title-text-shadow);
}

.mo-about__wave {
    width: var(--mo-about-wave-width);
    height: var(--mo-about-wave-height);
    margin: var(--mo-about-wave-margin);
    animation: var(--mo-about-wave-animation);
    border-radius: var(--mo-about-wave-border-radius);
    background: var(--mo-about-wave-background);
}

.mo-about__body {
    display: flex;
    align-items: center;
    flex: 1;
    flex-direction: column;
    justify-content: flex-start;
    padding: var(--mo-about-body-padding);
}

.mo-about__content.md.md-previewOnly {
    width: var(--mo-about-content-width);
    min-width: var(--mo-about-content-min-width);
}
</style>
