<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dp" tagdir="/WEB-INF/tags/protocol/" %>
<%@page pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
  <title><s:message code="navigation.protocol.medicalPracticeAnalysis"/></title>
</head>
<body>

<dp:nice-link dissectionProtocol="${dissectionProtocol}" />

<c:choose>
  <c:when test="${dissectionProtocol.status eq 'WORK_IN_PROGRESS'}">
    <div class="form-actions">
      <a class="btn btn-primary" href='<c:url value="/protocol/status/change/${dissectionProtocolId}/FINISHED" />'>
        <i class="icon-ok-sign"></i>
        <s:message code="navigation.protocol.finish"/>
      </a>
    </div>
  </c:when>
  <c:when test="${dissectionProtocol.status eq 'FINISHED'}">
    <c:choose>
      <c:when test="${success}">
        <div class="alert alert-success">
          <s:message code="protocol.status.finished.done"/>
        </div>
      </c:when>
      <c:otherwise>
        <div class="alert alert-info">
          <s:message code="protocol.status.finished.info"/>
        </div>
      </c:otherwise>
    </c:choose>
  </c:when>
</c:choose>
</body>
</html>
