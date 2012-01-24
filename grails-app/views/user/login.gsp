<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Anmelden</title>
    </head>
    <body>
        <div class="body">
            <h1>Anmelden</h1>
            <g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
            </g:if>
			
            <g:form action="authenticate" method="post">
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="login">Benutzer:</label>
                                </td>
                                <td valign="top">
									<input type="text" id="login" name="login"/>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="password">Kennwort:</label>
                                </td>
                                <td valign="top">
									<input type="password" id="password" name="password"/>
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button">
						<input type="submit" value="Anmelden"/>
					</span>
                </div>
            </g:form>
        </div>
    </body>
</html>
