import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * La clase Tower contiene toda la logica de la torre
 * * @author: Jose Alejandro Martinez Arias
 * @version: 2026-02-03
 */
public class Tower{
    /**
     * @param items guarda los distintos elementos de la torre
     * @param message gestiona las notificaciones y errores para el usuario
     * @param width ancho de la torre
     * @param maxHeight altura maxima que puede tener la torre
     * @param isOK verificar si la ultima operacion se pudo realizar
     * @param isVisible controla si la torre se muestra
     * @param COLORS colores disponibles para las tazas y copas
     * @param vista se encarga de moestrar la torre en pantalla
     */

    private ArrayList<ElementoTorre> items; 
    private Message message;
    private int width;
    private int maxHeight;
    private boolean isOk;
    private boolean isVisible;
    private static final String[] COLORS = {"red","yellow", "blue", 
        "green","magenta","black"};
    private TowerCanvas vista;

    /**
     * Crea una nueva torre
     * @param width ancho de la torre
     * @param maxHeight altura maxima que esta puede llegar a tener
     */
    public Tower(int width, int maxHeight){
        this.width = width;
        this.maxHeight = maxHeight;
        items = new ArrayList<ElementoTorre>();
        isOk = true;
        this.vista = new TowerCanvas(maxHeight);
        isVisible = false;
        message = new Message();
    }
    
    // segundo ciclo
    /**
     * Crea una torre dado n cantidad de tazas a añadir
     * @param n número de tazas 
     */
    public Tower(int n) {
        this(300, 70); 
        
        for (int i = n; i >= 1; i--) {
            pushCup(i);
            if (!isOk) break; 
        }
        
        if (isVisible) vista.visible(items);
    }
    
    /**
     * Insertar una taza en la cima de la torre, si esta no se pasa de la altura maxima (maxHeight)
     * @param i id de la taza 
     */
    public void pushCup(int i){
        isOk = false;
        
        if (buscarTazaPorId(i) != null) {
            message.errorPushCup(isVisible); 
            return;
        }
        int newCupHeight = Cup.calculateHeight(i);
        
        if (height() + newCupHeight <= maxHeight) {
            String color = COLORS[i % COLORS.length];
            Cup newCup = new Cup(i, color);
            items.add(new ElementoTorre(newCup));
            isOk = true;
            if (isVisible) makeVisible();
        } else {
            message.errorPushCupFull(isVisible);
        }
    }
    
    /**
    * Busca si hay una taza con ese id en la torre
    * @param id identificador de la taza
    * @return la taza
    */
    private Cup buscarTazaPorId(int id) {
        Cup cup = null;
        for (ElementoTorre e : items) {
            if (e.getCup() != null && e.getCup().getId() == id) {
                cup =  e.getCup();
                break;
            }
        }
        return cup;
    }
    
    /**
     * Insertar una tapa en la cima de la torre, si esta no se pasa de la altura maxima (maxHeight)
     * @param i id de la tapa 
     */
    public void pushLid(int i){
        isOk = false;

        if (laTapaYaExiste(i)) {
            message.errorPushLid(isVisible);
        } else if(height() + Lid.calculateHeight(i) > maxHeight)  {
            message.errorPushLidFull(isVisible); 
        } else {
            isOk = true;
        }
        
        if(isOk){
            Cup tazaConMismoId = buscarTazaPorId(i);
            String color = COLORS[i % COLORS.length];
            Lid nuevaTapa = new Lid(i, color);
        
            if (tazaConMismoId != null && esTopeDeTorre(tazaConMismoId)) {
                tazaConMismoId.setLid(nuevaTapa);
                nuevaTapa.setCup(tazaConMismoId);
            } else {
                items.add(new ElementoTorre(nuevaTapa));
            }
        
            if (isVisible) vista.visible(items);
        }
    }
    
    /**
     * Verifica si la tapa ya se encuentra en la torre
     * @param id a verificar para la tapa
     * @return boolean true si existe, caso contrario false
     */
    private boolean laTapaYaExiste(int id) {
        Lid suelta = buscarTapaSueltaPorId(id);
        Cup taza = buscarTazaPorId(id);
        return (suelta != null) || (taza != null && taza.getLid() != null);
    }

    /**
     * Verifica si la taza esta en la cima de la torre
     * @param taza taza a verificar si esta ne
     */
    private boolean esTopeDeTorre(Cup taza) {
        boolean isTazaTope = false;
        if (!items.isEmpty()){
            ElementoTorre tope = items.get(items.size() - 1);
            isTazaTope = tope.getCup() == taza;
        }
        
        return isTazaTope;
    }
    
    /**
    * Busca si hay una tapa sin su copa con ese id en la torre
    * @param id id a verificar de la tapa
    * @return tapa en el caso que se encuentra, si no null
    */
    private Lid buscarTapaSueltaPorId(int id) {
        Lid tapaSuelta = null;
        for (ElementoTorre e : items) {
            if (e.getLidOutCup() != null && e.getLidOutCup().getId() == id) {
                tapaSuelta = e.getLidOutCup();
                break;
            }
        }
        return tapaSuelta;
    }
    
    /**
     * Eliminar la taza de la cima de la torre, si el elemento en la cima es una taza.
     */
    public void popCup(){
        isOk = false;
        if (items.isEmpty()) {
            message.errorPopCup(isVisible);
        } else {
            ElementoTorre tope = items.get(items.size() - 1);
            
            if (tope.getLidOutCup() != null || (tope.getCup() != null && tope.getCup().getLid() != null)) {
                message.errorPopCup(isVisible);
            } else {
                isOk = true;
            }
        }
        
        if(isOk) {
            if (isVisible) items.get(items.size() - 1).hacerInvisible(); 
            items.remove(items.size() - 1);
        }
        if (isVisible) vista.visible(items);
    }
    
    /**
     * Elimina la tapa de la cima de la torre, si el elemento en la cima es una tapa
     */
    public void popLid() {
        isOk = false;
        
        if (!items.isEmpty()) {
            ElementoTorre tope = items.get(items.size() - 1);
    
            if (tope.getLidOutCup() != null) {
                if (isVisible) tope.hacerInvisible();
                items.remove(items.size() - 1);
                isOk = true;
                
            } else if (tope.getCup() != null && tope.getCup().getLid() != null) {
                if (isVisible) tope.hacerInvisible(); 
                items.remove(items.size() - 1);      
                isOk = true;
                
            } else {
                message.errorPopLid(isVisible);
            }
        
        } else {
            message.errorPopLid(isVisible);
        }
        
        if (isVisible) vista.visible(items);
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
     *  Calcula la altura de la torre
     *  @return totalHeight la altura total actual de la torre
     */
    public int height(){
        int totalHeight = 0;
        ElementoTorre anterior = null;
        
        for (ElementoTorre actual : items) {
            boolean sonTazas = (anterior != null && actual.getCup() != null && anterior.getCup() != null); 
            
            if (sonTazas && actual.getId() < anterior.getId() && anterior.getCup().getLid() == null) { 
            } else {
                totalHeight += actual.getAlturaTotal();
            }
            anterior = actual;
        }
        
        // message.showCurrentHeight(isVisible,totalHeight);
        return totalHeight;
    }
    
    /**
     * Retorna los elementos de la torre desde la base hasta la cima  
     * @return elementos en una lista {{"tipo","id"}}
     */
    public String[][] stackingItems() {
        ArrayList<String[]> elementos = new ArrayList<>();
        
        for (ElementoTorre e : items) {
            if (e.getCup() != null) {
                elementos.add(new String[]{"cup", String.valueOf(e.getCup().getId())});
                if (e.getCup().getLid() != null) {
                    elementos.add(new String[]{"lid", String.valueOf(e.getCup().getLid().getId())});
                }
            } else if (e.getLidOutCup() != null) {
                elementos.add(new String[]{"lid", String.valueOf(e.getLidOutCup().getId())});
            }
        }
        
        isOk = true;
        String[][] resultado = elementos.toArray(new String[0][0]);   
        message.showstackingItems(isVisible, resultado);
        return resultado; 
    }
    
    /**
     * Retorna los numeros de las tazas tapadas por sus tapas ordenados de menor a mayor
     * Esto se sabe por los id
     */
    public int[] lidedCups() {
        ArrayList<Integer> unidos = new ArrayList<>();
    
        for (ElementoTorre e : items) {
            if (e.getCup() != null && e.getCup().getLid() != null) {
                    unidos.add(e.getCup().getId());
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
     * Proceso para organizar la torre (orderTower/reverseTower)
     * @boolean isOrder indica como se organizara la torre
     */
    private void reorganizarTorre(boolean isOrder){
        if (isVisible) vista.invisible(items);
        if (isOrder) {
            Collections.sort(items, (a, b) -> Integer.compare(b.getId(), a.getId()));
        } else {
            Collections.reverse(items);
        }
        
        for (int i = 0; i < items.size() - 1; i++) {
            ElementoTorre abajo = items.get(i);
            ElementoTorre arriba = items.get(i + 1);
            
            if (abajo.getCup() != null && abajo.getCup().getLid() == null &&
                arriba.getLidOutCup() != null && arriba.getLidOutCup().getId() == abajo.getCup().getId()){
                
                abajo.getCup().setLid(arriba.getLidOutCup());
                arriba.getLidOutCup().setCup(abajo.getCup());

                items.remove(i + 1);
                i--; 
            }
        }
        
        ArrayList<ElementoTorre> elementos = new ArrayList<>();
        int alturaAcumulada = 0;
        ElementoTorre anterior = null;
        
        for (ElementoTorre actual : items) {
            int aporte = calcularAporteAltura(actual, anterior);
            
            if (alturaAcumulada + aporte <= maxHeight) {
                elementos.add(actual);
                alturaAcumulada += aporte;
                anterior = actual;
            } else {
                break;
            }
        }

        items.clear();
        items.addAll(elementos);
        
        isOk = true;
        if (isVisible) vista.visible(items);
    }
    
    private int calcularAporteAltura(ElementoTorre actual, ElementoTorre anterior) {
        int aporte = 0;
    
        if (anterior == null) {
            aporte = actual.getAlturaTotal(); 
        } else if (actual.getCup() != null && anterior.getCup() != null) {

            if (actual.getCup().getId() < anterior.getCup().getId() && anterior.getCup().getLid() == null){
                aporte = actual.getCup().getAlturaBase();
                
            } else {
                aporte = actual.getAlturaTotal();
            }
        
        } else {
            aporte = actual.getAlturaTotal();
        }
    
        return aporte;
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
        isOk = false;
        
        for (int j = 0; j < items.size(); j++) {
            ElementoTorre e = items.get(j);
            if (e.getCup() != null && e.getCup().getId() == i) {
                
                if (isVisible) e.hacerInvisible(); 
                items.remove(j);
                isOk = true;
                break;
            }
        }
        
        if (!isOk) {
            message.errorInfoPopCupId(isVisible);
        } else if (isVisible) {
            vista.visible(items);
        }
    }
    
    /**
     * Buscar y eliminar una tapa  por su id
     * @param i id de la tapa a remover
     */
    public void removeLid(int i){
        isOk = false;
        
        for (int j = 0; j < items.size(); j++) {
            ElementoTorre e = items.get(j);
            
            if (e.getLidOutCup() != null && e.getLidOutCup().getId() == i) {
                if (isVisible) e.hacerInvisible();
                items.remove(j);
                isOk = true;
                break;
                
            }else if (e.getCup() != null && e.getCup().getLid() != null 
                     && e.getCup().getLid().getId() == i) {
                
                if (isVisible) e.hacerInvisible();
                items.remove(j); 
                isOk = true;
                break;
            }
        }
        
        if (!isOk) {
            message.errorInfoPopLidId(isVisible);
        } else if (isVisible) {
            vista.visible(items);
        }
    }
    
    /**
     * Salir del simulador
     */
    public void exit() {
        System.exit(0); 
    }
    
    /**
     * Hacer visible en pantalla, los distintos elementos de la torre 
     */
    public void makeVisible() {
        this.isVisible = true;
        vista.visible(items); 
        this.isOk = true;
    }

    /**
     * Desaparecer en pantalla los distintos elementos de la torre
     */
    public void makeInvisible() {
        vista.invisible(this.items);
        this.isVisible = false;
        this.isOk = true;
    }
    
    /**
     * Tapa las tazas que tienen sus techos en la torre
     */
    public void cover() {
        isOk = false;
        
        for (int i = 0; i < items.size(); i++) {
            ElementoTorre elemento = items.get(i);
            
            if (elemento.getLidOutCup() != null) {
                int idBusqueda = elemento.getLidOutCup().getId();
                Cup tazaEncontrada = buscarTazaPorId(idBusqueda);
                
                if (tazaEncontrada != null && tazaEncontrada.getLid() == null) {
                    tazaEncontrada.setLid(elemento.getLidOutCup());
                    elemento.getLidOutCup().setCup(tazaEncontrada);
                    
                    if (isVisible) elemento.hacerInvisible();
                    items.remove(i);
                    i--; 
                    isOk = true;
                }
            }
        }
        
        if (isOk && isVisible) vista.visible(items);
    }
    
    /**
     * Intercambia dos objetos de la torre
     * @param item1 {tipo, id} elemento a cambiar su posicion con el item2
     * @param item2 {tipo, id} elemento a cambiar su posicion con el item1
     */
    public void swap(String[] item1, String[] item2) {
        isOk = false;
        
        String type1 = item1[0];
        int id1 = Integer.parseInt(item1[1]);
        
        String type2 = item2[0];
        int id2 = Integer.parseInt(item2[1]);
        
        int index1 = buscarIndiceElemento(type1, id1);
        int index2 = buscarIndiceElemento(type2, id2);
        
        if (index1 != -1 && index2 != -1 && index1 != index2) {
            Collections.swap(items, index1, index2);
            
            if (height() <= maxHeight) {
                isOk = true;
                if (isVisible) vista.visible(items);
            } else {
                Collections.swap(items, index1, index2);
            }
        }
    }
    
    /**
     * Método para encontrar la posición de un elemento en la torre.
     * @param tipo El elemento si es cup o lid
     * @param id identificador del elemento
     * @return indice posicion del elemento en la lista
     */
    private int buscarIndiceElemento(String tipo, int id) {
        for (int i = 0; i < items.size(); i++) {
            ElementoTorre e = items.get(i);
            if (tipo.equalsIgnoreCase("cup") && e.getCup() != null && e.getCup().getId() == id) {
                return i;
            }
            if (tipo.equalsIgnoreCase("lid") && e.getLidOutCup() != null && e.getLidOutCup().getId() == id) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Busca un intercambio que reduzca la altura actual de la torre.
     * @return una lista con los dos elementos que se deben cambiar de posicion {{tipo,id},{tipo,id}}
     */
    public String[][] swapToReduce() {
        isOk = false;
        int alturaInicial = height();
        String[][] movimiento = null;
        
        for (int i = 0; i < items.size(); i++) {
            for (int j = i + 1; j < items.size(); j++) {
                
                Collections.swap(items, i, j);
                
                if (height() < alturaInicial) {
                    isOk = true;
                    movimiento = new String[][]{ getInfo(i), getInfo(j)};
                    Collections.swap(items, i, j);
                    break; 
                }
                Collections.swap(items, i, j);
            }
        }
        
        message.showSwapToReduce(isVisible, movimiento);
        return movimiento;
    }
    
    private String[] getInfo(int i) {
        ElementoTorre e = items.get(i);
        String tipo = (e.getCup() != null) ? "cup" : "lid";
        int id = (e.getCup() != null) ? e.getCup().getId() : e.getLidOutCup().getId();
        return new String[]{tipo, String.valueOf(id)};
    }
}