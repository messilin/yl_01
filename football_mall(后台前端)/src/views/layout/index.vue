<script setup>
import Menu from '@/views/layout/menu'
import Breadcrumb from "@/views/layout/header/breadcrumb.vue";
import Avatar from "@/views/layout/header/avatar.vue";
import Footer from "@/views/layout/footer/index.vue";
import {ref} from "vue";

const form=ref({
  userName:""
})
</script>

<template>
  <div class="app-wrapper">
    <el-container>
      <el-aside width="200px" class="sidebar-container"><Menu/></el-aside>
      <el-container>
        <el-header>
          <div class="navbar">
            <Breadcrumb/>
            <div class="navbar-right"><Avatar/></div>
          </div>
        </el-header>
<!--        home是作为路由导入的-->
        <el-main>
          <router-view/>
        </el-main>
        <el-footer>
          <Footer/>
        </el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped lang="scss">
.app-wrapper {
  position: relative;
  width: 100%;
  height: 100%;
}

.sidebar-container {
  background-color: #228B22;
  height: 100%;
}

:deep(.el-container){
  height: 100%;
}

:deep(.el-header){
  padding: 0px;
}
//::v-deep .el-container{
//  height: 100%;
//}

//::v-deep ul.el-menu{
//  border-right-width: 0px;
//}

:deep(ul.el-menu){
  border-right-width: 0px;
}

.navbar {
  width: 100%;
  height: 60px;
  overflow: hidden;
  background-color: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  padding: 0 16px;
  display: flex;
  align-items: center;
  box-sizing: border-box;
  position: relative;
  .navbar-right{
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: flex-end;
    font-weight:bold;
    color: #228B22;
}
}
</style>