package aleksandr_logunov.task_list.dummy

import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<DummyItem> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()


    init {
        addItem(DummyItem("1", "Database", "Room"))
        addItem(DummyItem("2", "Shared pref", "Shared pref"))
        addItem(DummyItem("3", "In memory", "Not persisted"))
        addItem(DummyItem("4", "Internal", "Storage"))
        addItem(DummyItem("5", "External", "Storage"))
    }

    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(val id: String, val name: String, val details: String) {
        override fun toString(): String = "$name ($details)"
    }
}