# Step 6: Hand-off (Pipeline Integration)

The skill produces a stable Markdown + JSON pair that any subsequent step can consume. This step does NOT modify any other skill or file. It only signals integration points to the user.

## INSTRUCTIONS

1. **Surface the artifact paths**:
   - Print `{report_md_path}` and `{report_json_path}` again, both as absolute paths.
   - Mention that the JSON is the stable contract for downstream tooling.

2. **Suggest pipeline next steps** (informative, never automatic) in `{agent.output_language}`:

   - **Code review** — propose to follow up with `/bmad-code-review` to combine adversarial layers with this compliance report.
   - **Checkpoint review** — propose `/bmad-checkpoint-preview` to walk a human through the change including the compliance result.
   - **Retrospective** — at the end of an epic, suggest `/bmad-retrospective` and point to the audit-reports folder as the compliance evidence base.
   - **Sprint correction** — if BLOCKER count > 0, propose `/bmad-correct-course` to handle the impact at sprint level.

   These are **suggestions**, not invocations. The user decides.

3. **Gate evaluation** (only if `{gate_requested}` is `true`):
   - Compare the highest severity in `{violations}` against `{behavior.gate_on_severity}`.
   - If the highest severity is **≥** the threshold (order: INFO < WARNING < CRITICAL < BLOCKER):
     - Emit a clear, italian-language **HALT message**: "🛑 Gate fallito: trovate N violazioni di severity ≥ `{threshold}`. Si veda `{report_md_path}`."
     - Do NOT proceed with any further skill chain. The user must address the violations or re-invoke without `--gate`.
   - Otherwise: emit "✅ Gate superato".

4. **Update auto-memory pointer (optional, only on user request)**:
   - If the user asks "ricorda dove sono i report di compliance", emit a `reference` memory entry pointing to `{paths.report_output_path}`. Do NOT add this memory automatically — only on explicit request, to avoid memory pollution.

### CHECKPOINT

Workflow complete. The audit cycle is over.

## END OF WORKFLOW
