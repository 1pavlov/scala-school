package lectures.collections

import scala.collection.JavaConverters._

/**
  * Постарайтесь не использовать мутабильные коллекции и var
  * Подробнее о сортировке можно подсмотреть здесь - https://en.wikipedia.org/wiki/Merge_sort
  *
  *
  */
object MergeSortImpl extends App {

  def mergeSort(data: Seq[Int]): Seq[Int] = {
    val length = data.length
    if (length <= 1)
      data
    else {
      val (left, right) = data.splitAt(length/2)
      merge(mergeSort(left), mergeSort(right))
    }
  }

  def merge(left: Seq[Int], right: Seq[Int]): Seq[Int] = (left, right) match {
    case(left, Nil) => left
    case(Nil, right) => right
    case(lHead :: lTail, rHead :: rTail) =>
      if (lHead < rHead) Seq(lHead) ++ merge(lTail, right)
      else Seq(rHead) ++ merge(left, rTail)
  }

  print(mergeSort(List(100, 70, 200, -200, -1, 1000)))
}
