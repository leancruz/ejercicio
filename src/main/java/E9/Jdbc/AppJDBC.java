package E9.Jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import E9.Jdbc.Dao.EmploDao;

public class AppJDBC {

	public static void main(String[] args) {
		try {
			Connection conection = AdminBD.obtenerConexion();
			System.out.println("----SISTEMA DE ABM DE EMPLEADOS----");
			System.out.println("-----------------------------------");
			System.out.println("1 - Alta");
			System.out.println("2 - Modificacion");
			System.out.println("3 - Baja");
			System.out.println("4 - Listado");
			System.out.println("0 - Salir");
			System.out.println("--------------------");
			Scanner sc = new Scanner(System.in);
			int opcion = sc.nextInt();

			while (opcion != 0) {

				switch (opcion) {
				case 1:
					insertar(conection, sc);
					break;
				case 2:
					modif(conection, sc);
					break;
				case 3:
					baja(conection, sc);
					break;
				case 4:
					listar(conection);
					break;

				}
				System.out.println("--------------------");
				System.out.println("1 - Alta");
				System.out.println("2 - Modificacion");
				System.out.println("3 - Baja");
				System.out.println("4 - Listado");
				System.out.println("0 - Salir");
				System.out.println("--------------------");
				opcion = sc.nextInt();
			}
			sc.close();
			conection.close();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("No se pudo realizar la operacion " + e.getMessage());
		}
	}

	/*
	 * ----------------------------------------------------------------------
	 * -------------------------------ALTA-----------------------------------
	 * ----------------------------------------------------------------------
	 */

	private static void insertar(Connection conection, Scanner sc) throws SQLException {
		Statement stmt = conection.createStatement();
		System.out.println("Ingrese email:");
		String email = sc.next();

		System.out.println("Ingrese Nombre:");
		String firstName = sc.next();

		System.out.println("Ingrese Apellido:");
		String lastName = sc.next();

		Emplo emp = new Emplo();
		emp.setEmail(email);
		emp.setFirstName(firstName);
		emp.setLastName(lastName);
		EmploDao.insertar(emp, stmt);
	}

	/*
	 * ----------------------------------------------------------------------
	 * --------------------------MODIFICACION-------------------------------
	 * ----------------------------------------------------------------------
	 */

	private static void modif(Connection conection, Scanner sc) throws SQLException {
		Statement stmt = conection.createStatement();
		System.out.println("Ingrese ID a Modificar:");
		int id = sc.nextInt();

		Emplo buscado = buscar(conection, id);

		if (buscado == null) {

			System.out.println("No se encontro el ID ");

		} else {
			System.out.println("Ingrese email:");
			String email = sc.next();
			System.out.println("Ingrese Nombre:");
			String firstName = sc.next();
			System.out.println("Ingrese Apellido:");
			String lastName = sc.next();

			Emplo emp = new Emplo();
			emp.setId(id);
			emp.setEmail(email);
			emp.setFirstName(firstName);
			emp.setLastName(lastName);
			EmploDao.modif(emp, stmt);
		}
	}

	/*
	 * ----------------------------------------------------------------------
	 * -------------------------------BAJA-----------------------------------
	 * ----------------------------------------------------------------------
	 */
	private static void baja(Connection conection, Scanner sc) throws SQLException {
		Statement stmt = conection.createStatement();
		System.out.println("Ingrese ID para dar de baja:");
		int id = sc.nextInt();
		Emplo emp = buscar(conection, id);
		EmploDao.baja(emp, stmt);

	}

	/*
	 * ----------------------------------------------------------------------
	 * ------------------------------LISTAR----------------------------------
	 * ----------------------------------------------------------------------
	 */
	private static void listar(Connection conection) throws SQLException {

		Statement stmt = conection.createStatement();
		ResultSet rs = stmt.executeQuery("select * from emplo");
		while (rs.next()) {
			int id = rs.getInt(1);
			String mail = rs.getString(2);
			String name = rs.getString(3);
			String last = rs.getString(4);
			System.out.println(id + "|||" + mail + "|||" + name + "|||" + last);
		}
	}

	/*
	 * ----------------------------------------------------------------------
	 * ------------------------------BUSQUEDA---------------------------------
	 * ----------------------------------------------------------------------
	 */

	private static Emplo buscar(Connection con, int id) throws SQLException {

		Statement stmt = con.createStatement();
		Emplo emp = EmploDao.buscar(stmt, id);
		return emp;

	}
}