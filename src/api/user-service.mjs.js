import service from '@/utils/request.mjs.js';

const baseURL = service.defaults.baseURL;

/**
 * 用户接口地址
 */
export const userApi = {
    /**
     * 登录
     */
    login: `${baseURL}/user/login/`
};

/**
 * 用户接口服务
 */
const userService = {
    /**
     * 登录
     *
     * @param {any} params 登录参数
     * @returns Promise
     */
    login: async (params) => service.post(userApi.login, params)
};

export default userService;
