package org.iesalandalus.programacion.reservasaulas.modelo.dominio;

/**
 * @author Emanuel Martínez Lonardi
 *
 */
public enum Tramo {
	MANANA("Mañana"),
	TARDE("Tarde");
	
	private String cadenaAMostrar;
	private Tramo(String cadenaAMostrar) {
		this.cadenaAMostrar = cadenaAMostrar;
	}
	
	@Override
	public String toString() {
		return cadenaAMostrar;
	}

}