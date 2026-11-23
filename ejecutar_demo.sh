#!/bin/bash
set -e
echo "Compilando el proyecto..."
mkdir -p out
javac --release 8 -d out -cp lib/sqlite-jdbc.jar $(find src -name "*.java")
echo "Compilado correctamente. Corriendo la demo de consola..."
java -cp "out:lib/sqlite-jdbc.jar" com.cafeteria.DemoPedido
