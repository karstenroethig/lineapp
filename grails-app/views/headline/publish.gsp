

<%@ page import="karstenroethig.lineapp.Headline" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'headline.label', default: 'Headline')}" />
        <title><g:message code="headline.publish.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
			<span class="menuButton"><g:link class="list" action="list"><g:message code="headline.list.label" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
			<g:if test="${session?.user?.admin}">
				<span class="menuButton"><g:link class="edit" controller="user" action="list"><g:message code="admin.label" /></g:link></span>
			</g:if>
        </div>
        <div class="body">
            <h1><g:message code="headline.publish.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${headlineInstance}">
            <div class="errors">
                <g:renderErrors bean="${headlineInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${headlineInstance?.id}" />
                <g:hiddenField name="version" value="${headlineInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="mailingLists"><g:message code="headline.publish.mailingList.label" default="Mailing Lists" /></label>
                                </td>
                                <td valign="top" class="value">
                                    <g:select name="mailingLists"
										from="${karstenroethig.lineapp.MailingList.list()}"
										keys="${karstenroethig.lineapp.MailingList.list()*.name}"
										multiple="true" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="send" value="${message(code: 'headline.publish.button', default: 'Publish')}" /></span>
					<span class="button"><g:actionSubmit class="save" action="testMail" value="${message(code: 'headline.publish.testMail.button', default: 'Send Test-Mail')}" /></span>
					<span class="button"><g:link class="edit" controller="headline" action="show" id="${headlineInstance?.id}"><g:message code="default.cancel.button" default="Cancel" /></g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
