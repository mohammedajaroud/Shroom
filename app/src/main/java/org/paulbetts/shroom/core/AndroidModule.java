package org.paulbetts.shroom.core;

// Cribbed from https://github.com/square/dagger/blob/master/examples/android-simple/src/main/java/com/example/dagger/simple/AndroidModule.java

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
     * {@link ForApplication @Annotation} to explicitly differentiate it from an activity context.
     */
    @Provides @Singleton @ForApplication
    Context provideApplicationContext() {
        return application;
    }
}
