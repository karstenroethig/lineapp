
<%@ page import="karstenroethig.lineapp.Contact" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contact.label', default: 'Contact')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
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
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'contact.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'contact.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="email" title="${message(code: 'contact.email.label', default: 'Email')}" />
                        
                            <th><g:message code="contact.mailingList.label" default="Mailing List" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${contactInstanceList}" status="i" var="contactInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${contactInstance.id}">${fieldValue(bean: contactInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: contactInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: contactInstance, field: "email")}</td>
                        
                            <td>${fieldValue(bean: contactInstance, field: "mailingList")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${contactInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
