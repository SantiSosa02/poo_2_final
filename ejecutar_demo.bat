@echo off
echo Compilando el proyecto...
if not exist out mkdir out
javac --release 8 -d out -cp lib\sqlite-jdbc.jar -sourcepath src\main\java src\main\java\com\cafeteria\Main.java src\main\java\com\cafeteria\DemoPedido.java
if errorlevel 1 (
    echo.
    echo Hubo un error al compilar. Revisa el mensaje de arriba.
    pause
    exit /b 1
)
echo Compilado correctamente. Corriendo la demo de consola...
java -cp "out;lib\sqlite-jdbc.jar" com.cafeteria.DemoPedido
pause
