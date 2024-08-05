<%@ page contentType="text/html; charset=UTF-8"%>
<input type="hidden" value="${success}" id="success"/>
<input type="hidden" value="${error}" id="error"/>
<script type="text/javascript">
$(document).ready(function(){

	if($("#success").val() != "") {
		toastr.info($("#success").val());
    }
    if($("#error").val() != "") {
    	toastr.error($("#error").val());
    }
});
function loadAdd(urlAdd) {
	loadUrl(urlAdd);
}
function loadEdit(urlEdit) {
	loadUrl(urlEdit);
}
function loadUrl(url) {
	resetInfo();
	$.ajax({
		url : url,
		success : function(result) {
			$("#modal-content").html(result);
		}
	});
}
function resetInfo() {
	$("#error").val("");
	$("#success").val("");
	showWait();
}
function showWait() {
    $('#modal-content').html('<div  style="padding:10px;" class="d-flex justify-content-center"><div class="spinner-border text-primary" role="status"> <span class="sr-only">Loading...</span> </div></div>');

}

function processTable(data, idField, foreignKey, rootLevel) {
    let hash = {};

    for (let i = 0; i < data.length; i++) {
        let item = data[i];
        let id = item[idField];
        let parentId = item[foreignKey];

        hash[id] = hash[id] || [];
        hash[parentId] = hash[parentId] || [];

        item.items = hash[id];
        item.expanded = true;
        hash[parentId].push(item);
    }

    return hash[rootLevel];
}

function list_to_tree(list) {
        var map = {}, node, roots = [], i;
        for (i = 0; i < list.length; i += 1) {
            map[list[i].id] = i; // initialize the map
            list[i].items = []; // initialize the children
        }
        for (i = 0; i < list.length; i += 1) {
            node = list[i];
            if (node.parentId !== 0) {
                // if you have dangling branches check that map[node.parentId] exists
                list[map[node.parentId]].items.push(node);
            } else {
                roots.push(node);
            }
        }
        return roots;
    }


function alertSC(msg) {
	$.growl.notice({
        title: "Thông báo",
        message: msg
    });
}
function alertER(msg) {
	$.growl.error({
        title: "Thông báo",
        message: msg
    });
}
function deleteRC(url) {
	alertRC(url, "Bạn có chắc muốn xóa?");
}
function change(url) {
	alertRC(url, "Bạn có chắc muốn thay đổi?");
}
function alertRC(url, title) {
	swal({
		title: title,
        text: "",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Đồng ý",
        cancelButtonText: "Thoát",
        closeOnConfirm: false
    }, function () {
    	location.href=url;
    });
}
function goBack() {
  	window.history.back();
}
</script>