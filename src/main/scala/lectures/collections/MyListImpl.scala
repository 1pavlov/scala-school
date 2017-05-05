package lectures.collections

import scala.collection.mutable.{ListBuffer}

/**
  * Представим, что по какой-то причине Вам понадобилась своя обертка над списком целых чисел List[Int]
  *
  * Вы приняли решение, что будет достаточно реализовать 4 метода:
  * * * * * def flatMap(f: (Int => MyList)) -  реализуете на основе соответствующего метода из List
  * * * * * метод map(f: (Int) => Int) - с помощью только что полученного метода flatMap класса MyList
  * * * * * filter(???) - через метод flatMap класса MyList
  * * * * * foldLeft(acc: Int)(???) - через декомпозицию на head и tail
  *
  * Для того, чтобы выполнить задание:
  * * * * * раскомментируйте код
  * * * * * замените знаки вопроса на сигнатуры и тела методов
  * * * * * не используйте var и мутабильные коллекции
  *
  */
object MyListImpl extends App {

  case class MyList[T, T2 <: Iterable[T]](data: Iterable[T]) {

    def flatMap(f: (T => MyList[T, T2])): MyList[T, Iterable[T]] =
       MyList(data.flatMap(inp => f(inp).data))

    def map(f: T => T): MyList[T, Iterable[T]] =
      flatMap(inp => MyList(List(f(inp))))

    def foldLeft(acc: T)(f: ((T, T)) => T): T = data match {
      case head :: tail => MyList(tail).foldLeft(f(acc, head))(f)
      case _ => acc
    }

    def filter(f: (T => Boolean)) =
      flatMap(inp => MyList(if (f(inp)) List(inp) else Nil))
  }

  class MyListBuffer[T](data: ListBuffer[T]) extends MyList(data)

  class MyIndexedList[T](data: IndexedSeq[T]) extends MyList(data)

  require(MyList(List(1, 2, 3, 4, 5, 6)).map(_ * 2).data == List(2, 4, 6, 8, 10, 12))
  require(MyList(List(1, 2, 3, 4, 5, 6)).filter(_ % 2 == 0).data == List(2, 4, 6))
  require(MyList(List(1, 2, 3, 4, 5, 6)).foldLeft(0)((tpl) => tpl._1 + tpl._2) == 21)
  require(MyList(Nil).foldLeft(0)((tpl) => tpl._1 + tpl._2) == 0)


  require(new MyListBuffer[String](ListBuffer("1", "2", "3", "4", "5", "6")).filter(_.toInt % 2 == 0).data == List("2", "4", "6"))
  require(new MyIndexedList[String](IndexedSeq("1", "2", "3", "4", "5", "6")).filter(_.toInt % 2 == 0).data == IndexedSeq("2", "4", "6"))
}