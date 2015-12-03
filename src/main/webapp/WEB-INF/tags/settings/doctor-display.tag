<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<%@ attribute name="doctor" required="true" rtexprvalue="true" description="value of the doctor object" type="com.ra.dissection.protocol.domain.settings.Doctor" %>

${doctor.title}&nbsp;<b>${doctor.firstName}&nbsp;${doctor.lastName}</b>
<c:if test="${not empty doctor.phone}">
  ,&nbsp;tel:&nbsp;${doctor.phone}
</c:if>

