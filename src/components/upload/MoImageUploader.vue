<template>
    <div class="mo-image-uploader">
        <el-upload
            list-type="picture-card"
            v-model:file-list="fileList"
            :action="imageApi.upload"
            :headers="headers"
            :multiple="multiple"
            :before-upload="handleBeforeUpload"
            :on-success="handleSuccess"
            :on-error="handleError"
            :on-preview="handlePictureCardPreview"
        >
            <el-icon><plus /></el-icon>
        </el-upload>
        <teleport to="body">
            <el-image-viewer v-if="dialogImageUrl !== ''" :url-list="[dialogImageUrl]" @close="dialogImageUrl = ''" />
        </teleport>
    </div>
</template>

<script setup>
import { Plus } from '@element-plus/icons-vue';
import { computed, reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { imageApi } from '@/api/image-service.mjs.js';
import storage from '@/utils/storage.mjs.js';

// 参数
const props = defineProps({
    // 已上传图片列表
    imageFileList: {
        type: Array,
        default: []
    },
    // 是否支持多张图片
    multiple: {
        type: Boolean,
        default: false
    }
});

// 回调
const emits = defineEmits(['update:imageFileList']);

// 图片预览对话框图片路径
const dialogImageUrl = ref('');
// 上传请求headers
const headers = {
    Authorization: 'Bearer ' + storage.get('token')
};

// CSS变量
const cssVariable = reactive({
    uploadDisplay: computed(() => (!props.multiple && props.imageFileList.length > 0 ? 'none' : 'inline-flex'))
});

// 文件列表
const fileList = computed({
    get: () => props.imageFileList,
    set: (value) => emits('update:imageFileList', value)
});

/**
 * 上传文件前事件
 *
 * @param {any} rawFile 上传文件
 */
const handleBeforeUpload = (rawFile) => {
    if (rawFile.size / 1024 / 1024 > 100) {
        ElMessage({ message: '图片文件不能大于100MB!', type: 'error' });
        return false;
    }
    return true;
};

/**
 * 请求成功事件
 *
 * @param {Response} response 请求响应对象
 * @param {any} uploadFile 上传文件信息
 */
const handleSuccess = (response, uploadFile) => {
    uploadFile.url2 = response.data.fileURL;
};

/**
 * 请求失败事件
 *
 * @param {Error} error 错误
 */
const handleError = (error) => {
    console.log(error.message);
    ElMessage({ message: error.message, type: 'error' });
};

/**
 * 图片预览事件
 *
 * @param {any} uploadFile 上传文件信息
 */
const handlePictureCardPreview = (uploadFile) => {
    dialogImageUrl.value = uploadFile.url;
};
</script>

<style scoped>
.mo-image-uploader {
    --mo-image-uploader-upload-display: v-bind(cssVariable.uploadDisplay);
}

@layer MoImageUploader {
    * {
        --mo-image-uploader-width: 72px;
        --mo-image-uploader-height: 72px;
    }
}

.mo-image-uploader >>> .el-upload-list.el-upload-list--picture-card .el-upload-list__item {
    width: var(--mo-image-uploader-width);
    height: var(--mo-image-uploader-height);
    margin: 0 4px 4px 0;
    transition: unset;
    vertical-align: bottom;
}

.mo-image-uploader >>> .el-upload.el-upload--picture-card {
    display: var(--mo-image-uploader-upload-display);
    width: var(--mo-image-uploader-width);
    height: var(--mo-image-uploader-height);
    margin: 0 4px 4px 0;
}
</style>
