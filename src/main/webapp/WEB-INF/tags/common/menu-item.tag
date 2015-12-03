<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<%@ attribute name="current" required="false" rtexprvalue="true" description="Current menu" %>
<%@ attribute name="expected" required="false" rtexprvalue="true" description="Expecte menu" %>
<%@ attribute name="simple" required="false" rtexprvalue="true" description="Simple mode" %>
<%@ attribute name="url" required="false" rtexprvalue="true" description="Expecte menu" %>
<%@ attribute name="label" required="false" rtexprvalue="true" description="Expecte menu" %>
<%@ attribute name="itemClass" required="false" rtexprvalue="true" description="Additional class for list item" %>

<li class="${itemClass} ${current eq expected ? 'active' : ''}">
    <c:choose>
        <c:when test="${simple}">
            <a href='<c:url value="${url}" />'>
                <s:message code="${label}"/>
            </a>
        </c:when>
        <c:otherwise>
            <jsp:body/>
        </c:otherwise>
    </c:choose>
</li>