package com.yabushan.common.bpm.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * @ClassName:       UserDto
 * @Description:    TODO用户信息封装
 * @author:            yl
 * @date:            2019年3月13日        下午3:26:50
 */
public class UserDto implements Serializable,UserDetails{
	private static final long serialVersionUID = 7771773386823940966L;
	private String currentAuthority = "admin";
	private String userName;
	private String id;
	private String type;
	private String loginType;
	private String password;
	private String avatar;
	private List<? extends GrantedAuthority> authorities;
	
	public UserDto(){};
	
	public UserDto(String userName, String password){
		this.setUserName(userName);
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCurrentAuthority() {
		return currentAuthority;
	}

	public void setCurrentAuthority(String currentAuthority) {
		this.currentAuthority = currentAuthority;
	}
	
  public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

   @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setGrantedAuthorities(List<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
    @JsonIgnore
	@Override
	public String getUsername() {
		return userName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
}
