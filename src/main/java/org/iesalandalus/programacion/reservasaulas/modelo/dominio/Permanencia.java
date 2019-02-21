package org.iesalandalus.programacion.reservasaulas.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Emanuel Martínez Lonardi
 *
 */
public class Permanencia {
	private LocalDate dia;
	private Tramo tramo;
	private static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public Permanencia(LocalDate dia, Tramo tramo) {
		this.setDia(dia);
		this.setTramo(tramo);
	}

	public Permanencia(Permanencia permanencia) {
		if (permanencia == null) {
			throw new IllegalArgumentException("No se puede copiar una permanencia nula.");
		}
		this.setDia(permanencia.getDia());
		this.setTramo(permanencia.getTramo());

	}

	// Métodos get.
	public LocalDate getDia() {
		return dia;
	}

	public Tramo getTramo() {
		return tramo;
	}

	// Métodos set.
	private void setDia(LocalDate dia) {
		if (dia == null) {
			throw new IllegalArgumentException("El día de una permanencia no puede ser nulo.");
		}
		this.dia = dia;
	}

	private void setTramo(Tramo tramo) {
		if (tramo == null) {
			throw new IllegalArgumentException("El tramo de una permanencia no puede ser nulo.");
		}
		this.tramo = tramo;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dia == null) ? 0 : dia.hashCode());
		result = prime * result + ((tramo == null) ? 0 : tramo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Permanencia other = (Permanencia) obj;
		if (dia == null) {
			if (other.dia != null)
				return false;
		} else if (!dia.equals(other.dia))
			return false;
		if (tramo != other.tramo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[dia=" + dia.format(FORMATO_DIA) + ", tramo=" + tramo + "]";
	}

}