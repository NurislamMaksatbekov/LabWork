<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Who's winning?</title>
    <link rel="stylesheet" href="../css/candidates.css">
</head>

<body>
<div class="container flex flex-col align-center">
    <h1>Who's winning?</h1>
    <main class="flex flex-wrap align-center">
        <#assign str = 0.0>
        <#list candidates as c>
            <#if c.percent gt str>
                <#assign str = c.percent>
            </#if>
        </#list>
        <#list candidates as c>
            <#if c.percent == str>
                <div class="card winner">
                    <div class="flex flex-col align-center" href="#">
                        <img src="${c.photo}" alt="${c.name}">
                        <p>Votes: ${c.percent}%</p>
                    </div>
                </div>
            <#else>
                <div class="card">
                    <div class="flex flex-col align-center" href="#">
                        <img src="${c.photo}" alt="${c.name}">
                        <p>Votes: ${c.percent}%</p>
                    </div>
                </div>
            </#if>
        </#list>
        <a class="back flex align-center" href="/">back to main</a>
    </main>
</div>
</body>
</html>
