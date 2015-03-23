-- Create table
create table BS_T_WF_RELEASEINTERFACECHG
(
  PID        VARCHAR2(255) not null primary key,
  BASEID     VARCHAR2(255) not null,
  CHGTYPE    VARCHAR2(100),
  CHGNAME    VARCHAR2(100),
  CHGNUM     VARCHAR2(100),
  CHGCONTENT VARCHAR2(4000),
  CHGMODULE  VARCHAR2(1000)
);
-- Add comments to the columns 
comment on column BS_T_WF_RELEASEINTERFACECHG.BASEID
  is '发布工单关键字';
comment on column BS_T_WF_RELEASEINTERFACECHG.CHGTYPE
  is '变更类型';
comment on column BS_T_WF_RELEASEINTERFACECHG.CHGNAME
  is '变更名称';
comment on column BS_T_WF_RELEASEINTERFACECHG.CHGNUM
  is '变更个数';
comment on column BS_T_WF_RELEASEINTERFACECHG.CHGCONTENT
  is '变更内容说明';
comment on column BS_T_WF_RELEASEINTERFACECHG.CHGMODULE
  is '涉及模块';
