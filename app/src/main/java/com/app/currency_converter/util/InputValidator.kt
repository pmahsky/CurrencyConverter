package com.app.currency_converter.util

class InputValidator {
    companion object {
        fun processInputAmount(inputAmount: String): Boolean {
            if(inputAmount.isNotEmpty()) {
                val cleanedInputAmount = cleanInput(inputAmount)
                return isInputAmountValid(cleanedInputAmount)
            }
            return false
        }

        private fun cleanInput(input: String): String {
            var cleanInput = input.replace(",", ".")
            when {
                "." == cleanInput -> cleanInput = "0."
                Regex("0[^.]").matches(cleanInput) -> cleanInput =
                    input[Utils.Order.SECOND.position].toString()
            }
            return cleanInput
        }

        private fun isInputAmountValid(input: String): Boolean {
            return (validateLength(input) &&
                    validateDecimalPlaces(input) &&
                    validateDecimalSeparator(input))
        }

        private fun validateLength(input: String): Boolean {
            val maxDigitsAllowed = 20
            if (!input.contains(".") && input.length > maxDigitsAllowed) {
                return false
            }
            return true
        }

        private fun validateDecimalPlaces(input: String): Boolean {
            val maxDecimalPlacesAllowed = 4
            if (input.contains(".") &&
                input.substring(input.indexOf(".") + 1).length > maxDecimalPlacesAllowed
            ) {
                return false
            }
            return true
        }

        private fun validateDecimalSeparator(input: String): Boolean {
            val decimalSeparatorCount = input.asSequence()
                .count { it == '.' }
            if (decimalSeparatorCount > 1) {
                return false
            }
            return true
        }
    }
}