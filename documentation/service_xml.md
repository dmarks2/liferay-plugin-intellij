Service XML
===========

1. [Line Marker for service.xml](#line-marker-for-servicexml)
2. [Inspections for service.xml](#inspections-for-servicexml)

Line Marker for service.xml
---------------------------

This plugin adds a line marker to the ```service.xml``` for each entity. Using this marker you can jump to 
the generated Model Implementation Java class. 

![service.xml](service_xml.png "service.xml")

Within the Java class you can jump back to the declaration in the ```service.xml```.

![Foo Model Impl](foo_impl.png "Foo Model Impl")

Inspections for service.xml
---------------------------

Some inspections are provided to check for common mistakes in the ```service.xml```.

Those are:

* Check for valid ```<namespace>``` expressions
* Check for valid ```<exception>``` names
* Check for duplicate ```<entity>``` 
* Check for duplicate ```<exception>``` 
* Check for duplicate ```<finder>``` 
* Check for duplicate ```<column>``` inside an entity 
* Check for a valid type for the primary key column
* Ensure that entities having a uuid also have a primary key column