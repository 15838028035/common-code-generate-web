package com.lj.app.core.common.generator.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lj.app.core.common.generator.util.SCaptcha;

@RestController()
public class DrawVerCodeController {
	
	/**
     * @description 生成图片验证码
     */
    @RequestMapping(value = "/drawVerCode")
    public void drawVerCode(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //实例生成验证码对象
        SCaptcha instance = new SCaptcha();
        //将验证码存入session
        session.setAttribute("IMG_CODE", instance.getCode());
        //向页面输出验证码图片
        instance.write(response.getOutputStream());
    }
    
}
