<template>
    <div class="mo-main">
        <md-editor-v3 class="mo-main__content" v-model="content" :previewOnly="true" />
    </div>
</template>

<script setup>
import { getCurrentInstance, ref } from 'vue';
import MdEditorV3 from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';

// 获取真实路径函数
const getActualPath = getCurrentInstance().proxy.$getActualPath;

// 内容
const content = ref('');

// 初始化操作
(() => {
    fetch(getActualPath('static/doc/main.md'))
        .then((response) => {
            return response.text();
        })
        .then((text) => {
            content.value = text;
        });
})();
</script>

<style scoped>
.mo-main {
    height: 100%;
    display: flex;
    flex-flow: column;
}

.mo-main__content.md.md-previewOnly {
    flex: 1;
}
</style>
