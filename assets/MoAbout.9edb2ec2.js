import{s as _}from"./index.5b561da9.js";import{E as l}from"./style.38dcc1e9.js";import{_ as n}from"./_plugin-vue_export-helper.cdc0426e.js";const r=t=>(Vue.pushScopeId("data-v-1bd6cea1"),t=t(),Vue.popScopeId(),t),d={class:"mo-about"},m=r(()=>Vue.createElementVNode("div",{class:"mo-about__head"},[Vue.createElementVNode("div",{class:"mo-about__title"},[Vue.createElementVNode("span",null,"\u5173\u4E8E")]),Vue.createElementVNode("div",{class:"mo-about__wave"})],-1)),V={class:"mo-about__body"},p={__name:"MoAbout",setup(t){Vue.useCssVars(e=>({"6a52ae5c":s.coverURL}));const a=Vue.getCurrentInstance().proxy.$getActualPath,o=Vue.ref(""),s=Vue.reactive({coverURL:`url('${_.get("aboutCoverImageURL")||a("static/img/about_cover.jpg")}')`});return fetch(a("static/doc/about.md")).then(e=>e.text()).then(e=>o.value=e).catch(e=>console.log(e)),(e,c)=>(Vue.openBlock(),Vue.createElementBlock("div",d,[m,Vue.createElementVNode("div",V,[Vue.createVNode(Vue.unref(l),{class:"mo-about__content",modelValue:o.value,"onUpdate:modelValue":c[0]||(c[0]=u=>o.value=u),previewOnly:!0},null,8,["modelValue"])])]))}},h=n(p,[["__scopeId","data-v-1bd6cea1"]]);export{h as default};