<template>
    <div class="mo-management">
        <el-container class="mo-management__container">
            <el-header class="mo-management__header">
                <mo-toolbar
                    :model="model.toolbar"
                    @add="emits('add')"
                    @remove-batch="emits('remove-batch')"
                    @search="(params) => emits('search', params)"
                />
            </el-header>
            <el-main class="mo-management__main">
                <slot name="list" />
            </el-main>
            <el-footer class="mo-management__footer">
                <el-pagination
                    layout="prev, pager, next"
                    :page-size="model.pageSize"
                    :current-page="model.page"
                    :total="model.total"
                    @current-change="(page) => emits('change-page', page)"
                />
            </el-footer>
        </el-container>
        <slot name="dialog" />
    </div>
</template>

<script setup>
import MoToolbar from './MoToolbar.vue';
import { reactive } from 'vue';
import storage from '@/utils/storage.mjs.js';

//参数
defineProps({
    model: {
        type: Object,
        default: () =>
            reactive({
                toolbar: {
                    key: ''
                },
                pageSize: storage.getObject('pageSize')['admin'],
                page: 1,
                total: 0
            })
    }
});

// 回调事件
const emits = defineEmits(['add', 'remove-batch', 'search', 'change-page']);
</script>

<style scoped>
@layer MoManagement {
    * {
        --mo-management-header-height: 56px;
    }

    .mo-management {
        height: 100%;
    }
}

.mo-management__container.el-container {
    height: 100%;
}

.mo-management__header.el-header {
    height: var(--mo-management-header-height);
    padding: 10px 5px;
}

.mo-management__main.el-main {
    padding: 0;
}

.mo-management__footer.el-footer {
    display: flex;
    align-items: center;
    padding: 0;
}
</style>
