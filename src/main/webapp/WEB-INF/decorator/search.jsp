<%@ page import="java.util.HashMap" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title><sitemesh:write property='title'/></title>
</head>
<body>

<div class="container-fluid" style="margin-top: 2.5em">
    <div class="row-fluid">
        <div class="span5">
            <fieldset>
                <legend><s:message code="search.form.legend"/></legend>
            </fieldset>

            <form:form commandName="userSearch" action="/search/result/provide" class="form-horizontal">
                <form:errors path="*" cssClass="alert alert-error" element="div" />
                <form:hidden path="owner"/>
                <form:hidden path="id"/>
                <div class="control-group">
                    <label class="control-label" for="hospitalId">Szpital</label>

                    <div class="controls">
                        <form:select path="hospitalId" id="hospitalId">
                            <form:option value="">
                                <s:message code="select.empty"/>
                            </form:option>
                            <form:options items="${hospitals}" itemValue="id" itemLabel="name"/>
                        </form:select>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="doctorId">Lekarz</label>

                    <div class="controls">
                        <form:select path="doctorId" id="doctorId">
                            <form:option value="">
                                <s:message code="select.empty"/>
                            </form:option>
                            <form:options items="${doctors}" itemValue="id" itemLabel="name"/>
                        </form:select>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="medicalExaminationDate">Data badania</label>
                    <div class="controls">
                        <div class="input-prepend">
                            <span class="add-on" style="margin-top: 1px">=</span>
                            <form:input path="medicalExaminationDate" type="text" class="input-medium datepicker" id="medicalExaminationDate" placeholder="dd-MM-yyyy"/>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="input-prepend">
                            <span class="add-on" style="margin-top: 1px">≥</span>
                            <form:input path="medicalExaminationDateFrom" type="text" class="input-medium datepicker" id="medicalExaminationDateRangeFrom"
                                        placeholder="od"/>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="input-prepend">
                            <span class="add-on" style="margin-top: 1px">≤</span>
                            <form:input path="medicalExaminationDateTo" type="text" class="input-medium datepicker" id="medicalExaminationDateRangeTo" placeholder="do"/>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="medicalExaminationTimeRangeFrom">Godziny badania</label>

                    <div class="controls">
                        <div class="input-prepend">
                            <span class="add-on">≥</span>
                            <form:input path="medicalExaminationTimeFrom" class="input-mini" id="medicalExaminationTimeRangeFrom" placeholder="0:00"/>
                        </div>
                        <div class="input-prepend">
                            <span class="add-on">≤</span>
                            <form:input path="medicalExaminationTimeTo" type="text" class="input-mini" id="medicalExaminationTimeRangeTo" placeholder="23:59"/>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="medicalExaminationNumber">Numer badania</label>

                    <div class="controls">
                        <form:input path="medicalExaminationNumber" type="text" class="" id="medicalExaminationNumber" placeholder=""/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="patientLastName">Nazwisko pacjenta</label>

                    <div class="controls">
                        <form:input path="patientLastName" id="patientLastName"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="patientFirstName">Imię pacjenta</label>

                    <div class="controls">
                        <form:input path="patientFirstName" id="patientFirstName"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="patientIdentificationNumber">PESEL pacjenta</label>

                    <div class="controls">
                        <form:input path="patientIdentificationNumber" type="text" id="patientIdentificationNumber"/>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Filtruj</button>
                    <a class="btn" href='<c:url value="/search/result/reset"/>'>Reset</a>
                </div>
            </form:form>

        </div>
        <div class="span7">
            <ul class="nav nav-tabs">
                <li class="${searchList == null ? 'active' : ''}">
                    <a href='<c:url value="/search/latest/start" />'>Ostatnio wykonywane badania</a>
                </li>
                <li class="${searchList != null ? 'active' : ''}">
                    <a href='<c:url value="/search/result/start" />'>Wynik wyszukiwania</a>
                </li>
            </ul>
            <sitemesh:write property='body'/>
        </div>
    </div>

</div>

</body>
</html>