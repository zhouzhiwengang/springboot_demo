package com.zzg.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzg.entity.User;

/**
 * 系统用户Mapper
 * 
 * @author zzg
 *
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
