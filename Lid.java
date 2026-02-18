
/**
 * La clase Lip contiene todos la logica y configuracion para
 * la representacion del objeto en cuestion
 * 
 * @author: Jose Alejandro Martinez Arias
 *
 * @version: 2026-02-14
 */

public class Lid {
    /**
     * @param id identificador del techo
     * @param height altura del techo
     * @param color color del techo
     * @param shape forma del techo
     * @param width ancho del techo
    */
   
    private int id;
    private int height;
    private String color;
    private Rectangle shape;
    private int width;
    
    /**
     * Crea un nuevo techo
     * @param id identificador de la taza
     * @param color Color de la taza
     */
    public Lid(int id, String color){
        this.id = id;
        this.color = color;
        this.height = 1;  
        this.width = id;
        shape = new Rectangle();
        shape.changeColor(color);  
    }
    
    /**
     * Retornar el identificador del techo
     * @retorn id identificador del techo
     */
    public int getId(){
        return id;
    }
    
    /**
     * Retorna la altura del techo
     * @return height altura del techo
     */
    public int getHeight(){
        return height;
    }
    
    /**
     * Obtener el ancho del techo
     * @return width ancho del techo
     */
     public int getWidth(){
        return width;
    }
    
    /**
     * Hacer visible del techo
     */
    public void makeVisible(){
        shape.makeVisible();
    }
    
    /**
     * Hacer invisible del techo
     */
    public void makeInvisible(){
        shape.makeInvisible();
    }
    
    /**
     * Establecer el ancho y alto en pantalla
     */
    public void setSizeScreen(int factorx,int factory){
        shape.changeSize(factorx * height, factory * width);
    } 
    
    /**
     * Establecer la posicion de la taza
     * @param int x posicion en el eje x
     * @param int y posicion en el eje y
     */
    public void setPosition(int x, int y) {
        this.shape.setPosition(x, y);
    }
}