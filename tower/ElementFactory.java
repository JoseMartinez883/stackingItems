package tower;

/**
 * Clase encargada de crear los nuevos elementos que ingresaran en la torre
 * @author Jose Alejandro Martinez Arias
 * @version 03-04-2026
 */
public class ElementFactory {
    private static final String[] COLORS = {"red","yellow", "blue", 
        "green","magenta","black"};
        
    /**
     *  Crea una taza, teniendo en cuenta el tipo solicitado
     *  @param tipo tipo de taza solicitado
     *  @param id iddentificador de la taza
     *  @param color Color de la nueva taza
     *  @return Cup la nueva taza, segun el tipo
     *  @throws TowerException INVALID_ELEMENT_TYPE si el tipo no coincide con los disponibles.
     */
    public static Cup crearCup(String type, int id) throws TowerException {
        String color = COLORS[id % COLORS.length];
        Cup newCup = null;
        // String color
        if (type.equalsIgnoreCase("opener")) {
            newCup = new OpenerCup(id, color);
        } else if (type.equalsIgnoreCase("hierarchical")) {
            newCup = new HierarchicalCup(id, color);
        } else if (type.equalsIgnoreCase("bomb")) {
            newCup = new TimeBombCup(id, color);
        } else if (type.equalsIgnoreCase("normal")){
            newCup = new Cup(id, color);
        } 
        
        if(newCup == null) throw new TowerException(TowerException.INVALID_ELEMENT_TYPE);
        return newCup;
    }
    
    /**
     *  Crea una tapa, teniendo en cuenta el tipo solicitado
     *  @param tipo tipo de tapa solicitado
     *  @param id iddentificador de la tapa
     *  @param color Color de la nueva tapa
     *  @return Lid la nueva tapa, segun el tipo
     *  @throws TowerException INVALID_ELEMENT_TYPE si el tipo no coincide con los disponibles.
     */
    public static Lid crearLid(String type, int id) throws TowerException {
        String color = COLORS[id % COLORS.length];
        Lid newLid = null;
        // String color
        if (type.equalsIgnoreCase("fearful")) {
            newLid = new FearfulLid(id, color);
        } else if (type.equalsIgnoreCase("crazy")) {
            newLid = new CrazyLid(id, color);
        } else if (type.equalsIgnoreCase("normal")){
            newLid = new Lid(id, color); 
        }
   
        if(newLid == null) throw new TowerException(TowerException.INVALID_ELEMENT_TYPE);
        return newLid;
    }
}