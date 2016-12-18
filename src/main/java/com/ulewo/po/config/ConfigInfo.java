package com.ulewo.po.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * ClassName: ConfigInfo
 * date: 2015年8月9日 下午12:03:51 
 * @author 不错啊
 * @version 
 * @since JDK 1.7
 */
@Component("configInfo")
public class ConfigInfo {
	/**
	 *找回密码发送的邮箱地址
	 */
	@Value("#{applicationProperties['ulewo.email.findemail']}")
	private String findemail;

	/**
	 * 找回密码发送邮箱的密码
	 */
	@Value("#{applicationProperties['ulewo.emial.findpwd']}")
	private String findpwd;

	@Value("#{applicationProperties['ulewo.solr.server.url']}")
	private String solrServerUrl;

	@Value("#{applicationProperties['ulewo.solr.time.out']}")
	private int solrTimeOut;

	@Value("#{applicationProperties['ulewo.solr.max.totalconnections']}")
	private int maxTotalConnections;

	@Value("#{applicationProperties['ulewo.solr.max.connections.per.host']}")
	private int maxConnectionsPerHost;

	@Value("#{applicationProperties['ulewo.solr.open']}")
	private boolean openSolr;
	
	@Value("#{applicationProperties['ulewo.sshd.url']}")
	private String sshdUrl;
	
	@Value("#{applicationProperties['ulewo.sshd.user']}")
	private String sshdUser;

	@Value("#{applicationProperties['ulewo.sshd.port']}")
	private String sshdPort;
	
	@Value("#{applicationProperties['ulewo.sshd.password']}")
	private String sshdPassword;

	public String getSshdPort() {
		return sshdPort;
	}

	public void setSshdPort(String sshdPort) {
		this.sshdPort = sshdPort;
	}

	public String getSshdUrl() {
		return sshdUrl;
	}

	public void setSshdUrl(String sshdUrl) {
		this.sshdUrl = sshdUrl;
	}

	public String getSshdUser() {
		return sshdUser;
	}

	public void setSshdUser(String sshdUser) {
		this.sshdUser = sshdUser;
	}

	public String getSshdPassword() {
		return sshdPassword;
	}

	public void setSshdPassword(String sshdPassword) {
		this.sshdPassword = sshdPassword;
	}

	public String getFindemail() {
		return findemail;
	}

	public void setFindemail(String findemail) {
		this.findemail = findemail;
	}

	public String getFindpwd() {
		return findpwd;
	}

	public void setFindpwd(String findpwd) {
		this.findpwd = findpwd;
	}

	public String getSolrServerUrl() {
		return solrServerUrl;
	}

	public void setSolrServerUrl(String solrServerUrl) {
		this.solrServerUrl = solrServerUrl;
	}

	public int getSolrTimeOut() {
		return solrTimeOut;
	}

	public void setSolrTimeOut(int solrTimeOut) {
		this.solrTimeOut = solrTimeOut;
	}

	public int getMaxTotalConnections() {
		return maxTotalConnections;
	}

	public void setMaxTotalConnections(int maxTotalConnections) {
		this.maxTotalConnections = maxTotalConnections;
	}

	public int getMaxConnectionsPerHost() {
		return maxConnectionsPerHost;
	}

	public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}

	public boolean isOpenSolr() {
		return openSolr;
	}

	public void setOpenSolr(boolean openSolr) {
		this.openSolr = openSolr;
	}

}