# Functional programming common patterns

This project shows the most common functional programming patterns in Scala 2.12.

Each of the patterns developed here have the following:

- Scala domain objects to cover the example
- ScalaTest suite that covers:
  1. A feature convering the necessity of the pattern. 
     
    ```
     //Pattern necessity
      feature("Pattern not used should work as well") {

    ```
  2. A feature covering the pattern in action

    ```
    //Pattern in action
      feature("Lens pattern in action!") {
  
    ```


### List of patterns:

| Pattern           | Description |
| ----------------- | ------------------------------------------------------- |
| *Lenses pattern*  | Abstraction that scales when preserving immutability and wanted to present a decent API to the user: `case class Lens[O,V](get : O => V, set : (O, V) => O)` |


  
  



