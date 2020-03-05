/*
Dette er et program som lager en subklasse "BlaaResept" av superklassen "Resept",
for representasjon av blå resepter.
*/

class BlaaResept extends Resept {

  // "BlaaResept" arver egenskaper fra "Resept" (uten tilleggsegenskaper) , kaller derfor på superklasssens konstruktør vha. super (med de aktuelle parametrene).
  public BlaaResept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit) {
    super(legemiddel, utskrivendeLege, pasientId, reit);
  }

  @Override
  public String farge() {
    return "blaa";
  }

  // Returnerer prisen pasienten må betale, som er 25% av prisen på legemiddelet for blå resept.
  @Override
  public double prisAaBetale() {
    double pris = legemiddel.hentPris();
    return pris*0.25;
  }

  // Overskriver toString() metoden til å returnere all tilgjengelig informasjon om objektet.
  @Override
  public String toString() {
    return "Blaa resept\n" + "Resept id: " + id + "\nLegemiddel type: " + legemiddel + "\nLege: " + utskrivendeLege.hentNavn() +
          "\nPasient id: " + pasientId + "\nReit: " + reit + "\nFarge: " + this.farge() + "\nPris aa betale: " + this.prisAaBetale();
  }

}
