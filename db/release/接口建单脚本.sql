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
comment on table BS_T_WFI_DATAIN is '�м����ݽӿڱ�(��������)';
comment on column BS_T_WFI_DATAIN.SHEETTYPE is '�������';
comment on column BS_T_WFI_DATAIN.OPACTION is '����';
comment on column BS_T_WFI_DATAIN.SERVICETYPE is '�������ֻCRM����';
comment on column BS_T_WFI_DATAIN.SERIALNO is '�������';
comment on column BS_T_WFI_DATAIN.SERSUPPLIER is '�����ṩ��';
comment on column BS_T_WFI_DATAIN.SERCALLER is '������÷�';
comment on column BS_T_WFI_DATAIN.SENDNUMBER is '�ظ��ɵ���';
comment on column BS_T_WFI_DATAIN.CALLTIME is '�������ʱ��';
comment on column BS_T_WFI_DATAIN.OPPERSON is '������';
comment on column BS_T_WFI_DATAIN.OPCORP is '�����˵�λ';
comment on column BS_T_WFI_DATAIN.OPDEPART is '��������������';
comment on column BS_T_WFI_DATAIN.OPCONTACT is '�����˵绰';
comment on column BS_T_WFI_DATAIN.OPTIME is '����ʱ��';
comment on column BS_T_WFI_DATAIN.OPDETAIL is '�ֶ�����';
comment on column BS_T_WFI_DATAIN.ATTACHREF is '������Ϣ';
comment on column BS_T_WFI_DATAIN.CREATETIME is '��¼����ʱ��';
comment on column BS_T_WFI_DATAIN.OPSTATE is '����״̬1:δ����  2:������ 3:�Ѵ���δ������ar���� 4:�Ѵ�������ʧ��(ar����������) 5 �����';
comment on column BS_T_WFI_DATAIN.SCANTIME is 'ɨ�账��ʱ��';
comment on column BS_T_WFI_DATAIN.METHODNAME is '���񷽷���';