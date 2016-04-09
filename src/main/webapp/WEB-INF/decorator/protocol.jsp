<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
  <title><sitemesh:write property='title'/></title>
</head>
<body>
<div class="row">
  <div class="span3">
    <div class="bs-docs-sidebar">
      <ul class="nav nav-list bs-docs-sidenav">
        <c:set var="protocolActive" value="${dissectionProtocol != null && dissectionProtocol.id > 0}"/>
        <c:set var="protocolIdActive" value="${dissectionProtocolId != null && dissectionProtocolId > 0}"/>
        <c:choose>
          <c:when test="${protocolActive || protocolIdActive}">
            <c:if test="${protocolActive}">
              <c:set var="dissectionProtocolId" value="${dissectionProtocol.id}"/>
            </c:if>
            <c:set var="dissectionProtocolId" value="${dissectionProtocolId}"/>
            <c:url var="basicShow" value="/protocol/basic/show/${dissectionProtocolId}"/>
            <c:url var="clinicalDiagnosisShow" value="/protocol/clinicalDiagnosis/show/${dissectionProtocolId}"/>
            <c:url var="dissectionDiagnosisShow" value="/protocol/dissectionDiagnosis/show/${dissectionProtocolId}"/>
            <c:url var="descriptionShow" value="/protocol/description/show/${dissectionProtocolId}"/>
            <c:url var="histopathologicalExaminationShow" value="/protocol/histopathologicalExamination/show/${dissectionProtocolId}"/>
            <c:url var="clinicalDataShow" value="/protocol/clinicalData/show/${dissectionProtocolId}"/>
            <c:url var="medicalPracticeAnalysisShow" value="/protocol/medicalPracticeAnalysis/show/${dissectionProtocolId}"/>
          </c:when>
          <c:otherwise>
            <c:url var="basicShow" value="/protocol/basic/start"/>
            <c:url var="clinicalDiagnosisShow" value="/protocol/clinicalDiagnosis/denied"/>
            <c:url var="dissectionDiagnosisShow" value="/protocol/dissectionDiagnosis/denied"/>
            <c:url var="descriptionShow" value="/protocol/description/show"/>
            <c:url var="histopathologicalExaminationShow" value="/protocol/histopathologicalExamination/show"/>
            <c:url var="clinicalDataShow" value="/protocol/clinicalData/show"/>
            <c:url var="medicalPracticeAnalysisShow" value="/protocol/medicalPracticeAnalysis/show"/>
          </c:otherwise>
        </c:choose>

        <li class="${protocolMenu eq 'basic' ? 'active' : ''}">
          <a href="${basicShow}">
            <c:if test="${dissectionProtocolProgress != null}">
              <c:choose>
                <c:when test="${dissectionProtocolProgress.basicDataDone}">
                  <i class="icon-check"></i>
                </c:when>
                <c:otherwise>
                  <i class="icon-exclamation-sign"></i>
                </c:otherwise>
              </c:choose>
            </c:if>
            <i class="icon-chevron-right"></i>
            <s:message code="navigation.protocol.basicData"/>
          </a>
        </li>
        <li class="${protocolMenu eq 'clinicalDiagnosis' ? 'active' : ''}">
          <a href="${clinicalDiagnosisShow}">
            <c:if test="${dissectionProtocolProgress != null}">
              <c:choose>
                <c:when test="${dissectionProtocolProgress.clinicalDiagnosisDone}">
                  <i class="icon-check"></i>
                </c:when>
                <c:otherwise>
                  <i class="icon-exclamation-sign"></i>
                </c:otherwise>
              </c:choose>
            </c:if>
            <i class="icon-chevron-right"></i>
            <s:message code="navigation.protocol.clinicalDiagnosis"/>
          </a>
        </li>
        <li class="${protocolMenu eq 'dissectionDiagnosis' ? 'active' : ''}">
          <a href='${dissectionDiagnosisShow}'>
            <c:if test="${dissectionProtocolProgress != null}">
              <c:choose>
                <c:when test="${dissectionProtocolProgress.dissectionDiagnosisDone}">
                  <i class="icon-check"></i>
                </c:when>
                <c:otherwise>
                  <i class="icon-exclamation-sign"></i>
                </c:otherwise>
              </c:choose>
            </c:if>
            <i class="icon-chevron-right"></i>
            <s:message code="navigation.protocol.dissectionDiagnosis"/>
          </a>
        </li>
        <li class="${protocolMenu eq 'description' ? 'active' : ''}">
          <a href='${descriptionShow}'>
            <c:if test="${dissectionProtocolProgress != null}">
              <c:choose>
                <c:when test="${dissectionProtocolProgress.descriptionDone}">
                  <i class="icon-check"></i>
                </c:when>
                <c:otherwise>
                  <i class="icon-exclamation-sign"></i>
                </c:otherwise>
              </c:choose>
            </c:if>
            <i class="icon-chevron-right"></i>
            <s:message code="navigation.protocol.description"/>
          </a>
        </li>
        <li class="${protocolMenu eq 'histopathologicalExamination' ? 'active' : ''}">
          <a href='${histopathologicalExaminationShow}'>
            <c:if test="${dissectionProtocolProgress != null}">
              <c:choose>
                <c:when test="${dissectionProtocolProgress.histopathologicalExaminationDone}">
                  <i class="icon-check"></i>
                </c:when>
                <c:otherwise>
                  <i class="icon-exclamation-sign"></i>
                </c:otherwise>
              </c:choose>
            </c:if>
            <i class="icon-chevron-right"></i>
            <s:message code="navigation.protocol.histopathologicalExamination"/>
          </a>
        </li>
        <li class="${protocolMenu eq 'clinicalData' ? 'active' : ''}">
          <a href='${clinicalDataShow}'>
            <c:if test="${dissectionProtocolProgress != null}">
              <c:choose>
                <c:when test="${dissectionProtocolProgress.clinicalDataDone}">
                  <i class="icon-check"></i>
                </c:when>
                <c:otherwise>
                  <i class="icon-exclamation-sign"></i>
                </c:otherwise>
              </c:choose>
            </c:if>
            <i class="icon-chevron-right"></i>
            <s:message code="navigation.protocol.clinicalData"/>
          </a>
        </li>
        <li class="${protocolMenu eq 'medicalPracticeAnalysis' ? 'active' : ''}">
          <a href='${medicalPracticeAnalysisShow}'>
            <c:if test="${dissectionProtocolProgress != null}">
              <c:choose>
                <c:when test="${dissectionProtocolProgress.medicalPracticeAnalysisDone}">
                  <i class="icon-check"></i>
                </c:when>
                <c:otherwise>
                  <i class="icon-exclamation-sign"></i>
                </c:otherwise>
              </c:choose>
            </c:if>
            <i class="icon-chevron-right"></i>
            <s:message code="navigation.protocol.medicalPracticeAnalysis"/>
          </a>
        </li>
        <c:if test="${protocolActive || protocolIdActive}">
          <li class="${protocolMenu eq 'report' ? 'active' : ''}">
            <a href='<c:url value="/protocol/report/preview/${dissectionProtocolId}" />'>
              <i class="icon-print"></i>
              <s:message code="navigation.protocol.print"/>
              <i class="icon-chevron-right"></i>
            </a>
          </li>
          <li class="${protocolMenu eq 'clone' ? 'active' : ''}">
            <a href='<c:url value="/protocol/clone/start/${dissectionProtocolId}" />'>
              <i class="icon-share"></i>
              <s:message code="navigation.protocol.clone"/>
              <i class="icon-chevron-right"></i>
            </a>
          </li>
          <li class="${protocolMenu eq 'status' ? 'active' : ''}">
            <a href='<c:url value="/protocol/status/show/${dissectionProtocolId}" />'>
              <i class="icon-ok-sign"></i>
              <s:message code="navigation.protocol.finish"/>
              <i class="icon-chevron-right"></i>
            </a>
          </li>
        </c:if>
      </ul>
    </div>
  </div>
  <div class="span9">
    <section id="code">
      <sitemesh:write property='body'/>
    </section>
  </div>
</div>
</body>
</html>