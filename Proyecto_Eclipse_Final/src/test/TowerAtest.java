package test;
import javax.swing.JOptionPane;
import tower.*;
    

/**
 * Pruebas de aceptacion de lo mejor del proyecto
 * 
 * @author Jose Alejandro Martinez Arias
 * @version 1.0
 */

public class TowerAtest{
    
    private void esperar(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Paso de Prueba", JOptionPane.INFORMATION_MESSAGE);
    }

    public void caso1() {
        try {
            Tower torre = new Tower(100, 45);
            torre.makeVisible();

            esperar("Prueba de Aceptación 1\n" +
                    "Comenzaremos añadiendo tapas normales.");

            torre.pushLid("normal", 5);
            torre.pushLid("normal", 7);
            
            esperar("Ahora entra una taza OpenerCup\n" +
                    "Se ve como las tapas se destruyen");
                    
            torre.pushCup("opener", 8);

            esperar("Ahora insertaremos una HierarchicalCup.\n" +
                    "Se hundira hasta el fondo desplazando a la openerCup.");
                    
            torre.pushCup("hierarchical", 11);

            esperar("Insertaremos una taza y la taparemos con su FearfulLid");
            
            torre.pushCup("normal", 5);
            torre.pushLid("fearful", 5);

            esperar("Insertaremos una CrazyLid (y su taza).\n" +
                    "La tapa se movera por debajo para volverse la base de su taza.");
                    
            torre.pushCup("normal", 6);
            torre.pushLid("crazy", 6);

            esperar("Insertaremos una BombCup\n");
            torre.pushCup("bomb", 3);

            esperar("Haremos un movimiento insertando la taza (4).");
            torre.pushCup("normal", 4);

            esperar("Insertaremos otra taza y la taza BombCup explota.\n" +
                    "Destruyendo la taza de abajo y la de arriba.");
            torre.pushCup("normal", 2); 

            esperar("Agregamos una taza");
                
            torre.pushCup("normal", 9); 
        
            esperar("Llamaremos al metodo swapToReduce.");
            
            String[][] sugerencia = torre.swapToReduce();
        
            esperar("Se aplica el movimiento usando swap");
            
            torre.swap(sugerencia[0], sugerencia[1]);
            
            esperar("Aplicamos otro movimiento para reducir");
            
            String[][] sugerencia2 = torre.swapToReduce();
            
            torre.swap(sugerencia2[0], sugerencia2[1]);
            
            esperar("Se sale del sistema");
            torre.exit();
        } catch (TowerException e) {
            esperar("Error inesperado en Caso 1: " + e.getMessage());
        } catch (Exception e) {
                Log.record(e);
                esperar(Log.UNEXPECTED_ERROR);
        }
    }
    
    public void caso2() {
        try {
            Tower torre = new Tower(100, 45);
            torre.makeVisible();

            esperar("Bienvenido a la Prueba de Aceptación 2");

            esperar("Insertamos una HierarchicalCup.");
            torre.pushCup("hierarchical", 10);

            esperar("Ahora insertaremos una taza y la protegeremos con su FearfulLid.");
            torre.pushCup("normal", 3);
            torre.pushLid("fearful", 3);


            esperar("Intentaremos eliminar la HierarchicalCup (está en el fondo).\n");
            try {
                torre.removeCup(10);
            } catch (TowerException e) {
                esperar("Excepción capturada " + e.getMessage());
            }
            
            esperar("Intentaremos eliminar la FearfulLid usando removeCup.");
            
            try {
                torre.removeLid(3);  
            } catch (TowerException e) {
                esperar("Excepción capturada " + e.getMessage());
            }

            esperar("Agregamos varios elementos a la torre");
                
            torre.pushCup("normal", 5);
            torre.pushLid("normal", 4);
            torre.pushCup("normal", 7);
            torre.pushCup("normal", 8);
            torre.pushLid("normal", 7);
    
            esperar("Ejecutamos el metodo reverse.");
                    
            torre.reverseTower();
    
            esperar("Aplicamos el metodo order");
                    
            torre.orderTower();
    
            esperar("Insertamos una openerCup\n" +
                "Esta vez no elimina nada la tapa de abajo es miedosa");
                    
            torre.pushCup("opener", 1);
            
            esperar("Se sale del sistema");
            torre.exit();

        } catch (TowerException e) {
            esperar("Error inesperado en Caso 2: " + e.getMessage());
        } catch (Exception e) {
                Log.record(e);
                esperar(Log.UNEXPECTED_ERROR);
        }
    }    

}