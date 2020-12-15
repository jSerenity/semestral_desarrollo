package com.proyecto.modelo.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

public class caja {
	private Connection con;
	
	public caja(Connection con) {
		super();
		this.con = con;
	}

public boolean CheckClientName(String email, String cedula) {
		
		String sql="Select count(*) as dato from clientes where Email=? OR Cedula=?";
		int cta=0;
		
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, email);
			st.setString(2, cedula);
			
			ResultSet rs=st.executeQuery();
			
			if (rs.next()) {
				cta=rs.getInt("dato");
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		if (cta==0) {
			return false;
		}else return true;	
	}
public boolean crearCliente(cliente values){
	int cta=0;
	try {
		PreparedStatement st = con.prepareCall("{call sp_insertar_cliente(?,?,?,?,?,?)}");
		st.setString(1, values.getNombre());
		st.setString(2, values.getApellido());
		st.setString(3, values.getEmail());
		st.setString(4, values.getCelular());
		st.setString(5, values.getDireccion());
		st.setString(6, values.getCedula());
		ResultSet rs=st.executeQuery();
		if (rs.next()) {
			cta=rs.getInt("resultado");
		}
		rs.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("Error en el procedimiento");
	}
	if (cta==0) {
		return false;
	}else return true;
	
}
public ArrayList<cliente>  getClientes(){
	
	ArrayList<cliente>  lista = new ArrayList<cliente>(); 
	String sql="SELECT Id, Nombre, Apellido, Email, Celular, Direccion, Cedula FROM clientes";
	try {
		PreparedStatement st = con.prepareStatement(sql);
		ResultSet rs=st.executeQuery();
		while (rs.next()) {
			cliente objeto = new cliente(rs.getInt("Id"),rs.getString("nombre"),
					rs.getString("Apellido"), rs.getString("Cedula"),rs.getString("Email"),rs.getString("Celular"),rs.getString("Direccion"));
			lista.add(objeto);
		}
		rs.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("Error en la consulta");
	}
	System.out.println("TEST "+lista.size());
	return lista;
	
}
public boolean  UpdateInventario(cliente cli){
	int cta=0;
	try {
		 Gson gson = new Gson();
		String productojson =  gson.toJson(cli);
		 System.out.println(productojson);
		PreparedStatement st = con.prepareCall("{call sp_actualiZar_cliente(?,?,?,?,?,?,?)}");
		st.setInt(1, cli.getId());
		st.setString(2, cli.getNombre());
		st.setString(3, cli.getApellido());
		st.setString(4, cli.getEmail());
		st.setString(5, cli.getCedula());
		st.setString(6, cli.getDireccion());
		st.setString(7, cli.getCedula());
		
		ResultSet rs=st.executeQuery();
		if (rs.next()) {
			cta=rs.getInt("resultado");
		}	
		rs.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("Error en la consulta");
	}
	if (cta==0) {
		return false;	
	}else return true;			
}
public int crearFactura(String clienteId, double p_monto) {
	
	int cta=0;
	try {
		PreparedStatement st = con.prepareCall("{call sp_insert_factura(?,?)}");
		st.setString(1, clienteId);
		st.setDouble(2, p_monto);
		
		ResultSet rs=st.executeQuery();
		
		if (rs.next()) {
			cta=rs.getInt("dato");
		}
		rs.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return 0;
	}
	return cta;
}
public boolean insertar_productos_de_factura(int idFact,factura list) {
	
	int cta=0;
	try {
		PreparedStatement st = con.prepareCall("{call sp_insertar_productos_factura(?,?,?,?)}");
		st.setInt(1, idFact);
		st.setString(2, list.getCodigo());
		st.setInt(3, list.getCantidad());
		st.setDouble(4, list.getTotal());

		ResultSet rs=st.executeQuery();
		if (rs.next()) {
			cta=rs.getInt("resultado");
		}
		rs.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("Error en el procedimiento");
	}
	if (cta==0) {
		return false;
	}else return true;
	
}
public ArrayList<factura> getFacturas() {
	
	
	ArrayList<factura>  lista = new ArrayList<factura>(); 
	String sql="SELECT a.Id, b.Nombre,b.Apellido,DATE_FORMAT( a.Fecha , '%d-%m-%Y %r' ) as fechafa , a.Monto FROM facturas a INNER join clientes b on a.ClienteId=b.Id";
	try {
		PreparedStatement st = con.prepareStatement(sql);
		ResultSet rs=st.executeQuery();
		while (rs.next()) {
			factura objeto = new factura();
			objeto.setId(rs.getInt("Id"));
			objeto.setNombre(rs.getString("Nombre")+" "+rs.getString("Apellido"));
			objeto.setTotal(rs.getDouble("Monto"));
			objeto.setFecha(rs.getDate("fechafa"));
			lista.add(objeto);
		}
		rs.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("Error en la consulta");
	}
	System.out.println("TEST "+lista.size());
	return lista;
}
}
