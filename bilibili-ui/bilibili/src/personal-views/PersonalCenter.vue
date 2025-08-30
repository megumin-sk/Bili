<template>
  <div class="personal-center-container">
    <el-container>
      <el-aside width="200px" class="sidebar">
        <el-menu
            :default-active="$route.path"
            class="sidebar-menu"
            router
        >
          <el-menu-item index="/personalCenter/personDetail">
            <el-icon><USER/></el-icon>
            <span>个人信息</span>
          </el-menu-item>

          <el-menu-item index="/personalCenter/follow">
            <el-icon><Link /></el-icon>
            <span>关注列表</span>
          </el-menu-item>

          <el-sub-menu index="1">
            <template #title>
              <el-icon><VIDEO_CAMERA /></el-icon>
              <span>创作中心</span>
            </template>
            <el-menu-item index="/personalCenter/upload">稿件管理</el-menu-item>
            <el-menu-item index="/personalCenter/data">数据中心</el-menu-item>
          </el-sub-menu>

          <el-menu-item index="/personalCenter/fans">
            <template #title>
              <el-icon><TEAM /></el-icon>
              <span>粉丝管理</span>
            </template>
          </el-menu-item>

          <el-sub-menu index="2">
            <template #title>
              <el-icon><Comment /></el-icon>
              <span>互动管理</span>
            </template>
            <el-menu-item index="/personalCenter/like">点赞管理</el-menu-item>
            <el-menu-item index="/personalCenter/comment">评论管理</el-menu-item>
            <el-menu-item index="/personalCenter/reply">回复管理</el-menu-item>
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
import {User, VideoCamera, DataLine, Comment, Link} from '@element-plus/icons-vue';
const USER = User;
const VIDEO_CAMERA = VideoCamera;
const TEAM = DataLine;

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