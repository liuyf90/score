/**
 * Created by liuyf on 2018/7/22.
 */
//<![CDATA[
var n=0;
$("[name='ltree']").each(function(index,element){
    if(index==0){
        n=$(element).attr("data");
    }
    if(index==n-1){
        $(element).attr("class","active-menu");
    }
});


 //]]>

