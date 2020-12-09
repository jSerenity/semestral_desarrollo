package com.admin;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.proyecto.modelo.beans.login;
import com.proyecto.modelo.beans.user;

/**
 * Servlet implementation class AdminUsers
 */
public class AdminUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String rutaJsp;
	private DataSource ds;
	private Connection con; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUsers() {
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
		String accion = request.getParameter("accion");
		 HttpSession sesion = request.getSession();
		if (accion !=null) {
			 if(accion.equals("createUser")) {
				 getServletContext().getRequestDispatcher(rutaJsp+"/Admin/Add/CreateUser.jsp").forward(request,response);
			 }
			 else if (accion.equals("updateUser")){
				 getServletContext().getRequestDispatcher(rutaJsp+"index.jsp").forward(request,response);
			 }
			 /* else if (accion.equals("logout")){
				 sesion.invalidate();
				 getServletContext().getRequestDispatcher(rutaJsp+"login.jsp").forward(request,response);
			 }
			else if (accion.equals("iniciarSeccion")){
				 getServletContext().getRequestDispatcher(rutaJsp+"postLogin.jsp").forward(request,response);
			 }*/
			
		}
		else {
			getServletContext().getRequestDispatcher(rutaJsp+"/Admin/Administrador.jsp").forward(request,response);
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
			 if (accion.equals("userAdd")){
				 String nombre=request.getParameter("name");
				 String email=request.getParameter("email");
				 String password=request.getParameter("password");
				 int rolId= Integer.parseInt(request.getParameter("rolId"));
				
				 Administrador user = new Administrador();
				 user.setNombre(nombre);
				 user.setEmail(email);
				 user.setPassword(password);
				 user.setRolId(rolId);
				 user.setCon(con);
				 
				 if(!user.CheckUserName()) {
					 if(user.crearUsuario()) {
						 request.setAttribute("addUserResponse","Usuario creado correctamente");
					 }
					 else {
						 request.setAttribute("addUserResponse","Error al crear el usuario");
					 }
				 }else {
					 request.setAttribute("addUserResponse","El usuario ya existe");
				 }
				 getServletContext().getRequestDispatcher(rutaJsp+"Admin/Add/PostAddUser.jsp").forward(request,response);
			 }
		}
		else {
			getServletContext().getRequestDispatcher(rutaJsp+"login.jsp").forward(request,response);
		}
	}

}
