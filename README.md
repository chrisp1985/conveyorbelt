# Conveyor Belt Service

## Functionality

### Approach

I have used Component and Product enums that extend a general 'item' interface. The code checks whether the item in the slot
is a component (and can therefore be picked up) or a product. A less extensible version would be to specifically look for Components
A or B, but as we might get different products or components on the line in the future it seemed better to look for any component.

The driver of the service is in the ConveyorBeltService class in the 'process' method. This runs through the steps specified and
sets the conveyor belt to use. I've created a Config class for setting specifics within the service. This might be a bit overkill
at the moment, but if there are other elements we want to test or change within the class, we can just add to the Config class
rather than add lots of parameters to the method.

The primary test case (as asked for in the document) can be found in the ConveyorBeltServiceTests clas under canProcessSuccessfully(). It
provides output in the console as follows:

```text
Finished products: 22
Unused A: 0
Unused B: 5
```

Here, there were 22 completed products that made it to the end of the conveyor belt, along with 5 unused B items.

### Build Instructions

To execute, run `./gradlew clean build` to first build the jar manifest.

Then, in the build\libs directory the main class can be run via `java -jar .\conveyorbelt-1.0-SNAPSHOT.jar 150 3 5` where
the first argument is the stepCount, the second is the workerPair count and the third is the belt length.

Failure to supply any of these values will use the defaults as per the application.yml.

NOTE: The Worker name is not necessarily needed, but helps to identify which worker has taken what to help with debugging.

### Assumptions
- Items can only be placed onto the belt if the belt is empty. The requirements state:
  `there is time for a worker on one side of each slot to either take an item from the slot or replace an item onto the belt`

My assumption here is that 'replace an item onto the belt' means that a worker can only place an item if the slot is free. It
might be that 'replace an item' means that as an item is placed, anything already there can be picked up, but I assumed that
each worker was limited to one action per belt advance.

- We only count items that complete the conveyor belt length.
  Some items may be completed but not make it to the end of the conveyor belt. The function that counts the items looks at the
  last item on the belt only. If this is not the case (for example the finished product is packed in a box rather than adding
  back onto the line), we can just make the count as part of the placeFinishedProduct method.

### Scale
For testability, I have added process configuraton and the belt itself as parameters of the process. By adding dependencies in the
method, I have more flexibility within the tests.

I have also overloaded the ConveyorBelt to allow a list of pre-defined Components, again for test purposes. If the number of components
is insufficient, the rest of the process continues with automatically generated components.

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.4/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.4/gradle-plugin/packaging-oci-image.html)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

