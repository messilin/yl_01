<script setup>
import {ref, watch} from 'vue'
import {ElMessage} from 'element-plus'
import {Plus} from '@element-plus/icons-vue'
import {getServerUrl} from "@/config/sys";
import axios from "@/util/axios";

const emits=defineEmits(['update:modelValue','initProductList'])

const props=defineProps({
  id:{
    type:Number,
    default:-1,
    required:true
  }
})

const form=ref({
  id:-1,
  proPic:''
})

const imageUrl=ref("")

const headers=ref({
  token:window.sessionStorage.getItem('token')
})

const imageSrc=ref("")

watch(
    ()=>props.id,
    ()=>{
      form.value.id=props.id;
      imageSrc.value=getServerUrl()+'/image/product/'+form.value.proPic;
    },
    {deep:true,immediate:true}
)

// 上传图片前验证图片是否有效
const beforeAvatarUpload=(file)=>{
  const isJPG=file.type==='image/jpeg'
  const isLT2M=file.size/1024/1024<2
  if (!isJPG){
    ElMessage.error('图片格式必须是jpg格式!')
  }
  if (!isLT2M){
    ElMessage.error('图片大小不能超过2M!')
  }
  return isJPG&&isLT2M
}

// 回显事件
const handleAvatarSuccess=(res)=>{
  imageUrl.value=getServerUrl()+res.data.src;
  form.value.proPic=res.data.title;
}

const handleClose = () => {
  emits('update:modelValue',false)
}

const handleConfirm=async ()=>{
  let result=await axios.post("admin/product/saveImage",form.value);
  console.log(result[2]);
  let data=result.data;
  console.log(data);
  if(data.code===0){
    ElMessage.success("更换成功！");
    imageUrl.value="";
    emits("initProductList");
    handleClose();
  }else {
    ElMessage.error(data.msg);
  }
}
</script>

<template>
  <el-dialog
      :v-model="dialogVisible"
      title="更换商品图片"
      width="30%"
      :before-close="handleClose"
      center
  >
    <el-form
        ref="formRef"
        :model="form"
        label-width="100px"
        style="text-align: center">

      <el-upload
          :headers="headers"
          class="avatar-uploader"
          :action="getServerUrl()+'admin/product/uploadProductImage'"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
      >
        <img v-if="imageUrl" :src="imageUrl" class="avatar" />
        <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
      </el-upload>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="handleConfirm">
          确认更改
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>