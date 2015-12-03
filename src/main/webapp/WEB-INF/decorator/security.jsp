<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ page session="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title><sitemesh:write property='title'/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Lukasz Kaleta">

    <!-- CSS -->
    <link href='<c:url value="/resources/css/bootstrap.css" />' rel="stylesheet">
    <link href='<c:url value="/resources/css/bootstrap-responsive.css" />' rel="stylesheet">
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src='<c:url value="/resources/js/html5shiv.js" />'></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="shortcut icon" href='<c:url value="/resources/ico/icon.png" />'>

    <!-- Le styles -->
    <style type="text/css">
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }
        .form-message {
            max-width: 500px;
            padding: 19px 29px 29px;
            margin: 0 auto 20px;
        }
        .form-signin {
            max-width: 500px;
            padding: 19px 29px 29px;
            margin: 0 auto 20px;
            background-color: #fff;
            border: 1px solid #e5e5e5;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
            -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
            box-shadow: 0 1px 2px rgba(0,0,0,.05);
        }
        .form-signin .form-signin-heading,
        .form-signin .checkbox {
            margin-bottom: 10px;
        }
        .form-signin input[type="text"],
        .form-signin input[type="password"] {
            font-size: 16px;
            height: auto;
            margin-bottom: 15px;
            padding: 7px 9px;
        }

    </style>
</head>

<body>

<div class="container">
    <sitemesh:write property='body'/>
</div>

<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script type="text/javascript" src="http://platform.twitter.com/widgets.js"></script>
<script src='<c:url value="/resources/js/jquery.js" />'></script>
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
<script src='<c:url value="/resources/js/bootstrap-typeahead.jsf" />'></script>
<script src='<c:url value="/resources/js/bootstrap-affix.js" />'></script>
<script src='<c:url value="/resources/js/holder/holder.js" />'></script>
<script src='<c:url value="/resources/js/google-code-prettify/prettify.js" />'></script>
<script src='<c:url value="/resources/js/application.js" />'></script>
</body>
</html>
