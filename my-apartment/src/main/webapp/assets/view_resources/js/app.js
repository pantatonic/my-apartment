var app = {
    translate : function(key) {
        var js_string = jQuery('#js_language_strings').text();
        js_string = JSON.parse(js_string);

        if(key in js_string) {
            return js_string[key];
        }
        else {
            return key;
        }
    },
    alertSomethingError : function() {
        /*new PNotify({
            title: app.translate('Message'),
            text: app.translate('Something error please try again'),
            type: 'error'
        });*/
        
        new PNotify({
            title: 'Message',
            text: 'Something error please try again',
            type: 'error'
        });
    },
    convert_to_json_object: function(data) {
        if(typeof data !== 'object') {
            try {
                return JSON.parse(data);
            }
            catch(e) {
                return data;
            }
        }
        else {
            return data;
        }
    }
};