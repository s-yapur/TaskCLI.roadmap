

        
public class taskCli{
    public static void main(String[] args){
        TaskManager manager = new TaskManager();
        if(args.length<1){
                System.out.println("Ver comandos: taskCli help");
                return;
        }
        
        String command = args[0];
        
        // Ayuda - Muestra comandos disponibles
        if(command.equals("help")){
            System.out.println("Uso: taskCli <comando> [argumentos]");
            System.out.println("Comandos: 'add <descripción>' , 'status <id> <estado>', 'modify <id> <descripción> <estado>', 'list', 'delete <id>'.");
            System.out.println("Estados: 0= Por hacer, 1= En progreso, 2= Hecho");
        }else{
            try{

                // Añadir tarea
                switch(command){
                    case "add" -> { // Comando para añadir tarea
                        if(args.length!=2){
                            System.out.println("Entrada invalida: 'taskCli add <descripción>'"); // Corrección de comando 'add'
                        }else{
                            manager.addTask(args[1]);  // taskCli add (<descripción>)
                        }
                    }
                    case "status" -> { // Comando que modifica el estado de la tarea como(0= Por hacer, 1= En progreso, 2= Hecho)
                        if(args.length!=2){
                            System.out.println("Entrada invalida: 'taskCli status <id> <estado>"); // Corrección de comando 'add'
                        }else{    
                            int i = Integer.valueOf(args[1]);
                            manager.statusModifier(i, args[2]); // taskCli status (<id>, <estado>)
                        }
                    }
                    case "modify" -> { // Comando que modifica la tarea con el id pasado por teclado
                        if(args.length!=2){
                            System.out.println("Entrada invalida: 'taskCli modify <id> <estado> <descripción>'"); // Corrección de comando 'add'
                        }else{
                            int i = Integer.valueOf(args[1]);
                            manager.modifyTask(i, args[2], args[3]); // taskCli modify (<id>, <estado>, <descripción>)
                        }
                    }
                    case "list"-> { // Lista todas las tareas agendadas
                        if(args.length!=2){
                            System.out.println("Entrada invalida: 'taskCli list <id>"); // Corrección de comando 'add'
                        }else{
                            manager.listTasks(); // list (<id>)
                        }
                    }
                    case "delete"-> { // Elimina la tarea que tenga el id pasado por teclado
                        if(args.length!=2){
                            System.out.println("Entrada invalida: 'taskCli delete <id>'"); // Corrección de comando 'add'
                        }else{
                            int i = Integer.valueOf(args[1]);
                            manager.deleteTask(i); // delete (<id>)
                        }
                    }
                    default-> {
                        System.out.println("Ver comandos: taskCli help");
                        return;
                    }
                }
            }catch(Exception e){
                System.out.println("Error de argumentos");
            }
        }
    }   
}


    
    