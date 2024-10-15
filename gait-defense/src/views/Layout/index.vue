<template>
  <div class="common-layout">
    <el-container class="container">
      <Aside class="aside"></Aside>
      <el-container class="main-container">
        <el-header class="header">
          <div class="bread">
            <el-breadcrumb class="el-breadcrumb">
              <el-breadcrumb-item class="el-breadcrumb-item" style="text-align: center;">主页</el-breadcrumb-item>
              <el-breadcrumb-item v-for="(crumb, index) in crumbs" :key="index" class="el-breadcrumb-item">
                <template v-if="index > 0">/</template>
                {{ crumb.meta.title }}
              </el-breadcrumb-item>
            </el-breadcrumb>

          </div>
          <div></div>
        </el-header>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
  
<script>
import Aside from "./Aside.vue";
import { ArrowRight } from '@element-plus/icons-vue'

export default {
  components: { Aside, ArrowRight},
  computed: {
    crumbs() {
      return this.$route.matched;
    },
  },
  mounted() {
    const separators = document.querySelectorAll('.el-breadcrumb__separator');
    separators.forEach(separator => {
      separator.remove();
    });
  }
};

</script>
<style scoped>
.container {
  height: 100%;
}
.main-container {
  height: 100%;
  width: 85%;
}
.header {
  padding: 0;
  height: 60px;
  background-color: #b1b3b8;
}
.aside {
  width: 15%;
}

</style>
  