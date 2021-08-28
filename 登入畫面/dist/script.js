function login(){
  var name = $("#name").val();          
  var account = $("#account").val();         
  var password = $("#password").val;
  
 $.ajax({
     url:"<%=path%>/User/Login",
     type:"POST",
     async: false,
     contentType:"application/json;charset=UTF-8",
     dataType:'json',
     data:JSON.stringify({"name":name,"account":account,"password":password}), //將JSON物件轉為字串
     success:function(data){

     }
 });
}

$("#enter").click(login());