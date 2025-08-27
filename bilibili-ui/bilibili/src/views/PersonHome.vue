<template>
  <el-form :model="form" label-width="100px" class="profile-form">
    <el-form-item label="昵称：">
      <el-input v-model="form.name"></el-input>
      <span class="form-tip">注：修改一次昵称需要消耗6个硬币</span>
    </el-form-item>
    <el-form-item label="我的签名：">
      <el-input
          v-model="form.notice"
          type="textarea"
          :rows="3"
          placeholder="设置您的签名-(´-ω-`)つ口"
      ></el-input>
    </el-form-item>
    <el-form-item label="性别：">
      <el-radio-group v-model="form.sex">
        <el-radio-button :label="1">男</el-radio-button>
        <el-radio-button :label="0">女</el-radio-button>
        <el-radio-button :label="3">保密</el-radio-button>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="出生日期：">
      <el-date-picker
          v-model="form.birthday"
          type="date"
          placeholder="选择您的出生日期"
          style="width: 100%;"
      ></el-date-picker>
    </el-form-item>
    <el-form-item label="学校名称：">
      <el-input v-model="form.school"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="saveProfile">保 存</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import {queryUser, updateUser} from "../api/user.js";

export default {
  name: 'PersonHome',
  data(){
    return {
      form: {
        nick_name: '',
        username: '',
        signature: '',
        gender: '',
        birthday: ''
      }
    }
  },methods: {
    async queryProfile(){
      let {data:data} = await queryUser()
      console.log(data)
      this.form = data.data
    },async saveProfile(){
      let {data:data} = await updateUser(this.form)
      console.log(data)
      if (data.code === 201){
        this.$message.success(data.msg)
      }else {
        this.$message.error(data.msg)
      }
    }
  },mounted() {
    this.queryProfile()
  }
}


</script>

<style scoped>
.profile-form {
  margin-top: 20px;
  max-width: 600px;
}
.form-tip {
  color: #999;
  font-size: 12px;
  margin-left: 10px;
}
:deep(.el-form-item__label) {
  font-weight: bold;
  color: #333;
}
</style>