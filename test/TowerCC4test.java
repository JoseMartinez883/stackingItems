package test;
import tower.*;
import javax.swing.JOptionPane;

/**
 * Pruebas de aceptacion del ciclo 4
 * @author Jose Alejandro Martinez Arias
 */

public class TowerCC4test {

    private void esperar(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Paso de Prueba", JOptionPane.INFORMATION_MESSAGE);
    }

    public void testCiclo4() {
        try {
            Tower torre = new Tower(100, 45);
            torre.makeVisible();
    
            esperar("A continuación, probaremos el poder de la OpenerCup");
    
            torre.pushLid("normal", 5);
            torre.pushLid("normal", 7);
            
            esperar("Se han agregado dos tapas normales\n" +
                "Al insertar una openerCup se eliminan las dos tazas anteriores");
                
            torre.pushCup("opener", 4);
            
            esperar( "Se eliminaron las tapas");
        
            // herarchical
            torre.pushCup("normal", 3);
            torre.pushCup("normal", 2);
        
            esperar("se han agregado dos tazas\n" +
                "Ahora insertaremos una HierarchicalCup\n" +
                "Al insertar se mira como se hunde hasta el fondo desplazando a las menores.");
            
            torre.pushCup("hierarchical", 5);
        
            esperar("La taza 5 empujó a las demás y esta en la base");

            // fearFullLid
            esperar("Intentaremos meter una FearfulLid sin su taza en la torre.\n" +
                "No se permitira ingresar");
            
            try {
                torre.pushLid("fearful", 7);
            } catch (TowerException e) {
                esperar("Capturado con éxito: " + e.getMessage());
            }

            esperar("Ahora meteremos su taza y luego la tapa FearfulLid.\n" +
                "Ahora si entra y no se deja eliminiar.");
            
            torre.pushCup("normal", 7);
            torre.pushLid("fearful", 7);
    
            // CrazyLid
            torre.pushCup("normal", 6);
            
            esperar("Insertamos la taza. Ahora meteremos una CrazyLid .\n" +
                    "Se posicionara por debajo de la taza.");
                
            torre.pushLid("crazy", 6);
            
            esperar("La tapa loca se ubico como base."); 
            
            // BombCup
            torre.pushCup("bomb", 1);
            
            esperar("Se ha insertado la TimeBombCup.");
                
            torre.pushLid("normal", 1); 
            
            esperar("La bomba cambió de color. Al dar clic,\n" +
                    "haremos el movimiento final para su explocion.");
                    
            torre.pushLid("normal", 2); 
            
            esperar("Se saldra del simulador");
            torre.exit(); 

        } catch (TowerException e) {
            esperar("Error inesperado en la prueba: " + e.getMessage());
        } catch (Exception e) {
                Log.record(e);
                esperar(Log.UNEXPECTED_ERROR);
        }
    }
}