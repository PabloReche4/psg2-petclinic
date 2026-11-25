<div align="center">
  <br><br>
  <h1>Sprint Planning - Sprint 3</h1>
  <br>
  <img src="../../Sprint1/images/logo_us.jpg" alt="Logo US" width="220"/>
  <br><br>
  <p>
    <strong>ASIGNATURA:</strong> PROCESO SOFTWARE Y GESTION II<br>
    <strong>2025/2026</strong><br>
    <strong>G6-63</strong> PETCLINIC<br>
    <strong>REPOSITORIO:</strong> HTTPS://GITHUB.COM/GII-IS-PSG2/PSG2-2526-G6-63
  </p>
</div>

<br>
<div align="center">
  <h3>Equipo de Desarrollo</h3>
  Pablo Reche Gabaldon<br>
  Diego Vicente Camara<br>
  Ignacio Fuertes Montero<br>
  Mario Dominguez Lopez<br>
  Diego Jose Rodriguez Fernandez
</div>
<br>

## Indice
- [Hora de apertura de la primera sesion: 16:14](#hora-de-apertura-de-la-primera-sesion-1614)
- [Hora de cierre de la primera sesion: 17:20](#hora-de-cierre-de-la-primera-sesion-1720)
- [Hora de reapertura de la sesion: 22:35 (12/04/2026)](#hora-de-reapertura-de-la-sesion-2235-12042026)
- [Hora de cierre final del Sprint Planning: 23:45](#hora-de-cierre-final-del-sprint-planning-2345)
- [1. Introduccion y objetivo del sprint](#1-introduccion-y-objetivo-del-sprint)
- [2. Sprint Goal](#2-sprint-goal)
- [3. Milestone y decisiones iniciales de backlog](#3-milestone-y-decisiones-iniciales-de-backlog)
- [4. Criterios de planificacion y acuerdos de trabajo](#4-criterios-de-planificacion-y-acuerdos-de-trabajo)
- [5. Product Backlog Items seleccionados](#5-product-backlog-items-seleccionados)
- [6. Desglose de tareas tecnicas y funcionales](#6-desglose-de-tareas-tecnicas-y-funcionales)
  - [6.1 A3.2](#61-a32)
  - [6.2 A3.3](#62-a33)
  - [6.3 A3.4](#63-a34)
  - [6.4 A3.5](#64-a35)
  - [6.5 A3.8](#65-a38)
  - [6.6 A3.9](#66-a39)
  - [6.7 A3.10](#67-a310)
- [7. Asignacion de responsabilidades por miembro](#7-asignacion-de-responsabilidades-por-miembro)
- [8. Estimacion de esfuerzo y capacidad del equipo](#8-estimacion-de-esfuerzo-y-capacidad-del-equipo)
- [9. Riesgos detectados y plan de mitigacion](#9-riesgos-detectados-y-plan-de-mitigacion)
- [10. Definition of Done (DoD) aplicable al sprint](#10-definition-of-done-dod-aplicable-al-sprint)
- [11. Plan de seguimiento durante el sprint](#11-plan-de-seguimiento-durante-el-sprint)
- [12. Resumen de acuerdos de la sesion](#12-resumen-de-acuerdos-de-la-sesion)

# Sprint Planning

## 1. Introduccion y objetivo del sprint
Este documento recoge la planificacion del Sprint 3 en dos bloques de trabajo: una primera sesion iniciada a las **16:14** y cerrada a las **17:20**, y una segunda sesion de continuidad iniciada el **12/04/2026 a las 22:35** y cerrada a las **23:45**.

Durante la sesion se preparo el entorno para la creacion de issues, se reviso el nuevo Product Backlog y se acordo el desglose inicial de epicas y sub-issues para garantizar una ejecucion progresiva del sprint.

## 2. Sprint Goal
Completar los entregables del Sprint 3 con foco en:

- estructurar correctamente el backlog en epicas y sub-issues,
- repartir el trabajo para reducir bloqueos en los puntos de mayor carga,
- preparar la release de forma controlada,
- y mantener trazabilidad de avance en las tareas de gestion y documentacion.

El sprint se considerara exitoso si las tareas se cierran con seguimiento continuo, evidencias registradas y un reparto realista del trabajo entre miembros.

## 3. Milestone y decisiones iniciales de backlog
Se crea un nuevo milestone para el Sprint 3 y se crean las epicas correspondientes para los puntos del Product Backlog, con las siguientes excepciones:

1. **A3.1**: no se crea epica en esta fase.
2. **A3.6**: no se crea epica en esta fase y no se llevara a Zenhub.

Ademas, durante la planificacion se consultaron dudas del backlog con la profesora sobre **A3.2, A3.3 y A3.4**. Como criterio operativo, se establece que **A3.2** se trabajara como documento estructurado con sus sub-issues ya distribuidas.

## 4. Criterios de planificacion y acuerdos de trabajo
La planificacion del sprint se apoya en los siguientes acuerdos:

1. **Nuevo rol de Scrum Master:** Ignacio Fuertes Montero asume el rol de Scrum Master del equipo.
2. **Gestion de PR aprobadas:** cuando una Pull Request sea aprobada, se debe responder para dejar trazabilidad de la revision.
3. **Trazabilidad de actividades:** se acuerda anotar explicitamente los avances realizados en **A3.6, A3.7 y A3.8**.
4. **Division de bloques grandes:** los puntos con mayor carga se descomponen en sub-issues para evitar concentracion de trabajo al final.

## 5. Product Backlog Items seleccionados

| Epica / PBI | Naturaleza | Desglose acordado | Estado en planificacion |
|---|---|---|---|
| A3.1 | Pendiente de definicion especifica | Sin epica en esta fase | Pendiente |
| A3.2 | Documentacion | 6 sub-issues (uno por apartado) | Creadas y asignadas |
| A3.3 | Funcionalidades | 1 sub-issue por feature | Definido |
| A3.4 | Integracion con APIs externas | 2 bloques por API; segunda API dividida en 2 sub-issues adicionales por apoyo frontend | Definido |
| A3.5 | Release management | 1 bloque principal | Asignado a Ignacio |
| A3.6 | Seguimiento/documentacion | No se gestiona en Zenhub | Registro manual de actividad |
| A3.7 | Dependiente de incidencias externas | Sin division final hasta disponer de incidencias | En espera |
| A3.8 | Documentacion/gestion | 4 partes, segun Product Backlog | Definido |
| A3.9 | Bloque de cierre | 4 partes + division adicional del ultimo punto en 2 sub-issues | Definido |
| A3.10 | Gestion de sprint | 4 partes, siguiendo reparto de sprints anteriores | Definido |

## 6. Desglose de tareas tecnicas y funcionales

### 6.1 A3.2

| Tarea   | Alcance                  | Responsable                    |
|---------|--------------------------|--------------------------------|
| A3.2.1  | Apartado 1 del documento | Diego Jose Rodriguez Fernandez |
| A3.2.2  | Apartado 2 del documento | Diego Vicente Camara           |
| A3.2.3  | Apartado 3 del documento | Ignacio Fuertes Montero        |
| A3.2.4  | Apartado 4 del documento | Pablo Reche Gabaldon           |
| A3.2.5  | Apartado 5 del documento | Mario Dominguez Lopez          |
| A3.2.6  | Apartado 6 del documento | Pablo Reche Gabaldon           |

### 6.2 A3.3

| Tarea   | Alcance                                  | Responsable                    |
|---------|------------------------------------------|--------------------------------|
| A3.3.1  | Feature 1 del Product Backlog del sprint | Diego Jose Rodriguez Fernandez |
| A3.3.2  | Feature 2 del Product Backlog del sprint | Ignacio Fuertes Montero        |

### 6.3 A3.4

| Tarea           | Alcance                                          | Responsable               |
|-----------------|--------------------------------------------------|---------------------------|
| A3.4-f1         | Integracion con API externa 1                    | Diego Vicente Camara      |
| A3.4-f2-backend | Soporte de backend para API externa 2            | Ignacio Fuertes Montero   |
| A3.4-f2-frontend | Soporte de frontend para API externa 2          | Pablo Reche Gabaldon      |

Nota operativa: la segunda API se divide adicionalmente para reducir riesgo tecnico y facilitar apoyo en frontend.

### 6.4 A3.5

| Tarea | Alcance | Responsable |
|---|---|---|
| A3.5 | Preparacion y ejecucion de release | Ignacio Fuertes Montero |

### 6.5 A3.8

| Tarea    | Alcance                        | Responsable                    |
|----------|--------------------------------|--------------------------------|
| A3.8-1.1 | Parte 1.1 de la documentacion  | Diego Jose Rodriguez Fernandez |
| A3.8-1.2 | Parte 1.2 de la documentacion  | Mario Dominguez Lopez          |
| A3.8-1.3 | Parte 1.3 de la documentacion  | Ignacio Fuertes Montero        |
| A3.8-1.4 | Parte 1.4 de la documentacion  | Diego Vicente Camara           |
| A3.8-1.5 | Parte 1.5 de la documentacion  | Pablo Reche Gabaldon           |
| A3.8-2   | Parte 2 de la documentacion    | Diego Vicente Camara           |
| A3.8-3   | Parte 3 de la documentacion    | Mario Dominguez Lopez          |
| A3.8-4   | Parte 4 de la documentacion    | Diego Jose Rodriguez Fernandez |

### 6.6 A3.9

| Tarea   | Alcance                           | Responsable                    |
|---------|-----------------------------------|--------------------------------|
| A3.9-1  | Parte 1 del bloque de cierre      | Mario Dominguez Lopez          |
| A3.9-2  | Parte 2 del bloque de cierre      | Diego Vicente Camara           |
| A3.9-3  | Parte 3 del bloque de cierre      | Pablo Reche Gabaldon           |
| A3.9-4.1 | Subparte 4.1 del bloque de cierre | Diego Jose Rodriguez Fernandez |
| A3.9-4.2 | Subparte 4.2 del bloque de cierre | Ignacio Fuertes Montero        |


### 6.7 A3.10

| Tarea   | Alcance                         | Responsable                    |
|---------|---------------------------------|--------------------------------|
| A3.10-1 | Parte 1 de la gestion de sprint | Diego Vicente Camara           |
| A3.10-2 | Parte 2 de la gestion de sprint | Diego Vicente Camara           |
| A3.10-3 | Parte 3 de la gestion de sprint | Diego Jose Rodriguez Fernandez |
| A3.10-4 | Parte 4 de la gestion de sprint | Ignacio Fuertes Montero        |

## 7. Asignacion de responsabilidades por miembro

### Ignacio Fuertes Montero
- Scrum Master del Sprint 3.
- Responsable principal de **A3.5 (Release)**.
- Participacion en **A3.4** con apoyo de frontend en la segunda API externa.

### Pablo Reche Gabaldon
- Participacion en las sub-issues asignadas de **A3.2, A3.3, A3.8, A3.9 y A3.10**.

### Diego Vicente Camara
- Participacion en las sub-issues asignadas de **A3.2, A3.3, A3.8, A3.9 y A3.10**.

### Mario Dominguez Lopez
- Participacion en las sub-issues asignadas de **A3.2, A3.3, A3.8, A3.9 y A3.10**.

### Diego Jose Rodriguez Fernandez
- Participacion en las sub-issues asignadas de **A3.2, A3.3, A3.8, A3.9 y A3.10**.

Decision operativa: las tareas pendientes de owner se cerraran con asignacion explicita en tablero para mantener owner unico por issue.

## 8. Estimacion de esfuerzo y capacidad del equipo

La sesion deja los siguientes criterios de capacidad:

- Se prioriza la descomposicion de A3.2, A3.4, A3.9 y A3.10 para evitar bloques monoliticos.
- Se evita incorporar A3.6 al flujo de Zenhub para no distorsionar el seguimiento operativo.
- Se mantiene A3.7 en espera hasta disponer de incidencias, evitando planificaciones ficticias.
- Se aplica reparto progresivo en tareas documentales y de gestion para reducir carga al final.

La estimacion en Story Points se completara cuando terminen de asignarse las sub-issues pendientes.

## 9. Riesgos detectados y plan de mitigacion

| Riesgo detectado | Impacto esperado | Mitigacion acordada |
|---|---|---|
| Coordinacion de las sub-issues de A3.2 | Riesgo de dispersar el seguimiento | Revisar el avance por responsable en cada control de sprint |
| Dependencia de incidencias externas en A3.7 | Bloqueo de planificacion y ejecucion | Mantener tarea en espera y activar en cuanto lleguen incidencias |
| Sobrecarga en puntos finales (A3.9 y A3.10) | Acumulacion de trabajo y estres en cierre | Dividir puntos grandes en sub-issues adicionales |
| Falta de trazabilidad en PR aprobadas | Revisiones incompletas o ambiguas | Responder siempre tras aprobacion de PR |
| Falta de registro de actividad en A3.6, A3.7 y A3.8 | Perdida de evidencias de trabajo | Anotar de forma continua cada avance relevante |

## 10. Definition of Done (DoD) aplicable al sprint
Una tarea se considera **Done** unicamente cuando cumple todos los puntos:

1. Implementacion o entregable completado segun alcance de la issue.
2. Integracion en la rama de trabajo acordada sin conflictos pendientes.
3. Verificacion tecnica ejecutada para el alcance afectado (tests o validaciones necesarias).
4. Evidencias registradas en la issue (resumen, enlaces y resultado de validacion).
5. Documentacion asociada actualizada cuando aplique.
6. Revision por al menos otro miembro en tareas de impacto transversal.
7. Confirmacion de trazabilidad en PR aprobadas mediante respuesta explicita.
8. Estado final en tablero: **Done** y cierre conforme al flujo acordado.

## 11. Plan de seguimiento durante el sprint

- **Control diario de tablero:** cada avance debe reflejarse el mismo dia.
- **Control de asignaciones de A3.2:** revisar el avance de cada sub-issue asignada durante el sprint operativo.
- **Seguimiento de dependencias externas:** revisar estado de incidencias de A3.7 hasta su activacion.
- **Control de release:** seguimiento temprano de A3.5 por su impacto transversal.
- **Registro continuo de evidencia:** anotar actividad en A3.6, A3.7 y A3.8 durante todo el sprint.

## 12. Resumen de acuerdos de la sesion

- Se inicia el Sprint Planning a las **16:14** y se cierra el primer bloque a las **17:20**.
- Se continua la sesion el **12/04/2026 a las 22:35** y se cierra a las **23:45**.
- Ignacio Fuertes Montero asume el rol de nuevo Scrum Master.
- Se crea un nuevo milestone y las epicas del backlog, excepto **A3.1** y **A3.6**.
- Se consulta a la profesora sobre **A3.2, A3.3 y A3.4** y se acuerda tratar **A3.2** como documento.
- **A3.2** se divide en seis sub-issues con asignacion definida.
- **A3.3** se divide en una sub-issue por feature.
- **A3.4** se divide por API externa y se amplian sub-issues de la segunda API por apoyo de frontend.
- **A3.5** (Release) queda asignada a Ignacio.
- **A3.6** no se gestiona en Zenhub.
- **A3.7** queda en espera hasta creacion de incidencias.
- **A3.8**, **A3.9** y **A3.10** se dividen en bloques para repartir carga y mantener continuidad.

---
<div align="center">
  <sub>ASIGNATURA: PROCESO SOFTWARE Y GESTION II | CURSO: 2025/2026 | GRUPO: G6-63 PETCLINIC | REPOSITORIO: HTTPS://GITHUB.COM/GII-IS-PSG2/PSG2-2526-G6-63</sub>
</div>
