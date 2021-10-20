# Otopost
An app to manage posts such as create, update, show the post list and detail also delete the post.
## Minimum Requirements
### Software
 - Android Studio v4.1.1
 ### Modules
 - Kotlin v1.4.32
 - Gradle v4.1.1
## Versioning Guidelines
This project use [Semantic Versioning 2.0.0](https://semver.org/) rules.
In `build.gradle` (Module: app):
- Increase the **versionCode**
- Change the **versionName**

example:
- Before
```
defaultConfig {  
  ...
  versionCode 1  
  versionName "1.0.0"  
  ...
}
```
After:
```
defaultConfig {  
  ...
  versionCode 2  
  versionName "1.1.0"  
  ...
}
```
# Production Guidelines
## Create Keystore
The keystore is only need to create once and we will use it as long as its not expired. So, don't lose it. Keystore is used to build apps for google play store release

Follow this [guide](https://developer.android.com/studio/publish/app-signing#generate-key) to create keystore.
## Build APK/AAB for Production
For production you can use Android Studio IDE by going to **Build > Generate Signed Bundle/APK...** (then follow the instructions)

But here is for someone who love terminal:
For using terminal you need to define sign in config in your `local.properties`, like this:
```
app.release.store.file=path/to/keystore/file  
app.release.key.alias=release-key-alias  
app.release.store.password=keystore-password  
app.release.key.password=release-key-password
```
then go ahead:
- Flavor Production (.apk)
```
 ./gradlew assembleProductionRelease
```
- Flavor Production (.aab)
```
 ./gradlew bundleProductionRelease
```
# Development Guidelines
This project apply [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html), [MVVM and Repository Pattern](https://developer.android.com/jetpack/guide) (but in here we use [Fuel Kotlin](https://github.com/kittinunf/fuel) instead of Retrofit), and also [SOLID Principle](https://www.baeldung.com/solid-principles).
## Dependencies
- Dependency Injection: [Koin](#)
- Navigation: [Android Jetpack Navigation](#)
- HTTP Client: [Fuel Kotlin](#)
- Database: [Room](#)
## Flavors and Build Type
There are three flavors in this project, those are **development**, **staging**, and **production**. Each flavor has **debug** and **release** build type. Use **debug** type to debug the apps, **release** type will disable the apps from writing logs, apply proguard, and shrink resource.
## Build APK for Testing
Actually you can also do this using Android Studio IDE by going to **Build** -> **Generate Signed Bundle/APK...** (then follow the instructions and don't forget you need keystore for this)
But here is also for terminal lover:

- Flavor Development
```
 ./gradlew assembleDevelopment
```
- Flavor Staging
```
 ./gradlew assembleStaging
```
## Directory structure
#### Directory Tree
```
.
+-- app
|   +-- presentation
|   +-- util
+-- common
|   +-- constant
|   +-- extension
|   +-- mapper
|   +-- result
+-- core
|   +-- <your_module_name>
|   |    +-- model
|   |    +-- repo
|   |    +-- source
|   +-- <your_another_module_name>
|   |    +-- model
|   |    +-- repo
|   |    +-- source
+-- framework
|    +-- core
|    |   +-- <your_module_name>
|    |   |    +-- mapper
|    |   |    +-- model
|    |   |    +-- repo
|    |   |    +-- source
|   |    +-- <your_another_module_name>
|    |     |    +-- mapper
|    |   |    +-- model
|    |   |    +-- repo
|    |   |    +-- source
|    +-- http
|    +-- database
.
```
#### Explanation
There are four main modules those are `app`, `common`, `core`, and `framework`
- `app`: Contains app UI, the app face it self, and also where user interact with the app, *can't be implement to another module*.
- `app.presentation`: Contains activity, fragment, adapter, and viewmodel.
- `app.util`: Contains util that help the UI, only define this util if it cannot be used for another main modules, or its for UI specific.
.
 - `common` : Like its name, common module means this module *can be implement by all modules*.
 - `common.constant`: contains constant such as date format, codes, etc.
 - `common.extension`: contains your kotlin extension.
 - `common.mapper`: contains mapper interface such as map response to model, model to entiry, etc.
 - `common.result`: contains function's return value wrapper and error model.
.
- `core`: this is the shape of the app, contains app business process, and it is just interface.
- `core.<your_module_name>`: define your module name, example: `core.post`.
- `core.<your_module_name>.model`: contains the data layer of clean arch
- `core.<your_module_name>.repo`: contains the repository interface
- `core.<your_module_name>.source`: contains interfaces of the datasource, such as api, database, or preferences.
.
- `ramework`: this module contains the implementation of `core` module and another library integration such as database, http client, etc.
- `framework.core`: contains implementation of `core` module
- `framework.core.<your_module_name>.mapper`: contains implementation of mapper used by this module, implement mapper interface from `common` module.
- `framework.core.<your_module_name>.model`: contains response model, request model, and entity model of `your_module`.
- `framework.core.<your_module_name>.repo`: contains the repository implementation of `core` module.
- `framework.core.<your_module_name>.source`: contains the datasource implementation of `core` module.
- `framework.http`: contains http client interface and its implementation
- `framework.database` contains database instance.
 
## Environment variables
All environment variable, base url, sensitive data, keystore credential, or something that has limited access need to define in `local.properties`. Environment variable used in this project as below:

`local.properties`:
```
app.release.store.file=path/to/keystore/file  
app.release.key.alias=release-key-alias  
app.release.store.password=keystore-password  
app.release.key.password=release-key-password  
  
field.dev.base.url=https://limitless-forest-49003.herokuapp.com  
field.stg.base.url=https://limitless-forest-49003.herokuapp.com  
field.pro.base.url=https://limitless-forest-49003.herokuapp.com
```
