package org.iesalandalus.programacion.reservasaulas.modelo.dao;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;

/**
 * @author Emanuel Martínez Lonardi
 *
 */
public class Profesores {
	private static final int MAX_PROFESORES = 10;
	private int numProfesores;
	private Profesor[] grupoProfesores;
	
	public Profesores() {
		this.numProfesores = 0;
		this.grupoProfesores = new Profesor[this.MAX_PROFESORES];
	}
	
	public Profesores(Profesores profesores) {
		this.setProfesores(profesores);
	}

	private void setProfesores(Profesores profesores) {
		if (profesores == null) {
			throw new IllegalArgumentException("No se pueden copiar profesores nulos.");
		}
		this.grupoProfesores = this.copiaProfundaProfesores(profesores.grupoProfesores);
		this.numProfesores = profesores.numProfesores;
		
	}
	
	private Profesor[] copiaProfundaProfesores(Profesor[] grupoProfesores) {
		Profesor[] grupoProfesoresSalida = new Profesor[grupoProfesores.length];
		for (int i = 0; i < grupoProfesores.length && grupoProfesores[i] != null; i++) {
			grupoProfesoresSalida[i] = new Profesor(grupoProfesores[i]);
		}
		return grupoProfesoresSalida;
	}

	public Profesor[] getProfesores() {
		return this.copiaProfundaProfesores(this.grupoProfesores);
	}

	public int getNumProfesores() {
		return this.numProfesores;
	}
	
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new IllegalArgumentException("No se puede insertar un profesor nulo.");
		}
		int indice = this.buscarIndiceProfesor(profesor);
		if (indiceNoSuperaTamano(indice) == false) {
			this.grupoProfesores[indice] = new Profesor(profesor);
			this.numProfesores++;
		} else {
			if (indiceNoSuperaCapacidad(indice)) {
				throw new OperationNotSupportedException("El profesor ya existe.");
			} else {
				throw new OperationNotSupportedException("No se aceptan más profesores.");
			}
		}
	}

	private int buscarIndiceProfesor(Profesor profesor) {
		int indice = 0;
		boolean encontrado = false;
		while (indiceNoSuperaTamano(indice) && encontrado == false) {
			if (this.grupoProfesores[indice].equals(profesor)) {
				encontrado = true;
			} else {
				indice++;
			}
		}
		return indice;
	}

	private boolean indiceNoSuperaTamano(int indice) {
		return indice < this.numProfesores;
	}

	private boolean indiceNoSuperaCapacidad(int indice) {
		return indice < this.MAX_PROFESORES;
	}

	public Profesor buscar(Profesor profesor) {
		Profesor encontrado = null;
		int indice = buscarIndiceProfesor(profesor);
		if (indiceNoSuperaTamano(indice)) {
			encontrado = new Profesor(this.grupoProfesores[indice]);
		}
		return encontrado;
	}

	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new IllegalArgumentException("No se puede borrar un profesor nulo.");
		}
		int indice = buscarIndiceProfesor(profesor);
		if (indiceNoSuperaTamano(indice)) {
			this.desplazarUnaPosicionHaciaIzquierda(indice);
		} else {
			throw new OperationNotSupportedException("El profesor a borrar no existe.");
		}
	}

	private void desplazarUnaPosicionHaciaIzquierda(int posicion) {
		for (int i = posicion; i < this.numProfesores - 1; i++) {
			this.grupoProfesores[i] = this.grupoProfesores[i + 1];
		}
		this.grupoProfesores[this.numProfesores] = null;
		this.numProfesores--;
	}

	public String[] representar() {
		String[] profesoresCadena = null;
		if (this.numProfesores > 0) {
			profesoresCadena = new String[this.numProfesores];
			for (int i = 0; indiceNoSuperaTamano(i); i++) {
				profesoresCadena[i] = this.grupoProfesores[i].toString();
				System.out.println(i+1 + ".- " + profesoresCadena[i]);
			}
		}
		return profesoresCadena;
	}
}
