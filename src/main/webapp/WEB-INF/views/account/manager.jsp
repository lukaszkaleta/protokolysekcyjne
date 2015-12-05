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
  <title><s:message code="navigation.main.security.manager"/></title>
</head>
<body>

<c:if test="${createdUserAccount != null}">
  <div class="alert alert-success">
    <s:message code="security.management.user.created" arguments="${createdUserAccount}"/>
  </div>
</c:if>
<c:if test="${deletedUserAccount != null}">
  <div class="alert alert-success">
    <s:message code="security.management.user.deleted" arguments="${deletedUserAccount}"/>
  </div>
</c:if>
<c:if test="${resetedUserAccount != null}">
  <div class="alert alert-success">
    <s:message code="security.management.user.reseted" arguments="${resetedUserAccount}"/>
  </div>
</c:if>

<form:form class="form-inline" commandName="newUserAccount" action="${pageContext.request.contextPath}/account/manager/add">
  <s:message code="security.management.new.user"/>
  <s:message code="account.username" var="usernamePlaceholder"/>
  <form:input path="username" cssClass="input-large" placeholder="${usernamePlaceholder}"/>
  <c:forEach items="${roleNames}" var="roleName" varStatus="roleNameStatus">
    <label class="checkbox">
      <input type="checkbox" value="${roleName}" name="roles" id="${roleNameStatus.count}"> <s:message code="account.role.${roleName}"/>
    </label>
  </c:forEach>
  <button type="submit" class="btn"><s:message code="button.add"/></button>
</form:form>

<c:forEach items="${userAccounts}" var="userAccount">
  <div class="bs-docs-example">
    <form:form action="${pageContext.request.contextPath}/account/manager/delete" commandName="existingUserAccount" cssClass="pull-right">
      <input type="hidden" name="username" value="${userAccount.username}">
      <button type="submit" class="btn-mini btn-inverse">
        <s:message code="button.delete"/>
      </button>
    </form:form>
      <form:form action="${pageContext.request.contextPath}/account/manager/reset" commandName="existingUserAccount" cssClass="pull-right">
        <input type="hidden" name="username" value="${userAccount.username}">
        <button type="submit" class="btn-mini">
          <s:message code="button.reset"/>
        </button>
      </form:form>

    <dl class="dl-horizontal" style="margin: 0px !important;">
      <dt>
        <s:message code="account.username"/>:
      </dt>
      <dd>
          ${userAccount.username}
      </dd>
      <dt>
        <s:message code="account.role"/>:
      </dt>
      <dd>
        <c:forEach items="${userAccount.roles}" var="role">
          <c:choose>
            <c:when test="${role eq 'ADMIN'}">
                <span class="label label-important">
                  <s:message code="account.role.${role}"/>
                </span>
            </c:when>
            <c:when test="${role eq 'USER'}">
                <span class="label">
                  <s:message code="account.role.${role}"/>
                </span>
            </c:when>
          </c:choose>
        </c:forEach>
      </dd>
    </dl>
    </a>
  </div>

</c:forEach>

</body>
</html>
