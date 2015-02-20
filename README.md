# 2015-target
##Main Branch 
Authored by 
* Calvin Henry
* Atif Niyaz
* Andrew Bass

###About

The objective of this year's vision code is to track yellow totes. To achieve this result, we first take an image from the camera and take it through two fiters. The first one is the yellow filter. This filter focuses on just converting the image to a picture that just shows where yellow exists. From there it is sent to a Canny which will focus on edge detection.

Then the filtered image is taken into what is called a "Processor". The processor takes the filtered image and looks for contours. The contours are taken into a loop and the processor spits out the biggest contour. Then a rectangle is bound around the biggest contour.

Based on how big the rectangle is, the rectangle is used to determine how far away the tote is away from the center of the camera. A value called offset is calculated based on the difference between the center of the tote and the center of the camera. A value called distance factor is also calculated. This value is determined based on how much the tote takes up the camera.

The offset value and distance factor value are then passed through NetworkTables to the SmartDashboard which could be used to manipulate the robot to do fantastic things! The distance factor is the "magnitude" and the offset factor is the "rotation".

###Using the Vision Code

Download the binaries that are located in /jars folder. Within it, there are two .jar files, one is VisionTool.jar and one is VisionRunTime.jar. The VisionTool is used for looking at the camera and adjusting Hue Saturation Value (HSV) values through the graphical user interface. These values are saved in a special place. The VisionRunTime is primarily used for real-time vision during matches. The graphical user interface is a simple exit button yet it works exactly like the VisionTool.

In order to even run these .jar files, it is mandatory to download openCV binaries. The openCV binaries cannot be greater than or equal to version 3.0.0. Once downloaded, the location of opencv_X.X.X and opencv_ffmpegX.X.X (a library used to decode video files) should be added to the PATH of the system. Once done, the .jar files should successfully run without issue.

##Cascade branch

Authored by
* Chris Kannmacher
* Andrew Dennison

The Cascade Branch of the code is an alternate method that we would optimally use for the vision code in 2015. Cascade works by taking what are called positives and negatives. Positives are basically pictures of the tote. Negatives are everything else. These positives and negatives are processed through and openCV creates an XML file that should, when imported, bound a box around the tote. After that point the rectangle is processed like how it would be in the master branch.
