package tower;
import java.util.*;

/**
 * Clase abstracta que define comportamientos que deben seguir
 * los elementos de la torre
 * @Author Jose Alejandro Martinez Arias
 * @version 1.0
 */
public abstract class ElementoTorre {
    /**
     * @param id identificador del elemento
     * @param isVisible indica si el elemento esta visisble en pantalla
     * @param width ancho del elemento de la torre
     */
    protected int id;
    protected boolean isVisible;
    protected String color;
    
    /**
     * Constructor del elemento
     * @param id identificador del elemento de la torre
     * @param color Color del elemento
    */
    public ElementoTorre(int id, String color) {
        this.id = id;
        this.isVisible = false;
        this.color = color;
    }
    
    /**
     * Obtener el id del elemento de la torre
     * @return id identificador del elemento de la torre
     */
    public int getId() { 
        return id; 
    }

    /**
     * Calcula la altura del elemento
     * @return int altura del elemento
     */
    public abstract int getHeight();
    
    /**
     * Verifica si un elemento de la torre puede entrar o estar contenido dentro 
     * de otro elemento de la torre
     * @param otro elemento que se verificara si puede estar contenido dentro del otro
     * @return boolean true si puede entrar , sino false
     */
    public abstract boolean puedeContener(ElementoTorre otro);

    /**
     * Indica si el elemento esta abierto o esta tapado por su taza correspondiente
     * @return boolen true si esta tapado, sino false
     */
    public abstract boolean esContenedorAbierto();
   
    /**
     * Hace invisible el elemento en pantalla
     */
    public abstract void makeInvisible();
    
    /**
     * Hacer visible el elemento en pantalla
     */
    public abstract void makeVisible();
    
    /**
     * Determina como se dibujan los elementos en pantalla
     */
    public abstract void dibujar(int xCentro, int ySuelo, int fH, int fW);
    
    /**
     * Obtener la altura de la base del elemento
     * @return int altura de la base del elemento
     */
    public abstract int getHeightBase();
    
    /**
     * El elemento generar su reporte
     * @return ArrayList<String[]> reporte generado estilo {{"tipo","id"}}
     */
    public abstract ArrayList<String[]> generarReporte();
    
    /**
     * Verifica si el elemento se deja cubrir por el elemento enviado
     * @param otro elemento que quiere cubrirse
     */
    public abstract boolean intentarCubrirCon(ElementoTorre otro);
    
    /**
     * Verifica si el elemento es del tipo de elemento solicitado
     * @param elemento tipo de elemento que se quiere
     * @return boolean true si es del tipo solicitado, sino false
     */
    public abstract boolean esTipo(String elemento);
    
    /**
     * Validar si el elemento se deja eliminar
     * @param elemento tipo de elemento que se quiere eliminar
     * @return boolen true si se deja eliminar, sino false
     */
    public abstract boolean validarRemocion(String elemento);
    
    // ciclo 4
    /**
     * Poder que se ejecuta solo al momento de entrar en la torre
     * @return items elementos de la torre actualizados
     * @throws TowerException Si alguna regla de integridad del poder se rompe
     */
    public boolean aplicarPoderAlEntrar(ArrayList<ElementoTorre> items) throws TowerException{
        return true;
    }

    /**
     * Poder que se ejecuta al realizar cualquier movimiento en la torre
     * @return items elemento de la torre actualizados
     */
    public boolean aplicarPoderContinuo(ArrayList<ElementoTorre> items) {
        return true;
    }
    
    /**
     * Poder que se ejecutara cuando el usuario realize una accion en la torre
     * @return items elementos de la torre
     */
    public abstract void reaccionarTurno(ArrayList<ElementoTorre> items);
	}