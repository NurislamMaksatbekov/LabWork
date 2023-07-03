<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Vote for us!</title>
    <link rel="stylesheet" href="css/candidates.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.bundle.min.js"></script>
</head>

<body>
<div class="container flex flex-col align-center">
    <h1>Vote for us</h1>
    <div class="row">
        <#list candidates as candidate>
            <div class="col-md-4 g-4">
                <div class="card">
                    <img src="${candidate.photo}" class="card-img-top" alt="${candidate.name}">
                    <b class="card-text text-center mt-3 mb-3">${candidate.name} has ${candidate.percent}% of all votes</b>
                </div>
            </div>
        </#list>
    </div>
    <a class="back flex align-center mt-3" href="/">Back to main</a>
</div>
</body>

</html>