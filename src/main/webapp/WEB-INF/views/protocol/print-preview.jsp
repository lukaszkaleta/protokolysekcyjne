<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="dp" tagdir="/WEB-INF/tags/protocol/" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title><s:message code="navigation.protocol.print"/></title>
</head>

<body>
<dp:update updateDeniedMessageCode="print.preview.provide.basic.data">

  <ul class="nav nav-pills">
    <li class="">
      <c:url value="/protocol/report/generate/${dissectionProtocolId}" var="dissectionProtocolGenerateUrl"/>
      <a href="${dissectionProtocolGenerateUrl}">
        <s:message code="print.preview.pdf.generate"/>
      </a>
    </li>
  </ul>

  <c:url value="/protocol/report/${dissectionProtocolId}/${dissectionProtocolName}.pdf" var="dissectionProtocolReportUrl"/>

  <div id="preview-pdf">
    <s:message code="print.preview.pdf.missing"/>
    <a href="${dissectionProtocolReportUrl}"><s:message code="print.preview.pdf.download"/></a>
  </div>

  <script type="text/javascript">

    window.onload = function (){

      var success = new PDFObject({ url: "${dissectionProtocolReportUrl}" }).embed("preview-pdf");

    };

  </script>


</dp:update>

</body>
</html>

