<script type="text/javascript">

$(function () {
    $.AdminBSB.input.activate();
    $.AdminBSB.select.activate();
    
    $('.yearpicker').datepicker({
        format: "yyyy",
        viewMode: "years", 
        minViewMode: "years",
        autoclose: true
    });
});
</script>