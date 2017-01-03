package magelle.feature

import magelle.bankkata.newAccount
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

class KataBankFeature {

    val printed: MutableList<String>
    var todays: MutableList<String>

    init {
        printed = ArrayList<String>()
        todays = ArrayList<String>()
        todays.add("10/01/2012")
        todays.add("13/01/2012")
        todays.add("14/01/2012")
    }

    @Test fun scenario() {
        val todayAsString = { this.todayAsString() }
        newAccount()
                .deposit(1000, todayAsString)
                .deposit(2000, todayAsString)
                .withdraw(500, todayAsString)
                .printStatement { this.print(it) }

        assertThat(printed).containsExactly(
                "| date | credit | debit | balance |",
                "| 14/01/2012 |  | 500 | 2\u00A0500 |",
                "| 13/01/2012 | 2\u00A0000 |  | 3\u00A0000 |",
                "| 10/01/2012 | 1\u00A0000 |  | 1\u00A0000 |"
        )
    }

    fun print(text: String) = printed.add(text)

    fun todayAsString(): String {
        val date = todays.first()
        todays.removeAt(0)
        return date
    }

}

