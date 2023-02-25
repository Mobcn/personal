<template>
    <el-form-item class="mo-publish-article">
        <el-container class="mo-publish-article__container" v-loading="loading">
            <el-header class="mo-publish-article__header">
                <el-form
                    class="mo-publish-article__form"
                    :model="form"
                    ref="formComponent"
                    label-width="60px"
                    :rules="rules"
                >
                    <div class="mo-publish-article__form-left">
                        <el-form-item class="mo-publish-article__form-item" label="标题" prop="title">
                            <el-input
                                class="mo-publish-article__title"
                                v-model="form.title"
                                placeholder="请输入标题（长度5~100）"
                                minlength="5"
                                maxlength="100"
                                :show-word-limit="true"
                                clearable
                            />
                        </el-form-item>
                        <el-form-item class="mo-publish-article__form-item" label="分类">
                            <el-select
                                class="mo-publish-article__category"
                                v-model="form.categoryId"
                                placeholder="未分类"
                            >
                                <el-option
                                    v-for="category in categoryList"
                                    :key="category.id"
                                    :label="category.name"
                                    :value="category.id"
                                />
                            </el-select>
                        </el-form-item>
                        <el-form-item class="mo-publish-article__form-item" label="标签">
                            <el-select
                                class="mo-publish-article__tag"
                                v-model="form.tagIds"
                                :multiple-limit="3"
                                placeholder="无标签"
                                multiple
                                collapse-tags
                                collapse-tags-tooltip
                                clearable
                            >
                                <el-option v-for="tag in tagList" :key="tag.id" :label="tag.name" :value="tag.id" />
                            </el-select>
                        </el-form-item>
                        <el-form-item class="mo-publish-article__cover" label="封面">
                            <mo-image-uploader v-model:image-file-list="form.imageFileList" />
                        </el-form-item>
                    </div>
                    <div class="mo-publish-article__form-right">
                        <el-form-item label="摘要" prop="description">
                            <el-input
                                v-model="form.description"
                                placeholder="请输入摘要（长度5~300）"
                                type="textarea"
                                rows="6"
                                minlength="5"
                                maxlength="300"
                                :show-word-limit="true"
                            />
                        </el-form-item>
                    </div>
                </el-form>
            </el-header>
            <el-main class="mo-publish-article__main">
                <md-editor-v3
                    class="mo-publish-article__md-editor"
                    v-model="form.content"
                    placeholder="请输入文章内容"
                    @onUploadImg="handleUploadImage"
                    @onSave="handleAddOrUpdate"
                />
            </el-main>
        </el-container>
    </el-form-item>
</template>

<script setup>
import MoImageUploader from '@/components/upload/MoImageUploader.vue';
import { reactive, ref, watch } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import articleService from '@/api/article-service.mjs.js';
import categoryService from '@/api/category-service.mjs.js';
import tagService from '@/api/tag-service.mjs.js';
import imageService from '@/api/image-service.mjs.js';
import MdEditorV3 from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';

// 参数
const props = defineProps({
    params: {
        type: Object,
        default: () => ({})
    },
    notSaved: {
        type: Boolean,
        default: false
    }
});

// 回调
const emits = defineEmits(['update:notSaved']);

// 表单组件
const formComponent = ref();
// 是否显示加载动画
const loading = ref(false);
// 表单对象
const form = reactive({
    id: '',
    title: '',
    description: '',
    cover: '',
    content: '',
    categoryId: '',
    tagIds: [],
    imageFileList: []
});
// 旧表单JSON字符串
const oldFormString = ref(JSON.stringify(form));
// 文章分类选项列表
const categoryList = reactive([]);
// 文章标签选项列表
const tagList = reactive([]);

// 表单校验
const rules = {
    title: [
        { required: true, message: '标题不能为空', trigger: 'blur' },
        { min: 5, max: 100, message: '标题长度应为5~100', trigger: 'blur' }
    ],
    description: [
        { required: true, message: '摘要不能为空', trigger: 'blur' },
        { min: 5, max: 300, message: '摘要长度应为5~300', trigger: 'blur' }
    ]
};

// 监听是否存在修改
watch(form, (newValue) => {
    emits('update:notSaved', form2String(newValue) !== oldFormString.value);
});

/**
 * 添加或更新事件
 *
 * @param {string} value 文章内容文本
 */
const handleAddOrUpdate = (value) => {
    formComponent.value.validate(async (valid) => {
        if (valid) {
            if (!value) {
                const result = await ElMessageBox.confirm('当前没有文章内容，是否保存?', '系统提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).catch(() => 'cancel');
                if (result === 'cancel') {
                    return;
                }
            }
            // 显示加载动画
            loading.value = true;
            form.cover = form.imageFileList[0] ? form.imageFileList[0].url2 : '';
            const action = !form.id ? articleService.add : articleService.update;
            action(form)
                .then((res) => {
                    // 关闭加载动画
                    loading.value = false;
                    ElMessage({
                        message: `文章${!form.id ? '添加' : '更新'}成功!`,
                        type: 'success',
                        duration: 2 * 1000
                    });
                    form.id = res.data.id;
                    oldFormString.value = form2String(form);
                })
                .catch((error) => {
                    // 关闭加载动画
                    loading.value = false;
                });
        }
    });
};

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
                form[key] = res.data[key];
            }
            form.tagIds = res.data.tags.map((tag) => tag.id);
            if (form.cover && form.cover.trim() !== '') {
                form.imageFileList = [
                    {
                        url: form.cover,
                        url2: form.cover
                    }
                ];
            }
            oldFormString.value = form2String(form);
        })
        .catch((error) => {});
};

/**
 * 加载文章分类选项列表
 */
const loadCategoryList = () => {
    categoryService
        .simpleList()
        .then((res) => {
            categoryList.splice(0, categoryList.length);
            res.data.map((category) => categoryList.push(category));
        })
        .catch((error) => {});
};

/**
 * 加载文章标签选项列表
 */
const loadTagList = () => {
    tagService
        .simpleList()
        .then((res) => {
            tagList.splice(0, tagList.length);
            res.data.map((tag) => tagList.push(tag));
        })
        .catch((error) => {});
};

/**
 * 图片上传事件
 *
 * @param {Array<File>} files 图片文件集
 * @param {Function} callback 回调函数
 */
const handleUploadImage = async (files, callback) => {
    const resAll = await Promise.all(files.map((file) => imageService.upload(file)));
    const fileURLArray = resAll.map((res) => res.data.fileURL);
    // 预缓存图片
    await Promise.all(fileURLArray.map((fileURL) => fetch(fileURL, { mode: 'no-cors' })));
    callback(fileURLArray);
};

/**
 * 表单对象转字符串
 *
 * @param {any} form 表单对象
 */
const form2String = (form) => {
    if (form.imageFileList.length > 0) {
        const copyForm = Object.assign({}, form);
        copyForm.imageFileList = [
            {
                url: form.imageFileList[0].url,
                url2: form.imageFileList[0].url2
            }
        ];
        return JSON.stringify(copyForm);
    }
    return JSON.stringify(form);
};

// 初始化操作
(() => {
    // 加载文章分类选项列表
    loadCategoryList();
    // 加载文章标签选项列表
    loadTagList();
    if (props.params.articleId) {
        // 获取文章信息
        getArticleInfo(props.params.articleId);
    }
})();
</script>

<style scoped>
.mo-publish-article {
    height: 100%;
    --mo-publish-article-header-height: 166px;
}

.mo-publish-article__form-left,
.mo-publish-article__form-right {
    display: inline-block;
    width: 50%;
}

.mo-publish-article__container.el-container {
    height: 100%;
}

.mo-publish-article__header.el-header {
    height: var(--mo-publish-article-header-height);
    padding: 0;
}

.mo-publish-article__form.el-form {
    display: flex;
}

.mo-publish-article__form-item.el-form-item {
    margin: 0 0 20px 0;
}

.mo-publish-article__title.el-input {
    flex: 1;
}

.mo-publish-article__category.el-select,
.mo-publish-article__tag.el-select {
    flex: 0.7;
}

.mo-publish-article__cover.el-form-item {
    position: absolute;
    top: 52px;
    right: 50%;
}

.mo-publish-article__cover.el-form-item >>> .el-upload-list.el-upload-list--picture-card .el-upload-list__item,
.mo-publish-article__cover.el-form-item >>> .el-upload.el-upload--picture-card {
    --mo-image-uploader-width: 84px;
    --mo-image-uploader-height: 84px;
}

.mo-publish-article__main.el-main {
    padding: 0;
}

.mo-publish-article__md-editor.md {
    height: 100%;
}
</style>
