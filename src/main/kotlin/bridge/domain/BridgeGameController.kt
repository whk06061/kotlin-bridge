package bridge.domain

import bridge.view.InputView
import bridge.view.OutputView

private enum class Command(val message: String) {
    RETRY("R")
}

class BridgeGameController {

    private lateinit var bridgeGame: BridgeGame
    private val inputView = InputView()
    private val outputView = OutputView()

    private fun playOneGame(bridgeSize: Int): Boolean {
        for (step in 0 until bridgeSize) {
            val moving = readMoving()
            val isSuccess = bridgeGame.move(moving, step)
            outputView.printMap(bridgeGame.getMapResult())
            if (!isSuccess) return false
        }
        return true
    }

    private fun readMoving(): String {
        outputView.printReadMovingMent()
        return repeatInputProcess { inputView.readMoving() } as String
    }

    private fun readCommand(): String {
        outputView.printReadCommandMent()
        return repeatInputProcess { inputView.readGameCommand() } as String
    }

    private fun repeatInputProcess(inputOperation: () -> Any): Any {
        do {
            try {
                return inputOperation()
            } catch (e: IllegalArgumentException) {
                outputView.printErrorMessage(e.message.toString())
            }
        } while (true)
    }

    private fun manageCommand(command: String) {
        if (command == Command.RETRY.message) {
            bridgeGame.retry()
            return
        }
        outputView.printResult(bridgeGame, false)
    }


}
