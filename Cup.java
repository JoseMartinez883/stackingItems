/**
 * Representa una taza usando tres rectángulos (base y paredes)
 * @author: Jose Alejandro Martinez Arias
 * @version: 2026-02-14
 */

public class Cup {
    /**
     * @param id identificador la taza, determina ancho y alto
     * @param height, altura logico de la torre
     * @param width, ango logico de la torre
     * @param color, color de la taza
     * @param base, representa la base de la taza
     * @param leftWall, pared izquierda de la taza
     * @param rigthWall, pared derecha de la taza
     * @param WALL_THICKNESS grosor de las paredes
     * @param HEIGHT_BASE altura de la base 
     * @param lid tapa correspodiente a la copa
     */
    
    private int id;
    private int height;
    private int width;
    private String color;
    
    private Rectangle base;
    private Rectangle leftWall;
    private Rectangle rightWall;
    private static final int WALL_THICKNESS = 10;
    private static final int HEIGHT_BASE = 1;
    private Lid lid; 
    
    /**
     * Creo una taza
     * @param id identificador de la taza
     * @param color color de la taza
     */
    public Cup(int id, String color) {
        this.id = id;
        this.color = color;
        this.height = (2 * id) - 1; 
        this.width = id;

        base = new Rectangle();
        base.changeColor(color);
   
        if (id > 1) {
            leftWall = new Rectangle();
            leftWall.changeColor(color);
            rightWall = new Rectangle();
            rightWall.changeColor(color);
        }
    }

    /**
     * Ajusta el tamaño de cada componente.
     * factorH y factorW vienen de la configuración de Tower.
     */
    public void setSizeScreen(int hTotalPix, int wTotalPix, int hBasePix) {
        base.changeSize(hBasePix, wTotalPix);
        if (id > 1){
            int wallHeight = hTotalPix - hBasePix;
            leftWall.changeSize(wallHeight, WALL_THICKNESS); 
            rightWall.changeSize(wallHeight, WALL_THICKNESS);
        }
    }
    
    /**
     * Modifica o establece la posicion de la taza
     */
    public void setPosition(int x, int yPuntoApoyo, int hTotalPix, int wTotalPix, int hBasePix) {
        base.setPosition(x, yPuntoApoyo);
        
        if (id > 1) {
            int hParedes = hTotalPix - hBasePix;
            int ySuperiorParedes = yPuntoApoyo - hParedes;
            leftWall.setPosition(x, ySuperiorParedes);
            rightWall.setPosition(x + wTotalPix - WALL_THICKNESS, ySuperiorParedes);
        }
    }
    
    /**
     * Hacer invisible la taza en pantalla
     */
    public void makeVisible() {
        base.makeVisible();
        if (id > 1) {
            leftWall.makeVisible();
            rightWall.makeVisible();
        }
    }
    
    /**
     * Hacer invisible la taza en pantalla
     */
    public void makeInvisible() {
        base.makeInvisible();
        if (id > 1) {
            leftWall.makeInvisible();
            rightWall.makeInvisible();
        }
    }
    
    /**
     * Obtener el identificador de la taza
     * @return id identificador de la taza
     */
    public int getId() { 
        return id; 
    }
    
    /**
     * Obtener la altura de la taza
     * @return height altura de la taza
     */
    public int getHeight() { 
        return height; 
    }
    
    /**
     * Obtener el ancho de la taza
     * @return width ancho de la taza
     */
    public int getWidth() { 
        return width; 
    }
    
    /**
     * Asignar la tapa de la copa
     * @param lid tapa que le corresponde a la copa
     */
    public void setLid(Lid lid) {
        this.lid = lid;
    }
    
    /**
     * Obtener la tapa de la copa 
     * @return tapa de la copa
     */
    public Lid getLid() {
        return lid;
    }
    
    /**
     * Retornar la altura de la base de la copa
     * @return HEIGHT_BASE altura de la base
     */
    public int getAlturaBase() {
        return HEIGHT_BASE; 
    }   
    
    /**
     * Calcular la altura de la copa
     * @param id de la copa
     * @return altura de la copa
     */
    public static int calculateHeight(int id) {
        return (2 * id) - 1;
    }
}
