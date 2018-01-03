package com.jdreyesp.examples.fppatterns.reader.repository

import scala.util.Try

/**
  * Created by jreyes on 2/1/18.
  * Repository abstraction to perform common repository operations on entities
 *
  * @tparam A Entity to query / create
  * @tparam IdType Entity ID type
  */
trait Repository[A, IdType] {
  def query(id: IdType) : Try[Option[A]]
  def store(entity: A) : Try[Option[A]]
}
