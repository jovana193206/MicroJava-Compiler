# MicroJava-Compiler


– Kompajler za Mikrojavu –


Cilj projektnog zadatka je realizacija kompajlera za programski jezik Mikrojavu. Kompajler
omogućava prevodjenje sintaksno i semantički ispravnih Mikrojava programa u Mikrojava bajtkod
koji se izvršava na virtuelnoj mašini za Mikrojavu. Sintaksno i semantički ispravni Mikrojava
programi su definisani specifikacijom [MJ].
Programski prevodilac za Mikrojavu ima četiri osnovne funkcionalnosti: 
1. leksičku analizu
2. sintaksnu analizu
3. semantičku analizu
4. generisanje koda
Leksički analizator treba da prepoznaje jezičke lekseme i vrati skup tokena izdvojenih iz
izvornog koda, koji se dalje razmatraju u okviru sintaksne analize. Ukoliko se tokom leksičke
analize detektuje leksička greška, potrebno je ispisati odgovarajuću poruku na izlaz.
Sintaksni analizator ima zadatak da utvrdi da li izdvojeni tokeni iz izvornog koda programa
mogu formiraju gramatički ispravne sentence. Tokom parsiranja Mikrojava programa potrebno je
na odgovarajući način omogućiti i praćenje samog procesa parsiranja na način koji će biti u
nastavku dokumenta detaljno opisan. Nakon parsiranja sintaksno ispravnih Mikrojava programa
potrebno je obavestiti korisnika o uspešnosti parsiranja. Ukoliko izvorni kod ima sintaksne greške,
potrebno je izdati adekvatno objašnjenje o detektovanoj sintaksnoj grešci, izvršiti oporavak i
nastaviti parsiranje.
Semantički analizator se formira na osnovu apstraktnog sintaksnog stabla koje je nastalo kao
rezultat sintaksne analize. Semantička analiza se sprovodi implementacijom metoda za posećivanje
čvorova apstraktnog sintaksnog stabla. Stablo je formirano na osnovu gramatike implementirane u
prethodnoj fazi. Ukoliko izvorni kod ima semantičke greške, potrebno je prikazati adekvatnu
poruku o detektovanoj semantičkoj grešci.
Generator koda prevodi sintaksno i semantički ispravne programe u izvršni oblik za
odabrano izvršno okruženje Mikrojava VM. Generisanje koda se implementira na sličan način kao i
semantička analiza, implementacijom metoda koje posećuju čvorove.


Specifikacija zahteva

Leksička analiza:
- Potrebno je realizovati leksički analizator (skener) izvornih programa napisanih na jeziku
Mikrojava.
- Leksički analizator se implementira pisanjem .flex specifikacije
- Specifikacija .flex se transformiše u implementaciju leksera na programskom jeziku Java
korišćenjem alata JFlex.
- Interfejs leksičkog analizatora prema sintaksnom analizatoru mora biti standardni CUP interfejs.
- Skener prihvata fajl za izvornim kodom na jeziku Mikrojava i deli ga na tokene.
- Token se vraća eksplicitnim pozivom leksičkog analizatora (operacija next_token()).
Potrebno je detektovati i obraditi sledeće leksičke strukture:
	– identifikatore,
	– konstante,
	– ključne reči,
	– operatore,
	– komentare.
- Leksičke strukture implementirati prema specifikaciji jezika.
- Leksički analizator treba da preskače komentare i "beline" u tekstu programa.
- Pod "belinama" se smatraju: tabulatori (\t), prelazak u novi red (\r \n), razmak (' '), backspace
(\b), prelazak na novu stranu (\f, form feed).
- U slučaju leksičke greške, ispisuje se greška i nastavlja se obrada teksta programa.
- Poruka o grešci treba da sadrži sledeće informacije:
	– niz znakova koji nije prepoznat,
	– broj linije teksta programa u kojoj se desila greška, i
	– kolonu (poziciju prvog znaka) u kojoj je detektovana greška.


Sintaksna analiza:
Potrebno je napisati LALR(1) gramatiku na osnovu specifikacije jezika i implementirati
sintaksni analizator (parser) za programe napisane na jeziku Mikrojava.
Opšti tehnički zahtevi:
- Gramatika jezika Mikrojava mora biti napisana u skladu sa specifikacijom jezika definisanom u
[MJ].
- Za implementaciju parsera mora se koristiti generator sintaksnih analizatora AST-CUP (u
nastavku teksta: AST-CUP generator). AST-CUP generator je lokalno razvijeno proširenje alata
CUP za rad sa sintaksnim stablima.
- Gramatička specifikacija parsera mora biti napisana u CUP fajlu, u formatu koji AST-CUP
generator prepoznaje (u nastavku teksta: AST-CUP specifikacija).
- Sintaksni analizator mora biti integrisan sa CUP kompatibilnim leksičkim analizatorom za jezik
Mikrojava.
- U slučaju uspešnog parsiranja ulaznog fajla parser na kraju rada na standardnom izlazu
prikazuje apstraktno sintaksno stablo pozivom funkcije toString() nad korenom stabla (videti
primer mini domaćeg).
- Parser treba da omogući oporavak od sintaksnih grešaka za zadate jezičke elemente.
- U slučaju nailaska na sintaksnu grešku parser:
	– ispisuje poruku greške u log fajl,
	– vrši oporavak od greške i
	– nastavlja sa parsiranjem ostatka fajla.
- Opis sintaksne greške TREBA da sadrži:
	– broj linije ulaznog programa u kojoj je greška detektovana,
	– nedvosmislen opis greške.
Implementacija parsera
- Neterminali u AST-CUP specifikaciji moraju biti imenovani na način kako je to propisano
zadatom specifikacijom [MJ] uz eventualno dodavanje sopstvenih neterminale, ukoliko se za
tim ukaže potreba.
- Svakoj produkciji mora se zadati jedinstveni naziv na osnovu kojeg AST-CUP generator
generiše Java klasu koja reprezentuje deo podstabla koji odgovara toj produkciji.
- Na osnovu AST-CUP specifikacije AST-CUP generator proizvodi standardnu CUP
specifikaciju i geneiše klase elemenata sintaksnog stabla.
- Napisati klasu rs.ac.bg.etf.pp1.Compiler na programskom jeziku Java sa funkcijom glavnog
programa main koja pokreće parsiranje Mikrojava programa. U slučaju uspešnog parsiranja,
ispisuje strukturu sintaksnog stabla kako je opisano u zahtevima.
- Putanja do ulaznog fajla sa Mikrojava izvornim kodom prosleđuje se glavnom programu klase
Compiler kao prvi argument komandne linije.
Oporavak od grešaka:
- U AST-CUP specifikaciju gramatike TREBA dodati smene i akcije za oporavak od grešaka.
Implementirati oporavak od grešaka za sledeće jezičke elemente:
– definicija globalne promenljive – ignorisati karaktere do prvog znaka ";" ili sledećeg ","
– konstrukcija iskaza dodele – ignorisati karaktere do ";"
– deklaracija formalnog parametra funkcije – ignorisati znakove do znaka "," ili ")"
– logički izraz unutar if konstrukcije - ignorisati karaktere do prvog znaka ")"
Testiranje rada implementiranog parsera:
- Napisati reprezentativni skup testova sintaksno ispravnih i neispravnih programa i testirati
oporavak od grešaka.


Semantička analiza
U sklopu semantičke analize vrši se ažuriranje tabele simbola i provera kontekstnih uslova
opisanih u [MJ].
Opšti zahtevi:
- Semantička analiza se vrši obilaskom apstraktnog sintaksnog stabla koje je nastalo kao rezultat
sintaksne analize.
- Potrebno je implementirati klasu SemanticAnalyzer koja proširuje automatski generisanu klasu
rs.ac.bg.etf.pp1.ast.VisitorAdapter i u njoj redefinisati metode za obilazak onih čvorova stabla
koji su relevatni za semantičku analizu.
- Semantički obilazak stabla se pokreće u funkciji glavnog programa klase Compiler nakon
završetka sintaksne analize, tako što se objekat klase SemanticAnalyzer prosleđuje korenu
sintaksnog stabla.
- Semantički analizator je potrebno integrisati sa tabelom simbola.
- Tabela simbola se uvezuje sa ostatkom programa kao Java biblioteka (.jar) i dozvoljeno je
koristiti sve njene javne klase, metode i polja.
- Ukoliko postojeća implementacija tabele simbola ne zadovoljava sve zahteve iz date
specifikacije, može se nadograditi ISKLJUČIVO pomoću izvođenja klasa i redefinisanja
postojećih metoda. Tabela simbola ima nekoliko tačaka za proširenja.
- Implementirati javno dostupnu metodu void tsdump() u klasi Compiler za ispis sadržaja tabele
simbola. Metoda mora da se pozove glavnom programu klase Compiler po završetku
semantičkog prolaza..
Detektovanje korišćenja simbola:
- U klasi SemanticAnalyzer implementirati detektovanje upotrebe simbola za sledeće jezičke
elemente:
	– simboličke konstante
	– globalne promenljive
	– lokalne promenljive
	– globalne funkcije (pozivi)
	– pristup elementu niza
	– korišćenje formalnog argumenta funkcije
- Za svaki detektovani simbol potrebno je proveriti sledeće:
	– da li ime postoji u tabeli simbola,
	– da li je ispravnog tipa.
Format poruke
- Poruka o detektovanom simbolu MORA da sadrži sledeće podatke:
	– linija izvornog koda u kojoj je pronađen simbol,
	– naziv pronađenog simbola,
	– ispis objektnog čvora iz tabele simbola koji odgovara pronađenom simbolu.
Provera kontekstnih uslova:
- U klasi SemanticAnalyzer implementirati proveru svih kontekstnih uslova navedenih u
specifikaciji.
Testiranja rada implementiranog semantičkog analizatora:
- Napisati ulazne fajlove na programskom jeziku Mikrojava koji sadrže sve sintaksno i semantički
ispravne MJ programe uz pokrivanje svih smena iz gramatike.
- Napisati ulazne fajlove na programskom jeziku Mikrojava koji sadrže sve kombinacije
semantičkih grešaka.


Generisanje koda
Generisanje koda podrazumeva transformaciju sintaksno i semantički ispravnog sintaksnog
stabla u bajtkod za izvršno okruženje za MJ virtuelnu mašinu (MJVM).
Opšti zahtevi
- Generisanje koda vrši se obilaskom apstraktnog sintaksnog stabla koje je nastalo kao rezultat
sintaksne analize i zadovoljilo uslove semantičke provere.
- Potrebno je implementirati klasu rs.ac.bg.etf.pp1.CodeGenerator, koja proširuje automatski
generisanu klasu rs.ac.bg.etf.pp1.ast.VisitorAdapter, i u njoj redefinisati medote za obilazak
elemenata sintaksnog stabla koji su relevanti za generisanje koda.
- Generator koda mora da generiše ispravan bajtkod za MJVM.
- Za implementaciju generatora koda moraju se koristiti alati Code, disasm i Run. dostupni u
biblioteci mj-runtime.jar.
- Generisanje koda se pokreće u glavnom programu klase Compiler po završetku semantičke
analize i ispisa sadržaja tabele simbola. Implementira se prosleđivanjem objekta klase
CodeGenerator korenu sintaksnog stabla.
- Izlaz generatora koda mora da bude izvršivi .obj fajl za MJVM.
- Putanja do izlaznog .obj fajla prosleđuje se glavnom programu klase Compiler kao drugi
argument komandne linije.


- Potrebno je implementirati generisanje koda za SVE gramatičke smene u nastavku
(osnovni iskazi, aritmetički izrazi i rad sa nizovima prostih tipova):
	Statement := DesignatorStatement ";".
	DesignatorStatement := Designator "=" Expr.
	DesignatorStatement := Designator "++".
	DesignatorStatement := Designator "--".
	Statement := DesignatorStatement ";".
	Statement := "read" "(" Designator ")" ";".
	Statement := "print" "(" Expr [“,” number] ")" ";".
	Expr := ["‐"] Term {Addop Term}.
	Term := Factor {Mulop Factor}.
	Factor := numConst | charConst | "(" Expr ")" | boolConst | "new" Type [ "[" Expr "]" ].
	Designator := ident [ "." ident | "[" Expr "]" ]. // ident. ident samo za nabrajanja
	Addop := "+" | "‐" .
	Mulop := "*" | "/" | "%".
- Od nizova, treba podržati samo nizove ugrađenih tipova podataka i nabrajanja. Program mora
da sadrži funkciju main, globalne/lokalne promenljive (proste ili nizovne i nabrajanja), globalne
konstante. Ne treba obrađivati unutrašnje klase.
- Potrebno je implementirati sve gramatičke smene u
nastavku (kontrolne strukture, uslovni izrazi, pozivi globalnih metoda):
	DesignatorStatement := Designator "(" [ActPars]")" .
	Statement := "if" "(" Condition ")" Statement ["else" Statement ].
	Statement := "for" " (" [DesignatorStatement ] ";" [Condition] ";" [DesignatorStatement] ")"
	Statement.
	Statement := "break" ";".
	Statement := "continue" ";".
	Statement := "return" [Expr] ";".
	Statement := "{" {Statement} "}".
	ActPars := Expr {"," Expr }.
	Condition := CondTerm { "||" CondTerm }.
	CondTerm := CondFact { "&&" CondFact }.
	CondFact = Expr [ Relop Expr ]. // samo jedan izraz (Expr) u slučaju bool promenljive
	Factor := Designator [ "(" [ActPars] ")" ].


Dodatni zahtevi
- Proširiti implementaciju prevodioca za programski jezik Mikrojava dodavanjem podrške za
definisanje metoda sa opcionim formalnim argumentima koje mogu da vraćaju vrednost
proizvoljnog ugrađenog tipa. Implementirati semantičku analizu poziva funkcija sa argumentima (i
tipom rezultata) i proverom stvarnih argumenata (izraza) po broju i tipu sa formalnim. Prevodilac
treba da generiše kod za prosleđivanje stvarnih argumenata, poziv funkcije i adekvatnu obradu
povratne vrednosti.
- Proširiti programski jezik Mikrojava dodavanjem inicijalizatorskih listi za nizove primitivnih tipova
podataka. Inicijalizatorska lista se opciono može iskoristiti za inicijalizaciju elemenata niza
prilikom njegovog instanciranja. Primer upotrebe inicijalizatorske liste za niz celih brojeva dat je u
nastavku:
int niz[];
int niz2[];
int x;
...
int f(int arg){...}
...
niz2 = new int[3]{0, 1, 2};
niz = new int[5]{3, x, niz2[0], f(5), niz2[f(3)]};
Prilikom inicijalizacije niza obavezno se navodi dužina niza, a opciono se može navesti i lista
inicijalnih vrednosti elemenata niza. Broj elemenata u listi mora odgovarati dužini niza, a tipovi
inicijalnih vrednosti se moraju poklapati sa tipom niza koji se inicijalizuje.


Elementi rešenja su sledeći:
a) Propratna dokumentacija u obliku Word dokumenta MJProjekat.doc koji treba da se nalazi u
korenom direktorijumu rešenja i da sadrži:
b) naslovnu stranu,
c) kratak opis postavke zadatka od nekoliko rečenica,
d) opis komandi za generisanje java koda alatima, prevođenje koda kompajlerom, pokretanje i
testiranje rešenja,
e) kratak opis priloženih test primera (ne uključivati ulaze niti izlaze testiranja u izveštaj).
f) kratak opis novouvedenih klasa.
Izvorni i prevedeni programski kod mora da sledi direktorijumsku strukturu koja je određena
u šablonu projekta [PT]. Dakle moraju se poslati .flex i .cup fajlovi, svi izgenerisani i rukom pisani
.java fajlovi koji čine rešenje i odgovarajući prevedeni .class fajlovi. Rešenje treba da sadrži i .jar
arhive alata AST-CUP i Flex.
3. U posebnom folderu test treba da se nalaze svi ulazni test fajlovi sa ekstenzijom .MJ, kao i
odgovarajući izlazni fajlovi koji su rezultat testiranja, sa istim imenom kao ulazni fajl, ali sa
ekstenzijom .out za standardni izlaz i .err za izlaz greške,. Uputstvo: Pri pokretanju programa
standardni izlaz može se preusmeriti u fajl izlaz.out ako se na komandnoj liniji navede >izlaz.out, a
izlaz greške se preusmerava sa 2>izlaz.err.
