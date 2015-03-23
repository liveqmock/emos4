--事件工单
select * from  bs_f_cbd_incident
select * from bs_f_cbd_incident_fml

--备份的所有信息工单
create table BS_T_BPP_BASEINFOR_bak as select * from BS_T_BPP_BASEINFOR
--备份的代办工单 
create table bs_t_wf_currenttask_bak as select * from bs_t_wf_currenttask

--清空所有工单
truncate table  BS_T_BPP_BASEINFOR
--清空代办工单
truncate table   bs_t_wf_currenttask

--类型表 dictype=5 为流程管理
select * from BS_T_SM_DICTYPE  where dtname in('影响业务类型','处理结果','处理人类型')
--字典表
select  * from BS_T_SM_DICITEM  where diname = '电子运维系统'
--此两表通过dtcode进行关联，类型类型表与字典表一对多

--增加事件流程现象分类
select * from bs_t_sm_dicitem where dtcode = 'Incident_PhenoClass'
select * from bs_t_sm_dictype where DTNAME like '%事件流程_现象分类%'


--增加人员信息相关字段

-- Add/modify columns 
alter table BS_T_SM_USER add isvip number(20);
alter table BS_T_SM_USER add station varchar2(100);
alter table BS_T_SM_USER add OANUMBER varchar2(100);

-- Add comments to the columns 
comment on column BS_T_SM_USER.isvip
  is '1.是、2否';
comment on column BS_T_SM_USER.station
  is '工位';
comment on column BS_T_SM_USER.OANUMBER
  is 'OA号';
  
--增加公告等级数据字典
create table BS_T_NOTICE_INFO
(
  pid               VARCHAR2(48) not null,
  notice_level      VARCHAR2(50),
  notice_title      VARCHAR2(200),
  notice_createtime NUMBER(15),
  notice_losttime   NUMBER(15),
  creator_id        VARCHAR2(48),
  notice_content    CLOB,
  notice_starttime  NUMBER(15),
  creator_name      VARCHAR2(48),
  notice_status     VARCHAR2(50),
  basesn            VARCHAR2(255),
  attachmentid      VARCHAR2(255),
  customuser        VARCHAR2(4000),
  selectuser        VARCHAR2(4000),
  noticetype        VARCHAR2(10)
)

alter table BS_T_NOTICE_INFO
  add primary key (PID);

--公告日志表
create table BS_T_NOTICE_VIEWLOG
(
  id        VARCHAR2(254) not null,
  notice_id VARCHAR2(254) not null,
  user_id   VARCHAR2(254) not null,
  isview    VARCHAR2(2) default '0',
  reciverid VARCHAR2(50)
)

--增加现象分类人员映射表
create table BS_T_WF_INCPHENOCLASSCONF
(
  pid            VARCHAR2(50) not null,
  diname         VARCHAR2(100),
  divalue        VARCHAR2(20),
  dicdn          VARCHAR2(20),
  dicdns         VARCHAR2(100),
  dicfullname    VARCHAR2(500),
  isdefault      VARCHAR2(30),
  status         NUMBER(2),
  parentid       VARCHAR2(50),
  creater        VARCHAR2(50),
  createtime     NUMBER(15),
  lastmodifier   VARCHAR2(50),
  lastmodifytime NUMBER(15),
  remark         VARCHAR2(500),
  picid      	 VARCHAR2(200),
  picname     	 VARCHAR2(200)
)


--增加知识库相关信息

create table BS_T_BPP_EXTRACTKNOWLEDGE
(
  id           VARCHAR2(50),
  baseschema   VARCHAR2(200),
  ekorder      NUMBER(10),
  fieldenname  VARCHAR2(50),
  fieldcnname  VARCHAR2(50),
  defaultvalue VARCHAR2(50),
  fieldtype    VARCHAR2(50),
  fieldid      VARCHAR2(50),
  isbasefield  VARCHAR2(20),
  kmtag        VARCHAR2(50),
  iskmmodelcol VARCHAR2(10)
)


-- 公告等级管理表
create table BS_T_NOTICE_LEVELMANAGE
(
  pid          VARCHAR2(50) not null,
  notice_level VARCHAR2(50),
  reciverid    VARCHAR2(50),
  createtime   NUMBER(15),
  creator_id   VARCHAR2(48),
  creator_name VARCHAR2(48),
  recivertype  VARCHAR2(50)
)


--动作排序字段添加
alter table BS_T_BPP_ACTION add orderid number




--增加公告范围字段

-- Add/modify columns 
alter table BS_T_NOTICE_INFO add noticescope VARCHAR2(4000);
-- Add comments to the columns 
comment on column BS_T_NOTICE_INFO.noticescope
  is '公告范围';
  
  -- Add/modify columns 
alter table BS_T_NOTICE_INFO add noticescopeid VARCHAR2(4000);
-- Add comments to the columns 
comment on column BS_T_NOTICE_INFO.noticescopeid
  is '公告范围ID';
  
  
  
alter table BS_T_NOTICE_INFO add noticescopgroup VARCHAR2(4000);
alter table BS_T_NOTICE_INFO add noticescopegroupid VARCHAR2(4000);
comment on column BS_T_NOTICE_INFO.noticescopgroup
  is '公告范围组ID';
comment on column BS_T_NOTICE_INFO.noticescopegroupid
  is '公告范围组';


--公告所发布的组脚本
--公告发布的组
select * from (select
notice_title,
t.basesn,
regexp_substr(noticescopeid,'[^,]+',1,rn) noticescopeid
from BS_T_NOTICE_INFO t,
(select rownum rn from dual connect by rownum <= 100)
where regexp_substr(noticescopeid, '[^,]+', 1, rn) is not null)
where noticescopeid  in
--登陆人所在的组
(
select groupid from (
select
t.loginname,
regexp_substr(groupid,'[^,]+',1,rn) groupid
from bs_t_sm_user t,
(select rownum rn from dual connect by rownum <= 100)
where regexp_substr(groupid, '[^,]+', 1, rn) is not null
and loginname = 'Demo'
order by loginname 
)
)
order by BASESN 


