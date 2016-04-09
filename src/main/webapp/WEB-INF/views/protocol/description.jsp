<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dp" tagdir="/WEB-INF/tags/protocol" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
  <title><s:message code="navigation.protocol.description"/></title>
</head>
<body>

<common:message-panel/>

<div class="row-fluid">
  <div class="span3">
    <common:link url="/settings/descriptionPointSource/start" code="description.settings.descriptionPointSource.show.link"/>
  </div>
  <div class="span5">
    <common:link url="/protocol/description/list/${dissectionProtocolId}" code="protocol.description.list.link"/>
  </div>
</div>

<dp:update updateDeniedMessageCode="description.provide.basic.data">

  <form:form method="post" action="${pageContext.request.contextPath}/protocol/description/save/${dissectionProtocolId}" modelAttribute="descriptionForm">
    <c:forEach items="${descriptionForm.descriptionPoints}" var="descriptionPoint" varStatus="status">

      <div style="border-bottom: dotted 1px lightgray; padding-bottom: 3px;" class="descripiton-point" id="${descriptionPoint.id}">
        <div class="row-fluid">
          <span class="span1">
            <span class="badge badge-info">
                ${descriptionPoint.descriptionPointSource.point}. ${descriptionPoint.descriptionPointSource.digitPosition}
            </span>
          </span>
          <span class="span1">
            <div class="badge" data-toggle="tooltip" data-placement="left" title='<s:message code="protocol.description.point.type.${descriptionPoint.descriptionPointSource.type}${descriptionPoint.customization ? '.customized' : ''}"/>'>
              <s:message code="protocol.description.point.type.key.${descriptionPoint.descriptionPointSource.type}${descriptionPoint.customization ? '.customized' : ''}"/>
            </div>
          </span>
          <span class="span1">
            <a class="info" href='<c:url value="/protocol/description/point/${descriptionPoint.id}"/>'>
              <s:message code="button.edit"/>
            </a>
          </span>
          <span class="span9">
            <span class="pull-right">
              <dp:delete-description-point-link allowed="${changedDescriptionPointIds[descriptionPoint.id] == null}" descriptionPoint="${descriptionPoint}"/>
            </span>
          </span>
        </div>
        <div class="row-fluid">
          <div class="span12">
            <form:hidden path="descriptionPoints[${status.index}].id"/>
            <form:hidden path="descriptionPoints[${status.index}].dissectionProtocolId"/>
            <form:hidden path="descriptionPoints[${status.index}].index"/>
            <form:hidden path="descriptionPoints[${status.index}].customization"/>
            <form:hidden path="descriptionPoints[${status.index}].descriptionPointSource.id"/>
            <form:hidden path="descriptionPoints[${status.index}].descriptionPointSource.type"/>
            <form:hidden path="descriptionPoints[${status.index}].descriptionPointSource.point"/>
            <form:hidden path="descriptionPoints[${status.index}].descriptionPointSource.position"/>
            <div class="descriptionPointContainer">
              <form:textarea path="descriptionPoints[${status.index}].descriptionPointSource.description" cssClass="js-auto-size descriptionPointTextArea" rows="1"/>
            </div>
            <form:errors path="descriptionPoints[${status.index}].descriptionPointSource.description" cssClass="help-inline alert-error" element="span"/>
          </div>
        </div>
      </div>

    </c:forEach>
    <input value='<s:message code="button.save"/>' type="submit" class="btn btn-primary" style="position: fixed; bottom: 3em; right: 3em">

  </form:form>

  <script type="application/javascript">
    $( document ).ready(function() {

      $('.descriptionPointTextArea').textareaAutoSize();
    });

    $('.descripiton-point').hover(function() {
      $(this).css('background', '#ECECEC');
    },function(element){
      $(this).css('background', 'white');
    });
  </script>

</dp:update>
</body>
</html>
