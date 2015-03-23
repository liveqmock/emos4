package com.ultrapower.eoms.common.core.component.sms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="BS_T_SM_SMSRECEIVE")
public class SmsReceive implements java.io.Serializable {

	 private static final long serialVersionUID = -3329603512861000974L;
	 private String pid;
	 private String eomsid;
	 private String smscontext;
	 private String phone;
	 private int opstate;
	 private String creater;
	 private long createtime;
	 private String lastmodifier;
	 private long lastmodifytime;	

		@Id
		@GeneratedValue(generator = "system-uuid")
		@GenericGenerator(name = "system-uuid", strategy = "uuid")
		@Column(name = "PID", unique = true, nullable = false, insertable = true, updatable = true, length = 50)
		public String getPid() {
			return this.pid;
		}

		public void setPid(String pid) {
			this.pid = pid;
		}

		@Column(name = "EOMSID", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
		public String getEomsid() {
			return this.eomsid;
		}

		public void setEomsid(String eomsid) {
			this.eomsid = eomsid;
		}

		@Column(name = "CREATER", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
		public String getCreater() {
			return this.creater;
		}

		public void setCreater(String creater) {
			this.creater = creater;
		}
		
		@Column(name = "PHONE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
		public String getPhone() {
			return this.phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		@Column(name = "SMSCONTEXT", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
		public String getSmscontext() {
			return this.smscontext;
		}

		public void setSmscontext(String smscontext) {
			this.smscontext = smscontext;
		}		
		@Column(name = "OPSTATE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
		public int getOpstate() {
			return this.opstate;
		}

		public void setOpstate(int opstate) {
			this.opstate = opstate;
		}	
		
		@Column(name = "CREATETIME", unique = false, nullable = true, insertable = true, updatable = true, precision = 15, scale = 0)
		public Long getCreatetime() {
			return this.createtime;
		}

		public void setCreatetime(Long createtime) {
			this.createtime = createtime;
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
			this.lastmodifytime = lastmodifytime;
		}	 
	
}
