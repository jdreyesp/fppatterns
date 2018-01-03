package com.jdreyesp.examples.fppatterns.reader

import java.util.Calendar

import com.jdreyesp.examples.fppatterns.reader.Amount.Amount
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

sealed trait AccountServiceWithoutPattern {

  def today = Calendar.getInstance.getTime

  def debit(a: Account, amount: Amount) : Try[Account]
  def credit(a : Account, amount: Amount) : Try[Account]
  def lookupAccount(id: String, accountRepository: AccountRepository) : Try[Account]
  def storeAccount(d: Account, accountRepository: AccountRepository) : Try[Account]
}

object AccountServiceWithoutPattern extends AccountServiceWithoutPattern {

  override def debit(a: Account, amount: Amount): Try[Account] = {
    if(a.balance.amount < amount) {
      Failure(new Exception("Insufficient balance in account"))
    } else {
      Success(a.copy(balance = Balance(a.balance.amount - amount)))
    }
  }

  override def credit(a: Account, amount: Amount): Try[Account] = {
    Success(a.copy(balance = Balance(a.balance.amount + amount)))
  }

  /**
    * Uses of AccountRepository in this method
    * @param id
    * @return
    */
  override def lookupAccount(id : String, accountRepository : AccountRepository): Try[Account] = {
    accountRepository.query(id).flatMap {
      case Some(account) => Success(account)
      case _ => Failure(new Exception(s"Account with id $id not found"))
    }
  }

  override def storeAccount(account: Account, accountRepository : AccountRepository): Try[Account] = {
    accountRepository.store(account).flatMap {
      case Some(newAccount) => Success(newAccount)
      case _ => Failure(new Exception(s"Account with id ${account.no} not properly stored"))
    }
  }

  /**
    * Performs a complex operation
    *
    * @param id account ID
    * @param accountRepository: The injected account repository
    * @return account
    */
  def complexOperation(id : String, accountRepository: AccountRepository) : Try[Account] = {
    for {
      a <- lookupAccount(id, accountRepository) //it returns an account with balance=30
      b <- debit(a, 30)
      c <- credit(b, 60)
      d <- debit(c, 60)
      account <- storeAccount(d, accountRepository)
    } yield account

  }

}
