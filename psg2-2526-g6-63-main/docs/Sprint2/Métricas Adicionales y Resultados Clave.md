<div align="center">
   <br><br>
   <h1>Métricas adicionales y resultados clave</h1>
   <br>
   <img src="../Sprint1/images/logo_us.jpg" alt="Logo US" width="220"/>
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

# Métricas adicionales y resultados clave

## Índice
- [Introducción](#introducción)
- [Métrica 1: Work In Progress (WIP) medio](#métrica-1-work-in-progress-wip-medio)
- [Métrica 2: PR Review Latency](#métrica-2-pr-review-latency)
- [Métrica 3: Estimation Accuracy (Story Points)](#métrica-3-estimation-accuracy-story-points)

---

## Introducción

En este documento se presentan una serie de métricas adicionales definidas por el equipo con el objetivo de analizar y mejorar distintos aspectos del proceso de desarrollo.

Estas métricas permiten evaluar de forma objetiva el rendimiento del equipo durante los sprints, identificando puntos débiles en la planificación, ejecución y revisión del trabajo. A partir de su análisis, se han definido objetivos (OKR) orientados a mejorar la eficiencia, la calidad del desarrollo y la organización del equipo en los próximos sprints.

Siguiendo el principio de mejora continua, estas métricas no solo permiten entender mejor el estado actual del equipo, sino también establecer acciones concretas para optimizar el flujo de trabajo y la toma de decisiones.

---


## Métrica 1: Work In Progress (WIP) medio

### A. Goal

Analizar el nivel de trabajo en ejecución del equipo durante el sprint, con el propósito de mejorar la eficiencia del flujo de trabajo y evitar tanto la sobrecarga como la infrautilización de recursos, desde el punto de vista del equipo de desarrollo, en el contexto del uso de ZenHub durante los sprints del proyecto.

---

### B. Questions

- ¿Cuántas tareas están en estado **In Progress** de media durante el sprint?
- ¿Se está respetando un límite razonable de trabajo simultáneo por miembro del equipo?
- ¿El equipo está infrautilizando su capacidad o, por el contrario, sobrecargándose?

---

### C. Metric

- **Definición formal:**  
  Número medio de issues en estado **In Progress** a lo largo del sprint.

- **Fórmula:**  

WIP_medio = (suma de issues en In Progress cada día) / número de días

- **Fuente de datos:**  
  ZenHub → Bottleneck Tracking

- **Frecuencia de medición:**  
  Diaria o cada 2-3 días durante el sprint.

---

### D. Baseline (Sprint S1)

A partir del Bottleneck Tracking, se observa que el número de tareas en **In Progress** se mantiene generalmente entre 1 y 3 issues simultáneamente.

Se estima un valor medio de:
- **WIP medio ≈ 2 tareas**

---

### E. Interpretación

El valor obtenido está por debajo del máximo teórico de 5 tareas simultáneas (una por cada miembro del equipo), lo que indica que no existe sobrecarga de trabajo en ejecución.

No obstante, un WIP bajo sugiere que el equipo no estaba utilizando toda su capacidad disponible, lo que puede estar relacionado con la activación tardía de tareas o con bloqueos en fases previas. Esto contribuye a un flujo de trabajo poco continuo y a la acumulación de tareas hacia el final del sprint.

---

### F. OKR Definition

- **Objective:**  
  Mejorar el flujo de trabajo asegurando una distribución equilibrada de tareas en ejecución.

- **Key Result:**  
  Mantener el WIP medio entre **3 y 5 tareas simultáneas** durante el Sprint S3.

- **Justificación del objetivo:**  
  Un rango entre 3 y 5 tareas permite aprovechar la capacidad del equipo (5 miembros) sin incurrir en multitarea excesiva, favoreciendo un flujo continuo y estable.

---

### G. Seguimiento y Plan de Medición

- **Tracking plan:**  
  Se revisará el Bottleneck Tracking periódicamente (cada 2-3 días) para registrar el número de tareas en **In Progress** y calcular el valor medio al final del sprint.

- **Cálculo en S2 y S3:**  
  Se aplicará la misma fórmula utilizando datos actualizados del Bottleneck Tracking, recogiendo múltiples mediciones a lo largo del sprint para mejorar la precisión.

- **Seguimiento en retrospectivas:**  
  Se analizará si el WIP se mantiene dentro del rango objetivo. En caso contrario:
  - Si es bajo → se revisará la activación de tareas.
  - Si es alto → se limitará el trabajo simultáneo (WIP limits).

  Las conclusiones se utilizarán para ajustar la planificación y distribución del trabajo en los siguientes sprints.


---


## Métrica 2: PR Review Latency

### Goal (GQM)

Analizar el tiempo de respuesta en las revisiones de código con el propósito de mejorar la fluidez del proceso de desarrollo, desde el punto de vista del equipo de desarrollo, en el contexto del flujo de trabajo basado en Pull Requests.

### Questions

* ¿Cuánto tiempo pasa desde que se crea una Pull Request hasta que recibe su primera revisión?
* ¿Existen cuellos de botella en la fase de revisión?

### Metric

* **Nombre**: PR Review Latency
* **Definición formal**: Tiempo medio transcurrido entre la creación de una Pull Request y la recepción del primer comentario o revisión.
* **Fórmula**:

```
PR Review Latency = Σ (Fecha primera review - Fecha creación PR) / Número de PRs
```

* **Fuentes de datos**: GitHub (Pull Requests)
* **Frecuencia de medición**: Por sprint
* **Unidad**: Horas o días

### Baseline (Sprint S1)

- En el **Sprint 1**, debido al esfuerzo inicial de adaptación y organización, se estima un tiempo medio de revisión de aproximadamente **3 días (o ligeramente superior)**.
- En el **Sprint 2**, se observa una mejora, reduciéndose el tiempo medio a aproximadamente **2 días**.

 En el **Sprint 1** y **Sprint 2**, los valores obtenidos son estimaciones generales y poco precisas. Esto se debe a la dificultad de medir esta métrica de forma exacta de manera retrospectiva, ya que no se realizó un seguimiento sistemático desde el inicio.

### Interpretación

Un valor de 2/3 días indica un retraso significativo en la revisión de código, lo que puede generar bloqueos en el flujo de trabajo y ralentizar la entrega de tareas.

---

### OKR

* **Objective**: Mejorar la rapidez y eficiencia del proceso de revisión de código.

* **Key Result**: Reducir el PR Review Latency medio a menos de **1 día (24 horas)** en el Sprint S3.

#### Justificación

Reducir el tiempo a 24 horas permite evitar bloqueos prolongados y mantener un flujo de trabajo continuo.

#### Tracking Plan

Se calculará la métrica al final de cada sprint utilizando los datos de GitHub.

#### Cálculo en S2 y S3

Para mejorar la precisión de esta métrica, se propone adoptar un método de recogida de datos más directo y fiable:

Cada vez que una tarea sea finalizada y se cree su correspondiente Pull Request, se registrará el tiempo transcurrido desde su creación hasta su primera revisión. Este dato se anotará en un documento compartido (por ejemplo, Google Docs o Google Sheets) accesible por todos los miembros del equipo.

De esta forma, al final del sprint únicamente será necesario calcular la media de todos los tiempos registrados para obtener el valor de la métrica de forma sencilla y precisa.


#### Monitorización en retrospectivas

Se revisará la métrica en cada retrospectiva para detectar retrasos y proponer mejoras.
 
---

## Métrica 3: Estimation Accuracy (Story Points)

### Goal (GQM)

Evaluar la precisión de las estimaciones realizadas durante el Sprint Planning con el propósito de mejorar la planificación y distribución del trabajo, desde el punto de vista del equipo de desarrollo, en el contexto de Planning Poker.

### Questions

* ¿Estamos estimando correctamente el esfuerzo de las tareas?
* ¿Qué diferencia hay entre lo estimado inicialmente y el esfuerzo real tras completar las tareas?

### Metric

* *Nombre*: Estimation Accuracy
* *Definición formal*: Relación entre los story points estimados inicialmente y los story points reestimados tras completar las tareas.
* *Fórmula*:

```
Estimation Accuracy = Story Points Iniciales / Story Points Reales
```

* **Fuentes de datos**: ZenHub (Planning Poker) + Reestimación manual en retrospectiva
* **Frecuencia de medición**: Por sprint

### Baseline (Sprint S1)

Valor obtenido: **< 1 (infraestimación)**

### Interpretación

* **> 1 → Sobreestimación** (tareas más fáciles de lo esperado)
* **< 1 → Infraestimación** (tareas más complejas de lo esperado)
* **≈ 1 → Estimación precisa**

En el Sprint S1 se ha observado una ligera **infraestimación**, lo que indica que el equipo ha subestimado la complejidad real de las tareas.

⚠️ **Limitación importante**:
Esta métrica agregada puede ocultar errores, ya que la sobreestimación de algunas tareas puede compensar la infraestimación de otras.

💡 **Mejora propuesta**:
Analizar la métrica también a nivel de tarea o bloques del Product Backlog (por ejemplo: A1.1, A1.2, A1.3...), permitiendo identificar exactamente qué tareas se han estimado mal y por qué.

---

### OKR

* **Objective**: Mejorar la precisión en la estimación de tareas.

* **Key Result**: Alcanzar un valor de Estimation Accuracy entre **0.9 y 1.1** en el Sprint S3.

#### Justificación

Un rango cercano a 1 indica equilibrio entre planificación y ejecución.

#### Tracking Plan

Se registrarán los puntos estimados al inicio y los puntos reestimados tras completar las tareas.

#### Cálculo en S2 y S3

Se aplicará la misma fórmula en cada sprint y se analizará también por tarea.

Nos reuniremos al *principio del sprint* para hacer el sprint planning, lo cual conlleva que asignemos puntos de historia a cada issue propuesta, y al *final del sprint*, para volver a estimar, pero esta vez ya habiendo comprobado realmente (haciendo las tareas), como de dificiles eran realmente.

#### Monitorización en retrospectivas

Se discutirán las desviaciones y se analizarán las tareas mal estimadas para mejorar el proceso de Planning Poker.

---
<div align="center">
  <sub>ASIGNATURA: PROCESO SOFTWARE Y GESTIÓN II | CURSO: 2025/2026 | GRUPO: G6-63 PETCLINIC | REPOSITORIO: HTTPS://GITHUB.COM/GII-IS-PSG2/PSG2-2526-G6-63</sub>
</div>