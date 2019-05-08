Java Code Coverage Tool
===========================

An Automated coverage collection tool that can capture the statement coverage for the program under test.

##### Phase I #####

The tool uses ASM byte-code manipulation framework to manipulate the bytecode. Bytecode manipulation is performed on the fly by a 
Java Agent whcih makes use of the Intrumentation API. A JUnit listener is used to to capture the start and end events for each JUnit
test method. The agent jar file and the JUnit listener class can be integrated with any maven project to perform code coverage. This is done by updating the pom.xml file present in the maven project rool directory.

Following needs to be added to the pom.xml file.
1) Replace [path-to-your-agent.jar] with your java agent jar’s absolute path, and 
replace [YourListener] with your JUnit listener’s full name.
```
<plugin>
<groupId>org.apache.maven.plugins</groupId>
<artifactId>maven-surefire-plugin</artifactId>
<configuration>
<argLine>-javaagent:[path-to-your-agent.jar]</argLine>
<properties>
<property>
<name>listener</name>
<value>[YourListener]</value>
</property>
</properties>
</configuration>
</plugin>
```
2) Add the agent.jar file as a dependency as it is used by the 'Listener.java' file. 
(The following dependency was written for my 'JPAgent.jar' file which is attached 
in the repository for your reference)
```
<dependency>
<artifactId>TestCompetition.JavaAgent</artifactId>
<groupId>JPAgent</groupId>
<version>1.0</version>
<scope>system</scope>
<systemPath>${basedir}/JPAgent.jar</systemPath>
</dependency>
```
3) Add the asm package as a dependency if needed as it is used by multiple files.
```
<dependency>
<groupId>org.ow2.asm</groupId>
<artifactId>asm</artifactId>
<version>5.0.3</version>
</dependency>
```
4) Add the junit package as a dependency if needed.
```
<dependency>
<groupId>junit</groupId>
<artifactId>junit</artifactId>
<version>4.11</version>
<scope>test</scope>
</dependency>
```

When firing “mvn test”, tool will output a file named “stmt-cov.txt” under the project under test. This file will include
statement coverage information for each test method. Each line in the file represents a test method or a covered statement, while the test
method line will have the [TEST] prefix. Each test method will be followed by the set of statements covered by it.Each test method is 
represented by the full name of the test class +”:”+the test method name, while each statement is represented by the 
full name of the class +”:”+the line number.

##### Phase II - Incomplete Solution #####
##### Basic Implementation #####
1) Use ClassNode to get a collection of MethodNode
2) The collection of MethodNode can provide each method’s name, each method’s access flags, each method’s description, and each method’s signature etc.
3) Then, use method localVariables of class MethodNode to get a collection of LocalVariableNode. We can get each parameter’s name, each parameter’s description, and each parameter’s signature 
4) For the class field value, we can use method fields of class  ClassNode to get a collection of class FieldNode. Then we can get each field’s name, each field’s description, and each field’s value.
##### The reason that this solution cannot satisfy the requirement #####
1) It only can get the initial field values of every class. We also should get the field values after these field values change because these field values might possibly change through method invocation and constructor.
2) According to the result, the methods of some class are not from project commons-lang itself, and it is from JUnit class. Based on the requirement, the result should not include other methods except for project commons-lang itself.
3) The result shows that the procedure was access to each class for only one time. However, it should be access them for multiples times.
4) In conclusion, this is not the way to satisfy the requirement.



