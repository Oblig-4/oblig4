/*
Dette er et program som lager en generisk subklasse Stabel<T> som arver fra superklassen Lenkeliste<T>.
Klassen arver dessuten fra grensensittet Comparable<T>.
*/

public class SortertLenkeliste<T extends Comparable<T>> extends Lenkeliste<T> {

  // Overskriver metoden leggTil fra superklassen. Metoden skal legge til elementer i sortert rekkefølge (fra minst til størst).
  @Override
  public void leggTil(T x) {
    Node ny = new Node(x);
    Node a = start;

    // Tilstanden dersom lenkelisten er tom. Da vil den nye noden bli tilordnet variabelen "start".
    if (start == null) {
      start = ny;
      antall++;
      return;
    }

    // Benytter metoden compareTo til å sammenligne det nye elementet med det første elementet i listen.
    // Dersom det nye elementet større eller lik det første elementet, har vi en while-løkke som itererer gjennom lenkelisten så lenge vi ikke er på siste node og
    // så lenge hver node i listen er mindre enn det nye elementet.
    // While-løkken vil altså fortsette helt til vi treffer på et element som er større enn det nye elementet.
    // "a" vil dermed tilsvarer noden et som er et hakk før elementet som er større (enn det nye elementet).
    else if (x.compareTo(a.innhold) >= 0) {
      while (a.neste != null && a.neste.innhold.compareTo(x) < 0) {
        a = a.neste;
      }

      Node foran = a; // Noden foran elementet som er større.
      Node etter = a.neste; // Noden etter elementet som er større.

      // Legger til det nye elementet slik at det er foran elementet som er storre.
      foran.neste = ny;
      ny.neste = etter;
      antall++;
      return;
    }

    // Benytter metoden compareTo til å sammenligne det nye elementet med det første elementet i listen.
    // Dersom det nye elementet er midnre enn det første elementet, vil elementet legges til i starten av listen og flytte det første elementet et hakk lenger bak.
    else if (x.compareTo(a.innhold) < 0) {
      start = ny;
      ny.neste = a;
      antall++;
      return;
    }
  }

  // Overskriver metoden fjern fra superklassen. Metoden skal fjerne og returnere det største elementet.
  @Override
  public T fjern() throws UgyldigListeIndeks {
    // Tilstanden dersom lenkelisten er tom. "UgyldigListeIndeks" kastes med indeks -1.
    if (start == null) {
      UgyldigListeIndeks e = new UgyldigListeIndeks(-1);
      throw e;
    }

    // Det storste elementet sin posisjon tilsvarer antall-1 (altså slutten av listen, siden listen er sortert fra minst til størst).
    // Kaller på metoden fjern fra superklassen med denne posisjonen og returnerer dette.
    int storstPos = antall - 1; // det største elementet er den siste posisjonen.
    return fjern(storstPos);
  }

  // Overskriver metoden sett fra superklassen for å sørge for at listen forblir sortert. Metoden kaster unntaket "UnsupportedOperationException".
  @Override
  public void sett(int pos, T x) throws UnsupportedOperationException {
    UnsupportedOperationException e = new UnsupportedOperationException();
    throw e;
  }

  // Overskriver metoden leggTil fra superklassen for å sørge for at listen forblir sortert. Metoden kaster unntaket "UnsupportedOperationException".
  @Override
  public void leggTil(int pos, T x) throws UnsupportedOperationException {
    UnsupportedOperationException e = new UnsupportedOperationException();
    throw e;
  }
}
