<script setup>
import {defineEmits,defineProps,ref,watch} from "vue";
import axios from "@/util/axios";
import {ElMessage} from "element-plus";

// 给对话框的属性赋默认值
const props=defineProps({
  id:{
    type:Number,
    default:-1,
    required:true
  },
  dialogTitle:{
    type:String,
    default:'',
    required:true
  },
  dialogVisible:{
    type:Boolean,
    default:false,
    required:true
  }
})

// 通过Id获取商品大类
const initFormData=async (id)=>{
  const res=await axios.get("admin/bigType/"+id)
  form.value=res.data.bigType;
}

// 初始化对话框表单的值
const form=ref({
  id:-1,
  name:'',
  remark:''
})

// 监听Id的值,如果不为-1,通过Id获取商品大类信息并显示
watch(
    ()=>props.id,
    ()=>{
      console.log("id="+props.id);
      let id=props.id;
      form.value.id=id;
      if (id!==-1){
        initFormData(id)
      }
    },
    {deep:true,immediate:true}
)

const emits=defineEmits(['update:modelValue','initBigTypeList'])

// 对话框表单的值
const formRef=ref(null)

// 关闭对话框
const handleClose=()=>{
  emits("update:modelValue",false);
  formRef.value.resetFields();
}

// 表单要有输入的提示
const rules=ref({
  name:[{
    required:true,
    message:'请输入商品大类名称'
  }],
  remark:[{
    required:true,
    message:'请输入商品大类描述'
  }]
})

// 点击确定事件
const handleConfirm=()=>{
  formRef.value.validate(async (valid)=>{
    if (valid){
      try {
        let result=await axios.post("admin/bigType/save",form.value);
        let data=result.data;
        if (data.code===0){
          ElMessage.success("操作成功！");
          formRef.value.resetFields();
          // 调用initBigTypeList方法，子组件调用父组件
          emits("initBigTypeList");
          handleClose();
        }else {
          ElMessage.error(data.msg);
        }
      }catch (error){
        console.log("error="+error);
        ElMessage.error('系统运行出错');
      }
    }else {
      return false
    }
  })
}
</script>

<template>
<el-dialog
  :model-value="dialogVisible"
  :title="dialogTitle"
  width="30%"
  @close="handleClose">
  <el-form
    ref="formRef"
    :model="form"
    :rules="rules"
    label-width="150px">
    <el-form-item label="商品大类名称：" prop="name">
      <el-input v-model="form.name"/>
    </el-form-item>
    <el-form-item label="商品大类描述：" prop="remark">
      <el-input v-model="form.remark" type="textarea" :rows="4"/>
    </el-form-item>
  </el-form>
  <template #footer>
    <span class="dialog-footer">
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" @click="handleConfirm">确认</el-button>
    </span>
  </template>
</el-dialog>
</template>

<style scoped lang="scss">

</style>