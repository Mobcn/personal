import{a as t,s as i}from"./index.5b561da9.js";const a=t.defaults.baseURL,r=i.getObject("pageSize").admin,s={list:`${a}/category/list/`,simpleList:`${a}/category/simple/list/`,info:`${a}/category/info/`,add:`${a}/category/add/`,update:`${a}/category/update/`,remove:`${a}/category/remove/`,removeBatch:`${a}/category/batch/remove/`,status:`${a}/category/status/`},y={list:async(e,o=1,c=r)=>t.post(`${s.list}?page=${o}&size=${c}`,e),simpleList:async()=>t.get(s.simpleList),info:async e=>t.get(s.info+e),add:async e=>t.put(s.add,e),update:async e=>t.post(s.update,e),remove:async e=>t.delete(s.remove+e),removeBatch:async e=>t.delete(s.removeBatch,{data:e}),status:async e=>t.post(s.status+e)};export{y as c};
