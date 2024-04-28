<script setup>
import {Delete, DocumentAdd, Edit, Search} from "@element-plus/icons-vue";
import {getServerUrl} from "@/config/sys";
import Dialog from "@/views/product/components/dialog.vue";
import {ref} from "vue";
import axios from "@/util/axios";
import {ElMessage, ElMessageBox} from "element-plus";
import ProductImageDialog from "@/views/product/components/ProductImageDialog.vue";
import IndexSwiperDialog from "@/views/product/components/IndexSwiperDialog.vue";

const queryForm=ref({
  query:'',
  pageNum:1,
  pageSize:10
})

const total=ref(0)

const id=ref(-1)

const swiperSort=ref(-1)

const tableData=ref([])

const dialogValue=ref({})

const dialogTitle=ref('')

// 获取商品信息
const initProductList=async ()=>{
  const res=await axios.post('admin/product/list',queryForm.value);
  tableData.value=res.data.productList;
  total.value=res.data.total;
}

initProductList();

const dialogVisible=ref(false)

const productImageDialogVisible=ref(false)

const indexSwiperDialogVisible=ref(false)

const handleSizeChange=(pageSize)=>{
  queryForm.value.pageNum=1;
  queryForm.value.pageSize=pageSize;
  initProductList();
}

const handleCurrentChange=(pageNum)=>{
  queryForm.value.pageNum=pageNum;
  initProductList();
}

const handleDialogValue=(row)=>{
  if(row){
    dialogValue.value=JSON.parse(JSON.stringify(row));
    dialogTitle.value='商品修改'
  }else {
    dialogValue.value={
      productIntroImgs:'',
      productParaImgs:'',
      type:{
        id:""
      }
    }
    dialogTitle.value='商品添加'
  }
  dialogVisible.value=true
}

const handleDelete=(id)=>{
  ElMessageBox.confirm(
      '您确定要删除此商品吗？',
      '温馨提示',
      {
        confirmButtonText:'确定',
        cancelButtonText:'取消',
        type:'warning'
      }
  ).then(async ()=>{
    let res=await axios.get("admin/product/delete/"+id);
    if (res.data.code===0){
      ElMessage({
        type:'success',
        message:'删除成功！'
      })
      initProductList();
    }else {
      ElMessage({
        type:'error',
        message:res.data.msg
      })
    }
  }).catch(()=>{

  })
}

const typeNameFormatter=(row)=>{
  return row.type.name
}

// 更新商品是否热卖
const updateHotHandle=async (row)=>{
  let res=await axios.get("admin/product/updateHot/"+row.id+"/state/"+row.hot);
  if (res.data.code===0){
    ElMessage({
      type:'success',
      message:'设置热卖状态成功！'
    })
  }else {
    ElMessage({
      type:'error',
      message:res.data.msg
    })
    initProductList();
  }
}

// 更新商品swiper状态
const updateSwiperHandle=async (row)=>{
  let res=await axios.get("admin/product/updateSwiper/"+row.id+"/state/"+row.swiper);
  if (res.data.code===0){
    ElMessage({
      type:'success',
      message:'设置首页轮播状态成功！'
    })
  }else {
    ElMessage({
      type:'error',
      message:res.data.msg
    })
    initProductList();
  }
}

// 更换商品图片
const handleProductImage=(row)=>{
  id.value=row.id;
  productImageDialogVisible.value=true;
}

// 更换首页轮播
const handleIndexSwiper=(row)=>{
  id.value=row.id;
  swiperSort.value=row.swiperSort;
  indexSwiperDialogVisible.value=true;
}

</script>

<template>
<el-card>
  <el-row :gutter="20" class="header">
    <el-col :span="7">
      <el-input placeholder="请输入商品名称" clearable v-model="queryForm.query"></el-input>
    </el-col>
    <el-button type="primary" :icon="Search" @click="initProductList">搜索</el-button>
    <el-button type="primary" :icon="DocumentAdd" @click="handleDialogValue()">添加商品</el-button>
  </el-row>
  <el-table :data="tableData" stripe style="width: 100%">
    <el-table-column prop="name" label="商品名称" width="220" fixed/>
    <el-table-column prop="image" label="商品图片" width="110">
      <template v-slot="scope">
        <img :src="getServerUrl()+'/image/product/'+scope.row.proPic" width="80" height="80">
      </template>
    </el-table-column>
    <el-table-column prop="price" label="商品单价" width="100"/>
    <el-table-column prop="stock" label="商品库存" width="100"/>
    <el-table-column prop="type" label="商品类别" width="200" :formatter="typeNameFormatter"/>
    <el-table-column prop="hot" label="是否热卖" width="100" align="center">
      <template v-slot="{row}">
        <el-switch v-model="row.hot" @change="updateHotHandle(row)"></el-switch>
      </template>
    </el-table-column>
    <el-table-column prop="swiper" label="是否首页轮播" width="150" align="center">
      <template v-slot="{row}">
        <el-switch v-model="row.swiper" @change="updateSwiperHandle(row)"></el-switch>
      </template>
    </el-table-column>
    <el-table-column prop="swiperPic" label="首页轮播图片" width="150" align="center">
      <template v-slot="{row}">
        <img :src="getServerUrl()+'/image/product/'+row.swiperPic" width="150" height="75"/>
      </template>
    </el-table-column>
    <el-table-column prop="swiperSort" label="轮播排序" width="100" align="center"/>
    <el-table-column prop="description" label="商品描述" width="400"/>
    <el-table-column prop="action" label="操作" width="540" fixed="right">
      <template v-slot="scope">
        <el-button type="success" @click="handleProductImage(scope.row)">更换商品图片</el-button>
        <el-button type="primary" @click="handleIndexSwiper(scope.row)">首页轮播设置</el-button>
        <el-button type="primary" :icon="Edit" @click="handleDialogValue(scope.row)"></el-button>
        <el-button type="danger" :icon="Delete" @click="handleDelete(scope.row.id)"></el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination v-model:currentPage="queryForm.pageNum"
                 :page-size="queryForm.pageSize"
                 :page-sizes="[10,20,30,40,50]"
                 layout="total,sizes,prev,pager,next,jumper"
                 :total="total"
                 @size-change="handleSizeChange"
                 @current-change="handleCurrentChange"/>
</el-card>
<!--  添加或修改对话框-->
  <Dialog v-model="dialogVisible" :dialogTitle="dialogTitle"
          @initProductList="initProductList" :dialogValue="dialogValue"></Dialog>
<!--  商品图片更换-->
  <ProductImageDialog v-model="productImageDialogVisible" :id="id" @initProductList="initProductList"></ProductImageDialog>
<!--  首页轮播更换-->
  <IndexSwiperDialog v-model="indexSwiperDialogVisible" :id="id" :swiperSort="swiperSort" @initProductList="initProductList"></IndexSwiperDialog>
<!--  商品详情轮播更换-->
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