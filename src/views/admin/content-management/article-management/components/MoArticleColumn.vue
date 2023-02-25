<template>
    <div class="mo-article-column">
        <div class="mo-article-column__container">
            <el-row class="mo-article-column__row">
                <el-col class="mo-article-column__left" :span="20">
                    <div class="mo-article-column__title" @click="emits('view', model)" :title="model.title">
                        {{ model.title }}
                    </div>
                    <div class="mo-article-column__description" :title="model.description">
                        {{ model.description }}
                    </div>
                    <div class="mo-article-column__info">
                        <el-icon class="mo-article-column__info-icon">
                            <mo-icon icon-name="folder-fill" />
                        </el-icon>
                        <span>{{ model.categoryName || '未分类' }}</span>
                        <span class="mo-article-column__info-split">·</span>
                        <el-icon v-if="model.tags.length > 0" class="mo-article-column__info-icon">
                            <mo-icon icon-name="tags-fill" />
                        </el-icon>
                        <span v-if="model.tags.length > 0" v-for="tag in model.tags" :key="tag.id">
                            <span>{{ tag.name }}</span>
                            <span class="mo-article-column__info-split">·</span>
                        </span>
                        <el-icon class="mo-article-column__info-icon">
                            <mo-icon icon-name="eye-fill" />
                        </el-icon>
                        <span>{{ model.views }}</span>
                    </div>
                </el-col>
                <el-col class="mo-article-column__right" :span="4">
                    <div class="mo-article-column__time">
                        <el-icon class="mo-article-column__info-icon">
                            <mo-icon icon-name="clock-fill" />
                        </el-icon>
                        <span>{{ model.createTime }}</span>
                    </div>
                    <div class="mo-article-column__action">
                        <span class="mo-article-column__button" @click="emits('view', model)">浏览</span>
                        <span class="mo-article-column__button" @click="emits('edit', model)">编辑</span>
                        <span class="mo-article-column__button" @click="emits('remove', model.id)">删除</span>
                    </div>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script setup>
import MoIcon from '@/components/icons/MoIcon.vue';

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

// 回调对象
const emits = defineEmits(['view', 'edit', 'remove']);
</script>

<style scoped>
.mo-article-column {
    --mo-article-column-height: 120px;
    --mo-article-column-row-border-radius: 10px;
    --mo-article-column-row-background-color-hover: #f5f7fa;
    --mo-article-column-title-font-size: 16px;
    --mo-article-column-title-font-weight: 500;
    --mo-article-column-title-color: #555666;
    --mo-article-column-title-color-hover: red;
    --mo-article-column-description-font-size: 14px;
    --mo-article-column-description-color: #999aaa;
    --mo-article-column-info-font-size: 14px;
    --mo-article-column-info-color: #999aaa;
    --mo-article-column-info-icon-font-size: 14px;
    --mo-article-column-info-icon-color: #989898;
    --mo-article-column-time-font-size: 14px;
    --mo-article-column-time-color: #999;
    --mo-article-column-action-font-size: 14px;
    --mo-article-column-action-color: #222226;
    --mo-article-column-button-hover: #666;
}

.mo-article-column__container {
    height: var(--mo-article-column-height);
    border-bottom: 1px dashed #dcdfe6;
}

.mo-article-column__row.el-row {
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 100%;
    padding: 10px 5px 10px 5px;
    border-radius: var(--mo-article-column-row-border-radius);
}

.mo-article-column__row.el-row:hover {
    background-color: var(--mo-article-column-row-background-color-hover);
}

.mo-article-column__left.el-col {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    height: 100%;
}

.mo-article-column__title {
    font-size: var(--mo-article-column-title-font-size);
    font-weight: var(--mo-article-column-title-font-weight);
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    color: var(--mo-article-column-title-color);
}

.mo-article-column__title:hover {
    cursor: pointer;
    color: var(--mo-article-column-title-color-hover);
}

.mo-article-column__description {
    font-size: var(--mo-article-column-description-font-size);
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    color: var(--mo-article-column-description-color);
}

.mo-article-column__info {
    font-size: var(--mo-article-column-info-font-size);
    display: flex;
    align-items: center;
    justify-content: flex-start;
    color: var(--mo-article-column-info-color);
}

.mo-article-column__info-split {
    margin: 0 5px;
}

.mo-article-column__right.el-col {
    display: flex;
    align-items: flex-end;
    flex-direction: column;
    justify-content: space-between;
    height: 100%;
}

.mo-article-column__time {
    font-size: var(--mo-article-column-time-font-size);
    display: flex;
    align-items: center;
    justify-content: flex-end;
    color: var(--mo-article-column-time-color);
}

.mo-article-column__action {
    font-size: var(--mo-article-column-action-font-size);
    display: flex;
    align-items: center;
    justify-content: space-between;
    user-select: none;
    color: var(--mo-article-column-action-font-size);
}

.mo-article-column__button {
    padding-left: 10px;
}

.mo-article-column__button:hover {
    cursor: pointer;
    color: var(--mo-article-column-button-hover);
}

.mo-article-column__info-icon {
    font-size: var(--mo-article-column-info-icon-font-size);
    margin-right: 5px;
    color: var(--mo-article-column-info-icon-color);
}
</style>
