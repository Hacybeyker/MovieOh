# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#-keep class com.hacybeyker.movieoh.commons.base.BaseActivity {*;}
#-keep class com.hacybeyker.movieoh.ui.home.HomeActivity {*;}
#-keep class com.hacybeyker.movieoh.ui.home.viewmodel.HomeViewModel {*;}

#-keep class com.hacybeyker.movieoh.data.model.remote.response.ResultResponseModel {*;}
#-keep class com.hacybeyker.movieoh.data.model.remote.response.MovieResponseModel {*;}

#-obfuscationdictionary 'avengers_dictionary.txt'
#-classobfuscationdictionary 'avengers_dictionary.txt'
#-packageobfuscationdictionary 'avengers_dictionary.txt'

#-keepattributes Signature, InnerClasses, EnclosingMethod,Exceptions
#-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
#-keepclassmembers,allowshrinking,allowobfuscation interface * {
#    @retrofit2.http.* <methods>;
#}

-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

#-keep class com.google.crypto.** { *; }

#-keepnames class * extends java.io.Serializable
#-keepnames class * extends android.os.Parcelable