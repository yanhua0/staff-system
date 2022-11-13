$(function () {
    $(".edit").click(function () {
        var id = $(this).siblings(".aid").val();
        $("#editId").val(id);
        $.get("/wages/" + id, {}, function (result) {
            console.log("result: " + JSON.stringify(result));
            $("#form2 input").each(function (index, dom) {
                var name = $(dom).attr("name");
                var val = result[name];
                if (val != null && name !=null) {
                    $(dom).val(val);
                }
            });
        });

    })

    $("#staffName").bind("input propertychange",function () {
        var staffName = $("#staffName").val();
        $.get("/queryByName/" + staffName, {}, function (result) {
            console.log("result: " + JSON.stringify(result));
            $("#form1 input").each(function (index, dom) {
                var name = $(dom).attr("name");
                var val = result[name];
                if (val != null && name !=null) {
                    $(dom).val(val);
                }
            });
            var bankName=result["bankDesc"];
            if(bankName!=null){
                $("#bankDesc").val(bankName);
            }
        });

    })
    $("#query").bind("input propertychange",function () {
        console.log("result: ");
           $("#query").submit();
    });

    $("#init").click(function () {
        window.location.href = "/";
    });
    $("#export").click(function () {
        var str = {"staffName":$("#name").val(),"settlementTime":$("#settlementTime").val()}
        $("#query input").each(function (index, dom) {
            var name = $(dom).attr("name");
            var val = $(dom).attr("value");
            console.log( $(dom))
            if (val != null&&val !== ""&& name !=null) {
                str[name] = val;
            }

        });
        console.log(JSON.stringify(str))
        var param="";
        for (var key in str) {
           param=param+key+"="+str[key]+"&"
        }
        if(param.length>0){
            param=param.substring(0,param.length-1);
        }
        console.log("param: "+param)
        window.location.href ="/export?"+param
    });
});
