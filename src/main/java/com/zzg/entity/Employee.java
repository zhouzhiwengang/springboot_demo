package com.zzg.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName
public class Employee {
	@TableId(type=IdType.AUTO)
    private Integer id;

    private String code;

    private String name;

    private Integer sex;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String passwd;

    private String telephone;

    @TableField(fill = FieldFill.DEFAULT)
    private String note;

    private Integer workYear;

    
}