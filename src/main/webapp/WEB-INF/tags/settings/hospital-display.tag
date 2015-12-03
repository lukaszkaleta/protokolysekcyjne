<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ attribute name="hospital" required="true" rtexprvalue="true" description="value of the hospital object"
              type="com.ra.dissection.protocol.domain.settings.Hospital" %>

<a href='<c:url value="/settings/hospital/edit/${hospital.id}"/>' class="undecorated">
  <div class="row">
    <div class="span12">
      <h4>
        ${hospital.name}
      </h4>
    </div>
  </div>
  <div class="row">
    <div class="span2">
      <c:url value="/settings/hospital/image/${hospital.id}" var="hospitalImageUrl"/>
      <img src="${hospitalImageUrl}" alt="[IMAGE]">
    </div>
    <div class="span8">
      ${hospital.address.value}<br/>${hospital.address.postCode}&nbsp;${hospital.address.city}
      <c:if test="${not empty hospital.phone}">
        <br/>
        tel:${hospital.phone}
      </c:if>
    </div>
  </div>
</a>
