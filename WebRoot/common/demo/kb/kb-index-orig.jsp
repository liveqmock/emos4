<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/core/jspbase.jsp"%>
<!doctype html>
<html>
<head>
<%@ include file="/common/core/meta.jsp"%>
<title>Knowledge Base Demo</title>
<%@ include file="/common/core/cssbase.jsp"%>
<style>
body { /*background:#f5fffa;*/background:#fff;color:#555;}
a { color:#336699}
.w90p #header,
.w90p #content,
.w90p #footer { width: 90%;margin-left:30px;}
#header { padding:10px}
#header h2 { font-size:14px; color:#555;font-weight:bold;}

#tabs { position:relative;margin:30px 0 60px;width:600px;}
.ks-switchable-nav { left: 5px; margin-top: -29px; position: absolute; z-index: 99;}
.ks-switchable-nav li,
.pagemap-title { background: url(tabs-sprite.gif) no-repeat scroll 0 6px;cursor: pointer;float: left;height: 27px;line-height: 21px;margin-right: 3px;padding-top: 8px;text-align: center; width: 130px;}
.ks-switchable-nav li.ks-active,
.pagemap-title { background-position: 0 -40px; cursor: default;}
.ks-switchable-content { border-top: 1px solid #AEC7E5;height: 140px;padding: 10px;position: relative;}
.news { width:100%;}
.news td { border-bottom: 1px dashed #DDDDDD;padding: 3px 3px 3px 0;}

.pagemap { border-top: 1px solid #AEC7E5; padding:10px;width:600px;position: relative;}
.pagemap-title { position: absolute;margin-top:-40px}
.pagemap-item { margin:10px 0; clear:both;overflow:hidden;}
.pagemap-item dt { font-weight:bold}
.pagemap-item em { float:left;background:#FFFAAE;border:1px solid #ddd;margin:3px;padding:3px;}
.pagemap-item a { padding:3px;white-space:nowrap;color:#666}
</style>
<%@ include file="/common/core/jsbase.jsp"%>
</head>
<body class="w90p">
<div id="header"><h2>知识库首页</h2></div>
<div id="content">
	<div class="col-main">
		<div class="main-wrap">
			<div id="tabs" class="section">
				<ul class="ks-switchable-nav">
					<li class="ks-active">知识动态</li>
					<li>我的收藏</li>
					<li>我的订阅</li>
					<li>我的知识</li>
				</ul>
				<div class="ks-switchable-content">
					<div>
						<table class="news">
							<tr>
								<td>[省公司]刘成 提交了一条新知识 : "飞信号定义及注意事项"</td>
								<td>于 1小时前</td>
							</tr>
							<tr>
								<td>[网络部]admin 提交了一条新知识 : "移动打长途优惠方案"</td>
								<td>于 3小时前</td>
							</tr>
							<tr>
								<td>[无锡移动]李斌 审核通过了您的知识 : "变更套餐及转网用户积分策略分析"</td>
								<td>于 5小时前</td>
							</tr>
							<tr>
								<td>[省公司]苏建军 审核通过了您的知识 : "手机如何查询品牌功能"</td>
								<td>于 2010-10-23</td>
							</tr>
							<tr>
								<td>[省公司]刘成 提交了一条新知识 : "2011年业务平台部门KPI考核模板-草稿"</td>
								<td>于 2010-10-25</td>
							</tr>
						</table>
					</div>
					<div style="display: none">
						<table class="news">
							<tr>
								<td>标题</td>
								<td>分类</td>
								<td>作者</td>
								<td>时间</td>
							</tr>
							<tr>
								<td>飞信号定义及注意事项</td>
								<td>新业务</td>
								<td>吴长江</td>
								<td>2010-04-10</td>
							</tr>
							<tr>
								<td>移动打长途优惠方案</td>
								<td>移动</td>
								<td>admin</td>
								<td>2010-12-10</td>
							</tr>
						</table></div>
					<div style="display: none">
						<table class="news">
							<tr>
								<td>标题</td>
								<td>分类</td>
								<td>作者</td>
								<td>时间</td>
							</tr>
							<tr>
								<td>飞信号定义及注意事项</td>
								<td>新业务</td>
								<td>吴长江</td>
								<td>2010-04-10</td>
							</tr>
							<tr>
								<td>移动打长途优惠方案</td>
								<td>移动</td>
								<td>admin</td>
								<td>2010-12-10</td>
							</tr>
							<tr>
								<td>变更套餐及转网用户积分策略分析</td>
								<td>业务平台</td>
								<td>刘新</td>
								<td>2010-01-02</td>
							</tr>
							<tr>
								<td>手机如何查询品牌功能</td>
								<td>业务平台</td>
								<td>刘新</td>
								<td>2010-01-02</td>
							</tr>
						</table></div>
					<div style="display: none">
						<table class="news">
							<tr>
								<td>标题</td>
								<td>分类</td>
								<td>作者</td>
								<td>时间</td>
							</tr>
							<tr>
								<td>飞信号定义及注意事项</td>
								<td>新业务</td>
								<td>吴长江</td>
								<td>2010-04-10</td>
							</tr>
							<tr>
								<td>移动打长途优惠方案</td>
								<td>移动</td>
								<td>admin</td>
								<td>2010-12-10</td>
							</tr>
							<tr>
								<td>变更套餐及转网用户积分策略分析</td>
								<td>业务平台</td>
								<td>刘新</td>
								<td>2010-01-02</td>
							</tr>
						</table></div>
				</div>
			</div>
			
			<div class="pagemap">
				<div class="pagemap-title">知识地图</div>
				<dl class="pagemap-item">
					<dt>专业类别</dt>
					<dd>
						<em><a href="#">无线</a></em>
						<em><a href="#">数据网</a></em>
						<em><a href="#">传输网</a></em>
						<em><a href="#">网管支撑</a></em>
						<em><a href="#">交换网</a></em>
						<em><a href="#">业务平台</a></em>
						<em><a href="#">动力</a></em>
						<em><a href="#">其他</a></em>
					</dd>
				</dl>
				<dl class="pagemap-item">
					<dt>时间维度</dt>
					<dd>
						<em><a href="#">一季度</a></em>
						<em><a href="#">二季度</a></em>
						<em><a href="#">三季度</a></em>
						<em><a href="#">四季度</a></em>
					</dd>
				</dl>
				<dl class="pagemap-item">
					<dt>所属组织</dt>
					<dd>
						<em><a href="#">集团运维管理处</a></em>
						<em><a href="#">集团通信组织处</a></em>
						<em><a href="#">集团应急通信处</a></em>
						<em><a href="#">集团网络安全处</a></em>
						<em><a href="#">集团互联互通处</a></em>
						<em><a href="#">集团工程建设处</a></em>
						<em><a href="#">集团网络监控室</a></em>
						<em><a href="#">集团网管支撑室</a></em>
						<em><a href="#">集团技术支援室</a></em>
						<em><a href="#">集团维护优化室</a></em>
						<em><a href="#">省网络部门</a></em>
						<em><a href="#">内部其他部门</a></em>
						<em><a href="#">外联合作伙伴</a></em>
					</dd>
				</dl>
			</div>
		</div>
	</div>
</div>
<div id="footer"></div>
<script>
KISSY.use('switchable', function(S){
	var tabs = new S.Tabs('#tabs');
});
</script>
</body>
</html>