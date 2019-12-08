# Clean-Architecture Plugin

Generate the Clean-Architecture directory structure from Robert C. Martin (Uncle Bob) in your flutter projects. 
It is inspired by @ResoCoder [Clean Architecture for Flutter Course](https://github.com/ResoCoder/flutter-tdd-clean-architecture-course). 
## How To Install
- ~~Android Studio / IntelliJ IDEA -> Plugins -> Browse repositories~~
- ~~Search: Clean Architecture for Flutter~~
- Install
- Restart IDE

## How To Use
- Right click on or anywhere in your destination folder
- New -> Clean Generator -> Flutter
- Enter [feature_name] in the dialog or let it be empty to create structure in current directory

## What will be created
- [feature_name]
  - data
    - data_sources
    - models
    - repositories
  - domain
    - entities
    - use_cases
    - repositories
  - presentation
    - manager
    - pages
    - widgets
  
## Source
- https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html
- https://resocoder.com/2019/08/27/flutter-tdd-clean-architecture-course-1-explanation-project-structure/