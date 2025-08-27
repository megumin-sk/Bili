<template>
  <el-container class="main-container">
    <el-header class="main-header">
      <div class="logo-area" @click="goToHome" style="cursor: pointer;">
        <img alt="logo" src="/src/assets/ren.png" style="height: 60px; margin-right: 10px;"/>
        <span class="logo-text">BiliClone</span>
      </div>
      <el-menu
          :default-active="activeIndex"
          class="main-menu"
          mode="horizontal"
          background-color="#ffffff"
          text-color="#000"
          active-text-color="#fb7299"
          router
      >
        <el-menu-item index="/hot">热门视频</el-menu-item>
        <el-menu-item index="/bangumi">番剧</el-menu-item>
        <el-menu-item index="/mall">商城</el-menu-item>
      </el-menu>

      <div class="search-area">
        <el-input placeholder="搜点好东西..."></el-input>
      </div>

      <div class="user-area">
        <el-dropdown>
          <el-avatar v-if="isLoggedIn" :src="userInfo?.avatar_url || 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'" />
          <el-avatar v-else icon="el-icon-user-solid"></el-avatar>

          <template #dropdown>
            <el-dropdown-menu>
              <template v-if="isLoggedIn">
                <el-dropdown-item @click="personalCenter">个人中心</el-dropdown-item>
                <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
              </template>
              <template v-else>
                <el-dropdown-item @click="goToLogin">登录</el-dropdown-item>
                <el-dropdown-item @click="goToRegister">注册</el-dropdown-item>
              </template>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-header>

    <el-main class="main-content">
      <router-view/>
    </el-main>
  </el-container>
</template>

<script>
import { useUserStore } from "./store/user.js";
import { ElMessage } from 'element-plus';

export default {
  // 组件名称
  name: "App",

  // 响应式数据
  data() {
    return {
      // activeIndex 用于导航菜单的高亮状态
      activeIndex: this.$route.path
    };
  },

  // 计算属性
  computed: {
    // 判断用户是否登录
    isLoggedIn() {
      // 在计算属性中调用 Pinia store
      return useUserStore().isLoggedIn;
    },
    // 获取用户信息
    userInfo() {
      return useUserStore().userInfo;
    }
  },

  // 监听器
  watch: {
    // 监听路由对象的变化
    '$route'(to, from) {
      // 当路由路径变化时，更新 activeIndex
      this.activeIndex = to.path;
    }
  },

  // 方法
  methods: {
    // 跳转到首页
    goToHome() {
      this.$router.push('/');
    },

    // 跳转到登录页
    goToLogin() {
      this.$router.push('/login');
    },

    // 跳转到注册页
    goToRegister() {
      this.$router.push('/register');
    },

    // 跳转到个人中心
    personalCenter() {
      console.log('跳转到个人中心，当前用户信息:', this.userInfo);
      this.$router.push('/personalCenter'); // 直接跳转
    },

    // 处理退出登录
    handleLogout() {
      // 调用 Pinia store中的action
      useUserStore().logout();
      // 清除 sessionStorage 中的 token
      sessionStorage.removeItem("token");
      ElMessage.success('已退出登录');

      // 如果当前不在首页，则退出后跳转回首页
      if (this.$route.path !== '/hot') {
        this.$router.push('/');
      }
    }
  }
}
</script>

<style>
/* 样式部分保持不变 */
body {
  margin: 0;
  font-family: -apple-system, BlinkMacSystemFont, "Helvetica Neue", "PingFang SC", "Microsoft YaHei", "Source Han Sans SC", "Noto Sans CJK SC", "WenQuanYi Micro Hei", sans-serif;
  background-color: #f4f4f4;
}
.main-container {
  height: 100vh;
}
.main-header {
  display: flex;
  align-items: center;
  background-color: #ffffff;
  color: #333;
  padding: 0 20px;
  height: 60px !important;
  border-bottom: 1px solid #e3e5e7;
}
.logo-area {
  display: flex;
  align-items: center;
}
.logo-text {
  font-size: 20px;
  font-weight: bold;
}
.main-menu {
  border-bottom: none !important;
  margin-left: 50px;
  height: 60px;
}
.el-menu--horizontal > .el-menu-item {
  height: 60px;
  line-height: 60px;
  font-size: 15px;
}
.search-area {
  flex-grow: 1;
  max-width: 400px;
  margin: 0 20px 0 50px;
}
.user-area {
  display: flex;
  align-items: center;
  margin-left: auto; /* 确保用户区在最右侧 */
}
.user-area .el-dropdown {
  cursor: pointer;
}
.main-content {
  padding: 20px;
}
</style>