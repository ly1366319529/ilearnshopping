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
   pageNo：
   pageSize：
   
   sql：select * from  product limit(偏移量)(查询条数)
   pagenum=1   pagesize=10
   select * from product limait (pagenum-1)*pagesize,pagesize
    
   
   
   商品：
   时间字段
   图片路径
   
   动态查询：<where></where>
   图片上传：
   若返回值为json格式则加：@ResponseBody
   图片唯一标识UUID
   时间戳 +uuid
   将redis订单号提前生成号，放入数据池中，等用到的时候取出来
   
   
   产品的搜索及动态排序
   
```
   
   
   
   
   
   ```
   递归查询：
   递归必须有一个结束的条件，否则就是死循环了
   利用set集合将获取的category
   
   如果两个类相等用equals方法重写
   先要重写hashcode方法
   
   
    图片上传：springmvc 
    接收参数：
```


```
利用Bigdecimal实现价格的增删改查,具体思路：

```
```
免登录：cookie，session
```
```
@RestController,@Controller的区别，
RestController注解，相当于@Controller+@ResponseBody两个注解的结合，
返回json数据不需要在方法前面加@ResponseBody注解了，
但使用@RestController这个注解，就不能返回jsp,html页面，
视图解析器无法解析jsp,html页面
@RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用


使用@Controller 注解，在对应的方法上，视图解析器可以解析return 的jsp,html页面，
并且跳转到相应页面
```
```
如何调用封装高复用对象：
```

```
注册原理：
```
```
重置密码：如何校验校验密码是否相等
```
```
类别模块：用到的知识点：
```
```
产品搜索需要的知识点：
如何实现图片上传：
```
```
购物车模块需要的知识点：
```
```
订单模块需要的知识点：
```

```
springmvc的原理都体现在项目哪里
```
```
项目中哪些体现了依赖注入，哪些体现了面向切面编程，哪些体现了控制反转
```
```
项目中用到了哪些设计模式，分别体现在哪里？
单：bean

```
```
什么是动态代理，在项目的哪里体现

```
```
Bigdecimal中的加减乘除方法
```
```
paramterType ,resultType,resultMap,parameterMap, resultSetType起到了什么作用,分别与dao层的哪里对应，什么时候应该调用?
```
```
批处理：删除，添加
如何遍历；枚举
订单里面的组装vo
```

```

郭阳老师项目总结：
1，介绍横向越权
2, 介绍guava cache缓存使用
3，介绍如何封装响应前端的高可用、高复用对象
4，介绍MD5加密
5，介绍递归算法
6，介绍如何设计无限层级的的树状数据结构
7，介绍springmvc图片上传
8，介绍流读取Properties文件封装
9，用到了mybatis哪些插件
10，介绍POJO、BO、VO之间的转换及解决方案
11，介绍项目中如何处理时间
12，介绍Mybatis动态SQL、List遍历
13,介绍如何封装高可用分页模型
14，介绍购物车设计思想
15，介绍如何封装高复用的购物车核心方法
16，介绍如何解决浮点型在商业计算中丢失精度问题
17，介绍springmvc数据绑定
18，介绍mybatis自动生成主键
19，阿里云部署流程
20，介绍支付宝沙箱环境搭建及调试
21，介绍natapp外网穿透
22,介绍如何调通支付宝官方demo
23，介绍支付生成二维码
24，介绍支付宝支付流程及接口实现
25，介绍常量、枚举类封装
26，介绍订单号生成规则
27，介绍mybatis批量插入
28，介绍数据表设计。
29，如何配置图片服务器
30，介绍接口测试工具Restlet clinet
31，如何进行接口设计
```
```
15620263150
```
