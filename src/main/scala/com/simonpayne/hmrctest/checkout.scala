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
        val onXforY = furtherSplit._2

        // bogofs        
        val countB = onBogof.size
        val pairs = (countB / 2).toInt
        val remainder = (countB - pairs)
        val price = onBogof.take(1).head.price
        val totalBogof = price + (price * pairs)

        // for now assume just one XforY promotion is configured
        promos.find(_.isXforY) match {
          case Some(p) =>

            val xfory = onXforY.map { _.price }.sum
            val x = p.x.getOrElse(1)
            val y = p.y.getOrElse(1)
            val xforyNumRem = onXforY.size % x
            val pricefor1 = onXforY.head.price
            val remTotal =xforyNumRem*pricefor1
            val grpTotal = (y * (xfory-(remTotal))) / x
            val xForYTotal = grpTotal + remTotal

            totalBogof + nonPromoTotal + xForYTotal

          case None =>
            // if no XforY promos found, just output rest
            totalBogof + nonPromoTotal
        }


      }

    }

  }

}