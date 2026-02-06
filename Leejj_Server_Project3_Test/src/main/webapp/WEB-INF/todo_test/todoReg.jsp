<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>todoReg 글쓰기 임시 화면입니다.</h1>
<a href="/todo_test/list">목록가기</a>
 <form action="/todo_test/register" method="post">
     <div>
         <input type="text" name="title" placeholder="todo 제목을 입력해주세요">
     </div>
     <div>
         <input type="date" name="dueDate">
     </div>
     <button type="reset">초기화</button>
     <button type="submit">등록처리</button>
 </form>
</body>
</html>
