alter table bs_t_wf_serverquest add forwho varchar2(50);
alter table bs_t_wf_serverquest add release_scope_text varchar2(2000);
alter table bs_t_wf_serverquest add release_scope_id varchar2(2000);
alter table bs_t_wf_serverquest add dealtimelimit2 varchar2(2000);
alter table bs_t_wf_serverquest add workbegintime varchar2(2000);
alter table bs_t_wf_serverquest add workendtime varchar2(2000);

insert into bs_t_sm_dicitem (PID, DTID, DTCODE, DINAME, DIVALUE, DICDN, DICDNS, DICFULLNAME, ISDEFAULT, STATUS, ORDERNUM, PARENTID, CREATER, CREATETIME, LASTMODIFIER, LASTMODIFYTIME, REMARK)
values ('8a9c85174a853f09014a855318920002', '8a9c85174a853f09014a8552df6c0001', 'sheetTimeout', '是', '1', '001', '001', '是', '', 1, 10, '0', '297e39d1298150e8012981703b700002', 1419576023, '297e39d1298150e8012981703b700002', 1419576023, '');

insert into bs_t_sm_dicitem (PID, DTID, DTCODE, DINAME, DIVALUE, DICDN, DICDNS, DICFULLNAME, ISDEFAULT, STATUS, ORDERNUM, PARENTID, CREATER, CREATETIME, LASTMODIFIER, LASTMODIFYTIME, REMARK)
values ('8a9c85174a853f09014a855348080003', '8a9c85174a853f09014a8552df6c0001', 'sheetTimeout', '否', '0', '002', '002', '否', '', 1, 20, '0', '297e39d1298150e8012981703b700002', 1419576035, '297e39d1298150e8012981703b700002', 1419576035, '');

insert into bs_t_sm_dictype (PID, DTNAME, DTCODE, DICTYPE, STATUS, CREATER, CREATETIME, LASTMODIFIER, LASTMODIFYTIME, REMARK)
values ('8a9c85174a853f09014a8552df6c0001', '工单超时', 'sheetTimeout', '4', 1, '297e39d1298150e8012981703b700002', 1419576008, '297e39d1298150e8012981703b700002', 1419576008, '');

insert into bs_t_sm_menutree (PID, NODENAME, NODETYPE, PARENTID, NODEURL, STATUS, NODEMARK, OPENWAY, APPLYSYSNAME, MENUICON, NODEPATH, CLASSNAME, ORDERNUM, CREATER, CREATETIME, LASTMODIFIER, LASTMODIFYTIME, REMARK)
values ('8a9c85174a74fee0014a75140a6c0001', '工单查询', 1, '402894f5295d39ec01295e5724bf0002', '', 1, 'sheetQuery', '1', '', '', '当前位置：工单查询', '', 50, '297e39d1298150e8012981703b700002', 1419303455, '297e39d1298150e8012981703b700002', 1419303455, '');

insert into bs_t_sm_menutree (PID, NODENAME, NODETYPE, PARENTID, NODEURL, STATUS, NODEMARK, OPENWAY, APPLYSYSNAME, MENUICON, NODEPATH, CLASSNAME, ORDERNUM, CREATER, CREATETIME, LASTMODIFIER, LASTMODIFYTIME, REMARK)
values ('8a9c85174a74fee0014a751770f50003', '事件工单', 1, '8a9c85174a74fee0014a75140a6c0001', 'sheet/sheetQuery.action?baseSchema=CBD_INCIDENT&customizedPage=sqIncident', 1, 'incident', '1', '', '', '当前位置：工单查询>>事件工单', '', 20, '297e39d1298150e8012981703b700002', 1419303678, '297e39d1298150e8012981703b700002', 1419416831, '');

insert into bs_t_sm_menutree (PID, NODENAME, NODETYPE, PARENTID, NODEURL, STATUS, NODEMARK, OPENWAY, APPLYSYSNAME, MENUICON, NODEPATH, CLASSNAME, ORDERNUM, CREATER, CREATETIME, LASTMODIFIER, LASTMODIFYTIME, REMARK)
values ('8a9c85174a74fee0014a75151f090002', '服务请求', 1, '8a9c85174a74fee0014a75140a6c0001', 'sheet/sheetQuery.action?baseSchema=CDB_SERVICEREQUEST&customizedPage=sqServerQuest', 1, 'serverquest', '1', '', '', '当前位置：工单查询>>服务请求', '', 10, '297e39d1298150e8012981703b700002', 1419303526, '297e39d1298150e8012981703b700002', 1419313830, '');

create or replace function Get_StrArrayStrOfIndex
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

CREATE OR REPLACE FUNCTION "DATE_TO_SEC" (strDate IN CHAR)
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