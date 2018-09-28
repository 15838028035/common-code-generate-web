package com.lj.app.core.common.generator.controller;

import java.io.ByteArrayOutputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lj.app.core.common.generator.GeneratorFacade;
import com.lj.app.core.common.generator.GeneratorProperties;

/**
 * Created by ace on 2017/8/25.
 */
@Controller
@RequestMapping("/base/generator")
public class GeneratorRest {

    /**
     * 生成代码
     */
    @RequestMapping("/code")
    public void code(HttpServletRequest request, HttpServletResponse response, String jdbcUrl,String jdbcUsername, String jdbcPasswordText, String tableStr, String basepackageStr) throws Exception {
        String[] tableNames = new String[]{};

    
        String outRootStr = "";
        

        System.out.println("basepackage_dir:" + GeneratorProperties.getProperty("basepackage_dir"));

        GeneratorFacade g = new GeneratorFacade();
        g.clean();
        
        GeneratorProperties.setProperty("jdbc.url", jdbcUrl);
        GeneratorProperties.setProperty("jdbc.username", jdbcUsername);
        GeneratorProperties.setProperty("jdbc.password", jdbcPasswordText);
        
        GeneratorProperties.setProperty("basepackage", basepackageStr);
        GeneratorProperties.setProperty("basepackage_dir",
            GeneratorProperties.getProperty("basepackage").replace(".", "/"));
        GeneratorProperties.setProperty("outRoot", outRootStr);
        

        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        
        GeneratorTestHelper.generateByTable(g,zip, tableStr);
      
      byte[] data = outputStream.toByteArray();
        

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"ag-admin-code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }
}
