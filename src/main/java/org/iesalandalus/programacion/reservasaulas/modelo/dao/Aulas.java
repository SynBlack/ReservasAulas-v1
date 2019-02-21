/**
 * 
 */
package org.iesalandalus.programacion.reservasaulas.modelo.dao;

import java.io.NotActiveException;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;

/**
 * @author Emanuel Martínez Lonardi
 *
 */
public class Aulas {
	private static final int MAX_AULAS = 50;
	private int numAulas;
	private Aula[] conjuntoAulas;
	
	public Aulas() {
		this.numAulas = 0;
		this.conjuntoAulas = new Aula[MAX_AULAS];
	}
	
	public Aulas(Aulas aulas) {
		this.setAulas(aulas);
	}
	
	private void setAulas(Aulas aulas) {
		if (aulas == null) {
			throw new IllegalArgumentException("No se pueden copiar aulas nulas.");
		}
		this.conjuntoAulas = this.copiaProfundaAulas(aulas.conjuntoAulas);
		this.numAulas = aulas.numAulas;
	}
	
	private Aula[] copiaProfundaAulas(Aula[] conjuntoAulas) {
		Aula[] conjuntoAulasSalida = new Aula[conjuntoAulas.length];
		for (int i = 0; i < conjuntoAulas.length && conjuntoAulas[i] != null; i++) {
			conjuntoAulasSalida[i] = new Aula(conjuntoAulas[i]);
		}
		return conjuntoAulasSalida;
		
	}
	
	public Aula[] getAulas() {
		return this.copiaProfundaAulas(this.conjuntoAulas);
	}

	public int getNumAulas() {
		return this.numAulas;
	}
	
	public void insertar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new IllegalArgumentException("No se puede insertar un aula nula.");
		}
		int indice = buscarIndiceAula(aula);
		if (indiceNoSuperaTamano(indice) == false) {
			this.conjuntoAulas[indice] = new Aula(aula);
			this.numAulas++;
		} else {
			if (indiceNoSuperaCapacidad(indice)) {
				throw new OperationNotSupportedException("El aula ya existe.");
			} else {
				throw new OperationNotSupportedException("No se aceptan más aulas.");
			}
		}
	}
	
	private int buscarIndiceAula(Aula aula) {
		int indice = 0;
		boolean localizado = false;
		while (indiceNoSuperaTamano(indice) && localizado == false) {
			if (this.conjuntoAulas[indice].equals(aula)) {
				localizado = true;
			} else {
				indice++;
			}
		}
		return indice;
	}
	
	private boolean indiceNoSuperaTamano(int indice) {
		return indice < this.numAulas;
	}

	private boolean indiceNoSuperaCapacidad(int indice) {
		return indice < this.MAX_AULAS;
	}
	
	public Aula buscar(Aula aula) {
		Aula localizada = null;
		int indice = buscarIndiceAula(aula);
		if (indiceNoSuperaTamano(indice)) {
			localizada = new Aula(this.conjuntoAulas[indice]);
		}
		return localizada;
	}
	
	public void borrar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new IllegalArgumentException("No se puede borrar un aula nula.");
		}
		int indice = buscarIndiceAula(aula);
		if (indiceNoSuperaTamano(indice)) {
			this.desplazarUnaPosicionHaciaIzquierda(indice);
		} else {
			throw new OperationNotSupportedException("El aula a borrar no existe.");
		}
	}

	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		for (int i = indice; i < this.numAulas - 1; i++) {
			this.conjuntoAulas[i] = this.conjuntoAulas[i + 1];
		}
		this.conjuntoAulas[this.numAulas] = null;
		this.numAulas--;
	}

	public String[] representar() {
		String[] aulasCadena = null;
		if (this.numAulas > 0) {
			aulasCadena = new String[this.numAulas];
			for (int i = 0; indiceNoSuperaTamano(i); i++) {
				aulasCadena[i] = this.conjuntoAulas[i].toString();
				System.out.println(i+1 + ".- " + aulasCadena[i]);
			}
		}
		return aulasCadena;
	}
}
