<template>
    <div class="mo-toolbar">
        <el-form class="mo-toolbar__form" :model="model" :inline="true" @submit.native.prevent>
            <el-form-item class="mo-toolbar__item">
                <el-form-item class="mo-toolbar__sub-item">
                    <el-button type="primary" @click="emits('add')">添加</el-button>
                </el-form-item>
                <el-form-item class="mo-toolbar__sub-item--last">
                    <el-button type="danger" @click="emits('remove-batch')">批量删除</el-button>
                </el-form-item>
            </el-form-item>
            <el-form-item class="mo-toolbar__item">
                <el-form-item class="mo-toolbar__sub-item">
                    <slot name="select" :model="model" />
                    <el-input
                        class="mo-toolbar__input"
                        v-model="model.key"
                        placeholder="请输入关键词"
                        :maxlength="inputMaxlength"
                        clearable
                        @keyup.enter.native="emits('search', model)"
                    >
                    </el-input>
                </el-form-item>
                <el-form-item class="mo-toolbar__sub-item--last">
                    <el-button type="primary" round @click="emits('search', model)">
                        <el-icon><search /></el-icon>
                    </el-button>
                </el-form-item>
            </el-form-item>
        </el-form>
    </div>
</template>

<script setup>
import { Search } from '@element-plus/icons-vue';
import { reactive } from 'vue';

// 参数
defineProps({
    model: {
        type: Object,
        default: () =>
            reactive({
                key: ''
            })
    },
    inputMaxlength: {
        type: Number,
        default: 100
    }
});

// 回调事件
const emits = defineEmits(['add', 'remove-batch', 'search']);
</script>

<style scoped>
@layer MoToolbar {
    * {
        --mo-toolbar-input-width: 300px;
    }

    .mo-toolbar {
        height: 100%;
    }
}

.mo-toolbar__form.el-form {
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 100%;
}

.mo-toolbar__item.el-form-item {
    margin: 0;
}

.mo-toolbar__sub-item.el-form-item {
    margin-right: 10px;
}

.mo-toolbar__sub-item--last.el-form-item {
    margin: 0;
}

.mo-toolbar__input.el-input {
    width: var(--mo-toolbar-input-width);
}
</style>
