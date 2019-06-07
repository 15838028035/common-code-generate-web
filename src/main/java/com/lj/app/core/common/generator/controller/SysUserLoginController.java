package com.lj.app.core.common.generator.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lj.app.core.common.generator.bean.AdminLoginUser;
import com.lj.app.core.common.generator.util.JwtUtil;

/**
 * 管理
 */
@RestController()
public class SysUserLoginController extends BaseController {
    
	/**
	 * token超时时间
	 */
	@Value("${jtwTokenTimeOut}")
	private Long jtwTokenTimeOut;

	 /**
   * 后台用户登录
   * 
   * @return 自定义结果对象
   */
  @RequestMapping(value = "/api/adminLogin", method = RequestMethod.POST)
  public Map adminLogin(AdminLoginUser user, HttpServletRequest request, RedirectAttributes attributes) {

    Map<String,Object> map = new HashMap<>();
    map.put("respCode",1);
    map.put("respMsg","登录成功");

    if (user.getUserNo() == null || "".equals(user.getUserNo()) || user.getUserPass() == null
        || "".equals(user.getUserPass()) ) {
      map.put("respCode",0);
      map.put("respMsg","参数为空");
      return map;
    }
    
    if("admin".equals(user.getUserNo()) && "123456".equals(user.getUserPass())){
        map.put("respCode",1);
        map.put("respMsg","登录成功");
        String token = JwtUtil.generateToken("admin","1",jtwTokenTimeOut);
        map.put("token",token);   
        return map;
    }
    
    map.put("respCode",0);
    map.put("respMsg","用户名或密码错误");
      return map;
  }

}
