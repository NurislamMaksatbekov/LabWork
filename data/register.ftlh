<!DOCTYPE html>
<html>
<head>
    <title>Регистрация</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <style>
        body {
            background-image: url("https://www.google.com/imgres?imgurl=https%3A%2F%2Fmf.b37mrtl.ru%2Frussian%2Fimages%2F2017.09%2Farticle%2F59b14cd0183561754b8b458e.jpg&tbnid=wHNvnhzhbcvfxM&vet=12ahUKEwjc0qyPn-v_AhWI6CoKHQ-pDqgQMygKegUIARCXAQ..i&imgrefurl=https%3A%2F%2Frussian.rt.com%2Fscience%2Farticle%2F427416-dyadya-sem-ssha&docid=HHBhLYNQ7XISfM&w=827&h=465&q=ты%20нужен%20армии%20сша&client=safari&ved=2ahUKEwjc0qyPn-v_AhWI6CoKHQ-pDqgQMygKegUIARCXAQ");
            background-size: cover;
            background-position: center;
        }
        .container {
            max-width: 400px;
            margin: 0 auto;
            margin-top: 100px;
            background-color: rgba(255, 255, 255, 0.9);
            padding: 20px;
            border-radius: 5px;
        }
        h1 {
            text-align: center;
            margin-bottom: 30px;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Регистрация</h1>
    <form action="/register" method="POST">
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" class="form-control" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="name">First Name:</label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>
        <div class="form-group">
            <label for="surname">Surname:</label>
            <input type="text" class="form-control" id="surname" name="surname" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>
        <button type="submit" class="btn btn-primary btn-block">Зарегистрироваться</button>
    </form>
</div>

</body>
</html>