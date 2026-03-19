import java.util.ArrayList;

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

    public TowerCanvas(int maxHeight) {
        this.maxHeight = maxHeight;
        this.escala = null;
    }
    
    /**
     * Dibuja los elementos de la torre en pantalla
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
        
        for (int i = 0; i < items.size(); i++) {
            ElementoTorre actual = items.get(i);
            int yFinal = calcularNuevaNivelSuelo(actual, i, items, basesY, techosY, ySuelo);
            actual.dibujar(xCentro, yFinal, FACTOR_HEIGHT, FACTOR_WIDTH);
            basesY[i] = yFinal;
            techosY[i] = yFinal - (actual.getAlturaTotal() * FACTOR_HEIGHT);
        }
    }
        
    /**
     * Calcula la coordenada Y final de un elemento simulando gravedad y colisiones
     * contra todos los elementos que ya están en la torre.
     */
    private int calcularNuevaNivelSuelo(ElementoTorre actual, int indiceActual, ArrayList<ElementoTorre> items, int[] basesY, int[] techosY, int ySuelo) {
        int yCaida = ySuelo - FACTOR_HEIGHT; 
    
        for (int j = 0; j < indiceActual; j++) {
            ElementoTorre previo = items.get(j);
            
            boolean encajeTaza = (actual.getCup() != null && previo.getCup() != null 
                && actual.getId() < previo.getId() && previo.getCup().getLid() == null);
                
            boolean encajeTapaSuelte = (actual.getLidOutCup() != null && previo.getCup() != null 
                && actual.getId() < previo.getId() && previo.getCup().getLid() == null);
            
            int obstaculoY;
            if (encajeTaza || encajeTapaSuelte) {
                obstaculoY = basesY[j] - (1 * FACTOR_HEIGHT);
            } else {
                obstaculoY = techosY[j];
            }
            
            if (obstaculoY < yCaida) {
                yCaida = obstaculoY;
            }
        }
        
        return yCaida; 
    }
        
    /**
    * Hace invisible los elementos de la torre
    * @param items Elementos de la torre
    */
    public void invisible(ArrayList<ElementoTorre> items) {
        if (escala != null) escala.makeInvisible();
        for (ElementoTorre e : items) {
            e.hacerInvisible();
        }
    }
        
  
}