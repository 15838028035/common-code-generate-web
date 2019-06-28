package com.lj.app.core.common.generator.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lj.app.core.common.generator.GeneratorFacade;
import com.lj.app.core.common.generator.GeneratorProductAndConsumerFacade;
import com.lj.app.core.common.generator.GeneratorProperties;
import com.lj.app.core.common.generator.TableViewData;
import com.lj.app.core.common.generator.bean.GenerateTableDataVO;
import com.lj.app.core.common.generator.bean.TableColumnVO;
import com.lj.app.core.common.generator.provider.db.DbTableFactory;
import com.lj.app.core.common.generator.provider.db.model.Column;
import com.lj.app.core.common.generator.provider.db.model.Table;
import com.lj.app.core.common.generator.tree.BootStrapTreeView;
import com.lj.app.core.common.generator.tree.BootStrapTreeViewCheck;
import com.lj.app.core.common.generator.util.StringUtil;
import com.lj.app.core.common.generator.util.ZipUtils;

/**
 * 文件生成controller
 */
@Controller
@RequestMapping("/base/generator")
public class GeneratorRest {
  
    private Logger logger = LoggerFactory.getLogger(getClass());
    
	/**
	 * 模板输出目录
	 */
    @Value("${outRoot}")
   private String outRoot;
  /**
   * 列表
   */
  @ResponseBody
  @GetMapping("/getInitConfig")
  public Properties getInitConfig()  {
      return GeneratorProperties.getProperties();
  }
  
  @ResponseBody
  @GetMapping("/getGenerateTree")
    public String getGenerateTree(String jdbcUrl,String jdbcUsername, String jdbcPasswordText, HttpServletRequest request) throws Exception {
      String jsonData ="";

      
      if(jdbcUrl!=null && !"".equals(jdbcUrl)) {
        GeneratorProperties.setProperty("jdbc.url", jdbcUrl);
      }
      
      GeneratorProperties.setProperty("jdbc.username", jdbcUsername);
      GeneratorProperties.setProperty("jdbc.password", jdbcPasswordText);
      
      List<Table> result = DbTableFactory.getInstance().getAllTables();
      
      List<BootStrapTreeView> treeNodeList = new ArrayList<>();
      BootStrapTreeView bootStrapTreeView = new BootStrapTreeView();
      BootStrapTreeViewCheck bootStrapTreeViewCheck = new BootStrapTreeViewCheck(null,"ROOT");
      
      for(Table table: result){
          bootStrapTreeView = new BootStrapTreeView();
          bootStrapTreeView.setId(table.getSqlName());
          bootStrapTreeView.setText(table.getSqlName());
          bootStrapTreeView.setParentId("0");
          treeNodeList.add(bootStrapTreeView);
          
          
          bootStrapTreeViewCheck.addNode(table.getSqlName(), false, table.getSqlName());
      }
      
      jsonData=  bootStrapTreeViewCheck.toJsonString();
      if (StringUtil.isBlank(jsonData)) {
        jsonData = "";
      }
      return jsonData;
  }
  
  /**
   *获得表具体信息
   */
  @ResponseBody
  @GetMapping("/tableInfo")
  public Map<String,Object> tableInfo(String jdbcUrl,String jdbcUsername, String jdbcPasswordText, String tableStr) throws Exception {
     
    
    if(jdbcUrl!=null && !"".equals(jdbcUrl)) {
      GeneratorProperties.setProperty("jdbc.url", jdbcUrl);
    }
   
    GeneratorProperties.setProperty("jdbc.username", jdbcUsername);
    GeneratorProperties.setProperty("jdbc.password", jdbcPasswordText);
    
      List<Map<String,Object>> restultList = new ArrayList<>();
      
      Table  queryTable = DbTableFactory.getInstance().releaseConnection().getTable(tableStr);
      
      Set<Column> tableColumns = queryTable.getColumns();
      Iterator<Column> it = tableColumns.iterator();
      
      int i=0;
      while(it.hasNext()){
        Column columnObj = it.next();
        
        Map<String,Object> map = new HashMap<>();
        map.put("sqlName", columnObj.getSqlName());
        map.put("sqlTypeName", columnObj.getSqlTypeName());
        
        map.put("size", columnObj.getSize());
        map.put("remarks", columnObj.getRemarks());
        map.put("listIsShow", true);
        map.put("listColumnIsShow", true);
        
        if(columnObj.getIsStringColumn()){
            map.put("listMatchType", "like");
          }else {
            map.put("listMatchType", "=");
          }
        
        map.put("formIsShow", true);
        
        map.put("formShowType", "input");
        map.put("sortNo", i+1);
        
        restultList.add(map);
        i++;
      }
      
      
      Map<String,Object> map = new HashMap<>();
      map.put("count", i);
      map.put("data", restultList);
      
      return map;
  }
  
  /**
   * 列表
   */
  @ResponseBody
  @GetMapping("/page")
  public Map<String,Object> list(String jdbcUrl,String jdbcUsername, String jdbcPasswordText, String tableStr,
      String basepackageStr) throws Exception {
    
    if(jdbcUrl!=null && !"".equals(jdbcUrl)) {
      GeneratorProperties.setProperty("jdbc.url", jdbcUrl);
    }
   
    GeneratorProperties.setProperty("jdbc.username", jdbcUsername);
    GeneratorProperties.setProperty("jdbc.password", jdbcPasswordText);
    
      List<Table> result = DbTableFactory.getInstance().getAllTables();
      int total =result.size();
      
      List<Map<String,Object>> restultList = new ArrayList<>();
      
      for (int i = 0; i < result.size(); i++) {
        Table table =  result.get(i);
        
        Map<String,Object> map = new HashMap<>();
        map.put("sqlName", table.getSqlName());
        map.put("remarks", table.getRemarks());
        
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
  @GetMapping("/code")
  public void code(HttpServletRequest request, HttpServletResponse response, String jdbcUrl,String jdbcUsername, String jdbcPasswordText,
      String tableStr, String basepackageStr, String template) throws Exception {

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
      
      try(
              ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
              ZipOutputStream zip = new ZipOutputStream(outputStream);
              ){
      GeneratorTestHelper.generateByTable(g,zip, tableStr);
      
      byte[] data = outputStream.toByteArray();

      response.reset();
      response.setHeader("Content-Disposition", "attachment; filename=\"common-code-gencode.zip\"");
      response.addHeader("Content-Length", "" + data.length);
      response.setContentType("application/octet-stream; charset=UTF-8");

      IOUtils.write(data, response.getOutputStream());
      }catch(Exception e) {
          logger.error("生成文件异常",e);
      }
  }
  
  /**
   * 生成代码
   */
  @PostMapping(value="/codeV5")
  @ResponseBody
  public Map<String,Object> codeV5(HttpServletRequest request, HttpServletResponse response,@RequestBody GenerateTableDataVO generateTableDataVO) throws Exception {
      Map<String,Object> retMap  = new HashMap<>();

      GeneratorProductAndConsumerFacade g = new GeneratorProductAndConsumerFacade();
      g.clean();
      
      if(generateTableDataVO.getJdbcUrl()!=null && !"".equals(generateTableDataVO.getJdbcUrl())) {
        GeneratorProperties.setProperty("jdbc.url", URLDecoder.decode(generateTableDataVO.getJdbcUrl(),"UTF-8"));
      }
     
      GeneratorProperties.setProperty("jdbc.username", generateTableDataVO.getJdbcUsername());
      GeneratorProperties.setProperty("jdbc.password", generateTableDataVO.getJdbcPasswordText());
      
      if(generateTableDataVO.getBasepackageStr()!=null && !"".equals(generateTableDataVO.getBasepackageStr())) {
        GeneratorProperties.setProperty("basepackage", generateTableDataVO.getBasepackageStr());
      }
      
      if(generateTableDataVO.getTemplate()!=null && !"".equals(generateTableDataVO.getTemplate())) {
          g.getGenerator().setTemplateRootDir( URLDecoder.decode(generateTableDataVO.getTemplate(),"UTF-8"));
        }
      
      GeneratorProperties.setProperty("basepackage_dir",
      GeneratorProperties.getProperty("basepackage").replace(".", "/"));
      //确保有序
      Set<Column> tableColumSet = new LinkedHashSet<>(); 
     
        GeneratorProperties.setProperty("basepackage", generateTableDataVO.getBasepackageStr());
        GeneratorProperties.setProperty("basepackage_dir",
              GeneratorProperties.getProperty("basepackage").replace(".", "/"));
         
            Table  table = DbTableFactory.getInstance().releaseConnection().getTable(generateTableDataVO.getTableStr());
            
            Set<Column> tableColumns = table.getColumns();
            
            Iterator<Column> it = tableColumns.iterator();
            int i = 0;
            while(it.hasNext()){
                Column columnObj = it.next();
                TableColumnVO tableColumnVO = generateTableDataVO.getTableColumSet().get(i);
                BeanUtils.copyProperties(columnObj, tableColumnVO);
                tableColumSet.add(columnObj);
                  i++;
            }
            
            
            //排序
            Set<Column> sortSet = new TreeSet<Column>((o1, o2) -> o1.getSortNo().compareTo(o2.getSortNo()));
            sortSet.addAll(tableColumSet);
            
            
          table.setColumns(sortSet);
          
          g.generateByTable(g.createGeneratorForDbTable(), table);
          
      
      try{
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      ZipOutputStream zip = new ZipOutputStream(outputStream);
      
      //文件输出目录
      File tempDir =new File(outRoot);
      TableViewData tableViewData = new TableViewData();
      tableViewData.setBasepackage(generateTableDataVO.getBasepackageStr());
      tableViewData.setTableName(generateTableDataVO.getTableStr());
      GeneratorTestHelper.generateByTable(tempDir,g,zip, tableViewData);
      retMap.put("respData", tempDir);
      
      retMap.put("respCode", "1");
      retMap.put("respMsg", "成功");

      }catch(Exception e) {
          logger.error("生成文件异常",e);
          retMap.put("respCode", "0");
          retMap.put("respMsg", "代码生成失败");
      }
      
      return retMap;
  }
  
  /**
   * 下载代码
   */
  @GetMapping("/downloadfile")
  public void downloadfile(HttpServletRequest request, HttpServletResponse response, String outputTempDir)  {
      
      try( ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
              ZipOutputStream zip = new ZipOutputStream(outputStream);
          ){
          File tempDir = new File(outputTempDir);
          ZipUtils.doCompress(tempDir, zip);
          
      byte[] data = outputStream.toByteArray();

      response.reset();
      response.setHeader("Content-Disposition", "attachment; filename=\"common-code-gencode.zip\"");
      response.addHeader("Content-Length", "" + data.length);
      response.setContentType("application/octet-stream; charset=UTF-8");

      IOUtils.write(data, response.getOutputStream());
      }catch(Exception e) {
          logger.error("生成文件异常",e);
      }
  }
}
