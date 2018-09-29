package com.lj.app.core.common.generator.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lj.app.core.common.generator.GeneratorFacade;
import com.lj.app.core.common.generator.GeneratorProperties;
import com.lj.app.core.common.generator.provider.db.DbTableFactory;
import com.lj.app.core.common.generator.provider.db.model.Table;

/**
 * Created by ace on 2017/8/25.
 */
@Controller
@RequestMapping("/base/generator")
public class GeneratorRest {
  
  /**
   * 列表
   */
  @ResponseBody
  @RequestMapping("/getInitConfig")
  public Properties getInitConfig() throws Exception {
      return GeneratorProperties.getProperties();
  }
  
  /**
   * 列表
   */
  @ResponseBody
  @RequestMapping("/page")
  public Map list(String jdbcUrl,String jdbcUsername, String jdbcPasswordText, String tableStr,
      String basepackageStr) throws Exception {
    
    if(jdbcUrl!=null && !"".equals(jdbcUrl)) {
      GeneratorProperties.setProperty("jdbc.url", jdbcUrl);
    }
   
    GeneratorProperties.setProperty("jdbc.username", jdbcUsername);
    GeneratorProperties.setProperty("jdbc.password", jdbcPasswordText);
    
      List result = DbTableFactory.getInstance().getAllTables();
      int total =result.size();
      
      List<Map<String,Object>> restultList = new ArrayList<>();
      
      for (int i = 0; i < result.size(); i++) {
        Table table = (Table) result.get(i);
        
        Map<String,Object> map = new HashMap<>();
        map.put("sqlName", table.getSqlName());
        map.put("remarks", table.getRemarks());
        map.put("sqlName", table.getSqlName());
        
        restultList.add(map);
      }
      
      
      Map<String,Object> map = new HashMap<String,Object>();
      map.put("total", total);
      map.put("rows", restultList);
      
      return map;
  }

  /**
   * 生成代码
   */
  @RequestMapping("/code")
  public void code(HttpServletRequest request, HttpServletResponse response, String jdbcUrl,String jdbcUsername, String jdbcPasswordText,
      String tableStr, String basepackageStr, String template) throws Exception {
      String[] tableNames = new String[]{};


      GeneratorFacade g = new GeneratorFacade();
      g.clean();
      
      if(template!=null && !"".equals(template)) {
        g.getGenerator().setTemplateRootDir(template);
      }
      
      if(jdbcUrl!=null && !"".equals(jdbcUrl)) {
        GeneratorProperties.setProperty("jdbc.url", jdbcUrl);
      }
     
      GeneratorProperties.setProperty("jdbc.username", jdbcUsername);
      GeneratorProperties.setProperty("jdbc.password", jdbcPasswordText);
      
      if(basepackageStr!=null && !"".equals(basepackageStr)) {
        GeneratorProperties.setProperty("basepackage", basepackageStr);
      }
      
      if(template!=null && !"".equals(template)) {
        GeneratorProperties.setProperty("template", template);
      }
      
      GeneratorProperties.setProperty("basepackage_dir",
      GeneratorProperties.getProperty("basepackage").replace(".", "/"));
      
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      ZipOutputStream zip = new ZipOutputStream(outputStream);
      
      GeneratorTestHelper.generateByTable(g,zip, tableStr);
    
      IOUtils.closeQuietly(zip);
      
      byte[] data = outputStream.toByteArray();

      response.reset();
      response.setHeader("Content-Disposition", "attachment; filename=\"common-code-gencode.zip\"");
      response.addHeader("Content-Length", "" + data.length);
      response.setContentType("application/octet-stream; charset=UTF-8");

      IOUtils.write(data, response.getOutputStream());
  }
}
