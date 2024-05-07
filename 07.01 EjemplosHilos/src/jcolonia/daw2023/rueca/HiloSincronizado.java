package jcolonia.daw2023.rueca;

import java.util.ArrayList;
import java.util.List;

/**
 * Ejemplo de creación de hilos sobre una clase que implemente la interface
 * {@link java.lang.Runnable Runnable} y con un bloque sincronizado.
 * 
 * @author <a href= "mailto:dmartin.jcolonia@gmail.com">David H. Martín</a>
 * @version 1.2 (20240507)
 */
public class HiloSincronizado implements Runnable {
	/** Número de hilos a crear en la demostración. */
	private static final int MÁX_HILOS;

	/** Contador de hilos creados hasta el momento. */
	private static int númHilos;

	/** Numero de secuencia, para ordenar los mensajes de identificación. */
	private static Integer turno;

	/** Objeto básico usado solo como monitor para el bloque sincronizado. */
	private static Object bloqueo;

	/** Referencia al hilo en ejecución. */
	private Thread hilo;
	/**
	 * Número que identifica cada objeto. Emplea valores enteros consecutivos: 1, 2,
	 * 3…
	 */
	private int id;

	static {
		// int númProcesadores = Runtime.getRuntime().availableProcessors();
		// MÁX_HILOS = númProcesadores < 4 ? 4 : númProcesadores - 2;

		MÁX_HILOS = 20;

		númHilos = 0;
		turno = 1;
		bloqueo = new Object();
	}

	/**
	 * Establece el identificador según el orden de creación. Aunque se crea el
	 * objeto, no se inicia el hilo propiamente dicho.
	 */
	public HiloSincronizado() {
		id = ++númHilos;
	}

	/**
	 * Se ejecuta en cada hilo enviando sencillos mensajes de creación e
	 * identificación a la salida estándar. El orden de visualización de los
	 * mensajes de cada hilo coincide con el orden de creación, según el
	 * identificador.
	 */
	@Override
	public void run() {
		hilo = Thread.currentThread();

		System.out.printf("Activado %s con id=%d%n", hilo, hilo.threadId());
		try {
			fichar();
		} catch (InterruptedException e) {
		}

		hilo = null;
	}

	/**
	 * Envía un mensaje de identificación a la salida estándar, incrementa el número
	 * de secuencia y lo notifica al resto de hilos. Mantiene los hilos en espera
	 * hasta que no se alcance su «turno».
	 * 
	 * @throws InterruptedException en caso de que se aborte la espera
	 */

	private void fichar() throws InterruptedException {
		synchronized (bloqueo) {
			while (turno < id) {
				bloqueo.wait();
			}
			System.out.printf("\t→ Hilo %d%n", id);
			turno++;
			bloqueo.notifyAll();
		}
	}

	/**
	 * Ejecutable de ejemplo que crea varios objetos y activa los hilos
	 * correspondientes.
	 * 
	 * @param argumentos opciones de ejecución del programa –no se usan–
	 */
	public static void main(String[] argumentos) {
		ThreadGroup canastillo;
		Thread hilo;
		String nombre;

		List<Thread> listaHilos = new ArrayList<>(MÁX_HILOS);

		System.out.printf("Máximo de hilos: %d%n", MÁX_HILOS);

		canastillo = new ThreadGroup("demo");
		for (int i = 1; i <= MÁX_HILOS; i++) {
			nombre = String.format("Hilo «%d»", i);
			hilo = new Thread(canastillo, new HiloSincronizado(), nombre);
			listaHilos.add(hilo);
			hilo.start();
		}
	}
}
