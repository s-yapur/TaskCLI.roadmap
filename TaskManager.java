


//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;


public class TaskManager{
        private static final String FILE_PATH = "tasks.json"; // Ruta del archivo Json
        private List<Task> tasks; // Lista de tareas copiadas del .json
        private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //private ObjectMapper objectMapper; // Manejara la conversion a .json
        

        // Constructor  
        public TaskManager(){ // Inicializo los objetos.    

//this.objectMapper = new ObjectMapper();
//this.objectMapper.registerModule(new JavaTimeModule()); // Me permite almacenar el tipo de dato "DateTime" en el json
            
            this.tasks = loadTasks(); // Carga la lista desde el archivo
        }    
        // Cargar tareas a la lista
        public List<Task> loadTasks(){
            Path jsonPath = Paths.get(FILE_PATH); // Crea el flujo con el archivo .json
            if(!Files.exists(jsonPath)){ // Verifica la existencia de dicho archivo
                return new ArrayList<>(); // Si no existe, devuelve una lista vacia
            }else{
                try{ // Si existe, copia los datos del archivo en la lista
                    String jsonContent = new String(Files.readAllBytes(jsonPath)); // Json->Lista de tareas
                    return parseTasksFromJson(jsonContent);

//return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Task.class));
                
                }catch(IOException e){ // Si ocurre una excepcion, esta lo demuestra
                    e.printStackTrace(); // Enseña la excepcion por consola
                    return new ArrayList<>(); // Devuelve una lista vacia
                }
            }
        }
        // Parsing del json manualmente a lista de tareas
        private List<Task> parseTasksFromJson(String jsonContent){
            List<Task> tasksList = new ArrayList<>();
            
            //Elimina corchetes iniciales y finales del .json
            jsonContent = jsonContent.trim();
            if(jsonContent.startsWith("{") && jsonContent.endsWith("}")){
                jsonContent = jsonContent.substring(1, jsonContent.length()-1).trim();
            }
            // Separamos tareas y convertimos a objetos (asumiendo que estan separados por "},{")
            String[] taskJsons = jsonContent.split("},\\s*\\{");
            for(String taskJson:taskJsons){
                taskJson = taskJson.trim();
                // Nos aseguramos que cada tarea empieza y termina con llaves { }
                if(!taskJson.startsWith("{")) taskJson = "{"+taskJson;
                if(!taskJson.endsWith("}")) taskJson = taskJson+"}";
                Task task = parseTaskJson(taskJson);
                tasksList.add(task);
            }
            return tasksList;
        }
        // Pasar datos json a objeto tarea
        private Task parseTaskJson(String taskJson){
           Task task = new Task();
           // Pasamos cada propiedad a un atributo de tarea, manualmente
           String id = readJsonData(taskJson, "id");
           String desc = readJsonData(taskJson, "Descripción");
           String status = readJsonData(taskJson, "Estado");
           String created = readJsonData(taskJson, "Creada el");
           String updated = readJsonData(taskJson, "Actualizada el");
            
           if(id!=null)
               task.setId(Integer.parseInt(id));
           if(desc!=null)
               task.setDescripcion(desc);
           if(status!=null)
               task.setEstado(status);
           if(created!=null)
               task.jsonCreadoEl(LocalDateTime.parse(created, formatter));
           if(updated!=null)
               task.jsonActualizadaEl(LocalDateTime.parse(updated, formatter));
           return task;
        }
        
        //Extrae los datos en tipo json y los pasa a tarea
        private String readJsonData(String json, String data){
            String dataCom = "\""+data+"\""; // Lee los datos de parseTaskJson con comillas
            int search = json.indexOf(dataCom); // Este int nos ayuda a buscar en el archivo
            if(search==-1)
                return null; //  No existe el dato propuesto
            int start = json.indexOf(":", search)+1;
            int end = json.indexOf(",", start);
            if(end==-1) 
                end = json.indexOf("}", start);
            if(start==-1 || end==-1) 
                return null;
            
            return json.substring(start,end).trim().replaceAll("^\"|\"$",""); // Limpia las comillas
        }
        // Guardar en archivo json
        public void saveTask(){
            try{
                Path jsonPath = Paths.get(FILE_PATH); // Buscamos archivo
                String jsonContent = toJson(tasks); // Obtiene los datos convertidos a string soportado por json
                Files.write(jsonPath, jsonContent.getBytes()); // Escribe en el archivo json
                }catch(IOException e){
                    e.printStackTrace();
            }
        }
        // Convierte los datos tipo "Task" convertidos a string soportado por json
        private String toJson(List<Task> tasks){
            StringBuilder json = new StringBuilder("["); // Caracter inicial
            for(int i=0; i<tasks.size(); i++){
                Task task = tasks.get(i);
                json.append("{")
                    .append("\"id\":").append(task.getId()).append(",")
                    .append("\"Descripción\":\"").append(task.getDescripcion()).append("\",")
                    .append("\"Estado\":\"").append(task.getEstado()).append("\",")
                    .append("\"Creada el\":\"").append(task.getCreadaEl()).append("\",")
                    .append("\"Actualizada el\":\"").append(task.getActualizadaEl()).append("\"")
                    .append("}");
                if(i<tasks.size()-1) // Si aun quedan tareas por cargar, se seguira separando 
                    json.append(","); // por comas
            }
            json.append("]"); // Caracter final
            return json.toString();
        }
        
        
        
        // Añadir tarea a la lista
        public void addTask(String desc){
            Task task = new Task(); // Crea un nuevo objeto tarea
            this.setterId(task);// Le otorga un id unico a la tarea
            task.setDescripcion(desc); // Copia la descripcion al objeto tarea
            Integer i=0;
            if(this.tasks==null){
                this.tasks.get(0).equals(task);
            }else{
                this.tasks.add(task); // Ingresa la tarea a la lista de tareas
            }
            saveTask(); // Modifica la tarea en el archivo
            System.out.println("Tarea agregada correctamente(id: "+task.getId()+").");
        }
        // Actualizar una tarea de la lista
        public void modifyTask(Integer id, String desc, String status){
            Boolean idFound = false;
            for(Task task:tasks){ // Itera todas las tareas en la lista
                if(task.getId().equals(id)){ // Modifica la tarea deseada mediante la localizacion por id
                    idFound = true;
                    task.setDescripcion(desc); // Copia la descripcion
                    this.taskStatus(task, status); // Copia el estado
                    saveTask(); // Modifica la tarea en el archivo
                    System.out.println("Tarea modificada correctamente(id: "+task.getId()+").");
                    break;
                }               
            }
            if(!idFound){ // Si no encuentra el id en la lista, retorna falso y le avisa al usuario
                System.out.println("El id solicitado no fue encontrado");
            }
        }
        // Elimina una tarea de la lista
        public void deleteTask(Integer id){
            Integer size = tasks.size(); // Guardo el tamañno anterior para verificar luego si se borro correctamente
            tasks.removeIf(task->task.getId().equals(id)); // Elimina la tarea deseada mediante la localizacion por id
            if(size>tasks.size()){
                System.out.println("Se elimino la tarea de la lista de tareas (id: "+id+")"); // Si el tamaño actual es menor al anterior, se piensa que se borro correctamente
            }else{
                System.out.println("No se pudo eliminar la tarea de la lista de tareas o no existe."); // Si el tamaño es igual, se piensa que no se pudo borrar por algun error o directamente no existe el id en la lista
            }  
            this.sortTasksById();
            if(this.tasks.get(0)!=null)
                saveTask(); // Modifica la tarea en el archivo
            else
                System.out.println("Se elimino el ultimo elemento de la lista");
        }
        // Listar tareas
        public void listTasks(String status){	
            switch(status){
                case "0" -> {
                    status="Todos";
                }
                case "1" -> {
                    status="En progreso.";
                }
                case "2" -> {
                    status="Hecho.";
                }
                default -> {
                    System.out.println("Estado invalido.");
                    return;
                }
            }
            
            if (!this.tasks.isEmpty()){
                this.sortTasksById();
                for (Task task:tasks){ // Lista tarea por tarea hasta acabar con la misma
                    if(status=="Todos" || task.getEstado().equals(status)){
                        System.out.println("======================================="); // Separador por iteracion              
                        System.out.println("id: "+task.getId());                
                        System.out.println("Descripcion: "+task.getDescripcion());                
                        System.out.println("Estado: "+task.getEstado());                
                        System.out.println("Creada el: "+task.getCreadaEl());
                        System.out.println("Actualizada el: "+task.getActualizadaEl()); 
                    }
                }
                System.out.println("======================================="); // Separador final
            }else{
                System.out.println("No se encontraron elementos en la lista"); // Avisa al usuario que la lista no contiene elementos
            }
        }
        /*Guardar los cambios de las listas dados en los metodos
        public void saveTask(){
            try{
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), tasks); // Escribe las tareas en el archivo .json               
            }catch(IOException e){
                e.printStackTrace(); // Manejo de excepciones
            }        
        }
        */
        
        // Marcar estado de la tarea
        public void taskStatus(Task task, String status){
            switch(status){
                case "1" -> {
                    task.setEstado("En progreso.");
                    System.out.println("Tarea marcada como 'En progreso'(id: "+task.getId()+")");
                }
                case "2" -> {
                    task.setEstado("Hecho.");
                    System.out.println("Tarea marcada como 'Hecho'(id: "+task.getId()+")");
                }
            }
            
        }
        // Modificar estado de la tarea
        public void statusModifier(int id, String status){
            Boolean idFound = false; // Verifica si se pudo encontrar el id
            for(Task task:tasks){ // Itera todas las tareas en la lista
                if(task.getId().equals(id)){ // Modifica la tarea deseada mediante la localizacion por id
                    idFound = true;
                    this.taskStatus(task, status); // Llama el metodo que se utiliza para cambiar el estado de una tarea
                    saveTask(); // Modifica la tarea en el archivo
                    break;
                }               
            }
            if(!idFound){ // Si no encuentra el id en la lista, retorna falso y le avisa al usuario
                System.out.println("El id solicitado no fue encontrado");
            }
        }
        // Organizador de id
        public void setterId(Task task){
            this.sortTasksById();
            int id = 1;
            for(Task taskc:tasks){ // Itera por cada task
                if(taskc.getId().equals(id)){ // Verifica si el id esta siendo utilizado, desde el id 1, por cada tarea en la lista
                    ++id;
                }
            }
            task.setId(id);
            this.sortTasksById();
            return;
        }
        
    // Ordena la lista de tasks por id de manera ascendente
    public void sortTasksById() {
        Collections.sort(this.tasks, new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                return Integer.compare(task1.getId(), task2.getId()); // Ordena por id de menor a mayor
            }
        });
    }
}
