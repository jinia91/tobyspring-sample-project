package kr.co.jinia91.spring.sample.user

abstract class BusinessExceptions :RuntimeException()

abstract class UserException() : BusinessExceptions()

class AlreadyUserIdExist : UserException()

class InvalidUserName : UserException()