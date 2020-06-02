package machine

import java.util.*


class CofeeMashine() {

    enum class Products(var balance: Int) {
        WATER(400),
        MILK(540),
        BEANS(120),
        CUPS(9),
        MONEY(550);
    }

    enum class Status {
        BAY,
        FILL,
        TAKE,
        REMAINING,
        EXIT,
        NULL
    }

    companion object {

        private var state: Status = Status.NULL

        private val scanner = Scanner(System.`in`)

        private fun makeCof(water1: Int = 0, milk1: Int = 0, beans1: Int = 0, money1: Int = 0) {
            var text = ""

            when {
                Products.WATER.balance < water1 -> text = "Sorry, not enough water!"
                Products.MILK.balance < milk1 -> text = "Sorry, not enough milk!"
                Products.BEANS.balance < beans1 -> text = "Sorry, not enough beans!"
                Products.CUPS.balance == 0 -> text = "Sorry, not enough cups!"
                else -> {
                    text = "I have enough resources, making you a coffee!"
                    Products.WATER.balance -= water1
                    Products.MILK.balance -= milk1
                    Products.BEANS.balance -= beans1
                    Products.CUPS.balance -= 1
                    Products.MONEY.balance += money1
                }
            }
            return println("$text\n")
        }

        private fun buy() {
            state = Status.BAY
            println()
            print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: > ")
            val cofeeType = scanner.next()
            when (cofeeType) {
                "1" -> makeCof(water1 = 250, beans1 = 16, money1 = 4)
                "2" -> makeCof(350, 75, 20, 7)
                "3" -> makeCof(200, 100, 12, 6)
            }
        }

        private fun fill() {
            state = Status.FILL
            print("Write how many ml of water do you want to add: > ")
            val waterAdd = scanner.nextInt()
            print("Write how many ml of milk do you want to add: > ")
            val milkAdd = scanner.nextInt()
            print("Write how many grams of coffee beans do you want to add: > ")
            val beansAdd = scanner.nextInt()
            print("Write how many disposable cups of coffee do you want to add: > ")
            val cupsAdd = scanner.nextInt()

            Products.WATER.balance += waterAdd
            Products.MILK.balance += milkAdd
            Products.BEANS.balance += beansAdd
            Products.CUPS.balance += cupsAdd

            println()
        }

        private fun take() {
            state = Status.TAKE
            Products.MONEY.balance = 0
            println()
            println("I gave you \$${Products.MONEY.balance}")
        }

        private fun remaining() {
            state = Status.REMAINING
            println()
            println("The coffee machine has:")
            println("${Products.WATER.balance} of water\n" +
                    "${Products.MILK.balance} of milk\n" +
                    "${Products.BEANS.balance} of coffee beans\n" +
                    "${Products.CUPS.balance} of disposable cups\n" +
                    "\$${Products.MONEY.balance} of money")
        }

        fun startMash(operation: String) {
                when (operation) {
                    "buy" -> {
                        buy()
                        state = Status.BAY
                    }
                    "fill" -> {
                        fill()
                        state = Status.FILL
                    }
                    "take" -> {
                        take()
                        state = Status.TAKE
                    }
                    "remaining" -> {
                        remaining()
                        state = Status.REMAINING
                    }
                    "exit" -> state = Status.EXIT
                }
        }
    }
}

fun main() {
    val scanner = Scanner(System.`in`)
    var state = CofeeMashine.Status.NULL

    do {
        println()
        print("Write action (buy, fill, take, remaining, exit): > ")
        var choose = scanner.next()
        if (state == CofeeMashine.Status.BAY && choose == "back"){
            print("Write action (buy, fill, take, remaining, exit): > ")
            choose = scanner.next()
        }
        when (choose) {
            "buy" -> state = CofeeMashine.Status.BAY
            "fill" -> state = CofeeMashine.Status.FILL
            "take" -> state = CofeeMashine.Status.TAKE
            "remaining" -> state = CofeeMashine.Status.REMAINING
            "exit" -> state = CofeeMashine.Status.EXIT
            else -> state = CofeeMashine.Status.NULL
        }
        CofeeMashine.startMash(choose)
    } while (state != CofeeMashine.Status.EXIT)

}

