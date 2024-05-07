package jcolonia.daw.redipv4;

/**
 * Grupo de cuatro número enteros para direcciones o máscaras IP de versión 4.
 * 
 * @param byte1 Primer byte por la izquierda, el más significativo
 * @param byte2 Segundo byte por la izquierda, el segundo más significativo
 * @param byte3 Tercer byte por la izquierda, el segundo menos significativo
 * @param byte4 Cuarto byte por la izquierda, el menos significativo
 * 
 * @version 0.1 (20240503001)
 * @author <a href="mailto:dmartin.jcolonia@gmail.com">David H. Martín</a>
 */
public record NúmeroIPv4(int byte1, int byte2, int byte3, int byte4) {

	/**
	 * Verificación previa de los cuatro valores facilitados para el número IPv4.
	 */
	public NúmeroIPv4 {
		String mensaje;
		int byteMal = 0;

		// Rango [0,255]: ¿Bytes sin signo?
		if (0 != (byte1 & 0xFFFFFF00)) {
			byteMal = 1;
		} else if (0 != (byte2 & 0xFFFFFF00)) {
			byteMal = 2;
		} else if (0 != (byte3 & 0xFFFFFF00)) {
			byteMal = 3;
		} else if (0 != (byte4 & 0xFFFFFF00)) {
			byteMal = 4;
		}

		if (byteMal != 0) {
			mensaje = String.format("Byte %d en «%d-%d-%d-%d» fuera de rango", byteMal, byte1, byte2, byte3, byte4);
			throw new NúmeroIPv4Exception(mensaje);
		}
		// Constructor compacto → constructor canónico
	}

	/**
	 * Crea una máscara de dirección IPv4.
	 * 
	 * @param máscaraCIDR el número de bits de la parte de red
	 * @return el número IPv4 correspondiente
	 */
	public static NúmeroIPv4 máscara(int máscaraCIDR) {
		NúmeroIPv4 máscara;
		int bytes[] = new int[4];

		for (int posición = 0; posición < máscaraCIDR; posición++) {
			bytes[posición / 8] += 1 << (7 - posición % 8);
		}

		máscara = new NúmeroIPv4(bytes[0], bytes[1], bytes[2], bytes[3]);
		return máscara;
	}

	/**
	 * Crea un nuevo número IPv4 al aplicar una máscara de red. Es la operación que
	 * proporciona la dirección de red; permite comprobar si dos direcciones están
	 * en la misma red y determinar así sobre la tabla de rutas la ruta a aplicar.
	 * La dirección de red corresponde al primer número IPv4 de una subred o rango
	 * de direcciones.
	 * 
	 * @param máscara la máscara a aplicar
	 * @return el número IPv4 resultante
	 */
	public NúmeroIPv4 aplicarMáscara(NúmeroIPv4 máscara) {
		NúmeroIPv4 red;
		long red32, dirección32, máscara32;

		dirección32 = this.toInt32();
		máscara32 = máscara.toInt32();
		red32 = dirección32 & máscara32;

		red = toIPv4(red32);

		return red;
	}

	/**
	 * Crea un nuevo número IPv4 al aplicar una máscara de red. Véase la explicación
	 * dada en {@link #aplicarMáscara(NúmeroIPv4)}.
	 * 
	 * @param máscaraCIDR la máscara a aplicar en formato CIDR
	 * @return el número IPv4 resultante
	 */
	public NúmeroIPv4 aplicarMáscara(int máscaraCIDR) {
		return aplicarMáscara(máscara(máscaraCIDR));
	}

	/**
	 * Calcula el número de direcciones —tamaño del rango— establecido por una
	 * máscara. Para soportar valores positivos de 32 bits requiere emplear enteros
	 * de tipo <code>long</code>.
	 * 
	 * @param máscaraCIDR la máscara a consultar, en formato CIDR
	 * @return el valor correspondiente
	 */
	public static long capacidadRed(int máscaraCIDR) {
		long capacidad;
		int bitsNodos = 32 - máscaraCIDR;

		capacidad = 1L << bitsNodos;

		return capacidad;
	}

	/**
	 * Facilita la representación del número IPv4 completo como un valor entero de
	 * 32 bits. Para soportar valores positivos de 32 bits requiere emplear enteros
	 * de tipo <code>long</code>.
	 * 
	 * @return el valor correspondiente
	 */
	private long toInt32() {
		long total;

		total = (long) byte4;
		total += (long) byte3 << 8;
		total += (long) byte2 << 16;
		total += (long) byte1 << 24;

		return total;
	}

	/**
	 * Crea un número IPv4 a partir de una representación como valor entero de 32
	 * bits. Para soportar valores positivos de 32 bits requiere emplear enteros de
	 * tipo <code>long</code>.
	 * 
	 * @param n el valor entero de 32 bits
	 * @return el número IPv4 resultante
	 */
	private static NúmeroIPv4 toIPv4(long n) {
		NúmeroIPv4 ip4;

		int NÚM_BYTES = 4;
		long ventana = 0xFFL;
		int bytes[] = new int[NÚM_BYTES];

		for (int i = 0; i < NÚM_BYTES; i++, ventana <<= 8) {
			bytes[i] = (int) ((n & ventana) >> (8 * i));
		}

		ip4 = new NúmeroIPv4(bytes[3], bytes[2], bytes[1], bytes[0]);
		return ip4;
	}

	/**
	 * Facilita una representación tradicional de una dirección IP versión 4: los
	 * cuatro bytes en decimal separados por puntos y sin ningún espacio en blanco.
	 * <div>Ejemplo:
	 * 
	 * <pre>
	 * 192.168.32.11
	 * </pre>
	 * 
	 * </div>
	 */
	@Override
	public final String toString() {
		String texto;
		texto = String.format("%d.%d.%d.%d", byte1, byte2, byte3, byte4);
		return texto;
	}
}
