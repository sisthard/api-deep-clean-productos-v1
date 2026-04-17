# API Deep Clean - Gestión de Productos v1

Esta API es un microservicio desarrollado con **Quarkus** para la gestión de productos, integrando persistencia en base de datos, consumo de servicios externos mediante REST Client y mensajería asíncrona con Kafka y JSON.

## 🚀 Funcionalidades Principales

- **CRUD de Productos**: Gestión completa de inventario.
- **Validación de Datos**: Uso de Bean Validation para integridad de datos (SKU único, stocks positivos, etc.).
- **Mensajería Asíncrona**: Emisión y consumo de eventos Kafka ante cambios en productos.
- **Consumo Externo**: Integración con servicios de representantes de proveedores (EscuelaJS API).
- **Gestión de Excepciones**: Manejador global de errores con respuestas JSON estandarizadas y excepciones personalizadas (`ConflictException`, `AppException`, etc.).

---

## 🛠️ Requisitos Técnicos

- **Java**: 21
- **Base de Datos**: PostgreSQL
- **Mensajería**: Kafka (Local o vía Docker)
- **Framework**: Quarkus 3.15.1

---

## 🏗️ Estructura de Base de Datos

### Tablas Principales

1.  **`products`**: Almacena la información de los productos.
    - `id` (PK), `sku` (Único), `name`, `description`, `current_stock`, `status`, `created_at`, `updated_at`.
2.  **`product_logs`**: Bitácora de eventos consumidos desde Kafka.
    - `id` (PK), `productId`, `sku`, `action`, `description`, `timestamp`.

---

## 📡 Endpoints de la API

### Gestión de Productos (`/api/v1/products`)

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **GET** | `/all` | Lista todos los productos registrados. |
| **GET** | `/find/{id}` | Obtiene el detalle de un producto por ID. |
| **POST** | `/create` | Registra un nuevo producto y **emite un evento Kafka**. |
| **PUT** | `/update/{id}` | Actualiza un producto existente y **emite un evento Kafka**. |
| **DELETE** | `/delete/{id}` | Elimina un producto de la base de datos. |

### Representantes de Proveedores (`/api/v1/supplier-representative`)

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **GET** | `/info` | Obtiene información de representantes consumiendo un **servicio externo** y asignando manualmente el proveedor "DEEP CLEAN". |

---

## 📨 Mensajería Kafka (Event Driven)

El sistema utiliza un flujo de eventos para notificar cambios en el inventario:

1.  **Productor (`ProductProducer`)**: Al crear o actualizar un producto, se envía un mensaje JSON al tópico `products-events`.
2.  **Consumidor (`ProductConsumer`)**: Escucha el tópico `products-events` de forma asíncrona. Al recibir un mensaje, registra automáticamente una entrada en la tabla `product_logs`.

---

## ⚠️ Manejo de Errores

Se implementó un `GlobalExceptionHandler` que captura:
- **409 Conflict**: Cuando se intenta registrar un SKU duplicado.
- **400 Bad Request**: Errores de validación de campos.
- **404 Not Found**: Cuando el recurso solicitado no existe.
- **500 Internal Server Error**: Errores inesperados de infraestructura.

---

## 📝 Comandos Útiles

**Ejecutar en modo desarrollo:**
```sh
./mvnw quarkus:dev
```

**Compilar el proyecto:**
```sh
./mvnw clean package
```
