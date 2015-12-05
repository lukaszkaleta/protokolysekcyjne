<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@ taglib prefix="dp" tagdir="/WEB-INF/tags/protocol/" %>
<%@page pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
  <title><s:message code="navigation.protocol.dissectionDiagnoseOptions"/></title>
</head>
<body>

  <common:message-panel/>

  <s:message code="dissection.diagnose.source.latin"/>
  <span class="input-block-level uneditable-input">${dissectionDiagnose.name.latin}</span>

  <s:message code="dissection.diagnose.source.translated"/>
  <span class="input-block-level uneditable-input">${dissectionDiagnose.name.translated}</span>

  <c:choose>
    <c:when test="${empty dissectionDiagnoseSourceOptions}">
      <h3>
        <s:message code="protocol.dissection.diagnose.option.not.available"/>
      </h3>
    </c:when>
    <c:otherwise>
      <h3>
        <s:message code="protocol.dissection.diagnose.option.list"/>
      </h3>
      <c:forEach items="${dissectionDiagnoseSourceOptions}" var="dissectionDiagnoseSourceOption">

        <c:choose>
          <c:when test="${dissectionDiagnoseOptions[dissectionDiagnoseSourceOption.id] != null}">

            <c:set var="dissectionDiagnoseOption" value="${dissectionDiagnoseOptions[dissectionDiagnoseSourceOption.id]}"/>

            <form:form action="${pageContext.request.contextPath}/protocol/dissectionDiagnosis/option/save" commandName="dissectionDiagnoseOption" id="${dissectionDiagnoseOption.id}">

              <input type="hidden" name="id" value="${dissectionDiagnoseOption.id}"/>
              <input type="hidden" name="dissectionDiagnoseId" value="${dissectionDiagnoseOption.dissectionDiagnoseId}"/>
              <input type="hidden" name="dissectionDiagnoseSourceOptionId" value="${dissectionDiagnoseOption.dissectionDiagnoseSourceOptionId}"/>

              <div class="row-fluid">
                <div class="span1">
                  <h3>
                    <span class="sortIndex">
                      ${dissectionDiagnoseOption.sortIndex})
                    </span>
                  </h3>
                </div>
                <div class="span11">

                    <s:message code="dissection.diagnose.source.option.latin"/>
                    <input type="text" name="name.latin" value="${dissectionDiagnoseOption.name.latin}" class="input-block-level"/>
                    <s:hasBindErrors name="dissectionDiagnoseOption">
                        <form:errors path="name.latin" element="div" cssClass="alert alert-error"/>
                    </s:hasBindErrors>

                    <s:message code="dissection.diagnose.source.option.translated"/>
                    <input type="text" name="name.translated" value="${dissectionDiagnoseOption.name.translated}" class="input-block-level"/>
                    <s:hasBindErrors name="dissectionDiagnoseOption">
                        <form:errors path="name.translated" element="div" cssClass="alert alert-error"/>
                    </s:hasBindErrors>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            <s:message code="button.save"/>
                        </button>
                        <common:delete-link url="/protocol/dissectionDiagnosis/option/delete/${dissectionDiagnose.id}/${dissectionDiagnoseOption.id}" labelCode="protocol.dissection.diagnose.option.delete">
                            <div class="alert">
                                <s:message code="protocol.dissection.diagnose.option.delete.message"/>
                            </div>
                            <s:message code="dissection.diagnose.source.option.latin"/>
                            <h5>
                                <c:out value="${dissectionDiagnoseOption.name.latin}"/>
                            </h5>
                            <s:message code="dissection.diagnose.source.option.translated"/>
                            <h5>
                                <c:out value="${dissectionDiagnoseOption.name.translated}"/>
                            </h5>
                        </common:delete-link>
                    </div>

                </div>
              </div>

            </form:form>
          </c:when>
          <c:otherwise>
            <div class="row-fluid">
              <div class="span1">
                <h3>
                  <span class="sortIndex">
                    ${dissectionDiagnoseSourceOption.sortIndex})
                  </span>
                </h3>
              </div>
              <div class="span11">
                <s:message code="dissection.diagnose.source.option.latin"/>
                <span class="input-block-level uneditable-input">${dissectionDiagnoseSourceOption.name.latin}</span>

                <s:message code="dissection.diagnose.source.option.translated"/>
                <span class="input-block-level uneditable-input">${dissectionDiagnoseSourceOption.name.translated}</span>

                <div class="form-actions">
                  <common:link
                      url="/protocol/dissectionDiagnosis/option/add/${dissectionDiagnose.id}/${dissectionDiagnoseSourceOption.id}"
                      code="protocol.dissection.diagnose.option.add" cssClass="btn btn-primary"/>
                </div>
              </div>
            </div>
          </c:otherwise>
        </c:choose>
      </c:forEach>
    </c:otherwise>
  </c:choose>

</body>
</html>