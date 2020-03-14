import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.NoSuchElementException;
//import java.util.Arrays; test

class Legesystem{
  private Lenkeliste<Pasient> pasienter;
  private SortertLenkeliste<Lege> leger;
  private Lenkeliste<Legemiddel> legemidler;
  private Lenkeliste<Resept> resepter;

  public Legesystem(File filnavn) throws FileNotFoundException, UlovligUtskrift{
    pasienter = new Lenkeliste<Pasient>();
    legemidler = new Lenkeliste<Legemiddel>();
    leger = new SortertLenkeliste<Lege>();
    resepter = new Lenkeliste<Resept>();

    lesFraFil(filnavn);

  }

  public void lesFraFil(File filnavn) throws FileNotFoundException, UlovligUtskrift, NumberFormatException, NoSuchElementException{
      Scanner fil = null;
      try{
        fil = new Scanner(filnavn);
      }
      catch (FileNotFoundException e) {
        System.out.println("Fant ikke filen, gaar tilbake til hovedmeny");
        System.exit(1);//0 : success full termination, 1: exiter med vilje, -1: problem man ikke vet om
      }

      String a = fil.nextLine();

  //lager liste med pasienter
      while (fil.hasNextLine()){
        String linje = fil.nextLine();

        while (linje.charAt(0) != '#') { //charAt(int index) returnerer char verdi på den gitt indexen.
          String[] data = linje.split(",");
          //System.out.println(Arrays.toString(data)); test
            String pasientNavn = data[0];
            String fodselsnummer = data[1];
            Pasient pasient = new Pasient(pasientNavn, fodselsnummer);
            pasienter.leggTil(pasient);
            linje = fil.nextLine();
        }
        break;
      }

  //lager liste med legemidler
      while (fil.hasNextLine()){
      String linje = fil.nextLine();
        while (linje.charAt(0) != '#') {
          String[] data = linje.split(",");
          //System.out.println(Arrays.toString(data)); test
          try{
            String navn = data[0];
            String type = data[1];
            double pris = Double.parseDouble(data[2]);
            double virkestoff = Double.parseDouble(data[3]);
            int styrke = 0;
            linje = fil.nextLine();
            if (data.length == 5){
              styrke = Integer.parseInt(data[4]);
            }

            if (type.equals("vanlig")) {
              Vanlig vanlig = new Vanlig(navn, pris, virkestoff);
              legemidler.leggTil(vanlig);
            }
            else if (type.equals("narkotisk")) {
              Narkotisk narko = new Narkotisk(navn, pris, virkestoff, styrke);
              legemidler.leggTil(narko);
            }
            else if (type.equals("vanedannende")){
              Vanedannende vane = new Vanedannende(navn, pris, virkestoff, styrke);
              legemidler.leggTil(vane);
            }
          }
          catch(NumberFormatException f){
            linje = fil.nextLine();
          }
        }
        break;
      }

  //lager liste med leger -sortert
      while (fil.hasNextLine()){
      String linje = fil.nextLine();
        while (linje.charAt(0) != '#'){
            String[] data = linje.split(",");
            //System.out.println(Arrays.toString(data)); test
          try{
            String navn = data[0];
            int id = Integer.parseInt(data[1]);
            linje = fil.nextLine();
            if (id == 0){
              Lege l = new Lege(navn);
              leger.leggTil(l);
            }
            else{
              Spesialist s = new Spesialist(navn, id);
              leger.leggTil(s);
            }
          }
          catch(NumberFormatException f){
            linje = fil.nextLine();
          }
        }
        break;
      }

  //lager liste med resepter
      while (fil.hasNextLine()){
        String linje = fil.nextLine();
        while (linje.charAt(0) != '#'){
          String[] data = linje.split(",");
          //System.out.println(Arrays.toString(data)); test
          try{
            int id = Integer.parseInt(data[0]);
            String navn = data[1];
            int pId = Integer.parseInt(data[2]);
            String farge = data[3];
            int reit = 0;
            if (data.length == 5){
              reit = Integer.parseInt(data[4]);
            }
            Lege leg = null;
            Pasient pas = null;
            Legemiddel lm = null;



            if(pId < pasienter.stoerrelse() && id < legemidler.stoerrelse()){
            //finner riktig lege
              for (int i=0; i < leger.stoerrelse(); i++) {
                if(leger.hent(i).hentNavn().equals(navn)) {
                  leg = leger.hent(i);
                }
              }

              //finner riktig pasient
              for (Pasient p : pasienter) {
                if(p.hentPasientId() == pId){
                  pas = p;
                }
              }

              //finner riktig legemiddel
              for (Legemiddel legem : legemidler) {
                if(legem.hentId() == id){
                  if (legem instanceof Vanlig) {
                    lm = legem;
                    lm = (Vanlig) lm;
                  }

                  else if (legem instanceof Vanedannende){
                    lm = legem;
                    lm = (Vanedannende) lm;
                  }
                  else if (legem instanceof Narkotisk){
                    lm = legem;
                    lm = (Narkotisk) lm;
                  }
                }
              }


              if (farge.equals("p")){
                try{
                PResept p = leg.skrivPResept(lm, pas);
                pas.leggTilResept(p);
                }
                catch(UlovligUtskrift e){}
              }
              else if (farge.equals("hvit")) {
                try{
                Hvit h = leg.skrivHvitResept(lm, pas, reit);
                pas.leggTilResept(h);
                }
                catch(UlovligUtskrift e){}
                }
              else if (farge.equals("militaer")){
                try{
                  Militaer m = leg.skrivMillitaerResept(lm, pas, reit);
                  pas.leggTilResept(m);
                }
                catch(UlovligUtskrift e){}
              }
              else if (farge.equals("blaa")) {
                try{
                  Blaa b = leg.skrivBlaaResept(lm, pas, reit);
                  pas.leggTilResept(b);
                }
                catch(UlovligUtskrift e){}
              }
            }// slutt på if
          try{
            linje = fil.nextLine();
          }
          catch(NoSuchElementException f){
            break;
          }
        }
        catch(NumberFormatException f){
          linje = fil.nextLine();
        }

      }
      break;
    }
    fil.close();
  }
  
 //E2

  public void meny() {
    System.out.println("Meny:" + "\n");
    System.out.println("1: Skriv ut en fullstendig oversikt over pasienter, leger, legemidler og resepter");
    System.out.println("2: Opprette og legge til nye elementer i systemet:");
    System.out.println("3: Bruk en gitt resept fra listen til en pasient");
    System.out.println("4: Skriv ut forskjellige former for statistikk");
    System.out.println("5: Skriv ut alle data til fil");
    System.out.println("Skriv 0 for å avslutte:");

  }
  public void hovedmeny(){ //IF-test som sjekker at tallene ikke tastes, går tilbake til meny.
    this.meny();
    Scanner sc = new Scanner(System.in);
    int svar = sc.nextInt();

    while (svar != 0){
      try  {
        if (svar == 1){
          this.skrivUtOversikt();
        }
        }

        catch(InputMismatchException e){
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
          hovedmeny();

        }


        try {
         if (svar == 2){
          this.leggTilElement();
        }
        }

        catch(InputMismatchException e){
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
          hovedmeny();

        }


        try{
         if (svar == 3){
          this.skrivUtResept();
        }
      }
        catch(InputMismatchException e){
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
          hovedmeny();
        }


        try{
         if (svar == 4){
           this.statistikkMeny();
        }
        }

      catch(InputMismatchException e){
        System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
        hovedmeny();

      }

      try{
        if (svar == 5){
          this.METODE E8();
        }
      }
      catch(InputMismatchException e){
        System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
        hovedmeny();

      System.out.println("Vil du fortsette eller avslutte?");
      int svar1 = sc.nextInt();
      meny();
    }
    System.out.println("Du tastet 0, programmet avsluttes.");//E2

  public void meny() {
    System.out.println("Meny:" + "\n");
    System.out.println("1: Skriv ut en fullstendig oversikt over pasienter, leger, legemidler og resepter");
    System.out.println("2: Opprette og legge til nye elementer i systemet:");
    System.out.println("3: Bruk en gitt resept fra listen til en pasient");
    System.out.println("4: Skriv ut forskjellige former for statistikk");
    System.out.println("5: Skriv ut alle data til fil");
    System.out.println("Skriv 0 for å avslutte:");

  }
  public void hovedmeny(){ //IF-test som sjekker at tallene ikke tastes, går tilbake til meny.
    this.meny();
    Scanner sc = new Scanner(System.in);
    int svar = sc.nextInt();

    while (svar != 0){
      try  {
        if (svar == 1){
          this.skrivUtOversikt();
        }
        }

        catch(InputMismatchException e){
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
          hovedmeny();

        }


        try {
         if (svar == 2){
          this.leggTilElement();
        }
        }

        catch(InputMismatchException e){
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
          hovedmeny();

        }


        try{
         if (svar == 3){
          this.skrivUtResept();
        }
      }
        catch(InputMismatchException e){
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
          hovedmeny();
        }


        try{
         if (svar == 4){
           this.statistikkMeny();
        }
        }

      catch(InputMismatchException e){
        System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
        hovedmeny();

      }

      try{
        if (svar == 5){
          this.METODE E8(); //IKKE FERDIG
        }
      }
      catch(InputMismatchException e){
        System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
        hovedmeny();

      System.out.println("Vil du fortsette eller avslutte?");
      int svar1 = sc.nextInt();
      meny();
    }
    System.out.println("Du tastet 0, programmet avsluttes.");
   
    // E3
   public void skrivUtOversikt() {
     System.out.println("Fullstendig oversikt over pasienter, leger, legemidler og resepter:" + "\n");
     System.out.println("Pasienter:");
     for (Pasient pasient : pasienter) {
       System.out.println(pasient);
       System.out.println("\nPasientenes resepter: ");
       for (Resept r : pasient.hentUtResepter()) {
         System.out.println(r);
       }
     }

     System.out.println("Legemidler:");
     for (Legemiddel lm : legemidler) {
       System.out.println(lm);
     }


     System.out.println("Leger:");
     for (Lege lege : leger) {
       System.out.println(lege);
     }
   }


//E5
    
  public void skrivUtResept(){ //Dersom koden ikke funker --> HashMap
     int stoerrelse = 0;
     //Så lenge størrelse i listen er mindre en teller print tall
      System.out.println("Hvem vil du se resepter for?");
      //Bruker en for-løkke som går gjennom listen og printer ut pasienten + fnr
      while ( stoerrelse < pasienter.stoerrelse()){
        for (Pasient e: pasienter){
          System.out.println(stoerrelse+":" + e);
}
  stoerrelse++;
}

    Scanner scan = new Scanner(System.in);//1
    int a = scan.nextInt();//a=1
    System.out.println("Valgt pasient:" + pasienter.hent(a));
    System.out.println("Hvilken resept vil du bruke?");
    int telle = 0;
    while (telle < resepter.stoerrelse()){
      for (Pasient e: resepter){
        System.out.println(telle +":" + e);
      }
    telle++;
    }

    //Tom reit
    int b = scan.nextInt();
    if(resepter.hent(b).hentReit()==0){
      System.out.println("Kunne ikke bruke paa" + resepter.hent(b) + "(ingen gjenvaerende reit.)");
      this.meny();

    }

    else{
      resepter.hent(b).bruk();
      System.out.println("Brukte resept paa" + resepter.hent(b)+". Antall gjenvaerende reit:" +  resepter.hent(b).hentReit());
      this.meny();

    }

}
  
  //E6
  public void statistikk(){
    int antVane = 0;
    int antNarko = 0;

    for (Lege l : leger){
      for (Resept r : l.hentUtResepter()){
        resepter.leggTil(r);
      }
    }

    for(int i = 0; i < resepter.stoerrelse(); i++){
      if (resepter.hent(i).hentLegemiddel() instanceof Vanedannende){
        antVane += 1;
      }
      else if (resepter.hent(i).hentLegemiddel() instanceof Narkotisk) {
        antNarko += 1;
      }
    }

    System.out.println(resepter);

    System.out.println("Totalt antall utskrevne resepter paa vanedannende legemidler: " + antVane);
    System.out.println("Totalt antall utskrevne resepter paa narkotiske legemidler: " + antNarko);

    System.out.println("\nStatistikk over utskrevne resepter paa narkotiske resepter \n");

    for(Lege l : leger){
      int antNarkotisk = 0;
      for(Resept r : l.hentUtResepter()){
        if (r.hentLegemiddel() instanceof Narkotisk){
          antNarkotisk +=1;
        }
      }
      if (antNarkotisk > 0){
        System.out.println(l + "\n" + "Antall utskrevne resepter paa narkotiske legemidler:" + antNarkotisk +"\n");
      }
    }
  }
}
