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

// 全局拦截link标签请求
const linkNativeSet = Object.getOwnPropertyDescriptor(HTMLLinkElement.prototype, 'src').set;
Object.defineProperty(HTMLLinkElement.prototype, 'src', {
    set: function (url) {
        // 替换script标签的请求地址
        if (url.indexOf('cdnjs.cloudflare.com/ajax/libs') !== -1) {
            url = url.replace('cdnjs.cloudflare.com/ajax/libs', 'cdn.staticfile.org');
        }
        linkNativeSet.call(this, url);
    }
});
