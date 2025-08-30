<template>
  <el-container class="main-container">
    <el-header class="main-header">
      <div class="logo-area" @click="goToHome" style="cursor: pointer;">
        <img alt="logo" src="/src/static-resources/ren.png" style="height: 50px; margin-right: 10px;"/>
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
        <el-menu-item index="/hot">
          <el-icon><VideoCameraFilled /></el-icon>热门视频</el-menu-item>
        <el-menu-item index="/bangumi">
          <el-icon><Monitor /></el-icon>番剧</el-menu-item>
        <el-menu-item index="/mall">
          <el-icon><Goods /></el-icon>
          <span>商城</span>
        </el-menu-item>
        <el-menu-item index="/shopCar">
          <el-icon><ShoppingCart /></el-icon>
          <span>购物车</span>
        </el-menu-item>
      </el-menu>

      <div class="search-area">
        <el-input placeholder="搜点好东西..."></el-input>
      </div>

      <div class="user-area">
        <el-dropdown>
          <el-avatar v-if="isLoggedIn" :src="userInfo?.two || Megumin" />
          <el-avatar v-else icon="el-icon-user-solid"></el-avatar>

          <template #dropdown>
            <el-dropdown-menu>
              <template v-if="isLoggedIn">
                <el-dropdown-item @click="personalCenter">个人中心</el-dropdown-item>
                <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
              </template>
              <template v-else>
                <el-dropdown-item @click="goToLogin">登录</el-dropdown-item>
              </template>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-header>

    <el-main class="main-content">
      <router-view/>
    </el-main>

    <LoginRegisterModal v-model:visible="isLoginModalVisible" />

  </el-container>
</template>

<script>
import {Goods, Monitor, ShoppingCart, VideoCameraFilled} from '@element-plus/icons-vue';
import { useUserStore } from "./store/user.js";
import { ElMessage } from 'element-plus';
import LoginRegisterModal from './components/LoginAndRegister.vue';
import Megumin from './static-resources/megumin2.1.jpeg'
import defult from './static-resources/2233.jpg'

export default {
  name: "App",
  // 2. 注册组件
  components: {
    Goods,
    Monitor,
    VideoCameraFilled,
    ShoppingCart,
    LoginRegisterModal
  },
  data() {
    return {
      activeIndex: this.$route.path,
      // 3. 添加控制弹窗显示的数据
      isLoginModalVisible: false,
      Megumin: Megumin,
      two: defult
    };
  },
  computed: {
    isLoggedIn() {
      return useUserStore().isLoggedIn;
    },
    userInfo() {
      return useUserStore().userInfo;
    }
  },
  watch: {
    '$route'(to, from) {
      this.activeIndex = to.path;
    }
  },
  methods: {
    goToHome() {
      this.$router.push('/');
    },
    // 4. 修改登录和注册方法
    goToLogin() {
      this.isLoginModalVisible = true;
    },
    personalCenter() {
      this.$router.push('/personalCenter');
    },
    handleLogout() {
      useUserStore().logout();
      sessionStorage.removeItem("token");
      ElMessage.success('已退出登录');
      if (this.$route.path !== '/hot') {
        this.$router.push('/');
      }
    }
  }
}
</script>

<style>
body {
  margin: 0;
  font-family: -apple-system, BlinkMacSystemFont, "Helvetica Neue", "PingFang SC", "Microsoft YaHei", "Source Han Sans SC", "Noto Sans CJK SC", "WenQuanYi Micro Hei", sans-serif;
  background-color: #f4f4f4;
}
.main-container {
  height: 100vh;
}

/* --- 这里是修改的重点 --- */
.main-header {
  display: flex;
  align-items: center;
  background-color: #ffffff;
  padding: 0 30px; /* 增加了左右的内边距，让内容离屏幕边缘远一点 */
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
  margin-left: 60px; /* 增大了Logo和菜单之间的距离 */
  height: 60px;
}

.el-menu--horizontal > .el-menu-item {
  height: 60px;
  line-height: 60px;
  font-size: 15px;
  /* 为每个菜单项增加左右边距，让它们散开一点 */
  padding: 0 25px;
}

.search-area {
  flex-grow: 1; /* 这会让搜索框占据更多空间 */
  min-width: 250px;
  max-width: 500px;
  /* 增加了和左右元素的距离 */
  margin: 0 40px;
}

.user-area {
  display: flex;
  align-items: center;
  margin-left: auto; /* 这会把它推到最右边 */
}
.user-area .el-dropdown {
  cursor: pointer;
}

.main-content {
  padding: 20px;
}
</style>