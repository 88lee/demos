<!DOCTYPE html>
<html lang="">
<head>
    <meta charset="utf‐8">
    <title>Hello World!</title>
</head>
<body>
Hello ${name}!
<br>
遍历数据模型中的list学生信息(数据模型中的名称为stus)
<table border="1">
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>金额</td>
        <td>生日</td>
    </tr>
    <#if stus??><#--判断list存在-->
        <#list stus as stu>
            <tr>
            <td>${stu_index + 1}</td>
        <td <#if stu.name == '小明'>style="background: cornflowerblue" </#if>>${stu.name}</td>
            <td>${stu.age}</td>
        <#--使用括号或者gt解决大于号与尖括号混淆的问题-->
        <td <#if (stu.money > 300)>style="background: cornflowerblue" </#if>>${stu.money}</td>
        <#--<td <#if stu.money gt 300>style="background: cornflowerblue" </#if>>${stu.money}</td>-->
        <#--日期序号格式化-->
            <td>${stu.birthday?string("YYYY年MM月dd日")}</td>
            </tr>
        </#list>
        <br>
        学生数量：${stus?size}
    </#if>
</table><br>
取map数据模型值:<br>
第一种方法：[键值]<br>
<#--对象!默认值，解决判空-->
姓名：${(stuMap['stu1'].name)!''} 年龄：${(stuMap['stu1'].age)!''}<br>
第二种方法：(.属性名)<br>
姓名：${(stuMap.stu1.name)!''} 年龄：${(stuMap.stu1.age)!''}<br>
遍历map<br>
<#--map?keys取ketSets-->
<#list stuMap?keys as key>
姓名：${stuMap[key].name} 年龄：${stuMap[key].age}<br>
</#list>
<br>
<#--数字默认有分隔符-->
point:${point}
<br>
<#--?c表示转字符串-->
point:${point?c}
<br>
<#assign text="{'bank':'工商银行','account':'10101920201920212'}" />
<#--变量？eval：转json对象-->
<#assign data=text?eval />
开户行：${data.bank} 账号：${data.account}
<br>
<br>
</body>
</html>