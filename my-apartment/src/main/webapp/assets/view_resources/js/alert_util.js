/* global swal, OK_STRING, CANCEL_STRING */

var alertUtil = (function () {
    /*var _confirmButtonText = OK_STRING;
    var _cancelButtonText = CANCEL_STRING;*/

    var _mergeObject = function (baseObject, secondaryObject) {
        if (secondaryObject !== undefined) {
            for (var key in secondaryObject) {
                baseObject[key] = secondaryObject[key];
            }
        }
        return baseObject;
    };

    var _basicAlert = function (dataOption, functionCallBack) {
        dataOption.confirmButtonText = OK_STRING;

        if (typeof functionCallBack === 'object' || functionCallBack === undefined) {
            swal(dataOption);
        } else {
            swal(dataOption)
                    .then(function () {
                        return functionCallBack();
                    });
        }
    };

    var _confirmAlert = function (dataOption, functionConfirm) {
        if (dataOption.confirmButtonText === undefined) {
            dataOption.confirmButtonText = OK_STRING;
        }

        if (dataOption.cancelButtonText === undefined) {
            dataOption.cancelButtonText = CANCEL_STRING;
        }

        swal(dataOption).then(function (isConfirm) {
            if (isConfirm) {
                return functionConfirm();
            }
        });
    };

    var _confirmCancelAlert = function (dataOption, functionConfirm, functionCancel) {
        if (dataOption.confirmButtonText === undefined) {
            dataOption.confirmButtonText = OK_STRING;
        }

        if (dataOption.cancelButtonText === undefined) {
            dataOption.cancelButtonText = CANCEL_STRING;
        }

        swal(dataOption)
                .then(function (isConfirm) {
                    if (isConfirm) {
                        return functionConfirm();
                    } else if (!isConfirm) {
                        return functionCancel();
                    } else {
                    }
                });
    };

    return {
        basicSuccessAlert: function (msg, functionCallBack, options) {
            var dataOption = {
                text: msg,
                type: 'success'
            };

            if (typeof functionCallBack === 'object' || functionCallBack === undefined) {
                options = functionCallBack;
            }

            dataOption = _mergeObject(dataOption, options);
            _basicAlert(dataOption, functionCallBack);
        },
        basicInfoAlert: function (msg, functionCallBack, options) {
            var dataOption = {
                text: msg,
                type: 'info'
            };

            if (typeof functionCallBack === 'object' || functionCallBack === undefined) {
                options = functionCallBack;
            }

            dataOption = _mergeObject(dataOption, options);
            _basicAlert(dataOption, functionCallBack);
        },
        basicWarningAlert: function (msg, functionCallBack, options) {
            var dataOption = {
                text: msg,
                type: 'warning'
            };

            if (typeof functionCallBack === 'object' || functionCallBack === undefined) {
                options = functionCallBack;
            }

            dataOption = _mergeObject(dataOption, options);
            _basicAlert(dataOption, functionCallBack);
        },
        basicErrorAlert: function (msg, functionCallBack, options) {
            var dataOption = {
                text: msg,
                type: 'error'
            };

            if (typeof functionCallBack === 'object' || functionCallBack === undefined) {
                options = functionCallBack;
            }

            dataOption = _mergeObject(dataOption, options);
            _basicAlert(dataOption, functionCallBack);
        },
        basicQuestionAlert: function (msg, functionCallBack, options) {
            var dataOption = {
                text: msg,
                type: 'question'
            };

            if (typeof functionCallBack === 'object' || functionCallBack === undefined) {
                options = functionCallBack;
            }

            dataOption = _mergeObject(dataOption, options);
            _basicAlert(dataOption, functionCallBack);
        },
        confirmAlert: function (msg, functionConfirm, functionCancel, options) {
            var dataOption = {
                text: msg,
                type: 'question',
                showCancelButton: true
            };
            if (typeof functionCancel === 'function') {
                dataOption = _mergeObject(dataOption, options);
                _confirmCancelAlert(dataOption, functionConfirm, functionCancel);
            } else if (typeof functionCancel === 'object' || functionCancel === undefined) {
                options = functionCancel;
                dataOption = _mergeObject(dataOption, options);
                _confirmAlert(dataOption, functionConfirm);
            }
        },
        flashElement: function (jQueryObject) {
            jQueryObject.effect("pulsate", {times: 2}, 200);
        }
    };
})();