package com.simonpayne.hmrctest

import org.scalatest._

class CheckoutSystemTests extends FlatSpec with Matchers {
  
  val apple = GroceryItem("Apple", BigDecimal(0.6))
  val orange = GroceryItem("Orange", BigDecimal(0.25))   
  
  val system = new CheckoutSystem()

  "A checkout system" should "add up the items in a basket and output the correct total" in {    
    val basket = List(apple, apple, orange, apple) 
    system.addUpScannedItems(basket) should be (BigDecimal(2.05))    
  }
  
  "A checkout system" should "handle an empty list" in {    
    val basket = List.empty 
    system.addUpScannedItems(basket) should be (BigDecimal(0))    
  }
  
  "A checkout system" should "handle a list of incorrectly configured items (negative prices)" in {    
    val basket = List(GroceryItem("AppleWrongPrice", BigDecimal(-1)))
    try {
      system.addUpScannedItems(basket)
      fail()
    } catch {
      case e: Exception => // this is okay
    } 
  }
  
  
}