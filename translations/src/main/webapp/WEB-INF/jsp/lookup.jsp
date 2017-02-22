<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<head>
<link href="<c:url value="main.css" />" rel="stylesheet">
<title>Lost in Translation</title>
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h1>Lost in Translation</h1>
			<p>Use the form below to search the application's translation
				files.</p>
			<p>You can search by either label name (normal lookup) or label
				value (reverse lookup).</p>
			<p>Use the translation file drop down to select the desired
				language file to search.</p>
		</div>

		<div id="content">
			<spring:url value="/" htmlEscape="true" var="queryUrl" />
			<div class="form">
				<form:form modelAttribute="query" action="${queryUrl }"
					method="POST">
					<p>
						Translation File:
						<form:select id="translationFile" path="translationFile">
							<form:options items="${translationFiles}" />
						</form:select>
					</p>
					<p>
						Search String:
						<form:input id="queryString" path="queryString" />
					</p>
					<p>
						Lookup Type:
						<form:radiobuttons id="type" path="type" items="${lookupTypes}"
							itemLabel="displayText" />
					</p>
					<p>
						<input type="submit" value="Submit" />
					</p>
				</form:form>
			</div>

			<div class="results">
				<c:choose>
					<c:when test="${results != null}">
						<c:forEach var="result" items="${results}">
							<p>
								<c:out value="${result}" />
							</p>
						</c:forEach>
					</c:when>
					<c:when test="${noResults != null}">
						<p class="error">No results found</p>
					</c:when>
				</c:choose>
			</div>
		</div>

		<div id="footer">
			<footer>
				<p>Created by Meghan Day, 2017</p>
			</footer>
		</div>
	</div>
</body>

</html>