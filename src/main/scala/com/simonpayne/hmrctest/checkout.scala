package com.simonpayne.hmrctest

case class GroceryItem(name: String, price: BigDecimal)

class CheckoutSystem {

  def addUpScannedItems(goods: List[GroceryItem]): BigDecimal = goods.map { _.price }.sum    
  
}