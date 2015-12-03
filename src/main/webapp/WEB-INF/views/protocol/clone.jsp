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

  <div class="form-actions">
    <s:message code="protocol.clone.in.progress" var="cloneInProgress"/>
    <a class="btn btn-primary" href='<c:url value="/protocol/clone/do/${dissectionProtocolId}" />' id="doClone" onclick="disableLinkAfterClick($('#doClone'), '${cloneInProgress}')">
      <i class="icon-share"></i>
      <s:message code="navigation.protocol.clone"/>
    </a>
  </div>

</body>
</html>
