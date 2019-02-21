package org.iesalandalus.programacion.reservasaulas.vista;

import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.modelo.ModeloReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.utilidades.Entrada;

/**
 * @author Emanuel Martínez Lonardi
 *
 */

public class IUTextual {
	private static final String NOMBRE_VALIDO = "[a-zA-ZáéíóúÁÉÍÓÚñÑ]+";
	private static final String CORREO_VALIDO = "\\w[\\.\\w+]*@\\w+\\.\\w+";
	private static final String ERROR = "ERROR. Vuelve a intentarlo. ";

	private ModeloReservasAulas modelo;

	public IUTextual() {
		modelo = new ModeloReservasAulas();
		Opcion.setVista(this);
	}

	public void comenzar() {
		int ordinal;
		do {
			Consola.mostrarMenu();
			ordinal = Consola.elegirOpcion();
			Opcion opcion = Opcion.getOpcionSegunOrdinal(ordinal);
			opcion.ejecutar();
		} while (ordinal != Opcion.SALIR.ordinal());
	}

	public void salir() {
		System.out.println("Cerrando aplicación...");
	}

	public void insertarAula() {
		Consola.mostrarCabecera("Insertar Aula");
		Aula aula = Consola.leerAula();
		if (aula.getNombre().matches(NOMBRE_VALIDO)) {
			try {
				modelo.insertarAula(aula);
				System.out.println("Se agregó el Aula correctamente. " + aula);
			} catch (OperationNotSupportedException e) {
				System.out.println(ERROR + e.getMessage());
			}
		} else {
			System.out.println("El nombre no es válido. " + aula);
		}
	}

	public void borrarAula() {
		Consola.mostrarCabecera("Borrar Aula");
		Aula aula = Consola.leerAula();
		if (aula.getNombre().matches(NOMBRE_VALIDO)) {
			try {
				modelo.borrarAula(aula);
				System.out.println("Se borro el Aula correctamente. " + aula);
			} catch (OperationNotSupportedException e) {
				System.out.println(ERROR + e.getMessage());
			}
		} else {
			System.out.println("El nombre no es válido. " + aula);
		}
	}

	public void buscarAula() {
		Consola.mostrarCabecera("Buscar Aula");
		Aula aulaBuscar = Consola.leerAula();
		if (aulaBuscar.getNombre().matches(NOMBRE_VALIDO)) {
			Aula aulaRecibida = modelo.buscarAula(aulaBuscar);
			if (aulaRecibida != null) {
				System.out.println("Se encontró el Aula: " + aulaBuscar);
			} else {
				System.out.println("No se encontro el Aula. " + aulaBuscar);
			}
		} else {
			System.out.println("El nombre no es válido. " + aulaBuscar);
		}
	}

	public void listarAulas() {
		Consola.mostrarCabecera("Listado Aulas");
		String[] aulas = modelo.representarAulas();
		if (aulas == null) {
			System.out.println("No existen Aulas para representar.");
		}
	}

	public void insertarProfesor() {
		Consola.mostrarCabecera("Insertar Profesor");
		Profesor profesor = Consola.leerProfesor();
		String correo = "";
		if (profesor.getNombre().matches(NOMBRE_VALIDO)) {
			System.out.print("Introduce el correo del profesor: ");
			correo = Entrada.cadena();
			if (correo.matches(CORREO_VALIDO)) {
				profesor.setCorreo(correo);
				try {
					modelo.insertarProfesor(profesor);
					System.out.println("Se inserto el Profesor correctamente. " + profesor);
				} catch (OperationNotSupportedException e) {
					System.out.println(ERROR + e.getMessage());
				}
			} else {
				System.out.println("El correo no es válido. " + profesor);
			}
		} else {
			System.out.println("El nombre no es válido. " + profesor);
		}
	}

	public void borrarProfesor() {
		Consola.mostrarCabecera("Borrar Profesor");
		String nombre = Consola.leerNombreProfesor();
		if (nombre.matches(NOMBRE_VALIDO)) {
			try {
				Profesor profesor = new Profesor(nombre, "a@a.a");
				modelo.borrarProfesor(profesor);
				System.out.println("Se borro el Profesor correctamente. " + profesor);
			} catch (OperationNotSupportedException e) {
				System.out.println(ERROR + e.getMessage());
			}
		} else {
			System.out.println("El nombre no es válido. [nombre=" + nombre + "]");
		}
	}

	public void buscarProfesor() {
		Consola.mostrarCabecera("Buscar Profesor");
		String nombre = Consola.leerNombreProfesor();
		if (nombre.matches(NOMBRE_VALIDO)) {
			Profesor profesorBuscado = new Profesor(nombre, "a@a.a");
			Profesor profesorDevuelto = modelo.buscarProfesor(profesorBuscado);
			if (profesorDevuelto == null) {
				System.out.println("No se encontró el profesor con nombre: " + profesorBuscado);
			} else {
				System.out.println("Se encontró el profesor: " + profesorDevuelto);
			}
		} else {
			System.out.println("El nombre no es válido. [nombre=" + nombre + "]");
		}
	}

	public void listarProfesores() {
		Consola.mostrarCabecera("Lista de Profesores");
		String[] profesores = modelo.representarProfesores();
		if (profesores == null) {
			System.out.println("No existen profesores para representar.");
		}
	}

	public void realizarReserva() {
		Consola.mostrarCabecera("Realizar Reserva");
		String nombre = Consola.leerNombreProfesor();
		if (nombre.matches(NOMBRE_VALIDO)) {
			Profesor profesor = modelo.buscarProfesor(new Profesor(nombre, "a@a.a"));
			if (profesor == null) {
				System.out.println("El profesor debe estar registrado. " + profesor);
				this.comenzar();
			}
			Reserva reserva = leerReserva(profesor);
			try {
				modelo.realizarReserva(reserva);
				System.out.println("Se realizo la reserva correctamente. " + reserva);
			} catch (OperationNotSupportedException e) {
				System.out.println(ERROR + e.getMessage());
			}
		} else {
			System.out.println("El nombre no es válido. [nombre=" + nombre + "]");
		}
	}

	private Reserva leerReserva(Profesor profesor) {
		Reserva reserva = null;
		Aula aula = Consola.leerAula();
		if (aula.getNombre().matches(NOMBRE_VALIDO)) {
			aula = modelo.buscarAula(aula);
			if (aula == null) {
				System.out.println("El Aula debe estar registrada. " + aula);
			} else {
				Permanencia permanencia = new Permanencia(Consola.leerDia(), Consola.leerTramo());
				reserva = new Reserva(profesor, aula, permanencia);
			}
		} else {
			System.out.println("El nombre no es válido. " + aula);
		}
		return reserva;
	}

	public void anularReserva() {
		Consola.mostrarCabecera("Anular Reserva");
		Reserva reserva = leerReserva(new Profesor("a", "a@a.a"));
		try {
			modelo.anularReserva(reserva);
			System.out.println("La reserva se anulo correctamente. " + reserva);
		} catch (OperationNotSupportedException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void listarReservas() {
		Consola.mostrarCabecera("Lista de reservas");
		String[] reservas = modelo.representarReservas();
		if (reservas == null) {
			System.out.println("No se ha realizado ninguna reserva.");
		}
	}

	public void listarReservasAula() {
		Consola.mostrarCabecera("Lista de reservas por Aula");
		Aula aula = Consola.leerAula();
		if (aula.getNombre().matches(NOMBRE_VALIDO)) {
			Reserva[] reservas = modelo.getReservasAula(aula);
			if (reservas[0] == null) {
				System.out.println("No existen reservas para el Aula: " + aula);
			} else {
				Consola.mostrarCabecera("Lista de reservas por Aula: " + aula);
				for (int i = 0; i < reservas.length; i++) {
					if (reservas[i] != null) {
						System.out.println(reservas[i]);
					}
				}
			}
		} else {
			System.out.println("El nombre no es válido. " + aula);
		}
	}

	public void listarReservasProfesor() {
		Consola.mostrarCabecera("Lista de reservas por Profesor");
		Profesor profesor = new Profesor(Consola.leerNombreProfesor(), "a@a.a");
		if (profesor.getNombre().matches(NOMBRE_VALIDO)) {
			Reserva[] reservas = modelo.getReservasProfesor(profesor);
			if (reservas[0] == null) {
				System.out.println("No existen reservas para el Profesor: " + profesor);
			} else {
				Consola.mostrarCabecera("Lista de reservas por Profesor: " + profesor);
				for (int i = 0; i < reservas.length; i++) {
					if (reservas[i] != null) {
						System.out.println(reservas[i]);
					}
				}
			}
		} else {
			System.out.println("El nombre no es válido. " + profesor.getNombre());
		}
	}

	public void listarReservasPermanencia() {
		Consola.mostrarCabecera("Lista de reservas por Permanencia");
		Permanencia permanencia = new Permanencia(Consola.leerDia(), Consola.leerTramo());
		Reserva[] reservas = modelo.getReservasPermanencia(permanencia);
		if (reservas[0] == null) {
			System.out.println("No existen reservas para la Permanencia: " + permanencia);
		} else {
			Consola.mostrarCabecera("Lista de reservas por Permanencia: " + permanencia);
			for (int i = 0; i < reservas.length; i++) {
				if (reservas[i] != null) {
					System.out.println(reservas[i]);
				}
			}
		}
	}

	public void consultarDisponibilidad() {
		Consola.mostrarCabecera("Consultar disponibilidad");
		Aula aula = Consola.leerAula();
		if (aula.getNombre().matches(NOMBRE_VALIDO)) {
			Permanencia permanencia = new Permanencia(Consola.leerDia(), Consola.leerTramo());
			boolean disponible = modelo.consultarDisponibilidad(aula, permanencia);
			if (disponible == true) {
				System.out.println("Esta disponible. " + aula + " - " + permanencia);
			} else {
				System.out.println("NO esta disponible. " + aula + " - " + permanencia);
			}
		} else {
			System.out.println("El nombre no es válido. " + aula);
		}
	}
}
