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
<dp:update updateDeniedMessageCode="medical.practice.analysis.provide.basic.data">
  <c:if test="${saveSuccess}">
    <div class="alert alert-info form-message">
      <s:message code="dissection.protocol.medical.practice.analysis.save.success"/>
    </div>
  </c:if>
  <form:form commandName="dissectionProtocol" action="${pageContext.request.contextPath}/protocol/medicalPracticeAnalysis/save" class="form">
    <form:hidden path="id"/>
    <s:message code="dissection.protocol.medical.practice.analysis.label"/>
    <form:textarea rows="22" path="medicalPracticeAnalysis" id="medicalPracticeAnalysis" cssClass="input-block-level"></form:textarea>
    <form:errors path="medicalPracticeAnalysis" cssClass="alert alert-error form-message"/>
    <div class="form-actions-left">
      <button type="submit" class="btn btn-primary">
        <s:message code="button.save"/>
      </button>
    </div>
  </form:form>

</dp:update>

</body>
</html>
