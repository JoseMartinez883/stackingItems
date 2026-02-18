import java.util.ArrayList;
/**
 * Encargada de representar las escalas (regla) de la altura de la torre
 * 
 * @author Jose Alejandro Martinez Arias
 * @version (16-02-2026)
*/

public class Escala {
    private ArrayList<Rectangle> marcas;
    private Rectangle suelo;
    
    /**
     * Construye un base rectangular (suelo) y una escala que marca los centimetros
     * @param x posicion en el eje x donde se dibujara la regla
     * @param y posicion inicial en el eje y donde comenzara a graficarla
     * @param alturaMax altura maxima de la torre
     * @param factor factor pixeles del eje y
     * @param ancho que tendra cada linea de la regla
     */
    public Escala(int x, int y, int alturaMax, int factor, int ancho) {
        marcas = new ArrayList<>();
        
        suelo = new Rectangle();
        suelo.changeColor("black");
        suelo.changeSize(2, ancho * 40);
        suelo.setPosition(x, y);
        
        for (int i = 0; i <= alturaMax; i++) {
            Rectangle m = new Rectangle();
            m.changeColor("black");
            m.changeSize(1, 10);
            m.setPosition(x, y - (i * factor));
            marcas.add(m);
        }
    }
    
    /**
     * Hacer visible el suelo y la escala
     */
    public void makeVisible() {
        suelo.makeVisible();
        for (Rectangle m : marcas) m.makeVisible();
    }
    
    /**
     * Hacer invisible el suelo y la escala
     */
    public void makeInvisible() {
        suelo.makeInvisible();
        for (Rectangle m : marcas) m.makeInvisible();
    }
}