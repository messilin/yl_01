<script setup>
import {Search} from '@element-plus/icons-vue'
import { ref } from 'vue'
import axios from "@/util/axios";

// 数据库查询参数
const queryForm=ref({
  query:'',
  pageNum:1,
  pageSize:10
})

// 用户信息总数
const total=ref(0)

// 用于显示的数据
const tableData =ref([])

// 初始化，获取所有用户信息
const initUserList=async()=>{
  const res=await axios.post("admin/user/list",queryForm.value);
  tableData.value=res.data.userList;
  total.value=res.data.total;
}
initUserList();

// 改变每页要显示的记录数
const handleSizeChange = (pageSize) => {
  queryForm.value.pageNum=1;
  queryForm.value.pageSize=pageSize;
  initUserList();
}

// 当前页码变化
const handleCurrentChange = (pageNum) => {
  queryForm.value.pageNum=pageNum;
  initUserList();
}
</script>

<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <el-col :span="7">
        <el-input placeholder="请输入用户昵称" clearable v-model="queryForm.query"></el-input>
      </el-col>
      <el-button type="primary" :icon="Search" @click="initUserList">搜索</el-button>
    </el-row>
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="id" label="#ID" width="80" />
      <el-table-column prop="nickName" label="用户昵称" width="200" />
      <el-table-column prop="avatarUrl" label="用户头像" width="200">
        <template v-slot="scope">
          <img :src="scope.row.avatarUrl" width="50" height="50"/>
        </template>
      </el-table-column>
      <el-table-column prop="openid" label="openID" />
      <el-table-column prop="registerDate" label="注册日期" width="200"/>
      <el-table-column prop="lastLoginDate" label="最后登录日期" width="200"/>
    </el-table>
    <el-pagination
        v-model:currentPage="queryForm.pageNum"
        :page-size="queryForm.pageSize"
        :page-sizes="[10, 20, 30, 40,50]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </el-card>
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