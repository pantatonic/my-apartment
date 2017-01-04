<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test view</title>
        
        <script type="text/javascript" src="<c:url value="assets/js/jquery.1.11.0.min.js" />"></script>
        <script type="text/javascript">
            var jsonArrayData = ${jsonArrayData}
            var jsona = ${jsona};
            
            jQuery(document).ready(function() {
                render1();
                render2();
            });
            
            function render1() {
                var html = '';
                
                for(var index in jsonArrayData) {
                    html += '<tr>'
                    + '<td>' + jsonArrayData[index].id + '</td>'
                    + '<td>' + jsonArrayData[index].name + '</td>'
                    + '<td>' + jsonArrayData[index].surname + '</td>'
                    + '</tr>';
                }
                
                $('#table-json-array-data-1 tbody').html(html);
            }
            
            function render2() {
                var html = '';
                
                html += 'Id : ' + jsona[0].id + '<br>'
                + 'Name : ' + jsona[0].name + '<br>'
                + 'Surname : ' + jsona[0].surname;
                
                $('#jsona').html(html);
            }
        </script>
    </head>
    <body>
        <h1>This is test view </h1>
        ${privateParamString}
        <br>
        ${text1}
        <br>
        <c:forEach items="${strings}" var="string">
            ${string}
        </c:forEach>
        
        <hr>
        <table cellspacing="1" cellpadding="1" border="1" width="100%" id="table-json-array-data-1">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Surname</th>
                </tr>
            </thead>
            <tbody>
                
            </tbody>
        </table>
        
        <hr>
        <span id="jsona"></span>
    </body>
</html>
