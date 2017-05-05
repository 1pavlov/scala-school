package lectures.operators

import lectures.functions.{Data, Computation, CurriedComputation, FunctionalComputation}

/**
  * В задачке из lectures.functions.Computations мы реализовали
  * один и тот же метод 3-мя разными способами
  *
  * Пришло время оценить, насколько разные имплементации
  * отличаются друг от друга по производительности
  *
  * Для этого
  *   * в классах CurriedComputation и FunctionalComputation уберите extends App, оставьте extends Data
  *   * раскомментируйте код, выполните в циклах вызов 3-х имплементаций,
  *   * оцените разницу во времени выполнения и объясните ее происхожение
  *
  */
object EvaluateOptimization extends App with Data {
  val computationStartTimestamp = System.currentTimeMillis()
  // ВЫПОЛНИТЬ В ЦИКЛЕ ОТ 1 ДО 100 Computation.computation
  for (i <- 1 to 100) {
    Computation.computation(filterData, dataArray)
  }
  println("Elapsed time in computation(): " + (System.currentTimeMillis() - computationStartTimestamp))

  val partiallyAppliedStartTimestamp = System.currentTimeMillis()
  // ВЫПОЛНИТЬ В ЦИКЛЕ ОТ 1 ДО 100 CurriedComputation.partiallyAppliedCurriedFunction
  for (i <- 1 to 100) {
    CurriedComputation.partiallyAppliedCurriedFunction(dataArray)
  }
  println("Elapsed time in partiallyAppliedCurriedFunction(): " + (System.currentTimeMillis() - partiallyAppliedStartTimestamp))

  val filterAppliedStartTimestamp = System.currentTimeMillis()
  // ВЫПОЛНИТЬ В ЦИКЛЕ ОТ 1 ДО 100 FunctionalComputation.filterApplied
  for (i <- 1 to 100) {
    FunctionalComputation.filterApplied(dataArray)
  }
  println("Elapsed time in filterApplied(): " + (System.currentTimeMillis() - filterAppliedStartTimestamp))

  // ВЫВЕСТИ РАЗНИЦУ В ПРОДОЛЖИТЕЛЬНОСТИ ВЫПОЛНЕНИЯ МЕЖДУ КАРРИРОВАННОЙ ВЕРСИЕЙ
  // И ФУНКЦИОНАЛЬНОЙ

  val diff = filterAppliedStartTimestamp - partiallyAppliedStartTimestamp - (System.currentTimeMillis() - filterAppliedStartTimestamp)

  println(s"Difference is about $diff milliseconds")
}

