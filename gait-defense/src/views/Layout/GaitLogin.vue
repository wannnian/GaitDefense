<template>
  <el-form :model="form" ref="formRef" class="form-container">
    <div style="display: flex; flex-wrap: wrap; align-items: center">
      <el-form-item label="学号" label-width="150px" class="studentId">
        <el-input v-model="form.studentId" placeholder="请输入学号" />
      </el-form-item>
      <el-form-item label="姓名" label-width="150px" class="name">
        <el-input v-model="form.name" placeholder="请输入姓名" />
      </el-form-item>
      <el-form-item label="性别" label-width="150px" class="gender">
        <el-radio-group v-model="form.gender">
          <el-radio :label="1">男</el-radio>
          <el-radio :label="2">女</el-radio>
        </el-radio-group>
      </el-form-item>
    </div>
    <el-form-item label="学院" label-width="150px" class="college">
      <el-input v-model="form.college" placeholder="请输入学院" />
    </el-form-item>
    <el-form-item label="步态行为" label-width="150px" class="gaitVideo">
      <el-upload
        ref="uploadRef"
        name="gaitVideo"
        :http-request="uploadVideo"
        :on-success="handleSuccess"
        :on-error="handleError"
        :file-list="fileList"
      >
        <el-button size="small" type="primary">上传视频</el-button>
      </el-upload>
    </el-form-item>
    <el-form-item label="备注" label-width="150px" class="remark">
      <el-input v-model="form.remark" placeholder="请输入备注" />
    </el-form-item>
    <el-form-item label-width="400px">
      <div class="button-container">
        <el-button @click="submitForm">{{ isEdit ? '修改' : '提交' }}</el-button>
        <el-button @click="resetForm">重置</el-button>
      </div>
    </el-form-item>
  </el-form>

  <el-table :data="paginatedData" stripe style="width: 100%">
    <el-table-column prop="studentId" label="学号" width="180" />
    <el-table-column prop="name" label="姓名" width="180" />
    <el-table-column prop="gender" label="性别">
      <template #default="scope">
        {{ scope.row.gender === 1 ? '男' : '女' }}
      </template>
    </el-table-column>
    <el-table-column prop="college" label="学院" />
    <el-table-column prop="gaitVideo" label="步态行为">
      <template #default="scope">
        <video
          :src="scope.row.gaitVideo"
          controls
          width="80px"
          height="50px"
        ></video>
      </template>
    </el-table-column>
    <el-table-column
      prop="createTime"
      label="创建时间"
      :formatter="formatDate"
    />
    <el-table-column
      prop="updateTime"
      label="修改时间"
      :formatter="formatDate"
    />
    <el-table-column prop="remark" label="备注" />
    <el-table-column label="操作" width="180px">
      <template #default="scope">
        <el-button @click="editEntry(scope.row)" width="80px">修改</el-button>
        <el-button @click="deleteEntry(scope.row.id)" width="80px">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-pagination
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
    :current-page="currentPage"
    :page-size="pageSize"
    :total="tableData.length"
    layout="total, prev, pager, next"
  />

</template>

<script lang="ts" setup>
import { ElNotification } from "element-plus";
import { ref, onMounted, computed } from "vue";
import { gaitLoginApi } from "@/api/gaitLogin";

// 表单数据
const form = ref({
  id: null as number | null,
  studentId: "",
  name: "",
  gender: null,
  college: "",
  gaitVideo: null as File | null,
  remark: "",
  createTime: "",
  updateTime: "",
});

// 编辑状态
const isEdit = ref(false);

const fileList = ref([]); // 文件列表
const uploadRef = ref(null); // 上传组件
const fileRef = ref(null); // 新文件

// 字符串类型转文件
const createFileFromPath = async (path: string): Promise<File> => {
  try {
    // 从路径获取文件名
    const fileName = path.split('/').pop() || 'video.mp4';
    
    // 从路径获取文件
    const response = await fetch(path);
    const blob = await response.blob();
    
    // 创建 File 对象
    return new File([blob], fileName, { type: blob.type });
  } catch (error) {
    console.error('创建 File 对象失败:', error);
    throw new Error('无法从路径创建文件对象');
  }
};

// 提交表单
async function submitForm() {
  // 根据编辑状态，获取当前时间
  const now = new Date().toISOString();
  if (!isEdit.value) {
    form.value.createTime = now;
  } else {
    form.value.updateTime = now;
  }

  // 检查必填项
  if (!form.value.studentId || !form.value.name || !form.value.college || !form.value.gender) {
    ElNotification({
      title: '错误',
      message: '请填写所有必填项',
      type: 'error',
      duration: 5000,
    });
    return;
  }
  // 创建FormData对象
  const formData = new FormData();
  for (const key in form.value) {
    if (key === 'gaitVideo') {
      if (fileRef.value) {
        formData.append('gaitVideo', fileRef.value);
      } else if (form.value.gaitVideo) {
        console.log("gaitVideo的格式为: ",typeof form.value.gaitVideo);
        if (typeof form.value.gaitVideo === 'string') {
          const file = await createFileFromPath(form.value.gaitVideo);
          formData.append('gaitVideo', file);
        } 
      } else{
        ElNotification({
          title: '错误',
          message: '请上传步态视频',
          type: 'error',
          duration: 5000,
        });
        return;
      }
    } else {
      formData.append(key, form.value[key]);
    }
  }
  console.log('FormData 内容:');
  formData.forEach((value, key) => {
    console.log(`${key}:`, value);
  });
  try {
    let response;
    if (isEdit.value) {
      response = await gaitLoginApi.update(form.value.id, formData);
    } else {
      response = await gaitLoginApi.create(formData);
    }
    
    if (response.code === 1 || response.msg === 'success') {
      ElNotification({
        title: '成功',
        message: isEdit.value ? '修改成功' : '提交成功',
        type: 'success',
        duration: 5000,
      });
      fetchData();
      resetForm();
    } else {
      ElNotification({
        title: '错误',
        message: response.msg || (isEdit.value ? '修改失败，请重试' : '提交失败，请重试'),
        type: 'error',
        duration: 5000,
      });
    }
  } catch (error) {
    ElNotification({
      title: '错误',
      message: isEdit.value ? '修改异常，请检查提交是否合理' : '提交异常，请检查提交是否合理',
      type: 'error',
      duration: 5000,
    });
  }
}
// 上传视频
const uploadVideo = async ({ file }) => {
  try {
    const response = await gaitLoginApi.uploadVideo(file);
    handleSuccess(response, file);
  } catch (error) {
    handleError(error);
  }
}
// 上传成功
const handleSuccess = (response: any, file: any) => {
  form.value.gaitVideo = response.data;
  fileRef.value = file;
}
// 上传失败
const handleError = (error: any) => {
  ElNotification({
    title: '错误',
    message: '视频上传失败，请重试',
    type: 'error',
    duration: 5000,
  });
}

// 重置表单
function resetForm(){
  form.value = {
    id: null as number | null,
    studentId: "",
    name: "",
    gender: null,
    college: "",
    gaitVideo: null as File | null,
    remark: "",
    createTime: "",
    updateTime: "",
  };
  isEdit.value = false;
  fileRef.value = null;
  fileList.value = [];
  if (uploadRef.value) {
    uploadRef.value.clearFiles();
  }
}
// 编辑记录
function editEntry(row: any) {
  isEdit.value = true;
  form.value = { ...row };
  if (row.gaitVideo) {
    fileList.value = [{ name: '当前视频', url: row.gaitVideo }];

    console.log("当前视频：", form.value.gaitVideo);
  } 
  fileRef.value = null;
}
// 删除记录
async function deleteEntry(id: number) {
  try {
    await gaitLoginApi.delete(id);
    ElNotification({
      title: '成功',
      message: '删除成功',
      type: 'success',
      duration: 5000,
    });
    fetchData();
  } catch (error) {
    ElNotification({
      title: '错误',
      message: '删除失败，请重试',
      type: 'error',
      duration: 5000,
    });
  }
}

// 获取表格数据
const tableData = ref([]);

async function fetchData() {
  try {
    const response = await gaitLoginApi.getAll();
    tableData.value = response.data;
  } catch (error) {
    console.error(error);
  }
}

onMounted(() => {
  fetchData();
});

// 格式化日期
function formatDate(row, column, cellValue) {
  if (!cellValue) return '-';
  const date = new Date(cellValue);
  return isNaN(date.getTime()) ? '-' : date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
  });
}

// 分页
const currentPage = ref(1);
const pageSize = ref(6);

const paginatedData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return tableData.value.slice(start, end);
});

const handleSizeChange = (val: number) => {
  pageSize.value = val;
  currentPage.value = 1;
};

const handleCurrentChange = (val: number) => {
  currentPage.value = val;
};

</script>

<style>
.form-container {
  border: 1px solid #dcdcdc;
  border-radius: 4px;
  padding: 20px;
  margin-bottom: 20px;
}

.demo-form-inline .el-input {
  --el-input-width: 220px;
}

el-form-item {
  margin-bottom: 20px;
  width: 200px;
}

.button-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  margin-bottom: 20px;
}

.button-container .el-button {
  margin-right: 100px;
}
</style>