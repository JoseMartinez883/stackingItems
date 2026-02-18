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
     */
    private int id;
    private int height;
    private int width;
    private String color;
    
    private Rectangle base;
    private Rectangle leftWall;
    private Rectangle rightWall;
    
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
        if (id > 1) {
            int wallHeight = hTotalPix - hBasePix;
            leftWall.changeSize(wallHeight, 10); 
            rightWall.changeSize(wallHeight, 10);
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
            rightWall.setPosition(x + wTotalPix - 10, ySuperiorParedes);
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
}
