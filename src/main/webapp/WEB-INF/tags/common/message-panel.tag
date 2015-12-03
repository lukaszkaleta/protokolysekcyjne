<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<c:if test="${successMessage != null}">
  <div class="alert alert-success">
    <c:out value="${successMessage}"/>
  </div>
</c:if>
<c:if test="${successMessageCode != null}">
  <div class="alert alert-success">
    <s:message code="${successMessageCode}"/>
  </div>
</c:if>
<c:if test="${successMessages != null}">
  <c:forEach items="${successMessages}" var="successMessage">
    <div class="alert alert-success">
      <c:out value="${successMessage}"/>
    </div>
  </c:forEach>
</c:if>