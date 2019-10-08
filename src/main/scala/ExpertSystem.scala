import scala.io.StdIn

object ExpertSystem extends App {
  @scala.annotation.tailrec
  private def readMode: Int = {
    val mode = StdIn.readLine("1 - режим приобретения знаний\n2 - режим решения задачи\n> ").toIntOption
    mode match {
      case Some(n) if n == 1 || n == 2 => n
      case _ =>
        println("Некорректный ввод!")
        readMode
    }
  }

  readMode match {
    case 1 => KnowledgeAcquisition.run
    case 2 => ProblemSolution.run
  }
}