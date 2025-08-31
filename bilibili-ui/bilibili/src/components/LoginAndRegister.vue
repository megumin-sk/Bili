<template>
  <div v-if="visible" class="modal-container" @click.self="closeModal">
    <div class="modal-card">
      <div class="image-pane">
        <img src="../static-resources/ren.png" alt="登录插画">
      </div>
      <div class="form-pane">
        <el-tabs v-model="activeTab" class="custom-tabs" @tab-change="handleTabChange">
          <el-tab-pane label="登录" name="login">
            <el-form class="action-form" @submit.prevent="handleLogin">
              <el-input v-model="loginForm.name" placeholder="请输入账号" :prefix-icon="UserIcon" size="large"/>
              <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" :prefix-icon="LockIcon" size="large" show-password/>
              <el-button type="primary" size="large" native-type="submit">登 录</el-button>
            </el-form>
          </el-tab-pane>

          <el-tab-pane label="注册" name="register">
            <el-form class="action-form" @submit.prevent="handleRegister">
              <el-input v-model="registerForm.name" placeholder="请输入账号" :prefix-icon="UserIcon" size="large"/>
              <el-input v-model="registerForm.password" type="password" placeholder="请输入密码" :prefix-icon="LockIcon" size="large" show-password/>
              <el-input v-model="registerForm.confirmPassword" type="password" placeholder="请确认密码" :prefix-icon="LockIcon" size="large" show-password/>
              <el-button type="primary" size="large" native-type="submit" @click="handleRegister">注 册</el-button>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>

<script>
import { markRaw } from 'vue';
import { User, Lock, MessageBox } from '@element-plus/icons-vue';
import {login, register} from "../api/user.js";
import { useUserStore } from "../store/user.js";
import { ElMessage } from 'element-plus';

export default {
  name: 'LoginRegisterModal',
  props: {
    visible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      activeTab: 'login',
      loginForm: {
        name: '',
        password: ''
      },
      registerForm: {
        name: '',
        password: '',
        confirmPassword: '',
        verifyCode: ''
      },
      // 将Element Plus的图标赋值给data属性
      UserIcon: markRaw(User),
      LockIcon: markRaw(Lock),
      MessageBoxIcon: markRaw(MessageBox),
    };
  },
  methods: {
    // 登录逻辑
    async handleLogin() {
      if (!this.loginForm.name || !this.loginForm.password) {
        ElMessage.warning('请输入用户名和密码');
        return;
      }
      try {
        // 使用解构赋值获取返回的数据
        let { data: res } = await login(this.loginForm);
        if (res.code === 200) {
          sessionStorage.setItem("token", res.data.token);
          // 使用Pinia store
          useUserStore().loginSuccess(res.data);
          ElMessage.success('登录成功！');
          this.closeModal();
        } else {
          ElMessage.error(res.msg || '登录失败，请检查您的凭据');
        }
      } catch (error) {
        console.error("Login request failed:", error);
        ElMessage.error('请求失败，请稍后再试');
      }
    },

    // 注册逻辑
    async handleRegister() {
      if (this.registerForm.password !== this.registerForm.confirmPassword) {
        ElMessage.error('两次输入的密码不一致！');
        return;
      }
      // 在这里调用你的注册API
      console.log('提交注册信息:', this.registerForm);
      let {data:res} = register(this.registerForm)
      // 注册成功后可以关闭弹窗
      if (res.code === 200){
        this.closeModal();
        ElMessage.success('注册成功！');
      }else {
        ElMessage.error(res.msg || '注册失败，请检查您的凭据');
      }
    },

    // 刷新验证码
    refreshCaptcha() {
      ElMessage.info('刷新验证码');
      // 在实际项目中, 你需要在这里调用API刷新验证码URL
      // this.captchaUrl = '新的验证码URL'
    },

    // 关闭弹窗
    closeModal() {
      this.$emit('update:visible', false);
    },

    // 切换Tab时清空表单内容
    handleTabChange() {
      // 清空登录表单
      Object.keys(this.loginForm).forEach(key => {
        this.loginForm[key] = '';
      });
      // 清空注册表单
      Object.keys(this.registerForm).forEach(key => {
        this.registerForm[key] = '';
      });
    }
  },mounted() {
    const userStore = useUserStore();
  }
}
</script>

<style scoped>
/* 样式与上一个回答中的完全相同，这里保持不变 */
.modal-container {
  position: fixed;
  top: 0;
  left: 0;
  height: 100vh;
  width: 100vw;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(0, 0, 0, 0.4);
  backdrop-filter: blur(5px);
  z-index: 1000;
  overflow: hidden;
}

.modal-card {
  display: flex;
  width: 800px;
  height: 500px;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  animation: popIn 0.5s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}

.image-pane {
  flex: 1;
  padding: 2rem;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #fdeff2;
  position: relative;
  overflow: hidden;
}

.image-pane::before {
  content: '';
  position: absolute;
  bottom: -80px;
  left: -50px;
  width: 200px;
  height: 200px;
  background: #fb7299;
  border-radius: 50%;
  opacity: 0.1;
}

.image-pane img {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
  animation: punchOut 0.8s cubic-bezier(0.25, 0.46, 0.45, 0.94) 0.3s forwards;
  opacity: 0;
}

.form-pane {
  flex: 1;
  padding: 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.action-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 100%;
}

.verify-code-row {
  display: flex;
  align-items: center;
  gap: 10px;
}

.captcha-img {
  width: 110px;
  height: 40px;
  border-radius: 6px;
  cursor: pointer;
  border: 1px solid #dcdfe6;
}

.custom-tabs {
  width: 100%;
}
.custom-tabs :deep(.el-tabs__header) {
  margin-bottom: 25px;
}
.custom-tabs :deep(.el-tabs__item) {
  font-size: 18px;
  font-weight: 600;
  color: #888;
}
.custom-tabs :deep(.el-tabs__item.is-active) {
  color: #fb7299;
}
.custom-tabs :deep(.el-tabs__active-bar) {
  background-color: #fb7299;
  height: 3px;
}

:deep(.el-input__wrapper) {
  border-radius: 8px;
  padding: 5px 15px;
}
.el-button {
  border-radius: 8px;
  font-weight: 600;
  letter-spacing: 2px;
  background-color: #fb7299;
  border-color: #fb7299;
}
.el-button:hover {
  background-color: #fca1c0;
  border-color: #fca1c0;
}

@keyframes popIn {
  from { transform: scale(0.5); opacity: 0; }
  to { transform: scale(1); opacity: 1; }
}

@keyframes punchOut {
  0% { transform: scale(0.7); opacity: 0; }
  50% { transform: scale(1.05); opacity: 1; }
  100% { transform: scale(1); opacity: 1; }
}
</style>