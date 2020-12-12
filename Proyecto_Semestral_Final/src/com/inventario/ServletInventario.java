package com.inventario;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.proyecto.modelo.beans.Administrador;
import com.proyecto.modelo.beans.Producto;

/**
 * Servlet implementation class ServletInventario
 */
public class ServletInventario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String rutaJsp;
	private DataSource ds;
	private Connection con;     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletInventario() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		rutaJsp=config.getInitParameter("rutaJsp");
		System.out.println(rutaJsp);
		try {
			InitialContext initContext =new InitialContext();
			Context env=(Context)initContext.lookup("java:comp/env");
			ds=(DataSource) env.lookup("jdbc/DB1LS221");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String accion = request.getParameter("accion");
		 HttpSession sesion = request.getSession();
		if (accion !=null) {
			 if(accion.equals("registroProd")) {
				 getServletContext().getRequestDispatcher(rutaJsp+"/inventario/Registro/RegistroProducto.jsp").forward(request,response);
			 }			 
			/* else if (accion.equals("iniciarSeccion")){
				 getServletContext().getRequestDispatcher(rutaJsp+"postLogin.jsp").forward(request,response);
			 }*/
			
		}
		else {
			getServletContext().getRequestDispatcher(rutaJsp+"login.jsp").forward(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String accion = request.getParameter("accion");
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (accion !=null) {
			 if (accion.equals("ProdutAdd")){
				 
				 String nombre=request.getParameter("name");
				 String codigo=request.getParameter("codigo");

				 if(nombre=="" || codigo=="" ) {
					 request.setAttribute("error","Campos en blando no permitidos");
					 getServletContext().getRequestDispatcher(rutaJsp+"/inventario/Registro/RegistroProducto.jsp").forward(request,response);
				 }

				 Producto producto = new Producto();
				 producto.setNombre(nombre);
				 producto.setCodigo(codigo);
				 producto.setCon(con);
				 
				 if(!producto.CheckCodigoProducto()) {
					 if(producto.crearProducto()) {
						 request.setAttribute("addProdResponse","Producto creado correctamente");
					 }
					 else {
						 request.setAttribute("addProdResponse","Error al crear el producto");
					 }
				 }else {
					 request.setAttribute("addProdResponse","El Codigo de producto ya existe");
				 }
				 getServletContext().getRequestDispatcher(rutaJsp+"/inventario/Registro/RespuestaProducto.jsp").forward(request,response);
			 }
		}
		else {
			getServletContext().getRequestDispatcher(rutaJsp+"login.jsp").forward(request,response);
		}
	}

}
