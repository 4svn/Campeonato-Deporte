package main.doa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.config.ConfigBD;
import main.modelo.Equipo;
import main.modelo.Partido;

/**
 * @author YASSINE EL ATTAR
 *
 */
public class AccesoPartido {

	//insertar un partido con una sentencia insert 
	//usamos el objeto partido para obtener los valores de un partido
	public static boolean InsertarPartido(Partido partido) {
		Connection conexion = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conexion = ConfigBD.abrirConexion();
			System.out.println("Conectado");
			String sentenciaInsertar = "INSERT INTO partido (codigo_equipo_local, codigo_equipo_visitante, a�o_temporada, fecha, puntuacion_local, puntuacion_visitante) "
					+ "VALUES (?,?,?,?,?,?)";

			System.out.println(sentenciaInsertar);
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaInsertar);
			sentencia.setInt(1, partido.getCodigoEquipoLocal());
			sentencia.setInt(2, partido.getCodigoEquipoVisitante());
			sentencia.setDouble(3, partido.getA�oTemporada());
			sentencia.setString(4, partido.getFecha());
			sentencia.setInt(5, partido.getPuntuacionLocal());
			sentencia.setInt(6, partido.getPuntuacionVisitante());
			
			
			int filasInsertadas = sentencia.executeUpdate();
			if (filasInsertadas == 0) {
				System.out.println("Ya existe ese partido en la base de datos.");
				return false;
			} else {
				System.out.println("Se ha insertado el partido en la base de datos.");
				return true;
			}
		}

		catch (ClassNotFoundException cnfe) {
			System.out.println("Error al cargar el conector de SQLite: " + cnfe.getMessage());
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			System.out.println("Error de SQL: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			try {
				if (conexion != null) {
					conexion.close();
				}
			} catch (SQLException sqle) {
				System.out.println("Error al cerrar la base de datos: " + sqle.getMessage());
				sqle.printStackTrace();
			}
		}

		return false;
	}
	
	//actualizar un partido usando un objeto
	//el usuario proporciona un partido como objeto
	//proporciona el SET y las claves primarias en un solo objeto
	public static boolean ActualizarPartido(Partido partido) {
		Connection conexion = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conexion = ConfigBD.abrirConexion();
			System.out.println("Conectado");
			/*
			String sentenciaInsertar = 
					"UPDATE partido "
					+ "SET fecha = " + partido.getFecha() + ", "
					+ "SET puntuacion_local = "+ partido.getPuntuacionLocal() +", "
					+ "SET puntuacion_visitante = "+ partido.getPuntuacionVisitante() + ", "
					+ "WHERE codigo_equipo_local = "+  partido.getCodigoEquipoLocal() +", "
					+ "and codigo_equipo_visitante = "+ partido.getCodigoEquipoVisitante() +", "
					+ "and a�o_temporada = "+ partido.getA�oTemporada() + ",";
			
			System.out.println(sentenciaInsertar);
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaInsertar);
			*/
			String sentenciaInsertar = 
					"UPDATE partido "
					+ "SET fecha = ? , "
					+ "SET puntuacion_local = ?, "
					+ "SET puntuacion_visitante = ?, "
					+ "WHERE codigo_equipo_local = ?, "
					+ "and codigo_equipo_visitante = ?, "
					+ "and a�o_temporada = ?,";
			
			System.out.println(sentenciaInsertar);
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaInsertar);
			sentencia.setInt(4, partido.getCodigoEquipoLocal());
			sentencia.setInt(5, partido.getCodigoEquipoVisitante());
			sentencia.setDouble(6, partido.getA�oTemporada());
			sentencia.setString(1, partido.getFecha());
			sentencia.setInt(2, partido.getPuntuacionLocal());
			sentencia.setInt(3, partido.getPuntuacionVisitante());
			
			
			int filasInsertadas = sentencia.executeUpdate();
			if (filasInsertadas == 0) {
				System.out.println("Error al actualizar");
				return false;
			} else {
				System.out.println("Se ha actualizado el partido en la base de datos.");
				return true;
			}
		}

		catch (ClassNotFoundException cnfe) {
			System.out.println("Error al cargar el conector de SQLite: " + cnfe.getMessage());
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			System.out.println("Error de SQL: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			try {
				if (conexion != null) {
					conexion.close();
				}
			} catch (SQLException sqle) {
				System.out.println("Error al cerrar la base de datos: " + sqle.getMessage());
				sqle.printStackTrace();
			}
		}
		return false;
	}

	//para borrar un partido usamos un objeto partido con solo las claves primarias
	public static boolean EliminarPartido(Partido partido) {
		Connection conexion = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conexion = ConfigBD.abrirConexion();
			System.out.println("Conectado");
			/*
			String sentenciaInsertar = 
					"DELETE FROM partido "
					+ "WHERE codigo_equipo_local = "+  partido.getCodigoEquipoLocal() +", "
					+ "and codigo_equipo_visitante = "+ partido.getCodigoEquipoVisitante() +", "
					+ "and a�o_temporada = "+ partido.getA�oTemporada() + ",";
					
			System.out.println(sentenciaInsertar);
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaInsertar);
			*/
			String sentenciaInsertar = 
					"DELETE FROM partido "
					+ "WHERE codigo_equipo_local = ?, "
					+ "and codigo_equipo_visitante = ?, "
					+ "and a�o_temporada = ?,";
					
			System.out.println(sentenciaInsertar);
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaInsertar);
			sentencia.setInt(1, partido.getCodigoEquipoLocal());
			sentencia.setInt(2, partido.getCodigoEquipoVisitante());
			sentencia.setDouble(3, partido.getA�oTemporada());
			
			int filasInsertadas = sentencia.executeUpdate();
			if (filasInsertadas == 0) {
				System.out.println("Error al intentar borrar el partido de la base de datos.");
				return false;
			} else {
				System.out.println("Se ha borrado el partido en la base de datos.");
				return true;
			}
		}

		catch (ClassNotFoundException cnfe) {
			System.out.println("Error al cargar el conector de SQLite: " + cnfe.getMessage());
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			System.out.println("Error de SQL: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			try {
				if (conexion != null) {
					conexion.close();
				}
			} catch (SQLException sqle) {
				System.out.println("Error al cerrar la base de datos: " + sqle.getMessage());
				sqle.printStackTrace();
			}
		}
		return false;
	}
	
	//una consulta 
	public static Partido ConsultarPartido(Partido partidoEntrada) {
		Partido partido = null;
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaConsultar = "SELECT * FROM partido "
					+ "WHERE codigo_equipo_local = ?, "
					+ "and codigo_equipo_visitante = ?, "
					+ "and a�o_temporada = ?"
					+ "ORDER BY fecha";
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaConsultar);
			sentencia.setInt(1, partidoEntrada.getCodigoEquipoLocal());
			sentencia.setInt(2, partidoEntrada.getCodigoEquipoVisitante());
			sentencia.setDouble(3, partidoEntrada.getA�oTemporada());
			ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);
			while (resultados.next()) {
				partido = new Partido(
					resultados.getInt("codigo_equipo_local"),
					resultados.getInt("codigo_equipo_visitante"),
					resultados.getInt("a�o_temporada"),
					resultados.getString("fecha"),
					resultados.getInt("puntuacion_local"),
					resultados.getInt("puntuacion_visitante")
				);
			}
			resultados.close();
			sentencia.close();
		} catch (SQLException sqle) {
			System.out.println("Error de SQL: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			if (conexion != null) {
				ConfigBD.cerrarConexion(conexion);
			}
		}
		return partido;
	}

	public static List<Partido> ConsultarTodosPartidos() {
		List<Partido> partidos = new ArrayList<Partido>();
		Partido partido = null;
		Connection conexion = null;
		try {
			conexion = ConfigBD.abrirConexion();
			String sentenciaConsultar = "SELECT * FROM partido "
					+ "ORDER BY posicion";
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaConsultar);
			ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);
			while (resultados.next()) {
				partido = new Partido(
					resultados.getInt("codigo_equipo_local"),
					resultados.getInt("codigo_equipo_visitante"),
					resultados.getInt("a�o_temporada"),
					resultados.getString("fecha"),
					resultados.getInt("puntuacion_local"),
					resultados.getInt("puntuacion_visitante")
				);
				 partidos.add(partido);
			}
			resultados.close();
			sentencia.close();
		} catch (SQLException sqle) {
			System.out.println("Error de SQL: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			if (conexion != null) {
				ConfigBD.cerrarConexion(conexion);
			}
		}
		return  partidos;
	}
	
	public static boolean ImportarPartidos(String path) {
		BufferedReader flujoEntrada = null;
		//List<Partido> partidos = new ArrayList<Partido>();
		try {
			File fichero = new File(path);
			flujoEntrada = new BufferedReader(new FileReader(fichero));

			String linea = flujoEntrada.readLine();
			while (linea != null) {
				String[] datos = linea.split(";");
				
				int codigoEquipoLocal = Integer.parseInt(datos[0]);
				int codigoEquipoVisitante = Integer.parseInt(datos[1]);
				int a�oTemporada = Integer.parseInt(datos[2]);
				String fecha = datos[3];
				int puntuacionLocal = Integer.parseInt(datos[5]);
				int puntuacionVisitante = Integer.parseInt(datos[5]);

				Partido partido = new Partido(
						codigoEquipoLocal
						,codigoEquipoVisitante
						,a�oTemporada
						,fecha
						,puntuacionLocal
						,puntuacionVisitante
					);
				
				//partidos.add(partido);
				InsertarPartido(partido);
				linea = flujoEntrada.readLine();

			}
			/*
			for(int i = 0; i < partidos.size(); i++) {
				InsertarPartido(partidos.get(i));
			}
			*/
		} catch (FileNotFoundException fnfe) {
			System.out.println("Error al abrir el fichero: " + fnfe.getMessage());
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			System.out.println("Error al leer del fichero: " + ioe.getMessage());
			ioe.printStackTrace();
		} catch (NumberFormatException nfe) {
			System.out.println("Error al convertir de cadena a numero: " + nfe.getMessage());
			nfe.printStackTrace();
		} finally {
			try {
				if (flujoEntrada != null) {
					flujoEntrada.close();
				}
			} catch (IOException ioe) {
				System.out.println("Error al cerrar el fichero: " + ioe.getMessage());
				ioe.printStackTrace();
			}
		}
		return false;
	}
	
	public static boolean ExportarPartidos(String path) {
		BufferedWriter flujoSalida = null;
		List<Partido> partidos = new ArrayList<Partido>();
		try {
			FileWriter escritor = new FileWriter(path, false);
			flujoSalida = new BufferedWriter(new FileWriter(path, false));
			for (int i = 0; i < partidos.size(); i++) {

				flujoSalida.write(partidos.get(i).toStringWithSeparators());
				flujoSalida.newLine();
			}

		} catch (IOException ioe) {
			System.out.println("Error al escribir en el fichero: " + ioe.getMessage());
			ioe.printStackTrace();
		} finally {
			try {
				if (flujoSalida != null) {
					flujoSalida.close();
					return true;
				}
			} catch (IOException ioe) {
				System.out.println("Error al cerrar el fichero: " + ioe.getMessage());
				ioe.printStackTrace();
			}
		}
		return false;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}