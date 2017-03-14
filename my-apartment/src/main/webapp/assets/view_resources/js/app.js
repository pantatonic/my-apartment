var app = {
    translate : function(key) {
        var jsString = jQuery('#js_language_strings').text();
        jsString = JSON.parse(jsString);

        if(key in jsString) {
            return jsString[key];
        }
        else {
            alert('[app.translate] : Not found key : ' + key);
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
    convertToJsonObject: function(data) {
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
    },
    showNotice: function(options) {
        if(options.addclass == undefined) {
            options.addclass = '';
        }

        new PNotify({
            title: app.translate('common.message'),
            text: options.message,
            type: options.type,
            addclass: options.addclass
        });
    },
    
    valueUtils: {
        isEmptyValue: function(stringData) {
            return (stringData.trim() == '' ? true : false);
        },
        undefinedToEmpty: function(data) {
            return data == undefined ? '':data;
        },
        nullToEmpty: function(data) {
            return data == null ? '':data;
        }
    }
};