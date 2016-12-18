package com.jenshen.smartmirror.di.module.manager.session

import com.jenshen.smartmirror.di.scope.SessionScope
import com.jenshen.smartmirror.manager.firebase.auth.AuthManager
import com.jenshen.smartmirror.manager.firebase.auth.FirebaseAuthManager
import com.jenshen.smartmirror.manager.session.SessionManager
import com.jenshen.smartmirror.manager.session.UserSessionManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class UserSessionModule {

    @SessionScope
    @Binds
    abstract fun bindUserSessionManager(sessionManager: UserSessionManager) : SessionManager
}