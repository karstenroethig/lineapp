

<%@ page import="karstenroethig.lineapp.Headline" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'headline.label', default: 'Headline')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
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
                                  <label for="subject"><g:message code="headline.subject.label" default="Subject" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: headlineInstance, field: 'subject', 'errors')}">
                                    <g:textField name="subject" value="${headlineInstance?.subject}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="federalLand"><g:message code="headline.federalLand.label" default="Federal Land" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: headlineInstance, field: 'federalLand', 'errors')}">
                                    <g:select name="federalLand" from="${karstenroethig.lineapp.FederalLand?.values()}" keys="${karstenroethig.lineapp.FederalLand?.values()*.name()}" value="${headlineInstance?.federalLand?.name()}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="location"><g:message code="headline.location.label" default="Location" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: headlineInstance, field: 'location', 'errors')}">
                                    <g:textField name="location" value="${headlineInstance?.location}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="body"><g:message code="headline.body.label" default="Body" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: headlineInstance, field: 'body', 'errors')}">
                                    <g:textArea name="body" cols="80" rows="5" value="${headlineInstance?.body}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="recordingDate"><g:message code="headline.recordingDate.label" default="Recording Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: headlineInstance, field: 'recordingDate', 'errors')}">
                                    <g:datePicker name="recordingDate" precision="day" value="${headlineInstance?.recordingDate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="comment"><g:message code="headline.comment.label" default="Comment" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: headlineInstance, field: 'comment', 'errors')}">
                                    <g:textArea name="comment" cols="40" rows="5" value="${headlineInstance?.comment}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
