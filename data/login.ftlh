<head>
    <meta charset="UTF-8">
    <title>Login form</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="css/forms.css">
    <link href="https://fonts.googleapis.com/css2?family=Beer+Money&display=swap" rel="stylesheet">
    <style>
        .welcome-text {
            font-family: 'Beer Money', cursive;
            font-size: 32px;
            color: white;
            text-align: center;
            margin-top: 150px;
        }
    </style>
</head>
<body>
<main>
    <form action="/login" method="post">
        <fieldset>
            <div class="legend">
                <p class="welcome-text">Welcome to your library</p>
            </div>
            <style>
                body {
                    background-image: url('https://www.google.com/imgres?imgurl=https%3A%2F%2Fmf.b37mrtl.ru%2Frussian%2Fimages%2F2017.09%2Farticle%2F59b14cd0183561754b8b458e.jpg&tbnid=wHNvnhzhbcvfxM&vet=12ahUKEwjc0qyPn-v_AhWI6CoKHQ-pDqgQMygKegUIARCXAQ..i&imgrefurl=https%3A%2F%2Frussian.rt.com%2Fscience%2Farticle%2F427416-dyadya-sem-ssha&docid=HHBhLYNQ7XISfM&w=827&h=465&q=ты%20нужен%20армии%20сша&client=safari&ved=2ahUKEwjc0qyPn-v_AhWI6CoKHQ-pDqgQMygKegUIARCXAQ');
                    background-size: cover;
                    background-position: center;
                }
            </style>

            </div>
            <div class="form-element">
                <label for="user-email">email</label>
                <input type="email" name="email" id="user-email" placeholder="your email" required autofocus>
            </div>
            <div class="form-element">
                <label for="user-password">password</label>
                <input type="password" name="password" id="user-password" placeholder="your password" required>
            </div>

            <div class="form-element">
                <button class="register-button" type="submit">Login!</button>
            </div>
            <div class="form-element" style="text-align: center;">
                <a href="/register" class="register-button" type="submit">Register!</a>
            </div>

        </fieldset>
    </form>
</main>
</body>

</html>