/**
 * Ejemplo UT7: Demostración de programación concurrente usando hilos
 * ̣—{@link java.lang.Thread Threads}— en Java. Los hilos conforman un mecanismo
 * de multitarea ligera permitiendo la ejecución paralela de diversas partes del
 * programa compartiendo memoria. <div>Existen dos formas de programar clases
 * ejecutables como hilos:
 * <ul>
 * <li>Por herencia de la clase {@link java.lang.Thread Thread}:
 * {@code extends Thread}</li>
 * <li>Aplicando la interface {@link java.lang.Runnable Runnable}:
 * {@code implements Runnable}</li>
 * </ul>
 * También es necesario un mecanismo de bloqueos para permitir la coexistencia
 * entre hilos, dado que cada unos avanzará en orden y a ritmos impredecibles.
 * Para evitar conflictos habrá que arbitrar zonas de bloqueo en el código para
 * que en cada momento solo las pueda ejecutar un único hilo y los demás esperen
 * su turno.</div>
 * 
 * @author <a href= "mailto:dmartin.jcolonia@gmail.com">David H. Martín</a>
 * @version 1.0 (20230423)
 */
package jcolonia.daw2023.rueca;
