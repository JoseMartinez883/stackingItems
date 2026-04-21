package presentation;
import javax.swing.JOptionPane;
import java.util.ArrayList;

/**
 * Encargada de representar en pantalla un mensaje que 
 * valida la no o buena ejecucion de una operacion.
 * 
 * @author Jose Alejandro Martinez Arias
 * @version (16-02-2026)
*/

public class Message{
    private static  final String TITULO_ERROR = "Error en la torre";
    private static final String TITULO_INFORMACION = "Informacion de la torre";

    /**
     * Mostrar en pantalla, si la ultima operacion si se puedo o no realizar
     * @param isVisible si la torre es visible en pantalla
     * @param isOk si la ultima accion en la torre se realizo
     */
    public void showValidLastOperation(boolean isVisible, boolean isOk) {
        String infoLastOperation = (isOk) ? "La ultima operacion se pudo realizar" : "La operacion no se pudo realizar";
        if (isVisible) { 
            JOptionPane.showMessageDialog(null,infoLastOperation, 
            		TITULO_ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Mostrar en pantalla, la altura actual de la torre
     * @param isVisible si la torre es visible en pantalla
     * @param height altura actual de la torre
     */
    public void showCurrentHeight(boolean isVisible, int height) {
        if (isVisible) { 
            JOptionPane.showMessageDialog(null,
                "La altura total de la torre es: " + height + " cm.",
                TITULO_INFORMACION, JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Mostrar en pantalla, los elementos actuales de la torre
     * @param isVisible si la torre es visible en pantalla
     * @param elements elementos de la torre
     */
    public void showstackingItems(boolean isVisible, String[][] elements){
       String  infoElements = "{";
       if (isVisible) { 
            for(int i = 0; i < elements.length; i++){
                infoElements += "{\"" + elements[i][0] + "\", \"" + elements[i][1] + "\"}";
            
                if (i < elements.length - 1) {
                    infoElements += ", ";
                }
            }
            infoElements += "}";
            JOptionPane.showMessageDialog(null,infoElements,
            		TITULO_INFORMACION, JOptionPane.INFORMATION_MESSAGE);
        } 
    }
    
    /**
     * Error cuando la torre es demasiado alta para los píxeles de la pantalla.
     */
    public void errormakeVisibleScreen() {
        JOptionPane.showMessageDialog(null, "La torre es demasiado alta para la pantalla",
        		TITULO_ERROR, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Mostrar en pantalla, los elementos con tazas tapadas ordenados de menor a mayor
     * @param isVisible si la torre es visible en pantalla
     * @param unidos elementos unidos en la torre (taza + tapa)
     */
    public void showLidedCups(boolean isVisible, ArrayList<Integer> unidos){
        String infoElementsUnited = "{";
        if (isVisible) { 
            for (int i = 0; i < unidos.size(); i++) {
                infoElementsUnited += unidos.get(i);
                if (i < unidos.size() - 1) {
                    infoElementsUnited += ", ";
                }
            }  
            infoElementsUnited += "}";
            JOptionPane.showMessageDialog(null,infoElementsUnited,
            		TITULO_INFORMACION, JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Mostrar en pantalla, los elementos que se deben realizar en la torre
     * @param isVisible si la torre es visible en pantalla
     * @param movmiento, movimiento que se debe realizar en la torre
     */
    public void showSwapToReduce(boolean isVisible, String[][] movimiento){
       String  infoElements = "{";
       if (isVisible && movimiento != null) { 
            for(int i = 0; i < movimiento.length; i++){
                infoElements += "{\"" + movimiento[i][0] + "\", \"" + movimiento[i][1] + "\"}";
            
                if (i < movimiento.length - 1) {
                    infoElements += ", ";
                }
            }
            infoElements += "}";
            JOptionPane.showMessageDialog(null, infoElements,
            		TITULO_INFORMACION, JOptionPane.INFORMATION_MESSAGE);
        } else {
            if(isVisible && movimiento == null){
                JOptionPane.showMessageDialog(null, "No hay mas cambios posibles para reducir la torre",
                		TITULO_ERROR, JOptionPane.INFORMATION_MESSAGE);    
            }
        }
    }

}
