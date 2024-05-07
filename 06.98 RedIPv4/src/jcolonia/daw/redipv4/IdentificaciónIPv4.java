package jcolonia.daw.redipv4;

/**
 * Modelo: Identificación de un nodo, red o dirección IP incorporando la máscara de red.
 * 
 * @version 0.1 (20240503001)
 * @author <a href="mailto:dmartin.jcolonia@gmail.com">David H. Martín</a>
 */
public class IdentificaciónIPv4 {
	/**
	 * Separador empleado en la exportación pseudo-CSV: «#» (almohadilla, número…).
	 * 
	 * @see #toStringCSV()
	 * @see #of(String)
	 */
	private static final String SEPARADOR = "#";

	/**
	 * Dirección IPv4.
	 */
	private NúmeroIPv4 dirección;
	/**
	 * Máscara IPv4.
	 */
	private NúmeroIPv4 máscara;
	
	/**
	 * Resultado 1-X-2 del partido.
	 */
	private Resultado1X2 resultado;

	/**
	 * Inicializa una lista de valores vacía y pone el contador de valores a cero.
	 */
	public IdentificaciónIPv4() {
	}

	/**
	 * Construye un partido a partir de una línea de texto. La línea de texto debe
	 * contener, al menos, tres textos válidos en un formato pseudo-CSV empleando el
	 * carácter definido como {@link #SEPARADOR separador}.
	 * 
	 * <div>Se facilita este método estático como alternativa a la posibilidad de
	 * disponer un método setDatos(String), evitando así tener que gestionar las
	 * recargas sobre objetos parcialmente rellenados en el inicio. También se evita
	 * así el caso de que el objeto quedara incompleto si se produce una incidencia
	 * fatal a mitad de la carga.</div>
	 * 
	 * @param líneaCSV la línea de texto
	 * @throws DatoPartido1X2Exception si alguno de los datos no encaja en la
	 *                                 posición correspondiente
	 * @return el nuevo partido creado
	 */
	public static IdentificaciónIPv4 of(String líneaCSV) throws DatoPartido1X2Exception {
		IdentificaciónIPv4 nuevoPartido;
		nuevoPartido = new IdentificaciónIPv4();
	
		String[] partes = líneaCSV.split(SEPARADOR);
	
		if (partes.length < MÁX_CAMPOS) { // Excepción temprana si faltan campos
			throw new NúmeroIPv4Exception("Línea CSV mal formada");
		}
	
		for (String pieza : partes) {
			// Admitimos campos extra, pero ignoramos lo que sobre.
			if (nuevoPartido.estáCerrada()) {
				break;
			}
	
			nuevoPartido.setDato(pieza); // Atención: excepción si el dato no encaja
		}
	
		return nuevoPartido;
	}

	/**
	 * Construye un partido a partir de los datos del mismo.
	 * 
	 * @param nombreLocal      el nombre del primer equipo, el equipo local
	 * @param nombreVisitante  el nombre del segundo equipo, el equipo visitante
	 * @param resultadoPartido el resultado 1-X-2 del partido
	 * @throws DatoPartido1X2Exception si alguno de los datos no encaja en la
	 *                                 posición correspondiente
	 * @return el nuevo partido creado
	 */
	public static IdentificaciónIPv4 of(String nombreLocal, String nombreVisitante, String resultadoPartido)
			throws DatoPartido1X2Exception {
		IdentificaciónIPv4 nuevoPartido;
		nuevoPartido = new IdentificaciónIPv4();
	
		nuevoPartido.setDato(nombreLocal);
		nuevoPartido.setDato(nombreVisitante);
		nuevoPartido.setDato(resultadoPartido);
	
		return nuevoPartido;
	}

	/**
	 * Verifica que se haya proporcionado un texto.
	 * 
	 * @param texto el dato a comprobar
	 * @throws DatoPartido1X2Exception si el texto es nulo o está vacío
	 */
	public void verificarTextoNoNulo(String texto) throws IPv4Exception {
		if (texto == null || texto.length() == 0) {
			throw new IPv4Exception("Dato vacío");
		}
	}

	/**
	 * Proporciona una representación en texto formateada del partido. En la primera
	 * columna van los dos equipos -con puntos de relleno- y en la segunda los
	 * resultados. Ambas columnas son de ancho fijo para obtener composiciones
	 * alineadas que formen una tabla.
	 * 
	 * @return el texto correspondiente
	 */
	@Override
	public String toString() {
		String mensaje;
		String textoNombres, textoLocal, textoVisitante;
		String textoResultado;

		textoLocal = (equipoLocal == null) ? "¿?" : equipoLocal;
		textoVisitante = (equipoVisitante == null) ? "¿?" : equipoVisitante;
		textoNombres = String.format("%s - %s", textoLocal, textoVisitante);

		textoNombres = normalizarAncho(TXT_ANCHO_NOMBRES, textoNombres, '.');

		textoResultado = (resultado == null) ? Resultado1X2.toNullString() : resultado.to1X2String();
		mensaje = String.format("%s %s", textoNombres, textoResultado);
		return mensaje;
	}

	/**
	 * Coloca en una línea todos los datos del partido separados por el carácter
	 * definido como {@link #SEPARADOR separador}. Se utiliza para montar el archivo
	 * en formato pseudo CSV.
	 * 
	 * @return la línea completa
	 */
	public String toStringCSV() {
		verificarCerrada();
		StringBuffer texto = new StringBuffer();

		texto.append(equipoLocal);
		texto.append(SEPARADOR);
		texto.append(equipoVisitante);
		texto.append(SEPARADOR);
		texto.append(resultado);

		return texto.toString();
	}

	/**
	 * Proporciona una representación en texto de los puntos conseguidos por cada
	 * equipo. El ganador recibe tres puntos y en caso de empate se reparte un punto
	 * a cada uno.
	 * 
	 * @return el texto correspondiente
	 */
	public String toStringPuntos() {
		String mensaje;

		switch (resultado) {
		case Local1:
			mensaje = String.format("↑ %s (%d)", equipoLocal, 3);
			break;
		case Visitante2:
			mensaje = String.format("↓ %s (%d)", equipoVisitante, 3);
			break;
		case EmpateX:
			mensaje = String.format("= %s (%3$d) - %s (%3$d)", equipoLocal, equipoVisitante, 1);
			break;
		default: // No debe ocurrir, datos incompletos
			mensaje = "- - - -";
			break;
		}
		return mensaje;
	}
}
