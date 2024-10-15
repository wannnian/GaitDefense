package com.GaitDefense.Mapper;

import com.GaitDefense.pojo.College;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CollegeMapper {
    @Select("select * from college")
    List<College> getCollegeList();

    @Select("SELECT college_id FROM college WHERE name = #{collegeName}")
    Integer getCollegeIdByName(String collegeName);
}
