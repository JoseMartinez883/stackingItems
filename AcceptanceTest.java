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
        
        esperar("El limite de la torre es 30 cm");
         
        t.pushCup(10); 
        esperar("Se agrego una taza de id 10, su altura es de 19 cm");
        
        t.pushLid(2);
        esperar("Se agrego una tapa de id 2");
        
        t.height();
        
        t.pushCup(1);
        esperar("Se agrego una taza de id 1, su altura es de 1 cm");
       
        t.pushCup(3);
        esperar("Se agrego una taza de id 3, su altura es de 5 cm");
        
        t.pushCup(2);
        esperar("Se agrego una taza de id 2, su altura de 3 cm");
        
        esperar("Se intentara agregar un techo de id 2, pero dara error");
        t.pushLid(2); 
        esperar("Debio dar error ya que el techo de id 2 ya se encuentra");
        
        t.pushLid(1);
        esperar("Se agrego una tapa de id 1");

        
        t.height(); 
        
        esperar("Organizamos la torre de mayor a menor");
        
        t.orderTower();
        
        esperar("Se mostraran las tapas y tapas unidas");
        t.lidedCups(); 

        esperar("Se hara reverse al orden de la torre");
        t.reverseTower();
        
        esperar("Borramos la taza que estaba arriva de la torre");
        
        t.popCup(); 
        esperar("Se debió eliminar la Taza con id 10 que estaba en la cima.");
        
        t.pushLid(3);
        esperar("Se agrego una tapa de id 3");
        
        esperar("Se eliminara la tapa de la cima de la torre");
        t.popLid(); 
        
        esperar("Se agregara una taza de id 20, pero como la altura maxima es 30, no lo dejara");
        t.pushCup(20); 
        
        t.height(); 
       
        esperar("Se agregara una tapa de id 4");
        t.pushLid(4);
        
        esperar("se agregara una tapa de id 3");
        t.pushCup(3);
        
        esperar("Se agregara uan tapa de id 4");
        t.pushCup(4);
        
        esperar("Ordenamos la torre");
        t.orderTower();
        
        esperar("Reversamos el orden de la torre");
        t.reverseTower();
        
        t.height(); 
        
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