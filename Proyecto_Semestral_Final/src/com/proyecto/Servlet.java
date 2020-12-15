package com.proyecto;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.proyecto.modelo.beans.Administrador;
import com.proyecto.modelo.beans.login;

/**
 * Servlet implementation class Servlet
 */
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String rutaJsp;
	private DataSource ds;
	private Connection con; 
	private int hitCount; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
    	hitCount = 0;
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
		HttpSession sesion=request.getSession(true);
		if (accion !=null) {
			 if(accion.equals("login")) {
				 getServletContext().getRequestDispatcher(rutaJsp+"login.jsp").forward(request,response);
			 }
			 else if (accion.equals("inicio")){
				 getServletContext().getRequestDispatcher(rutaJsp+"index.jsp").forward(request,response);
			 }
			 else if (accion.equals("logout")){
				 sesion.invalidate();
				 getServletContext().getRequestDispatcher(rutaJsp+"login.jsp").forward(request,response);
			 }
			 else if (accion.equals("OlvideContra")){
				 getServletContext().getRequestDispatcher(rutaJsp+"/Login/OlvideContra/OlvideContra.jsp").forward(request,response);
			 }
			
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
				HttpSession sesion=request.getSession(true);
				if (accion !=null) {
					 if (accion.equals("iniciarSeccion")){
						 
						 String usuario=request.getParameter("email");
						 String password=request.getParameter("password");
						 if(usuario==""  || password=="") {
							 getServletContext().getRequestDispatcher(rutaJsp+"login.jsp").forward(request,response);
						 }
						 int Intentos =0;
						 String eMail="";
						 try {							 
							 if(sesion.getAttribute("usuario")!=null) {
								 eMail=  (String) sesion.getAttribute("usuario");
							 }
						 } catch (Exception e) {}
						 
						 login userlogin = new login(con);
						 if(!userlogin.loginCheckEmailInactivo(usuario)) {
						 if (userlogin.loginCheck(usuario, password)) {
						 Administrador uselogin = new login(con).getUserRol(usuario, password);
						 //Ambito Request
						 request.setAttribute("usuario",usuario);			 
						 request.setAttribute("password", password);
						 //Ambito Seccion
						 sesion.setAttribute("login",true);
						
						 if(uselogin.getRolId()==1) {
							 sesion.setAttribute("rol","1");
							 getServletContext().getRequestDispatcher(rutaJsp+"Admin/Administrador.jsp").forward(request,response); 
						 }else if(uselogin.getRolId()==2) {
							 sesion.setAttribute("rol","2");
							 getServletContext().getRequestDispatcher(rutaJsp+"Admin/Administrador.jsp").forward(request,response);
						 }else if(uselogin.getRolId()==3) {
							 sesion.setAttribute("rol","3");
							 getServletContext().getRequestDispatcher(rutaJsp+"Admin/Administrador.jsp").forward(request,response);
						 }
					    }else {
					    	System.out.println("eMail: "+eMail);
					    	System.out.println("usuario: "+usuario);
					    	if (eMail.equals(usuario)) {
					    		hitCount++;
					    	}else {
					    		hitCount=0;
					    		sesion.setAttribute("usuario",usuario);
					    	}
					    	if((3-hitCount)==0) {
					    		hitCount=0;
					    		userlogin.actualizarEstado(usuario);
					    		request.setAttribute("error","Usuario Bloqueado, Comuníquese con el administrador" );
					    	}else {
					    		request.setAttribute("error","Usuario o Contraseña Incorrecta: intentos restantes: "+(3-hitCount) );
					    	}
					    	
			
					    }
					    	getServletContext().getRequestDispatcher(rutaJsp+"login.jsp").forward(request,response);
					    }else {
					    	request.setAttribute("error","**Usuario Bloqueado, Comuníquese con el administrador**" );
					    	getServletContext().getRequestDispatcher(rutaJsp+"login.jsp").forward(request,response);
					    }
						 
					 }else if(accion.equals("GenerarContra")) {
						 String usuario=request.getParameter("email");
						 login userlogin = new login(con);
						 if(!userlogin.loginCheckEmailInactivo(usuario)) {
							 if (userlogin.loginCheckEmail(usuario))
							 {
								 StringBuffer password= GetPasswordTemp();
								 Date fecha =fechaEXpira(4);
								 System.out.println(fecha);
								 if(userlogin.actualiZareXpira(usuario, fecha, password.toString())) {
									 request.setAttribute("password",password);
									 getServletContext().getRequestDispatcher(rutaJsp+"/Login/OlvideContra/OlvideContra.jsp").forward(request,response);
		 
								 }else {
									 request.setAttribute("error","Error al actualiZar el password, Comuníquese con el administrador");
									 getServletContext().getRequestDispatcher(rutaJsp+"/Login/OlvideContra/OlvideContra.jsp").forward(request,response);
								 }
								 
								 
							 }else {
								 
								 request.setAttribute("error","Informacion poco precisa, Comuníquese con el administrador");
								 getServletContext().getRequestDispatcher(rutaJsp+"/Login/OlvideContra/OlvideContra.jsp").forward(request,response);
	 
							 } 
					 }else {
						 request.setAttribute("error","**Usuario Bloqueado, Comuníquese con el administrador**" );
						 getServletContext().getRequestDispatcher(rutaJsp+"/Login/OlvideContra/OlvideContra.jsp").forward(request,response);
					 }
				   }
				}
				else {
					getServletContext().getRequestDispatcher(rutaJsp+"login.jsp").forward(request,response);
				}
	}
	
   public StringBuffer GetPasswordTemp(){
		char[] allowedCharacters = {'a','b','c','1','2','3','4'};

		 SecureRandom random = new SecureRandom();
		 StringBuffer password = new StringBuffer();

		 for(int i = 0; i < 6; i++) {
		        password.append(allowedCharacters[ random.nextInt(allowedCharacters.length) ]);
		 }
	return password;
	} 
   public Date fechaEXpira(int hours) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.HOUR_OF_DAY, hours);
	    return calendar.getTime();
	}

}
