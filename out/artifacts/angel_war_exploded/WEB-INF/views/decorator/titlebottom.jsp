<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>titlebottom.jsp</title>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#loutBt").button().on("click", function () {
                logout();
            });
        });

        function logout() {
            $.juqueryAlert = function (msg) {
                var $messageBox = $.parseHTML('<div id = "alertBx"></div>');
                $("body").append($messageBox);
                $($messageBox).dialog({
                    open: $($messageBox).append(msg),
                    title: "System",
                    modal: true,
                    buttons: {
                        yes: function () {
                            location.href = "${pageContext.request.contextPath}/loginform.html"
                        },
                        no: function () {
                            $("#alertBx").dialog("close");
                        }
                    }
                });
            };

            $(function () {
                $.juqueryAlert("로그아웃");
            });
        }
    </script>
</head>
<body>
    <h4>
        사원번호 : ${sessionScope.empNo}
        사원명 : ${sessionScope.empName }
        &nbsp;&nbsp;&nbsp;
    </h4>
</body>
</html>