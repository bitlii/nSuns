package com.bitco.nsuns.listeners;

import android.os.Bundle;

public interface DialogFragmentListener {
    public void onReturnValue(String value, int position);
    public void onReturnBundle(Bundle bundle);
}
