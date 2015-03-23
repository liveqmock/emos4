-- Create table
create table BS_T_WF_RELEASEDEALUSERCONFIG
(
  pid               VARCHAR2(255) not null,
  bussystem         VARCHAR2(1000),
  edituser          VARCHAR2(2000),
  edituserid        VARCHAR2(2000),
  auditusers        VARCHAR2(2000),
  audituserids      VARCHAR2(2000),
  excuteuser        VARCHAR2(2000),
  excuteuserid      VARCHAR2(2000),
  testuser          VARCHAR2(2000),
  testuserid        VARCHAR2(2000),
  configdesc        VARCHAR2(4000),
  confirmid         VARCHAR2(1000),
  confirmname       VARCHAR2(100),
  organizeauditid   VARCHAR2(1000),
  organizeauditname VARCHAR2(1000),
  auditusers_2      VARCHAR2(2000),
  audituserids_2    VARCHAR2(2000)
)
tablespace BPP_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the columns 
comment on column BS_T_WF_RELEASEDEALUSERCONFIG.bussystem
  is '业务系统';
comment on column BS_T_WF_RELEASEDEALUSERCONFIG.edituser
  is '修订人';
comment on column BS_T_WF_RELEASEDEALUSERCONFIG.edituserid
  is '修订人ID';
comment on column BS_T_WF_RELEASEDEALUSERCONFIG.auditusers
  is '一级审批人';
comment on column BS_T_WF_RELEASEDEALUSERCONFIG.audituserids
  is '一级审批人ID';
comment on column BS_T_WF_RELEASEDEALUSERCONFIG.excuteuser
  is '实施人';
comment on column BS_T_WF_RELEASEDEALUSERCONFIG.excuteuserid
  is '实施人ID';
comment on column BS_T_WF_RELEASEDEALUSERCONFIG.testuser
  is '验证人';
comment on column BS_T_WF_RELEASEDEALUSERCONFIG.testuserid
  is '验证人ID';
comment on column BS_T_WF_RELEASEDEALUSERCONFIG.configdesc
  is '描述';
comment on column BS_T_WF_RELEASEDEALUSERCONFIG.confirmid
  is '方案验证人ID';
comment on column BS_T_WF_RELEASEDEALUSERCONFIG.confirmname
  is '方案验证人名称';
comment on column BS_T_WF_RELEASEDEALUSERCONFIG.organizeauditid
  is '评审人ID';
comment on column BS_T_WF_RELEASEDEALUSERCONFIG.organizeauditname
  is '评审人名称';
comment on column BS_T_WF_RELEASEDEALUSERCONFIG.auditusers_2
  is '二级审批人';
comment on column BS_T_WF_RELEASEDEALUSERCONFIG.audituserids_2
  is '二级审批人ID';
-- Create/Recreate primary, unique and foreign key constraints 
alter table BS_T_WF_RELEASEDEALUSERCONFIG
  add primary key (PID)
  using index 
  tablespace BPP_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

  
--20150129 增加 bussystemcode,变更由原先传递中文系统名称导致的无法修改系统名称的问题

  -- Add/modify columns 
alter table BS_T_WF_RELEASEDEALUSERCONFIG add bussystemcode VARCHAR2(1000);
-- Add comments to the columns 
comment on column BS_T_WF_RELEASEDEALUSERCONFIG.bussystemcode
  is '业务系统CODE';