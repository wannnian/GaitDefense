package com.GaitDefense.Controller;

import com.GaitDefense.Service.CollegeService;
import com.GaitDefense.pojo.College;
import com.GaitDefense.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    @GetMapping("/college")
    public Result getCollegeList(){
        List<College> collegeList = collegeService.getCollegeList();
        return Result.success(collegeList);
    }
}
