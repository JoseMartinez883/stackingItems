import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
/**
 * La clase Towe contiene todos la logica y configuracion para
 * la representacion del objeto en cuestion
 * * @author: Jose Alejandro Martinez Arias
 * @version: 2026-02-14
 */
public class Tower{
    /**
     * @param FLOOR_MARGIN longitud entre el suelo y la base de la torre
     * @param FACTOR_HEIGHT factor de altura en pixeles
     * @param FACTOR_WIDTH factor de ancho en pixeles
     * @param items guarda los distintos elementos de la torre
     * @param escala dibujar la regla de la altura maxima de la torre
     * @param message gestiona las notificaciones y errores para el usuario
     * @param width ancho de la torre
     * @param maxHeight altura maxima que puede tener la torre
     * @param isOK verificar si la ultima operacion se pudo realizar
     * @param isVisible controla si la torre se muestra
     * @param COLORS colores disponibles para las tazas y copas
     */
    private static final int FLOOR_MARGIN = 30;
    private static final int FACTOR_HEIGHT = 15; 
    private static final int FACTOR_WIDTH  = 40;
    private ArrayList<Object> items; 
    private Escala escala;
    private Message message;
    private int width;
    private int maxHeight;
    private boolean isOk;
    private boolean isVisible;
    private static final String[] COLORS = {"red","yellow", "blue", 
        "green","magenta","black"};

    /**
     * Crea una nueva torre
     * @param width ancho de la torre
     * @param maxHeight altura maxima que esta puede llegar a tener
     */
    public Tower(int width, int maxHeight){
        this.width = width;
        this.maxHeight = maxHeight;
        items = new ArrayList<>();
        isOk = true;
        isVisible = false;
        message = new Message();
        escala = null;
    }
    
    /**
     * Insertar una taza en la cima de la torre, si esta no se pasa de la altura maxima (maxHeight)
     * @param i id de la taza 
     */
    public void pushCup(int i){
        for(Object item : items){
            if (item instanceof Cup cup && cup.getId() == i){
                isOk = false;
                message.errorPushCup(isVisible);
                return;
            }
        }
        
        int newCupHeight = (2 * i) - 1;
        if (currentHeight() + newCupHeight <= maxHeight){
            String color = COLORS[i % COLORS.length];
            Cup newCup = new Cup(i,  color);
            items.add(newCup);
            isOk = true;
            if (isVisible) makeVisible(); 
        } else {
            isOk = false; 
            message.errorPushCupFull(isVisible);
        }
    }
    
    /**
     * Insertar una tapa en la cima de la torre, si esta no se pasa de la altura maxima (maxHeight)
     * @param i id de la tapa 
     */
    public void pushLid(int i){
        for( Object item : items){
            if(item instanceof Lid lid && lid.getId() == i){
                isOk = false;
                message.errorPushLid(isVisible);
                return;
            }
        }
        
        if(currentHeight() + 1 <= maxHeight){
            String color = COLORS[i % COLORS.length];
            Lid newLid = new Lid(i, color);
            items.add(newLid);
            isOk = true;
            if (isVisible) makeVisible();
        } else {
            isOk = false;
            message.errorPushLidFull(isVisible);
        }
    }
    
    /**
     * Eliminar la taza de la cima de la torre, si el elemento en la cima es una taza.
     */
    public void popCup(){
        if(!items.isEmpty()){
            Object top = items.get(items.size() - 1);
            if (top instanceof Cup cup){
                cup.makeInvisible(); 
                items.remove(items.size() - 1);
                isOk = true;
                if (isVisible) makeVisible();
                return;
            }
        }
        isOk = false;
        message.errorPopCup(isVisible);
    }
    
    /**
     * Elimina la tapa de la cima de la torre, si el elemento en la cima es una tapa
     */
    public void popLid() {
        if (!items.isEmpty()){
            Object top = items.get(items.size() - 1);
            if (top instanceof Lid lid){
                lid.makeInvisible();
                items.remove(items.size() - 1);
                isOk = true;
                if (isVisible) makeVisible();
                return;
            }
        }
        isOk = false;
        message.errorPopLid(isVisible);
    }
    
    /**
     * Calcular la altura total actual de la torre.
     */
    private int currentHeight() {
        if (items.isEmpty()) return 0;
        
        int yBaseAnterior = 0; 
        int hAnterior = 0;     
        int alturaMaxima = 0;
        boolean esPrimero = true;
    
        for (Object item : items) {
            int hActual = (item instanceof Cup c) ? c.getHeight() : 1;
            int idActual = obtenerId(item);
            Object anterior = (items.indexOf(item) > 0) ? items.get(items.indexOf(item) - 1) : null;
            int yBaseActual;
    
            if (esPrimero) {
                yBaseActual = 0;
                esPrimero = false;
            } else {
                if (anterior instanceof Cup && item instanceof Cup && idActual < obtenerId(anterior)) {
                    yBaseActual = yBaseAnterior + 1;
                } else {
                    yBaseActual = yBaseAnterior + hAnterior;
                }
            }
            int topeActual = yBaseActual + hActual;
            
            if (topeActual > alturaMaxima) {
                alturaMaxima = topeActual;
            }
            yBaseAnterior = yBaseActual;
            hAnterior = hActual;
        }
        
        return alturaMaxima;
    }  
    
    /**
     * Retorna el estado actual de la torre, es decir si la ultima operacion se pudo realizar
     * @boolean isOk indica si la ultima operacion se pudo realizar
     */
    public boolean ok(){
        message.showValidLastOperation(isVisible, isOk);
        return isOk;
    }
    
    /**
     *  @return int retorna la altura total actual de la torre
     */
    public int height(){
        int height = currentHeight();
        message.showCurrentHeight(isVisible, height);
        isOk = true;
        return height;
    }
    
    /**
     * Retorna los elementos de la torre desde la base hasta la cima  
     * @return elementos una lista {{"tipo","id"}}
     */
    public String[][] stackingItems() {
        String[][] elementos = new String[items.size()][2]; 
        
        for(int i = 0; i < items.size(); i++){
            Object it = items.get(i);
            String tipo = (it instanceof Cup) ? "cup" : "lid";
            int id = (it instanceof Cup) ? ((Cup)it).getId() : ((Lid)it).getId();
            elementos[i] = new String[]{tipo, String.valueOf(id)};
        }
        message.showstackingItems(isVisible, elementos);
        isOk = true;
        return elementos;
    }
    
    /**
     * Retorna los numeros de las tazas tapadas por sus tapas ordenados de menor a mayor
     * Esto se sabe por los id
     */
    public int[] lidedCups() {
        ArrayList<Integer> unidos = new ArrayList<>();
    
        for (int i = 0; i < items.size() - 1; i++) {
            Object actual = items.get(i);
            Object siguiente = items.get(i + 1);
            if (actual instanceof Cup cup && siguiente instanceof Lid lid) {
                if (cup.getId() == lid.getId()) {
                    unidos.add(cup.getId());
                }
            }
        }
        
        Collections.sort(unidos);
        int[] resultado = new int[unidos.size()];
        for (int i = 0; i < unidos.size(); i++) {
            resultado[i] = unidos.get(i);
        }
        message.showLidedCups(isVisible, unidos);
        isOk = true;
        return resultado;
    }
    
    /**
     * Retornar el id del objeto ya sea taza o techo
     */
    private int obtenerId(Object obj) {
        return (obj instanceof Cup c) ? c.getId() : ((Lid)obj).getId();
    }
    
    /**
     * Retonar la altura de los objetos ya sea taza o techo o ambos en la misma lista
     */
    private int calcularAlturaelementos(Object[] elemento) {
        int total = 0;
        for (Object obj : elemento) {
            total += (obj instanceof Cup c) ? c.getHeight() : ((Lid)obj).getHeight();
        }
        return total;
    }

    /**
     * Proceso común para agrupar, aplicar una acción (Order/reverse) y reconstruir la torre.
     */
    private void reorganizarTorre(boolean isOrder){
        ArrayList<Object[]> elementos  = new ArrayList<>();
        
        for(int i = 0; i < items.size(); i++){
            Object actual = items.get(i);
            if (actual instanceof Cup cup && (i + 1 <items.size()) &&
                items.get(i +1) instanceof Lid lid && lid.getId() == cup.getId()){
                elementos.add(new Object[]{actual, items.get(i+1)});
                i++;
            } else {
                elementos.add(new Object[]{actual});
            }
        }
        
        if(isOrder){
            Collections.sort(elementos, (a, b) -> Integer.compare(obtenerId(b[0]), obtenerId(a[0])));
        } else {
            Collections.reverse(elementos);
        }
        
        items.clear();
        for (Object[] elemento : elementos){
            int alturaElementos = calcularAlturaelementos(elemento);
            if (currentHeight() + alturaElementos <= maxHeight) {
                for (Object item : elemento) items.add(item);
            }
        }
        
        if (this.isVisible) makeVisible();   
        isOk = true;
    }
    
    /**
     * Invertir el orden de la torre, pero solo los que quepan dentro de la altura
     */
    public void reverseTower(){
        reorganizarTorre(false);
    }
    
    /**
     * Organiza los elementos de la torre de mayor a menor dependiendo del id
     */
    public void orderTower(){
        reorganizarTorre(true);
    }
    
    /**
     * Buscar y eliminar una taza por su id
     * @param i id identificador de la taza que se removera
     */
    public void removeCup(int i){
        for (int j = 0; j < items.size(); j++){
            Object item = items.get(j);
            if(item instanceof Cup cup && cup.getId() == i){
                cup.makeInvisible();
                items.remove(j);
                if (j < items.size() && items.get(j) instanceof Lid lid) {
                    if (lid.getId() == i) {
                        lid.makeInvisible();
                        items.remove(j); 
                    }
                }  
            }
            isOk = true;
            if (isVisible) makeVisible();
            return;
        }
        message.errorInfoPopCupId(isVisible);
    }
    
    /**
     * Buscar y eliminar una tapa  por su id
     * @param i id de la tapa a remover
     */
    public void removeLid(int i){
        for (int j = 0; j < items.size(); j++){
            Object item = items.get(j);
            if (item instanceof Lid lid && lid.getId() == i){
                lid.makeInvisible();
                items.remove(j);
                if (j > 0){
                    Object posibleTaza = items.get(j-1);
                    if(posibleTaza instanceof Cup cup && cup.getId() == i){
                        cup.makeInvisible();
                        items.remove(j - 1); 
                    }
                }
                isOk = true;
                if (isVisible) makeVisible(); 
                break;
            }
        }
        message.errorInfoPopLidId(isVisible);
    }
    
    /**
     * Salir del simulador
     */
    public void exit() {
        System.exit(0); 
    }
    
    /**
     * Hacer visible en pantalla, los distintos objetos de la torre 
     */
    public void makeVisible() {
        Canvas canvas = Canvas.getCanvas();
        int ySuelo = canvas.getHeight() - FLOOR_MARGIN;
        int xCentro = canvas.getWidth() / 2;
    
        if (this.escala == null) this.escala = new Escala(20, ySuelo, maxHeight, FACTOR_HEIGHT, 1);
        this.isVisible = true;
        escala.makeVisible();
        int yActual = ySuelo - FACTOR_HEIGHT;
        Object anterior = null;
    
        for (Object item : items) {
            yActual = calcularNuevoNivelSuelo(yActual, item, anterior);
            dibujarItem(item, xCentro, yActual);
            anterior = item;
        }
        this.isOk = true;
    }
   
    private int calcularNuevoNivelSuelo(int yActual, Object item, Object anterior) {
        if (anterior == null) return yActual;
    
        int idActual = obtenerId(item);
        if (anterior instanceof Cup && item instanceof Cup && idActual < obtenerId(anterior)) {
            return yActual - FACTOR_HEIGHT;
        }
        int hAnterior = (anterior instanceof Cup c) ? c.getHeight() : 1;
        return yActual - (hAnterior * FACTOR_HEIGHT);
    }
    
    private void dibujarItem(Object item, int xCentro, int yPos) {
        int id = obtenerId(item);
        int hUnidades = (item instanceof Cup c) ? c.getHeight() : 1;
        
        int hTotalPix = hUnidades * FACTOR_HEIGHT;
        int wTotalPix = id * FACTOR_WIDTH;
        int hBasePix = 1 * FACTOR_HEIGHT; 
        int xPos = xCentro - (wTotalPix / 2);
    
        if (item instanceof Cup cup) {
            cup.setSizeScreen(hTotalPix, wTotalPix, hBasePix);
            cup.setPosition(xPos, yPos, hTotalPix, wTotalPix, hBasePix);
            cup.makeVisible();
        } else if (item instanceof Lid lid) {
            lid.setSizeScreen(FACTOR_HEIGHT, FACTOR_WIDTH);
            lid.setPosition(xPos, yPos);
            lid.makeVisible();
        }
    }

    /**
     * Desaparecer en pantalla los distintos elementos de la torre
     */
    public void makeInvisible() {
        if (escala != null) escala.makeInvisible();
        for (Object item : items) {
            if (item instanceof Cup cup)  cup.makeInvisible();
            else if (item instanceof Lid lid)  lid.makeInvisible();
        }
        this.isVisible = false;
        this.isOk = true;
    }
}
