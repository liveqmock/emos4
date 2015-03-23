create table BS_T_SHEET_TAG
(
  ID          VARCHAR2(50) not null,
  NAME        VARCHAR2(50),
  taglevel          NUMBER(10),
  PARENTID VARCHAR2(50),
  primary key (ID)
);
create table BS_T_SHEET_TYPE
(
 ID          VARCHAR2(50) not null,
  NAME        VARCHAR2(50),
  baseschema        VARCHAR2(50),
  url        VARCHAR2(100),
  primary key (ID)
);
create table BS_T_SHEET_TAG_TYPE_LINK
(
  sheettagid          VARCHAR2(50) not null,
  sheettypeid          VARCHAR2(50) not null,
  primary key (sheettagid,sheettypeid)
);
