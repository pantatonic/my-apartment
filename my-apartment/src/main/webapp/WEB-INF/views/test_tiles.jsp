<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="title" value="Test Tiles" />
    <tiles:putAttribute name="css">

    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <script type="text/javascript">
            jQuery(document).on('ready', function() {
                alert('ok');
            });
        </script>
    </tiles:putAttribute>
    <tiles:putAttribute name="body">
        Test tiles
    </tiles:putAttribute>
</tiles:insertDefinition>