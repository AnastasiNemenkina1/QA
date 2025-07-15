# **Дипломный проект профессии «Тестировщик»**
В рамках дипломного проекта требуется автоматизировать тестирование комплексного сервиса покупки тура, взаимодействующего с СУБД и API Банка.

![image](https://github.com/user-attachments/assets/6c533754-b962-4166-aadc-ec32b9547bf6)

База данных хранит информацию о заказах, платежах, статусах карт, способах оплаты.

Для покупки тура есть два способа: с помощью карты и в кредит. В приложении используются два отдельных сервиса оплаты: Payment Gate и Credit Gate.

# Документы
1.  [Дипломное задание](https://github.com/netology-code/qa-diploma)
2.  [План автоматизации] (https://github.com/AnastasiNemenkina1/QA/blob/master/documentation/Plan.md)
3. [Отчет по итогам тестирования] (https://github.com/AnastasiNemenkina1/QA/blob/17892fef461fd826ba8f749095d6caeed9a95237/documentation/Report.md)
4. [Отчет по итогам автоматизации] (https://github.com/AnastasiNemenkina1/QA/blob/17892fef461fd826ba8f749095d6caeed9a95237/documentation/Summary.md)
# Подготовительный этап
1. Запустить Docker Desktop
2. Запустить IntelliJ IDEA
3. Открыть скачанный с GitHub проект 
# Запуск приложения, тестирование, отчёт
1. Запустить контейнеры командой в корне проекта:
   ```bash
   docker-compose up
   ```

2. В новой вкладке терминала запустить тестируемое приложение командой:

   - Для запуска с подключением к MySQL:
     ```bash
     java -jar artifacts/aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/app
     ```

   - Для запуска с подключением к PostgreSQL:
     ```bash
     java -jar artifacts/aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app
     ```
     
3. Во второй вкладке терминала запустите тесты:
   - **Для MySQL**:
     ```bash
     gradlew clean test -Ddblr1=jdbc:mysql://localhost:3306/app
     ```
     
   - **Для PostgreSQL**:
     ```bash
     gradlew clean test -Ddblr1=jdbc:postgresql://localhost:5432/app
     ```
     
4. Получить отчёт после полного завершения тестов в браузере командой:
   ```bash
   .\gradlew allureServe
   ```

5. Закрыть отчёт командой:
   - Нажать `Ctrl + C`
   - Подтвердить выход, нажав `Y`

6. Закрыть приложение командой:
   - Нажать `CTRL + C` в первой вкладке Terminal

7. После остановки приложения остановить контейнеры командой:
    ```bash
    docker-compose down
    ```
