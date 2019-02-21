package org.iesalandalus.programacion.reservasaulas.modelo.dao;

import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import java.util.ArrayList;

/**
 * @author Emanuel Martínez Lonardi
 *
 */

public class Reservas {
	private ArrayList<Reserva> reservasRealizadas;

	public Reservas() {
		this.reservasRealizadas = new ArrayList<>();
	}

	public Reservas(Reservas reservas) {
		this.setReservas(reservas);
	}

	private void setReservas(Reservas reservas) {
		if (reservas == null) {
			throw new IllegalArgumentException("No se pueden copiar reservas nulas.");
		}
		this.reservasRealizadas = this.copiaProfundaReservas(reservas.reservasRealizadas);
	}

	private ArrayList<Reserva> copiaProfundaReservas(ArrayList<Reserva> reservas) {
		ArrayList<Reserva> reservasSalida = new ArrayList<>();
		for (Reserva reserva : reservas) {
			reservasSalida.add(new Reserva(reserva));
		}
		return reservasSalida;
	}

	public ArrayList<Reserva> getReservas() {
		return this.copiaProfundaReservas(this.reservasRealizadas);
	}

	public int getNumReservas() {
		return this.reservasRealizadas.size();
	}

	public void insertar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new IllegalArgumentException("No se puede realizar una reserva nula.");
		}
		if (this.reservasRealizadas.contains(reserva)) {
			throw new OperationNotSupportedException("La reserva ya existe.");
		} else {
			this.reservasRealizadas.add(reserva);
		}
	}

	public Reserva buscar(Reserva reserva) {
		Reserva reservaSalida = null;
		if (this.reservasRealizadas.contains(reserva)) {
			reservaSalida = new Reserva(reserva);
		}
		return reservaSalida;
	}

	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new IllegalArgumentException("No se puede anular una reserva nula.");
		}
		if (this.reservasRealizadas.contains(reserva)) {
			this.reservasRealizadas.remove(reserva);
		} else {
			throw new OperationNotSupportedException("La reserva a anular no existe.");
		}
	}

	public ArrayList<String> representar() {
		ArrayList<String> cadenaSalida = new ArrayList<>();
		for (Reserva reserva : this.reservasRealizadas) {
			cadenaSalida.add(reserva.toString());
		}
		return cadenaSalida;
	}

	public ArrayList<Reserva> getReservasProfesor(Profesor profesor) {
		ArrayList<Reserva> reservasProfesor = new ArrayList<>();
		for (Reserva reserva : this.reservasRealizadas) {
			if (reserva.getProfesor().equals(profesor)) {
				reservasProfesor.add(new Reserva(reserva));
			}
		}
		return reservasProfesor;
	}

	public ArrayList<Reserva> getReservasAula(Aula aula) {
		ArrayList<Reserva> reservasAulas = new ArrayList<>();
		for (Reserva reserva : this.reservasRealizadas) {
			if (reserva.getAula().equals(aula)) {
				reservasAulas.add(new Reserva(reserva));
			}
		}
		return reservasAulas;
	}

	public ArrayList<Reserva> getReservasPermanencia(Permanencia permanencia) {
		ArrayList<Reserva> reservasPermanencia = new ArrayList<>();
		for (Reserva reserva : this.reservasRealizadas) {
			if (reserva.getPermanencia().equals(permanencia)) {
				reservasPermanencia.add(new Reserva(reserva));
			}
		}
		return reservasPermanencia;
	}

	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		if (aula == null) {
			throw new IllegalArgumentException("No se puede consultar la disponibilidad de un aula nula.");
		}
		if (permanencia == null) {
			throw new IllegalArgumentException("No se puede consultar la disponibilidad de una permanencia nula.");
		}
		for (Reserva reserva : this.reservasRealizadas) {
			if (reserva.getAula().equals(aula) && reserva.getPermanencia().equals(permanencia)) {
				return false;
			}
		}
		return true;
	}
}
