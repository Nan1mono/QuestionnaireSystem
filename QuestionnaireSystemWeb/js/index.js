$(document).ready(function(){








    
    $("#sidebar-menu a").click(function(){
        $("#sidebar-menu .active").removeClass('active');
        $(this).parent().addClass("active");
        var url = $(this).attr("url");
        $("#content").load(url+"?s="+new Date().getTime());
    });




});