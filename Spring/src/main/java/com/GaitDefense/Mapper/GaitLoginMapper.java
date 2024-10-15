package com.GaitDefense.Mapper;

import com.GaitDefense.pojo.GaitLogin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GaitLoginMapper {
    // 插入步态录入表信息
    @Insert("INSERT INTO gait_login (student_id,name,gender,college,create_time,update_time,gait_video,remark) VALUES (#{studentId},#{name},#{gender},#{college},#{createTime},#{updateTime},#{gaitVideo},#{remark})")
    void insert(GaitLogin gaitLogin);

    //查询全部步态录入表数据
    @Select("SELECT id, student_id AS studentId, name, gender, college, create_time AS createTime, update_time AS updateTime, gait_video AS gaitVideo, remark FROM gait_login;")
    List<GaitLogin> getAll();

    // 删除步态录入表信息
    @Delete("DELETE FROM gait_login WHERE id = #{id}")
    void delete(int id);

    // 更新步态录入信息的方法
    @Update("UPDATE gait_login SET name = #{name}, gender = #{gender}, college = #{college}, create_time = #{createTime}, update_time = #{updateTime}, gait_video = #{gaitVideo}, remark = #{remark} WHERE id = #{id}")
    void update(GaitLogin gaitLogin);

    // 根据id查询步态录入信息
    @Select("SELECT * FROM gait_login WHERE id = #{id}")
    GaitLogin getGaitLoginById(int id);

    // 根据id更新步态录入信息
    @Update("UPDATE gait_login SET " +
            "student_id = #{studentId}, " +
            "name = #{name}, " +
            "gender = #{gender}, " +
            "college = #{college}, " +
            "update_time = #{updateTime}, " +
            "gait_video = #{gaitVideo}, " +
            "remark = #{remark} " +
            "WHERE id = #{id}")
    void updateGaitLogin(GaitLogin gaitLogin);
}
