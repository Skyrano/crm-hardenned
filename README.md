# READ_ME 

### Authors Yoann KERGOSIEN, Lucas MANGIN, Alexis MATIAS GOMES, Alistair RAMEAU

Ce projet est une application Customer Relationship Management (CRM) qui a été développée et sécurisée. 

Cette application permet la gestion centralisée des contacts pour une organisation.

Cette application comporte donc une base de données permettant le stockage des contacts, entreprises, événements etc.

Cette application comporte une interface web, afin de pouvoir avoir accès aux contacts de l’organisation depuis un site distant.

Pour le bon fonctionnement de l'application, il est nécessaire d'avoir une base de données joignable , créée sous MySQL. Le script de création de base de données est fourni dans le dossier des sources données à l'emplacement */src/sql/base.sql*.

Le CRM communique avec la base de données en se s'authentifiant dessus. Pour ce faire, l’application récupère les informations nécessaires dans le fichier *src/main/resources/META-INF/dao.properties*.

Cette application web utilisait Glassfish comme serveur de déploiement lors de la conception, elle est donc optimisée pour cette plateforme.

Pour déployer le serveur Glassfish, il est nécessaire de le configurer correctement avec les artefacts construits par Gradle (Glassfish demandant l’artefact *exploded).

Glassfish5 peut être téléchargé à [cette adresse](https://www.oracle.com/java/technologies/java-ee-sdk-download.html). L'installation de Glassfish est assez simple :

- Dézippez les fichier de Glassfish5 dans un répertoire
- Modifier les fichiers *glassfish5\glassfish\config\asenv* et *glassfish5\glassfish\config\asenv.conf* an y ajoutant la ligne `set AS_JAVA=<Emplacement JDK Java utilisé>` dans le premier et la ligne `AS_JAVA="<Emplacement JDK Java utilisé>"` dans le second

2 possibilités s'offrent pour déployer l'application :

- Via l'IDE Intellij IDEA en sélectionnant l'artefact *.war exploded* généré par le build (en pensant à indiquer dans les paramètres l'emplacement de Glassfish) et en lançant l'application
- Via le dossier *autodeploy* de Glassfish dans lequel il est possible de déposer l’artefact *.war* généré et en lançant  la commande `asadmin start-domain`depuis  le répertoire `glassfish5\bin`

(Pour plus d'informations contacter Alistair.)


Afin d’ajouter la fonctionnalité des envois de mail, il est possible d’ajouter un serveur SMTP qui serait relié à la page de mailing. Nous n’avons cependant jamais testé l’envoie de mail avec un serveur SMTP.

