<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test view</title>
        
        <script type="text/javascript" src="<c:url value="assets/js/jquery.1.11.0.min.js" />"></script>
        <script type="text/javascript" src="<c:url value="assets/js/common_constant.js" />"></script>
        <script type="text/javascript">
            var jsonArrayData = ${jsonArrayData}
            var jsona = ${jsona};
            
            jQuery(document).ready(function() {
                render1();
                render2();
                dataFromWebService();
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
            
            function dataFromWebService() {
                var dataFromWebServiceElement = jQuery('#data-from-web-service');
                dataFromWebServiceElement.html('Calling Web Service...');
                
                setTimeout(function() {
                    jQuery.ajax({
                        type: 'get',
                        url: WS_URL + 'test/get_all_test',
                        cache: false,
                        success: function(response) {
                            if(typeof response !== 'object') {
                                response = JSON.parse(response);
                            }
                            
                            var responseData = response.data;
                            var html = '';
                            
                            if(response.result === SUCCESS_STRING) {
                                for(var index in responseData) {
                                    html += '- Id : ' + responseData[index].id
                                        + ' Name : ' + responseData[index].name
                                        + ' Surname : ' + responseData[index].surname
                                        + '<br>';
                                }
                            }
                            
                            dataFromWebServiceElement.html(html);
                        },
                        error: function() {
                            alert('Error to call Web service');
                        }
                    });
                }, 500);
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
        
        <hr>
        <h2>Data From Web Service</h2>
        <span id="data-from-web-service"></span>
    </body>
</html>
