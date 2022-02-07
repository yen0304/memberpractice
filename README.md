# 本文章涉及的知識

1. Springboot  、 Spring MVC
2. HTTP method
3. RESTful API
4. JDBC
5. mySQL語法
6. Html、css（小部分）
7. Javascript 、Jquery（小部分）

## 複習HTPP method-GET or POST 差別

GET 或 POST 的差別最大的差別在於傳遞的參數會被看見，因為參數會放在url裡面。

POST的話參數會放在request body裡面，request body裡面通常會用Json格式來進行前後端的溝通

在Spring Boot裡面，使用@RequestBody取得放在request body的參數



## 練習設計RESTful API

#### RESTful API三大原則：

1.使用http method表示動作

| HTTP Method |  對應資料動作  |
| :---------: | :------------: |
|    POST     | Create（新增） |
|     GET     |  Read（查詢）  |
|     PUT     | Update（修改） |
|   DELETE    | Delete（刪除） |

2.url路徑表示資源階層關係

3.response body返回json或是xml格式



## 設計本範例RESTful API

#### 設計會員資料之class

```java
public class Member {
    Integer id;
    String name;
    String account;
    String password;
  
  	//getter & setter
}
```



| Method | Url             | 資料庫         | 意義                       |
| ------ | --------------- | -------------- | -------------------------- |
| POST   | /members        | Create（新增） | 創建會員                   |
| GET    | /members/abc123 | Read（查詢）   | 查詢會員帳號為abc123的資料 |
| PUT    | /members/abc123 | Update（修改） | 修改會員帳號為abc123的資料 |
| DELETE | /members/abc123 | Delete（刪除） | 刪除會員帳號為abc123的資料 |



## 驗證請求參數 @NotBlank

為了讓使用者的登入數值不能為空白，避免後端程式發生錯誤，可以用此方法提前一部將錯誤回報給前端。

1.maven設定（2.3.X)版本之後才需要設定

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

2.用途

在數值上加入＠NotBlank代表參數不能為空、且不能為空白字串，@NotBlank只能放在String類型參數上面，若需要用在Integer上面，可使用@NotEmpty或是@NotNull。

```java
public class Member {

    Integer id;

    @NotBlank
    String name;
    @NotBlank
    String account;
    @NotBlank
  	String password;
    //getter&setter
}
```





## 資料庫連線設定 使用JDBC

 1.maven設定

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.22</version>
</dependency>
```

2.設定SpringBoot連線資訊（固定寫法），到application.properties

```yaml
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/mytest?serverTimezone=Asia/Taipei&characterEncoding=utf-8
spring.datasource.username=root
spring.datasource.password=********
```

3.複習一下在JDBC中的語法：

根據sql語法可分成兩類，分別為update()與query()，其中update可執行INSERT、UPDATE、SELETE語法，query則是執行SELECT語法



## 資料庫設定

```sql
CREATE TABLE member (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    account VARCHAR(30),
    password VARCHAR(30)
)
```



## 進入spring MVC環節

### 物件member

```java
public class Member {

    Integer id;
    String name;
    String account;
    String password;
    
    //getter&setter
}
```

### 設計DAO-負責與資料庫溝通

介面：

```java
public interface MemberDao {
    //返回值 依據名稱對應資料庫動作（參數類型 參數名稱）
    String CreatMember(Member member);
}
```

實作：

```java
@Component //將class交給Bean
public class MemberDaoImpl implements MemberDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public String CreatMember(Member member) {
        String sql ="INSERT INTO member(name,account,password)VALUE(:memberName,:memberAccount,:memberPassword)";// 在SQL前面加上：表示變數
        Map<String, Object> map =new HashMap<>();

        map.put("memberId",member.getId());     //put(SQL變數,值）
        map.put("memberName",member.getName());
        map.put("memberAccount",member.getAccount());
        map.put("memberPassword",member.getPassword());

        namedParameterJdbcTemplate.update(sql,map);
        return "註冊成功"; //return放將來要使用的html
    }
```



### 設計Service-負責商業邏輯

介面：

```java
public interface MemberService {

    String CreatMember(Member member);
}
```



實作：

```java
@Component
public class MemberServiceImpl implements MemberService{


    @Autowired//使用InterFace 發揮spring Ioc特性
    private MemberDao memberDao;

    @Override
    public String CreatMember(Member member) {
        return memberDao.CreatMember(member);
    }
}
```



### 設計Controller

```java
@RestController
public class MemberController {


    @Autowired //注入bean 使用jdbc執行 MySQL資料庫操作
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private MemberService memberService;


    @PostMapping("/members") //設置url路徑對應到此方法上，並限制只能使用Post方法,
    public String create(@RequestBody Member member){ //使用@RequestBody取得前端requestBody資訊

        return memberService.CreatMember(member);
    }
}
```



其實在這邊遇到了不少難題，

本來預計的流程是，想說使用@Postmapping後頁面自動跳轉到結果頁面（return值為ftl)，遇到了PostMapping回傳值為html頁面無法跳轉問題，發現＠ResponseBody＠RequestBody＠RestController要搞清楚之外，經由老師古古說指導後發現此方法無法將前後端完全分離，應該由後端傳回Json格式的檔案之後，在使用前端去顯示結果。



＠Controller+＠ResponseBody=＠RestController：用於回傳Json檔案給前端使用

＠RequestBody：用於接收前端的Json格式

在途中嘗試了不少次，回傳數值必須不能為純String或是html，要用加上註解的＠RestController讓回傳值變成Json，這樣前端才能順利收到數值，並狀態碼為200，（之後再研究看看是為什麼）。



## HTML & Javascript 

這邊用到了一點點的Jquery取得變數，以及$.post請求，不能用$.ajax，因為ajax不能挑轉頁面



#### HTML

```html
<body>
<!-- partial:index.partial.html -->
<div class="main-form first">
  <div class="main-form__title">
    <h1>Sign-up</h1>
  </div>
  <div class="main-form__body">
    <input id="name" class="main-form__input" type="text" placeholder="Username" />
    <input id="account" class="main-form__input" type="email" placeholder="Email Address" />
    <input id="password" class="main-form__input" type="password" placeholder="Password"/>
    <input class="main-form__input" type="password" placeholder="Repeat Password" />
    <button class="btn">Clear</button>
    <button id="enter" class="btn">Register</button>
  </div>
</div>
<!-- partial -->
<script src='https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.0.0/js/bootstrap.min.js'></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js'></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.6.11/vue.min.js"></script>
<script  src="/js/script.js"></script>

</body>
```



#### Javascript

```javascript
var apiurl={
    registerdata:"/members",
    successregister:"/display.html"
}
$("#enter").click(function (){
    var name = $("#name").val();
    var account = $("#account").val();
    var password = $("#password").val();

    $.post({
        url:apiurl.registerdata,
        contentType:"application/json;charset=UTF-8",
        data:JSON.stringify({"name":name,"account":account,"password":password}),
        success:function(res) {
            window.location.href = apiurl.successregister;//正確登入後頁面跳轉
        }
    });
});
```



## 檔案路徑配置

將首頁放在templates裡面，並取名為index，其餘分頁靜態的放static底下就好

##部落格文章連結：
https://yen0304.github.io/p/spring-boot-%E7%AE%A1%E7%90%86%E6%9C%83%E5%93%A1%E8%B3%87%E6%96%99%E4%B8%80-%E6%9C%83%E5%93%A1%E5%89%B5%E5%BB%BA-crud-create/
