<%@ page import="com.gdpost.web.entity.basedata.TblMember"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<%!
	public String tree(TblMember member, String basePath, long pid) {
		StringBuilder builder = new StringBuilder();
		
		//long pid = member.getParent() == null ? 0:member.getParent().getId();
		builder.append("{id:" + member.getId() +  ", pId:" + pid + 
				", name:\"" + member.getMemberName() + "\", url:\"" + basePath + "/members/member/list/" + member.getId() + "\", target:\"ajax\"},");
		
		for(TblMember o : member.getChildren()) {
			builder.append(tree(o, basePath, member.getId()));				
		}
		return builder.toString();
	}
%>
<%
TblMember member2 = (TblMember)request.getAttribute("member");
	String orgTree = tree(member2, request.getContextPath(), 0);
	orgTree = orgTree.substring(0, orgTree.length() - 1);
%>

<script type="text/javascript">
<!--
var setting = {
	check: {
		enable: true
	},
	view: {
		showIcon: true
	},
	data: {
		simpleData: {
			enable:true,
			idKey: "id",
			pIdKey: "pId",
			rootPId: ""
		}
	},
	callback: {
		onClick: function(event, treeId, treeNode) {
			event.preventDefault();
		}
	}	
};

function GetCheckedAll() {
    var treeObj = $.fn.zTree.getZTreeObj("documentMemberTree");
    var nodes = treeObj.getCheckedNodes(true);
    var treeIds = "";
    for (var i = 0; i < nodes.length; i++) {
    	//if(!nodes[i].isParent) {
    	if(nodes[i].id != 1) {
        	treeIds += nodes[i].id + ",";
        }
    }
    
    if(treeIds.length > 0) {
    	treeIds = treeIds.substr(0, treeIds.length - 1);
    }
    
    return(treeIds);
}
    
var zNodes =[<%=orgTree%>];
     	
$(document).ready(function(){
	var t = $("#documentMemberTree");
	t = $.fn.zTree.init(t, setting, zNodes);
	t.expandAll(true); 
});
//-->
</script>
<style>
<!--
#orgTree li span {
	text-align:left;
	float: left;
	display: inline;
} 
-->
</style>
<ul id="documentMemberTree" class="ztree" style="display: block;"></ul>