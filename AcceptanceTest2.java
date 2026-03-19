import javax.swing.JOptionPane;

/**
 * Pruebas de aceptacion verificando el funcionamiento del simulador
 * * @author Jose Alejandro Martinez Arias
 * @version 2026-02
*/

public class AcceptanceTest2{
    /**
     * Prueba de aceptacion para verificar metodos del ciclo 2
     */
    public void aceptacion1() {
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
        
        esperar("Se intercambiara el techo de id 7 (esta en la cima) con techo id 5 (esta en la base)");
        String[] item1 = {"lid", "7"};
        String[] item2 = {"lid", "5"};
        t.swap(item1,item2);
        
        esperar("cubrimos las tazas que tiene sus tapas en la torre");
        t.cover(); 
        
        esperar("Se presentan datos finales de la torre en este punto");
        t.height();
        t.stackingItems();
        t.lidedCups();
        
        esperar("Se saldra del simulador");
        t.exit(); 
    }

    /**
     * Prueba de aceptacion para verificar metodos del ciclo 2
     */
    public void aceptacion2() {
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
    }
    
        private void esperar(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Paso de Prueba", JOptionPane.INFORMATION_MESSAGE);
    }
}