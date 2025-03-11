This page will highlight all the pain points in KMM with compose multiplatform 
while a Instagram Clone

1. SVG Images
   - No solution from compose multiplatform to support this natively
   - Coil has library to support it but documentation is does not convey where to put the svg resources and  
   and how to access it
   - Solution: Convert them to xml and then compose multiplatform can support it
   - Or try this: https://github.com/JetBrains/compose-multiplatform/blob/master/tutorials/Image_And_Icons_Manipulations/README.md
   
2. PNG Images
   - No support for directly including all different dimensions of png images
   in the common folder
   - We need to either manually put the resources in android specific drawable-mdpi/drawable-hdpi .. 
   and ios specific 1x,2x... or use gradle automation
   - Solution: For Android use
   ```kotlin
   return painterResource(id = R.mipmap.auth_image)
   ```
   - For ios: **No solution yet**

3. Customize the status bar icon color
   - No good support from android & ios
   - So we will skip this
4. Paging 3 is KMP ready but not CMP 
   - https://youtrack.jetbrains.com/issue/CMP-6743/Support-Paging-3#:~:text=The%20androidx%20Paging%203%20library,this%20package%20is%20already%20commonized.
5. Dispatchers.Main is not available in all platform