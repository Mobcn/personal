import k from"./MoHeadMenu.d8f7c7d6.js";import T from"./MoLeftMenu.1cfcefbd.js";import{s as w}from"./index.5b561da9.js";import{_ as M}from"./_plugin-vue_export-helper.cdc0426e.js";import"./MoIcon.a8ae075d.js";import"./store.mod.8d7d9c48.js";const B={class:"mo-panel"},I={class:"mo-panel__left-menu"},E={class:"mo-panel__menu-body"},L={class:"mo-panel__menu-divider"},P=["title","innerHTML"],O={__name:"MoPanel",emits:["change-page"],setup(S,{emit:_}){const V=Vue.getCurrentInstance().proxy.$getActualPath,v=Vue.getCurrentInstance().proxy.$getAsyncComponent,r=Vue.ref(!1),u=Vue.ref(""),m=Vue.ref([]),f=Vue.computed(()=>r.value?"73px":"209px"),b=Vue.computed(()=>r.value?"\u5C55\u5F00":"\u6536\u8D77"),h=Vue.computed(()=>r.value?"&raquo;":"&laquo;"),d=n=>{const{title:e,componentName:o,params:t,multipleId:a}=n,l=m.value,c=a?e+String(a):e;v(V(`@/views/admin/${o}.vue`)).then(p=>{g(l,{componentId:c})||l.push({title:e,componentId:c,component:Vue.markRaw(p),params:t!=null?t:{},notSaved:!1}),u.value=c})},C=async n=>{const e=x(n);let o=u.value;if(e.notSaved){if(u.value=e.componentId,await ElementPlus.ElMessageBox.confirm("\u5B58\u5728\u4FEE\u6539\u5185\u5BB9\u672A\u4FDD\u5B58\uFF0C\u786E\u5B9A\u8981\u5173\u95ED?","\u7CFB\u7EDF\u63D0\u793A",{confirmButtonText:"\u786E\u5B9A",cancelButtonText:"\u53D6\u6D88",type:"warning"}).catch(()=>"cancel")==="cancel")return;o!==n&&(u.value=o)}const t=m.value;o===n&&t.forEach((a,l)=>{var c;if(a.componentId===n){const p=(c=t[l+1])!=null?c:t[l-1];p&&(o=p.componentId)}}),u.value=o,m.value=t.filter(a=>a.componentId!==n)},x=n=>{for(const e of m.value)if(e.componentId===n)return e;return null},g=(n,e)=>{const o=Object.keys(e);return n.findIndex(a=>{for(const l of o)if(a[l]!==e[l])return!1;return!0})>-1},y=()=>{w.remove("token"),_("change-page",{componentName:"MoLogin"})};return d({title:"\u9996\u9875",componentName:"main/MoMain"}),(n,e)=>{const o=Vue.resolveComponent("el-header"),t=Vue.resolveComponent("el-aside"),a=Vue.resolveComponent("el-scrollbar"),l=Vue.resolveComponent("el-tab-pane"),c=Vue.resolveComponent("el-tabs"),p=Vue.resolveComponent("el-main"),i=Vue.resolveComponent("el-container");return Vue.openBlock(),Vue.createElementBlock("div",B,[Vue.createVNode(i,{class:"mo-panel__container"},{default:Vue.withCtx(()=>[Vue.createVNode(o,{class:"mo-panel__header"},{default:Vue.withCtx(()=>[Vue.createVNode(k,{onLoginout:y})]),_:1}),Vue.createVNode(i,null,{default:Vue.withCtx(()=>[Vue.createVNode(t,{class:"mo-panel__aside",width:Vue.unref(f)},{default:Vue.withCtx(()=>[Vue.createElementVNode("div",I,[Vue.createElementVNode("span",E,[Vue.createVNode(T,{isCollapse:r.value,onOpenTab:d},null,8,["isCollapse"])]),Vue.createElementVNode("span",L,[Vue.createElementVNode("span",{class:"mo-panel__divider-icon",title:Vue.unref(b),innerHTML:Vue.unref(h),onClick:e[0]||(e[0]=s=>r.value=!r.value)},null,8,P)])])]),_:1},8,["width"]),Vue.createVNode(p,{class:"mo-panel__main"},{default:Vue.withCtx(()=>[Vue.createVNode(c,{class:"mo-panel__tabs",modelValue:u.value,"onUpdate:modelValue":e[1]||(e[1]=s=>u.value=s),type:"border-card",closable:"",onTabRemove:C},{default:Vue.withCtx(()=>[(Vue.openBlock(!0),Vue.createElementBlock(Vue.Fragment,null,Vue.renderList(m.value,s=>(Vue.openBlock(),Vue.createBlock(l,{key:s.componentId,label:s.title,name:s.componentId},{default:Vue.withCtx(()=>[Vue.createVNode(a,{class:"mo-panel__scrollbar"},{default:Vue.withCtx(()=>[(Vue.openBlock(),Vue.createBlock(Vue.resolveDynamicComponent(s.component),{"not-saved":s.notSaved,"onUpdate:not-saved":N=>s.notSaved=N,params:s.params,onOpenTab:d},null,40,["not-saved","onUpdate:not-saved","params"]))]),_:2},1024)]),_:2},1032,["label","name"]))),128))]),_:1},8,["modelValue"])]),_:1})]),_:1})]),_:1})])}}},j=M(O,[["__scopeId","data-v-c5663af6"]]);export{j as default};