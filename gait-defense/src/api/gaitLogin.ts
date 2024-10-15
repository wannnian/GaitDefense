import axios from 'axios';

const BASE_URL = 'http://localhost:8080';

export const gaitLoginApi = {
  // 获取所有记录
  getAll: async () => {
    try {
      const response = await axios.get(`${BASE_URL}/gaitLogin`);
      return response.data;
    } catch (error) {
      console.error("获取数据失败", error);
      throw error;
    }
  },

  // 创建新记录（包括视频上传）
  create: async (formData: FormData) => {
    try {
      const response = await axios.post(`${BASE_URL}/gaitLogin`, formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
      });
      return response.data;
    } catch (error) {
      console.error("创建记录失败", error);
      throw error;
    }
  },

  // 更新记录
  update: async (id: number, formData: FormData) => {
    try {
      // 打印 FormData 内容进行检查
      formData.forEach((value, key) => {
        console.log(key, value);
      });
  
      const response = await axios.put(`${BASE_URL}/gaitLogin/${id}`, formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
      });
  
      console.log("更新成功，响应数据:", response.data);
      return response.data;
    } catch (error) {
      console.error("更新记录失败", error);
      throw error;
    }
  },
  // 删除记录
  delete: async (id: number) => {
    try {
      const response = await axios.delete(`${BASE_URL}/gaitLogin/${id}`);
      return response.data;
    } catch (error) {
      console.error("删除记录失败", error);
      throw error;
    }
  },

  // 单独上传视频
  uploadVideo: async (file: File) => {
    const formData = new FormData();
    formData.append('gaitVideo', file);
    try {
      const response = await axios.post(`${BASE_URL}/gaitLogin/uploadVideo`, formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
      });
      return response.data;
    } catch (error) {
      console.error("上传视频失败", error);
      throw error;
    }
  }
};