/**
 * Clase que contiene los diferentes elementos que puede tener la pila de elementos
 * La cual solo puede estar compuestas por cups y lids
* * @author: Jose Alejandro Martinez Arias
 * @version: 2026-03-28
 */

public class ElementoTorre{
    /**
     * @param cup copa de la torre
     * @param lidOutCup corresponde a una taza que no esta junta a su copa
     */
    private Cup cup;
    private Lid lidOutCup;
    
    /**
     * Constructor para la copa, en el caso que el elemento que se registre sea una copa
     */
    public ElementoTorre(Cup cup) {
        this.cup = cup;
        this.lidOutCup = null;
    }
    
    /**
     * Constructor para la taza, en el caso que el elemento que se registre sea una tapa
     */
    public ElementoTorre(Lid lidOutCup) {
        this.cup = null;
        this.lidOutCup = lidOutCup;
    }
    
    /**
     * Obtener la copa, en el caso que el elemento sea una copa 
     * @return cup una copa 
     */
    public Cup getCup() {
        return cup;
    }
    
    /**
     * Obtener la taza, en el caso que el elemento se una tapa
     * @return lidOutCup una tapa
     */
    public Lid getLidOutCup() {
        return lidOutCup;
    }
    
    /**
     * Consigue el id del elemento 
     * @return id del elemento actual
     */
    public int getId() {
        if (cup != null) {
            return cup.getId();
        }
        return lidOutCup.getId();
    }
    
    /**
     * Obtener la altura del elemento actual
     * @return int altura del elemento
     */
    public int getAlturaTotal() {
        if (cup != null) {
            int h = cup.getHeight();
            if (cup.getLid() != null) h += cup.getLid().getHeight();
            return h;
        }
        
        return lidOutCup.getHeight();
    }
    
    /**
     * Se encarga de posicionar y mostrar el dibujo en el canvas.
     * @param xCentro Coordenada X central de la torre.
     * @param yPos Coordenada Y donde debe apoyarse la base.
     * @param fH Factor de altura (píxeles por unidad).
     * @param fW Factor de ancho (píxeles por ID).
     */
    public void dibujar(int xCentro, int yPos, int fH, int fW) {
        
        if (cup != null) {
            int hPix = cup.getHeight() * fH;
            int wPix = cup.getId() * fW;
            int hBasePix = 1 * fH; 
            int xPos = xCentro - (wPix / 2);
            
            cup.setSizeScreen(hPix, wPix, hBasePix);
            cup.setPosition(xPos, yPos, hPix, wPix, hBasePix);
            cup.makeVisible();
            
            if (cup.getLid() != null) {
                int anchoTapaEspecial = wPix + 6;
                int xPosTapa = xPos - 3;
                
                cup.getLid().setSizeScreen(fH, anchoTapaEspecial); 
                cup.getLid().setPosition(xPosTapa, yPos - hPix); 
                cup.getLid().makeVisible();
            }
        } else if (lidOutCup != null) {
            int wPix = lidOutCup.getId() * fW;
            int xPos = xCentro - (wPix / 2);
            
            lidOutCup.setSizeScreen(fH, wPix);
            lidOutCup.setPosition(xPos, yPos);
            lidOutCup.makeVisible();
        }
    }
    
    /**
     * Hace que todos los componentes del elemento desaparezcan.
     */
    public void hacerInvisible() {
        if (cup != null) {
            cup.makeInvisible();
            if (cup.getLid() != null) cup.getLid().makeInvisible();
        } else if (lidOutCup != null) {
            lidOutCup.makeInvisible();
        }
    }
}