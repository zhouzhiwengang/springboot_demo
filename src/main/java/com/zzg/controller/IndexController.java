package com.zzg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 系统首页Controller
 * 
 * @author zzg
 *
 */
@Controller
public class IndexController {
	/**
	 * 首页
	 * 
	 * @return
	 */
	@GetMapping(value = "index")
	public String index(Model model) {
		model.addAttribute("user", "周志刚");
		return "index";
	}
	
	/**
	 * 控制台
	 * 
	 * @return
	 */
	@GetMapping(value = "view")
	public String view() {
		return "page/sys/view";
	}
}
