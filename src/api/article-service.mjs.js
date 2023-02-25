import service from '@/utils/request.mjs.js';
import storage from '@/utils/storage.mjs.js';

const baseURL = service.defaults.baseURL;
const pageSize = storage.getObject('pageSize')['admin'];

/**
 * 文章接口地址
 */
export const articleApi = {
    /**
     * 获取文章列表
     */
    list: `${baseURL}/article/list/`,

    /**
     * 获取文章信息
     */
    info: `${baseURL}/article/info/`,

    /**
     * 添加文章
     */
    add: `${baseURL}/article/add/`,

    /**
     * 更新文章
     */
    update: `${baseURL}/article/update/`,

    /**
     * 删除文章
     */
    remove: `${baseURL}/article/remove/`,

    /**
     * 批量删除文章
     */
    removeBatch: `${baseURL}/article/batch/remove/`
};

/**
 * 文章接口服务
 */
const articleService = {
    /**
     * 获取文章列表
     *
     * @param {any} params 参数
     * @param {number} page 页码
     * @param {number} size 每页数据条数
     * @returns Promise
     */
    list: async (params, page = 1, size = pageSize) =>
        service.post(`${articleApi.list}?page=${page}&size=${size}`, params),

    /**
     * 获取文章信息
     *
     * @param {string} id 文章ID
     * @returns Promise
     */
    info: async (id) => service.get(articleApi.info + id),

    /**
     * 添加文章
     *
     * @param {any} params 参数
     * @returns Promise
     */
    add: async (params) => service.put(articleApi.add, params),

    /**
     * 更新文章
     *
     * @param {any} params 参数
     * @returns Promise
     */
    update: async (params) => service.post(articleApi.update, params),

    /**
     * 删除文章
     *
     * @param {string} id 文章ID
     * @returns Promise
     */
    remove: async (id) => service.delete(articleApi.remove + id),

    /**
     * 批量删除文章
     *
     * @param {Array<string>} ids 文章ID数组
     * @returns Promise
     */
    removeBatch: async (ids) => service.delete(articleApi.removeBatch, { data: ids })
};

export default articleService;
