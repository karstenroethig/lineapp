

<%@ page import="karstenroethig.lineapp.Attachment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'attachment.label', default: 'Attachment')}" />
        <g:set var="entityNameHeadline" value="${message(code: 'headline.label', default: 'Headline')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
			<span class="menuButton"><g:link class="list" controller="headline" action="list"><g:message code="headline.list.label" /></g:link></span>
            <span class="menuButton"><g:link class="create" controller="headline" action="create"><g:message code="default.new.label" args="[entityNameHeadline]" /></g:link></span>
			<g:if test="${session?.user?.admin}">
				<span class="menuButton"><g:link class="edit" controller="user" action="list"><g:message code="admin.label" /></g:link></span>
			</g:if>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${attachmentInstance}">
            <div class="errors">
                <g:renderErrors bean="${attachmentInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" enctype="multipart/form-data">
				<g:hiddenField name="headline.id" value="${attachmentInstance?.headline?.id}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="body"><g:message code="attachment.file.label" default="File" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: attachmentInstance, field: 'file', 'errors')}">
                                	<input type="file" name="file" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
					<span class="button"><g:link class="edit" controller="headline" action="show" id="${attachmentInstance?.headline?.id}"><g:message code="default.cancel.button" default="Cancel" /></g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
