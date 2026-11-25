<div align="center">
  <br><br>
  <h1>Sprint Planning - Sprint 2</h1>
  <br>
  <img src="../Métricas%20de%20Proceso%20Ágil%20y%20Recursos/images/logo_us.jpg" alt="Logo US" width="220"/>
  <br><br>
  <p>
    <strong>ASIGNATURA:</strong> PROCESO SOFTWARE Y GESTIÓN II<br>
    <strong>2025/2026</strong><br>
    <strong>G6-63</strong> PETCLINIC<br>
    <strong>REPOSITORIO:</strong> HTTPS://GITHUB.COM/GII-IS-PSG2/PSG2-2526-G6-63
  </p>
</div>

<br>
<div align="center">
  <h3>Equipo de Desarrollo</h3>
  Pablo Reche Gabaldón<br>
  Diego Vicente Cámara<br>
  Ignacio Fuertes Montero<br>
  Mario Domínguez López<br>
  Diego José Rodríguez Fernández
</div>
<br>

## Índice
- [1. Introducción y objetivo del sprint](#1-introduccion-y-objetivo-del-sprint)
- [2. Sprint Goal](#2-sprint-goal)
- [3. Milestone y horizonte temporal](#3-milestone-y-horizonte-temporal)
- [4. Criterios de planificación y reparto](#4-criterios-de-planificacion-y-reparto)
- [5. Product Backlog Items seleccionados](#5-product-backlog-items-seleccionados)
- [6. Desglose de tareas técnicas y funcionales](#6-desglose-de-tareas-tecnicas-y-funcionales)
  - [6.1 A2.4](#61-a24)
  - [6.2 A2.5](#62-a25)
  - [6.3 A2.6](#63-a26)
  - [6.4 A2.7](#64-a27)
  - [6.5 A2.8](#65-a28)
  - [6.6 A2.9](#66-a29)
- [7. Asignación de responsabilidades por miembro](#7-asignacion-de-responsabilidades-por-miembro)
- [8. Estimación de esfuerzo y capacidad del equipo](#8-estimacion-de-esfuerzo-y-capacidad-del-equipo)
- [9. Riesgos detectados y plan de mitigación](#9-riesgos-detectados-y-plan-de-mitigacion)
- [10. Definition of Done (DoD) aplicable al sprint](#10-definition-of-done-dod-aplicable-al-sprint)
- [11. Plan de seguimiento durante el sprint](#11-plan-de-seguimiento-durante-el-sprint)
- [12. Resumen de acuerdos de la sesión](#12-resumen-de-acuerdos-de-la-sesion)

# Sprint Planning

## 1. Introducción y objetivo del sprint
Este documento recoge la planificación inicial del Sprint 2, realizada el **03/03/2026** dentro de la franja de trabajo **11:24-12:39**. La sesión se centró en crear primero las épicas y las issues del sprint para, después, aplicar Planning Poker y repartir el trabajo de forma coherente con la disponibilidad, las preferencias del equipo y la complejidad de cada entregable.

El objetivo principal de esta planificación fue preparar un sprint ejecutable desde el primer día, evitando sobrecargas al final y dejando claro qué tareas debían avanzar con prioridad durante la primera semana para llegar con margen suficiente.

## 2. Sprint Goal
Completar el conjunto de entregables funcionales y documentales del Sprint 2, con especial atención a:

- la preparación temprana de **A2.3** y **A2.4** para ir con tiempo la semana siguiente,
- el despliegue y la gestión de release sin bloquear el resto del sprint,
- la documentación extensa asociada al sprint,
- y el cierre ordenado de las tareas finales para que el trabajo acumulado no se concentre en los últimos días.

El sprint se considerará exitoso si el equipo mantiene un flujo estable de trabajo, registra correctamente el avance en el tablero y llega al final con las entregas cerradas, revisadas y trazables.

## 3. Milestone y horizonte temporal
Se crea el milestone del Sprint 2 con una ventana temporal que va del **3 de marzo al 6 de abril**. Esta franja se utilizará como referencia para controlar el avance real mediante el milestone burndown y para revisar si la distribución de tareas está produciendo un cierre progresivo o, por el contrario, una acumulación al final.

El milestone se asocia a dos decisiones operativas:

1. mover a **Done** cada issue terminada con rapidez para reflejar el progreso real,
2. revisar el burndown de forma periódica para detectar retrasos antes de que se conviertan en bloqueo.

## 4. Criterios de planificación y reparto
La planificación se ha hecho siguiendo tres criterios:

1. **Afinidad técnica:** cada miembro asume las tareas que mejor encajan con su experiencia o preferencia.
2. **Carga equilibrada:** se reparte el trabajo para evitar cuellos de botella y mantener una capacidad razonable durante todo el sprint.
3. **Contexto temporal:** se priorizan antes las tareas que pueden bloquear otras, teniendo en cuenta exámenes, fechas festivas y disponibilidad real del equipo.

Con este enfoque, las tareas de desarrollo y despliegue se sitúan al principio o en mitad del sprint, mientras que las tareas de cierre y documentación final quedan reservadas para la fase final, pero sin retrasar su arranque.

## 5. Product Backlog Items seleccionados

| Épica / PBI | Naturaleza | Estimación inicial | Observaciones de planificación |
|---|---|---:|---|
| A2.2 | Individual | Individual | Tarea cerrada de forma individual, sin desglose adicional en esta fase. |
| A2.3 | Individual / divisible | Pendiente de cierre | Se dejará preparada para la semana siguiente y se dividirá cuando llegue el momento. |
| A2.4 | Desarrollo y validación | 26 SP | Se reparte según preferencia: Diego José evita tests, Pablo evita demasiado front-end. |
| A2.5 | Release management | 8 SP | Se planifica pronto para no dejar el despliegue para el final. |
| A2.6 | Documentación extensa | 53 SP | Epic larga, dividida en bloques atómicos para repartir el trabajo. |
| A2.7 | Documentación modular | 18 SP | Se puede dividir sin problema para repartir carga y avanzar en paralelo. |
| A2.8 | Documentación | 13 SP | Tareas de documentación asignadas de forma directa. |
| A2.9 | Cierre del sprint | 42 SP | Se reserva para la fase final del sprint y la consolidación de evidencias. |

## 6. Desglose de tareas técnicas y funcionales

### 6.1 A2.4

| Tarea | SP | Responsable |
|---|---:|---|
| A2.4.1 | 8 | Diego José |
| A2.4.2 | 13 | Pablo Reche |
| A2.4.3 | 5 | Ignacio Fuertes |

En caso de ser necesario, la tarea **A2.4.3** se completará con el apoyo con Mario Domínguez con el fin de reducir la carga.

Decisión operativa: esta épica se prioriza pronto para tenerla resuelta con margen. La intención es que quede encaminada antes de la siguiente semana para no arrastrar trabajo técnico a la parte final del sprint.

### 6.2 A2.5

| Tarea | SP | Responsable |
|---|---:|---|
| A2.5.1 | - | Bloque de preparación |
| A2.5.1.1 | 3 | Diego José |
| A2.5.1.2 | 2 | Ignacio Fuertes |
| A2.5.2 | 3 | Mario Domínguez |

Esta épica se considera sensible porque afecta al flujo de trabajo del resto del sprint. Por ello se sitúa temprano y se controla con especial atención.

### 6.3 A2.6

| Tarea | SP | Responsable |
|---|---:|---|
| A2.6.1.a | 5 | Pablo Reche |
| A2.6.1.b | 3 | Pablo Reche |
| A2.6.1.c | 3 | Pablo Reche |
| A2.6.1.d | 3 | Diego José |
| A2.6.1.e | 3 | Diego José |
| A2.6.1.f | 3 | Diego José |
| A2.6.2.g | 5 | Pablo Reche |
| A2.6.2.h | 3 | Pablo Reche |
| A2.6.2.i | 3 | Pablo Reche |
| A2.6.2.j | 3 | Diego José |
| A2.6.2.k | 3 | Diego José |
| A2.6.2.l | 3 | Diego José |
| A2.6.2.m | 13 | Diego Vicente |

La documentación de A2.6 es una de las partes más largas del sprint. Se ha atomizado para que el trabajo avance en paralelo y no dependa de una sola persona.

### 6.4 A2.7

| Tarea | SP | Observación |
|---|---:|---|
| A2.7.1 | 2 | Mario Domínguez |
| A2.7.2 | 5 | Mario Domínguez |
| A2.7.3 | 8 | Mario Domínguez |
| A2.7.4 | 3 | Mario Domínguez |

Esta épica se considera modular y, por tanto, fácil de repartir sin riesgo de bloqueo.

### 6.5 A2.8

| Tarea | SP | Responsable |
|---|---:|---|
| A2.8.1 | 3 | Pablo Reche |
| A2.8.2 | 2 | Ignacio Fuertes |
| A2.8.3 | 8 | Ignacio Fuertes |

### 6.6 A2.9

| Tarea | SP | Responsable |
|---|---:|---|
| A2.9.1 | 13 | Diego Vicente |
| A2.9.2 | 8 | Diego Vicente |
| A2.9.3 | 8 | Diego José |
| A2.9.4 | 13 | Ignacio Fuertes |

Esta épica se reserva para el final porque depende del cierre del sprint, la consolidación de evidencias y la verificación del trabajo realizado durante las semanas anteriores.

## 7. Asignación de responsabilidades por miembro

### Diego José Rodríguez Fernández
- A2.4.1 (Funcionalidad de reserva para las habitaciones del hotel para mascotas)
- A2.5.1.1 (Creación de la rama release)
- A2.6.1.d, A2.6.1.e y A2.6.1.f (Segunda parte de la documentación del sprint 1)
- A2.6.2.j, A2.6.2.k y A2.6.2.l (Segunda parte de la documentación del sprint 2)
- A2.9.3 (Sprint review)

### Pablo Reche Gabaldón
- A2.4.2 (Crear una funcionalidad de Adopciones)
- A2.6.1.a, A2.6.1.b y A2.6.1.c (Primera parte de la documentación del sprint 1)
- A2.6.2.g, A2.6.2.h y A2.6.2.i (Primera parte de la documentación del sprint 2)
- A2.8.1 (Métrica 1)

### Ignacio Fuertes Montero
- A2.4.3 (Tests unitarios)
- A2.5.1.2 (Despliegue de la aplicación)
- A2.8.2 y A2.8.3 (Métrica 2 y Métrica 3)
- A2.9.4 (Sprint retrospective)

### Diego Vicente Cámara
- A2.6.2.m (Calendario Niko Niko)
- A2.9.1 y A2.9.2 (Sprint Planning, meeting minutes y daily stand ups)

### Mario Domínguez López
- A2.4.3 en apoyo (Tests unitarios)
- A2.5.2 (Creación de una versión nueva en el repositorio de GitHub)
- A2.7 (Análisis de SonarQube)
- Refuerzo en tareas compartidas de documentación y validación 

Decisión operativa: las tareas compartidas se consideran apoyos y no deben generar duplicidad de esfuerzo. Cada issue debe tener un responsable final único para mantener la trazabilidad.

## 8. Estimación de esfuerzo y capacidad del equipo

La planificación deja varias conclusiones importantes:

- **A2.4** y **A2.3** deben ir por delante durante la primera semana para llegar con margen a la siguiente.
- **A2.5** se ejecuta pronto porque el despliegue y la rama de release pueden bloquear el resto del trabajo si se dejan para el final.
- **A2.6**, **A2.7** y **A2.8** concentran la parte más documental del sprint y se reparten en tareas pequeñas para permitir trabajo paralelo.
- **A2.9** queda reservada para la etapa final, pero no debe convertirse en un bloque improvisado; su preparación empieza durante el sprint.

En términos de capacidad, el equipo debe evitar dos problemas:

1. sobrecargar a las mismas personas con tareas largas y poco visibles,
2. acumular demasiadas tareas cerrables al final, lo que distorsionaría el burndown.

El criterio de planificación adoptado es, por tanto, de reparto progresivo y realista, dejando margen para incidencias, revisiones y correcciones.

## 9. Riesgos detectados y plan de mitigación

Durante la planificación se identificaron riesgos que podrían comprometer el ritmo de ejecución y la calidad del cierre del sprint. Para reducir su impacto, el equipo acordó medidas preventivas y de seguimiento que permitan actuar de forma temprana ante posibles desviaciones.

| Riesgo detectado | Impacto esperado | Mitigación acordada |
|---|---|---|
| Acumulación de documentación | Cierre tardío y sensación de atasco | Dividir la documentación en subtareas pequeñas y cerrarlas de forma progresiva |
| Dependencia de despliegue | Bloqueo técnico si se pospone | Ejecutar A2.5 pronto y validar el despliegue antes de avanzar al resto |
| Sobrepeso en la parte final del sprint | Estrés y caída de la moral | Revisar el tablero a mitad de sprint y adelantar tareas pendientes |
| Reparto desigual por disponibilidad | Descompensación de carga | Ajustar tareas según contexto real de cada miembro y redistribuir si hace falta |
| Mala lectura del burndown | Confusión sobre el progreso real | Registrar Done de forma inmediata y explicar el gráfico con el avance real del sprint |

## 10. Definition of Done (DoD) aplicable al sprint
Una tarea se considera **Done** únicamente cuando cumple todos los puntos:

1. Implementación completada según alcance de la issue.
2. Código integrado en la rama de trabajo acordada sin conflictos pendientes.
3. Verificación técnica ejecutada para el alcance afectado (tests/validaciones necesarias).
4. Evidencias registradas en la issue (qué se hizo, enlaces a commits/PR y resultado de validación).
5. Documentación asociada actualizada (incluyendo informes de A1.8 o CMDB cuando aplique).
6. Revisión por al menos otro miembro del equipo en tareas de impacto transversal.
7. Estado final en tablero: primero **Done**, y cierre definitivo de la issue conforme al flujo acordado.

## 11. Plan de seguimiento durante el sprint

El seguimiento del sprint se plantea como una actividad continua orientada a mantener visibilidad sobre el progreso real, detectar bloqueos con antelación y facilitar ajustes de planificación cuando sea necesario. Las siguientes pautas recogen el marco operativo acordado por el equipo para sostener ese control.

- **Control del tablero:** cada avance se traslada al tablero el mismo día.
- **Revisión del milestone burndown:** se comprobará periódicamente para ver si el sprint avanza de forma progresiva.
- **Cierre temprano de tareas clave:** A2.4 y A2.3 deben quedar encaminadas durante la primera semana.
- **Seguimiento de documentación:** A2.6, A2.7 y A2.8 se revisarán por bloques para evitar una acumulación final.
- **Validación de release:** A2.5 se controlará desde el principio para que no bloquee el resto del trabajo.

### Lectura esperada del burndown
La gráfica del burndown debería mostrar una bajada razonablemente continua, con pequeñas oscilaciones, pero sin permanecer plana durante demasiado tiempo. Si el gráfico se mantiene horizontal, significa que no se están cerrando issues; si cae en bloque al final, el sprint no está bien repartido. La lectura correcta del gráfico debe servir para ajustar la organización antes de que aparezcan retrasos graves.

## 12. Resumen de acuerdos de la sesión

Como cierre de la planificación, se consolidan a continuación los acuerdos principales alcanzados durante la sesión. Este resumen sirve como referencia común para alinear la ejecución del sprint y asegurar la trazabilidad de las decisiones tomadas.

- Se crea el milestone del Sprint 2 con horizonte del **3 de marzo al 6 de abril**.
- Se acuerda construir primero las épicas y las issues y después cerrar el reparto mediante Planning Poker.
- Se prioriza la preparación temprana de **A2.3** y **A2.4** para llegar con margen.
- Se adelanta **A2.5** para evitar bloqueos relacionados con release y despliegue.
- Se divide la documentación en subtareas pequeñas para repartirla entre Pablo, Diego José, Diego Vicente e Ignacio.
- Se reserva **A2.9** para la parte final del sprint, pero con seguimiento continuo.
- Se usará el milestone burndown como referencia de progreso y como indicador de si el trabajo se está cerrando de forma progresiva.

---
<div align="center">
  <sub>ASIGNATURA: PROCESO SOFTWARE Y GESTIÓN II | CURSO: 2025/2026 | GRUPO: G6-63 PETCLINIC | REPOSITORIO: HTTPS://GITHUB.COM/GII-IS-PSG2/PSG2-2526-G6-63</sub>
</div>
