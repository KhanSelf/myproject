<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script>
        var estimateList = [];
        var emptyEstimate = {};
        var emptyEstimateItem = {};

        $(document).ready(function () {
            $("#estimateTab").tabs();
            $("#registerEstimateBtn").button().click(registerEstimate);
            $("#saveEstimateBtn").button().click(saveEstimate);
            $("#registerEstimateItemBtn").button().click(registerEstimateItem);
            $("#estimateReviewBtn").button().click(estimateReviewList);
            $("#estimatePDFBtn").button().click(estimatePDF);
            $("#estimateReviewStartDate").datepicker({
                showMonthAfterYear: true,
                changeMonth: true,
                changeYear: true,
                dateFormat: "yy-mm-dd"
            });
            $("#estimateReviewEndDate").datepicker({
                showMonthAfterYear: true,
                changeMonth: true,
                changeYear: true,
                dateFormat: "yy-mm-dd"
            });

            getEstimate();

        });

        //견적데이터
        function getEstimate() {
            $
                .ajax({
                    url: "${pageContext.request.contextPath}/logistics/business/estimate.do",
                    type: "post",
                    data: {
                        "method": "findEstimateList"
                    },
                    dataType: "json",
                    success: function (data) {
                        if (data.errorCode < 0) {
                            alert(data.errorMsg);
                        } else {
                            estimateList = data.estimateList;
                            emptyEstimate = data.emptyEstimate;
                            emptyEstimateItem = data.emptyEstimateItem;
                            estimateListGrid();
                            estimateItemListGrid();
                        }
                    }
                });
        }

        //견적리스트
        function estimateListGrid() {
            $.jgrid.gridUnload("#estimateListGrid");
            $("#estimateListGrid")
                .jqGrid(
                    {
                        data: estimateList,
                        datatype: "local",
                        align: "center",
                        width: 350,
                        height: 280,
                        rownumbers: true,
                        cellEdit: true,
                        cellsubmit: "clientArray",
                        caption: "견적",
                        colNames: ["견적번호", "거래처번호", "견적일자", "상태", "수주적용", "상세"],
                        colModel: [{
                            name: "estimateNo",
                            width: 100,
                            editable: false
                        }, {
                            name: "customerNo",
                            width: 60,
                            editable: false
                        }, {
                            name: "estimateDate",
                            width: 70,
                            editable: true,
                            editoptions: {
                                size: 20,
                                dataInit: function (el) {
                                    $(el).datepicker({
                                        dateFormat: 'yy-mm-dd'
                                    });
                                }
                            }
                        }, {
                            name: "CON_Apply_YN",
                            width: 50,
                            editable: false
                        }, {
                            name: "status",
                            width: 50,
                            editable: false,
                            hidden: true
                        }, {
                            name: "estimateItemList",
                            hidden: true
                        }],
                        rowNum: 10,
                        rowList: [5, 10, 15],
                        pager: "#estimateListPager",
                        afterSaveCell: function (rowid, name, val, iRow,
                                                 iCol) {
                            if (name == 'estimateDate') {
                                var estimateDate = $("#estimateListGrid")
                                    .getCell(rowid, "estimateDate")
                                    .replace(/\-/g, "");
                                var estimateNo = "ES" + estimateDate
                                    + lpad(rowid, '0', 4);
                                $("#estimateListGrid").setCell(rowid,
                                    "estimateNo", estimateNo);
                            }
                        },
                        beforeSelectRow: function (rowid, e) {
                            if ($("#estimateListGrid").getCell(rowid,
                                    "status") == "select") {
                                $("#estimateListGrid").setColProp(
                                    "customerNo", {
                                        editable: false
                                    });
                                $("#estimateListGrid").setColProp(
                                    "estimateDate", {
                                        editable: false
                                    });
                            } else {
                                $("#estimateListGrid").setColProp(
                                    "estimateDate", {
                                        editable: true
                                    });
                            }
                        },
                        onCellSelect: function (rowid, iCol) {
                            estimateItemListGrid(estimateList[rowid - 1].estimateItemList);
                            if ($("#estimateListGrid").getCell(rowid,
                                    "status") != "select") {
                                if (iCol == 2) {
                                    codeDial(rowid, iCol, "CU");
                                }
                            }
                        }
                    });

        }

        //견적품목리스트
        function estimateItemListGrid(estimateItemRowid) {
            $.jgrid.gridUnload("#estimateItemListGrid");
            $("#estimateItemListGrid")
                .jqGrid(
                    {
                        data: estimateItemRowid,
                        datatype: "local",
                        align: "center",
                        width: 800,
                        height: 280,
                        rownumbers: true,
                        cellEdit: true,
                        cellsubmit: "clientArray",
                        caption: "견적상세",
                        colNames: ["견적일련번호", "납기일자", "품목번호", "품목명", "단위",
                            "단가", "견적수량", "상태", "견적번호"],
                        colModel: [{
                            name: "estimateItemNo",
                            width: 100,
                            editable: false,
                            key: true
                        }, {
                            name: "demandDate",
                            width: 50,
                            editable: true,
                            editoptions: {
                                size: 20,
                                dataInit: function (el) {
                                    $(el).datepicker({
                                        dateFormat: 'yy-mm-dd'
                                    });
                                }
                            }
                        }, {
                            name: "itemNo",
                            width: 50,
                            editable: false
                        }, {
                            name: "itemName",
                            width: 50,
                            editable: false
                        }, {
                            name: "itemUnit",
                            width: 50,
                            editable: false
                        }, {
                            name: "itemPrice",
                            width: 50,
                            editable: false
                        }, {
                            name: "estimateAmount",
                            width: 50,
                            editable: true
                        }, {
                            name: "status",
                            width: 50,
                            editable: false
                        }, {
                            name: "estimateNo",
                            hidden: true
                        }],
                        rowNum: 10,
                        rowList: [5, 10, 15],
                        pager: "#estimateItemListPager",
                        beforeSelectRow: function (rowid, e) {
                            if ($("#estimateItemListGrid").getCell(rowid,
                                    "status") == "select") {
                                $("#estimateItemListGrid").setColProp(
                                    "demandDate", {
                                        editable: false
                                    });
                                $("#estimateItemListGrid").setColProp(
                                    "estimateAmount", {
                                        editable: false
                                    });
                            } else {
                                $("#estimateItemListGrid").setColProp(
                                    "estimateDate", {
                                        editable: true
                                    });
                                $("#estimateItemListGrid").setColProp(
                                    "estimateAmount", {
                                        editable: true
                                    });
                            }
                        },
                        onCellSelect: function (rowid, iCol) {
                            if ($("#estimateItemListGrid").getCell(rowid,
                                    "status") != "select") {
                                if (iCol == 3 || iCol == 4) {
                                    codeDial(rowid, iCol, "IT-005"); //상품만..
                                }
                            }
                        }
                    });
        }

        //코드목록
        function codeDial(rowid, iCol, code) {
            $("#codeDialog").dialog({
                width: "450",
                height: "330"
            });
            $("#codeDialog").dialog("open");
            $.jgrid.gridUnload("#codeList");
            $("#codeList").jqGrid(
                {
                    url: "${pageContext.request.contextPath}/base/code.do",
                    mtype: "post",
                    postData: {
                        "method": "findCodeDetailList",
                        "codeNo": code
                    },
                    datatype: "json",
                    jsonReader: {
                        page: 'codeDetailList.pagenum',
                        total: 'codeDetailList.pagecount',
                        root: 'codeDetailList.list'
                    },
                    align: "center",
                    width: 400,
                    height: 160,
                    colNames: ["코드번호", "코드명"],
                    colModel: [{
                        name: "codeDetailNo",
                        width: 50,
                        editable: true,
                        key: true
                    }, {
                        name: "codeDetailName",
                        width: 50,
                        editable: true
                    }],
                    rowNum: 5,
                    rowList: [5, 10, 15],
                    pager: "#codePager",
                    onSelectRow: function (selectedCode) {
                        var detailCode = $("#codeList").getCell(selectedCode,
                            "codeDetailNo");
                        var detailName = $("#codeList").getCell(selectedCode,
                            "codeDetailName");
                        if (code == "CU") {
                            $("#estimateListGrid").jqGrid("setCell", rowid,
                                "customerNo", detailCode);
                        } else if (code == "IT-005") {
                            $("#estimateItemListGrid").jqGrid("setCell", rowid,
                                "itemNo", detailCode);
                            $("#estimateItemListGrid").jqGrid("setCell", rowid,
                                "itemName", detailName);
                        }
                        $("#codeDialog").dialog("close");

                    }
                });
        }

        //견적추가
        function registerEstimate() {
            var record = $("#estimateListGrid").getGridParam("records") + 1;
            emptyEstimate.status = "insert";
            $("#estimateListGrid").addRowData(record, emptyEstimate);
        }

        //저장
        function saveEstimate() {
            alert(JSON.stringify(estimateList));
            console.log(JSON.stringify(estimateList));

            var resultList = [];
            $.each(estimateList, function (index, item) {
                if (item.status != "select") {
                    resultList.push(item);
                }
            });
            $
                .ajax({
                    url: "${pageContext.request.contextPath}/logistics/business/estimate.do",
                    type: "post",
                    dataType: "json",
                    data: {
                        "method": "batchEstimate",
                        batchList: JSON.stringify(resultList)
                    },
                    success: function (data) {
                        alert(data.errorMsg);
                        location.reload();
                    }
                });
        }

        //견적상세추가
        function registerEstimateItem() {
            //견적번호
            var estimateNo = $("#estimateListGrid").getGridParam("selrow");
            estimateNo = $("#estimateListGrid").getCell(estimateNo, "estimateNo");

            var record = $("#estimateItemListGrid").getGridParam("records") + 1;
            //견적번호 세팅
            emptyEstimateItem.estimateNo = estimateNo;
            emptyEstimateItem.status = "insert";
            $("#estimateItemListGrid").addRowData(record, emptyEstimateItem);
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

        //견적내역조회
        function estimateReviewList() {
            $
                .ajax({
                    url: "${pageContext.request.contextPath}/logistics/business/estimate.do",
                    type: "post",
                    dataType: "json",
                    data: {
                        "method": "findEstimateReviewList",
                        "startDate": $("#estimateReviewStartDate").val(),
                        "endDate": $("#estimateReviewEndDate").val()
                    },
                    success: function (data) {
                        estimateReviewListGrid(data.estimateItemList);
                    }
                });

        }

        //견적내역리스트
        function estimateReviewListGrid(estimateReviewList) {
            $.jgrid.gridUnload("#estimateReviewListGrid");
            $("#estimateReviewListGrid").jqGrid(
                {
                    data: estimateReviewList,
                    datatype: "local",
                    align: "center",
                    width: 1150,
                    height: 320,
                    cache: false,
                    rownumbers: true,
                    caption: "견적내역",
                    colNames: ["견적일자", "견적번호", "견적일련번호", "거래처번호", "납기일자",
                        "품목번호", "품목명", "단위", "단가", "견적수량"],
                    colModel: [{
                        name: "estimateDate",
                        width: 100,
                        editable: true
                    }, {
                        name: "estimateNo",
                        width: 50,
                        editable: true,
                    }, {
                        name: "estimateItemNo",
                        width: 70,
                        editable: true
                    }, {
                        name: "customerNo",
                        width: 50,
                        editable: true
                    }, {
                        name: "demandDate",
                        width: 80,
                        editable: true
                    }, {
                        name: "itemNo",
                        width: 50,
                        editable: true
                    }, {
                        name: "itemName",
                        width: 50,
                        editable: true
                    }, {
                        name: "itemUnit",
                        width: 50,
                        editable: true
                    }, {
                        name: "itemPrice",
                        width: 50,
                        editable: true
                    }, {
                        name: "estimateAmount",
                        width: 50,
                        editable: true
                    }],
                    rowNum: 10,
                    rowList: [5, 10, 15],
                    pager: "#estimateReviewListPager",

                });
        }

        function estimatePDF() {
            var estimateRowid = $("#estimateListGrid").getGridParam("selrow");
            if (estimateRowid == null) {
                alert("항목을 선택후 이용해주세요");
            } else {
                var estimateNo = $("#estimateListGrid").jqGrid("getCell", estimateRowid, "estimateNo");
                var customerNo = $("#estimateListGrid").jqGrid("getCell", estimateRowid, "customerNo");
                window.open("${pageContext.request.contextPath}/base/pdf.html?method=estimateReport&estimateNo=" + estimateNo, "리포트보기", "width=800, height=600");
            }

        }
    </script>
</head>
<body>
<div id="estimateTab" style="width: 1200px; height: 500px;">
    <ul>
        <li><a href="#tab-1" id="findCustomer"><span
                class="ui-icon ui-icon-bullet"></span>견적관리&nbsp;&nbsp;</a></li>
        <li><a href="#tab-2"><span class="ui-icon ui-icon-bullet"></span>견적내역조회&nbsp;&nbsp;</a></li>
    </ul>
    <div id="tab-1">
        <table>
            <tr>
                <td>
                    <button id="registerEstimateBtn" style="margin-bottom: 3px;">추가</button>
                    <button id="estimatePDFBtn" style="margin-bottom: 3px;">PDF</button>
                    <button id="saveEstimateBtn" style="margin-bottom: 3px;">저장</button>
                </td>
                <td>
                    <button id="registerEstimateItemBtn" style="margin-bottom: 3px;">추가</button>
                </td>
            </tr>
            <tr>
                <td>
                    <table id="estimateListGrid"></table>
                    <div id="estimateListPager"></div>
                </td>
                <td>
                    <table id="estimateItemListGrid"></table>
                    <div id="estimateItemListPager"></div>
                </td>
            </tr>
        </table>
    </div>
    <div id="tab-2">
        <table>
            <tr>
                <td>견적일자 선택&nbsp;&nbsp;<input type="text"
                                              id="estimateReviewStartDate">&nbsp;~&nbsp; <input
                        type="text" id="estimateReviewEndDate">
                    <button id="estimateReviewBtn" style="margin-bottom: 3px;">
                        <span class="ui-icon ui-icon-search"></span>
                    </button>
                </td>
            </tr>
            <tr>
                <td>
                    <table id="estimateReviewListGrid"></table>
                    <div id="estimateReviewListPager"></div>
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