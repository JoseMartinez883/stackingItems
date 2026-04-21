package tower;
import java.util.ArrayList;

/**
 * Tapa de tipo CrazyLid, no tapa a su taza, se ubica de base.
 * @author Jose Alejandro Martinez Arias
 * @version 2026-04-04
 */
public class CrazyLid extends Lid {
    
    /**
     * Inicializa la tapa loca
     * @param id identificador de la tapa
     */
    public CrazyLid(int id, String color) {
        super(id, color);
    }

    /**
     * Dibuja la tapa loca en la torre
     * @param xCentro centro de la torre
     * @param ySuelo base donde se posicionara la tapa
     * @param fh factor de la altura en pixeles
     * @param fw factor del ancho en pixeles
     */
    @Override
    public void dibujar(int xCentro, int ySuelo, int fH, int fW) {
        super.dibujar(xCentro, ySuelo, fH, fW);
        getShape().changeColor("magenta"); 
    }
    
    /**
     * Verifica si la tapa se puede cubrir con otro elemento
     * @param otro elemento con el que se cubrira
     * @boolen true, como esta loca cree que tiene interior
     */
    @Override
    public boolean intentarCubrirCon(ElementoTorre otro) {
        return true;
    }
    
    /**
     * Poder que se ejecutara todo el ciclo de vida de la tapa
     * @param items elementos de la torre
     * @return boolean true si el poder se ejecuto de manera correcta, sino false
     */
    @Override
    public boolean aplicarPoderContinuo(ArrayList<ElementoTorre> items) {
        int miIndice = items.indexOf(this);
    
        Cup cup = this.getCup();
        if(cup != null){
            cup.setLid(null);
            this.setCup(null); 
        }
        
        if (miIndice > 0) {
            ElementoTorre elDeAbajo = items.get(miIndice - 1);
            if (elDeAbajo.esTipo("cup") && elDeAbajo.getId() == this.id) {
                java.util.Collections.swap(items, miIndice, miIndice - 1);
            }
        }
        return true;
    }    
}