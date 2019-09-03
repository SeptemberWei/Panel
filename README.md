# Panel
replace old panel

替换wilson panel项目，在此基础上维护

root project build.gradle文件做如下修改:
buildscript {
    repositories {
        google()
        maven{ url 'http://maven.aliyun.com/nexus/content/groups/public/'}
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.3.2'
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
        classpath 'com.jakewharton:butterknife-gradle-plugin:9.0.0-rc2'
    }
}

allprojects {
    repositories {
        google()
        maven{ url 'http://maven.aliyun.com/nexus/content/groups/public/'}
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}

ext {
    compileSdkVersion = 28
    minSdkVersion = 26
    targetSdkVersion = 28
}

使用：在app build.gradle中添加以下依赖
dependencies {
	        implementation 'com.github.SeptemberWei:Panel:1.0.0'
}
