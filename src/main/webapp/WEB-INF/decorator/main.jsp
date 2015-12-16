<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ page pageEncoding="UTF-8" session="false" %>

<!DOCTYPE html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <title><sitemesh:write property='title'/></title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="Lukasz Kaleta">

  <!-- CSS -->
  <link href='<c:url value="/resources/css/dissection-protocol.css" />' rel="stylesheet">
  <link href='<c:url value="/resources/css/bootstrap.css" />' rel="stylesheet">
  <link href='<c:url value="/resources/css/bootstrap-responsive.css" />' rel="stylesheet">
  <link href='<c:url value="/resources/css/docs.css" />' rel="stylesheet">
  <link href='<c:url value="/resources/css/datepicker.css" />' rel="stylesheet">
  <link href='<c:url value="/resources/css/bootstrap-wysihtml5.css" />' rel="stylesheet">
  <link href='<c:url value="/resources/css/dp.css" />' rel="stylesheet">

  <script type="text/javascript" src="http://platform.twitter.com/widgets.js"></script>
  <script src='<c:url value="/resources/js/pdfobject.js" />'></script>
  <script src='<c:url value="/resources/js/jquery-2.1.4.min.js" />'></script>
  <script src='<c:url value="/resources/js/jquery-ui.min.js" />'></script>
  <script src='<c:url value="/resources/js/bootstrap-transition.js" />'></script>
  <script src='<c:url value="/resources/js/bootstrap-alert.js" />'></script>
  <script src='<c:url value="/resources/js/bootstrap-modal.js" />'></script>
  <script src='<c:url value="/resources/js/bootstrap-dropdown.js" />'></script>
  <script src='<c:url value="/resources/js/bootstrap-scrollspy.js" />'></script>
  <script src='<c:url value="/resources/js/bootstrap-tab.js" />'></script>
  <script src='<c:url value="/resources/js/bootstrap-tooltip.js" />'></script>
  <script src='<c:url value="/resources/js/bootstrap-popover.js" />'></script>
  <script src='<c:url value="/resources/js/bootstrap-button.js" />'></script>
  <script src='<c:url value="/resources/js/bootstrap-collapse.js" />'></script>
  <script src='<c:url value="/resources/js/bootstrap-carousel.js" />'></script>
  <script src='<c:url value="/resources/js/bootstrap-affix.js" />'></script>
  <script src='<c:url value="/resources/js/holder/holder.js" />'></script>
  <script src='<c:url value="/resources/js/google-code-prettify/prettify.js" />'></script>
  <script src='<c:url value="/resources/js/application.js" />'></script>
  <script src='<c:url value="/resources/js/bootstrap-datepicker.js" />'></script>
  <script src='<c:url value="/resources/js/bootstrap-typeahead.js" />'></script>
  <script src='<c:url value="/resources/js/bootstrap-wysihtml5.js" />'></script>
  <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
  <!--[if lt IE 9]>
  <script src='<c:url value="/resources/js/html5shiv.js" />'></script>
  <![endif]-->

  <!-- Fav and touch icons -->
  <link rel="apple-touch-icon-precomposed" sizes="144x144" href='<c:url value="/resources/ico/apple-touch-icon-144-precomposed.png" />'>
  <link rel="apple-touch-icon-precomposed" sizes="114x114" href='<c:url value="/resources/ico/apple-touch-icon-114-precomposed.png" />'>
  <link rel="apple-touch-icon-precomposed" sizes="72x72" href='<c:url value="/resources/ico/apple-touch-icon-72-precomposed.png" />'>
  <link rel="apple-touch-icon-precomposed" href='<c:url value="/resources/ico/apple-touch-icon-57-precomposed.png" />'>
  <link rel="shortcut icon" href='<c:url value="/resources/ico/icon.png" />'>

</head>

<body>

<!-- Part 1: Wrap all page content here -->
<div id="wrap">

  <!-- Fixed navbar -->
  <div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
      <div class="container">

        <a class="brand" href="#">
          <s:message code="about.short"/>
        </a>

        <button class="btn btn-navbar collapsed" data-target=".nav-collapse" data-toggle="collapse" type="button">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>

        <div class="nav-collapse collapse">
          <ul class="nav">
            <li class="${mainMenu eq 'search' ? 'active' : ''}">
              <a href='<c:url value="/search/latest/start" />'>
                <s:message code="navigation.main.filter"/>
              </a>
            </li>
            <li class="${mainMenu eq 'protocol' ? 'active' : ''}">
              <a href='<c:url value="/protocol/basic/start" />'>
                <s:message code="navigation.main.protocol"/>
              </a>
            </li>
            <li class="dropdown ${mainMenu eq 'settings' ? 'active' : ''}">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <s:message code="navigation.main.settings"/>
                <b class="caret"></b>
              </a>
              <ul class="dropdown-menu">
                <li>
                  <a href='<c:url value="/settings/hospital/show" />'>
                    <s:message code="navigation.main.settings.hospitals"/>
                  </a>
                </li>
                <li>
                  <a href='<c:url value="/settings/doctor/show" />'>
                    <s:message code="navigation.main.settings.doctors"/>
                  </a>
                </li>
                <li>
                  <a href='<c:url value="/settings/dissectionDiagnoseSource/start" />'>
                    <s:message code="navigation.main.settings.dissection.diagnose.source"/>
                  </a>
                </li>
                <li>
                  <a href='<c:url value="/settings/descriptionPointSource/start" />'>
                    <s:message code="navigation.main.settings.description.point.source"/>
                  </a>
                </li>
              </ul>
            </li>
            <li class="dropdown ${mainMenu eq 'security' ? 'active' : ''}">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <sec:authentication property="name"/>
                <b class="caret"></b>
              </a>
              <ul class="dropdown-menu">
                <li>
                  <a href='<c:url value="/logout" />'>
                    <s:message code="logout.link"/>
                  </a>
                </li>
                <li>
                  <a href='<c:url value="/account/user/show" />'>
                    <s:message code="navigation.main.security.account"/>
                  </a>
                </li>
                <sec:authorize access="hasRole('ADMIN')">
                  <li>
                    <a href='<c:url value="/account/manager/show" />'>
                      <s:message code="navigation.main.security.manager"/>
                    </a>
                  </li>
                </sec:authorize>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>

  <div class="container">

    <sitemesh:write property='body'/>

  </div>

</div>

<!-- Footer
================================================== -->
<footer class="footer">
  <div class="container">
    <p><s:message code="about.short"/></p>

    <p></p><a href="http://www.e-histopatologia.pl"><s:message code="about.long"/></a>.</p>
    <%--<ul class="footer-links">--%>
    <%--<li><a href="http://blog.getbootstrap.com">Blog</a></li>--%>
    <%--<li class="muted">&middot;</li>--%>
    <%--<li><a href="https://github.com/twitter/bootstrap/issues?state=open">Issues</a></li>--%>
    <%--<li class="muted">&middot;</li>--%>
    <%--<li><a href="https://github.com/twitter/bootstrap/blob/master/CHANGELOG.md">Changelog</a></li>--%>
    <%--</ul>--%>
  </div>
</footer>
<%--<s:message code="about.short"/>--%>
<%--<a href="http://www.e-histopatologia.pl"><s:message code="about.long"/></a>--%>

<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->

<script>
  $(function () {
    window.prettyPrint && prettyPrint();
    var datePicker = $('.datepicker').datepicker({
      format: 'dd.mm.yyyy'
    }).on('changeDate', function (ev) {
          datePicker.datepicker('hide')
        })
  });

      function disableLinkAfterClick(aElement, message) {
        var location = aElement.attr('href');
        if (location != null) {
          aElement.removeAttr('href');
          window.location.href = location;
          return true;
        } else {
          alert(message);
          return false;
        }
      }
</script>

</body>
</html>
