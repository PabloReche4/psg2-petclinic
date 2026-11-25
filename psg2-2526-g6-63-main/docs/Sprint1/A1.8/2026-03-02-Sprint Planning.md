<div align="center">
  <br><br>
  <h1>Sprint Planning</h1>
  <br>
  <img src="../images/logo_us.jpg" alt="Logo US" width="220"/>
  <br><br>
  <p>
    <strong>ASIGNATURA:</strong> PROCESO SOFTWARE Y GESTIÓN II<br>
    <strong>2025/2026</strong> <br>
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
  - [**Hora de apertura del Sprint Planning:** 22:00](#hora-de-apertura-del-sprint-planning-2200)
- [1. Introducción y objetivo del sprint](#1-introduccion-y-objetivo-del-sprint)
- [2. Sprint Goal](#2-sprint-goal)
- [3. Product Backlog Items seleccionados](#3-product-backlog-items-seleccionados)
- [4. Desglose de tareas técnicas y funcionales](#4-desglose-de-tareas-tecnicas-y-funcionales)
  - [4.1 Épica 1.2 (personalización)](#41-epica-12-personalizacion)
  - [4.2 Resto de épicas comprometidas](#42-resto-de-epicas-comprometidas)
- [5. Asignación de responsabilidades por miembro](#5-asignacion-de-responsabilidades-por-miembro)
- [6. Estimación de esfuerzo y capacidad del equipo](#6-estimacion-de-esfuerzo-y-capacidad-del-equipo)
- [7. Riesgos detectados y plan de mitigación](#7-riesgos-detectados-y-plan-de-mitigacion)
- [8. Definition of Done (DoD) aplicable al sprint](#8-definition-of-done-dod-aplicable-al-sprint)
- [9. Plan de seguimiento durante el sprint](#9-plan-de-seguimiento-durante-el-sprint)
- [10. Resumen de acuerdos de la sesión](#10-resumen-de-acuerdos-de-la-sesion)
  - [**Hora de cierre del Sprint Planning:** 22:50.](#hora-de-cierre-del-sprint-planning-2250)

# Sprint Planning

### **Hora de apertura del Sprint Planning:** 22:00

## 1. Introducción y objetivo del sprint
Este documento recoge la planificación del sprint realizada el **13/02/2026** en la sesión de **22:00 a 22:50**. El objetivo de la reunión fue dejar el backlog del sprint completamente preparado para ejecución inmediata: épicas definidas, tareas desglosadas, estimadas con **Planning Poker (Story Points)** y asignadas.

Para este sprint, el equipo prioriza entregar un incremento funcional y documentado del proyecto, corrigiendo además debilidades detectadas en organización y trazabilidad (especialmente en reparto real del esfuerzo y cierre formal de tareas).

## 2. Sprint Goal
Completar las entregas funcionales y de documentación de los bloques **1.2 a 1.8**, con foco en:

- adaptación y personalización del proyecto base,
- implementación técnica general y gestión de release,
- documentación técnica y de gestión exigida en la práctica,
- evidencia verificable de seguimiento del sprint (daily reports, review y retrospective).

El sprint se considerará exitoso si las funcionalidades comprometidas están integradas y la documentación asociada queda cerrada con trazabilidad entre issue, evidencia y entregable.

## 3. Product Backlog Items seleccionados

| Épica / PBI | Alcance comprometido | Estimación total (SP) | Asignación inicial |
|---|---|---:|---|
| Épica 1.2 | Ajustes de personalización en `pom.xml` y frontend | 7 | Mixta (individual + todos) |
| Épica 1.3 | Implementación general | 21 | Todos |
| Épica 1.4 | Documentación (7 tareas) | 21 | Reparto individual |
| Épica 1.5 | Desarrollo funcional (4 tareas) | 21 | Reparto individual |
| Épica 1.6 | Release management | 5 | Todos |
| Épica 1.7 | CMDB (objetivos, estructura y relaciones) | 21 | Mario + Nacho |
| Épica 1.8 | Gestión del sprint y reportes (4 entregables) | 42 | Todos |
| **Total comprometido** |  | **117 SP** |  |

## 4. Desglose de tareas técnicas y funcionales

### 4.1 Épica 1.2 (personalización)

| Tarea | Tipo | SP | Responsable |
|---|---|---:|---|
| Add your information to the developers section in `pom.xml` | Técnica | 1 | Todos |
| Change the project information in `pom.xml` | Técnica | 1 | Nacho |
| Change the icons of each pricing plan shown at `/plans` | Funcional/UI | 1 | Diego Vicente |
| Change the colours of the buttons in the list of pets | Funcional/UI | 1 | Mario |
| Change the message of the home page | Funcional/UI | 1 | Pablo |
| Change the image of the home page | Funcional/UI | 1 | Diego José |
| Change the background colour of the table header on Consultations | Funcional/UI | 1 | Mario |

### 4.2 Resto de épicas comprometidas

| Épica | Desglose acordado | SP | Responsable |
|---|---|---:|---|
| 1.3 | Implementación general | 21 | Todos |
| 1.4 | Tareas 1.4.1 a 1.4.7 (documentación) | 21 | Pablo, Nacho, Mario, Diego José, Diego Vicente |
| 1.5 | Tareas 1.5.1 a 1.5.4 | 21 | Diego José, Diego Vicente, Pablo |
| 1.6 | Release management | 5 | Todos |
| 1.7 | Objectives and scope of CMDB + Structure/relationships | 21 | Mario (8), Nacho (13) |
| 1.8 | Teamwork+Daily+DoD, Planning+Task Mgmt, Review, Retrospective | 42 | Todos |

## 5. Asignación de responsabilidades por miembro

| Miembro | Responsabilidad principal en tareas individuales (SP explícitos) | Áreas de colaboración transversal |
|---|---:|---|
| Ignacio Fuertes Montero (Nacho) | 20 | 1.3, 1.6, 1.8 y coordinación técnica de CMDB |
| Mario Domínguez López | 16 | 1.3, 1.6, 1.8 y definición de alcance CMDB |
| Pablo Reche Gabaldón | 12 | 1.3, 1.6, 1.8 y documentación/release de entrega |
| Diego José Rodríguez Fernández | 12 | 1.3, 1.6, 1.8 y desarrollo funcional |
| Diego Vicente Cámara | 9 | 1.3, 1.6, 1.8 y ajustes frontend |

Decisión operativa: las tareas marcadas como **Todos** se ejecutarán como sub-tareas con responsable único para evitar duplicidad de esfuerzo y mejorar la trazabilidad por persona.

## 6. Estimación de esfuerzo y capacidad del equipo

- **Estimación total del sprint:** 117 SP.
- **Carga individual explícita ya repartida:** 69 SP.
- **Carga colaborativa pendiente de granularidad (tareas “Todos”):** 48 SP (1.3 + 1.6 + 1.8).
- **Criterio de capacidad aplicado en la planificación:** equilibrio entre preferencia/habilidad y reparto de carga para iniciar desarrollo justo al cierre de sesión.

Decisión de capacidad: durante la primera fase del sprint se descompondrán las tareas colaborativas en issues atómicas, con asignación individual y estimación coherente para que el esfuerzo imputado no se duplique.

## 7. Riesgos detectados y plan de mitigación

| Riesgo detectado | Impacto esperado | Mitigación acordada |
|---|---|---|
| Exceso de tareas asignadas a “Todos” | Distorsión de métricas y solape de trabajo | Dividir cada bloque colaborativo en sub-issues con un único owner y criterios de cierre por issue |
| Dependencia de documentación al final del sprint | Bloqueo del cierre global por entregables incompletos | Cerrar documentación en paralelo al desarrollo, no en lote final |
| Integración tardía de cambios de frontend/backend | Aparición de incidencias cerca de la release | Integración continua entre ramas de trabajo y validación periódica |
| Release management tratado al final | Riesgo de fallo en empaquetado/despliegue | Ejecutar checklist de release desde mitad de sprint con validaciones incrementales |
| Ambigüedad en cierre de tareas | Cierre prematuro sin evidencias completas | Aplicar DoD común y exigir evidencia antes de pasar a Done |

## 8. Definition of Done (DoD) aplicable al sprint
Una tarea se considera **Done** únicamente cuando cumple todos los puntos:

1. Implementación completada según alcance de la issue.
2. Código integrado en la rama de trabajo acordada sin conflictos pendientes.
3. Verificación técnica ejecutada para el alcance afectado (tests/validaciones necesarias).
4. Evidencias registradas en la issue (qué se hizo, enlaces a commits/PR y resultado de validación).
5. Documentación asociada actualizada (incluyendo informes de A1.8 o CMDB cuando aplique).
6. Revisión por al menos otro miembro del equipo en tareas de impacto transversal.
7. Estado final en tablero: primero **Done**, y cierre definitivo de la issue conforme al flujo acordado.

## 9. Plan de seguimiento durante el sprint

- **Daily de seguimiento:** reunión breve diaria para estado, bloqueos y próximas 24 horas.
- **Actualización del tablero:** cada responsable actualiza su issue el mismo día del avance.
- **Control intermedio del sprint:** revisión interna de progreso y riesgos en mitad de sprint para reajustar reparto.
- **Seguimiento de documentación:** validación periódica de informes A1.8 y trazabilidad con issues.
- **Cierre del sprint:** Sprint Review con demostración de entregables y Sprint Retrospective con acciones concretas para Sprint 2.

## 10. Resumen de acuerdos de la sesión

- El backlog del sprint queda definido, estimado y asignado para inicio inmediato.
- Se adopta Planning Poker por consenso como método de estimación para todas las tareas del sprint.
- Se prioriza reparto por afinidad técnica, corrigiendo en ejecución la granularidad de tareas colaborativas.
- Cada miembro asume la responsabilidad de sus tareas asignadas y del cumplimiento estricto de la DoD.

### **Hora de cierre del Sprint Planning:** 22:50.
---
<div align="center">
  <sub>ASIGNATURA: PROCESO SOFTWARE Y GESTIÓN II | CURSO: 2025/2026 | GRUPO: G6-63 PETCLINIC | REPOSITORIO: HTTPS://GITHUB.COM/GII-IS-PSG2/PSG2-2526-G6-63</sub>
</div>