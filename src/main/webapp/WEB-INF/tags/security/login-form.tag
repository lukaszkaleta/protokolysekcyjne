<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<form name='f' action='/protokolysekcyjne/j_spring_security_check' method='POST' class="form-signin">
    <h2 class="form-signin-heading">
        <s:message code="login.page.security.request"/>
    </h2>
    <s:message code="login.page.form.user" var="userPlaceholder"/>
    <input type="text" name='j_username' class="input-block-level" placeholder="${userPlaceholder}">
    <s:message code="login.page.form.password" var="passwordPlaceholder"/>
    <input type='password' name='j_password' class="input-block-level" placeholder="${passwordPlaceholder}">
    <br/>
    <button class="btn btn-primary" type="submit">
        <s:message code="login.page.form.login"/>
    </button>
</form>
