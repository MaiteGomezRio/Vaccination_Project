# Vaccination Project

Aplicación de consola en Java para gestionar información de vacunación en una clínica. Permite a pacientes, médicos y personal de dirección consultar y administrar datos relacionados con pacientes, enfermedades, vacunas, condiciones médicas y citas.

## Objetivo

Centralizar en una aplicación local la gestión básica del proceso de vacunación: mantener la relación entre pacientes y médicos, consultar vacunas y citas, y registrar condiciones de salud relevantes para la asignación de vacunas.

El proyecto es una aplicación de gestión y demostración técnica; no sustituye el criterio ni los sistemas clínicos profesionales.

## Funcionalidades

- Registro e inicio de sesión con roles de paciente, médico y dirección.
- Gestión de pacientes y médicos, incluida la consulta de pacientes asignados a un médico.
- Consulta y administración de enfermedades, vacunas y condiciones médicas.
- Consulta de citas de vacunación y operaciones para crear o cancelar citas.
- Consulta de vacunas asociadas a pacientes y de las vacunas disponibles.
- Importación y exportación de información de médicos y pacientes en XML.
- Transformación de un archivo XML a HTML mediante XSLT.

## Tecnologías

- **Java 17**: lenguaje de la aplicación y versión configurada en el proyecto de Eclipse.
- **SQLite**: almacenamiento local en `db/vaccination.db`.
- **JDBC**: acceso a los datos de pacientes, médicos, vacunas, enfermedades, condiciones y citas.
- **JPA con EclipseLink**: persistencia de usuarios y roles.
- **JAXB, XML y XSLT**: serialización y lectura de datos XML, y generación de una vista HTML a partir de XML.
- **Eclipse IDE**: el repositorio incluye la configuración del proyecto y las dependencias en `lib/`.

## Estructura principal

```text
.
├── db/                     Base de datos SQLite
├── doc/                    Diagramas y documentación del proyecto
├── lib/                    Bibliotecas Java incluidas
├── src/
│   ├── META-INF/            Configuración de persistencia JPA
│   └── vaccination/
│       ├── ifaces/          Interfaces de gestión
│       ├── jdbc/            Acceso a datos mediante JDBC
│       ├── jpa/             Gestión de usuarios y roles con JPA
│       ├── pojos/           Clases del dominio
│       ├── ui/              Menús de consola y utilidades
│       └── xml/             Operaciones de importación y exportación XML
└── xmls/                    Ejemplos y recursos XML/XSLT
```

## Requisitos y ejecución

- Java 17.
- Eclipse IDE u otro entorno que permita compilar proyectos Java y usar las bibliotecas incluidas en `lib/`.

Para abrirlo en Eclipse, importa el repositorio como proyecto existente y comprueba que el JRE del proyecto sea Java 17. El archivo `.classpath` referencia las dependencias locales del directorio `lib/`. La clase de entrada es `vaccination.ui.Menu`; ejecútala como aplicación Java desde el IDE.

La aplicación usa rutas relativas al directorio de trabajo: la base de datos se ubica en `./db/vaccination.db` y los recursos de intercambio XML/XSLT en `./xmls/`. Ejecuta desde la raíz del proyecto para que esas rutas coincidan.

No se incluyen instrucciones de compilación por línea de comandos ni una configuración de Maven o Gradle en el repositorio. La instalación y la ejecución no se han probado al redactar este README.

## Ejemplo de uso

Al iniciar, el menú permite registrarse o iniciar sesión. Tras acceder, las opciones dependen del rol:

- Un paciente puede consultar sus citas y condiciones, revisar vacunas y solicitar o cancelar una cita.
- Un médico puede consultar pacientes y citas, y exportar o importar datos de pacientes en XML.
- El rol de dirección permite administrar enfermedades, vacunas, condiciones y registros asociados.

## Estado y alcance

El repositorio contiene el código fuente, una base de datos SQLite, bibliotecas Java y documentación de diseño y requisitos. La interfaz es de consola y la configuración del proyecto está orientada a Eclipse. No se encontró una configuración de compilación automatizada ni una suite de pruebas en el repositorio.

## Créditos

- [MaiteGomezRio](https://github.com/MaiteGomezRio)
- [carmengarciaprieto](https://github.com/carmengarciaprieto)
