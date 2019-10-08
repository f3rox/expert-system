import scala.io.StdIn

object KnowledgeAcquisition {
  def run: Unit = {
    println("Режим приобретения знаний")
    val numberOfCharacteristics: Int = readNumberOfCharacteristics
    val characteristicsArray: Array[String] = readCharacteristicsArray(numberOfCharacteristics)
    val numberOfObjects: Int = readNumberOfObjects
    val objectsArray: Array[String] = readObjectsArray(numberOfObjects)
    val matrix: Array[Array[Boolean]] = readMatrix(objectsArray, characteristicsArray)
    if (KnowledgeBase(objectsArray, characteristicsArray, matrix).write) {
      println("База знаний успешно создана")
      KnowledgeBase.read.print
    }
    else println("Произошла ошибка при создании базы знаний!")
  }

  @scala.annotation.tailrec
  private def readNumberOfCharacteristics: Int = {
    val numberOfCharacteristics: Option[Int] = StdIn.readLine("Введите количество характеристик: ").toIntOption
    numberOfCharacteristics match {
      case Some(n) if n > 0 => n
      case _ =>
        println("Некорректный ввод!")
        readNumberOfCharacteristics
    }
  }

  private def readCharacteristicsArray(num: Int): Array[String] =
    Array.tabulate[String](num)(n => StdIn.readLine(s"Введите наименование ${n + 1}-й характеристики: "))

  @scala.annotation.tailrec
  private def readNumberOfObjects: Int = {
    val numberOfObjects: Option[Int] = StdIn.readLine("Введите количество объектов: ").toIntOption
    numberOfObjects match {
      case Some(n) if n > 0 => n
      case _ =>
        println("Некорректный ввод!")
        readNumberOfObjects
    }
  }

  private def readObjectsArray(num: Int): Array[String] =
    Array.tabulate[String](num)(n => StdIn.readLine(s"Введите наименование ${n + 1}-го объекта: "))

  private def readMatrix(objectsArray: Array[String], charsArray: Array[String]): Array[Array[Boolean]] = {
    val matrixMap = {
      for {
        objectIndex <- objectsArray.indices
        charIndex <- charsArray.indices
      } yield (objectIndex, charIndex) -> readResponse(s"Имеет ли ${objectsArray(objectIndex)} ${charsArray(charIndex)}?")
      }.toMap
    Array.tabulate[Boolean](charsArray.length, objectsArray.length)((c, o) => matrixMap(o, c))
  }

  @scala.annotation.tailrec
  def readResponse(message: String): Boolean = {
    val response: String = StdIn.readLine(s"$message\nд - да\nн - нет\n> ")
    response match {
      case "д" => true
      case "н" => false
      case _ =>
        println("Некорректный ввод!")
        readResponse(message)
    }
  }
}