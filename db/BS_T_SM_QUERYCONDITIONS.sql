create table BS_T_SM_QUERYCONDITIONS
(
  pid              VARCHAR2(255) not null primary key,
  user_id         VARCHAR2(255) not null,
  condition_info   VARCHAR2(2000) not null,
  ticket_type        VARCHAR2(100) not null
)