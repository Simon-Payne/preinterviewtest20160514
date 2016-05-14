package com.simonpayne.hmrctest

import org.scalatest._

class CheckoutSystemTests extends FlatSpec with Matchers {
  
  val apple = GroceryItem("Apple", BigDecimal(0.6))
  val orange = GroceryItem("Orange", BigDecimal(0.25))   
  
  val systemNoPromos = new CheckoutSystem(Nil)

  // v1 regression tests
  
  "A checkout system" should "add up the items in a basket and output the correct total" in {    
    val basket = List(apple, apple, orange, apple) 
    systemNoPromos.addUpScannedItems(basket) should be (BigDecimal(2.05))    
  }
  
  "A checkout system" should "handle an empty list" in {    
    val basket = List.empty 
    systemNoPromos.addUpScannedItems(basket) should be (BigDecimal(0))    
  }
  
  "A checkout system" should "handle a list of incorrectly configured items (negative prices)" in {    
    val basket = List(GroceryItem("AppleWrongPrice", BigDecimal(-1)))
    try {
      systemNoPromos.addUpScannedItems(basket)
      fail()
    } catch {
      case e: Exception => // this is okay
    } 
  }
  
  // v2 tests
  
  val bogofPromo = Promotion(apple, true)
  val xforYPromo = Promotion(orange, false, true, Some(3), Some(2))   
  val systemWithBogof = new CheckoutSystem(List(bogofPromo))
  val systemWithBogofAndXforY = new CheckoutSystem(List(bogofPromo, xforYPromo))
  
  "A checkout system with a bogof promotion on apples" should "handle an empty list" in {    
    val basket = List.empty 
    systemWithBogof.addUpScannedItems(basket) should be (BigDecimal(0))    
  }
  
  "A checkout system with a bogof promotion on apples" should "add up the items correctly including the bogofs and output correct total" in {    
    val basket = List(apple, apple, orange, apple)
    systemWithBogof.addUpScannedItems(basket) should be (BigDecimal(1.45))    
  }
  
  "A checkout system with a bogof promotion on apples" should "add up the items correctly including the bogofs and output correct total - data for 3for2 test included" in {    
    val basket = List(apple, apple, orange, apple, orange, orange)
    systemWithBogof.addUpScannedItems(basket) should be (BigDecimal(1.95))    
  }   
  
  "A checkout system with a bogof promotion on apples & 3 for 2 on oranges" should "add up the items correctly including the bogofs and output correct total - data for 3for2 test included" in {    
    val basket = List(apple, apple, orange, apple, orange, orange)
    systemWithBogofAndXforY.addUpScannedItems(basket) should be (BigDecimal(1.7))    
  }
  
}