<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script>
	$(document).ready(function() {
		$("#mrpTab").tabs();
		$("#workplaceList").click(workplaceList);
		$("#mrpListBtn").button().click(mrpListGrid);
		$("#mrpDisassembleBtn").button().click(mrpDisassembleList);
		$("#mrpTotalBtn").button().click(mrpTotalListGrid);
		$("#registerMrpTotalBtn").button().click(registerMrpTotal);
		$("#mrpStartDate").datepicker({
			showMonthAfterYear : true,
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy-mm-dd"
		});
		$("#mrpEndDate").datepicker({
			showMonthAfterYear : true,
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy-mm-dd"
		});

		mrpTotalReviewListGrid();
	});

	//사업장리스트
	function workplaceList() {
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
						"codeNo" : "WP"
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
						$("#workplaceList").val(detailCode);
						$("#codeDialog").dialog("close");
					}
				});
	}

	//mrp리스트
	function mrpListGrid() {
		$.jgrid.gridUnload("#mrpListGrid");
		$("#mrpListGrid")
				.jqGrid(
						{
							url : "${pageContext.request.contextPath}/logistics/product/mrp.do",
							mtype : "post",
							postData : {
								"method" : "findMrpList",
								"workplace" : $("#workplaceList").val(),
								"startDate" : $("#mrpStartDate").val(),
								"endDate" : $("#mrpEndDate").val()
							},
							datatype : "json",
							jsonReader : {
								page : 'mpsList.pagenum',
								total : 'mpsList.pagecount',
								root : 'mpsList.list'
							},
							beforeProcessing : function(data) {
								if (data.errorCode < 0) {
									alert(data.errorMsg);
								}
							},
							align : "center",
							width : 1150,
							height : 300,
							rownumbers : true,
							cellEdit : true,
							cellsubmit : "clientArray",
							caption : "MPS내역",
							colNames : [ "사업장", "수주번호", "수주일련번호", "품목번호",
									"품목명", "단위", "계획수량", "계획일자", "MRP여부", "상태" ],
							colModel : [ {
								name : "workplaceNo",
								width : 50,
								editable : false,
							}, {
								name : "contractNo",
								width : 50,
								editable : false
							}, {
								name : "contractItemNo",
								width : 100,
								editable : false
							}, {
								name : "itemNo",
								width : 50,
								editable : false
							}, {
								name : "itemName",
								width : 50,
								editable : false
							}, {
								name : "itemUnit",
								width : 50,
								editable : false
							}, {
								name : "planAmount",
								width : 50,
								editable : false
							}, {
								name : "planDate",
								width : 50,
								editable : false
							}, {
								name : "mrpStatus",
								width : 50,
								editable : false
							}, {
								name : "status",
								width : 50,
								editable : false
							} ],
							rowNum : 10,
							rowList : [ 5, 10, 15 ],
							pager : "#mrpListPager",
							onCellSelect : function(rowid, iCol) {

							}
						});
	}

	//전개데이터
	function mrpDisassembleList() {
		$
				.ajax({
					url : '${pageContext.request.contextPath}/logistics/product/mrp.do',
					type : "post",
					dataType : 'json',
					data : {
						"method" : "findMrpDisassembleList",
						"workplace" : $("#workplaceList").val(),
						"startDate" : $("#mrpStartDate").val(),
						"endDate" : $("#mrpEndDate").val()
					},
					cache : false,
					success : function(data) {
						if (data.errorCode < 0) {
							alert(data.errorMsg.errorMsg);
						} else {
							alert(data.errorMsg);
							mrpDisassembleListGrid(data.mrpList);
						}
					}
				});
	}

	//전개리스트
	function mrpDisassembleListGrid(mrpList) {
		$.jgrid.gridUnload("#mrpDisassembleListGrid");
		$.jgrid.gridUnload("#mrpTotalListGrid");
		$("#mrpDisassembleListGrid").jqGrid(
				{
					data : mrpList,
					datatype : "local",
					align : "center",
					width : 1150,
					height : 300,
					rownumbers : true,
					cellsubmit : "clientArray",
					caption : "소요량전개리스트",
					colNames : [ "사업장", "MPS번호", "소요량전개번호", "품목번호", "품목명",
							"단위", "완제품여부", "모품목번호", "계획수량", "소요일자", "발주예정일",
							"구매생산여부" ],
					colModel : [ {
						name : "workplaceNo",
						width : 50,
						editable : false,
					}, {
						name : "mpsNo",
						width : 70,
						editable : false
					}, {
						name : "mrpNo",
						width : 70,
						editable : false
					}, {
						name : "itemNo",
						width : 50,
						editable : false
					}, {
						name : "itemName",
						width : 70,
						editable : false
					}, {
						name : "itemUnit",
						width : 50,
						editable : false
					}, {
						name : "finishedItemStatus",
						width : 50,
						editable : false
					}, {
						name : "parentItemNo",
						width : 50,
						editable : false
					}, {
						name : "purchaseOrderAmount",
						width : 50,
						editable : true
					}, {
						name : "requiredDate",
						width : 50,
						editable : false
					}, {
						name : "orderDemantDate",
						width : 50,
						editable : false
					}, {
						name : "itemSupply",
						width : 50,
						editable : false
					} ],
					rowNum : 10,
					rowList : [ 5, 10, 15 ],
					pager : "#mrpDisassembleListPager",
				});
	}

	//취합리스트
	function mrpTotalListGrid() {
		$.jgrid.gridUnload("#mrpDisassembleListGrid");
		$("#mrpTotalListGrid")
				.jqGrid(
						{
							url : "${pageContext.request.contextPath}/logistics/product/mrp.do",
							mtype : "post",
							postData : {
								"method" : "findMrpTotalList",
							},
							datatype : "json",
							jsonReader : {
								page : 'mrpTotalList.pagenum',
								total : 'mrpTotalList.pagecount',
								root : 'mrpTotalList.list'
							},
							beforeProcessing : function(data) {
								if (data.errorCode < 0) {
									alert(data.errorMsg);
								}
							},
							align : "center",
							width : 1150,
							height : 300,
							rownumbers : true,
							cellsubmit : "clientArray",
							caption : "소요량취합리스트",
							colNames : [ "MRP취합번호", "품목번호", "품목명", "단위",
									"소요일자", "생산/발주 예정일", "계획수량", "구매생산여부",
									"거래처" ],
							colModel : [ {
								name : "mrpTotalNo",
								width : 50,
								editable : false,
							}, {
								name : "itemNo",
								width : 50,
								editable : false,
							}, {
								name : "itemName",
								width : 70,
								editable : false
							}, {
								name : "itemUnit",
								width : 70,
								editable : false
							}, {
								name : "requiredDate",
								width : 70,
								editable : false
							}, {
								name : "purchaseOrderDate",
								width : 70,
								editable : false
							}, {
								name : "purchaseOrderAmount",
								width : 70,
								editable : false
							}, {
								name : "itemSupply",
								width : 70,
								editable : false
							}, {
								name : "customerNo",
								width : 70,
								editable : false
							} ],
							rowNum : 15,
							rowList : [ 15, 30 ],
							pager : "#mrpTotalListPager",
							onCellSelect : function(rowid, iCol) {

							}
						});
	}

	//소요량취합내역 등록
	function registerMrpTotal() {
		var mrpTotalList = [];
		var rowid = $("#mrpTotalListGrid").jqGrid("getDataIDs"); //테이블 데이터 수집
		$.each(
						rowid,
						function(index, item) {
							var emptyMrpTotal = {};
							if ($("#mrpTotalListGrid").jqGrid("getCell", item,
									"itemSupply") == "구매") {
								emptyMrpTotal.mrpTotalNo = $(
										"#mrpTotalListGrid").getRowData(item).mrpTotalNo;
								emptyMrpTotal.itemNo = $("#mrpTotalListGrid")
										.getRowData(item).itemNo;
								emptyMrpTotal.customerNo = $(
										"#mrpTotalListGrid").getRowData(item).customerNo;
								emptyMrpTotal.purchaseOrderAmount = $(
										"#mrpTotalListGrid").getRowData(item).purchaseOrderAmount;
								emptyMrpTotal.purchaseOrderDate = $(
										"#mrpTotalListGrid").getRowData(item).purchaseOrderDate;
								mrpTotalList.push(emptyMrpTotal);
							}
						});
		alert(JSON.stringify(mrpTotalList));
		$
				.ajax({
					url : "${pageContext.request.contextPath}/logistics/product/mrp.do",
					type : "post",
					dataType : "json",
					data : {
						"method" : "registerMrpTotalList",
						mrpTotalList : JSON.stringify(mrpTotalList)
					},
					success : function(data) {
						if (data.errorCode < 0) {
							alert(data.errorMsg);
						} else {
							alert(data.errorMsg);
							mrpTotalReviewListGrid();
						}
					}
				});
	}

	//소요량취합내역 리스트
	function mrpTotalReviewListGrid() {
		$.jgrid.gridUnload("#mrpTotalReviewListGrid");
		$("#mrpTotalReviewListGrid")
				.jqGrid(
						{
							url : "${pageContext.request.contextPath}/logistics/product/mrp.do",
							mtype : "post",
							postData : {
								"method" : "findMrpTotalReviewList",
							},
							datatype : "json",
							jsonReader : {
								page : 'mrpTotalReviewList.pagenum',
								total : 'mrpTotalReviewList.pagecount',
								root : 'mrpTotalReviewList.list'
							},
							beforeProcessing : function(data) {
								if (data.errorCode < 0) {
									alert(data.errorMsg);
								}
							},
							align : "center",
							width : 1150,
							height : 300,
							rownumbers : true,
							cellsubmit : "clientArray",
							caption : "소요량취합내역",
							colNames : [ "MRP취합번호", "품목번호", "품목명", "단위",
									"발주일자", "발주수량", "구매생산여부", "거래처", "발주상태" ],
							colModel : [ {
								name : "mrpTotalNo",
								width : 50,
								editable : false,
							}, {
								name : "itemNo",
								width : 50,
								editable : false,
							}, {
								name : "itemName",
								width : 70,
								editable : false
							}, {
								name : "itemUnit",
								width : 70,
								editable : false
							}, {
								name : "purchaseOrderDate",
								width : 70,
								editable : false
							}, {
								name : "purchaseOrderAmount",
								width : 70,
								editable : false
							}, {
								name : "itemSupply",
								width : 70,
								editable : false
							}, {
								name : "customerNo",
								width : 70,
								editable : false
							}, {
								name : "purchaseOrderStatus",
								width : 70,
								editable : false
							} ],
							rowNum : 10,
							rowList : [ 10, 20, 30 ],
							pager : "#mrpTotalReviewListPager",
							onCellSelect : function(rowid, iCol) {
							}
						});
	}
</script>
</head>
<body>
	<div id="mrpTab" style="width: 1200px; height: 500px;">
		<ul>
			<li><a href="#tab-1"><span class="ui-icon ui-icon-bullet"></span>
					MRP관리&nbsp;&nbsp; </a></li>
			<li><a href="#tab-2"><span class="ui-icon ui-icon-bullet"></span>
					소요량전개/취합&nbsp;&nbsp; </a></li>
			<li><a href="#tab-3"><span class="ui-icon ui-icon-bullet"></span>소요량취합내역조회&nbsp;&nbsp;</a></li>
		</ul>

		<div id="tab-1">
			<table>
				<tr>
					<td>사업장 선택&nbsp;&nbsp; <input type="text" id="workplaceList">
						기간 선택&nbsp;&nbsp; <input type="text" id="mrpStartDate">
						&nbsp;~&nbsp; <input type="text" id="mrpEndDate">
						<button id="mrpListBtn" style="margin-bottom: 3px;">조회</button>
						<button id="mrpDisassembleBtn" style="margin-bottom: 3px;">소요량전개적용</button>
					</td>
				</tr>
				<tr>
					<td>
						<table id="mrpListGrid"></table>
						<div id="mrpListPager"></div>
					</td>
				</tr>
			</table>
		</div>

		<div id="tab-2">
			<table>
				<tr>
					<td>
						<button id="mrpTotalBtn" style="margin-bottom: 3px;">소요량취합적용</button>
						<button id="registerMrpTotalBtn" style="margin-bottom: 3px;">취합내역저장</button>
					</td>
				</tr>
				<tr>
					<td>
						<table id="mrpDisassembleListGrid"></table>
						<div id="mrpDisassembleListPager"></div>
						<table id="mrpTotalListGrid"></table>
						<div id="mrpTotalListPager"></div>
					</td>
				</tr>
			</table>
		</div>

		<div id="tab-3">
			<table>
				<tr>
					<td>
						<table id="mrpTotalReviewListGrid"></table>
						<div id="mrpTotalReviewListPager"></div>
					</td>
				</tr>
			</table>
		</div>
	</div>

	<div id="codeDialog" title="코드목록">
		<table id="codeList"></table>
		<div id="codePager"></div>
	</div>
</body>
</html>