<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UK</title>
</head>
<body>
	<script>
		var empDetail;

		$(document).ready(function() {
			$("#saveBtn").button();
			$("#searchImgBtn").button();
			$("#saveImgBtn").button();
			$("#empTab").tabs();
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
			$("#hireDate").datepicker({
				showMonthAfterYear : true,
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd"
			});
			$("#saveBtn").click(registerEmpDetail);
			empList();
			registerEmp();
		});

		function empList() {
			$("#empList").jqGrid({
				url : "${pageContext.request.contextPath}/hr/emp/emp.do",
				postData : {
					"method" : "findEmpList",
				},
				datatype : "json",
				jsonReader : {
					page : 'empList.pagenum',
					total : 'empList.pagecount',
					root : 'empList.list'
				},
				align : "center",
				width : 300,
				height : 420,
				rownumbers : true,
				caption : "사원리스트",
				colNames : [ "사원번호", "사원명" ],
				colModel : [ {
					name : "empNo",
					width : 50,
					editable : true,
					key : true
				}, {
					name : "empName",
					width : 50,
					editable : true
				} ],
				rowNum : 10,
				rowList : [ 10, 20, 30 ],
				pager : "#empPager",
				onSelectRow : function(empNo) {
					findEmpDetail(empNo);
				}
			});
			$("#empList")
					.jqGrid(
							'navGrid',
							"#empPager",
							{
								excel : false,
								add : true,
								edit : false,
								view : false,
								del : true,
								search : false,
								refresh : false,
							},
							{},
							{
								url : "${pageContext.request.contextPath}/hr/emp/emp.do?method=registerEmp",
								afterComplete : function(response) {
								},
								closeAfterAdd : true,
								reloadAfterSubmit : true,
								closeOnEscape : false
							},
							{
								url : "${pageContext.request.contextPath}/hr/emp/emp.do?method=removeEmp",
								afterComplete : function(response) {
									codeDetailList();
								},
								reloadAfterSubmit : true,
								closeOnEscape : false
							});
		}

		function findEmpDetail(empNo) {
			$.ajax({
						url : "${pageContext.request.contextPath}/hr/emp/emp.do",
						type : "post",
						data : {
							"method" : "findEmpDetail",
							"empNo" : empNo
						},
						dataType : "json",
						success : function(data) {
							empDetail = data.empDetail;
							//이미지 사원번호
							$("#empId").val(empDetail.empNo);
							$("#empNo").val(empDetail.empNo);
							$("#empName").val(empDetail.empName);
							$("#DPNo").val(empDetail.deptNo);
							$("#POName").val(empDetail.position);
							$("#GEName").val(empDetail.empGender);
							$("#empTel").val(empDetail.empTel);
							$("#empCelpone").val(empDetail.empCelpone);
							$("#zipCode").val(empDetail.zipCode);
							$("#basicAddress").val(empDetail.basicAddress);
							$("#detailAddress").val(empDetail.detailAddress);
							$("#empEmail").val(empDetail.empEmail);
							$("#hireDate").val(empDetail.hireDate);
							$("#password").val(empDetail.password);
							$("#empImgSrc").val(empDetail.image);

							if (empDetail.image == null
									|| empDetail.image == "") {
								$("#empImg")
										.attr("src",
												"${pageContext.request.contextPath}/scripts/img/noimage.png");
							} else {
                                $("#empImg").attr("src", "${pageContext.request.contextPath}/scripts/img/empimg/"+empDetail.image);
							}
						}
					});
		}

		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();
				reader.onload = function(e) {
					$("#empImg").attr("src", e.target.result);
				}
				reader.readAsDataURL(input.files[0]);
			}
		}

		$("#addImgForm").ajaxForm({
			dataType : "json",
			success : function(responseText, statusText, xhr, $form) {
				alert(responseText.errorMsg);
				$("#empImgSrc").val(responseText.url);
			}
		});

		function registerEmp() {
			$("#deptNoBtn").button().click("AG", codeDial);
			$("#positionNameBtn").button().click("PO", codeDial);
			$("#genderBtn").button().click("GE", codeDial);
			$("#zipCodeBtn").button().click(searchZipCode);
			$("#addressBtn").button().click(searchZipCode);
		}

		function codeDial(code) {
			var codeNo = code.data;
			alert(codeNo);
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
			window.open(
					"${pageContext.request.contextPath}/base/postform.html",
					"aa", "width=600px; height=520px; left=400px; top=100px;");
		}

		function registerEmpDetail() {
			empDetail.empNo = $("#empNo").val();
			empDetail.empName = $("#empName").val();
			empDetail.deptNo = $("#DPNo").val();
			empDetail.position = $("#POName").val();
			empDetail.empGender = $("#GEName").val();
			empDetail.empTel = $("#empTel").val();
			empDetail.empCelpone = $("#empCelpone").val();
			empDetail.zipCode = $("#zipCode").val();
			empDetail.basicAddress = $("#basicAddress").val();
			empDetail.detailAddress = $("#detailAddress").val();
			empDetail.empEmail = $("#empEmail").val();
			empDetail.hireDate = $("#hireDate").val();
			empDetail.password = $("#password").val();
			empDetail.image = $("#empImgSrc").val();

			$.ajax({
				url : "${pageContext.request.contextPath}/hr/emp/emp.do",
				type : "post",
				data : {
					"method" : "modifyEmp",
					"emp" : JSON.stringify(empDetail)
				},
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					alert(data.errorMsg);
				}
			});
		}
	</script>
	<table>
		<tr>
			<td>
				<table id="empList"></table>
				<div id="empPager"></div>
			</td>
			<td>
				<fieldset style="border-color: #35414f;">
				<legend>사원상세정보</legend>
				<table style="width: 900px; height: 490px;">
					<tr>
						<td>
							<center>
								<img
									src="${pageContext.request.contextPath}/scripts/img/noimage.png" id="empImg" style="width: 180px; height: 240px;">
								<form id="addImgForm" action="${pageContext.request.contextPath}/base/uploadFile.do?method=uploadfile"
									enctype="multipart/form-data" name="addImgForm" method="post">
									<input type="hidden" name="empId" id="empId">
									<input type="hidden" name="oper" value="image">
									<input type=file id="fileEmpImg" name="fileEmpImg" style="display: none;" onchange="readURL(this)">
									<button type="button" id="searchImgBtn" onclick="document.getElementById('fileEmpImg').click();" style="margin-top: 10px;">
										<span class="ui-icon ui-icon-image"></span>
									</button>
									<button type="submit" id="saveImgBtn" style="margin-top: 10px;"><span class="ui-icon ui-icon-disk"></span>
									</button>
								</form>
								<input type="hidden" id="empImgSrc">
							</center>
						</td>
						<td style="line-height: 200%;">
							<table>
								<tr>
									<td class="uk_td1">사원번호</td>
									<td><input type="text" id="empNo" class="uk_text1"
										disabled="disabled"></td>
									<td class="uk_td1">우편번호</td>
									<td><input type="text" id="zipCode" class="uk_text1">
										<button id="zipCodeBtn"><span class="ui-icon ui-icon-search"></span></button></td>
								</tr>
								<tr>
									<td class="uk_td1">사원명</td>
									<td><input type="text" id="empName" class="uk_text1"></td>
									<td class="uk_td1">기본주소</td>
									<td><input type="text" id="basicAddress" class="uk_text1">
										<button id="addressBtn"><span class="ui-icon ui-icon-search"></span></button></td>
								</tr>
								<tr>
									<td class="uk_td1">부서</td>
									<td><input type="text" id="AGNo" class="uk_text1">
										<button id="deptNoBtn"><span class="ui-icon ui-icon-search"></span></button></td>
									<td class="uk_td1">상세주소</td>
									<td><input type="text" id="detailAddress" class="uk_text1"></td>
								</tr>
								<tr>
									<td class="uk_td1">직급</td>
									<td><input type="text" id="POName" class="uk_text1">
										<button id="positionNameBtn"><span class="ui-icon ui-icon-search"></span></button></td>
									<td class="uk_td1">이메일</td>
									<td><input type="text" id="empEmail" class="uk_text1"></td>
								</tr>
								<tr>
									<td class="uk_td1">성별</td>
									<td><input type="text" id="GEName" class="uk_text1">
										<button id="genderBtn"><span class="ui-icon ui-icon-search"></span></button></td>
									<td class="uk_td1">입사일</td>
									<td><input type="text" id="hireDate" class="uk_text1"></td>
								</tr>
								<tr>
									<td class="uk_td1">전화번호</td>
									<td><input type="text" id="empTel" class="uk_text1"></td>
									<td class="uk_td1">비밀번호</td>
									<td><input type="text" id="password" class="uk_text1"></td>
								</tr>
								<tr>
									<td class="uk_td1">휴대폰번호</td>
									<td><input type="text" id="empCelpone" class="uk_text1"></td>
									<td class="uk_td1">&nbsp;</td>
									<td><center><button id="saveBtn">저장</button></center></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				</fieldset>
			</td>
		</tr>
	</table>
	<div id="codeDialog" title="코드목록">
		<table id="codeList"></table>
		<div id="codePager"></div>
	</div>
</body>
</html>