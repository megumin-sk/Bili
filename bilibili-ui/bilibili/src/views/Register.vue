<template>
  <div class="register-container">
    <div class="register-box">
      <h2>创建新账号</h2>
      <form @submit.prevent="handleRegister">
        <!-- 昵称 -->
        <div class="input-group">
          <label for="nick_name">昵称</label>
          <input type="text" id="nick_name" v-model="form.nick_name" placeholder="请输入您的昵称" required>
        </div>

        <!-- 邮箱 -->
        <div class="input-group">
          <label for="email">邮箱</label>
          <input type="email" id="email" v-model="form.email" placeholder="请输入您的邮箱地址" required>
        </div>

        <!-- 密码 -->
        <div class="input-group">
          <label for="password">密码</label>
          <input type="password" id="password" v-model="form.password" placeholder="请设置您的密码" required>
        </div>

        <!-- 确认密码 (可选，但推荐) -->
        <div class="input-group">
          <label for="confirm_password">确认密码</label>
          <input type="password" id="confirm_password" v-model="form.confirm_password" placeholder="请再次输入密码" required>
        </div>

        <!-- 性别 -->
        <div class="input-group">
          <label>性别</label>
          <div class="radio-group">
            <label><input type="radio" v-model="form.sex" value="1"> 男</label>
            <label><input type="radio" v-model="form.sex" value="0"> 女</label>
          </div>
        </div>

        <!-- 生日 -->
        <div class="input-group">
          <label for="birthday">生日</label>
          <input type="date" id="birthday" v-model="form.birthday">
        </div>

        <!-- 学校 -->
        <div class="input-group">
          <label for="school">学校</label>
          <input type="text" id="school" v-model="form.school" placeholder="请输入您的学校名称">
        </div>

        <!-- 个人介绍 -->
        <div class="input-group">
          <label for="person_introduction">个人介绍</label>
          <textarea id="person_introduction" v-model="form.person_introduction" rows="3" placeholder="简单介绍一下自己吧"></textarea>
        </div>

        <button type="submit" class="register-button">注 册</button>
      </form>
      <div class="links">
        <a @click="goToLogin()" style="cursor: pointer;">已有账号？返回登录</a>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Register',
  data() {
    return {
      form: {
        nick_name: '',
        email: '',
        password: '',
        confirm_password: '', // 用于密码验证
        sex: '1', // 默认选中'男'
        birthday: '',
        school: '',
        person_introduction: ''
      }
    }
  },
  methods: {
    handleRegister() {
      // 1. 校验密码和确认密码是否一致
      if (this.form.password !== this.form.confirm_password) {
        alert('两次输入的密码不一致，请检查！');
        return; // 阻止表单提交
      }

      // 2. 准备提交给后端的数据 (移除 confirm_password)
      const userData = { ...this.form };
      delete userData.confirm_password;

      // 3. 在这里处理注册逻辑，例如调用后端 API
      console.log('准备提交的注册信息:', userData);
      alert('注册信息已在控制台打印，请连接后端接口！');

      // 示例：使用 axios 发送请求
      /*
      axios.post('/api/register', userData)
        .then(response => {
          alert('注册成功！即将跳转到登录页面。');
          this.$router.push('/login');
        })
        .catch(error => {
          alert('注册失败：' + error.response.data.message);
        });
      */
    },
    goToLogin() {
      // 点击链接后跳转回登录页面
      this.$router.push('/login');
    }
  }
}
</script>

<style scoped>
/* 样式与登录页面保持一致，并做微调 */
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 2rem 0; /* 为较长的表单提供上下内边距 */
  background-color: #f0f2f5;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
}

.register-box {
  background: white;
  padding: 2.5rem;
  border-radius: 8px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 500px; /* 注册表单内容较多，可以适当加宽 */
  box-sizing: border-box;
}

h2 {
  text-align: center;
  margin-bottom: 2rem;
  color: #333;
  font-weight: 600;
}

.input-group {
  margin-bottom: 1.2rem; /* 稍微减小间距以容纳更多字段 */
}

.input-group label {
  display: block;
  margin-bottom: 0.5rem;
  color: #555;
  font-weight: 500;
}

.input-group input,
.input-group textarea {
  width: 100%;
  padding: 0.8rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  transition: border-color 0.3s ease;
}

.input-group input:focus,
.input-group textarea:focus {
  outline: none;
  border-color: #007bff;
  box-shadow: 0 0 0 2px rgba(0, 123, 255, 0.25);
}

.radio-group label {
  margin-right: 1.5rem;
  font-weight: normal;
  cursor: pointer;
}

.radio-group input {
  width: auto; /* 让 radio button 恢复默认宽度 */
  margin-right: 0.5rem;
}

.register-button {
  width: 100%;
  padding: 0.8rem;
  background-color: #28a745; /* 注册按钮使用绿色 */
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 500;
  transition: background-color 0.3s ease;
  margin-top: 1rem;
}

.register-button:hover {
  background-color: #218838;
}

.links {
  margin-top: 1.5rem;
  text-align: center; /* 居中显示 */
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