import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * La clase Towe contiene todos la logica y configuracion para
 * la representacion del objeto en cuestion
 * 
 * @author: Jose Alejandro Martinez Arias
 *
 * @version: 2026-02-14
 */

public class Tower{
    
    /**
     * @param width ancho de la torre
     * @param maxHeight altura maxima de la torre
     * @param isOk si la operacion se realizo 
     * @param items guarda los objetos cups y lips de la torre
     */
    private int width;
    private int maxHeight;
    private boolean isOk;
    private ArrayList<Object> items; 
    
    /**
     * Crea una nueva torre
     * @param width ancho de la torre
     * @param maxHeight altura maxima que esta puede llegar a tener
     */
    public Tower(int width, int maxHeight){
        this.width = width;
        this.maxHeight = maxHeight;
        this.items = new ArrayList<>();
        this.isOk = true;
    }
    
    /**
     * Insertar una taza en la cima de la torre, si esta no se pasa de la altura maxima (maxHeight)
     * @param i id de la taza 
     */
    public void pushCup(int i){
        for(Object item : items){
            if (item instanceof Cup cup && cup.getId() == 1){
                isOk = false;
                return;
            }
        }
        
        int newCupHeight = (2 * i) - 1;
        if (currentHeight() + newCupHeight <= maxHeight){
            Cup newCup = new Cup(i,  "magenta");
            items.add(newCup);
            isOk = true;
        } else {
            isOk = false; 
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
                return;
            }
        }
        
        if(currentHeight() + 1 <= maxHeight){
            Lid newLid = new Lid(i, "blue");
            items.add(newLid);
            isOk = true;
        } else {
            isOk = false;
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
                return;
            }
        }
        isOk = false; 
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
                return;
            }
        }
        isOk = false;
    }
    
    /**
     * Calcular la altura total actual de la torre.
     */
    private int currentHeight(){
        int total = 0;
        for(Object item : items){
            if (item instanceof Cup cup){
                total += cup.getHeight();
            } else if (item instanceof Lid lid){
                total += lid.getHeight();
            }
        }
        return total;
    }
    
    /**
     * Retorna el estado actual de la torre, es decir si la ultima operacion se pudo realizar
     * @boolean isOk indica si la ultima operacion se pudo realizar
     */
    public boolean ok(){
        return isOk;
    }
    
    /**
     *  @return int retorna la altura total actual de la torre
     */
    public int height(){
        return currentHeight(); 
    }
    
    /**
     * Retorna los elementos de la torre desde la base hasta la cima  
     * @return elementos una lista {{"tipo","id"}}
     */
    public String[][] stackingItems() {
        String[][] elementos = new String[items.size()][2];
    
        for(int i = 0; i < items.size(); i++){
            Object item = items.get(i);
            if(item instanceof Cup cup){
                elementos[i][0] = "cup";
                elementos[i][1] = String.valueOf(cup.getId());
            } else if (item instanceof Lid lid){
                elementos[i][0] = "lid";
                elementos[i][1] = String.valueOf(lid.getId());
            }
        }
        return elementos;
    }
    
    /**
     * Retorna los numeros de las tazas tapadas por sus tapas ordenados de menor a mayor
     * Esto se sabe por los id
     */
    public int[] lidedCups() {
        ArrayList<Integer> cups = new ArrayList<>();
        ArrayList<Integer> lids = new ArrayList<>();
        ArrayList<Integer> unidos = new ArrayList();

        for (Object item : items) {
            if (item instanceof Cup cup){
                cups.add(cup.getId());
            } else if (item instanceof Lid lid){
                lids.add(lid.getId());
            }
        }
        
        for (Integer id : cups){
            if (lids.contains(id)){
                unidos.add(id);
            }
        }
        
        Collections.sort(unidos);
        int[] unidosOrden = new int[unidos.size()];
        for (int i = 0; i < unidos.size(); i++){
            unidosOrden[i] = unidos.get(i);
        }
        
        isOk = true;
        return unidosOrden;
    }
    
    // falta terminarlo
    /**
     * Organiza los elementos de la torre de mayor a menor dependiendo del id
     */
    public void orderTower(){
        ArrayList<Cup> tempsCups = new ArrayList();
        ArrayList<Lid> tempsLids = new ArrayList();
        
        for (Object item : items){
            if(item instanceof Cup cup){
                tempsCups.add(cup);
            } else if (item instanceof Lid lid){
                tempsLids.add(lid);
            }
        }
        items.clear();
    }
    
    /**
     * Invierte el orden de la torre, pero solo los que quepan dentro de la altura
     */
    public void reverseTower(){
        ArrayList<Object> copy = new ArrayList<>(items);
        items.clear();
        int i = copy.size() - 1; 
        
        while (i >= 0){
            Object actual = copy.get(i);
            int alturaBloque  = 0;
            
            if(actual instanceof Lid lid && i > 0 && copy.get(i-1) instanceof Cup cup && 
                cup.getId() == lid.getId()){
    
                alturaBloque = cup.getHeight() + 1;
                
                if(currentHeight() + alturaBloque <= maxHeight){
                    items.add(cup);
                    items.add(lid);
                }
                i -= 2;
            } else {
                alturaBloque = (actual instanceof Cup cup) ? cup.getHeight() : 1;
                
                if(currentHeight() + alturaBloque <= maxHeight){
                    if(actual instanceof Cup cup){
                        items.add(cup);
                    } else if (actual instanceof Lid lip){
                        items.add(lip);
                    }
                }
                i -= 1;
            }
        }
        isOk = true;
    }
    
    /**
     * Busca y eliminar una taza por su id
     * @param i id identificador de la taza que se removera
     */
    public void removeCup(int i){
        boolean found = false;
        for (int j = 0; j < items.size(); j++){
            Object item = items.get(j);
            if(item instanceof Cup cup && cup.getId() == i){
                cup.makeInvisible();
                items.remove(j);
                found = true;
                break;
            }
        }
        isOk = found;
    }
    
    /**
     * Busca y eliminar una tapa  por su id
     * @param i id de la tapa a remover
     */
    public void removeLid(int i){
        boolean found = false;
        for (int j = 0; j < items.size(); j++){
            Object item = items.get(j);
            if (item instanceof Lid lid && lid.getId() == i){
                lid.makeInvisible();
                items.remove(j);
                found = true;
                break;
            }
        }
        isOk = found;
    }
}
