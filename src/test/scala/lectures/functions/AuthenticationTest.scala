package lectures.functions

import org.scalatest._
import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import AuthenticationData._
import Authentication._

/**
  * Авторизация - это очень важно, поэтому нам необходимо покрыть тестами ответсвенный за нее код
  * (lectures.functions.Authentication)
  *
  * Для этого
  * * * * уберите extends App у Authentication
  * * * * замените AuthenticationData.testUsers соответствующими генераторами
  * * * * напишите
  * * * * * 2 теста на authByCard
  * * * * * 2 теста на authByLP
  * * * * * 1 тест на их композицию
  *
  */

class AuthenticationTest extends WordSpec with PropertyChecks with Matchers {
  val userCardGenerator: Gen[CardUser] = Gen.zip(Gen.chooseNum(1, 9999), Gen.oneOf(registeredCards.toList)).map(x => new CardUser(x._1, x._2))
  val userLpGenerator: Gen[LPUser] = Gen.zip(Gen.chooseNum(1, 9999), Gen.oneOf(registeredLoginAndPassword.toList)).map(x => new LPUser(x._1, x._2))
  val userRandGenerator = Gen.oneOf(userCardGenerator, userLpGenerator)

  "An User" when {
    "has a registered card" should {
      "be authenticated" in {
        forAll(userCardGenerator) {
          user => authByCard.isDefinedAt(user) shouldBe true
        }
      }
    }

    "doesn't have a registered card" should {
      "not be authenticated" in {
        forAll(CardUser()) {
          user => Authentication.authByCard.isDefinedAt(user) shouldBe false
        }
      }
    }

    "has a correct login and password" should {
      "be authenticated" in {
        forAll(userLpGenerator) {
          user => authByLP.isDefinedAt(user) shouldBe true
        }
      }
    }

    "has an correct login and password" should {
      "not be authenticated" in {
        forAll(LPUser()) {
          user => authByCard.isDefinedAt(user) shouldBe false
        }
      }
    }

    "has registered card or a login and password" should {
      "be authenticated" in {
        forAll(userRandGenerator) {
          user => partial(user).isDefined shouldBe true
        }
      }
    }
  }
}