<template>
  <div class="login-container">
    <el-form :model="loginForm">
      <h2>GaitDefense</h2>
      <p>基于步态识别的校园安保系统</p>

      <!-- 用户名输入框 -->
      <el-form-item
        prop="username"
        :rules="[{ required: true, message: '请输入用户名', trigger: 'blur' }]"
      >
        <el-input
          v-model="loginForm.username"
          placeholder="请输入用户名"
          clearable
        ></el-input>
      </el-form-item>

      <!-- 密码输入框 -->
      <el-form-item
        prop="password"
        :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]"
      >
        <el-input
          v-model="loginForm.password"
          type="password"
          placeholder="请输入密码"
          show-password
        ></el-input>
      </el-form-item>

      <el-checkbox v-model="loginForm.remember">记住密码</el-checkbox>

      <!-- 登录按钮 -->
      <el-button
        type="primary"
        @click="handleLogin"
        style="width: 100%; margin-top: 20px"
      >
        登录
      </el-button>
    </el-form>
  </div>
</template>

<script lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";

export default {
  setup() {
    const loginForm = ref({
      username: "admin",
      password: "123456",
      remember: false,
    });

    const router = useRouter();

    const handleLogin = () => {
      // 处理用户名和密码校验逻辑
      if (
        loginForm.value.username === "admin" &&
        loginForm.value.password === "123456"
      ) {
        router.push("/layout"); // 跳转到 Layout 页面
      } else {
        // 使用 ElMessage 弹窗提示错误
        ElMessage.error("用户名或密码错误，请重新输入！");
      }
    };

    return {
      loginForm,
      handleLogin,
    };
  },
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #7a5cff; /* 背景颜色 */
  color: white;
}

.el-form {
  width: 350px;
  padding: 40px;
  background-color: #1f1f1f;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 10px;
}

p {
  text-align: center;
  margin-bottom: 20px;
}

.el-button {
  background-color: #007bff;
}

.other-login {
  text-align: center;
  margin-top: 15px;
  color: #fff;
}
</style>
