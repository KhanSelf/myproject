<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>stockform.jsp</title>
<script type="text/javascript">
	var warehouseList = [];

	$(document).ready(function() {
		findStockList();
	});

	function findStockList() {
		$.ajax({
			url : "${pageContext.request.contextPath}/logistics/item/stock.do",
			type : "post",
			data : {
				"method" : "findStockList"
			},
			dataType : "json",
			success : function(data) {
				warehouseList = data.warehouseList;
				warehouseListGrid();
				stockListGrid();
			}
		});
	}

	function warehouseListGrid() {
		$.jgrid.gridUnload("#warehouseListGrid");
		$("#warehouseListGrid").jqGrid({
			data : warehouseList,
			datatype : "local",
			align : "center",
			width : 1150,
			height : 150,
			rownumbers : true,
			caption : "창고리스트",
			colNames : [ "창고번호", "창고명", "사업장번호", "창고관리자" ],
			colModel : [ {
				name : "warehouseNo",
				width : 50,
				editable : true,
			}, {
				name : "warehouseName",
				width : 50,
				editable : true
			}, {
				name : "workplaceNo",
				width : 50,
				editable : true
			}, {
				name : "empNo",
				width : 50,
				editable : true
			} ],
			rowNum : 5,
			rowList : [ 5, 10, 15 ],
			pager : "#warehouseListPager",
			onCellSelect : function(rowid) {
				stockListGrid(warehouseList[rowid - 1].stockList);
			}
		});
	}

	function stockListGrid(warehouseRowid) {
		$.jgrid.gridUnload("#stockListGrid");
		$("#stockListGrid").jqGrid(
				{
					data : warehouseRowid,
					datatype : "local",
					align : "center",
					width : 1150,
					height : 150,
					rownumbers : true,
					caption : "재고리스트",
					colNames : [ "재고번호", "조정일자", "창고번호", "품목번호", "출고수량",
							"입고수량", "재고수량" ],
					colModel : [ {
						name : "stockNo",
						width : 50,
						editable : true,
						key : true
					}, {
						name : "adjustDate",
						width : 50,
						editable : true
					}, {
						name : "warehouseNo",
						width : 50,
						editable : true
					}, {
						name : "itemNo",
						width : 50,
						editable : true
					}, {
						name : "outAmount",
						width : 50,
						editable : true
					}, {
						name : "inAmount",
						width : 50,
						editable : true
					}, {
						name : "stockAmount",
						width : 50,
						editable : true
					} ],
					rowNum : 5,
					rowList : [ 5, 10, 15 ],
					pager : "#stockListPager",
					onCellSelect : function(rowid, iCol) {

					}
				});
	}
</script>
</head>
<body>
	<fieldSet>
		<legend>재고관리</legend>
		<table style="width: 1100px; height: 490px;">
			<tr>
				<td>
					<table id="warehouseListGrid"></table>
					<div id="warehouseListPager"></div>
				</td>
			</tr>

			<tr>
				<td>
					<table id="stockListGrid"></table>
					<div id="stockListPager"></div>
				</td>
			</tr>
		</table>
	</fieldSet>
</body>
</html>