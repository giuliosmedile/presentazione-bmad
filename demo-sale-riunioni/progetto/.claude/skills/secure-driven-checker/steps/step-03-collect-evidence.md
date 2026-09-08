---
verification_matrix: []       # set at runtime: list of { file, applicable_requirement_ids, skipped_requirement_ids_with_reason }
suppressions: []              # set at runtime: list of { file, line, requirement_id, reason }
---

# Step 3: Collect Evidence & Filter Applicability

## INSTRUCTIONS

1. **Build the verification matrix** `{verification_matrix}`:

   For each file in `{files_to_analyze}`:
   - Determine the file's "role" by matching its path against the rule groups in `{paths.applicability_rules}`. The authoritative list of patterns and the requirement IDs they unlock lives in that YAML file — read it once at the start of this step. Examples of groups currently configured for the TIRRENO BE_TRRN codebase:
     - GraphQL controllers (`**/controller/**/*Controller.java`, excluding `controller/utils/**`)
     - GraphQL schemas (`src/main/resources/graphql/**/*.graphqls`)
     - DTO classes (`**/dto/**/*Dto.java`, `**/dto/**/*Input.java`)
     - ArangoDB persistence layer (Nodes, edges, valueObjects, enums, raw) — no compliance surface
     - MySQL entities — no compliance surface
     - Services / DAO / Repository — cross-cutting subset
     - Spring `@Configuration` classes + `application*.properties`
     - Test sources (`src/test/**`) — exempt
   - Compute the **applicable requirement IDs** for this file: union of `requirement_ids` from every matching rule group (after applying group-level `excludes`).
   - Compute the **skipped requirement IDs** for this file: every requirement in `{requirements_index}` whose id is NOT in the applicable set, EXCEPT requirement IDs listed in `out_of_scope_requirement_ids` at the bottom of the YAML — those go to a separate top-level "Out-of-scope requirements" section in the report (listed once, not per file).
   - Record each per-file skip with a one-line reason, e.g.:
     - `"R412: file is an ArangoDB value object (no input surface)"`
     - `"R407: schema-declaration requirement, file is a Service (not a GraphQL schema)"`
     - `"R419: file path matches src/test/** (test code exempt)"`

2. **Scan for inline suppressions** in every file in scope:
   - Pattern: `{behavior.inline_suppression_marker}` (default `@secure-driven:ignore`) followed by one or more requirement IDs and an optional `(reason: ...)` clause.
   - Grep example: `// @secure-driven:ignore R413 R415 (reason: legacy endpoint, deprecate 2026-Q3)`
   - For every match, append to `{suppressions}` the file path, line number, requirement IDs, and the reason text.
   - The verification step (step-04) MUST consult `{suppressions}` and demote matching violations to a separate "Suppressed" section in the report (never silently dropped).

3. **Per-file scope discipline**:
   - In `diff`, `staged`, `pr` modes: a violation only "counts" if at least one line of it sits inside a hunk range from `{files_to_analyze}[*].hunks`. Pre-existing violations outside the diff are **flagged as "Pre-existing"** in the report but do not increment the primary violation count.
   - In `full` mode: every line is in scope; no pre-existing distinction.

### CHECKPOINT

Present a summary in `{agent.output_language}`:
- Total files: N.
- Files with at least one applicable requirement: M.
- Files with zero applicable requirements (will appear in the report as "no compliance surface"): M' = N - M.
- Total inline suppressions detected: S.

**HALT** and wait for user confirmation before performing the actual verification.

## NEXT

Read fully and follow `./step-04-verify.md`
