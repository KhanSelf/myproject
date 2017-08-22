<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script>
	$(document).ready(function() {
		$("#contractTab").tabs();
		$("#purchaseCustomerList").click(purchaseCustomerList);
		$("#purchaseCustomerListBtn").button().click(purchaseListGrid);
		$("#registerPurchaseBtn").button().click(registerPurchase);
		$("#purchaseReviewBtn").button().click(purchaseReviewList);
		$("#purchaseReviewStartDate").datepicker({
			showMonthAfterYear : true,
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy-mm-dd"
		});
		$("#purchaseReviewEndDate").datepicker({
			showMonthAfterYear : true,
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy-mm-dd"
		});
	});

	//구매처 리스트
	function purchaseCustomerList() {
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
						$("#purchaseCustomerList").val(detailCode);
						$("#codeDialog").dialog("close");
					}
				});
	}

	//소요량취합리스트
	function purchaseListGrid() {
		$.jgrid.gridUnload("#purchaseListGrid");
		$("#purchaseListGrid")
				.jqGrid(
						{
							url : "${pageContext.request.contextPath}/logistics/product/purchase.do",
							mtype : "post",
							postData : {
								"method" : "findPurchaseList",
								"customer" : $("#purchaseCustomerList").val()
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
							multiselect : true,
							multiboxonly : true,
							cellsubmit : "clientArray",
							caption : "수주내역",
							colNames : [ "소요량취합번호", "발주일", "품목번호", "품목명", "단위",
									"수량", "구매처" ],
							colModel : [ {
								name : "mrpTotalNo",
								width : 50,
								editable : false,
							}, {
								name : "purchaseOrderDate",
								width : 50,
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
								name : "purchaseOrderAmount",
								width : 50,
								editable : false
							}, {
								name : "customerNo",
								width : 50,
								editable : false
							} ],
							rowNum : 5,
							rowList : [ 5, 10, 15 ],
							pager : "#purchaseListPager",
						});
	}

	//발주적용
	function registerPurchase() {
		var resultList = [];
		var selectedPurchase = $("#purchaseListGrid").getGridParam("selarrrow");
		if (selectedPurchase == "") {
			alert("적용시킬 품목을 선택해주세요");
		} else {
			$
					.each(
							selectedPurchase,
							function(index, item) {
								var emptyPurchase = {};
								emptyPurchase.mrpTotalNo = $(
										"#purchaseListGrid").getRowData(item).mrpTotalNo;
								emptyPurchase.purchaseOrderDate = $(
										"#purchaseListGrid").getRowData(item).purchaseOrderDate;
								emptyPurchase.purchaseOrderAmount = $(
										"#purchaseListGrid").getRowData(item).purchaseOrderAmount;
								emptyPurchase.itemNo = $("#purchaseListGrid")
										.getRowData(item).itemNo;
								emptyPurchase.customerNo = $(
										"#purchaseListGrid").getRowData(item).customerNo;
								resultList.push(emptyPurchase);
							});
			alert(JSON.stringify(resultList));

			$
					.ajax({
						url : "${pageContext.request.contextPath}/logistics/product/purchase.do",
						type : "post",
						dataType : "json",
						data : {
							"method" : "registerPurchase",
							registerList : JSON.stringify(resultList)
						},
						success : function(data) {
							if (data.errorCode < 0) {
								alert(data.errorMsg);
							} else {
								alert(data.errorMsg);
								$.jgrid.gridUnload("#purchaseListGrid");
							}
						}
					});
		}
	}

	//발주내역조회
	function purchaseReviewList() {
		$
				.ajax({
					url : "${pageContext.request.contextPath}/logistics/product/purchase.do",
					type : "post",
					dataType : "json",
					data : {
						"method" : "findPurchaseReviewList",
						"startDate" : $("#purchaseReviewStartDate").val(),
						"endDate" : $("#purchaseReviewEndDate").val()
					},
					success : function(data) {
						purchaseReviewListGrid(data.purchaseReviewList);
					}
				});
	}

	//발주내역리스트
	function purchaseReviewListGrid(purchaseReviewList) {
		$.jgrid.gridUnload("#purchaseReviewListGrid");
		$("#purchaseReviewListGrid").jqGrid({
			data : purchaseReviewList,
			datatype : "local",
			align : "center",
			width : 1150,
			height : 320,
			cache : false,
			rownumbers : true,
			caption : "수주내역",
			colNames : [ "구매처", "발주번호", "품목번호", "발주일자", "발주수량", "비고" ],
			colModel : [ {
				name : "customerNo",
				width : 50,
				editable : true
			}, {
				name : "purchaseOrderNo",
				width : 50,
				editable : true,
			}, {
				name : "itemNo",
				width : 50,
				editable : true
			}, {
				name : "purchaseOrderDate",
				width : 50,
				editable : true
			}, {
				name : "purchaseOrderAmount",
				width : 50,
				editable : true
			}, {
				name : "bigo",
				width : 50,
				editable : true
			} ],
			rowNum : 10,
			rowList : [ 5, 10, 15 ],
			pager : "#purchaseReviewListPager",
		});
	}
</script>
</head>
<body>
	<div id="contractTab" style="width: 1200px; height: 500px;">
		<ul>
			<li><a href="#tab-1"><span class="ui-icon ui-icon-bullet"></span>발주관리&nbsp;&nbsp;</a></li>
			<li><a href="#tab-2"><span class="ui-icon ui-icon-bullet"></span>발주내역조회&nbsp;&nbsp;</a></li>
		</ul>
		<div id="tab-1">
			<table>
				<tr>
					<td>구매처 선택&nbsp;&nbsp;<input type="text"
						id="purchaseCustomerList">
						<button id="purchaseCustomerListBtn" style="margin-bottom: 3px;">
							조회</button>
						<button id="registerPurchaseBtn" style="margin-bottom: 3px;">
							발주적용</button>
					</td>
				</tr>
				<tr>
					<td>
						<table id="purchaseListGrid"></table>
						<div id="purchaseListPager"></div>
					</td>
				</tr>
			</table>
		</div>
		<div id="tab-2">
			<table>
				<tr>
					<td>발주일자 선택&nbsp;&nbsp;<input type="text"
						id="purchaseReviewStartDate">&nbsp;~&nbsp; <input
						type="text" id="purchaseReviewEndDate">
						<button id="purchaseReviewBtn" style="margin-bottom: 3px;">
							<span class="ui-icon ui-icon-search"></span>
						</button></td>
				</tr>
				<tr>
					<td>
						<table id="purchaseReviewListGrid"></table>
						<div id="purchaseReviewListPager"></div>
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