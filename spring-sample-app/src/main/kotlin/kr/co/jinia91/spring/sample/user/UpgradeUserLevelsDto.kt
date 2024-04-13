package kr.co.jinia91.spring.sample.user

data class UpgradeUserLevelsInfo(val upgradedLists: List<UpgradeUserLevelsDto>)

data class UpgradeUserLevelsDto(
    val userId: String,
    val level: User.Level,
)

