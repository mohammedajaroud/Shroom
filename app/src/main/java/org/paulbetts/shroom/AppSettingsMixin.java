package org.paulbetts.shroom;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import org.paulbetts.shroom.core.ElementMixin;
import org.paulbetts.shroom.core.Lifecycle;
import org.paulbetts.shroom.core.LifecycleEvents;
import org.paulbetts.shroom.core.RxDaggerActivity;
import org.paulbetts.shroom.core.RxDaggerElement;
import org.paulbetts.shroom.ui.WelcomeActivity;

import javax.inject.Inject;

import rx.Observable;

public class AppSettingsMixin implements ElementMixin {
    private SharedPreferences prefs;

    @Inject
    RxDaggerActivity hostActivity;

    @Inject
    public AppSettingsMixin() { }

    @Override
    public Observable<Boolean> initializeHelper(RxDaggerElement host) {
        prefs = hostActivity.getSharedPreferences("Settings", 0);

        if (prefs.getBoolean("shouldShowInitialRun", true) && hostActivity.getClass() != WelcomeActivity.class) {
            Intent welcomeIntent = new Intent(hostActivity, WelcomeActivity.class);

            return Lifecycle.getLifecycleFor(hostActivity, LifecycleEvents.CREATE).take(1)
                    .flatMap(x -> hostActivity.startObsActivityForResult(welcomeIntent, android.R.anim.fade_in, android.R.anim.fade_out))
                    .map(x -> x.getValue0() == Activity.RESULT_OK)
                    .doOnNext(resultIsOk -> prefs.edit().putBoolean("shouldShowInitialRun", resultIsOk == false).commit())
                    .publishLast()
                    .refCount();
        } else {
            return Observable.from(Boolean.TRUE);
        }
    }
}
