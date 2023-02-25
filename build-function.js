import { loadModule } from 'vue3-sfc-loader';

const { importToESM } = await fetch('./import.js').then(async (res) => eval(await res.text()));

let getActualPath;

/**
 * 构建获取实际路径函数
 *
 * @param {string} mainPath 入口函数路径
 */
export const buildGetActualPathFunction = (mainPath) => {
    const isDev = new URL(mainPath).pathname.endsWith('main.js');
    getActualPath = (path) => {
        path = importToESM[path] || path;
        if (path.startsWith('static/')) {
            path = new URL(path.replace('static/', isDev ? '../public/' : '../'), mainPath).pathname;
        } else if (path.startsWith('@/')) {
            path = new URL(path.replace('@/', './'), mainPath).pathname;
        } else if (path === 'root') {
            path = new URL('../index.html', mainPath).pathname;
        }
        return path;
    };
    return getActualPath;
};

/**
 * 构建获取动态组件函数
 *
 * @param {any} modules 引入模块
 */
export const buildGetAsyncComponentFunction = (modules) => {
    if (!getActualPath) {
        throw new Error('未构建获取实际路径函数（buildGetActualPathFunction）！');
    }

    // 模块缓存（定义后可以通过 import ... from [模块名] 方式导入）
    const moduleCache = modules || {};

    /**
     * 远程获取文件
     *
     * @param {string} path 请求路径
     */
    const getFile = async (path) => {
        let isModuleJS = false;
        let getContentData;
        if (importToESM[path] && !path.endsWith('.css')) {
            getContentData = async () => Object.assign({ __esModule: true }, await import(path));
            isModuleJS = true;
        } else {
            const res = await fetch(getActualPath(path));
            if (!res.ok) {
                throw Object.assign(new Error(res.statusText + ' ' + path), { res });
            }
            getContentData = (asBinary) => (asBinary ? res.arrayBuffer() : res.text());
            isModuleJS = path.endsWith('.mjs.js');
        }
        return isModuleJS ? { getContentData, type: '.mjs' } : { getContentData };
    };

    /**
     * 添加样式
     *
     * @param {string} textContent CSS样式内容
     */
    const addStyle = (textContent) => {
        const style = Object.assign(document.createElement('style'), { textContent });
        const ref = document.head.getElementsByTagName('style')[0] || null;
        document.head.insertBefore(style, ref);
    };

    /**
     * 文件处理事件
     *
     * @param {string} type 文件类型
     * @param {Function} getContentData 获取文件内容函数
     * @param {string} path 文件地址
     * @param {any} options 配置项
     */
    const handleModule = async (type, getContentData, path, options) => {
        switch (type) {
            // 外部ESM处理
            case '.mjs':
                if (importToESM[path]) {
                    return await getContentData();
                }
                break;
            // CSS样式处理
            case '.css':
                options.addStyle(await getContentData(false));
                return null;
            // JSON处理
            case '.json':
                return JSON.parse(await getContentData(false));
            // 图片处理
            case '.png':
            case '.jpg':
            case '.jpeg':
            case '.gif':
                return path;
        }
    };

    return (path) => loadModule(path, { moduleCache, getFile, addStyle, handleModule });
};
