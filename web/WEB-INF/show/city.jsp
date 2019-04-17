<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  Created by IntelliJ IDEA.
  User: lei02
  Date: 2019/4/17
  Time: 9:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>selectCity</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery-3.1.0.js"></script>
    <script type="text/javascript">
        $(function () {
            //1.获取 #province 添加 change 响应函数
            //2.使  #city 只保留第一个 option
            //3.获取 #province 选择的值，若该值为 "",即选择的是 "请选择..." 此时不需要发送 Ajax 请求
            //4.若值不是 ""，则表示 #province 改变，需要发送 Ajax 请求
            //4.1 url: getCity?choose=city   args:provinceName, time
            //5.返回的是一个 JSON 数组
            //5.1 若返回的数组中元素为 0 提示：当前省没有市区
            //5.2 若返回的数组中元素不为0，遍历，创建：<option value="cityName">cityName</option>
            //      并把新建的 option 节点添加到 #province 中
            $('#province').change(function () {
                $('#city option:not(:first)').remove();
                var province = $(this).val();
                if (province != "") {
                    var  url = "getCity?choose=city";
                    var args = {"provinceName" : province, "time" : new Date()};
                    $.getJSON(url, args, function (data) {
                        if (data.length == 0){
                            alert("当前省份没有市区");
                        } else {
                            for (var i = 0; i < data.length; i++) {
                                var city = data[i];
                                $('#city').append("<option value='" + city + "'>" + city + "</option>");
                            }
                        }
                    })
                }
            })

            $('#city').change(function () {
                $('#county option:not(:first)').remove();
                var county = $(this).val();
                if (county != "") {
                    var  url = "getCity?choose=county";
                    var args = {"countyName" : county, "time" : new Date()};
                    $.getJSON(url, args, function (data) {
                        if (data.length == 0){
                            alert("当前省份没有县");
                        } else {
                            for (var i = 0; i < data.length; i++) {
                                var county = data[i];
                                $('#county').append("<option value='" + county + "'>" + county + "</option>");
                            }
                        }
                    })
                }
            })
        })
    </script>
</head>
<body>
<img alt="" id="loading" src="images/loading.gif" style="display: none"/>
<br /><br />
Provinces:
<select id="province" name="province">
    <option value="">请选择...</option>
    <c:forEach items="${requestScope.provinces}" var="province">
        <option value="${province}">${province}</option>
    </c:forEach>
</select>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Cities:
<select id="city" name="city">
    <option value="">请选择...</option>
</select>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Counties:
<select id="county" name="county">
    <option value="">请选择...</option>
</select>
</body>
</html>
