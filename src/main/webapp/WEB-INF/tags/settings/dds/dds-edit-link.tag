<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="dissectionDiagnoseSource" required="true" rtexprvalue="true" type="com.ra.dissection.protocol.domain.settings.DissectionDiagnoseSource" description="Dissection Diagnose Source object" %>
<%@ attribute name="dissectionDiagnoseSourceOptions" required="true" rtexprvalue="true" type="java.util.Map" description="options" %>

TODO: jak dynamicznie zrobic lating/translated
<a class="undecorated" href='<c:url value="/settings/dissectionDiagnoseSource/edit/${dissectionDiagnoseSource.id}"/>'>
  <c:out value="${dissectionDiagnoseSource.name.latin}"/>
</a>
<c:if test="${dissectionDiagnoseSource.dissectionDiagnoseSourceOptionAvailable">
  <ul>
    <c:forEach items="${dissectionDiagnoseSourceOptions[dissectionDiagnoseSource.id]}" var="dissectionDiagnoseSourceOption">
      <li>
        <a class="undecorated" href='<c:url value="/settings/dissectionDiagnoseSource/option/edit/${dissectionDiagnoseSourceOption.id}"/>'>
          <c:out value="${dissectionDiagnoseSourceOption.name.latin}"/>
        </a>
      </li>
    </c:forEach>
  </ul>
</c:if>
