package com.ultrapower.eoms.ultrasm.model;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.annotations.GenericGenerator;

import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;

/**
 * UserInfo entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "BS_T_SM_USER")
public class UserInfo implements java.io.Serializable {

	// Fields    

	private String pid;
	private String jobnum;
	private String loginname;
	private String fullname;
	private String pyname;
	private String pwd;
	private String sex;
	private String position;
	private String type;
	private String mobile;
	private String phone;
	private String fax;
	private String email;
	private Long status;
	private Long ordernum;
	private String image;
	private String locationzone;
	private String depid;
	private String depname;
	private String groupid;
	private String groupname;
	private String ptdepid;
	private String ptdepname;
	private String profession;
	private String creater;
	private Long createtime;
	private String lastmodifier;
	private Long lastmodifytime;
	private Long lastlogintime;
	private String ipaddress;
	private String msn;
	private String qq;
	private String systemskin;
	private String remark;
	//数据库中不做存储
	private String companyId;
	private String companyName;
	//下面这几个属性是透明的，主要用于在页面上显示
	private String createtimeshow;
	private String lastmodifytimeshow;
	private String lastlogintimeshow;
	private String createrShow;
	private Long isVip; //是否vip
	private String station; //员工工位
	private String oaNumber; //员工oa号
	private String holidayState;

	//不做存储
	private String cdbUnitName;//国开单位名
	private String cdbDepName;//国开部门名
	private String groupDepNames;//
	private String cdbTeamId;//国开行团队名称
	private String cdbTeamName;//国开行团队名称
	
	// Constructors

	/** default constructor */
	public UserInfo() {
	}

	/** minimal constructor */
	public UserInfo(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public UserInfo(String pid, String jobnum, String loginname, String fullname, String pyname, String pwd, String sex,
			String position, String type, String mobile, String phone,
			String fax, String email, Long status, Long ordernum, String image, String locationzone,
			String depid, String depname, String groupid, String groupname,
			String ptdepid, String ptdepname, String profession, String creater, Long createtime,
			String lastmodifier, Long lastmodifytime, Long lastlogintime,
			String ipaddress, String msn, String qq, String systemskin, String remark,
			Long isVip, String station,String oaNumber
			) {
		this.pid = pid;
		this.jobnum = jobnum;
		this.loginname = loginname;
		this.fullname = fullname;
		this.pyname = pyname;
		this.pwd = pwd;
		this.sex = sex;
		this.position = position;
		this.type = type;
		this.mobile = mobile;
		this.phone = phone;
		this.fax = fax;
		this.email = email;
		this.status = status;
		this.ordernum = ordernum;
		this.image = image;
		this.locationzone = locationzone;
		this.depid = depid;
		this.depname = depname;
		this.groupid = groupid;
		this.groupname = groupname;
		this.ptdepid = ptdepid;
		this.ptdepname = ptdepname;
		this.profession = profession;
		this.creater = creater;
		this.createtime = createtime;
		this.lastmodifier = lastmodifier;
		this.lastmodifytime = lastmodifytime;
		this.lastlogintime = lastlogintime;
		this.ipaddress = ipaddress;
		this.msn = msn;
		this.qq = qq;
		this.systemskin = systemskin;
		this.remark = remark;
		this.isVip = isVip;
		this.station = station;
		this.oaNumber = oaNumber;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "assigned")
	@Column(name = "PID", unique = true, nullable = false, insertable = true, updatable = true, length = 50)
	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Column(name = "JOBNUM", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getJobnum() {
		return jobnum;
	}

	public void setJobnum(String jobnum) {
		this.jobnum = jobnum;
	}
	
	@Column(name = "LOGINNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 60)
	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "HolidayState", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getHolidayState() {
		return this.holidayState;
	}

	public void setHolidayState(String holidayState) {
		this.holidayState = holidayState;
	}
	@Column(name = "FULLNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Column(name = "PYNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getPyname() {
		return pyname;
	}

	public void setPyname(String pyname) {
		this.pyname = pyname;
	}

	@Column(name = "PWD", unique = false, nullable = true, insertable = true, updatable = true, length = 120)
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "SEX", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "POSITION", unique = false, nullable = true, insertable = true, updatable = true, length = 60)
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "TYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "MOBILE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "PHONE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "FAX", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "EMAIL", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "STATUS", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "ORDERNUM", unique = false, nullable = true, insertable = true, updatable = true, precision = 5, scale = 0)
	public Long getOrdernum() {
		return this.ordernum;
	}

	public void setOrdernum(Long ordernum) {
		this.ordernum = ordernum;
	}

	@Column(name = "IMAGE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Column(name = "LOCATIONZONE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getLocationzone() {
		return locationzone;
	}

	public void setLocationzone(String locationzone) {
		this.locationzone = locationzone;
	}
	
	@Column(name = "DEPID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getDepid() {
		return this.depid;
	}

	public void setDepid(String depid) {
		this.depid = depid;
	}

	@Column(name = "DEPNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getDepname() {
		return this.depname;
	}

	public void setDepname(String depname) {
		this.depname = depname;
	}

	@Column(name = "GROUPID", unique = false, nullable = true, insertable = true, updatable = true, length = 1200)
	public String getGroupid() {
		return this.groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	@Column(name = "GROUPNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 3000)
	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	@Column(name = "PTDEPID", unique = false, nullable = true, insertable = true, updatable = true, length = 1200)
	public String getPtdepid() {
		return this.ptdepid;
	}

	public void setPtdepid(String ptdepid) {
		this.ptdepid = ptdepid;
	}

	@Column(name = "PTDEPNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 3000)
	public String getPtdepname() {
		return this.ptdepname;
	}

	public void setPtdepname(String ptdepname) {
		this.ptdepname = ptdepname;
	}

	@Column(name = "PROFESSION", unique = false, nullable = true, insertable = true, updatable = true, length = 1200)
	public String getProfession() {
		return this.profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	@Column(name = "CREATER", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	@Column(name = "CREATETIME", unique = false, nullable = true, insertable = true, updatable = true, precision = 15, scale = 0)
	public Long getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Long createtime) {
		if(createtime == null)
			return;
		this.createtime = createtime;
		Date temp = new Date(createtime*1000);
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.setCreatetimeshow(fm.format(temp));
	}

	@Column(name = "LASTMODIFIER", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getLastmodifier() {
		return this.lastmodifier;
	}

	public void setLastmodifier(String lastmodifier) {
		this.lastmodifier = lastmodifier;
	}

	@Column(name = "LASTMODIFYTIME", unique = false, nullable = true, insertable = true, updatable = true, precision = 15, scale = 0)
	public Long getLastmodifytime() {
		return this.lastmodifytime;
	}

	public void setLastmodifytime(Long lastmodifytime) {
		if(lastmodifytime == null)
			return;
		this.lastmodifytime = lastmodifytime;
		Date temp = new Date(lastmodifytime*1000);
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.setLastmodifytimeshow(fm.format(temp));
	}

	@Column(name = "LASTLOGINTIME", unique = false, nullable = true, insertable = true, updatable = true, precision = 15, scale = 0)
	public Long getLastlogintime() {
		return this.lastlogintime;
	}

	public void setLastlogintime(Long lastlogintime) {
		if(lastlogintime == null)
			return;
		this.lastlogintime = lastlogintime;
		Date temp = new Date(lastlogintime*1000);
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.setLastlogintimeshow(fm.format(temp));
	}

	@Column(name = "IPADDRESS", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getIpaddress() {
		return this.ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	@Column(name = "MSN", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getMsn() {
		return this.msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	@Column(name = "QQ", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "SYSTEMSKIN", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getSystemskin() {
		return systemskin;
	}

	public void setSystemskin(String systemskin) {
		this.systemskin = systemskin;
	}

	@Column(name = "REMARK", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Transient
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Transient
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Transient
	public String getCreatetimeshow() {
		return createtimeshow;
	}
	
	public void setCreatetimeshow(String createtimeshow) {
		this.createtimeshow = createtimeshow;
	}
	
	@Transient
	public String getLastmodifytimeshow() {
		return lastmodifytimeshow;
	}

	public void setLastmodifytimeshow(String lastmodifytimeshow) {
		this.lastmodifytimeshow = lastmodifytimeshow;
	}
	
	@Transient
	public String getLastlogintimeshow() {
		return lastlogintimeshow;
	}

	public void setLastlogintimeshow(String lastlogintimeshow) {
		this.lastlogintimeshow = lastlogintimeshow;
	}
	
	@Transient
	public String getCreaterShow()
	{
		return this.createrShow;
	}
	
	public void setCreaterShow(String createrShow)
	{
		this.createrShow = createrShow;
	}

	@Column(name = "ISVIP", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public Long getIsVip() {
		return isVip;
	}

	public void setIsVip(Long isVip) {
		this.isVip = isVip;
	}

	@Column(name = "STATION", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getOaNumber() {
		return oaNumber;
	}

	public void setOaNumber(String oaNumber) {
		this.oaNumber = oaNumber;
	}

	@Transient
	public String getCdbUnitName() {
		return cdbUnitName;
	}

	public void setCdbUnitName(String cdbUnitName) {
		this.cdbUnitName = cdbUnitName;
	}
	@Transient
	public String getCdbDepName() {
		return cdbDepName;
	}

	public void setCdbDepName(String cdbDepName) {
		this.cdbDepName = cdbDepName;
	}
	@Transient
	public String getCdbTeamId() {
		return cdbTeamId;
	}

	public void setCdbTeamId(String cdbTeamId) {
		this.cdbTeamId = cdbTeamId;
	}

	@Transient
	public String getCdbTeamName() {
		return cdbTeamName;
	}

	public void setCdbTeamName(String cdbTeamName) {
		this.cdbTeamName = cdbTeamName;
	}

	/**
	 * 设置国开行的单位和部门名称
	 * @param depfullname 部门全称
	 */
	public void setCdbInfo(String depfullname){
		String[] depfullnameSplitByDot = depfullname.split("\\.");
		if(depfullnameSplitByDot.length>0){
			depfullnameSplitByDot = ArrayUtils.remove(depfullnameSplitByDot, 0);
		}
		if(depfullnameSplitByDot.length>0){
			setCdbUnitName(depfullnameSplitByDot[0]);
			depfullnameSplitByDot = ArrayUtils.remove(depfullnameSplitByDot, 0);
		}
		if(depfullnameSplitByDot.length>0){
			String depname = Arrays.toString(depfullnameSplitByDot);
			setCdbDepName(depname.substring(1,depname.length()-1).replaceAll(", ", "."));
		}
	}
	/**
	 * 设置国开行的单位和部门名称
	 * @param dep 人员所属的部门
	 */
	public void setCdbInfo(DepInfo cdbdep){
		if(cdbdep != null){
			setCdbInfo(cdbdep.getDepfullname());
		}
	}
	@Transient
	public String getGroupDepNames() {
		return groupDepNames;
	}

	public void setGroupDepNames(String groupDepNames) {
		this.groupDepNames = groupDepNames;
	}
	
	/**
	 * 取得职位的中文
	 */
	@Transient
	public String getPositionText(){
		DicManagerService dicManagerService = (DicManagerService) WebApplicationManager.getBean("dicManagerService");
		return dicManagerService.getTextByValue("userposition", position);
	}
	
}