<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<h2 class="contentTitle"><c:choose><c:when test="${not empty name }">${name }</c:when><c:otherwise>续期新体验</c:otherwise></c:choose></h2>
<div style="padding:0 10px;">
	<p>&nbsp;</p>
	<div style="font-size:20px;color:#00F;">
		<a href="${http }://${url}" target="navTab" rel="external" id="externalFrag" external="true"><div style="font-size:25px;color:#00F;"><c:choose><c:when test="${not empty name }">${name }</c:when><c:otherwise>续期新体验</c:otherwise></c:choose></div></a>
	</div>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<!-- 
	<label>如点击上面链接打开无显示，点击下方链接吧</label><br><br>
	<a href="http://10.201.223.123/spider/xuqi/index.html?organCode=${userOrgan.orgCode }" target="_blank">弹窗方式</a>
	 -->
</div>