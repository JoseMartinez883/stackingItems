package test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import tower.Tower;
import tower.TowerException;

import org.junit.jupiter.api.BeforeEach;



/**
 * Test correspondientes el ciclo 4
 * @author Jose Alejandro Martinez Arias
 * @version 05-04-2026
 */

public class  TowerC4Test {

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
    public void accordingMAshouldAddTowerElements() throws TowerException{
        tower.pushCup("normal", 10);
        tower.pushLid("normal", 9);
        tower.pushCup("normal", 8);
        tower.pushLid("normal", 7);

        assertEquals(19, tower.height(), "La altura no coincide con la suma de los elementos.");
        
        String[][] expected = {
            {"cup", "10"},
            {"lid", "9"},
            {"cup", "8"},
            {"lid", "7"}
        };
        
        assertStackingItems(expected, tower.stackingItems());
    }
    
    @Test
    public void accordingMAShouldAddDifferentTypesOfCupsAndLids() throws TowerException{
        tower.pushCup("normal", 10);
        tower.pushLid("fearful", 10);
        
        int alturaAntes = tower.height();
        int cantidadAntes = tower.stackingItems().length;
        
        assertEquals(20, alturaAntes, "La altura debería ser 20 tras agregar taza y tapa miedosa");
        assertEquals(2, cantidadAntes, "Debería haber exactamente 2 elementos en la torre");
    
        assertThrows(TowerException.class, () -> {
            tower.pushLid("fearful", 1);
        }, "Debería lanzar MISSING_CUP_DEPENDENCY (o POWER_FAILED)");
        
        assertEquals(alturaAntes, tower.height(), "La altura no debió cambiar al fallar la inserción de la fearful");
        assertEquals(cantidadAntes, tower.stackingItems().length, "La cantidad de ítems no debió aumentar");
        
        tower.pushCup("opener", 8);     
        tower.pushCup("bomb", 6);      
        tower.pushCup("hierarchical", 4);
        

        assertEquals(35, tower.height(), "La altura total con piezas especiales falló");
        
        String[][] expected = {
            {"cup", "10"},
            {"lid", "10"}, // Es la FearfulLid
            {"cup", "8"},  // Es la OpenerCup
            {"cup", "6"},  // Es la TimeBombCup
            {"cup", "4"}   // Es la HierarchicalCup
        };
        
        assertStackingItems(expected, tower.stackingItems());
    }
    
    @Test
    public void accordingMAshouldOpenTheCupDestroyTheNormalLidsAndStopWithFearLid() throws TowerException{
        tower.pushCup("normal", 20);
        tower.pushCup("normal", 18); 
        tower.pushCup("normal", 15); 
        tower.pushLid("fearful", 15);
        tower.pushLid("normal", 14); 
        tower.pushLid("normal", 12); 
        tower.pushLid("normal", 10); 
    
        assertEquals(39, tower.height(), "La altura previa a la destrucción no es correcta");
        assertEquals(7, tower.stackingItems().length, "La cantidad de elementos previa no coincide");
    
        tower.pushCup("opener", 8);
    
        assertEquals(47, tower.height(), "La altura final de 55 falló (Opener debe estar sobre la tapa miedosa)");
    
        String[][] expected = {
            {"cup", "20"},
            {"cup", "18"},
            {"cup", "15"},
            {"lid", "15"}, // La fearfullLid
            {"cup", "8"}   // la OpenerCup
        };
        
        assertStackingItems(expected, tower.stackingItems());
        assertEquals(5, tower.stackingItems().length, "Deben quedar exactamente 5 elementos en la torre tras el ataque");
    }
    
    @Test
    public void  accordingMAShouldBeNestedAndSortedByHierarchy() throws TowerException{
        tower.pushCup("normal", 20); 
        tower.pushCup("normal", 5);  
        tower.pushCup("normal", 4); 
        tower.pushCup("normal", 3);
        tower.pushCup("normal", 2);  
    
        assertEquals(39, tower.height(), "La altura inicial debe ser dictada por la taza 20");
        assertEquals(5, tower.stackingItems().length, "Deben haber 5 elementos");
    
        tower.pushCup("hierarchical", 10);
    
 
        String[][] expected = {
            {"cup", "20"},
            {"cup", "10"},
            {"cup", "5"},
            {"cup", "4"},
            {"cup", "3"},
            {"cup", "2"}
        };
        assertStackingItems(expected, tower.stackingItems());
    
        assertEquals(39, tower.height(), "La altura final se mantiene en 39");
        assertEquals(6, tower.stackingItems().length, "Ahora deben haber 6 elementos ordenados");
    }
    
    
    @Test
    public void accordingMAShouldMakeMultipleChangesGetToBottomAndStandFirm() throws TowerException{
        tower.pushCup("normal", 15); 
        tower.pushCup("normal", 12); 
        tower.pushCup("normal", 8);  
        tower.pushCup("normal", 3);  
    
        assertEquals(29, tower.height(), "Altura previa debe ser 29");
    
        tower.pushCup("hierarchical", 30);
    
        String[][] expected = {
            {"cup", "30"}, 
            {"cup", "15"},
            {"cup", "12"},
            {"cup", "8"},
            {"cup", "3"}
        };
        assertStackingItems(expected, tower.stackingItems());
        
        assertEquals(59, tower.height(), "La altura debe haber saltado a 59");
    
        assertThrows(TowerException.class, () -> {
            tower.removeCup(30); 
        }, "Debería lanzar IMMOVABLE_ELEMENT");

        assertEquals(59, tower.height(), "La altura sigue siendo 59, no se dejó borrar");
        assertEquals(5, tower.stackingItems().length, "La cantidad de elementos intacta");
        
        String[] elFondo = tower.stackingItems()[0];
        assertEquals("cup", elFondo[0]);
        assertEquals("30", elFondo[1]);
    }
    
    @Test
    public void accordingMAshouldRejectFearfulLidIfItsCupDoesNotExist() throws TowerException{
        tower.pushCup("normal", 5);
        tower.pushCup("normal", 3);
        
        int alturaAntes = tower.height();
        int cantidadAntes = tower.stackingItems().length;
        
        assertThrows(TowerException.class, () -> {
            tower.pushLid("fearful", 1);
        });
        
        assertEquals(alturaAntes, tower.height(), "La altura no debió cambiar al rechazar la tapa");
        assertEquals(cantidadAntes, tower.stackingItems().length, "La cantidad no debió aumentar");
    }
    
    @Test
    public void accordingMAShouldEnterFearFullLidEvenIfCupExistAndThereAreObstacles() throws TowerException{
        
        tower.pushCup("normal", 10);
        tower.pushLid("normal", 20); 
        tower.pushLid("fearful", 10); 
        
        assertEquals(21, tower.height(), "La altura debió subir a 21 porque se apiló arriba");
        assertEquals(3, tower.stackingItems().length, "Deben haber 3 elementos en total");
        
        String[][] expected = {
            {"cup", "10"},
            {"lid", "20"}, 
            {"lid", "10"}  
        };
        assertStackingItems(expected, tower.stackingItems());
    }
    
    @Test
    public void accordingMAShouldBlockRemovalFearfulLidIfItIsAttachedToItsCup() throws TowerException{
        
        tower.pushCup("normal", 10);
        tower.pushLid("fearful", 10); 
        assertEquals(20, tower.height());
        assertEquals(2, tower.stackingItems().length);
        
        
        assertThrows(TowerException.class, () -> {
            tower.removeLid(10);  
        }, "Debería lanzar IMMOVABLE_ELEMENT");
        
        assertEquals(20, tower.height(), "La altura no debió bajar; la tapa se protegió");
        assertEquals(2, tower.stackingItems().length, "Siguen estando los 2 elementos");
        
        String[][] expected = {
            {"cup", "10"},
            {"lid", "10"}
        };
        assertStackingItems(expected, tower.stackingItems());
    }
    
    @Test
    public void accordingMAShouldAllowRemovalFearfulLidIfItIsNotAttachedToTheCup() throws TowerException{
        
        tower.pushCup("normal", 10);  
        tower.pushLid("normal", 20);
        tower.pushLid("fearful", 10);
        
        assertEquals(21, tower.height(), "La altura inicial debe ser 21");
        assertEquals(3, tower.stackingItems().length, "Deben haber 3 elementos");
        

        tower.removeLid(10); 

        
        assertEquals(20, tower.height(), "La altura debió bajar a 20 tras borrar la tapa");
        assertEquals(2, tower.stackingItems().length, "Deben quedar solo 2 elementos");
        
        String[][] expected = {
            {"cup", "10"},
            {"lid", "20"}
        };
        
        assertStackingItems(expected, tower.stackingItems());
    }
    
    @Test
    public void accordingMACrazyLidShouldBePlacedUnderYourCup() throws TowerException{
    
        tower.pushCup("normal", 5); 
        tower.pushCup("normal", 10); 
        assertEquals(2, tower.stackingItems().length);
        tower.pushLid("crazy", 10);
    
        String[][] items = tower.stackingItems();
        assertEquals(3, items.length, "Deben haber 3 elementos tras meter la crazy");
    
        String[] ultimo = items[items.length - 1];
        assertEquals("cup", ultimo[0], "El último elemento debería ser una taza");
        assertEquals("10", ultimo[1], "El ID de la última taza debe ser 10");
    
        String[] penultimo = items[items.length - 2];
        assertEquals("lid", penultimo[0], "El penúltimo elemento debería ser la tapa");
        assertEquals("10", penultimo[1], "El ID de la tapa debe ser 10");
    
        String[][] expected = {
            {"cup", "5"}, 
            {"lid", "10"},
            {"cup", "10"}  
        };
        assertStackingItems(expected, items);
        
        assertEquals(29, tower.height(), "La altura final debe sumar las 3 piezas apiladas");
    }
    
    @Test
    public void accordingMAshouldReduceShiftsWithoutOverworking() throws TowerException{

        tower.pushCup("normal", 40);
        tower.pushCup("bomb", 30);
        tower.pushCup("normal", 20);
        
        assertEquals(3, tower.stackingItems().length, "Deben haber 3 elementos intactos en la torre");
      
        
        String[][] expected = {
            {"cup", "40"},
            {"cup", "30"},
            {"cup", "20"}
        };
        assertStackingItems(expected, tower.stackingItems());
    }
    
    @Test
    public void accordingMAshouldExplodeAndRemoveCentralNeighbors() throws TowerException{
        
        tower.pushCup("normal", 40); 
        tower.pushCup("bomb", 35);  
        tower.pushCup("normal", 30);
        tower.pushCup("normal", 20);   
        
        tower.pushCup("normal", 10);   
        
        assertEquals(2, tower.stackingItems().length, "Solo debe quedar 1 sobreviviente tras la explosión");
        
        String[][] expected = {
            {"cup","20"},
            {"cup", "10"}
        };
        assertStackingItems(expected, tower.stackingItems());
    }
    
    @Test
    public void accordingMAShouldCrashInTheBottonWithoutErrors() throws TowerException{
        
        tower.pushCup("bomb", 40);
        tower.pushCup("normal", 30);  
        
        tower.pushCup("bomb", 20);  
        tower.pushCup("normal", 10);  
        
        assertEquals(2, tower.stackingItems().length, "La torre no debe crashear y debe dejar 1 sobreviviente");
        
        String[][] expected = {
            {"cup", "20"},
            {"cup","10"}
        };
        assertStackingItems(expected, tower.stackingItems());
    }
    
    @Test
    public void accordingMAshouldBombRespectImmunityFromFearfulLid() throws TowerException{
        
        tower.pushCup("normal", 5);
        tower.pushLid("fearful", 5); 
        
        tower.pushCup("bomb", 4); 
        
        tower.pushCup("normal", 3);
        
        tower.pushCup("normal", 2);
        tower.pushCup("normal", 1);
        
        assertEquals(4, tower.stackingItems().length, "La tapa miedosa debió frenar la eliminación");
        
        String[][] expected = {
            {"cup", "5"},
            {"lid", "5"},
            {"cup", "2"},
            {"cup", "1"}
        };
        assertStackingItems(expected, tower.stackingItems());
    }
    
    @Test
    public void accordingMAshouldBombRespectHierarchicalImmunityInBottom() throws TowerException{
    
        tower.pushCup("hierarchical", 6);
        tower.pushCup("bomb", 5);
        
        tower.pushCup("normal", 4);
        tower.pushCup("normal", 3);
        
        tower.pushCup("normal", 2);
        
        assertEquals(3, tower.stackingItems().length, "La jerárquica en el fondo no debe ser destruida");
        
        String[][] expected = {
            {"cup", "6"}, 
            {"cup", "3"},
            {"cup", "2"}
        };
        assertStackingItems(expected, tower.stackingItems());
    }
}