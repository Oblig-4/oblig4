/*
Dette er et program som lager en subklasse "Vanlig" av superklassen "Legemiddel",
for representasjon av vanlige legemidler.
*/

class Vanlig extends Legemiddel {

  // @param navn er navnet på det vanedannende legemiddelet.
  // @param pris er prisen på det vanedannende legemiddelet.
  // @param virkestoff er mg virkestoff i det vanedannende legemiddelet.
  // "Vanlig" arver egenskaper fra "Legemiddel" (uten tilleggsegenskaper), kaller derfor på superklasssens konstruktør vha. super (med de aktuelle parametrene).
  public Vanlig(String navn, double pris, double virkestoff) {
    super(navn, pris, virkestoff);
  }

  // Overskriver toString() metoden til å returnere all informasjon om objektet.
  @Override
  public String toString() {
    return "Vanlig" + "\nLegemiddel id: " + id + "\nNavn: " + navn + "\nPris: " + pris + "\nVirkestoff: " + virkestoff;
  }
}
