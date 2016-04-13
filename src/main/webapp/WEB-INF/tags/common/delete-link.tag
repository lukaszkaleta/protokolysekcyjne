<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>

<%@ attribute name="url" required="true" rtexprvalue="true" type="java.lang.String" description="Delete url" %>
<%@ attribute name="id" required="false" rtexprvalue="true" type="java.lang.String" description="Id of delete" %>
<%@ attribute name="headerCode" required="false" rtexprvalue="true" type="java.lang.String" description="Header link label" %>
<%@ attribute name="cssClass" required="false" rtexprvalue="true" type="java.lang.String" description="Delete link css" %>
<%@ attribute name="labelCode" required="false" rtexprvalue="true" type="java.lang.String" description="Delete link label" %>

<c:if test="${cssClass == null}">
  <c:set var="cssClass" value="btn btn-inverse pull-right" />
</c:if>
<c:if test="${id == null}">
  <c:set var="id" value="X"/>
</c:if>
<c:if test="${labelCode == null}">
  <s:message code="button.delete" var="labelCode"/>
</c:if>
<c:if test="${headerCode == null}">
  <s:message code="common.modal.dialog.delete.header" var="headerCode"/>
</c:if>

<a href class="${cssClass}" data-toggle="modal" data-target="#deleteModal${id}">
  <s:message code="${labelCode}"/>
</a>

<div id="deleteModal${id}" class="modal hide fade">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h3>
      <s:message code="${headerCode}"/>
    </h3>
  </div>
  <div class="modal-body" >
    <jsp:doBody/>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal" aria-hidden="true">
      <s:message code="button.cancel"/>
    </a>
    <common:link url="${url}" code="button.confirm.delete" cssClass="btn btn-primary"/>
  </div>
</div>