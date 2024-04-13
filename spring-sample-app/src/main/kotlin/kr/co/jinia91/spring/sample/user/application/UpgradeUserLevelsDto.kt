package kr.co.jinia91.spring.sample.user.application

import kr.co.jinia91.spring.sample.user.domain.User

data class UpgradeUserLevelsInfo(val upgradedLists: List<UpgradeUserLevelsDto>)

data class UpgradeUserLevelsDto(
    val userId: String,
    val level: User.Level,
)

