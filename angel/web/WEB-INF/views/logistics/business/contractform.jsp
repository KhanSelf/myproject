<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
	var estimateList = [];
	var estimateItemList = [];
	var contractList = [];
	var emptyContract = {};
	var emptyContractItem = {};
	var estimateNo = "";
	var customerNo = "";
	var contractNo = "";
	var flag = 0;

	$(document).ready(function() {
		$("#callEstimateBtn").button().click(callEstimate);
		$("#contractTab").tabs();
		$("#registerContractBtn").button().click(registerContract);
		$("#saveContractBtn").button().click(saveContract);
		$("#registerContractItemBtn").button().click(registerContractItem);
		$("#contractReviewBtn").button().click(contractReviewList);
		$("#contractReviewStartDate").datepicker({
			showMonthAfterYear : true,
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy-mm-dd"
		});
		$("#contractReviewEndDate").datepicker({
			showMonthAfterYear : true,
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy-mm-dd"
		});

		getContract();
	});

	//수주데이터
	function getContract() {
		$
				.ajax({
					url : "${pageContext.request.contextPath}/logistics/business/contract.do",
					type : "post",
					data : {
						"method" : "findContractList"
					},
					dataType : "json",
					success : function(data) {
						if (data.errorCode < 0) {
							alert(data.errorMsg);
						} else {
							contractList = data.contractList;
							emptyContract = data.emptyContract;
							emptyContractItem = data.emptyContractItem;
							contractListGrid();
							contractItemListGrid();
						}
					}
				});
	}

	//수주리스트
	function contractListGrid() {
		$.jgrid.gridUnload("#contractListGrid");
		$("#contractListGrid")
				.jqGrid(
						{
							data : contractList,
							datatype : "local",
							align : "center",
							width : 350,
							height : 280,
							rownumbers : true,
							cellEdit : true,
							cellsubmit : "clientArray",
							caption : "수주",
							colNames : [ "수주번호", "거래처번호", "수주일자", "상태", "상세" ],
							colModel : [ {
								name : "contractNo",
								width : 100,
								editable : false
							}, {
								name : "customerNo",
								width : 50,
								editable : false
							}, {
								name : "contractDate",
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
								name : "status",
								width : 50,
								editable : false
							}, {
								name : "contractItemList",
								hidden : true
							} ],
							rowNum : 10,
							rowList : [ 5, 10, 15 ],
							pager : "#contractListPager",
							afterSaveCell : function(rowid, name, val, iRow,
									iCol) {
								if (name == "contractDate") {
									var contractDate = $("#contractListGrid")
											.getCell(rowid, "contractDate")
											.replace(/\-/g, "");
									contractNo = "CO" + contractDate
											+ lpad(rowid, '0', 4);
									$("#contractListGrid").setCell(rowid,
											"contractNo", contractNo);
								}
								if (flag == 1) {
									applyEstimate();
									flag = 0;
								}
							},
							beforeSelectRow : function(rowid, e) {
								if ($("#contractListGrid").getCell(rowid,
										"status") == "select") {
									$("#contractListGrid").setColProp(
											"customerNo", {
												editable : false
											});
									$("#contractListGrid").setColProp(
											"contractDate", {
												editable : false
											});
								} else {
									$("#contractListGrid").setColProp(
											"contractDate", {
												editable : true
											});
								}
							},
							onCellSelect : function(rowid, iCol) {
								contractItemListGrid(contractList[rowid - 1].contractItemList);
								if ($("#contractListGrid").getCell(rowid,
										"status") != "select") {
									if (iCol == 2) {
										codeDial(rowid, iCol, "CU");
									}
								}
							}
						});

	}

	//수주품목리스트
	function contractItemListGrid(contractItemRowid) {
		$.jgrid.gridUnload("#contractItemListGrid");
		$("#contractItemListGrid")
				.jqGrid(
						{
							data : contractItemRowid,
							datatype : "local",
							align : "center",
							width : 800,
							height : 280,
							multiselect : true,
							rownumbers : true,
							cellEdit : true,
							cellsubmit : "clientArray",
							caption : "수주상세",
							colNames : [ "수주일련번호", "수주번호", "납기예정일", "품목번호",
									"품목명", "단위", "수주수량", "MPS적용유무", "납품상태",
									"상태", "수주번호" ],
							colModel : [ {
								name : "contractItemNo",
								width : 100,
								editable : false,
								key : true
							}, {
								name : "contractNo",
								width : 100,
								editable : false,
								key : true
							}, {
								name : "demantDate",
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
								name : "contractAmount",
								width : 50,
								editable : true
							}, {
								name : "mpsStatus",
								width : 50,
								editable : false
							}, {
								name : "shippingStatus",
								width : 50,
								editable : false
							}, {
								name : "status",
								width : 50,
								editable : false
							}, {
								name : "contractNo",
								hidden : true
							} ],
							rowNum : 10,
							rowList : [ 5, 10, 15 ],
							pager : "#contractItemListPager",
							beforeSelectRow : function(rowid, e) {
								if ($("#contractItemListGrid").getCell(rowid,
										"status") == "select") {
									$("#contractItemListGrid").setColProp(
											"demantDate", {
												editable : false
											});
									$("#contractItemListGrid").setColProp(
											"contractAmount", {
												editable : false
											});
								} else {
									$("#contractItemListGrid").setColProp(
											"demantDate", {
												editable : true
											});
									$("#contractItemListGrid").setColProp(
											"contractAmount", {
												editable : true
											});
								}
							},
							onCellSelect : function(rowid, iCol) {
								if ($("#contractItemListGrid").getCell(rowid,
										"status") != "select") {
									if (iCol == 5 || iCol == 6) {
										codeDial(rowid, iCol, "IT-003"); //제품만..
									}
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
						if (code == "CU") {
							$("#contractListGrid").jqGrid("setCell", rowid,
									"customerNo", detailCode);
						} else if (code == "IT-003") {
							$("#contractItemListGrid").jqGrid("setCell", rowid,
									"itemNo", detailCode);
							$("#contractItemListGrid").jqGrid("setCell", rowid,
									"itemName", detailName);
						}
						$("#codeDialog").dialog("close");

					}
				});
	}

	//수주추가
	function registerContract() {
		var record = $("#contractListGrid").getGridParam("records") + 1;
		emptyContract.status = "insert";
		$("#contractListGrid").addRowData(record, emptyContract);
	}

	//견적수주추가
	function registerEstimateContract() {
		var record = $("#contractListGrid").getGridParam("records") + 1;
		emptyContract.status = "insert";
		emptyContract.customerNo = customerNo;
		$("#contractListGrid").addRowData(record, emptyContract);
	}

	//저장
	function saveContract() {
		alert(JSON.stringify(contractList));
		console.log(JSON.stringify(contractList));

		var resultList = [];
		$.each(contractList, function(index, item) {
			if (item.status != "select") {
				resultList.push(item);
			}
		});
		$
				.ajax({
					url : "${pageContext.request.contextPath}/logistics/business/contract.do",
					type : "post",
					dataType : "json",
					data : {
						"method" : "batchContract",
						batchList : JSON.stringify(resultList)
					},
					success : function(data) {
						alert(data.errorMsg);
						location.reload();
					}
				});
	}

	//수주상세추가
	function registerContractItem() {
		var contractNo = $("#contractListGrid").getGridParam("selrow");
		contractNo = $("#contractListGrid").getCell(contractNo, "contractNo");

		var record = $("#contractItemListGrid").getGridParam("records") + 1;
		emptyContractItem.contractNo = contractNo;
		emptyContractItem.mpsStatus = "N";
		emptyContractItem.shippingStatus = "N";
		emptyContractItem.status = "insert";
		$("#contractItemListGrid").addRowData(record, emptyContractItem);
	}

	function lpad(s, c, n) {
		if (!s || !c || s.length >= n) {
			return s;
		}
		var max = (n - s.length) / c.length;
		for (var i = 0; i < max; i++) {
			s = c + s;
		}
		return s;
	}

	//수주내역조회
	function contractReviewList() {
		$
				.ajax({
					url : "${pageContext.request.contextPath}/logistics/business/contract.do",
					type : "post",
					dataType : "json",
					data : {
						"method" : "findContractReviewList",
						"startDate" : $("#contractReviewStartDate").val(),
						"endDate" : $("#contractReviewEndDate").val()
					},
					success : function(data) {
						contractReviewListGrid(data.contractItemList);
					}
				});
	}

	//수주내역리스트
	function contractReviewListGrid(contractReviewList) {
		$.jgrid.gridUnload("#contractReviewListGrid");
		$("#contractReviewListGrid").jqGrid(
				{
					data : contractReviewList,
					datatype : "local",
					align : "center",
					width : 1150,
					height : 320,
					cache : false,
					rownumbers : true,
					caption : "수주내역",
					colNames : [ "수주일자", "수주번호", "수주일련번호", "거래처번호", "납기예정일",
							"품목번호", "품목명", "단위", "수주수량", "MPS적용유무", "납품상태" ],
					colModel : [ {
						name : "contractDate",
						width : 50,
						editable : true
					}, {
						name : "contractNo",
						width : 50,
						editable : true,
					}, {
						name : "contractItemNo",
						width : 70,
						editable : true
					}, {
						name : "customerNo",
						width : 50,
						editable : true
					}, {
						name : "demantDate",
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
						name : "itemUnit",
						width : 50,
						editable : true
					}, {
						name : "contractAmount",
						width : 50,
						editable : true
					}, {
						name : "mpsStatus",
						width : 50,
						editable : true
					}, {
						name : "shippingStatus",
						width : 50,
						editable : true
					} ],
					rowNum : 10,
					rowList : [ 5, 10, 15 ],
					pager : "#contractReviewListPager",
				});
	}

	//견적가져오기
	function callEstimate(rowid, iCol, code) {
		flag = 1;
		$("#estimateDialog").dialog({
			width : "400",
			height : "300"
		});
		$("#estimateDialog").dialog("open");

		$
				.ajax({
					url : "${pageContext.request.contextPath}/logistics/business/estimate.do",
					type : "post",
					data : {
						"method" : "findEstimateList"
					},
					dataType : "json",
					success : function(data) {
						if (data.errorCode < 0) {
							alert(data.errorMsg);
						} else {
							estimateList = data.estimateList;
							estimateListGrid();
						}
					}
				});

	}

	//견적목록
	function estimateListGrid() {
		$.jgrid.gridUnload("#estimateList");
		$("#estimateList")
				.jqGrid(
						{
							data : estimateList,
							datatype : "local",
							align : "center",
							width : 400,
							height : 280,
							rownumbers : true,
							cellEdit : true,
							cellsubmit : "clientArray",
							caption : "견적",
							colNames : [ "견적번호", "거래처번호", "견적일자", "상태", "수주적용",
									"상세" ],
							colModel : [ {
								name : "estimateNo",
								width : 100,
								editable : false
							}, {
								name : "customerNo",
								width : 50,
								editable : false
							}, {
								name : "estimateDate",
								width : 50,
								editable : false,
								editoptions : {
									size : 20,
									dataInit : function(el) {
										$(el).datepicker({
											dateFormat : 'yy-mm-dd'
										});
									}
								}
							}, {
								name : "status",
								width : 50,
								editable : false,
								hidden : true
							}, {
								name : "conApplyYN",
								width : 50,
								editable : false
							}, {
								name : "estimateItemList",
								hidden : true
							} ],
							rowNum : 10,
							rowList : [ 5, 10, 15 ],
							pager : "#estimateListPager",
							ondblClickRow : function(rowid) {
								estimateNo = $("#estimateList").getCell(rowid,
										"estimateNo");
								customerNo = $("#estimateList").getCell(rowid,
										"customerNo");

								estimateItemList = estimateList[rowid - 1].estimateItemList;

								//alert(JSON.stringify(estimateItemList));

								$("#estimateDialog").dialog("close");

								//수주에견적적용
								var record = $("#contractListGrid")
										.getGridParam("records") + 1;
								emptyContract.status = "insert";
								emptyContract.customerNo = customerNo;
								$("#contractListGrid").addRowData(record,
										emptyContract);
							}
						});
	}

	//수주품목에견적품목적용
	function applyEstimate() {
		//alert(estimateItemList.length);
		for (var i = 0; i < estimateItemList.length; i++) {
			var record = $("#contractItemListGrid").getGridParam("records") + 1;

			emptyContractItem.customerNo = customerNo;
			emptyContractItem.contractNo = contractNo;
			emptyContractItem.itemNo = estimateItemList[i].itemNo;
			emptyContractItem.itemName = estimateItemList[i].itemName;
			emptyContractItem.itemUnit = estimateItemList[i].itemUnit;
			emptyContractItem.contractAmount = estimateItemList[i].estimateAmount;
			emptyContractItem.mpsStatus = "N";
			emptyContractItem.shippingStatus = "N";
			emptyContractItem.status = "insert";
			$("#contractItemListGrid").addRowData(record, emptyContractItem);
		}
	}
</script>
</head>
<body>
	<div id="contractTab" style="width: 1200px; height: 500px;">
		<ul>
			<li><a href="#tab-1" id="findCustomer"><span
					class="ui-icon ui-icon-bullet"></span>수주관리&nbsp;&nbsp;</a></li>
			<li><a href="#tab-2"><span class="ui-icon ui-icon-bullet"></span>수주내역조회&nbsp;&nbsp;</a></li>
		</ul>
		<div id="tab-1">
			<table>
				<tr>
					<td>
						<button id="registerContractBtn" style="margin-bottom: 3px;">추가</button>
						<button id="saveContractBtn" style="margin-bottom: 3px;">저장</button>
						<button id="callEstimateBtn" style="margin-bottom: 3px;">견적가져오기</button>
					</td>
					<td>
						<button id="registerContractItemBtn" style="margin-bottom: 3px;">추가</button>
					</td>
				</tr>
				<tr>
					<td>
						<table id="contractListGrid"></table>
						<div id="contractListPager"></div>
					</td>
					<td>
						<table id="contractItemListGrid"></table>
						<div id="contractItemListPager"></div>
					</td>
				</tr>
			</table>
		</div>
		<div id="tab-2">
			<table>
				<tr>
					<td>수주일자 선택&nbsp;&nbsp;<input type="text"
						id="contractReviewStartDate">&nbsp;~&nbsp; <input
						type="text" id="contractReviewEndDate">
						<button id="contractReviewBtn" style="margin-bottom: 3px;">
							<span class="ui-icon ui-icon-search"></span>
						</button></td>
				</tr>
				<tr>
					<td>
						<table id="contractReviewListGrid"></table>
						<div id="contractReviewListPager"></div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="codeDialog" title="코드목록">
		<table id="codeList"></table>
		<div id="codePager"></div>
	</div>

	<div id="estimateDialog" title="견적목록">
		<table id="estimateList"></table>
		<div id="estimatePager"></div>
	</div>
</body>
</html>