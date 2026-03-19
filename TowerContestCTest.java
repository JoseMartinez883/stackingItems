/**
 * Pruebas del simulador del ejercisio Stacking Cups Ciclo 3
 * Casos planteados en el pdf
 * @Author Jose Alejandro Martinez Arias
 */
public class TowerContestCTest {
    private TowerContest t = new TowerContest();
    
    /**
     * Primer caso del pdf
     */
    public void primerCaso(){
        t.simulate(4,9);
    }
    
    /**
     * Segundo caso del pdf
     */
    public void segundoCaso(){
        t.simulate(4,100);
    }
}