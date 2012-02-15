
<%@ page import="karstenroethig.lineapp.Headline" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'headline.label', default: 'Headline')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
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
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
							
							<g:sortableColumn property="key" title="${message(code: 'headline.key.label', default: 'Key')}" />
                        
                            <g:sortableColumn property="subject" title="${message(code: 'headline.subject.label', default: 'Subject')}" />
                        
                            <g:sortableColumn property="subHeadline" title="${message(code: 'headline.subHeadline.label', default: 'Sub-Headline')}" />
                        
                            <g:sortableColumn property="federalLand" title="${message(code: 'headline.federalLand.label', default: 'Federal Land')}" />
                        
                            <g:sortableColumn property="location" title="${message(code: 'headline.location.label', default: 'Location')}" />
							
							<g:sortableColumn property="status" title="${message(code: 'headline.status.label', default: 'Status')}" />
							
							<g:sortableColumn property="dateCreated" title="${message(code: 'headline.dateCreated.label', default: 'Date Created')}" />
							
							<g:sortableColumn property="author" title="${message(code: 'headline.author.label', default: 'Author')}" />
							
							<g:sortableColumn property="lastUpdated" title="${message(code: 'headline.lastUpdated.label', default: 'Last Updated')}" />
							
							<g:sortableColumn property="updateAuthor" title="${message(code: 'headline.updateAuthor.label', default: 'Update Author')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${headlineInstanceList}" status="i" var="headlineInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
							
							<td><g:link action="show" id="${headlineInstance.id}">${fieldValue(bean: headlineInstance, field: "key")}</g:link></td>
                        
                            <td>${fieldValue(bean: headlineInstance, field: "subject")}</td>
                        
                            <td>${fieldValue(bean: headlineInstance, field: "subHeadline")}</td>
                        
                            <td>${fieldValue(bean: headlineInstance, field: "federalLand")}</td>
                        
                            <td>${fieldValue(bean: headlineInstance, field: "location")}</td>
							
							<td>${fieldValue(bean: headlineInstance, field: "status")}</td>
							
							<td><g:formatDate format="dd.MM.yyyy HH:mm" date="${headlineInstance?.dateCreated}" /></td>
							
							<td><g:link controller="user" action="show" id="${headlineInstance?.author?.id}">${headlineInstance?.author?.fullname?.encodeAsHTML()}</g:link></td>
							
							<td><g:formatDate format="dd.MM.yyyy HH:mm" date="${headlineInstance?.lastUpdated}" /></td>
							
							<td><g:link controller="user" action="show" id="${headlineInstance?.updateAuthor?.id}">${headlineInstance?.updateAuthor?.fullname?.encodeAsHTML()}</g:link></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${headlineInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
