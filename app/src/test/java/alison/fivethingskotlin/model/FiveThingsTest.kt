package alison.fivethingskotlin.model

import io.kotlintest.specs.FreeSpec
import io.kotlintest.matchers.shouldEqual
import java.util.*

class FiveThingsTest: FreeSpec( {
    "detects if Five things is complete" - {
        "with complete things" {
            val completeThings = FiveThings(
                    Date(),
                    listOf(Thing("MM-DD-YYYY", "one", 1),
                            Thing("MM-DD-YYYY", "two", 2),
                            Thing("MM-DD-YYYY", "three", 3),
                            Thing("MM-DD-YYYY", "four", 4),
                            Thing("MM-DD-YYYY", "five", 5)),
                    false,
                    false)
            completeThings.isComplete shouldEqual true
        }
        "with some empty things" {
            val completeThings = FiveThings(
                    Date(),
                    listOf(Thing("MM-DD-YYYY", "", 1),
                            Thing("MM-DD-YYYY", "two", 2),
                            Thing("MM-DD-YYYY", "three", 3),
                            Thing("MM-DD-YYYY", "four", 4),
                            Thing("MM-DD-YYYY", "five", 5)),
                    false,
                    false)
            completeThings.isComplete shouldEqual false
        }
        "with more empty things" {
            val completeThings = FiveThings(
                    Date(),
                    listOf(Thing("MM-DD-YYYY", "", 1),
                            Thing("MM-DD-YYYY", "", 2),
                            Thing("MM-DD-YYYY", "", 3),
                            Thing("MM-DD-YYYY", "", 4),
                            Thing("MM-DD-YYYY", "", 5)),
                    false,
                    false)
            completeThings.isComplete shouldEqual false
        }
    }

    "detects if Five things is empty" - {
        "with complete things" {
            val completeThings = FiveThings(
                Date(),
                    listOf(Thing("MM-DD-YYYY", "one", 1),
                            Thing("MM-DD-YYYY", "two", 2),
                            Thing("MM-DD-YYYY", "three", 3),
                            Thing("MM-DD-YYYY", "four", 4),
                            Thing("MM-DD-YYYY", "five", 5)),
                    false,
                false)
            completeThings.isEmpty shouldEqual false
        }
        "with some empty things" {
            val completeThings = FiveThings(
                    Date(),
                    listOf(Thing("MM-DD-YYYY", "", 1),
                            Thing("MM-DD-YYYY", "two", 2),
                            Thing("MM-DD-YYYY", "three", 3),
                            Thing("MM-DD-YYYY", "four", 4),
                            Thing("MM-DD-YYYY", "five", 5)),
                    false,
                    false)
            completeThings.isEmpty shouldEqual false
        }
        "with more empty things" {
            val completeThings = FiveThings(
                    Date(),
                    listOf(Thing("MM-DD-YYYY", "", 1),
                            Thing("MM-DD-YYYY", "", 2),
                            Thing("MM-DD-YYYY", "", 3),
                            Thing("MM-DD-YYYY", "", 4),
                            Thing("MM-DD-YYYY", "", 5)),
                    false,
                    false)
            completeThings.isEmpty shouldEqual true
        }
    }

    "returns correct date string for five things" - {
        "For this date" {
            val cal = Calendar.getInstance()
            cal.set(Calendar.YEAR, 2017)
            cal.set(Calendar.MONTH, Calendar.JANUARY)
            cal.set(Calendar.DAY_OF_MONTH, 22)
            val date = cal.time

            val things = FiveThings(
                    date,
                    listOf(Thing("MM-DD-YYYY", "one", 1),
                            Thing("MM-DD-YYYY", "two", 2),
                            Thing("MM-DD-YYYY", "three", 3),
                            Thing("MM-DD-YYYY", "four", 4),
                            Thing("MM-DD-YYYY", "five", 5)),
                    false,
                    false)
            things.fullDateString shouldEqual "Sunday January 22nd, 2017"
        }
    }

    "detects the number of things in a five things" - {
        "with complete things" {
            val completeThings = FiveThings(
                    Date(),
                    listOf(Thing("MM-DD-YYYY", "one", 1),
                            Thing("MM-DD-YYYY", "two", 2),
                            Thing("MM-DD-YYYY", "three", 3),
                            Thing("MM-DD-YYYY", "four", 4),
                            Thing("MM-DD-YYYY", "five", 5)),
                    false,
                    false)
            completeThings.thingsCount shouldEqual 5
        }
        "with some empty things" {
            val completeThings = FiveThings(
                    Date(),
                    listOf(Thing("MM-DD-YYYY", "", 1),
                            Thing("MM-DD-YYYY", "two", 2),
                            Thing("MM-DD-YYYY", "three", 3),
                            Thing("MM-DD-YYYY", "four", 4),
                            Thing("MM-DD-YYYY", "five", 5)),
                    false,
                    false)
            completeThings.thingsCount shouldEqual 4
        }
        "with more empty things" {
            val completeThings = FiveThings(
                    Date(),
                    listOf(Thing("MM-DD-YYYY", "", 1),
                            Thing("MM-DD-YYYY", "", 2),
                            Thing("MM-DD-YYYY", "", 3),
                            Thing("MM-DD-YYYY", "", 4),
                            Thing("MM-DD-YYYY", "", 5)),
                    false,
                    false)
            completeThings.thingsCount shouldEqual 0
        }
        "with less than five things" {
            val completeThings = FiveThings(
                    Date(),
                    listOf(Thing("MM-DD-YYYY", "two", 2),
                            Thing("MM-DD-YYYY", "three", 3),
                            Thing("MM-DD-YYYY", "five", 5)),
                    false,
                    false)
            completeThings.thingsCount shouldEqual 3
        }
    }

})

