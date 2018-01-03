package com.jdreyesp.examples.fppatterns.reader

/**
  * Created by jreyes on 3/1/18.
  *
  * In functional programming, it’s a common practice to compose and build computation pipelines
  * and defer evaluation until the end.
  *
  * The computation op: A => B is a Function1 that may need to be composed with a monadic pipeline
  * containing a List or an Option. You need monad transformers for this kind of composition.
  * But Function1 doesn’t have any transformer that can be used to stack up your computation into a monadic pipeline.
  * That's when Reader make sense, when you want to abstract Function1 as a monad, incorporating map and flatmap functions.
  *
  * This can be useful in dependency injection, environment parameters, or any kind of passing context.
  *
  * Reader can have a transformer, ReaderT, which can be used to compose other monads with the Reader (not covered here)
  */
case class Reader[R, A](apply: R => A) {

  def map[B](f: A => B): Reader[R, B] =
    Reader(r => f(apply(r)))

  def flatMap[B](f: A => Reader[R, B]): Reader[R, B] =
    Reader(r => f(apply(r)).apply(r))

}

