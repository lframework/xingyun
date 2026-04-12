@echo off
setlocal

powershell -ExecutionPolicy Bypass -File "%~dp0build-image.ps1" %*
set EXIT_CODE=%ERRORLEVEL%

endlocal & exit /b %EXIT_CODE%
