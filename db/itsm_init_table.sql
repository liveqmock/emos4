prompt PL/SQL Developer import file
prompt Created on 2013年8月23日 by Administrator
set feedback off
set define off
prompt Creating BS_F_UBP_TEST_FIX...
create table BS_F_UBP_TEST_FIX
(
  BASEID                VARCHAR2(50) not null,
  BASESCHEMA            VARCHAR2(255),
  BASENAME              VARCHAR2(255),
  BASESN                VARCHAR2(255),
  BASEENTRYID           VARCHAR2(50),
  BASECREATEDATE        NUMBER(15),
  BASESENDDATE          NUMBER(15),
  BASEACCEPTDATE        NUMBER(15),
  BASEFINISHDATE        NUMBER(15),
  BASECLOSEDATE         NUMBER(15),
  BASESTATUS            VARCHAR2(255),
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
  OKMONEY               VARCHAR2(10),
  APPROVALRESULT        VARCHAR2(10),
  ELEVEL                VARCHAR2(12),
  ENDTIME               NUMBER(15),
  RECHECKRESULT         VARCHAR2(10),
  AMOUNT                VARCHAR2(10),
  APPROVALCOMMENT       VARCHAR2(1000),
  STARTTIME             NUMBER(15),
  CHECKRESULT           VARCHAR2(10),
  EVECTIONCAUSE         VARCHAR2(1000),
  ATTACHMENTSLIST       VARCHAR2(50),
  CHECKBOXTEST          VARCHAR2(100),
  RADIOBOXTEST          VARCHAR2(50),
  WORKTIME              VARCHAR2(10),
  DEACINFO              VARCHAR2(2000),
  BASESUMMARY           VARCHAR2(1000),
  PRIORITYLEVEL         VARCHAR2(50)
)
;
alter table BS_F_UBP_TEST_FIX
  add primary key (BASEID);

prompt Creating BS_F_UBP_TEST_FIX_FML...
create table BS_F_UBP_TEST_FIX_FML
(
  ID         VARCHAR2(50) not null,
  BASEID     VARCHAR2(255),
  BASESCHEMA VARCHAR2(255),
  PHASENO    VARCHAR2(255),
  FIELDID    VARCHAR2(255),
  FIELDTYPE  VARCHAR2(255),
  FIELDVALUE VARCHAR2(2000),
  MODIFYDATE NUMBER(15),
  DEALERID   VARCHAR2(255),
  DEALER     VARCHAR2(255),
  TASKID     VARCHAR2(255),
  FIELDCODE  VARCHAR2(255),
  ACTIONTYPE VARCHAR2(255),
  ACTIONNAME VARCHAR2(255),
  FIELDLABEL VARCHAR2(255),
  ORDERNUM   NUMBER(15),
  COLSPAN    NUMBER(15)
)
;
alter table BS_F_UBP_TEST_FIX_FML
  add primary key (ID);

prompt Creating BS_F_UBP_TEST_FREE...
create table BS_F_UBP_TEST_FREE
(
  BASEID                VARCHAR2(50) not null,
  BASESCHEMA            VARCHAR2(255),
  BASENAME              VARCHAR2(255),
  BASESN                VARCHAR2(255),
  BASEENTRYID           VARCHAR2(50),
  BASECREATEDATE        NUMBER(15),
  BASESENDDATE          NUMBER(15),
  BASEACCEPTDATE        NUMBER(15),
  BASEFINISHDATE        NUMBER(15),
  BASECLOSEDATE         NUMBER(15),
  BASESTATUS            VARCHAR2(255),
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
  MEETINGSUMMARY        VARCHAR2(50),
  ISSUBMIT              VARCHAR2(4),
  BASESUMMARY           VARCHAR2(1000),
  MEETINGTIME           NUMBER(15),
  ZU_KAITIME            NUMBER(1),
  MEETINGLEVEL          VARCHAR2(15),
  APPROVAL              VARCHAR2(1000)
)
;
alter table BS_F_UBP_TEST_FREE
  add primary key (BASEID);

prompt Creating BS_F_UBP_TEST_FREE_FML...
create table BS_F_UBP_TEST_FREE_FML
(
  ID         VARCHAR2(50) not null,
  BASEID     VARCHAR2(255),
  BASESCHEMA VARCHAR2(255),
  PHASENO    VARCHAR2(255),
  FIELDID    VARCHAR2(255),
  FIELDTYPE  VARCHAR2(255),
  FIELDVALUE VARCHAR2(2000),
  MODIFYDATE NUMBER(15),
  DEALERID   VARCHAR2(255),
  DEALER     VARCHAR2(255),
  TASKID     VARCHAR2(255),
  FIELDCODE  VARCHAR2(255),
  ACTIONTYPE VARCHAR2(255),
  ACTIONNAME VARCHAR2(255),
  FIELDLABEL VARCHAR2(255),
  ORDERNUM   NUMBER(15),
  COLSPAN    NUMBER(15)
)
;
alter table BS_F_UBP_TEST_FREE_FML
  add primary key (ID);

prompt Creating BS_T_BPP_ACTION...
create table BS_T_BPP_ACTION
(
  ID          VARCHAR2(15) not null,
  BASESCHEMA  VARCHAR2(100),
  STEPNO      VARCHAR2(100),
  LABEL       VARCHAR2(100),
  ACTIONNAME  VARCHAR2(100),
  ACTIONTYPE  VARCHAR2(100),
  ISFREE      NUMBER,
  HASNEXT     NUMBER,
  DESCRIPTION VARCHAR2(200),
  HASDEPLOY   NUMBER,
  OPERATE     VARCHAR2(100),
  ISCLOSE     NUMBER default 1
)
;
comment on column BS_T_BPP_ACTION.ISCLOSE
  is '点击后是否关闭1，0，默认1';
alter table BS_T_BPP_ACTION
  add constraint PK_BS_T_BPP_ACTION primary key (ID);

prompt Creating BS_T_BPP_ACTIONFIELDREL...
create table BS_T_BPP_ACTIONFIELDREL
(
  ID         VARCHAR2(15) not null,
  BASESCHEMA VARCHAR2(100),
  FIELDID    VARCHAR2(15),
  REQUIRED   NUMBER,
  FIELDTYPE  VARCHAR2(100),
  ACTIONNAME VARCHAR2(100),
  STEPNO     VARCHAR2(100),
  VISIABLE   NUMBER
)
;
alter table BS_T_BPP_ACTIONFIELDREL
  add constraint PK_BS_T_BPP_ACTIONFIELDREL primary key (ID);

prompt Creating BS_T_BPP_ASSINGTREECONFIG...
create table BS_T_BPP_ASSINGTREECONFIG
(
  ID          VARCHAR2(15) not null,
  BASESCHEMA  VARCHAR2(100),
  FIELDNAME   VARCHAR2(100),
  FIELDLABEL  VARCHAR2(100),
  STEPNO      VARCHAR2(100),
  STEPDESC    VARCHAR2(200),
  ACTIONNAME  VARCHAR2(100),
  ACTIONLABEL VARCHAR2(100),
  SELECTTYPE  VARCHAR2(2),
  TABSHOW     VARCHAR2(2)
)
;
comment on column BS_T_BPP_ASSINGTREECONFIG.ID
  is '主键ID';
comment on column BS_T_BPP_ASSINGTREECONFIG.BASESCHEMA
  is '工单分类';
comment on column BS_T_BPP_ASSINGTREECONFIG.FIELDNAME
  is '字段名称（派发树）';
comment on column BS_T_BPP_ASSINGTREECONFIG.FIELDLABEL
  is '派发树显示名';
comment on column BS_T_BPP_ASSINGTREECONFIG.STEPNO
  is '环节标识';
comment on column BS_T_BPP_ASSINGTREECONFIG.STEPDESC
  is '环节描述';
comment on column BS_T_BPP_ASSINGTREECONFIG.ACTIONNAME
  is '动作标识';
comment on column BS_T_BPP_ASSINGTREECONFIG.ACTIONLABEL
  is '动作名';
comment on column BS_T_BPP_ASSINGTREECONFIG.SELECTTYPE
  is '选择类型';
comment on column BS_T_BPP_ASSINGTREECONFIG.TABSHOW
  is '显示类型，0，全部，1，组织机构，2，自定义派发树';
alter table BS_T_BPP_ASSINGTREECONFIG
  add constraint PK_BS_T_BPP_ASSINGTREECONFIG primary key (ID);

prompt Creating BS_T_BPP_ASSINGTREEORGANIZE...
create table BS_T_BPP_ASSINGTREEORGANIZE
(
  ID           VARCHAR2(15) not null,
  CONFIGID     VARCHAR2(15),
  ORGANIZETYPE NUMBER(2),
  ORGANIZEID   VARCHAR2(50),
  PARENTORGID  VARCHAR2(50)
)
;
comment on column BS_T_BPP_ASSINGTREEORGANIZE.ID
  is '主键ID';
comment on column BS_T_BPP_ASSINGTREEORGANIZE.CONFIGID
  is '派发树配置表ID';
comment on column BS_T_BPP_ASSINGTREEORGANIZE.ORGANIZETYPE
  is '组织类型（1，部门，2，人员）';
comment on column BS_T_BPP_ASSINGTREEORGANIZE.ORGANIZEID
  is '组织ID';
comment on column BS_T_BPP_ASSINGTREEORGANIZE.PARENTORGID
  is '上级组织ID';
alter table BS_T_BPP_ASSINGTREEORGANIZE
  add constraint PK_BS_T_BPP_ASSINGTREEORGANIZE primary key (ID);

prompt Creating BS_T_BPP_BASEINFOR...
create table BS_T_BPP_BASEINFOR
(
  BASEID                VARCHAR2(50) not null,
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
)
;
alter table BS_T_BPP_BASEINFOR
  add constraint PK_BPP_BASEINFO primary key (BASEID);
create index IDS_BASEINFOR_BASESCHEMA on BS_T_BPP_BASEINFOR (BASESCHEMA);
create index IDS_BASEINFOR_BID_SCHEMA on BS_T_BPP_BASEINFOR (BASEID, BASESCHEMA);

prompt Creating BS_T_BPP_ENTRYATTRIBUTE...
create table BS_T_BPP_ENTRYATTRIBUTE
(
  ATTRIBUTEID VARCHAR2(15) not null,
  BASEID      VARCHAR2(15),
  BASESCHEMA  VARCHAR2(100),
  KEY         VARCHAR2(200),
  VALUE       VARCHAR2(1000)
)
;
create index IDS_ENTRYATTRIBUTE_BASEID on BS_T_BPP_ENTRYATTRIBUTE (BASEID);
create index IDS_ENTRYATTRIBUTE_BASESCHEMA on BS_T_BPP_ENTRYATTRIBUTE (BASESCHEMA);
create index IDS_ENTRYATTRIBUTE_BID_SCHEMA on BS_T_BPP_ENTRYATTRIBUTE (BASEID, BASESCHEMA);

prompt Creating BS_T_BPP_FREEACTIONFIELDREL...
create table BS_T_BPP_FREEACTIONFIELDREL
(
  ID         VARCHAR2(15) not null,
  BASESCHEMA VARCHAR2(100),
  ACTIONTYPE VARCHAR2(100),
  BASESTATUS VARCHAR2(100),
  FIELDID    VARCHAR2(15),
  VISIABLE   NUMBER,
  REQUIRED   NUMBER,
  FIELDTYPE  VARCHAR2(100)
)
;
alter table BS_T_BPP_FREEACTIONFIELDREL
  add constraint PK_BS_T_BPP_FREEACTIONFIELDREL primary key (ID);

prompt Creating BS_T_BPP_F_ASSIGNTREE...
create table BS_T_BPP_F_ASSIGNTREE
(
  ID           VARCHAR2(15) not null,
  BASESCHEMA   VARCHAR2(100),
  PARENTID     VARCHAR2(15),
  LABEL        VARCHAR2(100),
  FIELDNAME    VARCHAR2(100),
  VISIABLE     NUMBER,
  COLSPAN      NUMBER,
  ROWSPAN      NUMBER,
  ORDERNUM     NUMBER,
  HASDEPLOY    NUMBER,
  OPERATE      VARCHAR2(100),
  SINGLESELECT NUMBER,
  ACTION       VARCHAR2(50),
  NEXT         VARCHAR2(50)
)
;
comment on column BS_T_BPP_F_ASSIGNTREE.PARENTID
  is '所属容器';
comment on column BS_T_BPP_F_ASSIGNTREE.LABEL
  is '中文名';
comment on column BS_T_BPP_F_ASSIGNTREE.FIELDNAME
  is '英文名 ';
comment on column BS_T_BPP_F_ASSIGNTREE.VISIABLE
  is '是否可见';
comment on column BS_T_BPP_F_ASSIGNTREE.COLSPAN
  is '占列';
comment on column BS_T_BPP_F_ASSIGNTREE.ROWSPAN
  is '占行';
comment on column BS_T_BPP_F_ASSIGNTREE.ORDERNUM
  is '排序';
comment on column BS_T_BPP_F_ASSIGNTREE.SINGLESELECT
  is '单人派发';
comment on column BS_T_BPP_F_ASSIGNTREE.ACTION
  is '自定义动作';
comment on column BS_T_BPP_F_ASSIGNTREE.NEXT
  is '下一步处理人';
alter table BS_T_BPP_F_ASSIGNTREE
  add constraint PK_BS_T_BPP_F_ASSIGNTREE primary key (ID);

prompt Creating BS_T_BPP_F_ATTACHMENT...
create table BS_T_BPP_F_ATTACHMENT
(
  ID         VARCHAR2(15) not null,
  BASESCHEMA VARCHAR2(100),
  PARENTID   VARCHAR2(15),
  LABEL      VARCHAR2(100),
  FIELDNAME  VARCHAR2(100),
  VISIABLE   NUMBER,
  ORDERNUM   NUMBER,
  OPERATE    VARCHAR2(100),
  HASDEPLOY  NUMBER,
  TYPE       VARCHAR2(15)
)
;
comment on column BS_T_BPP_F_ATTACHMENT.PARENTID
  is '所属容器';
comment on column BS_T_BPP_F_ATTACHMENT.LABEL
  is '中文名';
comment on column BS_T_BPP_F_ATTACHMENT.FIELDNAME
  is '英文名';
comment on column BS_T_BPP_F_ATTACHMENT.VISIABLE
  is '是否可见';
comment on column BS_T_BPP_F_ATTACHMENT.ORDERNUM
  is '排序';
comment on column BS_T_BPP_F_ATTACHMENT.TYPE
  is '文件类型，*.*';

prompt Creating BS_T_BPP_F_BLANK...
create table BS_T_BPP_F_BLANK
(
  ID         VARCHAR2(15) not null,
  BASESCHEMA VARCHAR2(100),
  PARENTID   VARCHAR2(15),
  LABEL      VARCHAR2(100),
  FIELDNAME  VARCHAR2(100),
  VISIABLE   NUMBER,
  COLSPAN    NUMBER,
  ORDERNUM   NUMBER,
  ROWSPAN    NUMBER,
  HASDEPLOY  NUMBER,
  OPERATE    VARCHAR2(100)
)
;
comment on column BS_T_BPP_F_BLANK.PARENTID
  is '所属容器';
comment on column BS_T_BPP_F_BLANK.LABEL
  is '中文名,数据库中为空，页面隐藏，不进行配置';
comment on column BS_T_BPP_F_BLANK.FIELDNAME
  is '英文名';
comment on column BS_T_BPP_F_BLANK.VISIABLE
  is '是否可见';
comment on column BS_T_BPP_F_BLANK.COLSPAN
  is '占列';
comment on column BS_T_BPP_F_BLANK.ORDERNUM
  is '排序';
comment on column BS_T_BPP_F_BLANK.ROWSPAN
  is '占行';
alter table BS_T_BPP_F_BLANK
  add constraint PK_BS_T_BPP_F_BLANK primary key (ID);

prompt Creating BS_T_BPP_F_BUTTON...
create table BS_T_BPP_F_BUTTON
(
  ID         VARCHAR2(15) not null,
  BASESCHEMA VARCHAR2(100),
  PARENTID   VARCHAR2(15),
  LABEL      VARCHAR2(100),
  FIELDNAME  VARCHAR2(100),
  VISIABLE   NUMBER,
  COLSPAN    NUMBER,
  ORDERNUM   NUMBER,
  BUTTONCODE VARCHAR2(100),
  OPERATE    VARCHAR2(100),
  HASDEPLOY  NUMBER,
  ROWSPAN    NUMBER
)
;
comment on column BS_T_BPP_F_BUTTON.PARENTID
  is '所属容器';
comment on column BS_T_BPP_F_BUTTON.LABEL
  is '中文名';
comment on column BS_T_BPP_F_BUTTON.FIELDNAME
  is '英文名';
comment on column BS_T_BPP_F_BUTTON.VISIABLE
  is '是否可见';
comment on column BS_T_BPP_F_BUTTON.COLSPAN
  is '占列';
comment on column BS_T_BPP_F_BUTTON.ORDERNUM
  is '排序';
comment on column BS_T_BPP_F_BUTTON.BUTTONCODE
  is '按钮标识';
comment on column BS_T_BPP_F_BUTTON.ROWSPAN
  is '占行';
alter table BS_T_BPP_F_BUTTON
  add constraint PK_BS_T_BPP_F_BUTTON primary key (ID);

prompt Creating BS_T_BPP_F_CHARACTER...
create table BS_T_BPP_F_CHARACTER
(
  ID           VARCHAR2(15) not null,
  BASESCHEMA   VARCHAR2(100),
  PARENTID     VARCHAR2(15),
  LABEL        VARCHAR2(100),
  FIELDNAME    VARCHAR2(100),
  VISIABLE     NUMBER,
  COLSPAN      NUMBER,
  NEEDSAVE     NUMBER,
  ORDERNUM     NUMBER,
  LENGTH       NUMBER,
  DEFAULTVALUE VARCHAR2(100),
  ROWSPAN      NUMBER default 1,
  OPERATE      VARCHAR2(100),
  HASDEPLOY    NUMBER,
  ISCLOB       NUMBER default 0
)
;
comment on column BS_T_BPP_F_CHARACTER.ID
  is 'ID';
comment on column BS_T_BPP_F_CHARACTER.BASESCHEMA
  is '工单类型';
comment on column BS_T_BPP_F_CHARACTER.PARENTID
  is '所属容器';
comment on column BS_T_BPP_F_CHARACTER.LABEL
  is '中文名';
comment on column BS_T_BPP_F_CHARACTER.FIELDNAME
  is '英文名';
comment on column BS_T_BPP_F_CHARACTER.VISIABLE
  is '是否可见';
comment on column BS_T_BPP_F_CHARACTER.COLSPAN
  is '占列';
comment on column BS_T_BPP_F_CHARACTER.NEEDSAVE
  is '需要保存';
comment on column BS_T_BPP_F_CHARACTER.ORDERNUM
  is '排序';
comment on column BS_T_BPP_F_CHARACTER.LENGTH
  is '最大长度';
comment on column BS_T_BPP_F_CHARACTER.DEFAULTVALUE
  is '默认值';
comment on column BS_T_BPP_F_CHARACTER.ROWSPAN
  is '占行';
alter table BS_T_BPP_F_CHARACTER
  add constraint PK_BS_T_BPP_F_CHARACTER primary key (ID);

prompt Creating BS_T_BPP_F_COLLECT...
create table BS_T_BPP_F_COLLECT
(
  ID           VARCHAR2(15) not null,
  BASESCHEMA   VARCHAR2(100),
  PARENTID     VARCHAR2(15),
  LABEL        VARCHAR2(100),
  FIELDNAME    VARCHAR2(100),
  VISIABLE     NUMBER,
  COLSPAN      NUMBER,
  NEEDSAVE     NUMBER,
  ORDERNUM     NUMBER,
  DEFAULTVALUE VARCHAR2(100),
  LENGTH       NUMBER,
  HASDEPLOY    NUMBER,
  OPERATE      VARCHAR2(100),
  TYPERESOURCE VARCHAR2(100),
  TYPE         VARCHAR2(100),
  PARAS        VARCHAR2(100),
  SHOWTYPE     VARCHAR2(100),
  rowspan	number
)
;
comment on column BS_T_BPP_F_COLLECT.PARENTID
  is '所属容器';
comment on column BS_T_BPP_F_COLLECT.LABEL
  is '中文名';
comment on column BS_T_BPP_F_COLLECT.FIELDNAME
  is '英文名';
comment on column BS_T_BPP_F_COLLECT.VISIABLE
  is '是否可见';
comment on column BS_T_BPP_F_COLLECT.COLSPAN
  is '占列';
comment on column BS_T_BPP_F_COLLECT.NEEDSAVE
  is '需要保存';
comment on column BS_T_BPP_F_COLLECT.ORDERNUM
  is '排序';
comment on column BS_T_BPP_F_COLLECT.DEFAULTVALUE
  is '默认值';
comment on column BS_T_BPP_F_COLLECT.LENGTH
  is '最大长度';
comment on column BS_T_BPP_F_COLLECT.TYPERESOURCE
  is '数据源';
comment on column BS_T_BPP_F_COLLECT.TYPE
  is '数据源类型';
comment on column BS_T_BPP_F_COLLECT.PARAS
  is '查询参数';
comment on column BS_T_BPP_F_COLLECT.SHOWTYPE
  is '显示类型：SELECT下拉菜单，RADIO单选框，CHECK复选框';
alter table BS_T_BPP_F_COLLECT
  add constraint COLLECT_PK primary key (ID);

prompt Creating BS_T_BPP_F_HIDDEN...
create table BS_T_BPP_F_HIDDEN
(
  ID           VARCHAR2(15) not null,
  BASESCHEMA   VARCHAR2(100),
  PARENTID     VARCHAR2(15),
  LABEL        VARCHAR2(100),
  FIELDNAME    VARCHAR2(100),
  VISIABLE     NUMBER default 0,
  COLSPAN      NUMBER,
  NEEDSAVE     NUMBER,
  ORDERNUM     NUMBER,
  LENGTH       NUMBER,
  DEFAULTVALUE VARCHAR2(100),
  OPERATE      VARCHAR2(100),
  HASDEPLOY    NUMBER,
  ROWSPAN      NUMBER
)
;
comment on column BS_T_BPP_F_HIDDEN.ID
  is 'ID';
comment on column BS_T_BPP_F_HIDDEN.BASESCHEMA
  is '工单类型';
comment on column BS_T_BPP_F_HIDDEN.PARENTID
  is '所属容器';
comment on column BS_T_BPP_F_HIDDEN.LABEL
  is '中文名';
comment on column BS_T_BPP_F_HIDDEN.FIELDNAME
  is '英文名';
comment on column BS_T_BPP_F_HIDDEN.VISIABLE
  is '是否可见';
comment on column BS_T_BPP_F_HIDDEN.COLSPAN
  is '占列';
comment on column BS_T_BPP_F_HIDDEN.NEEDSAVE
  is '需要保存';
comment on column BS_T_BPP_F_HIDDEN.ORDERNUM
  is '排序';
comment on column BS_T_BPP_F_HIDDEN.LENGTH
  is '最大长度';
comment on column BS_T_BPP_F_HIDDEN.DEFAULTVALUE
  is '默认值';
comment on column BS_T_BPP_F_HIDDEN.ROWSPAN
  is '占行';
alter table BS_T_BPP_F_HIDDEN
  add constraint PK_BS_T_BPP_F_HIDDEN primary key (ID);

prompt Creating BS_T_BPP_F_LABEL...
create table BS_T_BPP_F_LABEL
(
  ID         VARCHAR2(15),
  BASESCHEMA VARCHAR2(50),
  PARENTID   VARCHAR2(50),
  LABEL      VARCHAR2(100),
  FIELDNAME  VARCHAR2(100),
  VISIABLE   NUMBER,
  COLSPAN    NUMBER,
  ORDERNUM   NUMBER,
  ROWSPAN    NUMBER,
  HASDEPLOY  NUMBER,
  OPERATE    VARCHAR2(50),
  CSSNAME    VARCHAR2(15)
)
;
comment on column BS_T_BPP_F_LABEL.PARENTID
  is '所属容器';
comment on column BS_T_BPP_F_LABEL.LABEL
  is '中文名';
comment on column BS_T_BPP_F_LABEL.FIELDNAME
  is '英文名';
comment on column BS_T_BPP_F_LABEL.VISIABLE
  is '是否可见';
comment on column BS_T_BPP_F_LABEL.COLSPAN
  is '占列';
comment on column BS_T_BPP_F_LABEL.ORDERNUM
  is '排序';
comment on column BS_T_BPP_F_LABEL.ROWSPAN
  is '占行';
comment on column BS_T_BPP_F_LABEL.CSSNAME
  is '样式';

prompt Creating BS_T_BPP_F_PANEL...
create table BS_T_BPP_F_PANEL
(
  ID         VARCHAR2(15) not null,
  BASESCHEMA VARCHAR2(100),
  PARENTID   VARCHAR2(15),
  LABEL      VARCHAR2(100),
  FIELDNAME  VARCHAR2(100),
  VISIABLE   NUMBER,
  ORDERNUM   NUMBER,
  HASDEPLOY  NUMBER,
  OPERATE    VARCHAR2(100)
)
;
alter table BS_T_BPP_F_PANEL
  add constraint PK_BS_T_BPP_F_PANEL primary key (ID);

prompt Creating BS_T_BPP_F_PANELGROUP...
create table BS_T_BPP_F_PANELGROUP
(
  ID           VARCHAR2(15) not null,
  BASESCHEMA   VARCHAR2(100),
  PARENTID     VARCHAR2(15),
  LABEL        VARCHAR2(100),
  FIELDNAME    VARCHAR2(100),
  VISIABLE     NUMBER default 1,
  ORDERNUM     NUMBER default 0,
  COLSPAN      NUMBER,
  TITLEVISIBLE NUMBER,
  TYPE         VARCHAR2(100),
  HASDEPLOY    NUMBER,
  OPERATE      VARCHAR2(100)
)
;
comment on column BS_T_BPP_F_PANELGROUP.PARENTID
  is '所属容器';
comment on column BS_T_BPP_F_PANELGROUP.LABEL
  is '中文名';
comment on column BS_T_BPP_F_PANELGROUP.FIELDNAME
  is '英文名';
comment on column BS_T_BPP_F_PANELGROUP.VISIABLE
  is '是否可见';
comment on column BS_T_BPP_F_PANELGROUP.ORDERNUM
  is '排序';
comment on column BS_T_BPP_F_PANELGROUP.COLSPAN
  is '占列';
comment on column BS_T_BPP_F_PANELGROUP.TITLEVISIBLE
  is '标题可见';
comment on column BS_T_BPP_F_PANELGROUP.TYPE
  is '容器类型，tab,panel选择';
alter table BS_T_BPP_F_PANELGROUP
  add constraint PK_BS_T_BPP_F_PANELGROUP primary key (ID);

prompt Creating BS_T_BPP_F_ROLLBACKLIST...
create table BS_T_BPP_F_ROLLBACKLIST
(
  ID           VARCHAR2(15) not null,
  BASESCHEMA   VARCHAR2(100),
  PARENTID     VARCHAR2(15),
  LABEL        VARCHAR2(100),
  FIELDNAME    VARCHAR2(100),
  VISIABLE     NUMBER,
  COLSPAN      NUMBER,
  ORDERNUM     NUMBER,
  OPERATE      VARCHAR2(100),
  HASDEPLOY    NUMBER,
  SINGLESELECT NUMBER,
  TYPE         VARCHAR2(15)
)
;
comment on column BS_T_BPP_F_ROLLBACKLIST.PARENTID
  is '所属容器';
comment on column BS_T_BPP_F_ROLLBACKLIST.LABEL
  is '中文名';
comment on column BS_T_BPP_F_ROLLBACKLIST.FIELDNAME
  is '英文名';
comment on column BS_T_BPP_F_ROLLBACKLIST.VISIABLE
  is '是否可见';
comment on column BS_T_BPP_F_ROLLBACKLIST.COLSPAN
  is '占列';
comment on column BS_T_BPP_F_ROLLBACKLIST.ORDERNUM
  is '排序';
comment on column BS_T_BPP_F_ROLLBACKLIST.SINGLESELECT
  is '单选';
comment on column BS_T_BPP_F_ROLLBACKLIST.TYPE
  is '回退类型，值为动作类型';

prompt Creating BS_T_BPP_F_SELECT...
create table BS_T_BPP_F_SELECT
(
  ID           VARCHAR2(15) not null,
  BASESCHEMA   VARCHAR2(100),
  PARENTID     VARCHAR2(15),
  LABEL        VARCHAR2(100),
  FIELDNAME    VARCHAR2(100),
  VISIABLE     NUMBER,
  COLSPAN      NUMBER,
  NEEDSAVE     NUMBER,
  ORDERNUM     NUMBER,
  DEFAULTVALUE VARCHAR2(100),
  LENGTH       NUMBER,
  HASDEPLOY    NUMBER,
  OPERATE      VARCHAR2(100),
  TYPERESOURCE VARCHAR2(100),
  TYPE         VARCHAR2(100),
  PARAS        VARCHAR2(100)
)
;
comment on column BS_T_BPP_F_SELECT.PARENTID
  is '所属容器';
comment on column BS_T_BPP_F_SELECT.LABEL
  is '中文名';
comment on column BS_T_BPP_F_SELECT.FIELDNAME
  is '英文名';
comment on column BS_T_BPP_F_SELECT.VISIABLE
  is '是否可见';
comment on column BS_T_BPP_F_SELECT.COLSPAN
  is '占列';
comment on column BS_T_BPP_F_SELECT.NEEDSAVE
  is '需要保存';
comment on column BS_T_BPP_F_SELECT.ORDERNUM
  is '排序';
comment on column BS_T_BPP_F_SELECT.DEFAULTVALUE
  is '默认值';
comment on column BS_T_BPP_F_SELECT.LENGTH
  is '最大长度';
comment on column BS_T_BPP_F_SELECT.TYPERESOURCE
  is '数据源';
comment on column BS_T_BPP_F_SELECT.TYPE
  is '数据源类型';
comment on column BS_T_BPP_F_SELECT.PARAS
  is '查询参数';
alter table BS_T_BPP_F_SELECT
  add constraint PK_BS_T_BPP_F_SELECT primary key (ID);

prompt Creating BS_T_BPP_F_TIME...
create table BS_T_BPP_F_TIME
(
  ID           VARCHAR2(15) not null,
  BASESCHEMA   VARCHAR2(100),
  PARENTID     VARCHAR2(15),
  LABEL        VARCHAR2(100),
  FIELDNAME    VARCHAR2(100),
  VISIABLE     NUMBER,
  COLSPAN      NUMBER,
  NEEDSAVE     NUMBER,
  ORDERNUM     NUMBER,
  DEFAULTVALUE VARCHAR2(100),
  LENGTH       NUMBER,
  TIMEFORMAT   VARCHAR2(100),
  HASDEPLOY    NUMBER,
  OPERATE      VARCHAR2(100)
)
;
comment on column BS_T_BPP_F_TIME.PARENTID
  is '所属容器';
comment on column BS_T_BPP_F_TIME.LABEL
  is '中文名';
comment on column BS_T_BPP_F_TIME.FIELDNAME
  is '英文名';
comment on column BS_T_BPP_F_TIME.VISIABLE
  is '是否可见';
comment on column BS_T_BPP_F_TIME.COLSPAN
  is '占列';
comment on column BS_T_BPP_F_TIME.NEEDSAVE
  is '需要保存';
comment on column BS_T_BPP_F_TIME.ORDERNUM
  is '排序';
comment on column BS_T_BPP_F_TIME.DEFAULTVALUE
  is '默认值';
comment on column BS_T_BPP_F_TIME.LENGTH
  is '最大长度';
comment on column BS_T_BPP_F_TIME.TIMEFORMAT
  is '时间格式';
alter table BS_T_BPP_F_TIME
  add constraint PK_BS_T_BPP_F_TIME primary key (ID);

prompt Creating BS_T_BPP_F_VIEW...
create table BS_T_BPP_F_VIEW
(
  ID         VARCHAR2(15) not null,
  BASESCHEMA VARCHAR2(100),
  PARENTID   VARCHAR2(15),
  LABEL      VARCHAR2(100),
  FIELDNAME  VARCHAR2(100),
  VISIABLE   NUMBER,
  COLSPAN    NUMBER,
  ORDERNUM   NUMBER,
  PAGEURL    VARCHAR2(2000),
  ROWSPAN    NUMBER,
  HASDEPLOY  NUMBER,
  OPERATE    VARCHAR2(100),
  TYPE       VARCHAR2(15)
)
;
comment on column BS_T_BPP_F_VIEW.PARENTID
  is '所属容器';
comment on column BS_T_BPP_F_VIEW.LABEL
  is '中文名';
comment on column BS_T_BPP_F_VIEW.FIELDNAME
  is '英文名';
comment on column BS_T_BPP_F_VIEW.VISIABLE
  is '是否可见';
comment on column BS_T_BPP_F_VIEW.COLSPAN
  is '占列';
comment on column BS_T_BPP_F_VIEW.ORDERNUM
  is '排序';
comment on column BS_T_BPP_F_VIEW.PAGEURL
  is '链接地址';
comment on column BS_T_BPP_F_VIEW.ROWSPAN
  is '占行';
comment on column BS_T_BPP_F_VIEW.TYPE
  is '加载类型，默认值frame，include,frame选择';
alter table BS_T_BPP_F_VIEW
  add constraint PK_BS_T_BPP_F_VIEW primary key (ID);

prompt Creating BS_T_BPP_STEP...
create table BS_T_BPP_STEP
(
  ID                  VARCHAR2(15) not null,
  BASESCHEMA          VARCHAR2(100),
  STEPNO              VARCHAR2(100),
  DESCRIPTION         VARCHAR2(200),
  ROLENAME            VARCHAR2(100),
  ROLEID              VARCHAR2(100),
  ROLEPROCESSROLETYPE VARCHAR2(100),
  ASSIGNEE            VARCHAR2(100),
  ASSIGNEEID          VARCHAR2(100),
  GROUPNAME           VARCHAR2(100),
  GROUPID             VARCHAR2(100),
  ROLEKEY             VARCHAR2(100),
  CONTEXTKEY          VARCHAR2(100),
  ACTIONTYPE          VARCHAR2(100),
  TASKPOLICY          VARCHAR2(100),
  ACCEPTOVER          NUMBER,
  DEALOVER            NUMBER,
  OPERATE             VARCHAR2(100),
  HASDEPLOY           NUMBER,
  HASSUBFLOW          NUMBER default 0
)
;
comment on column BS_T_BPP_STEP.HASSUBFLOW
  is '内部子流程';
alter table BS_T_BPP_STEP
  add constraint PK_BS_T_BPP_STEP primary key (ID);

prompt Creating BS_T_BPP_STEPFIELDREL...
create table BS_T_BPP_STEPFIELDREL
(
  ID         VARCHAR2(15) not null,
  BASESCHEMA VARCHAR2(100),
  STEPNO     VARCHAR2(100),
  FIELDID    VARCHAR2(15),
  VISIABLE   NUMBER,
  REQUIRED   NUMBER,
  FIELDTYPE  VARCHAR2(100)
)
;
alter table BS_T_BPP_STEPFIELDREL
  add constraint PK_BS_T_BPP_STEPFIELDREL primary key (ID);

prompt Creating BS_T_CMBC_ALARMNOTE...
create table BS_T_CMBC_ALARMNOTE
(
  PID             VARCHAR2(50 CHAR) not null,
  ALARMDESC       VARCHAR2(500 CHAR),
  ALARMHAPPENTIME NUMBER(19),
  ALARMID         VARCHAR2(100 CHAR),
  ALARMLOCAL      VARCHAR2(254 CHAR),
  ALARMONETYPE    VARCHAR2(254 CHAR),
  ALARMSTARTTIME  NUMBER(19),
  ALARMSYSTYPE    VARCHAR2(254 CHAR),
  ALARMTHREETYPE  VARCHAR2(254 CHAR),
  ALARMTITLE      VARCHAR2(500 CHAR),
  ALARMTWOTYPE    VARCHAR2(254 CHAR),
  DEP             VARCHAR2(254 CHAR),
  FAULTLOG        VARCHAR2(1000 CHAR),
  INPUTTIME       VARCHAR2(10 CHAR),
  ISCREATESHEET   VARCHAR2(10 CHAR)
)
;
alter table BS_T_CMBC_ALARMNOTE
  add primary key (PID);

prompt Creating BS_T_RECORDS_OPERLOG...
create table BS_T_RECORDS_OPERLOG
(
  PID      VARCHAR2(50) not null,
  RECORDID VARCHAR2(50),
  MODULEID VARCHAR2(50),
  OPERATOR VARCHAR2(254),
  OPERTIME NUMBER(15),
  MODULE   VARCHAR2(254),
  OPERDESC VARCHAR2(254)
)
;
comment on table BS_T_RECORDS_OPERLOG
  is '用户记录值班记录操作的日志';
comment on column BS_T_RECORDS_OPERLOG.PID
  is '主键';
comment on column BS_T_RECORDS_OPERLOG.RECORDID
  is '值班记录ID';
comment on column BS_T_RECORDS_OPERLOG.MODULEID
  is '模块ID';
comment on column BS_T_RECORDS_OPERLOG.OPERATOR
  is '操作人';
comment on column BS_T_RECORDS_OPERLOG.OPERTIME
  is '操作时间';
comment on column BS_T_RECORDS_OPERLOG.MODULE
  is '模块';
comment on column BS_T_RECORDS_OPERLOG.OPERDESC
  is '操作内容';
alter table BS_T_RECORDS_OPERLOG
  add constraint PK_BS_T_RECORDS_OPERLOG primary key (PID);

prompt Creating BS_T_SM_ATTACHMENT...
create table BS_T_SM_ATTACHMENT
(
  PID            VARCHAR2(50) not null,
  NAME           VARCHAR2(200),
  REALNAME       VARCHAR2(50),
  RELATIONCODE   VARCHAR2(50),
  TYPE           VARCHAR2(50),
  PATH           VARCHAR2(300),
  ATTSIZE        VARCHAR2(50),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15),
  REMARK         VARCHAR2(500)
)
;
comment on column BS_T_SM_ATTACHMENT.PID
  is '自动生成';
comment on column BS_T_SM_ATTACHMENT.NAME
  is '外部显示的名称';
comment on column BS_T_SM_ATTACHMENT.REALNAME
  is '存放到数据库的名称';
comment on column BS_T_SM_ATTACHMENT.RELATIONCODE
  is '关联编码';
comment on column BS_T_SM_ATTACHMENT.TYPE
  is '附件的类型分类';
comment on column BS_T_SM_ATTACHMENT.PATH
  is '存放在服务器的路径';
comment on column BS_T_SM_ATTACHMENT.ATTSIZE
  is '附件大小';
comment on column BS_T_SM_ATTACHMENT.CREATER
  is '创建人';
comment on column BS_T_SM_ATTACHMENT.CREATETIME
  is '创建时间';
comment on column BS_T_SM_ATTACHMENT.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_ATTACHMENT.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_ATTACHMENT.REMARK
  is '附件描述';
alter table BS_T_SM_ATTACHMENT
  add constraint PK_BS_T_SM_ATTACHMENT primary key (PID);

prompt Creating BS_T_SM_BUSINESSORG...
create table BS_T_SM_BUSINESSORG
(
  PID            VARCHAR2(50) not null,
  ORGID          VARCHAR2(50) not null,
  ORGNAME        VARCHAR2(100),
  PARENTID       VARCHAR2(50),
  ORGDNS         VARCHAR2(100),
  ORGDN          VARCHAR2(20),
  DEFINETYPE     VARCHAR2(50),
  ORDERNUM       NUMBER(5),
  STATUS         NUMBER(2),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15),
  REMARK         VARCHAR2(100)
)
;
comment on column BS_T_SM_BUSINESSORG.PID
  is '自动生成';
comment on column BS_T_SM_BUSINESSORG.ORGID
  is '组ID';
comment on column BS_T_SM_BUSINESSORG.ORGNAME
  is '角色名称';
comment on column BS_T_SM_BUSINESSORG.PARENTID
  is '上级角色ID，不能修改';
comment on column BS_T_SM_BUSINESSORG.ORGDNS
  is '角色DNS';
comment on column BS_T_SM_BUSINESSORG.ORGDN
  is '本级DN';
comment on column BS_T_SM_BUSINESSORG.DEFINETYPE
  is '用途分类';
comment on column BS_T_SM_BUSINESSORG.ORDERNUM
  is '影响排序顺序';
comment on column BS_T_SM_BUSINESSORG.STATUS
  is '1.启用；0.停用';
comment on column BS_T_SM_BUSINESSORG.CREATER
  is '创建人';
comment on column BS_T_SM_BUSINESSORG.CREATETIME
  is '创建时间';
comment on column BS_T_SM_BUSINESSORG.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_BUSINESSORG.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_BUSINESSORG.REMARK
  is '备注';
alter table BS_T_SM_BUSINESSORG
  add constraint PK_BS_T_SM_BUSINESSORG primary key (PID);

prompt Creating BS_T_SM_BUSINESSORGANDDEP...
create table BS_T_SM_BUSINESSORGANDDEP
(
  PID            VARCHAR2(50) not null,
  DEPID          VARCHAR2(50) not null,
  ORGID          VARCHAR2(50) not null,
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15)
)
;
comment on column BS_T_SM_BUSINESSORGANDDEP.PID
  is '自动生成';
comment on column BS_T_SM_BUSINESSORGANDDEP.DEPID
  is '组织机构部门ID';
comment on column BS_T_SM_BUSINESSORGANDDEP.ORGID
  is ' 业务组织ID';
comment on column BS_T_SM_BUSINESSORGANDDEP.CREATER
  is '创建人';
comment on column BS_T_SM_BUSINESSORGANDDEP.CREATETIME
  is '创建时间';
comment on column BS_T_SM_BUSINESSORGANDDEP.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_BUSINESSORGANDDEP.LASTMODIFYTIME
  is '最后修改时间';
alter table BS_T_SM_BUSINESSORGANDDEP
  add constraint PK_BS_T_SM_BUSINESSORGANDDEP primary key (PID);

prompt Creating BS_T_SM_BUSINESSORGANDUSER...
create table BS_T_SM_BUSINESSORGANDUSER
(
  PID            VARCHAR2(50) not null,
  USERID         VARCHAR2(50) not null,
  ORGID          VARCHAR2(50) not null,
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15)
)
;
comment on column BS_T_SM_BUSINESSORGANDUSER.PID
  is '自动生成';
comment on column BS_T_SM_BUSINESSORGANDUSER.USERID
  is '用户ID';
comment on column BS_T_SM_BUSINESSORGANDUSER.ORGID
  is '人员ID或机构ID';
comment on column BS_T_SM_BUSINESSORGANDUSER.CREATER
  is '创建人';
comment on column BS_T_SM_BUSINESSORGANDUSER.CREATETIME
  is '创建时间';
comment on column BS_T_SM_BUSINESSORGANDUSER.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_BUSINESSORGANDUSER.LASTMODIFYTIME
  is '最后修改时间';
alter table BS_T_SM_BUSINESSORGANDUSER
  add constraint PK_BS_T_SM_BUSINESSORGANDUSER primary key (PID);

prompt Creating BS_T_SM_CUSTOMCONFIG...
create table BS_T_SM_CUSTOMCONFIG
(
  PID            VARCHAR2(50) not null,
  SENDTREENAME   VARCHAR2(100),
  SENDTREEMARK   VARCHAR2(50),
  CONFIGTYPE     VARCHAR2(10),
  ROLEID         VARCHAR2(50),
  FORMTYPE       VARCHAR2(50),
  OPTYPE         VARCHAR2(10),
  RULESTRING     VARCHAR2(500),
  COMTYPE1       VARCHAR2(100),
  COMTYPE2       VARCHAR2(100),
  COMTYPE3       VARCHAR2(100),
  COMTYPE4       VARCHAR2(100),
  COMTYPE5       VARCHAR2(100),
  COMTYPE6       VARCHAR2(100),
  ORDERNUM       NUMBER(5),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15),
  REMARK         VARCHAR2(500)
)
;
comment on column BS_T_SM_CUSTOMCONFIG.PID
  is '主键ID';
comment on column BS_T_SM_CUSTOMCONFIG.SENDTREENAME
  is '派发树名称';
comment on column BS_T_SM_CUSTOMCONFIG.SENDTREEMARK
  is '当配置类型为特定时生效';
comment on column BS_T_SM_CUSTOMCONFIG.CONFIGTYPE
  is '常规、本片区、特定';
comment on column BS_T_SM_CUSTOMCONFIG.ROLEID
  is '当配置分类为常规时生效';
comment on column BS_T_SM_CUSTOMCONFIG.FORMTYPE
  is '配置分类为非特定时生效';
comment on column BS_T_SM_CUSTOMCONFIG.OPTYPE
  is '派发、提审';
comment on column BS_T_SM_CUSTOMCONFIG.RULESTRING
  is '将通用的字段类型1-6配置成规则放入到此字符串中';
comment on column BS_T_SM_CUSTOMCONFIG.COMTYPE1
  is '类型1';
comment on column BS_T_SM_CUSTOMCONFIG.COMTYPE2
  is '类型2';
comment on column BS_T_SM_CUSTOMCONFIG.COMTYPE3
  is '类型3';
comment on column BS_T_SM_CUSTOMCONFIG.COMTYPE4
  is '类型4';
comment on column BS_T_SM_CUSTOMCONFIG.COMTYPE5
  is '类型5';
comment on column BS_T_SM_CUSTOMCONFIG.COMTYPE6
  is '类型6';
comment on column BS_T_SM_CUSTOMCONFIG.ORDERNUM
  is '排序值';
comment on column BS_T_SM_CUSTOMCONFIG.CREATER
  is '创建人';
comment on column BS_T_SM_CUSTOMCONFIG.CREATETIME
  is '创建时间';
comment on column BS_T_SM_CUSTOMCONFIG.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_CUSTOMCONFIG.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_CUSTOMCONFIG.REMARK
  is '描述';
alter table BS_T_SM_CUSTOMCONFIG
  add constraint PK_BS_T_SM_CUSTOMCONFIG primary key (PID);

prompt Creating BS_T_SM_CUSTOMORGANIZE...
create table BS_T_SM_CUSTOMORGANIZE
(
  PID           VARCHAR2(50) not null,
  CUSTOMINFOPID VARCHAR2(50),
  ORGANIZETYPE  NUMBER(2),
  ORGANIZEPID   VARCHAR2(50)
)
;
comment on table BS_T_SM_CUSTOMORGANIZE
  is '自定义组织结构树子表';
comment on column BS_T_SM_CUSTOMORGANIZE.PID
  is '主键';
comment on column BS_T_SM_CUSTOMORGANIZE.CUSTOMINFOPID
  is '自定义树信息表id';
comment on column BS_T_SM_CUSTOMORGANIZE.ORGANIZETYPE
  is '组织类型(1、部门;2、人员)';
comment on column BS_T_SM_CUSTOMORGANIZE.ORGANIZEPID
  is '组织PID';
alter table BS_T_SM_CUSTOMORGANIZE
  add constraint PK_BS_T_SM_CUSTOMORGANIZE primary key (PID);

prompt Creating BS_T_SM_DEP...
create table BS_T_SM_DEP
(
  PID            VARCHAR2(50) not null,
  DEPNAME        VARCHAR2(100),
  DEPTYPE        VARCHAR2(20),
  GROUPTYPE      VARCHAR2(20),
  PARENTID       VARCHAR2(50),
  DEPFULLNAME    VARCHAR2(500),
  DEPDNS         VARCHAR2(100),
  DEPDN          VARCHAR2(20),
  ORDERNUM       NUMBER(5),
  DEPASSGINEE    VARCHAR2(200),
  DEPPHONE       VARCHAR2(200),
  DEPFAX         VARCHAR2(200),
  DEPEMAIL       VARCHAR2(100),
  STATUS         NUMBER(2),
  LOCATIONZONE   VARCHAR2(50),
  DEPIMAGE       VARCHAR2(50),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15),
  REMARK         VARCHAR2(500)
)
;
comment on column BS_T_SM_DEP.PID
  is '自动生成';
comment on column BS_T_SM_DEP.DEPNAME
  is '机构名称';
comment on column BS_T_SM_DEP.DEPTYPE
  is '公司、子公司、部门、组、虚拟组';
comment on column BS_T_SM_DEP.GROUPTYPE
  is '虚拟组类型';
comment on column BS_T_SM_DEP.PARENTID
  is '上级机构ID';
comment on column BS_T_SM_DEP.DEPFULLNAME
  is '机构全名';
comment on column BS_T_SM_DEP.DEPDNS
  is 'DNS';
comment on column BS_T_SM_DEP.DEPDN
  is '本级DN';
comment on column BS_T_SM_DEP.ORDERNUM
  is '用于显示顺序';
comment on column BS_T_SM_DEP.DEPASSGINEE
  is '部门负责人';
comment on column BS_T_SM_DEP.DEPPHONE
  is '联系电话';
comment on column BS_T_SM_DEP.DEPFAX
  is '传真';
comment on column BS_T_SM_DEP.DEPEMAIL
  is 'E_mail';
comment on column BS_T_SM_DEP.STATUS
  is '1.启用、0.停用
数据库存储1或2';
comment on column BS_T_SM_DEP.LOCATIONZONE
  is '片区';
comment on column BS_T_SM_DEP.DEPIMAGE
  is '图片存放路径(暂时不用,留作备用)';
comment on column BS_T_SM_DEP.CREATER
  is '创建人';
comment on column BS_T_SM_DEP.CREATETIME
  is '创建时间';
comment on column BS_T_SM_DEP.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_DEP.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_DEP.REMARK
  is '备注';
alter table BS_T_SM_DEP
  add constraint PK_BS_T_SM_DEP primary key (PID);

prompt Creating BS_T_SM_DEPATR...
create table BS_T_SM_DEPATR
(
  PID            VARCHAR2(50) not null,
  DEPID          VARCHAR2(50) not null,
  DEPNAME        VARCHAR2(100),
  BCOREID        VARCHAR2(50),
  BCORENAME      VARCHAR2(100),
  DEPID2         VARCHAR2(50),
  DEPNAME2       VARCHAR2(100),
  DEPID3         VARCHAR2(50),
  DEPNAME3       VARCHAR2(100),
  DEPID4         VARCHAR2(50),
  DEPNAME4       VARCHAR2(100),
  REGIONID       VARCHAR2(50),
  REGIONNAME     VARCHAR2(100),
  COREID         VARCHAR2(50),
  CORENAME       VARCHAR2(100),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15)
)
;
comment on column BS_T_SM_DEPATR.BCOREID
  is '部门所属的公司ID';
comment on column BS_T_SM_DEPATR.BCORENAME
  is '部门所属的公司';
comment on column BS_T_SM_DEPATR.REGIONID
  is '来源于数据字典';
comment on column BS_T_SM_DEPATR.COREID
  is '顶级公司ID';
comment on column BS_T_SM_DEPATR.CORENAME
  is '顶级公司名称';
comment on column BS_T_SM_DEPATR.CREATER
  is '创建人';
comment on column BS_T_SM_DEPATR.CREATETIME
  is '创建时间';
comment on column BS_T_SM_DEPATR.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_DEPATR.LASTMODIFYTIME
  is '最后修改时间';
alter table BS_T_SM_DEPATR
  add constraint PK_BS_T_SM_DEPATR primary key (PID);

prompt Creating BS_T_SM_DICITEM...
create table BS_T_SM_DICITEM
(
  PID            VARCHAR2(50) not null,
  DTID           VARCHAR2(50),
  DTCODE         VARCHAR2(50),
  DINAME         VARCHAR2(100),
  DIVALUE        VARCHAR2(20),
  DICDN          VARCHAR2(20),
  DICDNS         VARCHAR2(100),
  DICFULLNAME    VARCHAR2(500),
  ISDEFAULT      VARCHAR2(30),
  STATUS         NUMBER(2),
  ORDERNUM       NUMBER(5),
  PARENTID       VARCHAR2(50),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15),
  REMARK         VARCHAR2(500)
)
;
comment on column BS_T_SM_DICITEM.PID
  is '主键ID';
comment on column BS_T_SM_DICITEM.DTID
  is '数据类型ID';
comment on column BS_T_SM_DICITEM.DTCODE
  is '字典类型编码';
comment on column BS_T_SM_DICITEM.DINAME
  is '字典名称';
comment on column BS_T_SM_DICITEM.DIVALUE
  is '字典值';
comment on column BS_T_SM_DICITEM.DICDN
  is '字典DN';
comment on column BS_T_SM_DICITEM.DICDNS
  is '字典DNS';
comment on column BS_T_SM_DICITEM.DICFULLNAME
  is '字典全名';
comment on column BS_T_SM_DICITEM.ISDEFAULT
  is '是否缺省状态';
comment on column BS_T_SM_DICITEM.STATUS
  is '1.启用、0.停用';
comment on column BS_T_SM_DICITEM.ORDERNUM
  is '排序值';
comment on column BS_T_SM_DICITEM.PARENTID
  is '父信息ID';
comment on column BS_T_SM_DICITEM.CREATER
  is '创建人';
comment on column BS_T_SM_DICITEM.CREATETIME
  is '创建时间';
comment on column BS_T_SM_DICITEM.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_DICITEM.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_DICITEM.REMARK
  is '备注';
alter table BS_T_SM_DICITEM
  add constraint PK_BS_T_SM_DICITEM primary key (PID);

prompt Creating BS_T_SM_DICTYPE...
create table BS_T_SM_DICTYPE
(
  PID            VARCHAR2(50) not null,
  DTNAME         VARCHAR2(50),
  DTCODE         VARCHAR2(50),
  DICTYPE        VARCHAR2(20),
  STATUS         NUMBER(2),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15),
  REMARK         VARCHAR2(500)
)
;
comment on column BS_T_SM_DICTYPE.PID
  is '主键ID';
comment on column BS_T_SM_DICTYPE.DTNAME
  is '数据类型名称';
comment on column BS_T_SM_DICTYPE.DTCODE
  is '数据类型编码';
comment on column BS_T_SM_DICTYPE.DICTYPE
  is '字典分类';
comment on column BS_T_SM_DICTYPE.STATUS
  is '1.启用、0.停用';
comment on column BS_T_SM_DICTYPE.CREATER
  is '创建人';
comment on column BS_T_SM_DICTYPE.CREATETIME
  is '创建时间
';
comment on column BS_T_SM_DICTYPE.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_DICTYPE.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_DICTYPE.REMARK
  is '备注';
alter table BS_T_SM_DICTYPE
  add constraint PK_BS_T_SM_DICTYPE primary key (PID);

prompt Creating BS_T_SM_DTREEINFO...
create table BS_T_SM_DTREEINFO
(
  PID            VARCHAR2(50) not null,
  DTREENAME      VARCHAR2(100),
  DTREEMARK      VARCHAR2(50),
  DTREESTATUS    NUMBER(2),
  DTREETYPE      VARCHAR2(50),
  ORDERNUM       NUMBER(2),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15),
  REMARK         VARCHAR2(500)
)
;
comment on column BS_T_SM_DTREEINFO.PID
  is '自动生成';
comment on column BS_T_SM_DTREEINFO.DTREENAME
  is '派发树名称';
comment on column BS_T_SM_DTREEINFO.DTREEMARK
  is '派发树标识';
comment on column BS_T_SM_DTREEINFO.DTREESTATUS
  is '1.启用、0.停用';
comment on column BS_T_SM_DTREEINFO.DTREETYPE
  is '派发数类型';
comment on column BS_T_SM_DTREEINFO.ORDERNUM
  is '用于显示顺序';
comment on column BS_T_SM_DTREEINFO.CREATER
  is '创建人';
comment on column BS_T_SM_DTREEINFO.CREATETIME
  is '创建时间';
comment on column BS_T_SM_DTREEINFO.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_DTREEINFO.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_DTREEINFO.REMARK
  is '备注';
alter table BS_T_SM_DTREEINFO
  add constraint PK_BS_T_SM_DTREEINFO primary key (PID);

prompt Creating BS_T_SM_DTREENODEINFO...
create table BS_T_SM_DTREENODEINFO
(
  PID            VARCHAR2(50) not null,
  DTREEID        VARCHAR2(50),
  DEPID          VARCHAR2(50),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15)
)
;
comment on column BS_T_SM_DTREENODEINFO.PID
  is '自动生成';
comment on column BS_T_SM_DTREENODEINFO.DTREEID
  is '关联派发树基本信息表';
comment on column BS_T_SM_DTREENODEINFO.DEPID
  is '关联部门表';
comment on column BS_T_SM_DTREENODEINFO.CREATER
  is '创建人';
comment on column BS_T_SM_DTREENODEINFO.CREATETIME
  is '创建时间';
comment on column BS_T_SM_DTREENODEINFO.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_DTREENODEINFO.LASTMODIFYTIME
  is '最后修改时间';
alter table BS_T_SM_DTREENODEINFO
  add constraint PK_BS_T_SM_DTREENODEINFO primary key (PID);

prompt Creating BS_T_SM_EXCELEXPCFG...
create table BS_T_SM_EXCELEXPCFG
(
  PID             VARCHAR2(50) not null,
  CFGMARK         VARCHAR2(50),
  CFGNAME         VARCHAR2(100),
  EXCELNAME       VARCHAR2(100),
  TITLEROW        NUMBER(5),
  TITLECOL        NUMBER(5),
  COLMERGE        VARCHAR2(20),
  INTERLACEDCOLOR VARCHAR2(20),
  SHEETROW        NUMBER(10),
  CREATER         VARCHAR2(50),
  CREATETIME      NUMBER(15),
  LASTMODIFIER    VARCHAR2(50),
  LASTMODIFYTIME  NUMBER(15),
  REMARK          VARCHAR2(500)
)
;
comment on column BS_T_SM_EXCELEXPCFG.PID
  is '自动生成';
comment on column BS_T_SM_EXCELEXPCFG.CFGMARK
  is '配置标识';
comment on column BS_T_SM_EXCELEXPCFG.CFGNAME
  is '配置名称';
comment on column BS_T_SM_EXCELEXPCFG.EXCELNAME
  is '导出后的EXCEL名字';
comment on column BS_T_SM_EXCELEXPCFG.TITLEROW
  is '标题行数';
comment on column BS_T_SM_EXCELEXPCFG.TITLECOL
  is '标题列数';
comment on column BS_T_SM_EXCELEXPCFG.COLMERGE
  is '合并列信息（存储模式：起始列-终止列。即：从起始列合并到终止列）';
comment on column BS_T_SM_EXCELEXPCFG.INTERLACEDCOLOR
  is '导出的EXCEL信息区域是否采用隔行换色';
comment on column BS_T_SM_EXCELEXPCFG.SHEETROW
  is '每页条数';
comment on column BS_T_SM_EXCELEXPCFG.CREATER
  is '创建人';
comment on column BS_T_SM_EXCELEXPCFG.CREATETIME
  is '创建时间';
comment on column BS_T_SM_EXCELEXPCFG.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_EXCELEXPCFG.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_EXCELEXPCFG.REMARK
  is '备注';
alter table BS_T_SM_EXCELEXPCFG
  add constraint PK_BS_T_SM_EXCELEXPCFG primary key (PID);

prompt Creating BS_T_SM_EXCELEXPCFGFLD...
create table BS_T_SM_EXCELEXPCFGFLD
(
  PID            VARCHAR2(50) not null,
  EECRID         VARCHAR2(50),
  FIELDNAME      VARCHAR2(50),
  DISPLAYNAME    VARCHAR2(100),
  WIDTH          NUMBER(10),
  ROWSPAN        NUMBER(2),
  COLSPAN        NUMBER(2),
  ORDERNUM       NUMBER(5),
  ALIGN          VARCHAR2(10),
  COLCOLOR       VARCHAR2(20),
  ENABLE         VARCHAR2(10),
  ISGROUP        VARCHAR2(10),
  DATATYPE       VARCHAR2(20),
  DATAINFO       VARCHAR2(100),
  DATALENGTH     NUMBER(5),
  OPERATOR       VARCHAR2(10),
  COMPAREDATA    VARCHAR2(50),
  DISPLAYCOLOR   VARCHAR2(20),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15)
)
;
comment on column BS_T_SM_EXCELEXPCFGFLD.PID
  is 'PID';
comment on column BS_T_SM_EXCELEXPCFGFLD.EECRID
  is '导出配置行ID';
comment on column BS_T_SM_EXCELEXPCFGFLD.FIELDNAME
  is '字段英文名';
comment on column BS_T_SM_EXCELEXPCFGFLD.DISPLAYNAME
  is '导出显示字段名';
comment on column BS_T_SM_EXCELEXPCFGFLD.WIDTH
  is '列宽';
comment on column BS_T_SM_EXCELEXPCFGFLD.ROWSPAN
  is '跨行（默认为1）';
comment on column BS_T_SM_EXCELEXPCFGFLD.COLSPAN
  is '跨列（默认为1）';
comment on column BS_T_SM_EXCELEXPCFGFLD.ORDERNUM
  is '排序值（字段的显示顺序）';
comment on column BS_T_SM_EXCELEXPCFGFLD.ALIGN
  is '对其方式';
comment on column BS_T_SM_EXCELEXPCFGFLD.COLCOLOR
  is '列数据背景颜色';
comment on column BS_T_SM_EXCELEXPCFGFLD.ENABLE
  is '是否可用（真实导出时是否被合并）';
comment on column BS_T_SM_EXCELEXPCFGFLD.ISGROUP
  is '是否按此字段分sheet页';
comment on column BS_T_SM_EXCELEXPCFGFLD.DATATYPE
  is '字典、日期、数字、字符串';
comment on column BS_T_SM_EXCELEXPCFGFLD.DATAINFO
  is '当数据类型是字典时，此字段存字典类型；当数据类型是日期时，此字段存日期格式';
comment on column BS_T_SM_EXCELEXPCFGFLD.DATALENGTH
  is '数据长度';
comment on column BS_T_SM_EXCELEXPCFGFLD.OPERATOR
  is '操作符';
comment on column BS_T_SM_EXCELEXPCFGFLD.COMPAREDATA
  is '比较数据';
comment on column BS_T_SM_EXCELEXPCFGFLD.DISPLAYCOLOR
  is '显示颜色（和操作符、比较数据作为绑定的；当满足条件则展示此配置颜色）';
comment on column BS_T_SM_EXCELEXPCFGFLD.CREATER
  is '创建人';
comment on column BS_T_SM_EXCELEXPCFGFLD.CREATETIME
  is '创建时间';
comment on column BS_T_SM_EXCELEXPCFGFLD.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_EXCELEXPCFGFLD.LASTMODIFYTIME
  is '最后修改时间';
alter table BS_T_SM_EXCELEXPCFGFLD
  add constraint PK_BS_T_SM_EXCELEXPCFGFLD primary key (PID);

prompt Creating BS_T_SM_EXCELEXPCFGROW...
create table BS_T_SM_EXCELEXPCFGROW
(
  PID            VARCHAR2(50) not null,
  EECID          VARCHAR2(50),
  ROWNUMBER      NUMBER(2),
  DATAROW        VARCHAR2(10),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15)
)
;
comment on column BS_T_SM_EXCELEXPCFGROW.PID
  is '自动生成';
comment on column BS_T_SM_EXCELEXPCFGROW.EECID
  is '导出配置ID';
comment on column BS_T_SM_EXCELEXPCFGROW.ROWNUMBER
  is '行号';
comment on column BS_T_SM_EXCELEXPCFGROW.DATAROW
  is '1.标题行 0.非标题行';
comment on column BS_T_SM_EXCELEXPCFGROW.CREATER
  is '创建人';
comment on column BS_T_SM_EXCELEXPCFGROW.CREATETIME
  is '创建时间';
comment on column BS_T_SM_EXCELEXPCFGROW.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_EXCELEXPCFGROW.LASTMODIFYTIME
  is '最后修改时间';
alter table BS_T_SM_EXCELEXPCFGROW
  add constraint PK_BS_T_SM_EXCELEXPCFGROW primary key (PID);

prompt Creating BS_T_SM_FIELD...
create table BS_T_SM_FIELD
(
  PID            VARCHAR2(50) not null,
  ENNAME         VARCHAR2(50),
  FIELD          VARCHAR2(50),
  FIELDNAME      VARCHAR2(80),
  FIELDTYPE      VARCHAR2(20),
  LENGTH         NUMBER(3),
  PRECISION      NUMBER(3),
  ISREQUIRED     NUMBER(2),
  DEFAULTVALUE   VARCHAR2(50),
  DTCODE         VARCHAR2(50),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15),
  REMARK         VARCHAR2(500)
)
;
comment on column BS_T_SM_FIELD.PID
  is '自动生成';
comment on column BS_T_SM_FIELD.ENNAME
  is '表名';
comment on column BS_T_SM_FIELD.FIELD
  is '字段名';
comment on column BS_T_SM_FIELD.FIELDNAME
  is '字段中文名';
comment on column BS_T_SM_FIELD.FIELDTYPE
  is '字段类型';
comment on column BS_T_SM_FIELD.LENGTH
  is '字段长度';
comment on column BS_T_SM_FIELD.PRECISION
  is '字段精度';
comment on column BS_T_SM_FIELD.ISREQUIRED
  is '是、否';
comment on column BS_T_SM_FIELD.DEFAULTVALUE
  is '默认值';
comment on column BS_T_SM_FIELD.DTCODE
  is '数据字典类型';
comment on column BS_T_SM_FIELD.CREATER
  is '创建人';
comment on column BS_T_SM_FIELD.CREATETIME
  is '创建时间';
comment on column BS_T_SM_FIELD.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_FIELD.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_FIELD.REMARK
  is '备注';
alter table BS_T_SM_FIELD
  add constraint PK_BS_T_SM_FIELD primary key (PID);

prompt Creating BS_T_SM_FLUXSTATLOG...
create table BS_T_SM_FLUXSTATLOG
(
  PID         VARCHAR2(50) not null,
  TIME        NUMBER(15),
  IP          VARCHAR2(50),
  LOGINNAME   VARCHAR2(60),
  URL         VARCHAR2(500),
  USERNAME    VARCHAR2(100),
  DEPID       VARCHAR2(50),
  DEPNAME     VARCHAR2(100),
  COMPANYNAME VARCHAR2(100),
  NODENAME    VARCHAR2(100)
)
;
comment on table BS_T_SM_FLUXSTATLOG
  is '流量统计日志';
comment on column BS_T_SM_FLUXSTATLOG.PID
  is '自动生成';
comment on column BS_T_SM_FLUXSTATLOG.TIME
  is '记录时间';
comment on column BS_T_SM_FLUXSTATLOG.IP
  is '主机ip';
comment on column BS_T_SM_FLUXSTATLOG.LOGINNAME
  is '登陆名';
comment on column BS_T_SM_FLUXSTATLOG.URL
  is '受访页面';
comment on column BS_T_SM_FLUXSTATLOG.USERNAME
  is '用户全名';
comment on column BS_T_SM_FLUXSTATLOG.DEPID
  is '部门id';
comment on column BS_T_SM_FLUXSTATLOG.DEPNAME
  is '部门名称';
comment on column BS_T_SM_FLUXSTATLOG.COMPANYNAME
  is '公司名称';
comment on column BS_T_SM_FLUXSTATLOG.NODENAME
  is '操作名称';
alter table BS_T_SM_FLUXSTATLOG
  add constraint PK_BS_T_SM_FLUXSTATLOG primary key (PID);

prompt Creating BS_T_SM_FORMCUSTSENDTREE...
create table BS_T_SM_FORMCUSTSENDTREE
(
  PID            VARCHAR2(50) not null,
  TREENAME       VARCHAR2(80),
  BASESCHEMA     VARCHAR2(30),
  LOGINNAME      VARCHAR2(60),
  STATUS         NUMBER(2),
  ORDERNUM       NUMBER(5),
  DESCRIBE       VARCHAR2(300),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15)
)
;
comment on table BS_T_SM_FORMCUSTSENDTREE
  is '自定义工单派发树主表';
comment on column BS_T_SM_FORMCUSTSENDTREE.PID
  is '主键';
comment on column BS_T_SM_FORMCUSTSENDTREE.TREENAME
  is '自定义树名称';
comment on column BS_T_SM_FORMCUSTSENDTREE.BASESCHEMA
  is '工单类别';
comment on column BS_T_SM_FORMCUSTSENDTREE.LOGINNAME
  is '用户登录名';
comment on column BS_T_SM_FORMCUSTSENDTREE.STATUS
  is '1.启用、0.停用';
comment on column BS_T_SM_FORMCUSTSENDTREE.ORDERNUM
  is '排序值';
comment on column BS_T_SM_FORMCUSTSENDTREE.DESCRIBE
  is '描述';
comment on column BS_T_SM_FORMCUSTSENDTREE.CREATER
  is '创建人';
comment on column BS_T_SM_FORMCUSTSENDTREE.CREATETIME
  is '创建时间';
comment on column BS_T_SM_FORMCUSTSENDTREE.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_FORMCUSTSENDTREE.LASTMODIFYTIME
  is '最后修改时间';
alter table BS_T_SM_FORMCUSTSENDTREE
  add constraint PK_BS_T_SM_FORMCUSTSENDTREE primary key (PID);

prompt Creating BS_T_SM_HOLIDAY...
create table BS_T_SM_HOLIDAY
(
  PID         VARCHAR2(32) not null,
  DATEINFO    VARCHAR2(10),
  HOLIDAYNAME VARCHAR2(50),
  YEARS       INTEGER,
  MONTHS      INTEGER,
  DAYS        INTEGER,
  NOTE        VARCHAR2(500),
  HIDEFLAG    INTEGER,
  HOLIDAYFLAG INTEGER
)
;
comment on column BS_T_SM_HOLIDAY.PID
  is '主键';
comment on column BS_T_SM_HOLIDAY.DATEINFO
  is '日期';
comment on column BS_T_SM_HOLIDAY.HOLIDAYNAME
  is '节假日名称';
comment on column BS_T_SM_HOLIDAY.YEARS
  is '年';
comment on column BS_T_SM_HOLIDAY.MONTHS
  is '月';
comment on column BS_T_SM_HOLIDAY.DAYS
  is '日';
comment on column BS_T_SM_HOLIDAY.NOTE
  is '备注';
comment on column BS_T_SM_HOLIDAY.HIDEFLAG
  is '是否隐藏，1：是，0：否';
comment on column BS_T_SM_HOLIDAY.HOLIDAYFLAG
  is '是否是节假日，0:节假日，1:非节假日';
alter table BS_T_SM_HOLIDAY
  add constraint PK_BS_T_SM_HOLIDAY primary key (PID);

prompt Creating BS_T_SM_MENUTREE...
create table BS_T_SM_MENUTREE
(
  PID            VARCHAR2(50) not null,
  NODENAME       VARCHAR2(80),
  NODETYPE       NUMBER(2),
  PARENTID       VARCHAR2(50),
  NODEURL        VARCHAR2(500),
  STATUS         NUMBER(2),
  NODEMARK       VARCHAR2(100),
  OPENWAY        VARCHAR2(20),
  APPLYSYSNAME   VARCHAR2(20),
  MENUICON       VARCHAR2(50),
  NODEPATH       VARCHAR2(400),
  CLASSNAME      VARCHAR2(80),
  ORDERNUM       NUMBER(5),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15),
  REMARK         VARCHAR2(500)
)
;
comment on column BS_T_SM_MENUTREE.PID
  is '自动生成';
comment on column BS_T_SM_MENUTREE.NODENAME
  is '节点显示名称';
comment on column BS_T_SM_MENUTREE.NODETYPE
  is '节点类型(0.导航栏;1节点信息)通过字典配置';
comment on column BS_T_SM_MENUTREE.PARENTID
  is '父节点ID';
comment on column BS_T_SM_MENUTREE.NODEURL
  is '点击该节点链接的URL';
comment on column BS_T_SM_MENUTREE.STATUS
  is '1.启用、0.停用';
comment on column BS_T_SM_MENUTREE.NODEMARK
  is '节点标识(要求唯一性)';
comment on column BS_T_SM_MENUTREE.OPENWAY
  is '打开方式(0:首页模式;1.右侧打开;2.弹出打开;3:左树隐藏)';
comment on column BS_T_SM_MENUTREE.APPLYSYSNAME
  is '应用系统名';
comment on column BS_T_SM_MENUTREE.MENUICON
  is '图片存放路径';
comment on column BS_T_SM_MENUTREE.NODEPATH
  is '节点路径';
comment on column BS_T_SM_MENUTREE.CLASSNAME
  is '点击该节点调用的类名';
comment on column BS_T_SM_MENUTREE.ORDERNUM
  is '排序值';
comment on column BS_T_SM_MENUTREE.CREATER
  is '创建人';
comment on column BS_T_SM_MENUTREE.CREATETIME
  is '创建时间';
comment on column BS_T_SM_MENUTREE.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_MENUTREE.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_MENUTREE.REMARK
  is '备注';
alter table BS_T_SM_MENUTREE
  add constraint PK_BS_T_SM_MENUTREE primary key (PID);

prompt Creating BS_T_SM_MENUTREE2...
create table BS_T_SM_MENUTREE2
(
  PID            VARCHAR2(50),
  NODENAME       VARCHAR2(80),
  NODETYPE       NUMBER(2),
  PARENTID       VARCHAR2(50),
  NODEURL        VARCHAR2(500),
  STATUS         NUMBER(2),
  NODEMARK       VARCHAR2(100),
  OPENWAY        VARCHAR2(20),
  APPLYSYSNAME   VARCHAR2(20),
  MENUICON       VARCHAR2(50),
  NODEPATH       VARCHAR2(400),
  CLASSNAME      VARCHAR2(80),
  ORDERNUM       NUMBER(5),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15),
  REMARK         VARCHAR2(500)
)
;
comment on column BS_T_SM_MENUTREE2.PID
  is '自动生成';
comment on column BS_T_SM_MENUTREE2.NODENAME
  is '节点显示名称';
comment on column BS_T_SM_MENUTREE2.NODETYPE
  is '节点类型(0.导航栏;1节点信息)通过字典配置';
comment on column BS_T_SM_MENUTREE2.PARENTID
  is '父节点ID';
comment on column BS_T_SM_MENUTREE2.NODEURL
  is '点击该节点链接的URL';
comment on column BS_T_SM_MENUTREE2.STATUS
  is '1.启用、0.停用';
comment on column BS_T_SM_MENUTREE2.NODEMARK
  is '节点标识(要求唯一性)';
comment on column BS_T_SM_MENUTREE2.OPENWAY
  is '打开方式(0:首页模式;1.右侧打开;2.弹出打开;3:左树隐藏)';
comment on column BS_T_SM_MENUTREE2.APPLYSYSNAME
  is '应用系统名';
comment on column BS_T_SM_MENUTREE2.MENUICON
  is '图片存放路径';
comment on column BS_T_SM_MENUTREE2.NODEPATH
  is '节点路径';
comment on column BS_T_SM_MENUTREE2.CLASSNAME
  is '点击该节点调用的类名';
comment on column BS_T_SM_MENUTREE2.ORDERNUM
  is '排序值';
comment on column BS_T_SM_MENUTREE2.CREATER
  is '创建人';
comment on column BS_T_SM_MENUTREE2.CREATETIME
  is '创建时间';
comment on column BS_T_SM_MENUTREE2.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_MENUTREE2.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_MENUTREE2.REMARK
  is '备注';
alter table BS_T_SM_MENUTREE2
  add constraint BIN$hfhTQVvASbWxkRJ6pDSDCQ==$0 primary key (PID);
alter table BS_T_SM_MENUTREE2
  add constraint BIN$BpDPgLNsSyGFDmKZ7BhlCg==$0
  check ("PID" IS NOT NULL);
create unique index BIN$nZMJ6m6mRdm591F4O2p7zQ==$0 on BS_T_SM_MENUTREE2 (PID);

prompt Creating BS_T_SM_MYMENU...
create table BS_T_SM_MYMENU
(
  PID            VARCHAR2(50) not null,
  USERID         VARCHAR2(50),
  MENUID         VARCHAR2(50),
  NODENAME       VARCHAR2(80),
  PARENTID       VARCHAR2(50),
  NODETYPE       NUMBER(2),
  NODEURL        VARCHAR2(200),
  STATUS         NUMBER(2),
  NODEMARK       VARCHAR2(50),
  ORDERNUM       NUMBER(5),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15),
  REMARK         VARCHAR2(500)
)
;
comment on column BS_T_SM_MYMENU.PID
  is '自动生成';
comment on column BS_T_SM_MYMENU.USERID
  is '用户信息pid';
comment on column BS_T_SM_MYMENU.MENUID
  is '菜单节点PID';
comment on column BS_T_SM_MYMENU.NODENAME
  is '节点显示名称';
comment on column BS_T_SM_MYMENU.PARENTID
  is '父节点ID';
comment on column BS_T_SM_MYMENU.NODETYPE
  is '1:菜单夹 2:菜单';
comment on column BS_T_SM_MYMENU.NODEURL
  is '点击该节点链接的URL';
comment on column BS_T_SM_MYMENU.STATUS
  is '1.启用、0.停用';
comment on column BS_T_SM_MYMENU.NODEMARK
  is '节点标识(要求唯一性)';
comment on column BS_T_SM_MYMENU.ORDERNUM
  is '排序值';
comment on column BS_T_SM_MYMENU.CREATER
  is '创建人';
comment on column BS_T_SM_MYMENU.CREATETIME
  is '创建时间';
comment on column BS_T_SM_MYMENU.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_MYMENU.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_MYMENU.REMARK
  is '备注';
alter table BS_T_SM_MYMENU
  add constraint PK_BS_T_SM_MYMENU primary key (PID);

prompt Creating BS_T_SM_OPDATAPRIVILEGE...
create table BS_T_SM_OPDATAPRIVILEGE
(
  PID            VARCHAR2(50) not null,
  RROID          VARCHAR2(50),
  RESID          VARCHAR2(50),
  OPID           VARCHAR2(50),
  RESPROID       VARCHAR2(50),
  PRIVILEGEDATA  VARCHAR2(500),
  LISTVALUE      VARCHAR2(500),
  OPERATOR       VARCHAR2(10),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15)
)
;
comment on column BS_T_SM_OPDATAPRIVILEGE.PID
  is '自动生成';
comment on column BS_T_SM_OPDATAPRIVILEGE.RROID
  is '角色权限ID
RRO（ROLE  RESOURCE  OPERATE）';
comment on column BS_T_SM_OPDATAPRIVILEGE.RESID
  is '资源ID';
comment on column BS_T_SM_OPDATAPRIVILEGE.OPID
  is '操作ID';
comment on column BS_T_SM_OPDATAPRIVILEGE.RESPROID
  is '资源属性ID';
comment on column BS_T_SM_OPDATAPRIVILEGE.PRIVILEGEDATA
  is '用于判断的权限数据';
comment on column BS_T_SM_OPDATAPRIVILEGE.LISTVALUE
  is '用于展示的权限数据';
comment on column BS_T_SM_OPDATAPRIVILEGE.OPERATOR
  is '例如：=、<=、>等';
comment on column BS_T_SM_OPDATAPRIVILEGE.CREATER
  is '创建人';
comment on column BS_T_SM_OPDATAPRIVILEGE.CREATETIME
  is '创建时间';
comment on column BS_T_SM_OPDATAPRIVILEGE.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_OPDATAPRIVILEGE.LASTMODIFYTIME
  is '最后修改时间';
alter table BS_T_SM_OPDATAPRIVILEGE
  add constraint PK_BS_T_SM_OPDATAPRIVILEGE primary key (PID);

prompt Creating BS_T_SM_OPERATE...
create table BS_T_SM_OPERATE
(
  PID            VARCHAR2(50) not null,
  OPNAME         VARCHAR2(50),
  OPTYPE         VARCHAR2(20),
  STATUS         NUMBER(2),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15),
  REMARK         VARCHAR2(500)
)
;
comment on column BS_T_SM_OPERATE.PID
  is '主键ID';
comment on column BS_T_SM_OPERATE.OPNAME
  is '操作名称';
comment on column BS_T_SM_OPERATE.OPTYPE
  is '操作分类';
comment on column BS_T_SM_OPERATE.STATUS
  is '1.启用、0.停用';
comment on column BS_T_SM_OPERATE.CREATER
  is '创建人';
comment on column BS_T_SM_OPERATE.CREATETIME
  is '创建时间';
comment on column BS_T_SM_OPERATE.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_OPERATE.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_OPERATE.REMARK
  is '备注';
alter table BS_T_SM_OPERATE
  add constraint PK_BS_T_SM_OPERATE primary key (PID);

prompt Creating BS_T_SM_OPERAUDITLOG...
create table BS_T_SM_OPERAUDITLOG
(
  PID         VARCHAR2(50) not null,
  TIME        NUMBER(15),
  IP          VARCHAR2(50),
  LOGINNAME   VARCHAR2(60),
  MODULE      VARCHAR2(10),
  OPERTYPE    VARCHAR2(10),
  DESCRIPTION VARCHAR2(1000)
)
;
comment on table BS_T_SM_OPERAUDITLOG
  is '审计日志表';
comment on column BS_T_SM_OPERAUDITLOG.PID
  is '自动生成';
comment on column BS_T_SM_OPERAUDITLOG.TIME
  is '记录时间';
comment on column BS_T_SM_OPERAUDITLOG.IP
  is '主机ip';
comment on column BS_T_SM_OPERAUDITLOG.LOGINNAME
  is '登陆名';
comment on column BS_T_SM_OPERAUDITLOG.MODULE
  is '模块';
comment on column BS_T_SM_OPERAUDITLOG.OPERTYPE
  is '操作类型';
comment on column BS_T_SM_OPERAUDITLOG.DESCRIPTION
  is '描述';
alter table BS_T_SM_OPERAUDITLOG
  add constraint PK_BS_T_SM_OPERAUDITLOG primary key (PID);

prompt Creating BS_T_SM_POSITION...
create table BS_T_SM_POSITION
(
  PID          VARCHAR2(50) not null,
  POSITIONNAME VARCHAR2(100),
  ORDERNUM     NUMBER(5),
  STATUS       NUMBER(2)
)
;
comment on column BS_T_SM_POSITION.PID
  is '自动生成';
comment on column BS_T_SM_POSITION.POSITIONNAME
  is '手动输入';
comment on column BS_T_SM_POSITION.ORDERNUM
  is '影响排序顺序';
comment on column BS_T_SM_POSITION.STATUS
  is '1.启用；0.停用';
alter table BS_T_SM_POSITION
  add constraint PK_BS_T_SM_POSITION primary key (PID);

prompt Creating BS_T_SM_POSITIONDEP...
create table BS_T_SM_POSITIONDEP
(
  PID      VARCHAR2(50) not null,
  POSID    VARCHAR2(50),
  DEPLEVEL NUMBER(2),
  DEPID    VARCHAR2(50),
  DEPID1   VARCHAR2(50),
  DEPID2   VARCHAR2(50),
  DEPID3   VARCHAR2(50),
  DEPID4   VARCHAR2(50),
  DEPID5   VARCHAR2(50)
)
;
comment on column BS_T_SM_POSITIONDEP.PID
  is '自动生成';
comment on column BS_T_SM_POSITIONDEP.POSID
  is '职位ID';
comment on column BS_T_SM_POSITIONDEP.DEPLEVEL
  is '如：总公司肯定是顶级部门，则填1，子公司填2……';
comment on column BS_T_SM_POSITIONDEP.DEPID
  is '关联的部门ID';
comment on column BS_T_SM_POSITIONDEP.DEPID1
  is '顶级部门ID';
comment on column BS_T_SM_POSITIONDEP.DEPID2
  is '二级部门ID';
comment on column BS_T_SM_POSITIONDEP.DEPID3
  is '三级部门ID';
comment on column BS_T_SM_POSITIONDEP.DEPID4
  is '四级部门ID';
comment on column BS_T_SM_POSITIONDEP.DEPID5
  is '五级部门ID';
alter table BS_T_SM_POSITIONDEP
  add constraint PK_BS_T_SM_POSITIONDEP primary key (PID);

prompt Creating BS_T_SM_POSITIONUSER...
create table BS_T_SM_POSITIONUSER
(
  PID    VARCHAR2(50) not null,
  POSID  VARCHAR2(50),
  USERID VARCHAR2(50)
)
;
comment on column BS_T_SM_POSITIONUSER.PID
  is '自动生成';
comment on column BS_T_SM_POSITIONUSER.POSID
  is '职位ID';
comment on column BS_T_SM_POSITIONUSER.USERID
  is '用户ID';
alter table BS_T_SM_POSITIONUSER
  add constraint PK_BS_T_SM_POSITIONUSER primary key (PID);

prompt Creating BS_T_SM_PWDMANAGE...
create table BS_T_SM_PWDMANAGE
(
  PID            VARCHAR2(50) not null,
  LOGINNAME      VARCHAR2(60),
  PWD            VARCHAR2(120),
  VISIBLEPWD     VARCHAR2(50),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15)
)
;
comment on column BS_T_SM_PWDMANAGE.PID
  is '自动生成';
comment on column BS_T_SM_PWDMANAGE.LOGINNAME
  is '用户登录名';
comment on column BS_T_SM_PWDMANAGE.PWD
  is '用户密码';
comment on column BS_T_SM_PWDMANAGE.VISIBLEPWD
  is '可视密码';
comment on column BS_T_SM_PWDMANAGE.CREATER
  is '创建人';
comment on column BS_T_SM_PWDMANAGE.CREATETIME
  is '创建时间';
comment on column BS_T_SM_PWDMANAGE.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_PWDMANAGE.LASTMODIFYTIME
  is '最后修改时间';
alter table BS_T_SM_PWDMANAGE
  add constraint PK_BS_T_SM_PWDMANAGE primary key (PID);

prompt Creating BS_T_SM_RESOURCE...
create table BS_T_SM_RESOURCE
(
  PID            VARCHAR2(50) not null,
  RESNAME        VARCHAR2(80),
  PARENTID       VARCHAR2(50),
  SYSTEMTYPE     VARCHAR2(20),
  DEFINETYPE     VARCHAR2(50),
  STATUS         NUMBER(2),
  ORDERNUM       NUMBER(5),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15),
  REMARK         VARCHAR2(500)
)
;
comment on column BS_T_SM_RESOURCE.PID
  is '主键';
comment on column BS_T_SM_RESOURCE.RESNAME
  is '资源名称';
comment on column BS_T_SM_RESOURCE.PARENTID
  is '父资源ID';
comment on column BS_T_SM_RESOURCE.SYSTEMTYPE
  is '系统分类';
comment on column BS_T_SM_RESOURCE.DEFINETYPE
  is '自定义分类';
comment on column BS_T_SM_RESOURCE.STATUS
  is '1.启用、0.停用';
comment on column BS_T_SM_RESOURCE.ORDERNUM
  is '排序值';
comment on column BS_T_SM_RESOURCE.CREATER
  is '创建人';
comment on column BS_T_SM_RESOURCE.CREATETIME
  is '创建时间';
comment on column BS_T_SM_RESOURCE.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_RESOURCE.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_RESOURCE.REMARK
  is '备注';
alter table BS_T_SM_RESOURCE
  add constraint PK_BS_T_SM_RESOURCE primary key (PID);

prompt Creating BS_T_SM_RESOURCEURL...
create table BS_T_SM_RESOURCEURL
(
  PID            VARCHAR2(50) not null,
  RESID          VARCHAR2(50),
  OPID           VARCHAR2(50),
  RESNAME        VARCHAR2(80),
  OPNAME         VARCHAR2(50),
  URL            VARCHAR2(150),
  STATUS         NUMBER(2),
  REMARK         VARCHAR2(150),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15)
)
;
comment on table BS_T_SM_RESOURCEURL
  is '根据URL找对应的资源和操作';
comment on column BS_T_SM_RESOURCEURL.PID
  is '自动生成';
comment on column BS_T_SM_RESOURCEURL.RESID
  is '资源ID';
comment on column BS_T_SM_RESOURCEURL.OPID
  is '操作ID';
comment on column BS_T_SM_RESOURCEURL.URL
  is '资源和操作对应的URL';
comment on column BS_T_SM_RESOURCEURL.STATUS
  is '1.启用、0.停用';
comment on column BS_T_SM_RESOURCEURL.REMARK
  is '描述';
comment on column BS_T_SM_RESOURCEURL.CREATER
  is '创建人';
comment on column BS_T_SM_RESOURCEURL.CREATETIME
  is '创建时间';
comment on column BS_T_SM_RESOURCEURL.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_RESOURCEURL.LASTMODIFYTIME
  is '最后修改时间';
alter table BS_T_SM_RESOURCEURL
  add constraint PK_BS_T_SM_RESOURCEURL primary key (PID);

prompt Creating BS_T_SM_RESPROPERTY...
create table BS_T_SM_RESPROPERTY
(
  PID               VARCHAR2(50) not null,
  RESPID            VARCHAR2(50),
  FIELDNAME         VARCHAR2(20),
  FIELDDISPLAYVALUE VARCHAR2(50),
  INTYPE            NUMBER(2),
  INVALUETYPE       NUMBER(2),
  INDATASOURTYPE    NUMBER(2),
  INDATA            VARCHAR2(1000),
  ORDERNUM          NUMBER(5),
  CREATER           VARCHAR2(50),
  CREATETIME        NUMBER(15),
  LASTMODIFIER      VARCHAR2(50),
  LASTMODIFYTIME    NUMBER(15),
  REMARK            VARCHAR2(500)
)
;
comment on column BS_T_SM_RESPROPERTY.PID
  is '主键';
comment on column BS_T_SM_RESPROPERTY.RESPID
  is '资源表中的主键';
comment on column BS_T_SM_RESPROPERTY.FIELDDISPLAYVALUE
  is '例如：所属部门：';
comment on column BS_T_SM_RESPROPERTY.INTYPE
  is '1:文本域；2:弹出列表域';
comment on column BS_T_SM_RESPROPERTY.INVALUETYPE
  is '1:字符串；2:时间；3:数字';
comment on column BS_T_SM_RESPROPERTY.INDATASOURTYPE
  is '1:字典配置；2:脚本配置;3:人员树;4:机构树;4:人员机构树';
comment on column BS_T_SM_RESPROPERTY.INDATA
  is '如果”输入数据源类型”选择字典配置则存字典类型ID;如果选择脚本则存脚本的内容,选择其它则为空';
comment on column BS_T_SM_RESPROPERTY.CREATER
  is '创建人';
comment on column BS_T_SM_RESPROPERTY.CREATETIME
  is '创建时间';
comment on column BS_T_SM_RESPROPERTY.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_RESPROPERTY.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_RESPROPERTY.REMARK
  is '备注';
alter table BS_T_SM_RESPROPERTY
  add constraint PK_BS_T_SM_RESPROPERTY primary key (PID);

prompt Creating BS_T_SM_ROLE...
create table BS_T_SM_ROLE
(
  PID            VARCHAR2(50) not null,
  ROLENAME       VARCHAR2(60),
  PARENTID       VARCHAR2(50),
  ROLEDNS        VARCHAR2(100),
  ROLEDN         VARCHAR2(20),
  DEFINETYPE     VARCHAR2(50),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15),
  REMARK         VARCHAR2(500)
)
;
comment on column BS_T_SM_ROLE.PID
  is '自动生成';
comment on column BS_T_SM_ROLE.ROLENAME
  is '角色名称';
comment on column BS_T_SM_ROLE.PARENTID
  is '上级角色ID，不能修改';
comment on column BS_T_SM_ROLE.ROLEDNS
  is '角色DNS';
comment on column BS_T_SM_ROLE.ROLEDN
  is '本级DN';
comment on column BS_T_SM_ROLE.DEFINETYPE
  is '便于维护角色';
comment on column BS_T_SM_ROLE.CREATER
  is '创建人';
comment on column BS_T_SM_ROLE.CREATETIME
  is '创建时间';
comment on column BS_T_SM_ROLE.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_ROLE.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_ROLE.REMARK
  is '备注';
alter table BS_T_SM_ROLE
  add constraint PK_BS_T_SM_ROLE primary key (PID);

prompt Creating BS_T_SM_ROLEMENUTREE...
create table BS_T_SM_ROLEMENUTREE
(
  PID            VARCHAR2(50) not null,
  ROLEID         VARCHAR2(50),
  MENUID         VARCHAR2(50),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15)
)
;
comment on column BS_T_SM_ROLEMENUTREE.PID
  is '自动生成';
comment on column BS_T_SM_ROLEMENUTREE.ROLEID
  is '关联角色信息表';
comment on column BS_T_SM_ROLEMENUTREE.MENUID
  is '关联菜单目录信息表';
comment on column BS_T_SM_ROLEMENUTREE.CREATER
  is '创建人';
comment on column BS_T_SM_ROLEMENUTREE.CREATETIME
  is '创建时间';
comment on column BS_T_SM_ROLEMENUTREE.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_ROLEMENUTREE.LASTMODIFYTIME
  is '最后修改时间';
alter table BS_T_SM_ROLEMENUTREE
  add constraint PK_BS_T_SM_ROLEMENUTREE primary key (PID);

prompt Creating BS_T_SM_ROLEORG...
create table BS_T_SM_ROLEORG
(
  PID            VARCHAR2(50) not null,
  ROLEID         VARCHAR2(50),
  ORGID          VARCHAR2(50),
  ORGTYPE        NUMBER(2),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15)
)
;
comment on column BS_T_SM_ROLEORG.PID
  is '自动生成';
comment on column BS_T_SM_ROLEORG.ROLEID
  is '角色ID';
comment on column BS_T_SM_ROLEORG.ORGID
  is '人员ID或机构ID';
comment on column BS_T_SM_ROLEORG.ORGTYPE
  is '人员(1)、机构(2)';
comment on column BS_T_SM_ROLEORG.CREATER
  is '创建人';
comment on column BS_T_SM_ROLEORG.CREATETIME
  is '创建时间';
comment on column BS_T_SM_ROLEORG.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_ROLEORG.LASTMODIFYTIME
  is '最后修改时间';
alter table BS_T_SM_ROLEORG
  add constraint PK_BS_T_SM_ROLEORG primary key (PID);

prompt Creating BS_T_SM_ROLERESOP...
create table BS_T_SM_ROLERESOP
(
  PID            VARCHAR2(50) not null,
  ROLEID         VARCHAR2(50),
  RESID          VARCHAR2(50),
  OPID           VARCHAR2(50),
  STATUS         NUMBER(2),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15)
)
;
comment on column BS_T_SM_ROLERESOP.PID
  is '自动生成';
comment on column BS_T_SM_ROLERESOP.ROLEID
  is '角色ID';
comment on column BS_T_SM_ROLERESOP.RESID
  is '资源ID';
comment on column BS_T_SM_ROLERESOP.OPID
  is '操作ID';
comment on column BS_T_SM_ROLERESOP.STATUS
  is '1.启用、0.停用(此字段预存,暂时不用)';
comment on column BS_T_SM_ROLERESOP.CREATER
  is '创建人';
comment on column BS_T_SM_ROLERESOP.CREATETIME
  is '创建时间';
comment on column BS_T_SM_ROLERESOP.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_ROLERESOP.LASTMODIFYTIME
  is '最后修改时间';
alter table BS_T_SM_ROLERESOP
  add constraint PK_BS_T_SM_ROLERESOP primary key (PID);

prompt Creating BS_T_SM_RULETPL...
create table BS_T_SM_RULETPL
(
  PID              VARCHAR2(50) not null,
  RULETEMPLATENAME VARCHAR2(80),
  SYSTEMTYPE       VARCHAR2(20),
  DATASOURCE       VARCHAR2(50),
  IMPLCLASS        VARCHAR2(200),
  TPLMARK          VARCHAR2(20),
  STATUS           NUMBER(2),
  ORDERNUM         NUMBER(5),
  DESCRIBE         VARCHAR2(300),
  CREATER          VARCHAR2(50),
  CREATETIME       NUMBER(15),
  LASTMODIFIER     VARCHAR2(50),
  LASTMODIFYTIME   NUMBER(15)
)
;
comment on table BS_T_SM_RULETPL
  is '规则模板配置主表';
comment on column BS_T_SM_RULETPL.PID
  is '主键';
comment on column BS_T_SM_RULETPL.RULETEMPLATENAME
  is '规则模板名称';
comment on column BS_T_SM_RULETPL.SYSTEMTYPE
  is '系统分类';
comment on column BS_T_SM_RULETPL.DATASOURCE
  is '数据源名称';
comment on column BS_T_SM_RULETPL.IMPLCLASS
  is '实现类';
comment on column BS_T_SM_RULETPL.TPLMARK
  is '模版标识';
comment on column BS_T_SM_RULETPL.STATUS
  is '1.启用、0.停用';
comment on column BS_T_SM_RULETPL.ORDERNUM
  is '排序值';
comment on column BS_T_SM_RULETPL.DESCRIBE
  is '描述';
comment on column BS_T_SM_RULETPL.CREATER
  is '创建人';
comment on column BS_T_SM_RULETPL.CREATETIME
  is '创建时间';
comment on column BS_T_SM_RULETPL.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_RULETPL.LASTMODIFYTIME
  is '最后修改时间';
alter table BS_T_SM_RULETPL
  add constraint PK_BS_T_SM_RULETPL primary key (PID);

prompt Creating BS_T_SM_RULETPLPROPERTY...
create table BS_T_SM_RULETPLPROPERTY
(
  PID                 VARCHAR2(50) not null,
  RULETPLPID          VARCHAR2(50),
  FIELDID             VARCHAR2(20),
  FIELDNAME           VARCHAR2(50),
  INPUTTYPE           NUMBER(2),
  INPUTVALUETYPE      NUMBER(2),
  INPUTDATASOURCETYPE NUMBER(2),
  INDATA              VARCHAR2(500),
  STATUS              NUMBER(2),
  ORDERNUM            NUMBER(5),
  DESCRIBE            VARCHAR2(300),
  CREATER             VARCHAR2(50),
  CREATETIME          NUMBER(15),
  LASTMODIFIER        VARCHAR2(50),
  LASTMODIFYTIME      NUMBER(15)
)
;
comment on table BS_T_SM_RULETPLPROPERTY
  is '规则模板属性配置明细表';
comment on column BS_T_SM_RULETPLPROPERTY.PID
  is '主键';
comment on column BS_T_SM_RULETPLPROPERTY.RULETPLPID
  is '规则模板PID';
comment on column BS_T_SM_RULETPLPROPERTY.FIELDID
  is '例如：DEP';
comment on column BS_T_SM_RULETPLPROPERTY.FIELDNAME
  is '例如：所属部门';
comment on column BS_T_SM_RULETPLPROPERTY.INPUTTYPE
  is '属性输入方式(0:变量域；1:文本域；2:弹出列表域)';
comment on column BS_T_SM_RULETPLPROPERTY.INPUTVALUETYPE
  is '属性输入值类型(1:字符串；2:时间；3:数字)';
comment on column BS_T_SM_RULETPLPROPERTY.INPUTDATASOURCETYPE
  is '属性输入数据源类型(1:字典配置；2:脚本配置;3:人员树;4:机构树;4:人员机构树)';
comment on column BS_T_SM_RULETPLPROPERTY.INDATA
  is '如果”输入数据源类型”选择字典配置则存字典类型ID;如果选择脚本则存脚本的内容,选择其它则为空';
comment on column BS_T_SM_RULETPLPROPERTY.STATUS
  is '1.启用、0.停用';
comment on column BS_T_SM_RULETPLPROPERTY.ORDERNUM
  is '排序值';
comment on column BS_T_SM_RULETPLPROPERTY.DESCRIBE
  is '描述';
comment on column BS_T_SM_RULETPLPROPERTY.CREATER
  is '创建人';
comment on column BS_T_SM_RULETPLPROPERTY.CREATETIME
  is '创建时间';
comment on column BS_T_SM_RULETPLPROPERTY.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_RULETPLPROPERTY.LASTMODIFYTIME
  is '最后修改时间';
alter table BS_T_SM_RULETPLPROPERTY
  add constraint PK_BS_T_SM_RULETPLPROPERTY primary key (PID);

prompt Creating BS_T_SM_SEARCHCONDITION...
create table BS_T_SM_SEARCHCONDITION
(
  PID        VARCHAR2(50) not null,
  LOGINNAME  VARCHAR2(60),
  SQLNAME    VARCHAR2(100),
  CONDITIONS VARCHAR2(1000)
)
;
comment on column BS_T_SM_SEARCHCONDITION.PID
  is '主键ID';
comment on column BS_T_SM_SEARCHCONDITION.LOGINNAME
  is '用户登录名';
comment on column BS_T_SM_SEARCHCONDITION.SQLNAME
  is '搜索条件配置文件';
comment on column BS_T_SM_SEARCHCONDITION.CONDITIONS
  is '客户个性化搜索条件，以‘,’进行分隔';

prompt Creating BS_T_SM_SLADEALRECORD...
create table BS_T_SM_SLADEALRECORD
(
  PID      VARCHAR2(50) not null,
  RULEID   VARCHAR2(50),
  TASKID   VARCHAR2(50),
  DEALNUM  NUMBER(2),
  DEALTIME NUMBER(15)
)
;
comment on table BS_T_SM_SLADEALRECORD
  is 'SLA业务处理记录';
comment on column BS_T_SM_SLADEALRECORD.PID
  is '自动生成';
comment on column BS_T_SM_SLADEALRECORD.RULEID
  is 'SLA动作ID';
comment on column BS_T_SM_SLADEALRECORD.TASKID
  is '业务数据ID';
comment on column BS_T_SM_SLADEALRECORD.DEALNUM
  is '处理次数';
comment on column BS_T_SM_SLADEALRECORD.DEALTIME
  is '处理时间';
alter table BS_T_SM_SLADEALRECORD
  add constraint PK_BS_T_SM_SLADEALRECORD primary key (PID);

prompt Creating BS_T_SM_SLAMAILACTION...
create table BS_T_SM_SLAMAILACTION
(
  PID                VARCHAR2(50) not null,
  RULETPLID          VARCHAR2(50),
  ACTIONNAME         VARCHAR2(50),
  ACTIONTYPE         VARCHAR2(20),
  ACTIONMARK         VARCHAR2(20),
  DEALMODE           NUMBER(2),
  STATUS             NUMBER(2),
  PRI                NUMBER(2),
  ORDERNUM           NUMBER(5),
  ISHOLIDAY          NUMBER(2),
  ISBUSINESSINFORMER NUMBER(2),
  NOTICEUSERID       VARCHAR2(1500),
  NOTICEUSERNAME     VARCHAR2(3000),
  NOTICEGROUPID      VARCHAR2(1500),
  NOTICEGROUPNAME    VARCHAR2(3000),
  COPYUSERID         VARCHAR2(1500),
  COPYUSERNAME       VARCHAR2(3000),
  COPYGROUPID        VARCHAR2(1500),
  COPYGROUPNAME      VARCHAR2(3000),
  SENDNUM            NUMBER(5),
  SENDSEPTIME        VARCHAR2(10),
  MAILSUBJECT        VARCHAR2(500),
  MAILCONTENT        VARCHAR2(2000),
  REMARK             VARCHAR2(300),
  CREATER            VARCHAR2(50),
  CREATETIME         NUMBER(15),
  LASTMODIFIER       VARCHAR2(50),
  LASTMODIFYTIME     NUMBER(15),
  THREADNO           NUMBER(2),
  SLAROLEMODEL       VARCHAR2(20),
  NOTICEROLEID       VARCHAR2(1500),
  NOTICEROLENAME     VARCHAR2(3000),
  DEALTIME           NUMBER(4) default 1,
  STEPTIME           NUMBER(10)
)
;
comment on table BS_T_SM_SLAMAILACTION
  is 'SLA邮件动作配置表';
comment on column BS_T_SM_SLAMAILACTION.PID
  is '主键';
comment on column BS_T_SM_SLAMAILACTION.RULETPLID
  is '模板ID';
comment on column BS_T_SM_SLAMAILACTION.ACTIONNAME
  is '动作名称';
comment on column BS_T_SM_SLAMAILACTION.ACTIONTYPE
  is '动作类型';
comment on column BS_T_SM_SLAMAILACTION.ACTIONMARK
  is '动作标识';
comment on column BS_T_SM_SLAMAILACTION.DEALMODE
  is '处理模式';
comment on column BS_T_SM_SLAMAILACTION.STATUS
  is '1.启用、0.停用';
comment on column BS_T_SM_SLAMAILACTION.PRI
  is '优先级(1:低 2:一般 3:中 4:高 5:紧急)';
comment on column BS_T_SM_SLAMAILACTION.ORDERNUM
  is '排序值';
comment on column BS_T_SM_SLAMAILACTION.ISHOLIDAY
  is '节假日除外(1：是 2 ： 否)';
comment on column BS_T_SM_SLAMAILACTION.ISBUSINESSINFORMER
  is '任务通知人/组(1:是 2:否)';
comment on column BS_T_SM_SLAMAILACTION.NOTICEUSERID
  is '自定义通知人Id(id,多个用,分割)';
comment on column BS_T_SM_SLAMAILACTION.NOTICEUSERNAME
  is '自定义通知人Name(名称,多个用,分割)';
comment on column BS_T_SM_SLAMAILACTION.NOTICEGROUPID
  is '自定义通知组Id(id,多个用,分割)';
comment on column BS_T_SM_SLAMAILACTION.NOTICEGROUPNAME
  is '自定义通知组Name(名称多个用,分割)';
comment on column BS_T_SM_SLAMAILACTION.COPYUSERID
  is '自定义抄送人Id(id,多个用,分割)';
comment on column BS_T_SM_SLAMAILACTION.COPYUSERNAME
  is '自定义抄送人Name(名称,多个用,分割)';
comment on column BS_T_SM_SLAMAILACTION.COPYGROUPID
  is '自定义抄送组Id(组id,多个用,分割)';
comment on column BS_T_SM_SLAMAILACTION.COPYGROUPNAME
  is '自定义抄送组Name(组名称多个用,分割)';
comment on column BS_T_SM_SLAMAILACTION.SENDNUM
  is '发送次数,预留字段(暂时不启用)';
comment on column BS_T_SM_SLAMAILACTION.SENDSEPTIME
  is '发送间隔时间(单位分钟),预留字段(暂不启用)';
comment on column BS_T_SM_SLAMAILACTION.MAILSUBJECT
  is '邮件主题';
comment on column BS_T_SM_SLAMAILACTION.MAILCONTENT
  is '邮件内容';
comment on column BS_T_SM_SLAMAILACTION.REMARK
  is '备注';
comment on column BS_T_SM_SLAMAILACTION.CREATER
  is '创建人';
comment on column BS_T_SM_SLAMAILACTION.CREATETIME
  is '创建时间';
comment on column BS_T_SM_SLAMAILACTION.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_SLAMAILACTION.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_SLAMAILACTION.THREADNO
  is '线程号，用于哪个线程来执行';
comment on column BS_T_SM_SLAMAILACTION.DEALTIME
  is '发送次数';
comment on column BS_T_SM_SLAMAILACTION.STEPTIME
  is '间隔时间';
alter table BS_T_SM_SLAMAILACTION
  add constraint PK_BS_T_SM_SLAMAILACTION primary key (PID);

prompt Creating BS_T_SM_SLARULE...
create table BS_T_SM_SLARULE
(
  PID            VARCHAR2(50) not null,
  RULEIDENTIFIER VARCHAR2(30),
  ACTIONID       VARCHAR2(50),
  STATUS         NUMBER(2),
  PRI            NUMBER(2),
  DESCRIBE       VARCHAR2(300),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15),
  RULENAME       VARCHAR2(80),
  ORDERNUM       NUMBER(5)
)
;
comment on table BS_T_SM_SLARULE
  is 'SLA规则配置表';
comment on column BS_T_SM_SLARULE.PID
  is '自动生成';
comment on column BS_T_SM_SLARULE.RULEIDENTIFIER
  is '唯一标识';
comment on column BS_T_SM_SLARULE.ACTIONID
  is 'SLA动作ID';
comment on column BS_T_SM_SLARULE.STATUS
  is '1.启用、0.停用';
comment on column BS_T_SM_SLARULE.PRI
  is '优先级(1:低 2:一般 3:中 4:高 5:紧急)';
comment on column BS_T_SM_SLARULE.DESCRIBE
  is '描述';
comment on column BS_T_SM_SLARULE.CREATER
  is '创建人';
comment on column BS_T_SM_SLARULE.CREATETIME
  is '创建时间';
comment on column BS_T_SM_SLARULE.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_SLARULE.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_SLARULE.RULENAME
  is '规则名称';
comment on column BS_T_SM_SLARULE.ORDERNUM
  is '排序值';
alter table BS_T_SM_SLARULE
  add constraint PK_BS_T_SM_SLARULE primary key (PID);

prompt Creating BS_T_SM_SLARULEPROPERTY...
create table BS_T_SM_SLARULEPROPERTY
(
  PID          VARCHAR2(50) not null,
  RULEID       VARCHAR2(50),
  PROPERTYID   VARCHAR2(50),
  VALUE        VARCHAR2(500),
  DISPLAYVALUE VARCHAR2(500),
  OPERATOR     VARCHAR2(10)
)
;
comment on table BS_T_SM_SLARULEPROPERTY
  is 'SLA规则属性表';
comment on column BS_T_SM_SLARULEPROPERTY.PID
  is '自动生成';
comment on column BS_T_SM_SLARULEPROPERTY.RULEID
  is 'SLA规则配置表主键';
comment on column BS_T_SM_SLARULEPROPERTY.PROPERTYID
  is 'SLA规则模板属性配置ID';
comment on column BS_T_SM_SLARULEPROPERTY.VALUE
  is '真实的数据';
comment on column BS_T_SM_SLARULEPROPERTY.DISPLAYVALUE
  is '显示的数据';
comment on column BS_T_SM_SLARULEPROPERTY.OPERATOR
  is '例如：=、<=、>等';
alter table BS_T_SM_SLARULEPROPERTY
  add constraint PK_BS_T_SM_SLARULEPROPERTY primary key (PID);

prompt Creating BS_T_SM_SLASMACTION...
create table BS_T_SM_SLASMACTION
(
  PID                VARCHAR2(50) not null,
  RULETPLID          VARCHAR2(50),
  ACTIONNAME         VARCHAR2(50),
  ACTIONTYPE         VARCHAR2(20),
  ACTIONMARK         VARCHAR2(20),
  DEALMODE           NUMBER(2),
  STATUS             NUMBER(2),
  PRI                NUMBER(2),
  ORDERNUM           NUMBER(5),
  ISHOLIDAY          NUMBER(2),
  STARTTIME          VARCHAR2(10),
  ENDTIME            VARCHAR2(10),
  ISBUSINESSINFORMER NUMBER(2),
  NOTICEUSERID       VARCHAR2(1500),
  NOTICEUSERNAME     VARCHAR2(3000),
  NOTICEGROUPID      VARCHAR2(1500),
  NOTICEGROUPNAME    VARCHAR2(3000),
  SENDNUM            NUMBER(5),
  SENDSEPTIME        VARCHAR2(10),
  SMCONTENT          VARCHAR2(300),
  REMARK             VARCHAR2(300),
  CREATER            VARCHAR2(50),
  CREATETIME         NUMBER(15),
  LASTMODIFIER       VARCHAR2(50),
  LASTMODIFYTIME     NUMBER(15),
  THREADNO           NUMBER(2),
  SLAROLEMODEL       VARCHAR2(20),
  NOTICEROLEID       VARCHAR2(1500),
  NOTICEROLENAME     VARCHAR2(3000),
  DEALTIME           NUMBER(4) default 1,
  STEPTIME           NUMBER(10)
)
;
comment on table BS_T_SM_SLASMACTION
  is 'SLA短信动作配置表';
comment on column BS_T_SM_SLASMACTION.PID
  is '主键';
comment on column BS_T_SM_SLASMACTION.RULETPLID
  is '模板ID';
comment on column BS_T_SM_SLASMACTION.ACTIONNAME
  is '动作名称';
comment on column BS_T_SM_SLASMACTION.ACTIONTYPE
  is '动作类型';
comment on column BS_T_SM_SLASMACTION.ACTIONMARK
  is '动作标识';
comment on column BS_T_SM_SLASMACTION.DEALMODE
  is '处理模式';
comment on column BS_T_SM_SLASMACTION.STATUS
  is '1.启用、0.停用';
comment on column BS_T_SM_SLASMACTION.PRI
  is '优先级(1:低 2:一般 3:中 4:高 5:紧急)';
comment on column BS_T_SM_SLASMACTION.ORDERNUM
  is '排序值';
comment on column BS_T_SM_SLASMACTION.ISHOLIDAY
  is '节假日除外(1：是 2 ： 否)';
comment on column BS_T_SM_SLASMACTION.STARTTIME
  is '接收开始时间';
comment on column BS_T_SM_SLASMACTION.ENDTIME
  is '接收结束时间';
comment on column BS_T_SM_SLASMACTION.ISBUSINESSINFORMER
  is '任务通知人/组(1:是 2:否)';
comment on column BS_T_SM_SLASMACTION.NOTICEUSERID
  is '通知人id,多个用,分割';
comment on column BS_T_SM_SLASMACTION.NOTICEUSERNAME
  is '通知人名称,多个用,分割';
comment on column BS_T_SM_SLASMACTION.NOTICEGROUPID
  is '通知组id,多个用,分割';
comment on column BS_T_SM_SLASMACTION.NOTICEGROUPNAME
  is '通知组名称多个用,分割';
comment on column BS_T_SM_SLASMACTION.SENDNUM
  is '发送次数,预留字段(暂时不启用)';
comment on column BS_T_SM_SLASMACTION.SENDSEPTIME
  is '发送间隔时间(单位分钟),预留字段(暂不启用)';
comment on column BS_T_SM_SLASMACTION.SMCONTENT
  is '短信内容';
comment on column BS_T_SM_SLASMACTION.REMARK
  is '备注';
comment on column BS_T_SM_SLASMACTION.CREATER
  is '创建人';
comment on column BS_T_SM_SLASMACTION.CREATETIME
  is '创建时间';
comment on column BS_T_SM_SLASMACTION.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_SLASMACTION.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_SLASMACTION.THREADNO
  is '线程号，用于哪个线程来执行';
comment on column BS_T_SM_SLASMACTION.DEALTIME
  is '发送次数';
comment on column BS_T_SM_SLASMACTION.STEPTIME
  is '发送多次的间隔时间';
alter table BS_T_SM_SLASMACTION
  add constraint PK_BS_T_SM_SLASMACTION primary key (PID);

prompt Creating BS_T_SM_SMBASEITEM...
create table BS_T_SM_SMBASEITEM
(
  PID        VARCHAR2(50) not null,
  LOGINNAME  VARCHAR2(100),
  BASESCHEMA VARCHAR2(150),
  BASEITEM   VARCHAR2(200)
)
;
comment on table BS_T_SM_SMBASEITEM
  is '工单短信订阅维度修改';
comment on column BS_T_SM_SMBASEITEM.LOGINNAME
  is '订阅人';
comment on column BS_T_SM_SMBASEITEM.BASESCHEMA
  is 'baseschema';
comment on column BS_T_SM_SMBASEITEM.BASEITEM
  is '专业';
alter table BS_T_SM_SMBASEITEM
  add constraint PK_BS_T_SM_SMBASEITEM primary key (PID);

prompt Creating BS_T_SM_SMSBASEITEMCFG...
create table BS_T_SM_SMSBASEITEMCFG
(
  PID        VARCHAR2(50) not null,
  BASESCHEMA VARCHAR2(150),
  ITEMSOURCE VARCHAR2(1000),
  STATUS     NUMBER(2)
)
;
comment on table BS_T_SM_SMSBASEITEMCFG
  is '短信订阅相关配置';
comment on column BS_T_SM_SMSBASEITEMCFG.BASESCHEMA
  is 'baseschema';
comment on column BS_T_SM_SMSBASEITEMCFG.ITEMSOURCE
  is '查询专业的sql';
comment on column BS_T_SM_SMSBASEITEMCFG.STATUS
  is '1.启用、0.停用';
alter table BS_T_SM_SMSBASEITEMCFG
  add constraint PK_BS_T_SM_SMSBASEITEMCFG primary key (PID);

prompt Creating BS_T_SM_SMSMONITOR...
create table BS_T_SM_SMSMONITOR
(
  PID           VARCHAR2(50) not null,
  CONTENT       VARCHAR2(500),
  SYSTEMTYPE    VARCHAR2(50),
  GOAL          VARCHAR2(20),
  SENDFLAG      NUMBER(2),
  PRESENDTIME   NUMBER(15) default 0,
  RELATEID      VARCHAR2(50),
  GATEWAYCODE   VARCHAR2(50),
  GATEWAYCRIBE  VARCHAR2(300),
  SENDNUM       NUMBER(4) default 0,
  FIRSTSENDTIME NUMBER(15),
  SENDTIME      NUMBER(15),
  PRI           NUMBER(5) default 0,
  INFOINPUTTIME NUMBER(15) default 0,
  REMATK        VARCHAR2(50)
)
;
comment on column BS_T_SM_SMSMONITOR.PID
  is '主键';
comment on column BS_T_SM_SMSMONITOR.CONTENT
  is '发送到手机的内容';
comment on column BS_T_SM_SMSMONITOR.SYSTEMTYPE
  is '信息类型';
comment on column BS_T_SM_SMSMONITOR.GOAL
  is '接收信息的手机号码';
comment on column BS_T_SM_SMSMONITOR.SENDFLAG
  is '0未发送:1已发送;2发送失败';
comment on column BS_T_SM_SMSMONITOR.PRESENDTIME
  is '定时发送时间';
comment on column BS_T_SM_SMSMONITOR.RELATEID
  is '业务关联ID';
comment on column BS_T_SM_SMSMONITOR.GATEWAYCODE
  is '网关回复标识代码';
comment on column BS_T_SM_SMSMONITOR.GATEWAYCRIBE
  is '网关反馈描述';
comment on column BS_T_SM_SMSMONITOR.SENDNUM
  is '发送次数';
comment on column BS_T_SM_SMSMONITOR.FIRSTSENDTIME
  is '第一次发送时间';
comment on column BS_T_SM_SMSMONITOR.SENDTIME
  is '发送时间';
comment on column BS_T_SM_SMSMONITOR.PRI
  is '发送优先级';
comment on column BS_T_SM_SMSMONITOR.INFOINPUTTIME
  is '信息入库时间';
alter table BS_T_SM_SMSMONITOR
  add constraint PK_BS_T_SM_SMSMONITOR primary key (PID);

prompt Creating BS_T_SM_SMSMONITORLOG...
create table BS_T_SM_SMSMONITORLOG
(
  SMSMONITORPID VARCHAR2(50) not null,
  CONTENT       VARCHAR2(500),
  SYSTEMTYPE    VARCHAR2(50),
  GOAL          VARCHAR2(20),
  SENDFLAG      NUMBER(2),
  PRESENDTIME   NUMBER(15) default 0,
  RELATEID      VARCHAR2(50),
  GATEWAYCODE   VARCHAR2(50),
  GATEWAYCRIBE  VARCHAR2(300),
  SENDNUM       NUMBER(4) default 0,
  FIRSTSENDTIME NUMBER(15),
  SENDTIME      NUMBER(15),
  PRI           NUMBER(5) default 0,
  INFOINPUTTIME NUMBER(15) default 0,
  REMATK        VARCHAR2(50),
  SEPARATETIME  NUMBER(15) default 0
)
;
comment on column BS_T_SM_SMSMONITORLOG.SMSMONITORPID
  is '主键';
comment on column BS_T_SM_SMSMONITORLOG.CONTENT
  is '发送到手机的内容';
comment on column BS_T_SM_SMSMONITORLOG.SYSTEMTYPE
  is '信息类型';
comment on column BS_T_SM_SMSMONITORLOG.GOAL
  is '接收信息的手机号码';
comment on column BS_T_SM_SMSMONITORLOG.SENDFLAG
  is '0未发送:1已发送;2发送失败';
comment on column BS_T_SM_SMSMONITORLOG.PRESENDTIME
  is '定时发送时间';
comment on column BS_T_SM_SMSMONITORLOG.RELATEID
  is '业务关联ID';
comment on column BS_T_SM_SMSMONITORLOG.GATEWAYCODE
  is '网关回复标识代码';
comment on column BS_T_SM_SMSMONITORLOG.GATEWAYCRIBE
  is '网关反馈描述';
comment on column BS_T_SM_SMSMONITORLOG.SENDNUM
  is '发送次数';
comment on column BS_T_SM_SMSMONITORLOG.FIRSTSENDTIME
  is '第一次发送时间';
comment on column BS_T_SM_SMSMONITORLOG.SENDTIME
  is '发送时间';
comment on column BS_T_SM_SMSMONITORLOG.PRI
  is '发送优先级';
comment on column BS_T_SM_SMSMONITORLOG.INFOINPUTTIME
  is '信息入库时间';
comment on column BS_T_SM_SMSMONITORLOG.SEPARATETIME
  is '数据分离时间';
alter table BS_T_SM_SMSMONITORLOG
  add constraint PK_BS_T_SM_SMSMONITORLOG primary key (SMSMONITORPID);

prompt Creating BS_T_SM_SMSORDERDUTY...
create table BS_T_SM_SMSORDERDUTY
(
  PID            VARCHAR2(50) not null,
  DUTYROOMID     VARCHAR2(50),
  LOGINNAME      VARCHAR2(60),
  USERMOBILE     VARCHAR2(20),
  AHEADDAYNUM    NUMBER(3),
  STARTTIME      VARCHAR2(10),
  ENDTIME        VARCHAR2(10),
  ISHOLIDAY      NUMBER(2),
  STATUS         NUMBER(2),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15),
  DUTYROOMNAME   VARCHAR2(100)
)
;
comment on table BS_T_SM_SMSORDERDUTY
  is '值班短信订阅表';
comment on column BS_T_SM_SMSORDERDUTY.PID
  is '主键';
comment on column BS_T_SM_SMSORDERDUTY.DUTYROOMID
  is '值班室ID';
comment on column BS_T_SM_SMSORDERDUTY.LOGINNAME
  is '用户登录名';
comment on column BS_T_SM_SMSORDERDUTY.USERMOBILE
  is '用户手机号码';
comment on column BS_T_SM_SMSORDERDUTY.AHEADDAYNUM
  is '提前天数';
comment on column BS_T_SM_SMSORDERDUTY.STARTTIME
  is '开始时间';
comment on column BS_T_SM_SMSORDERDUTY.ENDTIME
  is '结束时间';
comment on column BS_T_SM_SMSORDERDUTY.ISHOLIDAY
  is '1：是 2 ： 否';
comment on column BS_T_SM_SMSORDERDUTY.STATUS
  is '1.启用、0.停用';
comment on column BS_T_SM_SMSORDERDUTY.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_SMSORDERDUTY.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_SMSORDERDUTY.DUTYROOMNAME
  is '值班室名称';
alter table BS_T_SM_SMSORDERDUTY
  add constraint PK_BS_T_SM_SMSORDERDUTY primary key (PID);

prompt Creating BS_T_SM_SMSORDERFORM...
create table BS_T_SM_SMSORDERFORM
(
  PID            VARCHAR2(50) not null,
  FORMSCHEMA     VARCHAR2(80),
  FORMACTION     VARCHAR2(30),
  LOGINNAME      VARCHAR2(60),
  USERMOBILE     VARCHAR2(200),
  ISHOLIDAY      NUMBER(2),
  STARTTIME      VARCHAR2(10),
  ENDTIME        VARCHAR2(10),
  STATUS         NUMBER(2),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15)
)
;
comment on table BS_T_SM_SMSORDERFORM
  is '工单短信订阅';
comment on column BS_T_SM_SMSORDERFORM.PID
  is '主键';
comment on column BS_T_SM_SMSORDERFORM.FORMSCHEMA
  is '工单类别(工单FORM英文名)';
comment on column BS_T_SM_SMSORDERFORM.FORMACTION
  is '工单动作(工单派发动作中文名)';
comment on column BS_T_SM_SMSORDERFORM.LOGINNAME
  is '用户登录名';
comment on column BS_T_SM_SMSORDERFORM.USERMOBILE
  is '用户手机号码';
comment on column BS_T_SM_SMSORDERFORM.ISHOLIDAY
  is '1：是 2 ： 否';
comment on column BS_T_SM_SMSORDERFORM.STARTTIME
  is '开始时间';
comment on column BS_T_SM_SMSORDERFORM.ENDTIME
  is '结束时间';
comment on column BS_T_SM_SMSORDERFORM.STATUS
  is '1.启用、0.停用';
comment on column BS_T_SM_SMSORDERFORM.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_SMSORDERFORM.LASTMODIFYTIME
  is '最后修改时间';
alter table BS_T_SM_SMSORDERFORM
  add constraint PK_BS_T_SM_SMSORDERFORM primary key (PID);

prompt Creating BS_T_SM_SMSORDERPLAN...
create table BS_T_SM_SMSORDERPLAN
(
  PID            VARCHAR2(50) not null,
  LOGINNAME      VARCHAR2(60),
  USERMOBILE     VARCHAR2(20),
  ISHOLIDAY      NUMBER(2),
  STARTTIME      VARCHAR2(10),
  ENDTIME        VARCHAR2(10),
  STATUS         NUMBER(2),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15)
)
;
comment on table BS_T_SM_SMSORDERPLAN
  is '作业计划短信订阅表';
comment on column BS_T_SM_SMSORDERPLAN.PID
  is '主键';
comment on column BS_T_SM_SMSORDERPLAN.LOGINNAME
  is '用户登录名';
comment on column BS_T_SM_SMSORDERPLAN.USERMOBILE
  is '用户手机号码';
comment on column BS_T_SM_SMSORDERPLAN.ISHOLIDAY
  is '1：是 2 ： 否';
comment on column BS_T_SM_SMSORDERPLAN.STARTTIME
  is '开始时间';
comment on column BS_T_SM_SMSORDERPLAN.ENDTIME
  is '结束时间';
comment on column BS_T_SM_SMSORDERPLAN.STATUS
  is '1.启用、0.停用';
comment on column BS_T_SM_SMSORDERPLAN.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_SMSORDERPLAN.LASTMODIFYTIME
  is '最后修改时间';
alter table BS_T_SM_SMSORDERPLAN
  add constraint PK_BS_T_SM_SMSORDERPLAN primary key (PID);

prompt Creating BS_T_SM_SMSRECEIVE...
create table BS_T_SM_SMSRECEIVE
(
  PID            VARCHAR2(50),
  EOMSID         VARCHAR2(50),
  SMSCONTEXT     VARCHAR2(1000),
  PHONE          VARCHAR2(20),
  OPSTATE        NUMBER(2),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15)
)
;
comment on table BS_T_SM_SMSRECEIVE
  is '短信接收回复表';
comment on column BS_T_SM_SMSRECEIVE.SMSCONTEXT
  is '短信内容';
comment on column BS_T_SM_SMSRECEIVE.PHONE
  is '手机号';
comment on column BS_T_SM_SMSRECEIVE.OPSTATE
  is '处理状态1:未处理  2:处理中 3:已处理但未连接上ar服务 4:已处理但处理失败(ar服务错误除外) 5 已完成';
comment on column BS_T_SM_SMSRECEIVE.CREATER
  is '创建人';
comment on column BS_T_SM_SMSRECEIVE.CREATETIME
  is '创建时间';
comment on column BS_T_SM_SMSRECEIVE.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_SMSRECEIVE.LASTMODIFYTIME
  is '最后修改时间';

prompt Creating BS_T_SM_SQLDATAPRIVILEGE...
create table BS_T_SM_SQLDATAPRIVILEGE
(
  PID            VARCHAR2(50) not null,
  RROID          VARCHAR2(50),
  RESID          VARCHAR2(50),
  OPID           VARCHAR2(50),
  PRIVILEGEDATA  VARCHAR2(500),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15)
)
;
comment on column BS_T_SM_SQLDATAPRIVILEGE.PID
  is '自动生成';
comment on column BS_T_SM_SQLDATAPRIVILEGE.RROID
  is '角色权限PID';
comment on column BS_T_SM_SQLDATAPRIVILEGE.RESID
  is '资源PID';
comment on column BS_T_SM_SQLDATAPRIVILEGE.OPID
  is '操作PID';
comment on column BS_T_SM_SQLDATAPRIVILEGE.PRIVILEGEDATA
  is '存放权限的SQL信息';
comment on column BS_T_SM_SQLDATAPRIVILEGE.CREATER
  is '创建人';
comment on column BS_T_SM_SQLDATAPRIVILEGE.CREATETIME
  is '创建时间';
comment on column BS_T_SM_SQLDATAPRIVILEGE.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_SQLDATAPRIVILEGE.LASTMODIFYTIME
  is '最后修改时间';
alter table BS_T_SM_SQLDATAPRIVILEGE
  add constraint PK_BS_T_SM_SQLDATAPRIVILEGE primary key (PID);

prompt Creating BS_T_SM_TABLE...
create table BS_T_SM_TABLE
(
  PID            VARCHAR2(50) not null,
  ENNAME         VARCHAR2(50),
  CNNAME         VARCHAR2(100),
  TABLETYPE      VARCHAR2(20),
  SEQNUM         NUMBER(5),
  ORDERNUM       NUMBER(5),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15),
  REMARK         VARCHAR2(500)
)
;
comment on column BS_T_SM_TABLE.PID
  is '自动生成';
comment on column BS_T_SM_TABLE.ENNAME
  is '表英文名';
comment on column BS_T_SM_TABLE.CNNAME
  is '表中文名';
comment on column BS_T_SM_TABLE.TABLETYPE
  is '表分类';
comment on column BS_T_SM_TABLE.ORDERNUM
  is '排序值';
comment on column BS_T_SM_TABLE.CREATER
  is '创建人';
comment on column BS_T_SM_TABLE.CREATETIME
  is '创建时间';
comment on column BS_T_SM_TABLE.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_TABLE.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_TABLE.REMARK
  is '备注';
alter table BS_T_SM_TABLE
  add constraint PK_BS_T_SM_TABLE primary key (PID);

prompt Creating BS_T_SM_USER...
create table BS_T_SM_USER
(
  PID            VARCHAR2(50) not null,
  JOBNUM         VARCHAR2(50),
  LOGINNAME      VARCHAR2(60),
  FULLNAME       VARCHAR2(100),
  PWD            VARCHAR2(120),
  SEX            VARCHAR2(20),
  POSITION       VARCHAR2(60),
  TYPE           VARCHAR2(50),
  MOBILE         VARCHAR2(200),
  PHONE          VARCHAR2(200),
  FAX            VARCHAR2(200),
  EMAIL          VARCHAR2(100),
  STATUS         NUMBER(2),
  ORDERNUM       NUMBER(5),
  IMAGE          VARCHAR2(50),
  LOCATIONZONE   VARCHAR2(50),
  DEPID          VARCHAR2(50),
  DEPNAME        VARCHAR2(100),
  GROUPID        VARCHAR2(1500),
  GROUPNAME      VARCHAR2(4000),
  PTDEPID        VARCHAR2(1500),
  PTDEPNAME      VARCHAR2(4000),
  PROFESSION     VARCHAR2(50),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15),
  LASTLOGINTIME  NUMBER(15),
  SYSTEMSKIN     VARCHAR2(100),
  IPADDRESS      VARCHAR2(50),
  MSN            VARCHAR2(200),
  QQ             VARCHAR2(100),
  REMARK         VARCHAR2(500),
  PYNAME         VARCHAR2(100)
)
;
comment on column BS_T_SM_USER.PID
  is '自动生成';
comment on column BS_T_SM_USER.JOBNUM
  is '用户工号';
comment on column BS_T_SM_USER.LOGINNAME
  is '登录名唯一';
comment on column BS_T_SM_USER.FULLNAME
  is '用户中文名';
comment on column BS_T_SM_USER.PWD
  is '明文密码';
comment on column BS_T_SM_USER.SEX
  is '选项通过配置';
comment on column BS_T_SM_USER.POSITION
  is '选项通过配置';
comment on column BS_T_SM_USER.TYPE
  is '选项通过配置';
comment on column BS_T_SM_USER.MOBILE
  is '用户手机';
comment on column BS_T_SM_USER.PHONE
  is '用户固话';
comment on column BS_T_SM_USER.FAX
  is '用户传真';
comment on column BS_T_SM_USER.EMAIL
  is '用户Email';
comment on column BS_T_SM_USER.STATUS
  is '1.启用、0.停用';
comment on column BS_T_SM_USER.ORDERNUM
  is '用于显示顺序';
comment on column BS_T_SM_USER.IMAGE
  is '图片存放路径';
comment on column BS_T_SM_USER.LOCATIONZONE
  is '片区';
comment on column BS_T_SM_USER.DEPID
  is '行政部门id';
comment on column BS_T_SM_USER.DEPNAME
  is '只可选一个';
comment on column BS_T_SM_USER.GROUPID
  is '所属组id';
comment on column BS_T_SM_USER.GROUPNAME
  is '多个组以逗号隔开';
comment on column BS_T_SM_USER.PTDEPID
  is '兼职部门id';
comment on column BS_T_SM_USER.PTDEPNAME
  is '多个兼职部门以逗号隔开';
comment on column BS_T_SM_USER.PROFESSION
  is '专业';
comment on column BS_T_SM_USER.CREATER
  is '创建人';
comment on column BS_T_SM_USER.CREATETIME
  is '创建时间';
comment on column BS_T_SM_USER.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_USER.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_USER.LASTLOGINTIME
  is '最后登陆时间';
comment on column BS_T_SM_USER.SYSTEMSKIN
  is '用户登陆系统的样式';
comment on column BS_T_SM_USER.IPADDRESS
  is 'IP地址';
comment on column BS_T_SM_USER.MSN
  is 'MSN';
comment on column BS_T_SM_USER.QQ
  is 'QQ号码';
comment on column BS_T_SM_USER.REMARK
  is '备注';
comment on column BS_T_SM_USER.PYNAME
  is '全名拼音';
alter table BS_T_SM_USER
  add constraint PK_BS_T_SM_USER primary key (PID);

prompt Creating BS_T_SM_USERDEP...
create table BS_T_SM_USERDEP
(
  PID            VARCHAR2(50) not null,
  USERID         VARCHAR2(50),
  DEPID          VARCHAR2(50),
  RELATETYPE     VARCHAR2(50),
  LOGINNAME      VARCHAR2(60),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFIER   VARCHAR2(50),
  LASTMODIFYTIME NUMBER(15)
)
;
comment on column BS_T_SM_USERDEP.PID
  is '自动生成';
comment on column BS_T_SM_USERDEP.USERID
  is '人员ID';
comment on column BS_T_SM_USERDEP.DEPID
  is '机构ID';
comment on column BS_T_SM_USERDEP.RELATETYPE
  is '1.所属组、2.兼职部门';
comment on column BS_T_SM_USERDEP.LOGINNAME
  is '用户登录名';
comment on column BS_T_SM_USERDEP.CREATER
  is '创建人';
comment on column BS_T_SM_USERDEP.CREATETIME
  is '创建时间';
comment on column BS_T_SM_USERDEP.LASTMODIFIER
  is '最后修改人';
comment on column BS_T_SM_USERDEP.LASTMODIFYTIME
  is '最后修改时间';
alter table BS_T_SM_USERDEP
  add constraint PK_BS_T_SM_USERDEP primary key (PID);

prompt Creating BS_T_SM_USERTEMPLATE...
create table BS_T_SM_USERTEMPLATE
(
  PID            VARCHAR2(50) not null,
  TEMPLATENAME   VARCHAR2(100),
  UTTYPE         VARCHAR2(20),
  ISSHARE        NUMBER(2),
  ORDERNUM       NUMBER(5),
  STATUS         NUMBER(2),
  CREATER        VARCHAR2(50),
  CREATETIME     NUMBER(15),
  LASTMODIFYTIME NUMBER(15),
  REMARK         VARCHAR2(500),
  USERDATA       VARCHAR2(2000),
  DEPDATA        VARCHAR2(2000),
  USERSHARE      VARCHAR2(2000),
  DEPSHARE       VARCHAR2(2000),
  TYPEDATA       VARCHAR2(1500)
)
;
comment on column BS_T_SM_USERTEMPLATE.PID
  is '自动生成';
comment on column BS_T_SM_USERTEMPLATE.TEMPLATENAME
  is '手动输入';
comment on column BS_T_SM_USERTEMPLATE.UTTYPE
  is '字典配置(如:通用、工单等)';
comment on column BS_T_SM_USERTEMPLATE.ISSHARE
  is '1.共享；0.非共享';
comment on column BS_T_SM_USERTEMPLATE.ORDERNUM
  is '排序值';
comment on column BS_T_SM_USERTEMPLATE.STATUS
  is '1:启用;0:停用';
comment on column BS_T_SM_USERTEMPLATE.CREATER
  is '创建人';
comment on column BS_T_SM_USERTEMPLATE.CREATETIME
  is '创建时间';
comment on column BS_T_SM_USERTEMPLATE.LASTMODIFYTIME
  is '最后修改时间';
comment on column BS_T_SM_USERTEMPLATE.REMARK
  is '模版描述';
comment on column BS_T_SM_USERTEMPLATE.USERDATA
  is '冗余字段';
comment on column BS_T_SM_USERTEMPLATE.DEPDATA
  is '冗余字段';
comment on column BS_T_SM_USERTEMPLATE.USERSHARE
  is '冗余字段';
comment on column BS_T_SM_USERTEMPLATE.DEPSHARE
  is '冗余字段';
comment on column BS_T_SM_USERTEMPLATE.TYPEDATA
  is '冗余字段';
alter table BS_T_SM_USERTEMPLATE
  add constraint PK_BS_T_SM_USERTEMPLATE primary key (PID);

prompt Creating BS_T_SM_USERTEMPLATEDATA...
create table BS_T_SM_USERTEMPLATEDATA
(
  PID     VARCHAR2(50) not null,
  UTID    VARCHAR2(50),
  ORGID   VARCHAR2(50),
  ORGTYPE NUMBER(2)
)
;
comment on column BS_T_SM_USERTEMPLATEDATA.PID
  is '自动生成';
comment on column BS_T_SM_USERTEMPLATEDATA.UTID
  is '人员模版ID';
comment on column BS_T_SM_USERTEMPLATEDATA.ORGID
  is '人和组的ID';
comment on column BS_T_SM_USERTEMPLATEDATA.ORGTYPE
  is '1.人；2.组';
alter table BS_T_SM_USERTEMPLATEDATA
  add constraint PK_BS_T_SM_USERTEMPLATEDATA primary key (PID);

prompt Creating BS_T_SM_USERTEMPLATESHARE...
create table BS_T_SM_USERTEMPLATESHARE
(
  PID     VARCHAR2(50) not null,
  UTID    VARCHAR2(50),
  UTNAME  VARCHAR2(100),
  ORGID   VARCHAR2(50),
  ORGTYPE NUMBER(2),
  DEPDNS  VARCHAR2(100)
)
;
comment on column BS_T_SM_USERTEMPLATESHARE.PID
  is '自动生成';
comment on column BS_T_SM_USERTEMPLATESHARE.UTID
  is '人员模版ID';
comment on column BS_T_SM_USERTEMPLATESHARE.UTNAME
  is '人员模版名称';
comment on column BS_T_SM_USERTEMPLATESHARE.ORGID
  is '人和组的ID';
comment on column BS_T_SM_USERTEMPLATESHARE.ORGTYPE
  is '1.人；2.组';
comment on column BS_T_SM_USERTEMPLATESHARE.DEPDNS
  is '如果组织类型为组的话，存储组的DNS';
alter table BS_T_SM_USERTEMPLATESHARE
  add constraint PK_BS_T_SM_USERTEMPLATESHARE primary key (PID);

prompt Creating BS_T_SM_USERTEMPLATETYPE...
create table BS_T_SM_USERTEMPLATETYPE
(
  PID      VARCHAR2(50) not null,
  UTID     VARCHAR2(50),
  UTTYPE   VARCHAR2(20),
  TYPEMARK VARCHAR2(50),
  TYPENAME VARCHAR2(150)
)
;
comment on column BS_T_SM_USERTEMPLATETYPE.PID
  is '自动生成';
comment on column BS_T_SM_USERTEMPLATETYPE.UTID
  is '人员模版ID';
comment on column BS_T_SM_USERTEMPLATETYPE.UTTYPE
  is '字典配置(如:通用、工单等)';
comment on column BS_T_SM_USERTEMPLATETYPE.TYPEMARK
  is '类别标识';
comment on column BS_T_SM_USERTEMPLATETYPE.TYPENAME
  is '类别名称';
alter table BS_T_SM_USERTEMPLATETYPE
  add constraint PK_BS_T_SM_USERTEMPLATETYPE primary key (PID);

prompt Creating BS_T_USLA_DUETIMERULE...
create table BS_T_USLA_DUETIMERULE
(
  PID            VARCHAR2(15) not null,
  RULENAME       VARCHAR2(150),
  RULEEXPRESS    VARCHAR2(1000) not null,
  RULETYPE       VARCHAR2(100),
  ACCEPTTIME     NUMBER(15) default 0 not null,
  PROCESSTIME    NUMBER(15) default 0 not null,
  PRIORITY       NUMBER(2) default 2,
  VALIDSTARTTIME NUMBER(15) default 0,
  VALIDENDTIME   NUMBER(15) default 4102415998,
  STATUS         NUMBER(2) default 1 not null,
  CREATETIME     NUMBER(15),
  UPDATETIME     NUMBER(15)
)
;
comment on column BS_T_USLA_DUETIMERULE.RULENAME
  is '规则名称';
comment on column BS_T_USLA_DUETIMERULE.RULEEXPRESS
  is '规则表达式';
comment on column BS_T_USLA_DUETIMERULE.RULETYPE
  is '规则所属的业务类别（业务类别的粒度越小，匹配越快）';
comment on column BS_T_USLA_DUETIMERULE.ACCEPTTIME
  is '受理实现（单位为秒）';
comment on column BS_T_USLA_DUETIMERULE.PROCESSTIME
  is '处理实现（单位为秒）';
comment on column BS_T_USLA_DUETIMERULE.PRIORITY
  is '优先级（1：低；2：一般；3：中；4：高；5：紧急）';
comment on column BS_T_USLA_DUETIMERULE.VALIDSTARTTIME
  is '该规则的有效开始时间，单位为秒，默认为1970-01-01 08:00:00';
comment on column BS_T_USLA_DUETIMERULE.VALIDENDTIME
  is '该规则的有效结束时间，单位为秒，默认为2099-12-31 23:59:59';
comment on column BS_T_USLA_DUETIMERULE.STATUS
  is '该规则是否启用（1：启用；0：停用）';
comment on column BS_T_USLA_DUETIMERULE.CREATETIME
  is '该规则的创建时间';
comment on column BS_T_USLA_DUETIMERULE.UPDATETIME
  is '该规则的修改时间';
alter table BS_T_USLA_DUETIMERULE
  add constraint PK_BS_T_USLA_DUETIMERULE primary key (PID);

prompt Creating BS_T_USLA_EVENTACTION...
create table BS_T_USLA_EVENTACTION
(
  PID              VARCHAR2(50) not null,
  EVENTID          VARCHAR2(300) not null,
  RULEID           VARCHAR2(50) not null,
  DUETIME          NUMBER(15) not null,
  NOTICETIME       NUMBER(15) not null,
  NOTICEDTIMES     NUMBER(2) default 0,
  NEXTNOTICETIME   NUMBER(15),
  STATUS           NUMBER(2),
  ACTIONSTATUS     NUMBER(2),
  DEFAULTHANLDERID VARCHAR2(700),
  CREATETIME       NUMBER(15)
)
;
comment on column BS_T_USLA_EVENTACTION.EVENTID
  is '动作所属事件ID';
comment on column BS_T_USLA_EVENTACTION.RULEID
  is '表示由哪一条规则所生成的该条记录';
comment on column BS_T_USLA_EVENTACTION.DUETIME
  is '业务处理时限，单位为秒';
comment on column BS_T_USLA_EVENTACTION.NOTICETIME
  is '根据业务处理时限和规则产生的消息发送时间，考虑到延时，实际通知时间应该稍大于该字段值';
comment on column BS_T_USLA_EVENTACTION.NOTICEDTIMES
  is '如果该消息需要发送多次，则该字段记录已经发送了多少次';
comment on column BS_T_USLA_EVENTACTION.NEXTNOTICETIME
  is '如果该消息需要发送多次，则该字段记录了下一次的发送时间';
comment on column BS_T_USLA_EVENTACTION.STATUS
  is '表示该动作是产生还是销毁（0：销毁；1：产生）';
comment on column BS_T_USLA_EVENTACTION.ACTIONSTATUS
  is '表示该动作的通知状态（1：活动；2：已发送；3：补发活动；4：补发销毁）';
comment on column BS_T_USLA_EVENTACTION.DEFAULTHANLDERID
  is '默认通知对象的ID，格式为：U:id1,id2;D:id1,id2;R:id1,id2';
comment on column BS_T_USLA_EVENTACTION.CREATETIME
  is '该策略的创建时间';
alter table BS_T_USLA_EVENTACTION
  add constraint PK_BS_T_USLA_EVENTACTION primary key (PID);

prompt Creating BS_T_USLA_EVENTPARAM...
create table BS_T_USLA_EVENTPARAM
(
  PID            VARCHAR2(50) not null,
  EVENTID        VARCHAR2(300),
  ACTIONID       VARCHAR2(50),
  ACTIONPARAMXML VARCHAR2(3000),
  CREATETIME     NUMBER(15)
)
;
comment on column BS_T_USLA_EVENTPARAM.EVENTID
  is '该参数所属事件的ID';
comment on column BS_T_USLA_EVENTPARAM.ACTIONID
  is '该参数所属动作的ID';
comment on column BS_T_USLA_EVENTPARAM.ACTIONPARAMXML
  is '事件参数的XML文件';
comment on column BS_T_USLA_EVENTPARAM.CREATETIME
  is '创建时间';
alter table BS_T_USLA_EVENTPARAM
  add constraint PK_BS_T_USLA_EVENTPARAM primary key (PID);

prompt Creating BS_T_USLA_EVENTRULE...
create table BS_T_USLA_EVENTRULE
(
  PID                  VARCHAR2(15) not null,
  RULENAME             VARCHAR2(150) not null,
  RULETYPE             VARCHAR2(100),
  NOTICETYPE           NUMBER(2) not null,
  RULEEXPRESS          VARCHAR2(1000),
  TIMESPAN             NUMBER(15) not null,
  PRIORITY             NUMBER(2) default 2,
  ISDEFAULTNOTICE      NUMBER(2) default 1,
  DEFAULTCHECKTYPE     NUMBER(2) default 0,
  DEFAULTCHECKPARAM    VARCHAR2(300),
  NOTICEHANDLERID      VARCHAR2(700),
  NOTICEHANDLER        VARCHAR2(700),
  DUPLICATEHANDLERID   VARCHAR2(700),
  DUPLICATEHANDLER     VARCHAR2(700),
  UPGRADEHANDLERID     VARCHAR2(700),
  UPGRADEHANDLER       VARCHAR2(700),
  CUSTNOTICECOMPID     VARCHAR2(1500),
  NOTICETIMES          NUMBER(2) default 1,
  NOTICEINTERVAL       NUMBER(15) default 0,
  NOTICESTARTTIME      VARCHAR2(5) default '00:00',
  NOTICEENDTIME        VARCHAR2(5) default '23:59',
  ISNOTICEAFTERWARDS   NUMBER(2) default 0,
  ACTIONTYPE           NUMBER(2) not null,
  ACTIONPARAMETER      VARCHAR2(200),
  NOTICETOPIC          VARCHAR2(600),
  NOTICECONTENT        VARCHAR2(1500),
  DYNAMICCONCHECKTYPE  NUMBER(2),
  DYNAMICCONCHECKPARAM VARCHAR2(300),
  VALIDSTARTTIME       NUMBER(15) default 0,
  VALIDENDTIME         NUMBER(15) default 4102415998,
  STATUS               NUMBER(2) default 1 not null,
  NOTICEALIAS          VARCHAR2(150),
  CREATETIME           NUMBER(15),
  UPDATETIME           NUMBER(15),
  FIXEDHANDLER         VARCHAR2(1000),
  CALENDARTYPE         VARCHAR2(50)
)
;
comment on column BS_T_USLA_EVENTRULE.RULENAME
  is '规则名称';
comment on column BS_T_USLA_EVENTRULE.RULETYPE
  is '规则所属的业务类别，如：工单，值班等';
comment on column BS_T_USLA_EVENTRULE.NOTICETYPE
  is '该规则的通知类型（1：提前提醒；2：超时催办）';
comment on column BS_T_USLA_EVENTRULE.RULEEXPRESS
  is '规则表达式';
comment on column BS_T_USLA_EVENTRULE.TIMESPAN
  is '提前或者超期多少时间，单位为秒';
comment on column BS_T_USLA_EVENTRULE.PRIORITY
  is '优先级（1：低；2：一般；3：中；4：高；5：紧急）';
comment on column BS_T_USLA_EVENTRULE.ISDEFAULTNOTICE
  is '是否通知当前处理人或者组';
comment on column BS_T_USLA_EVENTRULE.DEFAULTCHECKTYPE
  is '当前处理人查询方式：0：取传递的默认处理者；1：SQL；2：实现类';
comment on column BS_T_USLA_EVENTRULE.DEFAULTCHECKPARAM
  is '当前处理人查询方式为1时，该字段值为sql名称，为2时，该字段值为实现类名';
comment on column BS_T_USLA_EVENTRULE.NOTICEHANDLERID
  is '通知对象ID，格式为：U:id1,id2;D:id1,id2;R:id1,id2';
comment on column BS_T_USLA_EVENTRULE.NOTICEHANDLER
  is '通知对象中文名，格式为：人员:人员1,人员;部门:部门1,部门2;角色:角色,角色2';
comment on column BS_T_USLA_EVENTRULE.DUPLICATEHANDLERID
  is '抄送对象ID，格式为：U:id1,id2;D:id1,id2;R:id1,id2';
comment on column BS_T_USLA_EVENTRULE.DUPLICATEHANDLER
  is '抄送对象中文名，格式为：人员:人员1,人员;部门:部门1,部门2;角色:角色,角色2';
comment on column BS_T_USLA_EVENTRULE.UPGRADEHANDLERID
  is '升级对象ID，格式为：U:id1,id2;D:id1,id2;R:id1,id2';
comment on column BS_T_USLA_EVENTRULE.UPGRADEHANDLER
  is '升级对象中文名，格式为：人员:人员1,人员;部门:部门1,部门2;角色:角色,角色2';
comment on column BS_T_USLA_EVENTRULE.CUSTNOTICECOMPID
  is '自定义通知组件ID，多个组件ID用逗号分隔';
comment on column BS_T_USLA_EVENTRULE.NOTICETIMES
  is '消息提醒次数，默认为1次';
comment on column BS_T_USLA_EVENTRULE.NOTICEINTERVAL
  is '当通知次数大于1时，设置发送消息的间隔时间，必须大于0，单位为秒';
comment on column BS_T_USLA_EVENTRULE.NOTICESTARTTIME
  is '消息发送有效开始时间，默认是0点0分';
comment on column BS_T_USLA_EVENTRULE.NOTICEENDTIME
  is '消息发送有效结束时间，默认是23点59分';
comment on column BS_T_USLA_EVENTRULE.ISNOTICEAFTERWARDS
  is '如果消息产生不在有效发送时间内，事后是否补发消息（1：是；0：否）';
comment on column BS_T_USLA_EVENTRULE.ACTIONTYPE
  is '如何发送消息（1：短信；2：邮件；3：短信+邮件；9：自定义动作）';
comment on column BS_T_USLA_EVENTRULE.ACTIONPARAMETER
  is '如动作类型字段值为9，则该字段值为自定义动作实现类全名';
comment on column BS_T_USLA_EVENTRULE.NOTICETOPIC
  is '通知主题，短信可以没有主题';
comment on column BS_T_USLA_EVENTRULE.NOTICECONTENT
  is '通知内容';
comment on column BS_T_USLA_EVENTRULE.DYNAMICCONCHECKTYPE
  is '通知内容中动态参数的获得方式（1：SQL；2：实现类）';
comment on column BS_T_USLA_EVENTRULE.DYNAMICCONCHECKPARAM
  is '动态内容查询方式，如果查询方式为SQL，则这里为SLQ名称，如果查询方式为实现类，则这里为实现类全名';
comment on column BS_T_USLA_EVENTRULE.VALIDSTARTTIME
  is '该规则的有效开始时间，单位为秒，默认为1970-01-01 08:00:00';
comment on column BS_T_USLA_EVENTRULE.VALIDENDTIME
  is '该规则的有效结束时间，单位为秒，默认为2099-12-31 23:59:59';
comment on column BS_T_USLA_EVENTRULE.STATUS
  is '该规则是否启用（1：启用；0：停用）';
comment on column BS_T_USLA_EVENTRULE.NOTICEALIAS
  is '通知类型别名，如：未受理未超时、未受理已超时、未处理未超时、未处理已超时等';
comment on column BS_T_USLA_EVENTRULE.CREATETIME
  is '该规则的创建时间，单位为秒';
comment on column BS_T_USLA_EVENTRULE.UPDATETIME
  is '该规则的最后修改时间，单位为秒';
comment on column BS_T_USLA_EVENTRULE.FIXEDHANDLER
  is '固定通知的短信及邮件，格式为mobile:号码1,号码2,...;mail:邮箱1,邮箱2,...
';
comment on column BS_T_USLA_EVENTRULE.CALENDARTYPE
  is '工作日历业务类别';
alter table BS_T_USLA_EVENTRULE
  add constraint PK_BS_T_USLA_EVENTRULE primary key (PID);

prompt Creating BS_T_USLA_EVENTTEMP...
create table BS_T_USLA_EVENTTEMP
(
  PID           VARCHAR2(15) not null,
  EVENTID       VARCHAR2(300) not null,
  RULEIDS       VARCHAR2(300) not null,
  DUETIME       NUMBER(15) not null,
  DEFAULTUSER   VARCHAR2(1000),
  DEFAULTGROUP  VARCHAR2(1000),
  DEFAULTROLE   VARCHAR2(1000),
  EVENTPARAMXML VARCHAR2(3000),
  STATUS        NUMBER(2),
  CREATETIME    NUMBER(15),
  UPDATETIME    NUMBER(15)
)
;
comment on column BS_T_USLA_EVENTTEMP.EVENTID
  is '该事件的唯一标识符号';
comment on column BS_T_USLA_EVENTTEMP.RULEIDS
  is '事件触发的规则ID，可以为多个，用逗号分隔';
comment on column BS_T_USLA_EVENTTEMP.DUETIME
  is '逻辑处理时限';
comment on column BS_T_USLA_EVENTTEMP.DEFAULTUSER
  is '默认的处理人ID，多个用逗号分隔';
comment on column BS_T_USLA_EVENTTEMP.DEFAULTGROUP
  is '默认的处理组ID，多个用逗号分隔';
comment on column BS_T_USLA_EVENTTEMP.DEFAULTROLE
  is '默认的处理角色ID，多个用逗号分隔';
comment on column BS_T_USLA_EVENTTEMP.EVENTPARAMXML
  is '事件参数的XML文件';
comment on column BS_T_USLA_EVENTTEMP.STATUS
  is '时间状态 1：未处理；0：已处理';
alter table BS_T_USLA_EVENTTEMP
  add constraint PK_BS_T_USLA_EVENTTEMP primary key (PID);

prompt Creating BS_T_USLA_EVTHDERCOMP...
create table BS_T_USLA_EVTHDERCOMP
(
  PID           VARCHAR2(50) not null,
  COMPONENTNAME VARCHAR2(100) not null,
  COMPONENTTYPE VARCHAR2(100),
  COMPEXETYPE   NUMBER(2) not null,
  COMPEXEPARAM  VARCHAR2(300) not null,
  STATUS        NUMBER(2) default 1 not null,
  CREATETIME    NUMBER(15),
  UPDATETIME    NUMBER(15)
)
;
comment on column BS_T_USLA_EVTHDERCOMP.COMPONENTNAME
  is '组件的名称';
comment on column BS_T_USLA_EVTHDERCOMP.COMPONENTTYPE
  is '组件的类型';
comment on column BS_T_USLA_EVTHDERCOMP.COMPEXETYPE
  is '组件的执行类型：1:SQL执行2:实现类';
comment on column BS_T_USLA_EVTHDERCOMP.COMPEXEPARAM
  is '根据执行类型分别为：SQL名称或者实现类全名';
comment on column BS_T_USLA_EVTHDERCOMP.STATUS
  is '组件当前的状态：1：启用；2：停用';
comment on column BS_T_USLA_EVTHDERCOMP.CREATETIME
  is '组件的注册时间';
comment on column BS_T_USLA_EVTHDERCOMP.UPDATETIME
  is '组件的修改时间';
alter table BS_T_USLA_EVTHDERCOMP
  add constraint PK_BS_T_USLA_EVTHDERCOMP primary key (PID);

prompt Creating BS_T_USLA_SLADEFINE...
create table BS_T_USLA_SLADEFINE
(
  PID             VARCHAR2(50) not null,
  BASESCHEMA      VARCHAR2(100) not null,
  SLANAME         VARCHAR2(100) not null,
  VALIDSTARTTIME  NUMBER(15) default 0,
  VALIDENDTIME    NUMBER(15) default 4102415998,
  STATUS          NUMBER(2) default 1,
  REMARK          VARCHAR2(300),
  FORMTIMERULEID  VARCHAR2(600),
  STEPTIMERULEID  VARCHAR2(600),
  FORMEVENTRULEID VARCHAR2(600),
  STEPEVENTRULEID VARCHAR2(600),
  CREATETIME      NUMBER(15),
  UPDATETIME      NUMBER(15)
)
;
comment on column BS_T_USLA_SLADEFINE.BASESCHEMA
  is '工单类别';
comment on column BS_T_USLA_SLADEFINE.SLANAME
  is '该服务等级协议的名称';
comment on column BS_T_USLA_SLADEFINE.VALIDSTARTTIME
  is '该协议的有效开始时间，默认为1970-01-01 08:00:00';
comment on column BS_T_USLA_SLADEFINE.VALIDENDTIME
  is '该协议的有效结束时间，默认为2099-12-31 23:59:59';
comment on column BS_T_USLA_SLADEFINE.STATUS
  is '该协议的启用状态，1：启用；0：停用；默认为1';
comment on column BS_T_USLA_SLADEFINE.REMARK
  is '该协议的描述信息';
comment on column BS_T_USLA_SLADEFINE.FORMTIMERULEID
  is '工单时限规则ID，多个用逗号分隔，最多为20个';
comment on column BS_T_USLA_SLADEFINE.STEPTIMERULEID
  is '环节时限规则ID，多个用逗号分隔，最多为20个';
comment on column BS_T_USLA_SLADEFINE.FORMEVENTRULEID
  is '工单事件规则ID，多个用逗号分隔，最多为20个';
comment on column BS_T_USLA_SLADEFINE.STEPEVENTRULEID
  is '环节事件规则ID，多个用逗号分隔，最多为20个';
comment on column BS_T_USLA_SLADEFINE.CREATETIME
  is '该协议的创建时间';
comment on column BS_T_USLA_SLADEFINE.UPDATETIME
  is '该协议的最后修改时间';
alter table BS_T_USLA_SLADEFINE
  add constraint PK_BS_T_USLA_SLADEFINE primary key (PID);

prompt Creating BS_T_WF_AGENT...
create table BS_T_WF_AGENT
(
  ID         VARCHAR2(50) not null,
  BASESCHEMA VARCHAR2(255),
  BASENAME   VARCHAR2(255),
  DEALERID   VARCHAR2(255),
  DEALER     VARCHAR2(255),
  AGENTID    VARCHAR2(255),
  AGENT      VARCHAR2(255),
  BGDATE     NUMBER(19),
  EDDATE     NUMBER(19),
  CREATETIME NUMBER(19)
)
;
comment on column BS_T_WF_AGENT.ID
  is '主键';
comment on column BS_T_WF_AGENT.BASESCHEMA
  is '工单标识';
comment on column BS_T_WF_AGENT.BASENAME
  is '工单名称';
comment on column BS_T_WF_AGENT.DEALERID
  is '处理人登录名';
comment on column BS_T_WF_AGENT.DEALER
  is '处理人名称';
comment on column BS_T_WF_AGENT.AGENTID
  is '代理人登录名';
comment on column BS_T_WF_AGENT.AGENT
  is '代理人名称';
comment on column BS_T_WF_AGENT.BGDATE
  is '开始日期';
comment on column BS_T_WF_AGENT.EDDATE
  is '结束日期';
comment on column BS_T_WF_AGENT.CREATETIME
  is '创建日期';
alter table BS_T_WF_AGENT
  add primary key (ID);

prompt Creating BS_T_WF_ATTACHMENT...
create table BS_T_WF_ATTACHMENT
(
  ID        VARCHAR2(50) not null,
  ATTACHID  VARCHAR2(50),
  SHEETID   VARCHAR2(50),
  PROCESSID VARCHAR2(50),
  FIELDID   VARCHAR2(50)
)
;
comment on column BS_T_WF_ATTACHMENT.ID
  is '自动生成';
comment on column BS_T_WF_ATTACHMENT.ATTACHID
  is '附件表主键';
comment on column BS_T_WF_ATTACHMENT.SHEETID
  is '工单附件关联id';
comment on column BS_T_WF_ATTACHMENT.PROCESSID
  is '工单环节附件关联id';
alter table BS_T_WF_ATTACHMENT
  add primary key (ID);

prompt Creating BS_T_WF_BASEOWNFIELDS...
create table BS_T_WF_BASEOWNFIELDS
(
  ID             VARCHAR2(50) not null,
  BASESCHEMA     VARCHAR2(255),
  BASENAME       VARCHAR2(255),
  FIELDID        VARCHAR2(255),
  FIELDCODE      VARCHAR2(255),
  FIELDTYPE      VARCHAR2(255),
  FIELDNAME      VARCHAR2(255),
  FIELDTYPEVALUE VARCHAR2(255),
  FREEEDITSTEP   VARCHAR2(255),
  DEFEDITSTEP    VARCHAR2(255),
  PRINTONELINE   NUMBER,
  PRINTORDER     NUMBER,
  ISPRINT        NUMBER,
  FIELDISTOLONG  NUMBER,
  CREATETIME     NUMBER(15),
  LOGPOSITION    NUMBER
)
;
alter table BS_T_WF_BASEOWNFIELDS
  add constraint PK_OWNFIELDS primary key (ID);

prompt Creating BS_T_WF_CHILDROLE...
create table BS_T_WF_CHILDROLE
(
  CHILDROLEID    VARCHAR2(15) not null,
  CHILDROLENAME  VARCHAR2(200),
  ROLECODE       VARCHAR2(50),
  MATCHCOUNT     NUMBER,
  CHILDROLEDESC  VARCHAR2(400),
  DIMENSIONVALUE VARCHAR2(4000),
  CHARGE         VARCHAR2(50),
  BASEVERSION    VARCHAR2(255)
)
;
comment on column BS_T_WF_CHILDROLE.CHILDROLENAME
  is '角色细分名称';
comment on column BS_T_WF_CHILDROLE.ROLECODE
  is '角色标识';
comment on column BS_T_WF_CHILDROLE.MATCHCOUNT
  is '维度数量';
comment on column BS_T_WF_CHILDROLE.CHILDROLEDESC
  is '角色细分描述';
comment on column BS_T_WF_CHILDROLE.DIMENSIONVALUE
  is '维度值';
comment on column BS_T_WF_CHILDROLE.CHARGE
  is '负责人';
alter table BS_T_WF_CHILDROLE
  add primary key (CHILDROLEID);

prompt Creating BS_T_WF_CURRENTSTEPS...
create table BS_T_WF_CURRENTSTEPS
(
  ID           VARCHAR2(15) not null,
  ENTRYID      VARCHAR2(15),
  STEPCODE     VARCHAR2(15),
  FORWARDID    VARCHAR2(500),
  FORWARDCODE  VARCHAR2(500),
  BACKWARDID   VARCHAR2(500),
  BACKWARDCODE VARCHAR2(500),
  FLOWID       VARCHAR2(15),
  ORIGID       VARCHAR2(15),
  ORIGCODE     VARCHAR2(15),
  STATE        VARCHAR2(255),
  TRACK        VARCHAR2(255),
  CREATETIME   NUMBER(19),
  SUBCOUNT     NUMBER(10),
  FINISHCOUNT  NUMBER(10),
  TYPE         VARCHAR2(255),
  TRANSTYPE    VARCHAR2(255),
  NAME         VARCHAR2(255),
  STEPNO       VARCHAR2(50),
  STEPGROUP    VARCHAR2(255)
)
;
comment on column BS_T_WF_CURRENTSTEPS.ID
  is '主键';
comment on column BS_T_WF_CURRENTSTEPS.ENTRYID
  is '流程实体id';
comment on column BS_T_WF_CURRENTSTEPS.STEPCODE
  is '流程定义环节号';
comment on column BS_T_WF_CURRENTSTEPS.FORWARDID
  is '正向前一环节stepId';
comment on column BS_T_WF_CURRENTSTEPS.FORWARDCODE
  is '正向前一环节号';
comment on column BS_T_WF_CURRENTSTEPS.BACKWARDID
  is '反向前一环节stepId';
comment on column BS_T_WF_CURRENTSTEPS.BACKWARDCODE
  is '反向前一环节号';
comment on column BS_T_WF_CURRENTSTEPS.FLOWID
  is '与子流程关联的标识';
comment on column BS_T_WF_CURRENTSTEPS.ORIGID
  is '转派回复的原始stepId';
comment on column BS_T_WF_CURRENTSTEPS.STATE
  is '环节状态';
comment on column BS_T_WF_CURRENTSTEPS.TRACK
  is '环节轨迹';
comment on column BS_T_WF_CURRENTSTEPS.CREATETIME
  is '创建时间';
comment on column BS_T_WF_CURRENTSTEPS.SUBCOUNT
  is '子流程个数';
comment on column BS_T_WF_CURRENTSTEPS.FINISHCOUNT
  is '子流程完成个数';
comment on column BS_T_WF_CURRENTSTEPS.TYPE
  is '环节类型';
comment on column BS_T_WF_CURRENTSTEPS.TRANSTYPE
  is '是否影响流转';
comment on column BS_T_WF_CURRENTSTEPS.NAME
  is '固定流程流程定义环节名称';
alter table BS_T_WF_CURRENTSTEPS
  add primary key (ID);
create index CUR_STEP_IDX on BS_T_WF_CURRENTSTEPS (ENTRYID);

prompt Creating BS_T_WF_CURRENTTASK...
create table BS_T_WF_CURRENTTASK
(
  ID                     VARCHAR2(15) not null,
  ENTRYID                VARCHAR2(15),
  PARENTENTRYID          VARCHAR2(15),
  TOPENTRYID             VARCHAR2(15),
  PREVTASKID             VARCHAR2(15),
  PARENTTASKID           VARCHAR2(15),
  TOPTASKID              VARCHAR2(15),
  ACTORTYPE              VARCHAR2(255),
  CREATOR                VARCHAR2(255),
  CREATORNAME            VARCHAR2(255),
  CREATORTYPE            VARCHAR2(255),
  STEPID                 VARCHAR2(15),
  STEPCODE               VARCHAR2(15),
  PROCESSSTATE           VARCHAR2(255),
  ENTRYSTATE             VARCHAR2(255),
  FLAGACTIVE             NUMBER(19),
  FLAGASSIGNTYPE         VARCHAR2(255),
  PROCESSTYPE            VARCHAR2(255),
  ASSIGNEEID             VARCHAR2(255),
  ASSIGNEE               VARCHAR2(255),
  ASSIGNEEDEPID          VARCHAR2(255),
  ASSIGNEEDEP            VARCHAR2(255),
  ASSIGNEECORPID         VARCHAR2(255),
  ASSIGNEECORP           VARCHAR2(255),
  ASSIGNEEDNID           VARCHAR2(255),
  ASSIGNEEDN             VARCHAR2(255),
  ASSIGNGROUPID          VARCHAR2(255),
  ASSIGNGROUP            VARCHAR2(255),
  DEALERID               VARCHAR2(255),
  DEALER                 VARCHAR2(255),
  DEALERDEPID            VARCHAR2(255),
  DEALERDEP              VARCHAR2(255),
  DEALERCORPID           VARCHAR2(255),
  DEALERCORP             VARCHAR2(255),
  DEALERDNID             VARCHAR2(255),
  DEALERDN               VARCHAR2(255),
  ASSIGNERID             VARCHAR2(100),
  ASSIGNER               VARCHAR2(100),
  ASSIGNERDEPID          VARCHAR2(100),
  ASSIGNERDEP            VARCHAR2(200),
  ASSIGNERCORPID         VARCHAR2(100),
  ASSIGNERCORP           VARCHAR2(200),
  ASSIGNERDNID           VARCHAR2(100),
  ASSIGNERDN             VARCHAR2(400),
  ROLEID                 VARCHAR2(255),
  ROLENAME               VARCHAR2(255),
  CREATETIME             NUMBER(19),
  ACCEPTTIME             NUMBER(19),
  FINISHTIME             NUMBER(19),
  ACCEPTDATE             NUMBER(19),
  SENDDATE               NUMBER(19),
  DUEDATE                NUMBER(19),
  BASECREATETIME         NUMBER(19),
  TASKNAME               VARCHAR2(255),
  DEFNAME                VARCHAR2(255),
  DEALTYPE               VARCHAR2(255),
  ACTIONNAME             VARCHAR2(255),
  ACTIONCODE             VARCHAR2(255),
  WORKSHEETTITLE         VARCHAR2(1000),
  WORKSHEETPROTYPE       VARCHAR2(255),
  WORKSHEETURGENTLEVEL   VARCHAR2(255),
  WORKSHEETDESC          VARCHAR2(255),
  BASESCHEMA             VARCHAR2(255),
  BASENAME               VARCHAR2(255),
  BASEID                 VARCHAR2(255),
  SERIALNUM              VARCHAR2(255),
  STEPNAME               VARCHAR2(255),
  PREPHASENO             VARCHAR2(100),
  FLAGDUPLICATED         NUMBER(19),
  FLAGENDDUPLICATED      NUMBER(19),
  FLAGENDPHASE           NUMBER(19),
  FLAGPREDEFINED         NUMBER(19),
  STPROCESSACTION        VARCHAR2(50),
  BASESTARTDATE          NUMBER(19),
  FLOWID                 VARCHAR2(100),
  PARENTFLOWID           VARCHAR2(100),
  BASEFLOWID             VARCHAR2(100),
  FLAGASSIGN             NUMBER(19),
  FLAGASSIST             NUMBER(19),
  FLAGCOPY               NUMBER(19),
  FLAGTRANSFER           NUMBER(19),
  FLAGTURNUP             NUMBER(19),
  FLAGRECALL             NUMBER(19),
  FLAGCANCEL             NUMBER(19),
  FLAGCLOSE              NUMBER(19),
  FLAGTURNDOWN           NUMBER(19),
  FLAGTOASSISTAUDITING   NUMBER(19),
  FLAGTOAUDITING         NUMBER(19),
  FLAGAUDITINGRESULT     NUMBER(19),
  INSIDEFLOWSCOUNT       NUMBER(19),
  FINISHINSIDEFLOWSCOUNT NUMBER(19),
  HASTENCOUNT            NUMBER(19),
  CUSTOMACTIONS          VARCHAR2(400),
  PREASSIGNSTRING        VARCHAR2(400),
  ORIGPHASENO            VARCHAR2(50),
  EDPROCESSACTION        VARCHAR2(50),
  STEPNO                 VARCHAR2(255),
  STEPGROUP              VARCHAR2(255),
  TRACK                  VARCHAR2(255)
)
;
create index CT_STEPID_IDX on BS_T_WF_CURRENTTASK (STEPID);
create index CT_TOPENTRYID_IDX on BS_T_WF_CURRENTTASK (TOPENTRYID);
create index IDS_CT_ASSIGNEEIDACTORTYPE on BS_T_WF_CURRENTTASK (ASSIGNEEID, ACTORTYPE);
create index IDS_CURRENTTASK_ASSIGNGROUPID on BS_T_WF_CURRENTTASK (ASSIGNGROUPID);
create index IDS_CURRENTTASK_BASEID on BS_T_WF_CURRENTTASK (BASEID);
create index IDS_CURRENTTASK_BASESCHEMA on BS_T_WF_CURRENTTASK (BASESCHEMA);
create index IDS_CURRENTTASK_BID_SCHEMA on BS_T_WF_CURRENTTASK (BASEID, BASESCHEMA);
create index IDS_CURRENTTASK_DEALERID on BS_T_WF_CURRENTTASK (DEALERID);

prompt Creating BS_T_WF_DEALGROUP...
create table BS_T_WF_DEALGROUP
(
  ID         VARCHAR2(50) not null,
  NAME       VARCHAR2(255),
  GROUPID    VARCHAR2(255),
  GROUPNAME  VARCHAR2(255),
  GROUPTYPE  VARCHAR2(50),
  ISENABLE   NUMBER,
  CREATETIME NUMBER(19),
  ENTRYSTATE VARCHAR2(2000)
)
;
alter table BS_T_WF_DEALGROUP
  add constraint PK_DEALGROUP primary key (ID);

prompt Creating BS_T_WF_DEALPROCESS...
create table BS_T_WF_DEALPROCESS
(
  PROCESSID              VARCHAR2(15) not null,
  PROCESSTYPE            VARCHAR2(15),
  TASKID                 VARCHAR2(100),
  PHASENO                VARCHAR2(100),
  STEPID                 VARCHAR2(100),
  FORWARDSTEPID          VARCHAR2(100),
  PREPHASENO             VARCHAR2(100),
  PROCESSSTATUS          VARCHAR2(50),
  FLAGACTIVE             NUMBER(19),
  FLAGDUPLICATED         NUMBER(19),
  FLAGENDDUPLICATED      NUMBER(19),
  FLAGENDPHASE           NUMBER(19),
  FLAGPREDEFINED         NUMBER(19),
  STPROCESSACTION        VARCHAR2(50),
  EDPROCESSACTION        VARCHAR2(50),
  SELFDEALLENGTH         NUMBER(19),
  BASESTARTDATE          NUMBER(19),
  BASEID                 VARCHAR2(254),
  BASESCHEMA             VARCHAR2(254),
  FLOWID                 VARCHAR2(100),
  PARENTFLOWID           VARCHAR2(100),
  BASEFLOWID             VARCHAR2(100),
  ENTRYID                VARCHAR2(50),
  PARENTENTRYID          VARCHAR2(50),
  TOPENTRYID             VARCHAR2(50),
  ACTORTYPE              VARCHAR2(50),
  DEALTYPE               VARCHAR2(50),
  ASSIGNEEID             VARCHAR2(100),
  ASSIGNEE               VARCHAR2(100),
  ASSIGNEEDEPID          VARCHAR2(100),
  ASSIGNEEDEP            VARCHAR2(200),
  ASSIGNEECORPID         VARCHAR2(100),
  ASSIGNEECORP           VARCHAR2(200),
  ASSIGNEEDNID           VARCHAR2(100),
  ASSIGNEEDN             VARCHAR2(400),
  ASSIGNGROUPID          VARCHAR2(100),
  ASSIGNGROUP            VARCHAR2(200),
  FLAGASSIGNTYPE         VARCHAR2(255),
  DEALERID               VARCHAR2(100),
  DEALER                 VARCHAR2(100),
  DEALERDEPID            VARCHAR2(100),
  DEALERDEP              VARCHAR2(200),
  DEALERCORPID           VARCHAR2(100),
  DEALERCORP             VARCHAR2(200),
  DEALERDNID             VARCHAR2(100),
  DEALERDN               VARCHAR2(400),
  ASSIGNERID             VARCHAR2(100),
  ASSIGNER               VARCHAR2(100),
  ASSIGNERDEPID          VARCHAR2(100),
  ASSIGNERDEP            VARCHAR2(200),
  ASSIGNERCORPID         VARCHAR2(100),
  ASSIGNERCORP           VARCHAR2(200),
  ASSIGNERDNID           VARCHAR2(100),
  ASSIGNERDN             VARCHAR2(400),
  STDATE                 NUMBER(19),
  BGDATE                 NUMBER(19),
  EDDATE                 NUMBER(19),
  ASSIGNOVERTIMEDATE     NUMBER(19),
  ACCEPTOVERTIMEDATE     NUMBER(19),
  DEALOVERTIMEDATE       NUMBER(19),
  FLAGASSIGN             NUMBER(19),
  FLAGASSIST             NUMBER(19),
  FLAGCOPY               NUMBER(19),
  FLAGTRANSFER           NUMBER(19),
  FLAGTURNUP             NUMBER(19),
  FLAGRECALL             NUMBER(19),
  FLAGCANCEL             NUMBER(19),
  FLAGCLOSE              NUMBER(19),
  FLAGTURNDOWN           NUMBER(19),
  FLAGTOASSISTAUDITING   NUMBER(19),
  FLAGTOAUDITING         NUMBER(19),
  FLAGSTARTINSIDEFLOW    NUMBER(19),
  FLAGAUDITINGRESULT     NUMBER(19),
  INSIDEFLOWSCOUNT       NUMBER(19),
  FINISHINSIDEFLOWSCOUNT NUMBER(19),
  HASTENCOUNT            NUMBER(19),
  BASECREATETIME         NUMBER(19),
  ROLEID                 VARCHAR2(255),
  ROLENAME               VARCHAR2(255),
  ACTIONNAME             VARCHAR2(255),
  CUSTOMACTIONS          VARCHAR2(400),
  DPDESC                 VARCHAR2(2000),
  PREASSIGNSTRING        VARCHAR2(400),
  ORIGPHASENO            VARCHAR2(255),
  STEPNO                 VARCHAR2(50),
  STEPGROUP              VARCHAR2(255),
  TRACK                  VARCHAR2(255)
)
;
comment on column BS_T_WF_DEALPROCESS.PROCESSID
  is '主键';
comment on column BS_T_WF_DEALPROCESS.PROCESSTYPE
  is 'DEAL、AUDITING';
comment on column BS_T_WF_DEALPROCESS.TASKID
  is '任务id';
comment on column BS_T_WF_DEALPROCESS.PHASENO
  is '环节号';
comment on column BS_T_WF_DEALPROCESS.STEPID
  is '对应stepId';
comment on column BS_T_WF_DEALPROCESS.FORWARDSTEPID
  is '前置环节主键';
comment on column BS_T_WF_DEALPROCESS.PREPHASENO
  is '前一环节号';
comment on column BS_T_WF_DEALPROCESS.PROCESSSTATUS
  is '环节状态';
comment on column BS_T_WF_DEALPROCESS.STPROCESSACTION
  is '开始动作名称';
comment on column BS_T_WF_DEALPROCESS.EDPROCESSACTION
  is '结束动作名称';
comment on column BS_T_WF_DEALPROCESS.SELFDEALLENGTH
  is '自处理时长';
comment on column BS_T_WF_DEALPROCESS.BASESTARTDATE
  is '第一次派发到本环节的时间';
comment on column BS_T_WF_DEALPROCESS.FLOWID
  is '父子流程对应标识';
comment on column BS_T_WF_DEALPROCESS.PARENTFLOWID
  is '父子流程对应标识';
comment on column BS_T_WF_DEALPROCESS.BASEFLOWID
  is '闲置';
comment on column BS_T_WF_DEALPROCESS.ENTRYID
  is '实体id';
comment on column BS_T_WF_DEALPROCESS.PARENTENTRYID
  is '父流程实体id';
comment on column BS_T_WF_DEALPROCESS.TOPENTRYID
  is '顶级父流程实体id';
comment on column BS_T_WF_DEALPROCESS.ACTORTYPE
  is '处理人类型';
comment on column BS_T_WF_DEALPROCESS.DEALTYPE
  is '处理类型：抢单、共享、管理员管理';
comment on column BS_T_WF_DEALPROCESS.ASSIGNEEID
  is '负责人登陆名';
comment on column BS_T_WF_DEALPROCESS.ASSIGNEE
  is '负责人名称';
comment on column BS_T_WF_DEALPROCESS.ASSIGNEEDEPID
  is '负责人部门id';
comment on column BS_T_WF_DEALPROCESS.ASSIGNEEDEP
  is '负责人部门名称';
comment on column BS_T_WF_DEALPROCESS.ASSIGNEECORPID
  is '负责人公司id';
comment on column BS_T_WF_DEALPROCESS.ASSIGNEECORP
  is '负责人公司名称';
comment on column BS_T_WF_DEALPROCESS.ASSIGNGROUPID
  is '负责组id';
comment on column BS_T_WF_DEALPROCESS.ASSIGNGROUP
  is '负责人名称';
comment on column BS_T_WF_DEALPROCESS.DEALERID
  is '处理人登陆名';
comment on column BS_T_WF_DEALPROCESS.DEALER
  is '处理人名称';
comment on column BS_T_WF_DEALPROCESS.DEALERDEPID
  is '处理人部门id';
comment on column BS_T_WF_DEALPROCESS.DEALERDEP
  is '处理人部门名称';
comment on column BS_T_WF_DEALPROCESS.DEALERCORPID
  is '处理人公司id';
comment on column BS_T_WF_DEALPROCESS.DEALERCORP
  is '处理人公司名称';
comment on column BS_T_WF_DEALPROCESS.ASSIGNERID
  is '派发人登陆名';
comment on column BS_T_WF_DEALPROCESS.ASSIGNER
  is '派发人名称';
comment on column BS_T_WF_DEALPROCESS.ASSIGNERDEPID
  is '派发人部门id';
comment on column BS_T_WF_DEALPROCESS.ASSIGNERDEP
  is '派发人部门';
comment on column BS_T_WF_DEALPROCESS.ASSIGNERCORPID
  is '派发人公司id';
comment on column BS_T_WF_DEALPROCESS.ASSIGNERCORP
  is '派发人公司名称';
comment on column BS_T_WF_DEALPROCESS.STDATE
  is '开始时间';
comment on column BS_T_WF_DEALPROCESS.BGDATE
  is '受理时间';
comment on column BS_T_WF_DEALPROCESS.EDDATE
  is '完成时间';
comment on column BS_T_WF_DEALPROCESS.ASSIGNOVERTIMEDATE
  is '受理时限';
comment on column BS_T_WF_DEALPROCESS.ACCEPTOVERTIMEDATE
  is '派发时限';
comment on column BS_T_WF_DEALPROCESS.DEALOVERTIMEDATE
  is '处理时限';
comment on column BS_T_WF_DEALPROCESS.INSIDEFLOWSCOUNT
  is '内部流程数量';
comment on column BS_T_WF_DEALPROCESS.FINISHINSIDEFLOWSCOUNT
  is '已完成的内部流程数量';
comment on column BS_T_WF_DEALPROCESS.HASTENCOUNT
  is '催办次数';
comment on column BS_T_WF_DEALPROCESS.BASECREATETIME
  is '建单时间';
comment on column BS_T_WF_DEALPROCESS.DPDESC
  is '环节描述';
comment on column BS_T_WF_DEALPROCESS.PREASSIGNSTRING
  is '提交审批时同时派发人的规则串';
alter table BS_T_WF_DEALPROCESS
  add primary key (PROCESSID);
create index DP_ENTRY_IDX on BS_T_WF_DEALPROCESS (ENTRYID);
create index DP_STEPID_IDEX on BS_T_WF_DEALPROCESS (STEPID);
create index DP_TASK_IDX on BS_T_WF_DEALPROCESS (TASKID);
create index IDS_DEALPROCESS_BASEID on BS_T_WF_DEALPROCESS (BASEID);
create index IDS_DEALPROCESS_BASESCHEMA on BS_T_WF_DEALPROCESS (BASESCHEMA);
create index IDS_DEALPROCESS_BID_SCHEMA on BS_T_WF_DEALPROCESS (BASEID, BASESCHEMA);

prompt Creating BS_T_WF_DEALPROCESSLOG...
create table BS_T_WF_DEALPROCESSLOG
(
  PROCESSLOGID   VARCHAR2(15) not null,
  PROCESSID      VARCHAR2(254),
  PROCESSTYPE    VARCHAR2(254),
  PHASENO        VARCHAR2(254),
  BASEID         VARCHAR2(254),
  BASESCHEMA     VARCHAR2(254),
  FLOWID         VARCHAR2(254),
  PARENTBASEID   VARCHAR2(254),
  PARENTFLOWID   VARCHAR2(254),
  BASEBASEID     VARCHAR2(254),
  BASEFLOWID     VARCHAR2(254),
  LOGUSERID      VARCHAR2(254),
  LOGUSER        VARCHAR2(254),
  LOGUSERDEPID   VARCHAR2(254),
  LOGUSERDEP     VARCHAR2(254),
  LOGUSERCORPID  VARCHAR2(254),
  LOGUSERCORP    VARCHAR2(254),
  LOGUSERDNID    VARCHAR2(254),
  LOGUSERDN      VARCHAR2(254),
  ACTIONNAME     VARCHAR2(254),
  BASECREATETIME NUMBER(19),
  LOGDESC        VARCHAR2(2000),
  LOGTIME        NUMBER(19)
)
;
alter table BS_T_WF_DEALPROCESSLOG
  add primary key (PROCESSLOGID);
create index IDS_DEALPROCESSLOG_BASEID on BS_T_WF_DEALPROCESSLOG (BASEID);
create index IDS_DEALPROCESSLOG_BASESCHEMA on BS_T_WF_DEALPROCESSLOG (BASESCHEMA);
create index IDS_DEALPROCESSLOG_BID_SCHEMA on BS_T_WF_DEALPROCESSLOG (BASEID, BASESCHEMA);
create index IDS_DEALPROCESSLOG_PHASENO on BS_T_WF_DEALPROCESSLOG (PHASENO);
create index IDS_DEALPROCESSLOG_PROCESSID on BS_T_WF_DEALPROCESSLOG (PROCESSID);

prompt Creating BS_T_WF_DIMENSIONS...
create table BS_T_WF_DIMENSIONS
(
  DIMENSIONID   VARCHAR2(50) not null,
  DIMENSIONCODE VARCHAR2(100),
  DIMENSIONNAME VARCHAR2(100),
  FIELDID       VARCHAR2(15),
  BASESCHEMA    VARCHAR2(50),
  BASENAME      VARCHAR2(200),
  DICTNAME      VARCHAR2(200),
  DICTSCHEMA    VARCHAR2(50),
  DICTFIELDID   VARCHAR2(15),
  DICTFIELDCODE VARCHAR2(50),
  DICTFIELDNAME VARCHAR2(100),
  DIMCODE       VARCHAR2(100),
  TABLENAME     VARCHAR2(100),
  TABLECOL      VARCHAR2(100),
  DTCODE        VARCHAR2(100),
  DIVALUE       VARCHAR2(100),
  DIMENSIONTYPE VARCHAR2(50),
  CUSTOMSQL     VARCHAR2(1000)
)
;
comment on column BS_T_WF_DIMENSIONS.DIMENSIONID
  is '主键';
comment on column BS_T_WF_DIMENSIONS.DIMENSIONCODE
  is '维度唯一标识';
comment on column BS_T_WF_DIMENSIONS.DIMENSIONNAME
  is '维度中文名';
comment on column BS_T_WF_DIMENSIONS.FIELDID
  is '工单字段ID';
comment on column BS_T_WF_DIMENSIONS.BASESCHEMA
  is '工单类别';
comment on column BS_T_WF_DIMENSIONS.BASENAME
  is '工单名称';
comment on column BS_T_WF_DIMENSIONS.DICTNAME
  is '字典中文名';
comment on column BS_T_WF_DIMENSIONS.DICTSCHEMA
  is '字典表名';
comment on column BS_T_WF_DIMENSIONS.DICTFIELDID
  is '字典中维度字段ID';
comment on column BS_T_WF_DIMENSIONS.DICTFIELDCODE
  is '字典中维度字段标识';
comment on column BS_T_WF_DIMENSIONS.DICTFIELDNAME
  is '字典中维度字段中文名';
comment on column BS_T_WF_DIMENSIONS.DIMCODE
  is '唯独英文标识';
comment on column BS_T_WF_DIMENSIONS.TABLENAME
  is '表名称';
comment on column BS_T_WF_DIMENSIONS.TABLECOL
  is '关联的列';
comment on column BS_T_WF_DIMENSIONS.DTCODE
  is '字典标识';
comment on column BS_T_WF_DIMENSIONS.DIVALUE
  is '字典参数';
comment on column BS_T_WF_DIMENSIONS.DIMENSIONTYPE
  is '维度类型，table，sysdic，remedy';
comment on column BS_T_WF_DIMENSIONS.CUSTOMSQL
  is '客户化查询sql';
alter table BS_T_WF_DIMENSIONS
  add primary key (DIMENSIONID);

prompt Creating BS_T_WF_EMAIL_NOTICE...
create table BS_T_WF_EMAIL_NOTICE
(
  PID         VARCHAR2(50) not null,
  BASEID      VARCHAR2(50),
  BASESCHEMA  VARCHAR2(50),
  PHASENO     VARCHAR2(50),
  CONTENT     VARCHAR2(2000),
  RECIVERID   VARCHAR2(50),
  RECIVERTYPE VARCHAR2(50),
  ISSEND      VARCHAR2(50),
  CREATETIME  NUMBER(15)
)
;
comment on column BS_T_WF_EMAIL_NOTICE.PID
  is '主键';
comment on column BS_T_WF_EMAIL_NOTICE.BASEID
  is '工单id';
comment on column BS_T_WF_EMAIL_NOTICE.BASESCHEMA
  is '工单类型';
comment on column BS_T_WF_EMAIL_NOTICE.PHASENO
  is '环节号';
comment on column BS_T_WF_EMAIL_NOTICE.CONTENT
  is '邮件内容，带格式';
comment on column BS_T_WF_EMAIL_NOTICE.RECIVERID
  is '接受者id';
comment on column BS_T_WF_EMAIL_NOTICE.RECIVERTYPE
  is 'role、group、user';
comment on column BS_T_WF_EMAIL_NOTICE.ISSEND
  is '1：发送；0：未发送；2：发送失败';
comment on column BS_T_WF_EMAIL_NOTICE.CREATETIME
  is '创建时间';
alter table BS_T_WF_EMAIL_NOTICE
  add constraint PK_BS_T_WF_EMAIL_NOTICE primary key (PID);

prompt Creating BS_T_WF_ENTRY...
create table BS_T_WF_ENTRY
(
  ID            VARCHAR2(15) not null,
  PARENTENTRYID VARCHAR2(15),
  TOPENTRYID    VARCHAR2(15),
  PARENTFLOWID  VARCHAR2(15),
  ENDCODE       VARCHAR2(255),
  TYPE          VARCHAR2(15),
  DEFNAME       VARCHAR2(255),
  STATE         VARCHAR2(255),
  CLOSETIME     NUMBER(19),
  CREATETIME    NUMBER(19),
  TOPFLOWID     VARCHAR2(15)
)
;
comment on column BS_T_WF_ENTRY.PARENTENTRYID
  is '父流程实体id';
comment on column BS_T_WF_ENTRY.TOPENTRYID
  is '顶级父流程实体id';
comment on column BS_T_WF_ENTRY.PARENTFLOWID
  is '流程号，用于父子流程对应';
comment on column BS_T_WF_ENTRY.TYPE
  is '流程类型';
comment on column BS_T_WF_ENTRY.DEFNAME
  is '流程版本号';
comment on column BS_T_WF_ENTRY.STATE
  is '流程状态';
comment on column BS_T_WF_ENTRY.CLOSETIME
  is '流程关闭时间';
comment on column BS_T_WF_ENTRY.CREATETIME
  is '流程创建时间';
alter table BS_T_WF_ENTRY
  add primary key (ID);

prompt Creating BS_T_WF_EVENTDEF...
create table BS_T_WF_EVENTDEF
(
  PID               VARCHAR2(15) not null,
  EVENTDEFID        VARCHAR2(200),
  BASESCHEMA        VARCHAR2(50),
  EVENTTYPE         VARCHAR2(20),
  EVENTSUBJECT      VARCHAR2(50),
  EVENTACTION       VARCHAR2(20),
  EVENTCONDITION    VARCHAR2(200),
  EVENTCONDITIONID  VARCHAR2(100),
  HANDLETYPE        VARCHAR2(20),
  HANDLETYPEGROUPID VARCHAR2(50),
  OPERATIONTYPE     VARCHAR2(20),
  OPERATIONNAME     VARCHAR2(200),
  CREATETIME        NUMBER(15),
  UPDATETIME        NUMBER(15),
  DUETIMEFIELD      VARCHAR2(15)
)
;
comment on column BS_T_WF_EVENTDEF.EVENTDEFID
  is '事件定义主键';
comment on column BS_T_WF_EVENTDEF.BASESCHEMA
  is '事件从属工单类型';
comment on column BS_T_WF_EVENTDEF.EVENTTYPE
  is '分为FORM(工单),STEP(环节),ACTION(动作)';
comment on column BS_T_WF_EVENTDEF.EVENTSUBJECT
  is '工单范围为FORM,环节范围为dp_n';
comment on column BS_T_WF_EVENTDEF.EVENTACTION
  is 'IN(流入),OUT(流出),DO(执行)';
comment on column BS_T_WF_EVENTDEF.EVENTCONDITION
  is 'FORM和STEP时为状态名称，ACTION时为动作名称';
comment on column BS_T_WF_EVENTDEF.EVENTCONDITIONID
  is '所属事件触发规则ID';
comment on column BS_T_WF_EVENTDEF.HANDLETYPE
  is 'NEW,DESTROY';
comment on column BS_T_WF_EVENTDEF.HANDLETYPEGROUPID
  is '触发组ID，new和destroy具有相同的触发组ID';
comment on column BS_T_WF_EVENTDEF.OPERATIONTYPE
  is 'CLASS(实现类),SLA';
comment on column BS_T_WF_EVENTDEF.OPERATIONNAME
  is '实现类的全路径(CLASS),SLA规则名称(SLA)';
comment on column BS_T_WF_EVENTDEF.DUETIMEFIELD
  is '时限字段ID';
alter table BS_T_WF_EVENTDEF
  add constraint PK_BS_T_WF_EVENTDEF primary key (PID);

prompt Creating BS_T_WF_EVENTINSTANCE...
create table BS_T_WF_EVENTINSTANCE
(
  PID                VARCHAR2(15) not null,
  EVENTINSTANCEID    VARCHAR2(50),
  SUBJECTINSTANCEID  VARCHAR2(50),
  EVENTCREATETIME    NUMBER(15),
  HANDLEMARK         VARCHAR2(200),
  EVENTSTATUS        NUMBER(2),
  EVENTDEFID         VARCHAR2(50),
  BASESCHEMA         VARCHAR2(50),
  EVENTTYPE          VARCHAR2(20),
  EVENTSUBJECT       VARCHAR2(50),
  EVENTACTION        VARCHAR2(20),
  EVENTCONDITION     VARCHAR2(200),
  HANDLETYPE         VARCHAR2(20),
  HANDLETYPEGROUPID  VARCHAR2(50),
  OPERATIONNAME      VARCHAR2(200),
  DUETIME            NUMBER(15),
  CREATETIME         NUMBER(15),
  UPDATETIME         NUMBER(15),
  DEFAULTHANDLERTYPE NUMBER(2),
  DEFAULTHANDLERID   VARCHAR2(300),
  OPERATIONTYPE      VARCHAR2(20),
  LOGRECORD          VARCHAR2(300),
  EVENTBASEID        VARCHAR2(20)
)
;
comment on column BS_T_WF_EVENTINSTANCE.EVENTINSTANCEID
  is '事件实例ID';
comment on column BS_T_WF_EVENTINSTANCE.SUBJECTINSTANCEID
  is '事件主体实例ID，如工单ID、TASKID等';
comment on column BS_T_WF_EVENTINSTANCE.EVENTCREATETIME
  is '事件发生时间';
comment on column BS_T_WF_EVENTINSTANCE.HANDLEMARK
  is '事件触发逻辑的返回标识';
comment on column BS_T_WF_EVENTINSTANCE.EVENTSTATUS
  is '1(已触发),0(为触发),2(触发异常)';
comment on column BS_T_WF_EVENTINSTANCE.EVENTDEFID
  is '事件的定义ID';
comment on column BS_T_WF_EVENTINSTANCE.BASESCHEMA
  is '事件从属工单类型';
comment on column BS_T_WF_EVENTINSTANCE.EVENTTYPE
  is '事件定义的字段';
comment on column BS_T_WF_EVENTINSTANCE.EVENTSUBJECT
  is '工单范围为FORM,环节范围为dp_n';
comment on column BS_T_WF_EVENTINSTANCE.EVENTACTION
  is 'IN(流入),OUT(流出),DO(执行)';
comment on column BS_T_WF_EVENTINSTANCE.EVENTCONDITION
  is 'FORM和STEP时为状态名称，ACTION时为动作名称';
comment on column BS_T_WF_EVENTINSTANCE.HANDLETYPE
  is 'NEW,DESTROY';
comment on column BS_T_WF_EVENTINSTANCE.OPERATIONNAME
  is '实现类的全路径(CLASS),SLA规则名称(SLA)';
comment on column BS_T_WF_EVENTINSTANCE.DUETIME
  is '该事件实例所对应的业务的处理或受理时限';
comment on column BS_T_WF_EVENTINSTANCE.DEFAULTHANDLERTYPE
  is '当前处理对象类型 0：人；1：组；2：角色';
comment on column BS_T_WF_EVENTINSTANCE.DEFAULTHANDLERID
  is '当前处理对象ID，多个用逗号分隔';
comment on column BS_T_WF_EVENTINSTANCE.OPERATIONTYPE
  is '操作类型';
comment on column BS_T_WF_EVENTINSTANCE.LOGRECORD
  is '扫描日志记录';
comment on column BS_T_WF_EVENTINSTANCE.EVENTBASEID
  is '事件baseid';
alter table BS_T_WF_EVENTINSTANCE
  add constraint PK_BS_T_WF_EVENTINSTANCE primary key (PID);

prompt Creating BS_T_WF_HISTORYSTEPS...
create table BS_T_WF_HISTORYSTEPS
(
  ID           VARCHAR2(15) not null,
  ENTRYID      VARCHAR2(15),
  STEPCODE     VARCHAR2(15),
  FORWARDID    VARCHAR2(500),
  FORWARDCODE  VARCHAR2(500),
  BACKWARDID   VARCHAR2(500),
  BACKWARDCODE VARCHAR2(500),
  FLOWID       VARCHAR2(15),
  ORIGID       VARCHAR2(15),
  ORIGCODE     VARCHAR2(15),
  STATE        VARCHAR2(255),
  TRACK        VARCHAR2(255),
  CREATETIME   NUMBER(19),
  SUBCOUNT     NUMBER(10),
  FINISHCOUNT  NUMBER(10),
  TYPE         VARCHAR2(255),
  TRANSTYPE    VARCHAR2(255),
  NAME         VARCHAR2(255),
  STEPNO       VARCHAR2(50),
  STEPGROUP    VARCHAR2(255)
)
;
comment on column BS_T_WF_HISTORYSTEPS.ID
  is '主键';
comment on column BS_T_WF_HISTORYSTEPS.ENTRYID
  is '流程实体id';
comment on column BS_T_WF_HISTORYSTEPS.STEPCODE
  is '流程定义环节号';
comment on column BS_T_WF_HISTORYSTEPS.FORWARDID
  is '正向前一环节stepId';
comment on column BS_T_WF_HISTORYSTEPS.FORWARDCODE
  is '正向前一环节号';
comment on column BS_T_WF_HISTORYSTEPS.BACKWARDID
  is '反向前一环节stepId';
comment on column BS_T_WF_HISTORYSTEPS.BACKWARDCODE
  is '反向前一环节号';
comment on column BS_T_WF_HISTORYSTEPS.FLOWID
  is '与子流程关联的标识';
comment on column BS_T_WF_HISTORYSTEPS.ORIGID
  is '转派回复的原始stepId';
comment on column BS_T_WF_HISTORYSTEPS.STATE
  is '环节状态';
comment on column BS_T_WF_HISTORYSTEPS.TRACK
  is '环节轨迹';
comment on column BS_T_WF_HISTORYSTEPS.CREATETIME
  is '创建时间';
comment on column BS_T_WF_HISTORYSTEPS.SUBCOUNT
  is '子流程个数';
comment on column BS_T_WF_HISTORYSTEPS.FINISHCOUNT
  is '子流程完成个数';
comment on column BS_T_WF_HISTORYSTEPS.TYPE
  is '环节类型';
comment on column BS_T_WF_HISTORYSTEPS.TRANSTYPE
  is '是否影响流转';
comment on column BS_T_WF_HISTORYSTEPS.NAME
  is '固定流程流程定义环节名称';
alter table BS_T_WF_HISTORYSTEPS
  add primary key (ID);
create index HIS_STEP_IDX on BS_T_WF_HISTORYSTEPS (ENTRYID);

prompt Creating BS_T_WF_HISTORYTASK...
create table BS_T_WF_HISTORYTASK
(
  ID                     VARCHAR2(15) not null,
  ENTRYID                VARCHAR2(15),
  PARENTENTRYID          VARCHAR2(15),
  TOPENTRYID             VARCHAR2(15),
  PREVTASKID             VARCHAR2(15),
  PARENTTASKID           VARCHAR2(15),
  TOPTASKID              VARCHAR2(15),
  ACTORTYPE              VARCHAR2(255),
  CREATOR                VARCHAR2(255),
  CREATORNAME            VARCHAR2(255),
  CREATORTYPE            VARCHAR2(255),
  STEPID                 VARCHAR2(15),
  STEPCODE               VARCHAR2(15),
  PROCESSSTATE           VARCHAR2(255),
  ENTRYSTATE             VARCHAR2(255),
  FLAGACTIVE             NUMBER(19),
  FLAGASSIGNTYPE         VARCHAR2(255),
  PROCESSTYPE            VARCHAR2(255),
  ASSIGNEEID             VARCHAR2(255),
  ASSIGNEE               VARCHAR2(255),
  ASSIGNEEDEPID          VARCHAR2(255),
  ASSIGNEEDEP            VARCHAR2(255),
  ASSIGNEECORPID         VARCHAR2(255),
  ASSIGNEECORP           VARCHAR2(255),
  ASSIGNEEDNID           VARCHAR2(255),
  ASSIGNEEDN             VARCHAR2(255),
  ASSIGNGROUPID          VARCHAR2(255),
  ASSIGNGROUP            VARCHAR2(255),
  DEALERID               VARCHAR2(255),
  DEALER                 VARCHAR2(255),
  DEALERDEPID            VARCHAR2(255),
  DEALERDEP              VARCHAR2(255),
  DEALERCORPID           VARCHAR2(255),
  DEALERCORP             VARCHAR2(255),
  DEALERDNID             VARCHAR2(255),
  DEALERDN               VARCHAR2(255),
  ASSIGNERID             VARCHAR2(100),
  ASSIGNER               VARCHAR2(100),
  ASSIGNERDEPID          VARCHAR2(100),
  ASSIGNERDEP            VARCHAR2(200),
  ASSIGNERCORPID         VARCHAR2(100),
  ASSIGNERCORP           VARCHAR2(200),
  ASSIGNERDNID           VARCHAR2(100),
  ASSIGNERDN             VARCHAR2(400),
  ROLEID                 VARCHAR2(255),
  ROLENAME               VARCHAR2(255),
  CREATETIME             NUMBER(19),
  ACCEPTTIME             NUMBER(19),
  FINISHTIME             NUMBER(19),
  ACCEPTDATE             NUMBER(19),
  SENDDATE               NUMBER(19),
  DUEDATE                NUMBER(19),
  BASECREATETIME         NUMBER(19),
  TASKNAME               VARCHAR2(255),
  DEFNAME                VARCHAR2(255),
  DEALTYPE               VARCHAR2(255),
  ACTIONNAME             VARCHAR2(255),
  ACTIONCODE             VARCHAR2(255),
  WORKSHEETTITLE         VARCHAR2(1000),
  WORKSHEETPROTYPE       VARCHAR2(255),
  WORKSHEETURGENTLEVEL   VARCHAR2(255),
  WORKSHEETDESC          VARCHAR2(255),
  BASESCHEMA             VARCHAR2(255),
  BASENAME               VARCHAR2(255),
  BASEID                 VARCHAR2(255),
  SERIALNUM              VARCHAR2(255),
  STEPNAME               VARCHAR2(255),
  PREPHASENO             VARCHAR2(100),
  FLAGDUPLICATED         NUMBER(19),
  FLAGENDDUPLICATED      NUMBER(19),
  FLAGENDPHASE           NUMBER(19),
  FLAGPREDEFINED         NUMBER(19),
  STPROCESSACTION        VARCHAR2(50),
  BASESTARTDATE          NUMBER(19),
  FLOWID                 VARCHAR2(100),
  PARENTFLOWID           VARCHAR2(100),
  BASEFLOWID             VARCHAR2(100),
  FLAGASSIGN             NUMBER(19),
  FLAGASSIST             NUMBER(19),
  FLAGCOPY               NUMBER(19),
  FLAGTRANSFER           NUMBER(19),
  FLAGTURNUP             NUMBER(19),
  FLAGRECALL             NUMBER(19),
  FLAGCANCEL             NUMBER(19),
  FLAGCLOSE              NUMBER(19),
  FLAGTURNDOWN           NUMBER(19),
  FLAGTOASSISTAUDITING   NUMBER(19),
  FLAGTOAUDITING         NUMBER(19),
  FLAGAUDITINGRESULT     NUMBER(19),
  INSIDEFLOWSCOUNT       NUMBER(19),
  FINISHINSIDEFLOWSCOUNT NUMBER(19),
  HASTENCOUNT            NUMBER(19),
  CUSTOMACTIONS          VARCHAR2(400),
  PREASSIGNSTRING        VARCHAR2(400),
  ORIGPHASENO            VARCHAR2(50),
  EDPROCESSACTION        VARCHAR2(50),
  STEPNO                 VARCHAR2(50),
  STEPGROUP              VARCHAR2(255),
  TRACK                  VARCHAR2(255)
)
;
create index HT_STEPID_IDX on BS_T_WF_HISTORYTASK (STEPID);
create index HT_TOPENTRYID_IDX on BS_T_WF_HISTORYTASK (TOPENTRYID);
create index IDS_HISTORYTASK_BASEID on BS_T_WF_HISTORYTASK (BASEID);
create index IDS_HISTORYTASK_BASESCHEMA on BS_T_WF_HISTORYTASK (BASESCHEMA);
create index IDS_HISTORYTASK_BID_SCHEMA on BS_T_WF_HISTORYTASK (BASEID, BASESCHEMA);

prompt Creating BS_T_WF_INTERFACE...
create table BS_T_WF_INTERFACE
(
  ID     VARCHAR2(254) not null,
  CODE   VARCHAR2(200),
  NAME   VARCHAR2(200),
  TYPE   VARCHAR2(200),
  PATH   VARCHAR2(1000),
  REMARK VARCHAR2(1000)
)
;
alter table BS_T_WF_INTERFACE
  add primary key (ID);

prompt Creating BS_T_WF_MODEL...
create table BS_T_WF_MODEL
(
  ID           VARCHAR2(254) not null,
  CODE         VARCHAR2(200),
  NAME         VARCHAR2(200),
  PHOTOPATH    VARCHAR2(200),
  ISPUBLISH    NUMBER,
  TYPE         VARCHAR2(100),
  REMARK       VARCHAR2(1000),
  PREFUNCTION  VARCHAR2(1000),
  POSTFUNCTION VARCHAR2(1000)
)
;
alter table BS_T_WF_MODEL
  add primary key (ID);

prompt Creating BS_T_WF_MODEL_PROPERTIES...
create table BS_T_WF_MODEL_PROPERTIES
(
  ID          VARCHAR2(254) not null,
  MODELCODE   VARCHAR2(200),
  CODE        VARCHAR2(200),
  NAME        VARCHAR2(200),
  TYPE        VARCHAR2(200),
  DICT        VARCHAR2(200),
  ISNULL      NUMBER,
  DEFAULVALUE VARCHAR2(200),
  MODELNAME   VARCHAR2(200),
  ORDERBY     NUMBER default 0
)
;
alter table BS_T_WF_MODEL_PROPERTIES
  add primary key (ID);

prompt Creating BS_T_WF_NOTICE...
create table BS_T_WF_NOTICE
(
  NOTICEID            VARCHAR2(15) not null,
  BASEID              VARCHAR2(255),
  BASESCHEMA          VARCHAR2(50),
  BASESN              VARCHAR2(50),
  TASKID              VARCHAR2(15),
  BASESUMMARY         VARCHAR2(500),
  BASEITEMS           VARCHAR2(200),
  BASEACTION          VARCHAR2(50),
  NOTICEGROUP         VARCHAR2(300),
  NOTICEGROUPID       VARCHAR2(50),
  NOTICEUSER          VARCHAR2(100),
  NOTICEUSERID        VARCHAR2(50),
  CONTENT             VARCHAR2(2000),
  MOBILES             VARCHAR2(500),
  ISSENT              NUMBER default 0,
  ISSCANED            NUMBER default 0,
  SCANDESC            VARCHAR2(50),
  PHASENO             VARCHAR2(15),
  FROMTASKID          VARCHAR2(15),
  FROMPHASENO         VARCHAR2(15),
  ISPUSH              NUMBER default 0,
  NOTICEUSERLOGINNAME VARCHAR2(100),
  CREATETIME          NUMBER(19),
  NOTICETYPE          VARCHAR2(50)
)
;
alter table BS_T_WF_NOTICE
  add primary key (NOTICEID);

prompt Creating BS_T_WF_RELATION...
create table BS_T_WF_RELATION
(
  ID                   VARCHAR2(15) not null,
  BASEID               VARCHAR2(254),
  BASENAME             VARCHAR2(254),
  BASESCHEMA           VARCHAR2(254),
  BASEFLOWID           VARCHAR2(254),
  BASEPROCESSID        VARCHAR2(254),
  BASEPHASENO          VARCHAR2(254),
  BASEPROCESSTYPE      VARCHAR2(254),
  BASEPROCESSLOGID     VARCHAR2(254),
  RELATEBASEID         VARCHAR2(254),
  RELATEBASENAME       VARCHAR2(254),
  RELATETYPE           NUMBER(15),
  RELATEBASESCHEMA     VARCHAR2(254),
  RELATEBASESN         VARCHAR2(254),
  RELATEBASESUMMARY    VARCHAR2(254),
  RELATEBASECREATETIME NUMBER(15),
  RELATEBASECREATEUSER VARCHAR2(254),
  BASECREATETIME       NUMBER(15),
  RELATETIME           NUMBER(15),
  RELATEUSERLOGINNAME  VARCHAR2(254),
  RELATETASKID         VARCHAR2(254),
  BASESN               VARCHAR2(254),
  BASESUMMARY          VARCHAR2(254),
  RELATEUSERNAME       VARCHAR2(254)
)
;
comment on column BS_T_WF_RELATION.BASEID
  is '工单ID';
comment on column BS_T_WF_RELATION.BASENAME
  is '工单名';
comment on column BS_T_WF_RELATION.BASESCHEMA
  is '工单表单名';
comment on column BS_T_WF_RELATION.BASEFLOWID
  is '流程定义版本';
comment on column BS_T_WF_RELATION.BASEPROCESSID
  is '环节ID';
comment on column BS_T_WF_RELATION.BASEPHASENO
  is '环节号';
comment on column BS_T_WF_RELATION.BASEPROCESSTYPE
  is '环节类型';
comment on column BS_T_WF_RELATION.BASEPROCESSLOGID
  is '环节日志ID';
comment on column BS_T_WF_RELATION.RELATEBASEID
  is '关联工单ID';
comment on column BS_T_WF_RELATION.RELATEBASENAME
  is '关联工单名';
comment on column BS_T_WF_RELATION.RELATETYPE
  is '关联关系';
comment on column BS_T_WF_RELATION.RELATEBASESCHEMA
  is '关联工单表单名';
comment on column BS_T_WF_RELATION.RELATEBASESN
  is '关联工单流水号';
comment on column BS_T_WF_RELATION.RELATEBASESUMMARY
  is '关联工单主题';
comment on column BS_T_WF_RELATION.RELATEBASECREATETIME
  is '关联工单建单时间';
comment on column BS_T_WF_RELATION.RELATEBASECREATEUSER
  is '关联工单建单人';
comment on column BS_T_WF_RELATION.BASECREATETIME
  is '工单新建时间';
comment on column BS_T_WF_RELATION.RELATETIME
  is '工单关联时间';
comment on column BS_T_WF_RELATION.RELATEUSERLOGINNAME
  is '关联人';
comment on column BS_T_WF_RELATION.RELATETASKID
  is '关联任务id（被挂起任务的id，激活时使用）';
comment on column BS_T_WF_RELATION.BASESN
  is '工单流水号';
comment on column BS_T_WF_RELATION.BASESUMMARY
  is '工单主题';
comment on column BS_T_WF_RELATION.RELATEUSERNAME
  is '关联人';
alter table BS_T_WF_RELATION
  add primary key (ID);

prompt Creating BS_T_WF_ROLEMATCHDIMENSION...
create table BS_T_WF_ROLEMATCHDIMENSION
(
  MATCHID       VARCHAR2(15) not null,
  DIMENSIONCODE VARCHAR2(100),
  ROLECODE      VARCHAR2(50),
  ORDERBY       NUMBER default 0,
  BASEVERSION   VARCHAR2(100)
)
;
alter table BS_T_WF_ROLEMATCHDIMENSION
  add primary key (MATCHID);

prompt Creating BS_T_WF_ROLEUSER...
create table BS_T_WF_ROLEUSER
(
  ROLEUSERID  VARCHAR2(50) not null,
  CHILDROLEID VARCHAR2(50),
  ROLECODE    VARCHAR2(50),
  LOGINNAME   VARCHAR2(50),
  FULLNAME    VARCHAR2(50),
  DEPNAME     VARCHAR2(100),
  DEPID       VARCHAR2(50)
)
;
comment on column BS_T_WF_ROLEUSER.ROLEUSERID
  is '主键';
comment on column BS_T_WF_ROLEUSER.CHILDROLEID
  is '角色细分ID';
comment on column BS_T_WF_ROLEUSER.ROLECODE
  is '角色标识';
comment on column BS_T_WF_ROLEUSER.LOGINNAME
  is '用户登录名';
comment on column BS_T_WF_ROLEUSER.FULLNAME
  is '用户全名';
comment on column BS_T_WF_ROLEUSER.DEPNAME
  is '部门名称';
comment on column BS_T_WF_ROLEUSER.DEPID
  is '部门ID';
alter table BS_T_WF_ROLEUSER
  add primary key (ROLEUSERID);

prompt Creating BS_T_WF_SORT...
create table BS_T_WF_SORT
(
  ID         VARCHAR2(15) not null,
  CODE       VARCHAR2(100),
  NAME       VARCHAR2(100),
  PID        VARCHAR2(254),
  ORDERBY    NUMBER default 0,
  REMARK     VARCHAR2(1000),
  CREATETIME NUMBER(19) default 0,
  PASSWORD   VARCHAR2(254)
)
;
alter table BS_T_WF_SORT
  add primary key (ID);

prompt Creating BS_T_WF_TYPE...
create table BS_T_WF_TYPE
(
  ID                        VARCHAR2(15) not null,
  SORTID                    VARCHAR2(254),
  CODE                      VARCHAR2(100),
  NAME                      VARCHAR2(100),
  ENTRYCOUNT                NUMBER default 0,
  TODAYENTRYCOUNT           NUMBER default 0,
  WFCOUNTTYPE               VARCHAR2(20),
  WFTYPE                    NUMBER(15) default 0,
  WFDEFAULTVERSION          VARCHAR2(254),
  ORDERBY                   NUMBER default 0,
  REMARK                    VARCHAR2(1000),
  LASTENTRYTIME             NUMBER(19) default 0,
  BASECATEGORYSCHAMA        VARCHAR2(254),
  BASECATEGORYCODE          VARCHAR2(254),
  BASECATEGORYISDEFAULTFIX  NUMBER(15) default 0,
  BASECATEGORYSTATE         NUMBER(15) default 1,
  BASECATEGORYBTNALLIDS     VARCHAR2(1000),
  BASECATEGORYBTNFREEIDS    VARCHAR2(1000),
  BASECATEGORYBTNFIXIDS     VARCHAR2(1000),
  BASECATEGORYPANELHIDS     VARCHAR2(1000),
  BASECATEGORYPANELIDS      VARCHAR2(1000),
  BASECATEGORYCUSTOMACTIONS VARCHAR2(1000),
  BASECATEGORYBEGINPHASENO  VARCHAR2(254),
  PANELGROUPID              VARCHAR2(100),
  THEME                     VARCHAR2(100),
  FLOWTYPE                  VARCHAR2(50)
)
;
comment on column BS_T_WF_TYPE.SORTID
  is '流程分类ID';
comment on column BS_T_WF_TYPE.CODE
  is '流程类型标识';
comment on column BS_T_WF_TYPE.NAME
  is '流程类型名称';
comment on column BS_T_WF_TYPE.ENTRYCOUNT
  is '总实例数';
comment on column BS_T_WF_TYPE.TODAYENTRYCOUNT
  is '当天实例数';
comment on column BS_T_WF_TYPE.WFCOUNTTYPE
  is '计数方式，如年、月、日';
comment on column BS_T_WF_TYPE.WFTYPE
  is '是否固定流程';
comment on column BS_T_WF_TYPE.WFDEFAULTVERSION
  is '默认的模板号';
comment on column BS_T_WF_TYPE.ORDERBY
  is '排序';
comment on column BS_T_WF_TYPE.REMARK
  is '描述';
comment on column BS_T_WF_TYPE.LASTENTRYTIME
  is '工单起始流水号更新的时间';
comment on column BS_T_WF_TYPE.BASECATEGORYSCHAMA
  is '工单Form名';
comment on column BS_T_WF_TYPE.BASECATEGORYCODE
  is '工单Form代码';
comment on column BS_T_WF_TYPE.BASECATEGORYISDEFAULTFIX
  is '是否默认模板';
comment on column BS_T_WF_TYPE.BASECATEGORYSTATE
  is '工单类别状态';
comment on column BS_T_WF_TYPE.BASECATEGORYBTNALLIDS
  is '需要控制的所有流程按钮';
comment on column BS_T_WF_TYPE.BASECATEGORYBTNFREEIDS
  is '需要控制的自由流程按钮';
comment on column BS_T_WF_TYPE.BASECATEGORYBTNFIXIDS
  is '需要控制的固定流程按钮';
comment on column BS_T_WF_TYPE.BASECATEGORYPANELHIDS
  is '需要控制的Panel Holder';
comment on column BS_T_WF_TYPE.BASECATEGORYPANELIDS
  is '需要控制的Panel';
comment on column BS_T_WF_TYPE.BASECATEGORYCUSTOMACTIONS
  is '建单环节客户化动作';
comment on column BS_T_WF_TYPE.BASECATEGORYBEGINPHASENO
  is '建单环节环节号';
alter table BS_T_WF_TYPE
  add primary key (ID);

prompt Creating BS_T_WF_USERROLE...
create table BS_T_WF_USERROLE
(
  ID          VARCHAR2(15) not null,
  LOGINNAME   VARCHAR2(50),
  CHILDROLEID VARCHAR2(4000),
  FULLNAME    VARCHAR2(50)
)
;
comment on column BS_T_WF_USERROLE.LOGINNAME
  is '用户登录名';
comment on column BS_T_WF_USERROLE.CHILDROLEID
  is '角色细分ID';
comment on column BS_T_WF_USERROLE.FULLNAME
  is '用户全名';
alter table BS_T_WF_USERROLE
  add primary key (ID);

prompt Creating BS_T_WF_VERSION...
create table BS_T_WF_VERSION
(
  ID              VARCHAR2(254) not null,
  BASECODE        VARCHAR2(100),
  CODE            VARCHAR2(100),
  NAME            VARCHAR2(200),
  ISPUBLISH       NUMBER,
  SUBFLAG         NUMBER,
  PUBLISHTIME     NUMBER,
  REMARK          VARCHAR2(1000),
  BASENAME        VARCHAR2(100),
  CREATETIME      NUMBER,
  DESIGNXML       VARCHAR2(4000),
  WORKFLOWXML     VARCHAR2(4000),
  ENTRYCOUNT      NUMBER,
  TODAYENTRYCOUNT NUMBER,
  LASTENTRYTIME   NUMBER
)
;
comment on column BS_T_WF_VERSION.BASECODE
  is '流程标识';
comment on column BS_T_WF_VERSION.CODE
  is '流程版本号';
comment on column BS_T_WF_VERSION.NAME
  is '流程版本名称';
comment on column BS_T_WF_VERSION.ISPUBLISH
  is '是否启用(0为未启用，1为启用)';
comment on column BS_T_WF_VERSION.SUBFLAG
  is '是否主流程(0为主流程，1为子流程)';
comment on column BS_T_WF_VERSION.PUBLISHTIME
  is '启用时间';
comment on column BS_T_WF_VERSION.REMARK
  is '备注';
comment on column BS_T_WF_VERSION.BASENAME
  is '流程名称';
comment on column BS_T_WF_VERSION.CREATETIME
  is '创建时间';
comment on column BS_T_WF_VERSION.DESIGNXML
  is '流程设计文件';
comment on column BS_T_WF_VERSION.WORKFLOWXML
  is '流程文件';
alter table BS_T_WF_VERSION
  add primary key (ID);

prompt Creating BS_T_WF_WORKFLOWROLE...
create table BS_T_WF_WORKFLOWROLE
(
  ROLEID      VARCHAR2(50) not null,
  ROLECODE    VARCHAR2(50),
  ROLENAME    VARCHAR2(200),
  BASESCHEMA  VARCHAR2(50),
  BASENAME    VARCHAR2(200),
  BASEVERSION VARCHAR2(50),
  PHASENO     VARCHAR2(50)
)
;
comment on column BS_T_WF_WORKFLOWROLE.ROLECODE
  is '角色标识';
comment on column BS_T_WF_WORKFLOWROLE.ROLENAME
  is '角色名称';
comment on column BS_T_WF_WORKFLOWROLE.BASESCHEMA
  is '工单类型';
comment on column BS_T_WF_WORKFLOWROLE.BASENAME
  is '工单名称';
comment on column BS_T_WF_WORKFLOWROLE.BASEVERSION
  is '工单版本号';
comment on column BS_T_WF_WORKFLOWROLE.PHASENO
  is '环节号';
alter table BS_T_WF_WORKFLOWROLE
  add primary key (ROLEID);

prompt Creating ZZTEST_DIC...
create table ZZTEST_DIC
(
  ID    NUMBER,
  KEY   VARCHAR2(100),
  VALUE VARCHAR2(100)
)
;

prompt Loading BS_F_UBP_TEST_FIX...
prompt Table is empty
prompt Loading BS_F_UBP_TEST_FIX_FML...
prompt Table is empty
prompt Loading BS_F_UBP_TEST_FREE...
prompt Table is empty
prompt Loading BS_F_UBP_TEST_FREE_FML...
prompt Table is empty
prompt Loading BS_T_BPP_ACTION...
prompt Table is empty
prompt Loading BS_T_BPP_ACTIONFIELDREL...
prompt Table is empty
prompt Loading BS_T_BPP_ASSINGTREECONFIG...
prompt Table is empty
prompt Loading BS_T_BPP_ASSINGTREEORGANIZE...
prompt Table is empty
prompt Loading BS_T_BPP_BASEINFOR...
prompt Table is empty
prompt Loading BS_T_BPP_ENTRYATTRIBUTE...
prompt Table is empty
prompt Loading BS_T_BPP_FREEACTIONFIELDREL...
prompt Table is empty
prompt Loading BS_T_BPP_F_ASSIGNTREE...
prompt Table is empty
prompt Loading BS_T_BPP_F_ATTACHMENT...
prompt Table is empty
prompt Loading BS_T_BPP_F_BLANK...
prompt Table is empty
prompt Loading BS_T_BPP_F_BUTTON...
prompt Table is empty
prompt Loading BS_T_BPP_F_CHARACTER...
prompt Table is empty
prompt Loading BS_T_BPP_F_COLLECT...
prompt Table is empty
prompt Loading BS_T_BPP_F_HIDDEN...
prompt Table is empty
prompt Loading BS_T_BPP_F_LABEL...
prompt Table is empty
prompt Loading BS_T_BPP_F_PANEL...
prompt Table is empty
prompt Loading BS_T_BPP_F_PANELGROUP...
prompt Table is empty
prompt Loading BS_T_BPP_F_ROLLBACKLIST...
prompt Table is empty
prompt Loading BS_T_BPP_F_SELECT...
prompt Table is empty
prompt Loading BS_T_BPP_F_TIME...
prompt Table is empty
prompt Loading BS_T_BPP_F_VIEW...
prompt Table is empty
prompt Loading BS_T_BPP_STEP...
prompt Table is empty
prompt Loading BS_T_BPP_STEPFIELDREL...
prompt Table is empty
prompt Loading BS_T_CMBC_ALARMNOTE...
prompt Table is empty
prompt Loading BS_T_RECORDS_OPERLOG...
prompt Table is empty
prompt Loading BS_T_SM_ATTACHMENT...
prompt Table is empty
prompt Loading BS_T_SM_BUSINESSORG...
prompt Table is empty
prompt Loading BS_T_SM_BUSINESSORGANDDEP...
prompt Table is empty
prompt Loading BS_T_SM_BUSINESSORGANDUSER...
prompt Table is empty
prompt Loading BS_T_SM_CUSTOMCONFIG...
prompt Table is empty
prompt Loading BS_T_SM_CUSTOMORGANIZE...
prompt Table is empty
prompt Loading BS_T_SM_DEP...
prompt Table is empty
prompt Loading BS_T_SM_DEPATR...
prompt Table is empty
prompt Loading BS_T_SM_DICITEM...
prompt Table is empty
prompt Loading BS_T_SM_DICTYPE...
prompt Table is empty
prompt Loading BS_T_SM_DTREEINFO...
prompt Table is empty
prompt Loading BS_T_SM_DTREENODEINFO...
prompt Table is empty
prompt Loading BS_T_SM_EXCELEXPCFG...
prompt Table is empty
prompt Loading BS_T_SM_EXCELEXPCFGFLD...
prompt Table is empty
prompt Loading BS_T_SM_EXCELEXPCFGROW...
prompt Table is empty
prompt Loading BS_T_SM_FIELD...
prompt Table is empty
prompt Loading BS_T_SM_FLUXSTATLOG...
prompt Table is empty
prompt Loading BS_T_SM_FORMCUSTSENDTREE...
prompt Table is empty
prompt Loading BS_T_SM_HOLIDAY...
prompt Table is empty
prompt Loading BS_T_SM_MENUTREE...
prompt Table is empty
prompt Loading BS_T_SM_MENUTREE2...
prompt Table is empty
prompt Loading BS_T_SM_MYMENU...
prompt Table is empty
prompt Loading BS_T_SM_OPDATAPRIVILEGE...
prompt Table is empty
prompt Loading BS_T_SM_OPERATE...
prompt Table is empty
prompt Loading BS_T_SM_OPERAUDITLOG...
prompt Table is empty
prompt Loading BS_T_SM_POSITION...
prompt Table is empty
prompt Loading BS_T_SM_POSITIONDEP...
prompt Table is empty
prompt Loading BS_T_SM_POSITIONUSER...
prompt Table is empty
prompt Loading BS_T_SM_PWDMANAGE...
prompt Table is empty
prompt Loading BS_T_SM_RESOURCE...
prompt Table is empty
prompt Loading BS_T_SM_RESOURCEURL...
prompt Table is empty
prompt Loading BS_T_SM_RESPROPERTY...
prompt Table is empty
prompt Loading BS_T_SM_ROLE...
prompt Table is empty
prompt Loading BS_T_SM_ROLEMENUTREE...
prompt Table is empty
prompt Loading BS_T_SM_ROLEORG...
prompt Table is empty
prompt Loading BS_T_SM_ROLERESOP...
prompt Table is empty
prompt Loading BS_T_SM_RULETPL...
prompt Table is empty
prompt Loading BS_T_SM_RULETPLPROPERTY...
prompt Table is empty
prompt Loading BS_T_SM_SEARCHCONDITION...
prompt Table is empty
prompt Loading BS_T_SM_SLADEALRECORD...
prompt Table is empty
prompt Loading BS_T_SM_SLAMAILACTION...
prompt Table is empty
prompt Loading BS_T_SM_SLARULE...
prompt Table is empty
prompt Loading BS_T_SM_SLARULEPROPERTY...
prompt Table is empty
prompt Loading BS_T_SM_SLASMACTION...
prompt Table is empty
prompt Loading BS_T_SM_SMBASEITEM...
prompt Table is empty
prompt Loading BS_T_SM_SMSBASEITEMCFG...
prompt Table is empty
prompt Loading BS_T_SM_SMSMONITOR...
prompt Table is empty
prompt Loading BS_T_SM_SMSMONITORLOG...
prompt Table is empty
prompt Loading BS_T_SM_SMSORDERDUTY...
prompt Table is empty
prompt Loading BS_T_SM_SMSORDERFORM...
prompt Table is empty
prompt Loading BS_T_SM_SMSORDERPLAN...
prompt Table is empty
prompt Loading BS_T_SM_SMSRECEIVE...
prompt Table is empty
prompt Loading BS_T_SM_SQLDATAPRIVILEGE...
prompt Table is empty
prompt Loading BS_T_SM_TABLE...
prompt Table is empty
prompt Loading BS_T_SM_USER...
prompt Table is empty
prompt Loading BS_T_SM_USERDEP...
prompt Table is empty
prompt Loading BS_T_SM_USERTEMPLATE...
prompt Table is empty
prompt Loading BS_T_SM_USERTEMPLATEDATA...
prompt Table is empty
prompt Loading BS_T_SM_USERTEMPLATESHARE...
prompt Table is empty
prompt Loading BS_T_SM_USERTEMPLATETYPE...
prompt Table is empty
prompt Loading BS_T_USLA_DUETIMERULE...
prompt Table is empty
prompt Loading BS_T_USLA_EVENTACTION...
prompt Table is empty
prompt Loading BS_T_USLA_EVENTPARAM...
prompt Table is empty
prompt Loading BS_T_USLA_EVENTRULE...
prompt Table is empty
prompt Loading BS_T_USLA_EVENTTEMP...
prompt Table is empty
prompt Loading BS_T_USLA_EVTHDERCOMP...
prompt Table is empty
prompt Loading BS_T_USLA_SLADEFINE...
prompt Table is empty
prompt Loading BS_T_WF_AGENT...
prompt Table is empty
prompt Loading BS_T_WF_ATTACHMENT...
prompt Table is empty
prompt Loading BS_T_WF_BASEOWNFIELDS...
prompt Table is empty
prompt Loading BS_T_WF_CHILDROLE...
prompt Table is empty
prompt Loading BS_T_WF_CURRENTSTEPS...
prompt Table is empty
prompt Loading BS_T_WF_CURRENTTASK...
prompt Table is empty
prompt Loading BS_T_WF_DEALGROUP...
prompt Table is empty
prompt Loading BS_T_WF_DEALPROCESS...
prompt Table is empty
prompt Loading BS_T_WF_DEALPROCESSLOG...
prompt Table is empty
prompt Loading BS_T_WF_DIMENSIONS...
prompt Table is empty
prompt Loading BS_T_WF_EMAIL_NOTICE...
prompt Table is empty
prompt Loading BS_T_WF_ENTRY...
prompt Table is empty
prompt Loading BS_T_WF_EVENTDEF...
prompt Table is empty
prompt Loading BS_T_WF_EVENTINSTANCE...
prompt Table is empty
prompt Loading BS_T_WF_HISTORYSTEPS...
prompt Table is empty
prompt Loading BS_T_WF_HISTORYTASK...
prompt Table is empty
prompt Loading BS_T_WF_INTERFACE...
prompt Table is empty
prompt Loading BS_T_WF_MODEL...
prompt Table is empty
prompt Loading BS_T_WF_MODEL_PROPERTIES...
prompt Table is empty
prompt Loading BS_T_WF_NOTICE...
prompt Table is empty
prompt Loading BS_T_WF_RELATION...
prompt Table is empty
prompt Loading BS_T_WF_ROLEMATCHDIMENSION...
prompt Table is empty
prompt Loading BS_T_WF_ROLEUSER...
prompt Table is empty
prompt Loading BS_T_WF_SORT...
prompt Table is empty
prompt Loading BS_T_WF_TYPE...
prompt Table is empty
prompt Loading BS_T_WF_USERROLE...
prompt Table is empty
prompt Loading BS_T_WF_VERSION...
prompt Table is empty
prompt Loading BS_T_WF_WORKFLOWROLE...
prompt Table is empty
prompt Loading ZZTEST_DIC...
prompt Table is empty
set feedback on
set define on
prompt Done.
