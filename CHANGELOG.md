# MovieOh Changelog

## Version 7.0.0
### Added
- Full UI migration from XML/Views to Jetpack Compose (Material 3).
- Single-activity architecture with Navigation Compose and a bottom navigation bar (Home/Favorites/Settings), plus the movie detail screen.
- Favorites: mark movies from the like/share bottom sheet (long-press on any poster) and browse them in their own tab, persisted with DataStore Preferences.
- Settings screen: theme selector (Light/Dark/System, persisted and applied live), app version and TheMovieDB attribution.
- Disney+-style Home: "For You" header, infinite auto-scrolling hero carousel with the upcoming movies in portrait poster format, and bold section titles.
- Disney+-style movie detail: key-art hero fading into the background, centered title and metadata line (rating, year, runtime, genres), full-width Play button opening the first available platform, and My list/Share actions row.
- Streaming platforms per movie from the TMDB watch/providers endpoint (JustWatch data): each provider shown once with its best availability (Streaming/Rent/Buy), country resolved from the device locale, provider logos linking straight to the platform when the movie homepage points to it, and the mandatory JustWatch attribution.
- Fluid transitions: fade between tabs, push/pop slide into the movie detail, crossfade between loading/error/content states and on image loading (Coil), and scale/alpha page transforms in the hero carousel.
- Loading skeletons that mirror each screen's real layout (Home hero + sections, Favorites grid, movie detail).
- Splash migrated to the official AndroidX SplashScreen API: drawn by the system from the first frame of the cold start, with no artificial delay.
- Home page de-duplication: popular multi-genre movies appear in a single row instead of topping every genre carousel.
- Gradle Version Catalog (gradle/libs.versions.toml) replaces buildSrc and the dependencies submodule.
- Compose design system in the uikit module (cinematic purple/magenta palette with gradients, Montserrat typography across all Material text styles, SectionHeader, NetworkImage, GradientButton).
- ViewModels migrated from LiveData to StateFlow with immutable UiState.
- Coil replaces Glide for image loading; Compose shimmer replaces Facebook Shimmer.
### Changed
- Migration Gradle 8.7 -> 9.6.1 and Android Gradle Plugin 8.5.1 -> 9.2.1 (built-in Kotlin).
- Migration Android SDK API 35 -> 37.
- Hilt migrated from kapt to KSP.
- Retrofit 3.x / OkHttp 5.x / Coroutines 1.11.
- Jacoco and SonarQube configuration rewritten in Kotlin DSL (task `sonar`, jacoco 0.8.13).
- ktlint 14.x and detekt 1.23.8 reconfigured for Compose; formatting delegated to ktlint.
- GitHub Actions updated: no git submodules, Gradle cache via setup-gradle, release tag read from the version catalog.
- GitHub Actions workflows unified into reusable workflows (`android_quality`, `android_qa_apk`) shared by validation, deploy, publish and manual artifact builds, removing a redundant double lint run per push.
- Actions bumped to their latest majors: checkout@v7, setup-java@v5, upload-artifact@v7, gradle/actions/setup-gradle@v6, upload-google-play@v1.1.5.
- `kotlin { compilerOptions }` moved to the top level of `app/build.gradle.kts`, out of the `android {}` block, to match its real `Project` receiver.
### Fixed
- Double bottom inset on the tab screens (the Scaffold already accounts for the bottom bar) and dark-mode cold start flashing the wrong background color.
- Redundant `isShrinkResources = false` removed from the `qa`/`debug` build types (only meaningful with `isMinifyEnabled = true`).
### Removed
- buscala.tv unofficial API replaced by TMDB watch/providers, removing its dedicated OkHttp/Retrofit stack and `BASE_URL_PLATFORMS` build config.
- `StreamEntity` and the hardcoded 4-platform detection, replaced by generic homepage-vs-provider matching that works with any platform.
- Lottie dependency and asset (the Compose splash was replaced by the system splash).
- buildSrc module and the external dependencies git submodule.
- ViewBinding/DataBinding, RecyclerView adapters, BaseActivity and XML layouts.
- Unused dependencies (Room, ViewPager2, Glide, Material Components for Views, AppCompat).
- Unused drawable/color resources and empty scaffolding directories left over from the migration.
- Dead `REPO_USERID`/`REPO_TOKEN` CI secrets no longer referenced by the build.

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
