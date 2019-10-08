import KnowledgeAcquisition.readResponse
import Utils._

object ProblemSolution {
  def run: Unit = {
    println("Режим решения задачи")
    val knowledgeBase = KnowledgeBase.read
    //    knowledgeBase.print
    search(knowledgeBase)
  }

  @scala.annotation.tailrec
  private def search(knowledgeBase: KnowledgeBase): Unit = {
    def checkResult: Unit =
      if (knowledgeBase.objectsArray.isEmpty) println("Неизвестный объект")
      else println(s"Искомый объект - ${knowledgeBase.objectsArray.head}")

    val zeroCharsIndices = knowledgeBase.charsArray.indices.filter(n => knowledgeBase.valueMatrix(n).count(_ == true) == 0)
    knowledgeBase.charsArray = knowledgeBase.charsArray.filterByIndices(n => !zeroCharsIndices.contains(n))
    knowledgeBase.valueMatrix = knowledgeBase.valueMatrix.filterByIndices(n => !zeroCharsIndices.contains(n))

    knowledgeBase.print

    val minCharIndex = knowledgeBase.charsArray.indices.minBy(n => knowledgeBase.valueMatrix(n).count(_ == true))
    if (readResponse(s"Имеет ли объект ${knowledgeBase.charsArray(minCharIndex)} ?")) {
      val objectsWithoutCharIndices = knowledgeBase.objectsArray.indices.filter(n => !knowledgeBase.valueMatrix(minCharIndex)(n))
      knowledgeBase.objectsArray = knowledgeBase.objectsArray.filterByIndices(n => !objectsWithoutCharIndices.contains(n))
      knowledgeBase.valueMatrix = knowledgeBase.valueMatrix.filterByIndices(n => !objectsWithoutCharIndices.contains(n))
      if (knowledgeBase.objectsArray.length > 1) search(knowledgeBase)
      else checkResult
    } else {
      val objectsWithCharIndices = knowledgeBase.objectsArray.indices.filter(n => knowledgeBase.valueMatrix(minCharIndex)(n))
      knowledgeBase.objectsArray = knowledgeBase.objectsArray.filterByIndices(n => !objectsWithCharIndices.contains(n))
      knowledgeBase.valueMatrix = knowledgeBase.valueMatrix.map(_.filterByIndices(n => !objectsWithCharIndices.contains(n)))
      if (knowledgeBase.objectsArray.length >= 2) search(knowledgeBase)
      else checkResult
    }
  }
}