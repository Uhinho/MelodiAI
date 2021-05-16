# Testit

## Yksikkötestit

Luokat on testattu kattavasti yksikkötesteillä. Käyttöliittymä ja sen käynnistävä luokka on jätetty testien ulkopuolelle.

![](https://github.com/Uhinho/tiralab/blob/master/dokumentaatio/kuvat/jacoco.png)

## Trie 

Trieen lisääminen ja siitä hakeminen testattiin 100, 1000, 10000, 100000 ja 1000000 suuruisilla taulukoilla. 
Testit suoritettiin AMD Ryzen 3600-suorittimella ja 32Gt DDR4 muistilla. Silti markovin 6. asteen luominen tuotti hankaluuksia suuren tilavaatimuksensa vuoksi. 

Triehen lisääminen:

![](https://github.com/Uhinho/tiralab/blob/master/dokumentaatio/kuvat/insert.JPG)

Triestä hakeminen:

![](https://github.com/Uhinho/tiralab/blob/master/dokumentaatio/kuvat/get.JPG)

Ajat millisekunteina.

Alle pienillä alle 500 000 kokoisilla taulukoilla ja 1-5 asteisella Markovin ketjulla toteutus toimii siis varsin jouhevasti.

## CheckStyle

Luo checkstyle-raportti komennolla
> mvn checkstyle:checkstyle

Checkstyle raportissa ainoat virheet ja varoitukset tulevat MIDI:n vaatimista byte-muotoisista notaatioista,
sekä parista liian pitkästä metodista, jotka voidaan lyhentää jatkokehityksessä.

![](https://github.com/Uhinho/tiralab/blob/master/dokumentaatio/kuvat/checkstyle.png)

