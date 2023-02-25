import { reactive } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import storage from '@/utils/storage.mjs.js';

/**
 * 管理相关视图工具
 */
const managementViewUtils = {
    /**
     * 创建管理相关视图所需属性函数实例
     *
     * @param {any} param0 {
     *     service: 接口服务,
     *     addOrUpdateComponent: 添加或更新组件对象,
     *     listComponent?: 列表组件对象,
     *     getSelectRows?: 获取当前选择行集
     * }
     * @returns 管理相关视图所需属性函数实例
     */
    create: ({ service, addOrUpdateComponent, listComponent, getSelectRows }) => {
        // 模型数据
        const model = reactive({
            toolbar: {
                key: ''
            },
            pageSize: storage.getObject('pageSize')['admin'],
            page: 1,
            total: 0
        });

        // 列表数据
        const listData = reactive([]);

        if (listComponent && !getSelectRows) {
            /**
             * 获取当前选择行集（el-table组件）
             */
            getSelectRows = () => {
                const store = listComponent.value.store;
                if (!store || !store.isSelected) {
                    throw new Error('列表组件应为el-table组件!');
                }
                const selectRows = [];
                for (let i = 0; i < listData.length; ++i) {
                    if (store.isSelected(listData[i])) {
                        selectRows.push(listData[i]);
                    }
                }
                return selectRows;
            };
        }

        /**
         * 打开添加或更新对话框事件
         *
         * @param {string} id ID
         */
        const handleOpenAddOrUpdateDialog = (id) => addOrUpdateComponent.value.init(id);

        /**
         * 搜索事件
         *
         * @param {any} params 查询参数
         */
        const handleSearch = (params) => {
            model.page = 1;
            list(params, model.page, model.pageSize);
        };

        /**
         * 添加或更新事件
         *
         * @param {any} data 添加或更新数据
         * @param {Function} callback 回调函数
         */
        const handleAddOrUpdate = async (data, callback) => {
            const result = await addOrUpdate(data);
            if (result) {
                callback();
            }
        };

        /**
         * 删除事件
         *
         * @param {string} id ID
         */
        const handleRemove = (id) => {
            ElMessageBox.confirm(`确定要删除吗?`, '系统提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
                .then(() => remove(id))
                .catch(() => 'cancel');
        };

        /**
         * 批量删除事件
         */
        const handleRemoveBatch = () => {
            const selectRows = getSelectRows();
            if (selectRows.length === 0) {
                ElMessageBox.alert('请选择需要删除的元素!', '系统提示', {
                    confirmButtonText: '确定',
                    type: 'info'
                });
            } else {
                ElMessageBox.confirm('确定要删除所选元素吗?', '系统提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                })
                    .then(() => {
                        const ids = selectRows.map((row) => row.id);
                        removeBatch(ids);
                    })
                    .catch(() => 'cancel');
            }
        };

        /**
         * 添加或更新
         *
         * @param {any} data 添加或更新数据
         */
        const addOrUpdate = async (data) => {
            const action = !data.id ? service.add : service.update;
            return await action(data)
                .then((res) => {
                    ElMessage({
                        message: `${!data.id ? '添加' : '更新'}成功！`,
                        type: 'success'
                    });
                    // 刷新列表
                    refresh({ page: 1 });
                    return true;
                })
                .catch((error) => false);
        };

        /**
         * 删除
         *
         * @param {string} id ID
         */
        const remove = async (id) => {
            await service
                .remove(id)
                .then((res) => {
                    ElMessage({
                        message: '删除成功!',
                        type: 'success'
                    });
                    // 刷新列表
                    refresh({ removeSize: 1 });
                })
                .catch((error) => {});
        };

        /**
         * 批量删除
         *
         * @param {string} id ID
         */
        const removeBatch = async (ids) => {
            await service
                .removeBatch(ids)
                .then((res) => {
                    ElMessage({
                        message: '批量删除成功!',
                        type: 'success'
                    });
                    // 刷新列表
                    refresh({ removeSize: ids.length });
                })
                .catch((error) => {});
        };

        /**
         * 刷新数据
         *
         * @param {{number, number}|number} params 刷新参数(removeSize: 删除条数, page: 页码)
         */
        const refresh = (params) => {
            let removeSize = 0;
            let page = 1;
            if (params) {
                if (typeof params === 'number') {
                    page = params;
                } else if (typeof params === 'object') {
                    removeSize = params?.removeSize ?? removeSize;
                    page = params?.page ?? page;
                }
            }
            if (page) {
                model.page = page;
            } else if (removeSize > 0 && model.page > 1 && listData.length - removeSize < 1) {
                model.page -= 1;
            } else {
                model.page = 1;
            }
            list({}, model.page, model.pageSize);
        };

        /**
         * 获取列表数据
         *
         * @param {any} params 参数
         * @param {number} page 页码
         * @param {number} pageSize 每页数据条数
         */
        const list = async (params, page, pageSize) => {
            await service
                .list(params, page, pageSize)
                .then((res) => {
                    listData.splice(0, listData.length);
                    res.data.list.map((item) => listData.push(item));
                    model.total = res.data.total;
                })
                .catch((error) => {});
        };

        return {
            // 模型数据
            model,
            // 列表数据
            listData,
            // 获取当前选择行集
            getSelectRows,
            // 打开添加或更新对话框事件
            handleOpenAddOrUpdateDialog,
            // 搜索事件
            handleSearch,
            // 添加或更新事件
            handleAddOrUpdate,
            // 删除事件
            handleRemove,
            // 批量删除事件
            handleRemoveBatch,
            // 添加或删除
            addOrUpdate,
            // 删除
            remove,
            // 批量删除
            removeBatch,
            // 刷新列表
            refresh,
            // 获取列表数据
            list
        };
    }
};

export default managementViewUtils;
