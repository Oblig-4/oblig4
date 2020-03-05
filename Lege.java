/*
Dette er et program som lager en klasse "Lege" for representasjon av leger.
*/

class Lege {
  protected String navn;

  // @param navn er navnet på legen. 
  public Lege(String navn) {
    this.navn = navn;
  }

  public String hentNavn() {
    return navn;
  }

  @Override
  public String toString() {
    return "Lege\n" + "Navn: " + navn;
  }
}
