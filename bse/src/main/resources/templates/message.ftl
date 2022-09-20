<#import "/spring.ftl" as spring />

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Best Shop Ever</title>
</head>
<body>
<div class="header">
    <div>
        <a href="/bse"><img height="100" src="/img/bse_logo.svg"/></a>
    </div>
    <div class="header-client"></div>
    <div>
        <a href="/orders">Все заказы</a>
    </div>
</div>
<h1>Ошибка</h1>
<div>
    ${message}
</div>
<div>
    <a href="/bse">На главную</a>
</div>
</body>
</html>