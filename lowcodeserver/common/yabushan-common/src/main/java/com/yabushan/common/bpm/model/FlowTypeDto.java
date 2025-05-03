package com.yabushan.common.bpm.model;

import java.io.Serializable;


/**
 * @ClassName:       FlowTypeDto
 * @Description:    流程类型
 * @author:            yl
 * @date:            2019年3月29日        上午9:22:52
 */
public class FlowTypeDto implements Serializable {
	private String  clsName;
	private String name;
	private String value;
	private Integer type;
	private String tips;

	private String url;
	private String tableName;

	private Integer flowType;
	private Integer isShow;
	private String flowName;

	public Integer getFlowType() {
		return flowType;
	}

	public void setFlowType(Integer flowType) {
		this.flowType = flowType;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	//	private EnumType currentEnum;
//	private EnumType nodeUserEnum;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public FlowTypeDto(String clsName, String name, String value, Integer type, String tips, String url, String tableName, Integer flowType, Integer isShow, String flowName) {
		this.clsName = clsName;
		this.name = name;
		this.value = value;
		this.type = type;
		this.tips = tips;
		this.url = url;
		this.tableName = tableName;
		this.flowType = flowType;
		this.isShow = isShow;
		this.flowName = flowName;
	}

	@Override
	public String toString() {
		return "FlowTypeDto{" +
				"clsName='" + clsName + '\'' +
				", name='" + name + '\'' +
				", value='" + value + '\'' +
				", type=" + type +
				", tips='" + tips + '\'' +
				", url='" + url + '\'' +
				", tableName='" + tableName + '\'' +
				", flowType=" + flowType +
				", isShow=" + isShow +
				", flowName='" + flowName + '\'' +
				'}';
	}

	public FlowTypeDto(String clsName,String name, String value, Integer type, String url,
			String tableName) {
		super();
		this.setClsName(clsName);
		this.name = name;
		this.value = value;
		this.type = type;
		this.url = url;
		this.tableName = tableName;
	}
	public FlowTypeDto() {
		super();
	}
	public String getClsName() {
		return clsName;
	}
	public void setClsName(String clsName) {
		this.clsName = clsName;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}
}
