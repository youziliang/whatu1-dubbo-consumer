package com.dp.petshome.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dp.petshome.persistence.model.User;
import com.dp.petshome.service.DubboDemoService;

/**
 * @Dsecription 测试服Controller
 * @author DU
 */
@Controller
@RequestMapping("dubbo")
public class DubboDemoController {

	@Autowired
	protected DubboDemoService dubboDemoService;

	private static final Logger log = LoggerFactory.getLogger(DubboDemoController.class);

	/**
	 * produces属性：表示可接收的数据格式
	 */
	@PostMapping(value = "insertUser")
	@ResponseBody
	public Map<String, Boolean> insertUser(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Boolean> map = new HashMap<>(1);
		try {
			String name = request.getParameter("name");
			String age = request.getParameter("age");
			User user = new User();
			user.setName(name);
			if (StringUtils.isNotBlank(age)) {
				user.setAge(Integer.valueOf(age));
			}
			log.info("获取到的用户信息: {}", user);
			Boolean isSuccess = dubboDemoService.insertUser(user);
			map.put("Insert Result: ", isSuccess);
			log.info("新增用户信息结果: {}", isSuccess);
		} catch (Exception e) {
			log.error("新增用户异常: {}", e.getMessage());
		}
		return map;
	}

	@GetMapping(value = "selectUsers")
	@ResponseBody
	public Map<String, List<User>> getInfo(HttpServletRequest request, HttpServletResponse response) {

		Map<String, List<User>> map = new HashMap<>(1);
		try {
			log.info("开始查询用户列表...");
			List<User> selectUsers = dubboDemoService.selectUsers();
			map.put("Search Results: ", selectUsers);
			log.info("查询用户列表结果: {}", selectUsers);
		} catch (Exception e) {
			log.error("查询用户异常: {}", e.getMessage());
		}
		return map;
	}

}
