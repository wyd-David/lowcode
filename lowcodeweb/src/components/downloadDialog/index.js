import fullNameVue from './index.vue'

const FullToast = {};

FullToast.install = function (Vue, option) {
  const FullNameInstance = Vue.extend(fullNameVue);
  let name;
  const initInstance = () => {
    name = new FullNameInstance();
    let nameSpan = name.$mount().$el;
    document.body.appendChild(nameSpan);
  }
  Vue.prototype.$uToast = {
    showToast(option){
      initInstance();
      if(typeof option === 'string'){
        name.firstName = option;
      }else if(typeof option === 'object'){
        Object.assign(name, option);
      }
      return initInstance;
    }
  };
}

export default FullToast;