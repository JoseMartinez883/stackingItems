/**
 * La clase Cup contiene todos la logica y configuracion para
 * la representacion del objeto en cuestion
 *
 * @author: Jose Alejandro Martinez Arias
 *
 * @version: 2026-02-14
 */

public class Cup{
    /**
     * @param id identificador de la taza
     * @param height altura de la taza
     * @param color color de la taza
     * @param shape forma de la taza 
    */
   
    private int id;
    private int height;
    private String color;
    private Rectangle shape;
    
    /**
     * Crea un nueva taza 
     * @param id identificador de la taza
     * @param color Color de la taza
     */
    public Cup(int id, String color){
        this.id = id;
        this.color = color;
        this.height = (2 * id) - 1; 
        
        shape  = new Rectangle();
        shape.changeColor(color);
        shape.changeSize(this.height, 30 + (id * 10));
        shape.changeFilled(false);
    }
    
    /**
     * Retornar el identificador de la taza
     * @retorn id identificador de la taza
     */
    public int getId(){
        return id;
    }
    
    /**
     * Retorna la altura de la taza
     * @return height altura de la taza
     */
    public int getHeight(){
        return height;
    }
    
    /**
     * Hacer visible la taza
     */
    public void makeVisible(){
        shape.makeVisible();
    }
    
    /**
     * Hacer invisible la taza
     */
    public void makeInvisible(){
        shape.makeInvisible();
    }
}