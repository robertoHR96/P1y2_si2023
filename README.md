# Descripción del Proyecto

Este proyecto consta de tres clases en Java que realizan operaciones de cifrado y descifrado de texto utilizando una clave de matriz 3x3. **Es condición necesaria y suficiente para que la inversa exista que su determinante sea distinto de 0 (todo modulo 27)**. El programa principal se encuentra en la clase `Main`, que permite configurar y ejecutar el cifrado/descifrado.La clase `Controller` es la encargada de la gestion/ejecución del fichero de configuración, la clase `EntradaSalida` se encarga de leer y escribir archivos de entrada y salida, mientras que la clase `Hill` realiza las operaciones de cifrado y descifrado.

## Clase Main

La clase `Main` contiene el método principal `main` que permite la ejecución del programa. A continuación, se describen las funcionalidades clave:

- `main(String[] args)`: Método principal que procesa los argumentos de la línea de comandos y controla el flujo del programa.
- `mostrarAyuda()`: Muestra información de ayuda sobre cómo utilizar el programa.
- `mostrarErrorDeArgumentos()`: Muestra un mensaje de error en caso de argumentos inválidos.


## Clase Controller

La clase `Controller` actúa como el controlador principal del programa, coordinando la interacción entre las clases `Main`, `EntradaSalida`, y `Hill`. Sus características incluyen:

- Configuración y gestión del estado de las banderas, como "TRAZA" y "CODIFICA".
- Ejecución de procesos basados en comandos desde un archivo de configuración.
- Selección de archivos de entrada, salida y claves.
- Ejecución del proceso de cifrado y descifrado utilizando la clase `Hill`.
- Formateo de texto de entrada y configuración de valores predeterminados.

## Clase EntradaSalida

La clase `EntradaSalida` se encarga de leer y escribir archivos, así como de realizar operaciones relacionadas con la matriz clave. A continuación, se destacan sus métodos más importantes:

- `leerEntrada()`: Lee el contenido de un archivo de entrada y lo devuelve como una cadena.
- `escribirSalida(String salida)`: Escribe una cadena en un archivo de salida.
- `leerClave()`: Lee una matriz de enteros (clave) desde un archivo.
- `leerClaveInversa()`: Calcula la matriz inversa de la clave.
- `calcularMatrizInversa(Integer[][] matriz)`: Calcula la matriz inversa de una matriz dada.
- `calcularDeterminante(Integer[][] matriz)`: Calcula el determinante de una matriz 3x3.
- `calcularMatrizAdjunta(Integer[][] matriz)`: Calcula la matriz adjunta de una matriz 3x3.
- Métodos para obtener y establecer nombres de archivos y configuraciones.

## Clase Hill

La clase `Hill` se encarga de realizar las operaciones de cifrado y descifrado del texto utilizando la matriz clave. Aquí se resumen sus principales características:

- `cifrar()`: Cifra el contenido de la variable de entrada.
- `codificar()`: Cifra el contenido de la variable de entrada.
- `generarTextoSalida(LinkedList<Integer[][]> listaMatricesCifradas)`: Genera el texto de salida a partir de las matrices cifradas.
- `multiplicarMatrices(Integer[][] matrizCifrar)`: Realiza la multiplicación de dos matrices cuadradas.
- `setClaveDefault()`: Establece una clave de cifrado por defecto.
- Métodos para obtener y establecer el texto de entrada, texto de salida, configuraciones y clave de cifrado.



## Uso y ejemplo del programa
- Para hacer uso del programa:
    - ```java -jar P1y2_si2023.jar -f <fichero>```
  

- Para consultar ayuda:
    - ```java -jar P1y2_si2023.jar -h```

Puede hacer uso del programa utilizando `el fichero de configracion config.txt`. En ejecutar el cifrado
del texto que se encuentra en el fichero `quijote1.txt` dejando en `quijoteformateado.txt` el texto formateado,
en `quijotecifrado.txt` el texto cifrado y en `quijoteRestaurado.txt` el texto descifrado nuevamente.

- Ejecutar el comando:
    - ```java -jar P1y2_si2023.jar -f config.txt```

  

Este proyecto proporciona una implementación básica de cifrado de Hill y puede ser personalizado y extendido según tus requisitos.

---

*Nota: Este README.md es una descripción general del proyecto y no incluye detalles de implementación. Para obtener información detallada sobre las clases y métodos, consulta el código fuente.*

