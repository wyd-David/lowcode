// 1. vue package.json 引入crypto-js的依赖："crypto-js": "^4.0.0",
// 导入
import CryptoJS from "crypto-js";
// 这两个必须是16位前后端一致
const key = CryptoJS.enc.Utf8.parse("q6Jxw7XJ8IrDvdsK");
const iv = CryptoJS.enc.Utf8.parse("q6Jxw7XJ8IrDvdsV");
// 加密
const encrypt = (pass) => {
  const password = CryptoJS.enc.Utf8.parse(pass);
  return CryptoJS.AES.encrypt(password, key, {
    mode: CryptoJS.mode.CBC,
    iv: iv,
    padding: CryptoJS.pad.Pkcs7,
  }).toString();
};
// 解密
const decrypt = (pass) => {
  return CryptoJS.AES.decrypt(pass, key, {
    mode: CryptoJS.mode.CBC,
    iv: iv,
    padding: CryptoJS.pad.Pkcs7,
  }).toString(CryptoJS.enc.Utf8);
};

export { encrypt, decrypt };
