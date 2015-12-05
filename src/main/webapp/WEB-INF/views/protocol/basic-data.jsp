<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common/" %>
<%@ taglib prefix="dp" tagdir="/WEB-INF/tags/protocol/" %>
<%@page pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
  <title><s:message code="navigation.protocol.basicData"/></title>
</head>
<body>

<c:if test="${basic.dissectionProtocol.id <= 0}">
  <div class="alert alert-info">
    <s:message code="dissection.protocol.new"/>
  </div>
</c:if>

<c:if test="${successMessage != null}">
  <div class="alert alert-success">
    <s:message code="dissection.protocol.basisData.successMessage.${successMessage}"/>
  </div>
</c:if>

<form:form commandName="basic" action="${pageContext.request.contextPath}/protocol/basic/save" class="form-horizontal" id="basicDataForm">

  <form:errors element="span" cssClass="alert alert-error"/>

<form:hidden path="dissectionProtocol.id"/>
<form:hidden id="submitMode" path="submitMode"/>

<div class="control-group">
  <label class="control-label" for="category">
    <s:message code="dissection.protocol.category.label"/>
  </label>

  <div class="controls">
    <form:select path="dissectionProtocol.category" id="category" cssClass="input-block-level" onchange="changeCategory(this.form)">
      <form:option value="ADULT">
        <s:message code="dissection.protocol.category.ADULT"/>
      </form:option>
      <form:option value="NEWBORN">
        <s:message code="dissection.protocol.category.NEWBORN"/>
      </form:option>
      <form:option value="FETUS">
        <s:message code="dissection.protocol.category.FETUS"/>
      </form:option>
    </form:select>
  </div>
</div>

<div class="control-group">
  <label class="control-label" for="category">
    <s:message code="dissection.protocol.hospital.label"/>
  </label>

  <div class="controls">
    <form:select path="dissectionProtocol.hospitalId" id="hospitalId" cssClass="input-block-level">
      <form:option value="">
        <s:message code="select.empty"/>
      </form:option>
      <form:options items="${dissectionHospitals}" itemValue="id" itemLabel="name"/>
    </form:select>
  </div>
</div>

<div class="control-group">
  <label class="control-label" for="category">
    <s:message code="dissection.protocol.number.label"/>
  </label>

  <div class="controls">
    <form:input path="dissectionProtocol.number" type="text" class="input-large" id="number" cssClass="input-xlarge"/>
  </div>
</div>

<fieldset>
  <legend><s:message code="dissection.protocol.autopsy.legend"/></legend>
</fieldset>

<div class="control-group">
  <label class="control-label" for="category">
    <s:message code="dissection.protocol.basicData.autopsy.doctorExecutor.label"/>
  </label>

  <div class="controls">
    <form:select path="dissectionProtocol.basicData.autopsy.doctorExecutorId" id="doctorId" cssClass="input">
      <form:option value="">
        <s:message code="select.empty"/>
      </form:option>
      <form:options items="${doctors}" itemValue="id" itemLabel="name"/>
    </form:select>
    <s:message code="dissection.protocol.basicData.autopsy.doctorPresence.label" var="doctorPresencePlaceholder"/>
    <form:input path="dissectionProtocol.basicData.autopsy.doctorExecutorPresence" type="text" id="number" cssClass="" placeholder="${doctorPresencePlaceholder}"/>
  </div>
</div>

<div class="control-group">
  <label class="control-label" for="category">
    <s:message code="dissection.protocol.basicData.autopsy.date.label"/>
  </label>

  <div class="controls">
    <form:input path="dissectionProtocol.basicData.autopsy.date" type="text" id="number" cssClass="input-xlarge datepicker" placeholder="${datePattern}"/>
    <form:input path="dissectionProtocol.basicData.autopsy.time" type="text" id="number" cssClass="input-mini" placeholder="${timePattern}"/>
  </div>
</div>

<fieldset>
  <legend><s:message code="dissection.protocol.patient.legend"/></legend>
</fieldset>

<c:choose>
  <c:when test="${'ADULT' eq basic.dissectionProtocol.category}">
    <div class="control-group">
      <form:hidden path="dissectionProtocol.basicData.patient.description"/>
      <label class="control-label" for="category">
        <s:message code="dissection.protocol.basicData.patient.name.label"/>
      </label>

      <div class="controls">
        <form:input path="dissectionProtocol.basicData.patient.firstName" type="text" id="number" cssClass="" placeholder="Imię"/>
        <form:input path="dissectionProtocol.basicData.patient.lastName" type="text" id="number" cssClass="" placeholder="Nazwisko"/>
      </div>
    </div>
  </c:when>
  <c:when test="${'NEWBORN' eq basic.dissectionProtocol.category}">
    <form:hidden path="dissectionProtocol.basicData.patient.firstName"/>
    <form:hidden path="dissectionProtocol.basicData.patient.lastName"/>
    <div class="control-group">
      <label class="control-label" for="category">
        <s:message code="dissection.protocol.basicData.patient.description.label"/>
      </label>

      <div class="controls">
        <form:input path="dissectionProtocol.basicData.patient.description" type="text" id="number" cssClass="input-xxlarge"/>
      </div>
    </div>
  </c:when>
  <c:when test="${'FETUS' eq basic.dissectionProtocol.category}">
    <form:hidden path="dissectionProtocol.basicData.patient.firstName"/>
    <form:hidden path="dissectionProtocol.basicData.patient.lastName"/>
    <div class="control-group">
      <label class="control-label" for="category">
        <s:message code="dissection.protocol.basicData.patient.description.label"/>
      </label>

      <div class="controls">
        <form:input path="dissectionProtocol.basicData.patient.description" type="text" id="number" cssClass="input-xxlarge"/>
      </div>
    </div>
  </c:when>
</c:choose>
<div class="control-group">
  <label class="control-label" for="category">
    <s:message code="dissection.protocol.basicData.patient.identificationNumber.${basic.dissectionProtocol.category}.label"/>
  </label>

  <div class="controls">
    <form:input path="dissectionProtocol.basicData.patient.identificationNumber" type="text" id="number" cssClass=""/>
    <c:choose>
      <c:when test="${patientIdValid}">
        <s:message code="dissection.protocol.basicData.patient.identificationNumber.age" arguments="${basic.dissectionProtocol.basicData.patient.yearsAge}"/>
        <form:hidden path="dissectionProtocol.basicData.patient.yearsAge" />
      </c:when>
      <c:otherwise>
        <span class="label label-important">
          <s:message code="dissection.protocol.basicData.patient.identificationNumber.invalidId"/>
        </span>
        <s:message code="dissection.protocol.basicData.patient.identificationNumber.age.label"/>
        <form:input path="dissectionProtocol.basicData.patient.yearsAge" type="text" id="yearsAge" cssClass="input-small"/>
      </c:otherwise>
    </c:choose>
  </div>
</div>
<div class="control-group">
  <label class="control-label" for="category">
    <s:message code="dissection.protocol.basicData.patient.address.value.label"/>
  </label>
  <div class="controls">
    <form:input path="dissectionProtocol.basicData.patient.address.value" type="text" id="number" cssClass="input-xxlarge"/>
  </div>
</div>
<div class="control-group">
  <div class="controls">
    <form:input path="dissectionProtocol.basicData.patient.address.postCode" type="text" id="number" cssClass="input-small" placeholder="00-000"/>
    <s:message code='dissection.protocol.basicData.patient.address.city.label' var="addressCityPlaceHolder"/>
    <form:input path="dissectionProtocol.basicData.patient.address.city" type="text" id="number" cssClass="input-330" placeholder="${addressCityPlaceHolder}"/>
  </div>
</div>

<fieldset>
  <legend><s:message code="dissection.protocol.story.legend"/></legend>
</fieldset>

<div class="control-group">
  <label class="control-label" for="category">
    <s:message code="dissection.protocol.story.hospital.label"/>
  </label>

  <div class="controls">
    <form:select path="dissectionProtocol.basicData.deathStory.hospitalId" id="hospitalId" cssClass="input-xxlarge" onchange="deathHospitalSelect(this.form)">
      <form:option value="">
        <s:message code="select.empty"/>
      </form:option>
      <form:options items="${hospitals}" itemValue="id" itemLabel="name"/>
    </form:select>
  </div>
</div>

<c:if test="${not empty basic.namedHospitalWards}">
  <div class="control-group">
    <label class="control-label" for="category">
      <s:message code="dissection.protocol.story.hospitalWards.label"/>
    </label>

    <div class="controls">
      <ul>
        <c:forEach items="${basic.namedHospitalWards}" varStatus="namedHospitalWardRow">
          <li>
            <s:bind path="namedHospitalWards[${namedHospitalWardRow.index}].hospitalWardEntry.dissectionProtocolId">
              <input type="hidden" name="<c:out value="${status.expression}"/>"
                     id="<c:out value="${status.expression}"/>"
                     value="<c:out value="${status.value}"/>"/>
            </s:bind>
            <s:bind path="namedHospitalWards[${namedHospitalWardRow.index}].name">
              <c:out value="${status.value}"/>
              <input type="hidden" name="<c:out value="${status.expression}"/>"
                     id="<c:out value="${status.expression}"/>"
                     value="<c:out value="${status.value}"/>"/>
            </s:bind>
            <s:bind path="namedHospitalWards[${namedHospitalWardRow.index}].hospitalWardEntry.hospitalWardId">
              <input type="hidden" name="<c:out value="${status.expression}"/>"
                     id="<c:out value="${status.expression}"/>"
                     value="<c:out value="${status.value}"/>"/>
              <c:url value="/protocol/basic/hospitalWardDelete/${dissectionProtocolId}/${status.value}" var="removeHospitalWardFromProtocolUrl"/>
              <a href="${removeHospitalWardFromProtocolUrl}"><s:message code="button.delete"/></a>
            </s:bind>
            <s:bind path="namedHospitalWards[${namedHospitalWardRow.index}].hospitalWardEntry.id">
              <input type="hidden" name="<c:out value="${status.expression}"/>"
                     id="<c:out value="${status.expression}"/>"
                     value="<c:out value="${status.value}"/>"/>
            </s:bind>
          </li>
        </c:forEach>
      </ul>
    </div>
  </div>
</c:if>
<c:choose>
  <c:when test="${basic.dissectionProtocol.basicData.deathStory.hospitalId > 0}">
    <div class="control-group">
      <label class="control-label" for="category">
      </label>

      <div class="controls">
        <form:input path="newHospitalWard.name" id="newHospitalWardName" cssClass="input-xxlarge" autocomplete="off"/>
        <a href="#" onclick="addHospitalWard(document.getElementById('basicDataForm'))">
          <s:message code="dissection.protocol.story.hospitalWard.new"/>
        </a>
        <script type="text/javascript">
          var hospitalWardArray = [
            <c:forEach items="${possibleHospitalWardMap}" var="possibleHospitalWardEntry">
            '${possibleHospitalWardEntry.value.name}',
            </c:forEach>
          ];
          $('#newHospitalWardName').typeahead({
            source: hospitalWardArray,
            minLength: 1,
            items: 20
          });
        </script>
      </div>
    </div>
  </c:when>
  <c:otherwise>
    <div class="control-group">
      <label class="control-label" for="category">
      </label>

      <div class="controls">
        <s:message code="dissection.protocol.story.hospitalWard.add.denied"/>
      </div>
    </div>
  </c:otherwise>
</c:choose>
<c:if test="">
</c:if>

<div class="control-group">
  <label class="control-label" for="category">
    <s:message code="dissection.protocol.story.bookNumber.label"/>
  </label>

  <div class="controls">
    <form:select path="dissectionProtocol.basicData.deathStory.bookType" id="bookType" cssClass="input-medium">
      <c:forEach items="${deathStoryBookTypes}" var="deathStoryBookType">
        <s:message code="dissection.protocol.story.bookType.${deathStoryBookType}" var="deathStoryBookTypeLabel"/>
        <form:option value="${deathStoryBookType}" label="${deathStoryBookTypeLabel}"/>
      </c:forEach>
    </form:select>
    <form:input path="dissectionProtocol.basicData.deathStory.bookNumber" id="bookNumber" cssClass="input-xlarge"/>
  </div>
</div>

<div class="control-group">
  <c:forEach items="${basic.dissectionProtocol.basicData.deathStory.storyEntries}" var="storyEntry" varStatus="storyEntryIndex">
    <c:choose>
      <c:when test="${activeEntriesStore[storyEntry.sourceName][basic.dissectionProtocol.category]}">
        <label class="control-label" for="deathStoryEntry-${storyEntry.sourceName}">
          <s:message code="dissection.protocol.story.entry.${storyEntry.sourceName}"/>
        </label>

        <div class="controls">
          <form:hidden path="dissectionProtocol.basicData.deathStory.storyEntries[${storyEntryIndex.index}].id" id="deathStoryEntry-${storyEntry.sourceName}"/>
          <form:hidden path="dissectionProtocol.basicData.deathStory.storyEntries[${storyEntryIndex.index}].name" id="deathStoryEntryName-${storyEntry.sourceName}"/>
          <form:hidden path="dissectionProtocol.basicData.deathStory.storyEntries[${storyEntryIndex.index}].sourceName" id="deathStoryEntrySourceName-${storyEntry.sourceName}"/>
          <form:input path="dissectionProtocol.basicData.deathStory.storyEntries[${storyEntryIndex.index}].date" id="deathStoryEntryDate-${storyEntry.sourceName}" cssClass="input-small datepicker"
                      placeholder="${datePattern}"/>
          <form:input path="dissectionProtocol.basicData.deathStory.storyEntries[${storyEntryIndex.index}].time" id="deathStoryEntryTime-${storyEntry.sourceName}" cssClass="input-mini"
                      placeholder="${timePattern}"/>
          <form:input path="dissectionProtocol.basicData.deathStory.storyEntries[${storyEntryIndex.index}].description" id="deathStoryEntryDescription-${storyEntry.sourceName}" cssClass="input-330"
                      placeholder="Dadatkowy opis"/>
        </div>
      </c:when>
      <c:otherwise>
        <form:hidden path="dissectionProtocol.basicData.deathStory.storyEntries[${storyEntryIndex.index}].id" id="deathStoryEntry-${storyEntry.sourceName}"/>
        <form:hidden path="dissectionProtocol.basicData.deathStory.storyEntries[${storyEntryIndex.index}].name" id="deathStoryEntryName-${storyEntry.sourceName}"/>
        <form:hidden path="dissectionProtocol.basicData.deathStory.storyEntries[${storyEntryIndex.index}].sourceName" id="deathStoryEntrySourceName-${storyEntry.sourceName}"/>
        <fmt:formatDate value="${basic.dissectionProtocol.basicData.deathStory.storyEntries[storyEntryIndex.index].date}" pattern="${datePattern}" var="hiddenEntryDate"/>
        <input type="hidden" value="${hiddenEntryDate}" name="basicData.deathStory.storyEntries[${storyEntryIndex.index}].date" id="deathStoryEntryDate-${storyEntry.sourceName}">
        <form:hidden path="dissectionProtocol.basicData.deathStory.storyEntries[${storyEntryIndex.index}].time" id="deathStoryEntryTime-${storyEntry.sourceName}"/>
        <form:hidden path="dissectionProtocol.basicData.deathStory.storyEntries[${storyEntryIndex.index}].description" id="deathStoryEntryDescription-${storyEntry.sourceName}"/>
      </c:otherwise>
    </c:choose>
  </c:forEach>
</div>

<div class="form-actions">
  <c:choose>
    <c:when test="${basic.dissectionProtocol.id > 0}">
      <button type="submit" class="btn btn-primary" onclick="save(this.form)">
        <s:message code="button.save"/>
      </button>
      <common:delete-link url="/protocol/basic/delete/${basic.dissectionProtocol.id}" headerCode="dissection.protocol.delete.header">
        <div class="alert">
          <s:message code="dissection.protocol.delete.message"/>
        </div>
        <dp:nice-link dissectionProtocol="${basic.dissectionProtocol}" />
      </common:delete-link>
    </c:when>
    <c:otherwise>
      <button type="submit" class="btn btn-primary" onclick="save(this.form)">
        <s:message code="button.create"/>
      </button>
    </c:otherwise>
  </c:choose>

</div>

</form:form>

<script type="text/javascript">
  function changeCategory(form) {
    submit(form, 'CATEGORY_SELECT')
  }
  function save(form) {
    submit(form, 'SAVE')
  }
  function addHospitalWard(form) {
    submit(form, 'HOSPITAL_WARD_ADD')
  }
  function deathHospitalSelect(form) {
    submit(form, 'DEATH_HOSPITAL_SELECT');
  }
  function submit(form, mode) {
    document.getElementById('submitMode').value = mode;
    form.submit();
    return true;
  }
</script>
</body>
</html>
