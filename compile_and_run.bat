@echo off
if not exist bin mkdir bin
echo Compiling...
javac -d bin -cp ".;lib/*" *.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)
echo Running...
java -cp "bin;lib/*" Programme
pause
