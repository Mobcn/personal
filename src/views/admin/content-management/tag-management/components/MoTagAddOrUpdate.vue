<template>
    <div class="mo-tag-dialog">
        <el-dialog v-model="dialogFormVisible" :title="title" width="400px">
            <el-form :model="formData" ref="formComponent" :rules="rules">
                <el-form-item label="名称" prop="name">
                    <el-input v-model="formData.name" placeholder="请输入标签名称" maxlength="128" />
                </el-form-item>
                <el-form-item label="描述" prop="description">
                    <el-input v-model="formData.description" placeholder="请输入标签描述" maxlength="255" />
                </el-form-item>
                <el-form-item label="状态" prop="status" :label-width="'50px'" v-if="formData.id">
                    <el-switch v-model="formData.status" class="mo-tag-dialog__status" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span>
                    <el-button @click="handleCancel">取消</el-button>
                    <el-button type="primary" @click="handleConfirm">{{ title }}</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import tagService from '@/api/tag-service.mjs.js';

// 回调事件
const emits = defineEmits(['confirm']);

// 表单数据组件
const formComponent = ref();
// 对话框是否显示
const dialogFormVisible = ref(false);
// 对话框标题
const title = ref('添加');
// 表单数据对象
const formData = reactive({
    id: null,
    name: '',
    description: '',
    status: true
});
// 表单校验
const rules = {
    name: [{ required: true, message: '标签名称不能为空!', trigger: 'blur' }],
    description: [{ required: true, message: '标签描述不能为空!', trigger: 'blur' }]
};

/**
 * 初始化操作
 */
const init = (id) => {
    clearDataForm();
    title.value = id ? '更新' : '添加';
    if (id) {
        // 获取标签数据
        tagService
            .info(id)
            .then((res) => {
                formData.id = res.data.id;
                formData.name = res.data.name;
                formData.description = res.data.description;
                formData.status = res.data.status;
                dialogFormVisible.value = true;
            })
            .catch((error) => {});
    } else {
        dialogFormVisible.value = true;
    }
};

/**
 * 确定操作事件
 */
const handleConfirm = () => {
    formComponent.value.validate((valid) => {
        if (valid) {
            emits('confirm', formData, () => {
                dialogFormVisible.value = false;
            });
        }
    });
};

/**
 * 取消操作事件
 */
const handleCancel = () => {
    dialogFormVisible.value = false;
};

/**
 * 清空表单数据
 */
const clearDataForm = () => {
    formData.id = null;
    formData.name = '';
    formData.description = '';
    formData.status = true;
};

// 暴露参数
defineExpose({ init });
</script>

<style scoped>
.mo-tag-dialog {
    --mo-tag-dialog-status-on-color: #13ce66;
    --mo-tag-dialog-status-off-color: #ff4949;
}

.mo-tag-dialog >>> .el-dialog__body {
    padding-bottom: 5px;
}

.mo-tag-dialog__status.el-switch {
    --el-switch-on-color: var(--mo-tag-dialog-status-on-color);
    --el-switch-off-color: var(--mo-tag-dialog-status-off-color);
}
</style>
