<html>
<head>
    <link rel="stylesheet" href="../css/wro.css"/>
</head>
<body>

<header>
    <div style="width: 100%;float:left;height: 35px;background-color: #ddd;padding: 0 15px;">
        <div style="float: left;height: 35px;line-height: 35px;"><strong>Authorize</strong> </div>
    </div>
</header>
<aside style="text-align: right"><img src="/whiteboard-api/images/logo-default.png"/></aside>
<section>
    <div style="width:70%;margin:2% auto;" class="table-responsive">
        <h2>Please Confirm</h2>
        <p>
            Do you authorize "${authorizationRequest.clientId}" at "${authorizationRequest.redirectUri}" to access your protected resources
            with scope .
        </p>
        <div>
            <form id="confirmationForm" name="confirmationForm" action="../oauth/authorize" method="post" style="width: 90%;float: left;text-align: right;padding-right: 10px;">
                <input name="user_oauth_approval" value="true" type="hidden" />
                <table class="table table-striped"  width="100%">
                    <tr>
                        <th width="5%">Approve</th>
                        <th  width="5%">Deny</th>
                        <th  width="90%">Scope</th>
                    </tr>
                    <#list authorizationRequest.scope as authScope>
                    <tr>
                        <#assign scopekey = "scope."+authScope>
                        <td><input type="radio" name="${scopekey}" value="true" checked></td>
                        <td><input type="radio" name="${scopekey}" value="false"></td>
                        <td>${authScope}</td>
                    </tr>
                    </#list>
                </table>
                <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-primary" type="submit">Submit</button>
            </form>
        </div>
    </div>
</section>
<script src="../js/wro.js"	type="text/javascript"></script>
</body>
</html>