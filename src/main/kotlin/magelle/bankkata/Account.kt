package magelle.bankkata

import java.util.concurrent.atomic.AtomicInteger

fun newAccount() = Account(emptyList<Transaction>())

fun addTransaction(account: Account, amount: Int, todayAsString: () -> String): Account {
    return Account(account.transactions.plus(Transaction(amount, todayAsString())))
}

fun printStatement(account: Account, print: (String) -> Unit) {
    val sum: AtomicInteger = AtomicInteger(0)
    print("| date | credit | debit | balance |")
    account.transactions
            .map { formatTransaction(it, sum.addAndGet(it.amount)) }
            .reversed()
            .forEach { print(it) }
}

private fun formatTransaction(it: Transaction, sum: Int)
        = "| ${it.date} " +
        "| ${if (it.amount > 0) formatNumber(it.amount) else ""} " +
        "| ${if (it.amount < 0) formatNumber(-it.amount) else ""} " +
        "| ${formatNumber(sum)} |"

class Account(val transactions: List<Transaction>) {

    fun deposit(amount: Int, todayAsString: () -> String)
            = addTransaction(this, amount, todayAsString)

    fun withdraw(amount: Int, todayAsString: () -> String)
            = addTransaction(this, -amount, todayAsString)

    fun printStatement(print: (String) -> Unit)
            = printStatement(this, print)

}

class Transaction(val amount: Int, val date: String)
