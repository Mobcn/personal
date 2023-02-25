<template>
    <div class="mo-archives">
        <div class="mo-archives__container">
            <el-timeline class="mo-archives__timeline">
                <el-timeline-item
                    v-for="(item, index) in archivesState.listData"
                    :key="index"
                    :class="!item.id ? 'mo-archives__year-month' : ''"
                    placement="top"
                    type="primary"
                    :size="item.id ? 'normal' : 'large'"
                    :hollow="item.id ? true : false"
                    :timestamp="item.createTime"
                    :hide-timestamp="item.id ? false : true"
                >
                    <router-link v-if="item.id" class="mo-archives__link" :to="`/article/${item.id}`">
                        {{ item.title }}
                    </router-link>
                    <span>{{ item.yearMonth }}</span>
                </el-timeline-item>
            </el-timeline>
            <span v-if="isLoading" class="mo-archives__tips mo-archives__tips--loading" />
            <span v-if="isCompleted" class="mo-archives__tips"> 没有更多数据了~ </span>
        </div>
    </div>
</template>

<script setup>
import { computed, onBeforeUnmount, onMounted, reactive, ref } from 'vue';
import articleService from '@/api/article-service.mjs.js';
import storage from '@/utils/storage.mjs.js';

// 归档状态数据
const archivesState = reactive({
    // 当前页数
    page: 0,
    // 分页每页数据条数
    pageSize: storage.getObject('pageSize')['home'],
    // 数据总条数
    total: 0,
    // 当前数据条数
    length: 0,
    // 年月列表
    yearMonthList: [],
    // 归档信息列表
    listData: []
});

// 是否正在加载
const isLoading = ref(false);
// 是否完全加载
const isCompleted = computed(() => archivesState.length === archivesState.total && archivesState.page > 0);

/**
 * 文章归档信息数据加载事件
 */
const handleLoad = async () => {
    if (isCompleted.value || isLoading.value) {
        return;
    }
    isLoading.value = true;
    archivesState.page += 1;
    await articleService
        .list({}, archivesState.page, archivesState.pageSize)
        .then((res) => {
            isLoading.value = false;
            archivesState.total = res.data.total;
            archivesState.length += res.data.list.length;
            res.data.list.map((item) => {
                const yearMonth = item.createTime.substring(0, 7);
                if (!archivesState.yearMonthList.includes(yearMonth)) {
                    archivesState.yearMonthList.push(yearMonth);
                    archivesState.listData.push({ yearMonth: yearMonth.replace('-', '年') + '月' });
                }
                archivesState.listData.push(item);
            });
        })
        .catch((error) => {
            isLoading.value = false;
        });
};

/**
 * 滚动条事件
 */
const handleScroll = () => {
    const clientHeight = document.documentElement.clientHeight;
    const scrollTop = document.documentElement.scrollTop;
    const scrollHeight = document.documentElement.scrollHeight;
    if (Math.abs(scrollHeight - clientHeight - scrollTop) < 20) {
        handleLoad();
    }
};

// 挂载
onMounted(async () => {
    do {
        // 加载文章归档信息数据
        await handleLoad();
    } while (document.querySelector('.mo-archives').clientHeight < document.documentElement.clientHeight);
    // 添加滚动条监听事件
    document.addEventListener('scroll', handleScroll, false);
});

// 销毁
onBeforeUnmount(() => {
    // 删除滚动条监听事件
    document.removeEventListener('scroll', handleScroll, false);
});
</script>

<style scoped>
.mo-archives {
    --mo-archives-padding: 100px 0 0 0;
    --mo-archives-timeline-width: 50%;
    --mo-archives-timeline-min-width: 220px;
    --mo-archives-timeline-padding: 0 50px 0 115px;
    --mo-archives-link-font-size: 14px;
    --mo-archives-link-color: #303133;
    --mo-archives-link-color-hover: #ffd04b;
    --mo-archives-timeline-item-wrapper-width: calc(100% - 28px);
    --mo-archives-year-month-wrapper-font-size: 16px;
    --mo-archives-year-month-wrapper-top: -4px;
    --mo-archives-year-month-wrapper-right: 130px;
    --mo-archives-tips-font-size: 15px;
    --mo-archives-tips-width: calc(50% + 20px);
    --mo-archives-tips-min-width: 240px;
    --mo-archives-tips-padding: 0 0 20px 135px;
    --mo-archives-tips-color: #303133;
}

.mo-archives__container {
    display: flex;
    align-items: center;
    flex-direction: column;
    justify-content: center;
    padding: var(--mo-archives-padding);
}

.mo-archives__link {
    font-size: var(--mo-archives-link-font-size);
    text-decoration: none;
    color: var(--mo-archives-link-color);
}

.mo-archives__link:hover {
    color: var(--mo-archives-link-color-hover);
}

.mo-archives__tips {
    font-size: var(--mo-archives-tips-font-size);
    width: var(--mo-archives-tips-width);
    min-width: var(--mo-archives-tips-min-width);
    padding: var(--mo-archives-tips-padding);
    color: var(--mo-archives-tips-color);
}

.mo-archives__tips--loading::after {
    content: '';
    animation: loading 1.5s infinite;
}

@keyframes loading {
    0% {
        content: '加载中 ';
    }
    25% {
        content: '加载中 .';
    }
    50% {
        content: '加载中 . .';
    }
    75% {
        content: '加载中 . . .';
    }
    100% {
        content: '加载中 ';
    }
}

.mo-archives__timeline.el-timeline {
    width: var(--mo-archives-timeline-width);
    min-width: var(--mo-archives-timeline-min-width);
    padding: var(--mo-archives-timeline-padding);
}

.mo-archives__timeline.el-timeline >>> .el-timeline-item__wrapper {
    width: var(--mo-archives-timeline-item-wrapper-width);
}

.mo-archives__year-month.el-timeline-item >>> .el-timeline-item__wrapper {
    font-size: var(--mo-archives-year-month-wrapper-font-size);
    top: var(--mo-archives-year-month-wrapper-top);
    right: var(--mo-archives-year-month-wrapper-right);
}
</style>
