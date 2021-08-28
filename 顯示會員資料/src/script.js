//新增物件
var memberlist={};
var apiurl="http://localhost:8080/members";
//定義自動生成的html （資料）
var list_html="<tr><th scope='col'>{{id}}</th><th scope='col'>{{name}}</th><th scope='col'>{{account}}</th><th scope='col'>{{password}}</th></tr>";
$.get({
  url:apiurl,
  dataType:"application/json;charset=UTF-8",
  function(res){
    memberlist.list=res;
    for(var i=0;i<memberlist.list.length;i++){
      var current_html=
      list_html.replace("{{id}}",memberlist.list[i].id)
               .replace("{{name}}",memberlist.list[i].name)
               .replace("{{account}}",memberlist.list[i].account)
               .replace("{{password}}",memberlist.list[i].password);
  
    $("#databody").append(current_html);
    }
  }
});