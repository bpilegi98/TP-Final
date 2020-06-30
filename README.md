# TP-Final
TP Final de Programación Avanzada I y Base de Datos II

UTN Phones es una compañia de telefonia celular con base en Mar del Plata, y quiere
empezar a operar con nuevos sistemas.

Con este fin se nos explica el siguiente escenario :
  ● En nuestra compañía, un cliente es un individuo que posee 1 o mas lineas de
telefono. Cada uno de estas líneas puede realizar llamadas a cualquier linea de
telefono de nuestra propia compañía. No hay forma de realizar una llamada a otra
compañía.
  ● Los precios de cada llamada se va a calcular dependiendo la localidad de origen y
la de destino. Los numeros seran guardados siempre con el prefijo (Ej : 4552394
de Mar del Plata será guardado como 2234552394) y se debe contar con la tarifa
correspondiente a las llamadas de la misma ciudad. Por ejemplo : Tarifa desde
MDQ a MDQ es de $10 por minuto , MDQ a MIR $5 el minuto. Idealmente al
momento de inserción de la llamada se debe calcular la localidad de origen y la de
destino y aplicar la lógica para calcular su precio. Cada localidad tiene un prefijo
que la identifica y que nos permitirá saber el precio por minuto de la llamada.
  ● Hay un proceso automático que todos los 1º de cada mes, corre automáticamente
y verifica las llamadas no facturadas y genera una nueva factura con un
vencimiento a 15 días. Debemos guardar solo el estado de si la factura esta paga
o no.
  ● Se hace una factura por cada línea registrada en el sistema.
  ● Cada llamada debe pertenecer a solo una factura.

La información de los empleados y clientes debe ser la siguiente :
    ● Nombre
  ● Apellido
  ● DNI
  ● Ciudad
  ● Provincia
  
Cada línea tiene los siguientes datos :
  ● Número de línea (número de teléfono).
  ● Tipo de usuario (móvil o residencial).

Las llamadas incluyen :
  ● Numero de telefono origen
  ● Número de teléfono destino
  ● Localidad de origen
  ● Localidad de destino
  ● Precio por minuto
  ● Duración (en segundos)
  ● Precio total
  ● Factura donde está incluida esta llamada.

Las tarifas incluyen :
  ● Ciudad de origen
  ● Ciudad de destino
  ● Precio por minuto
(*) La ciudad de origen y de destino serán iguales para llamadas locales

Las facturas deben tener :
  ● Línea
  ● Cliente
  ● Cantidad de llamadas
  ● Precio costo
  ● Precio total
  ● Fecha
  ● Fecha de vencimiento
  
# PROGRAMACIÓN AVANZADA I

UTN Phones ha contratado los servicios de dos empresas expertas en la realización de
plataformas web. Con el fin de distribuir la carga de trabajo entre las dos empresas, una
de ellas se encargará del desarrollo Web y Aplicación Celular, mientras a nuestra
organización se encargará del desarrollo de una API REST para el mantenimiento de las
líneas (Llámese desde ahora Backoffice, para permitir a los empleados poder manejar
los datos de clientes, facturación, suspensión de líneas ,etc) y también para dar soporte
al portal de usuarios web y Aplicación Android. Se debe generar también la
infraestructura de base de datos necesaria para guardar la información. La base de
datos será escogida por el alumno según considere.

● El portal de usuarios y aplicacion Android deberá permitir :
  1) Login de clientes
  2) Consulta de llamadas del usuario logueado por rango de fechas.
  3) Consulta de facturas del usuario logueado por rango de fechas.
  4) Consulta de TOP 10 destinos más llamados por el usuario.

● Desde el sistema de Backoffice, se debe permitir :
  1) Login de empleados.
  2) Manejo de clientes.
  3) Alta , baja y suspensión
de líneas.
  4) Consulta de tarifas.
  5) Consulta de llamadas
por usuario.
  6) Consulta de facturación .

La facturación se hará
directamente por un
proceso interno en la
base datos.

● Se debe permitir también el agregado de llamadas, con un login especial, ya que
este método de nuestra API será llamado nada más que por el área de
infraestructura cada vez que se produzca una llamada. El área de infraestructura
sólo enviará la siguiente información de llamadas :
  ○ Número de origen
  ○ Número de destino
  ○ Duración de la llamada
  ○ Fecha y hora de la llamada

La tarifa y las localidades de destino deberán calcularse al momento de guardar la
llamada y no será recibido por la API REST.

En caso de no realizar el TP de BASE DE DATOS II , se debe calcular esta última
información desde la API REST.

Para la completitud del TP se considera :
  ● Seguir los fundamentos de API REST.
  ● División en capas mostradas en clase (Controller, Service, Persistence/Dao)
  ● Unit tests con cobertura de al menos 70% del código.

# BASE DE DATOS II

Se debe generar un diseño de base de datos relacional que permita dar soporte al
escenario planteado anteriormente, y a su vez todo el trabajo que se requiera para
cumplimentar lo siguiente :
  1) Generar las estructuras necesarias para dar soporte a 4 sistemas diferentes :
    a) BACKOFFICE, que permitirá el manejo de clientes, líneas y tarifas.
    b) CLIENTES, que permitirá consultas de llamadas y facturación.
    c) INFRAESTRUCTURA , que será el sistema que enviará la información de
llamadas a la base de datos.
    d) FACTURACIÓN , proceso automático de facturación.
  2) La facturación se realizará por un proceso automático en la base de datos. Se
debe programar este proceso para el primer día de cada mes y debe generar una
factura por línea y debe tomar en cuenta todas las llamadas no facturadas
para cada una de las líneas, sin tener en cuenta su fecha. La fecha de
vencimiento de esta factura será estipulada a 15 días.
  3) Generar las estructuras necesarias para el cálculo de precio de llamadas y las
localidades ( Ciudad de origen y ciudad de destino) al momento de inserción de
llamadas, ya que solo se reciben los números de origen y destino , la fecha y la
duración de la llamada.
  4) Generar las estructuras necesarias para dar soporte a las consultas de llamadas
por fecha y por usuario , debido a que tenemos restricción de que estas no
pueden demorar más de dos segundos y tenemos previsto que tendremos
500.000.000 de llamadas en el sistema en el mediano plazo. Este reporte incluirá:
    i) Número de origen
    ii) Ciudad de origen
    iii) Número de destino
    iv) Ciudad de destino
    v) Precio total
    vi) Duración
    vii) Fecha y hora de llamada.
  5) Como PLAN B , generar una estructura de base de datos NoSQL de su preferencia
para dar soporte al problema planteado.

