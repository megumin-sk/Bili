<template>
  <div class="login-container">
    <div class="login-box">
      <h2>用户登录</h2>
      <form @submit.prevent="handleLogin">
        <div class="input-group">
          <label for="name">用户名</label>
          <input
              type="text"
              id="name"
              v-model="form.name"
              placeholder="请输入您的邮箱地址"
              required
          >
        </div>
        <div class="input-group">
          <label for="password">密码</label>
          <input
              type="password"
              id="password"
              v-model="form.password"
              placeholder="请输入您的密码"
              required
          >
        </div>
        <button type="submit" class="login-button">登 录</button>
      </form>
      <div class="links">
        <a href="#">忘记密码?</a>
        <a @click="createNew()" style="cursor: pointer;">注册新账号</a>
      </div>
    </div>
  </div>
</template>

<script>
import { login } from "../api/user.js";
import { useUserStore } from "../store/user.js";

export default {
  name: 'login',
  data(){
    return {
      form: {
        name: '',
        password: ''
      }
    }
  },
  methods:{
    async handleLogin() {
        let {data:data} = await login(this.form);
        console.log(data);
        if (data.code === 200){
          sessionStorage.setItem("token",data.data)
          let userStore = useUserStore();
          userStore.loginSuccess(data.data);
          this.$message.success('登录成功');
          this.$router.push('/');
        } else {
          this.$message.error(data.msg);
        }
      },
    },
    createNew(){
      this.$router.push('/register');
    }
  }
</script>

<style scoped>
/* 样式只作用于当前组件 */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f2f5;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
}

.login-box {
  background: white;
  padding: 2.5rem;
  border-radius: 8px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
  box-sizing: border-box;
}

h2 {
  text-align: center;
  margin-bottom: 2rem;
  color: #333;
  font-weight: 600;
}

.input-group {
  margin-bottom: 1.5rem;
}

.input-group label {
  display: block;
  margin-bottom: 0.5rem;
  color: #555;
  font-weight: 500;
}

.input-group input {
  width: 100%;
  padding: 0.8rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  transition: border-color 0.3s ease;
}

.input-group input:focus {
  outline: none;
  border-color: #007bff;
  box-shadow: 0 0 0 2px rgba(0, 123, 255, 0.25);
}

.login-button {
  width: 100%;
  padding: 0.8rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 500;
  transition: background-color 0.3s ease;
}

.login-button:hover {
  background-color: #0056b3;
}

.links {
  margin-top: 1.5rem;
  display: flex;
  justify-content: space-between;
  font-size: 0.9rem;
}

.links a {
  color: #007bff;
  text-decoration: none;
}

.links a:hover {
  text-decoration: underline;
}
</style>