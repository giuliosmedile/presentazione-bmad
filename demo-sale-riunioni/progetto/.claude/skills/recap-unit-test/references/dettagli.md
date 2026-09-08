# Dettagli e casi limite

## Come lo script risolve `nome_test` e `descrizione_test`

1. `build_display_maps(sources)` scandisce i `*Test.java` / `*Tests.java` / `*IT.java`
   e costruisce due mappe:
   - `method_to_disp`: nome metodo → `@DisplayName` (o il nome stesso se assente)
   - `disp_to_method`: `@DisplayName` → nome metodo
   L'estrazione raccoglie il blocco di annotazioni che precede ogni firma e verifica
   che contenga una annotazione di test (`@Test`, `@ParameterizedTest`,
   `@RepeatedTest`, `@TestFactory`, `@TestTemplate`). Ordine delle annotazioni
   irrilevante.

2. Per ogni `<testcase>` del report Surefire:
   - normalizza il `name` togliendo `()` e i suffissi dei parametrizzati `[...]`;
   - se il valore normalizzato e' un nome di metodo noto → `nome_test` = quello;
   - altrimenti se e' un display name noto → risale al metodo;
   - altrimenti usa il valore cosi' com'e' (best effort).
   - `descrizione_test` = `method_to_disp[nome_test]` (fallback: il nome).

## Esito

`esito_test` = `Non Passato` se il testcase contiene `<failure>`, `<error>` o
`<skipped>`; altrimenti `Passato`. Per i parametrizzati, basta una invocazione fallita
per marcare l'intera riga `Non Passato`.

## Modalita' diff — selezione dei file

Il criterio scelto e' **"file di test toccato"**: un test entra nel recap se il file che
lo contiene e' stato modificato/aggiunto dopo il riferimento.

- Rispetto a un commit: `git diff --name-only <ref> HEAD -- '...*Test.java'`
- Rispetto a una data: `git log --since=<data> --name-only --pretty=format: -- '...*Test.java' | sort -u`

I path passati a `--only-files` vengono ridotti al nome semplice della classe
(`Foo.java` → `Foo`) e confrontati con la parte finale del `classname` del testcase.
Quindi non serve passare path assoluti: bastano i nomi file che escono da git.

## Dedup fra branch

`merge` processa i JSON nell'ordine ricevuto e tiene la **prima** occorrenza di ogni
`nome_test`. Quindi metti per primo il branch la cui versione del test consideri
autoritativa (di norma il piu' recente). Se due branch hanno un metodo omonimo ma
semanticamente diverso, restera' solo quello del primo: e' un limite noto del criterio
"dedup per nome".

## Sicurezza

- `git checkout <branch>` fallisce se la working copy e' sporca: la skill impone di
  partire pulita e di ripristinare il branch iniziale a fine lavoro.
- Lo script e' sola-lettura sui sorgenti e sui report; scrive solo i JSON intermedi e il
  CSV nei path indicati.

## Estensioni possibili

- Aggiungere una colonna `ID` (formato `BE1`, `BE2`, …) come nel file di esempio
  `testW1S1.docx`: basta numerare le righe in `cmd_merge`.
- Emettere anche `classname` per disambiguare metodi omonimi.
- Filtrare per pattern di classe/pacchetto.
