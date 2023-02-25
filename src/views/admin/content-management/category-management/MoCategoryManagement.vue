<template>
    <div class="mo-category-management">
        <mo-management
            :model="model"
            @add="handleOpenAddOrUpdateDialog()"
            @remove-batch="handleRemoveBatch"
            @search="handleSearch"
            @change-page="refresh"
        >
            <template #list>
                <el-table
                    class="mo-category-management__list"
                    ref="tableComponent"
                    :key="tableKey"
                    :data="listData"
                    height="100%"
                >
                    <el-table-column type="selection" width="55" />
                    <el-table-column label="#" type="index" width="50" />
                    <el-table-column label="名称" property="name" width="120" />
                    <el-table-column label="描述" property="description" show-overflow-tooltip />
                    <el-table-column label="包含文章数量" property="articleAmount" :align="'center'" width="130" />
                    <el-table-column label="图片" property="image" :align="'center'" width="130">
                        <template #default="scope">
                            <el-image
                                class="mo-category-management__image"
                                :src="scope.row.image ? scope.row.image : undefined"
                                :preview-src-list="[scope.row.image]"
                                :initial-index="0"
                                :preview-teleported="true"
                            >
                                <template #error>
                                    <el-icon><picture /></el-icon>
                                </template>
                            </el-image>
                        </template>
                    </el-table-column>
                    <el-table-column label="状态" property="status" :align="'center'" width="120">
                        <template #default="scope">
                            <el-switch
                                class="mo-category-management__status ml-2"
                                v-model="scope.row.status"
                                @change="statusChange(scope.row)"
                            />
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" :align="'center'" width="140">
                        <template #default="scope">
                            <el-button type="primary" size="small" @click="handleOpenAddOrUpdateDialog(scope.row.id)"
                                >更新</el-button
                            >
                            <el-button type="danger" size="small" @click="handleRemove(scope.row.id)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </template>
            <template #dialog>
                <mo-category-add-or-update ref="tagAddOrUpdateComponent" @confirm="handleAddOrUpdate" />
            </template>
        </mo-management>
    </div>
</template>

<script setup>
import { Picture } from '@element-plus/icons-vue';
import MoManagement from '@/components/management/MoManagement.vue';
import MoCategoryAddOrUpdate from './components/MoCategoryAddOrUpdate.vue';
import { ref, watch } from 'vue';
import { ElMessage } from 'element-plus';
import categoryService from '@/api/category-service.mjs.js';
import managementViewUtils from '@/utils/management-view-utils.mjs.js';

// 表格组件对象
const tableComponent = ref();
// 标签添加或更新组件对象
const tagAddOrUpdateComponent = ref();
// 表格组件key
const tableKey = ref(0);

// 创建属性函数
const {
    model,
    listData,
    handleOpenAddOrUpdateDialog,
    handleSearch,
    handleAddOrUpdate,
    handleRemove,
    handleRemoveBatch,
    refresh
} = managementViewUtils.create({
    service: categoryService,
    addOrUpdateComponent: tagAddOrUpdateComponent,
    listComponent: tableComponent
});

// 监听列表数据，刷新列表组件（解决表格滚动条显示问题）
watch(listData, () => {
    tableKey.value++;
});

/**
 * 标签状态修改
 */
const statusChange = (row) => {
    categoryService
        .status(row.id)
        .then((res) => {
            if (res.data.status !== row.status) {
                row.status = !row.status;
                ElMessage({ message: '标签状态更新失败!', type: 'error' });
            } else {
                ElMessage({
                    message: row.status ? '已启用' : '已禁用',
                    type: row.status ? 'success' : 'error'
                });
            }
        })
        .catch((error) => {
            row.status = !row.status;
        });
};

// 初始化操作
(async () => {
    // 获取标签列表
    refresh(1);
})();
</script>

<style scoped>
.mo-category-management {
    height: 100%;
    --mo-category-management-status-on-color: #13ce66;
    --mo-category-management-status-off-color: #ff4949;
}

.mo-category-management__status.el-switch {
    --el-switch-on-color: var(--mo-category-management-status-on-color);
    --el-switch-off-color: var(--mo-category-management-status-off-color);
}

.mo-category-management__image.el-image {
    display: flex;
    align-items: center;
    justify-content: center;
}

.mo-category-management__image.el-image >>> .el-image__inner {
    max-width: 100px;
    max-height: 100px;
}
</style>
