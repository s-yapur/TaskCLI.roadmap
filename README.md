## Task C.L.I - Interfaz de la línea de comandos
Un programa CLI para organizar tareas utilizando funciones simples.
## Cómo opera?
1. Para utilizar este programa, primero debes clonar o descargar el repositorio (asegurate de encontrarte en el directorio donde quieras clonarlo).
```
git clone https://github.com/s-yapur/TaskCLI.roadmap.git
```

2. Luego, abre la consola y navega al siguiente directorio:
```
\TaskCLI.roadmap.git\build\classes
```
3. Ubicados ya en el directorio, comenzamos a operar. Cada operación que se haga debe empezar con **>java taskCli**, luego se procederá a ingresar la Función deseada junto a los argumentos que se soliciten.
A continuacion vera los parámetros necesarios en toda ejecución.
```
java taskCli <Función> [Argumentos]
```
Si lo desea, el programa tiene una función que te muestra el índice de funciones dentro del programa, para acceder a el utilice el siguiente comando:
```
java taskCli help
```
## Funciones
1. **add:** La función 'add' le permite al usuario añadir una nueva tarea a la lista de tareas, al momento de añadirla se le pedira una descripción para almacenarla, si esta cuenta con mas de una palabra se debe ingresar entre comillas la descripción completa.
```bash
java taskCli add [Descripción]
```
2. **status:** Con 'status' podremos modificar el estado/progreso de una tarea localizada por su id, las opciones posibles son las siguientes -> 1 = En progreso, 2 = Hecho.
```bash
java taskCli status [id] [estado]
```
3. **modify:** Con 'modify' podremos modificar una tarea localizada por su id en su totalidad, se le pedira que ingrese una nueva descripción y el estado actual de la misma.
```bash
java taskCli modify [id] [descripción] [estado]
```
4. **list:** La función 'list' nos listara las tareas por separado, visualizando cada dato ingresado en la misma.
```bash
java taskCli list
```
5. **list:** Con 'delete' podremos eliminar una tarea de la lista localizada por su id.
```bash
java taskCli delete [id]
```

## Ejemplos
```
# Añadiendo una tarea
java taskCli add "Comprar comida"
# Tarea agregada correctamente(id: 1).

# Modificando el estado de una tarea
java taskCli status 1 1
# Output: Tarea marcada como 'En progreso'(id: 1)

java taskCli status 1 2
# Output: Tarea marcada como 'Hecho'(id: 1)

task-cli list todo
task-cli list in-progress

# Actualizando una tarea
task-cli update 1 "Buy groceries and cook dinner"
task-cli delete 1

# Marking a task as in progress or done
task-cli mark-in-progress 1
task-cli mark-done 1

# Listing all tasks
task-cli list

```

## Por qué un programa tan simple?
Este programa esta hecho con el objetivo de ayudarme a entrenar con java, es mi primer programa en el dicho lenguaje asi que decidi buscar en internet como fortalecer mi conocimiento a traves de la practica con el mismo, es por ello que me encontre con 'https://roadmap.sh/projects/task-tracker' el cual me resulto interesante y puse manos a la obra. Falta muchos arreglos para que este al 100% pero con el tiempo lo ire acomodando.

## Agradecimientos
Gracias por husmear mi repositorio, cualquier tipo de feedback es bienvenido y le estare mas que agradecido, exitos.
