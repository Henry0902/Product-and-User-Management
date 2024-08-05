<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@include file="../layout/js.jsp"%>

<link href="${contextPath}/plugins/nestable/jquery-nestable.css" rel="stylesheet" />
<script src="${contextPath}/plugins/nestable/jquery.nestable.js"></script>
<script src="${contextPath}/js/sortable-nestable.js"></script>

<script type="text/javascript">
    $(document).ready(
        function() {
            var productGroupPermissions = '${productGroupPermissions}';
            if (productGroupPermissions) {
                var productGroupPermissionsJson = JSON
                    .parse(productGroupPermissions);
                for (var i = 0; i < productGroupPermissionsJson.length; i++) {
                    $("#checkbox" + productGroupPermissionsJson[i].moduleId)
                        .prop('checked', true);
                }
            }

            $("#submitFormModal").validate({
                rules : {
                    "groupProductName" : {
                        required : true,
                    }
                },
                messages : {
                    "groupProductName" : {
                        required : "Nhập vào tên nhóm sản phẩm",
                    }
                },
                errorPlacement : function(error, element) {
                    $(element).parent().parent().append(error);
                },
                submitHandler: function(form) {
                    $('#ajax-error').text('');
                    let name = $('input[name="groupProductName"]').val();
                    $.ajax({
                        url: '${contextPath}/api/product-group?name=' + name + '&id='+ '${productGroup.id}',
                        cache: false,
                        beforeSend: function(xhr, s){
                            $('#ajax-load').removeClass('hidden');
                        },
                        success: function (count) {
                            if(count > 0){
                                $('#ajax-error').text('Tên nhóm sản phẩm đã tồn tại');
                                $('#ajax-load').addClass('hidden');
                            }else{
                                form.submit();
                            }
                        },
                        error: function (jqXHR, status, err) {
                            console.log(jqXHR, status, err);
                            $('#ajax-error').text(err);
                            $('#ajax-load').addClass('hidden');
                        }
                    });
                }

            });
        });
</script>

<springForm:form method="POST" action="" id='submitFormModal' modelAttribute="productGroup">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="modal-header">
            <h2>${name } nhóm sản phẩm</h2>
        </div>
        <div class="modal-body">
            <div class="row clearfix">
                <div class="col-md-4">
                    <label class="form-label">Tên nhóm sản phẩm</label>
                    <div class="form-group form-float">
                        <div class="form-line">
                            <input type="text" class="form-control" value="${productGroup.groupProductName }" name="groupProductName">
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <label class="form-label">Số lượng sản phẩm</label>
                    <div class="form-group form-float">
                        <div class="form-line">
                            <input type="text" class="form-control" value="${productGroup.quantity }" name="quantity">
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <label class="form-label">Trạng thái</label>
                    <div class="form-group form-float">
                        <div class="form-line">
                            <select class="form-control show-tick" name="status">
								<option value="" ${empty productGroup.status ? 'selected' : ''}>Tất cả</option>
								<option value="0" ${productGroup.status eq '0' ? 'selected': ''}>Không hoạt động</option>
								<option value="1" ${productGroup.status eq '1' ? 'selected': ''}>Hoạt động</option>
							</select>
                        </div>
                    </div>
                </div>
<%--                <div class="col-md-12">--%>
<%--                    <div class="dd">--%>
<%--                        <ol class="dd-list">--%>
<%--                            <c:forEach items="${productModules}" var="item">--%>
<%--                                <c:if test="${item.parentProductId eq 0}">--%>
<%--                                    <li class="dd-item" data-id="${item.id }">--%>
<%--                                        <div class="dd-handle">${item.name }</div>--%>
<%--                                        <ol class="dd-list">--%>
<%--                                            <c:forEach items="${productModules}" var="itemChild">--%>
<%--                                                <c:if test="${itemChild.parentProductId eq item.id}">--%>
<%--                                                    <li class="dd-item" data-id="${itemChild.id }">--%>
<%--                                                        <div class="dd-handle">--%>
<%--                                                            <input type="checkbox" class="filled-in chk-col-red" id="checkbox${itemChild.id }" value="${itemChild.id }" name="permissions" />--%>
<%--                                                            <label for="checkbox${itemChild.id }">${itemChild.name }</label>--%>
<%--                                                        </div>--%>
<%--                                                    </li>--%>
<%--                                                </c:if>--%>
<%--                                            </c:forEach>--%>
<%--                                        </ol>--%>
<%--                                    </li>--%>
<%--                                </c:if>--%>
<%--                            </c:forEach>--%>
<%--                        </ol>--%>
<%--                    </div>--%>
<%--                </div>--%>
                <div class="col-md-12 mb-0 text-center">
                    <span style="color: red;font-size: 12px;" id="ajax-error"></span>
                </div>
                <div class="col-md-12 mb-0 text-right">
                    <div id="ajax-load" class="preloader pl-size-xs hidden">
                        <div class="spinner-layer pl-red-grey">
                            <div class="circle-clipper left">
                                <div class="circle"></div>
                            </div>
                            <div class="circle-clipper right">
                                <div class="circle"></div>
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary btn-sm"><i
                            class="fa fa-save"></i> <span>Lưu</span></button>
                    <button class="btn btn-danger btn-sm" data-dismiss="modal"><i
                            class="fa fa-times"></i> <span>Đóng</span></button>
                </div>
            </div>
        </div>
    </div>
</springForm:form>

<%@include file="../layout/footerAjax.jsp"%>