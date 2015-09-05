<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<link href="${contextPath}/css/common.css" rel="stylesheet"/>

<style type="text/css">
body,td,th {
	font-size: 14px;
}
.div{}
.table{border:0px solid #F00;border-collapse: collapse;height: 100%}
.table tr{border:1px solid #FFF;padding: 0.3em 1em;} 
.table td{border:1px solid #F00;padding: 0.3em 1em;line-height: 18px;}
.table p{}
.p{}
.mytable table{border:0px solid #F00;border-collapse: collapse;height: 100%}
.mytable table tr{border:1px solid #FFF;padding: 0.3em 1em;} 
.mytable table td{border:1px solid #F00;padding: 0.3em 1em;height: 16px;line-height: 18px;}
.mytable table span {
font-size:14px;
line-height: 18px;
}
.mytable span {
font-size:14px;
line-height: 18px;
}
.mytable table td p{}
.mytable p{}
</style>
<link rel="stylesheet" href="${contextPath}/styles/post/css/jquery.mobile-1.3.2.min.css" type="text/css"></link>
<script src="${contextPath}/styles/post/js/jquery.mobile-1.3.2.min.js"></script>
<div class="tabs" currentIndex="0" eventType="click" style="width:800px">
					<div class="tabsHeader">
						<div class="tabsHeaderContent">
							<ul>
								<li><a href="javascript:;"><span>标题1</span></a></li>
								<li><a href="javascript:;"><span>标题2</span></a></li>
							</ul>
						</div>
					</div>
					<div class="tabsContent" style="height:320px;">
						<div>内容1</div>
						<div>内容2</div>
					</div>
					<div class="tabsFooter">
						<div class="tabsFooterContent"></div>
					</div>
				</div>