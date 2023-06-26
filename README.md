Предварительные требования:

<ul>
 <li>Java 8+</li>
 <li>Apache Maven 3. x</li>
 <li>MySQL 8.0+</li>
 <li>Подключите следующие maven-зависимости:<br>
 <code>mysql-connector-java (8.0.32)
 javafx-controls (19)
 javafx-fxml (19)
 controlsfx (11)
 javafx-maven-plugin(0.0.8)
 org.apache.poi (5.2.2)
 liquibase-core (4.22.0)
 spring-boot-starter-web
 spring-boot-starter-data-jpa
 spring-boot-devtools
 spring-boot-maven-plugin
 lombok</code>
 </li>
 </ul>

    На вашем компьютере должны быть установлены:

    JavaMail API и Java Activation Framework (JAF) для работы с почтой с базового веб-сайта Java;

    JavaFX SDK 20.0.1;

Подключитесь к базе данных MySQL:

    Откройте файл "create-changeset-anketa-table.xml" и создайте нового пользователя:

    CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'password';

    В файл application.properties продублируйте имя и пароль:

    spring.datasource.username=...
    spring.datasource.password=...

    При запуске программы происходит миграция в базу данных "db_user"

Также, при запуске исполняемого jar-файла, необходимо прописать в вашей IDE или файле MANIFEST.MF настройки VM:

    VM-Options: --module-path /*путь до скачанной библиотеки, например:*/ "D:\Downloads\javafx-sdk-20\lib"
    --add-modules javafx.controls,javafx.fxml
