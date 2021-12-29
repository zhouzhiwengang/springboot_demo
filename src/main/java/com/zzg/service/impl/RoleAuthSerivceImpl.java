package com.zzg.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzg.entity.RoleAuth;
import com.zzg.mapper.RoleAuthMapper;
import com.zzg.service.RoleAuthService;

/**
 * 角色权限关系Service实现
 * 
 * @author zzg
 *
 */
@Service
public class RoleAuthSerivceImpl extends ServiceImpl<RoleAuthMapper, RoleAuth> implements RoleAuthService {

}
