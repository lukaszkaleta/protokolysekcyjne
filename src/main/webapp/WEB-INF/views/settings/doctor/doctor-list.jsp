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
  <title><s:message code="navigation.main.settings.doctors"/></title>
</head>
<body>

<common:link url="/settings/doctor/new" code="settings.doctor.create" cssClass="pull-right btn btn-primary"/>

<c:choose>
  <c:when test="${empty doctors}">
    <fieldset>
      <legend><s:message code="settings.doctor.list.empty"/></legend>
    </fieldset>
    <b><common:link url="/settings/doctor/new" code="settings.doctor.list.empty.create.link"/></b>
  </c:when>
  <c:otherwise>
    <fieldset>
      <legend><s:message code="settings.doctor.list"/></legend>
    </fieldset>
      <c:forEach var="doctor" items="${doctors}">
        <a href='<c:url value="/settings/doctor/edit/${doctor.id}"/>' class="undecorated">
          <settings:doctor-display doctor="${doctor}"/>
        </a>
      <hr/>
    </c:forEach>
  </c:otherwise>
</c:choose>

<common:link url="/settings/doctor/new" code="settings.doctor.create" cssClass="pull-right btn btn-primary"/>

</body>
</html>
