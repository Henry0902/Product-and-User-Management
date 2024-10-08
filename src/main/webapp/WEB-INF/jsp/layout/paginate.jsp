<%@ page contentType="text/html; charset=UTF-8"%>

<div class="paging">
    <input type='hidden' value="${currentPage }" name="page" id="page"/>
    <input type='hidden' value="${order }" name="order" id="orderVal"/>
    <input type='hidden' value="${sort }" name="sort" id="sortVal"/>
    <input type='hidden' value="${limit}" name="limit"/>
    
    <div class="card-footer clearfix">
        <c:set var="bound" value="3"/>
        <c:set var="startIndex" value="${currentPage - bound > 1 ? currentPage - bound : 1}"/>
        <c:set var="endIndex" value="${currentPage + bound < totalPage ? currentPage + bound : totalPage}"/>
        <ul class="pagination pagination-sm m-0 float-right">
        	<li class="page-item" style="margin-right: 15px;margin-top: 4px;">Trang ${currentPage}/${totalPage} (${totalElement} kết quả)</li>
            <li class="page-item ${currentPage eq 1 ? 'disabled':''}">
                <a href="javascript:void(0)" title="Về đầu trang" data-value="begin" class="page-link">
                     <i class="	fas fa-angle-double-left"></i>
                </a>
            </li>
            <li class="page-item ${currentPage eq 1 ? 'disabled':''}">
                <a href="javascript:void(0)" title="Trang trước" data-value="prev" class="page-link">
                    <i class="fas fa-angle-left"></i>
                </a>
            </li>
            <c:if test="${currentPage - bound > 1}">
                <li>
                    <span>...</span>
                </li>
            </c:if>
            <c:forEach begin="${startIndex}" end="${endIndex}" var="i">
                <li class="${i eq currentPage ? 'active':''}">
                    <a href="javascript:void(0)" title="Trang ${i}" data-value="${i}" class="page-link">${i}</a>
                </li>
            </c:forEach>

            <c:if test="${currentPage + bound < totalPage}">
                <li>
                    <span>...</span>
                </li>
            </c:if>
            <li class="page-item ${totalPage eq 0 || currentPage eq totalPage ? 'disabled':''}">
                <a href="javascript:void(0)" title="Trang sau" data-value="next" class="page-link">
                    <i class="fas fa-angle-right"></i>
                </a>
            </li>
            <li class="page-item ${currentPage >= totalPage ? 'disabled':''}">
                <a href="javascript:void(0)" title="Đến cuối trang" data-value="end" class="page-link">
                   <i class="fas fa-angle-double-right"></i>
                </a>
            </li>
        </ul>
    </div>
    <div style="clear: both"></div>
</div>