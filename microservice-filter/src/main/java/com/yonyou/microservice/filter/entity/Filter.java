package com.yonyou.microservice.filter.entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "gate_filter")
public class Filter {
    @Id
	private Integer id;          
	private String name;          
	private String type;          
	private String orders;          
	private String execSetting;          
	private String canarySetting;     
	private String version;     
	private String script;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public String getExecSetting() {
		return execSetting;
	}

	public void setExecSetting(String execSetting) {
		this.execSetting = execSetting;
	}

	public String getCanarySetting() {
		return canarySetting;
	}

	public void setCanarySetting(String canarySetting) {
		this.canarySetting = canarySetting;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

}
