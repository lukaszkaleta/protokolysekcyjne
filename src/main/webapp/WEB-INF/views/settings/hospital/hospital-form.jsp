<%@ page import="java.util.Enumeration" %>
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
      <c:when test="${hospital.id <= 0}">
        <s:message code="settings.hospital.create"/>
      </c:when>
      <c:otherwise>
        <s:message code="settings.hospital.edit"/>
      </c:otherwise>
    </c:choose>
  </title>
</head>

<body>

<div class="row">
  <div class="span2">
    <c:url value="/settings/hospital/image/${hospital.id}" var="hospitalImageUrl"/>
    <img width="100px" height="100px" src="${hospitalImageUrl}" alt="[IMAGE]">
  </div>
  <div class="span9">
    <form:form commandName="hospital" action="${pageContext.request.contextPath}/settings/hospital/save" class="form-horizontal">
      <c:set var="nameErrors"><form:errors path="name"/></c:set>
      <c:set var="nameErr" value="${not empty nameErrors}"/>
      <form:hidden path="id"/>
      <div class="control-group ${nameErr ? 'error' : ''}">
        <form:label cssClass="control-label" path="name">
          <s:message code="hospital.name.label"/>
        </form:label>
        <div class="controls">
          <form:input path="name" cssClass="input-xxlarge"/>
          <form:errors cssClass="help-inline" path="name" element="span"/>
        </div>
      </div>
      <div class="control-group">
        <form:label cssClass="control-label" path="address.value">
          <s:message code="hospital.address.label"/>
        </form:label>
        <div class="controls">
          <form:input path="address.value" cssClass="input-xxlarge"/>
        </div>
      </div>
      <div class="control-group">
        <div class="controls">
          <s:message code="hospital.address.postCode.placeholder" var="postCodePlaceholder"/>
          <s:message code="hospital.address.city.placeholder" var="cityPlaceholder"/>
          <form:input path="address.postCode" placeholder="${postCodePlaceholder}" cssClass="input-small"/>
          <form:input path="address.city" placeholder="${cityPlaceholder}" cssClass="input-330"/>
        </div>
      </div>
      <div class="control-group">
        <form:label cssClass="control-label" path="phone">
          <s:message code="hospital.phone.label"/>
        </form:label>
        <div class="controls">
          <form:input path="phone" cssClass="input-xxlarge"/>
        </div>
      </div>
      <div class="form-actions">
        <button type="submit" class="btn btn-primary">
          <s:message code="button.save"/>
        </button>
        <common:link url="/settings/hospital/show" code="button.cancel" cssClass="btn"/>
      </div>
    </form:form>

    <c:if test="${hospital.id > 0}">
      <form:form modelAttribute="uploadItem" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/settings/hospital/addImage" class="form-horizontal">
        <input type="hidden" name="id" value="${hospital.id}">
        <div class="control-group">
          <form:label cssClass="control-label" path="file">
            <s:message code="hospital.image.label"/>
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
</div>



</body>
</html>
