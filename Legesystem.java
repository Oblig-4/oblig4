import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;
import java.io.PrintWriter;

class Legesystem {
  private Lenkeliste<Pasient> pasienter;
  private SortertLenkeliste<Lege> leger;
  private Lenkeliste<Legemiddel> legemidler;
  private Lenkeliste<Resept> resepter;

  public Legesystem(File filnavn) throws FileNotFoundException, UlovligUtskrift {
    pasienter = new Lenkeliste<Pasient>();
    legemidler = new Lenkeliste<Legemiddel>();
    leger = new SortertLenkeliste<Lege>();
    resepter = new Lenkeliste<Resept>();

    lesFraFil(filnavn);
    hovedmeny();
  }

  public void lesFraFil(File filnavn) throws FileNotFoundException, UlovligUtskrift, NumberFormatException, NoSuchElementException {
    Scanner fil = null;

    //forsøker å opprette en ny fil
    try{
      fil = new Scanner(filnavn);
    }
    //Dersom filen ikke finnes, tar vi imot en FileNotFoundException som håndterer problemet ved å gå avslutte programet.
    catch (FileNotFoundException e) {
      System.out.println("Fant ikke filen, gaar tilbake til hovedmeny");
      System.exit(1);
      /*exit() metoden vil avslutte programmet avhengig av parameteren
      0: hvis success full termination, positivt heltall: avslutter programmet med vilje, negativt heltall: et problem man ikke er klar over*/
    }
    String a = fil.nextLine();

    //lager liste med pasienter
    while (fil.hasNextLine()) {
      String linje = fil.nextLine();
      while (linje.charAt(0) != '#') { //charAt(int index) returnerer char verdi paa den gitt indexen. så lenge det første tegnet ikke er #, så leser vi inn data fra filen.
        String[] data = linje.split(",");
        String pasientNavn = data[0];
        String fodselsnummer = data[1];
        Pasient pasient = new Pasient(pasientNavn, fodselsnummer); //oppretter pasient med gitt data fra filen.
        pasienter.leggTil(pasient); //legger til pasienten i listen pasienter
        linje = fil.nextLine(); //videre til neste linje
      }
      break;
    }

    //lager liste med legemidler paa tilsvarende måte
    while (fil.hasNextLine()) {
      String linje = fil.nextLine();
      while (linje.charAt(0) != '#') {
        String[] data = linje.split(",");

        try {
          String navn = data[0];
          String type = data[1];
          double pris = Double.parseDouble(data[2]);
          double virkestoff = Double.parseDouble(data[3]);
          int styrke = 0;
          linje = fil.nextLine();
          //sjekker om legemiddelet har en styrkeverdi eller ikke.
          if (data.length == 5) {
            styrke = Integer.parseInt(data[4]);
          }

          //finner riktig type:
          if (type.equals("vanlig")) {
            Vanlig vanlig = new Vanlig(navn, pris, virkestoff);
            legemidler.leggTil(vanlig);
          }
          else if (type.equals("narkotisk")) {
            Narkotisk narko = new Narkotisk(navn, pris, virkestoff, styrke);
            legemidler.leggTil(narko);
          }
          else if (type.equals("vanedannende")) {
            Vanedannende vane = new Vanedannende(navn, pris, virkestoff, styrke);
            legemidler.leggTil(vane);
          }
        }

        //Dersom data fra fil ikke tilsvarer de oppgitte typene, saa tar vi imot NumberFormatExceptions som haandterer problemet ved aa gaa videre til neste linje.
        catch(NumberFormatException f) {
          linje = fil.nextLine();
        }
      }
      break;
    }

    //lager en sortert liste med leger fra filen.
    while (fil.hasNextLine()) {
      String linje = fil.nextLine();
      while (linje.charAt(0) != '#') {
        String[] data = linje.split(",");
        try {
          String navn = data[0];
          int id = Integer.parseInt(data[1]);
          linje = fil.nextLine();
          if (id == 0){
            Lege l = new Lege(navn);
            leger.leggTil(l);
          }
          else {
            Spesialist s = new Spesialist(navn, id);
            leger.leggTil(s);
          }
        }
        catch (NumberFormatException f) {
          linje = fil.nextLine();
        }
      }
      break;
    }

    //lager liste med resepter
    while (fil.hasNextLine()) {
      String linje = fil.nextLine();
      while (linje.charAt(0) != '#') {
        String[] data = linje.split(",");
        try {
          int id = Integer.parseInt(data[0]);
          String navn = data[1];
          int pId = Integer.parseInt(data[2]);
          String farge = data[3];
          int reit = 0;
          if (data.length == 5) {
            reit = Integer.parseInt(data[4]);
          }
          Lege leg = null;
          Pasient pas = null;
          Legemiddel lm = null;

          //Dersom den oppgitte pasientid-en er mindre enn antall pasienter, og tilsvarede for legemidler så skal vi:
          if (pId < pasienter.stoerrelse() && id < legemidler.stoerrelse()) {
            //finner riktig lege
            for (int i=0; i < leger.stoerrelse(); i++) {
              if (leger.hent(i).hentNavn().equals(navn)) {
                leg = leger.hent(i);
              }
            }
            //finner riktig pasient
            for (Pasient p : pasienter) {
              if (p.hentPasientId() == pId) {
                pas = p;
              }
            }

              //finner riktig legemiddel
            for (Legemiddel legem : legemidler) {
              if (legem.hentId() == id) {
                //finner riktig type
                if (legem instanceof Vanlig) {
                  lm = legem;
                }

                else if (legem instanceof Vanedannende) {
                  lm = legem;
                }

                else if (legem instanceof Narkotisk) {
                  lm = legem;
                }
              }
            }

            //finner riktig "farge"
            if (farge.equals("p")) {
              try {
              PResept p = leg.skrivPResept(lm, pas); //oppretter P-resept med riktig, lege, pasient, legemiddel.
              pas.leggTilResept(p); //legger til resepten i pasientens liste med resepter
              resepter.leggTil(p); //legger til resepten i liste med alle resepter.
              }
              catch(UlovligUtskrift e){} //tar imot ulovligutskrift dersom legen ikke har lov til å skrive resepten.
            }
            else if (farge.equals("hvit")) {
              try {
                HvitResept h = leg.skrivHvitResept(lm, pas, reit);
                pas.leggTilResept(h);
                resepter.leggTil(h);
              }
              catch(UlovligUtskrift e){}
            }
            else if (farge.equals("militaer")){
              try {
                MilitaerResept m = leg.skrivMilitaerResept(lm, pas, reit);
                pas.leggTilResept(m);
                resepter.leggTil(m);
              }
              catch(UlovligUtskrift e){}
            }
            else if (farge.equals("blaa")) {
              try {
                BlaaResept b = leg.skrivBlaaResept(lm, pas, reit);
                pas.leggTilResept(b);
                resepter.leggTil(b);
              }
              catch(UlovligUtskrift e){}
            }
          }// slutt paa if
        try {
          linje = fil.nextLine(); //tester om den fortsetter til neste linje
        }
        catch (NoSuchElementException f) { //tar imot en NoSuchElementException som avslutter løkken
          break;
        }
      }
      catch (NumberFormatException f) {
        linje = fil.nextLine();
      }
    }
    break;
    }
    fil.close(); //lukker filen
  }

  //Brukeren faar presentert en kommandolokke som kjorer frem til brukeren velger aa avslutte programmet.
  public void meny() {
    System.out.println("Meny:" + "\n");
    System.out.println("0: Avslutt programmet");
    System.out.println("1: Skriv ut en fullstendig oversikt over pasienter, leger, legemidler og resepter");
    System.out.println("2: Opprett og legg til nye elementer i systemet");
    System.out.println("3: Bruk en gitt resept fra listen til en pasient");
    System.out.println("4: Skriv ut forskjellige former for statistikk");
    System.out.println("5: Skriv alle data til fil");
  }

  public void hovedmeny() throws InputMismatchException, UlovligUtskrift {
    meny(); //Kaller paa menyen.
    Scanner sc = new Scanner(System.in);
    int svar = sc.nextInt();

    //While-lokken gaar gjennom svaret til brukeren og gaar til den valgte kommandoen.
		//Dersom brukeren skriver noe annet enn et tall, blir det regnet som en ugyldig input.
		//Da gaar programmet tilbake til hovedmenyen
    while (svar != 0) {
      try  {
        if (svar == 1) {
          skrivUtOversikt();
        }
      }
      catch(InputMismatchException e){ //Dersom brukeren skriver en string eller et ugyldig tall, blir forsoket kastet.
        System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
        hovedmeny();
      }

      try {
        if (svar == 2) {
          leggTilElement();
        }
      }
      catch(InputMismatchException e){
        System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
        hovedmeny();
      }

      try {
        if (svar == 3) {
          brukResept();
        }
      }
      catch(InputMismatchException e){
        System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
        hovedmeny();
      }

      try {
        if (svar == 4) {
          statistikk();
        }
      }
      catch(InputMismatchException e) {
        System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
        hovedmeny();
      }

      try {
        if (svar == 5) {
          skrivDataTilFil();
        }
      }
      catch(FileNotFoundException e) {
        System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
        hovedmeny();
      }
      //Dersom brukeren skriver noe annet enn et tall fra 1-5, blir det regnet som en ugyldig input.
      if (svar != 1 && svar != 2 && svar != 3 && svar != 4 && svar != 5) {
        System.out.println("Ugyldig input, prov igjen");
        hovedmeny();
      }
      //Brukeren blir spurt om den onsker aa fortsette. Scanneren tar inn svaret.
      System.out.println("Vil du fortsette (ja/nei)?");
      Scanner nysc  = new Scanner(System.in);
      String svar2 = nysc.next();
      //Dersom brukeren svarer "ja" til aa fortsette, blir gaar programmet til hovedmenyen
      try {
        if (svar2.equals("ja")) {
          hovedmeny();
        } //Eller hvis brukeren svarer "nei", avsluttes programmet.
        else if (svar2.equals("nei")) {
          System.out.println("Programmet avsluttes");
          System.exit(1);
        }
        else { //Dersom brukeren skriver inn noe annet enn "ja/nei", gaar programmet tilbake til hovedmenyen.
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
          hovedmeny();
        }
      }
      catch(InputMismatchException e) {
        System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
        hovedmeny();
      }
    }
    System.out.println("Du tastet 0, programmet avsluttes.");
    System.exit(1); //Programmet avsluttes.
  }

  // Denne metoden skriver ut en ryddig oversikt over alle elementer i legesystemet.
  public void skrivUtOversikt() {
    System.out.println("Fullstendig oversikt over pasienter, leger, legemidler og resepter:" + "\n");

    System.out.println("PASIENTER: \n");
    // For-lokke som går gjennom listen "pasienter" og skriver ut hver pasient. Da går programmet til toString()-metoden i pasient-klassen og skriver ut informasjon.
    for (Pasient pasient : pasienter) {
      System.out.println(pasient + "\n");
      // Sjekker om den aktuelle pasientens liste med resepter er tom. Hvis den ikke er tom, vil pasientens resepter skrives ut til terminalen.
      if (pasient.hentUtResepter().stoerrelse() != 0) {
        System.out.println("Pasientens resepter:" + "\n");
        for (Resept r : pasient.hentUtResepter()) {
          System.out.println(r + "\n");
        }
      } else {
        System.out.println("Pasienten har ingen resepter\n");
      }
    }

    // For-lokke som går gjennom listen "legemidler" og skriver ut hvert legemiddel. Da går programmet til toString()-metoden i legemiddel-klassen og skriver ut informasjon.
    System.out.println("LEGEMIDLER: \n");
    for (Legemiddel lm : legemidler) {
       System.out.println(lm + "\n");
    }

    // For-lokke som går gjennom listen "leger" og skriver ut hver lege. Da går programmet til toString()-metoden i lege-klassen og skriver ut informasjon.
    System.out.println("\nLEGER: \n");
    for (Lege lege : leger) {
      System.out.println(lege + "\n");
    }
  }

  // Denne metoden legger til et element i legesystemet.
  public void leggTilElement() throws InputMismatchException, UlovligUtskrift {
    System.out.println("Hva vil du legge til i systemet? (lege/pasient/resept/legemiddel)");
    Scanner input = new Scanner(System.in);
    String svar = input.nextLine();

    // Hvis brukerinput er lege, legges det til en lege i systemet.
    if (svar.equals("lege")) {
      System.out.println("Oppgi navn paa legen:");
      String navnLege = input.nextLine();

      // Sjekker om legens navn er en gyldig input.
      // For-lokke som går itererer gjennom alle tegnene i legens navn, og tilordner hvert tegn i variabelen "c".
      for (int i = 0; i < navnLege.length(); i++) {
          char c = navnLege.charAt(i);

          // Sjekker om c er et tall. Da er navnet en ugyldig input, og systemet gaar tilbake til hovedmeny.
          if (Character.isDigit(c)) {
              System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
              hovedmeny();
          }
      }

      System.out.println("Oppgi kontrollid (0 hvis vanlig lege):");
      // Sjekker om kontrollid er gyldig input (er positiv).
      int kontrollid = 0;
      try {
        int inp = input.nextInt();
        if (inp > 0) {
          kontrollid = inp;
        } else {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        // Dersom kontrollid er 0, legges det til en lege i systemet.
        // Oppretter en ny lege som legges til i listen "leger".
        if (kontrollid == 0) {
          Lege lege = new Lege(navnLege);
          leger.leggTil(lege);
          System.out.println("Legen " + lege.hentNavn() + " har blitt lagt til i systemet.");
        }

        // Hvis ikke kontrollid er 0, legges det til en spesialist i systemet.
        // Oppretter en ny spesialist som legges til i listen "leger".
        else {
          Spesialist sp = new Spesialist(navnLege, kontrollid);
          leger.leggTil(sp);
          System.out.println("Spesialisten " + sp.hentNavn() + " har blitt lagt til i systemet.");
        }
      }
      // Dersom brukeren skriver en String for kontrollid og ikke int, tar vi i mot en NumberFormatException som haandterer dette problemet ved aa gaa tilbake til hovedmeny.
      catch (NumberFormatException e) {
        System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
        hovedmeny();
      }
    }

    // Hvis brukerinput er pasient, legges det til en pasient i systemet.
    else if (svar.equals("pasient")) {
      String navnPasient = "";
      String fodselsnr = "";

      System.out.println("Oppgi navn paa pasient:");

      try {
        navnPasient = input.nextLine();

        // Sjekker om pasientens navn er en gyldig input.
        for (int i = 0; i < navnPasient.length(); i++) {
          char c = navnPasient.charAt(i);

          if (Character.isDigit(c)) {
              System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
              hovedmeny();
          }
        }
      }
      // Dersom brukeren ikke skriver et navn av typen String, tar vi i mot en InputMismatchException som haandterer problemet ved aa gaa tilbake til hovedmeny.
      catch (InputMismatchException e) {
        System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
        hovedmeny();
      }

      System.out.println("Oppgi fodselsnummer til pasient:");

      try {
        fodselsnr = input.nextLine();

        // Sjekker om pasientens fodselsnummer er en gyldig input.
        // For-lokke som går itererer gjennom alle tegnene i pasientens fodselsnummer, og tilordner hvert tegn i variabelen "c".
        for (int i = 0; i < navnPasient.length(); i++) {
          char c = navnPasient.charAt(i);

          // Sjekker om c er en bokstav. Da er fodselsnummeret en ugyldig input, og systemet gaar tilbake til hovedmeny.
          if (Character.isLetter(c)) {
              System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
              hovedmeny();
          }
        }
      } catch (InputMismatchException e) {
        System.out.println("Ugydlig input, gaar tilbake til hovedmeny.");
        hovedmeny();
      }

      // Oppretter en ny pasient, og legger pasienten til i listen "pasienter".
      Pasient pasient = new Pasient(navnPasient, fodselsnr);
      pasienter.leggTil(pasient);
      System.out.println("Pasienten " + pasient.hentPasientensNavn() + " har blitt lagt til i systemet.");
    }

    // Hvis brukerinput er resept, legges det til en resept i systemet.
    else if (svar.equals("resept")) {
      System.out.println("Oppgi type resept (hvit/blaa/militaer/p):");
      String typeResept = input.nextLine();

      // Legger til en hvit resept.
      if (typeResept.equals("hvit")) {
        // Deklarerer de nodvendige variablene for å opprette en hvit resept.
        Legemiddel legem = null;
        Lege lege = null;
        Pasient pas = null;
        int reit = 0;

        System.out.println("Oppgi legemiddelid:");

        try {
          int legemId = input.nextInt();

          // For-lokke som itererer gjennom listen "legemidler", for å prøve å finne legemiddelet som tilhører legemiddelid fra brukerinput.
          // Dersom det ønskede legemiddelet finnes i listen, tilordnes legemiddelet til variabelen "legem".
          for (Legemiddel lm : legemidler) {
            if (lm.hentId() == legemId) {
              legem = lm;
            }
          }
        } catch (NumberFormatException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        // Dersom legemiddelet ikke finnes i listen, skrives det ut en feilmelding og systemet går tilbake til hovedmeny.
        if (legem == null) {
          System.out.println("Fant ikke legemiddelet, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        System.out.println("Oppgi navnet paa utskrivende lege:");
        Scanner nyInput = new Scanner(System.in);

        try {
          String navnLege = nyInput.nextLine();

          // For-lokke som itererer gjennom listen "leger", for å prøve å finne legen som tilhører legenavnet fra brukerinput.
          // Dersom den ønskede legen finnes i listen, tilordnes legen til variabelen "lege".
          for (Lege l : leger) {
            if (l.hentNavn().equals(navnLege)) {
              lege = l;
            }
          }
        } catch (InputMismatchException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        // Dersom legen ikke finnes i listen, skrives det ut en feilmelding og systemet går tilbake til hovedmeny.
        if (lege == null) {
          System.out.println("Fant ikke legen, sjekk stavelse. Prov igjen.");
          leggTilElement();
        }

        System.out.println("Oppgi pasientid:");

        try {
          int pasientId = input.nextInt();

          // For-lokke som itererer gjennom listen "pasienter", for å prøve å finne pasienten som tilhører pasientid fra brukerinput.
          // Dersom den ønskede pasienten finnes i listen, tilordnes pasienten til variabelen "pas".
          for (Pasient p : pasienter) {
            if (p.hentPasientId() == pasientId) {
              pas = p;
            }
          }
        } catch (NumberFormatException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        // Dersom pasienten ikke finnes i listen, skrives det ut en feilmelding og systemet går tilbake til hovedmeny.
        if (pas == null){
          System.out.println("Fant ikke pasienten, sjekk stavelse. Prov igjen.");
          leggTilElement();
        }

        System.out.println("Oppgi antall reit:");

        // Sjekker om reiten er gyldig input.
        try {
          // Sjekker om reiten som brukeren oppgir er positiv. Da er det gyldig input, og brukerinputet blir tilordnet variabelen "reit".
          int inp = input.nextInt();

          if (inp > 0) {
            reit = inp;
          } else {
            System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
            hovedmeny();
          }
        } catch (NumberFormatException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        // Prøver å opprette hvit resept med skrivHvitResept() metoden fra lege-klassen.
        // Prøver å legge til resepten i den aktuelle pasientens liste med resepter, og i listen "resepter" i systemet.
        // Dersom legemiddelet er narkotisk og legen ikke er en spesialist, kan ikke legen skrive ut resepten og vi tar i mot unntaket UlovligUtskrift.
        try {
          Resept hvitResept = lege.skrivHvitResept(legem, pas, reit);
          pas.hentUtResepter().leggTil(hvitResept);
          resepter.leggTil(hvitResept);
        } catch (UlovligUtskrift e) {
          System.out.println("Hvit resept kan ikke bli lagt til i systemet, gaar tilbake til hovedmeny.");
          hovedmeny();
        }
        System.out.println("Hvit resept har blitt lagt til i systemet.");
      }

      // Legger til en blaa resept (på tilsvarende vis som hvit resept).
      else if (typeResept.equals("blaa")) {
        Legemiddel legem = null;
        Lege lege = null;
        Pasient pas = null;
        int reit = 0;

        System.out.println("Oppgi legemiddelid:");

        try {
          int legemId = input.nextInt();

          for (Legemiddel lm : legemidler) {
            if (lm.hentId() == legemId) {
              legem = lm;
            }
          }
        } catch (NumberFormatException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        if (legem == null) {
          System.out.println("Fant ikke legemiddelet, sjekk stavelse. Prov igjen.");
          leggTilElement();
        }

        System.out.println("Oppgi navnet paa utskrivende lege:");
        Scanner nyInput = new Scanner(System.in);

        try {
          String navnLege = nyInput.nextLine();

          for (Lege l : leger) {
            if (l.hentNavn().equals(navnLege)) {
              lege = l;
            }
          }
        } catch (InputMismatchException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        if (lege == null) {
          System.out.println("Fant ikke legen, sjekk stavelse. Prov igjen.");
          leggTilElement();
        }

        System.out.println("Oppgi pasientid:");

        try {
          int pasientId = input.nextInt();

          for (Pasient p : pasienter) {
            if (p.hentPasientId() == pasientId) {
              pas = p;
            }
          }
        } catch (NumberFormatException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        if (pas == null) {
          System.out.println("Fant ikke pasienten, sjekk stavelse. Prov igjen.");
          leggTilElement();
        }

        System.out.println("Oppgi antall reit:");

        try {
          int inp = input.nextInt();

          if (inp > 0) {
            reit = inp;
          } else {
            System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
            hovedmeny();
          }
        } catch (NumberFormatException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        // Prøver å opprette blaa resept med skrivBlaaResept() metoden fra lege-klassen.
        // Prøver å legge til resepten i den aktuelle pasientens liste med resepter, og i listen "resepter" i systemet.
        try {
          Resept blaaResept = lege.skrivBlaaResept(legem, pas, reit);
          pas.hentUtResepter().leggTil(blaaResept);
          resepter.leggTil(blaaResept);
        } catch (UlovligUtskrift e) {
          System.out.println("Blaa resept kan ikke bli lagt til i systemet, gaar tilbake til hovedmeny.");
          hovedmeny();
        }
        System.out.println("Blaa resept har blitt lagt til i systemet.");
      }

      // Legger til en militaerresept (på tilsvarende vis som de foregående resept-typene).
      else if (typeResept.equals("militaer")) {
        Legemiddel legem = null;
        Lege lege = null;
        Pasient pas = null;
        int reit = 0;

        System.out.println("Oppgi legemiddelid:");

        try {
          int legemId = input.nextInt();

          for (Legemiddel lm : legemidler) {
            if (lm.hentId() == legemId) {
              legem = lm;
            }
          }
        } catch (NumberFormatException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        if (legem == null) {
          System.out.println("Fant ikke legemiddelet, sjekk stavelse. Prov igjen.");
          leggTilElement();
        }

        System.out.println("Oppgi navnet paa utskrivende lege:");
        Scanner nyInput = new Scanner(System.in);

        try {
          String navnLege = nyInput.nextLine();

          for (Lege l : leger) {
            if (l.hentNavn().equals(navnLege)) {
              lege = l;
            }
          }
        } catch (InputMismatchException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        if (lege == null) {
          System.out.println("Fant ikke legen, sjekk stavelse. Prov igjen.");
          leggTilElement();
        }

        System.out.println("Oppgi pasientid:");

        try {
          int pasientId = input.nextInt();

          for (Pasient p : pasienter) {
            if (p.hentPasientId() == pasientId) {
              pas = p;
            }
          }
        } catch (NumberFormatException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        if (pas == null) {
          System.out.println("Fant ikke pasienten, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        System.out.println("Oppgi antall reit:");

        try {
          int inp = input.nextInt();

          if (inp > 0) {
            reit = inp;
          } else {
            System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
            hovedmeny();
          }
        } catch (NumberFormatException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        // Prøver å opprette militaerresept med skrivMilitaerResept() metoden fra lege-klassen.
        // Prøver å legge til resepten i den aktuelle pasientens liste med resepter, og i listen "resepter" i systemet.
        try {
          Resept militaerResept = lege.skrivMilitaerResept(legem, pas, reit);
          pas.hentUtResepter().leggTil(militaerResept);
          resepter.leggTil(militaerResept);
        } catch (UlovligUtskrift e) {
          System.out.println("Militaerresept kan ikke bli lagt til i systemet, gaar tilbake til hovedmeny.");
          hovedmeny();
        }
        System.out.println("Militaerresept har blitt lagt til i systemet.");
      }

      // Legger til en presept (på tilsvarende vis som de foregående resept-typene, men uten reit).
      else if (typeResept.equals("p")) {
        Legemiddel legem = null;
        Lege lege = null;
        Pasient pas = null;

        System.out.println("Oppgi legemiddelid:");

        try {
          int legemId = input.nextInt();

          for (Legemiddel lm : legemidler) {
            if (lm.hentId() == legemId) {
              legem = lm;
            }
          }
        } catch (NumberFormatException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        if (legem == null) {
          System.out.println("Fant ikke legemiddelet, sjekk stavelse. Prov igjen.");
          leggTilElement();
        }

        System.out.println("Oppgi navnet paa utskrivende lege:");
        Scanner nyInput = new Scanner(System.in);

        try {
          String navnLege = nyInput.nextLine();

          for (Lege l : leger) {
            if (l.hentNavn().equals(navnLege)) {
              lege = l;
            }
          }
        } catch (InputMismatchException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        if (lege == null) {
          System.out.println("Fant ikke legen, sjekk stavelse. Prov igjen.");
          leggTilElement();
        }

        System.out.println("Oppgi pasientid:");

        try {
          int pasientId = input.nextInt();

          for (Pasient p : pasienter) {
            if (p.hentPasientId() == pasientId) {
              pas = p;
            }
          }
        } catch (NumberFormatException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        if (pas == null) {
          System.out.println("Fant ikke pasienten, sjekk stavelse. Prov igjen.");
          leggTilElement();
        }

        // Prøver å opprette presept med skrivPResept() metoden fra lege-klassen.
        // Prøver å legge til resepten i den aktuelle pasientens liste med resepter, og i listen "resepter" i systemet.
        try {
          Resept presept = lege.skrivPResept(legem, pas);
          pas.hentUtResepter().leggTil(presept);
          resepter.leggTil(presept);
        } catch (UlovligUtskrift e) {
          System.out.println("Presept kan ikke bli lagt til i systemet, gaar tilbake til hovedmeny.");
          hovedmeny();
        }
        System.out.println("Presept har blitt lagt til i systemet.");
      }

      // Sjekker om brukerinput på type resept ikke er lik gyldig input (hvit/blaa/militaer/p). Da går systemet tilbake til hovedmeny.
      if (!typeResept.equals("hvit") && !typeResept.equals("blaa") && !typeResept.equals("militaer") && !typeResept.equals("p")) {
        System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
        hovedmeny();
      }
    }

    // Hvis brukerinput er legemiddel, legges det til et legemiddel i systemet.
    else if (svar.equals("legemiddel")) {
      System.out.println("Oppgi type legemiddel (narkotisk/vanedannende/vanlig):");
      String typeLegem = input.nextLine();

      // Legger til et narkotisk legemiddel.
      if (typeLegem.equals("narkotisk")) {
        // Deklarerer de nodvendige variablene for å opprette et narkotisk legemiddel.
        String navn = "";
        double pris = 0;
        double virkestoff = 0;
        int styrke = 0;

        System.out.println("Oppgi navn paa legemiddel:");

        try {
          navn = input.nextLine();

          // Sjekker om navn på legemiddel er er en gyldig input.
          for (int i = 0; i < navn.length(); i++) {
            char c = navn.charAt(i);

            if (Character.isDigit(c)) {
                System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
                hovedmeny();
            }
          }
        } catch (InputMismatchException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        System.out.println("Oppgi pris:");

        // Sjekker om prisen er gyldig input.
        try {
          double inp = input.nextDouble();
          if (inp > 0) {
            pris = inp;
          }
          else {
            System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
            hovedmeny();
          }
        } catch (NumberFormatException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        System.out.println("Oppgi mg virkestoff:");

        // Sjekker om virkestoffet er gyldig input.
        try {
          double inp = input.nextDouble();
          if (inp > 0) {
            virkestoff = inp;
          }
          else {
            System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
            hovedmeny();
          }
        } catch (NumberFormatException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        System.out.println("Oppgi styrke:");

        // Sjekker om styrken er gyldig input.
        try {
          int inp = input.nextInt();
          if (inp > 0) {
            styrke = inp;
          }
          else {
            System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
            hovedmeny();
          }
        } catch (NumberFormatException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
        }

        // Oppretter et narkotisk legemiddel, og legger det til i listen "legemidler".
        Narkotisk narko = new Narkotisk(navn, pris, virkestoff, styrke);
        legemidler.leggTil(narko);
        System.out.println("Narkotisk legemiddel har blitt lagt til i systemet.");
      }

      // Legger til et vanedannende legemiddel.
      else if (typeLegem.equals("vanedannende")) {
        String navn = "";
        double pris = 0;
        double virkestoff = 0;
        int styrke = 0;

        System.out.println("Oppgi navn paa legemiddel:");

        try {
          navn = input.nextLine();
        } catch (InputMismatchException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        System.out.println("Oppgi pris:");

        try {
          double inp = input.nextDouble();
          if (inp > 0) {
            pris = inp;
          }
          else {
            System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
            hovedmeny();
          }
        } catch (NumberFormatException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        System.out.println("Oppgi mg virkestoff:");

        try {
          double inp = input.nextDouble();
          if (inp > 0) {
            virkestoff = inp;
          }
          else {
            System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
            hovedmeny();
          }
        } catch (NumberFormatException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        System.out.println("Oppgi styrke:");

        try {
          int inp = input.nextInt();
          if (inp > 0) {
            styrke = inp;
          }
          else {
            System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
            hovedmeny();
          }
        } catch (NumberFormatException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
        }

        // Oppretter et vanedannende legemiddel, og legger det til i listen "legemidler".
        Vanedannende vanedan = new Vanedannende(navn, pris, virkestoff, styrke);
        legemidler.leggTil(vanedan);
        System.out.println("Vanendannende legemiddel har blitt lagt til i systemet.");
      }

      // Legger til et vanlig legemiddel.
      else if (typeLegem.equals("vanlig")) {
        String navn = "";
        double pris = 0;
        double virkestoff = 0;

        System.out.println("Oppgi navn paa legemiddel:");

        try {
          navn = input.nextLine();
        } catch (InputMismatchException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        System.out.println("Oppgi pris:");

        try {
          double inp = input.nextDouble();
          if (inp > 0) {
            pris = inp;
          }
          else {
            System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
            hovedmeny();
          }
        } catch (NumberFormatException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        System.out.println("Oppgi mg virkestoff:");

        try {
          double inp = input.nextDouble();
          if (inp > 0) {
            virkestoff = inp;
          }
          else {
            System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
            hovedmeny();
          }
        } catch (NumberFormatException e) {
          System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
          hovedmeny();
        }

        // Oppretter et vanlig legemiddel, og legger det til i listen "legemidler".
        Vanlig van = new Vanlig(navn, pris, virkestoff);
        legemidler.leggTil(van);
        System.out.println("Vanlig legemiddel har blitt lagt til i systemet.");
      }

      // Sjekker om brukerinput på type legemiddel ikke er lik gyldig input (narkotisk/vanedannende/vanlig). Da går systemet tilbake til hovedmeny.
      if (!typeLegem.equals("narkotisk") && !typeLegem.equals("vanedannende") && !typeLegem.equals("vanlig")) {
        System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
        hovedmeny();
      }
    }

    // Sjekker om brukerinput på type element ikke er lik gyldig input (lege/pasient/resept/legemiddel). Da går systemet tilbake til hovedmeny.
    if (!svar.equals("lege") && !svar.equals("pasient") && !svar.equals("resept") && !svar.equals("legemiddel")) {
      System.out.println("Ugyldig input, gaar tilbake til hovedmeny");
      hovedmeny();
    }
  }

  // Metoden "brukResept()", gir brukeren mulighet for aa bruke en resept.
  // Dersom brukeren taster inn noe ugyldig, blir forsoket kastet.
  public void brukResept() throws UgyldigListeIndeks, UlovligUtskrift {
    try {
      Scanner scan = new Scanner(System.in); // Scanneren gjor det mulig for brukeren aa skrive inn tallet.
      int teller = 0;
      System.out.println("Hvem vil du se resepter for?\n"); // Brukeren faar opp en liste med pasienter.
      while (teller < pasienter.stoerrelse()) { // While-lokken gjor at det er mulig for brukeren aa velge en pasient ved aa skrive inn et tall.
        for (Pasient e: pasienter) { // For-lokken skriver ut alle pasientene i listen.
          System.out.println(teller+": " + e.hentPasientensNavn() + " (fnr " + e.hentFodselsnummer() + ")");
          teller ++;
        }
      }

      int pasient = scan.nextInt();

      System.out.println("Valgt pasient: " + pasienter.hent(pasient).hentPasientensNavn());

      Lenkeliste<Resept> reseptListe = pasienter.hent(pasient).hentUtResepter();

      if (reseptListe.stoerrelse() == 0) {
        System.out.println("Pasienten har ingen resepter, gaar tilbake til hovedmeny.");
        hovedmeny();
      }

      // Brukeren faar velge resepten til pasienten, som skal brukes.
      int teller2 = 0;
      System.out.println("\nHvilken resept vil du bruke\n");
      while (teller2 < reseptListe.stoerrelse()) { // Brukeren faar velge hvilken resept som skal brukes ved aa skrive inn tallet til resepten.
        for (Resept r : reseptListe) { // For-lokken gaar gjennom reseptene til pasienten, og skriver ut legemidlene og antall reit.
          System.out.println(teller2 +": " + r.hentLegemiddel().hentNavn() + " (Reit: " + r.hentReit() + ")");
          teller2++;
        }
      }
      int resept = scan.nextInt();
      // Dersom pasienten har ingen reit, faar brukeren en beskjed om det. Programmet gaar tilbake til hovedmenyen.
      if (reseptListe.hent(resept).hentReit() == 0) {
        System.out.println("Kunne ikke bruke resept paa " + reseptListe.hent(resept).hentLegemiddel().hentNavn() + " (ingen gjenvaerende reit.)");
        hovedmeny();
      }
      else { // Dersom resepten kan brukes, blir antall reit trukket ifra.
        reseptListe.hent(resept).bruk();
        System.out.println("Brukte resept paa " + reseptListe.hent(resept).hentLegemiddel().hentNavn() + ". Antall gjenvaerende reit: " + reseptListe.hent(resept).hentReit());
        hovedmeny();
      }
    } catch (UgyldigListeIndeks e) { // Dersom brukeren skriver inn noe ugyldig, gaar programmet tilbake til hovedmenyen.
      System.out.println("Ugyldig input, gaar tilbake til hovedmeny.");
      hovedmeny();
    }
  }

  // Denne metoden skriver ut en statistikk over resepter paa narkotiske og vanedannende.
  public void statistikk(){
    Lenkeliste<Resept> reseptliste = new Lenkeliste<Resept>();
    int antVane = 0;
    int antNarko = 0;

    //iterer gjennom listen leger, og legger til alle de utskrevne reseptene i listen resepter.
    for (Lege l : leger){
      for (Resept r : l.hentUtResepter()){
        reseptliste.leggTil(r);
      }
    }


    //går gjennom listen resepter og legger til antall vanedannende og narkotiske legemidler som den finner.
    for (Resept r : reseptliste) {
      if (r.hentLegemiddel() instanceof Vanedannende) {
        antVane += 1;
      }
      else if (r.hentLegemiddel() instanceof Narkotisk) {
        antNarko += 1;
      }
    }

    System.out.println("STATISTIKK OM ELEMENTENE I SYSTEMET: \n");

    System.out.println("Totalt antall utskrevne resepter paa vanedannende legemidler: " + antVane);
    System.out.println("Totalt antall utskrevne resepter paa narkotiske legemidler: " + antNarko);

    System.out.println("\nLeger med utskrevne resepter paa narkotiske legemidler \n");
    for (Lege l : leger) {
      int antNarkotisk = 0; //antall narkotiske resepter som legen har skrevet ut.
      for (Resept r : l.hentUtResepter()) {
        if (r.hentLegemiddel() instanceof Narkotisk) {
          antNarkotisk +=1;
        }
      }
      if (antNarkotisk > 0) { //skriver ut, kun når antall narkotisk er storre enn 0.
      System.out.println(l + "\n" + "Antall utskrevne resepter paa narkotiske legemidler:" + antNarkotisk +"\n");
      }
    }

    System.out.println("Pasienter med resepterter paa narkotiske legemidler \n");
    for(Pasient p : pasienter){
      int antNarkotisk = 0;
      for(Resept r : p.hentUtResepter()){
        if (r.hentLegemiddel() instanceof Narkotisk){
          antNarkotisk +=1;
        }
      }
      if (antNarkotisk > 0){
      System.out.println(p + "\n" + "Antall utskrevne resepter paa narkotiske legemidler:" + antNarkotisk +"\n");
      }
    }
  }

  //Denne metoden skal skrive alle elementer i det nåværende systemet til fil
  public void skrivDataTilFil() throws FileNotFoundException {
    Scanner input = new Scanner(System.in);
    System.out.println("Oppgi filnavnet du vil skrive til:");
    String filnavn = input.next();
    PrintWriter pw = new PrintWriter(filnavn); //bruker printwriter for å skrive til filen

    //skriver pasientene fra det naavaerende system til den nye filen
    pw.append("# Pasienter (navn, fnr)\n");
    for (Pasient pas : pasienter) {
        pw.append(pas.hentPasientensNavn() + "," + pas.hentFodselsnummer() + "\n"); //alle pasientene blir lagt til i filen
    }

    // Skriver legemidlene fra det naavaerende system til den nye filen
    pw.append("# Legemidler (navn,type,pris,virkestoff,[styrke])\n");
    for (Legemiddel legem : legemidler) {
      if (legem instanceof Narkotisk) { //sjekker om legemiddelet er en instans av Narkotisk
        Narkotisk lm = (Narkotisk) legem; //hvis true, caster legemiddelet til Narkotisk, så vi får tilgang til metodene i klassen Narkotisk
        pw.append(lm.hentNavn() + ",narkotisk," + lm.hentPris() + "," + lm.hentVirkestoff() + "," + lm.hentNarkotiskStyrke() + "\n");
      }
      else if (legem instanceof Vanedannende) { //tilsvarende for vanedannende
        Vanedannende lm = (Vanedannende) legem;
        pw.append(lm.hentNavn() + ",vanedannende," + lm.hentPris() + "," + lm.hentVirkestoff() + "," + lm.hentVanedannendeStyrke() + "\n");
      }
      pw.append(legem.hentNavn() + ",vanlig," + legem.hentPris() + "," + legem.hentVirkestoff() + "\n");
    }

    //skriver legene fra det naavaerende system til den nye filen.
    pw.append("# Leger (navn,kontrollid / 0 hvis vanlig lege)\n");
    for (Lege l : leger) {
      if (l instanceof Spesialist) {
        Spesialist s = (Spesialist) l;
        pw.append(s.hentNavn() + "," + s.hentKontrollID() + "\n");
      }
        pw.append(l.hentNavn() + ",0\n");
    }

    //skriver reseptene fra det naavaerende system til den nye filen
    pw.append("# Resepter (legemiddelNummer,legeNavn,pasientID,type,[reit])\n");
    for (Resept r : resepter) {
      if (r instanceof HvitResept) {
        HvitResept h = (HvitResept) r;
        pw.append(h.hentLegemiddel().hentId() + "," + h.hentLege().hentNavn() + "," + h.hentPasient().hentPasientId() + ",hvit," + h.hentReit() + "\n");
      }
      else if (r instanceof BlaaResept) {
        BlaaResept b = (BlaaResept) r;
        pw.append(b.hentLegemiddel().hentId() + "," + b.hentLege().hentNavn() + "," + b.hentPasient().hentPasientId() + ",blaa," + b.hentReit() + "\n");
      }
      else if (r instanceof MilitaerResept) {
        MilitaerResept m = (MilitaerResept) r;
        pw.append(m.hentLegemiddel().hentId() + "," + m.hentLege().hentNavn() + "," + m.hentPasient().hentPasientId() + ",militaer," + m.hentReit() + "\n");
      }
      pw.append(r.hentLegemiddel().hentId() + "," + r.hentLege().hentNavn() + "," + r.hentPasient().hentPasientId() + ",p\n");
    }

    pw.close(); //lukker filen

    System.out.println("Filen " + filnavn + " er lagt til. All data fra naavaerende system er skrevet ut til filen."); //en avsluttende kommentar til brukeren.
  }
}
