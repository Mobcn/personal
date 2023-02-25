<template>
    <div class="mo-beian">
        <div v-if="icpText.trim() !== '' || policeText.trim() !== ''" class="mo-beian__container">
            <div class="mo-beian__wrap">
                <a v-if="icpText.trim() !== ''" class="mo-beian__icp" href="https://beian.miit.gov.cn/" target="_blank">
                    {{ icpText }}
                </a>
                <a
                    v-if="policeText.trim() !== ''"
                    class="mo-beian__police"
                    :href="`http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=${policeCode}`"
                    target="_blank"
                >
                    <img class="mo-beian__police-img" :src="getActualPath('static/img/police.png')" />
                    <span>{{ policeText }}</span>
                </a>
            </div>
        </div>
    </div>
</template>

<script setup>
import { computed, getCurrentInstance } from 'vue';

// 参数
const props = defineProps({
    // ICP备案号
    icpText: {
        type: String,
        default: ''
    },
    // 公安备案号
    policeText: {
        type: String,
        default: ''
    }
});

// 获取真实路径函数
const getActualPath = getCurrentInstance().proxy.$getActualPath;

// 公安备案编号
const policeCode = computed(() => props.policeText.replace(/[^0-9]/g, ''));
</script>

<style scoped>
.mo-beian {
    --mo-beian-font-size: 12px;
    --mo-beian-padding: 10px 0;
    --mo-beian-background-color: #e6e6e6;
    --mo-beian-icp-height: 20px;
    --mo-beian-icp-margin: 0 10px 0 0;
    --mo-beian-icp-color: #939393;
    --mo-beian-police-height: 20px;
    --mo-beian-police-color: #939393;
    --mo-beian-police-img-margin: 0 2px 0 0;
}

.mo-beian__container {
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: var(--mo-beian-background-color);
}

.mo-beian__wrap {
    font-size: var(--mo-beian-font-size);
    padding: var(--mo-beian-padding);
}

.mo-beian__icp {
    line-height: var(--mo-beian-icp-height);
    display: inline-block;
    height: var(--mo-beian-icp-height);
    margin: var(--mo-beian-icp-margin);
    text-decoration: none;
    color: var(--mo-beian-icp-color);
}

.mo-beian__police {
    line-height: var(--mo-beian-police-height);
    display: inline-block;
    height: var(--mo-beian-police-height);
    text-decoration: none;
    color: var(--mo-beian-police-color);
}

.mo-beian__police-img {
    float: left;
    margin: var(--mo-beian-police-img-margin);
}
</style>
