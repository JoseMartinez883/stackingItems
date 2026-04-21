package tower;
import java.util.ArrayList;

/**
 * Taza Bomba (TimeBombCup): Explota tras 3 movimientos en la torre,
 * se elimina a ella misma y a los elementos que tiene arriba y abajo de ella
 * @author Jose Alejandro Martinez Arias
 */
public class TimeBombCup extends Cup {
    /**
     * @param turnosRestantes turnos que faltan para explotar
     */
    private int turnosRestantes;
    
    /**
     * Inicializa la taza bomba
     * @param id identificador de la taza
     * @param color color de la taza
     */
    public TimeBombCup(int id, String color) {
        super(id, color);
        this.turnosRestantes = 3; 
    }

    /**
     * Cada vez que se agregar una taza, se modifican los
     * turnos que faltan para explotar
     */
    public void ticTac() {
        if (turnosRestantes > 0) {
            turnosRestantes--;
        }
        actualizarAspecto();
    }

    /**
     * Cambia el color de la taza para alertar al usuario
     */
    private void actualizarAspecto() {
        String colorBomba = "black";
        if (turnosRestantes == 2) colorBomba = "yellow";
        else if (turnosRestantes == 1) colorBomba = "black";
        else if (turnosRestantes == 0) colorBomba = "red";
        
        if (getBase() != null) getBase().changeColor(colorBomba);
    }
    
    /**
     * Dibuja la copa en la torre
     * @param xCentro centro de la torre
     * @param ySuelo base donde se posicionara la copa
     * @param fh factor de la altura en pixeles
     * @param fw factor del ancho en pixeles
     */
    @Override
    public void dibujar(int xCentro, int ySuelo, int fH, int fW) {
        actualizarAspecto();
        super.dibujar(xCentro, ySuelo, fH, fW);
    }
    
    /**
     * Poder que se ejecutara cuando el usuario realizar una accion en la torre
     * @return items elementos de la torre
     */
    @Override
    public void reaccionarTurno(ArrayList<ElementoTorre> items) {
        ticTac();
        
        if (turnosRestantes == 0) {
            int miIndex = items.indexOf(this);
            if (miIndex != -1) {
                
                if (miIndex + 1 < items.size()) {
                    ElementoTorre arriba = items.get(miIndex + 1);
                    String tipoArriba = arriba.esTipo("cup") ? "cup" : "lid";
                    
                    if (arriba.validarRemocion(tipoArriba)) {
                        arriba.makeInvisible();
                        items.remove(arriba);
                    }
                }
                this.makeInvisible();
                items.remove(this);
                
                if (miIndex - 1 >= 0) {
                    ElementoTorre abajo = items.get(miIndex - 1);
                    String tipoAbajo = abajo.esTipo("cup") ? "cup" : "lid";
                    
                    if (abajo.validarRemocion(tipoAbajo)) {
                        abajo.makeInvisible();
                        items.remove(abajo);
                    }
                }
            }
        }
    }
}