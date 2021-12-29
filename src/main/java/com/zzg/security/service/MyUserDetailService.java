package com.zzg.security.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzg.entity.Role;
import com.zzg.entity.User;
import com.zzg.security.entity.MyUserDetails;
import com.zzg.service.RoleService;
import com.zzg.service.UserService;

@Component("MyUserDetailService")
public class MyUserDetailService implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		User user = userService.getOne(queryWrapper);
		if (user != null) {
			MyUserDetails userDetails = new MyUserDetails();
			BeanUtils.copyProperties(user, userDetails);

			// 用户角色
			Set<GrantedAuthority> authorities = new HashSet<>();

			// 查询用户角色
			List<Role> roleList = roleService.findByUserId(userDetails.getId());
			roleList.forEach(role -> {
				authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleCode()));
			});

			userDetails.setAuthorities(authorities);

			return userDetails;
		}
		return null;
	}

}
