
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

			<g:form method="post">
				<div class="dialog">
					<table>
						<tbody>
							<tr class="prop">
								<td valign="top" class="name"><g:message code="headline.search.param.textsearch.label" default="Textsearch" /></td>
								
								<td valign="top" class="value">
									<table style="padding: 0; border: 0; width: auto;">
										<tr>
											<td style="padding: 0;">
												<g:message code="headline.search.param.textsearch.query.label" default="Suchtext:" />
											</td>
											<td colspan="3" style="padding: 0;">
												<input id="search_param_text" type="text" value="${search_param_text}" name="search_param_text" style="width: 100%;" />
											</td>
										</tr>
										<tr>
											<td style="padding: 0 2px;">
												<g:message code="headline.search.param.textsearch.searchfields.label" default="Searchfields:" />
											</td>
											<td style="padding: 0 2px;">
												<g:checkBox name="search_param_text_opt_headline" value="${search_param_text_opt_headline}" /> <g:message code="headline.search.param.textsearch.searchfields.headline.label" default="Headline" />
											</td>
											<td style="padding: 0 2px;">
												<g:checkBox name="search_param_text_opt_subHeadline" value="${search_param_text_opt_subHeadline}" /> <g:message code="headline.search.param.textsearch.searchfields.subHeadline.label" default="Sub-Headline" />
											</td>
											<td style="padding: 0 2px;">
												<g:checkBox name="search_param_text_opt_body" value="${search_param_text_opt_body}" /> <g:message code="headline.search.param.textsearch.searchfields.body.label" default="Body" />
											</td>
										</tr>
										<tr>
											<td style="padding: 0 2px;">
												&nbsp;
											</td>
											<td style="padding: 0 2px;">
												<g:checkBox name="search_param_text_opt_comment" value="${search_param_text_opt_comment}" /> <g:message code="headline.search.param.textsearch.searchfields.comment.label" default="Comment" />
											</td>
											<td style="padding: 0 2px;">
												<g:checkBox name="search_param_text_opt_scenes" value="${search_param_text_opt_scenes}" /> <g:message code="headline.search.param.textsearch.searchfields.scenes.label" default="Bilder und Töne" />
											</td>
											<td style="padding: 0 2px;">
												&nbsp;
											</td>
										</tr>
									</table>
								</td>
								
							</tr>
							<tr class="prop">
								<td valign="top" class="name"><g:message code="headline.search.param.status.label" default="Status" /></td>
								
								<td valign="top" class="value">
									<g:select
										name="search_param_status"
										noSelection="${['':'Alle']}"
										from="${karstenroethig.lineapp.HeadlineStatus?.values()}"
										keys="${karstenroethig.lineapp.HeadlineStatus?.values()*.name()}"
										value="${search_param_status}" />
								</td>
								
							</tr>
						</tbody>
					</table>
				</div>
				<div class="buttons" style="margin-bottom: 15px;">
					<span class="button"><g:actionSubmit class="search" action="search" value="${message(code: 'default.button.search.label', default: 'Search')}" /></span>
					<span class="button"><g:actionSubmit class="resetSearch" action="resetSearch" value="${message(code: 'default.button.resetSearch.label', default: 'Reset')}" /></span>
				</div>
			</g:form>

            <div class="list">
                <table>
                    <thead>
                        <tr>
							
							<g:sortableColumn property="offerNumber" title="${message(code: 'headline.offerNumber.label', default: 'Offer Number')}" />
                        
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
							
							<td><g:link action="show" id="${headlineInstance.id}">${fieldValue(bean: headlineInstance, field: "offerNumber")}</g:link></td>
                        
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
