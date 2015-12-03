<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="path" required="false" rtexprvalue="true" type="java.lang.String" description="Path for point" %>

<c:choose>
  <c:when test="${path == null}">
    <c:set var="pointPath" value="point"/>
    <c:set var="positionPath" value="position"/>
  </c:when>
  <c:otherwise>
    <c:set var="pointPath" value="${path}.point"/>
    <c:set var="positionPath" value="${path}.position"/>
  </c:otherwise>
</c:choose>

<form:select path="${pointPath}">
  <c:forEach var="_point" begin="1" end="22">
    <form:option value="${_point}" label="${_point}"/>
  </c:forEach>
</form:select>
<form:select path="${positionPath}">
  <c:forTokens items="a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,r,s,t,u,w,z,y,z" delims="," var="_position" varStatus="_positionStatus">
    <form:option value="${_positionStatus.index + 1}" label="${_position}"/>
  </c:forTokens>
</form:select>