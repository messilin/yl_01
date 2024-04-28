<script setup>
import {defineEmits, defineProps, ref, watch} from "vue";
import axios from "@/util/axios";
import { ElMessage } from 'element-plus'

const props=defineProps({
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

const bigTypeId=ref("")

const form=ref({
  id:-1,
  name:'',
  price:0,
  stock:0,
  type:{
    id:''
  },
  description:'',
})

const emits=defineEmits(['update:modelValue','initProductList'])

const formRef=ref(null)

const handleClose=()=>{
  formRef.value.resetFields();
  emits('update:modelValue',false);
}

const rules=ref({
  name:[{
    required:true,
    message:'请输入商品小类名称!',
  }],
  price:[
    { required: true, message: '请输入商品价格!' },
    { type: 'number', message: '商品价格是数值类型！',transform: (value) => Number(value) },
  ],
  stock:[
    { required: true, message: '请输入商品库存!' },
    { type: 'number', message: '商品库存是数值类型！',transform: (value) => Number(value) },
  ],
  description:[
    {
      required: true,
      message: '请输入商品描述！',
    },
  ],
})

// 大类下拉框
const bigTypeSelectOptions=ref([])
//小类下拉框
const smallTypeSelectOptions=ref([])

// 获取商品大类
const initBigTypeSelectList=async()=>{
  const res=await axios.post("admin/bigType/all");
  bigTypeSelectOptions.value=res.data.bigTypeList;
}

// 获取商品小类
const initSmallTypeSelectList=async (bigTypeId)=>{
  const res=await axios.get("admin/smallType/listAll/"+bigTypeId);
  smallTypeSelectOptions.value=res.data.smallTypeList;
}

initBigTypeSelectList();

const handleConfirm=()=> {
  if (form.value.type.id === ""){
    ElMessage.error("请选择商品大类");
    return;
  }
  formRef.value.validate(async (valid)=>{
    if (valid){
      try {
        let result=await axios.post("admin/product/save",form.value);
        let data=result.data;
        if (data.code===0){
          ElMessage.success("执行成功!");
          formRef.value.resetFields();
          bigTypeId.value="";
          emits("initProductList");
          handleClose();
        }else {
          ElMessage.error(data.msg);
        }
      }catch (error){
        ElMessage.error("系统运行出错!");
      }
    }else {
      return false;
    }
  })
}

const handleBigTypeChange=(bigTypeId)=>{
  initSmallTypeSelectList(bigTypeId);
}

const getBigTypeIdBySmallTypeId=async (smallTypeId)=>{
  const res=await axios.get("admin/smallType/getBigTypeIdBySmallTypeId/"+smallTypeId);
  handleBigTypeChange(res.data.bigTypeId);
}

watch(
    ()=>props.dialogValue,
    ()=>{
      form.value=props.dialogValue;
      if(form.value.id===undefined){
        console.log("添加");
      }else {
        console.log("修改");
        getBigTypeIdBySmallTypeId(form.value.type.id);
      }
    },
    {deep:true,immediate:true}
)
</script>

<template>
  <el-dialog
      :model-value="dialogVisible"
      :title="dialogTitle"
      width="50%"
      @close="handleClose"
  >
    <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="150px"
    >
      <el-form-item label="商品名称" prop="name">
        <el-input v-model="form.name" style="width: 400px"/>
      </el-form-item>
      <el-form-item label="商品价格" prop="price">
        <el-input v-model="form.price" style="width: 100px"/>
      </el-form-item>
      <el-form-item label="商品库存" prop="stock">
        <el-input v-model="form.stock" style="width: 100px"/>
      </el-form-item>
      <el-form-item label="商品类别">
        <el-select v-model="bigTypeId" class="m-2" style="width: 150px" placeholder="请选择商品大类" @change="handleBigTypeChange">
          <el-option
              v-for="item in bigTypeSelectOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
          >
          </el-option>
        </el-select>
        &nbsp;
        <el-select v-model="form.type.id" class="m-2" style="width: 150px" placeholder="请选择商品小类">
          <el-option
              v-for="item in smallTypeSelectOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="商品描述" prop="description">
        <el-input v-model="form.description" type="textarea" :rows="4"/>
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