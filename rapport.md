# Rapport du Projet AndroidBooksClient

## Introduction

Ce rapport présente les choix de conception et les technologies utilisées dans le développement de l'application AndroidBooksClient, une application cliente pour la gestion d'une bibliothèque de livres et d'auteurs. L'application est conçue selon l'architecture Model-View-ViewModel (MVVM) pour séparer la logique métier de l'interface utilisateur, permettant ainsi une meilleure maintenance et testabilité du code.

## Architecture et Choix de Conception

### Architecture MVVM

L'application AndroidBooksClient suit l'architecture Model-View-ViewModel (MVVM), qui est manifeste par la séparation des responsabilités entre les modèles, les vues et les ViewModels.

- **Modèles** : Les entités telles que `Book`, `Author` et `Tag` sont utilisées pour encapsuler les données.
- **Vues** : Les fragments (`BooksFragment`, `AuthorsFragment`, `AddAuthor`, `AddBook`) servent d'interface utilisateur, affichant les données et capturant les interactions utilisateur.
- **ViewModels** : contiennent la logique d'affichage des données, stockage des données et la logique métier, agissant comme un lien entre les modèles et les vues.

### Communication et Partage de Données

Le `SharedViewModel` facilite la communication entre les fragments, permettant un partage efficace et cohérent des données sélectionnées à travers l'application.
Les `SharedPreferencies` ne sont pas utilisés car les données à stocker sont des objets.

### Utilisation de RecyclerView

Pour afficher des listes de livres et d'auteurs, l'application utilise `RecyclerView` avec des adaptateurs et des view holders spécifiques, suivant les bonnes pratiques de développement Android.

## Problèmes Identifiés et Suggestions

### Tags non affichés

- **Problème** : Malgré le chargement des tags dans `BookTagsViewModel`, ceux-ci ne s'affichent pas dans la description d'un livre.

### Suppression d'un auteur

- **Problème** : La fonctionnalité permettant de supprimer un auteur et ses livres associés n'est pas implémentée.

## Conclusion

Le projet AndroidBooksClient démontre une application solide de l'architecture MVVM et des pratiques de développement Android modernes. En adressant les problèmes identifiés et en mettant en œuvre les suggestions d'amélioration, l'application peut être rendue plus robuste, maintenable, et agréable pour l'utilisateur.
