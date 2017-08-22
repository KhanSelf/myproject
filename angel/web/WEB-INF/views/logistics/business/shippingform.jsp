<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>shippingform.jsp</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#shippingTab").tabs();
		$("#shippingCustomerList").click(shippingCustomerList);
		$("#shippingCustomerListBtn").button().click(shippingListGrid);
		$("#shippingBtn").button().click(shipping);
		$("#shippingReviewBtn").button().click(shippingReviewListGrid);
		$("#shippingReviewStartDate").datepicker({
			showMonthAfterYear : true,
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy-mm-dd"
		});
		$("#shippingReviewEndDate").datepicker({
			showMonthAfterYear : true,
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy-mm-dd"
		});
	});

	function shippingCustomerList() {
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
						"codeNo" : "CU"
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
						$("#shippingCustomerList").val(detailCode);
						$("#codeDialog").dialog("close");
					}
				});
	}

	function shippingListGrid() {
		$.jgrid.gridUnload("#shippingListGrid");
		$("#shippingListGrid")
				.jqGrid(
						{
							url : "${pageContext.request.contextPath}/logistics/business/shipping.do",
							mtype : "post",
							postData : {
								"method" : "findShippingList",
								"customer" : $("#shippingCustomerList").val()
							},
							datatype : "json",
							jsonReader : {
								root : 'contractItemList'
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
							caption : "수주내역",
							colNames : [ "거래처번호", "수주번호", "수주일련번호", "품목번호",
									"주문수량", "납기예정일", "재고수량", "납품일자", "납품번호",
									"납품수량", "상태" ],
							colModel : [ {
								name : "customerNo",
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
								name : "contractAmount",
								width : 50,
								editable : false
							}, {
								name : "demantDate",
								width : 50,
								editable : false
							}, {
								name : "stockAmount",
								width : 50,
								editable : false
							}, {
								name : "shippingDate",
								width : 50,
								editable : true,
								editoptions : {
									size : 20,
									dataInit : function(el) {
										$(el).datepicker({
											dateFormat : 'yy-mm-dd'
										});
									}
								}
							}, {
								name : "shippingNo",
								width : 50,
								editable : false
							}, {
								name : "shippingAmount",
								width : 50,
								editable : true
							}, {
								name : "status",
								width : 50,
								editable : false
							} ],
							onCellSelect : function(rowid, iCol) {
								$("#shippingListGrid").setCell(rowid, "status",
										"insert");
							}
						});
	}

	function shipping() {
		var resultList = [];
		var rowid = $("#shippingListGrid").jqGrid("getDataIDs");
		$.each(rowid,
				function(index, item) {
					if ($("#shippingListGrid")
							.jqGrid("getCell", item, "status") == "insert") {
						var emptyShipping = {};
						emptyShipping.contractItemNo = $("#shippingListGrid")
								.getRowData(item).contractItemNo;
						emptyShipping.itemNo = $("#shippingListGrid")
								.getRowData(item).itemNo;
						emptyShipping.shippingDate = $("#shippingListGrid")
								.getRowData(item).shippingDate;
						emptyShipping.shippingNo = $("#shippingListGrid")
								.getRowData(item).shippingNo;
						emptyShipping.shippingAmount = $("#shippingListGrid")
								.getRowData(item).shippingAmount;
						emptyShipping.status = $("#shippingListGrid")
								.getRowData(item).status;
						resultList.push(emptyShipping);
					}
				});
		$
				.ajax({
					url : "${pageContext.request.contextPath}/logistics/business/shipping.do",
					type : "post",
					dataType : "json",
					data : {
						"method" : "registerShipping",
						registerList : JSON.stringify(resultList)
					},
					success : function(data) {
						if (data.errorCode < 0) {
							alert(data.errorMsg);
						} else {
							alert(data.errorMsg);
							$.jgrid.gridUnload("#shippingListGrid");
						}
					}
				});
	}

	function shippingReviewListGrid() {
		$.jgrid.gridUnload("#shippingReviewListGrid");
		$("#shippingReviewListGrid")
				.jqGrid(
						{
							url : "${pageContext.request.contextPath}/logistics/business/shipping.do",
							mtype : "post",
							postData : {
								"method" : "findShippingReviewList",
								"startDate" : $("#shippingReviewStartDate")
										.val(),
								"endDate" : $("#shippingReviewEndDate").val()
							},
							datatype : "json",
							jsonReader : {
								root : 'shippingList'
							},
							align : "center",
							width : 1150,
							height : 320,
							cache : false,
							rownumbers : true,
							caption : "납품내역",
							colNames : [ "납품일자", "납품번호", "거래처번호", "수주품목일련번호",
									"품목번호", "품목명", "출고수량", "비고" ],
							colModel : [ {
								name : "shippingDate",
								width : 50,
								editable : true
							}, {
								name : "shippingNo",
								width : 50,
								editable : true,
							}, {
								name : "customerNo",
								width : 70,
								editable : true
							}, {
								name : "contractItemNo",
								width : 50,
								editable : true
							}, {
								name : "itemNo",
								width : 50,
								editable : true
							}, {
								name : "itemName",
								width : 50,
								editable : true
							}, {
								name : "shippingAmount",
								width : 50,
								editable : true
							}, {
								name : "bigo",
								width : 50,
								editable : true
							} ],
						});
	}
</script>
</head>
<body>
	<div id="shippingTab" style="width: 1200px; height: 500px;">
		<ul>
			<li><a href="#tab-1" id="findCustomer"><span
					class="ui-icon ui-icon-bullet"></span>납품관리&nbsp;&nbsp;</a></li>
			<li><a href="#tab-2"><span class="ui-icon ui-icon-bullet"></span>납품내역조회&nbsp;&nbsp;</a></li>
		</ul>
		<div id="tab-1">
			<table>
				<tr>
					<td>거래처 선택&nbsp;&nbsp;<input type="text"
						id="shippingCustomerList">
						<button id="shippingCustomerListBtn" style="margin-bottom: 3px;">
							조회</button>
						<button id="shippingBtn" style="margin-bottom: 3px;">
							출고처리</button>
					</td>
				</tr>
				<tr>
					<td>
						<table id="shippingListGrid"></table>
					</td>
				</tr>
			</table>
		</div>
		<div id="tab-2">
			<table>
				<tr>
					<td>출고일자 선택&nbsp;&nbsp;<input type="text"
						id="shippingReviewStartDate">&nbsp;~&nbsp; <input
						type="text" id="shippingReviewEndDate">
						<button id="shippingReviewBtn" style="margin-bottom: 3px;">
							<span class="ui-icon ui-icon-search"></span>
						</button></td>
				</tr>
				<tr>
					<td>
						<table id="shippingReviewListGrid"></table>
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