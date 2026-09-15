package com.joaomantellato.calculadorajoaomantellato

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joaomantellato.calculadorajoaomantellato.ui.theme.CalculadoraJoãoMantellatoTheme

class MainActivity : ComponentActivity() {
    var visor by mutableStateOf("0")

    val pilhaOperador = mutableListOf<String>()

    val pilhaOperando = mutableListOf<String>()

    var aguardandoOperando = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraJoãoMantellatoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    criaCalculadora(visor)
                }
            }
        }
    }

    @Composable
    fun criaCalculadora(visor: String) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                fontSize = 32.sp,
                text = visor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                criaBotaoPequeno(texto = "sin", identificador = BotaoOperacao.SENO)
                criaBotaoPequeno(texto = "cos", identificador = BotaoOperacao.COSSENO)
                criaBotaoPequeno(texto = "tan", identificador = BotaoOperacao.TANGENTE)
                criaBotaoPequeno(texto = "^", identificador = BotaoOperacao.POTENCIA)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                criaBotaoPequeno(texto = "!", identificador = BotaoOperacao.FATORIAL)
                criaBotaoPequeno(texto = "pi", identificador = BotaoOperacao.PI)
                criaBotaoPequeno(texto = "1/x", identificador = BotaoOperacao.INVERSO)
                criaBotaoPequeno(texto = "√", identificador = BotaoOperacao.RAIZ)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                criaBotaoPequeno(texto = "%", identificador = BotaoOperacao.PORCENTAGEM)
                criaBotaoPequeno(texto = "/", identificador = BotaoOperacao.DIVISAO)
                criaBotaoPequeno(texto = "*", identificador = BotaoOperacao.MULTIPLICACAO)
                criaBotaoPequeno(texto = "-", identificador = BotaoOperacao.SUBTRACAO)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                criaBotaoPequeno(texto = "7", identificador = BotaoOperacao.SETE)
                criaBotaoPequeno(texto = "8", identificador = BotaoOperacao.OITO)
                criaBotaoPequeno(texto = "9", identificador = BotaoOperacao.NOVE)
                criaBotaoPequeno(texto = "+", identificador = BotaoOperacao.SOMA)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                criaBotaoPequeno(texto = "4", identificador = BotaoOperacao.QUATRO)
                criaBotaoPequeno(texto = "5", identificador = BotaoOperacao.CINCO)
                criaBotaoPequeno(texto = "6", identificador = BotaoOperacao.SEIS)
                criaBotaoPequeno(texto = ",", identificador = BotaoOperacao.VIRGULA)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                criaBotaoPequeno(texto = "1", identificador = BotaoOperacao.UM)
                criaBotaoPequeno(texto = "2", identificador = BotaoOperacao.DOIS)
                criaBotaoPequeno(texto = "3", identificador = BotaoOperacao.TRES)
                criaBotaoPequeno(texto = "=", identificador = BotaoOperacao.IGUALDADE)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                criaBotaoPequeno(texto = "+/-", identificador = BotaoOperacao.LIMPAR)
                criaBotaoPequeno(texto = "0", identificador = BotaoOperacao.ZERO)
                criaBotaoPequeno(texto = "C", identificador = BotaoOperacao.LIMPAR)
                criaBotaoPequeno(texto = "<-", identificador = BotaoOperacao.LIMPAR)
            }
        }
    }

    @Composable
    fun criaBotaoPequeno(texto: String, identificador: BotaoOperacao) {
        Button(
            modifier = Modifier
                .width(80.dp)
                .height(50.dp), onClick = {
                if (identificador.ordinal <= BotaoOperacao.VIRGULA.ordinal) {
                    numPress(identificador)
                } else {
                    opPress(identificador)
                }
            },
            colors = if (identificador == BotaoOperacao.IGUALDADE) {
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            } else {
                ButtonDefaults.buttonColors()
            }
        ) {
            Text(texto)
        }
    }

    fun numPress(identificador: BotaoOperacao) {
        if (visor == "Erro") {
            visor = "0"
            aguardandoOperando = false
        }

        if (aguardandoOperando) {
            visor = "0"
            aguardandoOperando = false
        }
        if ((visor == "0") && (identificador.name == BotaoOperacao.ZERO.name)) {
            return
        }

        if ((visor.contains(",")) && (identificador.name == BotaoOperacao.VIRGULA.name)) {
            return
        }

        var tmp = identificador.name
        if (tmp == BotaoOperacao.VIRGULA.name) {
            tmp = ","
        } else {
            tmp = identificador.ordinal.toString()
        }

        if (visor.length == 1 && visor == "0") {
            visor = tmp
        } else {
            visor += tmp
        }
    }

    fun opPress(identificador: BotaoOperacao) {
        if (visor == "Erro" && identificador != BotaoOperacao.LIMPAR) {
            limpar()
            return
        }

        when (identificador) {
            BotaoOperacao.LIMPAR -> {
                limpar()
                return
            }
            BotaoOperacao.IGUALDADE -> {
                igualdade()
                return
            }
            BotaoOperacao.SENO, BotaoOperacao.COSSENO, BotaoOperacao.TANGENTE,
            BotaoOperacao.FATORIAL, BotaoOperacao.PI, BotaoOperacao.INVERSO,
            BotaoOperacao.RAIZ -> {
                aplicarUnaria(identificador)
                return
            }
            BotaoOperacao.PORCENTAGEM -> {
                aplicarPorcentagem()
                return
            }
            else -> {
                // operações binárias: soma, subtração, multiplicação, divisão, potenciação
            }
        }

        if (aguardandoOperando) {
            if (pilhaOperador.isNotEmpty()) {
                pilhaOperador[pilhaOperador.lastIndex] = identificador.name
            }
            return
        }

        if (pilhaOperador.isEmpty()) {
            pilhaOperador.add(identificador.name)
            pilhaOperando.add(visor)
        } else {
            igualdade()
            if (visor == "Erro") {
                return
            }
            pilhaOperador.add(identificador.name)
            pilhaOperando.add(visor)
        }

        aguardandoOperando = true
    }

    fun limpar() {
        pilhaOperador.clear()
        pilhaOperando.clear()
        aguardandoOperando = false
        visor = "0"
    }

    fun igualdade() {
        if (pilhaOperador.isEmpty() || pilhaOperando.isEmpty()) {
            return
        }
        if (visor == "Erro") {
            return
        }

        val operador = pilhaOperador.removeAt(pilhaOperador.lastIndex)
        val operandoStr = pilhaOperando.removeAt(pilhaOperando.lastIndex)
        val operando = operandoStr.replace(",", ".").toFloatOrNull()
        val aux = visor.replace(",", ".").toFloatOrNull()

        if (operando == null || aux == null) {
            visor = "Erro"
            aguardandoOperando = true
            return
        }

        val resultado: Float? = when (operador) {
            BotaoOperacao.SOMA.name -> operando + aux
            BotaoOperacao.SUBTRACAO.name -> operando - aux
            BotaoOperacao.MULTIPLICACAO.name -> operando * aux
            BotaoOperacao.DIVISAO.name -> {
                if (aux == 0f) null else operando / aux
            }
            BotaoOperacao.POTENCIA.name -> Math.pow(operando.toDouble(), aux.toDouble()).toFloat()
            else -> null
        }

        visor = if (resultado == null || resultado.isNaN() || resultado.isInfinite()) {
            "Erro"
        } else {
            formatarResultado(resultado)
        }

        aguardandoOperando = true
    }

    fun aplicarUnaria(identificador: BotaoOperacao) {
        if (visor == "Erro") {
            return
        }

        if (identificador == BotaoOperacao.PI) {
            visor = "3,14"
            aguardandoOperando = false
            return
        }

        val valor = visor.replace(",", ".").toFloatOrNull() ?: return

        when (identificador) {
            BotaoOperacao.SENO -> {
                visor = formatarResultado(Math.sin(Math.toRadians(valor.toDouble())).toFloat())
            }
            BotaoOperacao.COSSENO -> {
                visor = formatarResultado(Math.cos(Math.toRadians(valor.toDouble())).toFloat())
            }
            BotaoOperacao.TANGENTE -> {
                val cosseno = Math.cos(Math.toRadians(valor.toDouble()))
                visor = if (Math.abs(cosseno) < 1e-10) {
                    "Erro"
                } else {
                    formatarResultado(Math.tan(Math.toRadians(valor.toDouble())).toFloat())
                }
            }
            BotaoOperacao.FATORIAL -> {
                visor = if (valor < 0f || valor != Math.floor(valor.toDouble()).toFloat() || valor > 20f) {
                    "Erro"
                } else {
                    var resultado = 1.0
                    val n = valor.toInt()
                    for (i in 2..n) {
                        resultado *= i
                    }
                    formatarResultado(resultado.toFloat())
                }
            }
            BotaoOperacao.INVERSO -> {
                visor = if (valor == 0f) {
                    "Erro"
                } else {
                    formatarResultado(1f / valor)
                }
            }
            BotaoOperacao.RAIZ -> {
                visor = if (valor < 0f) {
                    "Erro"
                } else {
                    formatarResultado(Math.sqrt(valor.toDouble()).toFloat())
                }
            }
            else -> {}
        }

        aguardandoOperando = true
    }

    fun aplicarPorcentagem() {
        if (visor == "Erro") {
            return
        }
        val aux = visor.replace(",", ".").toFloatOrNull() ?: return

        val resultado = if (pilhaOperando.isNotEmpty()) {
            val operando = pilhaOperando.last().replace(",", ".").toFloatOrNull() ?: aux
            operando * (aux / 100f)
        } else {
            aux / 100f
        }

        visor = formatarResultado(resultado)
        aguardandoOperando = false
    }

    fun formatarResultado(valor: Float): String {
        return if (!valor.isNaN() && !valor.isInfinite() && valor == valor.toLong().toFloat()) {
            valor.toLong().toString()
        } else {
            valor.toString().replace(".", ",")
        }
    }

    enum class BotaoOperacao {
        ZERO, UM, DOIS, TRES, QUATRO, CINCO, SEIS, SETE, OITO, NOVE, VIRGULA,
        SOMA, SUBTRACAO, MULTIPLICACAO, DIVISAO, POTENCIA, IGUALDADE, LIMPAR, PORCENTAGEM,
        SENO, COSSENO, TANGENTE, FATORIAL, PI, INVERSO, RAIZ
    }
}