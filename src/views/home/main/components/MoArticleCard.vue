<template>
    <div class="mo-article-card">
        <div class="mo-article-card__container">
            <el-row class="mo-article-card__row">
                <el-col :span="10">
                    <div class="mo-article-card__cover" @click="emits('view', model.id)">
                        <img class="mo-article-card__cover-img" :src="model.cover || defaultCoverURL" />
                    </div>
                </el-col>
                <el-col :span="14">
                    <div class="mo-article-card__detail">
                        <div>
                            <div class="mo-article-card__title" @click="emits('view', model.id)">
                                {{ model.title }}
                            </div>
                            <div class="mo-article-card__description">{{ model.description }}</div>
                        </div>
                        <div class="mo-article-card__footer">
                            <el-icon class="mo-article-card__info-icon">
                                <mo-icon icon-name="clock-fill" />
                            </el-icon>
                            <span>{{ model.createTime }}</span>
                            <span class="mo-article-card__info-split">·</span>
                            <el-icon class="mo-article-card__info-icon">
                                <mo-icon icon-name="folder-fill" />
                            </el-icon>
                            <span>{{ model.categoryName || '未分类' }}</span>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script setup>
import MoIcon from '@/components/icons/MoIcon.vue';
import { getCurrentInstance } from 'vue';
import storage from '@/utils/storage.mjs.js';

// 参数
defineProps({
    model: {
        type: Object,
        default: () => ({
            id: '',
            title: '',
            description: '',
            cover: '',
            contentId: '',
            categoryId: '',
            categoryName: '',
            tags: [],
            views: 0,
            topping: false,
            status: 1,
            createTime: ''
        })
    }
});

// 回调
const emits = defineEmits(['view']);

// 获取真实路径函数
const getActualPath = getCurrentInstance().proxy.$getActualPath;

// 默认封面路径
const defaultCoverURL = storage.get('defaultCoverImageURL') || getActualPath('static/img/default_cover.png');
</script>

<style scoped>
.mo-article-card {
    --mo-article-card-background-color: #fff;
    --mo-article-card-box-shadow: 0 1px 20px -6px rgb(0 0 0 / 50%);
    --mo-article-card-box-shadow-hover: 0 5px 10px 5px rgb(110 110 110 / 40%);
    --mo-article-card-detail-font-family: 'HYWenHei-85W', 'Merriweather Sans', Helvetica, Tahoma, Arial, 'PingFang SC',
        'Hiragino Sans GB', 'Microsoft Yahei', 'WenQuanYi Micro Hei', 'sans-serif';
    --mo-article-card-title-font-weight: 550;
    --mo-article-card-title-color: #504e4e;
    --mo-article-card-title-color-hover: #fe9600;
    --mo-article-card-description-color: #000000a8;
    --mo-article-card-footer-color: #888;
    --mo-article-card-info-split-margin: 0 5px;
    --mo-article-card-info-icon-font-size: 16px;
    --mo-article-card-info-icon-margin: 0 5px 0 0;
}

@media screen and (max-width: 960px) {
    .mo-article-card {
        --mo-article-card-height: 270px;
        --mo-article-card-border-radius: 10px;
        --mo-article-card-cover-border-radius: 10px 10px 0 0;
        --mo-article-card-title-width: calc(100% - 20px);
        --mo-article-card-title-padding: 10px 10px 0 10px;
        --mo-article-card-title-font-size: 18px;
        --mo-article-card-description-font-size: 15px;
        --mo-article-card-footer-font-size: 12px;
        --mo-article-card-footer-padding: 0 0 10px 10px;
    }

    .mo-article-card__row.el-row {
        flex-direction: column;
    }

    .mo-article-card__row.el-row >>> .el-col-10,
    .mo-article-card__row.el-row >>> .el-col-14 {
        flex: 0 0 100%;
        max-width: 100%;
        max-height: 50%;
    }
}

@media screen and (min-width: 961px) {
    .mo-article-card {
        --mo-article-card-height: 210px;
        --mo-article-card-border-radius: 20px;
        --mo-article-card-cover-border-radius: 20px 0 0 20px;
        --mo-article-card-title-width: calc(100% - 40px);
        --mo-article-card-title-padding: 20px 20px 0 20px;
        --mo-article-card-title-font-size: 24px;
        --mo-article-card-description-font-size: 18px;
        --mo-article-card-footer-font-size: 16px;
        --mo-article-card-footer-padding: 0 0 20px 20px;
    }
}

.mo-article-card__container {
    height: var(--mo-article-card-height);
    border-radius: var(--mo-article-card-border-radius);
    background-color: var(--mo-article-card-background-color);
    box-shadow: var(--mo-article-card-box-shadow);
}

.mo-article-card__container:hover {
    box-shadow: var(--mo-article-card-box-shadow-hover);
}

.mo-article-card__cover {
    overflow: hidden;
    height: 100%;
    border-radius: var(--mo-article-card-cover-border-radius);
}

.mo-article-card__cover-img {
    width: 100%;
    height: 100%;
    user-select: none;
    pointer-events: none;
    object-fit: cover;
}

.mo-article-card:hover .mo-article-card__cover-img {
    transform: scale(1.1);
}

.mo-article-card__detail {
    font-family: var(--mo-article-card-detail-font-family);
    display: flex;
    align-items: flex-start;
    flex-direction: column;
    justify-content: space-between;
    height: 100%;
}

.mo-article-card__title {
    font-size: var(--mo-article-card-title-font-size);
    font-weight: var(--mo-article-card-title-font-weight);
    display: -webkit-box;
    overflow: hidden;
    -webkit-box-orient: vertical;
    width: var(--mo-article-card-title-width);
    padding: var(--mo-article-card-title-padding);
    text-overflow: ellipsis;
    word-wrap: break-word;
    color: var(--mo-article-card-title-color);
    -webkit-line-clamp: 2;
}

.mo-article-card__title:hover {
    cursor: pointer;
    color: var(--mo-article-card-title-color-hover);
}

.mo-article-card__description {
    font-size: var(--mo-article-card-description-font-size);
    display: -webkit-box;
    overflow: hidden;
    -webkit-box-orient: vertical;
    width: var(--mo-article-card-title-width);
    padding: var(--mo-article-card-title-padding);
    text-overflow: ellipsis;
    word-wrap: break-word;
    color: var(--mo-article-card-description-color);
    -webkit-line-clamp: 2;
}

.mo-article-card__footer {
    font-size: var(--mo-article-card-footer-font-size);
    display: flex;
    align-items: center;
    justify-content: flex-start;
    padding: var(--mo-article-card-footer-padding);
    color: var(--mo-article-card-footer-color);
}

.mo-article-card__info-split {
    margin: var(--mo-article-card-info-split-margin);
}

.mo-article-card__info-icon {
    font-size: var(--mo-article-card-info-icon-font-size);
    margin: var(--mo-article-card-info-icon-margin);
}

.mo-article-card__row.el-row {
    width: 100%;
    height: 100%;
}
</style>
