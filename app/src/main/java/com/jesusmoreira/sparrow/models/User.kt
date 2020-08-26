package com.jesusmoreira.sparrow.models

import twitter4j.User

data class User (
    val id: Long,
    val name: String = "",
    val email: String = "",
    val screenName: String = "",
    val location: String = "",
    val description: String = "",
    val url: String? = "",
//    val status: Status?,
//    val createdAt: Date?
    val utcOffset: Int = 0,
    val timeZone: String? = "",
    val lang: String? = "",
    val withheldInCountries: ArrayList<String>? = arrayListOf(),
    val isProtected: Boolean,
    val isVerified: Boolean,
    val isContributorsEnabled: Boolean,
    val isDefaultProfile: Boolean,
    val isShowAllInlineMedia: Boolean,
    val isGeoEnabled: Boolean,
    val isTranslator: Boolean,
    val isFollowRequestSent: Boolean,
    val isDefaultProfileImage: Boolean,
    val isProfileUseBackgroundImage: Boolean,
    val isProfileBackgroundTiled: Boolean,
    val profileImageURL: String? = "",
//    val profileImageURLOriginal: String = "",
//    val profileImageURLBigger: String = "",
//    val profileImageURLMini: String = "",
//    val profileImageURL400x400: String = "",
    val profileImageURLHttps: String? = "",
//    val profileImageURLHttpsBigger: String = "",
//    val profileImageURLHttpsMini: String = "",
//    val profileImageURLHttpsOriginal: String = "",
//    val profileImageURLHttps400x400: String = "",
    val profileBannerURL: String? = "",
    val profileBackgroundImageURL: String? = "",
    val profileBackgroundImageUrlHttps: String? = "",
    val profileBackgroundColor: String? = "",
    val followersCount: Int = 0,
    val friendsCount: Int = 0,
    val favouritesCount: Int = 0,
    val statusesCount: Int = 0,
    val listedCount: Int = 0
) {
    constructor(user: User): this(
        user.id,
        user.name,
        user.email
            ?: "'Request email address from users' on the Twitter dashboard is disabled",
        user.screenName,
        user.location,
        user.description,
        user.url,
//        user.status,
//        user.createdAt
        user.utcOffset,
        user.timeZone,
        user.lang,
        ArrayList(user.withheldInCountries?.toMutableList() ?: arrayListOf()),
        user.isVerified,
        user.isProtected,
        user.isContributorsEnabled,
        user.isDefaultProfile,
        user.isShowAllInlineMedia,
        user.isGeoEnabled,
        user.isTranslator,
        user.isFollowRequestSent,
        user.isDefaultProfileImage,
        user.isProfileUseBackgroundImage,
        user.isProfileBackgroundTiled,
        user.profileImageURL,
        user.profileImageURLHttps?.replace("_normal", ""),
        user.profileBannerURL,
        user.profileBackgroundImageURL,
        user.profileBackgroundImageUrlHttps,
        user.profileBackgroundColor,
        user.followersCount,
        user.friendsCount,
        user.favouritesCount,
        user.statusesCount,
        user.listedCount
    )
}



//
//String getProfileTextColor();
//String getProfileLinkColor();
//String getProfileSidebarFillColor();
//String getProfileSidebarBorderColor();
//String getProfileBannerRetinaURL();
//String getProfileBannerIPadURL();
//String getProfileBannerIPadRetinaURL();
//String getProfileBannerMobileURL();
//String getProfileBannerMobileRetinaURL();
//String getProfileBanner300x100URL();
//String getProfileBanner600x200URL();
//String getProfileBanner1500x500URL();
//URLEntity[] getDescriptionURLEntities();
//URLEntity getURLEntity();