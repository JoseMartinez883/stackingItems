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
         Tower t = new Tower(100, 30);
         t.makeVisible();
        
         t.pushCup(5);
         t.pushLid(2);  
         t.pushCup(5);  
         t.pushCup(2); 
         t.pushCup(3); 
         
         esperar("revertimos el orden de la torre");
         //t.reverseTower(); 

         esperar("cubrimos las tazas que tiene sus tapas en la torre");
         t.cover(); 
         
         esperar("se termino la prueba");
         t.exit(); 
    }

    /**
     * Prueba de aceptacion para verificar metodos del ciclo 2
     */
    public void aceptacion2() {
        Tower tower = new Tower(100, 35);
        tower.makeVisible();
        
        tower.pushCup(2);  
        tower.pushCup(5); 
        
        int alturaInicial = tower.height();
        esperar("Altura actual de la torre: " + alturaInicial + " cm.");
        
        // que cambios se deben hacer 
        String[][] sugerencia = tower.swapToReduce();
        
        // realizo el cambio, para tener una altura menor
        tower.swap(sugerencia[0], sugerencia[1]);
            
        int alturaFinal = tower.height();
        esperar("Altura actual de la torre: " + alturaFinal + " cm.");
        if(alturaInicial > alturaFinal){
            tower.exit(); 
        }
    }
    
        private void esperar(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Paso de Prueba", JOptionPane.INFORMATION_MESSAGE);
    }
}