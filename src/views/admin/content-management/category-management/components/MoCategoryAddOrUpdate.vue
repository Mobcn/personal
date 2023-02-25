<template>
    <div class="mo-category-dialog">
        <el-dialog v-model="dialogFormVisible" :title="title" :close-on-press-escape="false" width="400px">
            <el-form :model="formData" ref="formComponent" :rules="rules">
                <el-form-item label="名称" prop="name">
                    <el-input v-model="formData.name" placeholder="请输入分类名称" maxlength="128" />
                </el-form-item>
                <el-form-item label="描述" prop="description">
                    <el-input v-model="formData.description" placeholder="请输入分类描述" maxlength="255" />
                </el-form-item>
                <el-form-item class="mo-category-dialog__image" label="图片" :label-width="'50px'">
                    <mo-image-uploader
                        class="mo-category-dialog__uploader"
                        v-model:image-file-list="formData.imageFileList"
                    />
                </el-form-item>
                <el-form-item label="状态" :label-width="'50px'" v-if="formData.id">
                    <el-switch v-model="formData.status" class="mo-category-dialog__status" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span>
                    <el-button @click="handleCancel">取消</el-button>
                    <el-button type="primary" @click="hancleConfirm">{{ title }}</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import MoImageUploader from '@/components/upload/MoImageUploader.vue';
import { reactive, ref } from 'vue';
import categoryService from '@/api/category-service.mjs.js';

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
    image: '',
    imageFileList: [],
    status: true
});
// 表单校验
const rules = {
    name: [{ required: true, message: '分类名称不能为空!', trigger: 'blur' }],
    description: [{ required: true, message: '分类描述不能为空!', trigger: 'blur' }]
};

/**
 * 初始化操作
 */
const init = (id) => {
    clearDataForm();
    title.value = id ? '更新' : '添加';
    if (id) {
        // 获取分类数据
        categoryService
            .info(id)
            .then((res) => {
                formData.id = res.data.id;
                formData.name = res.data.name;
                formData.description = res.data.description;
                formData.image = res.data.image;
                if (formData.image && formData.image.trim() !== '') {
                    formData.imageFileList = [
                        {
                            url: formData.image,
                            url2: formData.image
                        }
                    ];
                }
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
const hancleConfirm = () => {
    formData.image = formData.imageFileList[0] ? formData.imageFileList[0].url2 : '';
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
    formData.image = '';
    formData.imageFileList = [];
    formData.status = true;
};

// 暴露参数
defineExpose({ init });
</script>

<style scoped>
.mo-category-dialog {
    --mo-category-dialog-status-on-color: #13ce66;
    --mo-category-dialog-status-off-color: #ff4949;
}

.mo-category-dialog >>> .el-dialog__body {
    padding-bottom: 5px;
}

.mo-category-dialog__image.el-form-item {
    margin-bottom: 8px;
}

.mo-category-dialog__image.el-form-item >>> .el-form-item__content {
    line-height: unset;
}

.mo-category-dialog__uploader.mo-image-uploader {
    margin-top: 6px;
}

.mo-category-dialog__status.el-switch {
    --el-switch-on-color: var(--mo-category-dialog-status-on-color);
    --el-switch-off-color: var(--mo-category-dialog-status-off-color);
}
</style>
