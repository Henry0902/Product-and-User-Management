
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@include file="../layout/js.jsp" %>


<springForm:form method="POST" id='submitFormModal' enctype="multipart/form-data" modelAttribute="productInfo">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
            <div class="modal-header">
                <h2>${name} sản phẩm</h2>
            </div>
            <div class="modal-body">
                <div class="row clearfix">
                    <div class="col-md-6 mb-0">
                        <label>Tên sản phẩm</label>
                        <div class="form-group form-float">
                            <div class="form-line">
                                <input type="text" class="form-control" value="${productInfo.productName }"
                                       name="productName" autocomplete="nofill">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 mb-0">
                        <label class="form-label">Giá</label>
                        <div class="form-group form-float">
                            <div class="form-line">
                                <input type="text" class="form-control" value="${productInfo.price }" name="price"
                                       autocomplete = "nofill">
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6 mb-0">

                        <p>Ảnh</p>
                        <div class="custom-file">
                            <input type="file" class="custom-file-input" name="productImage" accept="image/jpeg, image/png" id="productImage"/>
                            <label class="custom-file-label" for="productImage">Choose file</label>
                        </div>
                        <div class="form-group">
                            <c:choose>
                                <c:when test="${not empty productInfo.imageName}">
                                    <img src="${pageContext.request.contextPath}/img/product/${productInfo.imageName}" id="imgPreview" height="100px" width="100px" style="margin-top: 20px" alt="Product Image"/>
                                </c:when>
                                <c:otherwise>
                                    <img src="#" id="imgPreview" height="100px" width="100px" style="margin-top: 20px" alt="No Image"/>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <input type="hidden" name="imgName" value="${productInfo.imageName}">

                    </div>

                    <div class="col-md-6 mb-0">
                        <label class="form-label">Mô tả</label>
                        <div class="form-group form-float">
                            <div class="form-line">
                                <input type="text" class="form-control" value="${productInfo.productDesc }" name="productDesc"
                                       autocomplete = "nofill">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 mb-0">
                        <label class="form-label">Nguồn gốc xuất xứ</label>
                        <div class="form-group form-float">
                            <div class="form-line">
                                <input type="text" class="form-control" value="${productInfo.productOrigin }"
                                       name="productOrigin">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 mb-0">
                        <label class="form-label">Ngày sản xuất</label>
                        <div class="form-group form-float">
                            <div class="form-line">
                                <input type="date" class="form-control datepicker" value="${productInfo.productDate }" name="productDate">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 mb-0">
                        <label class="form-label">Nhóm sản phẩm</label>
                        <div class="form-group form-float">
                            <div class="form-line">
                                <select class="form-control show-tick" name="groupId">
                                    <option value="0">-- Chọn nhóm sản phẩm --</option>

                                    <c:forEach items="${productGroups }" var="item">
                                        <option value="${item.id }"
                                                <c:if test="${productInfo.groupId eq item.id }">selected="selected"</c:if>>${item.groupProductName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 mb-0">
                        <label class="form-label">Trạng thái</label>
                        <div class="form-group form-float">
                            <div class="form-line">
                                <select class="form-control show-tick" name="status">
									<option value="" ${empty productInfo.status ? 'selected' : ''}>Tất cả</option>
									<option value="0" ${productInfo.status eq '0' ? 'selected': ''}>Không hoạt động</option>
									<option value="1" ${productInfo.status eq '1' ? 'selected': ''}>Hoạt động</option>
								</select>
                            </div>
                        </div>
                    </div>
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
<script type="text/javascript">
    $(document).ready(function () {
        $('.datepicker').datepicker({
            autoclose: true,
            format: 'dd/mm/yyyy'
        })

        $.validator.addMethod("valueNotEquals", function (value, element, arg) {
            return arg !== value;
        }, "Value must not equal arg.");

        $("#submitFormModal").validate({
            ignore: function (index, el) {
                let $el = $(el);
                if ($el.hasClass('always-validate')) {
                    return false;
                }
                return $el.is(':hidden');
            },
            rules: {
                productName: {required: true},
                productDesc: {required: true},
                productOrigin: {required: true},
                productDate: {required: true},
                groupId: {valueNotEquals: "0"}
            },
            messages: {
                productName: {
                    required: "Nhập tên sản phẩm",
                },
                productDesc: {
                    required: "Nhập mô tả"
                },
                productOrigin: {
                    required: "Nhập nguồn gốc",
                },
                productDate: {
                    required: "Nhập ngày sản xuất",
                },
                groupId: {valueNotEquals: "Chọn nhóm sản phẩm"}
            },
            submitHandler: function(form) {
                $('#ajax-error').text('');
                if('${productInfo.id}') {
                    form.submit();
                }else{
                    let name = $('input[name="productName"]').val();
                    $.ajax({
                        url: '${contextPath}/api/product-info/' + '?name=' + name,
                        cache: false,
                        beforeSend: function(xhr, s){
                            $('#ajax-load').removeClass('hidden');
                        },
                        success: function (count) {
                            if(count > 0){
                                $('#ajax-error').text('Tên sản phẩm đã tồn tại');
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

            }
        });
    });
</script>

<script>
    function readURL(input){
        if(input.files && input.files[0]){
            var reader = new FileReader();
            reader.onload = function(e){
                $('#imgPreview').attr('src', e.target.result).width(100).height(100);
            }
            reader.readAsDataURL(input.files[0])
        }
    }
    $('#productImage').change(function(){
        readURL(this);
    });
    $(".custom-file-input").on("change", function() {
        var fileName = $(this).val().split("\\").pop();
        $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    });
</script>

<%@include file="../layout/footerAjax.jsp" %>
