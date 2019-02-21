package org.iesalandalus.programacion.reservasaulas.modelo.dao;

import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;

/**
 * @author Emanuel Martínez Lonardi
 *
 */

public class Reservas {
	private static final int MAX_RESERVAS = 50;
	private int numReservas;
	private Reserva[] reservasRealizadas;

	public Reservas() {
		this.numReservas = 0;
		this.reservasRealizadas = new Reserva[this.MAX_RESERVAS];
	}

	public Reservas(Reservas reservas) {
		this.setReservas(reservas);
	}

	private void setReservas(Reservas reservas) {
		if (reservas == null) {
			throw new IllegalArgumentException("No se pueden copiar reservas nulas.");
		}
		this.reservasRealizadas = this.copiaProfundaReservas(reservas.reservasRealizadas);
		this.numReservas = reservas.numReservas;
	}

	private Reserva[] copiaProfundaReservas(Reserva[] reservas) {
		Reserva[] reservasCopia = new Reserva[reservas.length];
		for (int i = 0; i < reservas.length && reservas[i] != null; i++) {
			reservasCopia[i] = new Reserva(reservas[i]);
		}
		return reservasCopia;
	}

	public Reserva[] getReservas() {
		return this.copiaProfundaReservas(this.reservasRealizadas);
	}

	public int getNumReservas() {
		return this.numReservas;
	}

	public void insertar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new IllegalArgumentException("No se puede realizar una reserva nula.");
		}
		int indice = this.buscarIndiceReserva(reserva);
		if (this.indiceNoSuperaTamano(indice) == false) {
			this.reservasRealizadas[this.numReservas] = new Reserva(reserva);
			this.numReservas++;
		} else {
			if (this.indiceNoSuperaCapacidad(indice)) {
				throw new OperationNotSupportedException("La reserva ya existe.");
			} else {
				throw new OperationNotSupportedException("No se permiten más reservas.");
			}
		}
	}

	private int buscarIndiceReserva(Reserva reserva) {
		int indice = 0;
		boolean encontrada = false;
		while (indiceNoSuperaTamano(indice) && encontrada == false) {
			if (this.reservasRealizadas[indice].equals(reserva)) {
				encontrada = true;
			} else {
				indice++;
			}
		}
		return indice;
	}

	private boolean indiceNoSuperaTamano(int indice) {
		return indice < this.numReservas;
	}

	private boolean indiceNoSuperaCapacidad(int indice) {
		return indice < this.MAX_RESERVAS;
	}

	public Reserva buscar(Reserva reserva) {
		Reserva reservaSalida = null;
		int indice = this.buscarIndiceReserva(reserva);
		if (this.indiceNoSuperaTamano(indice) == true) {
			reservaSalida = new Reserva(this.reservasRealizadas[indice]);
		}
		return reservaSalida;
	}

	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new IllegalArgumentException("No se puede anular una reserva nula.");
		}
		int indice = this.buscarIndiceReserva(reserva);
		if (this.indiceNoSuperaTamano(indice)) {
			this.desplazarUnaPosicionHaciaIzquierda(indice);
		} else {
			throw new OperationNotSupportedException("La reserva a anular no existe.");
		}
	}

	private void desplazarUnaPosicionHaciaIzquierda(int posicion) {
		for (int i = posicion; i < this.numReservas - 1; i++) {
			this.reservasRealizadas[i] = this.reservasRealizadas[i + 1];
		}
		this.reservasRealizadas[this.numReservas] = null;
		this.numReservas--;
	}

	public String[] representar() {
		String[] reservasCadena = null;
		if(this.numReservas > 0) {
			reservasCadena = new String[this.numReservas];
			for (int i = 0; this.indiceNoSuperaTamano(i); i++) {
				reservasCadena[i] = this.reservasRealizadas[i].toString();
				System.out.println(i+1 + ".- " + reservasCadena[i]);
			}
		}
		return reservasCadena;
	}

	public Reserva[] getReservasProfesor(Profesor profesor) {
		Reserva[] reservasProfesor = new Reserva[this.MAX_RESERVAS];
		int indice = 0;
		if(profesor == null) {
			throw new IllegalArgumentException("");
		}
		for(int i = 0; i < this.numReservas; i++) {
			if(this.reservasRealizadas[i].getProfesor().equals(profesor)) {
				reservasProfesor[indice] = new Reserva(this.reservasRealizadas[i]);
				indice++;
			}
		}
		return reservasProfesor;
	}

	public Reserva[] getReservasAula(Aula aula) {
		Reserva[] reservasAulas = new Reserva[this.MAX_RESERVAS];
		int indice = 0;
		if(aula == null) {
			throw new IllegalArgumentException("");
		}
		for(int i = 0; i < this.numReservas; i++) {
			if(this.reservasRealizadas[i].getAula().equals(aula)) {
				reservasAulas[indice] = new Reserva(this.reservasRealizadas[i]);
				indice++;
			}
		}
		return reservasAulas;
	}

	public Reserva[] getReservasPermanencia(Permanencia permanencia) {
		Reserva[] reservasPermanencia = new Reserva[this.MAX_RESERVAS];
		int indice = 0;
		if(permanencia == null) {
			throw new IllegalArgumentException("");
		}
		for(int i = 0; i < this.numReservas; i++) {
			if(this.reservasRealizadas[i].getPermanencia().equals(permanencia)) {
				reservasPermanencia[indice] = new Reserva(this.reservasRealizadas[i]);
				indice++;
			}
		}
		return reservasPermanencia;
	}

	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		if(aula == null) {
			throw new IllegalArgumentException("No se puede consultar la disponibilidad de un aula nula.");
		}
		if(permanencia == null) {
			throw new IllegalArgumentException("No se puede consultar la disponibilidad de una permanencia nula.");
		}
		boolean disponible = true;
		for(int i = 0; i < this.numReservas; i++) {
			if(this.reservasRealizadas[i].getAula().equals(aula) &
					this.reservasRealizadas[i].getPermanencia().equals(permanencia)) {
				disponible = false;
			}
		}
		return disponible;
	}
}
