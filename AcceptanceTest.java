import javax.swing.JOptionPane;

/**
 * Prueba para verificar el buen funcionamiento del simulador 
 * @Author Jose Alejandro Martinez Arias
 * @version 2026-03-03
 */
public class AcceptanceTest {
    public void casoAceptacion() {

        Tower t = new Tower(300, 30); 
        t.makeVisible();
        
        // Taza 1: Altura 1 (2*1 -1)
        t.pushCup(1);
        esperar("espera");
        // Taza 3: Altura 5 (2*3 -1)
        t.pushCup(3);
        esperar("espera");
        // Taza 2: Altura 3 (2*2 -1)
        t.pushCup(2);
        
        esperar("Se deben ver 3 tazas desordenadas (IDs: 1, 3, 2).");
        
        t.pushLid(2); 
        esperar("espera");
        t.pushLid(1);
        
        esperar("Se añadieron tapas a la 2 y 1. La tapa debe ser del color de la taza.");

        // Alturas: Taza1(1) + Taza3(5) + Taza2(3) + Tapa2(1) + Tapa1(1) = 11 cm
        t.height(); 
        
        esperar("espera");
        t.orderTower();
        esperar("Torre Ordenada: Base(3) -> Medio(2+Tapa) -> Cima(1+Tapa).");

        
        t.lidedCups(); // deben ser 1 y 2 que son las que estan tapadas

        esperar("espera");
        t.reverseTower();
        
        esperar("Torre Invertida. Los bloques Taza+Tapa deben haberse movido juntos.");

    
        t.popCup(); 
        esperar("Se debió eliminar la Taza 3 que estaba en la cima.");
        
        t.pushLid(3);
        esperar("espera");
        
        t.popLid(); 
        esperar("espera");
        
        t.pushCup(20); // no debe funcionar
        
        esperar("espera");
        t.height(); 
       
        t.pushLid(4);
        esperar("espera");
        
        t.pushCup(3);
        esperar("espera");
        
        t.pushCup(4);
        esperar("espera");
        
        t.orderTower();
        esperar("espera");
        
        t.reverseTower();
        esperar("espera");
        
        t.height(); 
        esperar("espera");
        
        t.exit(); 
    }

    private void esperar(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Paso de Prueba", JOptionPane.INFORMATION_MESSAGE);
    }
}