# Gestione Commesse
Una dashboard per la Gestione di Commesse che utilizza di base 
un database Postgresql.

INSTALLAZIONE

installare ultima versione della jdk 8 oracle (download.oracle.com)
installare maven 3.6.0 
effettuare il download dello zip
effettuare l'unzip di GestioneCommesse.zip
spostarsi nella direcvtory creata (GestioneCommesse)
eseguire: mvn spring-boot:run
l'applicazione è disponibile sulla porta 8080


INSTALLAZIONE POSTGRESQL
Installare Postgresql 11
creare il database eseguendo il comando:

createdb -h localhost -p 5432 -U postgres gestionecommesse

L'applicazione provvede a creare automaticamente il database di backend
ma al riavvio il database viene cancellato.

Se volete mantenere il database persistente bisogna cambiare 
in application.properties la seguente proprietà

spring.jpa.hibernate.ddl-auto=validate

