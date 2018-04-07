$(document).ready(function(){
    $("#submitId").click(function(){
        var userName = $("#userNameId").val();
        var password = $("#passwordId").val();
        var codeId = $("#codeId").val();
        var dataJson ="{\"userName\":\""+userName+"\",\"password\":\""+password
            +"\",\"code\":\""+codeId+"\"}";
        $.ajax({
            type: "POST",
            url: "login",
            cache: false,
            contentType:"application/json;charset=utf-8",
            dataType: "json",
            data: dataJson,
            success: function(dataJson,status){
                // alert(dataJson+status)
                if (dataJson.code==0){
                    return window.top.location.href="/app/index";
                }else if(dataJson.code==1){
                    alert(JSON.stringify(dataJson));
                    return ;
                }else if (dataJson.code==2){
                    alert(JSON.stringify(dataJson));
                    return ;
                }
            },
            error: function() {
                alert("没有查询到信息！");
            }
        })
    });
});

// 刷新图片
function changeImg() {
    var imgSrc = $("#imgObj");
    var src = imgSrc.attr("src");
    imgSrc.attr("src", changeUrl(src));
}

//为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
function changeUrl(url) {
    var timestamp = (new Date()).valueOf();
    var index = url.indexOf("?",url);
    if (index > 0) {
        url = url.substring(index, url.indexOf(url, "?"));
    }
    if ((url.indexOf("&") >= 0)) {
        url = url + "×tamp=" + timestamp;
    } else {
        url = url + "?" + timestamp;
    }
    return url;
}