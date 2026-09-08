---
guidelines_sha256: ''         # set at runtime
requirements_index: []        # set at runtime (parsed requirements as a list of structured records)
cache_hit: false              # set at runtime
reload_requested: false       # set at runtime if --reload-guidelines was passed
---

# Step 1: Load & Cache Requirements

## INSTRUCTIONS

1. **Detect `--reload-guidelines` flag** in the invocation args; set `{reload_requested}` accordingly.

2. **Compute SHA-256** of `{paths.guidelines_source}`:
   - PowerShell: `(Get-FileHash -Algorithm SHA256 "<path>").Hash.ToLower()`
   - Bash: `sha256sum "<path>" | awk '{print $1}'`
   Store the result in `{guidelines_sha256}`.

3. **Check the cache** at `{paths.cache_path}/requirements.json`:
   - If the file exists AND its `source_sha256` field equals `{guidelines_sha256}` AND `{reload_requested}` is `false`:
     - Set `{cache_hit} = true`. Load the cached `{requirements_index}` from the file.
     - Skip directly to the CHECKPOINT below.
   - Otherwise: proceed to step 4 (full re-extraction).

4. **Parse the guidelines document** into a structured index.

   Read `{paths.guidelines_source}` completely. The file follows the pattern:

   ```
   ## Area: <area>

   ### Ambito: <ambito>

   #### R<id>

   **Descrizione:** <text>

   | Attributo | Valore |
   | --- | --- |
   | Tipo | Requisito | Raccomandazione |
   | Normativa/Paragrafo | <paragraph ref> |
   | Origine | <source> |
   | Allocazione | <e.g. Sviluppo/Interoperabilità> |
   | Applicabilità | <e.g. Applicazione, AI> |
   | Note | <optional> |
   ```

   For every requirement build a record:

   ```yaml
   id: "R413"
   area: "Interoperabilità"
   ambito: "Interoperabilità tecnica"
   tipo: "Requisito"           # or "Raccomandazione"
   normativa: "par.4.1"
   origine: "..."
   allocazione: "Sviluppo/Interoperabilità"
   applicabilita: ["Applicazione", "AI"]
   note: "<optional>"
   descrizione: "<full text>"
   ```

5. **Classify each requirement** using the policy files:
   - **Severity**: open `{paths.severity_policy}` and resolve a severity for the requirement, using:
     1. Explicit `id_overrides` (e.g. `R413: CRITICAL`), if present.
     2. Otherwise `category_defaults` by `ambito` (e.g. "Interoperabilità tecnica" → CRITICAL).
     3. Otherwise the global `default_severity` (typically WARNING).
     Inject the resolved severity into the record as `severity: <BLOCKER|CRITICAL|WARNING|INFO>`.
   - **Applicability fingerprint**: open `{paths.applicability_rules}` and compute the list of file-pattern groups whose `requirement_ids` include this requirement (or whose `applies_when.ambito` matches). Inject the resolved fingerprint into the record as:

     ```yaml
     applies_to_patterns: ["**/*Controller.java", "**/api/**/*.yaml", ...]
     ```

   - **Verification heuristics**: optionally attach a small `heuristics` block lifted from the rule file (regex hints, keyword hints) — these are pointers, not authoritative. The model still reads the actual code.

6. **Write the cache** at `{paths.cache_path}/requirements.json`:

   ```json
   {
     "source_path": "docs/data-model/cyber/Requisiti normativi - Allocazione V2 (version 1).md",
     "source_sha256": "<sha>",
     "extracted_at": "<ISO 8601 timestamp>",
     "skill_version": "v2",
     "requirements": [ <records...> ]
   }
   ```

   Create `{paths.cache_path}` if it does not exist. The cache directory is gitignored.

7. **Set `{cache_hit} = false`** and continue.

### CHECKPOINT

Present a concise summary to the user in `{agent.output_language}`:
- Cache hit: yes/no (and reason if no: missing / hash mismatch / `--reload-guidelines`).
- Total requirements indexed: N.
- Breakdown by severity (count of BLOCKER / CRITICAL / WARNING / INFO).
- Breakdown by `ambito` (count per area).
- Source SHA-256 (first 12 chars).

**HALT** and wait for the user to confirm before proceeding.

## NEXT

Read fully and follow `./step-02-resolve-scope.md`
