<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<aside class="main-sidebar">
    <section class="sidebar">
        <div class="user-panel">
            <div class="pull-left image">
                <img src="<c:url value="assets/dist/img/user_160.png" />" 
                    class="img-circle user_profile_image" 
                    alt="User Image" id="aside_user_profile_image" 
                    style="width:45px;height:45px;">
            </div>
            <div class="pull-left info">
                <p>
                    <span class="session-user-firstname">${sessionScope.userFirstname}</span> 
                    <span class="session_user_lastname">${sessionScope.userLastname}</span>
                </p>
                <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
        </div>
        <!-- search form -->
        <!--<form action="#" method="get" class="sidebar-form">
            <div class="input-group">
                <input type="text" name="q" class="form-control" placeholder="Search...">
              <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i></button>
              </span>
            </div>
        </form>-->
        <!-- /.search form -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="header">
                &nbsp;
            </li>
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-bars"></i>
                    <span><spring:message code="apartment" /></span> <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li>
                        <a href="<c:url value="/building.html" />">
                            <i class="fa fa-circle-o"></i> <spring:message code="apartment.building" />
                        </a>
                    </li>
                    <li>
                        <a href="<c:url value="/room.html" />">
                            <i class="fa fa-circle-o"></i> <spring:message code="building.room" />
                        </a>
                    </li>
                </ul>
            </li>
            <li class="treeview ">
                <a href="#">
                    <i class="fa fa-file-o"></i> <span>Test 2</span>
                </a>
            </li>
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-gears"></i>
                    <span>Setting</span> <i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu">
                    <li>
                        <a href="#">
                            <i class="fa fa-file-o"></i> Test 3
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    </section>
</aside>