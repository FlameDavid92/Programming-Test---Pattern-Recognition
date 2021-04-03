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

### ERRORI:

| METODO | URL                             | MESSAGGIO DI ERRORE            | DESCRIZIONE                                   |
| ------ | ------------------------------- | ------------------------------ | --------------------------------------------- |
| POST   | http://localhost:8080/point     | 400 - Invalid content          | Il body in request non rappresenta un punto.  |
| POST   | http://localhost:8080/point     | 400 - Already added            | Il punto è già presente nel piano.            |
| GET    | http://localhost:8080/lines/{n} | 400 - Invalid number of points | Il numero "n" fornito è negativo              |
| DELETE | http://localhost:8080/space     | 400 - Error while deleting.    | Errore durante l'eliminazione.                |
| POST   | http://localhost:8080/loadspace | 400 - Invalid content          | Il body in request non rappresenta uno spazio |



### DESCRIZIONE SOLUZIONE

IN MEMORIA

- Un insieme di punti S (space) che rappresenta il piano
- Un dizionario V indicizzato dalla coordinata x che contiene le linee verticali
- Un dizionario O indicizzato dalla coordinata y che contiene le linee orizzontali
- Un dizionario SY indicizzato dalla coppia (slope, yIntersect) che contiene le linee con un coefficente angolare esistente e > 0.

--- L'idea è quella di tenere aggiornati i vari insiemi ad ogni aggiunta di un nuovo punto.

Quando viene aggiunto un nuovo punto P:

1) Per tutti i punti nel piano che hanno sia la coordinata x che la y differenti da quelle di P:

- Si calcola slope s e yIntersect y dato P ed il punto Q attualmente preso in considerazione:

   - Se la coppia calcolata (s,y) è già presente in SY allora si aggiunge P alla linea rappresentata da (s,y)

   - Altrimenti si aggiunge a SY la nuova chiave (s,y) che indicizza una nuova linea contente P e Q

2) Se la coordinata x xp di P è presente come chiave in V allora si aggiunge alla linea verticale indicizzata da xp il punto P, altrimenti si crea una nuova chiave xp per V che indicizza un nuovo insieme contente P. (ATT. finché l'insieme ha un solo elemento non conta come linea).

3)  Se la coordinata y yp di P è presente come chiave in O allora si aggiunge alla linea orizzontale indicizzata da yp il punto P, altrimenti si crea una nuova chiave yp per O che indicizza un nuovo insieme contente P. (ATT. finché l'insieme ha un solo elemento non conta come linea).

4) Si aggiunge P allo spazio S.

--- La chiamata di cancellazione del piano aggiornerà  S ad insieme vuoto ed i tre dizionari V,O,SY a vuoti.

--- La chiamata di restituzione del piano restituirà S.

--- La chiamata di restituzione delle linee con almeno n punti restituirà:

	- errore per n<0.
	- nessuna linea per n=0.
	- tutte le linee (insiemi da almeno 2 elementi) presenti in SY, O, V per n=1
	- tutte le linee con almeno n elementi presenti in SY,O,V per n>=2



---Poiché non si utilizza persistenza dei dati, è stata aggiunta una chiamata POST a /loadspace che permette il caricamento di un piano precedentemente ottenuto tramite GET a /space.

--- Come ulteriore ottimizzazione è stata abilitata la cache, questa viene pulita ad ogni nuovo inserimento di un punto (o di più punti se si carica un nuovo piano) o alla cancellazione di un piano.