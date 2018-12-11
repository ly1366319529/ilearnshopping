# -------------------------------------电商平台笔记-------------------------------------



##### @RestController与 @controller的区别
 ##### @RestController往前端传递的是json格式的数据
 #####  @controller
 ##### @Param ：参数为一个的时候可以不用写@Param
 
 
 
##   -------------------------------------登录接口笔记-------------------------------------
```
    1.登录
    2.注册
    3.检查用户名是否有效
    4.获取登录用户信息
    5.忘记密码
    6.提交问题答案
    7.忘记密码的重设密码
    8.登录状态中的重置密码
    9.登录状态更新个人信息
    10.获取当前登录的用户信息
    11.退出登录
```



### 1.登录接口

   #### /user/login.do
   
 
   ######request
   ```
   String username.
   String passward
   ```
   ###### response
   ###### 成功返回status类型和data类型
   ###### 失败返回 status类型和mag类型
   ##### success
   ```
   {
   "status": 0,               -----int类型
   "data":{                   ----- 对象类型或数组类型也叫泛型
        "id":12,
        "username":"aaa"
        "email":"aaa@126.com"
        "phone":null
        "createTime":1222
        "updateTime":1222
   
      }
   }
   ```
   ##### fail
   ```
   {                          -----返回的是json对象类型
       "status":1,             -----int类型
       "msg":"密码错误"         -----
   }
   ```
   
   
   ### 2.注册接口
   ### 3.
   ###  4.获取登录信息
   #### /portal/user/get_user_info.do
   ###### request
   ```
   无参数
   ```
   ###### response
   ##### success
   ```
   {
      "status":0,
      "data":{
          "id":12,
          "username":"aaaa",
          "email":"aaa",
          "phone":null,
          "createTime":1222,
          "updateTime":1222  
      }
   }
   ```
   ##### file
   {
      "status":1,
      "msg":"用户未登录，无法获取当前用户信息"
   }
   ### 5.忘记密码
   #### /portal/user/forget_get_question.do?username=admin
   ###### request
   ```
   String username
   ```
   ###### response
   ##### success
   ```
   {
        "status":0,
        "data":"这里是问题"
   }
```
   ##### fail
   ```
   {
       "status":1,
       "msg":"用户未设置找回密码问题"
   }
```
### 6.提交问题答案
   #### /portal/user/forget_check_answer.do
   ###### request
   ```
   String username,
   String question,
   String answer
   ```
   ###### response 正确的返回值里有一个token，修改密码的时候需要用这个传递给下一个接口
   ##### success
   ```
      {
           "status":0,
           "data":"ddfdf-fdfd-fd455df"
      }
   ```
  ##### fail
   ```
      {
          "status":1,
          "msg":"问题答案错误"
      }
   ```
 ###   7.忘记密码的重设密码
  #### /portal/user/forget_reset_password.do
  ###### request
```
     String username,
     String question,
     String answer
 ```

  ###### response
  ##### success
  ##### fail
 ###   8.在登陆状态下重置密码
 #### /portal/user/reset_password.do
  ###### request  
  ```
  String passwordOld,
  Srring passwordNew
```
 ###### response
##### success
```
{
   "status":0,
   "msg":"修改密码成功" 
 }
```
##### fail
```
 {
    "status":1,
    "msg":"旧密码输入错误"
 }
```
  
  
 ### 为了防止用户横向越权   设置用户的唯一标识
 #### 1.构建一个缓存类 LoadingCache
  #### 2.初始化缓存类
  #### 3.设置最大缓存值
  #### 向缓存添加键值对根据key找到value
  #### 获取缓存值
  
  
  #### 退出登录
  logout.do
   将用户信息从session中移除 removeAttribute
   
   
   
   
   ```用户模块亮点：
   

```
   ```
   类别模块
   亮点：五个接口：获取
   查询子节点：递归 自己调用自己  要有终止的条件
   用户显示：id  set集合 判断对象是否重复 重写 hashcode和equals方法 无序
   如何判断不重复 重写 hashcode和equals方法保障对象的不可重复
      

类序列化

实现    接口
将一个类保存到文件系统当中      一定会将这个类序列化


分页的实现：
用limit实现
   pageName：
   pageSize：
   
   sql：select * from  product limit(偏移量)(查询条数)
   pagenum=1   pagesize=10
   select * from product limait (pagenum-1)*pagesize,pagesize
   
   
   
   商品：
   时间字段
   图片路径
   
   动态查询：<where></where>
```
   
   
   
   
   
   ```
   递归查询：
   递归必须有一个结束的条件，否则就是死循环了
   利用set集合将获取的category
   
   如果两个类相等用equals方法重写
   先要重写hashcode方法
   
   
    图片上出：springmvc 
    接收参数：
```