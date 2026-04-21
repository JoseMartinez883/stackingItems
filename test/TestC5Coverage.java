package test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tower.*;
import tower.TowerException;

import org.junit.jupiter.api.BeforeEach;

/**
 * Test correspondientes el ciclo 5 (Adicionales para cubrir con >= 75% Test Coverage)
 * @author Jose Alejandro Martinez Arias
 * @version 05-04-2026
*/
public class  TestC5Coverage{

    private Tower tower;

    @BeforeEach
    public void setUp() throws TowerException{
        tower = new Tower(50, 200);
    }
    
    /**
     * Verifica si dos ArrayList 2D tienen los mismos elementos
     * @param expected elementos que se esperan que estan en la lista
     * @param actual elementos actuales a verificar si son los esperados
    */
    private void assertStackingItems(String[][] expected, String[][] actual) {
        assertEquals(expected.length, actual.length, "Distinta longitud de stackingItems");
        
        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals(expected[i], actual[i], "Diferencia en el elemento de la posición " + i);
        }
    }
    
    @Test
    public void accordingMAshouldThrowExceptionOnInvalidDimensions() {
        try {
            new Tower(-1, 0);
            fail("Se esperaba TowerException por dimensiones inválidas");
        } catch (TowerException e) {
            assertEquals(TowerException.INVALID_DIMENSIONS, e.getMessage());
        }
    }
    
    @Test
    public void accordingMAshouldThrowExceptionOnInvalidId() {
        try {
            Tower towerValida = new Tower(10, 10);
            towerValida.pushCup("normal", -5);
            fail("Se esperaba TowerException por ID inválido");
        } catch (TowerException e) {
            assertEquals(TowerException.INVALID_ID, e.getMessage());
        }
    }

    @Test
    public void accordingMAshouldHandleInvalidFactoryTypes() {
        try {
            ElementFactory.crearCup("tazaFantasma", 1);
            fail("Se esperaba TowerException por tipo inválido en Cup");
        } catch (TowerException e) {
            assertEquals(TowerException.INVALID_ELEMENT_TYPE, e.getMessage());
        }

        try {
            ElementFactory.crearLid("tapaInexistente", 1);
            fail("Se esperaba TowerException por tipo inválido en Lid");
        } catch (TowerException e) {
            assertEquals(TowerException.INVALID_ELEMENT_TYPE, e.getMessage());
        }
    }
    
    @Test
    public void accordingMAshouldRecordLogProperly() {
        RuntimeException e = new RuntimeException("Error de prueba");
        
        assertDoesNotThrow(() -> Log.record(e), 
            "El método record no debería lanzar excepciones");
    }
    
    @Test
    public void accordingMAshouldExecuteTimeBombCupPower() throws TowerException {
    	
        tower.pushCup("normal", 10);
        tower.pushCup("bomb", 5);     
        tower.pushCup("normal", 4);   
        tower.pushCup("normal", 3); 
        
        tower.pushCup("normal", 2);  
        
        String[][] expected = {
                {"cup", "3"},
                {"cup", "2"}
            };

        assertStackingItems(expected, tower.stackingItems());
    }

    @Test
    public void accordingMAshouldExecuteHierarchicalCupSwap() throws TowerException {
        Tower t = new Tower(10, 100);
        t.pushCup("normal", 3);
        t.pushCup("normal", 4);
   
        t.pushCup("hierarchical", 5); 
        
        String[][] expected = {
                {"cup", "5"}, 
                {"cup", "3"},
                {"cup", "4"}
            };
            
        assertStackingItems(expected, t.stackingItems());
            
        try {
            t.popCup(); 
        } catch (TowerException e) {
            assertTrue(true);
        }
    }
    
    @Test
    public void accordingMAshouldValidateFearfulAndCrazyLids() throws TowerException {
        Tower t = new Tower(10, 100);
        t.pushCup("normal", 1);
        
        t.pushLid("fearful", 1); 
        t.pushLid("crazy", 2);
        
        try {
            t.pushLid("fearful", 99); 
            fail("Se esperaba TowerException por poder fallido (no encontró la taza)");
        } catch (TowerException e) {
        }
    }
    
    @Test
    public void accordingMAshouldThrowExceptionOnInvalidLidId() {
        try {
            tower = new Tower(10, 10); 
            tower.pushLid("normal", 0); 
            fail("Se esperaba TowerException por ID de tapa inválido (i <= 0)");
            
        } catch (TowerException e) {
            assertEquals(TowerException.INVALID_ID, e.getMessage());
        }
    }
    
    @Test
    public void accordingMAshouldThrowExceptionOnSwapOverflow() {
        try {
            tower = new Tower(10, 10); 
            tower.pushCup("normal", 5); 
            tower.pushCup("normal", 2); 
            
            String[] item1 = {"cup", "5"};
            String[] item2 = {"cup", "2"};
            
            tower.swap(item1, item2); 
            fail("Se esperaba TowerException por OVERFLOW al realizar el swap");
            
        } catch (TowerException e) {
            assertEquals(TowerException.OVERFLOW, e.getMessage());
        }
    }
    
    @Test
    public void accordingMAshouldThrowExceptionOnPopCupWhenTowerIsEmpty() {
        try {
            tower = new Tower(10, 10);             
            tower.popCup(); 
            fail("Se esperaba TowerException por intentar hacer popCup en una torre vacía");
            
        } catch (TowerException e) {
            assertEquals(TowerException.NON_EXISTENT_ID, e.getMessage());
        }
    }
    
    @Test
    public void shouldThrowExceptionOnPopLidWhenTowerIsEmpty() {
        try {
            tower = new Tower(10, 10); 
            tower.popLid(); 
            fail("Se esperaba TowerException por intentar hacer popLid en una torre vacía");
            
        } catch (TowerException e) {

            assertEquals(TowerException.NON_EXISTENT_ID, e.getMessage());
        }
    }
    
    @Test
    public void shouldThrowExceptionOnInvalidLidIdInt() {
        try {
            tower = new Tower(10, 10); 
            tower.pushLid(0); 
            fail("Se esperaba TowerException por ID de tapa inválido (i <= 0)");
            
        } catch (TowerException e) {
            assertEquals(TowerException.INVALID_ID, e.getMessage());
        }
    }
    
    @Test
    public void shouldThrowExceptionOnDuplicateLidId() {
        try {
            tower = new Tower(10, 10); 
            
            tower.pushLid(1); 
            tower.pushLid(1); 
            
            fail("Se esperaba TowerException por intentar ingresar un ID duplicado");
        } catch (TowerException e) {
            assertEquals(TowerException.DUPLICATE_ID, e.getMessage());
        }
    }
    
    @Test
    public void accordingMAshouldThrowExceptionOnInvalidCupId() {
        try {
            tower = new Tower(10, 10);
            tower.pushCup(0); 
            fail("Se esperaba TowerException por ID de taza inválido (i <= 0)");
          
        } catch (TowerException e) {
            assertEquals(TowerException.INVALID_ID, e.getMessage());
        }
    }
    
    @Test
    public void accordingMAshouldThrowExceptionOnDuplicateCupId() {
        try {
            tower = new Tower(10, 10);
            tower.pushCup(3); 
            tower.pushCup(3); 
            
            fail("Se esperaba TowerException por intentar ingresar un ID de taza duplicado"); 
        } catch (TowerException e) {
            assertEquals(TowerException.DUPLICATE_ID, e.getMessage());
        }
    }
    
    @Test
    public void accordingMAshouldThrowExceptionOnPushCupOverflow() {
        try {
            tower = new Tower(10, 5);
            tower.pushCup(4); 
            
            fail("Se esperaba TowerException por OVERFLOW al superar la altura máxima de la torre");
            
        } catch (TowerException e) {
            assertEquals(TowerException.OVERFLOW, e.getMessage());
        }
    }
    
    @Test
    public void accordingMAshouldCreateTowerWithNCupsSuccessfully() {
        try {
            tower = new Tower(3);
            assertEquals(3, tower.stackingItems().length, "La torre debió inicializarse exactamente con 3 copas");
            
        } catch (TowerException e) {
            fail("No se esperaba ninguna excepción al crear una torre con n = 3 válido. Error: " + e.getMessage());
        }
    }
    
    @Test
    public void accordingMAshouldThrowExceptionOnInvalidNCups() {
        try {
           tower = new Tower(0);
            fail("Se esperaba TowerException por cantidad de copas inválida (n <= 0)");
            
        } catch (TowerException e) {
            assertEquals(TowerException.INVALID_DIMENSIONS, e.getMessage());
        }
    }
    
    @Test
    public void accordingMAshouldTriggerOverflowWhileLoopOnReverseTower() {
        try {
            Tower t = new Tower(10, 16);
           
            t.pushCup(8);
            t.pushCup(2);
            
            assertEquals(2, t.stackingItems().length, "La torre debe tener 2 tazas inicialmente");
            t.reverseTower();

            String[][] expected = {
                {"cup", "2"}
            };
            assertStackingItems(expected, t.stackingItems());
            
        } catch (TowerException e) {
            fail("No se esperaba ninguna excepción durante la configuración de la torre");
        }
    }  
    
    
    @Test
    public void accordingMAshouldSinkHierarchicalCupToBottomAndAnchor() throws TowerException {
        Tower t = new Tower(10, 20);
        t.pushCup("normal", 2);
        t.pushCup("hierarchical", 5);
        
        String[][] expected = {
            {"cup", "5"},
            {"cup", "2"}
        };
        assertStackingItems(expected, t.stackingItems());
    }
    
    @Test
    public void accordingMAshouldThrowExceptionWhenRemovingAnchoredHierarchicalCup() throws TowerException {
        Tower t = new Tower(10, 10);
        t.pushCup("hierarchical", 5);
        
        try {
            t.popCup();
            fail("Se esperaba TowerException.IMMOVABLE_ELEMENT porque la taza está anclada al fondo");
        } catch (TowerException e) {
            assertEquals(TowerException.IMMOVABLE_ELEMENT, e.getMessage());
            assertEquals(1, t.stackingItems().length);
        }
    }
    
    @Test
    public void accordingMAshouldReturnCorrectLidBaseHeight() {

        Lid tapa = new Lid(15, "red"); 
        int alturaBase = tapa.getHeightBase();  
        assertEquals(1, alturaBase, "La altura base de una tapa siempre debe ser 1");
    }
    
    @Test
    public void accordingMAlidShouldNotBeAnOpenContainer() {
        Lid tapa = new Lid(10, "blue");
        boolean esAbierto = tapa.esContenedorAbierto();
        assertFalse(esAbierto, "Una tapa nunca debe comportarse como un contenedor abierto");
    }   
}