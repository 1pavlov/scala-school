package lectures.oop

import lectures.functions.SQLAPI

/**
  * У вас есть приложение, которое можно запустить в тестовом или продуктовом окружении.
  * В зависимости от окружения ваше приложение создает БД с тестовым или бовым адресом
  * и использует подходящую реализацию сервиса.
  *
  * Ваша задача: реализовать методы doSomeService в тестовом и боевом сервисах. Для этого:
  * * * * добавьте SQLAPI как self type annotation
  * * * * уберите знаки вопроса и раскомментируйте execute(sql)
  * * * * допишите тело функций в соответсвии с комментарием в каждом методе
  * После этого допишите инициализацию usefulService в Application так, чтобы:
  *   в тестовой среде использовался TestServiceImpl
  *   в боевой - ProductionServiceImpl
  *
  * Допишите тесты в ApplicationTest
  *
  */

trait UsefulService {
  def doSomeService(): Int
}

trait TestServiceImpl extends UsefulService {
  self: SQLAPI =>

  private val sql = "do the SQL query and then count words"

  // Считает количество слов в результате execute
  def doSomeService() = {
    execute(sql).split(" ").length
  }
}

trait ProductionServiceImpl extends UsefulService {
  self: SQLAPI =>

  private val sql = "do the SQL query and than count 'a' sympols"

  // Считает количество символов 'a' в результате execute
  def doSomeService() = {
    execute(sql).count(_ == 'a')
  }
}

class Application(isTestEnv: Boolean) {
  val usefulService : UsefulService = if (isTestEnv)
    new SQLAPI("test db Resource") with TestServiceImpl
  else
    new SQLAPI("production Resource") with ProductionServiceImpl

  def doTheJob() = usefulService.doSomeService()
}
