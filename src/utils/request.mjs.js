import { ElMessage, ElMessageBox } from 'element-plus';
import axios from 'axios';
import storage from '@/utils/storage.mjs.js';
import globaleProperties from '@/utils/globale-properties.mjs.js';

// 后台管理入口
const adminEntrance = storage.get('adminEntrance') || 'admin';

// 配置项
const options = {
    // 是否开启mock
    enableMock: true,
    // mock基础路径
    mockBaseURL: new URL(window.location.href).origin + '/static/data',
    // mock替换请求数组
    mockMatcher: ['/setting/public/', '/article/list/', '/article/info/[0-9]+'],
    // 请求基础路径
    baseURL: 'https://api.mobingc.cn',
    // 请求超时时间（毫秒）
    timeout: 30000,
    // 请求header参数配置
    headers: {
        'Content-Type': 'application/json;charset=UTF-8'
    }
};

// 创建一个axios实例
const service = axios.create({
    baseURL: options.baseURL,
    timeout: options.timeout,
    headers: options.headers
});

// 请求拦截器
service.interceptors.request.use(
    async (config) => {
        const token = storage.get('token');
        if (token) {
            // 配置登录认证token
            config.headers['Authorization'] = 'Bearer ' + token;
        }
        const url = new URL(config.url);
        // 替换mock数据请求
        if (
            options.enableMock &&
            !new URL(window.location.href).searchParams.has(adminEntrance) &&
            options.mockMatcher.find((regExp) => new RegExp(regExp).test(url.pathname))
        ) {
            if (!options.mockBaseActualURL) {
                const getActualPath = await globaleProperties.get('getActualPath');
                const mockBaseURL = new URL(options.mockBaseURL);
                options.mockBaseActualURL = mockBaseURL.href.replace(
                    mockBaseURL.pathname,
                    getActualPath(mockBaseURL.pathname.substring(1))
                );
            }
            const mockUrl = new URL(config.url.replace(url.origin, options.mockBaseActualURL));
            config.url = mockUrl.href.replace(mockUrl.pathname, mockUrl.pathname + '.json').replace('/.json', '.json');
            config.method = 'get';
        }
        return config;
    },
    (error) => {
        console.log(error);
        ElMessage({
            message: error.message,
            type: 'error',
            duration: 5 * 1000
        });
        return Promise.reject(error);
    }
);

// 响应拦截器
service.interceptors.response.use(
    (response) => {
        const res = response.data;
        console.log(res.message);
        if (res.code !== 0) {
            ElMessageBox.alert(`${res.message}: ${res.data.errorMessage}`, '系统提示', {
                confirmButtonText: '确定',
                type: 'error'
            });
            return Promise.reject(new Error(`${res.message}: ${res.data.errorMessage}`));
        }
        const responseURL = new URL(response.request.responseURL);
        // mock数据分页处理
        if (
            options.enableMock &&
            responseURL.href.startsWith(options.mockBaseActualURL) &&
            responseURL.searchParams.has('size')
        ) {
            const page = responseURL.searchParams.has('page') ? responseURL.searchParams.get('page') : 1;
            const size = responseURL.searchParams.get('size');
            res.data.list = res.data.list.slice((page - 1) * size, page * size);
        }
        return res;
    },
    (error) => {
        console.log(error);
        if (error.response.status === 401) {
            ElMessageBox.confirm('登录状态已失效, 是否转到登录页面?', '系统提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
                .then(() => {
                    storage.remove('token');
                    window.location.href = `/?${storage.get('adminEntrance')}`;
                })
                .catch((error) => {
                    console.log(error);
                });
        } else {
            ElMessageBox.alert(error.message, '系统提示', { confirmButtonText: '确定', type: 'error' });
        }
        return Promise.reject(error);
    }
);

export default service;
