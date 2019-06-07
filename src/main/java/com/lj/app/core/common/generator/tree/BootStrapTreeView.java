package com.lj.app.core.common.generator.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * bootstrap树
 *
 */
public class BootStrapTreeView {

  private String id;
  private String text;
  private String icon;
  private String parentId;

  private String nodeId;

  private String href;

  // checked: true,
  // disabled: true,
  // expanded: true,
  // selected: true
  private Map<String, Boolean> state = new HashMap<>();

  private List<BootStrapTreeView> nodes;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Map<String, Boolean> getState() {
    return state;
  }

  public void setState(Map<String, Boolean> state) {
    this.state = state;
  }

  public List<BootStrapTreeView> getNodes() {
    return nodes;
  }

  public void setNodes(List<BootStrapTreeView> nodes) {
    this.nodes = nodes;
  }

  /**
   * 添加子节点
   * 
   * @param childrenNode
   *          添加子节点
   */
  public void addNodes(BootStrapTreeView childrenNode) {
    if (null == nodes) {
      nodes = new ArrayList<>();
    }
    nodes.add(childrenNode);
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNodeId() {
    return nodeId;
  }

  public void setNodeId(String nodeId) {
    this.nodeId = nodeId;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

}