<div id="search">
	<g:form url='[controller: "headline", action: "search"]'
		id="headlineSearchForm"
		name="headlineSearchForm"
		method="get">
		<g:textField name="offerNumber" value="${params.offerNumber}"/>
	</g:form>
</div>