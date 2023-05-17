package com.cyn.wandroider.ui.page.home.recommened

data class BannerInfo(
    var desc: String?,
    var id: Int,
    var imagePath: String?,
    var isVisible: Int,
    var order: Int,
    var title: String?,
    var type: Int,
    var url: String?
)