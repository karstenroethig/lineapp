

<%@ page import="karstenroethig.lineapp.Scene" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'scene.label', default: 'Scene')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" controller="headline" action="show" id="${sceneInstance?.headline?.id}"><g:message code="scene.headline.label" default="Headline" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${sceneInstance}">
            <div class="errors">
                <g:renderErrors bean="${sceneInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
				<g:hiddenField name="headline.id" value="${sceneInstance?.headline?.id}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="body"><g:message code="scene.body.label" default="Body" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sceneInstance, field: 'body', 'errors')}">
                                    <g:textArea name="body" cols="40" rows="5" value="${sceneInstance?.body}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
