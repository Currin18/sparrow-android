package com.jesusmoreira.sparrow.models

import twitter4j.User

data class User (
    val id: Long,
    val name: String = "",
    val email: String = "",
    val screenName: String = "",
    val location: String = "",
    val description: String = "",
    val isContributorsEnabled: Boolean,
    val profileImageURL: String = "",
//    val profileImageURLOriginal: String = "",
//    val profileImageURLBigger: String = "",
//    val profileImageURLMini: String = "",
//    val profileImageURL400x400: String = "",
    val profileImageURLHttps: String = "",
//    val profileImageURLHttpsBigger: String = "",
//    val profileImageURLHttpsMini: String = "",
//    val profileImageURLHttpsOriginal: String = "",
//    val profileImageURLHttps400x400: String = "",
    val isDefaultProfileImage: Boolean,
    val url: String? = ""
) {
    constructor(user: User): this(
        user.id,
        user.name,
        user.email
            ?: "'Request email address from users' on the Twitter dashboard is disabled",
        user.screenName,
        user.location,
        user.description,
        user.isContributorsEnabled,
        user.profileImageURL,
        user.profileImageURLHttps.replace("_normal", ""),
        user.isDefaultProfileImage,
        user.url
    )
}



//
//boolean isProtected();
//int getFollowersCount();
//Status getStatus();
//String getProfileBackgroundColor();
//String getProfileTextColor();
//String getProfileLinkColor();
//String getProfileSidebarFillColor();
//String getProfileSidebarBorderColor();
//boolean isProfileUseBackgroundImage();
//boolean isDefaultProfile();
//boolean isShowAllInlineMedia();
//int getFriendsCount();
//Date getCreatedAt();
//int getFavouritesCount();
//int getUtcOffset();
//String getTimeZone();
//String getProfileBackgroundImageURL();
//String getProfileBackgroundImageUrlHttps();
//String getProfileBannerURL();
//String getProfileBannerRetinaURL();
//String getProfileBannerIPadURL();
//String getProfileBannerIPadRetinaURL();
//String getProfileBannerMobileURL();
//String getProfileBannerMobileRetinaURL();
//String getProfileBanner300x100URL();
//String getProfileBanner600x200URL();
//String getProfileBanner1500x500URL();
//boolean isProfileBackgroundTiled();
//String getLang();
//int getStatusesCount();
//boolean isGeoEnabled();
//boolean isVerified();
//boolean isTranslator();
//int getListedCount();
//boolean isFollowRequestSent();
//URLEntity[] getDescriptionURLEntities();
//URLEntity getURLEntity();
//String[] getWithheldInCountries();