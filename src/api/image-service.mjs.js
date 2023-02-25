import service from '@/utils/request.mjs.js';

const baseURL = service.defaults.baseURL;

/**
 * 图片接口地址
 */
export const imageApi = {
    /**
     * 上传图片文件
     */
    upload: `${baseURL}/image/upload/`
};

/**
 * 图片接口服务
 */
const imageService = {
    /**
     * 上传图片文件
     *
     * @param {File} file 图片文件
     * @returns Promise
     */
    upload: async (file) => {
        const formData = new FormData();
        formData.append('file', file);
        return service.post(imageApi.upload, formData, {
            'Content-Type': 'multipart/form-data'
        });
    }
};

export default imageService;
