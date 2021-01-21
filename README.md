# Analýza prodejů
[Android ] Rozšíření Skladového pomocníka do čtečky čárových kódů. Zpracovává analýzu prodejů.  Samostatně nepoužitelné.

## První spuštění

Během prvního spuštění Vás aplikace požádá o udělení oprávnění ke čtení a ukládání dat z interní paměti zařízení. Tato oprávnění jsou nutná pro tvorbu excelových souborů se seznamem zboží na vrácení a objednání. 

Zároveň se během prvního spuštění rozbalí přiložená SQLite databáze s čárovými kódy a názvy všech knížek, které firma Knihy Dobrovský prodává. Tato databáze se používá k fulltextovém vyhledávání podle názvu knihy a obsahuje přes 450 000 záznamů, takže v závislosti na výkonu Vašeho zařízení může první spuštění trvat o něco déle. 

![uvodni2](https://user-images.githubusercontent.com/26610601/105231896-e19f3a80-5b67-11eb-8ccd-a161530343a7.png)



## Importování dat pomocí podpůrného programu

V levém rohu se zobrazí IP adresa  Vašeho počítače, která musí být nastavena v aplikaci.  To se dělá v  "Nastavení IP" v menu aplikace. 

Jakmile je IP adresa nastavená tak už jenom stačí přetáhnout CSV soubor s daty z analýzy prodejů na ikonu padáku a v menu aplikace zahájit přenos dat do aplikace pomocí tlačítka "Nahrát data z PC". 

Po nahrání dat je aplikace připravená k použití.


![prevodnik2](https://user-images.githubusercontent.com/26610601/105240398-59239880-5b6d-11eb-9f41-008d137225b1.png)



## Importování dat z serveru


Tento způsob je mnohem  jednoduší a pohodlnější než ten předchozí. V hlavním menu aplikace stačí kliknout na tlačítko "Nahrát data".  Poté vybrat prodejnu, vložit heslo a data se stáhnou po kliknutí na tlačítko "Stáhnout data z vybrané prodejny". 

V pozadí aplikace se zároveň spustí služba, která v případě změny dat na serveru provede automatickou aktualizaci dat.

![ftp2](https://user-images.githubusercontent.com/26610601/105240401-59bc2f00-5b6d-11eb-9a07-409458d176f2.jpg)



## Žebříček prodejů


Po nahrání dat z analýzy prodejů do aplikace se umožní zobrazování žebříčku prodejů pro danou prodejnu. Žebříček se zobrazí pomocí tlačítka 'Žebříček prodejů' v menu aplikace.

Nahoře se zobrazuje celková tržba za poslední měsíc.
U každé knížky jsou zobrazeny informace o pořadí prodejů v rámci prodejny, počtu kusů ve skladu, název, prodeje za poslední měsíc a od začátku účetního roku.

Po kliknutí na knížku se zobrazí detailní informace o knížce.


![zebricek](https://user-images.githubusercontent.com/26610601/105232658-fc25e380-5b68-11eb-9485-588256b3de7a.jpg)



## Vyhledávání a detail knihy


Knihu je možné vyhledat podle názvu knihy, naskenováním čárového kódu a nebo ručním zadáním čárového kódu. Po nalezení knihy se zobrazí fragment obsahující detailní informace o knize. 

Z tohoto fragmentu můžete knihu přidat do vratky nebo do objednávky kliknutím červeného tlačítka se symbolem plus.

![detail3](https://user-images.githubusercontent.com/26610601/105232730-165fc180-5b69-11eb-9a1e-5b2c59174648.jpg)


## Vratky a objednávky


K tomuto fragmentu je možné se dostat přes hlavní menu kliknutím na "Vratky a objednávky". Jsou zde zobrazené všechny knihy, které chceme vrátit dodavateli a nebo které potřebujeme objednat. Najdeme tady i tři tlačítka. 


Kliknutím tlačítka se symbolem obálky se otevře Váš emailový klient a do přílohy se vloží excelový soubor se seznamem knih.
Tlačítko se symbolem tiskárny Vám nabídne možnost vytisknout seznam knih v libovolné tiskárně na lokální síti.

Poslední tlačítko s symbolem odpadkového seznam vymaže celý seznam knih. 

V seznamu knih je možné kliknutím na knihu zobrazit dialog pro změnu množství a nebo vymazání knihy ze seznamu.


![vratkaTvorba](https://user-images.githubusercontent.com/26610601/105233302-f54ba080-5b69-11eb-8903-800a0b469ef2.jpg)


## Exportování seznamů vrátek a objednávek do počítače

K vyexportování excelových souborů do počítače stačí spustit podpůrný program, správně nastavit IP adresu a v menu aplikace kliknout na "Odeslat data do PC". Exportovaný soubor se vytvoří ve složce jejíž cestu je možné nastavit v nastavení podpůrného programu. Výchozí cesta je cesta k souboru "prevodník.jar".  

![excel2](https://user-images.githubusercontent.com/26610601/105233417-1dd39a80-5b6a-11eb-814b-6a981b3f8500.png)
