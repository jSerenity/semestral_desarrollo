<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
 <%@page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>FACTURAR PRODUCTO</title>
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
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/vendor/toastr/toastr.min.css">
<style>
    form{
        margin: 20px 0;
    }
    form input, button{
        padding: 5px;
    }
    table{
        width: 100%;
        margin-bottom: 20px;
		border-collapse: collapse;
    }
    table, th, td{
        border: 1px solid #cdcdcd;
    }
    table th, table td{
        padding: 10px;
        text-align: left;
    }
</style>

</head>
<body>

<%@include file="../../barra.jsp" %>
<% int i=0; String color=""; %>
	<div class="contact1">
		<div class="container-contact1">
	
			<!-- <form style="width: 100%;" method="post" action="/Proyecto_Semestral_Final/ServletCajera?accion=facturar" >-->
			<form style="width: 100%;">
				<span class="contact1-form-title">
					Creación de factura
				</span>

				
				<div class="row">
					<div class="col">
						 <div>
						 <div class="wrap-input1 validate-input" data-validate = "Tipo de usuario requerido">
					<label for="formGroupExampleInput">Seleccione el ccliente</label>
					<select class="input1" style="
						    height: 50px;
						    border-radius: 25px;
						    padding: 0 30px;
						" id="codigoCliente" name="codigoCliente" >
					    <option value="" disabled selected>Seleccione una opcion</option>	   
					    <c:forEach items="${clientesr}" var="cli">
				        <option value="${cli.id}">${cli.nombre}</option>
				    </c:forEach>
					</select>
					<span class="shadow-input1"></span>
				</div>
        <div class="wrap-input1 validate-input" data-validate = "Tipo de usuario requerido">
					<label for="formGroupExampleInput">Seleccione el producto</label>
					<select class="input1" style="
						    height: 50px;
						    border-radius: 25px;
						    padding: 0 30px;
						" id="codigoProducto" name="codigoProducto" >
					    <option value="" disabled selected>Seleccione una opcion</option>	   
					    <c:forEach items="${productos}" var="prod">
				        <option value="${prod.codigo}">${prod.nombre}</option>
				    </c:forEach>
					</select>
					<span class="shadow-input1"></span>
				</div>
        <div class="wrap-input1 validate-input" data-validate = "Campo requerido">
				    <label for="formGroupExampleInput">Cantidad en unidades</label>
					<input class="input1" type="text" name="cantidad" id="cantidad" placeholder="cantidad" >
					<span class="shadow-input1"></span>
				</div>
    	<input type="button" class="contact1-form-btn" id="agregar" value="Agregar">
    </div>
    <table>
        <thead>
            <tr>
                <th>#</th>
                <th>Codigo</th>
                <th>Descripción</th>
                <th>cantidad</th>
                <th>Precio por unidad</th>
                <th>valor</th>
                <th>Eliminar</th>
            </tr>
        </thead>
        <tbody>
        
        </tbody>
    </table>
					</div>
				</div>
				
				<div class="container-contact1-form-btn">
				<a class="contact1-form-btn" id="submit">Crear factura
					<span>
						<i class="fa fa-long-arrow-left" aria-hidden="true"></i>
					</span>
				</a>
				</div>
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
	<script src="<%=request.getContextPath()%>/vendor/toastr/toastr.min.js"></script>
	<script >
		$('.js-tilt').tilt({
			scale: 1.1
		})
	</script>
	<script>
    $(document).ready(function(){
        $("#agregar").click(function(){
            var codigo = $("#codigoProducto").val();
            var cantidad = $("#cantidad").val();
          
            var Nombre;
        	var Existencia;
    		var valor;
    		var Total;
    		var precio;
    		if(codigo!=""){ $.get('ServletInventario', {
            	accion:'getProductoValues',
                codigoProd : codigo
		        }, function(responseText) {
		        	Nombre =responseText.Nombre;
		        	Total =responseText.Total;
		            precio =responseText.precio;
		            valor = cantidad*precio;
		            console.log(Total);
		    		console.log(cantidad);
		    		if(Total>=cantidad){
		    			var markup = "<tr><td><input type='checkbox' name='record'></td><td name='test'>" + codigo + "</td><td>"+Nombre+"</td><td>" + cantidad +"</td><td>"+precio+"</td><td>"+valor+ "</td><td><a class='btn btn-xs delete-record' data-id='0'><i class='fa fa-trash-o' aria-hidden='true'></i></a></td><input type = 'hidden' name = 'someName' value = 'someData'></tr> ";
		                $("table tbody").append(markup);
		                console.log(get());
		    		}else{
		    			toastr.info('No hay suficiente stock de'+ Nombre+ ' para esta cantidad: '+cantidad)
		    		}
		        });
    		}
    		
            
        });
        
        jQuery(document).delegate('a.delete-record', 'click', function(e) {
             e.preventDefault();    
             
             var didConfirm = confirm("Esta seguro de eliminar esta fila?");
             if (didConfirm == true) {
              
              $(this).parents("tr").remove();
            
            return true;
          } else {
            return false;
          }
        });
        $("#submit").click(function() {
        	   //var orderId =  $("#orderId").val();
				var codigo = $("#codigoCliente").val();
        	   $.post('ServletCajera', {
        		   dataType: 'json',
            	accion:'facturar',
            	cliente:codigo,
            	data: JSON.stringify(get())
		        },
        	   function(data) {
        	     alert("Data Loaded: " + data);
        	   });
        	   })
    }); 
    function get(){
    	  var table = $('table');
    	  var data = [];

    	  table.find('tr').each(function (i, el) {
    	    // no thead
    	    if( i != 0){
    	      var $tds = $(this).find('td');
    	      var row = [];
    	      $tds.each(function (i, el){
    	        row.push($(this).text());
    	      });
    	      data.push(row);
    	    }
    	        
    	  });
    	  return data;
    	}
</script>
<!--===============================================================================================-->
</body>
</html>
