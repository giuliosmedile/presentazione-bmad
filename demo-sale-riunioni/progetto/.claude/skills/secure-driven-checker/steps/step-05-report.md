---
timestamp: ''                 # set at runtime: yyyyMMddHHmmss
report_md_path: ''            # set at runtime
report_json_path: ''          # set at runtime
audit_status: ''              # set at runtime: CLEAN | VIOLATIONS_FOUND
---

# Step 5: Generate Audit Report

## INSTRUCTIONS

1. **Generate the timestamp** `{timestamp}` in format `yyyyMMddHHmmss`:
   - PowerShell: `Get-Date -Format "yyyyMMddHHmmss"`
   - Bash: `date +%Y%m%d%H%M%S`

2. **Decide audit status** `{audit_status}`:
   - `VIOLATIONS_FOUND` if `{violations}` is non-empty.
   - `CLEAN` otherwise (even if `{preexisting_violations}` or `{suppressed_violations}` are non-empty — these are surfaced but do not change clean status of the diff).

3. **Ensure output directory** `{paths.report_output_path}` exists. Create it if missing.

4. **Render the Markdown report**.

   Load the template `{paths.report_template}` and substitute variables. The template is the source of truth for layout. The report MUST include, in order:

   1. **Header**
      - Skill: `secure-driven-checker v2`
      - Generated at: `<ISO 8601 timestamp>` (timezone Europe/Rome)
      - Mode: `{analysis_mode}` (+ scope, + base ref if applicable)
      - Commit base: `{commit_base}` (full SHA)
      - Source guidelines: file name, SHA-256
      - Files analyzed: count

   2. **Executive Summary**
      - Audit status: `CLEAN` or `VIOLATIONS_FOUND`
      - Counts: in-diff violations by severity; pre-existing; suppressed; requirements verified / total.

   3. **Violations Found** (only if non-empty)

      Group by `severity` (BLOCKER → CRITICAL → WARNING → INFO), then by `requirement_id` within each group. For each violation:

      ```markdown
      ### [{severity}] R{id} — {requirement_title}

      - **File:** `{file_path}`
      - **Package / Modulo:** `{package or module_path}`
      - **Line:** `{line}`
      - **Snippet:**
        ```{lang}
        {snippet}
        ```
      - **Spiegazione:** {explanation}
      - **Riferimento requisito:** {area} / {ambito} / {normativa}
      ```

   4. **Pre-existing Violations** (only if non-empty, only in diff/staged/pr modes)
      Same layout as above, in a separate top-level section. Note: "Queste violazioni sono presenti nel codice ma fuori dalle modifiche correnti."

   5. **Suppressed Violations** (only if non-empty)
      Same layout + the suppression `reason` quoted verbatim.

   6. **Requirements Verified**
      A bulleted list of every requirement ID in `{verified_requirement_ids}` with its title, sorted alphabetically.

   7. **Requirements Skipped (per-file)**
      A compact table: `(file_path, requirement_id, reason)`. Truncate to 50 rows; if more, attach the full list to the JSON sidecar only.

   7b. **Requirements Skipped (per-method, hybrid Controllers only)**
       For files in the "Controllers (REST + GraphQL surface)" group, list method-level skips from `{skipped_at_method_level}`. Compact table: `(file_path, method_name, requirement_id, reason)`. This is what proves that REST requirements were correctly NOT applied to GraphQL handlers (and vice versa). Truncate to 50 rows.

   8. **Reproducibility footer**
      - Source SHA-256 (full)
      - Commit base (full)
      - Skill version, customize.toml hash (optional)
      - Command invoked (the exact args parsed in step-02)

   Save to `{paths.report_output_path}/report-{timestamp}.md`. Store the full path in `{report_md_path}`.

5. **Render the JSON sidecar** at `{paths.report_output_path}/report-{timestamp}.json`:

   ```json
   {
     "skill": "secure-driven-checker",
     "skill_version": "v2",
     "generated_at_iso": "...",
     "mode": "diff",
     "target_scope": "",
     "base_ref": "",
     "commit_base": "<sha>",
     "guidelines": {
       "path": "...",
       "sha256": "..."
     },
     "files_analyzed": [...],
     "summary": {
       "status": "CLEAN | VIOLATIONS_FOUND",
       "violations_in_diff": { "BLOCKER": 0, "CRITICAL": 0, "WARNING": 0, "INFO": 0 },
       "violations_preexisting": 0,
       "violations_suppressed": 0,
       "requirements_verified": 0,
       "requirements_indexed_total": 0
     },
     "violations": [...],
     "preexisting_violations": [...],
     "suppressed_violations": [...],
     "verified_requirement_ids": [...],
     "skipped": [
       { "file": "...", "requirement_id": "R420", "reason": "..." }
     ],
     "skipped_at_method_level": [
       { "file": "...", "method": "creaSoggettoFisico", "requirement_id": "R413", "reason": "GraphQL handler — HTTP status semantics N/A" }
     ]
   }
   ```

   Store the full path in `{report_json_path}`.

   Only emit if `{behavior.emit_json_report}` is true. Same for Markdown via `{behavior.emit_markdown_report}`.

6. **Final user message** (in `{agent.output_language}`):
   - Report path (Markdown).
   - JSON sidecar path.
   - Status: CLEAN or count of violations by severity.
   - Reminder of `--gate` behaviour if relevant.

### CHECKPOINT

Report saved. Hand off to step-06 for optional pipeline integration signaling.

## NEXT

Read fully and follow `./step-06-handoff.md`
