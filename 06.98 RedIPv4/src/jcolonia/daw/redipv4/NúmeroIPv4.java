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
	 * Crea una dirección IPv4 con los cuatro valores facilitados.
	 * 
	 * @param byte1 Primer byte por la izquierda, el más significativo
	 * @param byte2 Segundo byte por la izquierda, el segundo más significativo
	 * @param byte3 Tercer byte por la izquierda, el segundo menos significativo
	 * @param byte4 Cuarto byte por la izquierda, el menos significativo
	 * @throws NúmeroIPv4Exception si alguno de los valores estuviera fuera del
	 *                             rango [0,255]
	 */
	public NúmeroIPv4(int byte1, int byte2, int byte3, int byte4) {
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

		this.byte1 = byte1;
		this.byte2 = byte2;
		this.byte3 = byte3;
		this.byte4 = byte4;
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
		int red32, dirección32, máscara32;

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
	 * máscara.
	 * 
	 * @param máscaraCIDR la máscara a consultar, en formato CIDR
	 * @return el valor correspondiente
	 */
	public static int capacidadRed(int máscaraCIDR) {
		int capacidad;
		int bitsNodos = 32 - máscaraCIDR;

		capacidad = 1 << bitsNodos;

		return capacidad;
	}

	/**
	 * Facilita la representación del número IPv4 completo como un valor entero de
	 * 32 bits. Solo es aplicable como apoyo para operaciones binarias internas,
	 * dado que el bit de signo pierde su significado habitual y su uso no sería
	 * coherente en operaciones aritméticas.
	 * 
	 * @return el valor correspondiente
	 */
	private int toInt32() {
		int total;

		total = byte4;
		total += byte3 << 8;
		total += byte2 << 16;
		total += byte1 << 24;

		return total;
	}

	/**
	 * Crea un número IPv4 a partir de una representación como valor entero de 32
	 * bits. Solo es aplicable como apoyo para operaciones binarias internas. Véase
	 * la explicación dada en {@link #toInt32()}.
	 * 
	 * @param n el valor entero de 32 bits
	 * @return el número IPv4 resultante
	 */
	private static NúmeroIPv4 toIPv4(int n) {
		NúmeroIPv4 ip4;

		int NÚM_BYTES = 4;
		int ventana = 0x000000FF;
		int bytes[] = new int[NÚM_BYTES];

		for (int i = 0; i < NÚM_BYTES; i++, ventana <<= 8) {
			bytes[i] = (n & ventana) >> (8 * i);
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
