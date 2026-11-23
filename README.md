# Cafetería - Calculadora de Descuentos

Aplicación de escritorio en Java (sin frameworks externos) que calcula el
descuento aplicable a un pedido de la cafetería, lo muestra en una interfaz
gráfica (Swing) y lo guarda en una base de datos local (SQLite vía JDBC).

## Estructura

- `model`: `Producto` (clase base), `Bebida` y `Comida` (heredan de Producto),
  `ReglaDescuento` (interfaz funcional) y `ReglasDescuento` (reglas ya
  definidas como lambdas).
- `service`: `CalculadoraDescuento`, la lógica que aplica una regla de
  descuento a un total.
- `persistence`: `ConexionBD`, conexión JDBC a SQLite con try-with-resources
  y manejo de `SQLException`.
- `gui`: `VentanaPrincipal`, la ventana Swing (JTextField + JButton +
  JTextArea).
- `Main`: punto de entrada de la aplicación gráfica.
- `DemoPedido`: demo de consola que arma un pedido con `Producto`
  polimórfico (Unidad I).

## Cómo compilar y ejecutar

Requiere tener el JDK de Java instalado (no solo el JRE) y el driver JDBC de
SQLite, ya incluido en `lib/sqlite-jdbc.jar`.

### Opción fácil: scripts

- **Windows**: desde una terminal (PowerShell) abierta en la carpeta del
  proyecto, ejecuta:

  ```powershell
  .\ejecutar.bat        # aplicación gráfica
  .\ejecutar_demo.bat   # demo de consola
  ```

  Ambos compilan solos antes de ejecutar. En PowerShell el `.\` es
  obligatorio: escribir solo `ejecutar` o `ejecutar.bat` da el error "no se
  reconoce como nombre de un cmdlet". También puedes hacer doble clic en el
  archivo `.bat` desde el Explorador de Windows.
- **Mac/Linux**: doble clic en `ejecutar.sh` o `ejecutar_demo.sh` (si el
  sistema no los deja ejecutar con doble clic, corre `./ejecutar.sh` desde
  una terminal, en la carpeta del proyecto).

### Opción manual (terminal)

```bash
javac -d out -cp lib/sqlite-jdbc.jar $(find src -name "*.java")

# Demo de consola (Unidad I: herencia y polimorfismo)
java -cp "out:lib/sqlite-jdbc.jar" com.cafeteria.DemoPedido

# Aplicación gráfica
java -cp "out:lib/sqlite-jdbc.jar" com.cafeteria.Main
```

En Windows, cambia `:` por `;` en el `-cp` y usa `\` en vez de `/` en las
rutas.

Al ejecutar `Main`, la aplicación intenta conectarse a `cafeteria.db` (se
crea sola si no existe) y muestra el resultado de la conexión en el
`JTextArea`. Al calcular un descuento, el pedido también se guarda ahí.

## Regla de descuento

La regla por defecto (`ReglasDescuento.ESTANDAR`) es la que pide el
enunciado: si el total es mayor a $10, aplica 15% de descuento; si no, 0%.
Es una expresión lambda que implementa `ReglaDescuento`. Para agregar una
regla nueva no hay que modificar `ReglaDescuento` ni `CalculadoraDescuento`
(principio abierto/cerrado): basta con escribir otra lambda o clase en
`ReglasDescuento`.
