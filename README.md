# THEOPlayerSample
demonstration of THEOPlayer Android SDK features 

This Android project is an example how to integrate [THEOplayer](https://www.theoplayer.com) into an Android app.
There is a step-by-step [guide](https://support.theoplayer.com/hc/en-us/articles/360000779729-Android-Starter-Guide) for this project, we suggest you to follow it for better insights.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

* Download and install Android Studio. 
* Obtain a THEOplayer [Android SDK](https://support.theoplayer.com/hc/en-us/categories/115000161065-SDK) license. 
If you don't have a license yet, contact your sales contact or email us at [support@theoplayer.com](mailto:support@theoplayer.com).

### Include THEOplayer Android SDK in the project

Once you obtained the license, you need copy it into the ``` app/libs ``` folder of the project.

### Link your SDK file in ```build.gradle```

In the module-level ```build.gradle``` file (```app/build.gradle```) use the proper name of your SDK file

```
dependencies {
  implementation(name: 'FILENAME_OF_YOUR_SDK_FILE', ext: 'aar')

  def applicationDependencies = deps.mainApplication
  def unitTestDependencies = deps.unitTesting
  def acceptanceTestDependencies = deps.acceptanceTesting

  implementation applicationDependencies.kotlin
  implementation applicationDependencies.kotlinCoroutines
  implementation applicationDependencies.kotlinCoroutinesAndroid
  implementation applicationDependencies.appCompat
  implementation applicationDependencies.constraintLayout
  implementation applicationDependencies.timber
  implementation applicationDependencies.material
  implementation applicationDependencies.gson



  testImplementation unitTestDependencies.junit
  androidTestImplementation acceptanceTestDependencies.testRunner
  androidTestImplementation acceptanceTestDependencies.espressoCore
}

```

For example if the SDK file is called ```theoplayer.aar```, then your ```build.gradle``` file should look like this :

```
dependencies {
  implementation fileTree(dir: 'libs', include: ['*.jar'])

  implementation(name:'theoplayer', ext:'aar')

  def applicationDependencies = deps.mainApplication
  def unitTestDependencies = deps.unitTesting
  def acceptanceTestDependencies = deps.acceptanceTesting

  implementation applicationDependencies.kotlin
  implementation applicationDependencies.kotlinCoroutines
  implementation applicationDependencies.kotlinCoroutinesAndroid
  implementation applicationDependencies.appCompat
  implementation applicationDependencies.constraintLayout
  implementation applicationDependencies.timber
  implementation applicationDependencies.material
  implementation applicationDependencies.gson



  testImplementation unitTestDependencies.junit
  androidTestImplementation acceptanceTestDependencies.testRunner
  androidTestImplementation acceptanceTestDependencies.espressoCore
}
```

## Important note

Besides of adding the player @aar dependency to your build.gradle file, You also need to add Gson dependency as well, otherwise app will crash. 
You can add the Gson dependency using the line below:

```

implementation 'com.google.code.gson:gson:2.8.5'

```




## Build the project

### In Android Studio

Just open the project, let Android Studio to install the dependencies and build the project.

### With Gradle

In a terminal navigate to the project folder and run:

```
./gradle assembleDebug
```

The generated APK file (what you need to install on your device) will be available in the ```app/build/outputs/apk/``` folder.


## License

This project is licensed under the BSD 3 Clause License - see the [LICENSE](LICENSE) file for details


