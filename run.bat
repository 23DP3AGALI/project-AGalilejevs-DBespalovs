@echo off
REM Переход в папку, где находится этот .bat
cd /d %~dp0

echo Компиляция проекта...
call mvnw clean package

echo Запуск приложения...
call mvnw exec:java -Dexec.mainClass="lv.rvt.Main"

pause
