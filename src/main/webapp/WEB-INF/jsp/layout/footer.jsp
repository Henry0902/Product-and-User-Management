<%@ page contentType="text/html; charset=UTF-8"%>
<div class="modal fade" id="largeModal" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content" id="modal-content">
		</div>
	</div>
</div>
<!-- /.content-wrapper -->
<footer class="main-footer">
	<div class="float-right d-none d-sm-block">
		<b>Version</b>
		1.0.0
	</div>
	<strong>
		Copyright &copy; 
	</strong>
	All rights reserved.
</footer>

<!-- Control Sidebar -->
<aside class="control-sidebar control-sidebar-dark">
	<!-- Control sidebar content goes here -->
</aside>
<!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->

<!-- jQuery -->

<%--<!-- jQuery, Popper.js, and Bootstrap JS -->--%>
<%--<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>--%>
<%--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>--%>

<!-- Bootstrap 4 -->
<script src="${contextPath }/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- bs-custom-file-input -->
<script src="${contextPath }/plugins/bs-custom-file-input/bs-custom-file-input.min.js"></script>
<script src="${contextPath }/plugins/pace-progress/pace.min.js"></script>
<!-- AdminLTE App -->
<script src="${contextPath }/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="${contextPath }/js/demo.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		bsCustomFileInput.init();
		$('[data-toggle="tooltip"]').tooltip();
	});
</script>
</body>
</html>