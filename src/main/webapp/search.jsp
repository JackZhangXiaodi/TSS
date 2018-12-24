<%@page isELIgnored="false" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="http://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="style.css">
    <title>Document</title>
    <style>
        {
            box-sizing: border-box;
        }
        body {
            margin: 0;
            padding: 0;
            background: #F9F0DA;
            font-weight: 500;
            font-family: "Microsoft YaHei","宋体","Segoe UI", "Lucida Grande", Helvetica, Arial,sans-serif, FreeSans, Arimo;
        }
        #container {
            width: 500px;
            height: 820px;
            margin: 0 auto;
        }
        div.search {padding: 30px 0;}

        form {
            position: relative;
            width: 300px;
            margin: 0 auto;
        }

        input, button {
            border: none;
            outline: none;
        }

        input {
            width: 100%;
            height: 42px;
            padding-left: 13px;
        }

        button {
            height: 49px;
            width: 50px;
            cursor: pointer;
            position: absolute;
        }

        .bar1 input {
            border: 3px solid #7BA7AB;
            border-radius: 5px;
            background: pink;
            color: #9E9C9C;
        }
        .bar1 button {
            top: 0;
            right: 0;
            background: #7BA7AB;
            border-radius: 0 5px 5px 0;
        }
        .bar1 button:before {
            content: "\f002";
            font-family: 宋体;
            font-size: 10px;
            color:  #F9F0DA;
        }
    </style>
    <h1 style="margin-top: 200px; text-align:center;color: orangered">唐诗搜索.....</h1>
</head>
<body>
<div id="container" >
        <div class="search bar1" >
            <form method="post" action="${pageContext.request.contextPath}/poetry/showAll">
                <input type="text" placeholder="请输入您要搜索的内容..." name="art">
                <button type="submit" >检索</button>
            </form>
        </div>
</div>
</body>
</html>
