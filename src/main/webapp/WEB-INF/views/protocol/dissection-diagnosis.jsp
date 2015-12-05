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
    <title><s:message code="navigation.protocol.dissectionDiagnosis"/></title>
</head>
<body>

<div class="pull-right">
  <common:link url="/settings/dissectionDiagnoseSource/start" code="dissection.diagnosis.settings.dissectionDiagnosisSource.show.link"/>
</div>
<br/>

<dp:update updateDeniedMessageCode="dissection.diagnosis.provide.basic.data">

    <c:choose>
      <c:when test="${newDescriptionPointSourceId > 0}">
        <div class="alert alert-success">
          <button type="button" class="close" data-dismiss="alert">&times;</button>
          <h4><s:message code="dissection.diagnose.added"/></h4>
        </div>
        <div class="alert alert-block">
          <button type="button" class="close" data-dismiss="alert">&times;</button>
          <s:message code="description.point.source.description.fill.empty.reminder"/>
          <common:link url="/settings/descriptionPointSource/edit/${newDescriptionPointSourceId}" code="description.point.source.description.fill.empty.link" />
        </div>
      </c:when>
      <c:when test="${addedDescriptionPointSource != null}">
        <div class="alert alert-success">
          <button type="button" class="close" data-dismiss="alert">&times;</button>
          <h4><s:message code="dissection.diagnose.added"/></h4>
        </div>
        <c:choose>
          <c:when test="${empty addedDescriptionPointSource.description}">
            <div class="alert alert-block">
              <button type="button" class="close" data-dismiss="alert">&times;</button>
              <s:message code="description.point.source.description.not.replaced.reminder"/>
              <common:link url="/settings/descriptionPointSource/edit/${addedDescriptionPointSource.id}" code="description.point.source.description.not.replaced.link" />
            </div>
          </c:when>
          <c:when test="${addedDescriptionPointSource.point <= 0}">
            <div class="alert alert-block">
              <button type="button" class="close" data-dismiss="alert">&times;</button>
              <s:message code="description.point.source.description.not.replaced.point.reminder"/>
              <common:link url="/settings/descriptionPointSource/edit/${addedDescriptionPointSource.id}" code="description.point.source.description.not.replaced.point.link" />
            </div>
          </c:when>
          <c:when test="${addedDescriptionPoint != null}">
            <div class="alert alert-success">
              <button type="button" class="close" data-dismiss="alert">&times;</button>
              <s:message code="description.point.source.description.replaced.details" argumentSeparator="***" arguments="${addedDescriptionPoint.descriptionPointSource.point}***${addedDescriptionPoint.descriptionPointSource.position}***${addedDescriptionPoint.descriptionPointSource.description}"/>
              <br/>
              <common:link url="/protocol/description/point/${addedDescriptionPoint.id}" code="description.point.source.description.replaced.edit.link" />
            </div>
          </c:when>
        </c:choose>
        <c:if test="${dissectionDiagnoseWithOptionId != null}">
          <div class="alert alert-success">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <s:message code="dissection.diagnose.source.option.available"/>
            <br/>
            <common:link url="/protocol/dissectionDiagnosis/option/show/${dissectionDiagnoseWithOptionId}" code="dissection.diagnose.option.choose" />
          </div>
        </c:if>
      </c:when>
    </c:choose>

  <c:choose>
    <c:when test="${editMode}">
      <s:message code="button.save" var="submitButtonLabel"/>
      <c:set var="actionSuffix" value="update"/>
    </c:when>
    <c:otherwise>
      <s:message code="dissection.diagnose.and.source.button.save" var="submitButtonLabel"/>
      <c:set var="actionSuffix" value="add"/>
    </c:otherwise>
  </c:choose>

  <form:form action="${pageContext.request.contextPath}/protocol/dissectionDiagnosis/${actionSuffix}" commandName="dissectionDiagnoseModel">

    <form:hidden path="dissectionDiagnose.id" id="id"/>
    <form:hidden path="dissectionDiagnose.dissectionProtocolId"/>
    <form:hidden path="dissectionDiagnose.descriptionPointId"/>
    <form:hidden path="dissectionDiagnose.dissectionDiagnoseSourceId" id="dissectionDiagnoseSourceId"/>

    <div class="controls">
      <s:message var="ddnLatinPlaceholder" code="dissection.diagnose.source.latin.placeholder"/>
      <form:input id="latinInput" path="dissectionDiagnose.name.latin" cssClass="input-block-level" placeholder="${ddnLatinPlaceholder}" autocomplete="off"/>
    </div>
    <s:hasBindErrors name="dissectionDiagnoseModel">
      <form:errors path="dissectionDiagnose.name.latin" element="div" cssClass="alert alert-error"/>
    </s:hasBindErrors>

    <div class="controls">
      <s:message var="ddnTranslatedPlaceholder" code="dissection.diagnose.source.translated.placeholder"/>
      <form:input id="translatedInput" path="dissectionDiagnose.name.translated" cssClass="input-block-level" placeholder="${ddnTranslatedPlaceholder}" autocomplete="off"/>
    </div>
    <s:hasBindErrors name="dissectionDiagnoseModel">
      <form:errors path="dissectionDiagnose.name.translated" element="div" cssClass="alert alert-error"/>
    </s:hasBindErrors>


    <form:checkbox id="createSource" path="createSource" cssClass="input-block-level" autocomplete="off"/>
    <s:message code="dissection.diagnose.source.createSource"/>
    <div class="form-actions">
      <button type="submit" class="btn btn-primary">${submitButtonLabel}</button>
    </div>
  </form:form>

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
    var idMap = {};
    var descriptionPointSourceIdMap = {};
    <c:forEach items="${dissectionDiagnoseSources}" var="dds">
      latinMap['${dds.name.latin}'] = '${dds.name.translated}';
      translatedMap['${dds.name.translated}'] = '${dds.name.latin}';
      idMap['${dds.name.latin}'] = '${dds.id}';
      descriptionPointSourceIdMap['${dds.name.latin}'] = '${dds.descriptionPointSourceId}';
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
      $('#dissectionDiagnoseSourceId').val(idMap[latinItem]);
      $('#descriptionPointSourceId').val(descriptionPointSourceIdMap[latinItem]);
      $('#createSource').prop('checked', false);
    }
    function resetDDS(latinName) {
      var alreadySelected = !$('#dissectionDiagnoseSourceId').val();
      if (alreadySelected) {
        $('#createSource').prop('checked', true);
      }
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


  <table class="table table-condensed table-bordered" id='table-draggable'>
    <thead>
    <c:if test="${not empty dissectionProtocol.dissectionDiagnoseList}">
      <tr>
        <th colspan="2">
          <s:message code="dissection.diagnose.list"/>
        </th>
      </tr>
    </c:if>
    <tr>
      <th><s:message code="dissection.diagnose.source.latin"/></th>
      <th><s:message code="dissection.diagnose.source.translated"/></th>
    </tr>
    </thead>
    <c:choose>
      <c:when test="${empty dissectionProtocol.dissectionDiagnoseList}">
        <tfoot>
        <tr>
          <th colspan="2">
            <s:message code="dissection.diagnose.list.empty"/>
          </th>
        </tr>
        </tfoot>
      </c:when>
      <c:otherwise></c:otherwise>
    </c:choose>
    <tbody class="connectedSortable">

    <s:message code="dissection.diagnose.source.description.point.create" var="descriptionPointMissingLinkLabel"/>
    <s:message code="dissection.diagnose.description.point.edit" var="descriptionPointEditLinkLabel"/>

    <c:forEach items="${dissectionProtocol.dissectionDiagnoseList}" var="dissectionDiagnose" varStatus="dissectionDiagnoseStatus">

      <c:choose>
        <c:when test="${dissectionDiagnose.descriptionPointId == null}">
          <c:url var="descriptionPointUrl" value="/protocol/dissectionDiagnosis/missingPoint/${dissectionDiagnose.dissectionProtocolId}/${dissectionDiagnose.id}"/>
          <c:set var="descriptionPointLabel" value="${descriptionPointMissingLinkLabel}"/>
        </c:when>
        <c:otherwise>
          <c:url var="descriptionPointUrl" value="/protocol/description/point/${dissectionDiagnose.descriptionPointId}"/>
          <c:set var="descriptionPointLabel" value="${descriptionPointEditLinkLabel}"/>
        </c:otherwise>
      </c:choose>

      <tr id="${dissectionDiagnose.id}">
        <td>
          <a class="undecorated" href='<c:url value="/protocol/dissectionDiagnosis/edit/${dissectionDiagnose.dissectionProtocolId}/${dissectionDiagnose.id}"/>'>
            <c:out value="${dissectionDiagnose.name.latin}"/>
          </a>
            <c:if test="${dissectionDiagnoseOptions[dissectionDiagnose.id] != null}">
              <ul class="unstyled">
                <c:forEach items="${dissectionDiagnoseOptions[dissectionDiagnose.id]}" var="dissectionDiagnoseOption">
                  <li>
                    <span class="sortIndex">
                      ${dissectionDiagnoseOption.sortIndex})
                    </span>
                    <a href='<c:url value="/protocol/dissectionDiagnosis/option/show/${dissectionDiagnose.id}"/>#${dissectionDiagnoseOption.id}' class="undecorated">
                      <c:out value="${dissectionDiagnoseOption.name.latin}"/>
                    </a>
                  </li>
                </c:forEach>
              </ul>
            </c:if>


          <div class="btn-group pull-right">
            <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">
              <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
              <li><a href="${descriptionPointUrl}">${descriptionPointLabel}</a></li>
              <c:if test="${optionSourceAvailable[dissectionDiagnose.dissectionDiagnoseSourceId]}">
                <li><a href='<c:url value="/protocol/dissectionDiagnosis/option/show/${dissectionDiagnose.id}"/>'><s:message code="dissection.diagnose.option.choose"/></a></li>
              </c:if>
              <li class="divider"></li>
              <li>
                <a href="#dissectionDiagnoseDelete${dissectionDiagnose.id}" role="button" class="${cssClass}" data-toggle="modal">
                  <s:message code="button.delete"/>
                </a>
              </li>
            </ul>
          </div>
        </td>
        <td>
          <a class="undecorated" href='<c:url value="/protocol/dissectionDiagnosis/edit/${dissectionDiagnose.dissectionProtocolId}/${dissectionDiagnose.id}"/>'>
            <c:out value="${dissectionDiagnose.name.translated}"/>
          </a>
            <c:if test="${dissectionDiagnoseOptions[dissectionDiagnose.id] != null}">
              <ul class="unstyled">
                <c:forEach items="${dissectionDiagnoseOptions[dissectionDiagnose.id]}" var="dissectionDiagnoseOption">
                  <li>
                    <span class="sortIndex">
                      ${dissectionDiagnoseOption.sortIndex})
                    </span>
                    <a href='<c:url value="/protocol/dissectionDiagnosis/option/show/${dissectionDiagnose.id}"/>#${dissectionDiagnoseOption.id}' class="undecorated">
                      <c:out value="${dissectionDiagnoseOption.name.translated}"/>
                    </a>
                  </li>
                </c:forEach>
              </ul>
            </c:if>

          <div class="btn-group pull-right">
            <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">
              <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
              <li><a href="${descriptionPointUrl}">${descriptionPointLabel}</a></li>
              <c:if test="${optionSourceAvailable[dissectionDiagnose.dissectionDiagnoseSourceId]}">
                <li><a href='<c:url value="/protocol/dissectionDiagnosis/option/show/${dissectionDiagnose.id}"/>'><s:message code="dissection.diagnose.option.choose"/></a></li>
              </c:if>
              <li class="divider"></li>
              <li>
                <a href="#dissectionDiagnoseDelete${dissectionDiagnose.id}" role="button" class="${cssClass}" data-toggle="modal">
                  <s:message code="button.delete"/>
                </a>
              </li>
            </ul>
          </div>

        </td>
      </tr>

      <div id="dissectionDiagnoseDelete${dissectionDiagnose.id}" class="modal hide fade">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h3>
            <s:message code="protocol.dissection.diagnose.delete.header"/>
          </h3>
        </div>
        <div class="modal-body">
          <div class="alert">
            <s:message code="protocol.dissection.diagnose.delete.message"/>
          </div>
          <s:message code="dissection.diagnose.source.latin"/>
          <h5>
            <c:out value="${dissectionDiagnose.name.latin}"/>
          </h5>
          <s:message code="dissection.diagnose.source.translated"/>
          <h5>
            <c:out value="${dissectionDiagnose.name.translated}"/>
          </h5>
        </div>
        <div class="modal-footer">
          <a href="#" class="btn" data-dismiss="modal" aria-hidden="true">
            <s:message code="button.cancel"/>
          </a>
          <common:link url="/protocol/dissectionDiagnosis/delete/${dissectionDiagnose.dissectionProtocolId}/${dissectionDiagnose.id}" code="button.confirm.delete" cssClass="btn btn-primary"/>
        </div>
      </div>


    </c:forEach>
    </tbody>
  </table>


  <script type="application/javascript">
    $(document).ready(function () {

      var $tabs = $('#table-draggable');

      $("tbody.connectedSortable")
              .sortable({
                connectWith: ".connectedSortable",
                appendTo: $tabs,
                helper: "clone",
                zIndex: 999990,
                update: function( event, ui ) {
                  var trs = this.children;
                  var ordered = [];
                  for(var i = 0; i < trs.length; i++) {
                    ordered.push(trs[i].id);
                  }
                  $.ajax({
                    headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
                    },
                    url: '${pageContext.request.contextPath}/protocol/clinicalDiagnosis/reorder/${dissectionProtocol.id}',
                    type: 'PUT',
                    data: JSON.stringify(ordered),
                    success: function(data) {
                      console.info("Saved")
                    },
                    error: function() {
                      console.info("Not saved");
                    }
                  });
                }
              })
              .disableSelection();

      var $tab_items = $("tbody.connectedSortable", $tabs).droppable({
        accept: ".connectedSortable tr",
        hoverClass: "ui-state-hover",

        drop: function (event, ui) {
          // Upuszczamy
          var id = ui.draggable[0].id;
          return true;
        },
        create: function( event, ui ) {
          // tu mozna obliczyc pozycje
          return true;
        },
        activate: function( event, ui ) {
          // Zaczynamy ciagnoc
          return true;
        },
        deactivate: function( event, ui ) {
          // po upuszczeniu
          return true;
        },
        out: function( event, ui ) {
          // Wychodzimy poza dragable
          return true;
        },
        over: function( event, ui ) {
          return true;
        }
      });

    });
  </script>
</dp:update>



</body>
</html>