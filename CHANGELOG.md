# MovieOh Changelog

## Version 7.0.0
### Added
- Full UI migration from XML/Views to Jetpack Compose (Material 3).
- Single-activity architecture with Navigation Compose (splash/home/detail).
- ViewModels migrated from LiveData to StateFlow with immutable UiState.
- Gradle Version Catalog (gradle/libs.versions.toml) replaces buildSrc and the dependencies submodule.
- Compose design system in the uikit module (theme, Montserrat typography, SectionHeader, NetworkImage).
- Coil replaces Glide for image loading; lottie-compose replaces lottie; Compose shimmer replaces Facebook Shimmer.
### Changed
- Migration Gradle 8.7 -> 9.6.1 and Android Gradle Plugin 8.5.1 -> 9.2.1 (built-in Kotlin).
- Migration Android SDK API 35 -> 37.
- Hilt migrated from kapt to KSP.
- Retrofit 3.x / OkHttp 5.x / Coroutines 1.11.
- Jacoco and SonarQube configuration rewritten in Kotlin DSL (task `sonar`, jacoco 0.8.13).
- ktlint 14.x and detekt 1.23.8 reconfigured for Compose; formatting delegated to ktlint.
- GitHub Actions updated: no git submodules, Gradle cache via setup-gradle, release tag read from the version catalog.
### Removed
- buildSrc module and the external dependencies git submodule.
- ViewBinding/DataBinding, RecyclerView adapters, BaseActivity and XML layouts.
- Unused dependencies (Room, ViewPager2, Glide, Material Components for Views, AppCompat).

## Version 6.1.0
### Added
- Migration Android SDK API 35
- Update Detekt

## Version 6.0.0
### Added
- Migration Gradle 8.0.2 -> 8.5.1
- Migration Android Gradle Plugin 8.0 -> 8.7
- Migration Android SDK API 34
- Migration Github Actions

## Version 5.0.0
### Added
- Migration Gradle 7.2.1 -> 8.0.2
- Migration Android Gradle Plugin 7.3.3 -> 8.0
- Migration Android SDK API 33
- Migration Java17
- Migration Github Actions

## Version 4.2.0
### Added
- The list of platforms in the movie detail was implemented.

## Version 4.1.0
### Added
- The adapters were migrated, only one will be used in common.
- Updated interfaces to fun interfaces.
- Update Github Actions.
- Update StatusBar.

## Version 4.0.0
### Added
- Update yml for actions.
- Add actions lint.

## Version 3.0.0
### Added
- Add actions for Publish in google play.
- Change README

## Version 2.0.0
### Added
- Was update for active track alpha in google play.

## Version 1.0.0
### Added
- Movie List
- Movie Detail