# Functional programming common patterns

This project shows the most common functional programming patterns, written in Scala.

Each of the patterns developed here have the following:

- Scala domain objects to cover the example
- ScalaTest suite that covers:
  1. A feature covering the necessity of the pattern. 
     
    ```
     //Pattern necessity
      feature("Pattern not used should work as well") {

    ```
  2. A feature covering the pattern in action

    ```
    //Pattern in action
      feature("Lens pattern in action!") {
  
    ```

**Note:** In order to understand the patterns and see the use cases, I recommend to read not only the pattern code, 
but the domain classes and the tests (and execute them).

### List of patterns:

| Pattern               | Description (see more detailed descriptions in code) |
| --------------------- | ------------------------------------------------------- |
| *Lenses abstraction*  | Abstraction that scale updates while preserving immutability and presenting a decent API to the user [[link to pattern]](https://github.com/jdreyesp/fppatterns/blob/master/src/main/scala/com/jdreyesp/examples/fppatterns/lens/Lens.scala) |
| *Reader abstraction*  | Abstraction monad that decouples reading from environment and/or dependency injection. For example, it can be used to inject a repository into a service without using the repository as a constructor parameter [[link to pattern]](https://github.com/jdreyesp/fppatterns/blob/master/src/main/scala/com/jdreyesp/examples/fppatterns/reader/Reader.scala) | 


### Run the tests
  
  As it's a Maven based project, you can run the tests as follows:
  `mvn clean test`
  

Happy Hacking! :smiley:


