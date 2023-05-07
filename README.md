
# MovieOh!  🎥

[![GithubActions](https://github.com/hacybeyker/Movieoh/actions/workflows/android_publish.yml/badge.svg?branch=master)](https://github.com/Hacybeyker/MovieOh/actions) [![Sonar Cloud](https://sonarcloud.io/api/project_badges/measure?project=com.hacybeyker.movieoh&metric=alert_status)](https://sonarcloud.io/project/overview?id=com.hacybeyker.movieoh) [![Google Play](https://img.shields.io/badge/GooglePlay-3DDC84?&logo=android&logoColor=white&labelColor=101020)](https://play.google.com/store/apps/details?id=com.hacybeyker.movieoh) [![API](https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=23) [![API](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://img.shields.io/badge/License-Apache%202.0-blue.svg)

This project consumes the [TheMovieDB](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) movie api and shows various categories of movies and when      
selecting a specific one, it shows a detail with more information about the movie.

## Setup

1. Run the following git commands:

 ``` text 
 git submodule init  
```     
 ``` text 
 git submodule update  
``` 

2. Add variables in local.properties:

 ``` text 
 KEY_PROD=YOUR_KEY  
 KEY_QA=YOUR_KEY 
 ``` 

## Project
Architecture and libraries that were used in the project:
- Clean Architecture
- MVVM
- Kotlin
- Hilt
- Mockito
- Coroutines
- Retrofit
- Glide

these support libraries were also used:

- Lint
- Detekt
- Ktlint
- Jacoco
- Sonarcube
- Chucker

### Publish/Api

- [Google Play](https://play.google.com/store/apps/details?id=com.hacybeyker.movieoh)
- [TheMovieDB](https://developers.themoviedb.org/3/getting-started/introduction).

## Images

<p align="center">      
 <img width="200" height="350" alt="MovieOh Home" src="https://drive.google.com/uc?export=view&id=14vsx4xQKC4C9guW4elhAo4EbEWgnDfkp"/>    
 <img width="200" height="350" alt="MovieOh Detail" src="https://drive.google.com/uc?export=view&id=1iHD1MaS3BQYwgveg4avoOHIb66kJRHj3"/> </p>   

## License

# License
```xml
Designed and developed by 2022 hacybeyker (Carlos Osorio)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```