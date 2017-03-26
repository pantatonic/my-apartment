(function ($) {
    $.fn.numberOnly = function (options) {
        var _settings = $.extend({
            useDecimal: false,
            thousandSeparate: false,
            thousandSeparator: ',',
            callBackFunction: function (thisEle) {

            }
        }, options);
        
        var _triggerCtrl = function (thisElement) {
            var keyEvent = $.Event("keydown");
            keyEvent.ctrlKey = true;
            keyEvent.key = 'Control';
            keyEvent.keyCode = 17;

            thisElement.trigger(keyEvent);
        };

        var _countDot = function (thisElement) {
            var str = thisElement.val();
            var numberOfDot = str.split(".").length - 1;

            return numberOfDot;
        };

        var _removeComma = function (thisElement) {
            var str = thisElement.val();
            var rex = new RegExp(_settings.thousandSeparator, "g");

            thisElement.val(str.replace(rex, ''));

            //thisElement.val(str.replace(/,/g, ''));
        };

        var _removeCommaByString = function (str) {
            var rex = new RegExp(_settings.thousandSeparator, "g");
            return str.replace(rex, '');

            //return str.replace(/,/g, '');
        };

        var _addComma = function (thisElement) {
            var newStr = thisElement.val().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1" + _settings.thousandSeparator);

            thisElement.val(newStr);

            var strSplit = thisElement.val().split('.');
            var strInteger = strSplit[0];
            var strDecimal = strSplit[1];

            if (strDecimal != undefined && strDecimal != '') {
                strDecimal = _removeCommaByString(strDecimal);

                if (_countDot(thisElement) > 0) {
                    thisElement.val(strInteger + '.' + strDecimal);
                } else {
                    thisElement.val(strInteger);
                }
            }
        };

        var _initial = function (thisElement, e) {
            var arr_allow = [48, 49, 50, 51, 52, 53, 54, 55, 56, 57, //1 - 9 keyboard
                96, 97, 98, 99, 100, 101, 102, 103, 104, 105, //1 - 9 keypad
                8, //backspace
                46, //delete
                17, //ctrl
                116, //f5
                35, //end
                36, //home
                9, //tab
                16, //shift
                13, //enter
                37, 38, 39, 40 //arrow left, up, right, down

            ];

            if (e.keyCode == 37 || e.keyCode == 38 || e.keyCode == 39|| e.keyCode == 40) {
                return true;
            }

            if (e.keyCode == 46 || e.keyCode == 8) {
                _triggerCtrl(thisElement);
                return true;
            }

            if (_settings.useDecimal) {
                arr_allow.push(110, 190);
            }

            if ($.inArray(e.keyCode, arr_allow) == -1) {
                e.preventDefault();
                return false;
            }

            if (_settings.useDecimal) {
                if (e.keyCode == 110 && (_countDot(thisElement) >= 1)) {
                    e.preventDefault();
                    return false;
                }
            }

            if (_settings.thousandSeparate) {
                _removeComma(thisElement);
                _addComma(thisElement);
            }

            if (e.type == 'keyup') {
                _settings.callBackFunction(thisElement);
            }


        };

        var _firstInit = function (thisElement) {
            thisElement.each(function() {
                var currentElement = $(this);

                if (isNaN(currentElement.val())) {
                    currentElement.val('');
                }

                _triggerCtrl(currentElement);
            });
        };


        $(this).on('keydown keyup paste', function (e) {
            _initial($(this), e);
        });

        _firstInit($(this));
    };
}(jQuery));