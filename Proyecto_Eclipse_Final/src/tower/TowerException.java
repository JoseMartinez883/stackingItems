package tower;

/**
 * Encargada de manejar las excepciones que se presenten en la torre
 * * @author Jose Alejandro Martinez Arias
 */
public class TowerException extends Exception {

    public static final String OVERFLOW = "La operacion superaria el limite de altura maximo de la torre";
    public static final String INVALID_DIMENSIONS = "Las dimensiones de la torre (ancho/alto) deben ser mayores a cero";
    public static final String INVALID_ID = "El identificador del elemento debe ser un numero entero positivo mayor a cero";
    public static final String DUPLICATE_ID = "Ya existe un elemento en la torre con el identificador proporcionado";
    public static final String NON_EXISTENT_ID = "No se encontro ningun elemento con el identificador solicitado";
    public static final String POWER_FAILED = "El poder especial del elemento no pudo ejecutarse correctamente";
    public static final String IMMOVABLE_ELEMENT = "El poder especial del elemento impide su eliminacion o movimiento en este estado";
    public static final String INVALID_ELEMENT_TYPE = "El tipo de elemento solicitado no es reconocido por el sistema";

    /**
     * Constructor de la excepción
     * @param mensaje texto de la excepcion ocurrida
     */
    public TowerException(String mensaje) {
        super(mensaje);
    }
}