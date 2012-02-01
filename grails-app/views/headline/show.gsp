
<%@ page import="karstenroethig.lineapp.Headline" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'headline.label', default: 'Headline')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
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
                            <td valign="top" class="name"><g:message code="headline.subject.label" default="Subject" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: headlineInstance, field: "subject")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="headline.subHeadline.label" default="Sub-Headline" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: headlineInstance, field: "subHeadline")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="headline.federalLand.label" default="Federal Land" /></td>
                            
                            <td valign="top" class="value">${headlineInstance?.federalLand?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="headline.location.label" default="Location" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: headlineInstance, field: "location")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="headline.body.label" default="Body" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: headlineInstance, field: "body")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="headline.scenes.label" default="Scenes" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${headlineInstance?.scenes?.sort{it.id}}" var="s">
                                    <li style="padding-bottom: 1em;">
                                    	${s?.encodeAsHTML()} (<g:link controller="scene" action="show" id="${s.id}"><g:message code="default.edit.label.single" default="edit" /></g:link>)
                                    </li>
                                </g:each>
                                </ul>
								<g:link controller="scene" action="create" params="['headline.id': headlineInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'scene.label', default: 'Scene')])}</g:link>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="headline.status.label" default="Status" /></td>
                            
                            <td valign="top" class="value">${headlineInstance?.status?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="headline.key.label" default="Key" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: headlineInstance, field: "key")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="headline.recordingDate.label" default="Recording Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate format="dd.MM.yyyy HH:mm" date="${headlineInstance?.recordingDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="headline.comment.label" default="Comment" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: headlineInstance, field: "comment")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="headline.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate format="dd.MM.yyyy HH:mm" date="${headlineInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="headline.author.label" default="Author" /></td>
                            
                            <td valign="top" class="value"><g:link controller="user" action="show" id="${headlineInstance?.author?.id}">${headlineInstance?.author?.fullname?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="headline.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate format="dd.MM.yyyy HH:mm" date="${headlineInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="headline.updateAuthor.label" default="Update Author" /></td>
                            
                            <td valign="top" class="value"><g:link controller="user" action="show" id="${headlineInstance?.updateAuthor?.id}">${headlineInstance?.updateAuthor?.fullname?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${headlineInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
					<span class="button"><g:actionSubmit class="edit" action="publish" value="${message(code: 'headline.publish.button', default: 'Publish')}" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
