import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

public class TowerContest{
    /**
     * Se presenta la solucion del ejercisio Stacking Cups
     * @param n numero de copas de la torre
     * @param h altura deseada por el usuario con las copas
     * @return Orden en que debemos apilar las copas si la altura se puede, sino 'impossible'
     */
    public String solve(int n, int h){
        int heightMin = 2*n - 1;
        int heightMax =  (int)Math.pow(n,2);
        int impossibleHeightCalculate = heightMax - 2;
        int totalTower = h;
        
        ArrayList<Integer> elementos = new ArrayList<>();
        
        if(h == impossibleHeightCalculate || h > heightMax || h < heightMin){
            return "impossible";
        }
        
        if(h == 2*n + 1 && n >= 3){
            elementos.add(2*n-1);
            elementos.add(3);
            
            for(int i = (n-1); i >= 2; i--){
                int cupHeight = 2*i - 1;
                if(cupHeight !=  3) elementos.add(cupHeight);
            }
            elementos.add(1);
            
        } else {
            ArrayList<Integer> encima = new ArrayList<>(); 
            ArrayList<Integer> adentro = new ArrayList<>(); 
            
            int falta = h - heightMin;
            adentro.add(heightMin);
            
            for(int i = n - 1; i >= 1; i--){
                int cupHeight = 2 * i -1;
                if(falta >= cupHeight && (falta - cupHeight != 2)){
                    encima.add(cupHeight);
                    falta -= cupHeight;
                } else{
                    adentro.add(cupHeight);
                }
            } 
            
            Collections.reverse(encima);
            
            elementos.addAll(encima);
            elementos.addAll(adentro);
            
        }
        
        String resultado = "";
        for(int i = 0; i < elementos.size(); i++){
            String altura = String.valueOf(elementos.get(i));
            resultado += altura;
            if (i < elementos.size() - 1) resultado += " ";
        }
        
        return resultado;
    }
    
    /**
     * Simula la solucion del problema en pantalla
     * @param n numero de copas de la torre
     * @param h altura deseada que tenemos que construir con el numero de copas n
     */
    public void simulate(int n, int h) {
        String res = solve(n, h);
        
        if (res.equals("impossible")) {
            esperar("No se puede graficar, impossible");
        } else {
            Tower t = new Tower(0); 
            t.makeVisible();
            
            String[] pasos = res.split(" ");
            for (int i = 0; i < pasos.length; i++) {
                int altura = Integer.parseInt(pasos[i]);
                int id = (altura + 1) / 2; 
                t.pushCup(id);
            }
        }
    }  
    
    private void esperar(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Paso de Prueba", JOptionPane.INFORMATION_MESSAGE);
    }
}