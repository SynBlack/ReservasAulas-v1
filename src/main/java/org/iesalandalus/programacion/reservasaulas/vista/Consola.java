package org.iesalandalus.programacion.reservasaulas.vista;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;

/**
 * @author Emanuel Martínez Lonardi
 *
 */
public class Consola {
	private static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String NOMBRE_VALIDO = "[a-zA-ZáéíóúÁÉÍÓÚñÑ]+";
	private static final String CORREO_VALIDO = "\\w[\\.\\w+]*@\\w+\\.\\w+";

	
	private Consola() {
	}

	public static void mostrarMenu() {
		mostrarCabecera("Reservas de aulas");
		for (Opcion opcion : Opcion.values()) {
			System.out.println(opcion);
		}
	}

	public static void mostrarCabecera(String cabecera) {
		System.out.printf("%n%s%n", cabecera);
		String cadena = "%0" + cabecera.length() + "d%n";
		System.out.println(String.format(cadena, 0).replace("0", "-"));
	}

	public static int elegirOpcion() {
		int opcion;
		do {
			System.out.print("Elige una opción: ");
			opcion = Entrada.entero();
		} while (Opcion.esOrdinalValido(opcion) == false);
		return opcion;
	}

	public static Aula leerAula() {
		Aula aula = new Aula(leerNombreAula());
		return aula;
	}

	public static String leerNombreAula() {
		String nombre = null;
		do {
			System.out.print("Introduce el nombre del Aula: ");
			nombre = Entrada.cadena();
		} while (nombre == null || nombre == "");
		return nombre;
	}

	public static Profesor leerProfesor() {
		Profesor profesor = new Profesor(leerNombreProfesor(),"a@a.a");
		return profesor;
	}

	public static String leerNombreProfesor() {
		String nombre = null;
		do {
			System.out.print("Introduce el nombre del Profesor: ");
			nombre = Entrada.cadena();
		} while (nombre == null || nombre.equals(""));
		return nombre;
	}

	public static Tramo leerTramo() {
		int indice = 0;
		System.out.println("1.- " + Tramo.MANANA);
		System.out.println("2.- " + Tramo.TARDE);
		do {
			System.out.print("Eliga un tramo (índice): ");
			indice = Entrada.entero();
		} while(indice < 1 && indice > 2);
		return Tramo.values()[indice-1];
	}

	public static LocalDate leerDia() {
		int ano, mes, dia;
		LocalDate salida;
		do {
			System.out.print("Introduce el año (" + LocalDateTime.now().getYear() +
					"-" + (LocalDateTime.now().getYear()+2) + "): ");
			ano = Entrada.entero();
		} while(ano < LocalDateTime.now().getYear() 
				| ano > LocalDateTime.now().getYear()+2);
		do {
			System.out.print("Introduce el mes (numérico): ");
			mes = Entrada.entero();
		} while(mes < 1 || mes > 12);
		do {
			System.out.print("Introduce el día: ");
			dia = Entrada.entero();
		} while(dia < 1 || dia > 31);
		salida = LocalDate.of(ano, mes, dia);
		return salida;
	}
}
