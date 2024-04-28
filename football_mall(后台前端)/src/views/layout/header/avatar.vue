<script setup>
import {ref} from 'vue'

const squareUrl=ref("https://img2.baidu.com/it/u=131246699,1538412024&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=400");
import {useStore} from "vuex";
const store=useStore();
const logout=()=>{
  store.dispatch('logout')
}
</script>

<template>
<el-dropdown>
  <span class="el-dropdown-link">
    <el-avatar shape="circle" :size="50" :src="squareUrl"/>
  </span>
  <template #dropdown>
  <el-dropdown-menu>
    <el-dropdown-item @click="logout">安全退出</el-dropdown-item>
  </el-dropdown-menu>
</template>
</el-dropdown>
</template>

<style scoped lang="scss">

</style>