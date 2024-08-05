<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@include file="../layout/js.jsp" %>


<springForm:form method="POST" action="" id='submitFormModal' modelAttribute="userInfo">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
            <div class="modal-header">
                <h2>${name} người dùng</h2>
            </div>
            <div class="modal-body">
                <div class="row clearfix">
                    <div class="col-md-6 mb-0">
                        <label>Tên đăng nhập</label>
                        <div class="form-group form-float">
                            <div class="form-line">
                                <input type="text" class="form-control" value="${userInfo.username }"
                                       name="username" autocomplete="nofill">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 mb-0">
                        <label class="form-label">Mật khẩu</label>
                        <div class="form-group form-float">
                            <div class="form-line">
                                <input type="password" class="form-control" value="" name="password"
                                       autocomplete = "new-password">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 mb-0">
                        <label class="form-label">Họ và tên</label>
                        <div class="form-group form-float">
                            <div class="form-line">
                                <input type="text" class="form-control" value="${userInfo.fullName }"
                                       name="fullName">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 mb-0">
                        <label class="form-label">Email</label>
                        <div class="form-group form-float">
                            <div class="form-line">
                                <input type="text" class="form-control" value="${userInfo.email }" name="email">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 mb-0">
                        <label class="form-label">Address</label>
                        <div class="form-group form-float">
                            <div class="form-line">
                                <input type="text" class="form-control" value="${userInfo.address }" name="address">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 mb-0">
                        <label class="form-label">Phone</label>
                        <div class="form-group form-float">
                            <div class="form-line">
                                <input type="text" class="form-control" value="${userInfo.phone }" name="phone">
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6 mb-0">
                        <label class="form-label">Nhóm người dùng</label>
                        <div class="form-group form-float">
                            <div class="form-line">
                                <select class="form-control show-tick" name="groupId">
                                    <option value="0">-- Chọn nhóm người dùng --</option>
                                    <c:forEach items="${userGroups }" var="item">
                                        <option value="${item.id }"
                                                <c:if test="${userInfo.groupId eq item.id }">selected="selected"</c:if>>${item.groupName}</option>
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
									<option value="" ${empty userInfo.status ? 'selected' : ''}>Tất cả</option>
									<option value="0" ${userInfo.status eq '0' ? 'selected': ''}>Không hoạt động</option>
									<option value="1" ${userInfo.status eq '1' ? 'selected': ''}>Hoạt động</option>
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
                username: {required: true},
                fullName: {required: true},
                password: {
                    required: true,
                    minlength: 8
                },
                groupId: {valueNotEquals: "0"}
            },
            messages: {
                username: {
                    required: "Nhập tên đăng nhập",
                },
                fullName: {
                    required: "Nhập họ và tên"
                },
                password: {
                    required: "Nhập vào mật khẩu",
                    minlength: "Hãy nhập ít nhất 8 ký tự"
                },
                groupId: {valueNotEquals: "Chọn nhóm người dùng"}
            },
            submitHandler: function(form) {
                $('#ajax-error').text('');
                if('${userInfo.id}') {
                    form.submit();
                }else{
                    let name = $('input[name="username"]').val();
                    $.ajax({
                        url: '${contextPath}/api/user-info/' + '?name=' + name,
                        cache: false,
                        beforeSend: function(xhr, s){
                            $('#ajax-load').removeClass('hidden');
                        },
                        success: function (count) {
                            if(count > 0){
                                $('#ajax-error').text('Tên đăng nhập đã tồn tại');
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

<%@include file="../layout/footerAjax.jsp" %>
