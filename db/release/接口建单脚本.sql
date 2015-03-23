-- Create table
create table BS_T_WFI_DATAIN
(
  PID         VARCHAR2(50) not null primary key,
  SHEETTYPE   VARCHAR2(50),
  OPACTION    VARCHAR2(30),
  SERVICETYPE VARCHAR2(4),
  SERIALNO    VARCHAR2(50),
  SERSUPPLIER VARCHAR2(20),
  SERCALLER   VARCHAR2(20),
  SENDNUMBER  NUMBER(3),
  CALLTIME    VARCHAR2(20),
  OPPERSON    VARCHAR2(50),
  OPCORP      VARCHAR2(50),
  OPDEPART    VARCHAR2(50),
  OPCONTACT   VARCHAR2(12),
  OPTIME      VARCHAR2(12),
  OPDETAIL    CLOB,
  ATTACHREF   CLOB,
  CREATETIME  NUMBER(15),
  OPSTATE     NUMBER(2),
  SCANTIME    NUMBER(15),
  METHODNAME  VARCHAR2(100),
  DEALDESC    VARCHAR2(4000)
);
-- Add comments to the table 
comment on table BS_T_WFI_DATAIN is '中间数据接口表(接收数据)';
comment on column BS_T_WFI_DATAIN.SHEETTYPE is '工单类别';
comment on column BS_T_WFI_DATAIN.OPACTION is '动作';
comment on column BS_T_WFI_DATAIN.SERVICETYPE is '操作类别只CRM有用';
comment on column BS_T_WFI_DATAIN.SERIALNO is '工单编号';
comment on column BS_T_WFI_DATAIN.SERSUPPLIER is '服务提供方';
comment on column BS_T_WFI_DATAIN.SERCALLER is '服务调用方';
comment on column BS_T_WFI_DATAIN.SENDNUMBER is '重复派单数';
comment on column BS_T_WFI_DATAIN.CALLTIME is '服务调用时间';
comment on column BS_T_WFI_DATAIN.OPPERSON is '操作人';
comment on column BS_T_WFI_DATAIN.OPCORP is '操作人单位';
comment on column BS_T_WFI_DATAIN.OPDEPART is '操作人所属部门';
comment on column BS_T_WFI_DATAIN.OPCONTACT is '操作人电话';
comment on column BS_T_WFI_DATAIN.OPTIME is '操作时间';
comment on column BS_T_WFI_DATAIN.OPDETAIL is '字段内容';
comment on column BS_T_WFI_DATAIN.ATTACHREF is '附件信息';
comment on column BS_T_WFI_DATAIN.CREATETIME is '记录创建时间';
comment on column BS_T_WFI_DATAIN.OPSTATE is '处理状态1:未处理  2:处理中 3:已处理但未连接上ar服务 4:已处理但处理失败(ar服务错误除外) 5 已完成';
comment on column BS_T_WFI_DATAIN.SCANTIME is '扫描处理时间';
comment on column BS_T_WFI_DATAIN.METHODNAME is '服务方法名';