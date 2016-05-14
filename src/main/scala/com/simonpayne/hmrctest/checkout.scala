package com.simonpayne.hmrctest

case class GroceryItem(name: String, price: BigDecimal)

case class Promotion(item: GroceryItem, isBogof: Boolean = false, isXforY: Boolean = false, x: Option[Int] = None, y: Option[Int] = None)

class CheckoutSystem(val promos: List[Promotion]) {

  def addUpScannedItems(goods: List[GroceryItem]): BigDecimal = {

    if (promos.isEmpty) goods.map { _.price }.sum
    else {

      if (goods.isEmpty) BigDecimal(0)
      else {
        val split = goods.partition { g => promos.map { _.item }.contains(g) }
        val nonPromoTotal = split._2.map { _.price }.sum
        val onPromotion = split._1

        val furtherSplit = onPromotion.partition { g => promos.filter(_.isBogof).map { _.item }.contains(g) }
        val onBogof = furtherSplit._1
        val onXforY = furtherSplit._2 // let's assume for now it's 3-for-2

        // bogofs        
        val countB = onBogof.size
        val pairs = (countB / 2).toInt
        val remainder = (countB - pairs)
        val price = onBogof.take(1).head.price
        val totalBogof = price + (price * pairs) 

        // 3for2                
        val xfory = onXforY.map { _.price }.sum
        val xForYTotal = (2 * xfory) / 3

        totalBogof + nonPromoTotal + xForYTotal

      }

    }

  }

}