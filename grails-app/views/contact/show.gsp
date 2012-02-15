
<%@ page import="karstenroethig.lineapp.Contact" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contact.label', default: 'Contact')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
			<span class="menuButton"><g:link class="list" controller="user" action="list"><g:message code="user.list.label" /></g:link></span>
			<span class="menuButton"><g:link class="list" controller="mailProperty" action="show"><g:message code="mailProperty.label" /></g:link></span>
			<span class="menuButton"><g:link class="list" controller="mailingList" action="list"><g:message code="mailingList.list.label" /></g:link></span>
			<span class="menuButton"><g:link class="list" controller="contact" action="list"><g:message code="contact.list.label" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
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
                            <td valign="top" class="name"><g:message code="contact.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contactInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contact.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contactInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contact.email.label" default="Email" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contactInstance, field: "email")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contact.mailingList.label" default="Mailing List" /></td>
                            
                            <td valign="top" class="value"><g:link controller="mailingList" action="show" id="${contactInstance?.mailingList?.id}">${contactInstance?.mailingList?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${contactInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
