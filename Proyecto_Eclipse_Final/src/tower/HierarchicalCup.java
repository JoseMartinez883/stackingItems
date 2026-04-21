package tower;
import java.util.ArrayList;

/**
 * Taza Jerárquica (HierarchicalCup): Desplaza a las menores y si llega al fondo, se ancla.
 * @author Jose Alejandro Martinez Arias
 * @version 2026-04-04
 */
public class HierarchicalCup extends Cup {
    /**
     * @param estaEnelFondo true si esta en la base, sino false
     */
    private boolean estaEnElFondo;
    
    /**
     * Inicializa la taza jerarquica
     * @param id identificador de la taza
     * @param color color de la taza
     */
    public HierarchicalCup(int id, String color) {
        super(id, color);
        this.estaEnElFondo = false;
    }
    
    /**
     * Verifica si la copa se puede remover de la torre que se quiere remover
     * @param comando elemento solicitado a remover
     * @return boolean true si es el del mismo tipo solicitado, sino false
     */
    @Override
    public boolean validarRemocion(String comando) {
        if (estaEnElFondo) {
            return false;
        }
        return super.validarRemocion(comando);
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
        
        if (getBase() != null) getBase().changeColor("black");
        if (getLeftWall() != null) getLeftWall().changeColor("black");
        if (getRightWall() != null) getRightWall().changeColor("black");
    }
    
    /**
     * Poder que se ejecutara todo el ciclo de vida de la taza
     * @param items elementos de la torre
     * @return boolean true si el poder se ejecuto de manera correcta, sino false
     */
    @Override
    public boolean aplicarPoderContinuo(ArrayList<ElementoTorre> items){
        if (items.indexOf(this) == 0) estaEnElFondo = true;  
        return true;
    }
    
    /**
     * Poder de la taza jerarquica que se aplicara al entrar en la torre
     * @param items elementos de la torre
     * @return boolean true si el poder se ejecuto correctamente, sino false
     * @throws TowerException POWER_FAILED si el poder falla
     */
    @Override
    public boolean aplicarPoderAlEntrar(ArrayList<ElementoTorre> items) throws TowerException{
        int miPosicion = items.size() - 1;
        
        while (miPosicion > 0) {
            ElementoTorre elDeAbajo = items.get(miPosicion - 1);
            
            if (elDeAbajo.getId() < this.getId()) {
                java.util.Collections.swap(items, miPosicion, miPosicion - 1);
                miPosicion--;
            } else {
                break; 
            }
        }

        if (miPosicion == 0) {
            this.estaEnElFondo = true;
        }
        
        return true;
    }
}