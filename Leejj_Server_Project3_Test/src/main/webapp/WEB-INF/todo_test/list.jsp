<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <a href="/todo_test/register">글쓰기</a>
</div>
 <ul>
     <c:forEach var="dto" items="${dtoList}">
         <li>
             <span>${dto.tno}</span>
             <span><a href="/todo/read?tno=${dto.tno}">${dto.title}</a></span>
             <span>${dto.dueDate}</span>
             <span>${dto.finished ? "완료" : "미완료"}</span>
         </li>
     </c:forEach>
 </ul>


</body>
</html>
