
<%@ page import="karstenroethig.lineapp.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
			<g:if test="${session?.user?.admin}">
				<span class="menuButton"><g:link class="list" controller="user" action="list"><g:message code="user.list.label" /></g:link></span>
				<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
				<span class="menuButton"><g:link class="list" controller="mailProperty" action="show"><g:message code="mailProperty.label" /></g:link></span>
				<span class="menuButton"><g:link class="list" controller="mailingList" action="list"><g:message code="mailingList.list.label" /></g:link></span>
				<span class="menuButton"><g:link class="list" controller="contact" action="list"><g:message code="contact.list.label" /></g:link></span>
			</g:if>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.username.label" default="Username" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "username")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.fullname.label" default="Fullname" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "fullname")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.email.label" default="Email" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "email")}</td>
                            
                        </tr>
                    
						<g:if test="${session?.user?.admin}">
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.status.label" default="Status" /></td>
                            
                            <td valign="top" class="value">${userInstance?.status?.encodeAsHTML()}</td>
                            
                        </tr>
						</g:if>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.role.label" default="Role" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "role")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${userInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
