package com.example.reposcribe.data.repository

import com.example.reposcribe.data.remote.FirebaseAuthDataSource
import com.example.reposcribe.domain.model.User
import com.example.reposcribe.domain.repository.AuthRepository

//This class knows HOW to actually sign up the user and save extra info
//does not know about UI or business logic


//implements AuthRepository from domain
class AuthRepositoryImpl(
    private val ds: FirebaseAuthDataSource
) : AuthRepository {

    override suspend fun signup(
        email: String,
        password: String,
        githubUsername: String
    ): Result<User> = runCatching {
        val (uid, email) = ds.createUser(email, password, githubUsername)
        User(uid = uid, email = email, githubUsername = githubUsername)
    }

    override suspend fun login(
        email: String,
        password: String
    ): Result<User> = runCatching {
        val (uid, email) = ds.signIn(email, password)
        val github = ds.fetchGithubUsername(uid)
        User(uid = uid, email = email, githubUsername = github)
    }

    override suspend fun logOut() {
        ds.signOut()
    }

    override suspend fun getCurrentUser(): User? {
        val uid = ds.currentUid() ?: return null
        val github = ds.fetchGithubUsername(uid)  //now safe, offline handled
        val email = ds.fetchEmail(uid) ?: return null// Email not directly available here; you can store it in FireStore or read from auth
        return User(uid = uid, email = email, githubUsername = github)
    }

    override fun checkIfUserLoggedIn(): Boolean {
        return ds.checkIfUserLoggedIn()
    }

    override suspend fun updateGithubUsername(uid: String, newUsername: String) {
        ds.updateGithubUsername(uid, newUsername)
    }
}