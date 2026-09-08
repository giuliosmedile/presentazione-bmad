---
analysis_mode: ''             # set at runtime: diff | staged | full | pr
target_scope: ''              # set at runtime: e.g., "src/main/java/it/tirreno/..." or "" for whole project
base_ref: ''                  # set at runtime when mode = pr (e.g., "main")
gate_requested: false         # set at runtime
files_to_analyze: []          # set at runtime: list of { path, hunks?: [{start,end}], status }
commit_base: ''               # set at runtime (HEAD sha or base-ref sha)
---

# Step 2: Resolve Scope

## INSTRUCTIONS

1. **Determine the analysis mode** from invocation args:
   - `staged` → `{analysis_mode} = "staged"`
   - `full <scope?>` → `{analysis_mode} = "full"`, `{target_scope} = <scope or "">`
   - `pr <base-ref>` → `{analysis_mode} = "pr"`, `{base_ref} = <ref>`
   - otherwise → `{analysis_mode} = "diff"` (default)

   Also recognise `--gate` and set `{gate_requested} = true`.

2. **Resolve the commit base** for reproducibility:
   - Run `git rev-parse HEAD` and store in `{commit_base}`.

3. **Collect the files to analyze** according to the mode.

   ### Mode: `diff` (default)
   Run BOTH:
   - `git diff --name-status HEAD`
   - `git diff --cached --name-status`
   Merge the file lists (deduplicate). For each modified file, also capture the hunk ranges using:
   - `git diff --unified=0 HEAD -- <file>` and `git diff --cached --unified=0 -- <file>`
   Parse the hunk headers (`@@ -a,b +c,d @@`) to extract `(start, end)` line ranges in the new file. Store in `{files_to_analyze}[*].hunks`.

   ### Mode: `staged`
   Same as `diff`, but only `--cached`.

   ### Mode: `full`
   - If `{target_scope}` is empty: list all source files in the project (skip `target/`, `node_modules/`, `.git/`, `.bmad-cache/`, `_bmad-output/`, `.claude/_archive/`).
   - Otherwise: list all source files under `{target_scope}`.
   - Do NOT compute hunks in full mode — every line of every file is in scope.
   - **Warn the user** if the file count exceeds `{behavior.max_files_full_mode_warning}` and ask for confirmation to proceed.

   ### Mode: `pr <base-ref>`
   Run `git diff --name-status <base-ref>...HEAD` and `git diff --unified=0 <base-ref>...HEAD -- <file>` per file. Same hunk-parsing as `diff` mode.

4. **Empty scope handling**:
   - If `{files_to_analyze}` is empty:
     - Inform the user that there is nothing to analyze.
     - Offer to switch to `full` mode for the project root.
     - HALT.

5. **File-type filter**:
   Drop files that the applicability rules clearly cannot match (e.g. `*.png`, `*.lock`, generated targets). Use the `excluded_paths` list from `{paths.applicability_rules}` if defined.

### CHECKPOINT

Present a summary in `{agent.output_language}`:
- Resolved mode: `{analysis_mode}` (gate: yes/no).
- Scope: `{target_scope}` or "uncommitted+staged diff" or `"PR vs {base_ref}"`.
- Commit base: short `{commit_base}` (first 12 chars).
- File count after filter: N.
- First 10 file paths (preview).

**HALT** and wait for user confirmation before proceeding. Make it explicit that no audit has been performed yet — only the scope has been determined.

## NEXT

Read fully and follow `./step-03-collect-evidence.md`
