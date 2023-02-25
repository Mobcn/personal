<template>
    <svg :width="width" :height="height" :fill="fill">
        <use :xlink:href="iconLink" />
    </svg>
</template>

<script setup>
import { computed, ref } from 'vue';
import store from '@/store/store.mjs.js';
import iconConfig from '@/assets/icons/icon.config.json';

// 参数
const prop = defineProps({
    // 图标名
    iconName: {
        type: String,
        default: ''
    },
    // 图标路径名
    iconPathName: {
        type: String,
        default: 'bootstrap'
    },
    // 宽度
    width: {
        type: Number,
        default: 16
    },
    // 高度
    height: {
        type: Number,
        default: 16
    },
    // 填充
    fill: {
        type: String,
        default: 'currentColor'
    }
});

// Blob图标地址
const blobIconURL = ref(store.blobIconURL?.[prop.iconPathName] ?? '');

// 图标链接
const iconLink = computed(() => {
    return `${blobIconURL.value}#${prop.iconName}`;
});

// 初始化操作
(() => {
    // 获取Blob图标地址
    if (blobIconURL.value === '') {
        const iconURL = iconConfig[prop.iconPathName];
        store.requestMap ??= {};
        store.requestMap[iconURL] ??= fetch(iconURL)
            .then((res) => res.blob())
            .then((blob) => {
                const blobURL = URL.createObjectURL(blob);
                store.blobIconURL ??= {};
                store.blobIconURL[prop.iconPathName] = blobURL;
                return blobURL;
            });
        store.requestMap[iconURL].then((blobURL) => (blobIconURL.value = blobURL));
    }
})();
</script>
