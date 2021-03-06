-- Create table
create table BS_T_WF_P_SAMPLE
(
  BASEID                VARCHAR2(50) not null primary key,
  BASESCHEMA            VARCHAR2(255),
  BASENAME              VARCHAR2(255),
  BASESN                VARCHAR2(255),
  BASEENTRYID           VARCHAR2(50),
  BASECREATEDATE        NUMBER(15),
  BASESENDDATE          NUMBER(15),
  BASEACCEPTDATE        NUMBER(15),
  BASEFINISHDATE        NUMBER(15),
  BASECLOSEDATE         NUMBER(15),
  BASESUMMARY           VARCHAR2(2000),
  BASESTATUS            VARCHAR2(255),
  BASEITEMS             VARCHAR2(255),
  BASEPRIORITY          VARCHAR2(255),
  BASEACCEPTOUTTIME     NUMBER(15),
  BASEDEALOUTTIME       NUMBER(15),
  BASEDESCRIPTION       VARCHAR2(255),
  BASECLOSESATISFY      VARCHAR2(255),
  BASETPLID             VARCHAR2(255),
  BASEISARCHIVE         VARCHAR2(255),
  BASEISTRUECLOSE       VARCHAR2(255),
  BASEWORKFLOWFLAG      NUMBER(15),
  BASECATAGORYNAME      VARCHAR2(255),
  BASECATAGORYID        VARCHAR2(255),
  BASECREATORFULLNAME   VARCHAR2(255),
  BASECREATORLOGINNAME  VARCHAR2(255),
  BASECREATORCONNECTWAY VARCHAR2(255),
  BASECREATORCORP       VARCHAR2(255),
  BASECREATORCORPID     VARCHAR2(255),
  BASECREATORDEP        VARCHAR2(255),
  BASECREATORDEPID      VARCHAR2(255),
  BASECREATORDN         VARCHAR2(255),
  BASECREATORDNID       VARCHAR2(255),
  BASEATTACHGUID        VARCHAR2(255)
);

