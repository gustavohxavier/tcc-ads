#!/usr/bin/env bash
set -e

echo "Starting CI monitor for branch feature/develop (poll interval 60s). Press Ctrl-C to stop." 

while true; do
  resp=$(curl -sS -H "Authorization: token $GITHUB_TOKEN" -H "Accept: application/vnd.github+json" "https://api.github.com/repos/gustavohxavier/tcc-ads/actions/runs?branch=feature/develop") || true
  python - <<'PY'
import sys,json
try:
    r=json.load(sys.stdin)
except Exception:
    print('Failed to parse API response')
    sys.exit(1)
if r.get('total_count',0)==0:
    print('No workflow runs found for branch feature/develop')
    sys.exit(1)
run=r['workflow_runs'][0]
print(f"Run id: {run['id']} | head_sha:{run['head_sha']} | status:{run['status']} | conclusion:{run['conclusion']} | url:{run['html_url']}")
if run['status']=='completed':
    sys.exit(0)
else:
    sys.exit(2)
PY
  code=$?
  if [ $code -eq 0 ]; then
    echo "Workflow completed. Exiting."
    break
  fi
  sleep 60
done
