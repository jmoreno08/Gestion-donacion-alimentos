# Sistema de Gestión de Donación de Alimentos

**Autor:** Jonathan Moreno Nuñez

---

## Introducción

Detrás de cada donación de alimentos hay una historia de solidaridad: personas, empresas y organizaciones que deciden tender una mano a quienes más lo necesitan. Sin embargo, muchas de las fundaciones y grupos comunitarios que reciben y distribuyen estos aportes enfrentan un reto silencioso pero muy real: no cuentan con las herramientas para administrar toda esa información de manera ordenada y eficiente.

Este proyecto nace precisamente de esa necesidad. Se propone un sistema de información que, a través de una base de datos relacional, facilite la gestión completa del proceso de donación de alimentos, desde el momento en que llega un aporte hasta que llega a la mesa de quien lo necesita.

---

## La Problemática

Hoy en día, muchas organizaciones sin ánimo de lucro y grupos comunitarios llevan el registro de sus donaciones con hojas de cálculo, cuadernos o documentos físicos. Es un esfuerzo valioso, pero frágil.

Esta forma de trabajar trae consigo problemas concretos que afectan la operación día a día:

- Se pierde información importante.
- Se duplican registros sin querer.
- Es difícil saber quién hizo una donación y cuándo.
- No hay claridad sobre qué productos hay disponibles ni en qué cantidades.
- Identificar a los beneficiarios que ya fueron atendidos se vuelve un reto.
- No queda rastro claro de las entregas realizadas.
- Generar reportes o indicadores toma mucho tiempo y esfuerzo.

El resultado es una distribución de alimentos menos eficiente y menos transparente, lo que termina afectando a las comunidades que más dependen de esta ayuda.

---

## ¿Por Qué Vale la Pena Este Sistema?

Centralizar la información cambia completamente el panorama. Con una base de datos relacional bien estructurada, la organización puede:

- Tener registros organizados y siempre actualizados.
- Hacer seguimiento real de cada donación recibida.
- Saber en todo momento qué productos hay disponibles.
- Conocer a los beneficiarios atendidos y su historial.
- Acceder a la información de forma rápida y segura.
- Generar reportes que apoyen la toma de decisiones.
- Demostrar transparencia en el manejo de los recursos.

Más allá de la tecnología, este proyecto busca poner el conocimiento de bases de datos al servicio de una causa social real.

---

## Objetivo General

Diseñar un sistema de gestión de donación de alimentos, sustentado en una base de datos relacional normalizada, que permita administrar de forma eficiente y organizada la información sobre donantes, donaciones, productos, beneficiarios y entregas.

---

## Objetivos Específicos

- Registrar la información de quienes donan.
- Llevar un registro detallado de cada donación realizada.
- Gestionar los productos alimenticios recibidos.
- Registrar a las personas y familias beneficiarias.
- Controlar y hacer seguimiento de las entregas.
- Mantener trazabilidad completa del proceso, desde la donación hasta la distribución.
- Facilitar la generación de consultas y reportes.
- Aplicar técnicas de normalización para garantizar la integridad y coherencia de los datos.

---

## La Solución Propuesta

Se propone construir un sistema de información basado en una base de datos relacional que cubra todo el ciclo de vida de una donación de alimentos.

### Gestión de Donantes
Un registro completo de las personas, empresas y fundaciones que contribuyen con donaciones.

### Gestión de Donaciones
Un historial de cada aporte recibido, con fecha, estado y observaciones.

### Gestión de Productos
Control actualizado de los alimentos donados: categorías, cantidades disponibles y fechas de vencimiento.

### Gestión de Beneficiarios
Un registro de las personas y familias que reciben apoyo, con toda la información necesaria para atenderlas bien.

### Gestión de Entregas
Seguimiento de cada distribución realizada, con detalle de los productos entregados a cada beneficiario.

### Consultas y Reportes
Herramientas para conocer, por ejemplo:

- El total de donaciones recibidas en un período.
- Los productos más frecuentemente donados.
- Cuántas personas han sido atendidas.
- El historial completo de entregas.
- Las cantidades distribuidas por tipo de producto.

---

## Alcance del Sistema

El sistema cubrirá los procesos esenciales: registro de donantes, donaciones, productos, beneficiarios, entregas y generación de reportes básicos.

Por ahora, quedan fuera del alcance los procesos financieros, la facturación, la gestión logística del transporte y el desarrollo de aplicaciones móviles o interfaces gráficas. El corazón del proyecto está en el diseño y la gestión correcta de la base de datos.

---

## Beneficios Esperados

Con este sistema en marcha, la organización podrá:

- Manejar su información de manera mucho más ordenada.
- Reducir errores administrativos que hoy generan reprocesos.
- Ser más transparente ante donantes, aliados y comunidades.
- Tener un control real del inventario de alimentos.
- Rastrear cada donación desde que llega hasta que se entrega.
- Producir reportes en minutos, no en horas.
- Tomar decisiones basadas en datos confiables, no en suposiciones.

---

## Las Entidades del Sistema

El modelo relacional se construirá sobre las siguientes entidades principales:

**Donantes** — Las personas, empresas o fundaciones que realizan aportes.

**Donaciones** — Cada contribución registrada en el sistema.

**Productos** — Los alimentos que ingresan al inventario.

**Beneficiarios** — Las personas o familias que reciben apoyo alimentario.

**Entregas** — Las distribuciones realizadas a los beneficiarios.

**Detalle de Donación** — El desglose de productos asociados a cada donación.

**Detalle de Entrega** — El desglose de productos entregados en cada distribución.

---

## Conclusión

Es una propuesta concreta para ayudar a que los alimentos donados lleguen de manera más eficiente, transparente y organizada a quienes más los necesitan. Con una base de datos relacional bien diseñada, las organizaciones que trabajan con comunidades vulnerables pueden hacer más con los mismos recursos, y rendir cuentas con más claridad a quienes confían en su labor.