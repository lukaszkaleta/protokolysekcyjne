<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dps" tagdir="/WEB-INF/tags/security/" %>
<%@page pageEncoding="UTF-8" %>

<div class="alert alert-error form-message">
    <s:message code="login.page.failure"/>
</div>
<dps:login-form/>
