import {
  fileUrls, downloadZip, downloadPdf
} from "@/api/upload";
import { Base64 } from 'js-base64'

import Confirm from "@/utils/Confirm.js";
let baseURL = CONFIG.url + process.env.VUE_APP_BASE_API;


// 下载文件
export async function downloadFile(fileId, fileName) {
  try {
    const res = await fileUrls({fileIds:fileId});
    openWindow(res.data.downloadUrl,res.data.signature);

    // debugger
    // const link = document.createElement('a')
    // const body = document.querySelector('body')
    // link.style.display = 'none';
    // link.href = data.downloadUrl;
    // link.download = fileName?fileName:'123';
    // document.body.appendChild(link);
    // link.click();
    // body.removeChild(link);
    return true;
  } catch (error) {
    return false;
  }
}
// 转PDF格式下载文件
export async function downloadZIP(fileIds, type, zipName) {
  try {
    // debugger
    if (Array.isArray(fileIds)) {
      fileIds = fileIds.join(",");
    }
    const res = await fileUrls({ fileIds: fileIds, type: type });
    let downloadUrl = res.data.downloadZipUrl;
    if (zipName) {
      downloadUrl += "&zipName=" + zipName;
    }
    openWindow(downloadUrl, res.data.signature);
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
}

// 预览文件
export async function previewFile(fileId) {
  try {
    const res = await fileUrls({ fileIds: fileId,type:2});
    let previewUrl=res.data.previewUrl + encodeURIComponent(Base64.encode(baseURL+res.data.downloadUrl))
    openWindow(previewUrl,res.data.signature);
    return true;
  } catch (error) {
    return false;
  }
}

// 批量下载
export function downloadZipFile(fileIds,zipName) {
  Confirm({ title: "批量下载", message: "确认删除该文件吗？", show: true }).then(
    (res) => {
      console.log(res);
      if (res == 0) {
        downloadZIP(fileIds, 3, zipName);
      }else{
        downloadZIP(fileIds, 4, zipName);
      }
    }
  );
}

function openWindow(url,signature) {
  if(url.indexOf('http')==-1){
    url=baseURL+url
  }
  window.open(url+"&signature="+signature);
}
