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
    * dibuja los elementos de la torre en pantalla
    */
    public void visible(ArrayList<ElementoTorre> items) {
        Canvas canvas = Canvas.getCanvas();
        int ySuelo = canvas.getHeight() - FLOOR_MARGIN;
        int xCentro = canvas.getWidth() / 2;
    
        if (this.escala == null) {
            this.escala = new Escala(20, ySuelo, maxHeight, FACTOR_HEIGHT, 1);
        }
        escala.makeVisible();
        
        int yActual = ySuelo - FACTOR_HEIGHT;
        ElementoTorre anterior = null;
    
        for (ElementoTorre actual : items) {
            yActual = calcularNuevaNivelSuelo(yActual, actual, anterior);
            actual.dibujar(xCentro, yActual, FACTOR_HEIGHT, FACTOR_WIDTH);            
            anterior = actual;
        }
    }
        
    /**
    * Calcula la coordenada Y para el siguiente elemento.
    */
    private int calcularNuevaNivelSuelo(int yActual, ElementoTorre actual, ElementoTorre anterior) {
        if (anterior == null) return yActual;

        boolean encajeTaza = (actual.getCup() != null && anterior.getCup() != null 
            && actual.getId() < anterior.getId() && anterior.getCup().getLid() == null);
                
        boolean encajeTapaSuelte = (actual.getLidOutCup() != null && anterior.getCup() != null 
            && actual.getId() < anterior.getId() && anterior.getCup().getLid() == null);
    
        if (encajeTaza || encajeTapaSuelte) {
            return yActual - (1 * FACTOR_HEIGHT); 
        }
    
        return yActual - (anterior.getAlturaTotal() * FACTOR_HEIGHT);
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