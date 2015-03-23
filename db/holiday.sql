drop table BS_T_SM_HOLIDAY;
create table BS_T_SM_HOLIDAY
(
  PID         VARCHAR2(32) not null primary key,
  DATEINFO    VARCHAR2(10),
  HOLIDAYNAME VARCHAR2(50),
  YEARS       INTEGER,
  MONTHS      INTEGER,
  DAYS        INTEGER,
  NOTE        VARCHAR2(500),
  HIDEFLAG    INTEGER,
  HOLIDAYFLAG INTEGER,
  DATETYPE    VARCHAR2(10)
);
-- Add comments to the columns 
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
comment on column BS_T_SM_HOLIDAY.DATETYPE
  is '日期类型：特殊日期、非变更日';

  
  
  
insert into BS_T_SM_DICITEM (PID, DTID, DTCODE, DINAME, DIVALUE, DICDN, DICDNS, DICFULLNAME, ISDEFAULT, STATUS, ORDERNUM, PARENTID, CREATER, CREATETIME, LASTMODIFIER, LASTMODIFYTIME, REMARK)
values ('8a81f1db3a970367013a9ac871c30008', '8a8171d23a781eea013a96d388c5004a', 'datetype', ' ', '0', '003', '003', ' ', null, 1, 0, '0', null, null, '3fceb719ae694b84b3642690e3d42f37', 1351230481, '特殊日期/非变更日');
insert into BS_T_SM_DICITEM (PID, DTID, DTCODE, DINAME, DIVALUE, DICDN, DICDNS, DICFULLNAME, ISDEFAULT, STATUS, ORDERNUM, PARENTID, CREATER, CREATETIME, LASTMODIFIER, LASTMODIFYTIME, REMARK)
values ('8a8171d23a781eea013a96d3e30a004b', '8a8171d23a781eea013a96d388c5004a', 'datetype', '特殊日期', '1', '001', '001', '特殊日期', null, 1, 1, '0', null, null, '3fceb719ae694b84b3642690e3d42f37', 1351230477, '特殊日期/非变更日');
insert into BS_T_SM_DICITEM (PID, DTID, DTCODE, DINAME, DIVALUE, DICDN, DICDNS, DICFULLNAME, ISDEFAULT, STATUS, ORDERNUM, PARENTID, CREATER, CREATETIME, LASTMODIFIER, LASTMODIFYTIME, REMARK)
values ('8a8171d23a781eea013a96d4214c004c', '8a8171d23a781eea013a96d388c5004a', 'datetype', '非变更日', '2', '002', '002', '非变更日', null, 1, 2, '0', null, null, '3fceb719ae694b84b3642690e3d42f37', 1351230459, '特殊日期/非变更日');
insert into BS_T_SM_DICTYPE (PID, DTNAME, DTCODE, DICTYPE, STATUS, CREATER, CREATETIME, LASTMODIFIER, LASTMODIFYTIME, REMARK)
values ('8a8171d23a781eea013a96d388c5004a', '日期类型', 'datetype', '1', 1, null, null, '3fceb719ae694b84b3642690e3d42f37', 1351230488, '特殊日期/非变更日');
commit;