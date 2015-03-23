---------------------------------------------
--国家开发银行数据库函数
---------------------------------------------
CREATE OR REPLACE TYPE ULTRABPP.en_concat_im
AUTHID CURRENT_USER AS OBJECT
(
  CURR_STR VARCHAR2(32767),
  STATIC FUNCTION ODCIAGGREGATEINITIALIZE(SCTX IN OUT en_concat_im) RETURN NUMBER,
  MEMBER FUNCTION ODCIAGGREGATEITERATE(SELF IN OUT en_concat_im,
       P1 IN VARCHAR2) RETURN NUMBER,
  MEMBER FUNCTION ODCIAGGREGATETERMINATE(SELF IN en_concat_im,
             RETURNVALUE OUT VARCHAR2,
             FLAGS IN NUMBER)
       RETURN NUMBER,
  MEMBER FUNCTION ODCIAGGREGATEMERGE(SELF IN OUT en_concat_im,
      SCTX2 IN  en_concat_im) RETURN NUMBER
)

CREATE OR REPLACE FUNCTION ULTRABPP."DATE_TO_SEC" (strDate IN CHAR)
--将日期型转换为整型秒
        RETURN INT
AS
        ret NUMBER;
        iDate DATE;
        tDate varchar2(30);
BEGIN

        if(length(strDate))>=18 then
         tDate:='YYYY-MM-DD HH24:MI:SS';
        elsif (length(strDate))>7 then
         tDate:='YYYY-MM-DD';
        else
         tDate:='YYYY-MM';
        end if;
        iDate:=TO_DATE(strDate,tDate);
        ret := (iDate-TO_DATE('1970-1-1 8:0:0','YYYY-MM-DD HH24:MI:SS'))*(24*60*60);
    RETURN ret;
END ;

CREATE OR REPLACE FUNCTION ULTRABPP.en_concat(P1 VARCHAR2) RETURN VARCHAR2 AGGREGATE USING en_concat_im ;

create or replace function ultrabpp.Get_StrArrayLength
(
  av_str varchar2,  --要分割的字符串
  av_split varchar2  --分隔符号
)
return number
is
  lv_str varchar2(1000);
  lv_length number;
begin
  lv_str:=ltrim(rtrim(av_str));
  lv_length:=0;
  while instr(lv_str,av_split)<>0 loop
     lv_length:=lv_length+1;
     lv_str:=substr(lv_str,instr(lv_str,av_split)+length(av_split),length(lv_str));
  end loop;
  lv_length:=lv_length+1;
  return lv_length;
end Get_StrArrayLength;

create or replace function ultrabpp.Get_StrArrayStrOfIndex
(
  av_str varchar2,  --要分割的字符串
  av_split varchar2,  --分隔符号
  av_index number --取第几个元素
)
return varchar2
is
  lv_str varchar2(1024);
  lv_strOfIndex varchar2(1024);
  lv_length number;
begin
  lv_str:=ltrim(rtrim(av_str));
  lv_str:=concat(lv_str,av_split);
  lv_length:=av_index;
  if lv_length=0 then
      lv_strOfIndex:=substr(lv_str,1,instr(lv_str,av_split)-length(av_split));
  else
      lv_length:=av_index+1;
      lv_strOfIndex:=substr(lv_str,instr(lv_str,av_split,1,av_index)+length(av_split),instr(lv_str,av_split,1,lv_length)-instr(lv_str,av_split,1,av_index)-length(av_split));
  end if;
  return  lv_strOfIndex;
end Get_StrArrayStrOfIndex;

CREATE OR REPLACE FUNCTION ULTRABPP."SEC_TO_DATE" (intDate IN NUMBER)
--将整型秒转换为日期型
RETURN DATE
AS
ret DATE;
BEGIN
ret := TO_DATE('1970-1-1 8:0:0','YYYY-MM-DD HH:MI:SS') + intDate/(24*60*60);
RETURN ret;
END sec_to_date;

CREATE OR REPLACE TYPE BODY ULTRABPP.en_concat_im
IS
  STATIC FUNCTION ODCIAGGREGATEINITIALIZE(SCTX IN OUT en_concat_im)
  RETURN NUMBER
  IS
  BEGIN
  SCTX := en_concat_im(NULL) ;
  RETURN ODCICONST.SUCCESS;
  END;
  MEMBER FUNCTION ODCIAGGREGATEITERATE(SELF IN OUT en_concat_im,
        P1 IN VARCHAR2)
  RETURN NUMBER
  IS
  BEGIN
  IF(CURR_STR IS NOT NULL) THEN
    CURR_STR := CURR_STR || ';' || P1;
  ELSE
    CURR_STR := P1;
  END IF;
  RETURN ODCICONST.SUCCESS;
  END;
  MEMBER FUNCTION ODCIAGGREGATETERMINATE(SELF IN en_concat_im,
             RETURNVALUE OUT VARCHAR2,
             FLAGS IN NUMBER)
  RETURN NUMBER
  IS
  BEGIN
  RETURNVALUE := CURR_STR ;
  RETURN ODCICONST.SUCCESS;
  END;
  MEMBER FUNCTION ODCIAGGREGATEMERGE(SELF IN OUT en_concat_im,
          SCTX2 IN en_concat_im)
  RETURN NUMBER
  IS
  BEGIN
  IF(SCTX2.CURR_STR IS NOT NULL) THEN
    SELF.CURR_STR := SELF.CURR_STR || ';' || SCTX2.CURR_STR ;
  END IF;
  RETURN ODCICONST.SUCCESS;
  END;
END;

CREATE OR REPLACE FUNCTION "GET_PER_DEPTNAME" (loginnameP NVARCHAR2)
--根据人员的登陆名查看人员部门
 return  nvarchar2
as
 ret nvarchar2(2000);

begin
  select substr(depfullname,instr(depfullname,'.',1,2)+1,length(depfullname)) into ret from bs_t_sm_dep z where z.pid =(select depid from bs_t_sm_user y where y.loginname = loginnameP);
return ret;
end;

CREATE OR REPLACE FUNCTION "GET_SOLVER" (baseidP NVARCHAR2)
--根据工单baseid获得工单的解决人,针对已经关闭的单子(点击了处理完成)
 return  nvarchar2
as
 ret nvarchar2(2000);

begin
  select q.currentuser into ret from bs_t_wf_record q  where baseid = baseidP and  q.dealaction = '处理完成';
return ret;
end;

CREATE OR REPLACE FUNCTION "GET_SOLVERID" (baseidP NVARCHAR2)
--根据工单baseid获得工单的解决人ID,针对已经关闭的单子(点击了处理完成)
 return  nvarchar2
as
 ret nvarchar2(2000);

begin
   select (case when closeSheet>0 then (select currentuserloginname from (select q.currentuserloginname from bs_t_wf_record q where baseid = baseidP and  q.dealaction = '处理完成' order by q.dealtime desc ) t where rownum = 1)
              when closeSheet=0 then (select x.basecreatorloginname from bs_t_bpp_baseinfor x where x.baseid= baseidP)
               end) into ret  from (
               select  count(*) closeSheet   from bs_t_wf_record q  where baseid = baseidP and  q.dealaction = '处理完成'
               );
return ret;
end;



CREATE OR REPLACE FUNCTION "GET_SOLVER" (baseidP NVARCHAR2)
--根据工单baseid获得工单的解决人,针对已经关闭的单子(点击了处理完成)
return  nvarchar2
as
ret nvarchar2(2000);
begin
select (case when closeSheet>0 then (select currentuser from (select q.currentuser from bs_t_wf_record q where baseid = baseidP and  q.dealaction = '处理完成' order by q.dealtime desc ) t where rownum = 1)
when closeSheet=0 then (select x.basecreatorfullname from bs_t_bpp_baseinfor x where x.baseid= baseidP)
end) into ret  from (
select  count(*) closeSheet   from bs_t_wf_record q  where baseid = baseidP and  q.dealaction = '处理完成'
);   
return ret;
end ;

CREATE OR REPLACE FUNCTION "GET_ASSIGNEEID" (baseidP NVARCHAR2)
--根据工单baseid获得工单的当前处理人,针对非关闭的单子
 return  nvarchar2
as
 ret nvarchar2(2000);

begin
  select t.assignee into ret from BS_T_WF_CURRENTTASK t where t.baseid = baseidP;
return ret;
end;

CREATE OR REPLACE FUNCTION "GET_ASSIGNEE" (baseidP NVARCHAR2)
--根据工单baseid获得工单的当前处理人,针对非关闭的单子
 return  nvarchar2
as
 ret nvarchar2(2000);

begin
  select t.Assignee into ret from BS_T_WF_CURRENTTASK t where t.baseid = baseidP;
return ret;
end;

