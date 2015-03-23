#将历史信息置为发送失败
update bs_t_wf_record t set t.smssendflag='2',t.emailsendflag='2' where t.issms='1' or t.isemail='1';

comment on column bs_t_wf_record.SMSSENDFLAG
  is '发送状态  0:未发送;1:已发送;2:发送失败;3:发送异常(组中存在部分用户发送失败);4:服务异常;5:取消发送(手机号为空);';
comment on column bs_t_wf_record.EMAILSENDFLAG
  is '发送状态  0:未发送;1:已发送;2:发送失败;3:发送异常(组中存在部分用户发送失败);4:服务异常;5:取消发送(EMAIL为空);';

create table BS_T_SM_SMSLOG
(
  PID        VARCHAR2(50) not null,
  RECORDPID  VARCHAR2(50),
  SENDBTIME  VARCHAR2(20),
  SENDETIME  VARCHAR2(20),
  SENDOBJ    VARCHAR2(4000),
  ERRORINFO  VARCHAR2(4000),
  SMSCONTENT VARCHAR2(4000),
  FLAG       VARCHAR2(10),
  SENDXML    VARCHAR2(4000),
  RETURNXML  VARCHAR2(4000),
  FLOWNO     VARCHAR2(50)
);
-- Add comments to the columns 
comment on column BS_T_SM_SMSLOG.RECORDPID
  is 'BS_T_WF_RECORD表ID';
comment on column BS_T_SM_SMSLOG.SENDBTIME
  is 'WS调用时间';
comment on column BS_T_SM_SMSLOG.SENDETIME
  is 'WS返回时间';
comment on column BS_T_SM_SMSLOG.SENDOBJ
  is '发送手机号';
comment on column BS_T_SM_SMSLOG.ERRORINFO
  is '错误信息';
comment on column BS_T_SM_SMSLOG.SMSCONTENT
  is '发送内容';
comment on column BS_T_SM_SMSLOG.FLAG
  is '标志,1:已发送;2:发送失败;3:发送异常(组中存在部分用户发送失败);5:取消发送(手机号为空)';
comment on column BS_T_SM_SMSLOG.SENDXML
  is 'WS请求XML';
comment on column BS_T_SM_SMSLOG.RETURNXML
  is 'WS返回XML';
comment on column BS_T_SM_SMSLOG.FLOWNO
  is 'WS请求流水号';

create table BS_T_SM_SMSMODEL
(
  PID                   VARCHAR2(50) not null,
  MODELTYPE            VARCHAR2(50),
  CONTENT               VARCHAR2(4000),
  CREATETIME            VARCHAR2(20),
  CREATER               VARCHAR2(20),
  MODIFYTIME            VARCHAR2(20),
  MODIFIER              VARCHAR2(20)
);

create table BS_T_SM_MAILLOG
(
  PID        VARCHAR2(50) not null,
  RECORDPID  VARCHAR2(50),
  SENDBTIME  VARCHAR2(20),
  SENDETIME  VARCHAR2(20),
  SENDOBJ    VARCHAR2(4000),
  ERRORINFO  VARCHAR2(4000),
  MAILCONTENT VARCHAR2(4000),
  FLAG       VARCHAR2(10),
  SENDXML    VARCHAR2(4000),
  RETURNXML  VARCHAR2(4000),
  FLOWNO     VARCHAR2(50),
  MAILTITLE  VARCHAR2(2000)
);
-- Add comments to the columns 
comment on column BS_T_SM_MAILLOG.RECORDPID
  is 'BS_T_WF_RECORD表ID';
comment on column BS_T_SM_MAILLOG.SENDBTIME
  is 'WS调用时间';
comment on column BS_T_SM_MAILLOG.SENDETIME
  is 'WS返回时间';
comment on column BS_T_SM_MAILLOG.SENDOBJ
  is '发送邮箱';
comment on column BS_T_SM_MAILLOG.ERRORINFO
  is '错误信息';
comment on column BS_T_SM_MAILLOG.MAILCONTENT
  is '发送内容';
comment on column BS_T_SM_MAILLOG.FLAG
  is '标志,1:已发送;2:发送失败;3:发送异常(组中存在部分用户发送失败);5:取消发送(手机号为空)';
comment on column BS_T_SM_MAILLOG.SENDXML
  is 'WS请求XML';
comment on column BS_T_SM_MAILLOG.RETURNXML
  is 'WS返回XML';
comment on column BS_T_SM_MAILLOG.FLOWNO
  is 'WS请求流水号';
comment on column BS_T_SM_MAILLOG.MAILTITLE
  is '邮件标题';

create table BS_T_SM_MAILMODEL
(
  PID                   VARCHAR2(50) not null,
  MODELTYPE            VARCHAR2(50),
  CONTENT               VARCHAR2(4000),
  CREATETIME            VARCHAR2(20),
  CREATER               VARCHAR2(20),
  MODIFYTIME            VARCHAR2(20),
  MODIFIER              VARCHAR2(20),
  MAILTITLE	  VARCHAR2(2000)
);