<%--
  Created by IntelliJ IDEA.
  User: it
  Date: 26. 2. 5.
  Time: 오후 2:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<h1>modify 임시화면</h1>
<h3>하나의 정보를 불러오기 , dto 라는 객체에서 정보를 하나씩 꺼내보기.</h3>
<p>_3_TodoDTO 모델클래스의 멤버를 getter 로 가져오는 효과</p>
<p>수정 화면이고, 수정된 내용을, 다시 서버에 전달해서, 수정 진행.</p>
<form id="form1" action="/todo_test/update" method="post">
  <div>
    <input type="text" name="tno" value="${dto.tno}" readonly>
  </div>
  <div>
    <input type="text" name="title" value="${dto.title}" >
  </div>
  <div>
    <input type="date" name="dueDate" value="${dto.dueDate}">
  </div>
  <div>
    <input type="checkbox" name="finished" ${dto.finished ? "checked" : ""}>
  </div>
  <div>
    <%-- 주소부분은 오늘 날짜로 변경 해야함.--%>
    <button type="submit">수정</button>
    <a href="/todo_test/list">목록가기</a>
  </div>
</form>
<%--삭제폼--%>
<form id="form2" action="/todo_test/delete" method="post">
  <%-- 삭제시 삭제할 tno 번호를 같이 전달 해야하므로, 숨겨서 보내기--%>
  <input type="hidden" name="tno" value="${dto.tno}" readonly>
  <div>
    <button type="submit">삭제</button>
  </div>
</form>

</body>
</html>
