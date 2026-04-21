package test;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tower.Tower;
import tower.TowerException;

/**
 * Pruebas de unidad para el Ciclo 2
 * @author Jose Alejandro Martinez Arias
 * @version 2026-03-03
 */
public class TowerC2Test {
    private Tower torre;

    @Before
    public void setUp() throws TowerException{
        torre = new Tower(100, 500);
    }
    
    /**
     * Verifica si dos ArrayList 2D tienen los mismos elementos
     * @param expected elementos que se esperan que estan en la lista
     * @param actual elementos actuales a verificar si son los esperados
     */
    private void assertStackingItems(String[][] expected, String[][] actual) {
        assertEquals("Distinta longitud de stackingItems", expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals("Diferencia en el elemento de la posición " + i, expected[i], actual[i]);
        }
    }
    
    // pushcup y pushlid 
    @Test
    public void accordingMAShouldStackLargerCupOverSmallerWithoutNesting() throws TowerException{
        torre.pushCup(2);
        torre.pushCup(10); 
        torre.pushCup(3);
        
        boolean isOkLastPush = torre.ok();
        
        String[][] expected = {
            {"cup", "2"},
            {"cup", "10"},
            {"cup", "3"}
        };
        
        assertTrue("El movimiento se estuvo que haber realizado",isOkLastPush);
        assertEquals("Se adjuntaron 3 elementos a la torre",3, torre.stackingItems().length);
        assertEquals("La taza grande 10 no debe hundirse en la pequeña 2. Altura debe ser 22", 22, torre.height());
        assertStackingItems(expected, torre.stackingItems());
    }

    @Test
    public void accordingMAShouldNestSmallerCupsInsideLargerCup() throws TowerException{
        torre.pushCup(10); 
        torre.pushCup(5);  
        torre.pushCup(2);  
        
        boolean isOkLastPush = torre.ok();
        
        String[][] expected = {
            {"cup", "10"},
            {"cup", "5"},
            {"cup", "2"}
        };
        
        assertTrue("El movimiento se estuvo que haber realizado",isOkLastPush);
        assertEquals("Se adjuntaron 3 elementos a la torre", 3, torre.stackingItems().length);
        assertStackingItems(expected, torre.stackingItems());
        assertEquals("Las tazas pequeñas deben hundirse en las grandes. Altura debe ser 19", 19, torre.height());
    }
    
    @Test
    public void accordingMAShouldNestSmallLidInsideLargeCup() throws TowerException{
        torre.pushCup(10);
        torre.pushLid(2);  
        
        boolean isOkLastPush = torre.ok();
        
        String[][] expected = {
            {"cup", "10"},
            {"lid", "2"}
        };
        
        assertTrue("El movimiento se estuvo que haber realizado",isOkLastPush);
        assertEquals("Se adjuntaron 2 elementos a la torre", 2, torre.stackingItems().length);
        assertStackingItems(expected, torre.stackingItems());
        assertEquals("Las tapas pequeñas deben hundirse en la taza grande. Altura debe ser 19", 19, torre.height());
    }

    @Test
    public void accordingMAShouldStackLargeLidOverSmallCup() throws TowerException{
        torre.pushCup(2);  
        torre.pushLid(10);
        
        boolean isOkLastPush = torre.ok();
        
        String[][] expected = {
            {"cup", "2"},
            {"lid", "10"}
        };
        
        assertTrue("El movimiento se estuvo que haber realizado",isOkLastPush);
        assertEquals("Se adjuntaron 2 elementos a la torre", 2, torre.stackingItems().length);
        assertStackingItems(expected, torre.stackingItems());
        assertEquals("La tapa 10 no cabe en la taza 2, queda encima de la taza. Altura debe ser 4", 4, torre.height());
    }

    @Test
    public void accordingMAShouldStackCupOverLidCorrectly() throws TowerException{
        torre.pushLid(5);
        torre.pushCup(5); 
        
        boolean isOkLastPush = torre.ok();
        
        String[][] expected = {
            {"lid", "5"},
            {"cup", "5"}
        };
        
        assertTrue("El movimiento se estuvo que haber realizado",isOkLastPush);
        assertEquals("Se adjuntaron 2 elementos a la torre", 2, torre.stackingItems().length);
        assertStackingItems(expected, torre.stackingItems());
        assertEquals("La taza grande no debe hundirse con la tapa. Altura debe ser 10", 10, torre.height());
        assertArrayEquals("Como el orden fue inverso, no deben haber tapas y techos unidos",new int[]{} ,torre.lidedCups());
    }

    @Test
    public void accordingMAShouldNotExceedMaxHeightWithLid() throws TowerException{
        Tower torreLimite = new Tower(100, 20);
        
        torreLimite.pushCup(10);
        torreLimite.pushLid(10);
        assertEquals("La altura debe estar a su límite de altura, 20", 20, torreLimite.height());
        
        
        try {
            torreLimite.pushLid(1);
            fail("Debió lanzar TowerException por OVERFLOW");
        } catch (TowerException e) {
            assertEquals(TowerException.OVERFLOW, e.getMessage());
            boolean isOkLastPush =  torreLimite.ok();
        
            String[][] expected = {
                {"cup", "10"},
                {"lid", "10"}
            };
    
            assertFalse("El ultimo movimiento fue fallido", isOkLastPush);
            assertEquals("La torre no debio permitir la entrada de la ultima tapa", 20, torreLimite.height());
            assertEquals("La lista de elementos no debió crecer, deben haber 2 elementos", 2, torreLimite.stackingItems().length);
            assertStackingItems(expected, torreLimite.stackingItems());
            assertArrayEquals("Debe haber una tapa y un techo unido, se agrego taza-techo id 10",new int[]{10} ,torreLimite.lidedCups());
        }
    }
    
    // pop y cover
    @Test
    public void accordingMAShouldPopElementsCorrectly() throws TowerException{
        torre.pushCup(5);
        torre.pushLid(2); 
        torre.popLid();
        
        boolean isOkLastPop =  torre.ok();
        
        assertEquals("Al quitar la tapa, la altura solo seria la de la taza, 9", 9, torre.height());
        assertTrue("El movimiento fue realizado", isOkLastPop);
        String[][] expected = {
            {"cup", "5"},
        };
        assertStackingItems(expected, torre.stackingItems());
    }

    @Test
    public void accordingMAShouldNotPopCupIfItHasItsOwnLidOnTop() throws TowerException{
        torre.pushCup(5);
        torre.pushLid(5);
        torre.cover(); 
        
        
        try {
            torre.popCup(); 
            fail("Debió lanzar TowerException por IMMOVABLE_ELEMENT");
        } catch (TowerException e) {
            assertEquals(TowerException.IMMOVABLE_ELEMENT, e.getMessage());
            
            boolean isOk = torre.ok();
        
            String[][] expected = {
                {"cup", "5"},
                {"lid", "5"}
            };
            
            assertStackingItems(expected, torre.stackingItems());
            assertFalse("El ultimo movimiento no se puedo haber realizado", isOk);
            assertEquals("La torre no debio dejar sacar la taza porque su tapa esta encima", 2, torre.stackingItems().length);
            assertArrayEquals(new int[]{5}, torre.lidedCups());
            assertEquals("La altura debe seguir igual, de 10", 10, torre.height());
        }
    }

    @Test
    public void accordingMAShouldNotPopCupIfLidIsInTheWay() throws TowerException{
        torre.pushCup(10);
        torre.pushLid(2); 
        
        String[][] expected = {
            {"cup", "10"},
            {"lid", "2"}
        };
        
        try {
            torre.popCup();
            fail("Debió lanzar IMMOVABLE_ELEMENT porque hay una tapa en la cima");
        } catch (TowerException e) {
            assertEquals(TowerException.IMMOVABLE_ELEMENT, e.getMessage());
            boolean isOk = torre.ok();
            assertFalse("El popCup debe faller, hay un techo en la cima", isOk);
            assertEquals("La torre debe mantener sus 2 elementos", 2, torre.stackingItems().length);
            assertStackingItems(expected, torre.stackingItems());
        }
    }

    @Test
    public void accordingMAShouldCoverMatchingElementsAndReduceList() throws TowerException{
        torre.pushCup(5); 
        torre.pushLid(3);
        torre.pushLid(2); 
        torre.pushCup(4);
        torre.pushLid(5); 
        torre.pushCup(3);
        
        assertEquals(16, torre.height());
        torre.cover();
        boolean isOkCover = torre.ok();
        
        assertTrue("El cover debió ser exitoso", isOkCover);
        assertEquals("Deben haber 6 elementos en la torre", 6, torre.stackingItems().length);
        assertArrayEquals("Deben haber tres elementos juntos, taza-techo id: 3,5", new int[]{3, 5}, torre.lidedCups());
    }

    @Test
    public void accordingMAShouldNotCoverIfIdsDoNotMatch() throws TowerException{
        torre.pushCup(5);
        torre.pushLid(2);
        torre.pushCup(3);
        torre.pushCup(4);
        
        torre.cover();
        boolean isOk = torre.ok();
        
        assertFalse("El cover debió fallar por id distintos", isOk);
        assertArrayEquals("No deben haber elementos unidos taza-techo", new int[]{}, torre.lidedCups());
    }
    
    // swap, order, reverse, lidedcups
    @Test
    public void accordingMAShouldAlterPhysicsWhenSwappingMiddleElements() throws TowerException{
        torre.pushCup(10); 
        torre.pushCup(2); 
        torre.pushCup(5); 
        
        assertEquals("Antes del swap, la altura debe ser 19", 19, torre.height());
        
        String[] item1 = {"cup", "10"};
        String[] item2 = {"cup", "2"};
        torre.swap(item1, item2);
        
        assertEquals("Después del swap, la altura cambia a 22", 22, torre.height());
        assertEquals("Deben haber tres elementos", 3, torre.stackingItems().length);
        
        String[] item3 = {"cup", "10"};
        String[] item4 = {"cup", "2"};
        torre.swap(item3, item4);
    
        assertEquals("Después del swap, la altura vuelve a 19", 19, torre.height());
        assertEquals("Deben haber tres elementos", 3, torre.stackingItems().length);
        
        String[] item5 = {"cup", "2"};
        String[] item6 = {"cup", "5"};
        torre.swap(item5, item6);   
        
        assertEquals("Como todo esta dentro de la taza mas grande, la altura es de 19", 19, torre.height());
        assertEquals("Deben haber tres elementos", 3, torre.stackingItems().length);
    }

    @Test
    public void accordingMAShouldMoveCoveredPairsTogetherDuringSwap() throws TowerException{
        torre.pushLid(1);  
        torre.pushCup(5);
        torre.pushCup(8);
        torre.pushLid(5);
        torre.cover();     
        
        String[] itemTapa = {"lid", "1"};
        String[] itemPareja = {"cup", "5"};
        torre.swap(itemTapa, itemPareja);
        
        assertArrayEquals("El swap no debió separar a la pareja ni alterar la cantidad de elementos juntos", new int[]{5},  torre.lidedCups());
    }

    @Test
    public void accordingMAShouldOrderTowerAndTriggerNestingPhysics() throws TowerException{
        torre.pushLid(1);
        torre.pushCup(2); 
        torre.pushCup(10);
        torre.pushCup(5); 
        torre.pushLid(5);
        
        assertEquals("Antes de ordenar, la altura no es la menor,  es 23", 23, torre.height());
        
        torre.orderTower();
        String[][] expected = {
            {"cup", "10"},
            {"cup", "5"}, 
            {"lid", "5"}, 
            {"cup", "2"}, 
            {"lid", "1"} 
        };
        
        assertEquals("Después de ordenar, la altura debe ser la menor ", 19, torre.height());
        assertArrayEquals("Se debe respetar el elemento taza-tapa id 5", new int[]{5}, torre.lidedCups());
        assertEquals("Deben seguir siendo 5 elementos", 5, torre.stackingItems().length);
        assertStackingItems(expected, torre.stackingItems());
    }

    @Test
    public void accordingMAShouldReverseTowerAndBreakNesting() throws TowerException{
        torre.pushCup(10); 
        torre.pushLid(2);
        torre.pushCup(2);
        torre.pushCup(5);
        torre.pushLid(10);
        
        torre.reverseTower();
        
        String[][] expected = {
            {"lid", "10"},
            {"cup", "5"},
            {"cup", "2"},
            {"lid", "2"},
            {"cup","10"}
        };
        
        assertStackingItems(expected, torre.stackingItems());
        assertEquals("Al invertir la torre, la taza se apoya sobre la tapa. Altura debe ser 29", 29, torre.height());
        assertArrayEquals("Se debe respetar el elemento taza-tapa id 2", new int[]{2}, torre.lidedCups());
    }

    @Test
    public void accordingMAShouldCountOnlyLegitimatelyCoveredCups() throws TowerException{
        torre.pushCup(5);
        torre.pushLid(2); 
        
        torre.pushCup(3);
        torre.pushLid(3);
        torre.cover();         
        int[] tapadas = torre.lidedCups();
        assertArrayEquals(new int[]{3}, tapadas);
    }
    
    // removecup y removelip
    @Test
    public void accordingMAShouldRemoveCupAndItsLidInCascade() throws TowerException{
        torre.pushCup(5);
        torre.pushLid(5);
        torre.pushCup(2); 
        
        assertEquals("Debe haber 2 elementos antes del borrado", 3, torre.stackingItems().length);
        
        torre.removeCup(5);
        
        assertEquals("Al borrar la taza, su tapa también debió desaparecer", 1, torre.stackingItems().length);
        assertEquals("La altura debe ser estrictamente la de la taza restante (3.0)", 3.0, torre.height(), 0.01);
    }

    @Test
    public void accordingMAShouldRemoveEntireBlockWhenRemovingCoveredCupOrLid() throws TowerException{
        torre.pushCup(5);
        torre.pushLid(5);
        
        torre.pushCup(10);
        torre.pushLid(10);
        
        torre.pushCup(2); 
        
        assertEquals("Debe haber 5 elementos antes del borrado", 5, torre.stackingItems().length);
        
        torre.removeCup(5);
        
        assertEquals("Al borrar la taza 5, su tapa también desaparece", 3, torre.stackingItems().length);
        
        torre.removeLid(10);
        
        assertEquals("Al borrar la tapa 10, su taza también debe desaparecer por estar unidas", 1, torre.stackingItems().length);
        assertEquals("Solo debió sobrevivir la taza 2 (altura 3.0)", 3, torre.height());
    }

    @Test
    public void accordingMAShouldIgnoreGhostRemovals() throws TowerException{
        torre.pushCup(10);
        int initialHeight = torre.height();
         
        try {
            torre.removeCup(99);  
        } catch (TowerException e) {
            assertEquals(TowerException.NON_EXISTENT_ID, e.getMessage());
            assertFalse(torre.ok());
            assertEquals("La torre no debió alterarse", 1, torre.stackingItems().length);
            assertEquals("La altura no debió cambiar", initialHeight, torre.height());
            
            try  {
                torre.removeLid(99);
            } catch (TowerException m){
                assertEquals(TowerException.NON_EXISTENT_ID, m.getMessage());
                assertFalse(torre.ok());
                assertEquals("La torre no debió alterarse", 1, torre.stackingItems().length);
                assertEquals("La altura no debió cambiar", initialHeight, torre.height());
            }
        }
        
    }

    @Test
    public void accordingMAShouldFindDistantSwapToReduceHeightInChaoticTower() throws TowerException{
        torre.pushCup(2);  
        torre.pushCup(8); 
        torre.pushCup(5);  
        torre.pushCup(10);
        torre.pushCup(1);  
        
        assertEquals("La torre ineficiente debe tener altura 37", 37, torre.height());
        
        String[][] swapResult = torre.swapToReduce();
        
        assertNotNull("El algoritmo debe encontrar un swap", swapResult);
        assertEquals("Debe retornar exactamente una pareja", 2, swapResult.length);
        
        boolean hasCup = (swapResult[0][1].equals("10") || swapResult[1][1].equals("10"));
        boolean hasCup2 = (swapResult[0][1].equals("2") || swapResult[1][1].equals("2"));
        
        assertTrue("El algoritmo falló. Debió sugerir mover la Taza 10", hasCup);
        assertTrue("El algoritmo falló. Debió sugerir mover la Taza 2", hasCup2);
        
        assertEquals("swapToReduce NO debe alterar la torre físicamente, solo calcular", 37, torre.height());
    }
    
    @Test
    public void accordingMAShouldReturnNullWhenLargeTowerIsAlreadyOptimal() throws TowerException{
        torre.pushCup(20); 
        torre.pushCup(10); 
        torre.pushCup(8);  
        torre.pushCup(5); 
        torre.pushCup(2);  
        
        assertEquals("La altura de la torre perfecta es 39", 39, torre.height());
        
        String[][] swapResult = torre.swapToReduce();
        
        boolean isOptimal = (swapResult == null || swapResult.length == 0);
        assertTrue("El algoritmo debe retornar null/vacio porque la torre ya es óptima", isOptimal);
    }
    
    @After
    public void tearDown() {
    }
}