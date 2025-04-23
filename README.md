# Projet de Débruitage d'Images par Analyse en Composantes Principales

## 📝 Description du projet

Ce projet s'inscrit dans le cadre d'une Situation d'Apprentissage et d'Évaluation (SAÉ) pour les étudiants de première année d'ingénierie à CY-Tech (2024-2025). L'objectif principal est de développer une application Java permettant de débruiter des images en utilisant l'Analyse en Composantes Principales (ACP) sur des patchs d'image.

### Principe de la méthode
Le débruitage se base sur l'hypothèse que le signal (image) est parcimonieux, régulier et contient des informations redondantes. La méthode implémentée suit les étapes suivantes :
1. Décomposition de l'image bruitée en patchs
2. Analyse en composantes principales des patchs
3. Reconstruction parcimonieuse avec seuillage
