<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	var customer;
	var emptyCustomer;

	$(document).ready(function() {
		$("#customerTab").tabs();
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
		$("#customerNoTd").tooltip();
		$("#registerCostomerBtn").button().click(registerCustomer);
		$("#removeCostomerBtn").button().click(removeCustomer);
		$("#zipCodeBtn").button().click(searchZipCode);
		$("#customerAddressBtn").button().click(searchZipCode);
		$("#businessCategoryBtn").button().click("BC", codeDial);
		$("#businessTypeBtn").button().click("BT", codeDial);
		$("#countryCodeBtn").button().click("CO", codeDial);
		$("#customerTradeTypeBtn").button().click("TT", codeDial);

		findCustomerList();
	});

	function findCustomerList() {
		$
				.ajax({
					url : "${pageContext.request.contextPath}/logistics/business/customer.do",
					type : "post",
					data : {
						"method" : "findCustomerList"
					},
					dataType : "json",
					success : function(data) {
						customer = data.customerList;
						emptyCustomer = data.emptyCustomer;
						customerGrid();
					}
				});
	}

	function customerGrid() {
		$.jgrid.gridUnload("#customerGrid");
		$("#customerGrid").jqGrid(
				{
					data : customer,
					datatype : "local",
					align : "center",
					width : 1150,
					height : 320,
					rownumbers : true,
					caption : "거래처리스트",
					colNames : [ "거래처번호", "거래처명", "사업자등록번호", "대표자성명",
							"대표자주민등록번호", "업태", "종목", "우편번호", "주소", "전화번호",
							"팩스번호", "홈페이지", "메일주소", "국가코드", "거래형태" ],
					colModel : [ {
						name : "customerNo",
						width : 50,
						editable : true,
						key : true
					}, {
						name : "customerName",
						width : 50,
						editable : true
					}, {
						name : "businessNumber",
						width : 50,
						editable : true
					}, {
						name : "representaticeName",
						width : 50,
						editable : true
					}, {
						name : "representaticeNumber",
						width : 50,
						editable : true
					}, {
						name : "businessCategory",
						width : 50,
						editable : true
					}, {
						name : "businessType",
						width : 50,
						editable : true
					}, {
						name : "zipCode",
						width : 50,
						editable : true
					}, {
						name : "customerAddress",
						width : 50,
						editable : true
					}, {
						name : "customerTel",
						width : 50,
						editable : true
					}, {
						name : "customerFax",
						width : 50,
						editable : true
					}, {
						name : "customerHomepage",
						width : 50,
						editable : true
					}, {
						name : "customerEmail",
						width : 50,
						editable : true
					}, {
						name : "countryCode",
						width : 50,
						editable : true
					}, {
						name : "customerTradeType",
						width : 50,
						editable : true
					} ],
				});
	}

	function codeDial(code) {
		var codeNo = code.data;
		$("#codeDialog").dialog({
			width : "450",
			height : "330"
		});
		$("#codeDialog").dialog("open");
		$.jgrid.gridUnload("#codeList");
		$("#codeList").jqGrid({
			url : "${pageContext.request.contextPath}/base/code.do",
			mtype : "post",
			postData : {
				"method" : "findCodeDetailList",
				"codeNo" : codeNo
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
				var detailCode = $("#codeList").getCell(selectedCode, 0);
				var detailName = $("#codeList").getCell(selectedCode, 1);
				$("#" + codeNo + "No").val(detailCode);
				$("#" + codeNo + "Name").val(detailName);
				$("#codeDialog").dialog("close");
			}
		});
	}

	function searchZipCode() {
		window.open("${pageContext.request.contextPath}/base/postform.html",
				"aa", "width=600px; height=520px; left=400px; top=100px;");
	}

	function registerCustomer() {
		emptyCustomer.customerNo = $("#customerNo").val();
		emptyCustomer.customerName = $("#customerName").val();
		emptyCustomer.businessNumber = $("#businessNumber").val();
		emptyCustomer.representaticeName = $("#representaticeName").val();
		emptyCustomer.representaticeNumber = $("#representaticeNumber").val();
		emptyCustomer.businessCategory = $("#BCName").val();
		emptyCustomer.businessType = $("#BTName").val();
		emptyCustomer.zipCode = $("#zipCode").val();
		emptyCustomer.customerAddress = $("#basicAddress").val();
		emptyCustomer.customerTel = $("#customerTel").val();
		emptyCustomer.customerFax = $("#customerFax").val();
		emptyCustomer.customerHomepage = $("#customerHomepage").val();
		emptyCustomer.customerEmail = $("#customerEmail").val();
		emptyCustomer.countryCode = $("#COName").val();
		emptyCustomer.customerTradeType = $("#TTNo").val();

		$
				.ajax({
					url : "${pageContext.request.contextPath}/logistics/business/customer.do",
					type : "post",
					data : {
						"method" : "registerCustomer",
						"customer" : JSON.stringify(emptyCustomer)
					},
					dataType : "json",
					success : function(data) {
						alert(data.errorMsg);
						document.getElementById("findCustomer").click();
						location.reload();
					}
				});
	}

	function removeCustomer() {
		$
				.ajax({
					url : "${pageContext.request.contextPath}/logistics/business/customer.do",
					type : "post",
					data : {
						"method" : "removeCustomer",
						"customer" : $("#customerGrid").jqGrid("getGridParam",
								"selrow")
					},
					dataType : "json",
					success : function(data) {
						alert(data.errorMsg);
						document.getElementById("findCustomer").click();
						location.reload();
					}
				});
	}
</script>
</head>
<body>
	<div id="customerTab" style="width: 1200px; height: 500px;">
		<ul>
			<li><a href="#tab-1" id="findCustomer"><span
					class="ui-icon ui-icon-bullet"></span>거래처 조회&nbsp;&nbsp;</a></li>
			<li><a href="#tab-2"><span class="ui-icon ui-icon-bullet"></span>거래처
					등록&nbsp;&nbsp;</a></li>
		</ul>
		<div id="tab-1">
			<table id="customerGrid"></table>
			<center>
				<input id="removeCostomerBtn" type="button" value="선택 거래처 삭제"
					style="margin-top: 10px;">
			</center>
		</div>

		<div id="tab-2">
			<table style="width: 1100px; height: 380px;">
				<tr>
					<td class="uk_td2">거래처번호</td>
					<td class="uk_td2" id="customerNoTd"
						title="※ CU-xxx 형식으로 입력하세요!!!  ex)CU-001"><input type="text"
						id="customerNo" class="uk_text2"></td>
					<td class="uk_td2">우편번호</td>
					<td class="uk_td2"><input type="text" id="zipCode"
						class="uk_text2">
						<button id="zipCodeBtn">
							<span class="ui-icon ui-icon-search"></span>
						</button></td>
				</tr>

				<tr>
					<td class="uk_td2">거래처명</td>
					<td class="uk_td2"><input type="text" id="customerName"
						class="uk_text2"></td>

					<td class="uk_td2">주소</td>
					<td class="uk_td2"><input type="text" id="basicAddress"
						class="uk_text2">
						<button id="customerAddressBtn">
							<span class="ui-icon ui-icon-search"></span>
						</button></td>
				</tr>

				<tr>
					<td class="uk_td2">사업자등록번호</td>
					<td class="uk_td2"><input type="text" id="businessNumber"
						class="uk_text2"></td>

					<td class="uk_td2">전화번호</td>
					<td class="uk_td2"><input type="text" id="customerTel"
						class="uk_text2"></td>
				</tr>

				<tr>
					<td class="uk_td2">대표자성명</td>
					<td class="uk_td2"><input type="text" id="representaticeName"
						class="uk_text2"></td>

					<td class="uk_td2">팩스번호</td>
					<td class="uk_td2"><input type="text" id="customerFax"
						class="uk_text2"></td>
				</tr>

				<tr>
					<td class="uk_td2">대표자주민등록번호</td>
					<td class="uk_td2"><input type="text"
						id="representaticeNumber" class="uk_text2"></td>

					<td class="uk_td2">홈페이지</td>
					<td class="uk_td2"><input type="text" id="customerHomepage"
						class="uk_text2"></td>
				</tr>

				<tr>
					<td class="uk_td2">업태</td>
					<td class="uk_td2"><input type="text" id="BCName"
						class="uk_text2">
						<button id="businessCategoryBtn">
							<span class="ui-icon ui-icon-search"></span>
						</button></td>

					<td class="uk_td2">메일주소</td>
					<td class="uk_td2"><input type="text" id="customerEmail"
						class="uk_text2"></td>
				</tr>

				<tr>
					<td class="uk_td2">종목</td>
					<td class="uk_td2"><input type="text" id="BTName"
						class="uk_text2">
						<button id="businessTypeBtn">
							<span class="ui-icon ui-icon-search"></span>
						</button></td>

					<td class="uk_td2">국가코드</td>
					<td class="uk_td2"><input type="text" id="COName"
						class="uk_text2">
						<button id="countryCodeBtn">
							<span class="ui-icon ui-icon-search"></span>
						</button></td>
				</tr>

				<tr>
					<td class="uk_td2">&nbsp;</td>
					<td class="uk_td2"></td>
					<td class="uk_td2">거래형태</td>
					<td class="uk_td2"><input type="text" id="TTNo"
						class="uk_text2">
						<button id="customerTradeTypeBtn">
							<span class="ui-icon ui-icon-search"></span>
						</button></td>
				</tr>
			</table>
			<center>
				<input id="registerCostomerBtn" type="button" value="저장">
			</center>
		</div>
	</div>
	<div id="codeDialog" title="코드목록">
		<table id="codeList"></table>
		<div id="codePager"></div>
	</div>
</body>
</html>