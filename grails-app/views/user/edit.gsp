

<%@ page import="karstenroethig.lineapp.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
			<g:if test="${session?.user?.admin}">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
			</g:if>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${userInstance}">
            <div class="errors">
                <g:renderErrors bean="${userInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${userInstance?.id}" />
                <g:hiddenField name="version" value="${userInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="username"><g:message code="user.username.label" default="Username" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'username', 'errors')}">
                                    <g:textField name="username" maxlength="30" value="${userInstance?.username}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fullname"><g:message code="user.fullname.label" default="Fullname" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'fullname', 'errors')}">
                                    <g:textField name="fullname" maxlength="100" value="${userInstance?.fullname}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="email"><g:message code="user.email.label" default="Email" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'email', 'errors')}">
                                    <g:textField name="email" value="${userInstance?.email}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="password"><g:message code="user.password.label" default="Password" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'passwordHash', 'errors')}">
                                    <g:passwordField name="password" maxlength="255" value="" />
                                </td>
                            </tr>
                        
							<g:if test="${session?.user?.admin}">
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="status"><g:message code="user.status.label" default="Status" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'status', 'errors')}">
                                    <g:select name="status" from="${karstenroethig.lineapp.UserStatus?.values()}" keys="${karstenroethig.lineapp.UserStatus?.values()*.name()}" value="${userInstance?.status?.name()}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="role"><g:message code="user.role.label" default="Role" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'role', 'errors')}">
                                    <g:select name="role" from="${userInstance.constraints.role.inList}" value="${userInstance?.role}" valueMessagePrefix="user.role"  />
                                </td>
                            </tr>
							</g:if>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
