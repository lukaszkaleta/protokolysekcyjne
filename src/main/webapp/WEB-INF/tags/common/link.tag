<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<%@ attribute name="url" required="true" rtexprvalue="true" description="Link url" %>
<%@ attribute name="code" required="true" rtexprvalue="true" type="java.lang.String" description="Message" %>
<%@ attribute name="cssClass" required="false" rtexprvalue="true" description="class for a element" %>

<a href='<c:url value="${url}" />' class="${cssClass}">
  <s:message code="${code}"/>
</a>