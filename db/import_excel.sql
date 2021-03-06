drop table BS_T_SM_IMPORT_INCIDENT;
-- Create table
create table BS_T_SM_IMPORT_INCIDENT
(
  baseid				  VARCHAR2(50),
  basestatus              VARCHAR2(255),
  basecreatedate          VARCHAR2(128),
  baseclosedate           VARCHAR2(128),
  basecreatorfullname     VARCHAR2(255),
  basecreatorloginname    VARCHAR2(255),
  contactperson           VARCHAR2(99),
  contactoa               VARCHAR2(128),
  contactstation          VARCHAR2(128),
  contacttel              VARCHAR2(128),
  icidentsource           VARCHAR2(128),
  basesummary             VARCHAR2(255),
  incidentdes             VARCHAR2(4000),
  occurrencetime          VARCHAR2(128),
  incident_urgentdgree    VARCHAR2(128),
  incident_effectdgree    VARCHAR2(128),
  priority                VARCHAR2(128),
  incidentphenoclass1      VARCHAR2(128),
  incidentphenoclass2      VARCHAR2(128),
  incidentphenoclass3      VARCHAR2(128),
  incidentidentification  VARCHAR2(20),
  basefinishdate          VARCHAR2(128),
  incidentpropertie       VARCHAR2(128),
  incidentsourceclass1     VARCHAR2(128),
  incidentsourceclass2     VARCHAR2(128),
  incidentsourceclass3     VARCHAR2(128),
  incidentsourceclass4     VARCHAR2(128),
  recoverytime            VARCHAR2(128),
  serviceinterruptiontime VARCHAR2(128),
  incidentsolutiontype    VARCHAR2(128),
  incidentreason          VARCHAR2(4000),
  procedureprocessing     VARCHAR2(4000),
  solution                VARCHAR2(4000),
  closecode               VARCHAR2(100),
  resolveuser     			VARCHAR2(255),
  resolveuseroa   		 VARCHAR2(255)
);
drop table BS_T_SM_IMPORT_SERVICEQUEST;
-- Create table
create table BS_T_SM_IMPORT_SERVICEQUEST
(
  baseid				     VARCHAR2(50),
  basestatus                 VARCHAR2(255),
  basecreatedate             VARCHAR2(100),
  baseclosedate              VARCHAR2(100),
  basecreatorfullname        VARCHAR2(255),
  basecreatorloginname       VARCHAR2(255),
  requestuser                VARCHAR2(100),
  requestuseroa              VARCHAR2(100),
  requestusersite            VARCHAR2(100),
  requestuserphone           VARCHAR2(100),
  dataresource               VARCHAR2(100),
  basesummary                VARCHAR2(1000),
  requestdesc                VARCHAR2(4000),
  requiredealtime            VARCHAR2(100),
  service_category1           VARCHAR2(100),
  service_category2           VARCHAR2(100),
  service_category3           VARCHAR2(100),
  basefinishdate             VARCHAR2(100),
  deal_process_solution      VARCHAR2(1000),
  close_code                 VARCHAR2(50),
  dealuser        			VARCHAR2(255),
  dealuseroa       			VARCHAR2(255)
);