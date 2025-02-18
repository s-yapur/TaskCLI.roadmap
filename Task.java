


//import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task{
    //@JsonProperty("id")
    private Integer id; // Identificador unico
    //@JsonProperty("Descripcion")
    private String descripcion; // Descripcion de la tarea
    //@JsonProperty("Estado")
    private String estado; // Estado de la tarea (Por hacer; En proceso; Hecho)
    //@JsonProperty("Creada el ")
    private LocalDateTime creadaEl; // Fecha de creacion de la tarea
    //@JsonProperty("Actualizada el ")
    private LocalDateTime actualizadaEl; // Ultima fecha que la tarea fue actualizada    
    private String updated;
    private String created;
    // Constructores default
    
    public Task() {
        this.id = 0;
        this.descripcion = null;
        this.estado = "Por hacer.";
        this.creadaEl = LocalDateTime.now();
        this.created = creadaEl.format(formatter);
        this.actualizadaEl = LocalDateTime.now();
        this.updated = actualizadaEl.format(formatter);
    }    
    // Constructores
    public Task(Integer id, String descripcion, String estado, LocalDateTime creadaEl, LocalDateTime actualizadaEl) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
        this.creadaEl = creadaEl;
        this.created = creadaEl.format(formatter);
        this.actualizadaEl = actualizadaEl;   
        this.updated = actualizadaEl.format(formatter);
    }
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    // Getters y Setters
    public Integer getId(){
        return id;
    }
    public void setId(int id){
        this.id = Integer.valueOf(id);
    }
    public String getDescripcion(){
        return descripcion;
    }
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
        this.setActualizadaEl();
    }
    public String getEstado(){
        return estado;
    }
    public void setEstado(String estado){
        if(!estado.isEmpty()){
            this.estado = estado;
            this.setActualizadaEl();            
        }
    }
    public  String getCreadaEl(){
        return created;
    }
    public void jsonCreadoEl(LocalDateTime json){ // Leido desde el json
        this.creadaEl = json;
    }  
    public void setCreadoEl(){
        this.creadaEl = LocalDateTime.now();
    }

    public String getActualizadaEl(){
        return updated;
    }
    public void setActualizadaEl(){ // Metodo que se utiliza para actualizar con cada cambio realizado
        this.actualizadaEl = LocalDateTime.now();
    }
    public void jsonActualizadaEl(LocalDateTime json){ // Leido desde el json
        this.actualizadaEl = json;
    }
    
   // @Override
    //public String toString(){
    //    return "Tarea{id="+id+", Descripcion='"+descripcion+"', Estado='"+estado+"', Creada el='"+creadaEl+"', actualizada el='"+actualizadaEl+"'}";
   // }
}


