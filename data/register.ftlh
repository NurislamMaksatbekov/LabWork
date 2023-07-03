<!DOCTYPE html>
<html>
<head>
    <title>Регистрация</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <style>
        body {
            background-image: url(images/wallpaperflare.com_wallpaper.jpg);
            background-size: cover;
        }
        .container {
            max-width: 350px;
            margin: 0 auto;
            margin-top: 16%;
            background-color: rgba(255, 255, 255, 0.3);
            padding: 20px;
            border-radius: 8px;
        }
        h1 {
            text-align: center;
            margin-bottom: 0px;
        }
        .form-group label {
            font-weight: bold;
        }
        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }
        .btn-primary:hover {
            background-color: #0069d9;
            border-color: #0062cc;
        }
    </style>
</head>
<body>

<div class="container">
    <h1 class="lol">Sign up</h1>
    <form action="/register" method="POST">
        <div class="form-group">
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <label for="email">Email:</label>
            <input type="email" class="form-control" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>
        <button type="submit" class="btn btn-info btn-block">Зарегистрироваться</button>
    </form>
</div>

</body>
</html>
