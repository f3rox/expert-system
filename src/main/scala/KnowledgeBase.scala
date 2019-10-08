import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}
import java.nio.file.{Files, Paths}

case class KnowledgeBase(
                          var objectsArray: Array[String],
                          var charsArray: Array[String],
                          var valueMatrix: Array[Array[Boolean]]
                        ) {
  def write: Boolean = {
    Files.deleteIfExists(Paths.get(KnowledgeBase.path))
    val objectOutputStream = new ObjectOutputStream(new FileOutputStream(KnowledgeBase.path))
    objectOutputStream.writeObject(this)
    objectOutputStream.close()
    Files.exists(Paths.get(KnowledgeBase.path))
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
  private val path = s"$rootPath/src/resources/knowledge_base"

  def read: KnowledgeBase = {
    val objectInputStream = new ObjectInputStream(new FileInputStream(path))
    val knowledgeBase = objectInputStream.readObject.asInstanceOf[KnowledgeBase]
    objectInputStream.close()
    knowledgeBase
  }
}