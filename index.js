fetch('./import.js').then(async (res) => {
    const { importToTag, importToESM } = eval(await res.text());

    const head = document.getElementsByTagName('head')[0];

    // 标签导入css
    for (const url of importToTag.css) {
        const link = document.createElement('link');
        link.rel = 'stylesheet';
        link.href = url;
        head.appendChild(link);
    }

    // 标签导入js
    for (const url of importToTag.js) {
        const script = document.createElement('script');
        script.type = 'text/javascript';
        script.async = false;
        script.src = url;
        head.appendChild(script);
    }

    const body = document.getElementsByTagName('body')[0];

    // 设置ESM导入映射
    const importMapScript = document.createElement('script');
    importMapScript.type = 'importmap';
    importMapScript.textContent = JSON.stringify({ imports: importToESM });
    body.appendChild(importMapScript);

    // 加载入口文件
    const mainScript = document.createElement('script');
    mainScript.type = 'module';
    mainScript.src = './src/main.js';
    body.appendChild(mainScript);

    // 全局拦截img标签请求
    const imageNativeSet = Object.getOwnPropertyDescriptor(Image.prototype, 'src').set;
    Object.defineProperty(Image.prototype, 'src', {
        set: async function (url) {
            try {
                const pathname = new URL(url).pathname;
                if (pathname.startsWith('/image/download/')) {
                    // 提前缓存图片，防止跳转跨域报错
                    await fetch(url, { mode: 'no-cors' });
                }
            } catch (error) {}
            imageNativeSet.call(this, url);
        }
    });

    // 全局拦截script标签请求
    const scriptNativeSet = Object.getOwnPropertyDescriptor(HTMLScriptElement.prototype, 'src').set;
    Object.defineProperty(HTMLScriptElement.prototype, 'src', {
        set: function (url) {
            // 替换script标签的请求地址
            if (url.indexOf('cdnjs.cloudflare.com/ajax/libs') !== -1) {
                url = url.replace('cdnjs.cloudflare.com/ajax/libs', 'cdn.staticfile.org');
            }
            scriptNativeSet.call(this, url);
        }
    });
});
