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
        <common:link url="/protocol/description/show/${dissectionProtocolId}" code="protocol.description.list.form.link"/>
    </div>
</div>

<dp:update updateDeniedMessageCode="description.provide.basic.data">
    <c:forEach items="${descriptionPointMap}" var="descriptionPointEntry">
        <c:forEach items="${descriptionPointEntry.value}" var="descriptionPoint">
            <c:set var="linkClass" value="${descriptionPoint.customization ? '' : 'undecorated'}"/>
            <div class="row-fluid" id="${descriptionPoint.id}">
                <div class="span1">
                    <span class="badge badge-info">
                        ${descriptionPointEntry.key}. ${descriptionPoint.descriptionPointSource.digitPosition}
                    </span>
                </div>
                <div class="span9">
                    <a class="${linkClass}" href='<c:url value="/protocol/description/point/${descriptionPoint.id}"/>'>
                            ${descriptionPoint.descriptionPointSource.description}
                    </a>
                </div>
            </div>
        </c:forEach>
    </c:forEach>
</dp:update>

</body>
</html>
