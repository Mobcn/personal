<template>
    <div class="mo-article-view">
        <span class="mo-article-view__title">{{ article.title }}</span>
        <p>
            <span>分类：</span>
            <el-tag type="danger" size="small">{{ article.categoryName || '未分类' }}</el-tag>
        </p>
        <p v-if="article.tags.length > 0">
            <span>标签：</span>
            <span v-for="(tag, index) in article.tags" :key="tag.id">
                <el-tag size="small">{{ tag.name }}</el-tag>
                <span v-if="index < article.tags.length - 1">、</span>
            </span>
        </p>
        <p>摘要：{{ article.description }}</p>
        <md-editor-v3 v-model="article.content" :previewOnly="true" />
        <el-backtop target=".mo-article-view" />
    </div>
</template>

<script setup>
import articleService from '@/api/article-service.mjs.js';
import MdEditorV3 from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import { reactive } from 'vue';

// 参数
const props = defineProps({
    params: {
        type: Object,
        default: () => ({})
    }
});

// 文章对象
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
        })
        .catch((error) => {});
};

// 初始化操作
(() => {
    // 获取文章信息
    getArticleInfo(props.params.articleId);
})();
</script>

<style scoped>
.mo-article-view {
    position: absolute;
    overflow: auto;
    width: 100%;
    height: 100%;
    --mo-article-view-title-font-size: 32px;
    --mo-article-view-title-font-weight: bold;
}

.mo-article-view__title {
    font-size: var(--mo-article-view-title-font-size);
    font-weight: var(--mo-article-view-title-font-weight);
}
</style>
