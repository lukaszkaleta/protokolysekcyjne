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
    <title><s:message code="navigation.protocol.histopathologicalExamination"/></title>
</head>
<body>
<dp:update updateDeniedMessageCode="histopathological.examination.provide.basic.data">

  <common:message-panel/>

  <form:form commandName="histopathologicalExamination" action="${pageContext.request.contextPath}/protocol/histopathologicalExamination/nameChange" class="form-horizontal">
    <form:hidden path="dissectionProtocolId"/>
    <s:message code="dissection.protocol.histopathological.examination.type.label"/>
    <form:select path="name" id="typeSelect" cssClass="input-block-level" onchange="this.form.submit()">
      <c:forEach items="${histopathologicalExaminationNames}" var="histopathologicalExaminationName">
        <s:message code="dissection.protocol.histopathological.examination.name.${histopathologicalExaminationName}" var="histopathologicalExaminationNameLabel"/>
        <form:option value="${histopathologicalExaminationName}" label="${histopathologicalExaminationNameLabel}"/>
      </c:forEach>
    </form:select>
  </form:form>

  <form:form commandName="histopathologicalExamination" action="${pageContext.request.contextPath}/protocol/histopathologicalExamination/save" class="form">
    <form:hidden path="name"/>
    <form:hidden path="id"/>
    <form:hidden path="dissectionProtocolId"/>
    <s:message code="dissection.protocol.histopathological.examination.label"/>
    <form:textarea path="description" id="description" cssClass="input-block-level" rows="22"/>
    <form:errors path="description" cssClass="alert alert-error form-message"/>
    <s:message code="dissection.protocol.histopathological.examination.number.label"/>
    <form:input id="number" path="number"/>
    <s:message code="dissection.protocol.histopathological.examination.fromDay.label"/>
    <form:input id="fromDay" path="fromDay" cssClass="datepicker"/>
    <div class="form-actions-left">
      <button type="submit" class="btn btn-primary">
        <s:message code="button.save"/>
      </button>
      <c:if test="${histopathologicalExamination.id > 0}">
        <common:delete-link url="/protocol/histopathologicalExamination/delete/${histopathologicalExamination.dissectionProtocolId}/${histopathologicalExamination.id}">
          <div class="alert">
            <s:message code="dissection.protocol.histopathological.examination.delete.message"/>
          </div>
        </common:delete-link>
      </c:if>
    </div>
  </form:form>
</dp:update>
</body>
</html>
