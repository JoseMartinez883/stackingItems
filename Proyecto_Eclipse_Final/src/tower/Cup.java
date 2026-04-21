package tower;
import java.util.ArrayList;
import shapes.Rectangle;

/**
 * Representa una taza usando tres rectángulos (base y paredes)
 * @author: Jose Alejandro Martinez Arias
 * @version: 2026-02-14
 */
public class Cup extends ElementoTorre{
    
    /**
     * @param height, altura logico de la torre
     * @param width, ango logico de la torre
     * @param base, representa la base de la taza
     * @param leftWall, pared izquierda de la taza
     * @param rigthWall, pared derecha de la taza
     * @param WALL_THICKNESS grosor de las paredes
     * @param HEIGHT_BASE altura de la base 
     * @param lid tapa correspodiente a la copa
     * @param TYPE tipo de elemento 
     */
    
    private int height;
    private int width;
    
    private Rectangle base;
    private Rectangle leftWall;
    private Rectangle rightWall;
    private Lid lid; 
    private static final int WALL_THICKNESS = 10;
    private static final int HEIGHT_BASE = 1;
    private static final String TYPE = "cup";
    
    /**
     * Creo una taza
     * @param id identificador de la taza
     * @param color color de la taza
     */
    public Cup(int id, String color) {
        super(id, color);
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
     * Ajusta el tamaño en pantalla de la copa
     * @param hTotalPix altura total de la copa en pixeles
     * @param wTotalPix ancho total de la copa en pixeles
     * @param hBasePix altura de la base en pixeles
     */
    private void setSizeScreen(int hTotalPix, int wTotalPix, int hBasePix) {
        base.changeSize(hBasePix, wTotalPix);
        if (id > 1){
            int wallHeight = hTotalPix - hBasePix;
            leftWall.changeSize(wallHeight, WALL_THICKNESS); 
            rightWall.changeSize(wallHeight, WALL_THICKNESS);
        }
    }
    
    /**
     * Ajusta la posicion de la copa en pantalla
     */
    private void setPosition(int x, int yPuntoApoyo, int hTotalPix, int wTotalPix, int hBasePix) {
        base.setPosition(x, yPuntoApoyo);
        
        if (id > 1) {
            int hParedes = hTotalPix - hBasePix;
            int ySuperiorParedes = yPuntoApoyo - hParedes;
            leftWall.setPosition(x, ySuperiorParedes);
            rightWall.setPosition(x + wTotalPix - WALL_THICKNESS, ySuperiorParedes);
        }
    }
    
    /**
     * Hacer visible la copa en pantalla
     */
    @Override
    public void makeVisible() {
        this.isVisible = true;
        base.makeVisible();
        if (id > 1) {
            leftWall.makeVisible();
            rightWall.makeVisible();
        }
    }
    
    /**
     * Retorna el ancho lógico de la copa.
     * @width, ancho de la tapa 
     */
    protected int getWidth() { 
        return width; 
    }
    
    /**
     * Asocia esta taza con su tapa
     * @param lid tapa de la taza
     */
    protected void setLid(Lid lid) {
        this.lid = lid;
    }
    
    /**
     * Retorna la tapa que tiene la taza (contenida)
     * @return tapa de la taza
    */
    protected Lid getLid() {
        return lid;
    }
  
    
    /**
     * Retorna la altura de la base de la copa
     * @return int altura de la base de la copa
     */
    @Override
    public int getHeightBase() {
        return HEIGHT_BASE; 
    }
    
    /**
     * Retorna la altura de la copa
     * @return int altura de la copa
     */
    @Override
    public int getHeight() { 
        int heightCup = height;
        if(lid != null){
            heightCup += lid.getHeight();
        }
        return heightCup;
    }
    
    /**
     * Verifica si la copa puede contener otro elemento (interior)
     * @param otro elemento que intentar contener dentro de ella
     * @return boolean true si lo puede contener, sino false
     */
    @Override
    public boolean puedeContener(ElementoTorre otro) {
        return this.esContenedorAbierto() && (otro.getId() < this.getId());
    }
    
    /**
     * Verifica si la copa esta abierta (sin su tapa)
     * @return boolen true si lo es, sino false
     */
    @Override
    public boolean esContenedorAbierto(){
        return this.lid == null;
    }
    
    /**
     * Hace invisible la copa en pantalla
     */
    @Override
    public void makeInvisible() {
        isVisible = false;
        
        base.makeInvisible();
        if (id > 1) {
            leftWall.makeInvisible();
            rightWall.makeInvisible();
        }

        if (lid != null) {
            lid.makeInvisible();
        }
    }
    /**
     * Genera un informe de la copa
     */
    @Override
    public ArrayList<String[]> generarReporte() {
        ArrayList<String[]> reporte = new ArrayList<>();
        reporte.add(new String[]{"cup", String.valueOf(this.id)}); 
        if (this.lid != null) {
            reporte.add(new String[]{"lid", String.valueOf(this.lid.getId())});
        }
        return reporte;
    }
    
    /**
     * Dibuja la copa en la torre
     * @param xCentro centro de la torre
     * @param ySuelo base donde se posicionara la copa
     * @param fh factor de la altura en pixeles
     * @param fw factor del ancho en pixeles
     */
    @Override
    public void dibujar(int xCentro, int ySuelo, int fH, int fW) {
        int hTotalPix = this.height * fH;
        int wTotalPix = this.width * fW;
        int hBasePix = this.HEIGHT_BASE * fH;
        
        int x = xCentro - (wTotalPix / 2);

        this.setSizeScreen(hTotalPix, wTotalPix, hBasePix);
        this.setPosition(x, ySuelo, hTotalPix, wTotalPix, hBasePix);
        this.makeVisible();

        if (this.lid != null) {
            int techoTazaY = ySuelo - hTotalPix;
            this.lid.dibujar(xCentro, techoTazaY, fH, fW + 6);
        }
    }
        
    /**
     * Verificar si la copa es el tipo de elemento que se busca
     * @param comando elemento solicitado
     * @return boolean true si es el elemento que se solicita, sino false
     */
    @Override
    public boolean esTipo(String comando) {
        boolean esTipo = false;
        
        if(comando.equalsIgnoreCase(TYPE)){
            esTipo = true;
        } else if (!this.esContenedorAbierto()){
            if(lid.esTipo(comando)) esTipo = true;
        }
        
        return esTipo;
    }
    
    /**
     * Verifica si la copa se puede cubrir con otro elemento
     * @param otro elemento con el que se cubrira
     * @boolen true si se puede cubrir, sino false
     */
    @Override
    public boolean intentarCubrirCon(ElementoTorre otro) {
        if (this.lid == null && otro.getId() == this.id && otro.esTipo("lid")) {
            if(!otro.intentarCubrirCon(this)){
                this.lid = (Lid) otro; 
                this.lid.setCup(this);
                return true; 
            }
        }
        return false;
    }

    /**
     * Retorna la base de la copa
     * @return Rectangle base de la copa
     */
    protected Rectangle getBase() {
        return base;
    }
    
    /**
     * Retorna la pared izquierda de la copa
     * @return Rectangle pared izquierda 
     */
    protected Rectangle getLeftWall() {
        return leftWall;
    }
    
    /**
     * Retorna la pared derecha de la copa
     * @return Rectangle pared derecha 
     */
    protected Rectangle getRightWall() {
        return rightWall;
    }
    
    /**
     * Verifica si la copa se puede remover de la torre que se quiere remover
     * @param comando elemento solicitado a remover
     * @return boolean true si es el del mismo tipo solicitado, sino false
     */
    @Override
    public boolean validarRemocion(String comando) {
        boolean canRemoved = false;    
        if (esTipo(comando)) {
            if (this.lid != null) {
                canRemoved =  this.lid.validarRemocion("lid");
            } else canRemoved = true; 
        }
        return canRemoved;
    }
    
    @Override
    public void reaccionarTurno(ArrayList<ElementoTorre> items) {
    }
}
