<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">

        <c:set var="randomTextVersion" scope="application"><%= java.lang.Math.random() %></c:set>
        
        <c:set var="req" value="${pageContext.request}" />
        <c:set var="url">${req.requestURL}</c:set>
        <c:set var="uri" value="${req.requestURI}" />
        <%-- <c:set var="baseUrl" value="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/" 
               scope="application" /> --%>
        <%--<c:set var="baseUrl"  value="http://localhost:7001/my-apartment/" scope="application" />--%>

        <title></title>
        
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        
        <!-- Font Awesome -->
        <link rel="stylesheet" href="<c:url value="/assets/font_awesome/css/font-awesome.css" />">
        
        <!-- animate css -->
        <link rel="stylesheet" href="<c:url value="/assets/animate_css/animate.css" />">
        
        <!-- jQuery -->
        <script type="text/javascript" src="<c:url value="/assets/plugins/jQuery/jQuery-2.1.4.min.js" />"></script>
        
        <!-- Bootstrap 3.3.4 -->
        <link rel="stylesheet" href="<c:url value="/assets/bootstrap/css/bootstrap.css" />">
        <script type="text/javascript" src="<c:url value="/assets/bootstrap/js/bootstrap.min.js" />"></script>
        <script type="text/javascript">
            var _CONTEXT_PATH_ = '${pageContext.request.contextPath}';
            var bootstrapButton = $.fn.button.noConflict();
            $.fn.bootstrapBtn = bootstrapButton;

            /*var bootstrapTooltip = $.fn.tooltip.noConflict();
            $.fn.bootstrapTooltip = bootstrapTooltip;*/
        </script>
        
        <!-- SlimScroll -->
        <script src="<c:url value="/assets/plugins/slimScroll/jquery.slimscroll.min.js" />"></script>

        <!-- FastClick -->
        <script src="<c:url value="/assets/plugins/fastclick/fastclick.min.js" />"></script>

        <!-- AdminLTE App -->
        <script src="<c:url value="/assets/dist/js/app.min.js" />"></script>
        
        <!-- AdminLTE for demo purposes -->
        <script src="<c:url value="/assets/dist/js/demo.js" />"></script>
        
        <!-- Theme style -->
        <link rel="stylesheet" href="<c:url value="/assets/dist/css/AdminLTE.css" />">
        
        <!-- AdminLTE Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="<c:url value="/assets/dist/css/skins/_all-skins.css" />">
        
        <!-- data table -->
        <link rel="stylesheet" href="<c:url value="/assets/data_table/media/css/dataTables.bootstrap.css" />">
        <link rel="stylesheet" href="<c:url value="/assets/data_table/extensions/Responsive/css/responsive.bootstrap.css" />">
        <script type="text/javascript" src="<c:url value="/assets/data_table/media/js/jquery.dataTables.js" />"></script>
        <script type="text/javascript" src="<c:url value="/assets/data_table/media/js/dataTables.bootstrap.js" />"></script>
        <script type="text/javascript" src="<c:url value="/assets/data_table/extensions/Responsive/js/dataTables.responsive.js" />"></script>
        
        <!-- pnotify -->
        <link rel="stylesheet" href="<c:url value="/assets/pnotify/pnotify.custom.css" />">
        <link rel="stylesheet" href="<c:url value="/assets/pnotify/pnotify.override.css?v=${randomTextVersion}" />">
        <script type="text/javascript" src="<c:url value="/assets/pnotify/pnotify.custom.js" />"></script>
        
        
        <!-- sweet alert 2 -->
        <link rel="stylesheet" href="<c:url value="/assets/sweetalert2/sweetalert2.min.css" />">
        <script type="text/javascript" src="<c:url value="/assets/sweetalert2/sweetalert2.min.js" />"></script>
        <script type="text/javascript" src="<c:url value="/assets/sweetalert2/promise.min.js" />"></script>
        
        <script type="text/javascript" src="<c:url value="/assets/plugins/my-number-only.js" />"></script>
        <!-- ------------------------------- -->
        
        <link rel="stylesheet" href="<c:url value="/assets/view_resources/css/app.css?v=${randomTextVersion}" />" />
        
        
        <!-- final override -->
        <link rel="stylesheet" href="<c:url value="/assets/view_resources/css/final_override.css?v=${randomTextVersion}" />">

        <script type="text/javascript" src="<c:url value="/assets/view_resources/js/common_constant.js?v=${randomTextVersion}" />"></script>
        <script type="text/javascript" src="<c:url value="/assets/view_resources/js/app.js?v=${randomTextVersion}" />"></script>
        <script type="text/javascript" src="<c:url value="/assets/view_resources/js/script.js?v=${randomTextVersion}" />"></script>
        <script type="text/javascript" src="<c:url value="/assets/view_resources/js/alert_util.js?v=${randomTextVersion}" />"></script>
        
        <script type="text/javascript">
            var page = (function() {
                _validateInputEmail = function() {
                    var email = jQuery('[name="email"]');
                    var validatePass = true;
                    
                    email.removeClass(INPUT_ERROR_CLASS);
                    
                    if(app.valueUtils.isEmptyValue( email.val() )) {
                        if(!app.checkNoticeExist('notice-input-data')) {
                            app.showNotice({
                                message: app.translate('common.please_enter_data'),
                                type: WARNING_STRING,
                                addclass: 'notice-input-data'
                            });
                        }

                        validatePass = false;
                    }
                    else {
                        if(!app.validateEmail(email.val())) {
                            if(!app.checkNoticeExist('notice-invalid-email-format')) {
                                app.showNotice({
                                    message: app.translate('common.invalid_email_format'),
                                    type: WARNING_STRING,
                                    addclass: 'notice-invalid-email-format'
                                });
                            }
                            
                            validatePass = false;
                        }
                    }
                    
                    if(!validatePass) {
                        email.addClass(INPUT_ERROR_CLASS);
                    }
                    
                    return validatePass;
                };
                
                _validateInputPassword = function() {
                    var password = jQuery('[name="password"]');
                    var validatePass = true;
                    
                    password.removeClass(INPUT_ERROR_CLASS);
                    
                    if(app.valueUtils.isEmptyValue( password.val() )) {
                        if(!app.checkNoticeExist('notice-input-data')) {
                            app.showNotice({
                                message: app.translate('common.please_enter_data'),
                                type: WARNING_STRING,
                                addclass: 'notice-input-data'
                            });
                        }
                        
                        password.addClass(INPUT_ERROR_CLASS);
                        
                        validatePass = false;
                    }
                    
                    return validatePass;
                };
                
                return {
                    initLoginProcess: function() {
                        jQuery('form[name="login_form"]').submit(function(e) {
                            e.preventDefault();
                            var thisForm = jQuery(this);
                            var formData = thisForm.serialize();
                            var buttonSubmit = thisForm.find('[type="submit"]');
                            var _validate = function() {
                                var validatePass = true;
                                var arrayValidateFunction = [
                                    _validateInputEmail(), _validateInputPassword()
                                ];
                                
                                for(var index in arrayValidateFunction){
                                    if(arrayValidateFunction[index] === false) {
                                        validatePass = false;
                                    }
                                }
                                
                                return validatePass;
                            };
                            
                            if(_validate()) {
                                buttonSubmit.bootstrapBtn('loading');
                            
                                setTimeout(function() {
                                    jQuery.ajax({
                                        type: 'post',
                                        url: thisForm.attr('action'),
                                        data: formData,
                                        cache: false,
                                        success: function(response) {
                                            response = app.convertToJsonObject(response);

                                            if(response.result === SUCCESS_STRING) {
                                                window.location.href = _CONTEXT_PATH_ + '/dashboard.html';
                                            }
                                            else {
                                                if(response.message == 'DATA_NOT_FOUND') {
                                                    app.showNotice({
                                                        message: app.translate('account_not_found'),
                                                        type: response.result
                                                    });
                                                }
                                                else
                                                if(response.message == 'STATUS IS DISABLED') {
                                                    app.showNotice({
                                                        message: app.translate('account_is_disabled'),
                                                        type: response.result
                                                    });
                                                }
                                                else {
                                                    app.showNotice({
                                                        message: response.message,
                                                        type: response.result
                                                    });
                                                }
                                            }

                                            buttonSubmit.bootstrapBtn('reset');
                                        },
                                        error: function() {
                                            app.alertSomethingError();
                                            buttonSubmit.bootstrapBtn('reset');
                                        }
                                    });
                                }, 500);
                            }
                        });
                    },
                    redirectIfLogin: function() {
                        var sessionUserId = '${sessionScope.userId}';
                        var contextPath = _CONTEXT_PATH_;
                        
                        if(sessionUserId != '') {
                            window.location.href = contextPath + '/dashboard.html';
                        }
                    }
                };
            })();
            
            page.redirectIfLogin();
            
            jQuery(document).ready(function() {
                page.initLoginProcess();
            });
            
            
        </script>

    </head>

    <body class="login-page">
        
        <div class="login-box">
            <div class="login-logo">
                <b>My</b> Apartment | ${sessionScope.userFirstname}
                <jsp:include page="WEB-INF/views/message_sources.jsp" />
            </div>
            <div class="login-box-body">
                <p class="login-box-msg"><spring:message code="common.login" /></p>
                <form id="login-form" name="login_form" method="post" action="<c:url value="/login_process.html" />">
                    <div class="form-group has-feedback">
                        <input type="text" name="email" placeholder="<spring:message code="common.user" />" 
                            autocomplete="off" 
                            class="form-control" autofocus="autofocus" value="admin@admin.com">
                        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
                    </div>
                    <div class="form-group has-feedback">
                        <input type="password" name="password" placeholder="<spring:message code="common.password" />" autocomplete="off" 
                               class="form-control" value="1234">
                        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <button type="submit" 
                                class="btn btn-primary btn-block btn-flat full-width btn-submit" 
                                data-loading-text="<spring:message code="common.now_processing" />">
                                    <spring:message code="common.login" />
                            </button>
                        </div>
                    </div>
                    <br><br>
                </form>
            </div>
        </div>
    </body>
</html>
