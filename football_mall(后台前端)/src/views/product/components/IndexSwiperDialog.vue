<script setup>
import {ref, watch} from 'vue'
import {ElMessage} from 'element-plus'
import {Plus} from '@element-plus/icons-vue'
import {getServerUrl} from "@/config/sys";
import axios from "@/util/axios";

const emits=defineEmits(['update:modelValue','initProductList'])

const rules=ref({
  swiperSort: [
    {
      required: true,
      message: '请输入轮播序号'
    },
    {
      type:'number',
      message: '轮播序号必须是数值类型',
      transform: (value) => Number(value)
    }
  ]
})

const formRef=ref(null);

const props=defineProps({
  id:{
    type:Number,
    default:-1,
    required:true
  },
  swiperSort:{
    type:Number,
    default:0,
    required:true
  }
})

const form=ref({
  id:-1,
  swiperPic:'',
  swiperSort:0
})

const headers=ref({
  token:window.sessionStorage.getItem('token')
})

const imageUrl=ref("")

watch(
    ()=>props.id,
    ()=>{
      form.value.id=props.id;
      form.value.swiperSort=props.swiperSort;
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
  console.log("src="+res.data.src);
  imageUrl.value=getServerUrl()+res.data.src
  form.value.swiperPic=res.data.title;
}

const handleClose = () => {
  emits('update:modelValue',false)
}

const handleConfirm=async ()=> {
  formRef.value.validate(async (valid) => {
    if (valid) {
      let result = await axios.post("admin/product/saveSwiper", form.value);
      console.log(result);
      let data = result.data;
      if (data.code === 0) {
        ElMessage.success("执行成功！");
        imageUrl.value = "";
        emits("initProductList");
        handleClose();
      } else {
        ElMessage.error(data.msg);
      }
    } else {
      return false;
    }
  })
}
</script>

<template>
  <el-dialog
      :model-value="indexSwiperDialogVisible"
      title="首页轮播设置"
      width="30%"
      @close="handleClose"
      center
  >
    <el-form
        ref="formRef"
        :model="form"
        label-width="100px"
        style="text-align: center"
        :rules="rules"
    >

      <el-form-item label="轮播序号" prop="swiperSort">
        <el-input v-model="form.swiperSort" style="width: 100px"/>
      </el-form-item>

      <el-upload
          :headers="headers"
          class="avatar-uploader"
          :action="getServerUrl()+'admin/product/uploadSwiperImage'"
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