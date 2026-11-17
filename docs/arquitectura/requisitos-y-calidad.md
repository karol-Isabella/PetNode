# PetNode · Requisitos y calidad arquitectónica

**Responsable:** Persona 1. **Estado:** propuesta para revisión del equipo.

PetNode centraliza las solicitudes de peluquería, consulta veterinaria y paseo. El dueño registra sus mascotas y solicita servicios. El coordinador consulta las solicitudes, asigna al empleado y confirma la cita.

La priorización se basa en la especificación del proyecto y en el código de `main` revisado. [Requisitos de la wiki][requisitos] · [Historias de usuario][historias] · [Commit `98afe63`][base].

## Drivers priorizados

**P1** orienta las primeras decisiones arquitectónicas. **P2** indica impacto relevante que se evalúa después de los drivers P1. El orden dentro de cada grupo es propuesto y refleja el impacto sobre el flujo de citas y sus datos.

### Drivers funcionales

| Orden | Driver | Requisitos e historias | Impacto arquitectónico |
| --- | --- | --- | --- |
| 1 · P1 | **DF01 · Identidad, perfil y mascotas del dueño.** Registrar y autenticar al dueño, permitirle gestionar su perfil y sus mascotas, y distinguirlo del coordinador. | RF001–RF007 · HU001–HU005 | La identidad autenticada y la relación dueño–mascota delimitan el acceso a los datos y a las solicitudes. |
| 2 · P1 | **DF02 · Radicación de solicitudes.** Crear citas de peluquería, consultas médicas o paseos para una mascota del dueño. Registrar la solicitud como pendiente y calcular en el servidor el total de peluquería según los servicios elegidos. | RF012–RF016 · HU006–HU008 | Los tres tipos comparten el ciclo de vida y requieren validar datos distintos. Peluquería depende de los precios del catálogo. |
| 3 · P1 | **DF03 · Gestión y confirmación de citas.** El coordinador consulta las solicitudes y sus detalles, asigna un empleado disponible y confirma la cita. La confirmación cambia el estado y genera el correo al dueño. | RF017–RF022 · HU012–HU013 | La asignación, el estado persistido y el correo requieren resultados coherentes incluso ante concurrencia o fallos. |
| 4 · P2 | **DF04 · Administración de empleados y catálogo.** El coordinador mantiene el personal y los servicios, incluidos los precios de peluquería. | RF008–RF011 · HU010–HU011 | Empleados y servicios son referencias que condicionan la asignación y la cotización de citas. |
| 5 · P2 | **DF05 · Historial del dueño.** Mostrar las solicitudes de sus mascotas con tipo, fecha, hora y estado. | RF023 · HU009 | La consulta del dueño tiene un alcance distinto al listado administrativo y depende de que cada solicitud conserve su relación con el dueño. |

### Drivers de calidad

| Orden | Driver | Fundamento | Escenario |
| --- | --- | --- | --- |
| 1 · P1 | **DC01 · Seguridad y aislamiento de datos.** Aplicar los permisos según rol y pertenencia del recurso. Proteger las contraseñas almacenadas con hash. | Separación de actores en RF003–RF010 y RF017–RF023, y protección de contraseñas en RNF007. | [E01](#e01--aislamiento-de-datos-y-operaciones) |
| 2 · P1 | **DC02 · Integridad del ciclo de la cita.** Mantener coherentes la asignación, el estado y la confirmación cuando llegan peticiones simultáneas. | RF016 y RF019–RF022, con disponibilidad del empleado en [HU013][confirmacion]. | [E02](#e02--confirmación-coherente-de-la-cita) |
| 3 · P1 | **DC03 · Rendimiento de las consultas.** Responder en máximo tres segundos en operación normal. | RNF010, aplicado a RF017 y RF023. | [E03](#e03--tiempo-de-respuesta-de-las-consultas) |
| 4 · P2 | **DC04 · Disponibilidad ante fallos de correo.** Preservar la confirmación y poder recuperar el envío cuando el correo vuelva a estar disponible. | RF022 y RNF008 sustentan la propuesta de tolerancia a fallos. | [E04](#e04--recuperación-del-envío-de-correo) |
| 5 · P2 | **DC05 · Validación y contrato de errores.** Rechazar datos inválidos antes de guardar y comunicar errores que el cliente pueda interpretar. | RNF005, RNF006 y RNF008, aplicados a RF012–RF016. | [E05](#e05--rechazo-de-solicitudes-inválidas) |

## Árbol de utilidad

Cada hoja indica **(importancia, dificultad)**. La importancia es **A** si el escenario compromete los datos, el flujo principal o un umbral explícito de la wiki, y **M** si afecta un caso acotado o la recuperación de una dependencia. La dificultad es **A** si cumplir la medida exige coordinar varios componentes o controlar fallos y concurrencia, y **M** si el alcance se concentra en un flujo existente o en la medición de sus consultas. Ambas son estimaciones sobre la línea base revisada.

```text
Utilidad de PetNode
├── Seguridad
│   └── E01 · Aislar recursos y operaciones por usuario y rol (A, A)
├── Integridad de datos
│   └── E02 · Confirmar una sola vez con empleado válido (A, A)
├── Rendimiento
│   └── E03 · Responder a las consultas en ≤ 3 s (A, M)
├── Disponibilidad
│   └── E04 · Recuperar el correo tras un fallo de envío (M, A)
└── Validación y manejo de errores
    └── E05 · Rechazar solicitudes inválidas sin persistirlas (M, M)
```

E01 y E02 tienen alta dificultad porque las reglas atraviesan la autorización, los servicios y la persistencia. E04 requiere conservar el resultado del envío y recuperarlo ante una falla de una dependencia externa. E03 y E05 se pueden evaluar primero sobre consultas y operaciones del flujo de citas.

## Restricciones

| Tipo | Restricción | Fuente o criterio |
| --- | --- | --- |
| Negocio | El dueño gestiona sus propios datos y mascotas, y consulta su propio historial. La consulta global de solicitudes, la gestión de personal y catálogo, y la confirmación corresponden al coordinador. | RF003–RF010 y RF017–RF023 · HU003–HU005, HU009–HU013. |
| Negocio | Cada solicitud empieza pendiente. La confirmación exige un empleado disponible asignado y genera un correo al dueño con fecha, hora y empleado. El total de peluquería se calcula a partir de los servicios seleccionados. | RF012–RF016 y RF019–RF022 · HU006 y HU013. |
| Técnica | La línea de trabajo documentada emplea Spring Boot y MongoDB, con modelos, DTO, repositorios, servicios y controladores. Cualquier cambio a esos requisitos debe acordarse con el equipo. | RNF001–RNF003 y [`pom.xml`][pom]. |
| Técnica | La interfaz cliente–servidor se documenta con Swagger/OpenAPI y usa REST con JSON. Las operaciones deben validar entradas, gestionar errores y proteger las contraseñas persistidas. | RNF004–RNF009. El backend actual compila para Java 21 y configura BCrypt como codificador de contraseñas. [Configuración de seguridad][seguridad]. |
| Proyecto | La API existente es la base de trabajo. Los ajustes de implementación se acotan a las decisiones que el equipo apruebe. | Alcance acordado para el trabajo de arquitectura y [línea base revisada][base]. |

## Escenarios verificables

Los casos se plantean para un ambiente de integración con datos sintéticos y servicios controlados. El límite de **3 s** proviene de RNF010. Los demás valores numéricos y la carga de prueba son propuestas para validar en equipo. La evaluación funcional queda pendiente de ejecutar.

### E01 · Aislamiento de datos y operaciones

- **Fuente.** Dos dueños con cuentas distintas y un coordinador.
- **Estímulo.** Se registran las cuentas de dueño y, tras iniciar sesión, cada dueño opera sobre sus recursos. La prueba incluye solicitudes sobre recursos del otro dueño y operaciones reservadas al coordinador.
- **Entorno.** Operación normal con una mascota y una cita de prueba por dueño y un juego de operaciones permitidas para el coordinador.
- **Artefacto.** Registro de cuentas y control de acceso de la API de perfiles, mascotas, historial y operaciones administrativas de citas.
- **Respuesta.** El registro persiste la contraseña como hash. La API entrega solo los recursos propios a cada dueño, rechaza las operaciones fuera de su alcance y permite al coordinador realizar las operaciones que le corresponden.
- **Medida.** Las dos cuentas almacenan un hash verificable con el codificador configurado. El 100 % de los controles permitidos devuelve los datos esperados. Todos los intentos fuera de alcance reciben 403 o 404 según el contrato, con cero registros ajenos en las respuestas y cero cambios de esos recursos.

### E02 · Confirmación coherente de la cita

- **Fuente.** Un coordinador autenticado y dos clientes de prueba que actúan con permisos de coordinador.
- **Estímulo.** Se intenta confirmar una solicitud pendiente sin empleado. Después de asignar un empleado disponible se envían dos confirmaciones simultáneas para esa misma cita.
- **Entorno.** Operación normal, con MongoDB disponible y un buzón de pruebas que acepta envíos.
- **Artefacto.** Servicio de citas, persistencia del estado e integración de correo.
- **Respuesta.** La primera operación conserva el estado pendiente. Tras la asignación se realiza una sola transición a confirmada y se genera un correo para el dueño.
- **Medida.** Antes de la asignación hay cero transiciones y cero correos. Después, hay una sola transición en la evidencia registrada por la prueba. El buzón captura un único mensaje para el dueño correcto con fecha, hora y empleado en los 30 s posteriores a la confirmación.

### E03 · Tiempo de respuesta de las consultas

- **Fuente.** Clientes de prueba autenticados como dueños o coordinadores.
- **Estímulo.** Consultan el historial propio y el listado del coordinador.
- **Entorno.** API y MongoDB en funcionamiento estable, con 1.000 citas de prueba repartidas entre 100 dueños y cinco clientes concurrentes. Se excluye el arranque de la aplicación de esta definición propuesta de operación normal.
- **Artefacto.** API de consulta de citas y acceso a MongoDB.
- **Respuesta.** La API devuelve el resultado correcto según el rol y alcance de cada consulta.
- **Medida.** Realizar 100 consultas válidas, 50 por tipo, y medir desde el envío hasta recibir el cuerpo completo. Las 100 deben devolver el conjunto esperado en ≤ 3 s. Registrar el máximo y el percentil 95 por tipo para comparar ejecuciones.

### E04 · Recuperación del envío de correo

- **Fuente.** Un coordinador autenticado y un servidor SMTP de pruebas.
- **Estímulo.** El coordinador confirma una cita pendiente con empleado asignado mientras el servidor SMTP rechaza temporalmente los envíos. Luego se restablece la aceptación de mensajes.
- **Entorno.** API y MongoDB disponibles durante la falla controlada del correo.
- **Artefacto.** Flujo de confirmación e integración de notificaciones.
- **Respuesta.** La API confirma la cita y el envío pendiente queda visible para operación. Una vez restablecido el correo, se recupera el envío de esa confirmación.
- **Medida.** La confirmación devuelve 2xx y la consulta posterior muestra el estado confirmado. Existe un envío pendiente asociado a la cita durante la falla. El buzón acepta exactamente un mensaje de esa confirmación en los cinco minutos siguientes a la recuperación del servidor.

### E05 · Rechazo de solicitudes inválidas

- **Fuente.** Un dueño autenticado con una mascota registrada.
- **Estímulo.** Presenta solicitudes de peluquería con datos inválidos para el negocio, como una fecha pasada o un servicio inexistente.
- **Entorno.** Operación normal con catálogo y base de datos de prueba disponibles.
- **Artefacto.** Entrada REST de solicitudes, validación de reglas y persistencia de citas.
- **Respuesta.** La API devuelve un error de cliente en JSON que identifica el dato o la regla rechazada, y conserva los datos existentes.
- **Medida.** Para cada caso inválido se recibe el código 4xx documentado y una causa verificable en el cuerpo JSON. Se persisten cero citas y se conservan los registros previos sin cambios. El control con datos válidos persiste una cita pendiente.

## Evidencia de la línea base y puntos para revisión

| Drivers | Evidencia observada en el código | Qué debe verificar el equipo |
| --- | --- | --- |
| DF01, DF05, DC01 | El registro aplica BCrypt y guarda la cuenta de usuario, pero construye el perfil en memoria. Hay autenticación JWT y una regla general de autenticación. El listado de citas usa `findAll()` y el repositorio ya declara `findByOwnerId`. [Registro][duenos] · [Seguridad][seguridad] · [Citas][citas] · [Repositorio de citas][repo-citas]. | Persistencia del perfil y aplicación de permisos por rol y pertenencia en todo el flujo. Ejecutar E01 con cuentas de prueba. |
| DF02, DF04, DC05 | El alta de citas usa `GROOMING` y conserva el estado inicial `PENDING`. El servicio guarda la cita sin calcular `totalCost`. Existe un catálogo de peluquería. El manejador global de excepciones responde con mensajes de texto. [Citas][citas] · [Modelo de cita][modelo-cita] · [Catálogo][catalogo] · [Manejador de excepciones][errores]. | Radicar los tres tipos con sus campos, validar las referencias y comprobar que el total coincida con los precios seleccionados. Ejecutar E05. |
| DF03, DC02, DC04 | El flujo guarda el empleado sin comprobar disponibilidad, confirma sin verificar asignación y llama al correo después de guardar. El destinatario es de ejemplo y el servicio de notificación escribe en consola si falta el componente de envío. [Citas][citas] · [Notificación][correo]. | Acordar cómo se determina la disponibilidad del empleado. Ejecutar E02 y E04, incluidos destinatario, contenido y recuperación del envío. |
| DC03 | La consulta administrativa recupera todas las citas con `findAll()`. La suite revisada contiene una prueba de arranque del contexto. [Citas][citas] · [Prueba existente][test]. | Confirmar el volumen y la concurrencia que representan la operación normal. Ejecutar E03 y registrar los resultados. |

[requisitos]: https://github.com/karol-Isabella/PetNode/wiki/2.-Introducci%C3%B3n#21-requerimientos-del-sistema
[historias]: https://github.com/karol-Isabella/PetNode/wiki/3.-Historias-de-Usuario
[confirmacion]: https://github.com/karol-Isabella/PetNode/issues/13
[base]: https://github.com/karol-Isabella/PetNode/commit/98afe63f8cce5a0e511e1ec17d3c37fb1104a44f
[pom]: ../../pom.xml
[seguridad]: ../../src/main/java/com/petnode/security/SecurityConfig.java
[duenos]: ../../src/main/java/com/petnode/service/impl/OwnerServiceImpl.java
[citas]: ../../src/main/java/com/petnode/service/impl/AppointmentServiceImpl.java
[repo-citas]: ../../src/main/java/com/petnode/repository/AppointmentRepository.java
[modelo-cita]: ../../src/main/java/com/petnode/model/Appointment.java
[catalogo]: ../../src/main/java/com/petnode/service/impl/ServiceManagementServiceImpl.java
[errores]: ../../src/main/java/com/petnode/exception/GlobalExceptionHandler.java
[correo]: ../../src/main/java/com/petnode/service/impl/NotificationServiceImpl.java
[test]: ../../src/test/java/com/petnode/PetnodeApiApplicationTests.java
