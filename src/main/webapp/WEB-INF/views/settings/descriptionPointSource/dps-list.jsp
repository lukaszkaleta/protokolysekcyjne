<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@page pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
  <title><s:message code="navigation.main.settings.description.point.source"/></title>
</head>
<body>

<common:link url="/settings/descriptionPointSource/new/${dissectionProtocolCategoryName}" code="settings.description.point.source.new.link" cssClass="pull-right btn btn-primary"/>

<ul class="nav nav-tabs">
  <li class="${dissectionProtocolCategoryName eq 'ADULT' ? 'active' : ''}">
    <a href='<c:url value="/settings/descriptionPointSource/filter/adult" />'>
      <s:message code="dissection.protocol.category.ADULT"/>
    </a>
  </li>
  <li class="${dissectionProtocolCategoryName eq 'FETUS' ? 'active' : ''}">
    <a href='<c:url value="/settings/descriptionPointSource/filter/fetus" />'>
      <s:message code="dissection.protocol.category.FETUS"/>
    </a>
  </li>
  <li class="${dissectionProtocolCategoryName eq 'NEWBORN' ? 'active' : ''}">
    <a href='<c:url value="/settings/descriptionPointSource/filter/newborn" />'>
      <s:message code="dissection.protocol.category.NEWBORN"/>
    </a>
  </li>
</ul>

<%
  java.util.Map<Integer, String> subPointMap = new java.util.HashMap<Integer, String>();
  subPointMap.put(1, "a");
  subPointMap.put(2, "b");
  subPointMap.put(3, "c");
  subPointMap.put(4, "d");
  subPointMap.put(5, "e");
  subPointMap.put(6, "f");
  subPointMap.put(7, "g");
  subPointMap.put(8, "h");
  subPointMap.put(9, "i");
  subPointMap.put(10, "j");
  subPointMap.put(11, "k");
  subPointMap.put(12, "l");
  subPointMap.put(13, "m");
  subPointMap.put(14, "n");
  subPointMap.put(15, "o");
  subPointMap.put(16, "p");
  subPointMap.put(17, "r");
  subPointMap.put(18, "s");
  subPointMap.put(19, "t");
  subPointMap.put(20, "u");
  subPointMap.put(21, "w");
  subPointMap.put(22, "x");
  subPointMap.put(23, "y");
  subPointMap.put(24, "z");
  request.setAttribute("subPointMap", subPointMap);
%>
<ul class="unstyled">
  <c:forEach items="${points}" var="point">
    <li>
        <b>${point.key}.</b>
        <c:forEach items="${point.value}" var="pointEntry">
            <a class="undecorated" href='<c:url value="/settings/descriptionPointSource/edit/${pointEntry.id}"/>'>
              <i>${subPointMap[pointEntry.position]})</i> ${pointEntry.description};<span style="padding-right: 2em; "/>
            </a>
        </c:forEach>
        <common:link url="/settings/descriptionPointSource/newAt/${dissectionProtocolCategoryName}/${point.key}" code="link.add.next"/>
      <hr/>
    </li>
  </c:forEach>
</ul>

<common:link url="/settings/descriptionPointSource/new/${dissectionProtocolCategoryName}" code="settings.description.point.source.new.link" cssClass="pull-right btn btn-primary"/>
</body>
</html>