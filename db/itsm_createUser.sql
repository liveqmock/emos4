--建立pasm表空间
--itsm_default

CREATE TABLESPACE itsm_default
DATAFILE  '/itsm_default.dbf' SIZE 1024 M 
AUTOEXTEND ON 
EXTENT MANAGEMENT LOCAL UNIFORM SIZE 1 M;

--建立pasm用户
--add user: pasm/pasm

CREATE USER cdb_itsm
  IDENTIFIED BY cdb_itsm
  DEFAULT TABLESPACE itsm_default
  TEMPORARY TABLESPACE temp
  PROFILE DEFAULT
  ACCOUNT UNLOCK;
  
  GRANT DBA TO cdb_itsm;
  GRANT RESOURCE TO cdb_itsm;
  ALTER USER cdb_itsm DEFAULT ROLE ALL;
  GRANT SELECT ANY TABLE TO cdb_itsm;
  GRANT UNLIMITED TABLESPACE TO cdb_itsm;
  GRANT CREATE ANY TABLE to cdb_itsm;
