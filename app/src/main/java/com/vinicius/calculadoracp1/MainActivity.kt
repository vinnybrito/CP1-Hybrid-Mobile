package com.vinicius.calculadoracp1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vinicius.calculadoracp1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // _________ VALORES MATEMATICOS _________ //

        binding.numberZero.setOnClickListener {
            addExpression("0")
        }

        binding.numberOne.setOnClickListener {
            addExpression("1")
        }

        binding.numberTwo.setOnClickListener {
            addExpression("2")
        }

        binding.numberTree.setOnClickListener {
            addExpression("3")
        }

        binding.numberFour.setOnClickListener {
            addExpression("4")
        }

        binding.numberFive.setOnClickListener {
            addExpression("5")
        }

        binding.numberSix.setOnClickListener {
            addExpression("6")
        }

        binding.numberSeven.setOnClickListener {
            addExpression("7")
        }

        binding.numberEight.setOnClickListener {
            addExpression("8")
        }

        binding.numberNine.setOnClickListener {
            addExpression("9")
        }

        binding.dot.setOnClickListener {
            addExpression(".")
        }

        // _________ EXPRESSÕES MATEMATICAS _________ //

        binding.plus.setOnClickListener {
            addExpression("+")
        }

        binding.subtraction.setOnClickListener {
            addExpression("-")
        }

        binding.multiplication.setOnClickListener {
            addExpression("x")
        }

        binding.division.setOnClickListener {
            addExpression("/")
        }

        binding.percentage.setOnClickListener {
            addExpression("%")
        }

        // _________ OUTROS BOTÕES _________ //

        binding.backspace.setOnClickListener {
            binding.txtOperation.text = binding.txtOperation.text.dropLast(1)
        }

        binding.clean.setOnClickListener {
            binding.txtOperation.text = ""
            binding.txtResult.text = ""
        }

        var firstClick = true
        binding.invertSignal.setOnClickListener {

            if (firstClick) {
                binding.txtOperation.text = "-" + binding.txtOperation.text
                firstClick = false
            } else {
                binding.txtOperation.text = binding.txtOperation.text.drop(1)
                firstClick = true
            }
        }

        // _________ RESULT BUTTON _________ //

        binding.equals.setOnClickListener {
            showResult()
        }

    }

    // ---------------------------------------------------------- //

    private fun addExpression(value: String) {
        binding.txtOperation.append(value)
    }


    private fun showResult() {
        val expression = binding.txtOperation.text.toString()

        try {
            val result = evaluateExpression(expression)
            binding.txtResult.text = result.toString()
        } catch (e: Exception) {
            binding.txtResult.text = "Erro"
        }
    }

    private fun evaluateExpression(expression: String): Double {
        val tokens = tokenizeExpression(expression)
        val numbers = mutableListOf<Double>()
        val operators = mutableListOf<String>()

        for (token in tokens) {
            if (token.isNumber()) {
                numbers.add(token.toDouble())
            } else {
                while (operators.isNotEmpty() && hasPrecedence(token, operators.last())) {
                    val operator = operators.removeLast()
                    val right = numbers.removeLast()
                    val left = numbers.removeLast()
                    numbers.add(applyOperation(left, right, operator))
                }
                operators.add(token)
            }
        }

        while (operators.isNotEmpty()) {
            val operator = operators.removeLast()
            val right = numbers.removeLast()
            val left = numbers.removeLast()
            numbers.add(applyOperation(left, right, operator))
        }

        return numbers.first()
    }

    private fun tokenizeExpression(expression: String): List<String> {
        val regex = "(?<=[-+*/])|(?=[-+*/])".toRegex()
        return expression.split(regex).filter { it.isNotBlank() }
    }

    private fun String.isNumber(): Boolean {
        return matches("-?\\d+(\\.\\d+)?".toRegex())
    }

    private fun hasPrecedence(op1: String, op2: String): Boolean {
        return !(op1 == "+" || op1 == "-") || (op2 == "*" || op2 == "/")
    }

    private fun applyOperation(left: Double, right: Double, operator: String): Double {
        return when (operator) {
            "+" -> left + right
            "-" -> left - right
            "x" -> left * right
            "/" -> left / right
            else -> throw IllegalArgumentException("Operador inválido: $operator")
        }
    }





    // _____________ FUNÇÕES MATEMÁTICAS _____________ //

    private fun addValues(firstValue: Double, secondValue: Double): Double {
        return firstValue + secondValue
    }

    private fun subtractValues(firstValue: Double, secondValue: Double): Double {
        return firstValue - secondValue
    }

    private fun multiplyValues(firstValue: Double, secondValue: Double): Double {
        return firstValue * secondValue
    }

    private fun divideValues(firstValue: Double, secondValue: Double): Double {
        return firstValue / secondValue
    }


}