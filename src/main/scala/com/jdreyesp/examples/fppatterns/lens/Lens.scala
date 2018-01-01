package com.jdreyesp.examples.fppatterns.lens

/**
  * Created by jreyes on 1/1/18.
  *
    The requirements of the algebra that such an abstraction (the Lens pattern) need to satisfy:

    Parametricity—The lens needs to be parametric on the type of the object (let’s call it O) that you need to update.
    And because each update is for a specific field of the object, the lens also needs to be parameterized on the type
    of the field being updated (let’s call it V).
    This gives you a basic contract of a lens as case class Lens[O, V] (..).

    ■ One lens per field—For every object, you need to have a lens for every field.
       This may sound verbose, and in some cases it may feel that way, but this could be improved with the use of macros.

    ■ Getter—The algebra of the lens needs to publish a getter for accessing the current value of the field.
      It’s simply a function: get: O => V.

    ■ Setter—The algebra of the lens needs to publish a setter.
      It should take an object and a new value and return a new object of the same type with the value of the field
      changed to the new value. It’s as obvious as having a function set: (O, V) => O.
  */
case class Lens[O,V](get : O => V, set : (O, V) => O)

object Lens {

  /**
    * We’ll define a generic compose function so that we don’t have to repeat the same code
    * for every pair of lenses that we compose.
    * Compose takes two lenses and a value, with the types of the lenses aligned for composition.
    * @param outer outer object to transform from
    * @param inner inner object (intermediate object)
    * @tparam Outer outer lens
    * @tparam Inner inner lens
    * @tparam Value value object to get
    * @return the outer object copied with desired update using lenses
    */
  def compose[Outer, Inner, Value](outer: Lens[Outer, Inner], inner: Lens[Inner, Value]) = {
    Lens[Outer, Value] (
      get = outer.get andThen inner.get,
      set = (outerObj, valueObj) => outer.set(outerObj, inner.set(outer.get(outerObj), valueObj))
    )
  }

}