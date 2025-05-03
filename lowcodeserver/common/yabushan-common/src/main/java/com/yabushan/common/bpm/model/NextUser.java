package com.yabushan.common.bpm.model;

import org.springframework.stereotype.Component;

/**
 * @ClassName:     NextUser.java
 * @Description:   流程管理的下一步人
 * @author:        yl
 * @version:       V1.0  
 * @Date:           2019年3月4日 下午5:11:44
 */
@Component
public class NextUser {
	private String type;//eg:user
	private String users;//eg:caijiamiao,shuai
	private String secretary;//eg:用户ID|部门名称，多个用逗号分割
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public String getSecretary() {
		return secretary;
	}
	public void setSecretary(String secretary) {

		this.secretary = secretary== null ? "" : secretary;
	}

	public NextUser(String type, String users, String secretary) {
		this.type = type;
		this.users = users;
		this.secretary = secretary;
	}

	public NextUser() {
		super();
	}

	@Override
	public String toString() {
		return "NextUser{" +
				"type='" + type + '\'' +
				", users='" + users + '\'' +
				", secretary='" + secretary + '\'' +
				'}';
	}
}
