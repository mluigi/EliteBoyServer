package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class MarketSell : JournalEntry() {
    var Type: String? = null
    var FriendlyType: String? = null
    var Count: Int = 0
    var SellPrice: Long = 0
    var TotalSale: Long = 0
    var AvgPricePaid: Long = 0
    var IllegalGoods: Boolean? = null
    var StolenGoods: Boolean? = null
    var BlackMarket: Boolean? = null
}