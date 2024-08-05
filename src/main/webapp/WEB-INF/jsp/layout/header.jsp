
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Admin</title>
<!-- Tell the browser to be responsive to screen width -->
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Font Awesome -->
<link rel="stylesheet" href="${contextPath }/plugins/fontawesome-free/css/all.min.css">
<!-- Ionicons -->
<%-- <link rel="stylesheet" href="${contextPath }/css/ionicons.min.css"> --%>
<!-- Theme style -->
<link rel="stylesheet" href="${contextPath }/css/adminlte.min.css">
<link rel="stylesheet" href="${contextPath }/plugins/toastr/toastr.min.css">
<link rel="stylesheet" href="${contextPath }/plugins/pace-progress/themes/black/pace-theme-flat-top.css">
<script src="${contextPath }/plugins/jquery/jquery.min.js"></script>

<script src="${contextPath }/plugins/toastr/toastr.min.js"></script>
<link href="${contextPath }/plugins/sweetalert/sweetalert.css" rel="stylesheet" />
<script src="${contextPath }/plugins/sweetalert/sweetalert.min.js"></script>

<!-- Google Font: Source Sans Pro -->
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">

<%--	<!-- Bootstrap CSS -->--%>
<%--	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">--%>

<script src="${contextPath}/js/jquery.validate.min.js" type="text/javascript"></script>
<script type="text/javascript">
    var contextPath = '${contextPath}';
    $(document).ready(function(){
        // Active menu
        $("#menu_left .has-treeview").each(function(){
            var obj = $(this);
            $(this).find("ul li a").each(function(){
            	var url = contextPath+window.location.pathname;
                if(url.startsWith($(this).attr("href"))) {
                    obj.addClass("menu-open");
                    obj.find("a").eq(0).addClass("active");
                    $(this).addClass("active");
                }
            });
        });
        var home = contextPath + "/";
        if(contextPath+window.location.pathname == home) {
        	$("#home_menu").addClass("active");
        }
    });

</script>
<style type="text/css">
label.error {
font-weight: normal !important;
color: red;
}
</style>
</head>
<body class="hold-transition sidebar-mini pace-primary">
	<div class="wrapper">
		<!-- Navbar -->
		<nav class="main-header navbar navbar-expand navbar-white navbar-light">
			<!-- Left navbar links -->
			<ul class="navbar-nav">
				<li class="nav-item">
					<a class="nav-link" data-widget="pushmenu" href="#" role="button">
						<i class="fas fa-bars"></i>
					</a>
				</li>
				<li class="nav-item d-none d-sm-inline-block">
					<a href="${contextPath }/" class="nav-link">Trang chủ</a>
				</li>
				
			</ul>
			<ul class="navbar-nav ml-auto">
				<li class="nav-item d-none d-sm-inline-block">
					<a href="/logout" class="nav-link"><i class="fas fa-sign-out-alt"></i>
					 Đăng xuất</a>
				</li>
			</ul>
		</nav>
		<!-- /.navbar -->

		<!-- Main Sidebar Container -->
		<aside class="main-sidebar sidebar-dark-primary elevation-4">
			<!-- Sidebar -->
			<div class="sidebar">
				<!-- Sidebar user (optional) -->
				<div class="user-panel mt-3 pb-3 mb-3 d-flex">
					<div class="image">
						<img src="${contextPath }/img/user.png" class="img-circle elevation-2" alt="User Image">
					</div>
					<div class="info">
						<a href="${contextPath }/change-pass" class="d-block">${username}</a>
					</div>
				</div>

				<!-- Sidebar Menu -->
				<nav class="mt-2">
					<ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false" id="menu_left">
						<li class="nav-item">
				            <a href="${contextPath }/" class="nav-link" id="home_menu">
				              <i class="nav-icon fas fa-home"></i> <p>Trang chủ</p>
				            </a>
				        </li>
						<c:forEach items="${userModuleMenus}" var="item">
						    <li class="nav-item has-treeview">
						        <c:if test="${item.parentId eq 0}">
						            <a href="javascript:void(0);" class="nav-link">
							            <i class="${item.icon}"></i>
							            <p>
											${item.name }
											<i class="right fas fa-angle-left"></i>
										</p>
						            </a>

						            <ul class="nav nav-treeview">
							            <c:forEach items="${userModuleMenus}" var="itemChild">
							                <c:if test="${itemChild.parentId eq item.id}">
							                    <li class="nav-item">
							                    	<a href="${contextPath}${itemChild.url }" class="nav-link"><i class="far fa-circle nav-icon"></i><p>${itemChild.name }</p></a>
							                    </li>
							                </c:if>
							            </c:forEach>
			                        </ul>
		                        </c:if>
		                    </li>
						</c:forEach>
<%--						&lt;%&ndash;						Quản trị sản phẩm&ndash;%&gt;--%>
<%--						<c:forEach items="${productModuleMenus}" var="item">--%>
<%--							<li class="nav-item has-treeview">--%>
<%--								<c:if test="${item.parentProductId eq 0}">--%>
<%--									<a href="javascript:void(0);" class="nav-link">--%>
<%--										<i class="${item.icon}"></i>--%>
<%--										<p>--%>
<%--												${item.name }--%>
<%--											<i class="right fas fa-angle-left"></i>--%>
<%--										</p>--%>
<%--									</a>--%>

<%--									<ul class="nav nav-treeview">--%>
<%--										<c:forEach items="${productModuleMenus}" var="itemChild">--%>
<%--											<c:if test="${itemChild.parentProductId eq item.id}">--%>
<%--												<li class="nav-item">--%>
<%--													<a href="${contextPath}${itemChild.url }" class="nav-link"><i class="far fa-circle nav-icon"></i><p>${itemChild.name }</p></a>--%>
<%--												</li>--%>
<%--											</c:if>--%>
<%--										</c:forEach>--%>
<%--									</ul>--%>
<%--								</c:if>--%>
<%--							</li>--%>
<%--						</c:forEach>--%>

<%--						&lt;%&ndash;						Quản lý giỏ hàng&ndash;%&gt;--%>
<%--						<c:forEach items="${productModuleMenus}" var="item">--%>
<%--							<li class="nav-item has-treeview">--%>
<%--								<c:if test="${item.parentProductId eq 0}">--%>
<%--									<a href="javascript:void(0);" class="nav-link">--%>
<%--										<i class="${item.icon}"></i>--%>
<%--										<p>--%>
<%--												${item.name }--%>
<%--											<i class="right fas fa-angle-left"></i>--%>
<%--										</p>--%>
<%--									</a>--%>

<%--									<ul class="nav nav-treeview">--%>
<%--										<c:forEach items="${productModuleMenus}" var="itemChild">--%>
<%--											<c:if test="${itemChild.parentProductId eq item.id}">--%>
<%--												<li class="nav-item">--%>
<%--													<a href="${contextPath}${itemChild.url }" class="nav-link"><i class="far fa-circle nav-icon"></i><p>${itemChild.name }</p></a>--%>
<%--												</li>--%>
<%--											</c:if>--%>
<%--										</c:forEach>--%>
<%--									</ul>--%>
<%--								</c:if>--%>
<%--							</li>--%>
<%--						</c:forEach>--%>
					</ul>
				</nav>
				<!-- /.sidebar-menu -->
			</div>
			<!-- /.sidebar -->
		</aside>