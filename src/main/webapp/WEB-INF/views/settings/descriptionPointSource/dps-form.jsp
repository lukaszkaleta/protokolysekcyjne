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
    <s:message code="settings.description.point.source.edit.${descriptionPointSource.type}"/>
  </title>
</head>
<body>


<form:form action="${pageContext.request.contextPath}/settings/descriptionPointSource/save" commandName="descriptionPointSource" class="form-horizontal">

  <c:if test="${dissectionDiagnoseSource != null}">
    <s:message code="dissection.diagnose.source.latin"/>
    <span class="input-block-level uneditable-input">${dissectionDiagnoseSource.name.latin}</span>
    <br/><br/>
    <s:message code="dissection.diagnose.source.translated"/>
    <span class="input-block-level uneditable-input">${dissectionDiagnoseSource.name.translated}</span>
    <br/><br/>
  </c:if>

  <form:hidden path="id"/>
  <form:hidden path="type"/>

  <!-- Description -->
  <c:set var="descriptionErrors"><form:errors path="description"/></c:set>
  <c:set var="descriptionErr" value="${not empty descriptionErrors}"/>

  <div class="control-group ${descriptionErr ? 'error' : ''}">
    <label class="control-label" for="description">
      <s:message code="description.point.source.description"/>
    </label>
    <div class="controls">
      <form:textarea rows="8" path="description" cssClass="input-block-level"></form:textarea>
      <form:errors path="description" cssClass="help-inline" element="span"/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label" for="point">
      <s:message code="description.point.source.position"/>
    </label>
    <div class="controls">
      <settings:description-point-select/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="categoryAdult">
      <s:message code="description.point.source.category"/>
    </label>
    <div class="controls">
      <label class="checkbox inline">
        <form:checkbox path="category.adult" id="categoryAdult"/> <s:message code="dissection.protocol.category.ADULT"/>
      </label>
      <label class="checkbox inline">
        <form:checkbox path="category.newborn" id="categoryNewborn"/> <s:message code="dissection.protocol.category.NEWBORN"/>
      </label>
      <label class="checkbox inline">
        <form:checkbox path="category.fetus" id="categoryFetus"/> <s:message code="dissection.protocol.category.FETUS"/>
      </label>
    </div>
  </div>
  <div class="form-actions">
    <button type="submit" class="btn btn-primary">
      <s:message code="button.save"/>
    </button>
    <c:if test="${descriptionPointSource.id > 0}">
      <common:delete-link url="/settings/descriptionPointSource/delete/${descriptionPointSource.id}" labelCode="settings.description.point.source.delete.${descriptionPointSource.type}">
        <div class="alert">
          <s:message code="settings.description.point.source.delete.question.${descriptionPointSource.type}"/>
        </div>
      </common:delete-link>
    </c:if>
    <c:choose>
      <c:when test="${descriptionPointSource.type eq 'GENERAL'}">
        <common:link url="/settings/descriptionPointSource/start" code="button.cancel" cssClass="btn"/>
      </c:when>
      <c:when test="${descriptionPointSource.type eq 'DIAGNOSE'}">
        <c:choose>
          <c:when test="${dissectionDiagnoseSource == null}">
            <common:link url="/settings/dissectionDiagnoseSource/start" code="button.cancel" cssClass="btn"/>
          </c:when>
          <c:otherwise>
            <common:link url="/settings/dissectionDiagnoseSource/edit/${dissectionDiagnoseSource.id}" code="button.cancel" cssClass="btn"/>
          </c:otherwise>
        </c:choose>
      </c:when>
    </c:choose>
  </div>
</form:form>

<c:if test="${dissectionDiagnoseSource == null}">
  <table class="table table-condensed table-bordered">
    <thead>
    <c:choose>
      <c:when test="${empty pointDissectionDiagnoseSources}">
        <tr>
          <th colspan="3">
            <s:message code="description.point.source.with.replacement.from.dissection.diagnose.source.table.empty"/>
          </th>
        </tr>
      </c:when>
      <c:otherwise>
        <tr>
          <th colspan="3"><s:message code="description.point.source.with.replacement.from.dissection.diagnose.source.table"/></th>
        </tr>
        <tr>
          <th><s:message code="dissection.diagnose.source.latin"/></th>
          <th><s:message code="dissection.diagnose.source.translated"/></th>
          <th></th>
        </tr>
      </c:otherwise>
    </c:choose>
    </thead>
    <tbody>
    <c:forEach items="${pointDissectionDiagnoseSources}" var="dissectionDiagnoseSource">
      <tr>
        <td>
          <a class="undecorated" href='<c:url value="/settings/dissectionDiagnoseSource/edit/${dissectionDiagnoseSource.id}"/>'>
            <c:out value="${dissectionDiagnoseSource.name.latin}"/>
          </a>
        </td>
        <td>
          <a class="undecorated" href='<c:url value="/settings/dissectionDiagnoseSource/edit/${dissectionDiagnoseSource.id}"/>'>
            <c:out value="${dissectionDiagnoseSource.name.translated}"/>
          </a>
        </td>
        <td>
          <c:set var="descriptionStyle" value="${dissectionDiagnoseSource.descriptionPointSourceAvailable ? 'btn' : 'btn btn-danger'}"/>
          <common:link url="/settings/descriptionPointSource/edit/${dissectionDiagnoseSource.descriptionPointSourceId}" code="description.point.source.description" cssClass="${descriptionStyle}" />
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</c:if>
</body>
</html>
