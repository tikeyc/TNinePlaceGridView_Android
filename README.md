# TNinePlaceGridView_Android
Android版九宫格、图片浏览等    

iOS版：https://github.com/tikeyc/TNinePlaceGridView  

![image](https://github.com/tikeyc/TikeycAndroid/blob/master/Readme/screen2.gif)      

详情访问我的简书：http://www.jianshu.com/p/99a2a72d8fef   


# 添加依赖：

1——：gradle
Step 1：添加maven { url 'https://jitpack.io' } 到project的build.gradle         
allprojects {    
   repositories {    
      ...    
      maven { url 'https://jitpack.io' }    
   }    
}    

Step 2:compile 'com.github.tikeyc.TNinePlaceGridView_Android:tnineplacegridviewlibrary:v1.0'到你app的build.gradle    
dependencies {     
    compile 'com.github.tikeyc.TNinePlaceGridView_Android:tnineplacegridviewlibrary:v1.0'    
}    
2——：或者直接下载完整项目，import一个module：tnineplacegridviewlibrary    

#如何使用：    

ninePlaceGridView.setImageNames(imageNames)
