<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="settings" tagdir="/WEB-INF/tags/settings/" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@ taglib prefix="dp" tagdir="/WEB-INF/tags/protocol/" %>
<%@page pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
  <title>
    <s:message code="settings.dissection.diagnose.source.create" var="createTitle"/>
    <s:message code="settings.dissection.diagnose.source.edit" var="editTitle"/>
    <c:choose>
      <c:when test="${dissectionDiagnoseSource.id <= 0}">
        ${createTitle}
      </c:when>
      <c:otherwise>
        ${editTitle}
      </c:otherwise>
    </c:choose>
  </title>
</head>
<body>

<form:form action="${pageContext.request.contextPath}/settings/dissectionDiagnoseSource/save" commandName="dissectionDiagnoseSource">

  <form:hidden path="id" />
  <form:hidden path="descriptionPointSourceId" />

  <s:message code="dissection.diagnose.source.latin"/>
  <s:message var="ddnLatinPlaceholder" code="dissection.diagnose.source.latin.placeholder"/>
  <form:input id="latinInput" path="name.latin" cssClass="input-block-level" placeholder="${ddnLatinPlaceholder}" autocomplete="off"/>

  <s:message code="dissection.diagnose.source.translated"/>
  <s:message var="ddnTranslatedPlaceholder" code="dissection.diagnose.source.translated.placeholder"/>
  <form:input id="translatedInput" path="name.translated" cssClass="input-block-level" placeholder="${ddnTranslatedPlaceholder}" autocomplete="off"/>

  <s:hasBindErrors name="dissectionDiagnoseSource">
    <div class="alert alert-error">
      <form:errors path="name.latin"  element="div"/>
      <form:errors path="name.translated" element="div"/>
    </div>
  </s:hasBindErrors>

  <label class="checkbox inline">
    <form:checkbox path="category.adult" id="categoryAdult"/> <s:message code="dissection.protocol.category.ADULT"/>
  </label>
  <label class="checkbox inline">
    <form:checkbox path="category.newborn" id="categoryNewborn"/> <s:message code="dissection.protocol.category.NEWBORN"/>
  </label>
  <label class="checkbox inline">
    <form:checkbox path="category.fetus" id="categoryFetus"/> <s:message code="dissection.protocol.category.FETUS"/>
  </label>

  <br/><br/>

  <form:select path="type" id="dissectionDiagnoseType" cssClass="input-xxlarge">
    <c:forEach items="${dissectionDiagnoseTypes}" var="dissectionDiagnoseType">
      <s:message code="dissection.diagnose.type.${dissectionDiagnoseType}" var="dissectionDiagnoseTypeLabel"/>
      <form:option value="${dissectionDiagnoseType}" label="${dissectionDiagnoseTypeLabel}"/>
    </c:forEach>
  </form:select>

  <div class="form-actions">
    <button type="submit" class="btn btn-primary">
      <s:message code="button.save"/>
    </button>
    <c:if test="${dissectionDiagnoseSource.id > 0}">
      <c:if test="${dissectionDiagnoseSource.type eq 'MACROSCOPIC'}">
        <common:link url="/settings/descriptionPointSource/edit/${dissectionDiagnoseSource.descriptionPointSourceId}" code="description.point.source.description" cssClass="btn"/>
      </c:if>

      <!-- Deletion of dissection diagnose source -->
      <c:choose>
        <c:when test="${empty dissectionDiagnoseProtocols}">
          <common:delete-link url="/settings/dissectionDiagnoseSource/delete/${dissectionDiagnoseSource.id}">
            <div class="alert">
              <s:message code="settings.dissection.diagnose.source.delete.question"/>
            </div>
            <s:message code="dissection.diagnose.source.latin"/>
            <h5>
              <c:out value="${dissectionDiagnoseSource.name.latin}"/>
            </h5>
            <s:message code="dissection.diagnose.source.translated"/>
            <h5>
              <c:out value="${dissectionDiagnoseSource.name.translated}"/>
            </h5>
          </common:delete-link>
        </c:when>
        <c:otherwise>
          <span class="pull-right">
            <s:message code="settings.dissection.diagnose.source.delete.impossible"/>
          </span>
        </c:otherwise>
      </c:choose>


      <common:link url="/settings/dissectionDiagnoseSource/option/new/${dissectionDiagnoseSource.id}" code="dissection.diagnose.source.new.option.link" cssClass="btn"/>
    </c:if>
    <common:link url="/settings/dissectionDiagnoseSource/start" code="button.cancel" cssClass="btn"/>
  </div>
</form:form>

<c:choose>
  <c:when test="${empty dissectionDiagnoseSourceOptions}">
    <h4>
      <s:message code="dissection.diagnose.source.empty.options.header"/>
    </h4>
    <common:link url="/settings/dissectionDiagnoseSource/option/new/${dissectionDiagnoseSource.id}" code="dissection.diagnose.source.first.option.link"/>
  </c:when>
  <c:otherwise>
    <h4>
      <s:message code="dissection.diagnose.source.options.header"/>
    </h4>
    <div class="row-fluid">
      <div class="span5">
        <s:message code="dissection.diagnose.source.option.latin"/>
        <ul class="unstyled">
          <c:forEach items="${dissectionDiagnoseSourceOptions}" var="dissectionDiagnoseSourceOption">
            <li>
              <span class="sortIndex">
                  ${dissectionDiagnoseSourceOption.sortIndex}.
              </span>
              <a class="undecorated" href='<c:url value="/settings/dissectionDiagnoseSource/option/edit/${dissectionDiagnoseSource.id}/${dissectionDiagnoseSourceOption.id}"/>'>
                <c:out value="${dissectionDiagnoseSourceOption.name.latin}"/>
              </a>
            </li>
          </c:forEach>
        </ul>
      </div>
      <div class="span5">
        <s:message code="dissection.diagnose.source.option.translated"/>
        <ul class="unstyled">
          <c:forEach items="${dissectionDiagnoseSourceOptions}" var="dissectionDiagnoseSourceOption">
            <li>
              <span class="sortIndex">
                  ${dissectionDiagnoseSourceOption.sortIndex}.
              </span>
              <a class="undecorated" href='<c:url value="/settings/dissectionDiagnoseSource/option/edit/${dissectionDiagnoseSource.id}/${dissectionDiagnoseSourceOption.id}"/>'>
                <c:out value="${dissectionDiagnoseSourceOption.name.translated}"/>
              </a>
            </li>
          </c:forEach>
        </ul>
      </div>
    </div>
  </c:otherwise>
</c:choose>

<br/>
<c:choose>
  <c:when test="${empty dissectionDiagnoseProtocols}">
    <h4>
      <s:message code="settings.dissection.diagnose.no.protocols"/>
    </h4>
  </c:when>
  <c:otherwise>
    <h4>
      <s:message code="settings.dissection.diagnose.protocols.header"/>
    </h4>
    <c:forEach items="${dissectionDiagnoseProtocols}" var="dissectionProtocol">
      <dp:nice-link dissectionProtocol="${dissectionProtocol}" />
    </c:forEach>
  </c:otherwise>
</c:choose>

<script language="JavaScript">
  var latinArray = [
    <c:forEach items="${dissectionDiagnoseSources}" var="dds">
    '${dds.name.latin}',
    </c:forEach>
  ];
  var translatedArray = [
    <c:forEach items="${dissectionDiagnoseSources}" var="dds">
    '${dds.name.translated}',
    </c:forEach>
  ];
  var editTitle = '${editTitle}';
  var createTitle = '${createTitle}';
  var latinMap = {};
  var translatedMap = {};
  var adultMap = {};
  var newbornMap = {};
  var fetusMap = {};
  var idMap = {};
  var descriptionPointSourceIdMap = {};
  var dissectionDiagnoseTypeMap = {};
  <c:forEach items="${dissectionDiagnoseSources}" var="dds">
  latinMap['${dds.name.latin}'] = '${dds.name.translated}';
  translatedMap['${dds.name.translated}'] = '${dds.name.latin}';
  adultMap['${dds.name.latin}'] = ${dds.category.adult};
  newbornMap['${dds.name.latin}'] = ${dds.category.newborn};
  fetusMap['${dds.name.latin}'] = ${dds.category.fetus};
  idMap['${dds.name.latin}'] = '${dds.id}';
  descriptionPointSourceIdMap['${dds.name.latin}'] = '${dds.descriptionPointSourceId}';
  dissectionDiagnoseTypeMap['${dds.name.latin}'] = '${dds.type}';
  </c:forEach>

  $('#latinInput').typeahead({
    source: latinArray,
    minLength : 2,
    items : 20,
    updater: function(item) {
      $('#translatedInput').val(latinMap[item]);
      changeDDS(item);
      return item;
    }
  });
  $('#translatedInput').typeahead({
    source: translatedArray,
    minLength : 2,
    items : 20,
    updater: function(item) {
      $('#latinInput').val(translatedMap[item]);
      changeDDS(translatedMap[item]);
      return item;
    }
  });
  function changeDDS(latinItem) {
    $('#categoryNewborn').attr('checked', newbornMap[latinItem]);
    $('#categoryFetus').attr('checked', fetusMap[latinItem]);
    $('#categoryAdult').attr('checked', adultMap[latinItem]);
    $('#pageHeader').text(editTitle);
    $('#id').val(idMap[latinItem]);
    $('#descriptionPointSourceId').val(descriptionPointSourceIdMap[latinItem]);
    $('#dissectionDiagnoseType').val(dissectionDiagnoseTypeMap[latinItem]);
  }
  function resetDDS(latinName) {
    $('#pageHeader').text(createTitle);
    $('#dissectionDiagnoseSourceId').val(0);
    $('#descriptionPointSourceId').val(0);
  }
  $('#translatedInput').keyup(function() {
    var translatedValue = $('#translatedInput').val();
    if (translatedValue in translatedMap) {
      changeDDS(translatedMap[translatedValue]);
    } else {
      resetDDS(translatedMap[translatedValue]);
    }
  });
  $('#latinInput').keyup(function() {
    var latinValue = $('#latinInput').val();
    if (latinValue in latinMap) {
      changeDDS(latinValue);
    } else {
      resetDDS(latinValue);
    }
  });
</script>


</body>
</html>
