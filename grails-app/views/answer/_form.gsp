<%@ page import="nagi.Answer" %>



<div class="fieldcontain ${hasErrors(bean: answerInstance, field: 'body', 'error')} ">
	<label for="body">
		<g:message code="answer.body.label" default="Body" />
		
	</label>
	<g:textField name="body" value="${answerInstance?.body}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: answerInstance, field: 'question', 'error')} ">
	<label for="question">
		<g:message code="answer.question.label" default="Question" />
		
	</label>
	<g:textField name="question" value="${answerInstance?.question}"/>

</div>

