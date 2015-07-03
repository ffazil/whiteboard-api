<html>
<head>
    <title>iDem</title>
    <link rel="stylesheet" href="css/wro.css"/>
</head>
<body>

<header>
    <div style="width: 100%;float:left;height: 35px;background-color: #ddd;padding: 0 15px;">
        <div style="float: left;height: 35px;line-height: 35px;"><strong>Login</strong> </div>
    </div>
</header>
<aside style="text-align: right"><img src="/whiteboard-api/images/logo-default.png"/></aside>
<section>
    <div  class="container" style="width:500px;height:400px; margin:5% auto; ">
    <#if RequestParameters['error']??>
        <div class="alert alert-danger">
            There was a problem logging in. Please try again.
        </div>
    </#if>
        <form role="form" action="login" method="post" style="align-self: center;border: black solid;padding: 2%;height: 350px;">

            <h4 style="text-align: center;height: 40px;">Sign in to continue to ###</h4>

            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" class="form-control" id="username" name="username"/>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" class="form-control" id="password" name="password"/>
            </div>
            <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" style="width: 100%" class="btn btn-primary">Sign In</button>
        </form>
        <p style="text-align: center;padding-top: 4%;"> No Account ? create one <a href="/idem/register">here</a></p>
        <script src="js/wro.js" type="text/javascript"></script>
    </div>

</section>
</body>
</html>