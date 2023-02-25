import service from '@/utils/request.mjs.js';
import storage from '@/utils/storage.mjs.js';

const baseURL = service.defaults.baseURL;
const pageSize = storage.getObject('pageSize')['admin'];

/**
 * 标签接口地址
 */
export const tagApi = {
    /**
     * 获取标签列表
     */
    list: `${baseURL}/tag/list/`,

    /**
     * 获取标签简要信息列表
     */
    simpleList: `${baseURL}/tag/simple/list/`,

    /**
     * 获取标签信息
     */
    info: `${baseURL}/tag/info/`,

    /**
     * 添加标签
     */
    add: `${baseURL}/tag/add/`,

    /**
     * 更新标签
     */
    update: `${baseURL}/tag/update/`,

    /**
     * 删除标签
     */
    remove: `${baseURL}/tag/remove/`,

    /**
     * 批量删除标签
     */
    removeBatch: `${baseURL}/tag/batch/remove/`,

    /**
     * 修改标签状态
     */
    status: `${baseURL}/tag/status/`
};

/**
 * 标签接口服务
 */
const tagService = {
    /**
     * 获取标签列表
     *
     * @param {any} params 参数
     * @param {number} page 页码
     * @param {number} size 每页数据条数
     * @returns Promise
     */
    list: async (params, page = 1, size = pageSize) => service.post(`${tagApi.list}?page=${page}&size=${size}`, params),

    /**
     * 获取标签简要信息列表
     *
     * @returns Promise
     */
    simpleList: async () => service.get(tagApi.simpleList),

    /**
     * 获取标签信息
     *
     * @param {string} id 标签ID
     * @returns Promise
     */
    info: async (id) => service.get(tagApi.info + id),

    /**
     * 添加标签
     *
     * @param {any} params 参数
     * @returns Promise
     */
    add: async (params) => service.put(tagApi.add, params),

    /**
     * 更新标签
     *
     * @param {any} params 参数
     * @returns Promise
     */
    update: async (params) => service.post(tagApi.update, params),

    /**
     * 删除标签
     *
     * @param {string} id 标签ID
     * @returns Promise
     */
    remove: async (id) => service.delete(tagApi.remove + id),

    /**
     * 批量删除标签
     *
     * @param {Array<string>} ids 标签ID数组
     * @returns Promise
     */
    removeBatch: async (ids) => service.delete(tagApi.removeBatch, { data: ids }),

    /**
     * 修改标签状态
     *
     * @param {string} id 标签ID
     * @returns
     */
    status: async (id) => service.post(tagApi.status + id)
};

export default tagService;
