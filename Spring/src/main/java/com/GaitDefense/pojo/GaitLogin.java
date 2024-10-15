package com.GaitDefense.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * @author fh
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GaitLogin {
    private int id;

    private String studentId;
    private String name;
    private int gender;
    private String college;
    private Date createTime;
    private Date updateTime;
    private String gaitVideo;
    private String remark;
}
