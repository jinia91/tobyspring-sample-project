package kr.co.jinia91.spring.sample.user.domain

abstract class BusinessExceptions :RuntimeException()

abstract class UserException() : BusinessExceptions()

class AlreadyUserIdExist : UserException()

class InvalidUserName : UserException()

class InvalidPassword : UserException()
