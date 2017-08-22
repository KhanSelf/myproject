<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script>
	$(document).ready(function() {
		$("#mpsTab").tabs();
		$("#workplaceList").click(workplaceList);
		$("#mpsListBtn").button().click(mpsListGrid);
		$("#registerMps").button().click(registerMps);
		$("#mpsReviewList").click(mpsReviewListGrid);
	});
	//사업장
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
	//MPS
	function mpsListGrid() {
		$.jgrid.gridUnload("#mpsListGrid");
		$("#mpsListGrid")
				.jqGrid(
						{
							url : "${pageContext.request.contextPath}/logistics/product/mps.do",
							mtype : "post",
							postData : {
								"method" : "findMpsList",
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
							colNames : [ "사업장", "수주일련번호", "품목번호", "품목명", "단위",
									"주문수량", "출하예정일", "계획일", "계획수량", "상태" ],
							colModel : [ {
								name : "workplaceNo",
								width : 50,
								editable : false,
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
								name : "contractAmount",
								width : 50,
								editable : false
							}, {
								name : "demantDate",
								width : 50,
								editable : false
							}, {
								name : "planDate",
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
								name : "planAmount",
								width : 50,
								editable : true
							}, {
								name : "status",
								width : 50,
								editable : false
							} ],
							onCellSelect : function(rowid, iCol) {
								$("#mpsListGrid").setCell(rowid, "status",
										"insert");
								if (iCol == 1) {
									$("#mpsListGrid").setCell(rowid,
											"workplaceNo",
											$("#workplaceList").val());
								}
							}
						});
	}
	//MPS등록
	function registerMps() {
		var resultList = []; //결과 값 할당 할 빈배열
		var rowid = $("#mpsListGrid").jqGrid("getDataIDs"); //데이터 수집
		$
				.each(rowid,
						function(index, item) { //수집한 데이터 푼다
							if ($("#mpsListGrid").jqGrid("getCell", item,
									"status") == "insert") {
								var emptyMps = {}; //빈 객체
								emptyMps.contractItemNo = $("#mpsListGrid")
										.getRowData(item).contractItemNo; //수주제품번호
								emptyMps.planDate = $("#mpsListGrid")
										.getRowData(item).planDate; //계획날자
								emptyMps.planAmount = $("#mpsListGrid")
										.getRowData(item).planAmount; //계획수량
								emptyMps.workplaceNo = $("#mpsListGrid")
										.getRowData(item).workplaceNo; //사업장번호
								resultList.push(emptyMps); //빈 배열에 객체 삽입
							}
						});
		$
				.ajax({
					url : "${pageContext.request.contextPath}/logistics/product/mps.do",
					type : "post",
					dataType : "json",
					data : {
						"method" : "registerMps",
						registerList : JSON.stringify(resultList)
					//registerList	<-	MPS등록 데이터
					},
					success : function(data) {
						if (data.errorCode < 0) {
							alert(data.errorMsg); //오류메세지
						} else {
							alert(data.errorMsg);
							$.jgrid.gridUnload("#mpsListGrid"); //MPS초기화
						}
					}
				});
	}

	//내역조회
	function mpsReviewListGrid() {
		$.jgrid.gridUnload("#mpsReviewListGrid");
		$("#mpsReviewListGrid")
				.jqGrid(
						{
							url : "${pageContext.request.contextPath}/logistics/product/mps.do",
							mtype : "post",
							postData : {
								"method" : "findMpsReviewList",
							},
							datatype : "json",
							jsonReader : {
								page : 'mpsReviewList.pagenum',
								total : 'mpsReviewList.pagecount',
								root : 'mpsReviewList.list',
							},
							align : "center",
							width : 1150,
							height : 320,
							cache : false,
							rownumbers : true,
							caption : "MPS내역",
							colNames : [ "사업장번호", "수주품목일련번호", "품목번호", "품목명",
									"단위", "주문수량", "출하예정일", "계획일", "계획수량",
									"MRP적용유무", "생산완료여부" ],
							colModel : [ {
								name : "workplaceNo",
								width : 50,
								editable : true
							}, {
								name : "contractItemNo",
								width : 100,
								editable : true,
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
								name : "demantDate",
								width : 50,
								editable : true
							}, {
								name : "planDate",
								width : 50,
								editable : true
							}, {
								name : "planAmount",
								width : 50,
								editable : true
							}, {
								name : "mrpStatus",
								width : 50,
								editable : true
							}, {
								name : "productStatus",
								width : 50,
								hidden : true
							} ],
							rowNum : 10,
							rowList : [ 5, 10, 15 ],
							pager : "#mpsReviewListPager",
						});
	}
</script>
</head>
<body>
	<div id="mpsTab" style="width: 1200px; height: 500px;">
		<ul>
			<li><a href="#tab-1"><span class="ui-icon ui-icon-bullet"></span>
					MPS관리&nbsp;&nbsp; </a></li>
			<li><a href="#tab-2" id="mpsReviewList"><span
					class="ui-icon ui-icon-bullet"></span> MPS내역조회&nbsp;&nbsp; </a></li>
		</ul>
		<div id="tab-1">
			<table>
				<tr>
					<td>사업장 선택&nbsp;&nbsp;<input type="text" id="workplaceList">
						<button id="mpsListBtn" style="margin-bottom: 3px;">조회</button>
						<button id="registerMps" style="margin-bottom: 3px;">MPS적용</button>
					</td>
				</tr>
				<tr>
					<td>
						<table id="mpsListGrid"></table>
					</td>
				</tr>
			</table>
		</div>
		<div id="tab-2">
			<table>
				<tr>
					<td>
						<table id="mpsReviewListGrid"></table>
						<div id="mpsReviewListPager"></div>
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