package jcolonia.daw.redipv4;

/**
 * Lanzador de la aplicación de gestión de resultados deportivos de tipo 1X2.
 * 
 * @see ControladorRedIPv4
 * 
 * @version 1.1 (20240502000)
 * @author <a href="mailto:dmartin.jcolonia@gmail.com">David H. Martín</a>
 */
public class LanzadorRedIPv4 {
	/**
	 * Inicia el programa creando una instancia de la clase y activando el bucle
	 * principal de opciones. Abre el lector asociado a la entrada estándar.
	 * 
	 * @param args no se usa
	 */
	public static void main(String[] args) {
		System.out.println(new NúmeroIPv4(192, 168, 332, 10));

		for (int i = 0; i <= 32; i++) {
			System.out.printf("/%d → %s (%d direcciones)%n", i, NúmeroIPv4.máscara(i), NúmeroIPv4.capacidadRed(i));
		}

		var ip = new NúmeroIPv4(10, 192, 35, 252);
		int máscara = 22;
		var ipRed = ip.aplicarMáscara(máscara);

		System.out.printf("%1$s/%2$d → %3$s/%2$d", ip, máscara, ipRed);
	}
}
