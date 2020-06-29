package com.bitco.nsuns.listeners;

import android.os.Bundle;

public interface DialogFragmentListener {
    void onReturnValue(String value, int position);
    void onReturnBundle(Bundle bundle);
}
