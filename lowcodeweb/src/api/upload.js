import request from '@/utils/request'
import {downloadUrl,downloadUrlPdf} from '@/utils/request'


// 获取上传
export function createMultipartUpload(query,data) {
  return request({
    url: '/file/minio/createMultipartUpload',
    method: 'post',
    params: query,
    data: data

  })
}


// 完成上传
export function completeMultipartUpload(query,data) {
  return request({
    url: '/file/minio/completeMultipartUpload',
    method: 'post',
    params: query,
    data: data

  })
}



// 获取文件下载和预览地址 query参数， fileId:文件id, fileUrl:文件地址  一般传id {fileId:"42342",fileUrl:""}
export function fileUrls(query) {
  return request({
    url: '/file/minio/urls',
    method: 'post',
    data:query,
    params: query
  })
}

// 批量下载 fileIds
export function downloadZip() {
  return downloadUrl()
}
// 批量下载 fileIds
export function downloadPdf() {
  return downloadUrlPdf()
}
