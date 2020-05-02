<%@ page import="Db.Pattern" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="Db.Compute" %>
<%@ page import="java.lang.reflect.Array" %><%--
  Created by IntelliJ IDEA.
  User: isanhae
  Date: 2020/05/01
  Time: 6:46 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
    String[] Arr = request.getParameterValues("arr[]");
    String line = "";
// 배열로 받은 데이터를 문자열로 변경하여
    for (int i = 0; i < Arr.length; i++) {
        String word = Arr[i];
        line += word;
        if (i != Arr.length - 1) {
            line += ",";
        }
    }
//    패턴 클래스 생성
    Pattern pattern = new Pattern(line);
// 계산을 시킬 객체 생성
    Compute compute = new Compute(pattern);
//    결과를 받아 rest 형식으로 반환
    int[] arr = compute.getResult();
    response.setContentType("application/json");
    out.println(Arrays.toString(arr));
%>
