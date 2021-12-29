package com.zzg.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzg.entity.UserRole;
import com.zzg.mapper.UserRoleMapper;
import com.zzg.service.UserRoleService;

/**
 * 用户角色关系Service实现
 * 
 * @author zzg
 *
 */
@Service
public class UserRoleSerivceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
