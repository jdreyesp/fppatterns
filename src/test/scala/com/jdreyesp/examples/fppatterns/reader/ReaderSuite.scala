package com.jdreyesp.examples.fppatterns.reader

import java.util.Calendar

import com.jdreyesp.examples.fppatterns.reader.model.{Account, AccountService, AccountServiceWithoutPattern}
import com.jdreyesp.examples.fppatterns.reader.repository.AccountRepositoryOracleDB
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

import scala.util.{Success, Try}

/**
  * Created by jreyes on 3/1/18.
  */
class ReaderSuite extends FeatureSpec with Matchers with GivenWhenThen {

  def today = Calendar.getInstance().getTime()
  val accountRepository = AccountRepositoryOracleDB

  //Pattern necessity
  feature("Pattern not used should work as well") {

    scenario("Simple dependency injection passing the repository into the service methods should work fine") {
      Given("An account id")
      val accountId = "1A2B3C"

      When("A debit operation is executed in that account")
      val newAccount : Try[Account] = AccountServiceWithoutPattern.complexOperation(accountId, accountRepository)

      Then("A new account is returned with the new Balance")
      newAccount.isSuccess should be (true)
      newAccount.map(acc => acc.balance.amount) shouldBe (Success(0))
    }
  }

  //Pattern in action
  feature("Reader monad pattern in action!") {
    scenario("Using Reader monad pattern to decouple dependency injection") {
      Given("An account id")
      val accountId = "1A2B3C"

      When("A debit operation is executed in that account")
      val newAccount : Try[Account] = AccountService.complexOperation(accountId).apply(accountRepository)

      Then("A new account is returned with the new Balance")
      newAccount.isSuccess shouldBe (true)
      newAccount.map(acc => acc.balance.amount) shouldBe (Success(0))
    }
  }

}
