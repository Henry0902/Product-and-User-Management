<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="../layout/header.jsp"%>
<%@include file="../layout/js.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="container-fluid">
			<div class="row mb-2">
				<div class="col-sm-6">
					<h1>Danh sách nhóm người dùng</h1>
				</div>
				<div class="col-sm-6">
					<ol class="breadcrumb float-sm-right">
						<li class="breadcrumb-item">
							<a href="${contextPath}/">Trang chủ</a>
						</li>
						<li class="breadcrumb-item active">Danh sách nhóm người dùng</li>
					</ol>
				</div>
			</div>
		</div>
		<!-- /.container-fluid -->
	</section>
	<form id="submitForm" action="" method="get">
	    <section class="content">
	        <div class="container-fluid">
	            <div class="row clearfix">
	                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
	                    <div class="card">
	                        <div class="card-header">
	                            <h2 class="card-title">
	                            	<button class="btn btn-block btn-primary btn-xs"
                                            data-toggle="modal" data-target="#largeModal" type="button"
                                            onclick="loadAdd('${contextPath}/nhom-nguoi-dung/them-moi')">
                                            <i class="fa fa-plus" aria-hidden="true"></i>
                                            
                                        <span>Thêm mới</span>
                                    </button>
	                            </h2>
	                            <div class="card-tools">
									<div class="input-group input-group-sm">
										<input class="form-control form-control-xs" type="text" value="${s_gname}" name="s_gname" placeholder="Tên nhóm" data-toggle="tooltip" title="Tên nhóm"/>
										<input class="form-control form-control-xs" type="text" value="${s_desc}" name="s_desc" placeholder="Mô tả" data-toggle="tooltip" title="Mô tả"/>
										<select class="form-control form-control-xs" name="s_status" data-toggle="tooltip" title="Trạng thái">
                                            <option value="" ${empty s_status ? 'selected' : ''}>Tất cả</option>
                                            <option value="0" ${s_status eq '0' ? 'selected': ''}>Không hoạt động
                                            </option>
                                            <option value="1" ${s_status eq '1' ? 'selected': ''}>Hoạt động</option>
                                        </select>
										<div class="input-group-append">
											<button type="submit" class="btn btn-default">
												<i class="fas fa-search"></i>
											</button>
										</div>
									</div>
								</div>
	                        </div>
	                        <div class="body table-responsive p-0">
	                            <table class="table table-bordered table-sm table-striped">
	                                <thead>
		                                <tr>
		                                    <th style="width: 50px;text-align: center;">#</th>
		                                    <th>Tên nhóm</th>
		                                    <th>Mô tả</th>
		                                    <th style="width: 180px;">Trạng thái</th>
		                                    <th style="width: 100px;"></th>
		                                </tr>
	                                </thead>
	                                <tbody>
	                                <c:forEach items="${userGroups }" var="item" varStatus="status">
	                                    <tr>
	                                        <th scope="row" style="text-align: center;">${status.index+1 }</th>
	                                        <td>${item.groupName }</td>
	                                        <td>${item.description }</td>
	                                        <td>
												<c:if test="${item.status eq 1 }">
													<span class="text-primary">Hoạt động</span>
												</c:if>
												<c:if test="${item.status ne 1 }">
													<span class="text-danger">Không hoạt động</span>
												</c:if>
											</td>
	                                        <td class="text-center">
	                                            <a href="javascript:void(0)" onclick="loadEdit('${contextPath}/nhom-nguoi-dung/sua?id=${item.id}')" data-toggle="modal" data-target="#largeModal" class="text-info">
	                                                <i class="fa fa-edit"></i>
	                                            </a>
	                                            <a href="javascript:void(0)" onclick="deleteRC('${contextPath}/nhom-nguoi-dung/xoa?id=${item.id}')" class="text-danger" style="margin-left: 5px;">
	                                                <i class="fa fa-trash"></i>
	                                            </a>
	                                        </td>
	                                    </tr>
	                                </c:forEach>
	                                </tbody>
	                            </table>
	
	                            <%@include file="../layout/paginate.jsp"%>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </section>
	</form>
</div>
<%@include file="../layout/footer.jsp"%>