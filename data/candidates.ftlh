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
<div class="d-flex justify-content-end align-items-center mt-4 mr-4">
    <a  href="/login" class="btn btn-success mr-2" type="submit">LogIn</a>
    <a href="/register" class="btn btn-success" type="submit">Register</a>
</div>
<div class="container flex flex-col align-center">
    <h1>Vote for us</h1>
    <div class="row">
        <#list candidates as candidate>
            <div class="col-md-4 g-4">
                <div class="card">
                    <img src="${candidate.photo}" class="card-img-top" alt="${candidate.name}">
                    <b class="card-text text-center mt-3 mb-3">${candidate.name}</b>
                    <form action="/vote" method="post" class="flex flex-col align-center" href="/thankyou">
                        <input name="name" type="hidden" value="${candidate.name}">
                        <button type="submit" class="vote flex align-center">Vote</button>
                    </form>

                </div>
            </div>
        </#list>
    </div>
    <p>-------------------------------------------------------</p>
    <a class="back flex align-center" href="/votes">Show total votes</a>
</div>
</body>

</html>