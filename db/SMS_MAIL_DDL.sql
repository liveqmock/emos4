#����ʷ��Ϣ��Ϊ����ʧ��
update bs_t_wf_record t set t.smssendflag='2',t.emailsendflag='2' where t.issms='1' or t.isemail='1';

comment on column bs_t_wf_record.SMSSENDFLAG
  is '����״̬  0:δ����;1:�ѷ���;2:����ʧ��;3:�����쳣(���д��ڲ����û�����ʧ��);4:�����쳣;5:ȡ������(�ֻ���Ϊ��);';
comment on column bs_t_wf_record.EMAILSENDFLAG
  is '����״̬  0:δ����;1:�ѷ���;2:����ʧ��;3:�����쳣(���д��ڲ����û�����ʧ��);4:�����쳣;5:ȡ������(EMAILΪ��);';

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
  is 'BS_T_WF_RECORD��ID';
comment on column BS_T_SM_SMSLOG.SENDBTIME
  is 'WS����ʱ��';
comment on column BS_T_SM_SMSLOG.SENDETIME
  is 'WS����ʱ��';
comment on column BS_T_SM_SMSLOG.SENDOBJ
  is '�����ֻ���';
comment on column BS_T_SM_SMSLOG.ERRORINFO
  is '������Ϣ';
comment on column BS_T_SM_SMSLOG.SMSCONTENT
  is '��������';
comment on column BS_T_SM_SMSLOG.FLAG
  is '��־,1:�ѷ���;2:����ʧ��;3:�����쳣(���д��ڲ����û�����ʧ��);5:ȡ������(�ֻ���Ϊ��)';
comment on column BS_T_SM_SMSLOG.SENDXML
  is 'WS����XML';
comment on column BS_T_SM_SMSLOG.RETURNXML
  is 'WS����XML';
comment on column BS_T_SM_SMSLOG.FLOWNO
  is 'WS������ˮ��';

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
  is 'BS_T_WF_RECORD��ID';
comment on column BS_T_SM_MAILLOG.SENDBTIME
  is 'WS����ʱ��';
comment on column BS_T_SM_MAILLOG.SENDETIME
  is 'WS����ʱ��';
comment on column BS_T_SM_MAILLOG.SENDOBJ
  is '��������';
comment on column BS_T_SM_MAILLOG.ERRORINFO
  is '������Ϣ';
comment on column BS_T_SM_MAILLOG.MAILCONTENT
  is '��������';
comment on column BS_T_SM_MAILLOG.FLAG
  is '��־,1:�ѷ���;2:����ʧ��;3:�����쳣(���д��ڲ����û�����ʧ��);5:ȡ������(�ֻ���Ϊ��)';
comment on column BS_T_SM_MAILLOG.SENDXML
  is 'WS����XML';
comment on column BS_T_SM_MAILLOG.RETURNXML
  is 'WS����XML';
comment on column BS_T_SM_MAILLOG.FLOWNO
  is 'WS������ˮ��';
comment on column BS_T_SM_MAILLOG.MAILTITLE
  is '�ʼ�����';

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