<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>bomform.jsp</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#bomMenuText").click(bomMenu);
		$("#bomListBtn").button().click(bomList);
		$("#codeDialog").dialog({
			autoOpen : false,
			resizable : false,
			show : {
				effect : "blind",
				duration : 300
			},
			hide : {
				effect : "blind",
				duration : 300
			}
		});
		bomListGrid(); //정전개
		bomTurnListGrid(); //역전개
	});
	function bomMenu() {
		$("#codeDialog").dialog({
			width : "450",
			height : "550"
		});
		$("#codeDialog").dialog("open");
		$.jgrid.gridUnload("#codeList");

		$("#codeList").jqGrid({
			url : "${pageContext.request.contextPath}/logistics/item/bom.do",
			mtype : "post",
			postData : {
				"method" : "findBomMenu",
			},
			datatype : "json",
			jsonReader : {
				root : 'bomMenuList'
			},
			align : "center",
			width : 400,
			height : 400,
			rownumbers : true,
			rowNum : 30,
			colNames : [ "코드번호", "코드명" ],
			colModel : [ {
				name : "codeDetailNo",
				width : 50,
				editable : true,
				key : true
			}, {
				name : "codeDetailName",
				width : 50,
				editable : true
			} ],
			onSelectRow : function(selectedCode) {
				var detailCode = $("#codeList").getCell(selectedCode, 1);
				var detailName = $("#codeList").getCell(selectedCode, 2);
				$("#bomMenuText").val(detailCode);
				$("#bomTurnMenuText").val(detailCode);
				$("#codeDialog").dialog("close");
			}
		});
	}

	function bomList() {
		bomListGrid();
		bomTurnListGrid();
	}

	function bomListGrid() {
		$.jgrid.gridUnload("#bomListGrid");
		$("#bomListGrid").jqGrid({
			url : "${pageContext.request.contextPath}/logistics/item/bom.do",
			mtype : "post",
			postData : {
				"method" : "findBomList",
				"itemNo" : $("#bomMenuText").val()
			},
			datatype : "json",
			jsonReader : {
				root : "bomList"
			},
			align : "center",
			width : 560,
			height : 370,
			rownumbers : true,
			caption : "BOM정전개",
			colNames : [ "품목코드", "품목명", "단위", "손실율", "소요일", "정미수량", "필요수량" ],
			colModel : [ {
				name : "hierarchicalItem",
				width : 70,
				editable : true,
				key : true
			}, {
				name : "itemDetailBean.itemName",
				width : 70,
				editable : true
			}, {
				name : "itemDetailBean.itemUnit",
				width : 50,
				editable : true
			}, {
				name : "itemDetailBean.lossRate",
				width : 50,
				editable : true
			}, {
				name : "itemDetailBean.leadTime",
				width : 50,
				editable : true
			}, {
				name : "itemDetailBean.demandQuantity",
				width : 50,
				editable : true
			}, {
				name : "quantity",
				width : 50,
				editable : true
			} ],
		});
	}

	function bomTurnListGrid() {
		$.jgrid.gridUnload("#bomTurnListGrid");
		$("#bomTurnListGrid").jqGrid({
			url : "${pageContext.request.contextPath}/logistics/item/bom.do",
			mtype : "post",
			postData : {
				"method" : "findBomTurnList",
				"itemNo" : $("#bomMenuText").val()
			},
			datatype : "json",
			jsonReader : {
				root : "bomList"
			},
			align : "center",
			width : 560,
			height : 370,
			rownumbers : true,
			caption : "BOM역전개",
			colNames : [ "품목코드", "품목명", "단위", "손실율", "소요일", "정미수량", "필요수량" ],
			colModel : [ {
				name : "hierarchicalItem",
				width : 70,
				editable : true,
				key : true
			}, {
				name : "itemDetailBean.itemName",
				width : 70,
				editable : true
			}, {
				name : "itemDetailBean.itemUnit",
				width : 50,
				editable : true
			}, {
				name : "itemDetailBean.lossRate",
				width : 50,
				editable : true
			}, {
				name : "itemDetailBean.leadTime",
				width : 50,
				editable : true
			}, {
				name : "itemDetailBean.demandQuantity",
				width : 50,
				editable : true
			}, {
				name : "quantity",
				width : 50,
				editable : true
			} ],
		});
	}
</script>
</head>
<body>
	<fieldSet style="border-color: #35414f;">
		<legend> BOM </legend>
		<table style="width: 1100px; height: 490px;">
			<tr>
				<td>품목선택&nbsp;&nbsp; <input type="text" id="bomMenuText">
					<button id="bomListBtn">
						<span class="ui-icon ui-icon-search"></span>
					</button>
				</td>
			</tr>

			<tr>
				<td>
					<table id="bomListGrid"></table>
				</td>
				<td>
					<table id="bomTurnListGrid"></table>
				</td>
			</tr>
		</table>
	</fieldSet>

	<div id="codeDialog" title="품목목록">
		<table id="codeList"></table>
	</div>
</body>
</html>