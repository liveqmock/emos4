create table BS_T_SM_BATCHCONF
(
  pid              VARCHAR2(255) not null primary key,
  batch_no         VARCHAR2(255) not null,
  chg_start_time   VARCHAR2(255) not null,
  chg_end_time     VARCHAR2(255) not null,
  reviewtime       VARCHAR2(255) not null,
  latestreviewtime VARCHAR2(255) not null,
  exec_flag        VARCHAR2(10) not null
)