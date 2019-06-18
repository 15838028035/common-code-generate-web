package com.lj.app.core.common.generator.bean;

import java.util.ArrayList;
import java.util.List;

/**
* 代码生产器临时数据VO
*/
public class GenerateTableDataVO{
   
    /**
     * 数据库url
     */
    private String jdbcUrl;
    /**
     * 账号
     */
    String jdbcUsername;
    /**
     * 密码
     */
    String jdbcPasswordText;
    /**
     * 包名称
     */
    String basepackageStr;
    /**
     * 模板目录
     */
    String template;
    
    /**
     * 表名称
     */
    private String tableStr;
	
    private List<TableColumnVO> tableColumSet = new ArrayList<>();

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getJdbcPasswordText() {
        return jdbcPasswordText;
    }

    public void setJdbcPasswordText(String jdbcPasswordText) {
        this.jdbcPasswordText = jdbcPasswordText;
    }

    public String getBasepackageStr() {
        return basepackageStr;
    }

    public void setBasepackageStr(String basepackageStr) {
        this.basepackageStr = basepackageStr;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<TableColumnVO> getTableColumSet() {
        return tableColumSet;
    }

    public void setTableColumSet(List<TableColumnVO> tableColumSet) {
        this.tableColumSet = tableColumSet;
    }

    public String getTableStr() {
        return tableStr;
    }

    public void setTableStr(String tableStr) {
        this.tableStr = tableStr;
    } 
    
}

