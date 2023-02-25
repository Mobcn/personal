<template>
    <div class="mo-panel">
        <el-container class="mo-panel__container">
            <!-- 导航 -->
            <el-header class="mo-panel__header">
                <mo-head-menu @loginout="loginout" />
            </el-header>
            <el-container>
                <!-- 菜单 -->
                <el-aside class="mo-panel__aside" :width="asideWidth">
                    <div class="mo-panel__left-menu">
                        <span class="mo-panel__menu-body">
                            <mo-left-menu :isCollapse="isCollapse" @open-tab="handleOpenTab" />
                        </span>
                        <span class="mo-panel__menu-divider">
                            <span
                                class="mo-panel__divider-icon"
                                :title="dividerTitle"
                                v-html="dividerIcon"
                                @click="isCollapse = !isCollapse"
                            />
                        </span>
                    </div>
                </el-aside>
                <!-- 显示主体 -->
                <el-main class="mo-panel__main">
                    <el-tabs
                        class="mo-panel__tabs"
                        v-model="editableTabsValue"
                        type="border-card"
                        closable
                        @tab-remove="handleRemoveTab"
                    >
                        <el-tab-pane
                            v-for="item in editableTabs"
                            :key="item.componentId"
                            :label="item.title"
                            :name="item.componentId"
                        >
                            <el-scrollbar class="mo-panel__scrollbar">
                                <component
                                    :is="item.component"
                                    v-model:not-saved="item.notSaved"
                                    :params="item.params"
                                    @open-tab="handleOpenTab"
                                />
                            </el-scrollbar>
                        </el-tab-pane>
                    </el-tabs>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script setup>
import MoHeadMenu from './MoHeadMenu.vue';
import MoLeftMenu from './MoLeftMenu.vue';
import { computed, getCurrentInstance, markRaw, ref } from 'vue';
import { ElMessageBox } from 'element-plus';
import storage from '@/utils/storage.mjs.js';

// 获取真实路径函数
const getActualPath = getCurrentInstance().proxy.$getActualPath;
// 获取动态组件函数
const getAsyncComponent = getCurrentInstance().proxy.$getAsyncComponent;

// 回调对象
const emits = defineEmits(['change-page']);

// 侧边栏是否展开
const isCollapse = ref(false);
// 当前活动标签值
const editableTabsValue = ref('');
// 标签数组
const editableTabs = ref([]);

// 侧边栏宽度
const asideWidth = computed(() => (isCollapse.value ? '73px' : '209px'));
// 侧边分界线标题
const dividerTitle = computed(() => (isCollapse.value ? '展开' : '收起'));
// 侧边分界线显示图标
const dividerIcon = computed(() => (isCollapse.value ? '&raquo;' : '&laquo;'));

/**
 * 打开菜单对应标签页事件
 *
 * @param {Object} data 打开标签数据：data.title（标题）、data.componentName（组件名）
 */
const handleOpenTab = (data) => {
    const { title, componentName, params, multipleId } = data;
    const tabs = editableTabs.value;
    const componentId = !multipleId ? title : title + String(multipleId);
    getAsyncComponent(getActualPath(`@/views/admin/${componentName}.vue`)).then((component) => {
        if (!containsObject(tabs, { componentId })) {
            tabs.push({
                title,
                componentId,
                component: markRaw(component),
                params: params ?? {},
                notSaved: false
            });
        }
        editableTabsValue.value = componentId;
    });
};

/**
 * 删除标签页事件
 *
 * @param {string} targetId 删除的目标标签页标识
 */
const handleRemoveTab = async (targetId) => {
    const currentRemoveTab = getTabByComponentId(targetId);
    let activeId = editableTabsValue.value;
    if (currentRemoveTab.notSaved) {
        editableTabsValue.value = currentRemoveTab.componentId;
        const result = await ElMessageBox.confirm('存在修改内容未保存，确定要关闭?', '系统提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).catch(() => 'cancel');
        if (result === 'cancel') {
            return;
        }
        if (activeId !== targetId) {
            editableTabsValue.value = activeId;
        }
    }
    const tabs = editableTabs.value;
    if (activeId === targetId) {
        tabs.forEach((tab, index) => {
            if (tab.componentId === targetId) {
                const nextTab = tabs[index + 1] ?? tabs[index - 1];
                if (nextTab) {
                    activeId = nextTab.componentId;
                }
            }
        });
    }
    editableTabsValue.value = activeId;
    editableTabs.value = tabs.filter((tab) => tab.componentId !== targetId);
};

/**
 * 通过组件ID获取组件信息对象
 *
 * @param {string} componentId 组件ID
 */
const getTabByComponentId = (componentId) => {
    for (const tab of editableTabs.value) {
        if (tab.componentId === componentId) {
            return tab;
        }
    }
    return null;
};

/**
 * 判断数组arr中是否包含属性相同的对象
 *
 * @param {Array} arr 数组
 * @param {Object} obj 对象
 */
const containsObject = (arr, obj) => {
    const keys = Object.keys(obj);
    const index = arr.findIndex((item) => {
        for (const key of keys) {
            if (item[key] !== obj[key]) {
                return false;
            }
        }
        return true;
    });
    return index > -1;
};

/**
 * 退出登录
 */
const loginout = () => {
    storage.remove('token');
    emits('change-page', { componentName: 'MoLogin' });
};

// 初始化操作
(() => {
    // 打开首页
    handleOpenTab({
        title: '首页',
        componentName: 'main/MoMain'
    });
})();
</script>

<style scoped>
.mo-panel {
    --mo-panel-header-height: 60px;
    --mo-panel-header-border-bottom: 1px solid #ffd04b;
    --mo-panel-header-background-color: #505860;
    --mo-panel-aside-background-color: #545c64;
    --mo-panel-main-padding: 5px;
    --mo-panel-main-background-color: #596172;
    --mo-panel-tabs-border-radius: 5px;
    --mo-panel-menu-divider-background-color: #bebebe;
}

.mo-panel__left-menu {
    display: flex;
    flex-flow: row;
    height: 100%;
}

.mo-panel__menu-body {
    flex: 1;
}

.mo-panel__menu-divider {
    display: flex;
    align-items: center;
    width: 9px;
    background-color: var(--mo-panel-menu-divider-background-color);
}

.mo-panel__divider-icon {
    cursor: pointer;
    user-select: none;
}

.mo-panel__container.el-container {
    height: 100vh;
    min-height: calc(var(--mo-panel-header-height) * 12);
    min-width: calc(var(--mo-panel-header-height) * 21);
}

.mo-panel__header.el-header {
    height: var(--mo-panel-header-height);
    padding: 0;
    border-bottom: var(--mo-panel-header-border-bottom);
    background-color: var(--mo-panel-header-background-color);
}

.mo-panel__aside.el-aside {
    background-color: var(--mo-panel-aside-background-color);
}

.mo-panel__main.el-main {
    padding: var(--mo-panel-main-padding);
    background-color: var(--mo-panel-main-background-color);
}

.mo-panel__tabs.el-tabs {
    display: flex;
    flex-flow: column;
    height: 99.5%;
    border-radius: var(--mo-panel-tabs-border-radius);
}

.mo-panel__tabs.el-tabs >>> .el-tabs__content {
    flex: 1;
    padding: 0;
}

.mo-panel__tabs.el-tabs >>> .el-tabs__content .el-tab-pane {
    position: absolute;
    width: 100%;
    height: 100%;
}

.mo-panel__scrollbar.el-scrollbar {
    position: absolute;
    width: calc(100% - 30px);
    height: calc(100% - 30px);
    margin: 15px;
}

.mo-panel__scrollbar.el-scrollbar >>> .el-scrollbar__view {
    height: 100%;
}

.mo-panel__tabs.el-tabs >>> .el-tabs__header .el-tabs__nav .el-tabs__item:first-child i {
    display: none;
}
</style>
