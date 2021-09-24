(function(){
    var cookie = $.cookie("userName");
    if(cookie == null || cookie == ""){


        console.log(cookie+"       "+typeof(cookie));
        location.href="login.html";
    }



    




})(window);;