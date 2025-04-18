# FindNCook

## DEVELOPPEUR
- ANNNOUR AHMAT MASNA
- HANITRINIRINA SAROBIDY FANNY

## DESCRIPTION

FindNCook est un projet Java utilisant JavaFX, conçu pour faciliter la recherche de recette de cuisine. Il intègre des outils modernes tels que Gradle pour la gestion de projet, JUnit pour les tests, et des workflows CI/CD préconfigurés pour assurer une intégration continue sur les systèmes Linux, Windows et macOS.

## TECHNOLOGIES UTILISEES

- Java 17.0.2
- Gradle 7.3.1
- JavaFX 17.0.1
- JUnit Jupiter pour les tests unitaires
- Workflows CI/CD avec GitHub Actions pour Linux, Windows et macOS
- Protections de branches préconfigurées
- IntelliJ IDEA pour le développement
- Git pour le contrôle de version
- Spoonacular API pour la recherche de recettes

## PREREQUIS

Assurez-vous d'avoir installé les éléments suivants sur votre machine :

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Gradle 7.3.1](https://gradle.org/releases/)
- [Git](https://git-scm.com/)

## 📦 Installation

1. Clonez le dépôt :

   ```bash
   git clone https://github.com/FannyHnt/FindNCook.git

2. Accédez au répertoire du projet :

   ```bash
   cd FindNCook
   ```

3. Ouvrez le projet dans votre IDE préféré (IntelliJ IDEA, Eclipse, etc.).

4. Importez le projet Gradle si nécessaire.

5. Assurez-vous que le JDK 17 est configuré dans votre IDE.
   - Pour IntelliJ IDEA, allez dans `File > Project Structure > Project` et sélectionnez le JDK 17.
   - Pour Eclipse, allez dans `Window > Preferences > Java > Installed JREs` et ajoutez le JDK 17.
   
6. Télécharger JavaFX SDK 17.0.1 depuis [GluonHQ](https://gluonhq.com/products/javafx/) et décompressez-le dans un répertoire de votre choix.

7. Ajoutez le chemin de JavaFX à votre projet :
   - Pour IntelliJ IDEA, allez dans `File > Project Structure > Libraries` et ajoutez le répertoire `lib` de JavaFX.
   - Pour Eclipse, allez dans `Project > Properties > Java Build Path > Libraries` et ajoutez le répertoire `lib` de JavaFX.
   
8. Buildez le projet :

   ```bash
   ./gradlew build
   ```
9. Exécutez le projet à l'aide de votre IDE 

10. Sinon exécutez le en ligne de commande :

   ```bash
   ./gradlew run
   ```
11. Modifier les configurations si nécessaire:
    - En utilisant Intellij, aller dans Edit configurations > Add new configuration > Application > Main class: `com.example.findncook.Main` > Program arguments: `--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml` (remplacez `/path/to/javafx-sdk/lib` par le chemin d'accès à votre installation JavaFX).

## FONCTIONNALITES
- Recherche de recettes par ingrédients
- Affichage des détails de la recette
- Sauvegarde des recettes favorites
- Interface utilisateur intuitive et réactive

## INFORMATIONS SUPPLEMENTAIRES

Additionally workflows for CI/CD on Linux, Windows, and MacOS are pre-configured below:

[![ubuntu-20.04](https://github.com/S010MON/java-gradle-fx/actions/workflows/ubuntu-20.yml/badge.svg)](https://github.com/S010MON/java-gradle-fx/actions/workflows/ubuntu-20.yml)
[![windows-2022](https://github.com/S010MON/java-gradle-fx/actions/workflows/windows-2022.yml/badge.svg)](https://github.com/S010MON/java-gradle-fx/actions/workflows/windows-2022.yml)

