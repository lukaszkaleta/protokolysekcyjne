<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="dp" tagdir="/WEB-INF/tags/protocol/" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
  <title><s:message code="navigation.protocol.description"/></title>
</head>
<body>

<common:message-panel/>

<div class="row">
  <div class="span2">
    <common:link url="/settings/descriptionPointSource/start" code="description.settings.descriptionPointSource.show.link"/>
  </div>
  <div class="span2">
    <common:link url="/protocol/description/list/${dissectionProtocolId}" code="protocol.description.list.link"/>
  </div>
</div>

<dp:update updateDeniedMessageCode="description.provide.basic.data">

  <form:form method="post" action="/protocol/description/save/${dissectionProtocolId}" modelAttribute="descriptionForm">
    <c:forEach items="${descriptionForm.descriptionPoints}" var="descriptionPoint" varStatus="status">
      <c:set var="linkClass" value="${descriptionPoint.customization ? '' : 'undecorated'}"/>
      <div class="row-fluid" id="${descriptionPoint.id}">
        <div class="span1">
          <span class="badge badge-info">
            ${descriptionPoint.descriptionPointSource.point}. ${descriptionPoint.descriptionPointSource.digitPosition}
          </span>
        </div>
        <div class="span8">
          <form:hidden path="descriptionPoints[${status.index}].id"/>
          <form:hidden path="descriptionPoints[${status.index}].dissectionProtocolId"/>
          <form:hidden path="descriptionPoints[${status.index}].index"/>
          <form:hidden path="descriptionPoints[${status.index}].customization"/>
          <form:hidden path="descriptionPoints[${status.index}].descriptionPointSource.id"/>
          <form:hidden path="descriptionPoints[${status.index}].descriptionPointSource.type"/>
          <form:hidden path="descriptionPoints[${status.index}].descriptionPointSource.point"/>
          <form:hidden path="descriptionPoints[${status.index}].descriptionPointSource.position"/>
          <form:textarea rows="2" cols="50" path="descriptionPoints[${status.index}].descriptionPointSource.description" cssStyle="width: 100%"/>
          <form:errors path="descriptionPoints[${status.index}].descriptionPointSource.description" cssClass="help-inline alert-error" element="span"/>
        </div>
        <div class="span1">
          <a class="${linkClass}" href='<c:url value="/protocol/description/point/${descriptionPoint.id}"/>'>
            <s:message code="button.edit"/>
          </a>
        </div>
      </div>
    </c:forEach>
    <input value='<s:message code="button.save"/>' type="submit" class="btn btn-primary" style="position: fixed; bottom: 3em; right: 3em">

  </form:form>

</dp:update>
</body>
</html>
