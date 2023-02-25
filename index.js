// index.html备案隐藏
const beianStyle = Object.assign(document.createElement('style'), {
    textContent: '#beian { display: none; }'
});
const ref = document.head.getElementsByTagName('style')[0] || null;
document.head.insertBefore(beianStyle, ref);
