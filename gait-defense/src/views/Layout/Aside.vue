<template>
  <el-aside width="200px">
    <el-scrollbar>
      <el-menu router>
        <div class="title">
          <img src="../../assets/logo.png">
        </div>

        <el-menu-item index="/layout">
          <el-icon><HomeFilled /></el-icon> 首页
        </el-menu-item>
        <el-menu-item index="/layout/gait-login">
          <el-icon><AddLocation /></el-icon> 步态录入
        </el-menu-item>
        <el-menu-item index="/layout/identify-recognition">
          <el-icon><document /></el-icon> 身份识别
        </el-menu-item>
        <el-menu-item index="/layout/camera-monitoring">
          <el-icon><Camera /></el-icon> 摄像布防
        </el-menu-item>
      </el-menu>
    </el-scrollbar>
  </el-aside>
</template>
  
<script lang="ts" setup>
import { ref } from "vue";
</script>
  
<style scoped>
.el-menu-item {
    margin: 40px 0;
    font-size: 20px;
    color: #333;
    text-align: center;
    border-radius: 0px;
    border: 1 solid #ccc;
    transition: all 0.3s ease-in-out;

}

.el-menu-item:hover {
    background-color: #f5f5f5;
    color: #409eff;
}

.title {
    text-align: center;
    margin-bottom: 20px;
}

.title img {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    border: 1px solid #ccc;
}

</style>
  