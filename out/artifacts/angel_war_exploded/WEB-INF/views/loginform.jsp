<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
    </style>
    <script>
        $(document).ready(function () {
            $(":button").button();
        });

    </script>
</head>
<body>
<form
      action="${pageContext.request.contextPath}/login.do?method=login"
      method="post">
    <fieldset>
        <div class="form-group">
            <label>사원번호</label>
            <div class="col-lg-10">
                <input type="text" name="empNo" placeholder="ID">
            </div>
        </div>
        <div>
            <label for="inputPassword">비밀번호</label>
            <div>
                <input type="password" name="password" placeholder="Password">
            </div>
        </div>
        <div>
            <button type="submit">로그인</button>
            <button type="reset">취소</button>
        </div>
        <h4>
            ${sessionScope.errors.errorMsg}
        </h4>

    </fieldset>
</form>
</body>
</html>
