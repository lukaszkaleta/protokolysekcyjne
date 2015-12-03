<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ attribute name="path" required="true" rtexprvalue="true" description="Path for element getter/setter" %>
<%@ attribute name="labelKey" required="true" rtexprvalue="true" description="Label for element" %>

<div class="control-group">
    <form:label cssClass="control-label" path="${path}">
        <spring:message code="${labelKey}"/>
    </form:label>
    <div class="controls">
        <form:input path="${path}" />
    </div>
</div>