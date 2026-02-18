import javax.swing.JOptionPane;
import java.util.ArrayList;
/**
 * Encargada de representar en pantalla un mensaje que 
 * valida la no buena ejecucion de una operacion.
 * 
 * @author Jose Alejandro Martinez Arias
 * @version (16-02-2026)
*/

public class Message{
    private final String tituloError = "Error en la torre";
    private final String TituloInformacion = "Informacion de la torre";
    
    /**
     *  Mostar en pantalla el error de agregar una taza, si ya se encuentra
     */
    public void errorPushCup(boolean isVisible) {
        if (isVisible) { 
            JOptionPane.showMessageDialog(null, 
                "La taza con el id solicitado ya se encuentra.", 
                tituloError, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Mostar en pantalla el error de agregar una taza, si la torre esta llena
     */
    public void errorPushCupFull(boolean isVisible) {
        if (isVisible) { 
            JOptionPane.showMessageDialog(null, 
                "No hay espacio suficiente en la torre para la taza.", 
                tituloError, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    /**
     * Mostar en pantalla el error de agregar un techo, si ya se encuentra
     */
    public void errorPushLid(boolean isVisible) {
        if (isVisible) { 
            JOptionPane.showMessageDialog(null, 
                "El techo con el id solicitado ya se encuentra.", 
                tituloError, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Mostar en pantalla el error de agregar un techo, si la torre esta llena
     */
    public void errorPushLidFull(boolean isVisible){
        if (isVisible) { 
            JOptionPane.showMessageDialog(null, 
                "No hay espacio suficiente en la torre para el techo.",
                tituloError, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Mostrar en pantalla el error elimnar una taza, si esta en la cima de la torre
     */
    public void errorPopCup(boolean isVisible) {
        if (isVisible) { 
            JOptionPane.showMessageDialog(null,"No hay una taza en la cima de la torre", 
                tituloError, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Mostrar en pantalla el error elimnar un techo, si esta en la cima de la torre
     */
    public void errorPopLid(boolean isVisible) {
        if (isVisible) { 
            JOptionPane.showMessageDialog(null,"No hay un techo en la cima de la torre", 
                tituloError, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Mostrar en pantalla, si la ultima operacion si se puedo o no realizar
     */
    public void showValidLastOperation(boolean isVisible, boolean isOk) {
        String infoLastOperation = (isOk) ? "La ultima operacion se pudo realizar" : "La operacion no se pudo realizar";
        if (isVisible) { 
            JOptionPane.showMessageDialog(null,infoLastOperation, 
            tituloError, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Mostrar en pantalla, la altura actual de la torre
     */
    public void showCurrentHeight(boolean isVisible, int height) {
        if (isVisible) { 
            JOptionPane.showMessageDialog(null,
                "La altura total de la torre es: " + height + " cm.",
                TituloInformacion, JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Mostrar en pantalla, los elementos actuales de la torre
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
                TituloInformacion, JOptionPane.INFORMATION_MESSAGE);
        } 
    }
    
    /**
     * Mostrar en pantalla, los elementos con tazas tapadas ordenados de menor a mayor
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
                TituloInformacion, JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Mostrar en pantalla, el error de eliminar una taza por su id cuando no se encuentra
     */
    public void errorInfoPopCupId(boolean isVisible){
        if (isVisible) { 
            JOptionPane.showMessageDialog(null, 
                "La taza a eliminar no se encuentra en la torre",
                tituloError, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Mostrar en pantalla, el error de eliminar un techo por su id cuanto no se encuentra
     */    
    public void errorInfoPopLidId(boolean isVisible){
        if (isVisible) { 
            JOptionPane.showMessageDialog(null, 
                "El techo a eliminar no se encuentra en la torre",
                tituloError, JOptionPane.ERROR_MESSAGE);
        } 
    }
    
    /**
     * Mostrar en pantalla, el error cuando la altura de la torre es mayor que la pantalla
     */
    public void errormakeVisibleScreen(){
        JOptionPane.showMessageDialog(null, "La torre es demasiado alta para la pantalla",
            tituloError, JOptionPane.ERROR_MESSAGE);
    }
}