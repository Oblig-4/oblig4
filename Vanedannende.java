/*
Dette er et program som lager en subklasse "Vanedannende" av superklassen "Legemiddel",
for representasjon av vanedannende legemidler.
*/

class Vanedannende extends Legemiddel {
  protected int styrke;

  // @param navn er navnet på det vanedannende legemiddelet.
  // @param pris er prisen på det vanedannende legemiddelet.
  // @param virkestoff er mg virkestoff i det vanedannende legemiddelet.
  // "Vanedannende" arver egenskaper fra "Legemiddel", kaller derfor på superklasssens konstruktør vha. super (med de aktuelle parametrene).
  // @param styrke er styrken på det vanedannende legemiddelet. Variabelen "styrke" arves ikke fra superklassen, og initialiseres derfor direkte i denne konstruktøren.
  public Vanedannende(String navn, double pris, double virkestoff, int styrke) {
    super(navn, pris, virkestoff);
    this.styrke = styrke;
  }


  public int hentVanedannedeStyrke() {
    return styrke;
  }

  // Overskriver toString() metoden til å returnere all informasjon om objektet.
  @Override
  public String toString() {
    return "Vanedannende" + "\nLegemiddel id: " + id + "\nNavn: " + navn + "\nPris: " + pris + "\nVirkestoff: " + virkestoff + "\nStyrke: " + styrke;
  }
}
