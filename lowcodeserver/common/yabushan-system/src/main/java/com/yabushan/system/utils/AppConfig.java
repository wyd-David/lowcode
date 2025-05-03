package com.yabushan.system.utils;

/**
 * Application Configurations Util
 * 
 * @author Wang Wen
 */
public class AppConfig {

	private static String appName;
	private static String isSSO;
	private static String portalSys;
	private static String portalUrl;
	private static String portalAdmin;
	private static String portalPwd;
	private static String portalACL;
	
	private static String dbDriver;
	private static String dbUrl;
	private static String dbUser;
	private static String dbPwd;
	
	
	private static String smsServerUrl;
	private static String smsServerUid;
	private static String smsServerPwd;
	
	private static String smsTerminlNo;
	private static String smsSystemNo;
	
	private static String mmsServerUrl;
	
	private static String meetingLiveInterval;
	
	private static String loginGreenPwd;

	public static String getAppName() {
		return appName;
	}
	
	public void setAppName(String value) {
		AppConfig.appName = value;
	}

	/**
	 * @return the isSSO
	 */
	public static String getIsSSO() {
		return isSSO;
	}

	/**
	 * @param isSSO the isSSO to set
	 */
	public void setIsSSO(String isSSO) {
		AppConfig.isSSO = isSSO;
	}

	/**
	 * @return the portalACL
	 */
	public static String getPortalACL() {
		return portalACL;
	}

	/**
	 * @param portalACL the portalACL to set
	 */
	public void setPortalACL(String portalACL) {
		AppConfig.portalACL = portalACL;
	}

	/**
	 * @return the portalAdmin
	 */
	public static String getPortalAdmin() {
		return portalAdmin;
	}

	/**
	 * @param portalAdmin the portalAdmin to set
	 */
	public void setPortalAdmin(String portalAdmin) {
		AppConfig.portalAdmin = portalAdmin;
	}

	/**
	 * @return the portalPwd
	 */
	public static String getPortalPwd() {
		return portalPwd;
	}

	/**
	 * @param portalPwd the portalPwd to set
	 */
	public void setPortalPwd(String portalPwd) {
		AppConfig.portalPwd = portalPwd;
	}

	/**
	 * @return the portalSys
	 */
	public static String getPortalSys() {
		return portalSys;
	}

	/**
	 * @param portalSys the portalSys to set
	 */
	public void setPortalSys(String portalSys) {
		AppConfig.portalSys = portalSys;
	}

	/**
	 * @return the portalUrl
	 */
	public static String getPortalUrl() {
		return portalUrl;
	}

	/**
	 * @param portalUrl the portalUrl to set
	 */
	public void setPortalUrl(String portalUrl) {
		AppConfig.portalUrl = portalUrl;
	}

	/**
	 * @return the dbDriver
	 */
	public static String getDbDriver() {
		return dbDriver;
	}

	/**
	 * @param dbDriver the dbDriver to set
	 */
	public void setDbDriver(String dbDriver) {
		AppConfig.dbDriver = dbDriver;
	}

	/**
	 * @return the dbPwd
	 */
	public static String getDbPwd() {
		return dbPwd;
	}

	/**
	 * @param dbPwd the dbPwd to set
	 */
	public void setDbPwd(String dbPwd) {
		AppConfig.dbPwd = dbPwd;
	}

	/**
	 * @return the dbUrl
	 */
	public static String getDbUrl() {
		return dbUrl;
	}

	/**
	 * @param dbUrl the dbUrl to set
	 */
	public void setDbUrl(String dbUrl) {
		AppConfig.dbUrl = dbUrl;
	}

	/**
	 * @return the dbUser
	 */
	public static String getDbUser() {
		return dbUser;
	}

	/**
	 * @param dbUser the dbUser to set
	 */
	public void setDbUser(String dbUser) {
		AppConfig.dbUser = dbUser;
	}

	/**
	 * @return the smsServerPwd
	 */
	public static String getSmsServerPwd() {
		return smsServerPwd;
	}

	/**
	 * @param smsServerPwd the smsServerPwd to set
	 */
	public void setSmsServerPwd(String smsServerPwd) {
		AppConfig.smsServerPwd = smsServerPwd;
	}

	/**
	 * @return the smsServerUid
	 */
	public static String getSmsServerUid() {
		return smsServerUid;
	}

	/**
	 * @param smsServerUid the smsServerUid to set
	 */
	public void setSmsServerUid(String smsServerUid) {
		AppConfig.smsServerUid = smsServerUid;
	}

	/**
	 * @return the smsServerUrl
	 */
	public static String getSmsServerUrl() {
		return smsServerUrl;
	}

	/**
	 * @param smsServerUrl the smsServerUrl to set
	 */
	public void setSmsServerUrl(String smsServerUrl) {
		AppConfig.smsServerUrl = smsServerUrl;
	}

	/**
	 * @return the smsSystemNo
	 */
	public static String getSmsSystemNo() {
		return smsSystemNo;
	}

	/**
	 * @param smsSystemNo the smsSystemNo to set
	 */
	public void setSmsSystemNo(String smsSystemNo) {
		AppConfig.smsSystemNo = smsSystemNo;
	}

	/**
	 * @return the smsTerminlNo
	 */
	public static String getSmsTerminlNo() {
		return smsTerminlNo;
	}

	/**
	 * @param smsTerminlNo the smsTerminlNo to set
	 */
	public void setSmsTerminlNo(String smsTerminlNo) {
		AppConfig.smsTerminlNo = smsTerminlNo;
	}

	/**
	 * @return the mmsServerUrl
	 */
	public static String getMmsServerUrl() {
		return mmsServerUrl;
	}

	/**
	 * @param mmsServerUrl the mmsServerUrl to set
	 */
	public void setMmsServerUrl(String mmsServerUrl) {
		AppConfig.mmsServerUrl = mmsServerUrl;
	}

	/**
	 * @return the meetingLiveInterval
	 */
	public static String getMeetingLiveInterval() {
		return meetingLiveInterval;
	}

	/**
	 * @param meetingLiveInterval the meetingLiveInterval to set
	 */
	public void setMeetingLiveInterval(String meetingLiveInterval) {
		AppConfig.meetingLiveInterval = meetingLiveInterval;
	}

	/**
	 * @return the loginGreenPwd
	 */
	public static String getLoginGreenPwd() {
		return loginGreenPwd;
	}

	/**
	 * @param loginGreenPwd the loginGreenPwd to set
	 */
	public void setLoginGreenPwd(String loginGreenPwd) {
		AppConfig.loginGreenPwd = loginGreenPwd;
	}
	
	

}