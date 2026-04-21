package test;
import tower.*;
import javax.swing.JOptionPane;

/**
 * Pruebas de aceptacion verificando el funcionamiento del simulador
 * * @author Jose Alejandro Martinez Arias
 * @version 2026-02
*/

public class AcceptanceTest2{

    /**
     * Muestra un mensaje en pantalla
     * @param mensaje informacion a presentar al usuario
     */
    private void esperar(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Paso de Prueba", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Prueba de aceptación para verificar métodos de movimiento e intercambio.
     */
    public void aceptacion1() {
        try {
            Tower t = new Tower(100, 35);
            t.makeVisible();
            
            t.pushLid(5);
            esperar("espera");
            
            t.pushLid(3);
            esperar("espera");
            
            t.pushLid(1);
            esperar("espera");
            
            t.pushLid(2);
            esperar("espera");
            
            t.pushLid(6);
            esperar("espera");
            
            t.pushCup(3);
            esperar("espera");
            
            t.pushCup(1);
            esperar("espera");
            
            t.pushCup(5);
            esperar("espera");
            
            t.pushCup(6);
            esperar("espera");
            
            t.pushCup(4);
            esperar("espera");
            
            t.pushLid(7);
            esperar("espera");
            
            esperar("Se intercambiará el techo de id 7 (cima) con techo id 5 (base)");
            String[] item1 = {"lid", "7"};
            String[] item2 = {"lid", "5"};
            
            try {
                t.swap(item1, item2);
            } catch (TowerException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error en Swap", JOptionPane.WARNING_MESSAGE);
            }

            esperar("cubrimos las tazas que tiene sus tapas en la torre");
            t.cover(); 
            
            esperar("Se presentan datos finales de la torre en este punto");
            t.height();
            t.stackingItems();
            t.lidedCups();
            
            esperar("Se saldra del simulador");
            t.exit(); 
        } catch (TowerException e) {
            esperar("Fallo crítico en la prueba: " + e.getMessage());
        } catch (Exception e) {
                Log.record(e);
                esperar(Log.UNEXPECTED_ERROR);
        }
    }

    /**
     * Prueba de aceptación para verificar metodos del ciclo 2
     */
    public void aceptacion2() {
        try {
            Tower t = new Tower(100, 35);
            t.makeVisible();
            
            t.pushLid(5);
            esperar("espera");
            
            t.pushLid(3);
            esperar("espera");
            
            t.pushLid(1);
            esperar("espera");
            
            t.pushLid(2);
            esperar("espera");
            
            t.pushLid(6);
            esperar("espera");
            
            t.pushCup(3);
            esperar("espera");
            
            t.pushCup(1);
            esperar("espera");
            
            t.pushCup(5);
            esperar("espera");
            
            t.pushCup(6);
            esperar("espera");
            
            t.pushCup(4);
            esperar("espera");
            
            t.pushLid(7);
            esperar("espera");
            
            
            String[][] sugerencia = t.swapToReduce();
            t.swap(sugerencia[0], sugerencia[1]);
            
            sugerencia = t.swapToReduce();
            t.swap(sugerencia[0], sugerencia[1]);
        
            esperar("Se presentan datos finales de la torre en este punto");
            t.height();
            t.stackingItems();
            t.lidedCups();
            
            esperar("Se saldra del simulador");
            t.exit(); 

        } catch (TowerException e) {
            esperar("Error: " + e.getMessage());
        } catch (Exception e) {
                Log.record(e);
                esperar(Log.UNEXPECTED_ERROR);
        }
    }
}