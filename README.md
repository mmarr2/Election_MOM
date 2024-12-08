# Durchführung

## Installation von Apache Kafka

`docker pull apache/kafka:3.9.0`

`docker run -p 9092:9092 apache/kafka:3.9.0`

## Projekt anpassen

https://medium.com/@abhishekranjandev/a-comprehensive-guide-to-integrating-kafka-in-a-spring-boot-application-a4b912aee62e

Ich hab einfach die vorige Aufgabe kopiert und passe diese jetzt so an, dass mit Message Queues gearbeitet werden kann. 

In `gradle.build` müssen die dependencies angepasst werden: 

```nix
implementation 'org.springframework.kafka:spring-kafka'
implementation 'org.springframework.boot:spring-boot-starter-web'
```

In der Datei `application.properties` im Verzeichnis `src/main/ressources` muss folgendes hinzugefügt werden: 

```nix
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=my-group-id
```

Als nächstes brauchen wir die `KafkaProducerConfig`  Klasse im Package `com.example.kafka.producer`.

Außerdem wird die Klasse `MessageProducer` im selben Package benötigt. Sie sendet Nachrichten an ein Kafka Topic. 

Natürlich muss auch wer die Nachrichten empfangen. Dazu ein neues Package namens `com.example.kafka.consumer`  erstellen und in diesem die Klassen `KafkaConsumerConfig` und `MessageConsumer` hinzufügen. 

Wichtig ist jetzt, dass die Daten per `POST`-Request an die Zentrale geschickt werden können. Dazu muss die Annotation `@PostMapping()` verwendet werden. Um die Daten in der Zentrale zu speichern, hab ich eine entsprechende Klasse geschrieben. 

So können die Daten an die Zentrale geschickt werden (in der Konsole): 

```bash
curl -X POST "http://localhost:8080/send/12345
```

Um die Daten zu sehen, muss einfach nur `localhost:8080/getData` im Browser aufgerufen werden. 

# Probleme

Es gab Probleme beim Ausführen, nachdem ich die Packages neu angeordnet hatte. Das lag daran, dass die Klasse `ElectionApplication` im falschen Package war. Um das Problem zu lösen, musste ich die Klasse ins `com.example` Package verschieben. 

# Fragestellungen

Nennen Sie mindestens 4 Eigenschaften der Message Oriented Middleware?

- Persistente, asynchrone Kommunikation
- Kommunikation kann Minuten dauern
- Effiziente Ressourcennutzung
- Die Nachrichten sind in einer Warteschlange

Was versteht man unter einer transienten und synchronen Kommunikation?

**Transient:** Die Nachricht wird nur so lange gespeichert, während Sender und Empfänger ausführen. 

**Synchron:** Der Absender kann nichts machen, bis die Nachricht beim Empfänger angekommen ist und die Nachricht vom Empfänger verarbeitet wurde. 

Beschreiben Sie die Funktionsweise einer JMS Queue?

Eine JMS Queue hat m Sender und n Empfänger, aber nur ein Empfänger kann eine Nachricht erhalten. 

JMS Overview - Beschreiben Sie die wichtigsten JMS Klassen und deren Zusammenhang?

**ConnectionFactory** creates **Connection** creates **Sessions** creates either **MessageProducer** or **MessageConsumer.** 

1. **ConnectionFactory:**
    - Wird verwendet, um Verbindungen zu einer JMS-Provider-Instanz (z. B. einem Broker) zu erstellen.
2. **Connection:**
    - Repräsentiert eine Verbindung zwischen dem Client und dem JMS-Provider.
    - Wird von der `ConnectionFactory` erstellt.
3. **Session:**
    - Wird innerhalb einer Connection erstellt.
    - Dient zum Senden und Empfangen von Nachrichten.
    - Unterstützt Transaktionen und den Nachrichtenfluss.
4. **MessageProducer:**
    - Wird innerhalb einer Session erstellt.
    - Verantwortlich für das Senden von Nachrichten an eine Queue oder ein Topic.
5. **MessageConsumer:**
    - Wird innerhalb einer Session erstellt.
    - Dient zum Empfangen von Nachrichten aus einer Queue oder einem Topic.

**Zusammenhang:**

- `ConnectionFactory` → erstellt `Connection` → erstellt `Session` → erstellt `MessageProducer` oder `MessageConsumer`.

Beschreiben Sie die Funktionsweise eines JMS Topic?

Ein JMS Topic hat m Publisher und n Subscriber und Nachrichten können an alle Subscriber geliefert werden. 

Was versteht man unter einem lose gekoppelten verteilten System? Nennen Sie ein Beispiel dazu. Warum spricht man hier von lose?  

Lose gekoppelt bedeutet, dass das System nicht fix an etwas gebunden ist und es eine Zwischenstufe zwischen Sender und Empfänger gibt. Auch wenn einer von den beiden Teilnehmern nicht aktiv ist, gehen die Nachrichten nicht verloren.
