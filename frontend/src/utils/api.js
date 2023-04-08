import axios from 'axios'
import {Message} from "element-ui";
import router from '../router'
import Vue from 'vue'

axios.defaults.baseURL = '/'
Vue.prototype.axios = axios

let base = '';

//传送json格式的post请求
export const postRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params
  })
}
//传递json的put请求
export const putRequest = (url, params) => {
  return axios({
    method: 'put',
    url: `${base}${url}`,
    data: params
  })
}
//传递json的get请求
export const getRequest = (url, params) => {
  return axios({
    method: 'get',
    url: `${base}${url}`,
    data: params
  })
}
//传递json的delete请求
export const deleteRequest = (url, params) => {
  return axios({
    method: 'delete',
    url: `${base}${url}`,
    data: params
  })
}
