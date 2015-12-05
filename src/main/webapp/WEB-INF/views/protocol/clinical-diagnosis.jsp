<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dp" tagdir="/WEB-INF/tags/protocol/" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@page pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
  <title><s:message code="navigation.protocol.clinicalDiagnosis"/></title>
</head>
<body>
<dp:update updateDeniedMessageCode="clinical.diagnosis.provide.basic.data">

  <common:message-panel/>

  <form:form commandName="dissectionProtocol" action="${pageContext.request.contextPath}/protocol/clinicalDiagnosis/save" class="form-horizontal">
    <form:hidden path="id"/>
    <s:message code="dissection.protocol.clinical.daignosis.label"/>
    <form:textarea rows="22" path="clinicalDiagnosis" id="clinicalDiagnosis" cssClass="input-block-level"></form:textarea>
    <form:errors path="clinicalDiagnosis" cssClass="alert alert-error form-message"/>
    <div class="form-actions-left">
      <button type="submit" class="btn btn-primary pull-left">
        <s:message code="button.save"/>
      </button>
    </div>

  </form:form>

</dp:update>
</body>
</html>
