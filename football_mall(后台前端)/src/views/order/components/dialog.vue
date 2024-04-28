<script setup>
import {defineEmits,defineProps,ref,watch} from "vue";
import axios from "@/util/axios";
import {getServerUrl} from "@/config/sys";

const tableData=ref([])

const  props=defineProps({
  id:{
    type:Number,
    default:-1,
    required:true
  }
})

//监听那个商品被点击
watch(
    ()=>props.id,
    ()=>{
      let id=props.id;
      if (id!==-1){
        initOrderDetailData(id);
      }
    }
)

const initOrderDetailData=async (id)=>{
  const res=await axios.get('admin/orderDetail/list/'+id);
  tableData.value=res.data.list;
}

// 动态修改dialog的值true或者false
// 定义父组件事件
const emits=defineEmits(['update:modelValue'])

const handleClose=()=>{
//   调用执行
  emits("update:modelValue",false)
}
</script>

<!--dialog是作为组件被父组件弹出来的-->
<template>
  <el-dialog model-value="dialogVisible" title="订单商品详情" width="40%" @close="handleClose">
    <!--stripe表示有斑马线-->
    <el-table
        :data="tableData" stripe style="width: 100%">
      <!--    type=index表示添加序号-->
      <el-table-column type="index" width="50"/>
      <el-table-column prop="goodsPic" label="商品图片" width="200">
        <template v-slot="scope">
          <img :src="getServerUrl()+'image/product/'+scope.row.goodsPic" width="80" height="80"/>
        </template>
      </el-table-column>
      <el-table-column prop="goodsName" label="商品名称"></el-table-column>
      <el-table-column prop="goodsPrice" label="商品价格" width="100"></el-table-column>
      <el-table-column prop="goodsNumber" label="商品数量" width="100"></el-table-column>
    </el-table>
  </el-dialog>
</template>

<style scoped lang="scss">

</style>