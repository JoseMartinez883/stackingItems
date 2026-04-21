package tower;
import java.util.ArrayList;

/**
 * Tapa Miedosa: No entra sin su taza y no se deja borrar si ya está puesta.
 * @author Jose Alejandro Martinez Arias
 * @version 2026-04-04
 */
public class FearfulLid extends Lid {
    
    /**
     * Constructor de la tapa miedosa
     * @param id identificador de la tapa
     * @param color color de la tapa
     */
    public FearfulLid(int id, String color) {
        super(id, color);
    }

    /**
     * Verifica si la tapa se puede remover de la torre que se quiere remover
     * @param comando elemento solicitado a remover
     * @return boolean true si es el del mismo tipo solicitado, sino false
     */
    @Override
    public boolean validarRemocion(String comando) {
        super.validarRemocion(comando);
        boolean canRemoved = true;    
        if (getCup() != null && getCup().getId() == this.getId()) {
            canRemoved = false; 
        } 
        return canRemoved;
    }
    
    /**
     * Dibuja la tapa miedosa en la torre
     * @param xCentro centro de la torre
     * @param ySuelo base donde se posicionara la tapa
     * @param fh factor de la altura en pixeles
     * @param fw factor del ancho en pixeles
     */
    @Override
    public void dibujar(int xCentro, int ySuelo, int fH, int fW) {
        super.dibujar(xCentro, ySuelo, fH, fW);
        getShape().changeColor("yellow"); 
    }
    
    /**
     * Poder de la tapa FearFullLid que se aplicara al entrar en la torre
     * @param items elementos de la torre
     * @return boolean true si el poder se ejecuto correctamente, sino false
     * @throws TowerException POWER_FAILED si la tapa miedosa no encuentra su taza en la torre.
     */
    @Override
    public boolean aplicarPoderAlEntrar(ArrayList<ElementoTorre> items) throws TowerException{
        boolean encontroTaza = false;
        
        if(this.getCup() != null) encontroTaza = true;
        
        for(ElementoTorre e : items){
            if(e.esTipo("cup") && e.getId() == this.id){
                encontroTaza = true;
                break;
            }
        }
        
        if (!encontroTaza) {
            throw new TowerException(TowerException.POWER_FAILED);
        }
        return true;
    }
}