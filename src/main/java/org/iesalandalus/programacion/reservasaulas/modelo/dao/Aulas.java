package org.iesalandalus.programacion.reservasaulas.modelo.dao;

import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;

/**
 * @author Emanuel Martínez Lonardi
 *
 */
public class Aulas {
	private ArrayList<Aula> conjuntoAulas;

	public Aulas() {
		this.conjuntoAulas = new ArrayList<>();
	}

	public Aulas(Aulas aulas) {
		this.setAulas(aulas);
	}

	private void setAulas(Aulas aulas) {
		if (aulas == null) {
			throw new IllegalArgumentException("No se pueden copiar aulas nulas.");
		}
		this.conjuntoAulas = this.copiaProfundaAulas(aulas.conjuntoAulas);
	}

	private ArrayList<Aula> copiaProfundaAulas(ArrayList<Aula> conjuntoAulas) {
		ArrayList<Aula> conjuntoAulasSalida = new ArrayList<>();
		for (Aula aula : conjuntoAulas) {
			conjuntoAulasSalida.add(new Aula(aula));
		}
		return conjuntoAulasSalida;

	}

	public ArrayList<Aula> getAulas() {
		return this.copiaProfundaAulas(this.conjuntoAulas);
	}

	public int getNumAulas() {
		return this.conjuntoAulas.size();
	}

	public void insertar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new IllegalArgumentException("No se puede insertar un aula nula.");
		}
		if (this.conjuntoAulas.contains(aula)) {
			throw new OperationNotSupportedException("El aula ya existe.");
		} else {
			this.conjuntoAulas.add(aula);
		}
	}

	public Aula buscar(Aula aula) {
		Aula aulaSalida = null;
		if (this.conjuntoAulas.contains(aula)) {
			aulaSalida = new Aula(aula);
		}
		return aulaSalida;
	}

	public void borrar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new IllegalArgumentException("No se puede borrar un aula nula.");
		}
		if (this.conjuntoAulas.contains(aula)) {
			this.conjuntoAulas.remove(aula);
		} else {
			throw new OperationNotSupportedException("El aula a borrar no existe.");
		}
	}

	public ArrayList<String> representar() {
		ArrayList<String> cadenaSalida = new ArrayList<>();
		for (Aula aula : this.conjuntoAulas) {
			cadenaSalida.add(aula.toString());
		}
		return cadenaSalida;
	}
}
