-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*
-keepattributes SourceFile, LineNumberTable
-keepattributes EnclosingMethod

# Retrofit
-dontwarn retrofit.**
-dontwarn com.squareup.okhttp.**
-dontwarn okio.**

-keep class retrofit.** { *; }

# Gson
-keep class com.google.gson.** { *; }
-keep class com.google.inject.** { *; }

# Jackson
-keepnames class com.fasterxml.jackson.** { *; }
-dontwarn com.fasterxml.jackson.databind.**
-keep class org.codehaus.** { *; }
-keepclassmembers public final enum org.codehaus.jackson.annotate.JsonAutoDetect$Visibility {
    public static final org.codehaus.jackson.annotate.JsonAutoDetect$Visibility *;
}
-keep public class gov.peacecorps.medlinkandroid.rest.models.** { *; }

# Butterknife
-dontwarn butterknife.internal.**

-keep class butterknife.** { *; }
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}