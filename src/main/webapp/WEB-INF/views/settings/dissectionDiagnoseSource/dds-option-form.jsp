<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="settings" tagdir="/WEB-INF/tags/settings/" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@page pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
  <title>
    <c:choose>
      <c:when test="${dissectionDiagnoseSourceOption.id <= 0}">
        <s:message code="settings.dissection.diagnose.source.option.create" />
      </c:when>
      <c:otherwise>
        <s:message code="settings.dissection.diagnose.source.option.edit" />
      </c:otherwise>
    </c:choose>
  </title>
</head>
<body>

<form:form action="${pageContext.request.contextPath}/settings/dissectionDiagnoseSource/option/save" commandName="dissectionDiagnoseSourceOption">

  <form:hidden path="id" />
  <form:hidden path="dissectionDiagnoseSourceId" />

  <s:message code="dissection.diagnose.source.latin"/>
  <span class="input-block-level uneditable-input">${dissectionDiagnoseSource.name.latin}</span>

  <s:message code="dissection.diagnose.source.translated"/>
  <span class="input-block-level uneditable-input">${dissectionDiagnoseSource.name.translated}</span>

  <s:message code="dissection.diagnose.source.option.latin"/>
  <s:message var="ddsoLatinPlaceholder" code="dissection.diagnose.source.option.latin.placeholder"/>
  <form:input id="latinInput" path="name.latin" cssClass="input-block-level" placeholder="${ddsoLatinPlaceholder}" autocomplete="off"/>

  <s:message code="dissection.diagnose.source.option.translated"/>
  <s:message var="ddsoTranslatedPlaceholder" code="dissection.diagnose.source.option.translated.placeholder"/>
  <form:input id="translatedInput" path="name.translated" cssClass="input-block-level" placeholder="${ddsoTranslatedPlaceholder}" autocomplete="off"/>

  <s:hasBindErrors name="dissectionDiagnoseSourceOption">
    <div class="alert alert-error">
      <form:errors path="name.latin"  element="div"/>
      <form:errors path="name.translated" element="div"/>
    </div>
  </s:hasBindErrors>

  <div class="form-actions">
    <button type="submit" class="btn btn-primary">
      <s:message code="button.save"/>
    </button>
    <common:link url="/settings/dissectionDiagnoseSource/edit/${dissectionDiagnoseSource.id}" code="button.cancel" cssClass="btn"/>
    <c:if test="${dissectionDiagnoseSourceOption.id > 0}">
      <common:delete-link url="/settings/dissectionDiagnoseSource/option/delete/${dissectionDiagnoseSource.id}/${dissectionDiagnoseSourceOption.id}">
        <div class="alert">
          <s:message code="settings.dissection.diagnose.source.option.delete.question"/>
        </div>
      </common:delete-link>
    </c:if>
  </div>

</form:form>

</body>
</html>
