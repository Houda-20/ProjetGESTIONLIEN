<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags"%>


<jsp:include page="../fragments/userheader.jsp" />
<div class="container">

	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">

			<jsp:include page="../fragments/usermenu.jsp" />

		</div>
	</nav>




	<div>
		<h3>Prof home page</h3>
		<p>Hello and welcome to your application</p>

		<s:authorize access="isAuthenticated()">
    			You are connected with: 
			<s:authentication property="principal.username" /> <br>
			Your Email :      <s:authentication property="principal.email" /><br>
			Your First Name : <s:authentication property="principal.firstName" /><br>
			Your Last name : <s:authentication property="principal.LastName" /><br>
		</s:authorize>


<%--		<form action="/prof/import" method="get" enctype="multipart/form-data">--%>
<%--			<button type="submit">Import File</button>--%>
<%--		</form>--%>
<%--	</div>--%>


	
	<div>
		<form method="post" action="/prof/import" enctype="multipart/form-data">
            Importez votre fichier EXCEL :<br/>
            <input type="file" name="name" accept=".xlsx, .xls, .csv" multiple
                   onchange="readFilesAndDisplayPreview(this.files);" class="form-control-file"/> <br/><br/>
            <input type="submit" value="IMPORTEZ" class="btn btn-primary"/> <br/>
            <div id="list"></div>
        </form>
	</div>



<jsp:include page="../fragments/userfooter.jsp" />

