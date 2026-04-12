param(
    [Parameter(Position = 0)]
    [string]$ImageTag = 'xingyun-cloud-gateway:local'
)

$ErrorActionPreference = 'Stop'

function Show-Usage {
    Write-Output 'Usage: powershell -ExecutionPolicy Bypass -File build-image.ps1 [image:tag]'
    Write-Output ''
    Write-Output 'Builds the xingyun-cloud-gateway jar and Docker image.'
    Write-Output 'Default image tag: xingyun-cloud-gateway:local'
}

if ($ImageTag -in @('-h', '--help')) {
    Show-Usage
    exit 0
}

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$targetDir = Join-Path $scriptDir 'target'
$dockerfile = Join-Path $targetDir 'Dockerfile'
$packageCommand = 'mvn -pl cloud/xingyun-cloud-gateway -am package -DskipTests'

if (-not (Test-Path -LiteralPath $dockerfile)) {
    Write-Output "Missing Dockerfile: $dockerfile"
    Write-Output "Run '$packageCommand' first to generate the module artifacts."
    exit 1
}

$jarFiles = @(
    Get-ChildItem $targetDir -Filter '*.jar' -File
)
$jarFiles = $jarFiles | Where-Object {
    $_.Name -notlike '*-sources.jar' -and
    $_.Name -notlike '*-javadoc.jar' -and
    $_.Name -notlike 'original-*.jar'
}

if ($jarFiles.Count -lt 1) {
    Write-Output "No runnable jar found in $targetDir."
    Write-Output "Run '$packageCommand' first to generate the module artifacts."
    exit 1
}

Write-Output "Building Docker image: $ImageTag"
& docker build -t $ImageTag -f $dockerfile $targetDir
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

Write-Output "Done: $ImageTag"
