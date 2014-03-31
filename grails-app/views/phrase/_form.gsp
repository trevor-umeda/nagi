<%@ page import="nagi.Phrase" %>



<div class="fieldcontain ${hasErrors(bean: phraseInstance, field: 'body', 'error')} ">
	<label for="body">
		<g:message code="phrase.body.label" default="Body" />
		
	</label>
	<g:textField name="body" value="${phraseInstance?.body}"/>

</div>

