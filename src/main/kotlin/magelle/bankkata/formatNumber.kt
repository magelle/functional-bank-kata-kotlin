package magelle.bankkata

import java.text.NumberFormat

fun formatNumber(number: Int) = NumberFormat.getIntegerInstance().format(number)!!