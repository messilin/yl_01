<script setup>
import {Search,Delete,Edit,DocumentAdd} from '@element-plus/icons-vue'
import { ref } from 'vue'
import axios from "@/util/axios";
import {getServerUrl} from "@/config/sys";
import Dialog from "@/views/bigType/components/dialog.vue";
import {ElMessageBox} from "element-plus";
import {ElMessage} from "element-plus";

// 商品大类总数
const total=ref(0)

// 用于显示的数据
const tableData =ref([])


const id=ref(-1)

// 会话框的标题
const dialogTitle=ref('')

// 会话框是否显示
const dialogVisible=ref(false)

// 数据库查询参数
const queryForm=ref({
  query:'',
  pageNum:1,
  pageSize:10
})

// 初始化，获取商品大类信息
const initBigTypeList=async()=>{
  const res=await axios.post("admin/bigType/list",queryForm.value);
  console.log(res.data.bigTypeList);
  tableData.value=res.data.bigTypeList;
  total.value=res.data.total;
}

initBigTypeList();

// 改变每页显示的记录数
const handleSizeChange = (pageSize) => {
  queryForm.value.pageNum=1;
  queryForm.value.pageSize=pageSize;
  initBigTypeList();
}

// 当前页码变化
const handleCurrentChange = (pageNum) => {
  queryForm.value.pageNum=pageNum;
  initBigTypeList();
}

// 会话框弹出事件
const handleDialogValue=(row)=>{
  if (row){
    id.value=row.id;
    dialogTitle.value="商品大类修改"
  }else {
    id.value=-1;
    dialogTitle.value="商品大类添加"
  }
  dialogVisible.value=true
}

// 删除商品大类
const deleteBigType=(id)=>{
  ElMessageBox.confirm(
      '您确定要删除此商品大类吗',
      '温馨提示',
      {
        confirmButtonText:'确定',
        cancelButtonText:'取消',
        type:'warning',
      }
  )
      .then(async ()=>{
        let res=await axios.get
        ('admin/bigType/deleteBigType/'+id)
        if (res.data.code===0){
          ElMessage({
            type:'success',
            message:'删除成功',
          })
          initBigTypeList();
        }else {
          ElMessage({
            type:'error',
            message:res.data.msg,
          })
        }
      })
      .catch(()=>{

      })
}

</script>

<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <el-col :span="7">
        <el-input placeholder="请输入商品大类名称" clearable v-model="queryForm.query"></el-input>
      </el-col>
      <el-button type="primary" :icon="Search" @click="initBigTypeList">搜索</el-button>
      <el-button type="primary" :icon="DocumentAdd" @click="handleDialogValue">添加商品大类</el-button>
    </el-row>
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="id" label="#ID" width="80" />
      <el-table-column prop="name" label="商品大类名称" width="200" />
      <el-table-column prop="remark" label="商品大类描述" />
      <el-table-column prop="action" label="操作" width="300">
        <template v-slot="scope">
          <el-button type="primary" :icon="Edit" @click="handleDialogValue(scope.row)"></el-button>
          <el-button type="danger" :icon="Delete" @click="deleteBigType(scope.row.id)"></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        v-model:currentPage="queryForm.pageNum"
        v-model:page-size="queryForm.pageSize"
        :page-sizes="[10, 20, 30, 40,50]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"/>
  </el-card>
  <Dialog v-model="dialogVisible" :id="id" :dialog-title="dialogTitle" @initBigTypeList="initBigTypeList" dialog-visible></Dialog>
</template>

<style scoped lang="scss">
.header{
  padding-bottom: 16px;
  box-sizing: border-box;
}

.el-pagination{
  padding-top: 15px;
  box-sizing: border-box;
}

</style>