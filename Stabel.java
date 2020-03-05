/*
Dette er et program som lager en generisk subklasse Stabel<T> som arver fra superklassen Lenkeliste<T>.
*/

public class Stabel<T> extends Lenkeliste<T> {

  // Metoden legger til et element på slutten av listen, ved å kalle på metoden leggTil fra superklassen med x som argument.
  public void leggPaa(T x) {
    leggTil(x);
  }

  // Metoden fjerner et element på slutten av listen, ved å kalle på metoden fjern fra superklassen, med antall-1 som argument.
  // (ettersom vi teller fra indeks 0 og oppover, vil slutten av listen ha posisjon antall-1)
  public T taAv() {
    return fjern(antall-1);
  }
}
