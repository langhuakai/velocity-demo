##模板代码生成demo
###模板基本语法
Velocity 中所有的关键字都是以 # 开头的，而所有的变量则是以$开头。Velocity 的语法类似于 JSP 中的 JSTL，甚至可以定义类似于函数的宏。
####变量
#####变量的定义
\#set关键字定义赋值变量
~~~~
##1.变量的定义
#set($names ="velocity")
#set($hello ="hello $names")
~~~~
#####变量的赋值
\#set关键字定义赋值变量<br/>
\##-变量是弱数据类型的，可以在赋了一个 String 给变量之后再赋一个数字或者数组给它。<br/>
\##-六种数据类型赋给Velocity变量：变量引用, 字面字符串, 属性引用, 方法引用, 字面数字, 数组列表。 boolean 类型也可以。<br/>
~~~~
##-变量是弱数据类型的，可以在赋了一个 String 给变量之后再赋一个数字或者数组给它。
##-六种数据类型赋给Velocity变量：变量引用, 字面字符串, 属性引用, 方法引用, 字面数字, 数组列表。
#set($test = 1)
#set($test ="cnzz")
#set($array = ["array",1,$test])
#set($obj={"name":"Cnzz","age":18})
#set($obj.name=$test)
~~~~
#####变量的使用
\##-使用$name 或者 ${name} 来使用定义的变量<br/>
\##-${person.name} 和 ${person.getName()}访问person的name属性<br/>
\## $!name与 ${!name}表示值为空时强制空白<br/>
~~~~
##-使用$name 或者 ${name} 来使用定义的变量
##-${person.name} 和 ${person.getName()}访问person的name属性
## $!name与 ${!name}表示值为空时强制空白
$test
${array}
${obj}
$obj.name
~~~~
####判断if
\#if(condition)<br/>
...<br/>
\#elseif(condition)<br/>
...<br/>
\#else<br/>
...<br/>
\#end<br/>
~~~~
#set($flag = "a")

#if($flag==1)
flag=1
#elseif($flag=="a")
flag=a
#else
flag=null
#end
~~~~
####循环foreach
\## velocityCount是Velocity提供的用来记录当前循环次数的计数器，默认从1开始计数，可以在velocity.properties文件中修改其初始值
~~~~
## velocityCount是Velocity提供的用来记录当前循环次数的计数器，默认从1开始计数，可以在velocity.properties文件中修改其初始值
#foreach($item in $list)
This is $item
    $velocityCount
#end
~~~~
####逻辑运算符 与或非
\##Velocity 引擎提供了 与、或 和 非 操作符，分别对应 &&、|| 和 !
~~~~
##Velocity 引擎提供了 与、或 和 非 操作符，分别对应 &&、|| 和 !
#set($var=true)
#set($var1=false)
## 与逻辑
#if($var && $var1)
    $var and $var1 = true
#else
    $var and $var1 = false
#end
## 或逻辑
#if($var || $var1)
    $var or $var1 = true
#else
    $var or $var1 = false
#end
## 非逻辑
#if(!$var)
    $var not  = true
#else
    $var not  = false
#end
~~~~
####宏 函数
Velocity 中的宏可以理解为函数定义。关键字 #macro

定义语法

\#macro(macroName arg1 arg2...)<br/>
...<br/>
\#end<br/>
使用语法<br/>
\#macroName(arg1 arg2...)<br/>
~~~~
## 关键字 #macro
##定义宏
#macro(sayHello $username)
hello $username
#end
##使用宏
#sayHello("cnzz")
~~~~
####文件引用
\#parse 和 #include 用来引用外部文件<br/>

\## #parse 会将引用的内容当成类似于源码文件，会将内容在引入的地方进行解析，<br/>
\## #include 是将引入文件当成资源文件，会将引入内容原封不动地以文本输出。<br/>
~~~~
## #parse 会将引用的内容当成类似于源码文件，会将内容在引入的地方进行解析，
## #include 是将引入文件当成资源文件，会将引入内容原封不动地以文本输出。
 
## foo.vm 内容  #set($name ="velocity")
 #parse("foo.vm")  ##输出 velocity
 
 #include("foo.vm")  ##输出 #set($name =”velocity”)
~~~~