# PascalFunctionDeclarationParser
Лабораторная работа №3. Использование
автоматических генераторов анализаторов
Bison и ANTLR<br/>
<br/>
Вариант 2. Арифметические выражения<br/>
Вычисление арифметических выражений с заведением переменных.
В результате трансляции должно вычисляться значение выражений,
в выражении допускается присваивание значений переменных.
Используйте целочисленные переменные.<br/>
Пример:<br/>
a = 2;<br/>
b = a + 2;<br/>
c = a + b * (b - 3);<br/>
a = 3;<br/>
c = a + b * (b - 3);<br/>
Вывод:<br/>
a = 2<br/>
b = 4<br/>
c = 6<br/>
a = 3<br/>
c = 7<br/>
<br/>
Модификация. Добавляется бинарный оператор деления по модулю с правой ассоциативностью
