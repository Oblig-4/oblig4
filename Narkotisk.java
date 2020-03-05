/*
Dette er et program som lager en subklasse "Narkotisk" av superklassen "Legemiddel",
for representasjon av narkotiske legemidler.
*/

class Narkotisk extends Legemiddel {
  protected int styrke;

  // @param navn er navnet på det narkotiske legemiddelet.
  // @param pris er prisen på det narkotiske legemiddelet.
  // @param virkestoff er mg virkestoff i det narkotiske legemiddelet.
  // "Narkotisk" arver egenskaper fra "Legemiddel", kaller derfor på superklasssens konstruktør vha. super (med de aktuelle parametrene).
  // @param styrke er styrken på det narkotiske legemiddelet. Variabelen "styrke" arves ikke fra superklassen, og initialiseres derfor direkte i denne konstruktøren.
  public Narkotisk(String navn, double pris, double virkestoff, int styrke) {
    super(navn, pris, virkestoff);
    this.styrke = styrke;
  }

  public int hentNarkotiskStyrke() {
    return styrke;
  }

  // Overskriver toString() metoden til å returnere all tilgjengelig informasjon om objektet.
  @Override
  public String toString() {
    return "Narkotisk" + "\nLegemiddel id: " + id + "\nNavn: " + navn + "\nPris: " + pris + "\nVirkestoff: " + virkestoff + "\nStyrke: " + styrke;
  }

}
