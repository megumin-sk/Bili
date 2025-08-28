<template>
  <div class="personal-center-container">
    <el-container>
      <el-aside width="200px" class="sidebar">
        <el-menu
            :default-active="$route.path"
            class="sidebar-menu"
            router
        >
          <el-menu-item index="/personalCenter/personHome">
            <el-icon><User /></el-icon>
            <span>个人信息</span>
          </el-menu-item>

          <el-sub-menu index="creation-center">
            <template #title>
              <el-icon><VideoCamera /></el-icon>
              <span>创作中心</span>
            </template>
            <el-menu-item index="/personalCenter/creation">投稿管理</el-menu-item>
            <el-menu-item index="/personalCenter/contentManagement">内容管理</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>

      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { User, VideoCamera } from '@element-plus/icons-vue';

</script>

<style scoped>
.personal-center-container {
  max-width: 1200px;
  margin: 20px auto;
  background-color: #fff;
  border-radius: 8px;
  height: calc(100vh - 100px);
}
.el-container {
  height: 100%;
}
.sidebar {
  border-right: 1px solid #e3e5e7;
}
.sidebar-menu {
  border-right: none;
  height: 100%;
}
.main-content {
  padding: 30px;
}
</style>