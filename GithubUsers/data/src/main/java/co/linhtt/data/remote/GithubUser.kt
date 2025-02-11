package co.linhtt.data.remote

import co.linhtt.data.db.entities.UserEntity

data class GithubUser(
    val login: String,
    val avatar_url: String,
    val html_url: String
)

fun GithubUser.toEntity(): UserEntity = UserEntity(login, avatar_url, html_url)