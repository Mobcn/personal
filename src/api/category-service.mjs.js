import service from '@/utils/request.mjs.js';
import storage from '@/utils/storage.mjs.js';

const baseURL = service.defaults.baseURL;
const pageSize = storage.getObject('pageSize')['admin'];

/**
 * 分类接口地址
 */
export const categoryApi = {
    /**
     * 获取分类列表
     */
    list: `${baseURL}/category/list/`,

    /**
     * 获取分类简要信息列表
     */
    simpleList: `${baseURL}/category/simple/list/`,

    /**
     * 获取分类信息
     */
    info: `${baseURL}/category/info/`,

    /**
     * 添加分类
     */
    add: `${baseURL}/category/add/`,

    /**
     * 更新分类
     */
    update: `${baseURL}/category/update/`,

    /**
     * 删除分类
     */
    remove: `${baseURL}/category/remove/`,

    /**
     * 批量删除分类
     */
    removeBatch: `${baseURL}/category/batch/remove/`,

    /**
     * 修改分类状态
     */
    status: `${baseURL}/category/status/`
};

/**
 * 分类接口服务
 */
const categoryService = {
    /**
     * 获取分类列表
     *
     * @param {any} params 参数
     * @param {number} page 页码
     * @param {number} size 每页数据条数
     * @returns Promise
     */
    list: async (params, page = 1, size = pageSize) =>
        service.post(`${categoryApi.list}?page=${page}&size=${size}`, params),

    /**
     * 获取分类简要信息列表
     *
     * @returns Promise
     */
    simpleList: async () => service.get(categoryApi.simpleList),

    /**
     * 获取分类信息
     *
     * @param {string} id 分类ID
     * @returns Promise
     */
    info: async (id) => service.get(categoryApi.info + id),

    /**
     * 添加分类
     *
     * @param {any} params 参数
     * @returns Promise
     */
    add: async (params) => service.put(categoryApi.add, params),

    /**
     * 更新分类
     *
     * @param {any} params 参数
     * @returns Promise
     */
    update: async (params) => service.post(categoryApi.update, params),

    /**
     * 删除分类
     *
     * @param {string} id 分类ID
     * @returns Promise
     */
    remove: async (id) => service.delete(categoryApi.remove + id),

    /**
     * 批量删除分类
     *
     * @param {Array<string>} ids 分类ID数组
     * @returns Promise
     */
    removeBatch: async (ids) => service.delete(categoryApi.removeBatch, { data: ids }),

    /**
     * 修改分类状态
     *
     * @param {string} id 分类ID
     * @returns
     */
    status: async (id) => service.post(categoryApi.status + id)
};

export default categoryService;
