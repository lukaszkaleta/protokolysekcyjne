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

<form:form action="/protocol/description/update/${descriptionPoint.id}${'#'}${descriptionPoint.id}" commandName="descriptionPoint" class="form-horizontal">

  <form:hidden path="id"/>
  <form:hidden path="dissectionProtocolId"/>
  <form:hidden path="index"/>
  <form:hidden path="customization"/>
  <form:hidden path="descriptionPointSource.id"/>
  <form:hidden path="descriptionPointSource.type"/>

  <div class="control-group">
    <label class="control-label">
      <s:message code="protocol.description.point.type"/>
    </label>
    <div class="controls">
      <s:message code="protocol.description.point.type.${descriptionPoint.descriptionPointSource.type}${descriptionPoint.customization ? '.customized' : ''}"/>
    </div>
  </div>

  <c:if test="${pointDissectionDiagnose != null}">
    <div class="control-group">
      <label class="control-label">
        <s:message code="dissection.diagnose.source.latin"/>
      </label>
      <div class="controls">
        <span class="input-block-level uneditable-input">${pointDissectionDiagnose.name.latin}</span>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label">
        <s:message code="dissection.diagnose.source.translated"/>
      </label>
      <div class="controls">
        <span class="input-block-level uneditable-input">${pointDissectionDiagnose.name.translated}</span>
      </div>
    </div>
  </c:if>

  <!-- Description -->
  <c:set var="descriptionErrors"><form:errors path="descriptionPointSource.description"/></c:set>
  <c:set var="descriptionErr" value="${not empty descriptionErrors}"/>

  <div class="control-group ${descriptionErr ? 'error' : ''}">
    <label class="control-label" for="description">
      <s:message code="description.point.source.description"/>
    </label>
    <div class="controls">
      <form:textarea rows="8" path="descriptionPointSource.description" cssClass="input-block-level"></form:textarea>
      <form:errors path="descriptionPointSource.description" cssClass="help-inline" element="span"/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label" for="point">
      <s:message code="description.point.source.position"/>
    </label>
    <div class="controls">
      <settings:description-point-select path="descriptionPointSource"/>
    </div>
  </div>

  <div class="form-actions">
    <button type="submit" class="btn btn-primary">
      <s:message code="button.save"/>
    </button>

    <c:choose>
    <c:when test="${pointDissectionDiagnose == null}">
      <common:delete-link url="/protocol/description/point/delete/${descriptionPoint.dissectionProtocolId}/${descriptionPoint.id}"
                          headerCode="description.point.delete.from.protocol">
        <div class="alert">
          <s:message code="description.point.delete.from.protocol.confirm" arguments="${descriptionPoint.descriptionPointSource.digitPosition},${descriptionPoint.descriptionPointSource.point}"/>
        </div>
        <p>
            ${descriptionPoint.descriptionPointSource.description}
        </p>
      </common:delete-link>
    </c:when>
    <c:otherwise>
      <div class="pull-right">
        <s:message code="description.point.delete.from.protocol.condition"/>
      </div>
    </c:otherwise>
    </c:choose>
  </div>

</form:form>

</body>
</html>
