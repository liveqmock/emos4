<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/core/jspbase.jsp"%>
<!doctype html>
<html>
<head>
<%@ include file="/common/core/meta.jsp"%>
<title>Knowledge Base Index</title>
<%@ include file="/common/core/cssbase.jsp"%>
<style>
html, body {overflow:hidden;}
a { color:#336699}
#header { width:100%;}
#content { position: relative;width:100%;overflow:auto;}
.main-wrap { margin:0 20px;width:900px;}
#header h2 { font-size:12px; color:#555;font-weight:normal;padding:2px 10px;*padding-top:4px;}

.ks-switchable-content { width:600px;}
.news { width:100%;}
.news td { border-bottom: 1px dashed #DDDDDD;padding: 3px 3px 3px 0;}
.recent { border-top: 1px solid #AEC7E5;height: 140px;padding: 10px 0;position: absolute;width:240px; right:0; top:0;}
.recent h3 { font-weight:normal;margin-top:-33px;position:absolute;}
	
.pagemap { border-top: 1px solid #AEC7E5; padding:10px 0 10px 10px;position: relative;width:100%}
.pagemap .ks-switchable-nav { margin-top:-40px;}
.pagemap h3 { background:#fefefe;}
.inner-col { float:left;overflow:hidden;width:280px;padding:0 14px 0 0;}
.pagemap-item { margin:10px 0; clear:both;border:1px solid #eee;background:#f3f3f3;}
.pagemap-item h4 { font-weight:normal;float:left;width:20px;*height:110px;line-height: 15px;color:#666;text-align:center;padding-top:3px}
.pagemap-item em { float:left;background:#FFFAAE;border:1px solid #ddd;margin:3px 3px 0 0;padding:2px 3px;}
.pagemap-item .info-list { margin: 0 0 0 20px;padding:0 6px;background:#fff;}
.pagemap-item .info-list li { padding:2px 0;}
.pagemap-item .word-list li { float:left;}
.pagemap-item a { padding:3px;white-space:nowrap;color:#666}
</style>
<%@ include file="/common/core/jsbase.jsp"%>
</head>
<body class="fit">
<div id="header" class="topbar"><h2>知识库首页</h2></div>
<div id="content">
		<div class="main-wrap">
			
			<div id="tabs" class="tabs">
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
				<div class="recent">
					<h3>最近浏览</h3>
						<table class="news">
							<tr>
								<td>[省公司]刘成 "飞信号定义及注意事项"</td>
							</tr>
							<tr>
								<td>[网络部]admin "移动打长途优惠方案"</td>
							</tr>
							<tr>
								<td>[无锡移动]李斌 "变更套餐及转网用户积分策</td>
							</tr>
							<tr>
								<td>[省公司]苏建军 "手机如何查询品牌功能"</td>
							</tr>
							<tr>
								<td>[省公司]刘成 "2011年业务平台部门KPI考核</td>
							</tr>
						</table>
					</div>
			</div>
			
			<div class="pagemap">
				<ul class="ks-switchable-nav">
					<li class="ks-active">故障</li>
					<li>投诉</li>
				</ul>
				<!-- 第1列 -->
				<div class="inner-col">
					<h3>专业分类</h3>
					<div class="pagemap-item">
						<h4>无线</h4>
						<ul class="info-list">
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li class="ks-clear">
								<ul class="word-list">
									<li><em><a href="#">TD无线网</a></em></li>
									<li><em><a href="#">GSM无线网</a></em></li>
									<li><em><a href="#">其他</a></em></li>
								</ul>
							</li>
						</ul>
						
					</div>
					<div class="pagemap-item">
						<h4>数据网</h4>
						<ul class="info-list">
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li class="ks-clear">
						<ul class="word-list">
							<li><em><a href="#">GPRS</a></em></li>
							<li><em><a href="#">CMNET骨干网</a></em></li>
							<li><em><a href="#">CMNET省网</a></em></li>
							<li><em><a href="#">CMNET城域网</a></em></li>
							
							<li><em><a href="#">IP承载网</a></em></li>
							<li><em><a href="#">IP承载网CE</a></em></li>
							<li><em><a href="#">WLAN网络</a></em></li>
							<li><em><a href="#">MDCN</a></em></li>
							<li><em><a href="#">时间同步网</a></em></li>
								
						</ul>
							</li>
						</ul>
					</div>
					<div class="pagemap-item">
						<h4>传输网</h4>
						<ul class="info-list">
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li class="ks-clear">
						<ul class="word-list">
							<li><em><a href="#">一干传输设备</a></em></li>
							<li><em><a href="#">一干传输线路</a></em></li>
							<li><em><a href="#">二干传输电路</a></em></li>
							<li><em><a href="#">本地骨干层传输设备</a></em></li>
							<li><em><a href="#">本地接入层传输设备</a></em></li>
							<li><em><a href="#">本地汇聚层传输设备</a></em></li>
							<li><em><a href="#">本地传输线路</a></em></li>
						</ul></li>
						</ul>
					</div>
					<div class="pagemap-item">
						<h4>网管支撑</h4>
						<ul class="info-list">
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li class="ks-clear">
						<ul class="word-list">
							<li><em><a href="#">总部网管系统</a></em></li>
							<li><em><a href="#">省网管系统</a></em></li>
							<li><em><a href="#">其他</a></em></li>
						</ul></li>
						</ul>
					</div>
					<div class="pagemap-item">
						<h4>交换网</h4>
						<ul class="info-list">
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li class="ks-clear">
						<ul class="word-list">
							<li><em><a href="#">国际交换网</a></em></li>
							<li><em><a href="#">国际信令网</a></em></li>
							<li><em><a href="#">省际信令网</a></em></li>
							<li><em><a href="#">TDM省际汇接网</a></em></li>
							<li><em><a href="#">TDM省内汇接网</a></em></li>
							<li><em><a href="#">软交换汇接网</a></em></li>
							<li><em><a href="#">智能网</a></em></li>
							<li><em><a href="#">本地网</a></em></li>
						</ul></li>
						</ul>
					</div>
					<div class="pagemap-item">
						<h4>业务平台</h4>
						<ul class="info-list">
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li class="ks-clear">
						<ul class="word-list">
							<li><em><a href="#">彩铃平台</a></em></li>
							<li><em><a href="#">视频会议</a></em></li>
							<li><em><a href="#">彩信网关</a></em></li>
							<li><em><a href="#">语音短信平台</a></em></li>
							<li><em><a href="#">EMAIL系统</a></em></li>
							<li><em><a href="#">通用下载平台</a></em></li>
							<li><em><a href="#">WLAN认证平台</a></em></li>
							<li><em><a href="#">其他</a></em></li>
						</ul></li>
						</ul>
					</div>
					<div class="pagemap-item">
						<h4>动力环境</h4>
						<ul class="info-list">
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li class="ks-clear">
						<ul class="word-list">
							<li><em><a href="#">基站动环设备</a></em></li>
							<li><em><a href="#">干线枢纽动环设备</a></em></li>
							<li><em><a href="#">应急传输设备</a></em></li>
						</ul></li>
						</ul>
					</div>				
				</div>
				
				<!-- 第2列 -->
				<div class="inner-col">
					<h3>集团</h3>
					<div class="pagemap-item">
						<h4>运维管理处</h4>
						<ul class="info-list">
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li class="ks-clear">
						<ul class="word-list">
							<li><em><a href="#">TD无线网</a></em></li>
							<li><em><a href="#">GSM无线网</a></em></li>
							<li><em><a href="#">其他</a></em></li>
						</ul>
							</li>
						</ul>
					</div>
					<div class="pagemap-item">
						<h4>通信组织处</h4>
						<ul class="info-list">
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li class="ks-clear">
						<ul class="word-list">
							<li><em><a href="#">TD无线网</a></em></li>
							<li><em><a href="#">GSM无线网</a></em></li>
							<li><em><a href="#">其他</a></em></li>
						</ul></li>
						</ul>
					</div>
					<div class="pagemap-item">
						<h4>应急通信处</h4>
						<ul class="info-list">
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li class="ks-clear">
						<ul class="word-list">
							<li><em><a href="#">TD无线网</a></em></li>
							<li><em><a href="#">GSM无线网</a></em></li>
							<li><em><a href="#">其他</a></em></li>
						</ul></li>
						</ul>
					</div>
					<div class="pagemap-item">
						<h4>网络安全处</h4>
						<ul class="info-list">
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li class="ks-clear">
						<ul class="word-list">
							<li><em><a href="#">TD无线网</a></em></li>
							<li><em><a href="#">GSM无线网</a></em></li>
							<li><em><a href="#">其他</a></em></li>
						</ul></li>
						</ul>
					</div>
					<div class="pagemap-item">
						<h4>互联互通处</h4>
						<ul class="info-list">
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li class="ks-clear">
						<ul class="word-list">
							<li><em><a href="#">TD无线网</a></em></li>
							<li><em><a href="#">GSM无线网</a></em></li>
							<li><em><a href="#">其他</a></em></li>
						</ul></li>
						</ul>
					</div>
					<div class="pagemap-item">
						<h4>工程建设处</h4>
						<ul class="info-list">
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li class="ks-clear">
						<ul class="word-list">
							<li><em><a href="#">TD无线网</a></em></li>
							<li><em><a href="#">GSM无线网</a></em></li>
							<li><em><a href="#">其他</a></em></li>
						</ul></li>
						</ul>
					</div>
					<div class="pagemap-item">
						<h4>网络监控室</h4>
						<ul class="info-list">
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li class="ks-clear">
						<ul class="word-list">
							<li><em><a href="#">TD无线网</a></em></li>
							<li><em><a href="#">GSM无线网</a></em></li>
							<li><em><a href="#">其他</a></em></li>
						</ul></li>
						</ul>
					</div>
					<div class="pagemap-item">
						<h4>网管支撑室</h4>
						<ul class="info-list">
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li class="ks-clear">
						<ul class="word-list">
							<li><em><a href="#">TD无线网</a></em></li>
							<li><em><a href="#">GSM无线网</a></em></li>
							<li><em><a href="#">其他</a></em></li>
						</ul></li>
						</ul>
					</div>
					<div class="pagemap-item">
						<h4>技术支援室</h4>
						<ul class="info-list">
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li class="ks-clear">
						<ul class="word-list">
							<li><em><a href="#">TD无线网</a></em></li>
							<li><em><a href="#">GSM无线网</a></em></li>
							<li><em><a href="#">其他</a></em></li>
						</ul></li>
						</ul>
					</div>
					<div class="pagemap-item">
						<h4>维护优化室</h4>
						<ul class="info-list">
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li class="ks-clear">
						<ul class="word-list">
							<li><em><a href="#">TD无线网</a></em></li>
							<li><em><a href="#">GSM无线网</a></em></li>
							<li><em><a href="#">其他</a></em></li>
						</ul></li>
						</ul>
					</div>
				</div>
				<!-- 第3列 -->
				<div class="inner-col">
					<h3>本地</h3>
					<div class="pagemap-item">
						<h4>省网络部门</h4>
						<ul class="info-list">
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li class="ks-clear">
						<ul class="word-list">
							<li><em><a href="#">TD无线网</a></em></li>
							<li><em><a href="#">GSM无线网</a></em></li>
							<li><em><a href="#">其他</a></em></li>
						</ul></li>
						</ul>
					</div>
					<div class="pagemap-item">
						<h4>内部其他部门</h4>
						<ul class="info-list">
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li>知识库分类显示之故障-无线分类知识标题</li>
							<li class="ks-clear">
						<ul class="word-list">
							<li><em><a href="#">TD无线网</a></em></li>
							<li><em><a href="#">GSM无线网</a></em></li>
							<li><em><a href="#">其他</a></em></li>
						</ul></li>
						</ul>
					</div>
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