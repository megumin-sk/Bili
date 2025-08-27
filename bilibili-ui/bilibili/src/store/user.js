import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
  // state: () => ({ ... }),
  state: () => {
    return {
      isLoggedIn: false,
      userInfo: null, // 存储用户信息
    };
  },
  actions: {
    // 登录成功后调用的 action
    loginSuccess(userInfo) {
      this.isLoggedIn = true;
      this.userInfo = userInfo;
    },

    // 退出登录
    logout() {
      this.isLoggedIn = false;
      this.userInfo = null;
    },
  },
});
