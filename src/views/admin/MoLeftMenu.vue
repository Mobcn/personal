<template>
    <div class="mo-left-menu">
        <el-menu class="mo-left-menu__menu" :collapse="isCollapse" :collapse-transition="false" default-active="1">
            <el-menu-item index="1" @click="openTab('首页', 'main/MoMain', {})">
                <el-icon><home-filled /></el-icon>
                <span>首页</span>
            </el-menu-item>
            <el-menu-item index="2" @click="openTab('发布文章', 'article-publish/MoPublishArticle', {}, Date.now())">
                <el-icon><opportunity /></el-icon>
                <span>发布文章</span>
            </el-menu-item>
            <el-sub-menu index="3">
                <template #title>
                    <el-icon><trend-charts /></el-icon>
                    <span>内容管理</span>
                </template>
                <el-menu-item
                    index="3-1"
                    @click="openTab('文章管理', 'content-management/article-management/MoArticleManagement', {})"
                >
                    <el-icon><document /></el-icon>
                    <span>文章管理</span>
                </el-menu-item>
                <el-menu-item
                    index="3-2"
                    @click="openTab('分类管理', 'content-management/category-management/MoCategoryManagement', {})"
                >
                    <el-icon><menu-icon /></el-icon>
                    <span>分类管理</span>
                </el-menu-item>
                <el-menu-item
                    index="3-3"
                    @click="openTab('标签管理', 'content-management/tag-management/MoTagManagement', {})"
                >
                    <el-icon><price-tag /></el-icon>
                    <span>标签管理</span>
                </el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="4">
                <template #title>
                    <el-icon><tools /></el-icon>
                    <span>系统设置</span>
                </template>
                <el-menu-item
                    index="4-1"
                    @click="openTab('常规设置', 'system-setting/general-setting/MoGeneralSetting', {})"
                >
                    <el-icon><operation /></el-icon>
                    <span>常规设置</span>
                </el-menu-item>
                <el-menu-item
                    index="4-2"
                    @click="openTab('上传设置', 'system-setting/uploud-setting/MoUploadSetting', {})"
                >
                    <el-icon><upload /></el-icon>
                    <span>上传设置</span>
                </el-menu-item>
            </el-sub-menu>
        </el-menu>
    </div>
</template>

<script setup>
import {
    HomeFilled,
    Opportunity,
    TrendCharts,
    Document,
    Menu as MenuIcon,
    PriceTag,
    Tools,
    Operation,
    Upload
} from '@element-plus/icons-vue';

// 参数
defineProps({
    isCollapse: Boolean
});

// 回调
const emits = defineEmits(['open-tab']);

/**
 * 打开标签
 *
 * @param {string} title 菜单标题
 * @param {string} componentName 组件名称
 * @param {any} params 组件参数
 * @param {any} multipleId 多标签唯一ID
 */
const openTab = (title, componentName, params, multipleId) =>
    emits('open-tab', { title, componentName, params, multipleId });
</script>

<style scoped>
.mo-left-menu__menu {
    --mo-left-menu-background-color: #545c64;
    --mo-left-menu-background-color-hover: #434a50;
    --mo-left-menu-text-color: #fff;
    --mo-left-menu-active-text-color: #ffd04b;
}

.mo-left-menu__menu.el-menu {
    border-right-width: 0;
    --el-menu-bg-color: var(--mo-left-menu-background-color);
    --el-menu-hover-bg-color: var(--mo-left-menu-background-color-hover);
    --el-menu-text-color: var(--mo-left-menu-text-color);
    --el-menu-hover-text-color: var(--mo-left-menu-text-color);
    --el-menu-active-color: var(--mo-left-menu-active-text-color);
}

.mo-left-menu__menu.el-menu >>> .el-menu {
    --el-menu-bg-color: var(--mo-left-menu-background-color);
    --el-menu-hover-bg-color: var(--mo-left-menu-background-color-hover);
    --el-menu-text-color: var(--mo-left-menu-text-color);
    --el-menu-hover-text-color: var(--mo-left-menu-text-color);
    --el-menu-active-color: var(--mo-left-menu-active-text-color);
}
</style>
