---
violations: []                      # set at runtime: list of violation records
suppressed_violations: []           # set at runtime: list of violation records matched by a suppression
preexisting_violations: []          # set at runtime: violations outside the diff hunks (diff/staged/pr modes only)
verified_requirement_ids: []        # set at runtime: every requirement ID that was actually checked at least once
skipped_at_method_level: []         # set at runtime: per-(file,method,requirement_id) skips with reason
---

# Step 4: Zealous Verification

## INSTRUCTIONS

1. **Verification protocol — for every (file, requirement_id) pair in `{verification_matrix}`:**

   a. Read the full file (not just the hunk) so you have semantic context. The hunk is the "in-scope filter" for violation reporting, NOT for understanding.

   b. **Method-level discrimination (Controllers only).**
      For files matching the "Controllers (REST + GraphQL surface)" rule group, you MUST examine annotations **per handler method** before applying a requirement, because REST and GraphQL handlers coexist in the same class:

      | Method annotation                                                | Surface  | Apply REST IDs? | Apply GraphQL IDs? |
      |------------------------------------------------------------------|----------|-----------------|---------------------|
      | `@GetMapping` / `@PostMapping` / `@PutMapping` / `@DeleteMapping` / `@PatchMapping` / `@RequestMapping` | REST     | YES (R406-R419, R500-R533, R563) | no |
      | `@QueryMapping` / `@MutationMapping` / `@SchemaMapping` / `@SubscriptionMapping` | GraphQL  | NO              | R411 (limited), R412, R419 |
      | no handler annotation (private helper, constructor, field)       | none     | no              | no                  |

      For each handler method, build the **applicable-id-at-method-level set**. If the requirement under evaluation is NOT in that set for any handler method in the file, record the requirement in `{verification_matrix}[file].skipped_at_method_level` with a one-line reason (e.g. `"R413: file has only GraphQL handlers in scope; HTTP 400 semantics N/A"`) — do NOT promote it to the global out-of-scope bucket and do NOT silently drop it.

      The cross-cutting requirements (R412, R419, R576) are applied to ALL handler methods regardless of surface, and additionally to private helpers when they have logging or input-processing surface.

   c. Apply the requirement's `descrizione` to the file (or to each handler method, after step 1b):
      - Read literally. Do not paraphrase the requirement to fit the code.
      - Identify any line that **violates** the requirement.
   c. For each violation candidate:
      - Determine the **Java package** (from `package ...;` declaration) or the **module path** for non-Java files.
      - Capture the exact **line number**.
      - Extract a **short code snippet** (≤ 5 lines, with the offending line highlighted).
      - Write a concise **explanation** of why it violates the requirement, citing the requirement text.
   d. Append the record to `{violations}`. Use this schema:

      ```yaml
      requirement_id: "R412"
      requirement_title: "<first 80 chars of descrizione>"
      severity: "<from requirements_index>"
      file_path: "src/main/java/com/leonardo/trrn/controller/InvestigazioneGraphController.java"
      package: "com.leonardo.trrn.controller"
      line: 34
      snippet: |
        @MutationMapping
        public SoggettoFisicoDto creaSoggettoFisico(@Argument SoggettoFisicoDto input) {
            return soggettoFisicoService.salva(input);
        }
      explanation: "Il requisito R412 impone validazione sintattica e semantica dei dati in ingresso. L'argomento @Argument SoggettoFisicoDto è privo di @Valid e il DTO non dichiara constraint Bean Validation: il payload può essere salvato senza alcun controllo."
      in_diff_hunk: true                  # diff/staged/pr modes only
      ```

2. **Suppression resolution**:
   - For each violation in `{violations}`, check whether any record in `{suppressions}` matches:
     - same `file_path`
     - covers `line` (an inline suppression covers the line it sits on AND the next 5 lines — convention)
     - includes the violation's `requirement_id`
   - On match: move the violation from `{violations}` to `{suppressed_violations}` and attach the suppression reason.

3. **Pre-existing vs in-diff classification** (diff / staged / pr modes only):
   - For each remaining violation: if `in_diff_hunk` is `false`, move it from `{violations}` to `{preexisting_violations}`.
   - Pre-existing violations must still appear in the report (transparency) but DO NOT count toward the primary violation total.

4. **Mark verified requirements**:
   - Add every requirement ID that was checked (regardless of outcome) to `{verified_requirement_ids}`.
   - The report uses this list to prove that the requirement was actually exercised, not silently skipped.

5. **Zero-violation handling**:
   - If after suppression and pre-existing classification `{violations}` is empty, the audit is "CLEAN". Generate a clean-state report at step-05 — do NOT skip the report.

### CHECKPOINT

Present a summary in `{agent.output_language}`:
- Total violations (in-diff): V
  - by severity: BLOCKER=…, CRITICAL=…, WARNING=…, INFO=…
- Pre-existing violations (outside diff): P
- Suppressed violations: S
- Distinct requirements verified at least once: D / total_indexed

**HALT** and inform the user that analysis is complete and you are ready to generate the audit report.

## NEXT

Read fully and follow `./step-05-report.md`
