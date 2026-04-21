package tower;  
import java.util.ArrayList;
import java.util.Collections;
import tower.presentation.TowerCanvas;
import tower.presentation.Message;

/**
 * La clase CopyOfTower contiene toda la logica de la torre
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
    * @param isVisible controla si la torre se muestre
    * @param vista se encarga de moestrar la torre en pantalla
    */
   
    private ArrayList<ElementoTorre> items; 
    private Message message;
    private int width;
    private int maxHeight;
    private boolean isOk;
    private boolean isVisible;
    private TowerCanvas vista;

    /**
     * Crea una nueva torre
     * @param width ancho de la torre
     * @param maxHeight altura maxima que esta puede llegar a tener
     * @throws TowerException INVALID_DIMENSIONS si el ancho o alto invalidos
     */
    public Tower(int width, int maxHeight) throws TowerException{
        
        if (width <= 0 || maxHeight <= 0) {
            throw new TowerException(TowerException.INVALID_DIMENSIONS);
        }
        
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
     * throws TowerException INVALID_DIMENSIONS si el ancho o alto invalidos 
     *                      - OVERFLOW si la altura calculada supera el límite,
     *                      - DUPLICATE_ID si hay IDs repetidos.
     */
    public Tower(int n) throws TowerException{
        this(300, 70); 
        
        if (n <= 0) {
            throw new TowerException(TowerException.INVALID_DIMENSIONS);
        }
        
        for (int i = n; i >= 1; i--) {
        	pushCup((Cup) ElementFactory.crearCup("normal", i));
            if (!isOk) break; 
        }
        
        if (isVisible) vista.visible(items);
    }
    
    /**
     * Busca un elemento específico dentro de la lista de la torre.
     * @param tipo El tipo de elemento ("cup" o "lid").
     * @param id El identificador único del elemento.
     * @return ElementoTorre El objeto encontrado.
     * @throws TowerException NON_EXISTENT_ID si no se encuentra ningún elemento que coincida con el tipo e ID proporcionados.
     */
    private ElementoTorre buscarElemento(String tipo, int id) throws TowerException{
        ElementoTorre elemento = null;
        
        for (ElementoTorre e : items){
            if (e.esTipo(tipo) && e.getId() == id) {
                elemento = e;
                break;
            }
        }
        
        if(elemento == null) throw new TowerException(TowerException.NON_EXISTENT_ID);
        return elemento;
    }
    
    /**
     * Verifica si un elemento existe
     * @param tipo tipo de elemento
     * @param id identificador del elemento
     */
    private boolean existeElemento(String tipo, int id) {
        for (ElementoTorre e : items) {
            if (e.esTipo(tipo) && e.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Inserta una taza en la torre
     * @param i id de la taza 
     * @throws TowerException INVALID_ID si el id no es no numero entero o mayor que cero
     *                      - DUPLICATE_ID si ya existe el id en la torre (del mismo tipo de elemento)
     *                      - OVERFLOW si excede la altura maxima de la torre.
     */
    public void pushCup(int i) throws TowerException{
        if (i <= 0) {
            throw new TowerException(TowerException.INVALID_ID);
        }
        
        pushCup(ElementFactory.crearCup("normal", i));
    }
    
    /**
     * Añade una taza en la torre y verifica la integridad física.
     * @param nuevaTaza Objeto Cup que se desea insertar.
     * @throws TowerException DUPLICATE_ID si ya existe un elemento con el mismo ID,
     *                      -  OVERFLOW si al añadir la taza se supera la altura máxima permitida.
     */
    private void pushCup(Cup nuevaTaza) throws TowerException{
        isOk = false;
        int id = nuevaTaza.getId();
    
        if (existeElemento("cup", id)) {
            throw new TowerException(TowerException.DUPLICATE_ID);
        }
        
        items.add(nuevaTaza);
        if (heightCalculate() <= maxHeight) {
            isOk = true;
            if (isVisible) vista.visible(items);
        } else {
            items.remove(items.size() - 1);
            throw new TowerException(TowerException.OVERFLOW);
        }
    }

    /**
     * Inserta una tapa en la torre
     * @param i id de la tapa 
     * @throws TowerException INVALID_ID si el id no es no numero entero o mayor que cero
     *                      - DUPLICATE_ID si ya existe el id en la torre (del mismo tipo de elemento)
     *                      - OVERFLOW si excede la altura maxima de la torre.
     */
    public void pushLid(int i) throws TowerException{
        
        if (i <= 0) {
            throw new TowerException(TowerException.INVALID_ID);
        }
        
        pushLid(ElementFactory.crearLid("normal", i));
    }
    
    /**
     * Valida si la tapa puede entrar en la torre
     * @param nuevaTapa tapa a ingresar en la torre
     * @throws TowerException DUPLICATE_ID si ya existe un elemento con el mismo ID,
     *                       - OVERFLOW si al añadir la tapa se supera la altura máxima permitida.
     */
    private void pushLid(Lid nuevaTapa) throws TowerException{
        isOk = false;
        int id = nuevaTapa.getId();
        
        if (existeElemento("lid", id)){
            throw new TowerException(TowerException.DUPLICATE_ID);
        } 
        
        items.add(nuevaTapa);
        if (heightCalculate() <= maxHeight) {
            isOk = true;
            items.remove(items.size() - 1);
        } else {
            items.remove(items.size() - 1);
            throw new TowerException(TowerException.OVERFLOW);
        }
        
        if(isOk){
            boolean unida = false;
            
            if (!items.isEmpty()){
                ElementoTorre tope = items.get(items.size() - 1);
                unida = tope.intentarCubrirCon(nuevaTapa);
            } 
            
            if(!unida) {
                items.add(nuevaTapa);
            }
            
            refrescarEstadoTorre();
            if (isVisible) vista.visible(items);
        }
    }
   
    /**
     * Eliminar la taza de la cima de la torre, si el elemento en la cima es una taza.
     * @throws TowerException NON_EXISTENT_ID si la torre está vacía
     *                      - IMMOVABLE_ELEMENT si el tope no se deja quitar.
     */
    public void popCup() throws TowerException{
        isOk = false;
        
        if (items.isEmpty()) {
            throw new TowerException(TowerException.NON_EXISTENT_ID);
        }
        
        ElementoTorre tope = items.get(items.size() - 1);
        
        if (tope.validarRemocion("cup") && tope.esContenedorAbierto()) {
            if (isVisible) tope.makeInvisible();
            items.remove(items.size() - 1);
            isOk = true;
        } else throw new TowerException(TowerException.IMMOVABLE_ELEMENT);
        
        if (isVisible) vista.visible(items);
    }
    
    /**
     * Elimina la tapa de la cima de la torre, si el elemento en la cima es una tapa
     * @throws TowerException NON_EXISTENT_ID si la torre está vacía
     *                       - IMMOVABLE_ELEMENT si el tope no se deja quitar.
     */
    public void popLid() throws TowerException{
        isOk = false;
        
        if (items.isEmpty()) {
            throw new TowerException(TowerException.NON_EXISTENT_ID);
        }
        
        ElementoTorre tope = items.get(items.size() - 1);
    
        if (tope.validarRemocion("lid")) {
            if (isVisible) tope.makeInvisible();
            items.remove(items.size() - 1);
            isOk = true;
            refrescarEstadoTorre();
        } else throw new TowerException(TowerException.IMMOVABLE_ELEMENT);
        
        if (isVisible) vista.visible(items);
    }  
    
    /**
     * Retorna el estado actual de la torre, es decir si la ultima operacion se pudo realizar
     * @return boolean isOk indica si la ultima operacion se pudo realizar
     */
    public boolean ok(){
        message.showValidLastOperation(isVisible, isOk);
        return isOk;
    }
    
    /**
     * Metodo auxiliar para determinar la altura actual de la torre
     * @return altura actual de la torre
     */
    private int heightCalculate(){
        int totalHeight = 0;
        int[] alturaBase  = new int[items.size()];
        int[] alturaTecho = new int[items.size()];
        
        for (int i = 0; i < items.size(); i++) {
            ElementoTorre actual = items.get(i);
            int nivelBase = 0;
    
            for (int j = 0; j < i; j++) {
                ElementoTorre previo = items.get(j);
                boolean encaja = previo.puedeContener(actual);
                int obstaculo = encaja ? alturaBase[j] + previo.getHeightBase() : alturaTecho[j];
    
                if (obstaculo > nivelBase) {
                    nivelBase = obstaculo;
                }
            }
            alturaBase[i]  = nivelBase;
            alturaTecho[i] = nivelBase + actual.getHeight();
    
            if (alturaTecho[i] > totalHeight) {
                totalHeight = alturaTecho[i];
            }
        }

        return totalHeight;
    }
    
    /**
     *  Calcula la altura de la torre
     *  @return totalHeight la altura total actual de la torre
     */
    public int height(){
        int totalHeight = heightCalculate();
        message.showCurrentHeight(isVisible, totalHeight);
        return totalHeight;
    }
    
    /**
     * Retorna los elementos de la torre desde la base hasta la cima  
     * @return elementos en una lista {{"tipo","id"}}
     */
    public String[][] stackingItems(){
        ArrayList<String[]> elementos = new ArrayList<>();
        
        for (ElementoTorre e : items){
            elementos.addAll(e.generarReporte());
        }
        
        isOk = true;
        String[][] resultado = elementos.toArray(new String[0][0]);   
        message.showstackingItems(isVisible, resultado);
        return resultado; 
    }
    
    /**
     * Retorna los numeros de las tazas tapadas por sus tapas ordenados de menor a mayor, esto se sabe por los id
     * @return lista con los id de los elementos con su taza-techo en la torre
     */
    public int[] lidedCups(){
        ArrayList<Integer> unidos = new ArrayList<>();
    
        for (ElementoTorre e : items) {
            if (e.esTipo("cup") && !e.esContenedorAbierto()) {
                unidos.add(e.getId());
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
        
        refrescarEstadoTorre();
        
        for (int i = 0; i < items.size() - 1; i++) {
            ElementoTorre abajo = items.get(i);
            ElementoTorre arriba = items.get(i + 1);
            
            if (abajo.intentarCubrirCon(arriba)){
                items.remove(i + 1);
                i--;
            }
        }
        
        while (heightCalculate() > maxHeight && !items.isEmpty()) {
            ElementoTorre borrar = items.get(items.size() - 1);
            borrar.makeInvisible();
            items.remove(items.size() - 1);
        }
        
        isOk = true;
        if (isVisible) vista.visible(items);
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
     * Elimina una taza específica de la torre según su identificador.
     * @param id Identificador de la taza a eliminar.
     * @throws TowerException NON_EXISTENT_ID si la taza con ese ID no está en la torre.
     *                       - IMMOVABLE_ELEMENT si la taza tiene restricciones (como estar en el fondo 
     *                                                 o tener una tapa puesta) que impiden su remoción.
     */
    public void removeCup(int i) throws TowerException{
        isOk = false;
        ElementoTorre e = buscarElemento("cup", i);
        
        if (e.validarRemocion("cup")) {
            if (isVisible) e.makeInvisible();
            items.remove(e);
            isOk = true;
            refrescarEstadoTorre();
        } else {
            throw new TowerException(TowerException.IMMOVABLE_ELEMENT);
        }
        
        if (isVisible) vista.visible(items);
    }
    
    /**
     * Buscar y eliminar una tapa  por su id
     * @param i id de la tapa a remover
     * @throws TowerException NON_EXISTENT_ID si la tapa con ese ID no está en la torre,
     *                          - IMMOVABLE_ELEMENT si el poder del elemento impide que sea retirada.
     */
    public void removeLid(int i) throws TowerException{
        isOk = false;
        ElementoTorre e = buscarElemento("lid", i);
    
        if (e.validarRemocion("lid")) {
            if (isVisible) e.makeInvisible();

            items.remove(e);
            this.isOk = true;
            refrescarEstadoTorre();
        } else {
            throw new TowerException(TowerException.IMMOVABLE_ELEMENT);
        }
        
        if (isVisible) vista.visible(items);
    }
    
    /**
     * Salir del simulador
     */
    public void exit(){
        System.exit(0); 
    }
    
    /**
     * Hacer visible en pantalla, los distintos elementos de la torre 
     */
    public void makeVisible(){
        this.isVisible = true;
        vista.visible(items); 
        this.isOk = true;
    }

    /**
     * Desaparecer en pantalla los distintos elementos de la torre
     */
    public void makeInvisible(){
        vista.invisible(this.items);
        this.isVisible = false;
        this.isOk = true;
    }
    
    /**
     * Tapa las tazas que tienen sus tapas en la torre y no estan unidos
     */
    public void cover(){
        isOk = false;
        
        for (int i = 0; i < items.size(); i++) {
            ElementoTorre elemento = items.get(i);
            if (elemento.esTipo("lid")) {
            
                try {
                    ElementoTorre taza = buscarElemento("cup", elemento.getId());
                    
                    if (taza.intentarCubrirCon(elemento)) {
                    items.remove(i);
                    i--; 
                    isOk = true;
                    }
                } catch(TowerException e) {}
            }
        }
        
        while (heightCalculate() > maxHeight && !items.isEmpty()) {
            ElementoTorre borrar = items.get(items.size() - 1);
            borrar.makeInvisible();
            items.remove(items.size() - 1);
        }
        
        refrescarEstadoTorre();
        if (isOk && isVisible) vista.visible(items);
    }
    
    /**
     * Intercambia dos objetos de la torre
     * @param item1 {tipo, id} elemento a cambiar su posicion con el item2
     * @param item2 {tipo, id} elemento a cambiar su posicion con el item1
     * @throws TowerException NON_EXISTENT_ID si los elementos no existen
     *                       - OVERFLOW si el intercambio supera la altura máxima.
     */
    public void swap(String[] item1, String[] item2) throws TowerException {
        isOk = false;
        
        ElementoTorre e1 = buscarElemento(item1[0], Integer.parseInt(item1[1]));
        ElementoTorre e2 = buscarElemento(item2[0], Integer.parseInt(item2[1]));
        
        int index1 = items.indexOf(e1);
        int index2 = items.indexOf(e2);
        
        Collections.swap(items, index1, index2);
            
        if (heightCalculate() <= maxHeight) {
            isOk = true;
            refrescarEstadoTorre();
            if (isVisible) vista.visible(items);
        } else {
            Collections.swap(items, index1, index2);
            throw new TowerException(TowerException.OVERFLOW);
        }    
    }
    
    /**
     * Busca un intercambio que reduzca la altura actual de la torre.
     * @return una lista con los dos elementos que se deben cambiar de posicion {{tipo,id},{tipo,id}}
     */
    public String[][] swapToReduce() {
        isOk = false;
        int mejorAltura = heightCalculate();
        String[][] movimiento = null;
        
        for (int i = 0; i < items.size(); i++) {
            for (int j = i + 1; j < items.size(); j++) {
                Collections.swap(items, i, j);
                int alturaActual = heightCalculate();

                if (alturaActual < mejorAltura) {
                    isOk = true;
                    mejorAltura = alturaActual;
                    movimiento = new String[][]{ 
                        items.get(i).generarReporte().get(0), 
                        items.get(j).generarReporte().get(0)
                    };
                }
                Collections.swap(items, i, j);
            }
        }
        
        message.showSwapToReduce(isVisible,movimiento);
        return movimiento;
    }
    
    // lo nuevo del ciclo 4
    /**
     * Añade una taza en la torre
     * @param type tipo taza que entrara en la tore
     * @param i identificador de la nueva taza
     * @throws TowerException INVALID_ELEMENT_TYPE si el tipo es desconocido
     *                       - POWER_FAILED si el poder especial falla
     *                       - OVERFLOW si excede la altura 
     *                       - INVALID_ID si el id no es no numero entero o mayor que cero
     */
    public void pushCup(String type, int i) throws TowerException{
        if (i <= 0) {
            throw new TowerException(TowerException.INVALID_ID);
        }
        
        Cup nuevaTaza = ElementFactory.crearCup(type, i);
        
        this.pushCup(nuevaTaza);
        
        if (this.isOk) {
            nuevaTaza.aplicarPoderAlEntrar(this.items);
            refrescarEstadoTorre();
            procesarTurnos();
        } 
        
        if (isVisible) vista.visible(this.items);
    }
    
    /**
     * Añade una tapa en la torre
     * @param type tipo tapa que entrara en la tore
     * @param i identificador de la nueva tapa
     *  @throws TowerException INVALID_ELEMENT_TYPE si el tipo es desconocido
     *                       - POWER_FAILED si el poder especial falla
     *                       - OVERFLOW si excede la altura 
     *                       - INVALID_ID si el id no es no numero entero o mayor que cero
     */
    public void pushLid(String type, int i) throws TowerException{
        if (i <= 0) {
            throw new TowerException(TowerException.INVALID_ID);
        }
        
        Lid nuevaTapa = ElementFactory.crearLid(type, i);
        
        this.pushLid(nuevaTapa);
        
        if (this.isOk) {
            try {
                nuevaTapa.aplicarPoderAlEntrar(this.items);
                refrescarEstadoTorre(); 
                procesarTurnos();
                
            } catch (TowerException e) {
                ElementoTorre fallido = items.get(items.size() - 1);
                fallido.makeInvisible();
                this.items.remove(fallido);
                this.isOk = false;
                throw e; 
            }
        } 
        
        if (isVisible) vista.visible(items);
    }  
    
    /**
     * Se ejecutan los poderes de cada elemento de la torre,
     * que reaccionan a una accion que realiza el usuario
     */
    private void procesarTurnos() {
        for (int i = items.size() - 1; i >= 0; i--) {
            if (i < items.size()) { 
                items.get(i).reaccionarTurno(this.items); 
            }
        }
    }
    
    /**
     * Se ejecutan los poderes de cada elemento de la torre,
     * por cada accion que modifique la estructura de la torre 
     */
    private void refrescarEstadoTorre() {
        for (int i = items.size() - 1; i >= 0; i--) {
            if (i < items.size()) {
                items.get(i).aplicarPoderContinuo(this.items);
            }
        }
    }
}