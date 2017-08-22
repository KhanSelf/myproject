<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>codelistform.jsp</title>
<script type="text/javascript">
	$(document).ready(function(){
		codeList();
		codeDetailList();
	});

	//codeList
	function codeList() {
		$("#codeList").jqGrid({
			url : "${pageContext.request.contextPath}/base/code.do",
			postData : {
				"method" : "findCodeList"
			},
			datatype : "json",
			jsonReader : {
				page : 'codeList.pagenum',
				total : 'codeList.pagecount',
				root : 'codeList.list'
			},
			align : "center",
			width : 400,
			height : 350,
			rownumbers : true,
			caption : "코드목록",
			colNames : ["코드번호","코드명"],
			colModel : [{
				name : "codeNo",
				width : 50,
				editable : true,
				key : true
			}, {
				name : "codeName",
				width : 50,
				editable : true
			}],
			rowNum : 20,
			rowList : [10, 20, 30],
			pager : "#codePager",
			onSelectRow : function(codeNo){
				codeDetailList(codeNo);
			}
		});
 		//codeListGrid
		$("#codeList").jqGrid(
		"navGrid",
		"#codePager",
		{
			excel : false,
			add : true,
			edit : true,
			view : true,
			del : true,
			search : true,
			refresh : true
		}, {
			//modify
			url : "${pageContext.request.contextPath}/base/code.do?method=modifyCode",
					afterComplete : function(response){
						alert($.parseJSON(response.responseText).errorMsg);
					},
					closeAfterEdit : true,
					reloadAfterSubmit : true,
					closeOnEscape : false
		}, {
			//register
			url : "${pageContext.request.contextPath}/base/code.do?method=registerCode",
					afterComplete : function(response){
						alert($.parseJOSN(response.responseText).errorMsg);
					},
					closeAfterAdd : true,
					reloadAfterSubmit : true,
					closeOnEscape : false
		},{
			//remove
			url : "${pageContext.request.contextPath}/base/code.do?method=removeCode",
					afterComplete : function(response){
						alert($.parseJSON(response.responseText).errorMsg);
						codeDetailList();
					},
					reloadAfterSubmit : true,
					closeOnEscape : false
		},{
			//search
			multipleSearch : true,
			multipleGroup : true,
			showQuery : false,
			closeOnEscape : false
		},{
			closeOnEscape : true
		});
	}

	//codeDetailList
	function codeDetailList(codeNo){
		$.jgrid.gridUnload("#codeDetailList");
		$("#codeDetailList").jqGrid({
			url : "${pageContext.request.contextPath}/base/code.do",
			postData : {
				"method" : "findCodeDetailList",
				"codeNo" : codeNo
			},
			datatype : "json",
			jsonReader : {
				page : "codeDetailList.pagenum",
				total : "codeDetailList.pagecount",
				root : "codeDetailList.list"
			},
			align : "center",
			width : 500,
			height : 350,
			rownumbers : true,
			caption : "상세코드목록",
			colNames : ["코드번호","상세코드번호","상세코드명"],
			colModel : [{
				name : "codeNo",
				width : 50,
				editable : false
			},{
				name : "codeDetailNo",
				width : 50,
				editable : true,
				key : true
			},{
				name : "codeDetailName",
				width : 50,
				editable : true
			}],
			rowNum : 20,
			rowList : [10, 20, 30],
			pager : "codeDetailPager"
		});
		//codeDetailListGrid
		$("#codeDetailList").jqGrid(
		"navGrid",
		"#codeDetailPager",
		{
			excel : false,
			add : true,
			edit : true,
			view : true,
			del : true,
			search : false,
			refresh : true
		},{
			//modify
			url : "${pageContext.request.contextPath}/base/code.do?method=modifyCodeDetail",
					afterComplete : function(response){
						alert($.parseJSON(response.responseText).errorMsg);
					},
					closeAfterEdit : true,
					reloadAfterSubmit : true,
					closeOnEscape : false
		},{
			//registe
			url : "${pageContext.request.contextPath}/base/code.do?method=registerCodeDetail&codeNo="+codeNo,
					afterComplete : function(response){
						alert($.parseJSON(response.responseText).errorMsg);
					},
					closeAfterAdd : true,
					reloadAfterSubmit : true,
					closeOnEscape : false
		},{
			//remove
			url : "${pageContext.request.contextPath}/base/code.do?method=removeCodeDetail",
					afterComplete : function(response){
						alert($.parseJSON(response.responseText).errorMsg);
					},
					reloadAfterSubmit : true,
					closeOnEscape : false
		},{
			//search
			multipleSearch : true,
			multipleGroup : true,
			showQuery : false,
			closeOnEscape : false
		},{
			closeOnEscape : true
		});
	}
</script>
</head>
<body>
	<fieldset style="border-color: #35414f;">
		<legend>코드 관리</legend>
		<table>
			<tr>
				<td>
					<table id="codeList"></table>
					<div id="codePager"></div>
				</td>
				<td>
					<table id="codeDetailList"></table>
					<div id="codeDetailPager"></div>
				</td>
			</tr>
		</table>
	</fieldset>
</body>
</html>