<script setup>
import {ref} from "vue";
// import {Edit} from "@element-plus/icons-vue"
import axios from "axios";
import {ElMessage} from "element-plus";
import axiosUtil from '@/util/axios'
import router from "@/router"

const form=ref({
  userName:'',
  password:''
})

const rules=ref({
  userName:[
    {required:true,message:'用户名不为空',trigger:'blur'}
  ],
  password:[
    {
      required:true,message:'密码不能为空',trigger:'blur'
    }
  ]
})

const formRef=ref(null);

const handleLogin=()=>{
  formRef.value.validate(async (valid)=>{
    // 成功的情况
    if(valid){
      try {
        let result= await axiosUtil.post("adminLogin",form.value);
        let data=result.data;
        if(data.code===0){
          window.sessionStorage.setItem("token",data.token);
          window.sessionStorage.setItem("userInfo",JSON.stringify(form.value));
          router.replace("/");
        }else {
          ElMessage.error(data.msg);
        }
      }catch (err){
        console.log("error:"+err);
        ElMessage.error("系统运行出错，请联系管理员")
      }
    }else {
      console.log("验证失败")
    }
  })
}
</script>

<template>
  <div class="login-container">
    <el-form
        ref="formRef"
        :rules="rules"
        :model="form"
        class="login-form" >
      <div class="title-container">
        <h3 class="title">绿茵之家微信小程序-后台管理员登录</h3>
      </div>
      <div class="form-content">
        <el-form-item prop="userName">
         <svg-icon icon="user" class="svg-container"></svg-icon>
         <el-input v-model="form.userName" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <svg-icon icon="password" class="svg-container"></svg-icon>
          <el-input v-model="form.password" placeholder="请输入密码" type="password"></el-input>
        </el-form-item>
        <el-button type="primary" class="login-button" @click="handleLogin">登录</el-button>
      </div>
    </el-form>
  </div>
</template>

<style lang="scss" scoped>
$bg: #1e2834;
$dark_gray: #889aa4;
$light_gray: #eee;
$cursor: #fff;

.login-container {
  min-height: 100%;
  width: 100%;
  //background-color: $bg;
  overflow: hidden;
  background-image: url("../../assets/images/bgimage.jpg");
  background-repeat: no-repeat;
  //background-size: cover;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;

    ::v-deep .el-form-item {
      border: 1px solid rgba(255, 255, 255, 0.1);
      background: rgba(0, 0, 0, 0.1);
      border-radius: 5px;
      color: #454545;
      height: 49px;
      width: 400px;
    }

    ::v-deep .el-form-item__content{
      //height: 50px;
      color: #454545;
      background-color: $bg;
      //background: rgba(0, 0, 0, 0.1);
      border-radius: 5px;
    }

    ::v-deep .el-input__wrapper {
      display: block;
      color: #454545;
      background: rgb(36,47,60);
      box-shadow:none;
      //height: 47px;
    }

    ::v-deep .el-input {
      display: inline-block;
      //background:rgba(0, 0, 0, 0.1);
      //background:#889aa4;
      //background: black;
      //background:rgba(0, 0, 0, 0.1);
      background-color: $bg;
      height: 47px;
      width: 83%;

      input {
      background: rgba(0, 0, 0, 0.1);
        //background: transparent;
        //background:rgba(0, 0, 0, 0.1);
        //background-color: white;
        border: 0px;
        -webkit-appearance: none;
        border-radius: 0px;
        padding: 12px 5px 12px 15px;
        color: $light_gray;
        height: 47px;
        caret-color: $cursor;
        //caret-color:rgb(0, 0, 0, 0.1) ;
      }
    }
    .login-button {
      width: 400px;
      box-sizing: border-box;
    }
  }

  .tips {
    font-size: 16px;
    line-height: 28px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    display: inline-block;

  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: #228B22;
      //color: #409eff;
      margin: -25px auto 65px auto;
      text-align: center;
      font-weight: bold;
    }

    ::v-deep .lang-select {
      position: absolute;
      top: 4px;
      right: 0;
      background-color: white;
      font-size: 22px;
      padding: 4px;
      border-radius: 4px;
      cursor: pointer;
    }
  }

  .form-content{
    //display: flex;
    height: 160px;
    width: 400px;
    padding: 60px 20px;
    //border: 1px solid black;
    //justify-content: center;
    //align-items: center;
    border-radius: 20px;
    background: linear-gradient(to right,#228B22,chartreuse);
    opacity: 0.5;
    margin: auto;
  }



  .show-pwd {
    // position: absolute;
    // right: 10px;
    // top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>