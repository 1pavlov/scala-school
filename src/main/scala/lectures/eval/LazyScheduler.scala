package lectures.eval

import java.time.Clock

import scala.collection.SeqView

/**
  * В этом задании ваша задча реализовать своеобразный View с таймером.
  *
  * Он должен представлять из себя стандартный SeqView c ограничением по времени.
  * Т.е. этот view ведет себя как обычно, пока не истечет таймаут, предеданный при создании.
  * Как только таймаут истекает, view должен начать вести себя так, как будто коллекция пуста.
  *
  * Для решения задачи подставьте на место вопросительных знаков реализацию view.
  * Раскомментируйте и выполните тесты в lectures.eval.LazySchedulerTest
  */

object LazySchedulerView {

  implicit class SeqViewConverter[A](f: Seq[A]) {
    val c = Clock.systemDefaultZone()

    /**
      *
      * @param expirationTimeout - таймаут, после которого view становится пустым, в миллисекундах
      * @return - view
      */
    def lazySchedule(expirationTimeout: Long): SeqView[A, Seq[_]]  = {
      val pumpkinDeadline = c.instant().plusMillis(expirationTimeout)
      new SeqView[A, Seq[_]] {

        override def iterator = underlying.iterator

        override def length = underlying.length

        override def apply(idx: Int) = underlying.apply(idx)

        protected def underlying = if (isNotPumpkin) f else List.empty

        private def isNotPumpkin: Boolean = pumpkinDeadline.isAfter(c.instant())
      }
    }
  }
}

object LazySchedulerViewExample extends App {

  import LazySchedulerView._

  val v = List(1, 2, 3, 56)
  val d = v.lazySchedule(1300)

  println("Length at start: " + d.length)
  Thread.sleep(1500)
  println("Length after pumpkin time: " + d.length)
}