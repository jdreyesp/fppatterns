package com.jdreyesp.examples.fppatterns.reader.repository

import com.jdreyesp.examples.fppatterns.reader.Account

import scala.util.Try

/**
  * Created by jreyes on 2/1/18.
  */
trait AccountRepository extends Repository[Account, String] {
  override def query(id: String): Try[Option[Account]]
  override def store(entity: Account): Try[Option[Account]]
}
