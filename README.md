# JitPack 步骤

步骤一 项目根目录 build.gradle 添加

```
classpath 'com.github.dcendents:android-maven-gradle-plugin:2.1'
```

------------------------------------------------------------------------------------------------------------------------

步骤二 项目module目录 build.gradle 添加

```
plugins {
    //...
    id 'maven-publish'
}
```

```
afterEvaluate {
    publishing {
        publications {
            release(MavenPublication) {
            from components.release
            groupId = 'ejiayou.home.module'
            artifactId = 'home'
            version = '1.0.0'
            }
        }
    }
}
```

## 关于当前工程调试

请将 gradle.properties 文件 改为lib即可当前工程调试 请勿提交此文件isModuleType=lib上传

将以下两个文件注册activity同步, module只提供android.intent.action.MAIN 非module不可启动android.intent.action.MAIN配置

1.src->main->module->AndroidManifest.xml

2.src->main->AndroidManifest.xml

# lib module模式 app 集成模式

isModuleType=app