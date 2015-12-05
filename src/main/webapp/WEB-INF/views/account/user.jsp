<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="settings" tagdir="/WEB-INF/tags/settings" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@page pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
  <title><s:message code="navigation.main.security.account"/></title>
</head>
<body>

<c:if test="${passwordChanged}">
  <div class="alert alert-success">
    <s:message code="security.account.password.changed"/>
  </div>
</c:if>

<form:form commandName="passwordChangeModel" action="${pageContext.request.contextPath}/account/user/changePassword" class="form-horizontal">
  <c:set var="usernameErrors"><form:errors path="username"/></c:set>
  <c:set var="usernameErr" value="${not empty usernameErrors}"/>
  <c:set var="currentPasswordErrors"><form:errors path="currentPassword"/></c:set>
  <c:set var="currentPasswordErr" value="${not empty currentPasswordErrors}"/>
  <c:set var="newPasswordErrors"><form:errors path="newPassword"/></c:set>
  <c:set var="newPasswordErr" value="${not empty newPasswordErrors}"/>
  <c:set var="confirmPasswordErrors"><form:errors path="confirmPassword"/></c:set>
  <c:set var="confirmPasswordErr" value="${not empty confirmPasswordErrors}"/>

  <div class="control-group ${usernameErr ? 'error' : ''}">
    <form:label cssClass="control-label" path="username">
      <s:message code="account.username"/>
    </form:label>
    <div class="controls">
      <span class="input-xlarge uneditable-input">${passwordChangeModel.username}</span>
      <form:hidden path="username"/>
      <form:errors cssClass="help-inline" path="username" element="span"/>
    </div>
  </div>

  <div class="control-group ${currentPasswordErr ? 'error' : ''}">
    <form:label cssClass="control-label" path="currentPassword">
      <s:message code="account.currentPassword"/>
    </form:label>
    <div class="controls">
      <form:password path="currentPassword" cssClass="input-xlarge"/>
      <form:errors cssClass="help-inline" path="currentPassword" element="span"/>
    </div>
  </div>

  <div class="control-group ${newPasswordErr ? 'error' : ''}">
    <form:label cssClass="control-label" path="newPassword">
      <s:message code="account.newPassword"/>
    </form:label>
    <div class="controls">
      <form:password path="newPassword" cssClass="input-xlarge"/>
      <form:errors cssClass="help-inline" path="newPassword" element="span"/>
    </div>
  </div>

  <div class="control-group ${confirmPasswordErr ? 'error' : ''}">
    <form:label cssClass="control-label" path="confirmPassword">
      <s:message code="account.confirmPassword"/>
    </form:label>
    <div class="controls">
      <form:password path="confirmPassword" cssClass="input-xlarge"/>
      <form:errors cssClass="help-inline" path="confirmPassword" element="span"/>
    </div>
  </div>

  <div class="form-actions">
    <button type="submit" class="btn btn-primary">
      <s:message code="button.save"/>
    </button>
  </div>
</form:form>

</body>
</html>
