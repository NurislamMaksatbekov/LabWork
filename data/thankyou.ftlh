<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Thank you!!</title>
    <link rel="stylesheet" href="css/candidates.css">
    <style>
        .button-container {
            margin-top: 10px;
        }
    </style>
</head>

<body>
<div class="container flex flex-col align-center">
    <h1> ${candidate.name} Thank you for your vote!</h1>
    <main class="flex flex-wrap align-center">
        <div class="card">
            <div class="flex flex-col align-center" href="#">
                <img src="${candidate.photo}" class="card-img-top" alt="${candidate.name}">
                <p>Thank you for your vote!</p>
                <p>Now I have ${candidate.percent}% of all votes</p>
            </div>
        </div>
        <div class="button-container">
            <p>-------------------------------------------------------</p>
            <a class="back flex align-center" href="/">Back to main</a>
            <p>-------------------------------------------------------</p>
            <br>
            <a class="back flex align-center" href="/votes">Show total votes</a>
            <p>-------------------------------------------------------</p>
        </div>
    </main>
</div>
</body>

</html>

