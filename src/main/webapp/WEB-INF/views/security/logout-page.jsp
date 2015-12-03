<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dps" tagdir="/WEB-INF/tags/security/" %>
<%@page pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>

    <meta charset="utf-8">
    <title><s:message code="login.page.security.request"/></title>
</head>

<body>
<div class="alert alert-info form-message">
    <s:message code="logout.message"/>
    <a href='<c:url value="/security/login" />'>
        <s:message code="logout.again.login"/>
    </a>
</div>
</body>
</html>
