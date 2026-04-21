package tower;
import java.util.ArrayList;

/**
 * Taza abierta (OpenerCup): Elimina todas las tapas que le impiden el paso.
 * @author Jose Alejandro Martinez Arias
 * @version 2026-04-04
 */
public class OpenerCup extends Cup {
    
    /**
     * Inicializa la taza abierta
     * @param id identificador de la taza
     * @param color color de la taza
     */
    public OpenerCup(int id, String color) {
        super(id, color);
    }

    /**
     * Dibuja la copa en la torre
     * @param xCentro centro de la torre
     * @param ySuelo base donde se posicionara la copa
     * @param fh factor de la altura en pixeles
     * @param fw factor del ancho en pixeles
     */
    @Override
    public void dibujar(int xCentro, int ySuelo, int fH, int fW) {
        super.dibujar(xCentro, ySuelo, fH, fW);
        
        if (getBase() != null) getBase().changeColor("red");
        if (getLeftWall() != null) getLeftWall().changeColor("red");
        if (getRightWall() != null) getRightWall().changeColor("red");
    }

    /**
     * Poder que se ejecutara todo el ciclo de vida de la taza
     * @param items elementos de la torre
     * @return boolean true si el poder se ejecuto de manera correcta, sino false
     */    
    @Override
    public boolean aplicarPoderContinuo(ArrayList<ElementoTorre> items) {
        int miPos = items.indexOf(this);
        
        while (miPos > 0) {
            ElementoTorre abajo = items.get(miPos - 1); 
            
            if (abajo.validarRemocion("lid")) {   
                items.remove(miPos - 1);
                abajo.makeInvisible(); 
                miPos--; 
            } else break; 
        }
        return true;
    }
}