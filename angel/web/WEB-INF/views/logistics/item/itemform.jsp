<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>UK</title>
<script>
	var itemList = [];
	var emptyItem = {};
	$(document).ready(function() {
		$("#registerItemBtn").button().click(registerItem);
		$("#batchItemBtn").button().click(batchItem);

		findItemList();
	});

	//데이터 겟
	function findItemList() {
		$.ajax({
			url : "${pageContext.request.contextPath}/logistics/item/item.do",
			type : "post",
			data : {
				"method" : "findItemList"
			},
			dataType : "json",
			success : function(data) {
				emptyItem = data.emptyItem;
				itemList = data.itemList;

				itemListGrid();

			}
		});
	}

	//품목리스트
	function itemListGrid() {
		$.jgrid.gridUnload("#itemListGrid");
		$("#itemListGrid")
				.jqGrid(
						{
							data : itemList,
							datatype : "local",
							align : "center",
							width : 1150,
							height : 320,
							rownumbers : true,
							multiselect : true,
							multiboxonly : true,
							cellEdit : true,
							cellsubmit : "clientArray",
							caption : "품목리스트",
							colNames : [ "품목번호", "품목명", "단위", "단가", "손실율",
									"소요일", "정미수량", "구매생산여부", "완제품여부", "거래처번호",
									"상태" ],
							colModel : [ {
								name : "itemNo",
								width : 50,
								editable : true,
								key : true
							}, {
								name : "itemName",
								width : 50,
								editable : true
							}, {
								name : "itemUnit",
								width : 50,
								editable : true
							}, {
								name : "itemPrice",
								width : 50,
								editable : true
							}, {
								name : "lossRate",
								width : 50,
								editable : true
							}, {
								name : "leadTime",
								width : 50,
								editable : true
							}, {
								name : "demandQuantity",
								width : 50,
								editable : true
							}, {
								name : "itemSupply",
								width : 50,
								editable : false
							}, {
								name : "finishedItemStatus",
								width : 50,
								editable : false
							}, {
								name : "customerNo",
								width : 50,
								editable : false
							}, {
								name : "status",
								width : 50,
								editable : false
							} ],
							rowNum : 10,
							rowList : [ 10, 20, 30 ],
							pager : "#itemListPager",
							onCellSelect : function(rowid, iCol) {
								if ($("#itemListGrid").getCell(rowid, "status") != "insert") {
									$("#itemListGrid").setCell(rowid, "status",
											"update");
								}
								if (iCol == 9) {
									codeDial(rowid, iCol, "IS");
								} else if (iCol == 10) {
									codeDial(rowid, iCol, "FI");
								} else if (iCol == 11) {
									codeDial(rowid, iCol, "CU");
								}
							}
						});
	}

	//코드목록
	function codeDial(rowid, iCol, code) {
		$("#codeDialog").dialog({
			width : "450",
			height : "330"
		});
		$("#codeDialog").dialog("open");
		$.jgrid.gridUnload("#codeList");
		$("#codeList").jqGrid(
				{
					url : "${pageContext.request.contextPath}/base/code.do",
					mtype : "post",
					postData : {
						"method" : "findCodeDetailList",
						"codeNo" : code
					},
					datatype : "json",
					jsonReader : {
						page : 'codeDetailList.pagenum',
						total : 'codeDetailList.pagecount',
						root : 'codeDetailList.list'
					},
					align : "center",
					width : 400,
					height : 160,
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
					rowNum : 5,
					rowList : [ 5, 10, 15 ],
					pager : "#codePager",
					onSelectRow : function(selectedCode) {
						var detailCode = $("#codeList").getCell(selectedCode,
								"codeDetailNo");
						var detailName = $("#codeList").getCell(selectedCode,
								"codeDetailName");
						if (code == "IS") {
							$("#itemListGrid").setCell(rowid, "itemSupply",
									detailName);
						} else if (code == "FI") {
							$("#itemListGrid").setCell(rowid,
									"finishedItemStatus", detailName);
						} else if (code == "CU") {
							$("#itemListGrid").setCell(rowid, "customerNo",
									detailCode);
						}
						$("#codeDialog").dialog("close");

					}
				});
	}

	//추가
	function registerItem() {
		var record = $("#itemListGrid").getGridParam("records") + 1;
		emptyItem.status = "insert";
		$("#itemListGrid").addRowData(record, emptyItem);
	}

	//일괄
	function batchItem() {
		var resultList = [];
		var selectedItems = $("#itemListGrid").getGridParam("selarrrow");
		//삭제 처리
		$.each(selectedItems, function(index, item) {
			if ($("#itemListGrid").getCell(item, "status") == "insert") {
				$("#itemListGrid").setCell(item, "status", "insert");
			} else {
				$("#itemListGrid").setCell(item, "status", "delete");
			}
		});
		$.each(itemList, function(index, item) {
			if (item.status != "select") {
				resultList.push(item);
			}
		});

		$.ajax({
			url : "${pageContext.request.contextPath}/logistics/item/item.do",
			type : "post",
			dataType : "json",
			data : {
				"method" : "batchItem",
				batchList : JSON.stringify(resultList)
			},
			success : function(data) {
				alert(data.errorMsg);
				location.reload();
			}
		});

		var check = "";
		$.each(resultList, function(a, b) {
			$.each(b, function(a1, b1) {
				check += b1 + ",";
			});
		});
		alert(check);

	}
</script>
</head>
<body>
	<fieldset style="border-color: #35414f;">
		<legend> 품목 관리 </legend>
		<table style="width: 1100px; height: 490px;">
			<tr>
				<td>
					<button id="registerItemBtn">추가</button>
					<button id="batchItemBtn">일괄처리</button>
				</td>
			</tr>
			<tr>
				<td>
					<table id="itemListGrid"></table>
					<div id="itemListPager"></div>
				</td>
			</tr>
		</table>
	</fieldset>
	<div id="codeDialog" title="코드목록">
		<table id="codeList"></table>
		<div id="codePager"></div>
	</div>
</body>
</html>