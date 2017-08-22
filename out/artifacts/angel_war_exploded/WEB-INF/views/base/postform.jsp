<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <link
            href="${pageContext.request.contextPath}/scripts/css/jquery-ui.css"
            rel="stylesheet">
    <link
            href="${pageContext.request.contextPath}/scripts/css/ui.jqgrid.css"
            rel="stylesheet">
    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/scripts/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/scripts/js/jquery-ui.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/scripts/js/i18n/grid.locale-kr.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/scripts/js/jquery.jqGrid.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/scripts/js/jquery.json-2.3.js"></script>

    <title>postform.jsp</title>
    <style type="text/css">
        body {
            font-size: 12px
        }

        #addrForm {
            height: 500px;
            overflow: auto;
        }

        #listContainerJibun {
            height: 250px;
            overflow: auto;
            font-size: 12px
        }

        #listContainerRoad {
            height: 250px;
            overflow: auto;
            font-size: 12px
        }

        #addrComplete {
            height: 120px;
            overflow: auto;
            font-size: 12px;
        }

        #addrCompleteRoad {
            height: 120px;
            overflow: auto;
            font-size: 12px;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            var dataSet;
            var sigungu;
            $("#addrForm").tabs();
            $("button").button();
            $(':submit').button();
            $("#dong").focus();
            $("#searchJibun").button();
            $('#searchRoad').button();
            $('#searchRoad').click(function () {
                var sido = $('#selSido').val().split("/")[0];
                var sigunguname = $('#selSigungu').val();
                var roadname = $('#roadname').val();
                searchRoadname(sido, sigunguname, roadname);
            });
            $("#searchJibun").click(function () {
                searchJibun();
            });
            setSido();
        });
        //searchJibun
        function searchJibun() {
            var dong = $("#dong").val();
            $.ajax({
                url: "post.do?method=searchJibun",
                data: {
                    dong: dong
                },
                type: "post",
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                cache: false,
                dataType: "json",
                success: function (data) {
                    if (data.errorCode < 0)
                        alert(data.errorMsg);
                    if (data.postList) {
                        dataSet = data.postList;
                        printAddrList(dataSet);
                    }
                },
                error: function () {
                    alert("오류:주소검색");
                }
            });
        }
        //printAddrList
        function printAddrList(dataSet) {
            $("#listContainerJibun").html("");
            var array = [];
            array.push("<tr><td><b>주소 선택</b></td></tr>");
            $.each(dataSet, function (index, postBean) {
                array.push("<tr><td onclick='insertAddr(this)'><font color='gray'>" + postBean.zipno + "/" + postBean.sido);
                array.push(" " + postBean.sigungu);
                array.push(" " + postBean.dong);
                array.push(" " + postBean.ri + "</font></td></tr>");
            });
            $("<table/>", {
                html: array.join(""),
                class: "listTable",
                width: "400px"
            }).appendTo("#listContainerJibun");
        }
        //insertAddr
        function insertAddr(addr) {
            var blank = "";
            $("#addrComplete").html("");
            var addr = $(addr).text();
            var zip = addr.split("/");
            blank += "<b>상세주소 입력</b><br/>";
            blank += "우편번호 : <input type='text' size='10' id='zipcode' value='" + zip[0] + "' disabled><br>";
            blank += "기본주소 : <input type='text' size='50' id='baseAddr' value='" + zip[1] + "' disabled><br>";
            blank += "상세주소 : <input type='text' size='50' id='detailAddr'><br><br>";
            blank += "<input type='button' id='done' onclick='completeAddr()' value='입력완료'>";
            $("#addrComplete").html(blank);
            $("#detailAddr").focus();
            $("#done").button();
        }
        //completeAddr
        function completeAddr() {
            $("#basicAddress", opener.document).val($("#baseAddr").val());
            $("#zipCode", opener.document).val($("#zipcode").val());
            $("#detailAddress", opener.document).val($("#detailAddr").val());
            self.close();
        }
        //setSido
        function setSido() {
            $.ajax({
                url: "post.do?method=searchSido",
                data: {},
                type: "post",
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                cache: false,
                dataType: "json",
                success: function (data) {
                    if (data.errorCode < 0)
                        alert(data.errorMsg);
                    if (data.sidoList) {
                        dataSet = data.sidoList;
                        printSidoList(dataSet);
                    }
                },
                error: function () {
                    alert("오류:시/도");
                }
            });
        }
        //printSidoList
        function printSidoList(dataSet) {
            $("#listContainerSido").html("");
            var array = [];
            array.push("<select id=selSido onchange='setSigungu(this.value)'>");
            array.push("<option>******</option>");
            $.each(dataSet, function (index, postBean) {
                array.push("<option value='" + postBean.sido + "/" + postBean.sidoname + "'>" + postBean.sidoname + "</option>");
            });
            array.push("</select>");
            $('#listContainerSido').append(array.join(''));
        }
        //setSigungu
        function setSigungu(sidocode) {
            sido = sidocode.split("/")[0];
            sidoname = sidocode.split("/")[1];
            $.ajax({
                url: 'post.do?method=searchSigungu',
                data: {
                    sido: sido
                },
                type: "post",
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                cache: false,
                dataType: 'json',
                success: function (data) {
                    if (data.errorCode < 0)
                        alert(data.errMsg);
                    if (data.sigunguList) {
                        dataSet = data.sigunguList;
                        printSigunguList(dataSet);
                    }
                },
                error: function () {
                    alert("오류:시/군/구");
                }
            });
        }
        //printSigunguList
        function printSigunguList(dataSet) {
            $('#listContainerSigungu').html("");
            var array = [];
            array.push("<select id=selSigungu onchange='setSigunguname(this.value)'>");
            array.push("<option>******</option>");
            $.each(dataSet, function (index, postBean) {
                array.push("<option value='" + postBean.sidoname + "'>" + postBean.sidoname + "</option>");
            });
            array.push("</select>");
            $('#listContainerSigungu').append(array.join(''));
        }
        //setSigunguname
        function setSigunguname() {
            sigunguname = sigungu;
            alert(sidoname + "," + sigunguname);
        }
        //sigungu grid
        function searchRoadname(sido, sigunguname, roadname) {
            if ($.trim(sido) == "******") {
                alert('시도를 선택하세요.');
                return;
            }
            if ($.trim(sigunguname) == "******") {
                alert('시군구를 선택하세요.');
                return;
            }
            if (!$.trim(roadname)) {
                alert('상세주소를 입력하세요.');
                return;
            }
            $("#listRoadName").jqGrid({
                url: "${pageContext.request.contextPath}/base/post.do",
                type: "post",
                postData: {
                    "method": "searchRoadname",
                    "sido": sido,
                    "sigunguname": sigunguname,
                    "roadname": roadname
                },
                datatype: "json",
                jsonReader: {
                    //page : 'empList.pagenum',
                    //total : 'empList.pagecount',
                    root: 'postRoadList.list'
                },
                align: "center",
                width: 450,
                height: 420,
                rownumbers: true,
                caption: "도로명 주소 리스트",
                colNames: ["우편번호", "도로명", "건물번호1", "건물번호2"],
                colModel: [{
                    name: "zipcode",
                    width: 50,
                    editable: true,
                    key: true
                }, {
                    name: "roadName",
                    width: 50,
                    editable: true
                }, {
                    name: "buildingCode1",
                    width: 50,
                    editable: true
                }, {
                    name: "buildingCode2",
                    width: 50,
                    editable: true
                }],
                success: function (data) {
                    if (data.errorCode < 0)
                        alert(data.errorMsg);
                    if (data.sidoList) {
                        dataSet = data.postRoadList;
                        (dataSet);
                    }
                },
                rowNum: 10,
                rowList: [10, 20, 30],
                pager: "#RoadNamePager"
            });
            $("#listRoadName")
                .jqGrid(
                    'navGrid',
                    "#RoadNamePager",
                    {
                        excel: false,
                        add: false,
                        edit: false,
                        view: false,
                        del: false,
                        search: false,
                        refresh: false,
                    });
        }
    </script>
</head>
<body>
<div id="addrForm">
    <ul>
        <li><a href="#jibun">주소검색</a></li>
        <li><a href="#roadName">도로명주소</a></li>
    </ul>
    <div id="jibun">
        <b>읍/면/동을 입력하세요.</b>&nbsp;&nbsp; <input type="text" id="dong"
                                                style="ime-mode: active">&nbsp; <input type="button"
                                                                                       id="searchJibun" value="검색"><br>
        <div id="listContainerJibun"></div>
        <br>
        <div id="addrComplete"></div>
    </div>
    <div id="roadName">
        <b>주소검색 &nbsp;&nbsp;&nbsp; 시도: </b> <span id="listContainerSido"></span>&nbsp;&nbsp;&nbsp;
        <b>시군구: </b> <span id="listContainerSigungu"></span> <br> <b>도로명을
        입력하세요. </b>&nbsp;&nbsp; <input type="text" id="roadname"
                                       style="ime-mode: active"/> &nbsp; <input type="button"
                                                                                id="searchRoad" value="검색"><br>
        <div id="listContainerRoad"></div>
        <table id="listRoadName"></table>
        <div id="RoadNamePager"></div>
        <br>
        <div id="addrCompleteRoad"></div>
    </div>
</div>
</body>
</html>