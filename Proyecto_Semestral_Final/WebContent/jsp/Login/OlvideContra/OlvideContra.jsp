<%@page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Contact V1</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/formulario.css" type="text/css"/>
<!--===============================================================================================-->
	<link rel="icon" type="image/png" href="<%=request.getContextPath()%>/images/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/vendor/animate/animate.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/vendor/select2/select2.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/util.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/froms.css">
<!--===============================================================================================-->
</head>
<body>
	<div class="contact1">
		<div class="container-contact1">

			<div class="contact1-pic js-tilt" data-tilt>
				<img src="images/img-03.png" alt="IMG">
			</div>

			<form class="contact1-form validate-form" method="post" action="?accion=GenerarContra" >
				<span class="contact1-form-title">
					Escriba su correo para recuperar la contraseña
				</span>		
				<%if(request.getAttribute("error")!=null){%>
					<div class="text-center p-t-12">
						<span class="txt1">
							<% out.println(request.getAttribute("error"));%>
						</span>
					</div>
					<%}%>		
				<%if(request.getAttribute("password")!=null){%>
				<div class="wrap-input1 validate-input" >
					<label>Nueva contraseña, temporal</label>
					<input class="input1" value="<% out.println(request.getAttribute("password"));%>">
					<span class="shadow-input1"></span>
				</div>
				<div class="container-contact1-form-btn">
					<a class="contact1-form-btn" href="?accion=login">Regresar login
						<span>
						    <i class="fa fa-long-arrow-left" aria-hidden="true"></i>
						</span>
					</a>
				</div>
		      <%} else {%>
				<div class="wrap-input1 validate-input" data-validate = "El correo es requerido: ejemplo@abc.com">
					<input class="input1" type="text" name="email" id="email" placeholder="Correo Electrónico">
					<span class="shadow-input1"></span>
				</div>

				<div class="container-contact1-form-btn">
					<a class="contact1-form-btn" href="?accion=login">Regresar login
						<span>
						    <i class="fa fa-long-arrow-left" aria-hidden="true"></i>
						</span>
					</a>
					<button  class="contact1-form-btn">
						<span>
							Enviar
							<i class="fa fa-long-arrow-right" aria-hidden="true"></i>
						</span>
					</button>
					
				</div>
				<%}%>
			</form>
			
		</div>
	</div>




<!--===============================================================================================-->
	<script src="<%=request.getContextPath()%>/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
	<script src="<%=request.getContextPath()%>/vendor/bootstrap/js/popper.js"></script>
	<script src="<%=request.getContextPath()%>/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="<%=request.getContextPath()%>/vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
	<script src="<%=request.getContextPath()%>/vendor/tilt/tilt.jquery.min.js"></script>
	<script >
		$('.js-tilt').tilt({
			scale: 1.1
		})
	</script>
<!--===============================================================================================-->
	<script src="<%=request.getContextPath()%>/js/validateUpdate.js"></script>

</body>
</html>