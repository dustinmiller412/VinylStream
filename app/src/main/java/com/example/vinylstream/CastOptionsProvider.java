package com.example.vinylstream;

import android.content.Context;
import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.OptionsProvider;
import com.google.android.gms.cast.framework.SessionProvider;
import java.util.List;

public class CastOptionsProvider implements OptionsProvider {

    @Override
    public CastOptions getCastOptions(Context context) {
        // Replace "YOUR_APPLICATION_ID" with your actual receiver application ID.
        return new CastOptions.Builder()
                .setReceiverApplicationId("E023FBC2")
                .build();
    }

    @Override
    public List<SessionProvider> getAdditionalSessionProviders(Context context) {
        return null; // No additional session providers are used in this basic setup.
    }
}
