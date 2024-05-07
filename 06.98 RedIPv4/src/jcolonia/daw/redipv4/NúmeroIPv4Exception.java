package jcolonia.daw.redipv4;

/**
 * Excepción ligera usada en la aplicación «Red IPv4». Alerta generalmente de
 * valores no válidos en direcciones IPv4.
 * 
 * @version 1.1 (20240502000)
 * @author <a href="mailto:dmartin.jcolonia@gmail.com">David H. Martín</a>
 */
public class NúmeroIPv4Exception extends RuntimeException {
	/**
	 * Número de serie, asociado a la versión de la clase.
	 */
	private static final long serialVersionUID = 20240503000L;

	/**
	 * Crea una excepción sin ninguna información adicional.
	 */
	public NúmeroIPv4Exception() {
		super();
	}

	/**
	 * Crea una excepción con un texto descriptivo.
	 * 
	 * @param mensaje el texto correspondiente
	 */
	public NúmeroIPv4Exception(String mensaje) {
		super(mensaje);
	}

	/**
	 * Crea una excepción secundaria almacenando otra excepción de referencia.
	 * 
	 * @param causa la excepción –o {@link Throwable}– correspondiente
	 */
	public NúmeroIPv4Exception(Throwable causa) {
		super(causa);
	}

	/**
	 * Crea una excepción secundaria almacenando otra excepción de referencia y un
	 * texto descriptivo.
	 * 
	 * @param mensaje el texto correspondiente
	 * @param causa   la excepción –o {@link Throwable}– correspondiente
	 */
	public NúmeroIPv4Exception(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
