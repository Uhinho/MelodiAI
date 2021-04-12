# Viikko 3

Viikko kolme on mennyt aikalailla trien kimpussa. Trien ajatus on yksinkertainen, mutta tähän kyseiseen käyttöön sen implementointi on osoittautunut yllättävän haastavaksi toteuttaa.
Olen hieman suunnittelemastani aikataulusta jäljessä. Mutta nyt midien parsiminen triehen näyttää onnistuvan. Tällä hetkellä käytössä on ainoastaan pianon sisältäviä Mozartin sävellyksiä, sillä tarkoituksena ei ainakaan alkuun ole generoida kuin yksinkertaisia melodioita ilman rumpuja tai muuta MIDIn mahdollistamia instrumentteja. 
Tein apumetodiksi yksinkertaisen printterin, joka tulostaa konsoliin trien auki, ja siitä onkin ollut valtaisa apu bugien metsästyksessä. 

Tähän mennessä kommentoinnit ja checkstylet on jääneet vielä hieman takavasemmalle ja ne teenkin seuraavaksi kun perusrakenne trielle näyttää enemmän tai vähemmän nyt toimivan. 
Itse MIDI tiedostojen generoinnissa ajattelin aluksi käyttää staattista nuotin pituutta ja rytmiä, ja miettiä mahdollisia toteutustapoja rytmiikan ja syvemmän melodisen sisällön aikaansaamiseksi kunhan perusidea toimii.
