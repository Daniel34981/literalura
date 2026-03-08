# 📚 LiterAlura

Aplicación de consola desarrollada en **Java con Spring Boot** que permite consultar libros desde la API de **Gutendex**, almacenarlos en una base de datos **PostgreSQL** y realizar distintas consultas sobre los libros y autores registrados.

Este proyecto fue desarrollado como parte del **Challenge LiterAlura** del programa **Oracle Next Education (ONE) + Alura Latam**.

---

# 🚀 Tecnologías utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Jackson
- API Gutendex
- IntelliJ IDEA

---

# 📖 API utilizada

La aplicación utiliza la API pública:

https://gutendex.com/

Esta API proporciona acceso a más de **70.000 libros del Project Gutenberg**.

Ejemplo de consulta:

```bash
https://gutendex.com/books/?search=don+quijote
```

---

# ⚙️ Configuración del proyecto

## 1️⃣ Clonar el repositorio

```bash
git clone https://github.com/Daniel34981/literalura
cd literalura
```

---

## 2️⃣ Crear base de datos en PostgreSQL

Abrir **pgAdmin** o cualquier cliente SQL y ejecutar:

```sql
CREATE DATABASE literalura;
```

---

## 3️⃣ Configurar application.properties

Ubicado en:

```
src/main/resources/application.properties
```

Configurar de la siguiente manera:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=postgres
spring.datasource.password=TU_PASSWORD

spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
```

---

## 4️⃣ Ejecutar la aplicación

Desde IntelliJ IDEA o usando Maven:

```bash
mvn spring-boot:run
```

---

# 🧠 Funcionalidades del sistema

La aplicación funciona mediante un **menú interactivo en consola**.

```text
===== LITERALURA =====

1 - Buscar libro por título
2 - Listar libros registrados
3 - Listar autores registrados
4 - Listar autores vivos en un determinado año
5 - Listar libros por idioma
6 - Mostrar cantidad de libros por idioma

0 - Salir
```

---

# 📚 Buscar libro por título

Consulta un libro en la API **Gutendex** y lo guarda en la base de datos.

Ejemplo:

```text
Ingrese el título del libro:
don quijote
```

Resultado:

```text
Título: Don Quijote
Autor: Cervantes Saavedra, Miguel de
Idioma: es
Número de descargas: 13446
```

Si el libro ya está registrado, el sistema evita duplicados.

---

# 📚 Listar libros registrados

Muestra todos los libros almacenados en la base de datos.

```text
----- LIBRO -----
Título: Don Quijote
Autor: Cervantes Saavedra, Miguel de
Idioma: es
Número de descargas: 13446
-----------------
```

---

# 👨‍🏫 Listar autores registrados

Lista todos los autores guardados junto con sus libros.

```text
----- AUTOR -----
Nombre: Shakespeare, William
Fecha de nacimiento: 1564
Fecha de fallecimiento: 1616
Libros: Hamlet
-----------------
```

---

# 📅 Listar autores vivos en un año

Permite consultar qué autores estaban vivos en un año determinado.

Ejemplo:

```text
Ingrese el año:
1600
```

Resultado:

```text
Cervantes Saavedra, Miguel de
Shakespeare, William
```

---

# 🌎 Listar libros por idioma

Consulta libros según el idioma almacenado en la base de datos.

Idiomas disponibles:

```text
es - español
en - inglés
fr - francés
pt - portugués
```

Ejemplo:

```text
Idioma: en
Resultado:
Pride and Prejudice
Hamlet
```

---

# 📊 Mostrar cantidad de libros por idioma

Permite ver estadísticas de libros registrados por idioma.

```text
Cantidad de libros en idioma 'es': 1
Cantidad de libros en idioma 'en': 2
```

---

# 🗂️ Estructura del proyecto

```
literalura
│
├── model
│   ├── Autor
│   ├── Libro
│   ├── DatosLibro
│   ├── DatosAutor
│   └── Datos
│
├── repository
│   ├── AutorRepository
│   └── LibroRepository
│
├── service
│   ├── ConsumoAPI
│   └── ConvierteDatos
│
├── principal
│   └── Principal
│
└── LiteraluraApplication
```

---

# 💾 Modelo de base de datos

El sistema utiliza dos entidades principales.

## 📘 Libro

- id
- título
- idioma
- número de descargas
- autor_id

## 👨‍🏫 Autor

- id
- nombre
- fecha de nacimiento
- fecha de fallecimiento

Relación:

```
Autor (1) ---- (N) Libro
```

---

# 🚀 Posibles mejoras

- Top **10 libros más descargados**
- Buscar **autor por nombre**
- Estadísticas usando **Streams**
- Mejor manejo de idiomas
- Crear **API REST**
- Interfaz gráfica

---

# 👨‍💻 Autor

**Daniel Suárez**

Proyecto desarrollado para el challenge **LiterAlura - Oracle Next Education + Alura Latam**

---

# 🏁 Estado del proyecto

✔ Proyecto finalizado  
✔ Todas las funcionalidades obligatorias implementadas