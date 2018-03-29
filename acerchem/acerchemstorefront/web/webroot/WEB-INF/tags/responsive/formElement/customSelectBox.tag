<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="items" required="true" type="java.util.Collection" %>
<%@ attribute name="idKey" required="true" type="java.lang.String" %>
<%@ attribute name="path" required="true" type="java.lang.String" %>
<%@ attribute name="itemValue" required="false" type="java.lang.String" %>
<%@ attribute name="itemLabel" required="false" type="java.lang.String" %>
<%@ attribute name="skipBlank" required="false" type="java.lang.Boolean" %>
<%@ attribute name="skipBlankMessageKey" required="false" type="java.lang.String" %>
<%@ attribute name="selectedValue" required="false" type="java.lang.String" %>
<%@ attribute name="tabindex" required="false" rtexprvalue="true" %>
<%@ attribute name="labelKey" required="true" type="java.lang.String" %>
<%@ attribute name="mandatory" required="false" type="java.lang.Boolean" %>
<%@ attribute name="selectCSSClass" required="false" type="java.lang.String" %>

<form:select id="${fn:escapeXml(idKey)}" path="${fn:escapeXml(path)}" tabindex="${fn:escapeXml(tabindex)}" class="required">
	<c:if test="${skipBlank == null || skipBlank == false}">
		<option value="" disabled="disabled" ${empty selectedValue ? 'selected="selected"' : ''}>
			<spring:theme code='${skipBlankMessageKey}'/>
		</option>
	</c:if>
	<form:options items="${items}" itemValue="${not empty itemValue ? itemValue :'code'}" itemLabel="${not empty itemLabel ? itemLabel :'name'}" htmlEscape="true"/>
</form:select>
