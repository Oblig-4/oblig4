/*
Dette er et program som lager en generisk klasse Lenkeliste<T> som implementerer grensesnittet Liste<T>.
Lenkelisten skal være av typen "First in, First out".
*/

public class Lenkeliste<T> implements Liste<T> {
  protected int antall; // antall elementer i lenkelisten
  protected Node start;

  protected class Node {
    public T innhold;
    public Node neste;

    public Node(T innhold) {
      this.innhold = innhold;
    }
  }

  // Metoden legger til et element på slutten av listen.
  // @param x er elementet som skal legges til (typeparameter).
  public void leggTil(T x) {
    Node a = start;

    // Tilstanden dersom lenkelisten er tom. Oppretter en ny node som tilordnes variabelen "start".
    if (start == null) {
      start = new Node(x);
      antall++;
      return;
    }

    // While-løkke som itererer gjennom lenkelisten helt til vi havner på siste node.
    while (a.neste != null) {
      a = a.neste;
    }

    // Legger til en node på slutten av listen.
    a.neste = new Node(x);
    antall++;
  }

  // Metoden legger til et element i en gitt posisjon i listen, og skyve neste element et hakk lenger bak.
  // @param pos er posisjonen i listen der elementet skal legges til i.
  // @param x er elementet som skal legges til.
  public void leggTil(int pos, T x) throws UgyldigListeIndeks{
    // Behandler ugyldige posisjonsindekser (når posisjonen er større enn antall elementer eller er negativ).
    // Kaster unntaksklassen "UgyldigListeIndeks".
    if  (pos > antall || pos < 0) {
      UgyldigListeIndeks e = new UgyldigListeIndeks(pos);
      throw e;
    }

    Node ny = new Node(x);
    Node a = start;

    // Tilstanden dersom posisjonen er 0. Da vil elementet plasseres i starten av listen.
    if (pos == 0) {
      start = ny;
      ny.neste = a;
      antall++;
      return;
    }

    // For-løkke som itererer gjennom lenkelisten helt til "a" tilsvarer noden som er et hakk foran posisjonen.
    for (int i = 0; i < pos-1; i++) {
      a = a.neste;
    }

    // Noden foran posisjonen blir tilordnet variabelen "foran", mens noden etter posisjonen blir tilordnet variabelen "etter".
    Node foran = a;
    Node etter = a.neste;

    // Legger til en ny node i gitt posisjon ved å endre på pekerne slik at noden "foran" peker på den nye noden,
    // og den nye noden peker på noden "etter".
    foran.neste = ny;
    ny.neste = etter;
    antall++;
  }

  // Metoden setter inn et element på en gitt posisjon og overskriver det som var der fra før.
  // @param pos er posisjonen der elementet skal settes inn i.
  // @param x er elementet som skal settes inn.
  public void sett(int pos, T x) throws UgyldigListeIndeks {
    // Behandler ugyldige posisjonsindekser, altså når posisjonen er negativ eller er større enn antall elementer - 1
    // (Ettersom vi teller fra indeks 0 og oppover, kan pos ikke være lik antall siden da vil vi havne på en posisjon uten node).
    if  (pos > antall-1 || pos < 0 ){
      UgyldigListeIndeks e = new UgyldigListeIndeks(pos);
      throw e;
    }

    Node a = start;

    // For-løkke som itererer gjennom lenkelisten helt til "a" tilsvarer noden som er i gitt posisjon.
    for (int i = 0; i < pos; i++) {
      a = a.neste;
    }

    // Setter inn elementet x ved å endre innholdet til noden "a" til å bli x.
    a.innhold = x;
  }

  // Metoden fjerner og returnerer elementet på starten av listen.
  public T fjern() throws UgyldigListeIndeks {
    // Tilstanden dersom lenkelisten er tom, da er det ikke mulig å fjerne et element. "UgyldigListeIndeks" kastes med indeks -1.
    if (start == null) {
      UgyldigListeIndeks e = new UgyldigListeIndeks(-1);
      throw e;
    }

    // Fjerner elementet på starten av listen og returnerer elementet.
    Node a = start;
    start = a.neste;
    antall--;
    return a.innhold;
  }

  // Fjerner elementet på gitt posisjon i listen.
  // @param pos er posisjonen til elementet som skal fjernes.
  public T fjern(int pos) throws UgyldigListeIndeks {
    // Behandler ugyldige posisjonsindekser, altså når posisjonen er negativ eller er større enn antall elementer - 1
    // (pos kan ikke være lik antall siden det ikke er mulig å fjerne et element i en posisjon uten node).
    if  (pos > antall-1 || pos < 0) {
      UgyldigListeIndeks e = new UgyldigListeIndeks(pos);
      throw e;
    }

    // Tilstanden dersom lenkelisten er tom, da er det ikke mulig å fjerne et element. "UgyldigListeIndeks" kastes med indeks -1.
    if (start == null) {
      UgyldigListeIndeks e = new UgyldigListeIndeks(-1);
      throw e;
    }

    Node a = start;
    // Tilstanden dersom posisjonen er 0. Da vil elementet på starten av listen fjernes og returneres.
    if (pos == 0) {
      start = a.neste;
      antall--;
      return a.innhold;
    }

    // For-løkke som itererer gjennom lenkelisten helt til "a" tilsvarer noden som er et hakk foran posisjonen.
    for (int i = 0; i < pos-1; i++) {
      a = a.neste;
    }

    Node foran = a; // Noden foran posisjonen.
    Node returneres = foran.neste; // Noden i gitt posisjon med elementet som skal fjernes og returneres.
    Node etter = foran.neste.neste; // Noden etter posisjonen.

    // Fjerner elementet på gitt posisjon ved å endre pekeren slik at noden foran posisjonen peker på noden etter posisjonen.
    // Deretter returneres elementet.
    foran.neste = etter;
    antall--;
    return returneres.innhold;

  }

  public int stoerrelse() {
    return antall;
  }

  // Metoden hentet ut et element på gitt posisjon.
  // @param pos er posisjonen til elementet som skal hentes ut.
  public T hent(int pos) throws UgyldigListeIndeks {
    // Behandler ugyldige posisjonsindekser, altså når posisjonen er negativ eller er større enn antall elementer - 1
    // (ettersom vi teller fra indeks 0 og oppover, kan pos ikke være lik antall siden da vil vi havne på en posisjon uten node).
    if  (pos > antall-1 || pos < 0) {
      UgyldigListeIndeks e = new UgyldigListeIndeks(pos);
      throw e;
    }

    Node a = start;
    // For-løkke som itererer gjennom lenkelisten helt til "a" tilsvarer noden som er i gitt posisjon.
    for (int i = 0; i < pos; i++) {
      a = a.neste;
    }

    return a.innhold;
  }
}
