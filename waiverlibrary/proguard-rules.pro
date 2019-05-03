# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
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

#-keep class com.yombu.waiverlibrary.YombuWaiver { *; }
#-keep class com.yombu.waiverlibrary.YombuWaiver$Gender { *; }
#-keep class com.yombu.waiverlibrary.callbacks.YombuInitializationCallback { *; }
#-keep class com.yombu.waiverlibrary.callbacks.YombuWaiverProcessingCallback { *; }
#
#
#-keep class org.spongycastle.** {*;}
#
## The -dontwarn option tells ProGuard not to complain about some artefacts in the Scala runtime
#-dontwarn android.support.**
#-dontwarn android.app.Notification
#-dontwarn org.apache.log4j.**
#-dontwarn com.google.common.**
#-dontwarn okio.**
#-dontwarn retrofit2.Platform$Java8
#-dontwarn com.google.android.gms.internal.zzbga
#-dontwarn org.spongycastle.**
#-dontwarn afu.org.checkerframework.checker.**
#-dontwarn org.checkerframework.checker.**
#-dontwarn com.google.errorprone.annotations.**