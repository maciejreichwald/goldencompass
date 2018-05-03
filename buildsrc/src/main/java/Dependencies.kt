object ApplicationId {
    val application_id = "com.rudearts.goldencompass"
}

object Modules {
    val domain = ":domain"
    val data = ":data"
}

object Releases {
    val version_code = 1
    val version_name = "1.0"
}

object Modes {
    val dataBinding = true
}

object Versions {
    val gradle = "3.2.0-alpha13"
    val kotlin = "1.2.41"
    val versions = "0.17.0"

    val compile_sdk = 27
    val min_sdk = 21
    val target_sdk = 27

    val inject = "1"

    val databinding = "3.2.0-alpha10"
    val support = "27.1.1"
    val maps = "12.0.0"

    val rxandroid = "2.0.2"
    val rxkotlin = "2.2.0"
    val dagger = "2.15"
    val lifecycle = "1.1.1"

    val junit = "4.12"
    val assertj_core = "3.9.1"
    val mockito_kotlin = "1.5.0"
}

object Libraries {
    val inject = "javax.inject:javax.inject:${Versions.inject}"

    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    val databinding = "com.android.databinding:compiler:${Versions.databinding}"

    val maps = "com.google.android.gms:play-services-maps:${Versions.maps}"

    val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroid}"
    val rxkotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxkotlin}"

    val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    val lifecycle_extensions = "android.arch.lifecycle:extensions:${Versions.lifecycle}"
    val lifecycle_compiler = "android.arch.lifecycle:compiler:${Versions.lifecycle}"
}

object SupportLibraries {
    val appcompat_v7 = "com.android.support:appcompat-v7:${Versions.support}}"
    val design = "com.android.support:design:${Versions.support}"
    val cardview_v7 = "com.android.support:cardview-v7:${Versions.support}"
    val recyclerview_v7 = "com.android.support:recyclerview-v7:${Versions.support}"
}

object TestLibraries {
    val junit = "junit:junit:${Versions.junit}"
    val assertj_core = "org.assertj:assertj-core:${Versions.assertj_core}"
    val mockito_kotlin = "com.nhaarman:mockito-kotlin:${Versions.mockito_kotlin}"
    val lifecycle_testing = "android.arch.core:core-testing:${Versions.lifecycle}"
}