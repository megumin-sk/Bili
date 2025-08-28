<script>
export default {
  name: 'CreationCenter',
  data() {
    return {
      activeView: 'upload',
      // videoList 数据保留，以备将来使用
      videoList: [
        { id: 1, title: '我的第一个Vlog：Vue.js入门教程', status: '已发布', views: '1.2万', date: '2025-08-26' },
        { id: 2, title: '周末爬山记录，风景太美了！', status: '已发布', views: '8,432', date: '2025-08-24' },
      ]
    };
  },
  methods: {
    setView(viewName) {
      this.activeView = viewName;
    }
  }
};
</script>

<template>
  <div class="creation-center">
    <main class="main-content">
      <div v-if="activeView === 'upload'" class="view-container">
        <header class="content-header">
          <nav class="tab-nav">
            <a href="#" class="tab-item active">视频投稿</a>
            <a href="#" class="tab-item">贴文投稿</a>
            <a href="#" class="tab-item">专栏投稿</a>
          </nav>
        </header>
        <div class="upload-area">
          <div class="upload-box">
            <svg class="upload-icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" width="48" height="48"><path d="M896 416H640V128c0-17.673-14.327-32-32-32h-192c-17.673 0-32 14.327-32 32v288H128c-17.673 0-32 14.327-32 32v192c0 17.673 14.327 32 32 32h288v288c0 17.673 14.327 32 32 32h192c17.673 0 32-14.327 32-32V672h288c17.673 0 32-14.327 32-32V448c0-17.673-14.327-32-32-32z" fill="#aaa"></path></svg>
            <p class="upload-text">拖拽到此处也可上传</p>
            <button class="upload-video-button">上传视频</button>
          </div>
        </div>
      </div>

      <div v-if="activeView === 'content-management'" class="view-container">
        <header class="content-header">
          <h2>投稿管理</h2>
          <p class="header-subtitle">在这里上传你的新作品，开启创作之旅。</p>
        </header>
        <div class="upload-area">
          <div class="upload-box">
            <svg class="upload-icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" width="48" height="48"><path d="M896 416H640V128c0-17.673-14.327-32-32-32h-192c-17.673 0-32 14.327-32 32v288H128c-17.673 0-32 14.327-32 32v192c0 17.673 14.327 32 32 32h288v288c0 17.673 14.327 32 32 32h192c17.673 0 32-14.327 32-32V672h288c17.673 0 32-14.327 32-32V448c0-17.673-14.327-32-32-32z" fill="#aaa"></path></svg>
            <p class="upload-text">拖拽到此处也可上传</p>
            <button class="upload-video-button">上传视频</button>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
/* 全局样式和布局 */
.creation-center {
  display: flex;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
  background-color: #f4f5f7;
  height: 100vh;
  color: #333;
}

/* 侧边栏样式 */
.sidebar {
  width: 200px;
  background-color: #ffffff;
  padding: 20px 0;
  flex-shrink: 0;
  border-right: 1px solid #e7e7e7;
}

.upload-main-button {
  background-color: #00a1d6;
  color: white;
  border: none;
  border-radius: 8px;
  padding: 12px 20px;
  font-size: 16px;
  width: calc(100% - 40px);
  margin: 0 20px 20px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.upload-main-button:hover {
  background-color: #00b5e5;
}

.main-nav ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.main-nav a {
  display: block;
  padding: 12px 20px;
  text-decoration: none;
  color: #555;
  font-size: 14px;
  transition: background-color 0.3s;
}

.main-nav a:hover {
  background-color: #f0f0f0;
}

.main-nav a.active {
  background-color: #e3f2fd;
  color: #00a1d6;
  font-weight: bold;
}

/* 主内容区样式 */
.main-content {
  flex-grow: 1;
  padding: 20px 30px;
  overflow-y: auto;
}

.view-container {
  background-color: #ffffff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.05);
}

.content-header {
  border-bottom: 1px solid #e7e7e7;
  padding-bottom: 15px;
  margin-bottom: 20px;
}

/* 增加了 header-subtitle 样式 */
.header-subtitle {
  font-size: 14px;
  color: #999;
  margin-top: 5px;
}


/* 视频投稿页样式 */
.tab-nav {
  display: flex;
  gap: 20px;
}
.tab-item {
  text-decoration: none;
  color: #555;
  padding-bottom: 10px;
  border-bottom: 2px solid transparent;
  font-size: 16px;
}
.tab-item.active {
  color: #00a1d6;
  border-bottom-color: #00a1d6;
}

.upload-area {
  text-align: center;
  padding: 40px 0;
}

.upload-box {
  border: 2px dashed #ccc;
  border-radius: 8px;
  padding: 60px 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  max-width: 600px;
  margin: 0 auto;
}

.upload-icon {
  /* 移除了 fill 属性，直接在 SVG path 中设置 */
}

.upload-text {
  color: #999;
  margin-top: 15px;
}

.upload-video-button {
  background-color: #00a1d6;
  color: white;
  border: none;
  border-radius: 8px;
  padding: 12px 40px;
  font-size: 16px;
  cursor: pointer;
  margin-top: 20px;
  transition: background-color 0.3s;
}

.upload-video-button:hover {
  background-color: #00b5e5;
}

.upload-badge {
  background-color: #e0e0e0;
  color: #757575;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
  margin-top: 15px;
}
</style>