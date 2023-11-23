rem *******************comienza la prueba del control de la Practica 3 de SI curso 2023-24
rem ***********************Version  21 de noviembre 2023
cd ..
rem call P3_SI2023.bat 

rem call P3_SI2023.bat  -h

rem call P3_SI2023.bat  -f
rem ***********************  COMIENZA CIFRADO CON TRAZA-----------------------------------------------------------
call P3_SI2023.bat  -f ./1juan_adelantada/config-corregir-21-nov-con-traza.txt
rem ***********************FIN CIFRADO CON TRAZA----------------------------------------------------------------------------------

cd ./1juan_adelantada/finales


rem ***********************  comparaciones

rem Estas no tienen que se iguales 100%
Fc criptograma1_juan.dat criptograma1.dat 
FC clave_examen_juan.cla clave_examen.cla
FC quijote1recuperado_juan.txt quijote1recuperado.txt

Fc criptograma2_juan.dat criptograma2.dat 

FC quijote2recuperado_juan.txt quijote2recuperado.txt
pause

cd ../..


