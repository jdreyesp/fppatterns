package com.jdreyesp.examples.fppatterns.reader.model

import java.util.Calendar

import com.jdreyesp.examples.fppatterns.reader.Reader
import com.jdreyesp.examples.fppatterns.reader.model.Amount.Amount
import com.jdreyesp.examples.fppatterns.reader.repository.AccountRepository

import scala.util.{Failure, Success, Try}

/**
  * Created by jreyes on 28/6/17.
  *
  *
  * This trait conforms to the 'building abstractions through incremental composition' approach,
  *  a technique used as a norm in functional programming. See AccountService object to see an example
  *
  */

sealed trait AccountService {

  def today = Calendar.getInstance.getTime

  def debit(a: Account, amount: Amount) : Reader[AccountRepository, Try[Account]]
  def credit(a : Account, amount: Amount) : Reader[AccountRepository,Try[Account]]
  def lookupAccount(id: String) : Reader[AccountRepository,Try[Account]]
  def storeAccount(d: Account) : Reader[AccountRepository,Try[Account]]
}

object AccountService extends AccountService {

  override def debit(a: Account, amount: Amount): Reader[AccountRepository,Try[Account]] =
    Reader(_ => {
    if(a.balance.amount < amount) {
      Failure(new Exception("Insufficient balance in account"))
    } else
      Success(a.copy(balance = Balance(a.balance.amount - amount)))
  })

  override def credit(a: Account, amount: Amount): Reader[AccountRepository, Try[Account]] = {
    Reader(_ => Success(a.copy(balance = Balance(a.balance.amount + amount))))
  }

  /**
    * Uses of AccountRepository in this method
    * @param id
    * @return
    */
  override def lookupAccount(id : String): Reader[AccountRepository, Try[Account]] = {
    Reader(_.query(id).flatMap {
      case Some(account) => Success(account)
      case _ => Failure(new Exception(s"Account with id $id not found"))
    })
  }

  override def storeAccount(account: Account): Reader[AccountRepository, Try[Account]] = {
    Reader(_.store(account).flatMap {
      case Some(newAccount) => Success(newAccount)
      case _ => Failure(new Exception(s"Account with id ${account.no} not properly stored"))
    })
  }
  /**
    * Extending Function1 as a monad, supporting flatmap with functions,
    *  you can use composition to create complex operations, using currying.
    *
    *  The first thing that you have to do is extend Function1 to support flatmap with functions. This is performed
    *
    *  From your client you can call this curried method like this:
    *
    *  complexOperation(Account(...)).run(AccountRepositoryOracleDB)
    *
    * @param id: String
    * @return
    */
  def complexOperation(id : String) : Reader[AccountRepository, Try[Account]] = {
    for {
      a <- lookupAccount(id) //it returns an account with balance=30
      b <- debit(a.get, 30)
      c <- credit(b.get, 60)
      d <- debit(c.get, 60)
      accountFn <- storeAccount(d.get)
    } yield accountFn

  }

}
