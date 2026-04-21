package tower;
import java.util.ArrayList;
import shapes.Rectangle;

/**
 * Representa una tapa en la torre 
 * @author: Jose Alejandro Martinez Arias
 * @version: 1.0
 */
public class Lid extends ElementoTorre {
    /**
     * @param height altura del techo
     * @param shape forma del techo
     * @param width ancho del techo
     * @param cup copa del techo
     * @param HEIGHT_LID altura del techo
     * @param TYPE tipo de elemento
    */
   
    private int height;
    private Rectangle shape;
    private int width;
    private Cup cup;
    private static final int HEIGHT_LID = 1;
    private static final String TYPE = "lid";
   
    /**
     * Crea un nuevo techo
     * @param id identificador de la taza
     * @param color Color de la taza
     */
    public Lid(int id, String color) {
        super(id, color);
        this.height = HEIGHT_LID;  
        this.width = id;
        shape = new Rectangle();
        shape.changeColor(color); 
    }

    /**
     * Ajusta el tamaño del rectángulo de la tapa
     */
    private void setSizeScreen(int hTotalPix, int wTotalPix) {
        shape.changeSize(hTotalPix, wTotalPix);
    }
    
    /**
     * Establece la posición de la tapa en la pantalla
     */
    private void setPosition(int x, int yPuntoApoyo, int hTotalPix) {
        shape.setPosition(x, yPuntoApoyo);
    }
    
     /**
    * Asocia esta tapa con una taza específica.
    * @param cup copa de la tapa
    */
    protected void setCup(Cup cup) {
        this.cup = cup;
    }
    
    /**
     * Retorna la taza asociada a esta tapa (o null si está suelta).
     */
    protected Cup getCup() {
        return cup;
    }
    
    /**
     * Retorna la altura de la base de la tapa
     * @return altura de la base
     */
    @Override
    public int getHeightBase() {
        return height; 
    }
    
    /**
     * Retorna la altura de la base
     * @return altura de la base
     */
    @Override
    public int getHeight() {
        return height;
    }
    
    /**
     * Verifica si la tapa puede contener dentro de ella un elemento
     * @return false, una tapa no tiene interior
     */
    @Override
    public boolean puedeContener(ElementoTorre otro) {
        return false; 
    }
    
    /**
     * Verifica si la tapa esta abierta
     * @return false, una tapa no tiene interior
     */
    @Override
    public boolean esContenedorAbierto() {
        return false; 
    }
   
    /**
     * Hace visible la tapa en pantalla
     */
    @Override
    public void makeVisible() {
        this.isVisible = true;
        shape.makeVisible();
    }
    
    /**
     * Hace invisible la tapa en pantalla
     */
    @Override
    public void makeInvisible() {
        this.isVisible = false;
        shape.makeInvisible();
    }
    
    /**
     * Dibuja la tapa en la torre
     * @param xCentro centro de la torre
     * @param ySuelo base donde se posicionara la tapa
     * @param fh factor de la altura en pixeles
     * @param fw factor del ancho en pixeles
     */
    @Override
    public void dibujar(int xCentro, int ySuelo, int fH, int fW) {
        int hTotalPix = this.height * fH;
        int wTotalPix = this.width * fW;
        
        int x = xCentro - (wTotalPix / 2);

        this.setSizeScreen(hTotalPix, wTotalPix);
        this.setPosition(x, ySuelo, hTotalPix);
        
        this.makeVisible();
    }
    
    /**
     * Genera un informe de la tapa
     * @return ArrayList<String[]> informacion de la tapa (tipo elemento y id)
     */
    @Override
    public ArrayList<String[]> generarReporte() {
        ArrayList<String[]> reporte = new ArrayList<>();
        reporte.add(new String[]{"lid", String.valueOf(this.getId())});
        return reporte;
    }
    
    /**
     * Verificar si la tapa es el tipo de elemento que se busca
     * @param comando elemento solicitado
     * @return boolean true si es el elemento que se solicita, sino false
     */
    @Override
    public boolean esTipo(String comando) {
        return comando.equalsIgnoreCase(TYPE);
    }
    
    /**
     * Verifica si la tapa se puede cubrir con otro elemento
     * @param otro elemento con el que se cubrira
     * @boolen false, una tapa no tiene interior
     */
    @Override
    public boolean intentarCubrirCon(ElementoTorre otro) {
        return false;
    }
   
    /**
     * Retorna la figura que representa una tapa
     * @return Rectangle, elemento que representa una tapa
     */
    protected Rectangle getShape() {
        return shape;
    }
    
    /**
     * Retorna el ancho lógico de la tapa.
     * @width, ancho de la tapa 
     */
    protected int getWidth() {
        return width;
    }
    
    /**
     * Verifica si la tapa se puede remover de la torre que se quiere remover
     * @param comando elemento solicitado a remover
     * @return boolean true si es el del mismo tipo solicitado, sino false
     */
    @Override
    public boolean validarRemocion(String comando) {
        return esTipo(comando); 
    }
    
    @Override
    public void reaccionarTurno(ArrayList<ElementoTorre> items) {
    }
    
}