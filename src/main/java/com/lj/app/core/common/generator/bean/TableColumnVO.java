package com.lj.app.core.common.generator.bean;

public class TableColumnVO {

    /**
     * SQL表名称
     */
    private String sqlName;
    
    /**
     * 
     */
    private String sqlTypeName;
    
    /**
     * 
     */
    private int size;
    
    private  String remarks;
    
    /**
     * 列表查询字段是否显示
     */
    private Boolean listIsShow = true;
    
    /**
     * 列表列是否显示
     */
    private Boolean listColumnIsShow = true;
    
    /**
     * 列表查詢類型 = like 
     */
    private String listMatchType = "=";
    
    /**
     * 表单字段是否显示
     */
    private Boolean formIsShow = true;
    
    /**
     * 表单查詢類型 input textarea select radio  date checkbox file
     */
    private String formShowType = "input";
    
    /**
     * 排序编号
     */
    private Integer sortNo;

    public String getSqlName() {
        return sqlName;
    }

    public void setSqlName(String sqlName) {
        this.sqlName = sqlName;
    }

    public String getSqlTypeName() {
        return sqlTypeName;
    }

    public void setSqlTypeName(String sqlTypeName) {
        this.sqlTypeName = sqlTypeName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getListIsShow() {
        return listIsShow;
    }

    public void setListIsShow(Boolean listIsShow) {
        this.listIsShow = listIsShow;
    }

    public Boolean getListColumnIsShow() {
        return listColumnIsShow;
    }

    public void setListColumnIsShow(Boolean listColumnIsShow) {
        this.listColumnIsShow = listColumnIsShow;
    }

    public String getListMatchType() {
        return listMatchType;
    }

    public void setListMatchType(String listMatchType) {
        this.listMatchType = listMatchType;
    }

    public Boolean getFormIsShow() {
        return formIsShow;
    }

    public void setFormIsShow(Boolean formIsShow) {
        this.formIsShow = formIsShow;
    }

    public String getFormShowType() {
        return formShowType;
    }

    public void setFormShowType(String formShowType) {
        this.formShowType = formShowType;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }
    
}
