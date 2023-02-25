<template>
    <div class="mo-article-view">
        <div class="mo-article-view__head">
            <div class="mo-article-view__detail" v-if="article.title">
                <div class="mo-article-view__title">{{ article.title }}</div>
                <div class="mo-article-view__info">
                    <span class="mo-article-view__info-item">
                        <a class="mo-article-view__avatar" :href="getActualPath('root')">
                            <img class="mo-article-view__avatar-img" :src="avatarURL" />
                        </a>
                    </span>
                    <span class="mo-article-view__info-item">{{ user }}</span>
                    <span class="mo-article-view__info-item">·</span>
                    <span class="mo-article-view__info-item">{{ article.createTime }}</span>
                </div>
            </div>
        </div>
        <div class="mo-article-view__body">
            <md-editor-v3
                class="mo-article-view__content"
                :editor-id="mdState.id"
                v-model="article.content"
                :previewOnly="true"
            />
            <md-catalog
                v-if="store.screenWidth > 960"
                class="mo-article-view__catalog"
                :editorId="mdState.id"
                :scroll-element="mdState.scrollElement"
                :offset-top="mdState.offsetTop"
            />
        </div>
        <el-backtop class="mo-article-view__backtop" />
    </div>
</template>

<script setup>
import { getCurrentInstance, reactive } from 'vue';
import { useRoute } from 'vue-router';
import articleService from '@/api/article-service.mjs.js';
import storage from '@/utils/storage.mjs.js';
import store from '@/store/store.mjs.js';
import MdEditorV3 from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';

const { MdCatalog } = MdEditorV3;

// 获取真实路径函数
const getActualPath = getCurrentInstance().proxy.$getActualPath;
// 跳转路由对象
const route = useRoute();

// 用户
const user = storage.get('user');
// 默认封面路径
const defaultCoverURL = storage.get('defaultCoverImageURL') || getActualPath('static/img/default_cover.png');
// 头像路径
const avatarURL = storage.get('avatarImageURL') || getActualPath('static/img/avatar.png');

// MdEditorV3 状态参数
const mdState = reactive({
    id: `md-editor-v3-${Date.now()}`,
    scrollElement: document.documentElement,
    offsetTop: 60
});

// 文章数据
const article = reactive({
    id: '',
    title: '',
    description: '',
    cover: '',
    content: '',
    categoryId: '',
    categoryName: '',
    tags: [],
    views: 0,
    topping: false,
    status: 1,
    createTime: ''
});

// CSS变量
const cssVariable = reactive({
    // 封面路径
    coverURL: `url('${defaultCoverURL}')`
});

/**
 * 获取文章信息
 *
 * @param {String} id 文章ID
 */
const getArticleInfo = (id) => {
    articleService
        .info(id)
        .then((res) => {
            for (const key in res.data) {
                article[key] = res.data[key];
            }
            // 设置文章封面
            cssVariable.coverURL = `url('${article.cover || defaultCoverURL}')`;
        })
        .catch((error) => {});
};

// 初始化操作
(() => {
    // 获取文章信息
    getArticleInfo(route.params.articleId);
})();
</script>

<style scoped>
.mo-article-view {
    display: flex;
    flex-flow: column;
    min-height: 100vh;
    --mo-article-view-cover-image: v-bind(cssVariable.coverURL);
    --mo-article-view-head-background-image: var(--mo-article-view-cover-image);
    --mo-article-view-detail-font-family: 'HYWenHei-85W', 'Merriweather Sans', Helvetica, Tahoma, Arial, 'PingFang SC',
        'Hiragino Sans GB', 'Microsoft Yahei', 'WenQuanYi Micro Hei', 'sans-serif';
    --mo-article-view-detail-color: #fff;
    --mo-article-view-detail-text-shadow: 2px 2px 10px #000;
    --mo-article-view-info-font-size: 14px;
    --mo-article-view-info-padding: 0 0 25px 0;
    --mo-article-view-info-item-padding: 0 10px 0 0;
    --mo-article-view-avatar-img-width: 35px;
    --mo-article-view-avatar-img-height: 35px;
    --mo-article-view-body-padding: 10px;
    --mo-article-view-body-background-color: #f5f6f7;
    --mo-article-view-backtop-width: 50px;
    --mo-article-view-backtop-height: 50px;
    --mo-article-view-backtop-icon-font-size: 20px;
}

@media screen and (max-width: 960px) {
    .mo-article-view {
        --mo-article-view-head-height: 280px;
        --mo-article-view-detail-width: 95%;
        --mo-article-view-detail-min-width: var(--mo-article-view-detail-width);
        --mo-article-view-detail-height: 100%;
        --mo-article-view-title-font-size: 26px;
        --mo-article-view-title-padding: 0 0 30px 0;
        --mo-article-view-content-width: 95%;
        --mo-article-view-content-min-width: var(--mo-article-view-content-width);
        --mo-article-view-content-padding: 5px;
        --mo-article-view-content-border-radius: 5px;
        --mo-article-view-backtop-right: 10px;
        --mo-article-view-backtop-bottom: 10px;
    }
}

@media screen and (min-width: 961px) {
    .mo-article-view {
        --mo-article-view-head-height: 50vh;
        --mo-article-view-detail-width: 60%;
        --mo-article-view-detail-min-width: 800px;
        --mo-article-view-detail-height: 100%;
        --mo-article-view-title-font-size: 32px;
        --mo-article-view-title-padding: 0 0 50px 0;
        --mo-article-view-content-width: 60%;
        --mo-article-view-content-min-width: 800px;
        --mo-article-view-content-padding: 10px;
        --mo-article-view-content-border-radius: 5px;
        --mo-article-view-catalog-top: calc(var(--mo-home-main-header-bar-height) + 10px);
        --mo-article-view-catalog-max-height: calc(100vh - var(--mo-article-view-catalog-top) - 10px);
        --mo-article-view-catalog-margin: 0 0 0 10px;
        --mo-article-view-catalog-padding: 10px;
        --mo-article-view-catalog-border-radius: 5px;
        --mo-article-view-catalog-background-color: #fff;
        --mo-article-view-backtop-right: 100px;
        --mo-article-view-backtop-bottom: 100px;
    }

    .mo-article-view__catalog.md-catalog {
        position: sticky;
        top: var(--mo-article-view-catalog-top);
        overflow: auto;
        box-sizing: border-box;
        max-height: var(--mo-article-view-catalog-max-height);
        margin: var(--mo-article-view-catalog-margin);
        padding: var(--mo-article-view-catalog-padding);
        border-radius: var(--mo-article-view-content-border-radius);
        background-color: var(--mo-article-view-catalog-background-color);
    }
}

.mo-article-view__head {
    display: flex;
    align-items: center;
    flex-direction: column;
    height: var(--mo-article-view-head-height);
    background-image: var(--mo-article-view-head-background-image);
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover;
}

.mo-article-view__title {
    font-size: var(--mo-article-view-title-font-size);
    padding: var(--mo-article-view-title-padding);
}

.mo-article-view__detail {
    font-family: var(--mo-article-view-detail-font-family);
    display: flex;
    align-items: flex-start;
    flex-direction: column;
    justify-content: flex-end;
    width: var(--mo-article-view-detail-width);
    height: var(--mo-article-view-detail-height);
    color: var(--mo-article-view-detail-color);
    text-shadow: var(--mo-article-view-detail-text-shadow);
}

.mo-article-view__info {
    font-size: var(--mo-article-view-info-font-size);
    display: flex;
    align-items: center;
    justify-content: flex-start;
    padding: var(--mo-article-view-info-padding);
}

.mo-article-view__info-item {
    padding: var(--mo-article-view-info-item-padding);
}

.mo-article-view__avatar {
    cursor: auto;
}

.mo-article-view__avatar-img {
    width: var(--mo-article-view-avatar-img-width);
    height: var(--mo-article-view-avatar-img-height);
    cursor: auto;
    border-radius: 50%;
}

.mo-article-view__body {
    display: flex;
    align-items: flex-start;
    flex: 1;
    justify-content: center;
    padding: var(--mo-article-view-body-padding);
    background-color: var(--mo-article-view-body-background-color);
}

.mo-article-view__content.md.md-previewOnly {
    width: var(--mo-article-view-content-width);
    min-width: var(--mo-article-view-content-min-width);
    padding: var(--mo-article-view-content-padding);
    border-radius: var(--mo-article-view-content-border-radius);
}

.mo-article-view__backtop.el-backtop {
    right: var(--mo-article-view-backtop-right) !important;
    bottom: var(--mo-article-view-backtop-bottom) !important;
    width: var(--mo-article-view-backtop-width);
    height: var(--mo-article-view-backtop-height);
}

.mo-article-view__backtop.el-backtop >>> .el-icon {
    font-size: var(--mo-article-view-backtop-icon-font-size);
}
</style>
