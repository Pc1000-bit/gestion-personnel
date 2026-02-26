@echo off
TITLE Lancement du Systeme de Gestion du Personnel
echo ======================================================
echo    LANCEMENT DE L'APPLICATION GESTION DU PERSONNEL
echo ======================================================
echo.
echo Verification de JAVA_HOME...
if "%JAVA_HOME%"=="" (
    echo Tentative de definition temporaire de JAVA_HOME...
    set JAVA_HOME=D:\java
)

echo Java Home est : %JAVA_HOME%
echo.

echo [INFO] Un script de surveillance attend que le serveur soit pret...
echo [INFO] Le navigateur s'ouvrira automatiquement sur http://localhost:8080
echo.

:: Lancement d'une commande PowerShell en arriere-plan qui attend que le port 8080 soit ouvert
start /b powershell -WindowStyle Hidden -Command "$wait = 0; while($wait -lt 60) { try { $c = New-Object System.Net.Sockets.TcpClient('localhost', 8080); if($c.Connected) { Start-Process 'http://localhost:8080'; break; } } catch { Start-Sleep -Seconds 2; $wait++ } }"

echo Demarrage de Spring Boot...
echo (Cela peut prendre 10 a 20 secondes)
echo.

call mvnw.cmd spring-boot:run

echo.
echo L'application s'est arretee.
pause
