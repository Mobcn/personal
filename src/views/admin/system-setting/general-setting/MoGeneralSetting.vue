<template>
    <div class="mo-general-setting">
        <el-card class="mo-general-setting__box-card">
            <template #header>
                <div class="mo-general-setting__card-header">
                    <span>常规设置</span>
                    <el-button type="primary" @click="handleSave">保存</el-button>
                </div>
            </template>
            <el-form :model="form" ref="formComponent" :rules="rules" label-width="140px">
                <el-form-item label="网站用户名" prop="user">
                    <el-input v-model="form.user" placeholder="网站用户名" />
                </el-form-item>
                <el-form-item label="网站用户头像">
                    <el-input v-model="form.avatarImageURL" placeholder="网站用户头像, 默认为/public/img/avatar.png" />
                </el-form-item>
                <el-form-item label="前台背景图片">
                    <el-input
                        v-model="form.backgroundImageURL"
                        placeholder="前台背景图片, 默认为/public/img/background.png"
                    />
                </el-form-item>
                <el-form-item label="关于页面封面">
                    <el-input
                        v-model="form.aboutCoverImageURL"
                        placeholder="关于页面封面, 默认为/public/img/about_cover.png"
                    />
                </el-form-item>
                <el-form-item label="文章默认封面">
                    <el-input
                        v-model="form.defaultCoverImageURL"
                        placeholder="文章默认封面, 默认为/public/img/default_cover.png"
                    />
                </el-form-item>
                <el-form-item label="404页面图片">
                    <el-input v-model="form._404ImageURL" placeholder="404页面图片, 默认为/public/img/404.png" />
                </el-form-item>
                <el-form-item label="网页标题">
                    <el-input class="mo-general-setting__input" v-model="form.title.home" placeholder="前台网页标题">
                        <template #prepend>前台</template>
                    </el-input>
                    <el-input class="mo-general-setting__input" v-model="form.title.admin" placeholder="后台网页标题">
                        <template #prepend>后台</template>
                    </el-input>
                    <el-input
                        class="mo-general-setting__input mo-general-setting__input--last"
                        v-model="form.title._404"
                        placeholder="错误网页标题"
                    >
                        <template #prepend>错误</template>
                    </el-input>
                </el-form-item>
                <el-form-item label="分页数据条数">
                    <label class="el-form-item__label">前台</label>
                    <span class="mo-general-setting__selector">
                        <el-select v-model="form.pageSize.home">
                            <el-option v-for="index of 100" :key="index" :label="index" :value="index" />
                        </el-select>
                    </span>
                    <label class="el-form-item__label">后台</label>
                    <span class="mo-general-setting__selector mo-general-setting__selector--last">
                        <el-select v-model="form.pageSize.admin">
                            <el-option v-for="index of 100" :key="index" :label="index" :value="index" />
                        </el-select>
                    </span>
                </el-form-item>
                <el-form-item label="后台管理入口">
                    <el-input v-model="form.adminEntrance" placeholder="后台管理入口, 默认为admin" />
                </el-form-item>
                <el-form-item label="备案配置">
                    <el-input class="mo-general-setting__input" v-model="form.beian.icp" placeholder="ICP备案号">
                        <template #prepend><span class="mo-general-setting__beian-label">ICP备案号</span></template>
                    </el-input>
                    <el-input class="mo-general-setting__input" v-model="form.beian.police" placeholder="公安备案号">
                        <template #prepend><span class="mo-general-setting__beian-label">公安备案号</span></template>
                    </el-input>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import settingService from '@/api/setting-service.mjs.js';
import storage from '@/utils/storage.mjs.js';

// 表单数据组件
const formComponent = ref();
// 表单数据对象
const form = reactive({
    user: '',
    backgroundImageURL: '',
    avatarImageURL: '',
    aboutCoverImageURL: '',
    defaultCoverImageURL: '',
    _404ImageURL: '',
    title: {
        home: '',
        admin: '',
        _404: ''
    },
    pageSize: {
        home: 10,
        admin: 20
    },
    adminEntrance: '',
    beian: {
        icp: '',
        police: ''
    }
});

// 校验规则
const rules = {
    user: [{ required: true, message: '请输入网站用户名', trigger: 'blur' }]
};

/**
 * 初始化
 */
const init = () => {
    settingService
        .map([
            // 网站用户名
            'user',
            // 后台管理入口
            'adminEntrance',
            // 网站用户头像
            'avatarImageURL',
            // 前台背景图片
            'backgroundImageURL',
            // 关于页面封面
            'aboutCoverImageURL',
            // 文章默认封面
            'defaultCoverImageURL',
            // 404页面图片
            '_404ImageURL',
            // 网页标题
            'title',
            // 分页列表每页显示数据条数
            'pageSize',
            // 备案配置
            'beian'
        ])
        .then((res) => {
            form.user = res.data['user'];
            form.avatarImageURL = res.data['avatarImageURL'];
            form.backgroundImageURL = res.data['backgroundImageURL'];
            form.aboutCoverImageURL = res.data['aboutCoverImageURL'];
            form.defaultCoverImageURL = res.data['defaultCoverImageURL'];
            form._404ImageURL = res.data['_404ImageURL'];
            form.title = Object.assign({}, form.title, JSON.parse(res.data['title']));
            form.pageSize = Object.assign({}, form.pageSize, JSON.parse(res.data['pageSize']));
            form.adminEntrance = res.data['adminEntrance'];
            form.beian = Object.assign({}, form.beian, JSON.parse(res.data['beian']));
        })
        .catch((error) => {});
};

/**
 * 保存事件
 */
const handleSave = () => {
    formComponent.value.validate((valid) => {
        if (valid) {
            const params = {
                user: form.user,
                avatarImageURL: form.avatarImageURL,
                backgroundImageURL: form.backgroundImageURL,
                defaultCoverImageURL: form.defaultCoverImageURL,
                _404ImageURL: form._404ImageURL,
                title: JSON.stringify(form.title),
                pageSize: JSON.stringify(form.pageSize),
                adminEntrance: form.adminEntrance,
                beian: JSON.stringify(form.beian)
            };
            settingService
                .update(params)
                .then((res) => {
                    ElMessage({ message: '保存成功!', type: 'success' });
                    // 更新相关设置
                    for (const key in params) {
                        storage.set(key, params[key]);
                    }
                })
                .catch((error) => {});
        }
    });
};

// 初始化操作
(() => {
    init();
})();
</script>

<style scoped>
.mo-general-setting {
    height: 100%;
}

.mo-general-setting__card-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.mo-general-setting__selector {
    margin-right: 12px;
    width: 130px;
}

.mo-general-setting__selector--last {
    margin-right: 0;
}

.mo-general-setting__box-card.el-card {
    width: 60%;
    min-width: 850px;
}

.mo-general-setting__input.el-input {
    min-width: 500px;
    margin-bottom: 10px;
}

.mo-general-setting__input--last.el-input {
    margin-bottom: 0;
}

.mo-general-setting__beian-label {
    width: 70px;
    text-align: center;
}
</style>
