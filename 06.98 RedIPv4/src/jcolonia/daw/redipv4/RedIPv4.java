package jcolonia.daw.redipv4;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Modelo: Colección básica de resultados deportivos en formato de quiniela 1X2.
 * 
 * @version 0.1 (20240503001)
 * @author <a href="mailto:dmartin.jcolonia@gmail.com">David H. Martín</a>
 */
public class RedIPv4 {
	/**
	 * Lista donde se almacenan los elementos.
	 */
	List<IdentificaciónIPv4> lista;

	/**
	 * Crea una lista inicialmente vacía.
	 */
	public RedIPv4() {
		lista = new Vector<IdentificaciónIPv4>(6);
	}

	/**
	 * Localiza un elemento de la lista por su posición.
	 * 
	 * @param pos la posición a consultar
	 * @return el elemento correspondiente
	 */
	public IdentificaciónIPv4 getElemento(int pos) {
		IdentificaciónIPv4 resultado;
		resultado = lista.get(pos);
		return resultado;
	}

	/**
	 * Incorpora un elemento al final de la lista.
	 * 
	 * @param nuevo el elemento a incorporar
	 */
	public void agregarElemento(IdentificaciónIPv4 nuevo) {
		lista.add(nuevo);
	}

	/**
	 * Elimina un elemento de la lista.
	 * 
	 * @param viejo el elemento retirado
	 */
	public void eliminarElemento(IdentificaciónIPv4 viejo) {
		lista.remove(viejo);
	}

	/**
	 * Devuelve el número de elementos almacenados en la lista.
	 * 
	 * @return el número de elementos
	 */
	public int size() {
		return lista.size();
	}

	/**
	 * Elimina todos los elementos de la lista.
	 */
	public void vaciar() {
		lista.clear();
	}

	/**
	 * Facilita una lista con las descripciones de todos los resultados almacenados.
	 * 
	 * @see IdentificaciónIPv4#toString()
	 * 
	 * @return la lista de textos correspondiente
	 */
	public List<String> generarListado() {
		List<String> listaTextos;
		listaTextos = new ArrayList<String>(lista.size());

		for (IdentificaciónIPv4 resultado : lista) {
			listaTextos.add(resultado.toString());
		}
		return listaTextos;
	}

	/**
	 * Facilita una lista con las líneas de todos los resultados almacenados aptas
	 * para el archivo de exportación en formato pseudo CSV.
	 * 
	 * @see IdentificaciónIPv4#toStringCSV()
	 * 
	 * @return la lista de textos correspondiente
	 */
	public List<String> generarListadoCSV() {
		List<String> listaTextos;
		listaTextos = new ArrayList<String>(lista.size());

		for (IdentificaciónIPv4 resultado : lista) {
			listaTextos.add(resultado.toStringCSV());
		}
		return listaTextos;
	}
}
