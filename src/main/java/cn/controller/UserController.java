package cn.controller;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.entity.User;
import cn.service.UserService;
 
@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;
	
	@RequestMapping("/showUser")
	@ResponseBody
	public Object toIndex(HttpServletRequest request,Model model){
		System.out.println("111111111");
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = this.userService.getUserById(userId);
		System.out.println(user.getUserName());
		model.addAttribute("user", user);
		return user;
	}
	
	
	
	@RequestMapping("/showUser2")
	@ResponseBody
	public Object toIndex2(HttpServletRequest request,Model model){
		System.out.println("22");
	
		return "2222";
	}
}
