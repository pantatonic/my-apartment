<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="title" value="Test Tiles" />
    <tiles:putAttribute name="css">
         <link rel="stylesheet" 
            href="<c:url value="/assets/view_resources/test/test_tiles/css/test_tiles.css?v=${randomTextVersion}"/>">
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <script type="text/javascript" 
                src="<c:url value="/assets/view_resources/test/test_tiles/js/test_tiles.js?v=${randomTextVersion}"/>"></script>
    </tiles:putAttribute>
    <tiles:putAttribute name="body">
       
        <div class="content-wrapper">
            <section class="content-header">
                <h1>
                    Page title
                    <small>Page Sub Title</small>
                </h1>
            </section>

            
            <section class="content">

                Body

            </section>
        </div>

        
    </tiles:putAttribute>
</tiles:insertDefinition>