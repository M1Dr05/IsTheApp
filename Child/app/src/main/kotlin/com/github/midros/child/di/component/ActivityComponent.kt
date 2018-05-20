package com.github.midros.child.di.component

import com.github.midros.child.di.PerActivity
import com.github.midros.child.di.module.ActivityModule
import com.github.midros.child.ui.login.LoginActivity
import com.github.midros.child.ui.main.MainActivity
import com.github.midros.child.ui.social.SocialActivity
import dagger.Component

/**
 * Created by luis rafael on 13/03/18.
 */
@PerActivity
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(loginActivity: LoginActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(socialActivity: SocialActivity)

}