import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/fund/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/fund/user/info',
    method: 'get',//TODO   get
    params: { token }
  })
}

export function logout(token) {
  return request({
    url: '/fund/user/logout',
    method: 'post',
    params: { token }
  })
}
