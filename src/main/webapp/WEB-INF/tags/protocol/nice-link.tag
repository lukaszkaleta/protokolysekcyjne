<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<%@ attribute name="dissectionProtocol" required="true" rtexprvalue="true" description="value of the doctor object"
              type="com.ra.dissection.protocol.domain.protocol.DissectionProtocol" %>

<c:url var="dissectionProtocolLink" value="/protocol/basic/show/${dissectionProtocol.id}"/>

<div class="bs-docs-example">
  <a href="${dissectionProtocolLink}" style="text-decoration:  none;">

    <!--
    <c:if test="${dissectionProtocol.category != null}">
          <span class="pull-right label label-info">
            <s:message code="dissection.protocol.category.${dissectionProtocol.category}"/>
          </span>
    </c:if>
    -->

    <dl class="dl-horizontal" style="margin: 0px !important;">
      <dt>
        <c:if test="${dissectionProtocol.status eq 'FINISHED'}">
          <s:message code="protocol.status.finished.info" var="protocolFinishedTooltip"/>
          <i class="icon-ok-sign pull-left" data-toggle="tooltip" title="${protocolFinishedTooltip}"></i>
        </c:if>
        <s:message code="search.nice.link.autopsy"/>:
      </dt>
      <dd>
        <span class="badge">${dissectionProtocol.number}</span>
        <span>
          <s:message code="search.nice.link.from.day"/>:
        </span>
        <span>
          <strong>
            <fmt:formatDate value="${dissectionProtocol.basicData.autopsy.dateTime}" pattern="dd-MM-yyyy HH:mm"/>
          </strong>
        </span>
        <span>
          <s:message code="search.nice.link.did"/>:
        </span>
        <span>
          <c:forEach items="${doctors}" var="doctor">
            <c:if test="${doctor.id eq dissectionProtocol.basicData.autopsy.doctorExecutorId}">
              <strong>${doctor.title}&nbsp;${doctor.firstName}&nbsp;${doctor.lastName}</strong>
            </c:if>
          </c:forEach>
        </span>
      </dd>
      <dt><s:message code="search.nice.link.patient"/>:</dt>
      <dd>
        <c:choose>
          <c:when test="${empty dissectionProtocol.basicData.patient.niceName}">
            <c:out value="${dissectionProtocol.basicData.patient.description}"/>
          </c:when>
          <c:otherwise>
            <c:out value="${dissectionProtocol.basicData.patient.niceName}"/>
          </c:otherwise>
        </c:choose>
        <c:if test="${not empty dissectionProtocol.basicData.patient.identificationNumber}">
          &nbsp;(${dissectionProtocol.basicData.patient.identificationNumber})
        </c:if>
      </dd>
    </dl>
  </a>
</div>
