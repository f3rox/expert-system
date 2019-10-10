import java.io._
import java.nio.file.{Files, Paths}

import scala.io.Source

case class KnowledgeBase(
                          var objectsArray: Array[String],
                          var charsArray: Array[String],
                          var valueMatrix: Array[Array[Boolean]]
                        ) {
  def write: Boolean = {
    if (Files.notExists(Paths.get(KnowledgeBase.directoryPath))) Files.createDirectory(Paths.get(KnowledgeBase.directoryPath))
    writeObjects && writeChars && writeMatrix
  }

  private def writeObjects: Boolean = {
    Files.deleteIfExists(Paths.get(KnowledgeBase.objectsPath))
    val objectsFile = new File(KnowledgeBase.objectsPath)
    val printWriter = new PrintWriter(objectsFile)
    printWriter.write(objectsArray.mkString("\n"))
    printWriter.close()
    objectsFile.length > 0
  }

  private def writeChars: Boolean = {
    Files.deleteIfExists(Paths.get(KnowledgeBase.charsPath))
    val charsFile = new File(KnowledgeBase.charsPath)
    val printWriter = new PrintWriter(charsFile)
    printWriter.write(charsArray.mkString("\n"))
    printWriter.close()
    charsFile.length > 0
  }

  private def writeMatrix: Boolean = {
    Files.deleteIfExists(Paths.get(KnowledgeBase.valueMatrixPath))
    val matrixFile = new File(KnowledgeBase.valueMatrixPath)
    val printWriter = new PrintWriter(matrixFile)
    printWriter.write(valueMatrix.map(_.map {
      case false => 0
      case true => 1
    }.mkString(" ")).mkString("\n"))
    printWriter.close()
    matrixFile.length > 0
  }

  def print: Unit = {
    println(s"Объекты: ${objectsArray.mkString(", ")}")
    println(s"Характеристики: ${charsArray.mkString(", ")}")
    println("Матрица значений: ")
    println(valueMatrix.map(_.mkString("\t")).mkString("\n"))
  }
}

object KnowledgeBase {
  private val rootPath: String = Paths.get("").toAbsolutePath.toString
  private val directoryPath = s"$rootPath/src/resources/knowledge_base"
  private val objectsPath = s"$directoryPath/objects.txt"
  private val charsPath = s"$directoryPath/characteristics.txt"
  private val valueMatrixPath = s"$directoryPath/matrix.txt"

  def read: KnowledgeBase = KnowledgeBase(readObjects, readChars, readMatrix)

  private def readObjects: Array[String] = {
    val source = Source.fromFile(objectsPath)
    val objectArray = source.getLines().toArray
    source.close()
    objectArray
  }

  private def readChars: Array[String] = {
    val source = Source.fromFile(charsPath)
    val charsArray = source.getLines().toArray
    source.close()
    charsArray
  }

  private def readMatrix: Array[Array[Boolean]] = {
    val source = Source.fromFile(valueMatrixPath)
    val valueMatrix = source.getLines().map(line => line.split(" ").map {
      case "0" => false
      case "1" => true
    }).toArray
    source.close()
    valueMatrix
  }
}