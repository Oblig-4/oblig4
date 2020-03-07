/*Dette er et program som lager en klasse "Lege" for representasjon av leger.*/
//MANGLER D3
class Lege implements Comparable<Lege> {
  protected Lenkeliste<Resept> utskrevendeResepter;
  protected String navn;

  // @param navn er navnet på legen.

  public Lege(String navn) {
    this.navn = navn;
  }



  public String hentNavn() {
    return navn;
  }

//Leger sorteres alfabetisk etter navn.
  public int compareTo(Lege annen){
    return navn.compareTo(annen.hentNavn());
  }

//Metoden gjør det mulig å hente ut en liste av resepter, som legen har skrevet.
  public Lenkeliste<Resept> hentUtResepter(){
    return utskrevendeResepter;
  }

  @Override
  public String toString() {
    return "Lege\n" + "Navn: " + navn;
  }

}
