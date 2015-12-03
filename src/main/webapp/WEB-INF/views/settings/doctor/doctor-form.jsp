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
      <c:when test="${doctor.id <= 0}">
        <s:message code="settings.doctor.create"/>
      </c:when>
      <c:otherwise>
        <s:message code="settings.doctor.edit"/>
      </c:otherwise>
    </c:choose>
  </title>
</head>
<body>

<form:form commandName="doctor" action="/settings/doctor/save" class="form-horizontal">

  <c:set var="firstNameErrors"><form:errors path="firstName"/></c:set>
  <c:set var="firstNameErr" value="${not empty firstNameErrors}"/>

  <c:set var="lastNameErrors"><form:errors path="lastName"/></c:set>
  <c:set var="lastNameErr" value="${not empty lastNameErrors}"/>

  <form:hidden path="id"/>
  <div class="control-group ${firstNameErr ? 'error' : ''}">
    <form:label cssClass="control-label" path="firstName">
      <s:message code="doctor.firstName.label"/>
    </form:label>
    <div class="controls">
      <form:input path="firstName" cssClass="input-xxlarge" />
      <form:errors cssClass="help-inline" path="firstName" element="span"/>
    </div>
  </div>
  <div class="control-group ${lastNameErr ? 'error' : ''}">
    <form:label cssClass="control-label" path="lastName">
      <s:message code="doctor.lastName.label"/>
    </form:label>
    <div class="controls">
      <form:input path="lastName" cssClass="input-xxlarge" />
      <form:errors cssClass="help-inline" path="lastName" element="span"/>
    </div>
  </div>
  <div class="control-group">
    <form:label cssClass="control-label" path="title">
      <s:message code="doctor.title.label"/>
    </form:label>
    <div class="controls">
      <form:input path="title" cssClass="input-xxlarge" />
    </div>
  </div>
  <div class="control-group">
    <form:label cssClass="control-label" path="phone">
      <s:message code="doctor.phone.label"/>
    </form:label>
    <div class="controls">
      <form:input path="phone" cssClass="input-xxlarge" />
    </div>
  </div>
  <div class="control-group">
    <div class="controls">
      <button type="submit" class="btn btn-primary">
        <s:message code="button.save"/>
      </button>
      <common:delete-link url="/settings/doctor/delete/${doctor.id}">
        <s:message code="doctor.delete.message"/>
        <settings:doctor-display doctor="${doctor}"/>
      </common:delete-link>
    </div>
  </div>
</form:form>

</body>
</html>