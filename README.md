È consigliata l'installazione dell'ultimo jre per l'esecuzione dell'API ( https://www.java.com/it/download/ ).

Nella cartella "JAR" è presente il file eseguibile tramite il comando: 

`java -jar [PERCORSOALFILE]/PatternRecognition-0.0.1-SNAPSHOT.jar`

Nella cartella POSTMAN_COLLECTION è presente un file json importabile su Postman per testare gli endpoint dell'API.

La REST API è stata sviluppata con SpringBoot versione 2.4.4.

### ENDPOINT:

| METODO | URL                             | REQUEST                                                      | RESPONSE                                                     | DESCRIZIONE                                                  |
| ------ | ------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| POST   | http://localhost:8080/point     | { "x":number, "y":number }                                   | { "code":number, "message":string }                          | Aggiunge un punto al piano cartesiano.                       |
| GET    | http://localhost:8080/space     | -                                                            | { "space": <br/>    [{ "x":number,<br/>    "y:number},...] } | Ritorna tutti i punti nel piano.                             |
| GET    | http://localhost:8080/lines/{n} | n:number PathVariable                                        | { "lines":<br/>    [{"line":<br/>        [{"x":number,<br/>        "y":number},<br/>        ...]<br/>    },<br/>.   ...] } | Ritorna tutti i segmenti che passano attraverso almeno "n" punti del piano. Ogni segmento ("line") è restituito come insieme di punti. |
| DELETE | http://localhost:8080/space     | -                                                            | { "code":number, "message":string }                          | Elimina tutti i punti nel piano.                             |
| POST   | http://localhost:8080/loadspace | { "space": <br/>    [{ "x":number,<br/>    "y:number},...] } | { "code":number, "message":string }                          | Elimina il precedente piano e ne crea uno nuovo con tutti i punti forniti. |



Alcune delle risposte di errore gestite contengono un body così formato: { "code":number, "message":string }

ERRORI:

| METODO | URL                             | MESSAGGIO DI ERRORE            | DESCRIZIONE                                   |
| ------ | ------------------------------- | ------------------------------ | --------------------------------------------- |
| POST   | http://localhost:8080/point     | 400 - Invalid content          | Il body in request non rappresenta un punto.  |
| POST   | http://localhost:8080/point     | 400 - Already added            | Il punto è già presente nel piano.            |
| GET    | http://localhost:8080/lines/{n} | 400 - Invalid number of points | Il numero "n" fornito è negativo              |
| DELETE | http://localhost:8080/space     | 400 - Error while deleting.    | Errore durante l'eliminazione.                |
| POST   | http://localhost:8080/loadspace | 400 - Invalid content          | Il body in request non rappresenta uno spazio |