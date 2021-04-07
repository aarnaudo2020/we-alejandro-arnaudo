# we-alejandro-arnaudo

#### API REST

## Herramientas de desarrollo
* SpringBoot 2.4.4
* SDK Java 1.8
* Xubuntu 20.04
* Intellij IDEA 2021.1

## Ejecución
Contando con java 1.8 en una consola bash se puede ejecutar el jar que se encuentra versionado en este repositorio en la raiz del proyecto de la siguiente manera:

```bash 
java -jar crypto_pricing-0.0.1-SNAPSHOT.jar
```
el servicio levanta por defecto en el puerto 8081 que debe estar liberado en su sistema, de lo contrario se puede correr en un puerto de su preferencia:

```bash
java -jar -Dserver.port=8081 spring-5.jar
```

## Servicios disponibles 
Obtener el precio dado una Fecha y hora con precision de segundos si el mismo esta registrado para dicho timestamp.

#### GET http://localhost:8081/we/crypto/btc/usd/{timestamp}

Donde timestamp debe tener el formato: yyyy-mm-ddTHH:MM:SS

###### Ejemplo:
```bash 
GET http://localhost:8081/we/crypto/btc/usd/2021-04-07T12:17:38
```
Obtener las estadisiticas del precio para un rango de tiempo dado: 

#### GET http://localhost:8081/we/crypto/btc/usd/statistics?from={timeFrom}&to={timeTo}

##### Ejemplo:
```bash 
GET http://localhost:8081/we/crypto/btc/usd/statistics?from=2021-04-07T12:25:12&to=2021-04-07T12:26:31
```

Obtener toda la serie de tiempo disponible ordenada por tiempo en forma descedente:
###### GET http://localhost:8081/we/crypto/btc/usd/all

Obtener los ultimos 10 precios ordenados por tiempo descendente:
###### GET http://localhost:8081/we/crypto/btc/usd/head

