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