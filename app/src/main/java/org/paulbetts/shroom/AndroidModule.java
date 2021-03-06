package org.paulbetts.shroom;

// Cribbed from https://github.com/square/dagger/blob/master/examples/android-simple/src/main/java/com/example/dagger/simple/AndroidModule.java

import android.content.Context;

import org.paulbetts.shroom.core.CupboardModuleRegistration;
import org.paulbetts.shroom.core.DaggerApplication;
import org.paulbetts.shroom.core.ForApplication;
import org.paulbetts.shroom.models.RomInfo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nl.qbusict.cupboard.Cupboard;

/**
 * A module for Android-specific dependencies which require a {@link Context} or
 * {@link android.app.Application} to create.
 */
@Module(
        injects = {
        },
        library = true
)
public class AndroidModule {
    private final DaggerApplication application;

    public AndroidModule(DaggerApplication application) {
        this.application = application;
    }

    /**
     * Allow the application context to be injected but require that it be annotated with
     * {@link org.paulbetts.shroom.core.ForApplication @Annotation} to explicitly differentiate it from an activity context.
     */
    @Provides @Singleton @ForApplication
    Context provideApplicationContext() { return application; }

    @Provides @Singleton
    CupboardModuleRegistration providesCupboardModuleRegistration() {
        return new CupboardModuleRegistration() {
            boolean registered;

            @Override
            public void register(Cupboard cupboard) {
                if (registered) return;

                cupboard.register(PlayableRom.class);
                cupboard.register(RomInfo.class);
            }
        };
    }
}
