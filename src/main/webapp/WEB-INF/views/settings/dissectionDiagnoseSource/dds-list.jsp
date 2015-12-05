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
  <title><s:message code="navigation.main.settings.dissection.diagnose.source"/></title>
</head>
<body>

<common:link url="/settings/dissectionDiagnoseSource/new" code="settings.dissection.diagnose.source.new.link" cssClass="pull-right btn btn-primary"/>

<form:form action="${pageContext.request.contextPath}/settings/dissectionDiagnoseSource/filter" commandName="dissectionDiagnoseSourceFilter" id="filterForm">
  <form:hidden path="letter" id="letter"/>
  <div class="btn-toolbar">
    <div class="btn-group">
      <c:forEach items="${letters}" var="letter">
        <c:choose>
          <c:when test="${dissectionDiagnoseSourceFilter.letter eq letter}">
            <a class="btn btn-primary" onclick="$('#letter').val('${letter}');$('#filterForm').submit();">${letter}</a>
          </c:when>
          <c:otherwise>
            <a class="btn" onclick="$('#letter').val('${letter}');$('#filterForm').submit();">${letter}</a>
          </c:otherwise>
        </c:choose>
      </c:forEach>
    </div>
  </div>

  <label class="checkbox inline">
    <form:checkbox path="dissectionProtocolCategory.adult" id="adult-category" onclick="this.form.submit()"/> <s:message code="dissection.protocol.category.ADULT"/>
  </label>
  <label class="checkbox inline">
    <form:checkbox path="dissectionProtocolCategory.newborn" id="newborn-category" onclick="this.form.submit()"/> <s:message code="dissection.protocol.category.NEWBORN"/>
  </label>
  <label class="checkbox inline">
    <form:checkbox path="dissectionProtocolCategory.fetus" id="fetus-category" onclick="this.form.submit()"/> <s:message code="dissection.protocol.category.FETUS"/>
  </label>
</form:form>

<table class="table table-condensed table-bordered">
  <thead>
    <tr>
      <th><s:message code="dissection.diagnose.source.latin"/></th>
      <th><s:message code="dissection.diagnose.source.translated"/></th>
      <th></th>
    </tr>
  </thead>
  <c:choose>
    <c:when test="${empty dissectionDiagnoseSources}">
      <tfoot>
      <tr>
        <th colspan="3">
          <s:message code="settings.dissection.diagnose.source.empty"/>
          <common:link url="/settings/dissectionDiagnoseSource/new" code="settings.dissection.diagnose.source.empty.start"/>
        </th>
      </tr>
      </tfoot>
    </c:when>
    <c:otherwise></c:otherwise>
  </c:choose>
  <tbody>
  <c:forEach items="${dissectionDiagnoseSources}" var="dissectionDiagnoseSource">
    <tr>
      <td>
        <a class="undecorated" href='<c:url value="/settings/dissectionDiagnoseSource/edit/${dissectionDiagnoseSource.id}"/>'>
          <c:out value="${dissectionDiagnoseSource.name.latin}"/>
        </a>
        <c:if test="${dissectionDiagnoseSource.dissectionDiagnoseSourceOptionAvailable}">
          <ul class="unstyled">
            <c:forEach items="${dissectionDiagnoseSourceOptions[dissectionDiagnoseSource.id]}" var="dissectionDiagnoseSourceOption">
              <li>
                <span class="sortIndex">
                  ${dissectionDiagnoseSourceOption.sortIndex}.
                </span>
                <a class="undecorated" href='<c:url value="/settings/dissectionDiagnoseSource/option/edit/${dissectionDiagnoseSource.id}/${dissectionDiagnoseSourceOption.id}"/>'>
                  <c:out value="${dissectionDiagnoseSourceOption.name.latin}"/>
                </a>
              </li>
            </c:forEach>
          </ul>
        </c:if>
      </td>
      <td>
        <a class="undecorated" href='<c:url value="/settings/dissectionDiagnoseSource/edit/${dissectionDiagnoseSource.id}"/>'>
          <c:out value="${dissectionDiagnoseSource.name.translated}"/>
        </a>
        <c:if test="${dissectionDiagnoseSource.dissectionDiagnoseSourceOptionAvailable}">
          <ul class="unstyled">
            <c:forEach items="${dissectionDiagnoseSourceOptions[dissectionDiagnoseSource.id]}" var="dissectionDiagnoseSourceOption">
              <li>
                <span class="sortIndex">
                  ${dissectionDiagnoseSourceOption.sortIndex}.
                </span>
                <a class="undecorated" href='<c:url value="/settings/dissectionDiagnoseSource/option/edit/${dissectionDiagnoseSource.id}/${dissectionDiagnoseSourceOption.id}"/>'>
                  <c:out value="${dissectionDiagnoseSourceOption.name.translated}"/>
                </a>
              </li>
            </c:forEach>
          </ul>
        </c:if>
      </td>
      <td>
        <c:choose>
          <c:when test="${dissectionDiagnoseSource.type eq 'MICROSCOPIC'}">
            <span class="label label-success">
              <s:message code="dissection.diagnose.type.MICROSCOPIC.info"/>
            </span>
          </c:when>
          <c:when test="${dissectionDiagnoseSource.type eq 'MACROSCOPIC'}">
            <c:set var="descriptionStyle" value="${dissectionDiagnoseSource.descriptionPointSourceAvailable ? 'btn' : 'btn btn-danger'}"/>
            <common:link url="/settings/descriptionPointSource/edit/${dissectionDiagnoseSource.descriptionPointSourceId}" code="description.point.source.description" cssClass="${descriptionStyle}" />
          </c:when>
        </c:choose>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>

</body>
</html>