package com.example.vinylstream;

import android.content.Context;
import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.OptionsProvider;
import com.google.android.gms.cast.framework.SessionProvider;
import java.util.List;
import android.util.Log;

public class CastOptionsProvider implements OptionsProvider {

    @Override
    public CastOptions getCastOptions(Context context) {
        Log.d("CastOptionsProvider", "Configuring CastOptions");
        return new CastOptions.Builder()
                .setReceiverApplicationId("E023FBC2")
                .build();
    }

    @Override
    public List<SessionProvider> getAdditionalSessionProviders(Context context) {
        return null; // No additional session providers are used in this basic setup.
    }
}
