package kr.co.jinia91.spring.sample.user

import io.kotest.assertions.withClue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kr.co.jinia91.spring.sample.user.application.UserUserCases
import kr.co.jinia91.spring.sample.user.domain.EVENT_STATUS
import kr.co.jinia91.spring.sample.user.domain.EventStatus
import kr.co.jinia91.spring.sample.user.domain.User
import kr.co.jinia91.spring.sample.user.domain.UserLevelUpgradeDefaultPolicy.MIN_LOG_COUNT_FOR_SILVER
import kr.co.jinia91.spring.sample.user.domain.UserLevelUpgradeDefaultPolicy.MIN_POST_COUNT_FOR_GOLD
import kr.co.jinia91.spring.sample.user.domain.UserLevelUpgradeEventPolicy
import kr.co.jinia91.spring.sample.user.domain.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import sample.sample.validUser

/**
 * 사용자 레벨 관리 유즈케이스
 *
 * - 사용자는 유저 레벨이 존재하며 BASIC, SILVER, GOLD 세가지 있다
 * - 가입 후 50회 이상 로그인을 하면 BASIC에서 SILVER로 레벨이 업그레이드 된다
 * - SILVER 레벨에서 30회 이상 게시글을 작성하면 GOLD로 레벨이 업그레이드 된다
 * - 사용자 레벨의 변경작업은 일정한 주기를 가지고 일괄적으로 진행된다. 변경 작업 전에는 조건을 충족하더라도 레벨의 변경이 일어나지않는다.(배치작업)
 *   (유즈케이스의 관심사가 아님, 어댑터 레이어에서 제어하면 된다)
 */
@SpringBootTest
@Transactional
class UpgradeUserLevelsTests {
    @Autowired
    private lateinit var sut: UserUserCases

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `BASIC 사용자는 50회 이상 로그인을 하면 SILVER 레벨로 업그레이드 된다`() {
        // given
        withClue("로그인 50회를 한 BASIC 신규 유저가 존재한다") {
            val userWith50LogInCount = User.newOne(
                id = "11112",
                name = "jinia",
                password = "1Q2w3e4r1!",
                email = null
            ).apply {
                logInCount = MIN_LOG_COUNT_FOR_SILVER
            }
            userRepository.save(userWith50LogInCount)
        }

        // when
        sut.upgradeUserLevels()

        // then
        val user = userRepository.findById("11112")
        user.shouldNotBeNull()
        user.level shouldBe User.Level.SILVER
    }

    @Test
    fun `BASIC 사용자가 아니면 50 이상 로그인해도 SILVER 레벨로 업그레이드 되지 않는다`() {
        // given
        withClue("BASIC 신규 유저가 존재한다") {
            val noneUpgradeTargetUser = validUser()
            userRepository.save(noneUpgradeTargetUser)
        }

        // when
        sut.upgradeUserLevels()

        // then
        val user = userRepository.findById("jinia91")
        user.shouldNotBeNull()
        user.level shouldBe User.Level.BASIC
    }

    @Test
    fun `SILVER 레벨에서 30회 이상 게시글을 작성하면 GOLD 레벨로 업그레이드 된다`() {
        // given
        withClue("게시글 30회를 작성한 SILVER 유저가 존재한다") {
            val userWith30PostCount = validUser().apply {
                level = User.Level.SILVER
                logInCount = MIN_LOG_COUNT_FOR_SILVER
                postCount = MIN_POST_COUNT_FOR_GOLD
            }
            userRepository.save(userWith30PostCount)
        }

        // when
        sut.upgradeUserLevels()

        // then
        val user = userRepository.findById("jinia91")
        user.shouldNotBeNull()
        user.level shouldBe User.Level.GOLD
    }

    @Test
    fun `30회 이상 게시글을 작성하더라도 SILVER 레벨이 아니면 GOLD 레벨로 업그레이드 되지 않는다`() {
        // given
        withClue("게시글 30회를 작성한 BASIC 유저가 존재한다") {
            val noneUpgradeTargetUser = validUser().apply {
                logInCount = MIN_LOG_COUNT_FOR_SILVER
                postCount = MIN_POST_COUNT_FOR_GOLD
            }
            userRepository.save(noneUpgradeTargetUser)
        }

        // when
        sut.upgradeUserLevels()

        // then
        val user = userRepository.findById("jinia91")
        user.shouldNotBeNull()
        user.level shouldBe User.Level.SILVER
    }

    @Test
    fun `주어진 데이터 셋트 중 업그레이드 대상을 적절히 업그레이드한다`() {
        val list = withClue("5가지 데이터셋 준비") { build5Users() }
        list.forEach { userRepository.save(it) }

        // when
        val result = sut.upgradeUserLevels()

        // then
        withClue("업그레이드 대상은 2명이다") {
            result.upgradedLists.map { it.userId }
        }
        withClue("2번은 SILVER로 업그레이드 되어야 한다") {
            val expectedSilver = result.upgradedLists.find { it.userId == "2" }
            expectedSilver.shouldNotBeNull()
            expectedSilver.level shouldBe User.Level.SILVER
        }
        withClue("4번은 GOLD로 업그레이드 되어야 한다") {
            val expectedGold = result.upgradedLists.find { it.userId == "4" }
            expectedGold.shouldNotBeNull()
            expectedGold.level shouldBe User.Level.GOLD
        }

        // 상태 검증
        withClue("나머지는 레벨이 그대로이다") {
            userRepository.findById("1")?.level shouldBe User.Level.BASIC
            userRepository.findById("3")?.level shouldBe User.Level.SILVER
            userRepository.findById("5")?.level shouldBe User.Level.GOLD
        }
    }

    private fun build5Users() = listOf(
        User.newOne("1", "jinia1", "1Q2w3e4r1!", email = null).apply {
            level = User.Level.BASIC
            postCount = MIN_POST_COUNT_FOR_GOLD - 1
        },
        User.newOne("2", "jinia2", "1Q2w3e4r1!", email = null).apply {
            level = User.Level.BASIC
            logInCount = MIN_LOG_COUNT_FOR_SILVER
        },
        User.newOne("3", "jinia3", "1Q2w3e4r1!", email = null).apply {
            level = User.Level.SILVER
            logInCount = MIN_LOG_COUNT_FOR_SILVER + 10
            postCount = MIN_POST_COUNT_FOR_GOLD - 1

        },
        User.newOne("4", "jinia4", "1Q2w3e4r1!", email = null).apply {
            level = User.Level.SILVER
            logInCount = MIN_LOG_COUNT_FOR_SILVER + 10
            postCount = MIN_POST_COUNT_FOR_GOLD
        },
        User.newOne("5", "jinia5", "1Q2w3e4r1!", email = null).apply {
            level = User.Level.GOLD
            logInCount = MIN_LOG_COUNT_FOR_SILVER + 10
            postCount = MIN_POST_COUNT_FOR_GOLD + 10
        },
    )

    @Test
    fun `이벤트 기간동안은 이벤트 정책에 따라 레벨이 업그레이드 된다`() {
        // given
        EVENT_STATUS = EventStatus.EVENT

        withClue("이벤트 기간동안 로그인 5회를 한 BASIC 신규 유저가 존재한다") {
            val userWith5LogInCount = User.newOne(
                id = "11112",
                name = "jinia",
                password = "1Q2w3e4r1!",
                email = null
            ).apply {
                logInCount = UserLevelUpgradeEventPolicy.MIN_LOG_COUNT_FOR_SILVER
            }
            userRepository.save(userWith5LogInCount)
        }

        // when
        sut.upgradeUserLevels()

        // then
        val user = userRepository.findById("11112")
        user.shouldNotBeNull()
        user.level shouldBe User.Level.SILVER

        // tearDown
        EVENT_STATUS = EventStatus.DEFAULT
    }
}