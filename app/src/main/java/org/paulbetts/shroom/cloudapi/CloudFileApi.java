package org.paulbetts.shroom.cloudapi;

import org.paulbetts.shroom.models.RomInfo;

import rx.Observable;

/**
 * Created by paul on 8/14/14.
 */

public interface CloudFileApi {
    String getToken();
    void setToken(String value);

    Observable<String> authenticate();
    Observable<Boolean> testToken();
    Observable<RomInfo> scanForRoms();
}
