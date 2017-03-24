/* global alert_util, _CONTEXT_PATH_ */

var app = {
    translate : function(key) {
        var jsString = jQuery('#js-language-strings').text();
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
        new PNotify({
            title: app.translate('common.message'),
            text: app.translate('common.something_error_try_again'),
            type: 'error'
        });
    },
    loading : function(type) {
        if (type == 'show') {
            if (jQuery('#overlay').length > 0) {
                return false;
            }
            var over = '<div id="overlay">' +
                '<img id="loading" src="' + _CONTEXT_PATH_ + '/assets/dist/img/loading.gif">' +
                '</div>';
            jQuery(over).appendTo('body');
        }
        else if (type == 'remove') {
            jQuery('#overlay').remove();
        }
    },
    loadingInElement : function(type, elementObject) {
        if (type == 'show') {
            if (jQuery('.overlay-in-element').length > 0) {
                return false;
            }
            var over = '<div class="overlay-in-element">' +
                '<img class="loading_2" src="' + _CONTEXT_PATH_ + '/assets/dist/img/loading.gif">' +
                '</div>';
            //jQuery(over).appendTo(element);
            elementObject.prepend(over);
        }
        else if (type == 'remove') {
            elementObject.find('.overlay-in-element').remove();
        }
    },
    noticeSomethingError : function() {
        new PNotify({
            title: app.translate('common.message'),
            text: app.translate('common.something_error_try_again'),
            type: 'error'
        });
    },
    alertSessionExpired: function() {
        alert_util.basicWarningAlert(app.translate('application.session_expired'), function() {
					
        },{
            animation: false,
            type: null
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
    checkNoticeExist: function(specifyClass) {
        if(specifyClass == undefined) {
            return jQuery('.ui-pnotify').length > 0 ? true : false;    
        }
        else {
            return jQuery('.'+ specifyClass).length > 0 ? true : false;
        }
    },
    validateEmail: function(emailString) {
        var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
        return re.test(emailString);
    },
    clearFormData: function(jqueryFormObject) {
        var resetButton = jQuery('<button/>', {
            type: 'reset',
            html: 'Reset',
            style: 'display: none'
        });
        
        resetButton.appendTo(jqueryFormObject);
        jqueryFormObject.find('button[type="reset"]').click();
        resetButton.remove();
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