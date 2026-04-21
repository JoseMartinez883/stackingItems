package tower.presentation;
import java.util.ArrayList;
import shapes.Canvas;
import tower.ElementoTorre;

/**
 * Se encarga de la representacion de los elementos de la torre en el canvas
 ** @author Jose Alejandro Martinez Arias
 * @version 2026-03-28
 */
public class TowerCanvas {
    /**
    * @param FLOOR_MARGIN longitud entre el suelo y la base de la torre
    * @param FACTOR_HEIGHT factor de altura en pixeles
    * @param FACTOR_WIDTH factor de ancho en pixeles
    * @param escala dibujar la regla de la altura maxima de la torre
    * @param maxHeight altura maxima que puede tener la torre
    */
    private static final int FLOOR_MARGIN = 30;
    private static final int FACTOR_HEIGHT = 15;  
    private static final int FACTOR_WIDTH = 40;
        
    private Escala escala;
    private int maxHeight;
    
    /**
     * Constructor, encargado de inicializar los atributos
     * @param maxHeight altura maxima de la torre
     */
    public TowerCanvas(int maxHeight) {
        this.maxHeight = maxHeight;
        this.escala = null;
    }
    
    /**
     * Dibuja los elementos de la torre en pantalla
     * @param items Elementos que estan en la torre
     */
    public void visible(ArrayList<ElementoTorre> items) {
        Canvas canvas = Canvas.getCanvas();
        int ySuelo = canvas.getHeight() - FLOOR_MARGIN;
        int xCentro = canvas.getWidth() / 2;
    
        if (this.escala == null) {
            this.escala = new Escala(20, ySuelo, maxHeight, FACTOR_HEIGHT, 1);
        }
        
        escala.makeVisible();
        
        int[] basesY = new int[items.size()];
        int[] techosY = new int[items.size()];
        for (int i = 0; i < items.size(); i++){
            ElementoTorre actual = items.get(i);
            int yNivelSuelo = ySuelo - FACTOR_HEIGHT;
            
            for (int j = 0; j < i; j++) {
                ElementoTorre previo = items.get(j);
                boolean encaja = previo.puedeContener(actual);
                
                int obstaculoY = encaja ? 
                        basesY[j] - (previo.getHeightBase() * FACTOR_HEIGHT) : techosY[j];
    
                if (obstaculoY < yNivelSuelo) {
                    yNivelSuelo = obstaculoY;
                }
            }
            
            actual.dibujar(xCentro, yNivelSuelo, FACTOR_HEIGHT, FACTOR_WIDTH);
            
            basesY[i] = yNivelSuelo;
            techosY[i] = yNivelSuelo - (actual.getHeight() * FACTOR_HEIGHT);
        }   
    }
        
    /**
    * Hace invisible los elementos de la torre
    * @param items Elementos de la torre
    */
    public void invisible(ArrayList<ElementoTorre> items) {
        if (escala != null) escala.makeInvisible();
        for (ElementoTorre e : items) {
            e.makeInvisible();
        }
    }
}
