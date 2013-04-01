<div id="search">
	<g:form url='[controller: "headline", action: "quicksearch"]'
		id="headlineSearchForm"
		name="headlineSearchForm"
		method="get">
		<g:message code="quicksearch.label" default="Quick Search" /> <g:textField name="quicksearchString" value="${params.quicksearchString}"/>
	</g:form>
</div>