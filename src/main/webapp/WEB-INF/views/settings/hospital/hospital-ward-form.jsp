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
      <c:when test="${hospitalWard.id <= 0}">
        <s:message code="settings.hospital.ward.create"/>
      </c:when>
      <c:otherwise>
        <s:message code="settings.hospital.ward.edit"/>
      </c:otherwise>
    </c:choose>
  </title>
</head>
<body>

<div class="row">
  <div class="span2">
    <c:url value="/settings/hospital/ward/image/${hospitalWard.id}" var="hospitalWardImageUrl"/>
    <img width="100px" height="100px" src="${hospitalWardImageUrl}" alt="[IMAGE]">
  </div>
  <div class="span9">
    <form:form commandName="hospitalWard" action="/settings/hospital/ward/save" class="form-horizontal">
      <c:set var="nameErrors"><form:errors path="name"/></c:set>
      <c:set var="nameErr" value="${not empty nameErrors}"/>

      <form:hidden path="hospitalId"/>
      <form:hidden path="id"/>

      <div class="control-group">
        <form:label cssClass="control-label" path="phone">
          <s:message code="hospital.label"/>
        </form:label>
        <div class="controls">
          <span class="input-block-level uneditable-input">${hospital.name}</span>
        </div>
      </div>

      <div class="control-group ${nameErr ? 'error' : ''}">
        <form:label cssClass="control-label" path="name">
          <s:message code="hospital.ward.name.label"/>
        </form:label>
        <div class="controls">
          <form:input path="name" cssClass="input-block-level"/>
          <form:errors cssClass="help-inline" path="name" element="span"/>
        </div>
      </div>

      <div class="control-group">
        <form:label cssClass="control-label" path="phone">
          <s:message code="hospital.ward.phone.label"/>
        </form:label>
        <div class="controls">
          <form:input path="phone" cssClass="input-xxlarge"/>
        </div>
      </div>
      <div class="control-group">
        <div class="controls">
          <label class="checkbox">
            <form:checkbox path="dissection"/> <s:message code="hospital.ward.dissection.label"/>
          </label>
        </div>
      </div>
      <div class="form-actions">
        <button type="submit" class="btn btn-primary">
          <s:message code="button.save"/>
        </button>
        <common:link url="/settings/hospital/edit/${hospitalWard.hospitalId}" code="Szpital" cssClass="btn"/>
        <common:link url="/settings/hospital/show" code="button.cancel" cssClass="btn"/>
        <common:delete-link url="/settings/hospital/ward/delete/${hospitalWard.id}">
          <div class="alert">
            <s:message code="settings.hospital.ward.delete.questions"/>
          </div>
          <p>
            ${hospitalWard.name}
          </p>
        </common:delete-link>
      </div>
    </form:form>

    <c:if test="${hospitalWard.id > 0 and hospitalWard.dissection}">
      <form:form modelAttribute="uploadItem" method="post" enctype="multipart/form-data" action="/settings/hospital/ward/addImage" class="form-horizontal">
        <input type="hidden" name="id" value="${hospitalWard.id}">
        <div class="control-group">
          <form:label cssClass="control-label" path="file">
            <s:message code="hospital.ward.image.label"/>
          </form:label>
          <div class="controls">
            <form:input path="file" type="file"/>
          </div>
        </div>
        <div class="form-actions">
          <button type="submit" class="btn btn-primary">
            <s:message code="button.add"/>
          </button>
        </div>
      </form:form>
    </c:if>
  </div>

</body>
</html>
