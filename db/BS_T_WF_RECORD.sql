-- Create table
create table BS_T_WF_RECORD
(
  PID                   VARCHAR2(50) not null primary key,
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
);