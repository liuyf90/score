/**
 * jeDate 演示
 */
$(function () {
    //常规选择


    // $("#time3").jeDate({
    //     format: "YYYY-MM-DD"
    // });
    // $("#time4").jeDate({
    //     format: "YYYY-MM-DD"
    // });

    // $("#time5").jeDate({
    //     format: "YYYY-MM-DD"
    // });

    $("#test02").jeDate({
        format: "YYYY-MM-DD hh:mm:ss"
    });
    // $("#test031").jeDate({
    //     format: "YYYY-MM-DD",
    //     okfun:function(obj) {
    //         $(obj.elem[0]).val(obj.val).change();
    //         $(obj.elem[0]).next().next().hide();
    //         $(obj.elem[0]).css("border","1px solid #3c763d");
    //         $(obj.elem[0]).parent().prev().css("color","#3c763d");
    //         $(obj.elem[0]).next().attr("class","form-control-feedback glyphicon glyphicon-ok").css("color","#3c763d");
    //     },
    //     clearfun:function(elem, val) {
    //         $(elem.elem[0]).next().next().show();
    //         $(elem.elem[0]).css("border","1px solid #a94442");
    //         $(elem.elem[0]).parent().prev().css("color","#a94442");
    //         $(elem.elem[0]).next().attr("class","form-control-feedback glyphicon glyphicon-remove").css("color","#a94442");
    //     }
    // });
    // $("#test041").jeDate({
    //     format: "YYYY-MM-DD",
    //     okfun:function(obj) {
    //         $(obj.elem[0]).val(obj.val).change();
    //         $(obj.elem[0]).next().next().hide();
    //         $(obj.elem[0]).css("border","1px solid #3c763d");
    //         $(obj.elem[0]).parent().prev().css("color","#3c763d");
    //         //color: #3c763d
    //         //form-control-feedback glyphicon glyphicon-ok
    //         $(obj.elem[0]).next().attr("class","form-control-feedback glyphicon glyphicon-ok").css("color","#3c763d");
    //     },
    //     clearfun:function(elem, val) {
    //         console.log(elem);
    //         $(elem.elem[0]).next().next().show();
    //         $(elem.elem[0]).css("border","1px solid #a94442");
    //         $(elem.elem[0]).parent().prev().css("color","#a94442");
    //         $(elem.elem[0]).next().attr("class","form-control-feedback glyphicon glyphicon-remove").css("color","#a94442");
    //     }
    // });
    // $("#testyi").jeDate({
    //     format: "YYYY-MM-DD hh:mm:ss",
    //     okfun:function(obj) {
    //         $(obj.elem[0]).val(obj.val).change();
    //         $(obj.elem[0]).next().next().hide();
    //         $(obj.elem[0]).css("border","1px solid #3c763d");
    //         $(obj.elem[0]).parent().prev().css("color","#3c763d");
    //         $(obj.elem[0]).next().attr("class","form-control-feedback glyphicon glyphicon-ok").css("color","#3c763d");
    //     },
    //     clearfun:function(elem, val) {
    //         $(elem.elem[0]).next().next().show();
    //         $(elem.elem[0]).css("border","1px solid #a94442");
    //         $(elem.elem[0]).parent().prev().css("color","#a94442");
    //         $(elem.elem[0]).next().attr("class","form-control-feedback glyphicon glyphicon-remove").css("color","#a94442");
    //     }
    // });
    // $("#tester").jeDate({
    //     format: "YYYY-MM-DD hh:mm:ss",
    //     okfun:function(obj) {
    //         $(obj.elem[0]).val(obj.val).change();
    //         $(obj.elem[0]).next().next().hide();
    //         $(obj.elem[0]).css("border","1px solid #3c763d");
    //         $(obj.elem[0]).parent().prev().css("color","#3c763d");
    //         //color: #3c763d
    //         //form-control-feedback glyphicon glyphicon-ok
    //         $(obj.elem[0]).next().attr("class","form-control-feedback glyphicon glyphicon-ok").css("color","#3c763d");
    //     },
    //     clearfun:function(elem, val) {
    //         console.log(elem);
    //         $(elem.elem[0]).next().next().show();
    //         $(elem.elem[0]).css("border","1px solid #a94442");
    //         $(elem.elem[0]).parent().prev().css("color","#a94442");
    //         $(elem.elem[0]).next().attr("class","form-control-feedback glyphicon glyphicon-remove").css("color","#a94442");
    //     }
    // });
    // $("#timeIn").jeDate({
    //     format: "YYYY-MM-DD"
    // });
    // $("#timeOut").jeDate({
    //     format: "YYYY-MM-DD"
    // });

    var timeIn = {
        format: 'YYYY-MM-DD hh:mm:ss',
        minDate: '2000-01-01 00:00:00', //设定最小日期为当前日期
        isinitVal:false,
        maxDate: $.nowDate({DD:0}), //最大日期
        okfun: function(obj){
            timeOut.minDate = obj.val; //开始日选好后，重置结束日的最小日期
            timeIn23();
        }
    };
    var timeOut = {
        format: 'YYYY-MM-DD hh:mm:ss',
        minDate: $.nowDate({DD:0}), //设定最小日期为当前日期
        maxDate: '2099-12-30 00:00:00', //最大日期
        okfun: function(obj){
            timeIn.maxDate = obj.val; //将结束日的初始值设定为开始日的最大日期
        }
    };
//这里是日期联动的关键
    function timeIn23() {
        //将结束日期的事件改成 false 即可
        timeOut.trigger = false;
        $("#timeOut").jeDate(timeOut);
    }
    $('#timeIn').jeDate(timeIn);
    $('#timeOut').jeDate(timeOut);











    var start1 = {
        format: 'YYYY-MM-DD hh:mm:ss',
        minDate: '2000-01-01 00:00:00', //设定最小日期为当前日期
        isinitVal:false,
        maxDate: $.nowDate({DD:0}), //最大日期
        okfun: function(obj){
            end1.minDate = obj.val; //开始日选好后，重置结束日的最小日期
            endDates();
        }
    };
    var end1 = {
        format: 'YYYY-MM-DD hh:mm:ss',
        minDate: $.nowDate({DD:0}), //设定最小日期为当前日期
        maxDate: '2099-12-30 00:00:00', //最大日期
        okfun: function(obj){
            start1.maxDate = obj.val; //将结束日的初始值设定为开始日的最大日期
        }
    };
//这里是日期联动的关键
    function endDates() {
        //将结束日期的事件改成 false 即可
        end1.trigger = false;
        $("#time2").jeDate(end1);
    }
    $('#time1').jeDate(start1);
    $('#time2').jeDate(end1);


    var test1 = {
        format: 'YYYY-MM-DD hh:mm:ss',
        minDate: '2000-01-01 00:00:00', //设定最小日期为当前日期
        isinitVal:false,
        maxDate: $.nowDate({DD:0}), //最大日期
        okfun: function(obj){
            test2.minDate = obj.val; //开始日选好后，重置结束日的最小日期
            endDates2();
        }
    };
    var test2 = {
        format: 'YYYY-MM-DD hh:mm:ss',
        minDate: $.nowDate({DD:0}), //设定最小日期为当前日期
        maxDate: '2099-12-30 00:00:00', //最大日期
        okfun: function(obj){
            test1.maxDate = obj.val; //将结束日的初始值设定为开始日的最大日期
        }
    };
//这里是日期联动的关键
    function endDates2() {
        //将结束日期的事件改成 false 即可
        test2.trigger = false;
        $("#test2").jeDate(test2);
    }
    $('#test1').jeDate(test1);
    $('#test2').jeDate(test2);



    var shi1 = {
        format: 'YYYY-MM-DD hh:mm:ss',
        minDate: '2000-01-01 00:00:00', //设定最小日期为当前日期
        isinitVal:false,
        maxDate: $.nowDate({DD:0}), //最大日期
        okfun: function(obj){
            shi2.minDate = obj.val; //开始日选好后，重置结束日的最小日期
            endDates3();
        }
    };
    var shi2 = {
        format: 'YYYY-MM-DD hh:mm:ss',
        minDate: $.nowDate({DD:0}), //设定最小日期为当前日期
        maxDate: '2099-12-30 00:00:00', //最大日期
        okfun: function(obj){
            shi1.maxDate = obj.val; //将结束日的初始值设定为开始日的最大日期
        }
    };
//这里是日期联动的关键
    function endDates3() {
        //将结束日期的事件改成 false 即可
        shi2.trigger = false;
        $("#shi2").jeDate(shi2);
    }
    $('#shi1').jeDate(shi1);
    $('#shi2').jeDate(shi2);


    var tester = {
        format: 'YYYY-MM-DD hh:mm:ss',
        minDate: '2000-01-01 00:00:00', //设定最小日期为当前日期
        isinitVal:false,
        maxDate: $.nowDate({DD:0}), //最大日期
        okfun: function(obj){
            testyi.minDate = obj.val; //开始日选好后，重置结束日的最小日期
            endDates4();
        }
    };
    var testyi = {
        format: 'YYYY-MM-DD hh:mm:ss',
        minDate: $.nowDate({DD:0}), //设定最小日期为当前日期
        maxDate: '2099-12-30 00:00:00', //最大日期
        okfun: function(obj){
            tester.maxDate = obj.val; //将结束日的初始值设定为开始日的最大日期
        }
    };
//这里是日期联动的关键
    function endDates4() {
        //将结束日期的事件改成 false 即可
        testyi.trigger = false;
        $("#testyi").jeDate(testyi);
    }
    $('#tester').jeDate(tester);
    $('#testyi').jeDate(testyi);




    var test04 = {
        format: 'YYYY-MM-DD hh:mm:ss',
        minDate: '2000-01-01 00:00:00', //设定最小日期为当前日期
        isinitVal:false,
        maxDate: $.nowDate({DD:0}), //最大日期
        okfun: function(obj){
            test03.minDate = obj.val; //开始日选好后，重置结束日的最小日期
            endDates5();
        }
    };
    var test03 = {
        format: 'YYYY-MM-DD hh:mm:ss',
        minDate: $.nowDate({DD:0}), //设定最小日期为当前日期
        maxDate: '2099-12-30 00:00:00', //最大日期
        okfun: function(obj){
            test04.maxDate = obj.val; //将结束日的初始值设定为开始日的最大日期
        }
    };
//这里是日期联动的关键
    function endDates5() {
        //将结束日期的事件改成 false 即可
        test03.trigger = false;
        $("#test03").jeDate(test03);
    }
    $('#test04').jeDate(test04);
    $('#test03').jeDate(test03);


    var time3 = {
        format: 'YYYY-MM-DD',
        minDate: '2000-01-01', //设定最小日期为当前日期
        isinitVal:false,
        maxDate: $.nowDate({DD:0}), //最大日期
        okfun: function(obj){
            time4.minDate = obj.val; //开始日选好后，重置结束日的最小日期
            endDates6();
        }
    };
    var time4 = {
        format: 'YYYY-MM-DD',
        minDate: $.nowDate({DD:0}), //设定最小日期为当前日期
        maxDate: '2099-12-30', //最大日期
        okfun: function(obj){
            time3.maxDate = obj.val; //将结束日的初始值设定为开始日的最大日期
        }
    };
//这里是日期联动的关键
    function endDates6() {
        //将结束日期的事件改成 false 即可
        time4.trigger = false;
        $("#time4").jeDate(time4);
    }
    $('#time3').jeDate(time3);
    $('#time4').jeDate(time4);


    var time5 = {
        format: 'YYYY-MM-DD hh:mm:ss',
        minDate: '2000-01-01 00:00:00', //设定最小日期为当前日期
        isinitVal:false,
        maxDate: $.nowDate({DD:0}), //最大日期
        okfun: function(obj){
            time6.minDate = obj.val; //开始日选好后，重置结束日的最小日期
            endDates7();
            $(obj.elem[0]).attr("value", obj.val);
            $(obj.elem[0]).next().next().hide();
            $(obj.elem[0]).css("border", "1px solid #3c763d")
            $(obj.elem[0]).parent().prev().css("color", "#3c763d");
            //color: #3c763d
            //form-control-feedback glyphicon glyphicon-ok
            $(obj.elem[0]).next().attr("class", "form-control-feedback glyphicon glyphicon-ok").css("color", "#3c763d");
        },
        clearfun: function (elem, val) {
            $(elem.elem[0]).next().next().show();
            $(elem.elem[0]).css("border", "1px solid #a94442")
            $(elem.elem[0]).parent().prev().css("color", "#a94442");
            $(elem.elem[0]).next().attr("class", "form-control-feedback glyphicon glyphicon-remove").css("color", "#a94442");
        }
    };
    var time6 = {
        format: 'YYYY-MM-DD hh:mm:ss',
        minDate: $.nowDate({DD:0}), //设定最小日期为当前日期
        maxDate: '2099-12-30 00:00:00', //最大日期
        okfun: function(obj){
            time5.maxDate = obj.val; //将结束日的初始值设定为开始日的最大日期
            $(obj.elem[0]).attr("value", obj.val);
            $(obj.elem[0]).next().next().hide();
            $(obj.elem[0]).css("border", "1px solid #3c763d")
            $(obj.elem[0]).parent().prev().css("color", "#3c763d");
            //color: #3c763d
            //form-control-feedback glyphicon glyphicon-ok
            $(obj.elem[0]).next().attr("class", "form-control-feedback glyphicon glyphicon-ok").css("color", "#3c763d");
        },
        clearfun: function (elem, val) {
            $(elem.elem[0]).next().next().show();
            $(elem.elem[0]).css("border", "1px solid #a94442")
            $(elem.elem[0]).parent().prev().css("color", "#a94442");
            $(elem.elem[0]).next().attr("class", "form-control-feedback glyphicon glyphicon-remove").css("color", "#a94442");
        }
    };
//这里是日期联动的关键
    function endDates7() {
        //将结束日期的事件改成 false 即可
        time6.trigger = false;
        $("#time6").jeDate(time6);
    }
    $('#time5').jeDate(time5);
    $('#time6').jeDate(time6);

    var time7 = {
        format: 'YYYY-MM-DD hh:mm:ss',
        minDate: '2000-01-01 00:00:00', //设定最小日期为当前日期
        isinitVal:false,
        maxDate: $.nowDate({DD:0}), //最大日期
        okfun: function(obj){
            time8.minDate = obj.val; //开始日选好后，重置结束日的最小日期
            endDates9();
            $(obj.elem[0]).attr("value", obj.val);
            $(obj.elem[0]).next().next().hide();
            $(obj.elem[0]).css("border", "1px solid #3c763d")
            $(obj.elem[0]).parent().prev().css("color", "#3c763d");
            //color: #3c763d
            //form-control-feedback glyphicon glyphicon-ok
            $(obj.elem[0]).next().attr("class", "form-control-feedback glyphicon glyphicon-ok").css("color", "#3c763d");
        },
        clearfun: function (elem, val) {
            $(elem.elem[0]).next().next().show();
            $(elem.elem[0]).css("border", "1px solid #a94442")
            $(elem.elem[0]).parent().prev().css("color", "#a94442");
            $(elem.elem[0]).next().attr("class", "form-control-feedback glyphicon glyphicon-remove").css("color", "#a94442");
        }
    };
    var time8 = {
        format: 'YYYY-MM-DD hh:mm:ss',
        minDate: $.nowDate({DD:0}), //设定最小日期为当前日期
        maxDate: '2099-12-30 00:00:00', //最大日期
        okfun: function(obj){
            time7.maxDate = obj.val; //将结束日的初始值设定为开始日的最大日期
            $(obj.elem[0]).attr("value", obj.val);
            $(obj.elem[0]).next().next().hide();
            $(obj.elem[0]).css("border", "1px solid #3c763d")
            $(obj.elem[0]).parent().prev().css("color", "#3c763d");
            //color: #3c763d
            //form-control-feedback glyphicon glyphicon-ok
            $(obj.elem[0]).next().attr("class", "form-control-feedback glyphicon glyphicon-ok").css("color", "#3c763d");
        },
        clearfun: function (elem, val) {
            $(elem.elem[0]).next().next().show();
            $(elem.elem[0]).css("border", "1px solid #a94442")
            $(elem.elem[0]).parent().prev().css("color", "#a94442");
            $(elem.elem[0]).next().attr("class", "form-control-feedback glyphicon glyphicon-remove").css("color", "#a94442");
        }
    };
//这里是日期联动的关键
    function endDates9() {
        //将结束日期的事件改成 false 即可
        time8.trigger = false;
        $("#time8").jeDate(time8);
    }
    $('#time7').jeDate(time7);
    $('#time8').jeDate(time8);


    var test06 = {
        format: 'hh:mm:ss',

        isinitVal:false,
        maxDate: $.nowDate({DD:0}), //最大日期
        okfun: function(obj){
            test06.minDate = obj.val; //开始日选好后，重置结束日的最小日期
            endDates8();
        }
    };
    var test05 = {
        format: 'hh:mm:ss',


        okfun: function(obj){
            test06.maxDate = obj.val; //将结束日的初始值设定为开始日的最大日期
        }
    };
//这里是日期联动的关键
    function endDates8() {
        //将结束日期的事件改成 false 即可
        test05.trigger = false;
        $("#test05").jeDate(test05);
    }
    $('#test06').jeDate(test06);
    $('#test05').jeDate(test05);



    var test031 = {
        format: 'YYYY-MM-DD',
        minDate: '2000-01-01', //设定最小日期为当前日期
        isinitVal:false,
        maxDate: $.nowDate({DD:0}), //最大日期
        okfun: function(obj){
            test041.minDate = obj.val; //开始日选好后，重置结束日的最小日期
            endDates11();
            $(obj.elem[0]).attr("value", obj.val);
            $(obj.elem[0]).next().hide();
            $(obj.elem[0]).css("border", "1px solid #3c763d")
            $(obj.elem[0]).parent().prev().css("color", "#3c763d");
            //color: #3c763d
            //form-control-feedback glyphicon glyphicon-ok
            $(obj.elem[0]).next().attr("class", "form-control-feedback glyphicon glyphicon-ok").css("color", "#3c763d");
        },
        clearfun: function (elem, val) {

            $(elem.elem[0]).css("border", "1px solid #a94442")
            $(elem.elem[0]).parent().prev().css("color", "#a94442");
            $(elem.elem[0]).next().attr("class", "form-control-feedback glyphicon glyphicon-remove").css("color", "#a94442");
        }
    };
    var test041 = {
        format: 'YYYY-MM-DD',
        minDate: $.nowDate({DD:0}), //设定最小日期为当前日期
        maxDate: '2099-12-30', //最大日期
        okfun: function(obj){
            test031.maxDate = obj.val; //将结束日的初始值设定为开始日的最大日期
            $(obj.elem[0]).attr("value", obj.val);
            $(obj.elem[0]).next().hide();
            $(obj.elem[0]).css("border", "1px solid #3c763d")
            $(obj.elem[0]).parent().prev().css("color", "#3c763d");
            //color: #3c763d
            //form-control-feedback glyphicon glyphicon-ok
            $(obj.elem[0]).next().attr("class", "form-control-feedback glyphicon glyphicon-ok").css("color", "#3c763d");
        },
        clearfun: function (elem, val) {

            $(elem.elem[0]).css("border", "1px solid #a94442")
            $(elem.elem[0]).parent().prev().css("color", "#a94442");
            $(elem.elem[0]).next().attr("class", "form-control-feedback glyphicon glyphicon-remove").css("color", "#a94442");
        }
    };
//这里是日期联动的关键
    function endDates11() {
        //将结束日期的事件改成 false 即可
        test041.trigger = false;
        $("#test041").jeDate(test041);
    }
    $('#test031').jeDate(test031);
    $('#test041').jeDate(test041);

    // $("#time1").jeDate({
    //     format: "YYYY-MM-DD hh:mm:ss"
    // });
    // $("#time2").jeDate({
    //     format: "YYYY-MM-DD hh:mm:ss"
    // });
    //区域范围选择
    // $("#test06").jeDate({
    //     format: "YYYY",
    //     range:" ~ "
    // });
    // $("#test07").jeDate({
    //     format: "YYYY-MM",
    //     range:" To "
    // });
    // $("#test08").jeDate({
    //     format: "YYYY-MM-DD",
    //     range:" 至 "
    // });
    //区域范围双面板选择
    $("#test09").jeDate({
        format: "YYYY-MM-DD hh:mm:ss",
        multiPane:false,
        range:" ~ "
    });
    // $("#test10").jeDate({
    //     format: "YYYY-MM",
    //     multiPane:false,
    //     range:" To "
    // });
    // $("#test11").jeDate({
    //     format: "YYYY-MM-DD",
    //     multiPane:false,
    //     range:" 至 "
    // });
    // //自定义格式选择
    // $("#test12").jeDate({
    //     format: "YYYY年MM月DD日"
    // });
    // $("#test13").jeDate({
    //     format: "MM-DD-YYYY"
    // });
    // $("#test14").jeDate({
    //     format: "DD/MM/YYYY"
    // });
    // //一次绑定多个选择
    // $(".moredate").each(function(){
    //     var mat = $(this).attr("placeholder");
    //     $(this).jeDate({
    //         format: mat
    //     });
    // });
    // //其它功能展示选择
    // $("#test15").jeDate({
    //     format: "YYYY-MM-DD",
    //     isinitVal: true
    // });
    // $("#test16").jeDate({
    //     format: "YYYY-MM-DD",
    //     okfun:function (elem,value) {
    //         alert('你选择的日期是：' + value)
    //     }
    // });
    // $("#test17").jeDate({
    //     format: "YYYY-MM-DD",
    //     toggle:function (elem,value,date) {
    //         alert('你选择的日期是：' + value + '\n\n获得的对象是' + JSON.stringify(date));
    //     }
    // });
    // $("#test18").jeDate({
    //     trigger:"dblclick",
    //     format: "YYYY-MM-DD"
    // });
    // var custom = $("#test19").jeDate({
    //     format: "YYYY-MM-DD"
    // });
    // custom.setValue("2017-09-01");
    // //有效、无效日期限制
    // $("#test20").jeDate({
    //     valiDate:["0[4-7]$,1[1-5]$,2[58]$",true],
    //     format: "YYYY年MM月DD日"
    // });
    // $("#test21").jeDate({
    //     valiDate:["0[4-7]$,1[1-5]$,2[58]$",false],
    //     format: "YYYY年MM月DD日"
    // });
    // $("#test22").jeDate({
    //     valiDate:["1$,3$,6$,9$",true],
    //     format: "YYYY年MM月DD日"
    // });
    // $("#test23").jeDate({
    //     valiDate:["1$,3$,6$,9$",false],
    //     format: "YYYY年MM月DD日"
    // });
    // $("#test24").jeDate({
    //     valiDate:["%1,%3,%6,%9,%12,%15,%25",true],
    //     format: "YYYY年MM月DD日"
    // });
    // $("#test25").jeDate({
    //     valiDate:["%1,%3,%6,%9,%12,%15,%25",false],
    //     format: "YYYY年MM月DD日"
    // });
    //直接展示日历
    // $("#show01").jeDate({
    //     isShow:false,
    //     format: "YYYY-MM-DD"
    // });
    // $("#show02").jeDate({
    //     isShow:false,
    //     format: "YYYY-MM"
    // });
    // $("#show03").jeDate({
    //     isShow:false,
    //     format: "YYYY"
    // });
    // $("#show04").jeDate({
    //     isShow:false,
    //     format: "hh:mm:ss"
    // });
});