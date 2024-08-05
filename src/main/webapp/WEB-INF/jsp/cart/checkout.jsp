<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../layout/header.jsp"%>
<%@include file="../layout/js.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

	.container {
		max-width: 960px;
	}

	.lh-condensed { line-height: 1.25; }
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>Danh sách sản phẩm</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item">
							<a href="${contextPath}/">Trang chủ</a>
						</li>
						<li class="breadcrumb-item active">Danh sách sản phẩm</li>
					</ol>
				</div>
			</div>
		</div>
	</section>

	<div class="container">
		<div class="row">
			<div class="col-md-4 order-md-2 mb-4">
				<h4 class="d-flex justify-content-between align-items-center mb-3">
					<span class="text-muted">Your cart</span>
					<span class="badge badge-secondary badge-pill">${cartItems.size()}</span>

				</h4>
				<ul class="list-group mb-3 sticky-top">
					<c:forEach items="${cartItems}" var="item">
						<li class="list-group-item d-flex justify-content-between lh-condensed">
							<div>
								<h6 class="my-0">${item.productInfo.productName}</h6>
								<small class="text-muted" id="quantity-${item.id}" name="quantity">Số lượng : ${item.quantity}</small>
							</div>
							<span class="text-muted">$<span id="totalPrice-${item.id}">${item.productInfo.price * item.quantity}</span></span>
						</li>
					</c:forEach>
					<li class="list-group-item d-flex justify-content-between lh-condensed">
						<div>
							<h6 class="my-0">SHIPPING</h6>
							<small class="text-muted"  name="">
								Standard-Delivery
							</small>
						</div>
						<span class="text-muted">$<span>5.00</span></span>
					</li>
					<li class="list-group-item d-flex justify-content-between">
						<span>Total (USD)</span>
						<strong>$${totalPrice + 5}</strong>
					</li>
				</ul>
			</div>
			<div class="col-md-8 order-md-1">
				<h4 class="mb-3">Billing address</h4>
				<springForm:form method="POST" action="${contextPath}/checkout" id='submitFormModal' modelAttribute="receipt">
					<div class="row">
						<div class="col-md-6 mb-3">
							<label for="firstname" id="firstname">First name</label>
							<input type="text" class="form-control" value="${Receipt.receiptFirstName}" placeholder="" name="firstname" required>
							<div class="invalid-feedback">Valid first name is required.</div>
						</div>
						<div class="col-md-6 mb-3">
							<label for="lastName">Last name</label>
							<input type="text" class="form-control" value="${Receipt.receiptLastName}" id="lastName" placeholder="" name="lastName" required>
							<div class="invalid-feedback">Valid last name is required.</div>
						</div>
					</div>
					<div class="mb-3">
						<label for="phone" id="phone">Phone <span class="text-muted"></span></label>
						<input type="text" class="form-control" value="${Receipt.receiptPhone}" placeholder="(+84)" name="phone">
					</div>
					<div class="mb-3">
						<label for="address">Address</label>
						<input type="text" class="form-control" id="address" value="${Receipt.receiptAddress}" placeholder="1234 Main St" name="address" required>
						<div class="invalid-feedback">Please enter your shipping address.</div>
					</div>
					<hr class="mb-4">
					<h4 class="mb-3">Payment</h4>
					<div class="d-block my-3">
						<div class="custom-control custom-radio">
							<input id="tienmat" name="paymentMethod" type="radio" class="custom-control-input" checked required>
							<label class="custom-control-label" for="tienmat">Thanh toán khi nhận hàng</label>
						</div>
						<div class="custom-control custom-radio">
							<input id="credit" name="paymentMethod" type="radio" class="custom-control-input" required>
							<label class="custom-control-label" for="credit">Credit card</label>
						</div>
						<div class="custom-control custom-radio">
							<input id="nganhang" name="paymentMethod" type="radio" class="custom-control-input" required>
							<label class="custom-control-label" for="nganhang">Ứng dụng ngân hàng</label>
						</div>
						<div class="custom-control custom-radio">
							<input id="paypal" name="paymentMethod" type="radio" class="custom-control-input" required>
							<label class="custom-control-label" for="paypal">PayPal</label>
						</div>
					</div>
					<hr class="mb-4">
						<!-- Form fields -->
						<button class="btn btn-primary btn-lg btn-block" type="submit">In hóa đơn</button>


				</springForm:form>

			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
	<script>
		(function () {
			'use strict'
			window.addEventListener('load', function () {
				var forms = document.getElementsByClassName('needs-validation')
				Array.prototype.filter.call(forms, function (form) {
					form.addEventListener('submit', function (event) {
						if (form.checkValidity() === false) {
							event.preventDefault()
							event.stopPropagation()
						}
						form.classList.add('was-validated')
					}, false)
				})
			}, false)
		}())
	</script>
</div>
<%@include file="../layout/footer.jsp"%>
