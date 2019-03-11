<html>
<head>
    <title>student</title>
</head>
<body>
    学生信息:<br/>



    date ${date!111}<br/>
    <#if date??>
    date有值
    <#else>
    date没有值
    </#if>
    <br/>
    include测试Hello <#include "hello.ftl"/>

</body>

</html>