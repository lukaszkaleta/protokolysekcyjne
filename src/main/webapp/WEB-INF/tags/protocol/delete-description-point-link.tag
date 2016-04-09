<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>

<%@ attribute name="allowed" required="true" rtexprvalue="true"  type="java.lang.Boolean" %>
<%@ attribute name="descriptionPoint" required="true" rtexprvalue="true" type="com.ra.dissection.protocol.domain.protocol.DescriptionPoint" %>

<c:choose>
    <c:when test="${allowed}">
        <common:delete-link url="/protocol/description/point/delete/${descriptionPoint.dissectionProtocolId}/${descriptionPoint.id}" cssClass="info"
                            headerCode="description.point.delete.from.protocol" id="${descriptionPoint.id}">
            <div class="row-fluid">
                <div class="span12">
                    <span class="alert">
                        <s:message code="description.point.delete.from.protocol.confirm" arguments="${descriptionPoint.descriptionPointSource.digitPosition},${descriptionPoint.descriptionPointSource.point}"/>
                    </span>
                </div>
                <div class="span12">
                    <h5>
                        ${descriptionPoint.descriptionPointSource.description}
                    </h5>
                </div>
            </div>
        </common:delete-link>
    </c:when>
    <c:otherwise>
        <span class="pull-right">
            <s:message code="description.point.delete.from.protocol.condition"/>
        </span>
    </c:otherwise>
</c:choose>
