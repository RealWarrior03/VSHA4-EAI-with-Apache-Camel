# How to run our code
- to be written

# To-Do
- BillingSystem Package
  - [ ] Anstatt String mit Messages arbeiten
  - [ ] Nachricht richtig ans ResultSystem.ResultSystem weiterleiten
  - [ ] CustomerCreditStanding Logik implementieren
  - [ ] Betrag abziehen, wenn Rückmeldung vom ResultSystem.ResultSystem kommt
  - [ ] CustomerCreditStanding ins BillingSystem migrieren

# Questions
- CCOS
  - Läuft auf einer while(true) loop, soll das so?
    - open
  - Liest mittels Thread.sleep(120000) für 2min, gibt es einen anderen Weg?
    - open
  - Ergebnis wird zusätzlich in Output.txt gepackt, scheint so gewünscht
    - open

# Systems
- [x] WebOrderSystem
  - [x] from
  - [x] process
  - [x] enrich
  - [x] to
- [x] CallCenterOrderSystem
  - [x] from
  - [x] process
  - [x] enrich
  - [x] to
- [ ] BillingSystem
  - [ ] CustomerCreditStanding
- [ ] InventorySystem
- [ ] ResultSystem.ResultSystem
- [ ] Database
  - [ ] Inventory

# Bugs
- nothing