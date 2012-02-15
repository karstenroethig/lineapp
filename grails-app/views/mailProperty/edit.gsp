

<%@ page import="karstenroethig.lineapp.MailProperty" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'mailProperty.label', default: 'MailProperty')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
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
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${mailPropertyInstance}">
            <div class="errors">
                <g:renderErrors bean="${mailPropertyInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${mailPropertyInstance?.id}" />
                <g:hiddenField name="version" value="${mailPropertyInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hostName"><g:message code="mailProperty.hostName.label" default="Host Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mailPropertyInstance, field: 'hostName', 'errors')}">
                                    <g:textField name="hostName" value="${mailPropertyInstance?.hostName}" />
									<br/>
									<g:message code="mailProperty.hostName.description" default="n. A." />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="port"><g:message code="mailProperty.port.label" default="Port" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mailPropertyInstance, field: 'port', 'errors')}">
                                    <g:textField name="port" value="${fieldValue(bean: mailPropertyInstance, field: 'port')}" />
									<br/>
									<g:message code="mailProperty.port.description" default="n. A." />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tls"><g:message code="mailProperty.tls.label" default="Tls" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mailPropertyInstance, field: 'tls', 'errors')}">
                                    <g:checkBox name="tls" value="${mailPropertyInstance?.tls}" />
									<br/>
									<g:message code="mailProperty.tls.description" default="n. A." />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="username"><g:message code="mailProperty.username.label" default="Username" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mailPropertyInstance, field: 'username', 'errors')}">
                                    <g:textField name="username" value="${mailPropertyInstance?.username}" />
									<br/>
									<g:message code="mailProperty.username.description" default="n. A." />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="password"><g:message code="mailProperty.password.label" default="Password" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mailPropertyInstance, field: 'password', 'errors')}">
                                    <g:textField name="password" value="${mailPropertyInstance?.password}" />
									<br/>
									<g:message code="mailProperty.password.description" default="n. A." />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fromAddress"><g:message code="mailProperty.fromAddress.label" default="From Address" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mailPropertyInstance, field: 'fromAddress', 'errors')}">
                                    <g:textField name="fromAddress" value="${mailPropertyInstance?.fromAddress}" />
									<br/>
									<g:message code="mailProperty.fromAddress.description" default="n. A." />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fromName"><g:message code="mailProperty.fromName.label" default="From Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mailPropertyInstance, field: 'fromName', 'errors')}">
                                    <g:textField name="fromName" value="${mailPropertyInstance?.fromName}" />
									<br/>
									<g:message code="mailProperty.fromName.description" default="n. A." />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="subject"><g:message code="mailProperty.subject.label" default="Subject" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mailPropertyInstance, field: 'subject', 'errors')}">
                                    <g:textField name="subject" value="${mailPropertyInstance?.subject}" />
									<br/>
									<g:message code="mailProperty.subject.description" default="n. A." />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="htmlMsg"><g:message code="mailProperty.htmlMsg.label" default="Html Msg" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mailPropertyInstance, field: 'htmlMsg', 'errors')}">
                                    <g:textArea name="htmlMsg" cols="40" rows="5" value="${mailPropertyInstance?.htmlMsg}" />
									<br/>
									<g:message code="mailProperty.htmlMsg.description" default="n. A." />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="textMsg"><g:message code="mailProperty.textMsg.label" default="Text Msg" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mailPropertyInstance, field: 'textMsg', 'errors')}">
                                    <g:textArea name="textMsg" cols="40" rows="5" value="${mailPropertyInstance?.textMsg}" />
									<br/>
									<g:message code="mailProperty.textMsg.description" default="n. A." />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="htmlSignature"><g:message code="mailProperty.htmlSignature.label" default="Html Signature" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mailPropertyInstance, field: 'htmlSignature', 'errors')}">
                                    <g:textArea name="htmlSignature" cols="40" rows="5" value="${mailPropertyInstance?.htmlSignature}" />
									<br/>
									<g:message code="mailProperty.htmlSignature.description" default="n. A." />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="textSignature"><g:message code="mailProperty.textSignature.label" default="Text Signature" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mailPropertyInstance, field: 'textSignature', 'errors')}">
                                    <g:textArea name="textSignature" cols="40" rows="5" value="${mailPropertyInstance?.textSignature}" />
									<br/>
									<g:message code="mailProperty.textSignature.description" default="n. A." />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="backupAddresses"><g:message code="mailProperty.backupAddresses.label" default="Backup Addresses" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mailPropertyInstance, field: 'backupAddresses', 'errors')}">
                                    <g:textField name="backupAddresses" value="${mailPropertyInstance?.backupAddresses}" />
									<br/>
									<g:message code="mailProperty.backupAddresses.description" default="n. A." />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="testAddresses"><g:message code="mailProperty.testAddresses.label" default="Test Addresses" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: mailPropertyInstance, field: 'testAddresses', 'errors')}">
                                    <g:textField name="testAddresses" value="${mailPropertyInstance?.testAddresses}" />
									<br/>
									<g:message code="mailProperty.testAddresses.description" default="n. A." />
                                </td>
                            </tr>
                        
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
