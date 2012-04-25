
<%@ page import="karstenroethig.lineapp.MailProperty" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'mailProperty.label', default: 'MailProperty')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
			<span class="menuButton"><g:link class="list" controller="user" action="list"><g:message code="user.list.label" /></g:link></span>
            <span class="menuButton"><g:link class="list" controller="mailProperty" action="show"><g:message code="mailProperty.label" /></g:link></span>
			<span class="menuButton"><g:link class="list" controller="mailingList" action="list"><g:message code="mailingList.list.label" /></g:link></span>
			<span class="menuButton"><g:link class="list" controller="contact" action="list"><g:message code="contact.list.label" /></g:link></span>
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
                            <td valign="top" class="name"><g:message code="mailProperty.hostName.label" default="Host Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: mailPropertyInstance, field: "hostName")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="mailProperty.port.label" default="Port" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: mailPropertyInstance, field: "port")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="mailProperty.tls.label" default="Tls" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${mailPropertyInstance?.tls}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="mailProperty.username.label" default="Username" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: mailPropertyInstance, field: "username")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="mailProperty.fromAddress.label" default="From Address" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: mailPropertyInstance, field: "fromAddress")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="mailProperty.fromName.label" default="From Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: mailPropertyInstance, field: "fromName")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="mailProperty.subject.label" default="Subject" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: mailPropertyInstance, field: "subject")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="mailProperty.htmlMsg.label" default="Html Msg" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: mailPropertyInstance, field: "htmlMsg")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="mailProperty.textMsg.label" default="Text Msg" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: mailPropertyInstance, field: "textMsg")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="mailProperty.htmlSignature.label" default="Html Signature" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: mailPropertyInstance, field: "htmlSignature")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="mailProperty.textSignature.label" default="Text Signature" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: mailPropertyInstance, field: "textSignature")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="mailProperty.backupAddresses.label" default="Backup Addresses" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: mailPropertyInstance, field: "backupAddresses")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="mailProperty.testAddresses.label" default="Test Addresses" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: mailPropertyInstance, field: "testAddresses")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${mailPropertyInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
