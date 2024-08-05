<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../layout/header.jsp"%>
<%@include file="../layout/js.jsp"%>

<script type="text/javascript">
$().ready(function() {
	$("#submitForm").validate({
		rules: {
			"old-password": {
				required: true,
				minlength: 8
			},
			"new-password": {
				required: true,
				minlength: 8
			},
			"re-password": {
				equalTo: "#new-password",
				minlength: 8
				
			}
		},
		messages: {
			"old-password": {
				required: "Bắt buộc nhập mật khẩu cũ",
				minlength: "Hãy nhập ít nhất 8 ký tự"
			},
			"new-password": {
				required: "Bắt buộc nhập mật khẩu mới",
				minlength: "Hãy nhập ít nhất 8 ký tự"
			},
			"re-password": {
				equalTo: "Hai mật khẩu phải giống nhau",
				minlength: "Hãy nhập ít nhất 8 ký tự"
			}
		}, 
        errorPlacement: function(error, element) {
        	$(element).parent().parent().append(error);
         }
	});
});
</script>
<div class="content-wrapper">
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>Thay đổi mật khẩu</h1>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
	<springForm:form method="POST" action="" id='submitForm'>
		<section class="content">
			<div class="container-fluid">
				<div class="row clearfix">
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<div class="card">
							<div class="card-body">
								<div class="row clearfix">
									<div class="col-md-12 mb-0">
										<div class="col-md-4 mb-0">
											<div class="form-group form-float">
												<div class="form-line">
													<label class="form-label">Mật khẩu cũ (<font style="color: red;">*</font>)</label>
													<input type="password" class="form-control" value="" name="old-password">
												</div>
											</div>
										</div>
										<div class="col-md-4 mb-0">
											<div class="form-group form-float">
												<div class="form-line">
													<label class="form-label">Mật khẩu mới (<font style="color: red;">*</font>)</label>
													<input type="password" class="form-control" value="" id="new-password" name="new-password">
												</div>
											</div>
										</div>
										<div class="col-md-4 mb-0">
											<div class="form-group form-float">
												<div class="form-line">
													<label class="form-label">Xác nhận mật khẩu mới (<font style="color: red;">*</font>)</label>
													<input type="password" class="form-control" value="" name="re-password">
												</div>
											</div>
										</div>
									</div>
									<div class="col-md-12">
										<a href="${contextPath}/" class="btn btn-default m-t-15 waves-effect">Quay lại</a>
										<button type="submit" class="btn btn-primary m-t-15 waves-effect">Đổi mật khẩu</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</springForm:form>
</div>
<%@include file="../layout/footer.jsp"%>