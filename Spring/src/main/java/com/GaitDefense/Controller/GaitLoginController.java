package com.GaitDefense.Controller;
import org.apache.ibatis.annotations.Update;
import org.springframework.format.annotation.DateTimeFormat;
import com.GaitDefense.Service.CollegeService;
import com.GaitDefense.Service.GaitLoginService;
import com.GaitDefense.pojo.GaitLogin;
import com.GaitDefense.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins="https://localhost:5173")
public class GaitLoginController {

    @Autowired
    private GaitLoginService gaitLoginService;

    private String handleVideoUpload(MultipartFile gaitVideo) {
        log.info("gaitVideo", gaitVideo);
        if (gaitVideo == null || gaitVideo.isEmpty()) {
            return null; // 如果视频为空，返回 null
        }

        String fileName = gaitVideo.getOriginalFilename(); // 获取原始文件名
        String filePath = "F:/Project Practice/Backend/GaitDefense/src/main/resources/video/";  // 设置你的视频存储路径
        File targetFile = new File(filePath + fileName);

        // 创建目标目录（如果不存在）
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            gaitVideo.transferTo(targetFile); // 将文件保存到指定路径
            return fileName; // 返回文件名
        } catch (IOException e) {
            e.printStackTrace(); // 打印异常信息
            return null; // 上传失败，返回 null
        }
    }

    @PostMapping("gaitLogin")
    public Result addGaitLogin(
            @RequestParam("studentId") String studentId,
            @RequestParam("name") String name,
            @RequestParam("gender") int gender,
            @RequestParam("college") String college,
            @RequestParam(value = "createTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date createTime,
            @RequestParam(value = "updateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date updateTime,
            @RequestParam("gaitVideo") MultipartFile gaitVideo,
            @RequestParam("remark") String remark
    ) {
        GaitLogin gaitLogin = new GaitLogin();
        gaitLogin.setStudentId(studentId);
        gaitLogin.setName(name);
        gaitLogin.setGender(gender);
        gaitLogin.setCollege(college);
        gaitLogin.setCreateTime(createTime);
        gaitLogin.setUpdateTime(updateTime);
        gaitLogin.setRemark(remark);

        String fileName = handleVideoUpload(gaitVideo);
        gaitLogin.setGaitVideo(fileName);

        log.info("添加步态录入表数据:{}", gaitLogin);
        gaitLoginService.addGaitLogin(gaitLogin);
        return Result.success();
    }
    @PostMapping("/gaitLogin/uploadVideo")
    public Result uploadVideo(@RequestParam("gaitVideo") MultipartFile gaitVideo) {
        return Result.success(handleVideoUpload(gaitVideo));
    }

    @DeleteMapping("/gaitLogin/{id}")
    public Result deleteGaitLogin(@PathVariable("id") int id) {
        gaitLoginService.deleteGaitLogin(id);
        return Result.success();
    }

    @PutMapping("/gaitLogin/{id}")
    public Result updateGaitLogin(
            @PathVariable("id") int id,
            @RequestParam("studentId") String studentId,
            @RequestParam("name") String name,
            @RequestParam("gender") int gender,
            @RequestParam("college") String college,
            @RequestParam(value = "updateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date updateTime,
            @RequestPart(value = "gaitVideo", required = true) MultipartFile gaitVideo,
            @RequestParam("remark") String remark
    ) {
        log.info("Received update request for id: {}", id);
        log.info("studentId: {}, name: {}, gender: {}, college: {}", studentId, name, gender, college);
        log.info("updateTime: {}, remark: {}", updateTime, remark);
        log.info("gaitVideo present: {}", (gaitVideo != null));
        if (gaitVideo != null) {
            log.info("gaitVideo name: {}, size: {}", gaitVideo.getOriginalFilename(), gaitVideo.getSize());
        }

        GaitLogin existingGaitLogin = gaitLoginService.getGaitLoginById(id);
        if (existingGaitLogin == null) {
            return Result.error("未找到指定ID的记录");
        }

        existingGaitLogin.setStudentId(studentId);
        existingGaitLogin.setName(name);
        existingGaitLogin.setGender(gender);
        existingGaitLogin.setCollege(college);
        existingGaitLogin.setUpdateTime(updateTime != null ? updateTime : new Date());
        existingGaitLogin.setRemark(remark);


        existingGaitLogin.setGaitVideo(handleVideoUpload(gaitVideo));

        log.error("更新步态录入表数据:{}", existingGaitLogin);
        gaitLoginService.updateGaitLogin(existingGaitLogin);
        return Result.success();
    }
    // 查询全部数据
    @GetMapping("/gaitLogin")
    public Result gaitLogin(){
        log.info("查询全部成员数据");

        //调用service查询数据
        List<GaitLogin> gaitLoginList = gaitLoginService.getAllGaitLogin();

        return Result.success(gaitLoginList);
    }
}
