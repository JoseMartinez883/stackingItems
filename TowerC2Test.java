import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

/**
 * Pruebas de unidad para el Ciclo 2
 * @author Jose Alejandro Martinez Arias
 * @version 2026-03-03
 */
public class TowerC2Test {
    private Tower tower;

    @Before
    public void setUp() {
        tower = new Tower(50, 70); 
    }

    @Test
    public void accordingMAShouldCreateTowerWithNItems() {
        tower = new Tower(5); 
        assertEquals(9, tower.height()); 
        assertTrue(tower.ok());
    }

    @Test
    public void accordingMAShouldNotCreateTowerExceedingMaxHeight() {
        tower = new Tower(1000); 
        assertFalse(tower.ok());
    }

    @Test
    public void accordingMAShouldSwapValidElements() {
        tower.pushCup(10); 
        tower.pushLid(20);
        String[] item1 = {"cup", "10"};
        String[] item2 = {"lid", "20"};
        
        tower.swap(item1, item2);
        assertTrue(tower.ok());
    }

    @Test
    public void accordingMAShouldNotSwapIfIdDoesNotExist() {
        tower.pushCup(10);
        String[] item1 = {"cup", "10"};
        String[] item2 = {"cup", "999"}; 
        
        tower.swap(item1, item2);
        assertFalse(tower.ok());
    }

    @Test
    public void accordingMAShouldCoverMatchingIds() {
        tower.pushCup(5);
        tower.pushCup(2);
        tower.pushLid(5);
        
        tower.cover();
        assertTrue("La operación cover debe ser exitosa", tower.ok());
    }

    @Test
    public void accordingMAShouldNotCoverIfNoMatch() {
        tower.pushCup(1);
        tower.pushLid(2); 
        int alturaAntes = tower.height();
        
        tower.cover();
        assertFalse(tower.ok());
    }

    @Test
    public void accordingMAShouldFindMoveToReduceHeight() {
        tower.pushCup(2);  
        tower.pushCup(20); 
        
        String[][] sugerencia = tower.swapToReduce();
        
        assertNotNull("El simulador debió encontrar un movimiento para reducir la altura", sugerencia);
        assertTrue(tower.ok());
    }

    @Test
    public void accordingMAShouldNotFindReductionIfAlreadyOptimal() {
        tower.pushCup(5); 
        tower.pushCup(2);  
        
        String[][] sugerencia = tower.swapToReduce();
        
        assertNull("No debería sugerir movimientos si la torre ya está en su estado más óptimo", sugerencia);
    }
    
    @After
    public void tearDown() {
    }
}