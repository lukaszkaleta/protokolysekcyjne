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
  <title><s:message code="navigation.main.settings.hospitals"/></title>
</head>
<body>

  <common:link url="/settings/hospital/new" code="settings.hospital.create" cssClass="pull-right btn btn-primary"/>

  <c:choose>
    <c:when test="${empty hospitals}">
      <fieldset>
        <legend><s:message code="settings.hospital.list.empty"/></legend>
      </fieldset>
      <b><common:link url="/settings/hospital/new" code="settings.hospital.list.empty.create.link"/></b>
    </c:when>
    <c:otherwise>
      <fieldset>
        <legend><s:message code="settings.hospital.list"/></legend>
      </fieldset>
      <c:forEach var="hospital" items="${hospitals}">

        <settings:hospital-display hospital="${hospital}"/>

        <c:choose>
          <c:when test="${empty hospitalWards[hospital.id]}">
            <s:message code="settings.hospital.ward.list.empty"/>
            <br/>
            <b><common:link url="/settings/hospital/ward/new/${hospital.id}" code="settings.hospital.ward.list.empty.create.link"/></b>
          </c:when>
          <c:otherwise>

            <a href="#" onclick="$('#hospital-${hospital.id}-ward').toggle();">
              <s:message code="settings.hospital.ward.list.expand"/>
            </a>
            <div id="hospital-${hospital.id}-ward" style="display: none">
              <c:forEach var="hospitalWard" items="${hospitalWards[hospital.id]}">
                <settings:hospital-ward-display hospitalWard="${hospitalWard}"/><br/>
              </c:forEach>
              <common:link url="/settings/hospital/ward/new/${hospital.id}" code="settings.hospital.ward.create.link"/>
            </div>
          </c:otherwise>
        </c:choose>
        <hr/>
      </c:forEach>
    </c:otherwise>
  </c:choose>
  <common:link url="/settings/hospital/new" code="settings.hospital.create" cssClass="pull-right btn btn-primary"/>
</body>
</html>
