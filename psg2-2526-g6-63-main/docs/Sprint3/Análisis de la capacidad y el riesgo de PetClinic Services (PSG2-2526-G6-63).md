<div align="center">
   <br><br>
   <h1>Análisis de la capacidad y el riesgo de PetClinic Services (PSG2-2526-G6-63)</h1>
   <br>
   <img src="images/logo_us.jpg" alt="Logo US" width="220"/>
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

## Historial de Versiones

| Versión | Fecha de modificación | Autor | Descripción del cambio |
|---------|-----------------------|-------|------------------------|
|    1.0     |  15/04/2026  | Mario D.L.  | Creación del documento y Demanda de Servicio de Soporte  |
|    2.0     |  15/05/2026  | Diego V.C.  | Demanda de Servicio de PetClinic  |
|    3.0     |  15/05/2026  | Pablo R.G.  | Disrupciones de Servicio de PetClinic por límites de API  |
|    4.0     |  15/05/2026  | Diego José R.F.  | Estimación de TCO  |





## Índice

- [Análisis de la capacidad y el riesgo del Servicio PetClinic (PSG2-2526-G6-63)](#análisis-de-la-capacidad-y-el-riesgo-del-servicio-petclinic-psg2-2526-g6-63)
- [1. Demanda de servicio de soporte](#1-demanda-de-servicio-de-soporte)
  - [1.1. Datos básicos del contrato, duración y afluencia de incidencias](#11-datos-básicos-del-contrato-duración-y-afluencia-de-incidencias)
  - [1.2. Estimación de recursos humanos](#12-estimación-de-recursos-humanos)
  - [1.3. Organización operativa del soporte](#13-organización-operativa-del-soporte)
  - [1.4. Recursos materiales y ecosistema devops](#14-recursos-materiales-y-ecosistema-devops)
  - [1.5. Estimación de costes del servicio de soporte](#15-estimación-de-costes-del-servicio-de-soporte)
  - [1.6. Conclusión](#16-conclusión)
- [2. Demanda de Servicio de PetClinic](#2-demanda-de-servicio-de-petclinic)
  - [2.1. Restricciones evaluadas para las demandas de usuario](#21-restricciones-evaluadas-para-las-demandas-de-usuario)
  - [2.2. Estimación de Demanda: API de Recordatorios por Email](#22-estimación-de-demanda-api-de-recordatorios-por-email)
  - [2.3. Estimación de Demanda: API de Alojamiento de Imágenes](#23-estimación-de-demanda-api-de-alojamiento-de-imágenes)
  - [2.4. Resumen de Demanda Agregada](#24-resumen-de-demanda-agregada)
- [3. Disrupciones de Servicio de PetClinic por límites de API](#3-disrupciones-de-servicio-de-petclinic-por-límites-de-api)
- [4. Estimación de TCO](#4-estimación-de-tco)

# Análisis de la capacidad y el riesgo del Servicio PetClinic (PSG2-2526-G6-63)

Este documento recoge el análisis de la capacidad y el riesgo asociado al servicio PetClinic, considerando el uso de PetClinic como Software como Servicio (SaaS) así como el servicio de soporte necesario para que funcione correctamente. El objetivo principal es estimar la demanda esperada del sistema, calcular los recursos necesarios para poder cubrirla y evaluar los posibles riesgos que puedan afectar al funcionamiento del servicio.

A lo largo del documento se estudian distintos aspectos clave como la demanda del servicio de soporte, la carga derivada del uso de la propia aplicación PetClinic, el impacto de las limitaciones impuestas por APIs externas y, finalmente, una estimación del coste total de propiedad (TCO), teniendo en cuenta un los costes derivados del uso de APIs y similares.

Este análisis resulta crucial para asegurar que el sistema es capaz de mantener los niveles de servicio definidos en el SLA y minimizar los riesgos derivados tanto de la infraestructura como de la gestión del servicio. Además, permite establecer una base sólida para la toma de decisiones relacionadas con la planificación de la gestión de los recursos dedicados al soporte.

# 1. Demanda de servicio de soporte

En esta sección se analiza en detalle la demanda del servicio de soporte de PetClinic, estimando los recursos necesarios para garantizar el cumplimiento de los niveles de servicio definidos en el SLA. Este análisis se basa en las condiciones que nos encontraremos a la hora de proporcionar el servicio, y en los datos obtenidos analizando nuestro trabajo de sprints anteriores.

## 1.1. Datos básicos del contrato, duración y afluencia de incidencias

Para realizar la estimación de la demanda, se han considerado los siguientes parámetros:

- Duración del contrato: 24 meses  
- Usuarios activos totales: 10.000  
  - Plan Basic: 5.000 usuarios  
  - Plan Gold: 3.000 usuarios  
  - Plan Platinum: 2.000 usuarios  
- Carga media mensual:
  - Incidencias: 120  
  - Solicitudes de servicio: 100  
  - **Total**: 220 tickets/mes  

A partir de estos datos, podemos calcular que a lo largo de los dos años que dura el contrato, nos encontraremos:

- **Demanda anual:** 2.640 tickets  
- **Demanda total (24 meses):** 5.280 tickets  

Estos valores constituyen la base sobre la que planear la gestión de recursos dedicados al servicio de soporte.

## 1.2. Estimación de recursos humanos

Para comenzar a calcular los recursos que necesitaremos para llevar adelante el servicio debemos empezar con una estimación del trabajo a realizar (calculada anteriormente), y de cuántos trabajadores harían falta para sobrellevarlo.

### 1.2.1. Capacidad de resolución por técnico

Tomando como referencia el trabajo realizado por el grupo en este sprint y el anterior, hemos llegado a la conclusión de que un técnico puede gestionar entre 40 y 60 tickets/mes sin sacrificar la calidad de su trabajo por estar sobrecargado. 

Por ello, tomaremos como valor para calcular los siguientes datos la media de 50 tickets/mes por técnico. Ésto nos parece una carga de trabajo razonable para un equipo cuyas responsabilidades se centren en la resolución de incidencias.

### 1.2.2. Dimensionamiento del equipo

Dado que la carga mensual es de 220 tickets:

220 / 50 = 4.4

Esto implica que para asumir la carga de trabajo que hemos calculado harían falta al menos 5 técnicos de soporte

Sin embargo, este valor representa únicamente la capacidad teórica mínima, y hemos considerado que para garantizar el buen funcionamiento del sistema se deben considerar también factores adicionales como:

  - Cobertura de soporte 24/7 (plan Platinum)
  - Vacaciones, bajas y rotaciones

Por lo tanto, creemos que es irresponsable tratar de llevar el servicio de soporte con únicamente 5 técnicos, y hemos decidido aumentar este número a 7 para cubrir todas las posibles inconveniencias que pudieran ocurrir, como picos de uso o incidencias especialmente graves. Así, el equipo quedaría de la siguiente manera:
  - 1 responsable de soporte
  - 6 técnicos de soporte

Esta distribución nos asegurará que podamos cumplir los SLT definidos sin comprometer la calidad del servicio.

## 1.3. Organización operativa del soporte

Para cumplir con los requisitos del SLA, especialmente en el plan Platinum, se propone una organización basada en turnos:

- Cobertura 24/7 mediante turnos rotativos  
- Priorización de tickets según el plan del cliente  
- Uso de mecanismos de escalado y clasificación temprana  

Esto permite optimizar los tiempos de respuesta y garantizar el cumplimiento de los objetivos definidos.

## 1.4. Recursos materiales y ecosistema DevOps

El servicio de soporte depende de diversas herramientas:

- Sistemas de gestión de incidencias (iTop)  
- Repositorios de código y control de versiones (Github)
- Herramientas de integración y despliegue continuo 
- Sistemas de monitorización y logging (ZenHub)

Dado que no se consideran planes gratuitos, se deben tener en cuenta:

- Costes de licencias  
- Infraestructura cloud  
- Almacenamiento de datos  
- Servicios de monitorización  

Como tenemos un gran volumen de tickets, tendremos una demanda constante de almacenamiento y procesamiento de información.

## 1.5. Estimación de costes del servicio de soporte

Una vez nos hemos hecho una idea de los recursos humanos que necesitaremos, y habiendo definido también los recursos necesarios para operar el servicio de soporte, podemos estimar los costes asociados. Para ello, distinguimos entre costes de personal y costes de infraestructura y servicios.

### 1.5.1. Costes de personal

Según la organización definida anteriormente, el equipo de soporte estará compuesto por un responsable de soporte y 6 técnicos de soporte, para los que establecemos los siguientes salarios:

- Técnico de soporte: **2000 €/mes** - Responsable de soporte: **2700 €/mes**

#### Coste mensual

- Técnicos:  
  6 × 2000 € = **12.000 €/mes**

- Responsable:  
  1 × 2700 € = **2.700 €/mes**

- **Coste total mensual de personal:** **14.700 €/mes**

#### Coste anual

14.700 € × 12 = **176.400 €/año**

#### Coste total (24 meses)

176.400 € × 2 = **352.800 €**

### 1.5.2. Costes de infraestructura y servicios

El servicio de soporte depende de diversas herramientas del ecosistema DevOps. Al no considerar planes gratuitos, se realiza una estimación aproximada de costes basada en precios de mercado.

#### Herramientas principales

- Repositorio y control de versiones (GitHub Enterprise): **147 €/mes**
- Gestión de proyectos y tickets (ZenHub Teams): **60 €/mes**
- CI/CD (GitHub Actions / similar): ~ **150 €/mes**
- Infraestructura cloud (servidores, almacenamiento, backups): ~ **500 €/mes**

#### Coste mensual estimado

- **Total servicios:** ~ **857 €/mes**

#### Coste anual

857 € × 12 = **10.284 €/año**

#### Coste total (24 meses)

10.284 € × 2 = **20.568 €**

### 1.5.3. Coste total del servicio de soporte

Sumando los costes de personal e infraestructura:

#### Coste mensual total

- Personal: 14.700 €  
- Servicios: 857 €  

- **Total:** **15.557 €/mes**

#### Coste anual total

- **186.684 €/año**

#### Coste total (24 meses)

- **373.368 €**

### 1.5.4. Análisis de los costes

A partir de los datos obtenidos, se pueden extraer varias conclusiones:

- El **coste principal** del servicio de soporte es el personal, representando más del 90% del gasto total.
- Los costes de infraestructura, aunque menores, son esenciales para garantizar la operatividad y escalabilidad del sistema.
- El coste mensual (~16.000 €) es coherente con un servicio que ofrece:
  - Cobertura 24/7  
  - Soporte con SLA diferenciados  
  - Gestión de más de 200 tickets mensuales  

Además, estos valores deberán ajustarse en el futuro teniendo en cuenta incrementos en precios de servicios y posible crecimiento del número de usuarios. 

## 1.6. Conclusión

El análisis realizado permite establecer una capacidad base del servicio de soporte, estimando que con el equipo del que disponemos tendremos capacidad e infraestructura suficientes para soportar la operación del servicio PetClinic.  

El coste estimado del servicio de soporte proporciona una base sólida para el cálculo del TCO global del sistema. Este análisis permitirá, en secciones posteriores, evaluar la rentabilidad del servicio y asegurar un margen de beneficio adecuado (≥15%) ajustando los precios de los planes ofrecidos a los clientes.

Este dimensionamiento nos asegura también el cumplimiento del SLA, la calidad del servicio y la optimización de costes, sirviendo como punto de partida para futuras iteraciones. Sin embargo, si a la larga crece el volumen de usuarios tendremos que aumentar el equipo de soporte y seguramente también las subscripciones a servicios como ZenHub o iTop.

# 2. Demanda de Servicio de PetClinic

Este apartado estima la demanda agregada del servicio PetClinic derivada del consumo de las APIs externas integradas en las PRs del proyecto, tomando **10.000 usuarios** exclusivamente como propietarios (`owners`) (distribuidos según la Sección 1 en Plan Basic, Gold y Platinum). Se diferencia el comportamiento por plan porque los clientes con planes superiores mantienen más mascotas y mayor actividad en la comunidad, lo que impacta en las tasas de uso de la API de imágenes y en la generación de peticiones.

## 2.1. Supuestos y restricciones

- **Población (heredada de la Sección 1):** 10.000 `owners` totales, distribuidos como en la Sección 1:
  - Plan Basic: 5.000 owners
  - Plan Gold: 3.000 owners
  - Plan Platinum: 2.000 owners
- **Clínicas:** hemos estipulado un número que se adapta a la magnitud de la aplicación: **500 clínicas** (para la API de recordatorios por email).
- **Mascotas por owner (hipótesis por plan):** mayor nivel de plan → más mascotas. Usamos valores razonados:
  - Basic: 1.2 mascotas/owner
  - Gold: 1.8 mascotas/owner
  - Platinum: 2.5 mascotas/owner
- **Frecuencia de uso de la API de imágenes (llamadas/owner·día), por plan:** actividad proporcional al número de mascotas y al tipo de plan:
  - Basic: 3 llamadas/owner·día
  - Gold: 6 llamadas/owner·día
  - Platinum: 10 llamadas/owner·día
  (estas llamadas agrupan listados, visualizaciones, votos y subidas ocasionales)
- **Frecuencia de subidas (escrituras) — hipótesis por plan (uploads/owner·día):** subidas menos frecuentes que lecturas:
  - Basic: 0.15 uploads/owner·día
  - Gold: 0.20 uploads/owner·día
  - Platinum: 0.40 uploads/owner·día
- **API de Recordatorios por Email (por clínica):** 0.5 recordatorios/clinic·día (envíos programados y notificaciones básicas).
- **Ventana de pico:** 70% del tráfico diario concentrado en 3 horas (10 800 s).

Las hipótesis anteriores son parametrizables; las fórmulas siguientes permiten recalcular con otros supuestos.

## 2.2. Cálculos preliminares: mascotas por plan

- Mascotas Basic = 5.000 × 1.2 = 6.000 mascotas
- Mascotas Gold = 3.000 × 1.8 = 5.400 mascotas
- Mascotas Platinum = 2.000 × 2.5 = 5.000 mascotas
- **Total mascotas estimadas = 6.000 + 5.400 + 5.000 = 16.400 mascotas**

Este dato justifica que los owners de planes superiores aporten una mayor fracción de la actividad de la comunidad y de las subidas de imágenes.

## 2.3. Estimación de Demanda: API de Alojamiento de Imágenes / Comunidad (desagregada por plan)

Calculamos primero la demanda diaria por plan usando las frecuencias por owner indicadas.

- Demand_daily Basic = 5.000 × 3 = 15.000 peticiones/día
- Demand_daily Gold = 3.000 × 6 = 18.000 peticiones/día
- Demand_daily Platinum = 2.000 × 10 = 20.000 peticiones/día
- **Demand_daily (total imágenes) = 15.000 + 18.000 + 20.000 = 53.000 peticiones/día**

Desglose escrituras/lecturas (uploads vs reads):

- Uploads/day Basic = 5.000 × 0.15 = 750 uploads/día
- Uploads/day Gold = 3.000 × 0.20 = 600 uploads/día
- Uploads/day Platinum = 2.000 × 0.40 = 800 uploads/día
- **Uploads/day (total) = 750 + 600 + 800 = 2.150 uploads/día**
- **Reads/day (aprox.) = 53.000 - 2.150 = 50.850 lecturas/día**

Cálculos de pico (70% en 3 h = 10.800 s):

- Demand_peak (total imágenes) = 0.7 × 53.000 = 37.100 peticiones (en 3 h)
- Rate_peak (total imágenes) = 37.100 / 10.800 ≈ 3.43519 pet/s (≈206.11 pet/min)

- Uploads_peak = 0.7 × 2.150 = 1.505 uploads (en 3 h)
- Uploads_rate_peak = 1.505 / 10.800 ≈ 0.13935 pet/s (≈8.36 pet/min)

- Reads_peak = 0.7 × 50.850 = 35.595 lecturas (en 3 h)
- Reads_rate_peak = 35.595 / 10.800 ≈ 3.29583 pet/s (≈197.75 pet/min)

Cuotas mensuales (30 días):

- Quota_monthly (total imágenes) = 53.000 × 30 = 1.590.000 peticiones/mes
- Quota_monthly (uploads) = 2.150 × 30 = 64.500 uploads/mes
- Quota_monthly (reads) = 50.850 × 30 = 1.525.500 lecturas/mes

> Observación: la actividad de los owners con planes Gold/Platinum representa una fracción superior de la carga (Gold+Platinum = 38.000/53.000 ≈ 71,7% del tráfico de imágenes), pese a representar solo 5.000/10.000 = 50% de los owners.

## 2.4. Estimación de Demanda: API de Recordatorios por Email (por clínicas)

Propósito: envíos de recordatorios de citas, vacunaciones y revisiones periódicas (módulos `appointments`, `reminders`, `notifications`).

- Demand_daily (recordatorios) = N_clinics × f_recordatorios = 500 × 0.5 = 250 recordatorios/día
- Demand_peak = 0.7 × 250 = 175 recordatorios (en 3 h)
- Rate_peak = 175 / 10.800 ≈ 0.01620 pet/s (≈0.97 pet/min)
- Quota_monthly = 250 × 30 = 7.500 recordatorios/mes

Por su naturaleza programada, estos envíos generan picos muy bajos en comparación con la API de imágenes; su impacto en costes suele medirse por cuota mensual.

## 2.5. Resumen de Demanda Agregada y métricas clave

| Integración Externa | Tasa estimada (pico) | Tasa estimada (pico, pet/min) | Cuota estimada (mensual) |
| :--- | ---: | ---: | ---: |
| API de Recordatorios por Email | 1.62·10⁻² pet/s (≈0.01620 pet/s) | 0.97 pet/min | 7.500 recordatorios/mes |
| API de Alojamiento de Imágenes (total) | 3.44·10⁰ pet/s (≈3.43519 pet/s) | 206.11 pet/min | 1.590.000 peticiones/mes |
|  — de las cuales: Uploads | 1.39·10⁻¹ pet/s (≈0.13935 pet/s) | 8.36 pet/min | 64.500 uploads/mes |
|  — de las cuales: Reads | 3.30·10⁰ pet/s (≈3.29583 pet/s) | 197.75 pet/min | 1.525.500 lecturas/mes |

# 3. Disrupciones de Servicio de PetClinic por límites de API
Análisis de disrupciones por agotamiento de cuotas
El uso de APIs de terceros (Imágenes y Recordatorios por Email) introduce una dependencia crítica en la arquitectura del sistema. Un agotamiento de la cuota mensual contratada o la superación de la tasa límite de peticiones (Rate Limit) por segundo derivaría en las siguientes disrupciones del servicio PetClinic:

API de Alojamiento de Imágenes: Al concentrar un pico de ~3.44 pet/s, el bloqueo por límite de cuota impediría la carga de la interfaz visual de las mascotas y bloquearía las funcionalidades de comunidad de los planes Gold y Platinum. Esto generaría una degradación severa de la experiencia de usuario y un previsible aluvión de tickets al soporte técnico (incumpliendo el SLA).

API de Recordatorios por Email: Un bloqueo en esta integración impediría el envío de recordatorios de citas y revisiones, aumentando los no-shows, la saturación del soporte y el incumplimiento de las expectativas de servicio. El impacto es operativo y degrada la experiencia del cliente.

Estrategia de mitigación: Para evitar estas disrupciones, la capacidad mínima contratada con los proveedores de APIs debe situarse al menos un 20% por encima de nuestras estimaciones mensuales (contratando tiers que soporten >1.9M peticiones/mes para imágenes). Además, se debe implementar un sistema de caché (para reducir las lecturas de imágenes) y un mecanismo de Rate Limiting interno para evitar que ataques maliciosos o picos anómalos consuman la cuota de las APIs externas.

# 4. Estimación del TCO

En esta sección se calcula el Coste Total de Propiedad (TCO) mensual y anual esperado para el ciclo de vida del contrato. Este cálculo unifica la capacidad del equipo de soporte, el volumen de la demanda de PetClinic y aplica las fluctuaciones económicas del mercado para establecer un precio de servicio que garantice su viabilidad y un margen de beneficio mínimo del 15%.

## 4.1. Bases de cálculo y estimación de APIs

Dado que el volumen de la demanda de PetClinic no estima el coste base de las APIs, establecemos una aproximación basada en precios de mercado actuales para soportar las cuotas calculadas:

- **API de Imágenes:** Para soportar aproximadamente 1,6 millones de peticiones y el almacenamiento derivado, se estima un coste base de 100 €/mes.
- **API de Recordatorios por Email:** Para los envíos programados (7.500 recordatorios/mes), se estima un tier base SaaS de 50 €/mes.
- **Coste Base Total APIs:** 150 €/mes.

## 4.2. Variación de precios y capacidad

Para mitigar riesgos financieros a lo largo de los 24 meses, no podemos asumir costes fijos. Se aplican las siguientes variaciones de precios justificadas:

- **Salarios (Personal de soporte):** Se aplica un incremento del 2% anual. Esto nos sitúa en un punto medio del rango esperado y es necesario para igualar el IPC, mantener la motivación del equipo y evitar el riesgo de alta rotación.
- **Servicios habilitadores (Infraestructura DevOps):** Partiendo de los 857 €/mes del equipo de soporte, aplicamos una subida del 5% cada 6 meses para protegernos frente a la volatilidad de los precios en la nube.
- **APIs externas:** Partiendo de los 150 €/mes calculados, se aplica igualmente una subida de seguridad del 5% cada semestre.

## 4.3. Cálculo del Coste de Operación

Aplicando las fluctuaciones definidas previamente, proyectamos los costes del sistema:

| Concepto | Semestre 1 (Mes 1-6) | Semestre 2 (Mes 7-12) | Semestre 3 (Mes 13-18) | Semestre 4 (Mes 19-24) | Total (24 Meses) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Personal (Salarios)** | 88.200,00 € | 88.200,00 € | 89.964,00 € *(+2%)* | 89.964,00 € | **356.328,00 €** |
| **Infraestructura DevOps** | 5.142,00 € | 5.399,10 € *(+5%)* | 5.669,06 € *(+5%)* | 5.952,51 € *(+5%)* | **22.162,67 €** |
| **APIs Externas** | 900,00 € | 945,00 € *(+5%)* | 992,25 € *(+5%)* | 1.041,86 € *(+5%)* | **3.879,11 €** |
| **Coste Total Operativo** | | | | | **382.369,78 €** |

## 4.4. TCO Promedio y Margen de Beneficio

A partir del coste operativo total para los dos años, extraemos los promedios y aplicamos el*margen de beneficio mínimo exigido del 15% para determinar cuánto debemos facturar para que el servicio sea técnica y financieramente seguro.

- **TCO Medio Mensual (Coste):** 15.932,07 €
- **TCO Medio Anual (Coste):** 191.184,89 €

Para obtener el precio final asegurando nuestro margen del 15%, multiplicamos los costes por 1,15. Estos son los valores finales que justifican la viabilidad del proyecto:

- **Objetivo de Facturación Mensual Promedio:** **18.321,88 €/mes**
- **Objetivo de Facturación Anual Promedio:** **219.862,62 €/año**

## 4.5. Justificación y Análisis de Riesgos

La estructura de costes calculada se ha diseñado específicamente para absorber impactos operativos y garantizar la continuidad de PetClinic sin devorar el margen de beneficio. Las operaciones de análisis realizadas son las siguientes:

- **Riesgo de Capacidad Humana:** Con una estimación base de 220 tickets al mes, el dimensionamiento a 6 técnicos + 1 responsable genera una "capacidad ociosa" planificada. Esto no es una ineficiencia, sino una necesidad operativa estricta para garantizar la cobertura 24/7 exigida por el Plan Platinum. Si la demanda de soporte se duplicase repentinamente, el equipo actual podría absorber el pico temporalmente hasta alcanzar los 350 tickets sin colapsar.
- **Riesgo de Costes y Volatilidad (Dependencia de Terceros):** Al proyectar una subida acumulativa del 5% semestral tanto en DevOps como en APIs, hemos blindado el 15% de beneficio neto de la empresa. Si los servicios externos aplican subidas más agresivas, el colchón proyectado absorberá la mitad del impacto.
- **Riesgo Operativo y de Mantenimiento:** El 93% del gasto total recae en el capital humano. El riesgo principal de mantenimiento no es que se caiga un servidor, sino que el equipo técnico renuncie. El incremento salarial anual del 2% y la limitación de la carga de trabajo a 50 tickets por técnico actúan directamente como mitigadores de este riesgo operativo crítico.
---
<div align="center">
  <sub>ASIGNATURA: PROCESO SOFTWARE Y GESTIÓN II | CURSO: 2025/2026 | GRUPO: G6-63 PETCLINIC | REPOSITORIO: HTTPS://GITHUB.COM/GII-IS-PSG2/PSG2-2526-G6-63</sub>
</div>