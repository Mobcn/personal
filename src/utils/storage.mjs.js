/**
 * 封装storage
 */
export default {
    /**
     * 存储数据
     *
     * @param {string} key 键
     * @param {any} value 值
     */
    set: (key, value) => {
        if (typeof value === 'string') {
            localStorage.setItem(key, value);
        } else {
            localStorage.setItem(key, JSON.stringify(value));
        }
    },

    /**
     * 获取键对应数据
     *
     * @param {string} key 键
     * @returns 值
     */
    get: (key) => localStorage.getItem(key),

    /**
     * 获取键对应对象
     *
     * @param {string} key
     * @returns 值
     */
    getObject: (key) => {
        const value = localStorage.getItem(key);
        if (value && value !== 'undefined' && value !== 'null') {
            return JSON.parse(value);
        }
        return value;
    },

    /**
     * 删除键对应数据
     *
     * @param {string} key
     * @returns
     */
    remove: (key) => localStorage.removeItem(key)
};
