### Progetto per esame di Tecnologie e Sviluppo Web (TSW).
Si tratta di una web application sviluppata, seguendo il _modello MVC_, interamente in Java, sfruttando varie tecnologie come _Servlet_, _JSP_ e _Json_.

Nel dettaglio è un E-commerce di Giochi da tavolo, le figure principali del applicazione web sono:

- **Utenti non registrati**, in questo caso le azioni effettuabili sono ben poche, si può accedere alla pagina di registrazione, login e catalogo. Si possono aggiungere prodotti al carrello che verrà salvato nella sessione permettendo all'utente una volta loggato o registrato di mantenere la consistenza del carrello.
- **Utenti Registrati**, in questo caso le azioni effettuabili sono identiche a quelle dell'utente non registrato ma con la possibilità di finalizzare ordine e poter accedere alla propria pagina personale dove trovare i propri dati (modificabili) e lo storico degli ordini personali
- **Moderatore**, figura fondamentale per l'applicazione, può eseguire azioni di manipolazione degli oggetti nel catalogo, li può rimuovere o eliminare completamente dal sistema, li può modificare e infine ne può aggiungere di nuovi. Inoltre presenta diverse pagine che gli permettono di ottenere informazioni riguardanti gli iscritti all'applicazione, agli ordini effettuati e all'andamento delle vendite nei diversi mesi dell'anno.
