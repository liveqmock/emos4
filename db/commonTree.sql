--通用树型配置表
create table bs_t_sm_commonTree
(
  id           varchar2(255) not null primary key,
  name         varchar2(100) not null,
  fullName     varchar2(1000) not null,
  pid          varchar2(255) default 0 not null,
  tlevel       number not null,
  orderby      number,
  type         varchar2(100) not null,
  propField_01 varchar2(2000),
  propField_02 varchar2(2000),
  propField_03 varchar2(2000),
  propField_04 varchar2(2000),
  propField_05 varchar2(2000),
  propField_06 varchar2(2000),
  propField_07 varchar2(2000),
  propField_08 varchar2(2000),
  propField_09 varchar2(2000),
  propField_10 varchar2(2000),
  propField_11 varchar2(2000),
  propField_12 varchar2(2000),
  propField_13 varchar2(2000),
  propField_14 varchar2(2000),
  propField_15 varchar2(2000) ,
  PROPFIELD_16 VARCHAR2(2000),
  PROPFIELD_17 VARCHAR2(2000),
  PROPFIELD_18 VARCHAR2(2000),
  PROPFIELD_19 VARCHAR2(2000),
  PROPFIELD_20 VARCHAR2(2000),
  PROPFIELD_21 VARCHAR2(2000),
  PROPFIELD_22 VARCHAR2(2000),
  PROPFIELD_23 VARCHAR2(2000),
  PROPFIELD_24 VARCHAR2(2000),
  PROPFIELD_25 VARCHAR2(2000),
  PROPFIELD_26 VARCHAR2(2000),
  PROPFIELD_27 VARCHAR2(2000),
  PROPFIELD_28 VARCHAR2(2000),
  PROPFIELD_29 VARCHAR2(2000),
  PROPFIELD_30 VARCHAR2(2000)
);
-- Add comments to the columns 
comment on column bs_t_sm_commonTree.id
  is '关键字';
comment on column bs_t_sm_commonTree.name
  is '树节点名称';
comment on column bs_t_sm_commonTree.fullName
  is '树节点全称';
comment on column bs_t_sm_commonTree.pid
  is '父节点ID 第一级为0';
comment on column bs_t_sm_commonTree.tlevel
  is '几级节点';
comment on column bs_t_sm_commonTree.type
  is '对象分类';
comment on column bs_t_sm_commonTree.propField_01
  is '字段属性1';
comment on column bs_t_sm_commonTree.propField_02
  is '字段属性2';
