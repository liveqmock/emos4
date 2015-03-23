-- Create table
create table BS_T_WF_SERVERQUEST
(
  PID                 VARCHAR2(50) not null,
  SERVERQUESTNAME     VARCHAR2(100),
  SERVERQUESTVALUE    VARCHAR2(100),
  SERVERQUESTDN       VARCHAR2(20),
  SERVERQUESTDNS      VARCHAR2(100),
  SERVERQUESTFULLNAME VARCHAR2(500),
  STATUS              VARCHAR2(50),
  ORDERNUM            NUMBER(5),
  PARENTID            VARCHAR2(50),
  CREATER             VARCHAR2(50),
  CREATETIME          VARCHAR2(50),
  REMARK              VARCHAR2(500),
  DILEVER             NUMBER(5),
  VIPFLOG             VARCHAR2(50),
  AUDIT_0_ID          VARCHAR2(200),
  HY                  VARCHAR2(50),
  WB                  VARCHAR2(50),
  QT                  VARCHAR2(50),
  WORKTIME            VARCHAR2(50),
  NOWORKTIME          VARCHAR2(50),
  DEALTIMELIMIT       VARCHAR2(50),
  AUDIT_1_NAME        VARCHAR2(200),
  AUDIT_2_NAME        VARCHAR2(200),
  AUDIT_3_NAME        VARCHAR2(200),
  AUDIT_4_NAME        VARCHAR2(200),
  AUDIT_2_ID          VARCHAR2(200),
  AUDIT_3_ID          VARCHAR2(200),
  AUDIT_4_ID          VARCHAR2(200),
  AUDIT_1_ID          VARCHAR2(200),
  AUDIT_0_NAME        VARCHAR2(200),
  ATTAMENTID          VARCHAR2(50),
  AUDIT_ID            VARCHAR2(500),
  AUDIT_NAME          VARCHAR2(500),
  ISCOMMON            VARCHAR2(50),
  SERVICE_INFO        VARCHAR2(500),
  RESPONSIBLE_DEP     VARCHAR2(50),
  DEAL_GROUP_NAME     VARCHAR2(50),
  DEAL_GROUP_ID       VARCHAR2(50),
  FORWHO              VARCHAR2(50),
  RELEASE_SCOPE_TEXT  VARCHAR2(2000),
  RELEASE_SCOPE_ID    VARCHAR2(2000)
)
tablespace ITSM_DEFAULT
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 1M
    next 8K
    minextents 1
    maxextents unlimited
    pctincrease 0
  );
-- Add comments to the columns 
comment on column BS_T_WF_SERVERQUEST.PID
  is '主键ID';
comment on column BS_T_WF_SERVERQUEST.SERVERQUESTNAME
  is '服务请求名称';
comment on column BS_T_WF_SERVERQUEST.SERVERQUESTVALUE
  is '服务请求英文';
comment on column BS_T_WF_SERVERQUEST.SERVERQUESTDN
  is '字典DN';
comment on column BS_T_WF_SERVERQUEST.SERVERQUESTDNS
  is '字典DNS';
comment on column BS_T_WF_SERVERQUEST.SERVERQUESTFULLNAME
  is '服务请求全名';
comment on column BS_T_WF_SERVERQUEST.STATUS
  is '1.启用、0.停用';
comment on column BS_T_WF_SERVERQUEST.ORDERNUM
  is '排序值';
comment on column BS_T_WF_SERVERQUEST.PARENTID
  is '父信息ID';
comment on column BS_T_WF_SERVERQUEST.CREATER
  is '创建人';
comment on column BS_T_WF_SERVERQUEST.CREATETIME
  is '创建时间';
comment on column BS_T_WF_SERVERQUEST.REMARK
  is '备注';
comment on column BS_T_WF_SERVERQUEST.DILEVER
  is '字典层级';
comment on column BS_T_WF_SERVERQUEST.VIPFLOG
  is '是否有VIP服务';
comment on column BS_T_WF_SERVERQUEST.AUDIT_0_ID
  is '服务处理责任处室id';
comment on column BS_T_WF_SERVERQUEST.HY
  is '行员';
comment on column BS_T_WF_SERVERQUEST.WB
  is '外包';
comment on column BS_T_WF_SERVERQUEST.QT
  is '其他';
comment on column BS_T_WF_SERVERQUEST.WORKTIME
  is '工作日响应时间';
comment on column BS_T_WF_SERVERQUEST.NOWORKTIME
  is '非工作日响应时间';
comment on column BS_T_WF_SERVERQUEST.DEALTIMELIMIT
  is '解决时间上限';
comment on column BS_T_WF_SERVERQUEST.AUDIT_1_NAME
  is '承办处管理负责人(行员）';
comment on column BS_T_WF_SERVERQUEST.AUDIT_2_NAME
  is '承办处处长';
comment on column BS_T_WF_SERVERQUEST.AUDIT_3_NAME
  is '运行中心副主任';
comment on column BS_T_WF_SERVERQUEST.AUDIT_4_NAME
  is '运行中心主管副局长';
comment on column BS_T_WF_SERVERQUEST.AUDIT_2_ID
  is '承办处处长id';
comment on column BS_T_WF_SERVERQUEST.AUDIT_3_ID
  is '运行中心副主任id';
comment on column BS_T_WF_SERVERQUEST.AUDIT_4_ID
  is '运行中心主管副局长id';
comment on column BS_T_WF_SERVERQUEST.AUDIT_1_ID
  is '承办处管理负责人(行员）id';
comment on column BS_T_WF_SERVERQUEST.AUDIT_0_NAME
  is '服务处理责任处室';
comment on column BS_T_WF_SERVERQUEST.ATTAMENTID
  is '附件id';
comment on column BS_T_WF_SERVERQUEST.AUDIT_ID
  is '汇总审批人id';
comment on column BS_T_WF_SERVERQUEST.AUDIT_NAME
  is '汇总名称';
comment on column BS_T_WF_SERVERQUEST.ISCOMMON
  is '是否常用';
comment on column BS_T_WF_SERVERQUEST.SERVICE_INFO
  is '服务说明';
comment on column BS_T_WF_SERVERQUEST.RESPONSIBLE_DEP
  is '负责部室';
comment on column BS_T_WF_SERVERQUEST.DEAL_GROUP_NAME
  is '处理组名(可能是人)';
comment on column BS_T_WF_SERVERQUEST.DEAL_GROUP_ID
  is '处理组id(可能是人)';
comment on column BS_T_WF_SERVERQUEST.FORWHO
  is '对内，对外';
comment on column BS_T_WF_SERVERQUEST.RELEASE_SCOPE_TEXT
  is '发布范围中文';
comment on column BS_T_WF_SERVERQUEST.RELEASE_SCOPE_ID
  is '发布范围id';
-- Create/Recreate primary, unique and foreign key constraints 
alter table BS_T_WF_SERVERQUEST
  add primary key (PID)
  using index 
  tablespace ITSM_DEFAULT
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 1M
    next 1M
    minextents 1
    maxextents unlimited
    pctincrease 0
  );
