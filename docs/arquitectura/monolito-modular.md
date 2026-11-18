# PetNode · Monolito modular con capas

PetNode se despliega como una sola aplicación Spring Boot. El código se organiza por **módulo de negocio** y, dentro de cada módulo, por las **capas** que ya usaba el proyecto.

## Estructura

```text
com.petnode
├── PetnodeApiApplication
├── auth          Identidad, login, JWT y seguridad (User, ServiceCoordinator)
├── owner         Registro y perfil del dueño
├── pet           Mascotas del dueño
├── employee      Personal y roles
├── catalog       Catálogo de servicios y precios
├── appointment   Solicitud, asignación y confirmación de citas
├── notification  Correos de confirmación
└── shared        Configuración transversal (config) y excepciones (exception)
```

Cada módulo tiene las mismas capas, creadas solo si el módulo las necesita:

```text
<modulo>
├── controller    API REST (depende de dto y service)
├── dto           Contratos de entrada y salida
├── service       Interfaces de casos de uso (API pública del módulo)
│   └── impl      Implementaciones
├── repository    Acceso a MongoDB
└── model         Documentos y enums del dominio
```

## Reglas de dependencia

- Dentro de un módulo: `controller → service → repository → model`. Los controladores no acceden a repositorios.
- Entre módulos solo se usa la capa `service` (y los tipos `model` compartidos que se indican abajo). Un módulo no usa los repositorios de otro.
- `shared` no depende de ningún módulo de negocio.

## Dependencias actuales entre módulos

| Origen | Destino | Qué usa |
| --- | --- | --- |
| `appointment` | `notification` | `NotificationService` para el correo de confirmación |
| `appointment` | `catalog` | `ServiceType` (enum del tipo de servicio) |
| `owner` | `auth` | `UserRepository` y `User` al registrar la cuenta del dueño |
| `auth` | `shared` | Excepciones |

## Pendientes

- `owner → auth` accede a un repositorio de otro módulo, lo que incumple la regla. Conviene exponer un servicio en `auth` para crear cuentas y usarlo desde `owner`.
- `ServiceType` podría moverse a `shared` o a un contrato propio de `appointment` para no acoplar `appointment` al modelo de `catalog`.
- La seguridad (`auth.security`) concentra `SecurityConfig` y el filtro JWT, que afectan a toda la aplicación.
- Una futura verificación automática de las reglas (por ejemplo con ArchUnit o Spring Modulith) evitaría que se rompan.
