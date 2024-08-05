<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../layout/header.jsp"%>
<%@include file="../layout/js.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<style>
	/* Chrome, Safari, Edge, Opera */
	input::-webkit-outer-spin-button,
	input::-webkit-inner-spin-button {
		-webkit-appearance: none;
		margin: 0;
	}

	/* Firefox */
	input[type=number] {
		-moz-appearance: textfield;
	}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>Giỏ Hàng</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item">
							<a href="${contextPath}/">Trang chủ</a>
						</li>
						<li class="breadcrumb-item active">Quản lý giỏ hàng</li>
					</ol>
				</div>
			</div>
		</div>
	</section>

	<form id="submitForm" action="" method="post">
		<section class="h-100 h-custom" style="background-color: #d2c9ff;">
			<div class="container py-5 h-100">
				<div class="row d-flex justify-content-center align-items-center h-100">
					<div class="col-12">
						<div class="card card-registration card-registration-2" style="border-radius: 15px;">
							<div class="card-body p-0">
								<div class="row g-0">
									<div class="col-lg-8">
										<div class="p-5">
											<div class="d-flex justify-content-between align-items-center mb-5">
												<h1 class="fw-bold mb-0">Shopping Cart</h1>

											</div>
											<c:forEach items="${cartItems}" var="item">
												<hr class="my-4">

												<div class="row mb-4 d-flex justify-content-between align-items-center">
													<div class="col-md-2 col-lg-2 col-xl-2">
														<img src="${pageContext.request.contextPath}/img/product/${item.productInfo.imageName}" alt="${item.productInfo.productName}" class="img-fluid rounded-3" >
													</div>
													<div class="col-md-3 col-lg-3 col-xl-3">
<%--														<h6 class="text-muted">${producGroup.groupProductName}</h6>--%>

														<h6 class="mb-0">${item.productInfo.productName}</h6>
													</div>
													<div class="col-md-3 col-lg-3 col-xl-2 d-flex">
														<button type="button" class="btn btn-link px-2" onclick="this.parentNode.querySelector('input[type=number]').stepDown(); loadEdit('${contextPath}/cart/update-so-luong?id=${item.id}&dau=tru')">
															<i class="fas fa-minus"></i>
														</button>

														<input id="quantity-${item.id}" min="0" name="quantity" value="${item.quantity}" type="number" class="form-control form-control-sm" />

														<button type="button" class="btn btn-link px-2" onclick="this.parentNode.querySelector('input[type=number]').stepUp(); loadEdit('${contextPath}/cart/update-so-luong?id=${item.id}&dau=cong')">
															<i class="fas fa-plus"></i>
														</button>
													</div>
													<div class="col-md-3 col-lg-2 col-xl-2 offset-lg-1">
														<h6 class="mb-0">Total Price: $<span id="totalPrice-${item.id}">${item.productInfo.price * item.quantity}</span></h6>
													</div>
													<div class="col-md-1 col-lg-1 col-xl-1 text-end">
<%--														<input type="hidden" name="userId" value="${userId}"/>--%>
														<a href="javascript:void(0)" onclick="deleteRC('${contextPath}/cart/xoa?id=${item.id}')" class="text-muted"><i class="fas fa-times"></i></a>
													</div>
												</div>
											</c:forEach>

											<hr class="my-4">

											<div class="pt-5">
												<h6 class="mb-0"><a href="${contextPath}/home-shopping" class="text-body"><i class="fas fa-long-arrow-alt-left me-2"></i>Back to shop</a></h6>
											</div>
										</div>
									</div>
									<div class="col-lg-4 bg-body-tertiary">
										<div class="p-5">
											<h3 class="fw-bold mb-5 mt-2 pt-1">Summary</h3>
											<hr class="my-4">

											<div class="d-flex justify-content-between mb-4">
												<h5 class="text-uppercase">items ${cartItems.size()}</h5>
												<h5 id="totalPrice">€ ${totalPrice}</h5>
											</div>

											<h5 class="text-uppercase mb-3">Shipping</h5>

											<div class="mb-4 pb-2">
												<select data-mdb-select-init>
													<option value="1">Standard-Delivery- €5.00</option>
<%--													<option value="2">Two</option>--%>
<%--													<option value="3">Three</option>--%>
<%--													<option value="4">Four</option>--%>
												</select>
											</div>

<%--											<h5 class="text-uppercase mb-3">Give code</h5>--%>

<%--											<div class="mb-5">--%>
<%--												<div data-mdb-input-init class="form-outline">--%>
<%--													<input type="text" id="form3Examplea2" class="form-control form-control-lg" />--%>
<%--													<label class="form-label" for="form3Examplea2">Enter your code</label>--%>
<%--												</div>--%>
<%--											</div>--%>

											<hr class="my-4">

											<div class="d-flex justify-content-between mb-5">
												<h5 class="text-uppercase">Total price</h5>
												<h5>€ ${totalPrice + 5}</h5>
											</div>

												<a type="submit" href="${pageContext.request.contextPath}/checkout/${userId}" data-mdb-button-init data-mdb-ripple-init class="btn btn-dark btn-block btn-lg" data-mdb-ripple-color="dark">Thanh Toán</a>

										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</form>
</div>





<%@include file="../layout/footer.jsp"%>
