package org.tzachi


import spock.lang.Specification
import org.spockframework.util.Pair

class CalculatorManagerSpec extends Specification {


    CalculatorManager calculateManager

    def setup () {
        calculateManager = new CalculatorManager()
    }

    def "Calculator manager should successfully evaluate list of expressions"() {

        given:
        def expressions = List.of(
                "i = 0",
                "j = ++i",
                "x = i++ + 5",
                "y = 5 + 3 * 10",
                "i += y")

        when:
        expressions
                .forEach(exp -> calculateManager.calculate(exp))

        then:
        calculateManager.calcVariablesMap == Map.of("i", 37, "j", 1, "x", 6, "y", 35)
    }


    def "Calculate manager should verify #scenario"() {

        when:
        for(expr in pair_expression_result) {
            def result = calculateManager.calculate(expr.first())
            assert result == expr.second()
        }
        then:
        calculateManager.calcVariablesMap == calculator_result

        where:
        scenario                       | pair_expression_result                                       | calculator_result
        //Increment
        "pre incremental"              |List.of(Pair.of("i = 10", 10), Pair.of("j = ++i", 11))        |  Map.of ("i", 11, "j", 11)
        "post incremental"             |List.of(Pair.of("i = 10", 10), Pair.of("j = i++", 10))        |  Map.of ("i", 11, "j", 10)
        "double post incremental"      |List.of(Pair.of("i = 10", 10), Pair.of("j = i++ + i++", 21))  |  Map.of ("i", 12, "j", 21)
        "double - pre incremental"     |List.of(Pair.of("i = 10", 10), Pair.of("j = ++i + ++i", 23))  |  Map.of ("i", 12, "j", 23)
        "double pre post incremental"  |List.of(Pair.of("i = 10", 10), Pair.of("j = ++i + i++", 22))  |  Map.of ("i", 12, "j", 22)
        "double post pre incremental"  |List.of(Pair.of("i = 10", 10), Pair.of("j = i++ + ++i", 22))  |  Map.of ("i", 12, "j", 22)

        //Decrement
        "pre decremental"              |List.of(Pair.of("i = 10", 10), Pair.of("j = --i", 9))         |  Map.of("i", 9, "j", 9)
        "post decremental"             |List.of(Pair.of("i = 10", 10), Pair.of("j = i--", 10))        |  Map.of ("i", 9, "j", 10)
        "double post decremental"      |List.of(Pair.of("i = 10", 10), Pair.of("j = i-- - i--", 1))   |  Map.of ("i", 8, "j", 1)
        "double - pre decremental"     |List.of(Pair.of("i = 10", 10), Pair.of("j = --i - --i", 1))   |  Map.of ("i", 8, "j", 1)
        "double pre post decremental"  |List.of(Pair.of("i = 10", 10), Pair.of("j = --i - i--", 0))   |  Map.of ("i", 8, "j", 0)
        "double post pre decremental"  |List.of(Pair.of("i = 10", 10), Pair.of("j = i-- - --i", 2))   |  Map.of ("i", 8, "j", 2)

        //Increment & Decrement
        "double - pre decremental"     |List.of(Pair.of("i = 10", 10), Pair.of("j = --i + --i", 17))   |  Map.of ("i", 8, "j", 17)
        "double pre post decremental"  |List.of(Pair.of("i = 10", 10), Pair.of("j = ++i + i--", 22))   |  Map.of ("i", 10, "j", 22)
        "double post pre decremental"  |List.of(Pair.of("i = 10", 10), Pair.of("j = i-- + ++i", 20))   |  Map.of ("i", 10, "j", 20)
        "double - pre decremental"     |List.of(Pair.of("i = 10", 10), Pair.of("j = ++i - ++i", -1))   |  Map.of ("i", 12, "j", -1)
        "double pre post decremental"  |List.of(Pair.of("i = 10", 10), Pair.of("j = --i - i++", 0))    |  Map.of("i", 10, "j", 0)
        "double post pre decremental"  |List.of(Pair.of("i = 10", 10), Pair.of("j = i++ - --i", 0))    |  Map.of("i", 10, "j", 0)

        //Assignments
        "add assigned "                |List.of(Pair.of("i = 10", 10), Pair.of("i += 10", 20))       |  Map.of("i", 20)
        "add assigned with multiple "  |List.of(Pair.of("i = 10", 10), Pair.of("i += 10 * 2", 30))   |  Map.of("i", 30)
        "add assigned with multiple"   |List.of(Pair.of("i = 10", 10), Pair.of("i += 10 - 2", 18))   |  Map.of("i", 18)
        "mul assigned"                 |List.of(Pair.of("i = 10", 10), Pair.of("i *= 10 - 2", 98))   |  Map.of("i", 98)

    }
}