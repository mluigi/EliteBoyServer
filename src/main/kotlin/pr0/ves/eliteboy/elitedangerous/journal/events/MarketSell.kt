package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class MarketSell : JournalEntry() {
    var Type: String? = null
    var FriendlyType: String? = null
    var Count: Int? = null
    var SellPrice: Long? = null
    var TotalSale: Long? = null
    var AvgPricePaid: Long? = null
    var IllegalGoods: Boolean? = null
    var StolenGoods: Boolean? = null
    var BlackMarket: Boolean? = null
}