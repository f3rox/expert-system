object Utils {

  import scala.reflect.ClassTag

  implicit class ArrayOps[A: ClassTag](array: Array[A]) {
    def filterByIndices(p: Int => Boolean): Array[A] =
      array.zipWithIndex.filter { case (_, n) => p(n) }.map(_._1)
  }

}