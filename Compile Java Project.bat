@echo off

set MainFile=Main.java
set ProjectName=WildWidgetsWarehouse.jar
set ProjectPath=%UserProfile%\Documents\NetBeansProjects\2080W13As1_Thomas_Brandon\src\pkg2080w13as1_thomas_brandon

set path=C:/Program Files/Java/jdk1.7.0_11/bin

echo Welcome To Brandon's NetBeans Project Compiler.
echo.
::dir %ProjectPath%\*.java
::echo.
::echo.

for %%* in (%ProjectPath%) do set FolderName=%%~n*
for %%* in (%ProjectPath%\..\) do set ParentDirectory=%%~dpnx*
for %%* in (%ParentDirectory%\..\dist\lib) do set DistributionDir=%%~dpnx*

IF EXIST %UserProfile%\Desktop\lib rmdir /S/ Q %UserProfile%\Desktop\lib
IF EXIST %ParentDirectory%\Classes rmdir /S /Q %ParentDirectory%\Classes
IF NOT EXIST %ParentDirectory%\Classes mkdir %ParentDirectory%\Classes


echo Manifest-Version: 1.0 > %ProjectPath%\MANIFEST.MF
echo Created-By: 1.7.0_11-b21 (Oracle Corporation) >> %ProjectPath%\MANIFEST.MF
echo Class-Path: lib/std.jar >> %ProjectPath%\MANIFEST.MF
echo Main-Class: %FolderName%.Main >> %ProjectPath%\MANIFEST.MF

javac -d %ParentDirectory%\Classes %ProjectPath%/*.java -cp .;%DistributionDir%\*
::jar cmf %ProjectPath%\MANIFEST.MF %UserProfile%\Desktop\%ProjectName% -C %ParentDirectory%\Classes %FolderName%
::%SystemRoot%\system32\xcopy.exe %DistributionDir% %UserProfile%\Desktop\lib /S /Y /I
::java -jar %UserProfile%\Desktop\%ProjectName%

for %%* in (%ProjectPath%\..\) do set ProjectName=%%~dpnx*
for %%* in (%ProjectName:~0,-1%\..\) do set ProjectName=%%~dpnx*
for %%* in (%ProjectName:~0,-1%) do set ProjectName=%%~n*
set "ProjectName=%ProjectName%.jar"

jar cmf %ProjectPath%\MANIFEST.MF %DistributionDir%\..\%ProjectName% -C %ParentDirectory%\Classes %FolderName%

cls
echo Running Jar File..
echo.
java -jar %DistributionDir%\..\%ProjectName%

PAUSE