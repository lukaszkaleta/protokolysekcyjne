<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@page pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
  <title><sitemesh:write property='title'/></title>
</head>
<body>
<div class="row">
  <div class="span12">
    <section id="code">
      <div class="page-header">
        <h3 id="pageHeader"><sitemesh:write property='title'/></h3>
      </div>
      <sitemesh:write property='body'/>
    </section>
  </div>
</div>
</body>
</html>