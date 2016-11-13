package com.jenshen.smartmirror.di.module.manager

import com.jenshen.smartmirror.di.scope.UserScope
import com.jenshen.smartmirror.manager.auth.AuthManager
import com.jenshen.smartmirror.manager.auth.FirebaseAuthManager
import com.jenshen.smartmirror.manager.session.SessionManager
import com.jenshen.smartmirror.manager.session.UserSessionManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class UserSessionModule {

    @UserScope
    @Binds
    abstract fun bindUserSessionManager(sessionManager: UserSessionManager) : SessionManager
}