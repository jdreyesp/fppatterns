import com.jdreyesp.examples.fppatterns.lens.Lens
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

class LensSuite extends FeatureSpec with Matchers with GivenWhenThen {

  val address = Address("B-12", "Monroe Street", "Denver", "CO", "80231")
  val customer = Customer(1, "John", address)

  //Pattern necessity
  feature("Pattern not used should work as well") {

    scenario("Simple copy solution without Lens pattern should work fine") {

      Given("An address")
      When("Trying to create a new address due to FP immutability (we want to update the address no, for instance)")
      val addressCopy = address.copy(no = "B-56")

      Then("Should work without the Lens pattern")
      address shouldBe Address("B-12", "Monroe Street", "Denver", "CO", "80231")
      addressCopy shouldBe Address("B-56", "Monroe Street", "Denver", "CO", "80231")
    }

    scenario("Solution won't scale with this approach") {

      Given("An Customer")
      When("Trying to create a new Customer due to FP immutability (we want to update the customer address, for instance)")
      val newAddress = address.copy(no="B-56")
      val customerCopy = customer.copy(address = newAddress) //We're facing double nested copy here... bad thing huh? -> this doesn't scale

      Then("Should work without the Lens pattern")
      customer shouldBe Customer(1, "John", address)
      customerCopy shouldBe Customer(1, "John", newAddress)
    }

  }

  //Pattern in action
  feature("Lens pattern in action!") {

    //We define lenses here. Continue reading to see how they're used in the code
    val lensForAddressStringField = Lens[Address, String](
      get = _.no,
      set = (address, newNo) => address.copy(no = newNo)
    )

    val lensForCustomerAddressField = Lens[Customer, Address](
      get = _.address,
      set = (customer, address) => customer.copy(address = address)
    )

    scenario("Using Lens Pattern with Address (simple object)") {

      Given("A lens for String field in Address") //lensForAddressStringField
      And("An address") //address instance

      When("Trying to create a new address due to FP immutability (we want to update the address no, for instance)")
      val newAddress = lensForAddressStringField.set(address, "B-56")

      Then("Address creation should work with Lens pattern")
      address shouldBe Address("B-12", "Monroe Street", "Denver", "CO", "80231")
      newAddress shouldBe Address("B-56", "Monroe Street", "Denver", "CO", "80231")
    }

    scenario("Using Lens Pattern with Customer (composing object)") {

      Given("A lens for Adddress field in Customer")
      //In this case we use a composition pattern that helps us to compose two lenses.
      // This is the art of composability in FP!
      val composingLensCustomerAddress = Lens.compose(lensForCustomerAddressField, lensForAddressStringField)

      And("A customer") //customer instance

      When("Trying to create a new Customer due to FP immutability (we want to update the customer address, for instance)")
      val newCustomer = composingLensCustomerAddress.set(customer, "B-56")

      Then("Customer creation should work with Lens pattern")
      val newAddress = address.copy(no="B-56")
      customer shouldBe Customer(1, "John", address)
      newCustomer shouldBe Customer(1, "John", newAddress)
    }
  }
}