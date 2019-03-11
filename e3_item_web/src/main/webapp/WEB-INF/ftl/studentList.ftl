<html>
<head>
    <title>student</title>
</head>
<body>
    学生信息:<br/>

    <table border="1px">
        <tr>
            <th>index</th>
            <th>ID</th>
            <th>姓名</th>
            <th>年龄</th>
            <th>地址</th>
        </tr>
        <#list stuList as stu>
        <#if stu_index % 2 == 0 && (stu_index > 5)>
        <tr bgcolor="red">
        <#else>
        <tr bgcolor="blue">
        </#if>

                <td>${stu_index}</td>
                <td>${stu.id}</td>
                <td>${stu.name}</td>
                <td>${stu.age}</td>
                <td>${stu.address}</td>
            </tr>
        </#list>
    </table>

</body>

</html>