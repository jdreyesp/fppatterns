package com.jdreyesp.examples.fppatterns.reader.repository

import java.util.Calendar

import com.jdreyesp.examples.fppatterns.reader.{Account, Balance}

import scala.util.{Success, Try}

/**
  * Created by jreyes on 2/1/18.
  *
  * Implementation of a AccountRepository, performing operation over Accounts
  * through an Oracle database
  *
  */
object AccountRepositoryOracleDB extends AccountRepository {

  //This should query from DB
  override def query(id: String): Try[Option[Account]] = {

    val now = Calendar.getInstance.getTime
    Success(Some(Account(id, "Orange account", now, Balance(30), Seq())))

  }

  //This should store an account to DB
  override def store(entity: Account): Try[Option[Account]] = {
    Success(Some(entity))
  }
}
