package com.zzg.controller;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzg.common.AbstractCommonController;
import com.zzg.common.Response;
import com.zzg.entity.Employee;
import com.zzg.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController extends AbstractCommonController<Employee> {
	@Autowired
	private EmployeeService service;
	
	@GetMapping(value = "/list")
	public String view() {
		return "page/sys/employeeList";
	}
	@GetMapping(value = "/add")
	public String add() {
		return "page/sys/employeeAdd";
	}
	
	
	 // 增
	@RequestMapping(value = "save")
	@ResponseBody
    public Response<Boolean> save(Employee employe) {
    	Boolean target = service.save(employe);
        return Response.success(target);
    }
    
    @RequestMapping(value = "edit")
	public String edit(Employee employee, Model model) {
		model.addAttribute("employee", service.getById(employee.getId()));
		return "page/sys/employeeEdit";
	}

    // 改
    @RequestMapping(value = "update")
	@ResponseBody
    public Response<Boolean> update(Employee employee ) {
        return Response.success(service.updateById(employee));
    }

    // 删
    @RequestMapping(value = "delete")
	@ResponseBody
    public Response<Boolean> delete( Employee employee ) {
        return Response.success(service.removeById(employee.getId()));
    }

    // 查
    @GetMapping( value = "/getUserByName")
    public Object getUserByName( @RequestParam String userName ) {
    	QueryWrapper<Employee> wrapper =new QueryWrapper<Employee>() ;
    	wrapper.like("name", userName);
        return service.getOne(wrapper);
    }
    
    // 查
    @GetMapping( value = "/page", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object page( @RequestParam(required = false) Map paramter ) {
    	Page<Employee> page = super.getPage(paramter);
    	QueryWrapper<Employee> wrapper =new QueryWrapper<Employee>() ;
    	
    	if(paramter.get("id") != null && StringUtils.isNotEmpty(paramter.get("id").toString())) {
    		wrapper.eq(true, "id", paramter.get("id"));
    	}
    	if(paramter.get("name") != null && StringUtils.isNotEmpty(paramter.get("name").toString())) {
    		wrapper.like(true, "name", paramter.get("name"));
    	}
    	if(paramter.get("code") != null && StringUtils.isNotEmpty(paramter.get("code").toString())) {
    		wrapper.like(true, "code", paramter.get("code"));
    	}
    	IPage<Employee> result = service.page(page, wrapper);
        return result;
    }
    
    
}
