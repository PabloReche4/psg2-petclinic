<div align="center">
  <br><br>
  <h1>Sprint Retrospective</h1>
  <br>
  <img src="../../Sprint1/images/logo_us.jpg" alt="Logo US" width="220"/>
  <br><br>
  <p>
    <strong>ASIGNATURA:</strong> PROCESO SOFTWARE Y GESTIÓN II<br>
    <strong>2025/2026</strong> <br>
    <strong>G6-63</strong> PETCLINIC <br>
    <strong>REPOSITORIO:</strong> https://github.com/gii-is-psg2/psg2-2526-g6-63
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

- [Sprint Retrospective](#sprint-retrospective)
- [Discussion about Team Methods and Possible Improvements](#discussion-about-team-methods-and-possible-improvements)
- [Performance Table and Discussion](#performance-table-and-discussion)
- [Team Practices Fulfilment](#team-practices-fulfilment)
- [Action Plan (Start / Stop / Continue)](#action-plan-start--stop--continue)

# Sprint Retrospective

En este documento se presenta la retrospectiva del Sprint 3 realizada por los miembros del equipo. En ella se analizan tanto los aspectos positivos como aquellos susceptibles de mejora, con el objetivo de optimizar el trabajo de cara al siguiente sprint.

A continuación, se recogen los principales aprendizajes del sprint, organizados en torno a las áreas de mejora identificadas durante el mismo. Para cada una de ellas, se detallan las decisiones tomadas y las acciones que se aplicarán en el siguiente sprint, con el fin de reforzar la organización del equipo, mejorar la trazabilidad del trabajo y aumentar la calidad del desarrollo.

---

# Discussion about Team Methods and Possible Improvements

Durante este sprint se han identificado varias áreas de mejora relacionadas con la organización y metodología del equipo:

- **Mejora del reparto de tareas**  

Aunque durante este sprint se intentó dividir mejor las funcionalidades, se ha detectado que algunas tareas seguían siendo demasiado grandes o complejas para una única persona.

Esto ha ocurrido especialmente en la tarea de integrar *Space*, donde separar únicamente backend y frontend no fue suficiente. Habría sido más efectivo dividir  por funcionalidades concretas o por partes más específicas, como integración con cada una de las APIs utlizadas, sincronización, interfaz visual de selección de planes y Addons,etc.

Este sprint ha dejado claro que la división de tareas en funcionalidades complejas es especialmente difícil y que una mala separación puede generar dependencias muy fuertes entre miembros del equipo.

Además, cuando una funcionalidad completa queda demasiado centralizada en una sola persona, resulta más complicado que otros miembros puedan ayudar de forma rápida y efectiva, ya que no conocen directamente toda la lógica interna de la tarea. Por ello, se considera importante seguir manteniendo tareas lo más independientes posible, pero realizando divisiones más precisas y equilibradas.

- **Monitorización y seguimiento de incidencias**  

Se ha detectado la necesidad de realizar un seguimiento más constante de las incidencias y solicitudes abiertas, especialmente aquellas relacionadas con los tiempos de respuesta y resolución (TTO y TTR).

Para ello, el equipo propone:
- Realizar comprobaciones diarias al menos dos veces al día
- Revisar de forma continua los tiempos de aceptación (TTO) y resolución (TTR)
- Monitorizar posibles bloqueos o incidencias pendientes
- Mejorar el control del estado de las tareas y requests

Esto permitirá reaccionar antes ante posibles retrasos, evitar acumulación de incidencias y mantener un flujo de trabajo más estable y controlado.

- **Mejor uso de herramientas de gestión**  

Se ha observado que algunas herramientas ofrecían funcionalidades útiles que no se utilizaron correctamente durante el sprint.

Por ejemplo, en iTop existía la posibilidad de indicar periodos de vacaciones o indisponibilidad. Esto habría evitado que ciertos TTO y TTR contabilizaran días en los que algunos miembros no estaban disponibles, ya que varios SLT fueron definidos incluyendo periodos de vacaciones aun cuando el equipo tenía intención de cumplirlos correctamente.

También se considera importante aprovechar mejor herramientas como Zenhub para realizar un seguimiento más preciso del estado de las tareas, incidencias y planificación general del sprint.

- **Cumplimiento de Team Practices**  

Durante este sprint se ha conseguido aplicar correctamente la mayoría de las *team practices*, mejorando notablemente respecto al sprint anterior.

Aun así, el equipo considera importante seguir manteniendo estas prácticas de forma estricta para conservar un flujo de trabajo organizado y consistente.

---

# Performance Table and Discussion

| Team Member | S1 | S2 | S3 | Total | Calculated Weight | FINAL MARK |
|------------|----|----|----|--------|-------------------|-------------|
| Ignacio Fuertes Montero | 4 | 4 | 4 | 12 | - | - |
| Pablo Reche Gabaldón | 4 | 4 | 4 | 12 | - | - |
| Diego Vicente Cámara | 4 | 4 | 4 | 12 | - | - |
| Diego José Rodríguez Fernández | 4 | 4 | 4 | 12 | - | - |
| Mario Domínguez López | 4 | 4 | 4 | 12 | - | - |
| **Total** | 20 | 20 | 20 | 60 | - | - |

El equipo ha mantenido nuevamente una contribución equilibrada entre todos sus miembros durante el Sprint 3. Aunque algunas tareas han resultado más complejas o problemáticas que otras, especialmente aquellas relacionadas con *Space*, el esfuerzo y la implicación del equipo han sido homogéneos.

Por ello, se ha decidido mantener una valoración equivalente para todos los integrantes del grupo.

---

# Team Practices Fulfilment

A continuación, se analiza el grado de cumplimiento de las prácticas del equipo:

- **TPa – Creación de rama al pasar a In Progress**  
  ✔️ Cumplida. Se han creado ramas asociadas a las issues de forma consistente.

- **TPb – Rama única por issue**  
  ✔️ Cumplida. No se han reutilizado ramas, cada issue ha tenido su propia rama.

- **TPc – Creación de PR al pasar a In Review**  
  ✔️ Cumplida.

- **TPd – Merge al pasar a Done**  
  ✔️ Cumplida.

- **TPe – Un desarrollador trabajando únicamente en una issue en cada momento**  
  ✔️ Cumplida. En general, cada miembro trabajó en una única issue simultáneamente.

- **TPf – Al menos una issue completada por semana**  
  ⚠️ No cumplida completamente.  

  No queda completamente claro cómo debe contabilizarse exactamente una “semana” (de domingo a domingo, de martes a martes, etc.). Además, hubo casos en los que un desarrollador estuvo trabajando durante bastante tiempo en una tarea especialmente compleja o mal dividida.

- **TPg – PR aprobadas antes de mergear**  
  ✔️ Cumplida.

- **TPh – Merge solo tras aprobación y por el autor**  
  ✔️ Cumplida prácticamente en todos los casos.

- **TPi – Participación en PRs de otros compañeros**  
  ✔️ Cumplida.  
  Los miembros han participado revisando y comentando PRs de otros semanalmente.

---

# Action Plan (Start / Stop / Continue)

## ✅ Continue doing
- Mantener la buena comunicación del equipo  
- Seguir realizando reuniones efectivas  
- Continuar con la colaboración y apoyo entre miembros  

## 🚀 Start doing
- Continuar aplicando las métricas introducidas en el sprint anterior  
- Realizar monitorización constante de incidencias y solicitudes  
- Hacer checks diarios al menos dos veces al día sobre incidencias y requests  
- Utilizar mejor las herramientas de gestión (iTop, Zenhub, etc.)  
- Aplicar correctamente todas las *team practices*  

## 🛑 Stop doing
- Dejar de probar *Space* todos a la vez, especialmente en momentos de alta demanda, ya que el sistema no funciona correctamente bajo esas condiciones  
- Evitar tareas demasiado grandes sin dividir  
- Dejar de asignar funcionalidades críticas completas a una sola persona. Tratar de realizar una separación correcta de tareas que permita trabajar de forma relativamente independiente, pero evitando divisiones artificiales o poco equilibradas que generen demasiadas dependencias. 

En funcionalidades complejas, resulta más efectivo dividir por partes funcionales concretas para facilitar la colaboración y permitir que otros miembros puedan ayudar más fácilme 

---

<div align="center">
  <sub>ASIGNATURA: PROCESO SOFTWARE Y GESTIÓN II | CURSO: 2025/2026 | GRUPO: G6-63 PETCLINIC | REPOSITORIO: HTTPS://GITHUB.COM/GII-IS-PSG2/PSG2-2526-G6-63</sub>
</div>