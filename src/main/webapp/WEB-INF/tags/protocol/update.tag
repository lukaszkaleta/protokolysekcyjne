<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<%@ attribute name="updateDeniedMessageCode" required="true" rtexprvalue="true" description="Message which does not allow to update protocol" type="java.lang.String" %>

<c:set var="protocolActive" value="${dissectionProtocol != null && dissectionProtocol.id > 0}"/>
<c:set var="protocolIdActive" value="${dissectionProtocolId > 0}"/>

<c:choose>
    <c:when test="${protocolActive || protocolIdActive}">
      <jsp:doBody/>
    </c:when>
    <c:otherwise>
      <div class="alert alert-block">
        <s:message code="${updateDeniedMessageCode}"/>
        <br/>
        <a href='<c:url value="/protocol/basic/start" />'>
          <s:message code="dissection.protocol.create.howto"/>
        </a>
      </div>
    </c:otherwise>
</c:choose>
