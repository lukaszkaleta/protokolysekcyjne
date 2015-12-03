<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<%@ attribute name="hospitalWard" required="true" rtexprvalue="true" description="value of the hospital ward object" type="com.ra.dissection.protocol.domain.settings.HospitalWard" %>

<a href='<c:url value="/settings/hospital/ward/edit/${hospitalWard.id}"/>' class="undecorated">
${hospitalWard.name}
<c:if test="${not empty hospitalWard.phone}">
  ,&nbsp;tel:${hospitalWard.phone}
</c:if>
</a>
