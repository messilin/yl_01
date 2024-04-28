<script setup>
import {defineEmits, defineProps, ref, watch} from "vue";
import axios from "@/util/axios";
import { ElMessage } from 'element-plus'

const props=defineProps(
    {
      dialogTitle:{
        type:String,
        default:'',
        required:true
      },
      dialogValue:{
        type:Object,
        default:()=>{}
      }
    }
)

const form=ref({
  id:-1,
  name:'',
  remark:'',
  bigType:{
    id:""
  }
})

watch(
    ()=>props.dialogValue,
    ()=>{
      console.log("props.dialogValue="+props.dialogValue);
      form.value=props.dialogValue;
    },
    {deep:true,immediate:true}
)

const emits=defineEmits(['update:modelValue','initSmallTypeList'])

const formRef=ref(null)

const handleClose=()=>{
  formRef.value.resetFields();
  emits('update:modelValue',false)
}

const rules=ref({
  name:[
    { required: true, message: '请输入商品小类名称'}
  ],
  remark:[
    { required: true, message: '请输入商品小类描述'}
  ]
})

const bigTypeSelectOptions=ref([])

const initBigTypeSelectList=async()=>{
  const res=await axios.post("admin/bigType/all");
  bigTypeSelectOptions.value=res.data.bigTypeList;
}

initBigTypeSelectList();

const handleConfirm=()=>{
  formRef.value.validate(async(valid)=>{
    if(valid){
      try {
        if(form.value.bigType.id===""){
          ElMessage.error("请选择商品大类");
          return;
        }
        let result=await axios.post("admin/smallType/save",form.value);
        let data=result.data;
        if(data.code===0){
          ElMessage.success("操作成功！")
          formRef.value.resetFields();
          emits("initSmallTypeList")
          handleClose();
        }else{
          ElMessage.error(data.msg);
        }
      } catch(error){
      console.log("error="+error);
      ElMessage.error('系统出错');
    }
    }else {
      return false;
    }
  })
}
</script>

<template>
  <el-dialog
      :model-value="dialogVisible"
      :title="dialogTitle"
      width="35%"
      @close="handleClose"
  >
    <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="150px"
    >
      <el-form-item label="商品小类名称" prop="name">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="所属商品大类">
        <el-select v-model="form.bigType.id" class="m-2" placeholder="请选择商品大类">
          <el-option
              v-for="item in bigTypeSelectOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="商品小类描述" prop="remark">
        <el-input v-model="form.remark" type="textarea" :rows="4"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">

        <el-button  @click="handleClose">取消</el-button>
     <el-button type="primary" @click="handleConfirm">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>

</style>