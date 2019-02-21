package org.iesalandalus.programacion.reservasaulas.modelo.dao;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;

/**
 * @author Emanuel Martínez Lonardi
 *
 */

public class Profesores {
	private ArrayList<Profesor> grupoProfesores;

	public Profesores() {
		this.grupoProfesores = new ArrayList<>();
	}

	public Profesores(Profesores profesores) {
		this.setProfesores(profesores);
	}

	private void setProfesores(Profesores profesores) {
		if (profesores == null) {
			throw new IllegalArgumentException("No se pueden copiar profesores nulos.");
		}
		this.grupoProfesores = this.copiaProfundaProfesores(profesores.grupoProfesores);

	}

	private ArrayList<Profesor> copiaProfundaProfesores(ArrayList<Profesor> profesores) {
		ArrayList<Profesor> profesoresSalida = new ArrayList<>();
		for (Profesor profesor : profesores) {
			profesoresSalida.add(new Profesor(profesor));
		}
		return profesoresSalida;
	}

	public ArrayList<Profesor> getProfesores() {
		return this.copiaProfundaProfesores(this.grupoProfesores);
	}

	public int getNumProfesores() {
		return this.grupoProfesores.size();
	}

	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new IllegalArgumentException("No se puede insertar un profesor nulo.");
		}
		if (this.grupoProfesores.contains(profesor)) {
			throw new OperationNotSupportedException("El profesor ya existe.");
		} else {
			this.grupoProfesores.add(profesor);
		}
	}

	public Profesor buscar(Profesor profesor) {
		Profesor profesorSalida = null;
		if (this.grupoProfesores.contains(profesor)) {
			profesorSalida = new Profesor(profesor);
		}
		return profesorSalida;
	}

	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new IllegalArgumentException("No se puede borrar un profesor nulo.");
		}
		if (this.grupoProfesores.contains(profesor)) {
			this.grupoProfesores.remove(profesor);
		} else {
			throw new OperationNotSupportedException("El profesor a borrar no existe.");
		}
	}

	public ArrayList<String> representar() {
		ArrayList<String> cadenaSalida = new ArrayList<>();
		for (Profesor profesor : this.grupoProfesores) {
			cadenaSalida.add(profesor.toString());
		}
		return cadenaSalida;
	}
}
