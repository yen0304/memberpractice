//新增物件
var memberlist={};
var apiurl="/members";

//定義自動生成的html （資料）
var list_html="<tr><th class='id 'scope='col'>{{id}}</th><th class='name 'scope='col'>{{name}}</th><th class='account' scope='col'>{{account}}</th><th class='password 'scope='col'>{{password}}</th><th scope='col'> <button class='btn btn-primary edit' type='button'>編輯</button><button class='btn btn-danger remove' type='button'>刪除</button></th></tr>";
$.ajax({
  url: apiurl,
  dataType:"json",
  success:function(res){
    memberlist.list=(res);
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

$(document).on("click",'.remove',function(){
  console.log($(this).parent().parent().children('.account').text());
  $.ajax({
    type: 'DELETE',
    url:apiurl + '/' + $(this).parent().parent().children('.account').text(),
    success: function(){
    $(this).parent().parent().remove();
    window.location.reload();
    }
  });
});