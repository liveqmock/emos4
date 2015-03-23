-- Create table
create table BS_T_WF_RECORD
(
  PID                   VARCHAR2(50) not null,
  BASESCHEMA            VARCHAR2(255) not null,
  BASEID                VARCHAR2(50) not null,
  CURRENTUSER           VARCHAR2(255) not null,
  CURRENTUSERLOGINNAME  VARCHAR2(255),
  DEALTIME              VARCHAR2(20),
  DEALDESC              VARCHAR2(500),
  DEALACTION            VARCHAR2(50),
  ISVIEW                VARCHAR2(10),
  NEXTDEALUSER          VARCHAR2(500),
  NEXTDEALUSERLOGINNAME VARCHAR2(500),
  ISSMS                 VARCHAR2(10),
  SMSCONTENT            VARCHAR2(500),
  SMSSENDFLAG           VARCHAR2(10),
  ISEMAIL               VARCHAR2(10),
  EMAILSENDFLAG         VARCHAR2(10),
  EMAILCONTENT          VARCHAR2(2000)
)
tablespace ITSM_DEFAULT
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 1
    next 1
    minextents 1
    maxextents unlimited
    pctincrease 0
  );
-- Add comments to the columns 
comment on column BS_T_WF_RECORD.SMSSENDFLAG
  is '发送状态  0:未发送;1:已发送;2:发送失败;3:发送异常(组中存在部分用户发送失败);4:服务异常(会再次尝试发送)';
