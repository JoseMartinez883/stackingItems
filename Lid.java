
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
    */
   
    private int id;
    private int height;
    private String color;
    private Rectangle shape;
    
    /**
     * Crea un nuevo techo
     * @param id identificador de la taza
     * @param color Color de la taza
     */
    public Lid(int id, String color){
        this.id = id;
        this.color = color;
        this.height = 1;  
        
        shape = new Rectangle();
        shape.changeColor(color); 
        shape.changeSize(5, 30 +(id * 10)); 
        shape.changeFilled(true); 
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
    
    
}