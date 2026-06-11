# Ingesta de Documentos - Mensapex

Microservicio Java desarrollado durante el periodo de practicas para la recepcion, validacion y persistencia de mensajes FHIR relacionados con la ingesta de documentos clinicos.

El proyecto forma parte del ambito asistencial y trabaja sobre una arquitectura modular con separacion entre dominio, casos de uso, persistencia, capa REST y arranque sobre Open Liberty.

## Indice

1. [Descripcion](#descripcion)
2. [Tecnologias](#tecnologias)
3. [Instalacion y uso](#instalacion-y-uso)
4. [Capturas de pantalla y evidencias](#capturas-de-pantalla-y-evidencias)
5. [Funcionalidades principales](#funcionalidades-principales)
6. [Arquitectura del proyecto](#arquitectura-del-proyecto)
7. [Aprendizajes clave](#aprendizajes-clave)
8. [Mejoras futuras](#mejoras-futuras)
9. [Autor](#autor)

## Descripcion

Este repositorio contiene un microservicio orientado a la gestion de mensajes de integracion de Mensapex. Su responsabilidad principal es exponer un endpoint REST que recibe un recurso FHIR `Bundle`, aplica reglas de validacion segun el tipo de documento, transforma la informacion recibida hacia el modelo de dominio y la persiste en una base de datos Oracle mediante JPA.

Durante mis practicas he trabajado con una base de codigo real, organizada por capas y alineada con patrones habituales en servicios empresariales: inyeccion de dependencias con CDI, contratos de dominio, repositorios, validadores, mapeadores, gestion centralizada de excepciones y despliegue sobre servidor de aplicaciones.

Endpoint principal:

```http
POST /api/v1/eventos/nuevoDocumento
Content-Type: application/fhir+json
```

## Tecnologias

| Categoria | Tecnologia |
| --- | --- |
| Lenguaje | Java 21 |
| Construccion | Maven multimodulo |
| API REST | Jakarta REST / JAX-RS |
| Plataforma | Jakarta EE 10 y MicroProfile 6.1 |
| Servidor | Open Liberty 24.0.0.9 |
| Formato clinico | HL7 FHIR R4 con HAPI FHIR 8.6.0 |
| Persistencia | JPA, EclipseLink y Oracle JDBC |
| Base de datos | Oracle |
| Inyeccion de dependencias | CDI |
| Pruebas | JUnit 5 y Mockito |
| Calidad | JaCoCo, Checkstyle y OWASP Dependency Check |
| Contenedores | Docker |
| CI/CD | Jenkins |

## Instalacion y uso

### Requisitos previos

- JDK 21.
- Maven 3.x.
- Acceso a las dependencias corporativas necesarias, como `arquitectura-framework` y `componentescomunes-all`.
- Acceso a una base de datos Oracle configurada para el datasource `jdbc/dataSource`.
- Docker, solo si se quiere generar o ejecutar la imagen del servicio.

### Compilar el proyecto

Desde la raiz del repositorio:

```bash
mvn clean package
```

Este comando compila los modulos y genera los artefactos:

| Modulo | Artefacto |
| --- | --- |
| `domain` | `domain-0.0.0.3.jar` |
| `application` | `application-0.0.0.3.jar` |
| `database` | `database-0.0.0.3.jar` |
| `rest` | `rest-0.0.0.3.jar` |
| `boot` | `boot.war` |

### Ejecutar pruebas

```bash
mvn test
```

### Arrancar en Open Liberty

Tras compilar el proyecto:

```bash
mvn -pl boot liberty:run
```

El servicio queda disponible, por defecto, en:

```text
http://localhost:9080/api/v1/eventos/nuevoDocumento
```

La configuracion del servidor se encuentra en `boot/src/main/liberty/config/server.xml`.

### Construir imagen Docker

```bash
docker build -t mensapex-ingesta-documentos .
```

## Capturas de pantalla y evidencias

Este apartado esta pensado para documentar las evidencias del trabajo realizado durante las practicas. Se recomienda guardar las capturas en una carpeta como `docs/evidencias/` y enlazarlas desde esta tabla.

| Evidencia | Descripcion | Ruta sugerida |
| --- | --- | --- |
| Compilacion Maven | Ejecucion correcta de `mvn clean package` | `docs/evidencias/compilacion-maven.png` |
| Pruebas unitarias | Resultado de `mvn test` con JUnit y Mockito | `docs/evidencias/pruebas-unitarias.png` |
| Endpoint REST | Peticion `POST` al endpoint de nuevo documento | `docs/evidencias/postman-endpoint.png` |
| Respuesta FHIR | Respuesta generada como `Bundle` u `OperationOutcome` | `docs/evidencias/respuesta-fhir.png` |
| Despliegue Liberty | Servidor Open Liberty arrancado correctamente | `docs/evidencias/open-liberty.png` |

## Funcionalidades principales

- Exposicion de un endpoint REST para la recepcion de documentos en formato FHIR `Bundle`.
- Lectura y escritura de recursos FHIR JSON mediante un proveedor JAX-RS basado en HAPI FHIR.
- Validacion de documentos mediante una factoria de validadores y patron Strategy.
- Soporte inicial para tipos de documento como `NuevoDocumento` y `modificacionDocumento`.
- Gestion centralizada de errores con respuestas basadas en FHIR `OperationOutcome`.
- Separacion del caso de uso `PostGiMessageUseCase` respecto a la infraestructura de persistencia.
- Persistencia de mensajes GI en la tabla Oracle `S411_VTM_MENS_GI`.
- Configuracion de Open Liberty con Jakarta EE, MicroProfile, JPA, JDBC, OpenAPI y JWT.
- Preparacion para despliegue mediante Docker y Jenkins.

## Arquitectura del proyecto

El proyecto esta organizado como un Maven multimodulo:

```text
mensapex-srv-arcs-msc/
|-- domain/       # Entidades de dominio, contratos de repositorio y casos de uso
|-- application/  # Implementacion de casos de uso de aplicacion
|-- database/     # Entidades JPA, mappers, repositorios y configuracion de persistencia
|-- rest/         # Controladores REST, validadores, mappers, providers y excepciones
|-- boot/         # Aplicacion JAX-RS, WAR y configuracion de Open Liberty
|-- Dockerfile    # Imagen de ejecucion sobre Open Liberty
|-- Jenkinsfile   # Pipeline de integracion y despliegue
`-- pom.xml       # POM padre y gestion de dependencias
```

Flujo principal de una peticion:

```text
Cliente REST
  -> GiMessageController
  -> BundleToDocumentPayloadDTOMapper
  -> ValidationService
  -> DocumentValidatorFactory
  -> PostGiMessageUseCase
  -> GiMessageRepository
  -> GiMessageJpaRepository
  -> Oracle
```

La estructura permite mantener aisladas las reglas de negocio del detalle tecnico de la API REST y de la base de datos. Esta separacion facilita las pruebas unitarias, la evolucion de validaciones y la sustitucion de componentes de infraestructura.

## Aprendizajes clave

Durante el periodo de practicas he aprendido y reforzado los siguientes puntos:

- Comprender la estructura de un microservicio Java empresarial organizado por capas y modulos Maven.
- Trabajar con Java 21, Jakarta EE y MicroProfile en un entorno de servidor Open Liberty.
- Implementar endpoints REST con JAX-RS y manejar recursos clinicos FHIR usando HAPI FHIR.
- Aplicar inyeccion de dependencias con CDI para desacoplar controladores, servicios, validadores y repositorios.
- Disenar validaciones extensibles mediante interfaces, factoria de validadores y patron Strategy.
- Diferenciar entre modelo de dominio, DTOs, entidades JPA y mappers.
- Entender el papel de los casos de uso como punto intermedio entre la capa REST y la persistencia.
- Mapear entidades con JPA y trabajar con repositorios sobre una base de datos Oracle.
- Centralizar el tratamiento de errores y devolver respuestas consistentes mediante `OperationOutcome`.
- Escribir y mantener pruebas unitarias con JUnit 5 y Mockito.
- Interpretar configuraciones de Maven, Open Liberty, Docker y Jenkins dentro de un flujo de entrega real.
- Mejorar la lectura de codigo existente, la depuracion de problemas y la documentacion tecnica del proyecto.

## Mejoras futuras

- Completar el mapeo de `Bundle` FHIR a `DocumentPayloadDTO`.
- Completar el mapeo de `Bundle` FHIR a la entidad de dominio `GiMessage`.
- Ampliar las reglas de validacion con los campos definitivos del contrato funcional.
- Incorporar pruebas unitarias para los mappers y validadores pendientes.
- Anadir pruebas de integracion del endpoint REST con payloads FHIR reales.
- Revisar la configuracion de `persistence.xml` para asegurar que las clases JPA coinciden con los paquetes actuales.
- Mapear las relaciones JPA pendientes de `GiMessageEntity`, como protocolo, estado y tipo de operacion.
- Documentar ejemplos reales de peticion y respuesta cuando el contrato FHIR este cerrado.
- Automatizar evidencias de cobertura y calidad dentro del pipeline de Jenkins.
- Externalizar configuraciones sensibles mediante variables de entorno o secretos del entorno de despliegue.

## Autor

**Sergio**

Alumno en practicas - Desarrollo de microservicios Java / Jakarta EE
