package test;
import tower.*;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Pruebas de unidad de la solucion de ejercisio Stacking Cups 
 * Ciclo 3
 * @Author Jose Alejandro Martinez Arias
 */
public class TowerContestTest {
	
    private TowerContest contest;
    
    @Before
    public void setUp() {
        contest = new TowerContest();
    }
    
    // minimos y maximos 
    @Test
    public void shouldReturnMaximumHeight(){
        String expected = "1 3 5 7"; 
        assertEquals(expected, contest.solve(4, 16));
    }
    
    @Test
    public void shouldReturnMinimumHeight() {
        assertEquals("5 3 1", contest.solve(3, 5));
    }
    
    // casos de alturas pedidas
    @Test
    public void shouldHandleSpecialCase2NPlus1() {
        String result = contest.solve(4, 9);
        assertEquals("7 3 5 1", result);
    }
    
    @Test
    public void shouldHandleSpecialCase2NPlus12() {
        int n = 5;
        int h = 11;
        String result = contest.solve(n, h);
        assertEquals("9 3 7 5 1", result);
    }
    
    @Test
    public void shouldHandleTheN2Minus2Gap() {
        assertEquals("impossible", contest.solve(3, 7));
        assertEquals("impossible", contest.solve(4, 14));
    }

    // casos imposibles
        @Test
    public void shouldReturnImpossibleStrings() {
        assertEquals("impossible", contest.solve(3, 10)); 
        assertEquals("impossible", contest.solve(3, 4));  
        assertEquals("impossible", contest.solve(3, 7)); 
    }
    
    // caso normal    
    @Test
    public void shouldSolveRandomValidHeight() {
        assertEquals("1 7 9 5 3", contest.solve(5, 17));
    }
}