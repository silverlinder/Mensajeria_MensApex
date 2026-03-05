param(
  [Parameter(Mandatory=$true)][string]$FeatureName,     # ej: FMWC-129_jenkins_pipe_line_test
  [Parameter(Mandatory=$true)][string]$CommitMessage,   # ej: "FMWC-129: Jenkins pipeline test"
  [Parameter(Mandatory=$true)][string]$ReleaseVersion   # ej: 1.0.0.4
)

function Fail($msg) { Write-Error $msg; exit 1 }

# --- checks básicos ---
git rev-parse --is-inside-work-tree *> $null
if ($LASTEXITCODE -ne 0) { Fail "No estás dentro de un repositorio git." }

# Guardamos si había stash que vamos a crear
$stashMade = $false

# Estado inicial
$status = git status --porcelain
if (-not $status) {
  Fail "No hay cambios locales. Si no hay nada que commitear, no tiene sentido este flujo."
}

# 1) STASH SOLO TRACKED (NO incluye untracked)
#    (git stash por defecto no mete untracked si no pones -u)
$stamp = Get-Date -Format "yyyy-MM-dd_HH-mm-ss"
git stash push -m "auto-stash($stamp): $FeatureName" | Out-Null
if ($LASTEXITCODE -ne 0) { Fail "Falló git stash push." }
$stashMade = $true
Write-Host "Stash creado (solo tracked)." -ForegroundColor Yellow

# 2) fetch (opcional pero recomendable)
git fetch --all --prune | Out-Null

# 3) start feature
git flow feature start $FeatureName
if ($LASTEXITCODE -ne 0) { Fail "Falló git flow feature start $FeatureName." }
Write-Host "Start feature" -ForegroundColor Yellow

# 4) stash pop dentro de la feature
if ($stashMade) {
  git stash pop
  if ($LASTEXITCODE -ne 0) {
    Fail "stash pop falló (conflictos). Resuelve conflictos y vuelve a ejecutar o continúa manualmente."
  }
  Write-Host "Stash aplicado (pop) dentro de la feature." -ForegroundColor Green
}

# 5) add SOLO tracked (no añade untracked)
git add -u
if ($LASTEXITCODE -ne 0) { Fail "Falló git add -u." }

# Evitar commit vacío
$staged = git diff --cached --name-only
if (-not $staged) {
  Fail "No hay cambios staged (evito commit vacío). Puede que solo tuvieras untracked o no haya cambios tracked."
}

# 6) commit
git commit -m $CommitMessage
if ($LASTEXITCODE -ne 0) { Fail "Falló git commit." }

# 7) push feature
git push -u origin ("feature/" + $FeatureName)
if ($LASTEXITCODE -ne 0) { Fail "Falló push de la feature." }

# 8) finish feature
git flow feature finish $FeatureName
if ($LASTEXITCODE -ne 0) { Fail "Falló git flow feature finish." }
Write-Host "Finish feature" -ForegroundColor Yellow

# 9) release start/finish
git flow release start $ReleaseVersion
if ($LASTEXITCODE -ne 0) { Fail "Falló git flow release start." }
Write-Host "Start release" -ForegroundColor Yellow

git flow release finish -m "Finish $ReleaseVersion" $ReleaseVersion
if ($LASTEXITCODE -ne 0) { Fail "Falló git flow release finish." }
Write-Host "Finish release" -ForegroundColor Yellow

# 10) push develop/master/tags
git push origin develop
if ($LASTEXITCODE -ne 0) { Fail "Falló push develop." }

git push origin master
if ($LASTEXITCODE -ne 0) { Fail "Falló push master." }

git push origin --tags
if ($LASTEXITCODE -ne 0) { Fail "Falló push tags." }

Write-Host "OK: stash->feature->commit(tracked)->push->finish + release + pushes." -ForegroundColor Green
