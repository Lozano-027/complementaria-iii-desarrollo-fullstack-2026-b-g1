# Entity y Repository - Task Manager

## 1. Entity

Para el sistema de gestión de tareas se creó la entidad `Task`, que representa una tarea dentro de la aplicación.

La entidad utiliza la anotación `@Entity` para representar una tabla y `@Id` para identificar de forma única cada registro.

### Atributos

* `id`: identificador único de la tarea.
* `title`: título de la tarea.
* `description`: descripción de la tarea.
* `status`: estado actual de la tarea.

La estructura de la entidad se encuentra en:

`entity/Task.java`

## 2. Repository

Se creó `TaskRepository`, que extiende `JpaRepository<Task, Long>`.

Esto permite utilizar las operaciones básicas de persistencia sin tener que implementarlas manualmente.

La estructura se encuentra en:

`repository/TaskRepository.java`

### Consulta por método

Se definió el siguiente método:

`findByStatus(String status)`

Este método permite consultar las tareas según su estado.

Por ejemplo, permite obtener las tareas que tengan un estado determinado como `PENDING` o `COMPLETED`.

## 3. Operaciones CRUD

### Create

Se utilizaría `save()` para crear una nueva tarea y almacenarla.

### Read

Se utilizarían `findAll()` para consultar todas las tareas y `findById()` para consultar una tarea específica.

### Update

Se utilizaría `save()` para actualizar una tarea existente después de modificar sus datos.

### Delete

Se utilizaría `deleteById()` para eliminar una tarea utilizando su identificador.

## 4. Resumen

La Entity `Task` representa los datos de una tarea, mientras que `TaskRepository` permite acceder y modificar esos datos. `JpaRepository` proporciona las operaciones básicas de CRUD y además se agregó la consulta `findByStatus()` para buscar tareas según su estado.
