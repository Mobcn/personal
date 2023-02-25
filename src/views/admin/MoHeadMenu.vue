<template>
    <div class="mo-head">
        <span class="mo-head__logo">个人空间</span>
        <span class="mo-head__logout" title="退出" @click="emits('loginout', {})">
            <el-icon><mo-icon icon-name="box-arrow-in-right" /></el-icon>
        </span>
    </div>
</template>

<script setup>
import MoIcon from '@/components/icons/MoIcon.vue';

// 回调对象
const emits = defineEmits(['loginout']);
</script>

<style scoped>
.mo-head {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    height: 100%;
    --mo-head-logo-font-family: '宋体';
    --mo-head-logo-font-size: 30px;
    --mo-head-logo-color: #e6e6e6;
    --mo-head-logo-border: 1px solid #f5f5f5;
    --mo-head-logo-border-radius: 5px;
    --mo-head-logout-font-size: 24px;
    --mo-head-logout-color: #e6e6e6;
    --mo-head-logout-color-hover: #ffd04b;
}

.mo-head__logo {
    font-family: var(--mo-head-logo-font-family);
    font-size: var(--mo-head-logo-font-size);
    margin-left: 30px;
    padding: 5px;
    color: var(--mo-head-logo-color);
    border: var(--mo-head-logo-border);
    border-radius: var(--mo-head-logo-border-radius);
}

.mo-head__logout {
    font-size: var(--mo-head-logout-font-size);
    margin-right: 20px;
    color: var(--mo-head-logout-color);
}

.mo-head__logout:hover {
    cursor: pointer;
    color: var(--mo-head-logout-color-hover);
}
</style>
