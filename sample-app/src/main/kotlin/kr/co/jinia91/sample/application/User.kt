package kr.co.jinia91.sample.application

<<<<<<< Updated upstream
data class User(
    val id: String,
    var name: String,
    var password: String
)
=======
import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import jakarta.validation.Validator
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator

data class User(
    @field:Size(min = 4, max = 10)
    @field:NotEmpty
    val id: String,
    @field:Size(min = 1, max = 5)
    @field:NotEmpty
    var name: String,
    @field:Size(min = 6, max = 10)
    var password: String,
) {
    init {
        validateInvariants(this)
    }
}

internal val validator: Validator = Validation.byDefaultProvider()
    .configure()
    .messageInterpolator(ParameterMessageInterpolator())
    .buildValidatorFactory().validator

internal fun  validateInvariants(domain : Any) {
    val violations = validator.validate(domain)
    if (violations.isNotEmpty()) {
        throw DomainInvariantViolationException(domain, violations)
    }
}

class DomainInvariantViolationException(
    domain: Any,
    violations: Set<ConstraintViolation<Any>>
) : IllegalArgumentException(){
    override val message: String =
        domain.javaClass.simpleName + " 초기화 시 도메인 불변 규칙 위반\n" + violations.joinToString("") {
            "프로퍼티 : ${it.propertyPath}, 시도한 값 : ${it.invalidValue}, 에러메시지 : ${it.message}\n"
        }
}
>>>>>>> Stashed changes
