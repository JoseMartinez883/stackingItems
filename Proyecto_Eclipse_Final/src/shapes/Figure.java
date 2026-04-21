package shapes;

public abstract class Figure {
    /**
     * @param xPosition posicion en el eje x de la figura
     * @param yPosition position en el eje y de la figura
     * @param color color de la figure
     * @param isVisible bandera booleana que indica la visibilidad de la figura en pantalla
     */
    protected int xPosition;
    protected int yPosition;
    protected String color;
    protected boolean isVisible;
    
    /**
     * Constructor de la figura
     * @param x posicion en el eje x de la figura
     * @param y posicion en el eje y de la figure
     * @param color color de la fibandera booleana que indica la visibilidad de la figura en pantallagura
     * @param isVisible bandera booleana que indica la visibilidad de la figura en pantalla
     */
    public Figure(int xPosition, int yPosition, String color){
        this.color = color;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        isVisible = false;
    }
    
    /**
     * Make this figure visible. If it was already visible, do nothing.
     */
    public void makeVisible() {
        isVisible = true;
        draw();
    }
    
    /**
     * Make this figure invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }
    
    
    /**
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor) {
        color = newColor;
        draw();
    }
    
    /**
     * Modificar la posicion de la figura 
     * @param x posicion en el eje x de la figura
     * @param y posicion en el eje y de la figura
     */
    public void setPosition(int x, int y) {
        erase();
        this.xPosition = x;
        this.yPosition = y;
        draw();
    }
    
    /**
     * Move the figure a few pixels to the right.
     */
    public void moveRight() {
        moveHorizontal(20);
    }
    
    /**
     * Move the figure a few pixels to the left.
     */
    public void moveLeft() {
        moveHorizontal(-20);
    }
    
    /**
     * Move the figure a few pixels up.
     */
    public void moveUp() {
        moveVertical(-20);
    }
    
    /**
     * Move the figure a few pixels down.
     */
    public void moveDown() {
        moveVertical(20);
    }
    
    /**
     * Move the figure horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
        draw();
    }
    
    /**
     * Move the figure vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance) {
        erase();
        yPosition += distance;
        draw();
    }
    
    /**
     * Slowly move the figure horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance) {
        int delta;
        int distanciaAbsoluta = distance;
        if(distance < 0) {
            delta = -1;
            distanciaAbsoluta = -distance;
        } else {
            delta = 1;
        }
        for(int i = 0; i < distanciaAbsoluta; i++){
            xPosition += delta;
            draw();
        }
    }
    
    /**
     * Slowly move the figure vertically.
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance) {
        int delta;
        int distanciaAbsoluta = distance;
        
        if(distance < 0) {
            delta = -1;
            distanciaAbsoluta = -distance;
        } else {
            delta = 1;
        }
        for(int i = 0; i < distanciaAbsoluta; i++){
            yPosition += delta;
            draw();
        }
    }
    
    /**
     * Draw the figure with current specifications on screen.
     */
    protected abstract void draw();
    
    /**
     * Erase the figure on screen.
     */
    protected void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}