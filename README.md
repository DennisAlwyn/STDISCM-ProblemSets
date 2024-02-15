# STDISCM-ProblemSets

### If compiling from .java files
- Open a command terminal in the folder containing the files.
- Run this command to generate .class files:
  
  javac Main.java
  
- Run this command to generate the executable jar file:

  jar -cfve STDISCM-ProblemSets.jar Main *.class

### Executing the jar file
- Open a command terminal in the folder containing jar the file.
- Run this command to execute the jar file:

  java -jar --enable-preview STDISCM-ProblemSets.jar
